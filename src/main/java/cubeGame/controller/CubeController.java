package main.java.cubeGame.controller;

import javax.swing.Timer;

import main.java.cubeGame.model.CubeWorld;
import main.java.cubeGame.view.CubeGameScreen;
import main.java.menu.controller.MGController;
import main.java.menu.view.MenuScreen;

/**
 * * @author Cathrine and Collin Controls the Cube game. Detects screen size and
 * creates a cubeWorld and an animation.
 */
public class CubeController extends MGController {
	final int timerTick = 17;
	CubeWorld world;
	CubeGameScreen view;
	CubeListener listener;
	Timer timer; // Constantly update the view and model

	public boolean recording = false;

	public CubeController(MenuScreen menu) {
		view = new CubeGameScreen(this, menu);
		world = new CubeWorld();
		listener = new CubeListener(this);
		timer = new Timer(timerTick, listener); // The timer triggers an actionevent in cubeListener every frame
		view.addMouseListener(listener);
		view.addMouseMotionListener(listener);
	}


	@Override
	public void dispose() {
		timer.stop();
		world.reset();
		view.setVisible(false);
		view.reset();
		view.showTutorialScreen(); // Resets settings so tutorial screen showsfor next player
	}

	public CubeWorld getWorld() {
		return world;
	}

	@Override
	public CubeGameScreen getView() {
		return view;
	}

	public Timer getTimer() {
		return timer;
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub
	}
}
