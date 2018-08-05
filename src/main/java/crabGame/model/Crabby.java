package main.java.crabGame.model;

import java.util.Collections;

import main.java.crabGame.CrabController;

/**
 * This class constitutes the main playable character in the game
 */
public class Crabby extends Mover {
	static final int CRAB_WIDTH_ = 100;
	static final int CRAB_HEIGHT_ = 50;
	static final int CRAB_LIFE_BASE = 3;
	private int frame = 0;

	/**
	 * Create instance of a crabbies
	 */
	public Crabby(int x, int y) {
		super(x, y, CRAB_WIDTH_, CRAB_HEIGHT_);
		CrabGameWorld.lives = CRAB_LIFE_BASE;
	}

	/**
	 * What happens to crabbies every frame
	 */
	@Override
	void update(long deltaTime) {
		super.update(deltaTime);
		if (CrabGameWorld.lives <= 0) {
			die(); // If Crabby runs out of lives, do what happens when it dies
		}
	}

	/**
	 * What happens when Crabby gets hit by an Enemy
	 */
	public void hitEnemy() {
		CrabGameWorld.lives--;
		setxVel(0);
		setyVel(0);
		setState(State.SAFE);
		if (CrabGameWorld.lives > 0) {
			CrabController.paused = true;
		}
		System.out.println("Hit enemy");
	}

	public int getFrame() {
		// frame = (frame+1) % 3;
		frame = (frame + 1) % 3;
		return frame;
	}

}
