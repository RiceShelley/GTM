package main.java.boatGame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import main.java.boatGame.model.BoatWorld;
import main.java.boatGame.view.BoatGameScreen;
import main.java.menu.controller.MGController;
import main.java.menu.controller.MGView;
import main.java.menu.view.MenuScreen;


public class BoatController extends MGController implements ActionListener {
	public BoatWorld world;
	MenuScreen menu;
	public BoatGameScreen view;
	public Timer timer;
	int frameWait = 17;
	private int millisPassed = 0;
	public final int milliTimeLimit = 60000; // 2 mins = 120,000 milliseconds
	public static boolean gameOver = false;
	private final static int mt = 15;
	private final static int dt = 15;
	private final static int qt = 100; //frameWait *qt = num of milliseconds before prompting
	private final static int st = 3; //st * view.NFF = frames it should play
	private static boolean MCount = false;
	private static boolean DCount = false;
	private static boolean QCount = false;
	private static boolean SCount = false;
	private static long ct = 0;
	private static long mct = 0;
	private static long dct = 0;
	private static long qct = 0;
	private static long sct = 0;
	public static boolean paused = false;
	
	public BoatController(MenuScreen menu) {
		this.menu = menu;
		world = new BoatWorld();
		view = new BoatGameScreen(this);
		view.addMouseListener(new BoatListener(this));
		timer = new Timer(frameWait, this);
		paused = true;
	}

	@Override
	public void update() {
		view.updateLabels();
		
		if (!paused) {
			world.update();
			view.repaint();
		}
	}

	@Override
	public void dispose() {
		timer.stop();
		menu.hideMenuButton();
		millisPassed = 0;
		world.reset();
		view.reset();
		MCount = false;
		DCount = false;
		QCount = false;
		ct = 0;
		mct = 0;
		dct = 0;
		qct = 0;
		paused = true;
		view.setVisible(false);
		gameOver = false;
	}
	
	public void replay() {
		menu.replayBoatGame();
	}
	
	@Override
	public MGView getView() {
		return view;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) { //tick method	
		ct++;
		count();
		update();
		
		if (!paused) {
			millisPassed += frameWait;
			gameOver = millisPassed >= milliTimeLimit;
		}
		
		if (world.oysters >= 3 && world.rocks >= 3 && world.cordgrass >= 3)
			gameOver = true;
		
		if (gameOver) {
			 view.displayEnd();
			 timer.stop();
		}

	}

	public int getMillisPassed() {
		return millisPassed;
	}

	private void count() {
		view.setOK();
		if (MCount && ct - mct > mt) {
			if (world.hasMoved) {
				MCount = false;
			}
			view.setmDisplay(MCount);
		} else if (DCount && ct - dct > dt) {
			if (world.hasPickedUp) {
				DCount = false;
			}
			view.setdDisplay(DCount);
		} else if (QCount && ct - qct > qt) {
			if (world.canPickUp) {
				QCount = false;
			} 
			view.setqDisplay(QCount);
		}
		if (SCount) {
			if (ct-sct > st*view.NFF) {
				SCount = false;
			}
			view.setsDisplay(SCount);
		}
	}
	
	public static void startMCount(){
		stopCounts();
		MCount = true;
		mct = ct;
	}
	
	public static void startDCount(){
		stopCounts();
		DCount = true;
		dct = ct;
	}

	public static void startQCount(){
		stopCounts();
		QCount = true;
		qct = ct;
	}
	
	public static void stopCounts(){
		MCount = false;
		DCount = false;
		QCount = false;
	}

	public static void startSCount() {
		SCount = true;
		sct = ct;
	}
	
}
