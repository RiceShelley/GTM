package main.java.crabGame.model;

import main.java.crabGame.CrabController;
import main.java.menu.view.MenuScreen;

import java.util.ArrayList;
import java.util.ListIterator;

import static main.java.crabGame.model.SaltCloud.SALT_CLOUD_HEIGHT;
import static main.java.crabGame.model.SaltCloud.SALT_CLOUD_WIDTH;
import static main.java.crabGame.model.SaltCloud.getTrailState;

/**
 * @author Zachary
 * <p>
 * This class is the game class that includes all elements that exist in the game and
 * are displayed on screen.
 * It updates their positions and checks for any and all collisions.
 */
public class CrabGameWorld {
	public final static int WORLD_ORIGIN_X = 0; //200; //
	public final static int WORLD_ORIGIN_Y = 0; //200; //
	public final static int WORLD_WIDTH = MenuScreen.frameWidth;//- 400; //
	public final static int WORLD_HEIGHT = MenuScreen.frameHeight;//- 400; //
	final static int WORLD_RIGHT_BOUND = WORLD_ORIGIN_X + WORLD_WIDTH;
	final static int WORLD_LEFT_BOUND = WORLD_ORIGIN_X;
	final static int WORLD_TOP_BOUND = WORLD_ORIGIN_Y;
	final static int WORLD_BOTTOM_BOUND = WORLD_ORIGIN_Y + WORLD_HEIGHT;
	final static int WORLD_CENTER_X = WORLD_WIDTH / 2;
	final static int WORLD_CENTER_Y = WORLD_HEIGHT / 2;

	public static ArrayList<Crabby> crabbies;
	public static ArrayList<SaltCloud> saltClouds;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<Friend> friends;
	public static int lives;
	private static float worldStateTime;

	private State gameState = CrabGameWorld.State.READY; // The gamePanel's state at any given time, initially LOADING

	public static boolean crabbyScoring;


	/**
	 * Creates the world and everything in it
	 */
	public CrabGameWorld() {
		restartWorld();
	}


	public void restartWorld() {
		final int littleBitOfSpace = 10;
		crabbies = new ArrayList<Crabby>();
		friends = new ArrayList<Friend>();
		enemies = new ArrayList<Enemy>();
		saltClouds = new ArrayList<SaltCloud>();

		spawnCrabby();

		saltClouds.add(new SaltCloud(
				WORLD_RIGHT_BOUND + SALT_CLOUD_WIDTH + littleBitOfSpace,
				WORLD_BOTTOM_BOUND / 2,
				SALT_CLOUD_WIDTH,
				SALT_CLOUD_HEIGHT));

		crabbyScoring = false;

		setGameStateToReady();
		//setGameState(State.WIN);
	}


	/**
	 * What happens to CrabGameWorld every frame, this should call all other update and check methods
	 */
	public void update(long deltaTime) {
		setWorldStateTime(getWorldStateTime() + deltaTime);
		updateCrabbies(deltaTime);
		updateSaltClouds(deltaTime);
		updateEnemies(deltaTime);
		updateFriends(deltaTime);

		if (crabbies.size() == 0) setGameState(State.CONTINUE); // Checks if crabby died
		if (CrabController.score >= CrabController.MAX_SCORE) setGameState(State.WIN); // Check if the game is over or not

	}

	void updateCrabbies(long deltaTime) {
		ListIterator<Crabby> crabbyIterator = crabbies.listIterator();
		while (crabbyIterator.hasNext()) {
			// Update crabbies
			Crabby crabby = crabbyIterator.next(); // Grab the next thing from the iterator
			crabby.update(deltaTime); // Update the thing
			checkCollisions(crabby); // Check if this ran into anything else in the world
			if (crabby.getState() == Mover.State.DIE) crabbyIterator.remove(); // Remove the thing from the world if the state is DIE
		}
	}

	void updateEnemies(long deltaTime) {
		ListIterator<Enemy> enemyIterator = enemies.listIterator();
		while (enemyIterator.hasNext()) {
			// Update all of enemies
			Enemy enemy = enemyIterator.next(); // Grab the next thing from the iterator
			enemy.update(deltaTime);  // Update the thing
			if (enemy.getState() == Mover.State.DIE) enemyIterator.remove(); // Remove the thing from the world if the state is DIE
		}
	}

