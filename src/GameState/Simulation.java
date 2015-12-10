package GameState;

import Evenements.AsteroidIncoming;
import Evenements.FireEvent;
import GameObjects.Zone;
import Utilities.JukeBox;
import Handlers.Keys;
import Interface.DocksPanel;
import Interface.EchapPanel;
import Interface.Main.RadarView;
import Interface.Main.RessourcesView;
import Interface.Main.SpaceStationView;
import Interface.MainPanel;
import Interface.MissionPanel;
import Interface.callUrgencesPanel;
import Utilities.Time;
import Utilities.Variables;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public final class Simulation extends GameState {

    private static final String[] missionMessages = {"Caution : Asteroid incomming!", "Warning : Fire!"};
    private static final int ASTEROID = 0;
    private static final int FIRE = 1;

    private int tWidth, tHeight, mWidth, sWidth, sHeight;

    private Zone space;

    private boolean echap = false, call = false, fireEvent = false;

    //Gestion des MainPanels
    private int curMainPanel;
    private static final int NUMPANELS = 3;
    private static final int RADAR = 0;
    private static final int STATION = 1;
    private static final int RESSOURCES = 2;

    private RadarView radarView;
    private SpaceStationView SSView;
    private RessourcesView ReView;
    private MainPanel[] mainPanel;

    private MissionPanel missionPanel;
    private DocksPanel docksPanel;
    private EchapPanel echapPanel;
    private callUrgencesPanel callPanel;

    private Variables var;

    public Simulation(GameStateManager gsm) {
        super(gsm);
        init();
        Timer timer;
        timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int number = var.randNum(0, 149);
                if (number >= 35 & number < 100) { //40
                    AsteroidIncoming asteroid = new AsteroidIncoming();
                    asteroid.launch();
                    radarView.createAsteroid();
                    missionPanel.addMision(missionMessages[ASTEROID]);
                }
                if (number >= 30 & number < 35 & !fireEvent) {
                    FireEvent fire = new FireEvent(SSView.getListBuildings());
                    fire.launch();
                    missionPanel.addMision(missionMessages[FIRE]);
                    fireEvent = true;
                }
                if (number >= 0 & number < 30) {
                    int numShips = var.randNum(0, 2);
                    radarView.createVehicle(numShips);
                }
            }
        });
        timer.start();
    }

    @Override
    public void init() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        space = new Zone("SPACE");

        JukeBox.load("/SFX/radar.mp3", "radar");
        JukeBox.load("/SFX/amongTheStars.mp3", "mainBG");

        tWidth = (int) screenSize.getWidth();
        mWidth = tWidth - tWidth / 5;
        sWidth = tWidth - mWidth;
        tHeight = (int) screenSize.getHeight();
        sHeight = tHeight / 2;

        mainPanel = new MainPanel[NUMPANELS];
        radarView = new RadarView("Radar View", space, sWidth, mWidth, tWidth, tHeight, "radar");
        SSView = new SpaceStationView("Space Station View", sWidth, mWidth, tWidth, tHeight, null);
        ReView = new RessourcesView("Ressources View", sWidth, mWidth, tWidth, tHeight, null, SSView.getListBuildings());
        missionPanel = new MissionPanel("Missions", sWidth, tHeight);
        docksPanel = new DocksPanel("Docks", sWidth, tHeight);
        echapPanel = new EchapPanel(tWidth, tHeight);
        callPanel = new callUrgencesPanel(tWidth, tHeight);

        docksPanel.setListBuilding(SSView.getListBuildings());

        var = new Variables();

        mainPanel[RADAR] = radarView;
        mainPanel[STATION] = SSView;
        mainPanel[RESSOURCES] = ReView;
        curMainPanel = RADAR;

        //JukeBox.loop("mainBG");
        //JukeBox.loop(mainPanel[curMainPanel].getSound());
    }

    @Override
    public void update() {
        Time.update();
        for (MainPanel mP : mainPanel) {
            mP.update();
            if (mP.getGameOver()) {
                gsm.setState(GameStateManager.GAMEOVERSTATE);
            }
        }

        if (echap) {
            if (echapPanel.getEchap()) {
                gsm.setState(GameStateManager.MENUSTATE);
            } else if (echapPanel.getStay()) {
                echap = false;
                echapPanel.reInit();
            }
            if (call) {
                callPanel.reInit();
                call = !call;
                echap = !echap;
            }
        }

        if (call) {
            SSView.setDetailBar(false);
            if (callPanel.isGoodNum()) {
                SSView.setDetailBar(true);
                SSView.getIPanel().handleInput();
                SSView.getIPanel().setUrgence(true);
                if (SSView.getIPanel().getUrgenceSent()) {
                    SSView.setDetailBar(false);
                    SSView.getIPanel().reInit();
                    callPanel.reInit();
                    call = !call;
                }
            }
        }

        missionPanel.update();
        docksPanel.update();

        for (int i = 0; i < radarView.getListToDock().size(); i++) {
            int randHangar = var.randNum(0, 4);
            SSView.getListHangars().get(randHangar).addCapacity(radarView.getListToDock().get(i).getVolume());
            radarView.getListToDock().get(i).setDestination(SSView.getListHangars().get(randHangar));
            radarView.getListToDock().get(i).dock();
            radarView.removeFromListToDock(radarView.getListToDock().get(i));
        }

        ReView.setShieldOn(radarView.getShieldOn());

        if (radarView.getMinusAsteroid() != 0) {
            missionPanel.delMission(missionMessages[ASTEROID], radarView.getMinusAsteroid());
            radarView.resetMinusAsteroid();
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

        if (echap && !call) {
            echapPanel.draw(g);
        }

        if (call && !echap) {
            callPanel.draw(g);
        }

    }

    @Override
    public void handleInput() {
        if (echap) {
            echapPanel.handleInput();
        } else if (call) {
            callPanel.handleInput();
        } else {
            mainPanel[curMainPanel].handleInput();

            if (Keys.isPressed(Keys.RIGHT)) {
                JukeBox.stop(mainPanel[curMainPanel].getSound());
                curMainPanel++;
                if (curMainPanel > NUMPANELS - 1) {
                    curMainPanel = 0;
                }
                //JukeBox.loop(mainPanel[curMainPanel].getSound());
            }

            if (Keys.isPressed(Keys.ENTER)) {
                boolean isDetailBarOn = mainPanel[curMainPanel].getDetailBarOn();
                if (!isDetailBarOn) {
                    mainPanel[curMainPanel].setDetailBar(true);
                } else {
                    mainPanel[curMainPanel].setDetailBar(false);
                }
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

        if (Keys.isPressed(Keys.ECHAP)) {
            echap = !echap;
        }
        if (Keys.isPressed(Keys.N) && curMainPanel == STATION) {
            call = !call;
        }

    }

}
