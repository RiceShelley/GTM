package main.java.crabGame.model;

import main.java.menu.view.MenuScreen;

/**
 * Abstract class that outlines behaviour of movable figures in the game
 * (Friend, Enemy, Crabby)
 */
public abstract class Mover extends GameObject {
	public double movementVelocity;
	private State state = State.ALIVE;
	private float stateTime = 0;

	// The point the mover is trying to get to, if any
	public double xGoal, yGoal;

	// The current velocity of the mover
	private double xVel, yVel;

	Mover(int x, int y, int width, int height) {
		super(x, y, width, height);

		if (MenuScreen.frameHeight > 1200 && MenuScreen.frameWidth > 2000) {
			movementVelocity = .5;
		} else {
			movementVelocity = .3;
		}
		stateTime = 0;
	}

	public double getxVel() {
		return xVel;
	}

	public void setxVel(double xVel) {
		this.xVel = xVel;
	}

	public double getyVel() {
		return yVel;
	}

	public void setyVel(double yVel) {
		this.yVel = yVel;
	}

	/**
	 * What happens when mover dies
	 */
	public void die() {
		setState(State.DIE);
		setStateTime(0);
		System.out.println(this + " Set state to: " + state);
	}

	void update(long deltaTime) {
		int rightBound = CrabGameWorld.WORLD_RIGHT_BOUND - getBounds().width;
		int leftBound = CrabGameWorld.WORLD_LEFT_BOUND;
		int bottomBound = CrabGameWorld.WORLD_BOTTOM_BOUND - getBounds().height;
		int topBound = CrabGameWorld.WORLD_TOP_BOUND;
		double GRAVITY_CONSTANT = 0;
		if (MenuScreen.frameHeight > 1200 && MenuScreen.frameWidth > 2000) {
			GRAVITY_CONSTANT = .02;
		} else {
			GRAVITY_CONSTANT = .01;
		}
		final double FLOOR_DAMPENER = .1;

		setxPos((int) (getxPos() + getxVel() * deltaTime)); // Update x position with velocity and time
		setyPos((int) (getyPos() + getyVel() * deltaTime)); // Update y position with velocity and time

		setBounds(); // Update bounds

		if (getxPos() > rightBound) { // If the thing's position is greater than the width of the world and its own
										// boundaries, the rightmost bound of the screen...
			if (this instanceof Crabby) {
				setxVel(-getxVel()); // Reverse movement
				setxPos(rightBound); // Set position to the edge of the screen
			} else {
				moveLeft();
			}
		}

		if (getyPos() > bottomBound) { // If the thing's position is greater than the world's height, minus its own
										// boundaries, the bottommost bound of the screen...
			setyVel(-getyVel() + FLOOR_DAMPENER); // Decrease and reverse movement
			setyPos(bottomBound); // Set position to the edge of the screen
		}

		if (getxPos() <= leftBound) {
			if (this instanceof Crabby) {
				setxVel(-getxVel()); // Reverse movement
				setxPos(leftBound);
			}
			if (getxPos() + getBounds().width <= leftBound) {
				die(); // Set its state to die, which should have the world's update function remove it
						// later
			}
		}

		if (getyPos() <= topBound) { // If the thing's position is less than 0, the uppermost bound of the screen...
			setyVel(-getyVel()); // Set it back and make it 'bounce" off the wall
			setyPos(topBound);
		}

		if (!(this instanceof SaltCloud) && !(this instanceof Friend))
			yVel += GRAVITY_CONSTANT; // Apply the world's gravity to everything but SaltCloud and Friends

		setStateTime(getStateTime() + deltaTime); // Update the amount of time since the current state has been set

		int SAFE_TIME = 5000;
		if (state == State.SAFE && stateTime > SAFE_TIME) {
			setState(State.ALIVE);
			System.out.println("set to alive" + stateTime);
		}

	}

	public void moveUp() {
		setyVel(-movementVelocity);
	}

	public void moveDown() {
		setyVel(movementVelocity);
	}

	public void moveLeft() {
		setxVel(-movementVelocity);
	}

	public void moveRight() {
		setxVel(movementVelocity);
	}

	/**
	 * Determines the state of the mover, this should usually be either alive or
	 * dead, but some movers get more specific states for their own purposes.
	 * <p>
	 * There's probably a better way to do this that doesn't have them all use the
	 * same set of states, but oh well.
	 */
	public enum State {
		// Standard states for all movers, used to determine what to do with something
		// when it's alive or dead,
		// usually either draw it or delete it
		ALIVE, DIE,
		// Crabby specific states
		SAFE
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		// System.out.println("Set state to: " + state);
		this.state = state;
		setStateTime(0);
	}

	public float getStateTime() {
		return stateTime;
	}

	private void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
}