	void updateFriends(long deltaTime) {
		ListIterator<Friend> friendIterator = friends.listIterator();
		while (friendIterator.hasNext()) {
			// Update all of friends
			Friend friend = friendIterator.next();  // Grab the next thing from the iterator
			friend.update(deltaTime); // Update the thing
			if (friend.getState() == Mover.State.DIE) friendIterator.remove(); // Remove the thing from the world if the state is DIE
		}
	}

	private int saltCloudCount = 0;

	void updateSaltClouds(long deltaTime) {
		ListIterator<SaltCloud> saltCloudIterator = saltClouds.listIterator();
		while (saltCloudIterator.hasNext()) {
			// Update all of saltClouds
			SaltCloud saltCloud = saltCloudIterator.next(); // Grab the next thing from the iterator
			saltCloud.update(deltaTime); // Update the thing
			////


			SaltCloud newSaltCloud = null;

			final int littleBitOfSpace = 5;

			// If it's ok to spawn the next saltCloud
			boolean spawnNextSaltCloud = !saltCloudIterator.hasNext() && saltCloud.getxPos() < WORLD_RIGHT_BOUND + littleBitOfSpace && saltCloud.getxPos() > WORLD_RIGHT_BOUND - littleBitOfSpace;

			int saltCloudStateCountMax = 4;
			if (saltCloudCount >= saltCloudStateCountMax) tryToChangeTrailState();

			if (spawnNextSaltCloud) newSaltCloud = saltCloud.generateNextSaltCloud(saltCloud); // If this particular saltCloud's position is its width of the way through the screen and is the last one on the list, generate the next one

			if (newSaltCloud != null) {
				saltCloudIterator.add(newSaltCloud);  // Add the next one if it exists
				saltCloudCount++;
				//System.out.println("salt cloud count: " + saltCloudCount + " Trail State: " + SaltCloud.getTrailState());
			}

			////
			if (saltCloud.getState() == Mover.State.DIE) saltCloudIterator.remove(); // Remove from the world if its state is DIE
		}
	}

	void tryToChangeTrailState() {

		double rand = Math.random();
		double fiftyPercent = .5;
		double toChangeChance = .4;

		SaltCloud.TrailState newTrailState = getTrailState();

		if (SaltCloud.getTrailState() == SaltCloud.TrailState.NORMAL_ENTRY) newTrailState = SaltCloud.TrailState.NORMAL;
		else if (SaltCloud.getTrailState() == SaltCloud.TrailState.DROUGHT_ENTRY) newTrailState = SaltCloud.TrailState.DROUGHT;
		else if (SaltCloud.getTrailState() == SaltCloud.TrailState.STORM_ENTRY) newTrailState = SaltCloud.TrailState.STORM;
		else if (SaltCloud.getTrailState() == SaltCloud.TrailState.NORMAL) {
			if (rand > toChangeChance) {
				newTrailState = (Math.random() > fiftyPercent) ? SaltCloud.TrailState.DROUGHT_ENTRY : SaltCloud.TrailState.STORM_ENTRY;
			}
		} else if (SaltCloud.getTrailState() == SaltCloud.TrailState.STORM) {
			if (rand > toChangeChance) {
				newTrailState = (Math.random() > fiftyPercent) ? SaltCloud.TrailState.NORMAL_ENTRY : SaltCloud.TrailState.STORM;
			}
		} else if (SaltCloud.getTrailState() == SaltCloud.TrailState.DROUGHT) {
			if (rand > toChangeChance) {
				newTrailState = (Math.random() > fiftyPercent) ? SaltCloud.TrailState.NORMAL_ENTRY : SaltCloud.TrailState.DROUGHT;
			}
		}

		SaltCloud.setTrailState(newTrailState);
		saltCloudCount = 0;
		System.out.println("Tried to change Trail State to: " + newTrailState);
	}

	// Collision Checks //

