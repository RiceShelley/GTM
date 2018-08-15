package main.java.crabGame.model;

import main.java.menu.view.MenuScreen;

/**
 * Turtles (or other animals) that move across the bottom of the screen with
 * useful hints for the crabbies
 */
public class Friend extends Mover {
	static final int FRIEND_HEIGHT = MenuScreen.frameHeight / 7;
	static final int FRIEND_WIDTH = MenuScreen.frameWidth / 10;
	public static int friendCounter = 0;
	public static int textSize;
	public static final String[] facts = { "Welcome to Crab Run! Tap the screen to move.",
			"Fill up the green bar above by staying within the scent trail.",
			"Avoid the fish or you'll have to answer a question to continue." };
	private static int picNum = 0;
	private int pFriendCounter;

	/**
	 * Creates new instance of Friend
	 *
	 * @param b crabWorld to spawn the friend within
	 */
	public Friend(int x, int y) {
		super(x, y, FRIEND_WIDTH, FRIEND_HEIGHT);
		// Friends start down in the bottom moveRight corner and move across, carrying a
		// fun fact along with them

		pFriendCounter = friendCounter;
		super.setyPos(y - (pFriendCounter * 50));
		friendCounter++;
	}

	public static void load() {
		Friend.friendCounter = 0;
	}

	/**
	 * animates legs, updates position on screen
	 */
	@Override
	void update(long deltaTime) {
		super.update(deltaTime);
		super.moveLeft();
		picNum = ++picNum % 3;
	}

	public int getFriendCounter() {
		return pFriendCounter;
	}

	public static void setFriendCounter(int fc) {
		//Friend.friendCounter = fc;
	}

	public int getTextSize() {
		return Friend.textSize;
	}

	public void setTextSize(int ts) {
		Friend.textSize = ts;
	}

	public int getPicNum() {
		return picNum;
	}
}
