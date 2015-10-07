package GameState;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Simulation extends GameState {
    
    private int tWidth, tHeight, mWidth, sWidth, sHeight;
    
    public Simulation(GameStateManager gsm) {
        super(gsm);
        init();
    }
    
    @Override
    public void init() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        tWidth = (int) screenSize.getWidth();
        mWidth = tWidth-tWidth/5;
        sWidth = tWidth-mWidth;
        tHeight = (int) screenSize.getHeight();
        sHeight = tHeight/2;
    }
    
    @Override
    public void update() {
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, tWidth, tHeight);
        g.setColor(Color.GREEN);
        g.fillRect(sWidth, 0, 2, tHeight);
        g.fillRect(0, sHeight, sWidth, 2);
        g.drawRect(0, 0, tWidth-1, tHeight-1);
        g.setColor(Color.RED);
        g.fillRect(sWidth+mWidth/2-10,tHeight/2-10,20,20);
        g.setColor(new Color(220,205,205));
        Area circle = new Area(new Ellipse2D.Double(sWidth+mWidth/2-100, tHeight/2-100, 200,200));
        g.draw(circle);
        Area circle2 = new Area(new Ellipse2D.Double(sWidth+mWidth/2-250, tHeight/2-250, 500,500));
        g.draw(circle2);
        Area circle3 = new Area(new Ellipse2D.Double(sWidth+mWidth/2-400, tHeight/2-400, 800,800));
        g.draw(circle3);
        Area circle4 = new Area(new Ellipse2D.Double(sWidth+mWidth/2-550, tHeight/2-550, 1100,1100));
        g.draw(circle4);
        Area circle5 = new Area(new Ellipse2D.Double(sWidth+mWidth/2-700, tHeight/2-700, 1400,1400));
        g.draw(circle5);
        Area circle6 = new Area(new Ellipse2D.Double(sWidth+mWidth/2-850, tHeight/2-850, 1700,1700));
        g.draw(circle6);
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, sWidth-1, tHeight/2-1);
        g.fillRect(1, tHeight/2+1, sWidth-1, tHeight/2-2);
        drawString(g);
    }
    
    public void drawString(Graphics2D g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.setColor(Color.GREEN);
        g.drawString("Missions", 130, 35);
        g.drawLine(60, 60, 280, 60);
    }
}
