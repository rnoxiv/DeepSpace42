package Interface.Main;

import Actors.Vehicle;
import Interface.MainPanel;
import Utilities.Variables;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class RadarView extends MainPanel {

    private final ArrayList<Vehicle> listVehicles;
    private Variables var;
    
    private static final Color FRONT_RADAR_PAINT = Color.GREEN;
    private static final Color FRONT_PASSIVE_RADAR_PAINT = FRONT_RADAR_PAINT.darker().darker();
    private static final Color FRONT_ACTIVE_RADAR_PAINT = FRONT_RADAR_PAINT;
    private static final Color FRONT_ACTIVE_RADAR_TRANSPARENT_PAINT = new Color(FRONT_RADAR_PAINT.getRed(), FRONT_RADAR_PAINT.getGreen(), FRONT_RADAR_PAINT.getBlue(), 0);
    private static final Stroke PASSIVE_STROKE = new BasicStroke(0.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
    private static final Stroke ACTIVE_STROKE = new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);

    /**
     * affichage du balayage
     */
    private GradientPaint gradient;

    private Line2D.Float line = new Line2D.Float();
    private Line2D.Float lineRadar = new Line2D.Float();
    private Arc2D.Float arc = new Arc2D.Float();
    private Rectangle2D arcRect = new Rectangle2D.Float();
    private Ellipse2D.Float ellipse = new Ellipse2D.Float();

    /**
     * Radar position
     */
    private float radarX, radarY, radius, diameter, centerX, centerY;

    /**
     * Radar angle
     */
    private double angle = 0, da = 8 * Math.PI / 1000;

    public RadarView(String n, int sW, int mW, int tH, int tW) {
        super(n, sW, mW, tW, tH);
        listVehicles = new ArrayList<>();
        var = new Variables();
        addObjects();
        init();
    }

    public void init() {

        radius = Math.min(width, mainHeight) / 2f-5f;
        if (radius != 0) {
            diameter = 2 * radius;
            centerX = sWidth + width / 2f;
            centerY = topHeight + mainHeight / 2f;
            radarX = centerX - radius;
            radarY = centerY - radius;
            // Note arc intialization angles are in degrees.
            arc.setArc(new Rectangle2D.Double(radarX, radarY, diameter, diameter), 0f, 20f, Arc2D.PIE);
            arcRect.setRect(centerX, centerY - radius / 8f, radius, radius / 8f);

            gradient = new GradientPaint(centerX, centerY - radius / 8f, FRONT_ACTIVE_RADAR_TRANSPARENT_PAINT, centerX, centerY, FRONT_RADAR_PAINT, false);

        }
    }

    public void addObjects() {
        Vehicle v1 = new Vehicle(var.randomName(),200,20,20,sWidth + 900, 300, -.1f, 0.1f);
        this.addVehicle(v1);
        Vehicle v2 = new Vehicle(var.randomName(),200,20,20,sWidth + 450, 415, 0.1f, -.1f);
        this.addVehicle(v2);
        Vehicle v3 = new Vehicle(var.randomName(),200,20,20,sWidth + 450, 500, 0.0f, 0.0f);
        this.addVehicle(v3);
    }
    
    public void addVehicle(Vehicle v){listVehicles.add(v);}

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

        for (Vehicle v : listVehicles) {
            v.draw(g);
        }

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

    @Override
    public void update() {
        super.update();
        radarScan();
        checkVehicleOnRadar();
        if (!finishSliding) {
            slideRadar();
        }
    }

    public void slideRadar() {
        radius = Math.min(width, mainHeight) / 2f-5f;
        diameter = 2 * radius;
        centerX = sWidth + width / 2f;
        centerY = topHeight + mainHeight / 2f;
        radarX = centerX - radius;
        radarY = centerY - radius;
        // Note arc intialization angles are in degrees.
        arc.setArc(new Rectangle2D.Double(radarX, radarY, diameter, diameter), 0f, 20f, Arc2D.PIE);
        arcRect.setRect(centerX, centerY - radius / 8f, radius, radius / 8f);

        gradient = new GradientPaint(centerX, centerY - radius / 8f, FRONT_ACTIVE_RADAR_TRANSPARENT_PAINT, centerX, centerY, FRONT_RADAR_PAINT, false);
    }
    
    public void checkVehicleOnRadar(){
        for(Vehicle v : listVehicles){
            double distance = var.calculateDistanceFromRadar(centerX, centerY,v.getObjectX(),v.getObjectY());
            if(distance <= radius) {
                v.setIsOnRadar(true);
                iPanel.addToList(v);
            }else {
                v.setIsOnRadar(false);
                iPanel.removeFromList(v);
            }
        } 
    }
    
    public void radarScan() {
        for (Vehicle v : listVehicles) {
            v.radarBeamCollisionCheck(lineRadar);
            v.move();
        }
        
        angle += da;
        if (angle > 360) {
            angle = 0;
        }
    }
}
