package GameState;

import Main.Game;
import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Enemies.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Level1State implements GameState {
    private GameStateManager gsm;
// public class Level1State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	


	private Teleport teleport;  // local variable
	
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {
			if (GameSettings.isSoundOn()) {
				Game.playMusic();
			} else if (GameSettings.isSoundOff()) {
				Game.stopMusic();}
//			}else {
//				Game.playMusic();}


		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/m2.jpg", 0.1);

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

		// Initialize and set the position of the teleport
		teleport = new Teleport(tileMap);
		// Khởi tạo Teleport với toạ độ mục tiêu và GameStateManager
		teleport.setPosition(3105, 183);  // vị trí của Teleport
	//	 teleport.setPosition(150, 196);  // vị trí của Teleport test
		
	}

	// check chuyển trạng thái
	private void checkPlayerStatus() {
		if (player.dead() == true) {  // Giả sử isDead() kiểm tra nếu trạng thái dead là true
			gsm.setState(GameStateManager.GAMEOVERSTATE);

		}
	}

	// next level
	private void checkForWin() {
		// Assuming the end of the level is at x = 2000 (for example)
		if (player.getx() >= 3105) {
		//if (player.getx() >= 150) {
			// Transition to the next level
			gsm.setState(GameStateManager.WINNERSTATE);
		}
	}


	private void populateEnemies() {
		
		enemies = new ArrayList<Enemy>();

		Dog d;
		Point[] point1 = new Point[] {
//				new Point(250, 100),
				new Point(860, 200),
				new Point(1525, 200),
				new Point(1680, 200),
				new Point(1800, 200),
				new Point(2400, 200),
				new Point(3000,198)
		};
		for(int i = 0; i < point1.length; i++) {
			d = new Dog(tileMap);
			d.setPosition(point1[i].x, point1[i].y);
			enemies.add(d);
		}

		Insect ins;
		Point[] point2 = new Point[] {
//				new Point(200, 198),
				new Point(830, 198),
				new Point(960, 198),
				new Point(1550, 198),
//			new Point(1800, 200)
		};
		for(int i = 0; i < point2.length; i++) {
			ins = new Insect(tileMap);
			ins.setPosition(point2[i].x, point2[i].y);
			enemies.add(ins);
		}

		Arachnik a;
		Point[] point3 = new Point[] {
//				new Point(1000, 200),
				new Point(1400, 65),
				new Point(1625, 200),
				new Point(2400, 200),
				new Point(3000,198)
		};
		for(int i = 0; i < point3.length; i++) {
			a = new Arachnik(tileMap);
			a.setPosition(point3[i].x, point3[i].y);
			enemies.add(a);
		}

		Bird bi;
		Point[] point4 = new Point[] {
//				new Point(100, 50),
				new Point(860, 155),
				new Point(960, 100),
				new Point(2500, 70)
		};
		for(int i = 0; i < point4.length; i++) {
			bi = new Bird(tileMap);
			bi.setPosition(point4[i].x, point4[i].y);
			enemies.add(bi);
		}
	}

	public void update() {
		// Update player
		player.update();
		
		// update teleport
		teleport.update();


		// Set tilemap position
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety()
		);

		// Set background position
		bg.setPosition(tileMap.getx(), tileMap.gety());

		// Attack enemies
		player.checkAttack(enemies);

		// Check player status (e.g., health, dead)
		checkPlayerStatus();

		// Check if player has reached the end of the level
		checkForWin();

		// Update all enemies
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if (e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}

		// Update explosions
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if (explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}

		}

				// them code
		if (teleport != null) {
			teleport.update();
		}


		//Thuc Minh: For level 2
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












