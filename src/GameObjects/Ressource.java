package GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class Ressource {

    private String name;

    private Color color;

    private boolean isDecreasing = false;

    private boolean createShip = false;

    private boolean command = false, canCommand = true;
    protected static final float restoreRate = 0.0001f;
    protected static final float totalCommandTime = 1f;
    protected float currentCommandTime = totalCommandTime;

    private boolean showInfo = false;

    private static final Color green = new Color(0, 255, 0);
    private static final Color red = new Color(255, 0, 0);
    private static final Color orange = new Color(255, 160, 0);
    private float opacity, currentCap;

    private final int initPosX;

    private int maxCap, posX, posY, widthP, heightP;

    public Ressource(String _n, int _mc, int _cc, int _x, int _y, int _w, int _h) {

        this.name = _n;
        this.maxCap = _mc;
        this.currentCap = _cc;
        this.posX = _x;
        this.initPosX = this.posX;
        this.posY = _y;
        this.widthP = _w / 15;
        this.heightP = 2 * _h / 3;
        this.color = green;
        this.opacity = 0;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        int capa = this.maxCap / 10;
        int pStart = this.posY + heightP;

        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);

        int textWidth = (int) (g.getFont().getStringBounds(this.name, frc).getWidth());

        g.setColor(this.color);
        g.drawRect(this.posX, this.posY + (heightP / 10), widthP, heightP);
        g.drawString(name, this.posX + widthP / 2 - textWidth / 2, this.posY);
        g.drawString("0%", this.posX - (this.widthP / 2), this.posY + heightP + (heightP / 10));
        g.drawString("50%", this.posX - (this.widthP / 2), pStart - (4 * heightP / 10));
        g.drawString("100%", this.posX - (this.widthP / 2), this.posY + (heightP / 10));
        
        if (this.currentCap <= 0) {
            changeOpacity();
            this.color = new Color(1f, 0, 0, opacity);
            this.currentCap = 0;
        } else if ((int)this.currentCap <= capa) {
            this.color = red;
            //JukeBox.play("lowRessource", 1);
        } else if ((int)this.currentCap <= (5 * capa)) {
            this.color = orange;
        } else if ((int)this.currentCap <= (10 * capa)) {
            this.color = green;
        }

        double ratio = heightP * (float) (this.currentCap * 100 / this.maxCap) / 100;

        int curCapHeight = (int) (g.getFont().getStringBounds(this.name, frc).getHeight());
        int curCapWidth = (int) (g.getFont().getStringBounds(this.name, frc).getWidth());

        String curCap = "" + (int) this.currentCap;

        g.drawString(curCap, this.posX + (widthP / 2) - curCapWidth / 4, this.posY + (heightP / 10) + (heightP - (int) ratio - curCapHeight / 2));
        g.drawRect(this.posX, this.posY + (heightP / 10), widthP, heightP);
        g.fillRect(this.posX, this.posY + (heightP / 10) + (heightP - (int) ratio), widthP, (int) ratio);

        if (!"POPULATION".equals(this.name)){
            this.color = green;
            g.drawRect(this.posX, this.posY + heightP + (heightP / 8), (int) (totalCommandTime * 100), (heightP / 50));
            g.setColor(Color.blue);
            g.fillRect(this.posX, this.posY + heightP + (heightP / 8), (int) (currentCommandTime * 100), (heightP / 50));
        }
    }

    public void coolDown() {
        if (this.currentCommandTime != this.totalCommandTime) {
            this.currentCommandTime += this.restoreRate;
            if (this.currentCommandTime >= this.totalCommandTime) {
                this.currentCommandTime = this.totalCommandTime;
                this.canCommand = true;
            }
        }
    }

    public void commandRessource() {
        if (this.currentCommandTime == this.totalCommandTime) {
            this.command = true;
        }

        if (this.command) {
            this.currentCommandTime = 0.00000000001f;
            this.createShip = true;
            this.command = false;
            this.canCommand = false;
        }
    }

    public void changeOpacity() {
        if (this.isDecreasing) {
            this.opacity -= 0.01;
            if (this.opacity < 0.05f) {
                this.opacity = 0.05f;
                this.isDecreasing = false;
            }
        } else {
            this.opacity += 0.01;
            if (this.opacity > 0.98f) {
                this.opacity = 0.99f;
                this.isDecreasing = true;
            }
        }
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setMaxcap(int mc) {
        this.maxCap = mc;
    }

    public void setCurrentcap(float cp) {
        this.currentCap = cp;
    }

    public void setPosx(int x) {
        this.posX = x;
    }

    public int getInitPosX() {
        return this.initPosX;
    }

    public void setPosy(int y) {
        this.posY = y;
    }

    public void setWidth(int w) {
        this.widthP = w;
    }

    public void setHeight(int h) {
        this.heightP = h;
    }

    public String getName() {
        return this.name;
    }

    public int getMaxcap() {
        return this.maxCap;
    }

    public float getCurrentcap() {
        return this.currentCap;
    }

    public int getPosx() {
        return this.posX;
    }

    public int getPosy() {
        return this.posY;
    }

    public int getWidth() {
        return this.widthP;
    }

    public int getHeigth() {
        return this.heightP;
    }

    public void showInfo(boolean b) {
        this.showInfo = b;
    }

    public boolean getInfo() {
        return this.showInfo;
    }

    public boolean getCommand() {
        return this.command;
    }
    
    public boolean getCanCommand() {
        return this.command;
    }

    public void setCommand(boolean _b) {
        this.command = _b;
    }

    public boolean getCreateShip() {
        return this.createShip;
    }

    public void resetCreateShip() {
        this.createShip = !this.createShip;
    }

    public Ressource getRessource() {
        return this;
    }

    public void setMaxCap() {
        this.currentCap = this.maxCap;
    }

}
