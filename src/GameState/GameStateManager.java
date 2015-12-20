/**
 * classe permettant de gérer les différents états du jeu
 */
package GameState;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

public class GameStateManager {

    private final GameState[] gameStates;
    private int currentState;

    private static final int NUMGAMESTATES = 6;
    public static final int MENUSTATE = 0;
    public static final int HELPSTATE = 1;
    public static final int SIMULATIONSTATE = 2;
    public static final int GAMEOVERSTATE = 3;

    private Font subFont;

    public GameStateManager() throws IOException, InterruptedException {

        gameStates = new GameState[NUMGAMESTATES];

        currentState = MENUSTATE;
        loadState(currentState);

    }
    
    //charge l'état du jeu correspondant
    private void loadState(int state) throws IOException, InterruptedException {
        if (state == SIMULATIONSTATE) {
            gameStates[state] = new Simulation(this);
        } else if (state == MENUSTATE) {
            gameStates[state] = new Intro(this);
        } else if (state == GAMEOVERSTATE) {
            gameStates[state] = new GameOver(this);
        } else if (state == HELPSTATE) {
            gameStates[state] = new Help(this);
        }
    }
    
    //decharge l'état du jeu correspondant
    private void unloadState(int state) {
        gameStates[state] = null;
    }
    
    //décharge l'état du jeu actuel pour charger l'état du jeu demandé
    public void setState(int state) throws IOException, InterruptedException {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);
    }

    public void update() {
        if (gameStates[currentState] != null) {
            gameStates[currentState].update();
        }
    }

    public void draw(java.awt.Graphics2D g) {
        if (gameStates[currentState] != null) {
            gameStates[currentState].draw(g);
        }
    }

    public Font loadFont() {
        try {
            InputStream is = getClass().getResourceAsStream("/FONTS/spaceAge.ttf");
            subFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return subFont;
    }

}
