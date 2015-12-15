/**
 * classe permettant de gérer les différents états du jeu
 */
package GameState;

import java.awt.Font;
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

    public GameStateManager() {

        gameStates = new GameState[NUMGAMESTATES];

        currentState = MENUSTATE;
        loadState(currentState);

    }

    private void loadState(int state) {
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

    private void unloadState(int state) {
        gameStates[state] = null;
    }

    public void setState(int state) {
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
