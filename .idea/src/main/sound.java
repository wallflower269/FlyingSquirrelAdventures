package main;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
public class sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public sound() {

        soundURL[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/sound/dooropen.wav");
        soundURL[3] = getClass().getResource("/sound/gameover.wav");
        soundURL[4] = getClass().getResource("/sound/hitmonster.wav");
        soundURL[5] = getClass().getResource("/sound/levelup.wav");
    }
    public void setFile(int i) {
            try {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
                    clip = AudioSystem.getClip();
                    clip.open(ais);

            } catch (Exception e) {
        }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void close() {
        if (clip != null) {
            clip.close();
        }
    }
}
