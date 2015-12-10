package GameObjects.Zones;

import GameObjects.Zone;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Building extends Zone {

    private String type;

    private boolean showInfo;

    private ArrayList<Building> neighbours = new ArrayList();
    
    private final int maxCapacity, happiness, fontSize;
    private int  currentCapacity, width, height, posX, posY ;

    private final int initPosX, widthConst = 10;

    private boolean isFire = false;
    private boolean selected;
    private Color color;

    private static final Color colorBasic = Color.green;
    private static final Color colorSelected = Color.white;
    private final static int red = 255, blue = 0;
    private int green = 0;
    private boolean orange;

    public Building(String name, int maxCap, int x, int y, String type, int w, int h, ArrayList<Building> neighbours) {
        super(name);
        this.neighbours = neighbours;

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

        this.maxCapacity = maxCap;
        this.currentCapacity = 200;
        this.happiness = 0;
        this.posX = x;
        this.posY = y;
        this.initPosX = x;
        this.fontSize = w / 22;

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
        g.fillRect(this.posX - textWidth / 2 -widthConst/2, this.posY - textHeight, textWidth+widthConst, 2 * textHeight);
        if(this.isFire){
            changeColor(g);
        }
        g.setColor(this.color);
        g.drawRect(this.posX - textWidth / 2 - widthConst/2, this.posY - textHeight, textWidth + widthConst, 2 * textHeight);
//        g.drawString(name, posX + 10, posY + 45);
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.drawString(this.name, this.posX - textWidth / 2 + widthConst/4, this.posY+widthConst/4);
    }
    
    public void changeColor(Graphics2D g){
        if(!orange && green <=140){
            green++;
            if(green == 140){
                orange = true;
            }
        }else{
            green--;
            if(green == 0){
                orange = false;
            }
        }
        
        this.color = new Color(red, green, blue);
    }
    
    public ArrayList<Building> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(ArrayList<Building> neighbours) {
        this.neighbours = neighbours;
    }

    public ArrayList<Building> addNeighbourFire (ArrayList <Building> ab, Building b){
        List<Double> distanceList = new ArrayList();
        for (Building ab1 : ab) {
            float centerX = ab1.getPosX() + ab1.getWidth() / 2;
            float centerY = ab1.getPosY() + ab1.getHeight() / 2;
            double distance = sqrt(abs((centerX-b.getPosX())*(centerX-b.getPosX())+(centerY-b.getPosY())*(centerY-b.getPosY())));
            if (distance != 0.0){
                distanceList.add(distance);
            }
        }
        Collections.sort(distanceList);
        System.out.println("distanceList = " + distanceList);
        double firstB = distanceList.get(0);
        double secondB = distanceList.get(1);
        for (Building ab1 : ab) {
            float centerX = ab1.getPosX() + ab1.getWidth() / 2;
            float centerY = ab1.getPosY() + ab1.getHeight() / 2;
            double distance = sqrt(abs((centerX-b.getPosX())*(centerX-b.getPosX())+(centerY-b.getPosY())*(centerY-b.getPosY())));
            if (distance == firstB) {
                //b.addNeighbour(ab.get(j));
                neighbours.add(ab1);
            }
            if (distance == secondB) {
                //b.addNeighbour(ab.get(j));
                neighbours.add(ab1);
            }
        }
        return b.getNeighbours();
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
    
    public void sendUrgence(){
        
        System.out.println("URGENCE sent to " + this.name);
        System.out.println("Building : test - A CODER");
        
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

    public Color getColorBuilding() {
        return this.color;
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
        }
        else{
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

    public boolean getFire(){
        return this.isFire;
    }
    
    public void setFire(boolean b){
        this.isFire = b;
        if(!isFire) this.green = 0;
    }
    
    public void setCapacity(int cc){
         this.currentCapacity = cc;
     }
    
}
