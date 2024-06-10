package GameState;
import java.awt.event.KeyEvent;
import Main.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

import Audio.AudioPlayer;

import java.awt.image.BufferedImage;

import java.io.IOException;

import static Audio.AudioPlayer.setSoundOn;


public class SettingState implements GameState {
    private GameStateManager gsm;
    private BufferedImage background;


    private String[] options = {"ON",
                                "OFF",
                                "Quit"
    };
    private int currentChoice = 0;

    private Color titleColor;
    private Font titleFont;
    private Font font;

    private AudioPlayer audioPlayer;

    public SettingState(GameStateManager gsm) {
        this.gsm = gsm;
        init();

    }

    // @Override
    public void init() {
        Game.playMusic();

        try {
            background = ImageIO.read(
                    getClass().getResourceAsStream("/Backgrounds/m1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update() {}
    public void draw(Graphics2D g) {
        if (background != null) {
            g.drawImage(background, 0, 0, null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 320, 240);
        }


        // Draw Title
        g.setColor(new Color(246, 58, 15));
        g.setFont(new Font("Cambria", Font.BOLD,30));
        g.drawString("Music", 120, 60);


        // Draw options
        g.setFont(new Font("Arial", Font.BOLD, 14));
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(new Color(210, 86, 21, 215));
            } else {
                g.setColor(new Color(8, 14, 16));
            }
            g.drawString(options[i], 145, 143 + i * 15);
        }
    }


    private void select() {
        String selectedOption = options[currentChoice];
        if (selectedOption.equals("ON")) {
            setSoundOn(true);
            Game.playMusic();
        } else if (selectedOption.equals("OFF")) {
            setSoundOn(false);
            Game.stopMusic();
        } else if (selectedOption.equals("Quit")) {
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }
    // @Override
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


    // @Override
    public void keyReleased(int k) {}
}
