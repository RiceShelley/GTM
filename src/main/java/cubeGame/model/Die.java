package main.java.cubeGame.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import main.java.cubeGame.view.CubeGameScreen;
import main.java.menu.enums.IMAGES;
import main.java.menu.view.ImageManager;
import main.java.menu.view.MenuScreen;

public class Die {

	public static final int WIDTH = MenuScreen.frameHeight * 43 / 268;
	public static final int HEIGHT = MenuScreen.frameHeight * 43 / 268;
	public static final int INITSPEED = 100;
	public static final int NUMFACE = countDiceImages(); // How many possible die faces
	public final Rectangle bounds = new Rectangle(WIDTH, HEIGHT); // Physical area occupied
	public int xVel;
	public int yVel;
	private double minDrag = .99;
	private int dragVariable = 100;
	private boolean placed;
	private boolean firstResting; // True when rolling and before settled, to avoid overlap
	private static boolean checkingBounds;
	private String name;

	private int rollingImageIndex; // Random value corresponding to how the rolling dice looks
	private int endImageIndex; // Random value corresponding to the final image on the die
	private static Set<Integer> usedIndeces; // Stores the index of each die's image to avoid overlap

	public Die(Point pos) {
		if (usedIndeces == null)
			usedIndeces = new HashSet<>();
		gameReset(pos);
		firstResting = true;
		checkingBounds = true;
	}

	public void gameReset(Point pos) {
		bounds.x = pos.x;
		bounds.y = pos.y;
		placed = false;
		firstResting = true;
		checkingBounds = true;
	}

	/*
	 * Returns the index corresponding to the final image
	 */
	private void setRandomImageIndex() {
		endImageIndex = CubeWorld.RAND.nextInt(NUMFACE);
		while (usedIndeces.contains(endImageIndex)) { // Repeat until finding a unique index
			endImageIndex = CubeWorld.RAND.nextInt(NUMFACE);
		}
		addIndexToSet();
	}

	/*
	 * Adds the die's image index to the static set
	 */
	public void addIndexToSet() {
		usedIndeces.add(endImageIndex);
	}

	/*
	 * Update's the die's position
	 */
	public void move() {
		if (isRolling()) {
			if (xVel != 0) {
				bounds.x += xVel;
				xVel *= minDrag + CubeWorld.RAND.nextInt(dragVariable) / dragVariable;
			}
			if (yVel != 0) {
				bounds.y += yVel;
				yVel *= minDrag + CubeWorld.RAND.nextInt(dragVariable) / dragVariable;
			}
			rollingImageIndex = (rollingImageIndex + 1) % CubeGameScreen.diceFrames;
		}
	}

	/*
	 * Moves the die by (x, y)
	 */
	public void translate(int x, int y) {
		bounds.translate(x, y);
	}

	/*
	 * Rolling variable stored in function to eliminate redundancy
	 */
	public boolean isRolling() {
		return xVel != 0 && yVel != 0;
	}

	/*
	 * Reset images and velocities
	 */
	public void roll() {
		firstResting = true;
		setRandomImageIndex();
		xVel = CubeWorld.RAND.nextInt(INITSPEED * 2 - 1) - INITSPEED + 1;
		yVel = CubeWorld.RAND.nextInt(INITSPEED * 2 - 1) - INITSPEED + 1;
	}

	public void bounceX(int move) {
		if (Math.abs(xVel) < move) {
			xVel += (xVel > 0 ? move : -move);
		}
		this.xVel = xVel * -1;
	}

	public void bounceY(int move) {
		if (Math.abs(yVel) < move) {
			yVel += (yVel > 0 ? move : -move);
		}
		this.yVel = yVel * -1;
	}

	/*
	 * Checks if two resting dice are overlapping
	 */
	private boolean intersecting(Die other) {
		if ((!isRolling() && !other.isRolling()) && bounds.intersects(other.bounds)) {
			return true;
		}
		return false;
	}

	/*
	 * When the die first settles, make sure it doesn't overlap with any others
	 * 
	 */
	public void noOverlaps() {
		if (!isRolling() && checkingBounds && firstResting) {
			for (Die d : CubeWorld.dice) {
				if (d != this && intersecting(d)) {
					int x = (bounds.x > d.bounds.x ? 1 : -1);
					int y = (bounds.y > d.bounds.y ? 2 : -1);
					translate(x, y); // Move the die to the nearest edge
					// If moving the die gets pushed off the edge, move it back
					Rectangle intersection = CubeWorld.rollZone.intersection(d.bounds);
					if (intersection.width < Die.WIDTH) {
						translate(-x, 0);
					}
					if (intersection.height < Die.HEIGHT) {
						translate(0, -y);
					}
					noOverlaps(); // Recursively call until cleared
				}
			}
			firstResting = false; // Marks the die as placed
		}
	}
	
	/*
	 * Triggered the mouse is released.  If the die is off the screen, moves it back on
	 */
	public void ensureBounds() {
		if (!checkingBounds || placed) return;
		Rectangle intersection = CubeWorld.rollZone.intersection(bounds);
		if (intersection.width < Die.WIDTH) {
			bounds.x = (bounds.x > CubeWorld.rollZone.width / 2 ? CubeWorld.rollZone.width - WIDTH : 0);
			firstResting = true;
		}
		if (intersection.height < Die.HEIGHT) {
			bounds.y = (bounds.y < CubeWorld.rollZone.height / 2 ? (MenuScreen.frameHeight - CubeWorld.rollZone.height) : MenuScreen.frameHeight - HEIGHT); 
			firstResting = true;
		}
		noOverlaps();
	}


	/*
	 * Triggered when the resting die is placed in a final slot
	 */
	public void setPlaced(boolean b) {
		placed = b;
	}

	/*
	 * Is the die placed in a slot?
	 * 
	 * @return true if placed, false otherwise
	 */
	public boolean isPlaced() {
		return placed;
	}

	/*
	 * Resets the set containing the used indeces. Called whenever the dice are
	 * rolled
	 */
	public static void clearIndeces() {
		if (usedIndeces != null)
			usedIndeces.clear();
	}

	/*
	 * Returns the number of images to be used for die faces
	 */
	public static int countDiceImages() {
		return (int) Arrays.stream(IMAGES.values()).filter(image -> image.toString().contains("DICE_")).count();
	}

	/*
	 * Gives the die a name corresponding to its current image
	 */
	public void setName(BufferedImage bf) {
		try {
			IMAGES value = ImageManager.findImage(bf); // Find the corresponding IMAGES
			name = value.toString().split("_")[1]; // Name should be only the identifying part of the string
		} catch (Exception e) {
			name = "Image not Found";
		}
	}

	/*
	 * Called when the mouse is not pressed.  Triggers functions that regulate position
	 */
	public static void doCheckBounds() {
		checkingBounds = true;
	}
	
	/*
	 * Called when the mouse is pressed. Prevents position-controlling logic from firing
	 */
	public static void dontCheckBounds() {
		checkingBounds = false;
	}
	
	public String getName() {
		return (name == null ? "" : name);
	}
	
	public int getRollingImageIndex() {
		return rollingImageIndex;
	}

	
	public int getEndImageIndex() {
		return endImageIndex;
	}
	
	public void setFirstResting() {
		firstResting = true;
	}

}
