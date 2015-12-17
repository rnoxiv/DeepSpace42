package GameState;

import Evenements.AsteroidIncoming;
import Evenements.FightEvent;
import Evenements.FireEvent;
import GameObjects.Zone;
import GameObjects.Zones.Building;
import Handlers.Arduino;
import static Handlers.Arduino.turnOffLed;
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
import Utilities.Functions;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Simulation extends GameState {

    private static final String[] missionMessages = {"Caution : Asteroid incomming!", "Warning : Fire!", "A fight has begun !"};
    private static final int ASTEROID = 0;
    private static final int FIRE = 1;
    private static final int FIGHT = 2;

    private final Timer timerSimu = new Timer();

    private int tWidth, tHeight, mWidth, sWidth, sHeight;

    private Zone space;

    private boolean echap = false, call = false, fireEvent = false, fightEvent = false;

    private final int maxChoice = 150;

    //Gestion des MainPanels
    private int curMainPanel;
    private static final int NUMPANELS = 3;
    private static final int RADAR = 0;
    private static final int STATION = 1;
    private static final int RESSOURCES = 2;

    private FireEvent fire;
    private FightEvent fight;

    private RadarView radarView;
    private SpaceStationView SSView;
    private RessourcesView ReView;
    private MainPanel[] mainPanel;

    private MissionPanel missionPanel;
    private DocksPanel docksPanel;
    private EchapPanel echapPanel;
    private callUrgencesPanel callPanel;

    private Functions var;

    private TimerTask taskPerformerSimu;

    public Arduino obj;

    public Simulation(GameStateManager gsm) throws IOException, InterruptedException {
        super(gsm);
        init();
        obj = new Arduino();
        obj.initialize();
        fire = new FireEvent(SSView.getListBuildings());
        fight = new FightEvent(SSView.getListBuildings());

        taskPerformerSimu = new TimerTask() {
            @Override
            public void run() {
                int number = var.randNum(1, maxChoice);
                if (number >= 1 & number < 5) {
                    AsteroidIncoming asteroid = new AsteroidIncoming();
                    radarView.createAsteroid();
                    missionPanel.addMision(missionMessages[ASTEROID]);
                    JukeBox.play("asteroid");
                    try {
                        obj.turnOnLed(3);
                    } catch (IOException ex) {
                        Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (number >= 5 & number < 10 & !fireEvent) {
                    fire = new FireEvent(SSView.getListBuildings());
                    fireEvent = true;
                }
                if (number >= 10 & number < 60 & !fightEvent) {
                    fight = new FightEvent(SSView.getListBuildings());
                    fightEvent = true;
                }
                if (number >= 15 & number < 60) {
                    int numShips = var.randNum(0, 2);
                    radarView.createVehicle(numShips, null);
                }
                SSView.peopleTraffic();
            }
        };

        timerSimu.scheduleAtFixedRate(taskPerformerSimu, 0, 5000);

    }

    @Override
    public void init() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        space = new Zone("SPACE");

        JukeBox.load("/SFX/radar.mp3", "radar");
        JukeBox.load("/SFX/mainBG.mp3", "mainBG");
        JukeBox.load("/SFX/AsteroidIncomming.mp3", "asteroid");
        JukeBox.load("/SFX/FireDetected.mp3", "fire");
        JukeBox.load("/SFX/FightDetected.mp3", "fight");
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

        var = new Functions();

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
                timerSimu.cancel();
                timerSimu.purge();
                for (int i = 0; i < 18; i++) {
                    try {
                        obj.turnOffLed(i);
                    } catch (IOException ex) {
                        Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    gsm.setState(GameStateManager.GAMEOVERSTATE);
                } catch (IOException ex) {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        int numBuildingOnFire = 0;
        int numBuildingFighting = 0;
        for (Building bu : SSView.getListBuildings()) {
            if (bu.getFire()) {
                numBuildingOnFire++;
            }
            if (bu.getFight()) {
                numBuildingFighting++;
            }
        }

        if (fireEvent && !fire.getLaunched()) {
            fire.launch();
        } else if (fire.getStarted() && fire.getFirstLoop()) {
            missionPanel.addMision(missionMessages[FIRE]);
            try {
                obj.turnOnLed(1);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
            fire.initFirstLoop();
        } else if (fire.getLaunched() && numBuildingOnFire == 0 && fire.getStarted()) {
            missionPanel.delMission(missionMessages[FIRE], 1);
            fire.getTimer().cancel();
            fire.initSystem();
            fireEvent = false;
            try {
                obj.turnOffLed(1);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (fightEvent && !fight.getLaunched()) {
            fight.launch();
        } else if (fight.getStarted() && fight.getFirstLoop()) {
            missionPanel.addMision(missionMessages[FIGHT]);
            try {
                obj.turnOnLed(4);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
            fight.initFirstLoop();
        } else if (fight.getLaunched() && numBuildingFighting == 0 && fight.getStarted()) {
            missionPanel.delMission(missionMessages[FIGHT], 1);
            fight.getTimer().cancel();
            fight.initSystem();
            fightEvent = false;
            try {
                obj.turnOffLed(4);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (echap) {
            if (echapPanel.getEchap()) {
                timerSimu.cancel();
                timerSimu.purge();

                for (int i = 1; i < 18; i++) {
                    try {
                        turnOffLed(i);
                    } catch (IOException ex) {
                        Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                try {
                    gsm.setState(GameStateManager.MENUSTATE);
                } catch (IOException ex) {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (echapPanel.getStay()) {
                echap = false;
                echapPanel.reInit();
            } else if (echapPanel.getLeaveSimulation()) {
                for (int i = 1; i < 18; i++) {
                    try {
                        turnOffLed(i);
                    } catch (IOException ex) {
                        Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.exit(0);
            }
            if (call) {
                if (!SSView.getIPanel().getUrgenceSent()) {
                    callPanel.reInit();
                }
                call = !call;
                echap = !echap;
            }
        }

        if (call && !echap) {
            try {
                obj.turnOnLed(17);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                obj.turnOffLed(17);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (call && callPanel.isGoodNum()) {
            SSView.setDetailBar(true);
            SSView.getIPanel().handleInput();
            SSView.getIPanel().setUrgence(true);
        }

        if (!callPanel.getUrgences().get(callPanel.FIREDepa).getBusy()) {
            try {
                obj.turnOnLed(11);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (!callPanel.getUrgences().get(callPanel.POLICE).getBusy()) {
            try {
                obj.turnOnLed(10);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (SSView.getIPanel().getUrgenceSent()) {
            if (callPanel.getUrgenceToSend().equals(callPanel.getUrgences().get(callPanel.FIREDepa))) {
                try {
                    obj.turnOnLed(8);
                } catch (IOException ex) {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (callPanel.getUrgenceToSend().equals(callPanel.getUrgences().get(callPanel.POLICE))) {
                try {
                    obj.turnOnLed(7);
                } catch (IOException ex) {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (callPanel.getUrgenceToSend().getBusy()) {
                if (callPanel.getUrgenceToSend().equals(callPanel.getUrgences().get(callPanel.FIREDepa))) {
                    try {
                        obj.turnOffLed(11);
                    } catch (IOException ex) {
                        Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (callPanel.getUrgenceToSend().equals(callPanel.getUrgences().get(callPanel.POLICE))) {
                    try {
                        obj.turnOffLed(10);
                    } catch (IOException ex) {
                        Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                callPanel.getUrgenceToSend().inService();
            } else {
                if (callPanel.getUrgenceToSend().equals(callPanel.getUrgences().get(callPanel.FIREDepa))) {
                    SSView.getIPanel().buildingInNeed().setFire(false);
                    fire.neutralizedBuilding(SSView.getIPanel().buildingInNeed());
                    try {
                        obj.turnOffLed(8);
                    } catch (IOException ex) {
                        Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (callPanel.getUrgenceToSend().equals(callPanel.getUrgences().get(callPanel.POLICE))) {
                    SSView.getIPanel().buildingInNeed().setFight(false);
                    fight.neutralizedBuilding(SSView.getIPanel().buildingInNeed());
                    try {
                        obj.turnOffLed(7);
                    } catch (IOException ex) {
                        Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                SSView.getIPanel().reInit();
                callPanel.reInit();
                call = false;
            }
        }

        if (SSView.getIPanel().getPurge()) {
            ReView.setPurge(SSView.getIPanel().getPurge());
            if (SSView.getIPanel().buildingInNeed().getFire()) {
                fire.neutralizedBuilding(SSView.getIPanel().buildingInNeed());
            }
            SSView.getIPanel().setPurge(false);
        }

        missionPanel.update();
        docksPanel.update();

        if (!radarView.getEmpty()) {
            try {
                obj.turnOnLed(9);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                obj.turnOffLed(9);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (radarView.getNumMissile() != 0) {
            try {
                obj.turnOnLed(15);
                obj.turnOnLed(6);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                obj.turnOffLed(15);
                obj.turnOffLed(6);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (SSView.getListBuildings().get(15).getMissile()) {
            ReView.setMissile(3 - (radarView.getNumMissile()));
            radarView.setNumMissile(3);
            SSView.getListBuildings().get(15).setMissile(false);
            try {
                obj.turnOffLed(14);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (radarView.getNumMissile() != 3) {
            try {
                obj.turnOnLed(14);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (int i = 0; i < SSView.getListBuildings().size(); i++) {
            if (SSView.getListBuildings().get(i).happiness(ReView.getListRessources())) {
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

        if (radarView.getShieldOn()) {
            try {
                obj.turnOnLed(5);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                obj.turnOffLed(12);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            try {
                obj.turnOnLed(12);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                obj.turnOffLed(5);
                if (obj.getMessage() == 6) {
                    obj.clearLCD();
                }
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (radarView.getListAsteroids().isEmpty()) {
            try {
                obj.turnOffLed(3);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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

            if (Keys.isPressed(Keys.Y)) {
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
