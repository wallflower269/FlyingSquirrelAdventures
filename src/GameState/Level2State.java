package GameState;

import Main.Game;
import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Enemies.*;
import Audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level2State extends GameState {

    private TileMap tileMap;
    private Background bg;

    private Player player;

    private ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;

    private HUD hud;

    private AudioPlayer bgMusic;

    private Teleport teleport;

    public Level2State(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    public void init() {

        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/grasstileset.gif");
        tileMap.loadMap("/Maps/level2-2.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(1);

        bg = new Background("/Backgrounds/moonbg2.gif", 0.1);

        player = new Player(tileMap , gsm) {
            @Override
            public boolean dead() {
                return false;
            }
        };
        player.setPosition(100, 100);

        populateEnemies();

        explosions = new ArrayList<Explosion>();

        hud = new HUD(player);

        bgMusic = new AudioPlayer("/Music/level1-1.mp3");
        bgMusic.play();

        // Initialize and set the position of the teleport
        teleport = new Teleport(tileMap);
        // Khởi tạo Teleport với toạ độ mục tiêu và GameStateManager
        teleport.setPosition(3127, 150);  // vị trí của Teleport
        // teleport.setPosition(142, 150);  // vị trí của Teleport

        //
    }


    private void checkPlayerStatus() {
        if (player.dead() == true) {  // Giả sử isDead() kiểm tra nếu trạng thái dead là true
            gsm.setState(GameStateManager.GAMEOVERSTATE);
            Game.stopMusic();
        }
    }


    private void checkForWin() {
		// Assuming the end of the level is at x = 2000 (for example)
		if (player.getx() >= 3127) {
		// if (player.getx() >= 143) {
			// Transition to the next level
			gsm.setState(GameStateManager.WINNERSTATE);
		}
	}

    private void populateEnemies() {

        enemies = new ArrayList<Enemy>();

        Slugger s;
        Point[] point1 = new Point[] {
                new Point(936, 106),
                new Point(1427, 160),
                new Point(1723, 160),
                new Point(1900, 160),
                new Point(2416, 160),
                new Point(2630, 160)
        };
        for(int i = 0; i < point1.length; i++) {
            s = new Slugger(tileMap);
            s.setPosition(point1[i].x, point1[i].y);
            enemies.add(s);
        }

        Bat b;
        Point[] point4 = new Point[] {
//                new Point(100, 50),
                new Point(980, 73),
                new Point(1886, 110),
//                new Point(2530, 102),
//                new Point(2770, 105)
        };
        for(int i = 0; i < point4.length; i++) {
            b = new Bat(tileMap);
            b.setPosition(point4[i].x, point4[i].y);
            enemies.add(b);
        }

//        FlyingDragon f;
//        Point[] point5 = new Point[] {
//				new Point(100, 50),
//				new Point(740, 165),
//                new Point(860, 155),
//
//        };
//        for(int i = 0; i < point5.length; i++) {
//            f = new FlyingDragon(tileMap);
//            f.setPosition(point5[i].x, point5[i].y);
//            enemies.add(f);
//        }

        Goblin g;
        Point[] point7 = new Point[] {
//                new Point(360, 160),
//                new Point(2400, 160),
                new Point(650, 160),
                new Point(1843, 106),
                new Point(2670, 160)
        };
        for(int i = 0; i < point7.length; i++) {
            g = new Goblin(tileMap);
            g.setPosition(point7[i].x, point7[i].y);
            enemies.add(g);
        }

//        Insect in;
//		Point[] point8 = new Point[] {
//				new Point(130, 150)
////				new Point(680, 160),
////				new Point(1550, 160),
////				new Point(1970, 166),
////				new Point(100, 160),
////				new Point(2500, 155)
////                new Point(2710, 160),
//		};
//		for(int i = 0; i < point8.length; i++) {
//			in = new Insect(tileMap);
//			in.setPosition(point8[i].x, point8[i].y);
//			enemies.add(in);
//		}

    }

    public void update() {

        // update player
        player.update();

        // UPDATE Teleport
        teleport.update();


        tileMap.setPosition(
                GamePanel.WIDTH / 2 - player.getx(),
                GamePanel.HEIGHT / 2 - player.gety()
        );

        // set background
        bg.setPosition(tileMap.getx(), tileMap.gety());

        // attack enemies
        player.checkAttack(enemies);

		// Check player status (e.g., health, dead)
		checkPlayerStatus();

		// Check if player has reached the end of the level
		checkForWin();

        // update all enemies
        for(int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if(e.isDead()) {
                enemies.remove(i);
                i--;
                explosions.add(
                        new Explosion(e.getx(), e.gety()));
            }
        }

        // update explosions
        for(int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if(explosions.get(i).shouldRemove()) {
                explosions.remove(i);
                i--;
            }
        }
        if (player.getHealth() <= 0) {
            gsm.setState(GameStateManager.GAMEOVERSTATE);

        }

        // Next win
        checkForWin();

    }

    public void draw(Graphics2D g) {

        // draw bg
        bg.draw(g);

        // draw tilemap
        tileMap.draw(g);

        // draw player
        player.draw(g);

        // draw enemies
        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }

        // draw explosions
        for(int i = 0; i < explosions.size(); i++) {
            explosions.get(i).setMapPosition(
                    (int)tileMap.getx(), (int)tileMap.gety());
            explosions.get(i).draw(g);
        }

		// Teleport
		if (teleport != null) {
			teleport.draw(g);
		}

        // draw hud
        hud.draw(g);

    }

    public void keyPressed(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(true);
        if(k == KeyEvent.VK_RIGHT) player.setRight(true);
        if(k == KeyEvent.VK_UP) player.setUp(true);
        if(k == KeyEvent.VK_DOWN) player.setDown(true);
        if(k == KeyEvent.VK_W) player.setJumping(true);
        if(k == KeyEvent.VK_E) player.setGliding(true);
        if(k == KeyEvent.VK_R) player.setScratching();
        if(k == KeyEvent.VK_F) player.setFiring();
    }

    public void keyReleased(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(false);
        if(k == KeyEvent.VK_RIGHT) player.setRight(false);
        if(k == KeyEvent.VK_UP) player.setUp(false);
        if(k == KeyEvent.VK_DOWN) player.setDown(false);
        if(k == KeyEvent.VK_W) player.setJumping(false);
        if(k == KeyEvent.VK_E) player.setGliding(false);
    }

}












