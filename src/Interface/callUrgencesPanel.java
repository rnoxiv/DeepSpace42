package Interface;

import GameObjects.Actors.Urgence;
import GameObjects.Zone;
import Handlers.Keys;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//Panel de l'appel des secours
public class callUrgencesPanel {
    
    //gère les différents types d'urgences
    private ArrayList<Urgence> urgences = new ArrayList(); 
    public static final int FIREDepa = 0;
    public static final int POLICE = 1;
    public static final int AMBULANCE = 2;
    
    //Urgence appelé
    private Urgence uToSend;
    
    //String affiché dans les différentes phases d'appels
    private static final String question1 = "Write down the urgence number you would like to call :";
    private static final String question2 = "Please select where to send help on the panel beside : ";
    private static final String urgenceSentMessage = "Urgences are dispatching, please wait end of operation : ";
    private static final String numAvailable = "Police : 17  /  Fire Department : 18";
    private static final String call = "Call!";
    private static final String[] nums = {"17", "18"};
    private String number = "";
    private static final int MAX_NUM = 1;
    
    //Timer
    private TimerTask taskPerformer;
    private Timer timer;

    private boolean goodNum = false;
    private final int tWidth, width, tHeight, height, widthCoolDown;
    
    public callUrgencesPanel(int tW, int tH) {
        super();
        this.tWidth = tW;
        this.width = tW / 3;
        this.tHeight = tH;
        this.height = tH / 3;
        this.widthCoolDown = width / 2;
        
        urgences.add(new Urgence(new Zone(""), "18", 10));
        urgences.add(new Urgence(new Zone(""), "17", 7));
        
        //urgence à envoyer par défaut
        uToSend = urgences.get(FIREDepa);
        
        taskPerformer = new TimerTask() {
            @Override
            public void run() {
                for (Urgence u : urgences) {
                    u.setUsed(u.getUsed() - 1);
                }
            }
        };
    }

    public void update() {
        for (Urgence u : urgences) {
            if (u.getUsed() > 0) {
                timer.scheduleAtFixedRate(taskPerformer, 0, 10);
            }else{
                timer.cancel();
                timer = new Timer();
            }
        }
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
        
        if (goodNum && uToSend.getOnTheirWay()) {
            int textWidth = (int) (g.getFont().getStringBounds(urgenceSentMessage, frc).getWidth());
            int textHeight = (int) (g.getFont().getStringBounds(urgenceSentMessage, frc).getHeight());
            int heightQuestion = tHeight / 2 - textHeight / 2;
            g.drawString(urgenceSentMessage, tWidth / 2 - textWidth / 2, heightQuestion);
            g.drawRect(tWidth / 2 - widthCoolDown / 2, tHeight / 2 + height / 8, widthCoolDown, height / 4);
            g.setColor(Color.BLUE);
            g.fillRect(tWidth / 2 - widthCoolDown / 2 + 2, tHeight / 2 + height / 8 + 1, (widthCoolDown * uToSend.getActualTime() / uToSend.getTime()) - 1, height / 4 - 1);
        
        } else if (goodNum && !uToSend.getOnTheirWay()) {
            int textWidth = (int) (g.getFont().getStringBounds(question2, frc).getWidth());
            int textHeight = (int) (g.getFont().getStringBounds(question2, frc).getHeight());
            int heightQuestion = tHeight / 2 - textHeight / 2;
            g.drawString(question2, tWidth / 2 - textWidth / 2, heightQuestion);
        
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
    
    public ArrayList<Urgence> getUrgences(){
        return this.urgences;
    }
    
    public Urgence getUrgenceToSend() {
        return uToSend;
    }

    public void reInit() {
        number = "";
        goodNum = false;
    }

    private void select() {
        for (Urgence u : urgences) {
            if (number.equals(u.getNumber())) {
                uToSend = u;
                uToSend.setBusy(true);
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
            if (!goodNum) {
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
