package main.java.crabGame.view;

import main.java.crabGame.model.*;
import main.java.menu.enums.IMAGES;
import main.java.menu.view.ImageManager;
import main.java.menu.view.MenuScreen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Alina on 2017-07-24.
 */
public class WorldRenderer {
	private final static int NQF = 5; // number of non-active quest frames
	static BufferedImage[] crab = ImageManager.arrayPopulator(IMAGES.BLUE_CRAB, 3);
	static BufferedImage[] crabInvincible = ImageManager.arrayPopulator(IMAGES.BLUE_CRAB_INVINCIBLE, 3);
	static BufferedImage[] trail = ImageManager.arrayPopulator(IMAGES.TRAIL, 7);
	static BufferedImage[] enemies = ImageManager.arrayPopulator(IMAGES.BASS, NQF);
	static BufferedImage[] bgs = ImageManager.arrayPopulator(IMAGES.CRAB_BG, 4);
	// need to scale panels so they fill height, then find how many of those fill
	// the screen
	static int numBgPanels = MenuScreen.frameWidth / (bgs[0].getWidth() * (MenuScreen.frameHeight / bgs[0].getHeight()))
			+ 1;
	static int[] bgPanels = new int[numBgPanels];
	static Random rand = new Random();

	/**
	 * Draws a CrabGamePanel's game, should call all other draw methods
	 */
	public static void render(Graphics g) {
		// System.out.println("WorldRenderer: " + game);

		drawBackground(g);
		drawCrabbies(CrabGameWorld.crabbies, g);
		drawEnemies(CrabGameWorld.enemies, g);
		drawFriends(CrabGameWorld.friends, g);
		drawSaltClouds(CrabGameWorld.saltClouds, g);

	}

	/**
	 * Draws the background
	 *
	 * @param g
	 */
	static void drawBackground(Graphics g) {

		g.drawImage(MenuScreen.IMAGE.get(IMAGES.CRAB_BG), 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight, null);

	}

	static void drawCrabbies(ArrayList<Crabby> crabbies, Graphics g) {
		for (Crabby crabby : crabbies) {
			drawCrabby(crabby, g);
		}

	}

	/**
	 * Draws a given crabbies
	 *
	 * @param crabby
	 * @param g
	 */
	static void drawCrabby(Crabby crabby, Graphics g) {
		// System.out.println("drawCrabby: " + crabby);

//		g.setColor(Color.CYAN);
//		fillBounds(crabby.getBounds(), g);
//
//		g.setColor(Color.BLACK);
//		drawBounds(crabby.getBounds(), g);
//
//		g.setColor(Color.BLACK);
//		g.drawRect(crabby.getxPos(), crabby.getyPos(), 1, 1);
//		g.drawRect(crabby.getxPos(), crabby.getyPos() + crabby.getBounds().height, 1, 1);
//		g.drawRect(crabby.getxPos() + crabby.getBounds().width, crabby.getyPos(), 1, 1);
//		g.drawRect(crabby.getxPos() + crabby.getBounds().width, crabby.getyPos() + crabby.getBounds().height, 1, 1);

		if (crabby.getState() == Mover.State.SAFE) {
			drawImageBounds(crabby.getBounds(), g, crabInvincible[crabby.getFrame()]);
		} else {
			drawImageBounds(crabby.getBounds(), g, crab[crabby.getFrame()]);
		}
		
		// draw a velocity vector on top of crab
		g.setColor(Color.red);
		Point cCenter = new Point();
		cCenter.x = (int) (crabby.getxPos() + crabby.getBounds().getWidth() / 2);
		cCenter.y = (int) (crabby.getyPos() + crabby.getBounds().getHeight() / 2);
		g.drawLine(cCenter.x, cCenter.y, (int) (cCenter.x + crabby.getxVel() * 400.0), (int) (cCenter.y + crabby.getyVel() * 400.0));

	}

	static void fillBounds(Rectangle bounds, Graphics g) {
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	static void drawBounds(Rectangle bounds, Graphics g) {
		g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	static void drawImageBounds(Rectangle bounds, Graphics g, Image image) {
		g.drawImage(image, bounds.x, bounds.y, bounds.width, bounds.height, null);
	}

	/**
	 * Draws a given list of Friends and the facts that follow them
	 *
	 * @param g
	 */
	static void drawFriends(ArrayList<Friend> friends, Graphics g) {
		System.out.println("drawFriends: " + friends);
		for (Friend f : friends) {
			drawFriend(f, g);
			// drawFact(f, g);
		}
	}

	/**
	 * Draws a friend
	 *
	 * @param friend
	 * @param g
	 */
	static void drawFriend(Friend friend, Graphics g) {
		// System.out.println("drawFriend: " + friend );

		g.setColor(Color.PINK);
		fillBounds(friend.getBounds(), g);

		g.setColor(Color.BLACK);
		drawBounds(friend.getBounds(), g);

		// g.drawImage(
		// MainMenuScreen.IMAGE.get(IMAGES.BOGTURTLE0.path),
		// friend.getBounds().x,
		// friend.getBounds().y,
		// this);
	}

//	/**
//	 * Draws a fact following a given friend
//	 *
//	 * @param friend
//	 * @param g
//	 */
//	void drawFact(Friend friend, Graphics g) {
//		g.setColor(Color.BLACK);
//		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(), 30));
//		friend.setTextSize(g.getFontMetrics().stringWidth(Friend.facts[friend.getFriendCounter()]));
//		g.drawString(
//				Friend.facts[friend.getFriendCounter()],
//				(int) (friend.getBounds().getX() + friend.getBounds().getWidth()),
//				(int) friend.getBounds().getY());
//	}

	/**
	 * Draw a given salt cloud
	 */
	static void drawSaltCloud(SaltCloud saltCloud, Graphics g) {
//		Color FESTIVAL = new Color(239, 211, 70, 127);
//		g.setColor(FESTIVAL);
//		fillBounds(saltCloud.getBounds(), g);
//		g.setColor(Color.BLACK);
//		drawBounds(saltCloud.getBounds(), g);

		drawImageBounds(saltCloud.getBounds(), g, trail[0]);
	}

	/**
	 * Draw a given list of saltClouds
	 *
	 * @param saltClouds
	 * @param g
	 */
	static void drawSaltClouds(ArrayList<SaltCloud> saltClouds, Graphics g) {
		// System.out.println("drawSaltClouds: " + saltClouds);
		for (SaltCloud s : saltClouds) {
			drawSaltCloud(s, g);
		}
	}

	/**
	 * Draw a given enemy
	 *
	 * @param enemy
	 * @param g
	 */
	static void drawEnemy(Enemy enemy, Graphics g) {
		// System.out.println("drawEnemy: " + enemy);
//		g.setColor(Color.RED);
//		fillBounds(enemy.getBounds(), g);
//		g.setColor(Color.BLACK);
//		drawBounds(enemy.getBounds(), g);

		drawImageBounds(enemy.getBounds(), g, enemies[enemy.getFrame()]);
	}

	/**
	 * Draw a given list of enemies
	 *
	 * @param enemies
	 * @param g
	 */
	static void drawEnemies(ArrayList<Enemy> enemies, Graphics g) {
		// System.out.println("drawEnemies: " + enemies);
		for (Enemy e : enemies) {
			drawEnemy(e, g);
		}
	}

}
