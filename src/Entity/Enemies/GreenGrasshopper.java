//package Entity.Enemies;
//
//import Entity.*;
//import TileMap.TileMap;
//import Entity.Enemy;
//import java.awt.image.BufferedImage;
//import java.awt.Graphics2D;
//
//import javax.imageio.ImageIO;
//public class GreenGrasshopper extends Enemy {
//    private BufferedImage[] sprites;
//
//    public GreenGrasshopper (TileMap tm) {
//
//        super(tm);
//
//        moveSpeed = 1.3;
//        maxSpeed = 1.3;
////        fallSpeed = 0.2;
////        maxFallSpeed = 10.0;
//
//        width = 20;
//        height = 20;
//        cwidth = 10;
//        cheight = 10;
//
//        health = maxHealth = 2;
//        damage = 1;
//
//        // load sprites
//        try {
//
//            BufferedImage spritesheet = ImageIO.read(
//                    getClass().getResourceAsStream(
//                            "/Sprites/Enemies/green-grassshopper.gif"
//                    )
//            );
//
//            sprites = new BufferedImage[1];
//            for(int i = 0; i < sprites.length; i++) {
//                sprites[i] = spritesheet.getSubimage(
//                        i * width,
//                        0,
//                        width,
//                        height
//                );
//            }
//
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }
//
//        animation = new Animation();
//        animation.setFrames(sprites);
//        animation.setDelay(300);
//
//        up = true;
//        facingRight = true;
//
//    }
//
//    private void getNextPosition() {
//
//        // movement
////        if(left) {
////            dx -= moveSpeed;
////            if(dx < -maxSpeed) {
////                dx = -maxSpeed;
////            }
////        }
////        else if(right) {
////            dx += moveSpeed;
////            if(dx > maxSpeed) {
////                dx = maxSpeed;
////            }
////        }
////
////        // falling
////        if(falling) {
////            dy += fallSpeed;
////        }
//
//        if (up) dy = -moveSpeed;
//        else if (down) dy = moveSpeed;
//        else dy = 0;
//    }
//
//    public void update() {
//
//        // update position
//        getNextPosition();
//        checkTileMapCollision();
//
//        calculateCorners(x, ydest + 1);
//        if (!topLeft && topRight) {
//            up = false;
//        }
//        if (!bottomLeft && bottomRight) {
//            up = true;
//        }
//
//        setPosition(xtemp, ytemp);
//
//        if (dy == 0) {
//            up = !up;
//            down = !down;
//        }
//        // check flinching
//        if(flinching) {
//            long elapsed =
//                    (System.nanoTime() - flinchTimer) / 1000000;
//            if(elapsed > 400) {
//                flinching = false;
//            }
//        }
//
//        // if it hits a wall, go other direction
////        if(right && dx == 0) {
////            right = false;
////            left = true;
////            facingRight = false;
////        }
////        else if(left && dx == 0) {
////            right = true;
////            left = false;
////            facingRight = true;
////        }
//
//        // update animation
//        animation.update();
//
//    }
//
//    public void draw(Graphics2D g) {
//
//        //if(notOnScreen()) return;
//
//        setMapPosition();
////        setMapPosition();
//
//        super.draw(g);
//
//    }
//}
