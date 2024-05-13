package GameState;

import java.util.ArrayList;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	public static final int NUMGAMESTATES = 3;
	public static final int GAMEOVERSTATE = 2;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	
	public GameStateManager() {
		
		gameStates = new GameState[NUMGAMESTATES ];
		
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		if(state == GAMEOVERSTATE)
			gameStates[state] = new GameOverState(this);
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		if (state < 0 || state >= gameStates.length) {
			System.out.println("Invalid state index: " + state);
			return; // Tránh truy cập mảng ngoài giới hạn
		}
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		gameStates[currentState].init();
		System.out.println("State changed to: " + currentState);

	}
	
	public void update() {
		try {
			gameStates[currentState].update();
		} catch(Exception e) {}
	}
	
	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch(Exception e) {}
	}
	
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	

	public void keyReleased(int k) {
		if (currentState >= 0 && currentState < gameStates.length) {
				gameStates[currentState].keyReleased(k);
		} else {
				System.out.println("Attempted to call keyReleased on invalid state index: " + currentState);
		}
		}

	}
	










