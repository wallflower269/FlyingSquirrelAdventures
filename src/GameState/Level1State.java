package GameState;

import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Enemies.*;
import Audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level1State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;

//	private ArrayList<DieEnemy> dieEnemies;
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	
	private AudioPlayer bgMusic;
	
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(100, 100);

//		dieEnemies = new ArrayList<>();
		populateEnemies();
		
		explosions = new ArrayList<Explosion>();

		hud = new HUD(player);
		
		bgMusic = new AudioPlayer("/Music/level1-1.mp3");
		bgMusic.play();
		
	}
	
	private void populateEnemies() {
		
		enemies = new ArrayList<>();
		
		Slugger s;
		Point[] point1 = new Point[] {
			new Point(200, 100),
			new Point(830, 200),
			new Point(960, 200),
			new Point(1550, 200)
//			new Point(1800, 200)
		};
		for(int i = 0; i < point1.length; i++) {
			s = new Slugger(tileMap);
			s.setPosition(point1[i].x, point1[i].y);
			enemies.add(s);
		}

		Dog d;
		Point[] point2 = new Point[] {
				new Point(250, 100),
				new Point(860, 200),
				new Point(1525, 200),
				new Point(1680, 200),
				new Point(1800, 200),
				new Point(2400, 200)
		};
		for(int i = 0; i < point2.length; i++) {
			d = new Dog(tileMap);
			d.setPosition(point2[i].x, point2[i].y);
			enemies.add(d);
		}

		Bat b;
		Point[] point4 = new Point[] {
				new Point(100, 50),
				new Point(860, 155),
//				new Point(960, 100),
//				new Point(2500, 70)
		};
		for(int i = 0; i < point4.length; i++) {
			b = new Bat(tileMap);
			b.setPosition(point4[i].x, point4[i].y);
			enemies.add(b);
		}

		Arachnik a;
		Point[] point3 = new Point[] {
				new Point(1000, 200),
				new Point(1400, 65),
				new Point(1625, 200),
				new Point(2400, 200)
		};
		for(int i = 0; i < point3.length; i++) {
			a = new Arachnik(tileMap);
			a.setPosition(point3[i].x, point3[i].y);
			enemies.add(a);
		}

		FlyingDragon f;
		Point[] point5 = new Point[] {
//				new Point(100, 50),
//				new Point(860, 155),
				new Point(960, 100),
				new Point(2500, 70)
		};
		for(int i = 0; i < point5.length; i++) {
			f = new FlyingDragon(tileMap);
			f.setPosition(point5[i].x, point5[i].y);
			enemies.add(f);
		}
	}
	
	public void update() {
		
		// update player
		player.update();
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		
		// set background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		// attack enemies
		player.checkAttack(enemies);
		
		// update all enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
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












