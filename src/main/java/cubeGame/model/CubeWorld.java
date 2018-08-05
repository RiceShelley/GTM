package main.java.cubeGame.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import main.java.menu.view.MenuScreen;

public class CubeWorld {
	public static final Die[] dice = new Die[5];
	private static int h = MenuScreen.frameHeight;
	private static int w = MenuScreen.frameWidth;
	// The five slots that accept dice
	public final DieHolder[] markers = { new DieHolder((w - 5 * Die.WIDTH) / 2, h / 3 - Die.HEIGHT / 2),
			new DieHolder((w - 2 * Die.WIDTH) / 2, h / 3 - Die.WIDTH / 2),
			new DieHolder((w + Die.WIDTH) / 2, h / 3 - Die.HEIGHT / 2),
			new DieHolder((w + 4 * Die.WIDTH) / 2, h / 3 - Die.HEIGHT / 2),
			new DieHolder((w + 7 * Die.WIDTH) / 2, h / 3 - Die.HEIGHT / 2) };
	public static final Rectangle rollZone = new Rectangle(0, h / 3, w, 2 * h / 3);
	public static final Random RAND = new Random();

	/*
	 * Initializes 5 dice in random positions and allows the user to roll
	 */
	public CubeWorld() {
		for (int i = 0; i < dice.length; i++) {
			dice[i] = new Die(diceLoc());
		}
	}

	/*
	 * Updates the logic every frame and ensures that the dice roll within bounds
	 */
	public void update() {
		for (Die d : dice) {
			Rectangle intersection = rollZone.intersection(d.bounds);
			if (d.isRolling()) {
				if (intersection.width < Die.WIDTH) {
					d.bounceX(Die.WIDTH - intersection.width);
				}
				if (intersection.height < Die.HEIGHT) {
					d.bounceY(Die.HEIGHT - intersection.height);
				}
			} else {
				d.ensureBounds(); // Make sure the die is in the rollzone
				d.noOverlaps(); // Make sure 2 dice don't overlap
			}
			d.move();
		}
	}
	

	/*
	 * Rolls all dice within the boundary
	 */
	public void rollDice() {
		Die.clearIndeces();
		for (Die d : dice) {
			if (d.isPlaced()) {
				d.addIndexToSet();
			}
		}
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
		Arrays.stream(dice).forEach(die -> die.gameReset(diceLoc()));
		Arrays.stream(markers).forEach(holder -> holder.removeDie());
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

	public boolean allDicePlaced() {
		return Arrays.stream(markers).allMatch(holder -> holder.containsDie());
	}
	
	/*
	 * Returns an unordered array of the dice in play
	 */
	public Die[] getDice() {
		return dice;
	}
	
	/*
	 * Returns an ordered array of the dice in the holders.  Throws RuntimeException if any holders are unoccupied
	 */
	public Die[] getPlacedDice() {
		List<Die> results = new ArrayList<>();
		for (DieHolder holder : markers) {
			if (!holder.containsDie()) {
				throw new RuntimeException("No Die Placed");
			} else {
				results.add(holder.getDie());
			}
		}
		return results.toArray(new Die[5]);
	}
	
	public int getRollWidth() {
		return rollZone.width;
	}

	public int getRollHeight() {
		return rollZone.height;
	}
	
	


}
