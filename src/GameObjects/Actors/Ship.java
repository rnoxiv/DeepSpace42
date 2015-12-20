package GameObjects.Actors;

import GameObjects.Actor;
import GameObjects.Zone;
import GameObjects.Zones.Building;
import GameObjects.Zones.Buildings.Dock;
import Utilities.Functions;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

//CLASSE PERMETTANT LA CREATION VAISSEAU 
public class Ship extends Actor {

    //LA COULEUR DU VAISSEAU
    private final float GREEN_FIX = 1.0f;
    private Color clrVehicle;

    //RANDOM
    private Functions var;

    //RECTANGLE DE COLLISION POUR DETECTION 
    private final Rectangle rdrDetectRect;
    private final int wShip;
    
    //BOOLEANS GERANT LE VAISSEAU
    private boolean visible = false, isOnRadar = false, showInfo = false, dockingAccepted = false, hasChosen = false, moving = true, destroyed = false;
    //VERIFIE SI LE VAISSEAU SE DIRIGE VERS LA STATION SPATIALE
    private final boolean towardSS;

    //GERE LES MOUVEMENTS DU VAISSEAU
    private final float directionX, directionY, speed;
    private float vehicleX, vehicleY, vOpacity = 0;

    //GERE LA MARCHANDISE DU VAISSEAU
    private Integer res = null;

    // LES PASSAGERS DU VAISSEAU
    private ArrayList<Person> passagers;
    // LA TAILLE DU VAISSEAU ( GENRE XL L etc...)
    private String size;
    // LE HANGAR OU SE DIRIGE LE VAISSEAU
    private Dock destination;
    // L'ID QUI S'AFFICHERA DU VAISSEAUs
    private String id;
    // LA TAILE NUMERIQUE DU VAISSEAU
    private int volume;

    public Ship(Zone zone, float sp, float objX, float objY, float dirX, float dirY, int side, Color _clrVehicle, Integer _res, boolean toward) {
        super(zone);

        var = new Functions();
        this.clrVehicle = _clrVehicle;
        Random generator = new Random();
        this.res = _res;

        // TAILLE DU VAISSEAU ALEATOIRE
        int iSize = generator.nextInt(12) + 1;

        if (iSize <= 4) {
            this.size = "S";
            this.addpassagers(2);
            this.volume = 2;

        } else if (iSize <= 7) {
            this.size = "M";
            this.addpassagers(5);
            this.volume = 6;

        } else if (iSize <= 9) {
            this.size = "L";
            this.addpassagers(14);
            this.volume = 18;

        } else if (iSize <= 11) {
            this.size = "XL";
            this.addpassagers(45);
            this.volume = 42;

        } else {
            this.size = "C";
            this.addpassagers(4);
            this.volume = 50;

        }
        // GENERATION DE L'ID DU VAISSEAU
        this.id = var.randomName() + " - " + this.size;
        //this.id = RandomStringUtils.randomAlphabetic(3).toUpperCase() + "-" + RandomStringUtils.randomNumeric(3) + "-" + this.size;

        this.var = new Functions();
        this.vehicleX = objX;
        this.vehicleY = objY;
        this.speed = sp;
        this.directionX = dirX;
        this.directionY = dirY;
        this.wShip = side;

        this.towardSS = toward;

        this.rdrDetectRect = new Rectangle(wShip, wShip);

    }

    //DETECTION SUR LE RADAR 
    public void radarBeamCollisionCheck(Shape radarLine) {
        if (radarLine == null) {
            return;
        }

        if (radarLine.intersects(rdrDetectRect) || visible) {
            vOpacity = GREEN_FIX;
            clrVehicle = new Color(clrVehicle.getRed() / 255, clrVehicle.getGreen() / 255, clrVehicle.getBlue() / 255, vOpacity);
        } else {
            vOpacity -= 0.01;
            if (vOpacity < 0) {
                vOpacity = 0;
            }
            clrVehicle = new Color(clrVehicle.getRed() / 255, clrVehicle.getGreen() / 255, clrVehicle.getBlue() / 255, vOpacity);
        }
    }

