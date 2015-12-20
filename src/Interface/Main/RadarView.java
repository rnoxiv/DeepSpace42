package Interface.Main;

import Evenements.AsteroidIncoming;
import GameObjects.Actors.Ship;
import GameObjects.Zone;
import Handlers.Keys;
import Interface.MainPanel;
import Utilities.JukeBox;
import Utilities.Functions;
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

/**Panel principal lié au radar**/
public class RadarView extends MainPanel {

    private static final String IPANELNAME = "Ships";

    private boolean asteroid = false, empty = true;

    private final ArrayList<Ship> listVehicles;
    private final ArrayList<Ship> listAsteroids;
    private final ArrayList<Ship> listToDock;
    private int prevSizeListAsteroids = 0;
    private int minusAsteroid = 0, attackDelay = 0;
    private final static int attackMaxDelay = 20;

    private final Functions var;
    private static final Color FRONT_RADAR_PAINT = Color.GREEN;
    private static final Color FRONT_PASSIVE_RADAR_PAINT = FRONT_RADAR_PAINT.darker().darker();
    private static final Color FRONT_ACTIVE_RADAR_PAINT = FRONT_RADAR_PAINT;
    private static final Color FRONT_ACTIVE_RADAR_TRANSPARENT_PAINT = new Color(FRONT_RADAR_PAINT.getRed(),
            FRONT_RADAR_PAINT.getGreen(), FRONT_RADAR_PAINT.getBlue(), 0);

    /**
     * affichage du balayage
     */
    private GradientPaint gradient;

    private final Zone space;

    private final Line2D.Float line = new Line2D.Float();
    private final Line2D.Float lineRadar = new Line2D.Float();
    private final Arc2D.Float arc = new Arc2D.Float();
    private final Rectangle2D arcRect = new Rectangle2D.Float();
    private final Ellipse2D.Float ellipse = new Ellipse2D.Float();
    private Rectangle station;
    
    /**
     * Radar position
     */
    private float radarX, radarY, radius, diameter, centerX, centerY;

    /**
     * Shield
     */
    private Area shield;
    private boolean shieldOn = false;
    private static final float consumeRate = 0.001f, restoreRate = 0.0001f;
    private static final float totalShieldTime = 1f;
    private float currentShieldTime = totalShieldTime;
    private int shieldDiam;

    /**
     * Missiles
     */
    private int numMissiles = 3;
    
    /**
     * Radar angle
     */
    private double angle = 0;
    private final double da = 8 * Math.PI / 1000;

    public RadarView(String n, Zone z, int sW, int mW, int tH, int tW, String sound) {
        super(n, sW, mW, tW, tH, Ship.class, IPANELNAME, sound);
        space = z;
        listVehicles = new ArrayList<>();
        listAsteroids = new ArrayList<>();
        listToDock = new ArrayList<>();
        var = new Functions();
        init();
    }

