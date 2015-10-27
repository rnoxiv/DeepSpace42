package GameState;

import Audio.JukeBox;
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
import java.util.ArrayList;

public class Simulation extends GameState {

    private int tWidth, tHeight, mWidth, sWidth, sHeight;
    private int curMainPanel;
    private static final int NUMPANELS = 3;

    private RadarView radarView;
    private SpaceStationView SSView;
    private RessourcesView ReView;
    private ArrayList<MainPanel> mainPanel;
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
        tWidth = (int) screenSize.getWidth();
        mWidth = tWidth - tWidth / 5;
        sWidth = tWidth - mWidth;
        tHeight = (int) screenSize.getHeight();
        sHeight = tHeight / 2;

        mainPanel = new ArrayList<>();
        radarView = new RadarView("Radar View", sWidth, mWidth, tWidth, tHeight);
        SSView = new SpaceStationView("Space Station View", sWidth, mWidth, tWidth, tHeight);
        ReView = new RessourcesView("Ressources View", sWidth, mWidth, tWidth, tHeight);
        missionPanel = new MissionPanel("Missions",sWidth,tHeight);
        docksPanel = new DocksPanel("Docks",sWidth,tHeight);
        
        var = new Variables();
        
        mainPanel.add(radarView);
        mainPanel.add(SSView);
        mainPanel.add(ReView);
        curMainPanel = 0;
        JukeBox.load("/SFX/radar.mp3", "radar");
        JukeBox.loop("radar");
    }

    @Override
    public void update() {
        for (int i = 0; i < mainPanel.size(); i++) {
            mainPanel.get(i).update();
        }
        missionPanel.update();
        docksPanel.update();
        handleInput();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, tWidth, tHeight);

        mainPanel.get(curMainPanel).draw(g);
        missionPanel.draw(g);
        docksPanel.draw(g);

        g.setColor(var.getMainColor());
        g.fillRect(sWidth, 0, 2, tHeight);
        g.fillRect(0, sHeight, sWidth, 2);
        g.drawRect(0, 0, tWidth - 1, tHeight - 1);

    }

    @Override
    public void handleInput() {
        mainPanel.get(curMainPanel).handleInput();
        if (Keys.isPressed(Keys.RIGHT)) {
            curMainPanel++;
            if (curMainPanel > NUMPANELS - 1) {
                curMainPanel = 0;
            }
        }
        
        if (Keys.isPressed(Keys.ECHAP)) {
            System.exit(0);
        }
        
        if (Keys.isPressed(Keys.ENTER)) {
            boolean isDetailBarOn = mainPanel.get(curMainPanel).getDetailBarOn();
            if(!isDetailBarOn)
                mainPanel.get(curMainPanel).setDetailBar(true);
            else
                mainPanel.get(curMainPanel).setDetailBar(false);
        }

        if (Keys.isPressed(Keys.LEFT)) {
            curMainPanel--;
            if (curMainPanel < 0) {
                curMainPanel = NUMPANELS - 1;
            }
        }
    }

}
