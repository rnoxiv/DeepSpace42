package GameState;

import Handlers.Arduino;
import static Handlers.Arduino.turnOffLed;
import static Handlers.Arduino.turnOnLed;
import Utilities.JukeBox;
import Handlers.Keys;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Intro extends GameState {

    private Arduino obj;

    private int width, height, eventCount;

    private List<String> text;
    private static final String title1 = "DEEP SPACE", title2 = "42", action = "press Enter to continue";

    private String[] options = {"Start", "Help", "Quit"};
    private int currentSelection = 0;

    private Font introFont, subFont;
    private float subFontSize, fontSize;
    private final float WHITE_FIX = 1.0f;
    private Color clrObject;
    private float objOpacity = 0, totOpacity = 0, bgOpacity = 0;

    private Image bgImg;

    private boolean isDecreasing, hasFontDecreased, eventWait, screenFilled, intro, menu;

    public Intro(GameStateManager gsm) {
        super(gsm);
        obj = new Arduino();
        obj.initialize();
        subFont = gsm.loadFont();
        init();
    }

    @Override
    public void init() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) screenSize.getWidth();
        height = (int) screenSize.getHeight();
        try {
            bgImg = ImageIO.read(Intro.class.getResource("/IMG/bgIntro.jpg"));

        } catch (Exception e) {
        }
        text = new ArrayList();
        text.add(title1);
        text.add(title2);
        subFontSize = Math.min(width, height);
        clrObject = new Color(WHITE_FIX, WHITE_FIX, WHITE_FIX, 1.0f);
        isDecreasing = true;
        hasFontDecreased = false;
        eventWait = false;
        screenFilled = false;
        intro = true;
        menu = false;
        eventCount = 0;
        JukeBox.load("/SFX/bgIntro1.mp3", "bgIntro1");
        JukeBox.load("/SFX/bgIntro2.mp3", "bgIntro2");
        JukeBox.load("/SFX/menuselect.mp3", "menuselect");
        JukeBox.load("/SFX/menuoption.mp3", "menuoption");
        JukeBox.play("bgIntro1");

        try {
            for (int i = 1; i < 18; i++) {
                try {
                    turnOnLed(i);
                } catch (IOException ex) {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Intro.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void update() {
        handleInput();
        if (intro) {
            changeOpacity();
            if (!hasFontDecreased) {
                changeFontSize();
                changeBgOpacity();
            }

            if ((!JukeBox.isRunning("bgIntro1") && !JukeBox.isRunning("bgIntro2") && !screenFilled) || JukeBox.getPosition("bgIntro1") == 1503843) {
                JukeBox.play("bgIntro2");
                text.add(action);
            }

            fontSize = subFontSize;

            if ((!JukeBox.isRunning("bgIntro2") || !JukeBox.isRunning("bgIntro1")) && screenFilled) {
                resetIntro();
            }
        }
    }

    //gère l'affichage graphique de l'intro / menu
    @Override
    public void draw(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, bgOpacity - totOpacity));
        g.drawImage(bgImg, 0, 0, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        if (intro) {
            if (!JukeBox.isRunning("bgIntro1") && !JukeBox.isRunning("bgIntro2")) {
                totOpacity += 0.002;
                if (totOpacity > 0.98f) {
                    totOpacity = 0.99f;
                    screenFilled = true;
                }
                Color screen = new Color(WHITE_FIX, WHITE_FIX, WHITE_FIX, totOpacity);
                g.setColor(screen);
                g.fillRect(0, 0, width, height);
            } else if (!screenFilled) {
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
            }
        } else if (menu) {
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
                int textWidth = (int) (introFont.getStringBounds(options[i], frc).getWidth());
                if (i == 0) {
                    boxWidth = (int) (introFont.getStringBounds(options[i], frc).getWidth());
                }
                int textHeight = (int) (introFont.getStringBounds(options[i], frc).getHeight());
                g.drawString(options[i], (i + 1) * width / (options.length + 1) - textWidth / 2, height / 2 + textHeight / 4);
                g.drawRect((i + 1) * width / (options.length + 1) - boxWidth / 2, height / 2 - textHeight / 2, boxWidth, textHeight);
            }
        }
    }

    public void drawTitle(Graphics2D g) {

    }

    //change l'opacité du message invitant les joueurs à rentrer dans l'état menu
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

    //change opacité fond d'écran pour intro
    public void changeBgOpacity() {
        bgOpacity += 0.0005f;
        if (bgOpacity > 0.99f) {
            bgOpacity = 0.99f;
        }
    }

    //diminue la taille de la font
    public void changeFontSize() {
        if (subFontSize > Math.min(width, height) / 10) {
            subFontSize -= width / 3000f; //0.7f
        } else {
            subFontSize = Math.min(width, height) / 10;
            hasFontDecreased = true;
        }
    }

    //reset la 'cinématique' d'intro
    public void resetIntro() {
        hasFontDecreased = false;
        text.remove(text.size() - 1);
        subFontSize = Math.min(width, height);
        totOpacity = 0;
        screenFilled = false;
        JukeBox.play("bgIntro1");
    }

    //Actions selon choix joueur : lancer simulation, lancer Help, Quitter
    private void select() {
        if (currentSelection == 0) {
            JukeBox.stop("bgIntro2");

            for (int i = 1; i < 18; i++) {
                try {
                    turnOffLed(i);
                } catch (IOException ex) {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                gsm.setState(GameStateManager.SIMULATIONSTATE);
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (currentSelection == 2) {
            for (int i = 1; i < 18; i++) {
                try {
                    turnOffLed(i);
                } catch (IOException ex) {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.exit(0);
        }
    }

    //gère les entrées de l'utilisateur
    @Override
    public void handleInput() {
        if (intro) {
            if (Keys.anyKeyPress() && !hasFontDecreased) {
                hasFontDecreased = true;
                subFontSize = Math.min(width, height) / 10;
                bgOpacity = 0.99f;
                JukeBox.stop("bgIntro1");
            } else if (hasFontDecreased && Keys.isPressed(Keys.ENTER)) {
                JukeBox.play("menuselect", 0);
                intro = false;
                menu = true;
            }
        } else if (menu) {
            if (Keys.isPressed(Keys.ECHAP)) {
                System.exit(0);
            }

            if (Keys.isPressed(Keys.ENTER)) {
                select();
            }

            if (Keys.isPressed(Keys.LEFT)) {
                if (currentSelection > 0) {
                    JukeBox.play("menuoption", 0);
                    currentSelection--;
                }
            }

            if (Keys.isPressed(Keys.RIGHT)) {
                if (currentSelection < options.length - 1) {
                    JukeBox.play("menuoption", 0);
                    currentSelection++;
                }
            }
        }
    }

}
