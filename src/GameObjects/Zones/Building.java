package GameObjects.Zones;

import GameObjects.Zone;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Building extends Zone {

    private String name, type;

    private boolean showInfo;

    private ArrayList<Building> neighbours;

    private int maxCapacity, currentCapacity, happiness, width, height, posX, posY, fontSize, topHeight, sideWidth;

    private final int initPosX;

    private boolean selected;
    private Color color;

    private static final Color colorBasic = Color.GREEN;
    private static final Color colorSelected = Color.WHITE;

    public Building(String name, int maxCap, int x, int y, String type, int w, int h, ArrayList<Building> neighbours) {
        super(name);
        this.neighbours = neighbours;
        this.sideWidth = w;
        this.topHeight = h;

        this.name = name;
        this.maxCapacity = maxCap;
        this.currentCapacity = 0;
        this.happiness = 0;
        this.posX = x;
        this.posY = y;
        this.initPosX = x;
        this.fontSize = w / 20;

        this.showInfo = false;

        this.color = colorBasic;

    }

    public Building(String name, int maxCap, int x, int y, String type, int w, int h) {
        super(name);
        this.sideWidth = w;
        this.topHeight = h;

        this.name = name;
        this.maxCapacity = maxCap;
        this.currentCapacity = 0;
        this.happiness = 0;
        this.posX = x;
        this.posY = y;
        this.initPosX = x;
        this.fontSize = w / 20;

        this.showInfo = false;

        this.color = colorBasic;

    }

    public void purge() {
    }

    ;
    public void evacuate() {
        this.currentCapacity = 0;
    }

    public void draw(Graphics2D g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, this.fontSize));
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        int textWidth = (int) (g.getFont().getStringBounds(this.name, frc).getWidth());
        int textHeight = (int) (g.getFont().getStringBounds(this.name, frc).getHeight());
        g.setColor(Color.BLACK);
        g.fillRect(this.posX - textWidth / 2, this.posY - textHeight, textWidth, 2 * textHeight);
        g.setColor(color);
        g.drawRect(this.posX - textWidth / 2, this.posY - textHeight, textWidth, 2 * textHeight);
//        g.drawString(name, posX + 10, posY + 45);
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.drawString(name, posX - textWidth / 2, posY);
    }

    public ArrayList<Building> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(ArrayList<Building> neighbours) {
        this.neighbours = neighbours;
    }

    // RAJOUTE UN BATIMENT ADJACENT
    public boolean addNeighbour(Building pNeighbour) {
        if (this.neighbours.contains(pNeighbour)) {
            System.out.println("Ce voisin à  déjà  été indiqué");
            return false;
        } else {
            this.neighbours.add(pNeighbour);
            return true;
        }
    }

    //FAIT UN LIEN ENTRE DES BATIMENTS
    public static void makeLink(ArrayList<Building> tails, ArrayList<Building> heads) {
        if (tails.size() == heads.size()) {
            for (int i = 0; i < tails.size(); i++) {
                tails.get(i).addNeighbour(heads.get(i));
                heads.get(i).addNeighbour(tails.get(i));
            }
        } else {
            System.out.println("Tails et heads n'ont pas la mÃªme taille");
        }
    }

    public void addCapacity(int capa) {
        this.currentCapacity += capa;
    }

    public int getNumberOfActors() {
        return this.getActors().size();
    }

    public String getType() {
        return type;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getInitPosX() {
        return this.initPosX;
    }

    public int getPosY() {
        return this.posY;
    }

    public String getName() {
        return this.name;
    }

    public int getCurrentCapacity() {
        return this.currentCapacity;
    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public int getHappiness() {
        return this.happiness;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public boolean getInfo() {
        return this.showInfo;
    }

    public void setSelected(boolean b) {
        selected = b;
        if (selected) {
            this.color = colorSelected;
        } else {
            this.color = colorBasic;
        }
    }

    public void setPosX(int _posX) {
        this.posX = _posX;
    }

    public void setPosY(int _posY) {
        this.posY = _posY;
    }

    public void setWidth(int _width) {
        this.width = _width;
    }

    public void setHeight(int _height) {
        this.height = _height;
    }

    public void setColorBuilding(Color c) {
        this.color = c;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public void showInfo(boolean b) {
        this.showInfo = b;
    }

}
