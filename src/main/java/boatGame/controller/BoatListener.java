package main.java.boatGame.controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.java.boatGame.model.BoatWorld;

public class BoatListener implements MouseListener{
	BoatController controller;
	
	public BoatListener(BoatController controller){
		this.controller = controller;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		//Point mouse = arg0.getLocationOnScreen();
		//controller.world.mouse = new Point((int)(mouse.x/BoatWorld.widthRatio), (int)(mouse.y/BoatWorld.heightRatio));
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {//TODO - pause?
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (BoatController.gameOver == true) {
			controller.dispose();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (BoatController.paused == false) {
			controller.world.prevMouse = controller.world.mouse;
			Point mouse = arg0.getLocationOnScreen();
			controller.world.mouse = new Point((int) (mouse.x / BoatWorld.widthRatio),
					(int) (mouse.y / BoatWorld.heightRatio));
			// controller.world.mouse = mouse;
		}
		else {
			BoatController.paused = false;
		}
	}


}