    private void init() {

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

        prevSizeListAsteroids = listAsteroids.size();

        radarScan();
        checkVehicleOnRadar();
        if (isSliding) {
            slideRadar();
        }

        checkDocking();
        checkAsteroidDestroyed();

        shieldMechanism();

        if (!listAsteroids.isEmpty()) {
            for (int i = 0; i < listAsteroids.size(); i++) {
                for (int j = 0; j < listVehicles.size(); j++) {
                    if ((listAsteroids.get(i) != listVehicles.get(j)) && var.Collision(listAsteroids.get(i).vehicleCollision(), listVehicles.get(j).vehicleCollision())) {
                        this.iPanel.removeVehicleFromList(listVehicles.get(j));
                        listVehicles.remove(listVehicles.get(j));
                    }
                }
            }
        }

        if (asteroid) {
            createAsteroid();
        }

        if (numMissiles == 1) {
            JukeBox.play("ammo", 1);
        }

        if (this.iPanel.isAttacking()) {
            if (numMissiles == 0) {
                this.iPanel.reInit();
                attackDelay = 0;
            } else if (attackDelay == attackMaxDelay) {
                this.iPanel.getCible().setDestroyed(true);
                this.iPanel.reInit();
                attackDelay = 0;
                if (numMissiles > 0) {
                    numMissiles--;
                }
            }
            attackDelay++;
        }

        minusAsteroid = prevSizeListAsteroids - listAsteroids.size();
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

        g.setColor(new Color(15f / 255, 157f / 255, 232f / 255f, 0.3f));

        if (this.iPanel.isAttacking() && numMissiles != 0) {
            g.drawLine((int) centerX, (int) centerY, (int) this.iPanel.getCible().getObjectX() + this.iPanel.getCible().getSide() / 2, (int) this.iPanel.getCible().getObjectY() + this.iPanel.getCible().getSide() / 2);

        }

        if (shieldDiam != 0) {
            g.fill(shield);
        }

        g.setColor(FRONT_PASSIVE_RADAR_PAINT);

        line.setLine(radarX, centerY, radarX + diameter, centerY);
        g.draw(line);
        line.setLine(centerX, radarY, centerX, radarY + diameter);
        g.draw(line);

        for (int i = 0; i < listVehicles.size(); i++) {
            listVehicles.get(i).draw(g);
        }
        
        //affiche centre du radar : la station
        g.setColor(Color.GREEN);
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
    
    //gère la taille du shield et son affichage selon son activation
    public void shieldMechanism() {

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
                Ship a = listAsteroids.get(i);
                if (var.Collision(shield, a.vehicleCollision())) {
                    a.setDestroyed(true);
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
    }

    public void setAsteroid(boolean b) {
        this.asteroid = b;
    }
    
    
    //collision box de la station
    public Area stationCollision() {
        return new Area(new Rectangle((int) (centerX - tWidth / 128), (int) (centerY - tWidth / 128), (int) tWidth / 64, (int) tWidth / 64));
    }
    
    //gère le déplacement des éléments présents sur le panel si l'info panel est activé
    public void slideRadar() {
        float lastCX = centerX;
        float lastCY = centerY;
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

        float dx = centerX - lastCX;
        float dy = centerY - lastCY;

        for (int i = 0; i < listVehicles.size(); i++) {
            Ship v = listVehicles.get(i);
            float vX = v.getObjectX();
            float vY = v.getObjectY();
            v.setObjectX(vX + dx);
            v.setObjectY(vY + dy);
        }

    }

    public void addVehicle(Ship v) {
        listVehicles.add(v);
    }
    
    //crée un vaisseau aléatoire (ou pas)
    public void createVehicle(int n, Integer res) {
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

            int dir = var.randNum(1, 50);

            float dirX = centerX - 15 - posX;
            float dirY = centerY - 15 - posY;
            
            boolean towardSS = true;
            
            if (dir >= 40 && res == null) {
                dirX = var.randNum(sWidth, tWidth - rightBarWidth) - posX;
                dirY = var.randNum(topHeight, height) - posY;
                towardSS = false;
            }

            Color color = new Color(0, 1.0f, 0, 0.0f);

            if (res != null) {
                color = new Color(0, 0, 1.0f, 0.0f);
            }

            listVehicles.add(new Ship(space, speed, posX, posY, dirX, dirY, side, color, res, towardSS));
        }
    }
    
    //crée un vaisseau sortant de la station
    public void createVehicleFromDocks(int n, Integer res) {
        for (int i = 0; i < n; i++) {
            int sp = var.randNum(1, 3);
            float speed = sp / 5000f;
            int randDirX = var.randNum(-1, 1);
            int randDirY = var.randNum(-1, 1);
            int posX = (int) centerX;
            int posY = (int) centerY;
            float dirX = randDirX * var.randNum(sWidth, tWidth - rightBarWidth);
            float dirY = randDirY * var.randNum(topHeight, height);
            if (dirX == 0 | dirY == 0) {
                dirX = dirX - var.randNum(sWidth, tWidth - rightBarWidth);
                dirY = dirY + var.randNum(topHeight, height);
            }

            int side = (int) (tWidth / 64);

            Color color = new Color(0, 1.0f, 1.0f, 0.0f);

            listVehicles.add(new Ship(space, speed, posX, posY, dirX, dirY, side, color, res, false));
        }
    }
    
    //crée un Asteroid
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

        Ship AsteroidVehicle = new Ship(space, speed, posX, posY, dirX, dirY, (int) Asteroid.getRayonAsteroid(), Asteroid.getColorAsteroid(), null, false);
        AsteroidVehicle.setSize("ENORME");
        AsteroidVehicle.addpassagers(0);
        AsteroidVehicle.setId(" ???? - " + AsteroidVehicle.getSize());
        addVehicle(AsteroidVehicle);
        listAsteroids.add(AsteroidVehicle);
        asteroid = false;
    }
    