	/**
	 * Checks the various collisions that could be going on in the world between a given crabby and the rest of the world,
	 * which call what should happen afterwards when one of them does
	 */
	private static void checkCollisions(Crabby crabby) {
		crabbyScoring = checkSaltCloudCollision(crabby);
		System.out.println("Crabby Scoring: " + crabbyScoring);
		checkEnemyCollision(crabby);
	}

	/**
	 * Checks to see if a given crabby has collided with any of the world's enemies
	 */
	private static void checkEnemyCollision(Crabby crabby) {
		for (Enemy enemy : enemies) {
			if (crabby.getState() != Mover.State.SAFE && crabby.getBounds().intersects(enemy.getBounds())) crabby.hitEnemy();
		}
	}


	/**
	 * Checks whether or not a given crabby is intersected with any of the world's saltClouds
	 */
	private static boolean checkSaltCloudCollision(Crabby crabby) {
		for (SaltCloud saltCloud : saltClouds) {
			if (crabby.getBounds().intersects(saltCloud.getBounds())) {
				CrabController.incrementScore();
				return true;
			}
		}
		return false;
	}

	// GameState Controls //

	/**
	 * Various game states
	 */
	public enum State {
		// READY is when the gamePanel is about ready to start with an introductory screen, this should be at the beginning
		READY,
		// PLAY_ENTRY is the part where you just got here and things are 'soft'
		PLAY_ENTRY,
		// PLAY is when the gamePanel is, well, running, this should be the vast majority of the time
		PLAY,
		// CONTINUE is when the player has died, it should be a prompt to try again with a classic arcade-style countdown that goes back to the main menu afterwards
		CONTINUE,
		// WIN is the win screen, congrats you beat the game, this is what it should look and act like, it should probably go back to the main menu after a timeout or a tap
		WIN
	}


	public float getWorldStateTime() {
		return worldStateTime;
	}

	public void setWorldStateTime(float worldStateTime) {
		CrabGameWorld.worldStateTime = worldStateTime;
	}

	public State getGameState() {
		return gameState;
	}

	public void setGameState(State newGameState) {
		gameState = newGameState;
		setWorldStateTime(0);
	}

	public boolean checkGameStateReady() {
		return getGameState() == State.READY;
	}

	public void setGameStateToReady() {
		setGameState(State.READY);
	}

	public boolean checkGameStatePlay() {
		return getGameState() == State.PLAY;
	}

	public void setGameStateToPlay() {
		setGameState(State.PLAY);
	}

	public boolean checkGameStatePlayEntry() {
		return getGameState() == State.PLAY_ENTRY;

	}

	public void setGameStateToPlayEntry() {
		setGameState(State.PLAY_ENTRY);
	}

	public boolean checkGameStateContinue() {
		return getGameState() == State.CONTINUE;
	}

	public boolean checkGameStateWin() {
		return getGameState() == State.WIN;
	}

	// Crabby Controls //

	public static void spawnCrabby() {
		crabbies.add(new Crabby(WORLD_CENTER_X, WORLD_CENTER_Y));
	}

	public static void moveCrabbyUp() {
		for (Crabby crabby : crabbies) crabby.moveUp();
	}

	public static void moveCrabbyRight() {
		for (Crabby crabby : crabbies) crabby.moveRight();
	}

	public static void moveCrabbyLeft() {
		for (Crabby crabby : crabbies) crabby.moveLeft();
	}

	public static void moveCrabbyDown() {
		for (Crabby crabby : crabbies) crabby.moveDown();
	}

	// Enemy Controls //

	public static void spawnEnemy() {
		Enemy newEnemy;
		int littleBitOfSpace = 10;
		int newX = WORLD_RIGHT_BOUND + Enemy.ENEMY_WIDTH + littleBitOfSpace;
		int newY = Utilities.randomWithRange(WORLD_ORIGIN_Y, WORLD_BOTTOM_BOUND);

		newEnemy = new Enemy(newX, newY);

		enemies.add(newEnemy);
	}

	// Misc World Controls //

	public static void moveSaltClouds() {
		for (SaltCloud saltCloud : saltClouds) saltCloud.moveLeft();
	}

}