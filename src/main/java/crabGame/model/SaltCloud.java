package main.java.crabGame.model;

import main.java.menu.view.MenuScreen;

/**
 * Created by Alina on 2017-07-13.
 */
public class SaltCloud extends Mover {
	static final int SALT_CLOUD_WIDTH = MenuScreen.frameWidth / 8;
	static final int SALT_CLOUD_HEIGHT = MenuScreen.frameHeight / 4;
	final double SALT_CLOUD_VELOCITY = super.movementVelocity;
	static boolean waveGoingDown = false; // Whether or not the waves as a set are appearing lower and lower on the screen or higher and higher on the screen
	private static TrailState trailState = TrailState.NORMAL_ENTRY;
	private static float trailStateTime = 0;

	public float getTrailStateTime() {
		return trailStateTime;
	}

	private static void setTrailStateTime(float trailStateTime) {
		SaltCloud.trailStateTime = trailStateTime;
	}

	/**
	 * Creates an instance of a SaltCloud
	 */
	SaltCloud(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.movementVelocity = SALT_CLOUD_VELOCITY;
	}

	public enum TrailState {
		// SaltCloud specific states
		NORMAL, NORMAL_ENTRY, STORM_ENTRY, STORM, DROUGHT_ENTRY, DROUGHT
	}

	public static TrailState getTrailState() {
		return trailState;
	}

	public static void setTrailState(TrailState ts) {
		trailState = ts;
		setTrailStateTime(0);
	}

	/**
	 * Generates the next saltCloud based on the given saltCloud
	 *
	 * @param saltCloud
	 */
	SaltCloud generateNextSaltCloud(SaltCloud saltCloud) {
		int newX;
		int newY;
		int newHeight;
		int newWidth;
		SaltCloud returnSaltCloud;
		double yChange;
		final int littleBitOfSpace = 10;
		final double normalWaveDirectionChangeChance = .9;
		final double stormWaveDirectionChangeChance = .3;
		final double droughtWaveDirectionChanceChance = .1;
		final double stormYPosChangeMultiplier = 3;
		final double droughtHeightMultiplier = .75;
		final double yChangeBase = 15;


		newX = saltCloud.getxPos() + SALT_CLOUD_WIDTH + littleBitOfSpace; // Bases newX on the world's width
		newY = saltCloud.getyPos(); // Bases newY on the given SaltCloud
		newWidth = SALT_CLOUD_WIDTH;
		newHeight = SALT_CLOUD_HEIGHT;

		yChange = yChangeBase; // Set a base change

		if (getTrailState() == TrailState.NORMAL || getTrailState() == TrailState.NORMAL_ENTRY) {
			if (Math.random() > normalWaveDirectionChangeChance) {
				SaltCloud.waveGoingDown = !SaltCloud.waveGoingDown; // Potentially change the wave direction
			}
		} else if (getTrailState() == TrailState.STORM || getTrailState() == TrailState.STORM_ENTRY) {
			if (Math.random() > stormWaveDirectionChangeChance) {
				SaltCloud.waveGoingDown = !SaltCloud.waveGoingDown; // Potentially change the wave direction
			}
			yChange *= stormYPosChangeMultiplier;
		} else if (getTrailState() == TrailState.DROUGHT || getTrailState() == TrailState.DROUGHT_ENTRY) {
			if (Math.random() > droughtWaveDirectionChanceChance) {
				SaltCloud.waveGoingDown = !SaltCloud.waveGoingDown; // Potentially change the wave direction
			}
			newHeight = (int) (SALT_CLOUD_HEIGHT * droughtHeightMultiplier);
		}

		yChange = SaltCloud.waveGoingDown ? yChange * 1 : yChange * -1; // Depending on what the direction is, reverse the change
		newY += yChange; // Add the change to the new yPos

		returnSaltCloud = new SaltCloud(newX, newY, newWidth, newHeight); // Creates the saltCloud with newX, newY, and waveGoingDown
		return returnSaltCloud; // Return the new salt cloud


	}

	/**
	 * What happens to SaltCloud every frame
	 *
	 * @param deltaTime the change in time since the SaltCloud last moved
	 */
	@Override
	public void update(long deltaTime) {
		super.update(deltaTime);
		setTrailStateTime(getTrailStateTime() + deltaTime);

	}
}
