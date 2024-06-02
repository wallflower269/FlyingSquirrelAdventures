package GameState;

import java.awt.Graphics2D;

public interface GameState {

    void init();
    void update();
    void draw(Graphics2D g);
    void keyPressed(int k);
    void keyReleased(int k);
}



