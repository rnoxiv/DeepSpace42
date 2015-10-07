package Main;

import GameState.GameStateManager;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    private Thread thread;
    private boolean running;
    private GameStateManager gsm;

    //Thread
    private static final int FPS = 60;
    private static final long targetTime = 1000 / FPS;
    private long start, elapsed, wait;

    // graphics
    private BufferedImage image;
    private Graphics2D g;
    private int tWidth, tHeight, mWidth, sWidth, sHeight;

    public GamePanel() {
        super();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        tWidth = (int) screenSize.getWidth();
        tHeight = (int) screenSize.getHeight();
        
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

    public void init() {
        image = new BufferedImage(tWidth, tHeight, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        
        gsm = new GameStateManager();
    }
    
    @Override
    public void run() {
        init();

        while (running) {
            start = System.nanoTime();
            update();
            draw();
            drawToScreen();
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;
            if (wait < 0) {
                wait = 5;
            }
            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
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
    }
    
    public int getTWidth(){return tWidth;}
    public int getTHeight(){return tHeight;}

}
