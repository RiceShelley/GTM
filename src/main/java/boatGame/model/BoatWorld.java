package main.java.boatGame.model;

import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import main.java.boatGame.controller.BoatController;
import main.java.boatGame.enums.ITEM;
import main.java.boatGame.enums.STATE;
import main.java.menu.enums.IMAGES;
import main.java.menu.view.MenuScreen;

public class BoatWorld {
	private final static int fw = MenuScreen.frameWidth;
	private final static int fh = MenuScreen.frameHeight;
	private final int space = 100; //space from edge on bg
	BufferedImage bg = MenuScreen.IMAGE.get(IMAGES.BOATS_BG);
	public final static double widthRatio = fw * 1.0
			/ MenuScreen.IMAGE.get(IMAGES.BOATS_BG).getWidth();
	public final static double heightRatio = fh * 1.0
			/ MenuScreen.IMAGE.get(IMAGES.BOATS_BG).getHeight();
	public GeneralPath waterBoundary;
	final ArrayList<Point> coords = Boundaries.findCoords(bg);
	ArrayList<Point> spots = new ArrayList<Point>();
	private final Point boatStart = new Point(bg.getWidth() / 2, bg.getHeight() / 2);
	private final int shoreSpace = 700;
	public int shoreSize;
	public final static int qRadius = 50;
	public final static int dRadius = 100;
	private final int questFreq = 75; // used in rand.nextInt(questFreq)
	private final double dmgMulti = .5;

	public Point mouse;
	public Point prevMouse;
	public final Boaty boat = new Boaty(boatStart);
	public ArrayList<Wake> wakes = new ArrayList<Wake>();
	private ArrayList<Integer> spaces = new ArrayList<Integer>();
	public ArrayList<Quest> quests = new ArrayList<Quest>();
	public ArrayList<Dock> docks = new ArrayList<Dock>();
	public ArrayList<Point> hits = new ArrayList<Point>();
	public boolean canPickUp;
	public int score;
	public int oysters, rocks, cordgrass; //amount of each item collected
	Random rand = new Random();
	
	public STATE state;
	public boolean hasMoved;
	public boolean hasPickedUp;
	
	public Quest firstO;
	public Quest firstR;
	public Quest firstC;
	
	private final int leftMassX = 995; //where the left most land mass ends

	public BoatWorld() {
		drawBounds();
		reset();
	} // Constructor

	public void reset() {
		shoreSize = spots.size() / shoreSpace;
		System.out.println("Shore size: " + shoreSize);
		System.out.println("Spots size: " + spots.size());
		boat.reset(boatStart);
		mouse = boatStart;
		prevMouse = mouse;
		wakes.clear();
		spaces.clear();
		docks.clear();
		quests.clear();
		hits.clear();
		initializeDocks();
		canPickUp = false;
		score = 100;
		oysters = 0;
		rocks = 0;
		cordgrass = 0;
		setState(STATE.MOVE);
		hasMoved = false;
		hasPickedUp = false;
	}

	/**
	 * Initializes waterBoundary using points returned from
	 * Boundaries.findCoords(BufferedImage)
	 */
	private void drawBounds() {
		waterBoundary = new GeneralPath(Path2D.WIND_NON_ZERO, coords.size());
		Point start = coords.get(0);
		waterBoundary.moveTo(start.getX(), start.getY());
		for (Point p : coords) {
			waterBoundary.lineTo(p.getX(), p.getY());
			if (p.x > space && p.y > space  && p.x < bg.getWidth()-space && p.y < bg.getHeight()-space) {
				spots.add(p);
			}
		}
		waterBoundary.closePath();
	}

	/**
	 * Initializes a randomly placed warehouse for each ITEM enum except for the
	 * first.
	 */
	private void initializeDocks() {
		ITEM[] items = ITEM.values();
		int index;
		for (int i = 0; i < items.length-1; i++) {
			do {
				index = rand.nextInt(shoreSize) * shoreSpace;
			} while (spots.get(index).x >= leftMassX || spaces.contains(index));
			spaces.add(index);

			Point loc = spots.get(index); // new Point(coords.get(index));
											// //findOpenSpot;
			docks.add(new Dock(items[i], loc.x, loc.y));
		}
	}

	public void update() {
		moveBoat();
		moveWake();
		if (spaces.size() < shoreSize && rand.nextInt(questFreq) == 0) {
			addQuest();
		}
		updateQuests();
		firstC = null;
		firstO = null;
		firstR = null;
		for (int i = quests.size()-1; i >= 0; i --) {
			switch (quests.get(i).wanted) {
			case CORDGRASS:
				firstC = quests.get(i);
				break;
			case OYSTER:
				firstO = quests.get(i);
				break;
			case ROCK:
				firstR = quests.get(i);
				break;
			default:
				System.out.println("Woops");
				break;
			}
		}
	} // update

	private void updateQuests() {
		Iterator<Quest> i = quests.iterator();
		while (i.hasNext()) {
			Quest q = i.next();
			if (q.active && q.health <= 0) {
				spaces.remove(spaces.indexOf(spots.indexOf(q.pos)));
				i.remove();
			} else if (q.active) {
				q.health--;
			}
		}
	}


