package main.java.cubeGame.model;

import java.awt.Point;

/*
 * Model describing the rectangles that can hold dice
 */
public class DieHolder extends Point {

	private static final long serialVersionUID = 1L;
	private Die currentDie;

	public DieHolder(int x, int y) {
		super(x, y);
	}

	/*
	 * Place a die if there isn't one already placed
	 */
	public void placeDie(Die d) {
		if (currentDie != null)
			return;
		currentDie = d;
	}

	/*
	 * Returns the placed die.  Should never be called without die placed
	 */
	public Die getDie() {
		if (currentDie == null)
			throw new RuntimeException("No Die Placed");
		return currentDie;
	}

	/*
	 * Removes the placed die
	 */
	public void removeDie() {
		currentDie = null;
	}

	public boolean containsDie() {
		return currentDie != null;
	}
}