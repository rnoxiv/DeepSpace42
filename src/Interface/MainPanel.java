package Interface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MainPanel {

    protected boolean detailBarOn, finishSliding;
    protected InfosPanel iPanel;
    private String name;
    /**
     * swidth = width panel gauche, width = width mainPanel, tWidth = width
     * fenêtre, height = height fenêtre
     */
    protected int width, height, sWidth, tWidth, topHeight, mainHeight, rightBarWidth, maxRightBarWidth;

    public MainPanel(String n, int sW, int mW, int tH, int tW) {
        super();
        this.name = n;
        this.width = mW;
        this.sWidth = sW;
        this.tWidth = tW;
        this.height = tH;
        this.topHeight = this.height / 18;
        this.mainHeight = height - topHeight - 2;
        maxRightBarWidth = tWidth/6;
        this.iPanel = new InfosPanel("Infos",maxRightBarWidth,tWidth,height,topHeight);
        
        detailBarOn = false;
        finishSliding = false;
        rightBarWidth = 0;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.drawLine(sWidth + 1, topHeight, tWidth, topHeight);
        
        g.setColor(Color.BLACK);
        g.fillRect(tWidth - rightBarWidth, topHeight + 1, tWidth, height);
        g.setColor(Color.GREEN);
        g.drawLine(tWidth - rightBarWidth, topHeight + 1, tWidth - rightBarWidth, height);
        
        drawName(g);
        
        if(this.iPanel.isShown()){
            this.iPanel.draw(g);
        }
    }

    public void update() {
        iPanel.update();
        if (detailBarOn && !finishSliding) {
            showDetailBar();
        }else if(!detailBarOn && !finishSliding){
            hideDetailBar();
        }else if((detailBarOn && finishSliding) || (!detailBarOn && finishSliding)){
            finishSliding = false;
            
        }
    }

    public void drawName(Graphics2D g) {
        int fontSize = (width + rightBarWidth) / 60;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.setColor(Color.GREEN);
        int sizeName = this.getName().length() * fontSize;
        g.drawString(this.getName(), sWidth + (width + rightBarWidth) / 2 - sizeName / 4, 2 * topHeight / 3);
    }

    public void showDetailBar() {
        if(rightBarWidth<maxRightBarWidth){
            this.rightBarWidth += 10;
            this.width -=10;
        }else{
            this.rightBarWidth = maxRightBarWidth;
            this.width = tWidth-sWidth-rightBarWidth;
            this.finishSliding = true;
            this.iPanel.setShown(true);        
        }
    }
    
    public void hideDetailBar(){
        this.iPanel.setShown(false);
        if(rightBarWidth>0){
            this.rightBarWidth -=10;
            this.width += 10;
        }else{
            this.rightBarWidth = 0;
            this.width = tWidth-sWidth;
            this.finishSliding = true;
        }
    }
    
    public void setDetailBar(boolean b) {this.detailBarOn = b;}
    
    public InfosPanel getIPanel(){return this.iPanel;}
    public boolean getDetailBarOn(){return this.detailBarOn;}
    public String getName() {return this.name;}
    
    public void handleInput(){
        if(this.detailBarOn){
            iPanel.handleInput();
        }
    }
}
