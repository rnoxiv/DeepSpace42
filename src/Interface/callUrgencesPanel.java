package Interface;

import GameObjects.Actors.Urgence;
import Handlers.Keys;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class callUrgencesPanel {

    private ArrayList<Urgence> urgences = new ArrayList();

    private static final String question1 = "Write down the urgence number you would like to call :";
    private static final String question2 = "Please select where to send help on the panel beside : ";
    private static final String numAvailable = "Ambulance : 15  /  Police : 17  /  Fire Department : 18";
    private static final String call = "Call!";
    private static final String[] nums = {"15", "17", "18"};
    private String number = "";
    private static final int MAX_NUM = 1;

    private boolean goodNum = false;
    private final int tWidth, width, tHeight, height;

    public callUrgencesPanel(int tW, int tH) {
        super();
        this.tWidth = tW;
        this.width = tW / 3;
        this.tHeight = tH;
        this.height = tH / 3;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.drawRect(tWidth / 2 - width / 2, tHeight / 2 - height / 2, width, height);
        g.setColor(new Color(0f, 0f, 0f, 0.7f));
        g.fillRect(tWidth / 2 - width / 2, tHeight / 2 - height / 2, width, height);

        g.setColor(Color.GREEN);

        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);

        int fontSize = Math.min(width, height) / 15;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        if (goodNum) {
            int textWidth = (int) (g.getFont().getStringBounds(question2, frc).getWidth());
            int textHeight = (int) (g.getFont().getStringBounds(question2, frc).getHeight());
            int heightQuestion = tHeight / 2 - textHeight / 2;
            g.drawString(question2
                    
                    , tWidth / 2 - textWidth / 2, heightQuestion);
        } else {
            int textWidth = (int) (g.getFont().getStringBounds(question1, frc).getWidth());
            int textHeight = (int) (g.getFont().getStringBounds(question1, frc).getHeight());
            int heightQuestion = tHeight / 2 - height / 4 - textHeight / 4;
            g.drawString(question1, tWidth / 2 - textWidth / 2, heightQuestion);

            textWidth = (int) (g.getFont().getStringBounds(numAvailable, frc).getWidth());
            textHeight = (int) (g.getFont().getStringBounds(numAvailable, frc).getHeight());
            g.drawString(numAvailable, tWidth / 2 - textWidth / 2, heightQuestion + 2 * textHeight);

            g.setColor(Color.GREEN);
            g.drawRect(tWidth / 2 - width / 4 - width / 10, tHeight / 2 + height / 8, width / 2, height / 4);
            g.drawRect(tWidth / 2 + width / 4 - width / 20, tHeight / 2 + height / 8, width / 4, height / 4);
            g.setColor(new Color(1f, 1f, 1f, 0.7f));
            g.fillRect(tWidth / 2 - width / 4 - width / 10, tHeight / 2 + height / 8, width / 2, height / 4);
            if (number.length() <= MAX_NUM) {
                g.setColor(new Color(0f, 1f, 0f, 0.5f));
            } else {
                g.setColor(Color.GREEN);
            }

            fontSize = Math.min(width, height) / 10;
            g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
            textWidth = (int) (g.getFont().getStringBounds(call, frc).getWidth());
            textHeight = (int) (g.getFont().getStringBounds(call, frc).getHeight());
            g.drawString(call, tWidth / 2 + width / 4 + textWidth / 2 - width / 20, tHeight / 2 + height / 8 + textHeight);

            g.setColor(new Color(0, 102, 51));

            if (number != null) {
                fontSize = Math.min(width, height) / 9;
                g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
                textWidth = (int) (g.getFont().getStringBounds(number, frc).getWidth());
                textHeight = (int) (g.getFont().getStringBounds(number, frc).getHeight());
                g.drawString(number, tWidth / 2 - textWidth / 2 - width / 10, tHeight / 2 + height / 8 + textHeight);
            }
        }

    }

    public void reInit() {
        number = "";
        goodNum = false;
    }

    private void select() {
        for (String num : nums) {
            if (number.equals(num)) {
                goodNum = true;
            }
        }
        if (!goodNum) {
            reInit();
        }
    }

    public boolean isGoodNum() {
        return goodNum;
    }

    public String getNumber() {
        return number;
    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ENTER)) {
            if(!goodNum){
                select();
            }
        }

        if (number.length() <= MAX_NUM) {
            if (Keys.isPressed(Keys.UN)) {
                number += 1;
            } else if (Keys.isPressed(Keys.DE)) {
                number += 2;
            } else if (Keys.isPressed(Keys.TR)) {
                number += 3;
            } else if (Keys.isPressed(Keys.QU)) {
                number += 4;
            } else if (Keys.isPressed(Keys.CI)) {
                number += 5;
            } else if (Keys.isPressed(Keys.SI)) {
                number += 6;
            } else if (Keys.isPressed(Keys.SE)) {
                number += 7;
            } else if (Keys.isPressed(Keys.HU)) {
                number += 8;
            } else if (Keys.isPressed(Keys.NE)) {
                number += 9;
            } else if (Keys.isPressed(Keys.ZE)) {
                number += 0;
            }
        }
    }

}
