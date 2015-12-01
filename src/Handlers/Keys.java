package Handlers;

import java.awt.event.KeyEvent;

public class Keys {
	//number of Keys 
	public static final int NUM_KEYS = 12;
	
        //arrays of the keys states
	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];
	
	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	public static final int ECHAP = 4;
	public static final int ENTER = 5;
	public static final int CTRL = 6;
	public static final int A = 7;
	public static final int R = 8;
	public static final int W = 9;
        public static final int E = 10;
        public static final int S = 11;
        
	
	public static void keySet(int i, boolean b) {
		if(i == KeyEvent.VK_RIGHT) keyState[RIGHT] = b;
		else if(i == KeyEvent.VK_LEFT) keyState[LEFT] = b;
		else if(i == KeyEvent.VK_ESCAPE) keyState[ECHAP] = b;
		else if(i == KeyEvent.VK_UP) keyState[UP] = b;
		else if(i == KeyEvent.VK_DOWN) keyState[DOWN] = b;
		else if(i == KeyEvent.VK_ENTER) keyState[ENTER] = b;
		else if(i == KeyEvent.VK_CONTROL) keyState[CTRL] = b;
		else if(i == KeyEvent.VK_A) keyState[A] = b;
		else if(i == KeyEvent.VK_R) keyState[R] = b;
		else if(i == KeyEvent.VK_W) keyState[W] = b;
                else if(i == KeyEvent.VK_E) keyState[E] = b;
                else if(i == KeyEvent.VK_S) keyState[S] = b;
	}
	
	public static void update() {
		for(int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
	}
	
	public static boolean isPressed(int i) {
		return keyState[i] && !prevKeyState[i];
	}
	
	public static boolean anyKeyPress() {
		for(int i = 0; i < NUM_KEYS; i++) {
			if(keyState[i]) return true;
		}
		return false;
	}
	
}
