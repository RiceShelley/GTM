package main.java.cubeGame.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import main.java.cubeGame.controller.CubeListener;
import main.java.cubeGame.enums.STATE;
import main.java.menu.view.MenuScreen;

public class CubeWorld {
	public final Die[] dice = new Die[5];
	private int h = MenuScreen.frameHeight;
	private int w = MenuScreen.frameWidth;
	public final Point[] markers = { new Point((w - 5 * Die.WIDTH) / 2, h / 3 - Die.HEIGHT / 2),
			new Point((w - 2 * Die.WIDTH) / 2, h / 3 - Die.WIDTH / 2),
			new Point((w + Die.WIDTH) / 2, h / 3 - Die.HEIGHT / 2),
			new Point((w + 4 * Die.WIDTH) / 2, h / 3 - Die.HEIGHT / 2),
			new Point((w + 7 * Die.WIDTH) / 2, h / 3 - Die.HEIGHT / 2) };
	public final Rectangle rollZone = new Rectangle(0, h / 3, w, 2 * h / 3);
	private STATE state;
	public static final Random RAND = new Random();

	public boolean hasRolled = false;
	public boolean hasMoved = false;

	/*
	 * Initializes 5 dice in random positions and allows the user to roll
	 */
	public CubeWorld() {
		for (int i = 0; i < dice.length; i++) {
			dice[i] = new Die(diceLoc());
		}
		state = STATE.ROLL;
	}

	public void update() {
		for (Die d : dice) {
			Rectangle intersection = rollZone.intersection(d.bounds);
			if (d.isRolling()) {
				state = STATE.ROLL;
				if (intersection.equals(null)) {
					d.gameReset(diceLoc());
					System.out.println("Something went wrong. Resetting.");
				} else {

					if (intersection.width < Die.WIDTH) {
						d.bounceX(Die.WIDTH - intersection.width);
					} // if width
					if (intersection.height < Die.HEIGHT) {
						d.bounceY(Die.HEIGHT - intersection.height);
					} // if height
				} // if else
			}
			d.move();
		}
	}

	/*
	 * Rolls all dice within the boundary
	 */
	public void rollDice() {
		for (Die d : dice) {
			if (d.bounds.intersects(rollZone))
				d.roll();
		}
	}

	/*
	 * Creates a random point within the boundaries
	 */
	private Point diceLoc() {
		int y = RAND.nextInt(rollZone.height - Die.HEIGHT) + rollZone.y;
		int x = RAND.nextInt(rollZone.width - Die.WIDTH) + rollZone.x;
		return new Point(x, y);
	}

	/*
	 * Resets dice positions and returns the state to ROLL
	 */
	public void reset() {
		for (Die d : dice) {
			d.gameReset(diceLoc());
		}
		state = STATE.ROLL;
		hasRolled = false;
	}

	/*
	 * Returns true if all the dice are within the boundary
	 */
	public boolean checkBounds() {
		for (Die d : dice) {
			if (d.bounds.intersects(rollZone)) {
				return false;
			}
		}
		return true;
	}

	public Die[] getDice() {
		return dice;
	}

	public int getRollWidth() {
		return rollZone.width;
	}

	public int getRollHeight() {
		return rollZone.height;
	}

	// TODO: Currently, the state begins as ROLL and remains that way until the
	// first die is touched
	// Once any die has been interacted with, the state remains MOVE for the rest
	public void setState(STATE state) {
		this.state = state;
	}

	public STATE getState() {
		return state;
	}
}
