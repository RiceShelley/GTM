package main.java.boatGame.model;

import java.awt.Point;

public class Wake {
	public final static int WIDTH = 35;
	public final static int HEIGHT = 75;
	public final static int WAKE_DIST = 40;
	public final static int VELOCITY = 7;
	public Point pos;
	public int amplitude = 20;
	public int dir;
	public double angle;
	public boolean active = true;
	public final static double kelvinWaveAngle = Math.asin(1/3);

	public Wake(Point position, double angle, int dir) {
		this.pos = position;
		this.angle = angle;
		this.dir = dir;
	}

	public void move() {
		pos.translate((int) (VELOCITY * Math.sin(angle)), (int) (VELOCITY * Math.cos(angle)));
		if (amplitude > 0) {
			amplitude --;
		} else {
			active = false;
		}
	}

}
