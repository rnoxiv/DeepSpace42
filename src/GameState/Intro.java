package GameState;

import Audio.JukeBox;
import Handlers.Keys;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Intro extends GameState {

    private int width, height, eventCount;

    private List<String> text;
    private static final String title1 = "DEEP SPACE", title2 = "42", action = "press Enter to continue";

    private Font introFont, subFont;
    private float subFontSize, fontSize;
    private final float WHITE_FIX = 1.0f;
    private Color clrObject;
    private float objOpacity = 0, totOpacity = 0;

    private boolean isDecreasing, hasFontDecreased, eventWait, screenFilled;

    public Intro(GameStateManager gsm) {
        super(gsm);
        subFont = gsm.loadFont();
        init();
    }

    @Override
    public void init() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) screenSize.getWidth();
        height = (int) screenSize.getHeight();
        text = new ArrayList();
        text.add(title1);
        text.add(title2);
        subFontSize = Math.min(width, height);
        clrObject = new Color(WHITE_FIX, WHITE_FIX, WHITE_FIX, 1.0f);
        isDecreasing = true;
        hasFontDecreased = false;
        eventWait = false;
        screenFilled = false;
        eventCount = 0;
        JukeBox.load("/SFX/bgIntro.mp3", "bgIntro");
        JukeBox.play("bgIntro");
    }

    @Override
    public void update() {
        handleInput();
        changeOpacity();
        if (!hasFontDecreased) {
            changeFontSize();
        }
        if (eventWait) {
            eventWait();
        }
        fontSize = subFontSize;

        if (!JukeBox.isRunning("bgIntro") && screenFilled) {
            resetIntro();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (!JukeBox.isRunning("bgIntro")) {
            totOpacity += 0.002;
            if (totOpacity > 0.98f) {
                totOpacity = 0.99f;
                screenFilled = true;
            }
            Color screen = new Color(WHITE_FIX, WHITE_FIX, WHITE_FIX, totOpacity);
            g.setColor(screen);
            g.fillRect(0, 0, width, height);
        } else if(!screenFilled){
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);

            g.setColor(Color.WHITE);

            introFont = subFont.deriveFont(Font.PLAIN, fontSize);

            g.setFont(introFont);
            AffineTransform affinetransform = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
            int prevTextHeight = 0;
            for (int i = 0; i < text.size(); i++) {
                int textHeight = (int) (introFont.getStringBounds(text.get(i), frc).getHeight());
                if (i != 0) {
                    prevTextHeight += (int) (int) (introFont.getStringBounds(text.get(i - 1), frc).getHeight());
                }
                if (i == 2) {
                    fontSize = Math.min(width, height) / 30;
                    introFont = subFont.deriveFont(Font.PLAIN, fontSize);
                    prevTextHeight *= 2;
                    g.setFont(introFont);
                    g.setColor(clrObject);
                }
                int textWidth = (int) (introFont.getStringBounds(text.get(i), frc).getWidth());

                g.drawString(text.get(i), (width / 2) - (textWidth / 2), height / 2 - textHeight / 2 + prevTextHeight);

            }
        }if(hasFontDecreased){
            
        }
    }

    public void changeOpacity() {
        if (isDecreasing) {
            objOpacity -= 0.01;
            if (objOpacity < 0.05f) {
                objOpacity = 0.05f;
                isDecreasing = false;
            }
        } else {
            objOpacity += 0.01;
            if (objOpacity > 0.98f) {
                objOpacity = 0.99f;
                isDecreasing = true;
            }
        }
        clrObject = new Color(WHITE_FIX, WHITE_FIX, WHITE_FIX, objOpacity);
    }

    public void changeFontSize() {
        if (subFontSize > Math.min(width, height) / 10) {
            subFontSize -= width / 3250f; //0.7f
        } else {
            subFontSize = Math.min(width, height) / 10;
            hasFontDecreased = true;
            eventWait = true;
        }
    }

    public void eventWait() {
        eventCount++;
        if (eventCount == 500) {
            eventWait = false;
            text.add(action);
            eventCount = 0;
        }
    }

    public void resetIntro() {
        hasFontDecreased = false;
        eventWait = false;
        text.remove(text.size() - 1);
        subFontSize = Math.min(width, height);
        eventCount = 0;
        totOpacity = 0;
        screenFilled = false;
        JukeBox.play("bgIntro");
    }
    
    public void createLine(int x1,int y1,int x2,int y2,float d){
        
    }
    
    @Override
    public void handleInput() {
        if (Keys.anyKeyPress() && !hasFontDecreased) {
            hasFontDecreased = true;
            subFontSize = Math.min(width, height) / 10;
            text.add(action);
        } else if (hasFontDecreased && Keys.isPressed(Keys.ENTER)) {
            gsm.setState(GameStateManager.MENUSTATE);
        }

    }

}
