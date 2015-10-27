package Interface;

import Actors.Vehicle;
import Handlers.Keys;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class InfosPanel {

    private final static String name = "Infos";
    private int height, width, tWidth, topHeight, curSelect;

    private ArrayList<Vehicle> list;

    private boolean isShown;

    public InfosPanel(int w, int tW, int h, int tH) {
        width = w;
        height = h;
        tWidth = tW;
        topHeight = tH;
        isShown = false;
        curSelect = 0;
        list = new ArrayList<>();
    }

    public void draw(Graphics2D g) {
        drawString(g);

    }

    public void update() {

    }

    public void addToList(Vehicle v) {
        if (!list.contains(v)) {
            this.list.add(v);
        }
    }

    public void removeFromList(Vehicle v) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == v) {
                v.setVisible(false);
                list.remove(v);
                if (i == 0) {
                    curSelect = 0;
                } else {
                    curSelect = i - 1;
                }
            }
        }
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
        int boxInfoSize = 0;
        for (int i = 0; i < list.size(); i++) {
            if (i == curSelect) {
                g.setColor(new Color(15, 185, 120));
            } else {
                g.setColor(Color.GREEN);
            }
            String n = list.get(i).getName();
            int sizeName = n.length() * fontSize;
            g.drawString(n, tWidth - width / 2 - sizeName / 4, height / 10 + (i + 3) * 2 * fontSize + boxInfoSize);
            if (list.get(i).getInfo()) {
                String passengers = "passengers : " + list.get(i).getNbPassenger();
                String size = "size : " + list.get(i).getSize();
                AffineTransform affinetransform = new AffineTransform();
                FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
                int textHeightP = (int) (g.getFont().getStringBounds(passengers, frc).getHeight());
                int textHeightS = (int) (g.getFont().getStringBounds(size, frc).getHeight());
                int textWidthP = (int) (g.getFont().getStringBounds(passengers, frc).getWidth());
                int textWidthS = (int) (g.getFont().getStringBounds(size, frc).getWidth());
                
                int textHeight = textHeightP + textHeightS;
                int textWidth = Math.max(textWidthP, textWidthS);
                boxInfoSize = 2 * textHeight;
                g.setStroke(new BasicStroke(1));
                g.drawRect(tWidth - width / 2 - textWidth, height / 10 + (i + 3) * 2 * fontSize + 5, 2 * textWidth, boxInfoSize);
                g.drawString(passengers, tWidth - width / 2 - textWidth / 2, height / 10 + (i + 3) * 2 *fontSize + boxInfoSize/2);
                g.drawString(size, tWidth - width / 2 - textWidth / 2, height / 10 + (i + 3) * 2 * fontSize + 5 + boxInfoSize/2  + fontSize / 2);
            }
        }
    }

    public void select() {
    }

    public void handleInput() {
        if (Keys.isPressed(Keys.CTRL)) {
            if (this.list.get(curSelect).getVisible()) {
                this.list.get(curSelect).setVisible(false);
                this.list.get(curSelect).showInfo(false);
            } else {
                this.list.get(curSelect).setVisible(true);
                this.list.get(curSelect).showInfo(true);
            }
        }

        if (Keys.isPressed(Keys.UP)) {
            this.list.get(curSelect).setVisible(false);
            this.list.get(curSelect).showInfo(false);
            if (curSelect > 0) {
                curSelect--;
            }
        }

        if (Keys.isPressed(Keys.DOWN)) {
            this.list.get(curSelect).setVisible(false);
            this.list.get(curSelect).showInfo(false);
            if (curSelect < list.size() - 1) {
                curSelect++;
            }
        }

    }
}
