package GameState;

import Handlers.Keys;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Help extends GameState {

    private int width, height;

    public Help(GameStateManager gsm) {
        super(gsm);
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

    }

    //gère les entrées de l'utilisateur
    @Override
    public void handleInput() {
        if (Keys.isPressed(Keys.ECHAP)) {
            try {
                gsm.setState(GameStateManager.MENUSTATE);
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
