package main.java.cubeGame.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Collections;

import main.java.cubeGame.view.CubeGameScreen;
import main.java.menu.enums.IMAGES;
import main.java.menu.view.MenuScreen;

public class Die {
	public static final int WIDTH = MenuScreen.frameHeight*43/268;
	public static final int HEIGHT = MenuScreen.frameHeight*43/268;
	public static final int INITSPEED = 100;
	public static final int NUMFACE = 19; // How many possible die faces
	public final Rectangle bounds = new Rectangle(WIDTH, HEIGHT); // Physical area occupied
	public int xVel;
	public int yVel;
	private double minDrag = .99;
	private int dragVariable = 100;
	private boolean placed;

	
	private int rollingImageIndex; // Random value corresponding to how the rolling dice looks
	private int endImageIndex; // Random value corresponding to the final image on the die

	public Die(Point pos) {
		gameReset(pos);
	}
	
	public boolean isRolling() {
		return xVel != 0 && yVel != 0;
	}

	public void gameReset(Point pos) {
		bounds.x = pos.x;
		bounds.y = pos.y;
		placed = false;
		xVel = 0;
		yVel = 0;
		rollingImageIndex = 0;
		endImageIndex = getRandomImageIndex();
	}

	/*
	 * Returns the index corresponding to the final image
	 */
	private int getRandomImageIndex() {
		return CubeWorld.RAND.nextInt(NUMFACE);
	}

	/*
	 * Update's the die's position
	 */
	public void move() {
		if (isRolling()) {
			if (xVel != 0) {
				bounds.x += xVel;
				xVel *= minDrag + CubeWorld.RAND.nextInt(dragVariable)/dragVariable;
			}
			if (yVel != 0) {
				bounds.y += yVel;
				yVel *= minDrag + CubeWorld.RAND.nextInt(dragVariable)/dragVariable;
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

	public void roll() {
		endImageIndex = getRandomImageIndex();
		xVel = CubeWorld.RAND.nextInt(INITSPEED * 2 - 1) - INITSPEED + 1;
		yVel = CubeWorld.RAND.nextInt(INITSPEED * 2 - 1) - INITSPEED + 1;
	}

	public void bounceX(int move) {
		if (Math.abs(xVel) < move) {
			xVel += (xVel > 0 ? move : - move);
		}
		this.xVel = xVel * -1;
	}

	public void bounceY(int move) {
		if (Math.abs(yVel) < move) {
			yVel += (yVel > 0 ? move : - move);
		}
		this.yVel = yVel * -1;
	}
	
	public int getRollingImageIndex() {
		return rollingImageIndex;
	}

	public int getEndImageIndex() {
		return endImageIndex;
	}
	
	public void setPlaced(boolean b) {
		placed = b;
	}
	
	public boolean getPlaced() {
		return placed;
	}
}
