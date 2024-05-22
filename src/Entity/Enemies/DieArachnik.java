//package Entity.Enemies;
//
//import Entity.DieEnemy;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//
//public class DieArachnik extends DieEnemy {
//
//    private BufferedImage[] sprites;
//
//    public DieArachnik(int x, int y) {
//        super(x, y);
//
//        this.width = 28;
//        this.height = 29;
//
//        try {
//
//            BufferedImage spritesheet = ImageIO.read(
//                    getClass().getResourceAsStream(
//                            "/Sprites/Enemies/diearachnik.gif"
//                    )
//            );
//
//            sprites = new BufferedImage[23];
//            for (int i = 0; i < sprites.length; i++) {
//                sprites[i] = spritesheet.getSubimage(
//                        i * width,
//                        0,
//                        width,
//                        height
//                );
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        animation.setFrames(sprites);
//        animation.setDelay(70L);
//    }
//}
