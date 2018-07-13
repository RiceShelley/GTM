package main.java.boatGame.enums;

import main.java.menu.enums.IMAGES;

public enum ITEM {
	OYSTER(IMAGES.BOAT_O, IMAGES.OYSTER, 125),
	CORDGRASS(IMAGES.BOAT_C, IMAGES.CORDGRASS, 75),
	ROCK(IMAGES.BOAT_R, IMAGES.ROCK, 125),
	NONE (IMAGES.BOAT_N, IMAGES.BLANK, 0);
	
	public final static int WIDTH = 55;
	public final static int HEIGHT = 55;
	public final IMAGES boatImg;
	public final IMAGES img;
	public final int intitHealth;
	
	
	private ITEM (IMAGES boatImg, IMAGES img, int initHealth) {
		this.boatImg = boatImg;
		this.img = img;
		this.intitHealth = initHealth;
	}
}
