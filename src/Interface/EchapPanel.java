package Interface;

import Handlers.Keys;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

//classe permettant l'affichage du panel quit
public class EchapPanel {

    private final String question = "You sure about leaving the Simulation mate?", title = "DEEP SPACE 42";
    private final String[] options = {"Exit to Menu", "Quit", "Cancel"};

    private boolean exit = false, stay = false, leaveSimulation = false;
    ;
    
    protected int tWidth, width, tHeight, height, currentSelection = 0;

    public EchapPanel(int tW, int tH) {
        super();
        this.tWidth = tW;
        this.width = tW / 2;
        this.tHeight = tH;
        this.height = tH / 2;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.drawRect(width - width / 2, height - height / 2, width, height);
        g.setColor(new Color(0f, 0f, 0f, 0.7f));
        g.fillRect(width - width / 2, height - height / 2, width, height);

        g.setColor(Color.GREEN);

        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);

        int fontSize = Math.min(width, height) / 10;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        int textWidthT = (int) (g.getFont().getStringBounds(title, frc).getWidth());
        int textHeightT = (int) (g.getFont().getStringBounds(title, frc).getHeight());
        int heightTitle = height - height / 4 + textHeightT / 4;
        g.drawString(title, width - textWidthT / 2, heightTitle);
        fontSize = Math.min(width, height) / 15;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        int textWidth = (int) (g.getFont().getStringBounds(question, frc).getWidth());
        int textHeight = (int) (g.getFont().getStringBounds(question, frc).getHeight());
        int heightQuestion = heightTitle + 2 * textHeight;
        g.drawString(question, width - textWidth / 2, heightQuestion);
        for (int i = 0; i < options.length; i++) {
            if (i == currentSelection) {
                g.setColor(Color.WHITE);
            } else {

                g.setColor(Color.GREEN);
            }
            textWidth = (int) (g.getFont().getStringBounds(options[i], frc).getWidth());

            textHeight = (int) (g.getFont().getStringBounds(options[i], frc).getHeight());
            g.drawString(options[i], width - width / 2 + (2 + i) * width / 6 - textWidth / 2, heightQuestion + 2 * textHeight);
        }
    }

    private void select() {
        if (currentSelection == 0) {
            exit = true;
        } else if (currentSelection == 1) {
            leaveSimulation = true;
        } else if (currentSelection == 2) {
            stay = true;
        }
    }

    public boolean getEchap() {
        return exit;
    }

    public boolean getStay() {
        return stay;
    }

    public boolean getLeaveSimulation() {
        return leaveSimulation;
    }

    public void reInit() {
        stay = false;
        currentSelection = 0;
    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ENTER)) {
            select();
        }

        if (Keys.isPressed(Keys.LEFT)) {
            if (currentSelection > 0) {
                currentSelection--;
            }
        }

        if (Keys.isPressed(Keys.RIGHT)) {
            if (currentSelection < options.length - 1) {
                currentSelection++;
            }
        }

    }

}
