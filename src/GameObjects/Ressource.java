package GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class Ressource {

    private String name;

    private Color color;

    private boolean isDecreasing = false;
    
    private boolean showInfo;

    private static final Color green = new Color(0, 255, 0);
    private static final Color red = new Color(255, 0, 0);
    private static final Color orange = new Color(255, 160, 0);
    private float opacity,currentCap;
    
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
        heightP = 2 * _h / 3;
        this.color = green;
        this.opacity = 0;
        
        this.showInfo = false;
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
        g.drawString(name, this.posX + widthP/2 - textWidth/2, this.posY);
        g.drawString("0%", this.posX - (this.widthP / 2), this.posY + heightP + (heightP / 10));
        g.drawString("50%", this.posX - (this.widthP / 2), pStart - (4 * heightP / 10));
        g.drawString("100%", this.posX - (this.widthP / 2), this.posY + (heightP / 10));

        if (this.currentCap <= 0) {
            changeOpacity();
            this.color = new Color(1f, 0, 0, opacity);
            this.currentCap = 0;
        } else if (this.currentCap <= capa) {
            this.color = red;
        } else if (this.currentCap <= (5 * capa)) {
            this.color = orange;
        } else if (this.currentCap <= (10 * capa)) {
            this.color = green;
        }
        
        double ratio = heightP*(float)(this.currentCap*100/this.maxCap)/100;
        
        int curCapHeight = (int) (g.getFont().getStringBounds(this.name, frc).getHeight());
        int curCapWidth = (int) (g.getFont().getStringBounds(this.name, frc).getWidth());
        
        String curCap = "" + (int)this.currentCap;
        
        g.drawString(curCap, this.posX + (widthP / 2) - curCapWidth/4, this.posY + (heightP / 10) + (heightP -(int)ratio - curCapHeight/2) );
        g.drawRect(this.posX, this.posY + (heightP / 10), widthP, heightP);
        g.fillRect(this.posX, this.posY + (heightP / 10) + (heightP -(int)ratio), widthP, (int)ratio);

    }
    
    public void changeOpacity() {
        if (isDecreasing) {
            opacity -= 0.01;
            if (opacity < 0.05f) {
                opacity = 0.05f;
                isDecreasing = false;
            }
        } else {    
            opacity += 0.01;
            if (opacity > 0.98f) {
                opacity = 0.99f;
                isDecreasing = true;
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

}
