package main.java.boatGame.model;


import java.awt.Point;
import main.java.boatGame.enums.ITEM;

public class Dock{
	public final static int WIDTH = 150;
	public final static int HEIGHT = 70;
	public final Point pos = new Point();
	public ITEM stored;
	
	public Dock (ITEM stored, int xLoc, int yLoc){
		this.pos.move(xLoc, yLoc);
		this.stored = stored;
	}
	
}
