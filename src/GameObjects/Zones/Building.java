package GameObjects.Zones;

import GameObjects.Ressource;
import GameObjects.Zone;
import Utilities.Variables;
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
import java.util.Random;

public class Building extends Zone {

    private String type;

    private boolean showInfo, missile = false;

    private ArrayList<Building> neighbours = new ArrayList();

    private final int maxCapacity, fontSize;
    private int currentCapacity, width, height, posX, posY;

    private float happiness;

    private final int initPosX, widthConst = 10;
    private Variables var = new Variables();
    
    private boolean isFire = false;
    private boolean isFight = false;
    private boolean selected, happinessLow = false;
    private Color color;

    private static final Color colorBasic = Color.green;
    private static final Color colorSelected = Color.white;
    private int red = 0, blue = 0, green = 0;
    private boolean orange, darkBlue;

    public Building(String name, int maxCap, int x, int y, String type, int w, int h, ArrayList<Building> neighbours) {
        super(name);
        this.neighbours = neighbours;

        this.maxCapacity = maxCap;
        this.currentCapacity = 0;
        this.happiness = var.randNum(20,80);;
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
        this.currentCapacity = (4 * maxCap) / 10;
        this.happiness = var.randNum(20,80);
        this.posX = x;
        this.posY = y;
        this.initPosX = x;
        this.fontSize = w / 22;

        this.showInfo = false;

        this.color = colorBasic;

    }

    public void purge() {
        this.currentCapacity = 0;
        this.isFire = false;
        this.isFight = false;
    }

    public void evacuate() {
        Random rand = new Random();
        int evacuateToBuilding = rand.nextInt(this.neighbours.size());

        if (this.neighbours.get(evacuateToBuilding).getCurrentCapacity() + this.currentCapacity > this.neighbours.get(evacuateToBuilding).getMaxCapacity()) {
            int diffCapacity = this.neighbours.get(evacuateToBuilding).getMaxCapacity() - this.neighbours.get(evacuateToBuilding).getCurrentCapacity();
            this.neighbours.get(evacuateToBuilding).setCapacity(this.neighbours.get(evacuateToBuilding).getMaxCapacity());
            this.currentCapacity -= diffCapacity;
            evacuate();
        } else {
            this.neighbours.get(evacuateToBuilding).setCapacity(this.neighbours.get(evacuateToBuilding).getCurrentCapacity() + this.currentCapacity);
        }
        this.currentCapacity = 0;
    }

    public boolean happiness(ArrayList<Ressource> _r){
        int a=0;
        int b=0;
        for (int i=1;i<_r.size();i++){
            a+=_r.get(i).getMaxcap();
            b+=_r.get(i).getCurrentcap();
        }
        
        if(this.currentCapacity<=0){
            happiness=0;
        }
        else if((a/b)<0.3 || this.currentCapacity>((9*this.maxCapacity)/10)){
            float fireHapbis=0;
            if (isFire || isFight){
                fireHapbis=0.01f;
            }
            happiness-=(0.001 + fireHapbis);
            fireHapbis=0;
        }
        else if ((a/b)<0.5 || this.currentCapacity>((8*this.maxCapacity)/10)){
            float fireHap=0;
            if (isFire || isFight){
                fireHap=0.01f;
            }
            happiness-=(0.00001+fireHap);
            fireHap=0;
        }
        else{
            if(isFire || isFight){
                happiness-=0.001;
            }
            else{
                happiness+=0.001;
            }
        }
        if(happiness <=0){
            this.happiness=0;
        }
        if (happiness>=100){
            this.happiness=100;
        }
        if(happiness<25){
            happinessLow=true;
        }
        return happinessLow;
    }

