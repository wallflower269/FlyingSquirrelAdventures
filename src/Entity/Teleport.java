package Entity;

import TileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.ImageReader;
import java.util.ArrayList;
import java.util.Iterator;

public class Teleport extends MapObject {

    private ArrayList<BufferedImage> sprites;

    public Teleport(TileMap tm) {
        super(tm);
        facingRight = true;
        width = height = 40;
        cwidth = 20;
        cheight = 40;
        try {
            File gifFile = new File("Resources/Sprites/tele.gif");
            sprites = loadGIF(gifFile);

            animation = new Animation();
            animation.setFrames(sprites.toArray(new BufferedImage[0]));
            animation.setDelay(100L);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void update() {
        animation.update();
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);
    }

    public Rectangle getRectangle() {
        return new Rectangle(
            (int)(x - cwidth / 2),
            (int)(y - cheight / 2),
            cwidth,
            cheight
        );
    }

    private ArrayList<BufferedImage> loadGIF(File file) throws IOException {
        ArrayList<BufferedImage> frames = new ArrayList();
        ImageReader reader = null;
        ImageInputStream stream = null;

        try {
            stream = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
            if (readers.hasNext()) {
                reader = readers.next();
                reader.setInput(stream);

                int numFrames = reader.getNumImages(true);
                for (int i = 0; i < numFrames; i++) {
                    BufferedImage frame = reader.read(i);
                    frames.add(frame);
                }
            }
        } finally {
            if (reader != null) reader.dispose();
            if (stream != null) stream.close();
        }

        return frames;
    }

    @Override
    public boolean dead() {
        return false;
    }
}
