package Handlers;

import java.awt.event.KeyEvent;

public class Keys {
	//number of Keys 
	public static final int NUM_KEYS = 27;
	
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
        public static final int N = 12;
        public static final int UN = 13;
        public static final int DE = 14;
        public static final int TR = 15;
        public static final int QU = 16;
        public static final int CI = 17;
        public static final int SI = 18;
        public static final int SE = 19;
        public static final int HU = 20;
        public static final int NE = 21;
        public static final int ZE = 22;
        public static final int C = 23;
        public static final int P = 24;
        public static final int T = 25;
        public static final int B = 26;
        
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
                else if(i == KeyEvent.VK_N) keyState[N] = b;
                else if(i == KeyEvent.VK_NUMPAD1) keyState[UN] = b;
                else if(i == KeyEvent.VK_NUMPAD2) keyState[DE] = b;
                else if(i == KeyEvent.VK_NUMPAD3) keyState[TR] = b;
                else if(i == KeyEvent.VK_NUMPAD4) keyState[QU] = b;
                else if(i == KeyEvent.VK_NUMPAD5) keyState[CI] = b;
                else if(i == KeyEvent.VK_NUMPAD6) keyState[SI] = b;
                else if(i == KeyEvent.VK_NUMPAD7) keyState[SE] = b;
                else if(i == KeyEvent.VK_NUMPAD8) keyState[HU] = b;
                else if(i == KeyEvent.VK_NUMPAD9) keyState[NE] = b;
                else if(i == KeyEvent.VK_NUMPAD0) keyState[ZE] = b;
                else if(i == KeyEvent.VK_C) keyState[C] = b;
                else if(i == KeyEvent.VK_T) keyState[T] = b;
                else if(i == KeyEvent.VK_P) keyState[P] = b;
                else if(i == KeyEvent.VK_B) keyState[B] = b;
	}
	
	public static void update() {
            System.arraycopy(keyState, 0, prevKeyState, 0, NUM_KEYS);
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
