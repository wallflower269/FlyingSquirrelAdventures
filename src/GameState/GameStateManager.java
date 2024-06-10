package GameState;

import java.awt.Graphics2D;
import java.util.Stack;

public class GameStateManager {
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int GAMEOVERSTATE = 2;
	public static final int WINNERSTATE = 3;
	public static final int LEVEL2STATE = 4;
	public static final int HEPSTATE = 5;
	public static final int SETTINGSTATE = 6;

	public static final int NUMGAMESTATES = 7;

	private GameState[] gameStates;
	private int currentState;
	private Stack<Integer> stateHistory;

	public GameStateManager() {
		gameStates = new GameState[NUMGAMESTATES];
		stateHistory = new Stack<>();
		currentState = MENUSTATE;
		loadState(currentState);
	}

	private void loadState(int state) {
		try {
			if (state == MENUSTATE) {
				gameStates[state] = new MenuState(this);
			} else if (state == LEVEL1STATE) {
				gameStates[state] = new Level1State(this);
			} else if (state == GAMEOVERSTATE) {
				gameStates[state] = new GameOverState(this);
			} else if (state == WINNERSTATE) {
				gameStates[state] = new WinnerState(this);
			} else if (state == LEVEL2STATE) {
				gameStates[state] = new Level2State(this);
			} else if (state == HEPSTATE) {
				gameStates[state] = new HelpState(this);
			}  else if (state == SETTINGSTATE) {
				gameStates[state] = new SettingState(this);
			}
			if (gameStates[state] != null) {

				gameStates[state].init();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public void setState(int state) {
		unloadState(currentState);
		stateHistory.push(currentState);
		currentState = state;
		loadState(currentState);
	}

	public void update() {
		if (gameStates[currentState] != null) {
			try {
				gameStates[currentState].update();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void draw(Graphics2D g) {
		if (gameStates[currentState] != null) {
			try {
				gameStates[currentState].draw(g);
			} catch (Exception e) {
				e.printStackTrace();
			}}

	}

	public void keyPressed(int k) {
		if (gameStates[currentState] != null) {
			gameStates[currentState].keyPressed(k);
		}
	}

	public void keyReleased(int k) {
		if (gameStates[currentState] != null) {
			gameStates[currentState].keyReleased(k);
		}
	}

	public int getCurrentState() {
		return currentState;
	}

	public int getPreviousState() {
		if (stateHistory.size() < 2) {
			return -1;
		}
		return stateHistory.get(stateHistory.size() - 2);
	}

	public int popPreviousState() {
		if (!stateHistory.isEmpty()) {
			return stateHistory.pop();
		}
		return -1;
	}
}