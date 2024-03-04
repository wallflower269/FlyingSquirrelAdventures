import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;

public final class sound {

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
public class GamePanel extends JPanel implements Runnable, KeyListener {
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;

    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime;
    private BufferedImage image;
    private Graphics2D g;
    private GameStateManager gsm;
    private sound sound;

    public GamePanel() {
        this.targetTime = (long)(1000 / this.FPS);
        this.setPreferredSize(new Dimension(640, 480));
        this.setFocusable(true);
        this.requestFocus();
        playMusic(0);
    }

    public void addNotify() {
        super.addNotify();
        if (this.thread == null) {
            this.thread = new Thread(this);
            this.addKeyListener(this);
            this.thread.start();
        }
    }

    private void init() {
        this.image = new BufferedImage(320, 240, 1);
        this.g = (Graphics2D)this.image.getGraphics();
        this.running = true;
        this.gsm = new yGameStateManager();
    }

    public void run() {
        this.init();

        while(this.running) {
            long start = System.nanoTime();
            this.update();
            this.draw();
            playMusic(0);
            this.drawToScreen();
            long elapsed = System.nanoTime() - start;
            long wait = this.targetTime - elapsed / 1000000L;
            if (wait < 0L) {
                wait = 5L;
            }

            try {
                Thread.sleep(wait);
            } catch (Exception var8) {
                var8.printStackTrace();
            }
        }
    }

    private void update() {
        this.gsm.update();
    }

    private void draw() {
        this.gsm.draw(this.g);
    }

    private void drawToScreen() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(this.image, 0, 0, 640, 480, (ImageObserver)null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent key) {
    }

    public void keyPressed(KeyEvent key) {
        this.gsm.keyPressed(key.getKeyCode());
    }

    public void keyReleased(KeyEvent key) {
        this.gsm.keyReleased(key.getKeyCode());
    }

    // Phát âm thanh khi cần thiết
    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    // Dừng âm thanh khi cần thiết
    public void stopMusic() {
        sound.stop();
    }
    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

}

