
package Interface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class MissionPanel {
    
    private final String name;
    private final int height, width;
    
    private ArrayList<String> missions = new ArrayList();
    
    public MissionPanel(String n, int sW, int tH) {
       
        name = n;
        width = sW;
        height = tH/2;
    }
    
    public void draw(Graphics2D g){
        
        drawString(g);
        
    }
    
    public void update(){
        System.out.println(missions.size());
    }
    
    public void drawString(Graphics2D g) {
        int fontSize = width/20;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.setColor(Color.GREEN);
        int sizeName = name.length()*fontSize;
        g.drawString(name, width/2-sizeName/4, height/10);
        g.drawLine(width/2-sizeName/2, height/9, width/2+sizeName/2, height/9);
        
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        for (int i = 0; i < missions.size(); i++) {
            
            AffineTransform affinetransform = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
            int textWidth = (int) (g.getFont().getStringBounds(missions.get(i), frc).getWidth());
            int textHeight = (int) (g.getFont().getStringBounds(missions.get(i), frc).getHeight());
            g.drawString(missions.get(i), width / 2 - textWidth / 2, height - height / 3 - (i - missions.size()+4) * 2 * (textHeight+width/20));

        }
    }
    
    public void addMision(String m){
        missions.add(m);
    }
    
}
