package GameState;

import java.awt.Graphics2D;

public interface GameState {
    void init();
    void update();
    void draw(Graphics2D g);
    void keyPressed(int k);
    void keyReleased(int k);
}

// public abstract class GameState {
	
// 	protected GameStateManager gsm;
	
// 	public abstract void init();
// 	public abstract void update();
// 	public abstract void draw(java.awt.Graphics2D g);
// 	public abstract void keyPressed(int k);
// 	public abstract void keyReleased(int k);
	
// }

