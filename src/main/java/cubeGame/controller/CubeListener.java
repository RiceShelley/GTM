package main.java.cubeGame.controller;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import main.java.cubeGame.model.CubeWorld;
import main.java.cubeGame.model.Die;
import main.java.cubeGame.model.DieHolder;

public class CubeListener implements MouseInputListener, ActionListener {
	CubeController control;
	private int selectedImage;
	private int curX; // Mouse's X position when clicked
 	private int curY; // Mouse's Y position when clicked
	private boolean dragging; // is the mouse currently clicked
	private int nextX;  // Updates mouse positions when dragging
	private int nextY;
	
	
	public CubeListener(CubeController control) {
		this.control = control;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Die.dontCheckBounds();
		Point point = e.getPoint();
		selectedImage = getSelected(point);
		curX = point.x;
		curY = point.y;
		dragging = true;
	}
	
	/*
	 * Returns the index of the die currently selected by the mouse
	 */
	private int getSelected(Point p) { 
		control.getWorld();
		for (int i = 0; i < CubeWorld.dice.length; i ++) {
			control.getWorld();
			if (CubeWorld.dice[i].bounds.contains(p)) {
				control.getWorld();
				CubeWorld.dice[i].setPlaced(false); // While the die is selected, it can't be placed
				for (DieHolder holder : control.getWorld().markers) { // Deselect the current Holder
					control.getWorld();
					if (holder.containsDie() && holder.getDie() == CubeWorld.dice[i]) {
						holder.removeDie();
						break;
					}
				}
				return i;
			}
		}
		return -1;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int sel = getSelected(e.getPoint()); // Index of the die selected
		dragging = false;
		Rectangle markBounds = new Rectangle(0, 0, 0, 0);
		DieHolder selectedHolder = null;
		for (int i = 0; i < control.getWorld().markers.length; i ++) {
			markBounds.setBounds(control.getWorld().markers[i].x - Die.WIDTH, control.getWorld().markers[i].y - Die.HEIGHT, Die.WIDTH, Die.HEIGHT);
			if (sel >=0 && CubeWorld.dice[sel].bounds.intersects(markBounds)) {
				selectedHolder = control.getWorld().markers[i];
			}
		}
		if (selectedHolder != null && !selectedHolder.containsDie()) { //die snaps to marker
			selectedHolder.placeDie(CubeWorld.dice[sel]); // Place the die on the corresponding marker
			CubeWorld.dice[sel].translate(-1*CubeWorld.dice[sel].bounds.x, -1*CubeWorld.dice[sel].bounds.y);
			CubeWorld.dice[sel].translate(selectedHolder.x-Die.WIDTH, selectedHolder.y-Die.HEIGHT);
			CubeWorld.dice[sel].setPlaced(true); // Marks the die as PLACED
		}
		// If all dice are placed, show the submit button
		if (control.getWorld().allDicePlaced()) {
			control.getView().showSubmitButton();
		} else {
			control.getView().hideSubmitButton();
		}
		Die.doCheckBounds();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point curPoint = e.getPoint();
		nextX = curPoint.x;
		nextY = curPoint.y;
		if (dragging) {
			if (selectedImage >= 0) {
				control.getWorld();
				CubeWorld.dice[selectedImage].translate((nextX - curX), (nextY - curY));
				control.getView().repaint();
			}
			curX = nextX;
			curY = nextY;
		} 
	}

	@Override
	/*
	 * An internal timer constantly updates the view 
	 */
	public void actionPerformed(ActionEvent arg0) {
		control.getView().repaint();
		control.world.update();
	}
	
	/*
	 *Unused MouseListener Methods
	 */
	
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}


}
