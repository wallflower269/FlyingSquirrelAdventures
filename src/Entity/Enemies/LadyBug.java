package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
public class LadyBug extends Enemy {
    private BufferedImage[] sprites;

    private int jumpHeight;
    private int maxJumpHeight;

    public LadyBug(TileMap tm) {

        super(tm);

        moveSpeed = 1.5;
        maxSpeed = 1.0;
        fallSpeed = 0.6;
        maxFallSpeed = 4.0;
        jumpStart = -16;
        stopJumpSpeed = 0.5;
        maxJumpHeight = (int)y + 200;

        width = 19;
        height = 16;
        cwidth = 10;
        cheight = 10;

        health = maxHealth = 2;
        damage = 1;

        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Enemies/ladybug.gif"
                    )
            );

            sprites = new BufferedImage[4];
            for(int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        up = true;
//        facingRight = true;

    }

    private void getNextPosition() {

        // movement
        if (up) {
            if (!jumping && !falling) {
                dy = jumpStart;
                jumping = true;
                jumpHeight = (int) y;
            }

            if (jumping && dy < 0) {
                dy += stopJumpSpeed; // reduce jump speed when the key is released
            }

            if (jumping && dy >= 0) {
                jumping = false;
                falling = true;
            }
        }

        // falling
        if (falling) {
            dy += fallSpeed;

            if (dy > maxFallSpeed) {
                dy = maxFallSpeed;
            }
        }
    }

    public void update() {

        // update position
        getNextPosition();
        checkTileMapCollision();

        calculateCorners(x, ydest + 1);
        if (!topLeft && topRight) {
            up = false;
        }
        if (!bottomLeft && bottomRight) {
            up = true;
        }

        setPosition(xtemp, ytemp);
        if (dy == 0) {
            up = !up;
            down = !down;
        }
        // check flinching
        if(flinching) {
            long elapsed =
                    (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 400) {
                flinching = false;
            }
        }

        // update animation
        animation.update();

    }

    public void draw(Graphics2D g) {

        //if(notOnScreen()) return;

        setMapPosition();
        setMapPosition();

        super.draw(g);

    }
}
