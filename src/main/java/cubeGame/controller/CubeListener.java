package main.java.cubeGame.controller;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;
import main.java.cubeGame.model.Die;

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
		for (int i = 0; i < control.getWorld().dice.length; i ++) {
			if (control.getWorld().dice[i].bounds.contains(p)) {
				control.getWorld().dice[i].setPlaced(false); // While the die is selected, it can't be placed
				return i;
			}
		}
		return -1;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int sel = getSelected(e.getPoint());
		int marker = -1;
		dragging = false;
		Rectangle markBounds = new Rectangle(0, 0, 0, 0);
		for (int i = 0; i < control.getWorld().markers.length; i ++) {
			markBounds.setBounds(control.getWorld().markers[i].x - Die.WIDTH, control.getWorld().markers[i].y - Die.HEIGHT, Die.WIDTH, Die.HEIGHT);
			if (sel >=0 && control.getWorld().dice[sel].bounds.intersects(markBounds)) {
				marker = i;
			}
		}
		if (marker >= 0) { //die snaps to marker
			control.getWorld().dice[sel].translate(-1*control.getWorld().dice[sel].bounds.x, -1*control.getWorld().dice[sel].bounds.y);
			control.getWorld().dice[sel].translate(control.getWorld().markers[marker].x-Die.WIDTH, control.getWorld().markers[marker].y-Die.HEIGHT);
			control.getWorld().dice[sel].setPlaced(true); // Marks the die as PLACED
		}
		// If all dice are placed, show the submit button
		if (control.getWorld().allDicePlaced()) {
			control.getView().showSubmitButton();
		} else {
			control.getView().hideSubmitButton();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point curPoint = e.getPoint();
		nextX = curPoint.x;
		nextY = curPoint.y;
		if (dragging) {
			if (selectedImage >= 0) {
				control.getWorld().dice[selectedImage].translate((nextX - curX), (nextY - curY));
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
