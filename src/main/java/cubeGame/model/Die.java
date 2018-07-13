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
	public static final int NUMFACE = 25;
	public final Rectangle bounds = new Rectangle(WIDTH, HEIGHT);
	public int xVel;
	public int yVel;
	private double minDrag = .99;
	private int dragVariable = 100;

	private boolean rolling;
	private int cur;
	private int endFace;

	public Die(Point pos) {
		gameReset(pos);
	}

	public void gameReset(Point pos) {
		bounds.x = pos.x;
		bounds.y = pos.y;
		xVel = 0;
		yVel = 0;
		rolling = true;
		cur = 0;
		endFace = getRandomImage();
	}

	private int getRandomImage() {
		IMAGES[] values = IMAGES.values();
		Collections.shuffle(Arrays.asList(values));
		return CubeWorld.RAND.nextInt(NUMFACE);
	}

	public void move() {
		if (xVel == 0 && yVel == 0) {
			rolling = false;
		} else {
			if (xVel != 0) {
				bounds.x += xVel;
				xVel *= minDrag + CubeWorld.RAND.nextInt(dragVariable)/dragVariable;
			}
			if (yVel != 0) {
				bounds.y += yVel;
				yVel *= minDrag + CubeWorld.RAND.nextInt(dragVariable)/dragVariable;
			}
			rolling = true;
			cur = (cur + 1) % CubeGameScreen.diceFrames;
		}
	}
	
	public void translate(int x, int y) {
		bounds.translate(x, y);
	}

	public boolean isRolling() {
		return rolling;
	}

	public int getCur() {
		return cur;
	}

	public int getEndFace() {
		return endFace;
	}

	public void bounceX(int move) {
		if (Math.abs(xVel) < move) {
			if (xVel > 0) {
				xVel += move;
			} else if (xVel < 0) {
				xVel -= move;
			}
		}
		this.xVel = xVel * -1;
	}

	public void bounceY(int move) {
		if (Math.abs(yVel) < move) {
			if (yVel > 0) {
				yVel += move;
			} else if (yVel < 0) {
				yVel -= move;
			}
		}
		this.yVel = yVel * -1;
	}

	public void roll() {
		endFace = getRandomImage();
		xVel = CubeWorld.RAND.nextInt(INITSPEED * 2 - 1) - INITSPEED + 1;
		yVel = CubeWorld.RAND.nextInt(INITSPEED * 2 - 1) - INITSPEED + 1;
	}
}
