package GameState;

import Main.Game;
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class WinnerState implements GameState {
    private GameStateManager gsm;
    private BufferedImage background;
    private String[] options = {"Restart", "Next Level", "Menu"};
    private int currentChoice = 0;

    public WinnerState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    @Override
    public void init() {
        Game.stopMusic();
        try {
            background = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/m1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading background image.");
        }
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics2D g) {
        if (background != null) {
            g.drawImage(background, 0, 0, null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 320, 240);
        }

        g.setColor(new Color(246, 58, 15));
        g.setFont(new Font("Cambria", Font.BOLD, 30));
        g.drawString("Winner", 115, 60);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(new Color(210, 86, 21, 215));
            } else {
                g.setColor(new Color(8, 14, 16));
            }
            g.drawString(options[i], 130, 143 + i * 15);
        }
    }

    private void select() {
        int previousState = gsm.getPreviousState();


        if (currentChoice == 0) {

            gsm.setState(GameStateManager.LEVEL1STATE);
        } else if (currentChoice == 1) {
            if (previousState == GameStateManager.LEVEL1STATE) {

                gsm.setState(GameStateManager.LEVEL2STATE);
            } else if (previousState == GameStateManager.LEVEL2STATE) {

                gsm.setState(GameStateManager.MENUSTATE);
            }
        } else if (currentChoice == 2) {
            gsm.setState(GameStateManager.MENUSTATE);

        }

    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            select();
        }
        if (k == KeyEvent.VK_UP) {
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if (currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    @Override
    public void keyReleased(int k) {}
}
