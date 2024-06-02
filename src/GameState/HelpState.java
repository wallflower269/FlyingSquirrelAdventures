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

    private Font font; // Font cho đoạn văn bản
    private Color textColor; // Màu sắc cho đoạn văn bản

    // private String[] options = {"Back","0"};
    private String[] options = {"Back"};
    private int currentChoice = 0;

    public HelpState(GameStateManager gsm) {
        // Khởi tạo đoạn văn bản và các thuộc tính liên quan
        this.gsm = gsm;
        init();
    }

    public void init() {
        // Game.playMusic();

        try {
            // Đường dẫn tới hình nền, thay đổi nếu chạy từ môi trường khác nhau có thể cần chỉnh sửa
            background = ImageIO.read(getClass().getResourceAsStream("/Image_icon/n3.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading background image.");
        }

        // font = new Font("Arial", Font.BOLD | Font.ITALIC, 18); // Font in đậm và nghiêng
        // textColor = Color.WHITE;
    }

    public void update() {
        // Cập nhật trạng thái, nếu cần
    }

    public void draw(Graphics2D g) {
        // Đảm bảo hình nền được vẽ trước tiên
        if (background != null) {
            g.drawImage(background, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        }

        // Vẽ đoạn văn bản vào cửa sổ help với hiệu ứng đổ bóng
        // g.setFont(font);

        // Vẽ tùy chọn
        g.setFont(new Font("Arial ", Font.BOLD, 11));
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
            gsm.setState(GameStateManager.MENUSTATE); // Thoát game
        }
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_BACK_SPACE) {
            select();
        }
    }

    public void keyReleased(int k) {
        // Xử lý sự kiện khi có phím được thả ra, có thể không cần thiết
    }
}
