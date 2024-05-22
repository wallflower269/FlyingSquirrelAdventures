package GameState;

import Main.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WinnerState extends GameState {
    private BufferedImage background;

    public WinnerState(GameStateManager gsm) {
        super.gsm = gsm;


        init();
    }

    @Override
    public void init() {
        Game.stopMusic();

        try {
            // Đường dẫn tới hình nền, thay đổi nếu chạy từ môi trường khác nhau có thể cần chỉnh sửa
            background = ImageIO.read(
                    getClass().getResourceAsStream("/Backgrounds/menubg.gif"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading background image.");
        }

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        // Đảm bảo hình nền được vẽ trước tiên
        if (background != null) {
            g.drawImage(background, 0, 0, null);
        } else {
            // Nếu không tải được hình nền, sử dụng màu đen làm hình nền
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 320, 240); // Giả sử kích thước màn hình là 320x240
        }

        // Đặt màu và font cho chữ "Game Over"
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("Winner", 90, 120); // Căn giữa màn hình
    }


    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {

            gsm.setState(GameStateManager.MENUSTATE);
            Game.playMusic();
        }
    }

    @Override
    public void keyReleased(int k) {}
}