	private void moveWake() {
		Iterator<Wake> i = wakes.iterator();
		while (i.hasNext()) {
			Wake w = i.next();
			if (!w.active) {
				i.remove();
			} else if (waterBoundary.contains(w.pos)) {
				w.move();
			} else {
				boolean notNearQuest = true;
				for (Quest q : quests) {
					if (q.active && Point2D.distance(q.pos.getX(), q.pos.getY(), w.pos.getX(), w.pos.getY()) <= qRadius) {
						notNearQuest = false;
					}
				}
				if (notNearQuest && w.pos.x < bg.getWidth() && w.pos.x >= 0 && w.pos.y < bg.getHeight() && w.pos.y >= 0) {
					score -= w.amplitude * dmgMulti;
					hits.add(w.pos);
					BoatController.startSCount();
				}
				i.remove();
			} // if else
		} // while
	}

	private void moveBoat() { //TODO: recursive edge finding algorithm
		// collision with shore
		mouse = findEdge(mouse);

		if (Math.abs(mouse.x - boat.getXCoord()) >= Boaty.EASING
				|| Math.abs(mouse.y - boat.getYCoord()) >= Boaty.EASING) {
			wakes.addAll(boat.move(mouse));
			if (state == STATE.MOVE) {
				setState(STATE.DOCK);
			}
		}
		for (Quest q : quests) {
			if (Point2D.distance(boat.getXCoord(), boat.getYCoord(), q.pos.getX(), q.pos.getY()) <= qRadius
					&& boat.holding == q.wanted) {
				score += q.WORTH;
				q.active = true;
				boat.holding = ITEM.NONE;
				setState(STATE.OK);
				removeClosestHits(boat.getXCoord(), boat.getYCoord());
				
				//Check for what item to increment the count
				if (q.wanted == ITEM.ROCK) {
					rocks++;
				}
				else if (q.wanted == ITEM.CORDGRASS) {
					cordgrass++;
				}
				else if (q.wanted == ITEM.OYSTER) {
					oysters++;
				}
			}
			
		}
		for (Dock w : docks) {
			if (Point2D.distance(boat.getXCoord(), boat.getYCoord(), w.pos.getX(), w.pos.getY()) <= dRadius
					) {
				boat.holding = w.stored;
				setState(STATE.QUEST);
			}
		}
	}

	private Point findEdge(Point p) {
		if (waterBoundary.contains(p)){
			return p;
		} else {
			int h = (int) p.distance(new Point(boat.getXCoord(), boat.getYCoord()));
			for (int i = h; i > -1; i --) {
				double angle = Math.atan2(p.getY()-boat.getYCoord(), p.getX() - boat.getXCoord());
				//System.out.println("i: " + i + " angle:  " + angle);
				p.move((int) (boat.getXCoord() + i * Math.cos(-1*angle)), (int) (boat.getYCoord() + i * Math.sin(angle)));
				if (waterBoundary.contains(p)) {
					return p;
				}
			}
		}
		return null;
	}

	private void removeClosestHits(int xCoord, int yCoord) {
		if (hits.size() <= 0) {
			return;
		}
		for (int i = 0;  i < hits.size(); i ++) {
			if (Math.abs(hits.get(i).x-xCoord) <= qRadius ) {
				hits.remove(i);
				return;
			}
		}
		hits.remove(0);
	}

	/**
	 * randomly selects an enum and calls findOpenSpot() to initialize a quest
	 */
	private void addQuest() {
		ITEM[] items = ITEM.values();
		Point loc = findOpenSpot();
		
		if (loc != null) {
			if (quests.isEmpty() && boat.holding != ITEM.NONE) {
				quests.add(new Quest(boat.holding, loc.x, loc.y));
			} else if (boat.holding == ITEM.CORDGRASS && firstC == null) {
				quests.add(new Quest(boat.holding, loc.x, loc.y));
			} else if (boat.holding == ITEM.OYSTER && firstO == null) {
				quests.add(new Quest(boat.holding, loc.x, loc.y));
			} else if (boat.holding == ITEM.ROCK && firstR == null) {
				quests.add(new Quest(boat.holding, loc.x, loc.y));
			} else {
				quests.add(new Quest(items[rand.nextInt(ITEM.values().length - 1)], loc.x, loc.y));
			}
		}
	}

	/**
	 * Randomly selects an int for an index until it finds one that isn't in
	 * spaces and returns the corresponding point.
	 * 
	 * @return random point that is not the location of a warehouse or active
	 *         quest
	 */
	private Point findOpenSpot() {
		int index;
		if (spaces.size() == shoreSize) {
			return null;
		}
		do {
			index = rand.nextInt(shoreSize) * shoreSpace;
			
		} while (spaces.contains(index));
		spaces.add(index);
		return new Point(spots.get(index));
	}

	public void setState(STATE state) {
		this.state = state;
		switch (state) {
		case DOCK:
			BoatController.startDCount();
			break;
		case MOVE:
			BoatController.startMCount();
			break;
		case OK:
			BoatController.stopCounts();
			break;
		case QUEST:
			BoatController.startQCount();
			break;
		}
	}
	
}
