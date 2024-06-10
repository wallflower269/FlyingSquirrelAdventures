package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class HelpState implements GameState {
    private GameStateManager gsm;

    private BufferedImage background;

    private String[] options = {"Back"};
    private int currentChoice = 0;

    public HelpState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    public void init() {

        try {
            background = ImageIO.read(getClass().getResourceAsStream("/Image_icon/n3.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading background image.");
        }
    }

    public void update() {
    }

    public void draw(Graphics2D g) {
        if (background != null) {
            g.drawImage(background, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        }
        g.setFont(new Font("Press Start 2P ", Font.BOLD, 11));
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(new Color(19, 19, 24, 215));
            }
            g.drawString(options[i], 278, 16);
        }
    }

    private void select() {
        String selectedOption = options[currentChoice];
        if (selectedOption.equals("Back")) {
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            select();
        }
    }

    public void keyReleased(int k) {

    }
}



/*
Link: https://www.freepik.com/free-vector/game-wooden-interface-elements-set_9509858.htm#query=game%20menu%20background&position=0&from_view=keyword&track=ais_user&uuid=96ae9222-66e9-40dc-aef2-faa896470fa7
https://www.canva.com/design/DAGG7HYO3AI/MUBtewbm-oXGn3RlEB5G8Q/edit?ui=eyJEIjp7IkoiOnsiQiI6eyJBPyI6IkIifX19LCJBIjp7IkEiOiJkb3dubG9hZF9wbmciLCJGIjp0cnVlfSwiRyI6eyJEIjp7IkQiOnsiQT8iOiJBIiwiQSI6IkIifX19fQ
* */