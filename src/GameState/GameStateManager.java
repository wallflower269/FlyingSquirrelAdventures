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
	public static final int HelpState = 6;

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
	//	System.out.println("Loading state: " + state);
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
			} else if (state == HelpState) {
				gameStates[state] = new HelpState(this);
			}
			if (gameStates[state] != null) {
				System.out.println("Initializing state: " + state);
				gameStates[state].init();
			} else {
				System.out.println("Error: Failed to load state " + state);
			}
		} catch (Exception e) {
			System.out.println("Exception loading state " + state + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void unloadState(int state) {
	//	System.out.println("Unloading state: " + state);
		gameStates[state] = null;
	}

	public void setState(int state) {
	//	System.out.println("Unloading current state: " + currentState);
		unloadState(currentState);
		stateHistory.push(currentState); // Lưu trạng thái hiện tại vào stack trước khi chuyển trạng thái
		currentState = state;
	//	System.out.println("Loading new state: " + currentState);
		loadState(currentState);
	//	System.out.println("State changed to: " + currentState);
	}

	public void update() {
		if (gameStates[currentState] != null) {
			try {
				gameStates[currentState].update();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Error: Current game state is null during update");
		}
	}

	public void draw(Graphics2D g) {
		if (gameStates[currentState] != null) {
			try {
				gameStates[currentState].draw(g);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Error: Current game state is null during draw");
		}
	}

	public void keyPressed(int k) {
		if (gameStates[currentState] != null) {
			gameStates[currentState].keyPressed(k);
		} else {
			System.out.println("Error: Current game state is null during keyPressed");
		}
	}

	public void keyReleased(int k) {
		//System.out.println("Current State: " + currentState);
		if (gameStates[currentState] != null) {
			gameStates[currentState].keyReleased(k);
		} else {
			System.out.println("Error: Current GameState is null during keyReleased");
		}
	}

	public int getCurrentState() {
		return currentState;
	}

	public int getPreviousState() { // Lấy trạng thái trước đó từ stack
		if (stateHistory.size() < 2) {
			return -1; // Không có trạng thái trước đó
		}
		return stateHistory.get(stateHistory.size() - 2); // Lấy trạng thái trước đó nữa
	}

	public int popPreviousState() { // Lấy và loại bỏ trạng thái trước đó từ stack
		if (!stateHistory.isEmpty()) {
			return stateHistory.pop();
		}
		return -1; // Trả về giá trị không hợp lệ nếu stack trống
	}
}
