// Audio/AudioPlayer.java
package Audio;

import GameState.ControlCenter;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

	private Clip clip;

	public AudioPlayer(String s) {
		try {
			File file = new File(s);
			if (!file.exists()) {
				throw new RuntimeException("Resource not found: " + s);
			}
			AudioInputStream ais = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		if (ControlCenter.isAudioOn) {
			if (clip == null) return;
			stop();
			clip.setFramePosition(0);
			clip.start();
		}
	}

	public void stop() {
		if (clip.isRunning()) clip.stop();
	}

	public void close() {
		stop();
		clip.close();
	}

	public void loop() {
		if (clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}
