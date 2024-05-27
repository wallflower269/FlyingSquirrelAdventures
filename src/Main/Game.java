// Main/Game.java
package Main;

import Audio.AudioPlayer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game {
	private static AudioPlayer bgMusic;
	
	private static BufferedImage icon;


	public static void main(String[] args) {

		String path = "/Image_icon/icon.jpg"; // Đường dẫn tương đối trong thư mục tài nguyên
        try {
            // Sử dụng Game.class để đọc tệp
            icon = ImageIO.read(Game.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Could not find the resource: " + path);
        }

		JFrame window = new JFrame("Flying Squirrel Adventure");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setIconImage(icon); // Set the new icon
		window.pack();
		window.setVisible(true);

		// Initialize background music
		bgMusic = new AudioPlayer("D:\\Users\\ttnhu\\Desktop\\PDM\\FlyingSquirrelAdventures\\Resources\\Music\\Salt-Marsh-Birds.wav");
		bgMusic.loop();
	}

	public static void stopMusic() {
		if (bgMusic != null) {
			bgMusic.stop();
		}
	}

	public static void playMusic() {
		if (bgMusic != null) {
			bgMusic.loop();
		}
	}
}
