package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
public class Dog extends Enemy{
    private BufferedImage[] sprites;

    public Dog(TileMap tm) {

        super(tm);

        moveSpeed = 0.7;
        maxSpeed = 0.3;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 30;
        height = 45;
        cwidth = 20;
        cheight = 20;

        health = maxHealth = 2;
        damage = 1;

        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Enemies/dog.gif"
                    )
            );

            sprites = new BufferedImage[1];
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
        animation.setDelay(100);

        right = true;
        facingRight = true;

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

//        if(notOnScreen()) return;

        setMapPosition();

        super.draw(g);

    }
}
