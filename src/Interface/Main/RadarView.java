package Interface.Main;

import Evenements.AsteroidIncoming;
import GameObjects.Actors.Ship;
import GameObjects.Zone;
import Handlers.Keys;
import Interface.MainPanel;
import Utilities.Variables;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class RadarView extends MainPanel {

    private static final String IPANELNAME = "Vehicles";

    private int eventCountAddVehicle, addVehicleEventMaxTime, waitEvent, waitEventMaxTime;

    private boolean asteroid = false;

    private final ArrayList<Ship> listVehicles;
    private final ArrayList<Ship> listAsteroids;
    private final ArrayList<Ship> listToDock;
    private final Variables var;
    private static final Color FRONT_RADAR_PAINT = Color.GREEN;
    private static final Color FRONT_PASSIVE_RADAR_PAINT = FRONT_RADAR_PAINT.darker().darker();
    private static final Color FRONT_ACTIVE_RADAR_PAINT = FRONT_RADAR_PAINT;
    private static final Color FRONT_ACTIVE_RADAR_TRANSPARENT_PAINT = new Color(FRONT_RADAR_PAINT.getRed(),
            FRONT_RADAR_PAINT.getGreen(), FRONT_RADAR_PAINT.getBlue(), 0);

    /**
     * affichage du balayage
     */
    private GradientPaint gradient;

    private Zone space;

    private Line2D.Float line = new Line2D.Float();
    private Line2D.Float lineRadar = new Line2D.Float();
    private Arc2D.Float arc = new Arc2D.Float();
    private Rectangle2D arcRect = new Rectangle2D.Float();
    private Ellipse2D.Float ellipse = new Ellipse2D.Float();
    private Rectangle station;
    /**
     * Radar position
     */
    private float radarX, radarY, radius, diameter, centerX, centerY;

    /**
     * Shield
     */
    private Area shield;
    protected boolean shieldOn = false;
    protected static final float consumeRate = 0.001f, restoreRate = 0.0001f;
    protected static final float totalShieldTime = 1f;
    protected float currentShieldTime = totalShieldTime;
    protected int shieldDiam;
    /**
     * Radar angle
     */
    private double angle = 0, da = 8 * Math.PI / 1000;

    public RadarView(String n, Zone z, int sW, int mW, int tH, int tW, String sound) {
        super(n, sW, mW, tW, tH, Ship.class, IPANELNAME, sound);
        space = z;
        listVehicles = new ArrayList<>();
        listAsteroids = new ArrayList<>();
        listToDock = new ArrayList<>();
        var = new Variables();
        init();
    }

    public void init() {
        eventCountAddVehicle = 0;
        addVehicleEventMaxTime = 50;
        waitEvent = 0;
        waitEventMaxTime = 50;

        shieldDiam = 0;

        radius = Math.min(width, mainHeight) / 2f - 5f;
        if (radius != 0) {
            diameter = 2 * radius;
            centerX = sWidth + width / 2f;
            centerY = topHeight + mainHeight / 2f;
            radarX = centerX - radius;
            radarY = centerY - radius;
            station = new Rectangle((int) (centerX - tWidth / 128), (int) (centerY - tWidth / 128), (int) tWidth / 64, (int) tWidth / 64);
            // Note : arc intialization angles are in degrees.
            arc.setArc(new Rectangle2D.Double(radarX, radarY, diameter, diameter), 0f, 20f, Arc2D.PIE);
            arcRect.setRect(centerX, centerY - radius / 8f, radius, radius / 8f);

            gradient = new GradientPaint(centerX, centerY - radius / 8f, FRONT_ACTIVE_RADAR_TRANSPARENT_PAINT, centerX, centerY, FRONT_RADAR_PAINT, false);

        }
    }

    @Override
    public void update() {
        super.update();
        radarScan();
        checkVehicleOnRadar();
        if (isSliding) {
            slideRadar();
        }
        //eventAddVehicleToMap();
        checkDocking();
        //checkWaiting();
        checkAsteroidDestroyed();

        if (shieldOn) {

            currentShieldTime -= consumeRate;
            if (currentShieldTime <= 0) {
                shieldOn = false;
            }

            if (shieldDiam < tWidth / 10) {
                shieldDiam++;
            } else {
                shieldDiam = tWidth / 10;
            }

            for (int i = 0; i < listAsteroids.size(); i++) {
                if (var.Collision(shield, listAsteroids.get(i).vehicleCollision())) {
                    listAsteroids.get(i).setDestroyed(true);
                }
            }

        } else {
            currentShieldTime += restoreRate;
            if (currentShieldTime >= totalShieldTime) {
                currentShieldTime = totalShieldTime;
            }
            
            if (shieldDiam > 0) {
                shieldDiam--;
            } else {
                shieldDiam = 0;
            }
        }

        shield = new Area(new Ellipse2D.Double((int) (centerX) - shieldDiam / 2, (int) (centerY) - shieldDiam / 2, shieldDiam, shieldDiam));

        if (asteroid) {
            createAsteroid();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // affiche les axes du radar
        g.setStroke(PASSIVE_STROKE);
        g.setColor(FRONT_PASSIVE_RADAR_PAINT);
        float dx = radius / 4f;

        //affiche les cercles du radar
        for (int i = 0; i < 5; i++) {
            ellipse.setFrame(radarX + i * dx, radarY + i * dx, diameter - 2 * i * dx, diameter - 2 * i * dx);
            g.draw(ellipse);
        }

        g.setColor(new Color(0f, 0f, 1f, 0.1f));

        if (shieldDiam != 0) {
            g.fill(shield);
        }

        g.setColor(FRONT_PASSIVE_RADAR_PAINT);

        line.setLine(radarX, centerY, radarX + diameter, centerY);
        g.draw(line);
        line.setLine(centerX, radarY, centerX, radarY + diameter);
        g.draw(line);

        for (Ship v : listVehicles) {
            v.draw(g);
        }

        g.setColor(Color.GREEN);
        //g.fillRect((int) centerX - 15, (int) centerY - 15, 30, 30);
        g.fillRect(station.x, station.y, station.width, station.height);

        g.setColor(FRONT_PASSIVE_RADAR_PAINT);
        // backup de la rotation afin que l'écran ne bouge pas
        AffineTransform backTransform = g.getTransform();

        //affiche la ligne du radar en fonctionde son angle
        g.setPaint(FRONT_ACTIVE_RADAR_PAINT);
        lineRadar.setLine(centerX, centerY, centerX + radius * (Math.cos(angle)), centerY + radius * (Math.sin(angle)));// permet la rotation
        g.draw(lineRadar);

        //affiche le balayage
        g.rotate(angle, centerX, centerY);
        g.setPaint(gradient);
        g.fill(arc);
        g.setStroke(ACTIVE_STROKE);
        g.setTransform(backTransform);

    }

    public void setAsteroid(boolean b) {
        this.asteroid = b;
    }

    public Area stationCollision() {
        return new Area(new Rectangle((int) (centerX - tWidth / 128), (int) (centerY - tWidth / 128), (int) tWidth / 64, (int) tWidth / 64));
    }

    public void slideRadar() {
        radius = Math.min(width, mainHeight) / 2f - 5f;
        diameter = 2 * radius;
        centerX = sWidth + width / 2f;
        centerY = topHeight + mainHeight / 2f;
        radarX = centerX - radius;
        radarY = centerY - radius;

        station = new Rectangle((int) (centerX - tWidth / 128), (int) (centerY - tWidth / 128), (int) tWidth / 64, (int) tWidth / 64);

        // Note arc intialization angles are in degrees.
        arc.setArc(new Rectangle2D.Double(radarX, radarY, diameter, diameter), 0f, 20f, Arc2D.PIE);
        arcRect.setRect(centerX, centerY - radius / 8f, radius, radius / 8f);

        shield = new Area(new Ellipse2D.Double((int) (centerX) - shieldDiam / 2, (int) (centerY) - shieldDiam / 2, shieldDiam, shieldDiam));

        gradient = new GradientPaint(centerX, centerY - radius / 8f, FRONT_ACTIVE_RADAR_TRANSPARENT_PAINT, centerX, centerY, FRONT_RADAR_PAINT, false);

        for (int i = 0; i < listVehicles.size(); i++) {
            Ship v = listVehicles.get(i);
            float vX = v.getObjectX();
            v.setObjectX(width - vX);
        }

    }

    public void addVehicle(Ship v) {
        listVehicles.add(v);
    }

    public void createVehicle(int n) {
        for (int i = 0; i < n; i++) {
            int sp = var.randNum(1, 3);
            float speed = sp / 5000f;
            int randSource = var.randNum(0, 3);
            int posX = sWidth;
            int posY = topHeight;
            switch (randSource) {
                case 0:
                    posX = var.randNum(sWidth, tWidth - rightBarWidth);
                    break;
                case 1:
                    posY = var.randNum(topHeight, height);
                    posX = tWidth - rightBarWidth;
                    break;
                case 2:
                    posX = var.randNum(sWidth, tWidth - rightBarWidth);
                    posY = height;
                    break;
                case 3:
                    posY = var.randNum(topHeight, height);
                    break;
            }

            int side = (int) (tWidth / 64);

            float dirX = centerX - 15 - posX;
            float dirY = centerY - 15 - posY;

            listVehicles.add(new Ship(space, speed, posX, posY, dirX, dirY, side, new Color(0, 1.0f, 0, 0.0f)));
        }
    }

    public void createAsteroid() {
        AsteroidIncoming Asteroid = new AsteroidIncoming();
        int sp = var.randNum(1, 2);
        float speed = sp / 1000f;
        int randSource = var.randNum(0, 3);
        int posX = sWidth;
        int posY = topHeight;

        switch (randSource) {
            case 0:
                posX = var.randNum(sWidth, tWidth - rightBarWidth);
                break;
            case 1:
                posY = var.randNum(topHeight, height);
                posX = tWidth - rightBarWidth;
                break;
            case 2:
                posX = var.randNum(sWidth, tWidth - rightBarWidth);
                posY = height;
                break;
            case 3:
                posY = var.randNum(topHeight, height);
                break;
        }

        float dirX = var.randNum(sWidth, tWidth - rightBarWidth) - (int) Asteroid.getRayonAsteroid() / 2 - posX;
        float dirY = var.randNum(topHeight, height) - (int) Asteroid.getRayonAsteroid() / 2 - posY;

        Ship AsteroidVehicle = new Ship(space, speed, posX, posY, dirX, dirY, (int) Asteroid.getRayonAsteroid(), Asteroid.getColorAsteroid());
        AsteroidVehicle.setSize("ENORME");
        AsteroidVehicle.addpassagers(0);
        AsteroidVehicle.setId(" ???? - " + AsteroidVehicle.getSize());
        addVehicle(AsteroidVehicle);
        listAsteroids.add(AsteroidVehicle);
        asteroid = false;
    }

    public void checkVehicleOnRadar() {
        for (Ship v : listVehicles) {
            double distance = var.calculateDistanceFromPoint(centerX, centerY, v.getObjectX(), v.getObjectY());
            if (distance <= radius) {
                v.setIsOnRadar(true);
                iPanel.addVehicle(v);
            } else {
                v.setIsOnRadar(false);
                iPanel.removeVehicleFromList(v);
            }
        }
    }

    public void checkAsteroidDestroyed() {
        for (int i = 0; i < listAsteroids.size(); i++) {
            Ship v = listAsteroids.get(i);
            if (v.getDestroyed()) {
                v.setIsOnRadar(false);
                iPanel.removeVehicleFromList(v);
                listVehicles.remove(v);
            }
        }
    }

    //check if a vehicle has been granted the right to dock, if so allow him to dock and erase him from the vehicles on the radar. If the vehicle
    // hasn't been granted any choices, and it is at a certain distance from the Space Station, we can't choose anymore, the vehicle will not be able
    // to dock on the Space Station
    public void checkDocking() {
        for (int i = 0; i < listVehicles.size(); i++) {
            if (!listVehicles.get(i).getDockingAccepted() && var.calculateDistanceFromPoint(centerX, centerY, listVehicles.get(i).getObjectX(), listVehicles.get(i).getObjectY()) <= tWidth / 64) {
                listVehicles.get(i).setHasChosen(true);
            }
            if (listVehicles.get(i).getDockingAccepted() && var.Collision(listVehicles.get(i).vehicleCollision(), stationCollision())) {
                listVehicles.get(i).setIsOnRadar(false);
                listVehicles.get(i).setIsMoving(false);
                listVehicles.get(i).setDocked(true);
                iPanel.removeVehicleFromList(listVehicles.get(i));
                listToDock.add(listVehicles.get(i));
                listVehicles.remove(listVehicles.get(i));
            }
            if (listVehicles.get(i).getSize() == "ENORME" && var.Collision(listVehicles.get(i).vehicleCollision(), stationCollision())) {
                System.out.println("Simulation terminée, un asteroide s'est écrasé sur la station Deep Space 42");
                gameOver = true;
            }
        }
    }
    
//
//    public void checkWaiting() {
//        for (Ship v : listVehicles) {
//            if (!v.isMoving()) {
//                int time = 50;
//                waitEvent(v, time);
//            }
//        }
//    }

//    public void eventAddVehicleToMap() {
//        eventCountAddVehicle++;
//        if (eventCountAddVehicle == addVehicleEventMaxTime) {
//            createVehicle(var.randNum(0, 2));
//            addVehicleEventMaxTime = var.randNum(300, 1000);
//            eventCountAddVehicle = 0;
//        }
//    }
//
//    public void waitEvent(Ship v, int t) {
//        waitEvent++;
//        if (waitEvent == t) {
//            v.setIsMoving(true);
//        }
//    }

    public void radarScan() {
        for (Ship v : listVehicles) {
            v.radarBeamCollisionCheck(lineRadar);
            v.move();
        }

        angle += da;
        if (angle > 360) {
            angle = 0;
        }
    }

    public ArrayList<Ship> getListToDock() {
        return listToDock;
    }

    public void removeFromListToDock(Ship v) {
        for (int i = 0; i < listToDock.size(); i++) {
            if (listToDock.get(i) == v) {
                v.setVisible(false);
                listToDock.remove(v);
            }
        }
    }

    @Override
    public void drawName(Graphics2D g) {
        super.drawName(g);
        int fontSize = (tWidth - sWidth) / 100;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        int sizeShieldName = "SHIELD".length() * fontSize;
        g.drawString("SHIELD", sWidth + 7 * (width + rightBarWidth) / 8 - sizeShieldName / 4, topHeight / 4);

        g.drawRect(sWidth + 7 * (width + rightBarWidth) / 8 + sizeShieldName / 2, topHeight / 8, (int)(totalShieldTime * 100), topHeight / 8);
        g.setColor(Color.blue);
        g.fillRect(sWidth + 7 * (width + rightBarWidth) / 8 + sizeShieldName / 2, topHeight / 8, (int)(currentShieldTime * 100), topHeight / 8);
    }

    @Override
    public void handleInput() {
        super.handleInput();

        if (Keys.isPressed(Keys.S)) {
            System.out.println("shield");
            if (!shieldOn) {
                shieldOn = true;
            } else {
                shieldOn = false;
            }

        }
    }
}
