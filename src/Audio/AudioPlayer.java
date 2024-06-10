package Audio;
import javax.sound.sampled.*;
import java.io.File;
public class AudioPlayer {
	private static boolean soundOn = true;
	private static boolean soundOff = false;

	public static boolean isSoundOn() {
		return soundOn;
	}
	public static boolean isSoundOff() {
		return soundOff;
	}


	public static void setSoundOn(boolean soundOn) {
		AudioPlayer.soundOn = soundOn;
	}
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
		if (AudioPlayer.isSoundOn()) {
			if (clip == null) return;
			stop();
			clip.setFramePosition(0);
			clip.start();
		}
	}

	public void stop() {
		if (clip.isRunning()) clip.stop();
	}

	public void loop() {
		if (clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}