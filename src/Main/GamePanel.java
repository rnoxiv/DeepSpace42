/*
* classe Panel, gère l'affichage, les updates, et les inputs/outputs
*/

package Main;

import Utilities.JukeBox;
import GameState.GameStateManager;
import Handlers.Keys;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    private Thread thread;
    private final boolean running;
    private GameStateManager gsm;
    
    //Thread
    private static final int FPS = 60;
    private static final long targetTime = 1000 / FPS;
    private long start, elapsed, wait;

    // graphics
    private BufferedImage image;
    private Graphics2D g;
    private final int tWidth, tHeight;
    
    public GamePanel() {
        super();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        tWidth = (int) screenSize.getWidth();
        tHeight = (int) screenSize.getHeight();
        
        addKeyListener(this);
        
        setPreferredSize(new Dimension(tWidth, tHeight));
        requestFocus();
        setFocusable(true);
        running = true;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void init() throws IOException, InterruptedException {
        image = new BufferedImage(tWidth, tHeight, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        
        JukeBox.init();
        
        gsm = new GameStateManager();
    }

    @Override
    public void run() {
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (running) {
            start = System.nanoTime();
            draw();
            update();
            drawToScreen();
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;
            if (wait < 0) {
                wait = 5;
            }
            try {
                Thread.sleep(wait);
            } catch (Exception e) {
            }
        }
    }

    public void draw() {
        gsm.draw(g);
    }

    public void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, tWidth, tHeight, null);
        g2.dispose();
    }
    
    public void update() {
        gsm.update();
        Keys.update();
    }
    
    @Override
    public void keyPressed(KeyEvent k) {
        Keys.keySet(k.getKeyCode(), true);
    }
    
    @Override
    public void keyTyped(KeyEvent k) {
    }
    
    @Override
    public void keyReleased(KeyEvent k) {
        Keys.keySet(k.getKeyCode(), false);
    }
}
