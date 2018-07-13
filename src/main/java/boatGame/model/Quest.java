package main.java.boatGame.model;

import java.awt.Point;
import main.java.boatGame.enums.ITEM;

public class Quest{
	public final static int WIDTH = 70;
	public final static int HEIGHT = 70;
	public final int WORTH = 10;
	public int health;
	public int curFrame = 0;
	public final Point pos = new Point();
	public ITEM wanted;
	public boolean active = false;
	
	static int completed = 0;
	
	public Quest(ITEM wanted, int xLoc, int yLoc){
		this.pos.move(xLoc, yLoc);
		this.wanted = wanted;
		health = wanted.intitHealth;
	}
	
	public void updateCur(int frameTotal){
		curFrame = (curFrame+1)%frameTotal;
	}
}
