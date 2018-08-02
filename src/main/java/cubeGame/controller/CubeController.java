package main.java.cubeGame.controller;

import javax.swing.Timer;

import main.java.cubeGame.model.CubeWorld;
import main.java.cubeGame.view.CubeGameScreen;
import main.java.menu.controller.MGController;

/**
 * 
 * @author Cathrine and Collin
 *Controls the Cube game. Detects screen size and creates a cubeWorld and an animation.
 */
public class CubeController extends MGController{
	final int timerTick = 17;
	CubeWorld world;
	CubeGameScreen view;
	CubeListener listener;
	Timer timer; //Constantly update the view and model
	
	public boolean recording = false;
	
	public CubeController(){
		view = new CubeGameScreen(this);
		world = new CubeWorld();
		listener = new CubeListener(this);
		timer = new Timer (timerTick, listener); // The timer triggers an actionevent in cubeListener every frame
		view.addMouseListener(listener);
		view.addMouseMotionListener(listener);
		timer.start();
	}
	
	@Override
	public void dispose() {
		world.reset();
		view.setVisible(false);
		timer.stop();
		view.reset();
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
	
	public void submit() {
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
	}
}