    public void draw(Graphics2D g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, this.fontSize));
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        int textWidth = (int) (g.getFont().getStringBounds(this.name, frc).getWidth());
        int textHeight = (int) (g.getFont().getStringBounds(this.name, frc).getHeight());
        g.setColor(Color.BLACK);
        g.fillRect(this.posX - textWidth / 2 - widthConst / 2, this.posY - textHeight, textWidth + widthConst, 2 * textHeight);
        if (this.isFire || this.isFight) {
            changeColor(g);
        }
        g.setColor(this.color);
        g.drawRect(this.posX - textWidth / 2 - widthConst / 2, this.posY - textHeight, textWidth + widthConst, 2 * textHeight);
//        g.drawString(name, posX + 10, posY + 45);
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.drawString(this.name, this.posX - textWidth / 2 + widthConst / 4, this.posY + widthConst / 4);
    }

    public void changeColor(Graphics2D g) {
        if (this.isFire) {
            if (!this.orange && this.green <= 140) {
                this.green++;
                if (this.green >= 140) {
                    this.orange = true;
                }
            } else if (green >= 0) {
                this.green--;
            }

            if (green < 0) {
                green = 1;
                this.orange = false;
            }
        } else if (this.isFight) {
            if (this.darkBlue && this.green <= 140) {
                this.green++;
                if (this.green >= 140) {
                    this.darkBlue = false;
                }
            } else if (green >= 0) {
                this.green--;
            }

            if (green < 0) {
                green = 1;
                this.darkBlue = true;
            }
        }
        this.color = new Color(this.red, this.green, this.blue);
    }

    public ArrayList<Building> getNeighbours() {
        return this.neighbours;
    }

    public void setNeighbour(ArrayList<Building> ab) {
        List<Double> distanceList = new ArrayList();
        for (Building ab1 : ab) {
            float centerX = ab1.getPosX() + ab1.getWidth() / 2;
            float centerY = ab1.getPosY() + ab1.getHeight() / 2;
            double distance = sqrt(abs((centerX - this.getPosX()) * (centerX - this.getPosX()) + (centerY - this.getPosY()) * (centerY - this.getPosY())));
            if (distance != 0.0) {
                distanceList.add(distance);
            }
        }
        Collections.sort(distanceList);
        double firstB = distanceList.get(0);
        double secondB = distanceList.get(1);
        for (Building ab1 : ab) {
            float centerX = ab1.getPosX() + ab1.getWidth() / 2;
            float centerY = ab1.getPosY() + ab1.getHeight() / 2;
            double distance = sqrt(abs((centerX - this.getPosX()) * (centerX - this.getPosX()) + (centerY - this.getPosY()) * (centerY - this.getPosY())));
            if (distance == firstB) {
                //b.addNeighbour(ab.get(j));
                neighbours.add(ab1);
            }
            if (distance == secondB) {
                //b.addNeighbour(ab.get(j));
                neighbours.add(ab1);
            }
        }
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

    public Color getColorBuilding() {
        return this.color;
    }

    public int getCurrentCapacity() {
        return this.currentCapacity;
    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public float getHappiness() {
        return this.happiness;
    }

    public boolean getMissile() {
        return this.missile;
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

    public boolean getFire() {
        return this.isFire;
    }

    public boolean getFight() {
        return this.isFight;
    }

    public void setFire(boolean b) {
        this.isFire = b;
        if (!this.isFire) {
            this.red = 0;
            if (!this.selected) {
                this.color = colorBasic;
            } else if (this.selected) {
                this.color = colorSelected;
            }
        } else if (this.isFire) {
            this.red = 255;
        }
    }

    public void setCapacity(int cc) {
        this.currentCapacity = cc;
    }

    public void setHappiness(boolean _b) {
        this.happinessLow = _b;
    }

    public void setHappinessInt(float h) {
        this.happiness = h;
    }

    public void setFight(boolean b) {
        this.isFight = b;
        if (!this.isFight) {
            this.blue = 0;
            if (!this.selected) {
                this.color = colorBasic;
            } else if (this.selected) {
                this.color = colorSelected;
            }
        } else if (this.isFight) {
            this.blue = 255;
        }
    }

    public void setMissile(boolean _b) {
        this.missile = _b;
    }

}
