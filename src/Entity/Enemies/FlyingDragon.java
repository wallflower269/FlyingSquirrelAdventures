package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;
import Entity.Enemy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
public class FlyingDragon extends Enemy{

    private BufferedImage[] sprites;

    public FlyingDragon(TileMap tm) {

        super(tm);

        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 40;
        height = 31;
        cwidth = 15;
        cheight = 15;

        health = maxHealth = 2;
        damage = 1;

        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Enemies/flyingdragon.gif"
                    )
            );

            sprites = new BufferedImage[8];
            for(int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        (int) (i * width),
                        0,
                        (int) width,
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
    }

    public void update() {

        // update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        // check flinching
        if(flinching) {
            long elapsed =
                    (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 400) {
                flinching = false;
            }
        }

        // if it hits a wall, go other direction
        if(right && dx == 0) {
            right = false;
            left = true;
            facingRight = false;
        }
        else if(left && dx == 0) {
            right = true;
            left = false;
            facingRight = true;
        }

        // update animation
        animation.update();

    }

    public void draw(Graphics2D g) {

        //if(notOnScreen()) return;

        setMapPosition();

        super.draw(g);

    }
}
