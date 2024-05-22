// Main/Game.java
package Main;

import Audio.AudioPlayer;
import javax.swing.JFrame;

public class Game {
	private static AudioPlayer bgMusic;
	//

	public static void main(String[] args) {
		JFrame window = new JFrame("Flying Squirrel Adventure");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);

		// Initialize background music
		bgMusic = new AudioPlayer("Resources/Music/Juhani Junkala [Retro Game Music Pack] Title Screen.wav");
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
