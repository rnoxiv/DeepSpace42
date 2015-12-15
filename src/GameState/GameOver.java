package GameState;

import Handlers.Keys;
import Utilities.JukeBox;
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
import javax.imageio.ImageIO;

public class GameOver extends GameState {

    private int width, height;
    private static final String title = "Simulation Over";

    private Font introFont, subFont;
    private float fontSize;
    private float opacity = 0.009f;

    private Image bgImg;

    private int waitTime = 50, curWaitTime = 0;
    private boolean checked;

    public GameOver(GameStateManager gsm) {
        super(gsm);
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
            e.printStackTrace();
        }

        fontSize = Math.min(width, height) / 10;
        checked = false;
        JukeBox.load("/SFX/GameOver.mp3", "over");
        JukeBox.stop("mainBG");
        JukeBox.play("over");
    }

    @Override
    public void update() {
        if (curWaitTime >= waitTime) {
            handleInput();
        }
        changeOpacity();
        curWaitTime++;
    }

    //gère l'affichage graphique de l'intro / menu
    @Override
    public void draw(Graphics2D g) {
        if (opacity < 0.009f) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            gsm.setState(gsm.MENUSTATE);
        }
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g.drawImage(bgImg, 0, 0, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        g.setColor(new Color(1.0f, 1.0f, 1.0f, opacity));

        introFont = subFont.deriveFont(Font.PLAIN, fontSize);

        g.setFont(introFont);
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        int textHeight = (int) (introFont.getStringBounds(title, frc).getHeight());
        int textWidth = (int) (introFont.getStringBounds(title, frc).getWidth());

        g.drawString(title, (width / 2) - (textWidth / 2), height / 2 - textHeight / 2);
    }

    //change opacité fond d'écran pour intro
    public void changeOpacity() {
        if (checked) {
            opacity -= 0.005f;
        } else {
            opacity += 0.005f;
            if (opacity > 0.99f) {
                opacity = 0.99f;
            }
        }
    }

    //gère les entrées de l'utilisateur
    @Override
    public void handleInput() {
        if (Keys.anyKeyPress()) {
            checked = true;
        }
    }
}
