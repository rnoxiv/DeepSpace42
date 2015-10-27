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

public class Menu extends GameState {

    private String[] options = {"Start", "Help", "Quit"};
    private int width, height;
    private int currentSelection = 0;
    private Font introFont, subFont;

    public Menu(GameStateManager gsm) {
        super(gsm);
        subFont = gsm.loadFont();
        init();
    }

    @Override
    public void init() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) screenSize.getWidth();
        height = (int) screenSize.getHeight();
    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(Graphics2D g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        int fontSize = Math.min(width, height) / 10;
        introFont = subFont.deriveFont(Font.PLAIN, fontSize);
        g.setFont(introFont);
        int boxWidth = 0;
        for (int i = 0; i < options.length; i++) {
            if (i == currentSelection) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.WHITE);
            }
            int textWidth = (int)(introFont.getStringBounds(options[i], frc).getWidth());
            if(i ==0) boxWidth = (int)(introFont.getStringBounds(options[i], frc).getWidth());
            int textHeight = (int)(introFont.getStringBounds(options[i], frc).getHeight());
            //g.drawString(options[i], width / 2 - textWidth/2, height * (i + 1) / (options.length + 1));
            g.drawString(options[i], (i+1)*width/(options.length+1) - textWidth/2, height/2+textHeight/4);
            g.drawRect((i+1)*width/(options.length+1) - boxWidth/2, height/2-textHeight/2, boxWidth, textHeight);
        }
    }

    private void select() {
        if (currentSelection == 0) {
            JukeBox.stop("bgIntro");
            gsm.setState(GameStateManager.SIMULATIONSTATE);
        } else if (currentSelection == 1) {
            //gsm.setState(GameStateManager.HELPSTATE);
        } else if (currentSelection == 2) {
            System.exit(0);
        }
    }

    @Override
    public void handleInput() {
        if (Keys.isPressed(Keys.ECHAP)) {
            System.exit(0);
        }

        if (Keys.isPressed(Keys.ENTER)) {
            select();
        }

        if (Keys.isPressed(Keys.LEFT)) {
            if (currentSelection > 0) {
                //JukeBox.play("menuoption", 0);
                currentSelection--;
            }
        }

        if (Keys.isPressed(Keys.RIGHT)) {
            if (currentSelection < options.length - 1) {
                //JukeBox.play("menuoption", 0);
                currentSelection++;
            }
        }

    }
}
