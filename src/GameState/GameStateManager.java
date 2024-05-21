package GameState;

import java.util.ArrayList;


public class GameStateManager {

	// Existing constants

	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
		// Additional constant for Level 2 state
	public static final int LEVEL2STATE = 2;
	public static final int WINNERSTATE = 3;
	public static final int GAMEOVERSTATE = 4;
	public static final int NUMGAMESTATES = 5;


	private GameState[] gameStates;
	private int currentState;

	public GameStateManager() {
		gameStates = new GameState[NUMGAMESTATES];
		currentState = MENUSTATE;
		loadState(currentState);
	}

	private void loadState(int state) {
		if (state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		else if (state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		else if (state == LEVEL2STATE)
			gameStates[state] = new Level2State(this);
		else if (state == GAMEOVERSTATE)
			gameStates[state] = new GameOverState(this);
		else if (state == WINNERSTATE)
			gameStates[state] = new WinnerState(this);
	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public void setState(int state) {
		// Check if the state index is within bounds
		if (state < 0 || state >= NUMGAMESTATES) {
			System.out.println("Invalid state index: " + state);
			return;
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
		} catch (Exception e) {
			// Handle exceptions
		}
	}

	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch (Exception e) {
			// Handle exceptions
		}
	}

	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}

	public void keyReleased(int k) {
		System.out.println("Current State: " + currentState);
		if (gameStates[currentState] != null) {
			gameStates[currentState].keyReleased(k);
		} else {
			System.out.println("Error: Current GameState is null!");
		}
	}
	
}

