package GameState;
import java.awt.event.KeyEvent;
import Main.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.IOException;

public class GameOverState implements GameState {
    private GameStateManager gsm;
    private BufferedImage background;


    private String[] options = {"Restart",
            "Menu"};
    private int currentChoice = 0;


    public GameOverState(GameStateManager gsm) {

        this.gsm = gsm;

        init();
    }
    public void init() {
        Game.stopMusic();

        try {
            background = ImageIO.read(
                    getClass().getResourceAsStream("/Backgrounds/m1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // @Override
    public void update() {}

    public void draw(Graphics2D g) {
        if (background != null) {
            g.drawImage(background, 0, 0, null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 320, 240);
        }

        g.setColor(new Color(246, 58, 15));
        g.setFont(new Font("Cambria", Font.BOLD,30));
        g.drawString("Game Over", 80, 60);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(new Color(210, 86, 21, 215));
            } else {
                g.setColor(new Color(8, 14, 16));
            }
            g.drawString(options[i], 135, 143 + i * 15);

        }
    }


    private void select() {

        if (currentChoice == 0) {
            gsm.setState(gsm.popPreviousState());
        }
        if (currentChoice == 1) {
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }
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
    public void keyReleased(int k) {}
}