package GameState;

import Main.Game;
import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState implements GameState {
    private GameStateManager gsm;


    private Background bg;

    private int currentChoice = 0;
    private String[] options = {
            "Start",
            "Help",
            "Music",
            "Quit"
    };

    private Color titleColor;
    private Font titleFont;

    private Font font;

    public MenuState(GameStateManager gsm) {
        if (GameSettings.isSoundOn()) {
            Game.playMusic();
        } else if (GameSettings.isSoundOff()) {
            Game.stopMusic();}

        this.gsm = gsm;

        try {

            bg = new Background("/Backgrounds/Background_could.png", 1);
            bg.setVector(-0.1, 0);

            titleColor = new Color(241, 58, 17);
            titleFont = new Font(
                    "Century Gothic",
                    Font.BOLD,
                    30);

            font = new Font("Arial", Font.BOLD, 14);

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void init() {


    }

    public void update() {
        bg.update();
    }

    public void draw(Graphics2D g) {

        // draw bg
        bg.draw(g);

        // draw title
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("Flying Squirrel", 60, 70);

        // draw menu options
        g.setFont(font);

        for(int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(new Color(210, 86, 21, 215));
            } else {
                g.setColor(new Color(8, 14, 16));
            }
            g.drawString(options[i], 145, 140 + i * 15);
        }

    }

    private void select() {
        if(currentChoice == 0) {
            gsm.setState(GameStateManager.LEVEL1STATE);
        }
        if(currentChoice == 1) {
            gsm.setState(GameStateManager.HEPSTATE);
        }
        if(currentChoice == 2) {
            gsm.setState(GameStateManager.SETTINGSTATE);
        }
        if(currentChoice == 3) {
            System.exit(0);
        }
    }

    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ENTER){
            select();
        }
        if(k == KeyEvent.VK_UP) {
            currentChoice--;
            if(currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if(k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if(currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }
    public void keyReleased(int k) {

    }

}










