package Interface;

import GameObjects.Zones.Building;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class DocksPanel {

    private final String name;
    private final int posY, height, width;
    private ArrayList<Building> buildingsList;

    public DocksPanel(String n, int sW, int tH) {
        name = n;
        width = sW;
        posY = tH / 2 + 1;
        height = tH / 2;
        buildingsList = new ArrayList<>();
    }

    public void draw(Graphics2D g) {
        int fontSize = width / 20;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.setColor(Color.GREEN);
        int sizeName = name.length() * fontSize;
        g.drawString(name, width / 2 - sizeName / 4, posY + height / 10);
        g.drawLine(width / 2 - sizeName / 2, posY + height / 9, width / 2 + sizeName / 2, posY + height / 9);
        
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        for (int i = buildingsList.size() - 5; i < buildingsList.size(); i++) {
            
            String infos = buildingsList.get(i).getName() + " : " + buildingsList.get(i).getCurrentCapacity() + " / " + buildingsList.get(i).getMaxCapacity();
            AffineTransform affinetransform = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
            int textWidth = (int) (g.getFont().getStringBounds(infos, frc).getWidth());
            int textHeight = (int) (g.getFont().getStringBounds(infos, frc).getHeight());
            g.drawString(infos, width / 2 - textWidth / 2, height + height / 3 + (i - buildingsList.size()+4) * 2 * (textHeight+width/20));

        }

    }

    public void update() {

    }

    public void setListBuilding(ArrayList<Building> lB) {
        buildingsList = lB;
    }

}
