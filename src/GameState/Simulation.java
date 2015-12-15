package GameState;

import Evenements.AsteroidIncoming;
import Evenements.FireEvent;
import GameObjects.Zone;
import GameObjects.Zones.Building;
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

    private final Timer timer;

    private int tWidth, tHeight, mWidth, sWidth, sHeight;

    private Zone space;

    private boolean echap = false, call = false, fireEvent = false;

    private final int maxChoice = 150;

    //Gestion des MainPanels
    private int curMainPanel;
    private static final int NUMPANELS = 3;
    private static final int RADAR = 0;
    private static final int STATION = 1;
    private static final int RESSOURCES = 2;

    private FireEvent fire;
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
        fire = new FireEvent(SSView.getListBuildings());
        timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int number = var.randNum(1, maxChoice);
                if (number >= 1 & number < 5) {
                    AsteroidIncoming asteroid = new AsteroidIncoming();
                    asteroid.launch();
                    radarView.createAsteroid();
                    missionPanel.addMision(missionMessages[ASTEROID]);
                    JukeBox.play("asteroid");
                }
                if (number >= 5 & number < 150 & !fireEvent) {
                    fire = new FireEvent(SSView.getListBuildings());
                    fireEvent = true;
                }
                if (number >= 10 & number < 50) {
                    int numShips = var.randNum(0, 2);
                    radarView.createVehicle(numShips, null);
                }
                if (number >= 50 & number < 100) {
                    SSView.peopleTraffic();
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
        JukeBox.load("/SFX/mainBG.mp3", "mainBG");
        JukeBox.load("/SFX/AsteroidIncomming.mp3", "asteroid");
        JukeBox.load("/SFX/FireDetected.mp3", "fire");
        JukeBox.load("/SFX/LaserAmmo.mp3", "ammo");
        JukeBox.load("/SFX/LowRessources.mp3", "lowRessource");
        JukeBox.load("/SFX/ShieldActivated.mp3", "shieldOn");
        JukeBox.load("/SFX/ShieldDeactivated.mp3", "shieldOff");
        JukeBox.load("/SFX/WelcomeToDS42.mp3", "welcome");

        tWidth = (int) screenSize.getWidth();
        mWidth = tWidth - tWidth / 5;
        sWidth = tWidth - mWidth;
        tHeight = (int) screenSize.getHeight();
        sHeight = tHeight / 2;

        mainPanel = new MainPanel[NUMPANELS];
        radarView = new RadarView("Radar View", space, sWidth, mWidth, tWidth, tHeight, "radar");
        SSView = new SpaceStationView("Space Station View", sWidth, mWidth, tWidth, tHeight, null);
        SSView.docksList();
        ReView = new RessourcesView("Ressources View", sWidth, mWidth, tWidth, tHeight, null, SSView.getListBuildings(), SSView.getListHangars());
        missionPanel = new MissionPanel("Missions", sWidth, tHeight);
        docksPanel = new DocksPanel("Docks", sWidth, tHeight);
        echapPanel = new EchapPanel(tWidth, tHeight);
        callPanel = new callUrgencesPanel(tWidth, tHeight);

        docksPanel.setListBuilding(SSView.getListHangars());

        var = new Variables();

        mainPanel[RADAR] = radarView;
        mainPanel[STATION] = SSView;
        mainPanel[RESSOURCES] = ReView;
        curMainPanel = RADAR;

        JukeBox.play("welcome");
        JukeBox.loop("mainBG");
        //JukeBox.loop(mainPanel[curMainPanel].getSound());
    }

    @Override
    public void update() {
        for (MainPanel mP : mainPanel) {
            mP.update();
            if (mP.getGameOver()) {
                timer.stop();
                //fire.getTimer().stop();
                gsm.setState(GameStateManager.GAMEOVERSTATE);
            }
        }
        int numBuildingOnFire = 0;
        for (Building bu : SSView.getListBuildings()) {
            if (bu.getFire()) {
                numBuildingOnFire++;
                break;
            }
        }

        if (fireEvent && !fire.getLaunched()) {
            fire.launch();
        } else if(fire.getFireStarted() && fire.getFirstFire()){
            missionPanel.addMision(missionMessages[FIRE]);
            fire.initFirstFire();
        }else if (fire.getLaunched() && numBuildingOnFire == 0 && fire.getFireStarted()) {
            missionPanel.delMission(missionMessages[FIRE], 1);
            fireEvent = false;
            fire.getTimer().cancel();
            fire.initFireSystem();
        }

        if (echap) {
            if (echapPanel.getEchap()) {
                timer.stop();
                gsm.setState(GameStateManager.MENUSTATE);
            } else if (echapPanel.getStay()) {
                echap = false;
                echapPanel.reInit();
            }
            if (call) {
                if (!callPanel.getUrgenceToSend().getBusy()) {
                    callPanel.reInit();
                }
                call = !call;
                echap = !echap;
            }
        }

        if (call && callPanel.isGoodNum()) {
            SSView.setDetailBar(true);
            SSView.getIPanel().handleInput();
            SSView.getIPanel().setUrgence(true);
        }

        if (SSView.getIPanel().getUrgenceSent()) {
            if (callPanel.getUrgenceToSend().getBusy()) {
                callPanel.getUrgenceToSend().inService();
            } else {
                if ("18".equals(callPanel.getUrgenceToSend().getNumber())) {
                    SSView.getIPanel().buildingInNeed().setFire(false);
                    fire.fireNeutralizedBuilding(SSView.getIPanel().buildingInNeed());
                }
                SSView.getIPanel().reInit();
                callPanel.reInit();
                call = false;
            }
        }

        if (SSView.getIPanel().getPurge()) {
            ReView.setPurge(SSView.getIPanel().getPurge());
            if (SSView.getIPanel().buildingInNeed().getFire()) {
                fire.fireNeutralizedBuilding(SSView.getIPanel().buildingInNeed());
            }
            SSView.getIPanel().setPurge(false);
        }

        missionPanel.update();
        docksPanel.update();

        if (SSView.getListBuildings().get(15).getMissile()) {
            ReView.setMissile(3 - (radarView.getNumMissile()));
            radarView.setNumMissile(3);
            SSView.getListBuildings().get(15).setMissile(false);
        }

        for (int i = 0; i < SSView.getListBuildings().size(); i++) {
            if (SSView.getListBuildings().get(i).happiness(ReView.getListRessources())) {
                SSView.peopleQuitBuildingInBuildingDirection();
                SSView.getListBuildings().get(i).setHappiness(false);
            }
        }

        for (int i = 0; i < radarView.getListToDock().size(); i++) {
            if (radarView.getListToDock().get(i).getRes() != null) {
                ReView.getListRessources().get(radarView.getListToDock().get(i).getRes()).setMaxCap();
            } else {
                int randHangar = var.randNum(0, 4);
                SSView.getListHangars().get(randHangar).addCapacity(radarView.getListToDock().get(i).getVolume());
                radarView.getListToDock().get(i).setDestination(SSView.getListHangars().get(randHangar));
                radarView.getListToDock().get(i).dock();
                SSView.getListHangars().get(randHangar).addShips(radarView.getListToDock().get(i));
            }
            radarView.removeFromListToDock(radarView.getListToDock().get(i));
        }

        if (ReView.getCommand() != null) {
            radarView.createVehicle(1, ReView.getCommand());
            ReView.resetCommand();
        }

        if (SSView.getShipInSpaceDirection()) {
            System.out.println("simulation : vaisseau parti");
            radarView.createVehicleFromDocks(1, null);
            SSView.setShipInSpaceDirection(false);
        }

        ReView.setShieldOn(radarView.getShieldOn());

        if (radarView.getMinusAsteroid() != 0) {
            missionPanel.delMission(missionMessages[ASTEROID], radarView.getMinusAsteroid());
            radarView.resetMinusAsteroid();
        }

        if (ReView.getListRessources().get(3).getCurrentcap() <= 0) {
            SSView.peopleDying();
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
