// Main/Game.java
package Main;

import Audio.AudioPlayer;
import GameState.ControlCenter;
import GameState.GameSettings;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import static Audio.AudioPlayer.*;

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
		GamePanel gamePanel = new GamePanel();
		window.setContentPane(gamePanel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);   // screen full windown
		window.setIconImage(icon); // Set the new icon
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		// Initialize background music
		bgMusic = new AudioPlayer("Resources\\Music\\a-little-quirky-167769.wav");
		if (GameSettings.isSoundOn()) {
			playMusic();
		} else {
			stopMusic();
		}

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