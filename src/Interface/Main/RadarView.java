package Interface.Main;

import GameObjects.Actors.Ship;
import GameObjects.Zone;
import Interface.MainPanel;
import Utilities.Variables;
import java.awt.Color;
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
import java.util.Random;

public class RadarView extends MainPanel {

    private static final String IPANELNAME = "Vehicles";

    private int eventCountAddVehicle, addVehicleEventMaxTime, waitEvent, waitEventMaxTime;
    
    
    
    private final ArrayList<Ship> listVehicles;
    private final ArrayList<Ship> listToDock;
    private Variables var;
    private Random rand;
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
     * Radar angle
     */
    private double angle = 0, da = 8 * Math.PI / 1000;

    public RadarView(String n, Zone z, int sW, int mW, int tH, int tW, String sound) {
        super(n, sW, mW, tW, tH, Ship.class, IPANELNAME, sound);
        space = z;
        listVehicles = new ArrayList<>();
        listToDock = new ArrayList<>();
        var = new Variables();
        init();
    }

    public void init() {
        eventCountAddVehicle = 0;
        addVehicleEventMaxTime = 50;
        waitEvent = 0;
        waitEventMaxTime = 50;
        
        rand = new Random();
        radius = Math.min(width, mainHeight) / 2f - 5f;
        if (radius != 0) {
            diameter = 2 * radius;
            centerX = sWidth + width / 2f;
            centerY = topHeight + mainHeight / 2f;
            radarX = centerX - radius;
            radarY = centerY - radius;
            station = new Rectangle((int)(centerX-tWidth/128),(int)(centerY-tWidth/128),(int)tWidth/64,(int)tWidth/64);
            // Note arc intialization angles are in degrees.
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
        eventAddVehicleToMap();
        checkDocking();
        checkWaiting();
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

        line.setLine(radarX, centerY, radarX + diameter, centerY);
        g.draw(line);
        line.setLine(centerX, radarY, centerX, radarY + diameter);
        g.draw(line);

        for (Ship v : listVehicles) {
            v.draw(g);
        }

        g.setColor(Color.GREEN);
        //g.fillRect((int) centerX - 15, (int) centerY - 15, 30, 30);
        g.fillRect(station.x,station.y,station.width,station.height);

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
    
    public Area stationCollision(){
            return new Area(new Rectangle((int)(centerX-tWidth/128),(int)(centerY-tWidth/128),(int)tWidth/64,(int)tWidth/64));
    }
    
    public void slideRadar() {
        radius = Math.min(width, mainHeight) / 2f - 5f;
        diameter = 2 * radius;
        centerX = sWidth + width / 2f;
        centerY = topHeight + mainHeight / 2f;
        radarX = centerX - radius;
        radarY = centerY - radius;
        
        station = new Rectangle((int)(centerX-tWidth/128),(int)(centerY-tWidth/128),(int)tWidth/64,(int)tWidth/64);
        
        // Note arc intialization angles are in degrees.
        arc.setArc(new Rectangle2D.Double(radarX, radarY, diameter, diameter), 0f, 20f, Arc2D.PIE);
        arcRect.setRect(centerX, centerY - radius / 8f, radius, radius / 8f);

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
            String name = var.randomName();
            int psgrs = var.randNum(2, 25);
            int size = var.randNum(1, 20);
            int sp = var.randNum(1,3);
            float speed = sp / 5000f;
            int randSource = var.randNum(0, 3);
            int posX = sWidth;
            int posY = topHeight;
            switch (randSource) {
                case 0:
                    posX = var.randNum(sWidth, tWidth-rightBarWidth);
                    break;
                case 1:
                    posY = var.randNum(topHeight, height);
                    posX = width;
                    break;
                case 2:
                    posX = var.randNum(sWidth, tWidth-rightBarWidth);
                    posY = height;
                    break;
                case 3:
                    posY = var.randNum(topHeight, height);
                    break;
            }
            
            int side = (int)(tWidth / 64);
            
            float dirX = centerX - 15 - posX;
            float dirY = centerY - 15 - posY;

            listVehicles.add(new Ship(space, speed, posX, posY, dirX, dirY, side));
        }
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
    
    //check if a vehicle has been granted the right to dock, if so allow him to dock and erase him from the vehicles on the radar. If the vehicle
    // hasn't been granted any choices, and it is at a certain distance from the Space Station, we can't choose anymore, the vehicle will not be able
    // to dock on the Space Station
    public void checkDocking(){
        for (int i=0; i<listVehicles.size();i++) {
            if(!listVehicles.get(i).getDockingAccepted() && var.calculateDistanceFromPoint(centerX, centerY,listVehicles.get(i).getObjectX(), listVehicles.get(i).getObjectY()) <= tWidth/64){
                listVehicles.get(i).setHasChosen(true);
            }
            if(listVehicles.get(i).getDockingAccepted() && var.Collision(listVehicles.get(i).vehicleCollision(), stationCollision())){
                listVehicles.get(i).setIsOnRadar(false);
                listVehicles.get(i).setIsMoving(false);
                listVehicles.get(i).setDocked(true);
                iPanel.removeVehicleFromList(listVehicles.get(i));
                listToDock.add(listVehicles.get(i));
                listVehicles.remove(listVehicles.get(i));
            }
        }
    }
    
    public void checkWaiting(){
        for(Ship v : listVehicles){
            if(!v.isMoving()){
                int time = 50;
                waitEvent(v,time);
            }
        }
    }

    public void eventAddVehicleToMap() {
        eventCountAddVehicle++;
        if (eventCountAddVehicle == addVehicleEventMaxTime) {
            createVehicle(var.randNum(0, 2));
            addVehicleEventMaxTime = var.randNum(300, 1000);
            eventCountAddVehicle = 0;
        }
    }
    
    public void waitEvent(Ship v,int t){
        waitEvent++;
        if(waitEvent == t){
            v.setIsMoving(true);
        }
    }
    
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
    
    public ArrayList<Ship> getListToDock(){
        return listToDock;
    }
    
    public void removeFromListToDock(Ship v){
        for (int i = 0; i < listToDock.size(); i++) {
            if (listToDock.get(i) == v) {
                v.setVisible(false);
                listToDock.remove(v);
            }
    }
    }
}
