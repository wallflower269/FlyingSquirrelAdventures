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
        Game.playMusic();
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/grasstileset.gif");
        tileMap.loadMap("/Maps/level2-2.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(1);

        bg = new Background("/Backgrounds/moonbg2.gif", 0.1);

        player = new Player(tileMap, gsm) {
            @Override
            public boolean dead() {
                return false;
            }
        };
        player.setPosition(100, 100);

        populateEnemies();

        explosions = new ArrayList<Explosion>();

        hud = new HUD(player);

        // Initialize and set the position of the teleport
        teleport = new Teleport(tileMap);
        teleport.setPosition(3127, 150);
    }

    private void checkPlayerStatus() {
        if (player.dead()) {
            gsm.setState(GameStateManager.GAMEOVERSTATE);
            Game.stopMusic();
        }
    }

    private void checkForWin() {
        if (player.getx() >= 3127) {
            gsm.setState(GameStateManager.WINNERSTATE);
        }
    }

    private void populateEnemies() {
        enemies = new ArrayList<Enemy>();

        Slugger s;
        Point[] sluggerPoints = new Point[]{
                new Point(936, 106),
                new Point(1427, 160),
                new Point(1723, 160)
        };
        for (Point point : sluggerPoints) {
            s = new Slugger(tileMap);
            s.setPosition(point.x, point.y);
            enemies.add(s);
        }

        Worm w;
        Point[] wormPoints = new Point[]{
                new Point(1900, 160),
                new Point(2416, 160),
                new Point(2630, 160)
        };
        for (Point point : wormPoints) {
            w = new Worm(tileMap);
            w.setPosition(point.x, point.y);
            enemies.add(w);
        }

        Parrot p;
        Point[] parrotPoints = new Point[]{
                new Point(980, 73),
                new Point(1886, 110)
        };
        for (Point point : parrotPoints) {
            p = new Parrot(tileMap);
            p.setPosition(point.x, point.y);
            enemies.add(p);
        }

        Bat b;
        Point[] batPoints = new Point[]{
                new Point(2530, 102),
                new Point(2770, 105)
        };
        for (Point point : batPoints) {
            b = new Bat(tileMap);
            b.setPosition(point.x, point.y);
            enemies.add(b);
        }

        Goblin g;
        Point[] goblinPoints = new Point[]{
                new Point(650, 160),
                new Point(1843, 106)
        };
        for (Point point : goblinPoints) {
            g = new Goblin(tileMap);
            g.setPosition(point.x, point.y);
            enemies.add(g);
        }

        LadyBug l;
        Point[] ladybugPoints = new Point[]{
                new Point(665, 160),
                new Point(1860, 106),
                new Point(2685, 160)
        };
        for (Point point : ladybugPoints) {
            l = new LadyBug(tileMap);
            l.setPosition(point.x, point.y);
            enemies.add(l);
        }
    }

    public void update() {
        player.update();
        teleport.update();

        tileMap.setPosition(
                GamePanel.WIDTH / 2 - player.getx(),
                GamePanel.HEIGHT / 2 - player.gety()
        );

        bg.setPosition(tileMap.getx(), tileMap.gety());

        player.checkAttack(enemies);

        checkPlayerStatus();
        checkForWin();

        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if (e.isDead()) {
                enemies.remove(i);
                i--;
                explosions.add(new Explosion(e.getx(), e.gety()));
            }
        }

        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if (explosions.get(i).shouldRemove()) {
                explosions.remove(i);
                i--;
            }
        }

        if (player.getHealth() <= 0) {
            gsm.setState(GameStateManager.GAMEOVERSTATE);
        }

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
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        // draw explosions
        for (Explosion explosion : explosions) {
            explosion.setMapPosition((int) tileMap.getx(), (int) tileMap.gety());
            explosion.draw(g);
        }

        // Teleport
        if (teleport != null) {
            teleport.draw(g);
        }

        // draw hud
        hud.draw(g);
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_LEFT) player.setLeft(true);
        if (k == KeyEvent.VK_RIGHT) player.setRight(true);
        if (k == KeyEvent.VK_UP) player.setUp(true);
        if (k == KeyEvent.VK_DOWN) player.setDown(true);
        if (k == KeyEvent.VK_W) player.setJumping(true);
        if (k == KeyEvent.VK_E) player.setGliding(true);
        if (k == KeyEvent.VK_R) player.setScratching();
        if (k == KeyEvent.VK_F) player.setFiring();
    }

    public void keyReleased(int k) {
        if (k == KeyEvent.VK_LEFT) player.setLeft(false);
        if (k == KeyEvent.VK_RIGHT) player.setRight(false);
        if (k == KeyEvent.VK_UP) player.setUp(false);
        if (k == KeyEvent.VK_DOWN) player.setDown(false);
        if (k == KeyEvent.VK_W) player.setJumping(false);
        if (k == KeyEvent.VK_E) player.setGliding(false);
    }
}
