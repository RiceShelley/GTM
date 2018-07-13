package main.java.boatGame.model;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public final class Boundaries {
	private static BufferedImage img;
	private static ArrayList<Point> points;
	private static Point start;
	private static Point cur;
	private static Point split;
	
	private Boundaries(){}
	
	/**
	 * Generates a list of points from the parameter that can be connected to
	 * build a java.awt.geom.GeneralPath
	 * 
	 * @param img
	 *            BufferedImage from which boundary is to be found
	 * @return an arraylist of java.awt.Point objects
	 */
	public static ArrayList<Point> findCoords(BufferedImage img) {
		Boundaries.img = img;
		points = new ArrayList<Point>();

		setPoints();

		do {
			findNext();
		} while (!start.equals(cur));

		return points;
	} // findCoords(BufferedImage)

	/**
	 * Goes through the values of array alphas until it finds a transparent
	 * pixel and sets points start, previous, and current to the row and column
	 * of first transparent pixel.
	 */
	private static void setPoints() {
		start = new Point(-1, -1);
		boolean searching = true;
		for (int j = 0; j < img.getHeight(); j++) {
			for (int i = 0; i < img.getWidth(); i++) {
				if (searching && ((img.getRGB(i, j) & 0xff000000) >> 24) >= 0) {
					searching = false;
					start.setLocation(i, j);
					points.add(start);
				} // if
			} // for i
		} // for j
		cur = new Point(start);
		split = new Point(start);
	} // setPoints()

	/**
	 * Uses the prev Point and cur Point to find the next Point in the desired
	 * path
	 * 
	 */
	private static void findNext() {
		Point next = new Point();
		ArrayList<Point> potential = adjacentPoints(cur);

		if (potential.isEmpty()) {
			backTrack(next);
		} else {
			if (potential.size() > 1) {
				split.setLocation(cur); // if cur has more than 1 potential next
			} // if places to go, mark it here
			next.setLocation(potential.get(0));
		} // if else

		points.add(next);
		cur.setLocation(next);

	} // findNext()

	/**
	 * Finds adjacent points of point passed in that are within bounds of array alphas and
	 * returns them.
	 * 
	 * @param point
	 *            Point you want adjacent points of
	 * @return arraylist containing up to 4 points
	 */
	private static ArrayList<Point> adjacentPoints(Point point) {
		ArrayList<Point> adjacent = new ArrayList<Point>();
		if (point.x + 1 < img.getWidth())
			adjacent.add(new Point(point.x + 1, point.y));
		if (point.y + 1 < img.getHeight())
			adjacent.add(new Point(point.x, point.y + 1));
		if (point.x - 1 >= 0)
			adjacent.add(new Point(point.x - 1, point.y));
		if (point.y - 1 >= 0)
			adjacent.add(new Point(point.x, point.y - 1));
		
		trimList(adjacent);
		return adjacent;
	} // adjacentPoints(Point)

	/**
	 * Takes a list of potential next points and trims it down
	 * 
	 * @param list
	 *            ArrayList of points
	 */
	private static void trimList(ArrayList<Point> list) {
		Iterator<Point> i = list.iterator();
		while (i.hasNext()) {
			Point p = i.next();
			if (((img.getRGB(p.x, p.y) & 0xff000000) >> 24) < 0) { // not transparent
				i.remove();
			} else if (isOpenWater(p)) {
				i.remove();
			} else if (points.contains(p)) {
				i.remove();
			} // if else if else if
		} // while iterator has next
	} // trimList(ArrayList)

	/**
	 * Returns true if parameter has eight adjacent points within the bounds of
	 * alpha and all of them are transparent
	 * 
	 * @param point
	 * @return true/false
	 */
	private static boolean isOpenWater(Point point) {
		ArrayList<Point> tempPoints = surroundingPoints(point);
		if (tempPoints.size() < 8) { // 8 is the number of surrounding squares
			return false; // in a grid
		} // if
		for (Point p : surroundingPoints(point)) {
			if (((img.getRGB(p.x, p.y) & 0xff000000) >> 24) < 0) {
				return false;
			} // if
		} // for
		return true;
	} // isOpenWater(Point)

	/**
	 * Finds surrounding points of point that are within bounds of array alphas
	 * and returns them.
	 * 
	 * @param point
	 *            you want the surrounding points of
	 * @return arraylist containing up to 8 points
	 */
	private static ArrayList<Point> surroundingPoints(Point point) {
		ArrayList<Point> surrounding = new ArrayList<Point>();
		int rightBound = point.x + 1;
		int leftBound = point.x - 1;
		int topBound = point.y - 1;
		int bottomBound = point.y + 1;

		if (rightBound >= img.getWidth())
			rightBound--;
		if (bottomBound >= img.getHeight())
			bottomBound--;
		if (leftBound < 0)
			leftBound++;
		if (topBound < 0)
			topBound++;

		for (int x = leftBound; x <= rightBound; x++) {
			for (int y = topBound; y <= bottomBound; y++) {
				if (!(point.x == x && point.y == y)) {
					surrounding.add(new Point(x, y));
				} // if
			} // for y
		} // for x

		return surrounding;
	} // surroundingPoints(Point)

	/**
	 * Function called when algorithm backs itself into corner. Goes back to
	 * last spot there was more than one potential next point and continues from
	 * there.
	 * 
	 * @param next
	 *            Point that is to be the next point in the algorithm
	 */
	private static void backTrack(Point next) {
		next.setLocation(split);
		ArrayList<Point> potential = adjacentPoints(next);
		if (potential.isEmpty()) {
			next.setLocation(start);
		} else {
			next = potential.get(0);
		} // if else
	} // backTrack(Point)

}
