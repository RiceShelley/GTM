package main.java.crabGame.model;

/**
 * Turtles (or other animals) that move across the bottom of the screen with
 * useful hints for the crabbies
 */
public class Friend extends Mover {
	static final int FRIEND_HEIGHT = 64;
	static final int FRIEND_WIDTH = 114;
	public static int friendCounter;
	public static int textSize;
	public static String[] facts = { "Welcome to Crab Run! Press SPACE to Jump.",
			"The goal of this game is to fill up the distanceSoFar bar above.",
			"You fill up the bar by staying within the scent trail leading you home.",
			"Avoid fish at all costs or you'll be forced to answer a question.",
			"Watch out for storms and droughts that make it more difficult to return home. Good Luck!" };
	private static int picNum = 0;

	/**
	 * Creates new instance of Friend
	 *
	 * @param b crabWorld to spawn the friend within
	 */
	public Friend(int x, int y) {
		super(x, y, FRIEND_WIDTH, FRIEND_HEIGHT);
		setxVel(-5);
		// Friends start down in the bottom moveRight corner and move across, carrying a
		// fun fact along with them

		Friend.friendCounter = 0;

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
		if (super.getyVel() > -100) {
			super.moveUp();
		}
		picNum = ++picNum % 3;
	}

	public int getFriendCounter() {
		return Friend.friendCounter;
	}

	public void setFriendCounter(int fc) {
		Friend.friendCounter = fc;
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
