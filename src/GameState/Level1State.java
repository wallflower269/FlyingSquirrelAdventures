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
	private ArrayList<Explosion> explosions;

	private HUD hud;

	private AudioPlayer bgMusic;

	private Teleport teleport;  // Add teleport
	// private ArrayList<Teleport> teleports;

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

		// player = new Player(tileMap , gsm) {
		// 	@Override
		// 	public boolean dead() {
		// 		return false;
		// 	}
		// };

		player = new Player(tileMap , gsm) {};
		player.setPosition(100, 100);

		populateEnemies();

		explosions = new ArrayList<Explosion>();

		hud = new HUD(player);

		bgMusic = new AudioPlayer("/Music/level1-1.mp3");
		bgMusic.play();

		//thêm code:
		// Initialize and set the position of the teleport
		teleport = new Teleport(tileMap);
		// Khởi tạo Teleport với toạ độ mục tiêu và GameStateManager
		// teleport = new Teleport(tileMap, 226,196, gsm); // khởi chạy class Teleport.java1
		teleport.setPosition(150, 190);  // vị trí của Teleport

		//

	}


	private void checkPlayerStatus() {
		if (player.isDead() == true) {  // Giả sử isDead() kiểm tra nếu trạng thái dead là true
			gsm.setState(GameStateManager.GAMEOVERSTATE);
			// gsm.setState(GameStateManager.WINNERSTATE);
		}

		// thêm code:
		// if (player.intersects(teleport)) {
		// 	gsm.setState(GameStateManager.WINNERSTATE);  // Change to WinnerState on teleport collision
		// }

		// Kiểm tra sự kiện chiến thắng
		// if (teleport.isPlayerCollided()) {
		// 	// Nếu người chơi va chạm với teleport, chuyển sang trạng thái WINNERSTATE
		// 	gsm.setState(GameStateManager.WINNERSTATE);
		// }
	}

	// thêm code
	// private void populateTeleport(){
	// 	//teleport
	// 	Point point = new Point(3140, 190);

	// 	Teleport teleport = new Teleport(tileMap);
	// 	teleport.setPosition(point.x, point.y);


	// 	// teleport.add(teleport);

	// }

	//

	private void populateEnemies() {

		enemies = new ArrayList<Enemy>();
		//thêm
		//

		Slugger s;
		Point[] points = new Point[] {
				new Point(200, 100),
				new Point(860, 200),
				new Point(1525, 200),
				new Point(1680, 200),
				new Point(1800, 200)
		};
		for(int i = 0; i < points.length; i++) {
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}

	}

	public void update() {

		// update player
		player.update();
		teleport.update();

		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety()
		);

		// set background
		bg.setPosition(tileMap.getx(), tileMap.gety());

		// attack enemies
		player.checkAttack(enemies);
		checkPlayerStatus();

		// update all enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(
						new Explosion(e.getx(), e.gety()));
				//populateTeleport();
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

		// them code
		if (teleport != null) {
			teleport.update();
		}

		// Check if player is at the special position 3140, 190 < chuyển trạng thái -> Win
		if (player.isAtPosition(150, 196)) {
			gsm.setState(GameStateManager.WINNERSTATE);
		}

		if (player.getHealth() <= 0) {
			gsm.setState(GameStateManager.GAMEOVERSTATE);
			// gsm.setState(GameStateManager.WINNERSTATE); // test trạng thái
		}

		// them code
		// Kiểm tra sự kiện chiến thắng
		// if (teleport.isPlayerCollided()) {
		// 	// Nếu người chơi va chạm với teleport, chuyển sang trạng thái WINNERSTATE
		// 	gsm.setState(GameStateManager.WINNERSTATE);
		// }

		//them code

		// // Update Teleport
		// if (teleport != null) {
		// 	teleport.update();
		// }

		// // Kiểm tra va chạm giữa Player và Teleport
		// if (player.intersects(teleport)) {
		// 	// Chuyển trạng thái sang WINNERSTATE
		// 	gsm.setState(GameStateManager.WINNERSTATE);
		// }

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

		//them code
		if (teleport != null) {
			teleport.draw(g);
		}
		// draw teleport
		// teleport.draw(g);

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












