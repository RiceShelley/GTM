package main.java.crabGame.model;

import java.awt.*;

/**
 * Created by Alina on 2017-07-18
 */
public abstract class GameObject {
	private Rectangle bounds;
	private int xPos, yPos;

	public GameObject(int x, int y, int width, int height) {
		this.xPos = x;
		this.yPos = y;
		this.bounds = new Rectangle(
				x,
				y,
				width,
				height);
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public void setBounds(int xPos, int yPos) {
		this.bounds = new Rectangle(
				xPos,
				yPos,
				bounds.width,
				bounds.height);
	}

	public void setBounds(int xPos, int yPos, int width, int height) {
		this.bounds = new Rectangle(
				xPos,
				yPos,
				width,
				height);
	}

	public void setBounds() {
		this.bounds = new Rectangle(
				xPos,
				yPos,
				bounds.width,
				bounds.height);
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
		//System.out.println(this + " setxPos: " + xPos);
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
}
