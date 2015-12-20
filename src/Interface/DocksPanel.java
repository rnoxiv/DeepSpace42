package Interface;

import GameObjects.Zones.Buildings.Dock;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

//classe créant la partie visuelle des hangars sur l'affichage
public class DocksPanel {

    private final String name;
    private final int posY, height, width;
    private ArrayList<Dock> docksList;

    public DocksPanel(String n, int sW, int tH) {
        name = n;
        width = sW;
        posY = tH / 2 + 1;
        height = tH / 2;
        docksList = new ArrayList<>();
    }

    public void draw(Graphics2D g) {
        int fontSize = width / 20;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.setColor(Color.GREEN);
        int sizeName = name.length() * fontSize;
        g.drawString(name, width / 2 - sizeName / 4, posY + height / 10);
        g.drawLine(width / 2 - sizeName / 2, posY + height / 9, width / 2 + sizeName / 2, posY + height / 9);
        
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        for (int i = 0; i < docksList.size(); i++) {
            
            int volShips = 0;
            for(int j = 0; j< docksList.get(i).getShips().size();j++){
                volShips+= docksList.get(i).getShips().get(j).getVolume();
            }
            
            String infos = docksList.get(i).getName() + " : " + volShips + " / " + docksList.get(i).getMaxCapacity();
            AffineTransform affinetransform = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
            int textWidth = (int) (g.getFont().getStringBounds(infos, frc).getWidth());
            int textHeight = (int) (g.getFont().getStringBounds(infos, frc).getHeight());
            g.drawString(infos, width / 2 - textWidth / 2, height + height / 3 + (i - docksList.size()+4) * 2 * (textHeight+width/20));

        }

    }

    public void setListBuilding(ArrayList<Dock> lB) {
        docksList = lB;
    }

}