    public void draw(Graphics g) {
        rdrDetectRect.setLocation((int) vehicleX, (int) vehicleY);

        g.setColor(clrVehicle);

        g.fillOval(rdrDetectRect.x, rdrDetectRect.y, rdrDetectRect.width, rdrDetectRect.height);
    }
    
    //COLLISION BOX DU VAISSEAU
    public Area vehicleCollision() {
        return new Area(new Ellipse2D.Double((double) (this.vehicleX), (double) (this.vehicleY), (double) this.wShip, (double) this.wShip));
    }
    
    //GERE LES MOUVEMENTS DU VAISSEAU SUR LE RADAR
    public void move() {
        if (moving) {
            this.setObjectX(this.getObjectX() + this.speed * directionX);
            this.setObjectY(this.getObjectY() + this.speed * directionY);
        }

    }

    public boolean getVisible() {
        return this.visible;
    }

    public boolean getIsOnRadar() {
        return this.isOnRadar;
    }

    public boolean getInfo() {
        return this.showInfo;
    }

    public float getObjectX() {
        return this.vehicleX;
    }

    public float getObjectY() {
        return this.vehicleY;
    }

    public boolean getDockingAccepted() {
        return this.dockingAccepted;
    }

    public boolean getHasChosen() {
        return this.hasChosen;
    }

    public boolean getDestroyed() {
        return this.destroyed;
    }

    public void setHasChosen(boolean b) {
        this.hasChosen = b;
    }

    public void setObjectX(float _objectX) {
        this.vehicleX = _objectX;
    }

    public void setObjectY(float _objectY) {
        this.vehicleY = _objectY;
    }

    public void setDestroyed(boolean b) {
        this.destroyed = b;
    }

    public void setIsMoving(boolean b) {
        this.moving = b;
    }

    public void setDockingAccepted(boolean b) {
        this.dockingAccepted = b;
    }

    public void setVisible(boolean b) {
        this.visible = b;
    }

    public void setIsOnRadar(boolean b) {
        this.isOnRadar = b;
    }

    public void showInfo(boolean b) {
        this.showInfo = b;
    }

    public ArrayList<Person> getPassagers() {
        return passagers;
    }

    public void setPassagers(ArrayList<Person> passagers) {
        this.passagers = passagers;
    }

    public Dock getDestination() {
        return destination;
    }

    public void setDestination(Dock destination) {
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public int getSide() {
        return wShip;
    }

    public boolean getTowardSS() {
        return this.towardSS;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void addpassagers(int num) {
        this.passagers = new ArrayList<Person>(num);
        for (int i = 0; i < num; i++) {
            this.passagers.add(new Person(this.getLocation(), this));
        }
    }

    // FAIT ATTERIR LE VaISEAU
    public boolean dock() {
        // DONNE LA PLACE DISPONIBLE DANS LE HANGAR DE DESTINATION
        int hangarUse = 0;
        for (int i = 0; i < this.getDestination().getShips().size(); i++) {
            hangarUse += this.getDestination().getShips().get(i).getVolume();
        }

        // VERIFIE SI UN HALL D'ARRIVEE EST DISPONIBLE
        Building bestHall = destination.findBestHall();
        if (((this.volume + hangarUse) <= this.destination.getMaxCapacity())
                || (bestHall != null)
                || ((bestHall.getMaxCapacity() - bestHall.getNumberOfActors()) >= this.getPassagers().size())) {
            this.setLocation(destination);
            this.destination.addCapacity(this.passagers.size());
            this.setPassagers(new ArrayList<Person>());
        }

        return false;
    }

    public Integer getRes() {
        return res;
    }

    public void setRes(Integer res) {
        this.res = res;
    }
}
