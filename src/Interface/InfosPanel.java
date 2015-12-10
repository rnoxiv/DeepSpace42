package Interface;

import GameObjects.Actors.Ship;
import GameObjects.Zones.Building;
import GameObjects.Ressource;
import Handlers.Keys;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class InfosPanel {

    private final String name;
    private final int height, width, tWidth, widthBox;
    private int curSelect, classList;

    private static final int boxConst = 10;

    private static final int VEHICLE = 0;
    private static final int RESSOURCE = 1;
    private static final int BUILDING = 2;

    private ArrayList<Ship> vehiclesList;
    private ArrayList<Ressource> ressourcesList;
    private ArrayList<Building> buildingsList;

    private Ship cible;

    private boolean isShown = false, info = false, urgence = false, sentUrgence = false, isAttacking = false;

    public InfosPanel(String n, int w, int tW, int h, int tH, Class c) {
        this.name = "Infos (" + n + ")";
        width = w;
        height = h;
        tWidth = tW;
        widthBox = width - 20;
        setClassList(c);
        curSelect = 0;
        vehiclesList = new ArrayList<>();
        ressourcesList = new ArrayList<>();
        buildingsList = new ArrayList<>();
    }

    private void setClassList(Class c) {
        if (c == Ship.class) {
            this.classList = VEHICLE;
        } else if (c == Building.class) {
            this.classList = BUILDING;
        } else if (c == Ressource.class) {
            this.classList = RESSOURCE;
        }
    }

    public void draw(Graphics2D g) {
        drawString(g);
    }

    public void update() {
        if (classList == BUILDING) {
        } else if (classList == VEHICLE) {

        } else if (classList == RESSOURCE) {

        }
    }

    public void setRessourcesList(ArrayList l) {
        this.ressourcesList = l;
    }

    public void setBuildingsList(ArrayList l) {
        this.buildingsList = l;
    }

    public void addVehicle(Ship v) {
        if (!vehiclesList.contains(v)) {
            this.vehiclesList.add(v);
        }

    }

    public void removeVehicleFromList(Ship v) {
        for (int i = 0; i < vehiclesList.size(); i++) {
            if (vehiclesList.get(i) == v) {
                v.setVisible(false);
                vehiclesList.remove(v);
                if (i == 0) {
                    curSelect = 0;
                } else {
                    curSelect = i - 1;
                }
            }
        }
    }

    public void setShown(boolean b) {
        this.isShown = b;
    }

    public boolean isShown() {
        return this.isShown;
    }

    public void drawString(Graphics2D g) {
        int fontSize = width / 10;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.setColor(Color.GREEN);
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        int textWidth = (int) (g.getFont().getStringBounds(name, frc).getWidth());
        g.drawString(name, tWidth - width / 2 - textWidth / 2, height / 10);
        g.drawLine(tWidth - width, height / 9, tWidth + width, height / 9);

        switch (this.classList) {
            case VEHICLE:
                drawVehicles(g);
                break;
            case BUILDING:
                drawBuildings(g);
                break;
            case RESSOURCE:
                drawRessources(g);
                break;
        }

    }

    public void drawRessources(Graphics2D g) {

        int fontSize = width / 20;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        int boxInfoSize = 0;
        for (int i = 0; i < ressourcesList.size(); i++) {
            if (i == curSelect) {
                g.setColor(new Color(15, 185, 120));
            } else {
                g.setColor(Color.GREEN);
            }
            String na = ressourcesList.get(i).getName();
            AffineTransform affinetransform = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
            int textHeightT = (int) (g.getFont().getStringBounds(na, frc).getHeight());
            int textWidthT = (int) (g.getFont().getStringBounds(na, frc).getWidth());
            g.drawString(na, tWidth - width / 2 - textWidthT / 2, height / 10 + (i + 3) * 2 * textHeightT + boxInfoSize);
            if (ressourcesList.get(i).getInfo()) {
                String maxCap = "Maximum Capacity : " + ressourcesList.get(i).getMaxcap();
                String curCap = "Current Capacity : " + (int) ressourcesList.get(i).getCurrentcap();
                affinetransform = new AffineTransform();
                frc = new FontRenderContext(affinetransform, true, true);

                int textHeightP = (int) (g.getFont().getStringBounds(maxCap, frc).getHeight());
                int textHeightS = (int) (g.getFont().getStringBounds(curCap, frc).getHeight());
                int textWidthP = (int) (g.getFont().getStringBounds(maxCap, frc).getWidth());
                int textWidthS = (int) (g.getFont().getStringBounds(curCap, frc).getWidth());
                int textHeightA = 0;
                int textWidthA = 0;

                int textHeight = textHeightP + textHeightS + textHeightA;
                int textWidth = Math.max(textWidthP, textWidthS + textWidthA);

                boxInfoSize = 2 * textHeight;
                g.setStroke(new BasicStroke(1));
                g.drawRect(tWidth - width + boxConst, height / 10 + (i + 3) * 2 * textHeightT + 5, widthBox, boxInfoSize);
                g.drawString(maxCap, tWidth - width / 2 - textWidth / 2, height / 10 + (i + 3) * 2 * textHeightT + boxInfoSize / 2);
                g.drawString(curCap, tWidth - width / 2 - textWidth / 2, height / 10 + (i + 3) * 2 * textHeightT + 5 + boxInfoSize / 2 + textHeightT / 2);

            }
        }
    }

    public void drawVehicles(Graphics2D g) {
        int fontSize = width / 20;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        int boxInfoSize = 0;
        for (int i = 0; i < vehiclesList.size(); i++) {
            if (i == curSelect) {
                g.setColor(new Color(15, 185, 120));
            } else {
                g.setColor(Color.GREEN);
            }

            String na = vehiclesList.get(i).getId();
            AffineTransform affinetransform = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
            int textHeightT = (int) (g.getFont().getStringBounds(na, frc).getHeight());
            int textWidthT = (int) (g.getFont().getStringBounds(na, frc).getWidth());
            g.drawString(na, tWidth - width / 2 - textWidthT / 2, height / 10 + (i + 3) * 2 * textHeightT + boxInfoSize);
            if (vehiclesList.get(i).getInfo() && !"ENORME".equals(vehiclesList.get(i).getSize())) {
                String passengers = "passengers : " + vehiclesList.get(i).getPassagers().size();
                String size = "size : " + vehiclesList.get(i).getSize();
                String acceptOrReject = "A - Accept / R - Reject / W - Wait";
                affinetransform = new AffineTransform();
                frc = new FontRenderContext(affinetransform, true, true);

                int textHeightP = (int) (g.getFont().getStringBounds(passengers, frc).getHeight());
                int textHeightS = (int) (g.getFont().getStringBounds(size, frc).getHeight());
                int textWidthP = (int) (g.getFont().getStringBounds(passengers, frc).getWidth());
                int textWidthS = (int) (g.getFont().getStringBounds(size, frc).getWidth());
                int textHeightA = 0;
                int textWidthA = 0;

                if (!vehiclesList.get(i).getHasChosen()) {
                    textHeightA = (int) (g.getFont().getStringBounds(acceptOrReject, frc).getHeight());
                    textWidthA = (int) (g.getFont().getStringBounds(acceptOrReject, frc).getWidth());
                }

                int textHeight = textHeightP + textHeightS + textHeightA;
                int textWidth = Math.max(textWidthP, textWidthS + textWidthA);

                boxInfoSize = 2 * textHeight;
                g.setStroke(new BasicStroke(1));
                g.drawRect(tWidth - width + boxConst, height / 10 + (i + 3) * 2 * textHeightT + 5, widthBox, boxInfoSize);
                g.drawString(passengers, tWidth - width / 2 - textWidth / 2, height / 10 + (i + 3) * 2 * textHeightT + boxInfoSize / 2);
                g.drawString(size, tWidth - width / 2 - textWidth / 2, height / 10 + (i + 3) * 2 * textHeightT + 5 + boxInfoSize / 2 + textHeightT / 2);
                if (!vehiclesList.get(i).getHasChosen()) {
                    g.drawString(acceptOrReject, tWidth - width / 2 - textWidth / 2, height / 10 + (i + 3) * 2 * textHeightT + 20 + boxInfoSize / 2 + textHeightT / 2 + textHeightA / 2);
                }

            }
            if (vehiclesList.get(i).getInfo() && "ENORME".equals(vehiclesList.get(i).getSize())) {
                String rayonAsteroid = "rayon : " + vehiclesList.get(i).getSide();
                String size = "size : " + vehiclesList.get(i).getSize();
                String eliminateOrShield = "E - Eliminate";
                affinetransform = new AffineTransform();
                frc = new FontRenderContext(affinetransform, true, true);

                int textHeightP = (int) (g.getFont().getStringBounds(rayonAsteroid, frc).getHeight());
                int textHeightS = (int) (g.getFont().getStringBounds(size, frc).getHeight());
                int textWidthP = (int) (g.getFont().getStringBounds(rayonAsteroid, frc).getWidth());
                int textWidthS = (int) (g.getFont().getStringBounds(size, frc).getWidth());
                int textHeightA = 0;
                int textWidthA = 0;

                if (!vehiclesList.get(i).getHasChosen()) {
                    textHeightA = (int) (g.getFont().getStringBounds(eliminateOrShield, frc).getHeight());
                    textWidthA = (int) (g.getFont().getStringBounds(eliminateOrShield, frc).getWidth());
                }

                int textHeight = textHeightP + textHeightS + textHeightA;
                int textWidth = Math.max(textWidthP, textWidthS + textWidthA);

                boxInfoSize = 2 * textHeight;
                g.setStroke(new BasicStroke(1));
                g.drawRect(tWidth - width + boxConst, height / 10 + (i + 3) * 2 * textHeightT + 5, widthBox, boxInfoSize);
                g.drawString(rayonAsteroid, tWidth - width / 2 - textWidth / 2, height / 10 + (i + 3) * 2 * textHeightT + boxInfoSize / 2);
                g.drawString(size, tWidth - width / 2 - textWidth / 2, height / 10 + (i + 3) * 2 * textHeightT + 5 + boxInfoSize / 2 + textHeightT / 2);
                if (!vehiclesList.get(i).getHasChosen()) {
                    g.drawString(eliminateOrShield, tWidth - width / 2 - textWidth / 2, height / 10 + (i + 3) * 2 * textHeightT + 20 + boxInfoSize / 2 + textHeightT / 2 + textHeightA / 2);
                }

            }
        }
    }

    public void drawBuildings(Graphics2D g) {
        int fontSize = width / 21;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        int boxInfoSize = 0;
        for (int i = 0; i < buildingsList.size(); i++) {
            if (i == curSelect) {
                g.setColor(new Color(15, 185, 120));
            } else if (buildingsList.get(i).getFire()) {
                g.setColor(Color.red);
            } else {
                g.setColor(Color.GREEN);
            }

            String na = buildingsList.get(i).getName();
            AffineTransform affinetransform = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
            int textHeightT = (int) (g.getFont().getStringBounds(na, frc).getHeight());
            int textWidthT = (int) (g.getFont().getStringBounds(na, frc).getWidth());
            g.drawString(na, tWidth - width / 2 - textWidthT / 2, height / 10 + (i + 1) * 2 * textHeightT + boxInfoSize); // 1 == > 3
            if (buildingsList.get(i).getInfo() && !urgence) {
                String capacity = "capacité : " + buildingsList.get(i).getCurrentCapacity() + " / " + buildingsList.get(i).getMaxCapacity();
                String happiness = "happiness : " + buildingsList.get(i).getHappiness();
                affinetransform = new AffineTransform();
                frc = new FontRenderContext(affinetransform, true, true);
                int textHeightP = (int) (g.getFont().getStringBounds(capacity, frc).getHeight());
                int textHeightS = (int) (g.getFont().getStringBounds(happiness, frc).getHeight());
                int textWidthP = (int) (g.getFont().getStringBounds(capacity, frc).getWidth());
                int textWidthS = (int) (g.getFont().getStringBounds(happiness, frc).getWidth());

                int textHeight = textHeightP + textHeightS;
                int textWidth = Math.max(textWidthP, textWidthS);
                boxInfoSize = 2 * textHeight;
                g.setStroke(new BasicStroke(1));
                g.drawRect(tWidth - width + boxConst, height / 10 + (i + 1) * 2 * textHeightT + 5, widthBox, boxInfoSize);// (i+3)
                g.drawString(capacity, tWidth - width / 2 - textWidth / 2, height / 10 + (i + 1) * 2 * textHeightT + boxInfoSize / 2);
                g.drawString(happiness, tWidth - width / 2 - textWidth / 2, height / 10 + (i + 1) * 2 * textHeightT + 5 + boxInfoSize / 2 + textHeightT / 2);
            }
        }
        g.setColor(Color.GREEN);
    }

    public void setUrgence(boolean b) {
        this.urgence = b;
    }

    public void setIsAttacking(boolean b) {
        this.isAttacking = b;
    }

    public void reInit() {
        this.urgence = false;
        this.sentUrgence = false;
        this.isAttacking = false;
    }

    public boolean getUrgenceSent() {
        return this.sentUrgence;
    }

    public boolean isAttacking() {
        return this.isAttacking;
    }

    public Ship getCible() {
        return this.cible;
    }

    public void handleInput() {
        if (Keys.isPressed(Keys.CTRL)) {
            info = !info;
            if (this.classList == BUILDING) {
                if (this.buildingsList.get(curSelect).getInfo()) {
                    this.buildingsList.get(curSelect).showInfo(false);
                } else {
                    this.buildingsList.get(curSelect).showInfo(true);
                }
            } else if (this.classList == VEHICLE && !this.vehiclesList.isEmpty()) {
                if (this.vehiclesList.get(curSelect).getVisible()) {
                    this.vehiclesList.get(curSelect).setVisible(false);
                    this.vehiclesList.get(curSelect).showInfo(false);
                } else {
                    this.vehiclesList.get(curSelect).setVisible(true);
                    this.vehiclesList.get(curSelect).showInfo(true);
                }
            } else if (this.classList == RESSOURCE) {
                if (this.ressourcesList.get(curSelect).getInfo()) {
                    this.ressourcesList.get(curSelect).showInfo(false);
                } else {
                    this.ressourcesList.get(curSelect).showInfo(true);
                }
            }

        }

        if (Keys.isPressed(Keys.UP)) {
            if (this.classList == BUILDING && !this.buildingsList.isEmpty()) {
                this.buildingsList.get(curSelect).setSelected(false);
                this.buildingsList.get(curSelect).showInfo(false);
                if (curSelect > 0) {
                    curSelect--;
                    this.buildingsList.get(curSelect).setSelected(true);
                    if (info) {
                        this.buildingsList.get(curSelect).showInfo(true);
                    }
                }
            } else if (this.classList == VEHICLE && !this.vehiclesList.isEmpty()) {
                this.vehiclesList.get(curSelect).setVisible(false);
                this.vehiclesList.get(curSelect).showInfo(false);
                if (curSelect > 0) {
                    curSelect--;
                    if (info) {
                        this.vehiclesList.get(curSelect).showInfo(true);
                    }
                }
            } else if (this.classList == RESSOURCE && !this.ressourcesList.isEmpty()) {
                this.ressourcesList.get(curSelect).showInfo(false);
                if (curSelect > 0) {
                    curSelect--;
                    if (info) {
                        this.ressourcesList.get(curSelect).showInfo(true);
                    }
                }
            }
        }

        if (Keys.isPressed(Keys.DOWN)) {
            if (this.classList == BUILDING && !this.buildingsList.isEmpty()) {
                this.buildingsList.get(curSelect).setSelected(false);
                this.buildingsList.get(curSelect).showInfo(false);
                if (curSelect < buildingsList.size() - 1) {
                    curSelect++;
                    this.buildingsList.get(curSelect).setSelected(true);
                    if (info) {
                        this.buildingsList.get(curSelect).showInfo(true);
                    }
                }
            } else if (this.classList == VEHICLE && !this.vehiclesList.isEmpty()) {
                this.vehiclesList.get(curSelect).setVisible(false);
                this.vehiclesList.get(curSelect).showInfo(false);
                if (curSelect < vehiclesList.size() - 1) {
                    curSelect++;
                    if (info) {
                        this.vehiclesList.get(curSelect).showInfo(true);
                    }
                }
            } else if (this.classList == RESSOURCE && !this.ressourcesList.isEmpty()) {
                this.ressourcesList.get(curSelect).showInfo(false);
                if (curSelect < ressourcesList.size() - 1) {
                    curSelect++;
                    if (info) {
                        this.ressourcesList.get(curSelect).showInfo(true);
                    }
                }
            }
        }

        if (Keys.isPressed(Keys.A)) {
            if (this.classList == VEHICLE) {
                if (!this.vehiclesList.get(curSelect).getHasChosen()) {
                    this.vehiclesList.get(curSelect).setHasChosen(true);
                    this.vehiclesList.get(curSelect).setDockingAccepted(true);
                    this.vehiclesList.get(curSelect).setIsMoving(true);
                }
            }
        }

        if (Keys.isPressed(Keys.R)) {
            if (this.classList == VEHICLE) {
                if (!this.vehiclesList.get(curSelect).getHasChosen()) {
                    this.vehiclesList.get(curSelect).setHasChosen(true);
                    this.vehiclesList.get(curSelect).setDockingAccepted(false);
                    this.vehiclesList.get(curSelect).setIsMoving(true);
                }
            }
        }

        if (Keys.isPressed(Keys.W)) {
            if (this.classList == VEHICLE) {
                if (!this.vehiclesList.get(curSelect).getHasChosen()) {
                    this.vehiclesList.get(curSelect).setIsMoving(false);
                }
            }
        }

        if (Keys.isPressed(Keys.ENTER)) {
            if (this.classList == BUILDING && urgence) {
                this.buildingsList.get(curSelect).sendUrgence();
                this.sentUrgence = true;
            }
        }

        if (Keys.isPressed(Keys.E)) {
            if (this.classList == VEHICLE && !this.vehiclesList.isEmpty()) {
                if ("ENORME".equals(vehiclesList.get(curSelect).getSize())) {
                    if (!this.vehiclesList.get(curSelect).getHasChosen()) {
                        isAttacking = true;
                        cible = this.vehiclesList.get(curSelect);
                    }
                }
            }
        }
    }
}
