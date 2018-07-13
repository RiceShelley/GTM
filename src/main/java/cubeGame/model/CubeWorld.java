package main.java.cubeGame.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import main.java.cubeGame.controller.CubeListener;
import main.java.cubeGame.enums.STATE;
import main.java.menu.view.MenuScreen;

public class CubeWorld {
	public final Die[] dice = new Die[5];
	private int h = MenuScreen.frameHeight;
	private int w = MenuScreen.frameWidth;
	public final Point[] markers = { new Point((w - 5 * Die.WIDTH) / 2, h / 3 - Die.HEIGHT / 2),
			new Point((w - 2 * Die.WIDTH) / 2, h / 3 - Die.WIDTH / 2),
			new Point((w + Die.WIDTH) / 2, h / 3 - Die.HEIGHT / 2),
			new Point((w + 4 * Die.WIDTH) / 2, h / 3 - Die.HEIGHT / 2),
			new Point((w + 7 * Die.WIDTH) / 2, h / 3 - Die.HEIGHT / 2) };
	public final Rectangle rollZone = new Rectangle(0, h / 3, w, 2 * h / 3);
	@SuppressWarnings("unused")
	private STATE state;
	public static final Random RAND = new Random();
	
	public boolean hasRolled = false;
	public boolean hasMoved = false;
	

	public CubeWorld() {
		for (int i = 0; i < dice.length; i++) {
			dice[i] = new Die(diceLoc());
		}
		setState(STATE.ROLL);
	}
	
	public void update(){
		for (Die d : dice){
			Rectangle intersection = rollZone.intersection(d.bounds);
			if(d.isRolling()){
				if (intersection.equals(null)){
					d.gameReset(diceLoc());
					System.out.println("Something went wrong. Resetting.");
				} else {
					
					if (intersection.width < Die.WIDTH){
						d.bounceX(Die.WIDTH - intersection.width);
					} //if width
					if (intersection.height < Die.HEIGHT) {
						d.bounceY(Die.HEIGHT - intersection.height);
					} //if height
				} //if else
			}
			d.move();
		} //for
	} //update()
	
	public void rollDice(){
		for (Die d : dice) {
			if (d.bounds.intersects(rollZone))
			d.roll();
		}
	}
	
	private Point diceLoc(){
		int y = RAND.nextInt(rollZone.height - Die.HEIGHT) + rollZone.y;
		int x = RAND.nextInt(rollZone.width - Die.WIDTH) + rollZone.x;
		return new Point(x, y);
	}

	public void reset() {
		for (Die d : dice) {
			d.gameReset(diceLoc());
		}
		hasRolled = false;
	}
	
	public boolean checkBounds() {
		for (Die d : dice) {
			if (d.bounds.intersects(rollZone)) {
				return false;
			}
		}
		return true;
	}

	public Die[] getDice() {
		return dice;
	}

	public int getRollWidth() {
		return rollZone.width;
	}
	
	public int getRollHeight() {
		return rollZone.height;
	}
	
	public void setState(STATE state) {
		this.state  = state;
		switch (state) {
		case MOVE:
			System.out.println("Switching state to MOVE");
			CubeListener.startMCount();
			break;
		case OK:
			CubeListener.setOK();
			break;
		case ROLL:
			System.out.println("Switching state to ROLL");
			CubeListener.startRCount();
			break;
		}
	}
}
