package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Insect extends Enemy {
    private BufferedImage[] sprites;

    public Insect(TileMap tm) {
        super(tm);

        moveSpeed = 1;
        maxSpeed = 1;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        // Adjusting width and height to match the desired subimage size
        width = 32;
        height = 36;
        cwidth = 20;
        cheight = 20;

        health = maxHealth = 1;
        damage = 1;

        // Load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/burt.gif"));

            /* Minh: Need to scale?
            sprites = new BufferedImage[4];

            for(int i = 0; i < sprites.length; i++) {
                // Scale the subimage to match the desired size
                sprites[i] = scaleImage(spritesheet.getSubimage(i * 94, 0, 94, 72), width, height);
            }

             */
            sprites = new BufferedImage[8];
            for(int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(100);

        right = true;
        facingRight = true;
    }

    private BufferedImage scaleImage(BufferedImage image, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return scaledImage;
    }

    private void getNextPosition() {
        // movement
        if(left) {
            dx -= moveSpeed;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        }
        else if(right) {
            dx += moveSpeed;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        }

        // falling
        if(falling) {
            dy += fallSpeed;
        }
    }

    public void update() {
        // update position
        getNextPosition();
        checkTileMapCollision();

        calculateCorners(x, ydest + 1);
        if(!bottomLeft) {
            left = false;
            right = facingRight = true;
        }
        if(!bottomRight) {
            left = true;
            right = facingRight = false;
        }

        setPosition(xtemp, ytemp);
        if(dx == 0) {
            left = !left;
            right = !right;
            facingRight = !facingRight;
        }

        // check flinching
        if(flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 400) {
                flinching = false;
            }
        }

        // update animation
        animation.update();
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);
    }
}
