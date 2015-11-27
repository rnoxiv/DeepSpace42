package GameObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class Ressource {

    private String name;

    private Color color;

    private boolean isDecreasing = false;

    private static final Color green = new Color(0, 255, 0);
    private static final Color red = new Color(255, 0, 0);
    private static final Color orange = new Color(255, 160, 0);
    private float opacity;
    
    private final int initPosX;
    
    private int maxCap, currentCap, posX, posY, widthP, heightP;

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
        String curCap = "" + this.currentCap;
        
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        
        int textWidth = (int) (g.getFont().getStringBounds(this.name, frc).getWidth());
        
        g.setColor(this.color);
        g.drawRect(this.posX, this.posY + (heightP / 10), widthP, heightP);
        g.drawString(name, this.posX + widthP/2 - textWidth/2, this.posY);
        g.drawString("0%", this.posX - (this.widthP / 2), this.posY + heightP + (heightP / 10));
        g.drawString("50%", this.posX - (this.widthP / 2), pStart - (4 * heightP / 10));
        g.drawString("100%", this.posX - (this.widthP / 2), this.posY + (heightP / 10));

        if (this.currentCap == 0) {
            changeOpacity();
            this.color = new Color(1f, 0, 0, opacity);
            g.drawString(curCap, this.posX + (widthP / 2) - 10, pStart + (heightP / 10));
            g.drawRect(this.posX, this.posY + (heightP / 10), widthP, heightP);
        } else if (this.currentCap <= capa) {
            this.color = red;
            g.fillRect(this.posX, pStart, widthP, (heightP / 10));
            g.drawString(curCap, this.posX + (widthP / 2) - 10, pStart);
            g.drawRect(this.posX, this.posY + (heightP / 10), widthP, heightP);
        } else if (this.currentCap <= (2 * capa)) {
            this.color = orange;
            g.fillRect(this.posX, pStart - (heightP / 10), widthP, 2 * (heightP / 10));
            g.drawString(curCap, this.posX + (widthP / 2) - 10, pStart - (heightP / 10));
        } else if (this.currentCap <= (3 * capa)) {
            this.color = orange;
            g.fillRect(this.posX, pStart - 2 * (heightP / 10), widthP, 3 * (heightP / 10));
            g.drawString(curCap, this.posX + (widthP / 2) - 10, pStart - 2 * (heightP / 10));
        } else if (this.currentCap <= (4 * capa)) {
            this.color = orange;
            g.fillRect(this.posX, pStart - 3 * (heightP / 10), widthP, 4 * (heightP / 10));
            g.drawString(curCap, this.posX + (widthP / 2) - 10, pStart - 3 * (heightP / 10));
        } else if (this.currentCap <= (5 * capa)) {
            this.color = orange;
            g.fillRect(this.posX, pStart - 4 * (heightP / 10), widthP, 5 * (heightP / 10));
            g.drawString(curCap, this.posX + (widthP / 2) - 10, pStart - (4 * heightP / 10));

        } else if (this.currentCap <= (6 * capa)) {
            this.color = green;
            g.fillRect(this.posX, pStart - 5 * (heightP / 10), widthP, 6 * (heightP / 10));
            g.drawString(curCap, this.posX + (widthP / 2) - 10, pStart - 5 * (heightP / 10));
        } else if (this.currentCap <= (7 * capa)) {
            this.color = green;
            g.fillRect(this.posX, pStart - 6 * (heightP / 10), widthP, (7 * (heightP / 10)));
            g.drawString(curCap, this.posX + (widthP / 2) - 10, pStart - 6 * (heightP / 10));
        } else if (this.currentCap <= (8 * capa)) {
            this.color = green;
            g.fillRect(this.posX, pStart - 7 * (heightP / 10), widthP, 8 * (heightP / 10));
            g.drawString(curCap, this.posX + (widthP / 2) - 10, pStart - 7 * (heightP / 10));
        } else if (this.currentCap <= (9 * capa)) {
            this.color = green;
            g.fillRect(this.posX, pStart - 8 * (heightP / 10), widthP, 9 * (heightP / 10));
            g.drawString(curCap, this.posX + (widthP / 2) - 10, pStart - 8 * (heightP / 10));
        } else if (this.currentCap <= (10 * capa)) {
            this.color = green;
            g.fillRect(this.posX, pStart - 9 * (heightP / 10), widthP, heightP);
            g.drawString(curCap, this.posX + (widthP / 2) - 10, pStart - 9 * (heightP / 10));
        }

    }

    ;
    
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

    public void setCurrentcap(int cp) {
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

    public int getCurrentcap() {
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

}
