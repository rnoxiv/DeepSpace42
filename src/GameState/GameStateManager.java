package GameState;

import Main.GamePanel;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	public static final int NUMGAMESTATES = 6;
	public static final int MENUSTATE = 0;
	public static final int HELPSTATE = 1;
	public static final int SIMULATIONSTATE = 2;
	
	public GameStateManager() {
		
		gameStates = new GameState[NUMGAMESTATES];
		
		currentState = SIMULATIONSTATE;
		loadState(currentState);
		
	}
	
	private void loadState(int state) {
                if(state == SIMULATIONSTATE)
			gameStates[state] = new Simulation(this);
//                else if(state == MENUSTATE)
//			gameStates[state] = new MenuState(this);
//                else if(state == HELPSTATE)
//			gameStates[state] = new HelpState(this);
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
		if(gameStates[currentState] != null) gameStates[currentState].update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		if(gameStates[currentState] != null) gameStates[currentState].draw(g);
//		else {
//			g.setColor(java.awt.Color.BLACK);
//			g.fillRect(0, 0, GamePanel.tWidth, GamePanel.tHeight);
//		}
	}
	
}