    //détermine si un vaisseau et sur le radar selon sa distance de la station
    public void checkVehicleOnRadar() {
        int numVehicleOnRadar = 0;
        for (int i = 0; i < listVehicles.size(); i++) {
            Ship v = listVehicles.get(i);
            double distance = var.calculateDistanceFromPoint(centerX, centerY, v.getObjectX(), v.getObjectY());
            if (distance <= radius) {
                v.setIsOnRadar(true);
                iPanel.addVehicle(v);
                empty = false;
                numVehicleOnRadar++;
            } else if ((v.getIsOnRadar() && distance > radius) || distance>3*radius) {
                v.setIsOnRadar(false);
                iPanel.removeVehicleFromList(v);
                if ("ENORME".equals(listVehicles.get(i).getSize())) {
                    listAsteroids.remove(v);
                }
                listVehicles.remove(v);
            }
        }
        if(numVehicleOnRadar == 0){
            empty = true;
        }
    }
    
    //vérifie si un Astéroid a été détruit
    public void checkAsteroidDestroyed() {
        for (int i = 0; i < listAsteroids.size(); i++) {
            Ship a = listAsteroids.get(i);
            if (a.getDestroyed()) {
                a.setIsOnRadar(false);
                iPanel.removeVehicleFromList(a);
                listVehicles.remove(a);
                listAsteroids.remove(a);
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
                iPanel.removeVehicleFromList(listVehicles.get(i));
                listToDock.add(listVehicles.get(i));
                listVehicles.remove(listVehicles.get(i));
            }
        }

        for (int i = 0; i < listAsteroids.size(); i++) {
            if (var.Collision(listAsteroids.get(i).vehicleCollision(), stationCollision())) {
                gameOver = true;
            }
        }
    }
    
    //gère le radar, son balayage et l'affichage des éléments touchés par le balayage
    public void radarScan() {
        for (int i=0; i<listVehicles.size();i++) {
            listVehicles.get(i).radarBeamCollisionCheck(lineRadar);
            listVehicles.get(i).move();
        }

        angle += da;
        if (angle > 360) {
            angle = 0;
        }
    }

    public ArrayList<Ship> getListToDock() {
        return listToDock;
    }
    
    public ArrayList<Ship> getListAsteroids(){
        return listAsteroids;
    }
    
    public boolean getShieldOn() {
        return this.shieldOn;
    }
    
    public boolean getEmpty(){
        return empty;
    }
    
    public int getMinusAsteroid() {
        return minusAsteroid;
    }

    public int getNumMissile() {
        return this.numMissiles;
    }

    public void setNumMissile(int _m) {
        this.numMissiles = _m;
    }

    public void resetMinusAsteroid() {
        minusAsteroid = 0;
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

        //Draw shield infos on top of the screen
        int sizeShieldName = "SHIELD".length() * fontSize;

        g.drawString("SHIELD", sWidth + 7 * (width + rightBarWidth) / 8 - sizeShieldName / 4, topHeight / 4);
        g.setColor(Color.blue);
        g.fillRect(sWidth + 7 * (width + rightBarWidth) / 8 + sizeShieldName / 2, topHeight / 8, (int) (currentShieldTime * (width + rightBarWidth) / 20), topHeight / 8);
        g.setColor(Color.GREEN);
        g.setStroke(PASSIVE_STROKE);
        g.drawRect(sWidth + 7 * (width + rightBarWidth) / 8 + sizeShieldName / 2, topHeight / 8, (int) (totalShieldTime * (width + rightBarWidth) / 20), topHeight / 8);
        //Draw Missiles infos on top of the screen
        int sizeMissileName = "LASER".length() * fontSize;
        g.drawString("LASER", sWidth + 7 * (width + rightBarWidth) / 8 - sizeMissileName / 4, 3 * topHeight / 4);
        g.setColor(Color.blue);
        g.fillRect(sWidth + 7 * (width + rightBarWidth) / 8 + sizeShieldName / 2, 5 * topHeight / 8, (int) (numMissiles * (width + rightBarWidth) / 60), topHeight / 8);
        g.setColor(Color.GREEN);
        g.drawRect(sWidth + 7 * (width + rightBarWidth) / 8 + sizeShieldName / 2, 5 * topHeight / 8, (width + rightBarWidth) / 60, topHeight / 8);
        g.drawRect(sWidth + 7 * (width + rightBarWidth) / 8 + sizeShieldName / 2, 5 * topHeight / 8, (width + rightBarWidth) / 30, topHeight / 8);
        g.drawRect(sWidth + 7 * (width + rightBarWidth) / 8 + sizeShieldName / 2, 5 * topHeight / 8, (width + rightBarWidth) / 20, topHeight / 8);
        g.setStroke(ACTIVE_STROKE);
    }

    @Override
    public void handleInput() {
        super.handleInput();

        if (Keys.isPressed(Keys.S)) {
            if (!shieldOn) {
                shieldOn = true;
                JukeBox.play("shieldOff", 1);
            } else {
                shieldOn = false;
                JukeBox.play("shieldOn", 1);
            }

        }
    }
}
