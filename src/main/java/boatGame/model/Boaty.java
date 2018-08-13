package main.java.boatGame.model;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import main.java.boatGame.enums.ITEM;

public class Boaty {
	public final static int HEIGHT = 60;
	public final static int WIDTH = 105;
	public final static int EASING = 6;
	private final Point pos = new Point();
	private double velocity;

	public double angle;
	public boolean canPickUp;
	public boolean canDropOff;
	public ITEM holding;

	public Boaty(Point loc) {
		reset(loc);
	}

	public int getXCoord() {
		return pos.x;
	}

	public int getYCoord() {
		return pos.y;
	}
	
	public double getVelocity() {
		return velocity;
	}

	/**
	 * updates angle, velocity, and position of boat
	 * 
	 * @param mouse
	 *            Point clicked on screen that boat is moving to
	 */
	public ArrayList<Wake> move(Point mouse) {
		double xDist = mouse.x - pos.x;
		double yDist = mouse.y - pos.y;
		angle = Math.atan2(yDist, xDist);
		velocity = Point2D.distance(mouse.x, mouse.y, pos.x, pos.y) / EASING;
		pos.x += (int) (xDist / EASING);
		pos.y += (int) (yDist / EASING);
		//System.out.println("\nBoat: " + pos);
		return spawnWakes();
	} // move

	/**
	 * initializes wake objects according to velocity and position of boat
	 * @return ArrayList of new wakes
	 */
	private ArrayList<Wake> spawnWakes() {
		ArrayList<Wake> wakes = new ArrayList<Wake>();
		double numWakes =  Math.floor(velocity/Wake.WAKE_DIST);
		//TODO: if numWakes > 1, it causes the weird thing where they don't follow in a line
		for (int i = 0; i < numWakes; i++) {
			int deltaY = (int) (Wake.WAKE_DIST * Math.sin(angle - Wake.kelvinWaveAngle));
			int deltaX = (int) (Wake.WAKE_DIST * Math.cos(angle - Wake.kelvinWaveAngle));
			int x = pos.x - deltaX * i;
			int y = pos.y + deltaY * i;
			wakes.add(new Wake(new Point(x, y), angle*-1 + Math.PI, 0));
			wakes.add(new Wake(new Point(x, y), angle*-1 - 2*Math.PI, 1));
		}
		return wakes;
	}

	public void reset(Point loc) {
		pos.move(loc.x, loc.y);
		angle = 0;
		velocity = 0;
		canPickUp = false;
		canDropOff = false;
		holding = ITEM.NONE;
	}
	
	public void moveAway(Point loc) {
		pos.move(loc.x, loc.y);
	}

}
