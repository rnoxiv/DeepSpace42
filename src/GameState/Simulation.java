package GameState;

import GameObjects.Zone;
import Utilities.JukeBox;
import Handlers.Keys;
import Interface.DocksPanel;
import Interface.Main.RadarView;
import Interface.Main.RessourcesView;
import Interface.Main.SpaceStationView;
import Interface.MainPanel;
import Interface.MissionPanel;
import Utilities.Variables;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;

public class Simulation extends GameState {
    
    private int tWidth, tHeight, mWidth, sWidth, sHeight;
    
    private Zone space, docks, city; 
    
    //Gestion des MainPanels
    private int curMainPanel;
    private static final int NUMPANELS = 3;
    private static final int RADAR = 0;
    private static final int STATION = 1;
    private static final int RESSOURCES = 2;
    //Gestion des Hangars
    private static final int HANGAR_A = 19;
    private static final int HANGAR_B = 20;
    private static final int HANGAR_C = 21;
    private static final int HANGAR_D = 22;
    private static final int HANGAR_E = 23;
    
    private RadarView radarView;
    private SpaceStationView SSView;
    private RessourcesView ReView;
    private MainPanel[] mainPanel;
    
    private MissionPanel missionPanel;
    private DocksPanel docksPanel;
    
    private Variables var;

    public Simulation(GameStateManager gsm) {
        super(gsm);
        init();
    }
    
    @Override
    public void init() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        space = new Zone("SPACE");
        docks = new Zone("DOCKS");
        city = new Zone("CITY");
        
        JukeBox.load("/SFX/radar.mp3", "radar");
        JukeBox.load("/SFX/amongTheStars.mp3", "mainBG");
        
        tWidth = (int) screenSize.getWidth();
        mWidth = tWidth - tWidth / 5;
        sWidth = tWidth - mWidth;
        tHeight = (int) screenSize.getHeight();
        sHeight = tHeight / 2;

        mainPanel = new MainPanel[NUMPANELS];
        radarView = new RadarView("Radar View", space, sWidth, mWidth, tWidth, tHeight, "radar");
        SSView = new SpaceStationView("Space Station View", sWidth, mWidth, tWidth, tHeight,null);
        ReView = new RessourcesView("Ressources View", sWidth, mWidth, tWidth, tHeight, null);
        missionPanel = new MissionPanel("Missions",sWidth,tHeight);
        docksPanel = new DocksPanel("Docks",sWidth,tHeight);
        
        docksPanel.setListBuilding(SSView.getListBuildings());
        
        var = new Variables();
        
        mainPanel[RADAR] = radarView;
        mainPanel[STATION] = SSView;
        mainPanel[RESSOURCES]  = ReView;
        curMainPanel = RADAR;
        
        JukeBox.loop("mainBG");
        JukeBox.loop(mainPanel[curMainPanel].getSound());
        
    }
    
    @Override
    public void update() {
        for (int i = 0; i < mainPanel.length; i++) {
            mainPanel[i].update();
        }
        missionPanel.update();
        docksPanel.update();
        
        for(int i=0; i<radarView.getListToDock().size();i++){
            int randHangar = var.randNum(0,4);
               SSView.getListHangars().get(randHangar).addCapacity(radarView.getListToDock().get(i).getVolume());
               radarView.getListToDock().get(i).setDestination(SSView.getListHangars().get(randHangar));
               radarView.getListToDock().get(i).dock();
               radarView.removeFromListToDock(radarView.getListToDock().get(i));
        }
        handleInput();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, tWidth, tHeight);

        mainPanel[curMainPanel].draw(g);
        missionPanel.draw(g);
        docksPanel.draw(g);

        g.setColor(var.getMainColor());
        g.fillRect(sWidth, 0, 2, tHeight);
        g.fillRect(0, sHeight, sWidth, 2);
        g.drawRect(0, 0, tWidth - 1, tHeight - 1);

    }
    
    @Override
    public void handleInput() {
        mainPanel[curMainPanel].handleInput();
        if (Keys.isPressed(Keys.RIGHT)) {
            JukeBox.stop(mainPanel[curMainPanel].getSound());
            curMainPanel++;
            if (curMainPanel > NUMPANELS - 1) {
                curMainPanel = 0;
            }
            //JukeBox.loop(mainPanel[curMainPanel].getSound());
        }
        
        if (Keys.isPressed(Keys.ECHAP)) {
            System.exit(0);
        }
        
        if (Keys.isPressed(Keys.ENTER)) {
            boolean isDetailBarOn = mainPanel[curMainPanel].getDetailBarOn();
            if(!isDetailBarOn)
                mainPanel[curMainPanel].setDetailBar(true);
            else
                mainPanel[curMainPanel].setDetailBar(false);
        }

        if (Keys.isPressed(Keys.LEFT)) {
            JukeBox.stop(mainPanel[curMainPanel].getSound());
            curMainPanel--;
            if (curMainPanel < 0) {
                curMainPanel = NUMPANELS - 1;
            }
            //JukeBox.loop(mainPanel[curMainPanel].getSound());
        }
    }

}
