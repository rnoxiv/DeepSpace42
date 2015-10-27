package Interface;

import Actors.Vehicle;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class InfosPanel {

    private final static String name = "Infos";
    private int height, width, tWidth, topHeight;

    private ArrayList<Vehicle> list;

    private boolean isShown;

    public InfosPanel(int w, int tW, int h, int tH) {
        width = w;
        height = h;
        tWidth = tW;
        topHeight = tH;
        isShown = false;
        list = new ArrayList<>();
    }

    public void draw(Graphics2D g) {
        drawString(g);

    }

    public void update() {

    }

    public void setList(ArrayList l) {
        this.list = l;
    }

    public void setShown(boolean b) {
        isShown = b;
    }

    public boolean isShown() {
        return isShown;
    }

    public void drawString(Graphics2D g) {
        int fontSize = width / 10;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.setColor(Color.GREEN);
        int sizeName = name.length() * fontSize;
        g.drawString(name, tWidth - width / 2 - sizeName / 4, height / 10);
        g.drawLine(tWidth - width / 2 - sizeName / 2, height / 9, tWidth - width / 2 + sizeName / 2, height / 9);
        drawList(g);
    }

    public void drawList(Graphics2D g) {
        int fontSize = width / 20;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.setColor(Color.GREEN);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsOnRadar()) {
                String n = list.get(i).getName();
                int sizeName = n.length() * fontSize;
                g.drawString(n, tWidth - width / 2 - sizeName / 4, height / 10 + (i+2)*fontSize);
            }
        }
    }
}
