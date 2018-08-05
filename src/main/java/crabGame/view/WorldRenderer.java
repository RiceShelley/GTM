package main.java.crabGame.view;

import main.java.crabGame.CrabController;
import main.java.crabGame.model.*;
import main.java.menu.enums.IMAGES;
import main.java.menu.view.ImageManager;
import main.java.menu.view.MenuScreen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
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
	static BufferedImage[] friends = ImageManager.arrayPopulator(IMAGES.TURTLE, 1);
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

		drawBackground(g);
		drawCrabbies(CrabGameWorld.crabbies, g);
		drawEnemies(CrabGameWorld.enemies, g);
		drawSaltClouds(CrabGameWorld.saltClouds, g);
		drawFriends(CrabGameWorld.friends, g);
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
		if (crabby.getState() == Mover.State.SAFE) {
			drawImageBounds(crabby.getBounds(), g, crabInvincible[crabby.getFrame()]);
		} else {
			drawImageBounds(crabby.getBounds(), g, crab[crabby.getFrame()]);
		}
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
		for (Friend f : friends) {
			drawFriend(f, g);
			drawFact(f, g);
		}
	}

	/**
	 * Draws a friend
	 *
	 * @param friend
	 * @param g
	 */
	static void drawFriend(Friend friend, Graphics g) {
		drawImageBounds(friend.getBounds(), g, WorldRenderer.friends[0]);
	}

//	/**
//	 * Draws a fact following a given friend
//	 *
//	 * @param friend
//	 * @param g
//	 */
	static void drawFact(Friend friend, Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(), 30));
		friend.setTextSize(g.getFontMetrics().stringWidth(Friend.facts[friend.getFriendCounter()]));
		g.drawString(Friend.facts[friend.getFriendCounter()],
				(int) (friend.getBounds().getX() - 300 + friend.getBounds().getWidth()),
				(int) friend.getBounds().getY());
	}

	/**
	 * Draw a given salt cloud
	 */
	static void drawSaltCloud(SaltCloud saltCloud, Graphics g) {
		drawImageBounds(saltCloud.getBounds(), g, trail[0]);
	}

	/**
	 * Draw a given list of saltClouds
	 *
	 * @param saltClouds
	 * @param g
	 */
	static void drawSaltClouds(ArrayList<SaltCloud> saltClouds, Graphics g) {
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
		drawImageBounds(enemy.getBounds(), g, enemies[enemy.getFrame()]);
	}

	/**
	 * Draw a given list of enemies
	 *
	 * @param enemies
	 * @param g
	 */
	static void drawEnemies(ArrayList<Enemy> enemies, Graphics g) {
		for (Enemy e : enemies) {
			drawEnemy(e, g);
		}
	}

}
