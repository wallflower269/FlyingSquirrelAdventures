package Main;
import Audio.AudioPlayer;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game {
	private static AudioPlayer bgMusic;

	private static BufferedImage icon;


	public static void main(String[] args) {

		String path = "/Image_icon/icon.jpg";
		try {
			icon = ImageIO.read(Game.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();}

		JFrame window = new JFrame("Flying Squirrel Adventure");
		GamePanel gamePanel = new GamePanel();
		window.setContentPane(gamePanel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setIconImage(icon);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		bgMusic = new AudioPlayer("Resources\\Music\\a-little-quirky-167769.wav");
		if (AudioPlayer.isSoundOn()) {
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