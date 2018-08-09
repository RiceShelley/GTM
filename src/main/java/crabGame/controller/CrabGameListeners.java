package main.java.crabGame.controller;

import main.java.crabGame.CrabController;
import main.java.crabGame.CrabGamePanel;
import main.java.crabGame.model.CrabGameWorld;
import main.java.crabGame.model.Crabby;
import main.java.crabGame.model.Friend;
import main.java.crabGame.model.Question;
import main.java.menu.view.MenuScreen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import java.util.Collections;

import static main.java.crabGame.CrabController.paused;

/**
 * Created by Alina on 2017-07-30.
 */
public class CrabGameListeners implements KeyListener, MouseListener, MouseMotionListener {

	// Keyboard actions //

	@Override
	public void keyTyped(KeyEvent e) {
		// System.out.println("KeyTyped: " + e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// System.out.println("KeyPressed: " + e);

		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_UP) {
			CrabGameWorld.moveCrabbyUp();
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			CrabGameWorld.moveCrabbyRight();
		}
		if (keyCode == KeyEvent.VK_LEFT) {
			CrabGameWorld.moveCrabbyLeft();
		}
		if (keyCode == KeyEvent.VK_DOWN) {
			CrabGameWorld.moveCrabbyDown();
		}
		if (keyCode == KeyEvent.VK_SPACE) {
			paused = !paused;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	// Mouse actions //

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (!CrabController.running)
			CrabController.startGame();

		if (CrabController.game.checkGameStateReady())
			CrabController.game.setGameStateToPlayEntry();
		else if (CrabController.game.checkGameStatePlay() || CrabController.game.checkGameStatePlayEntry()) {
			Point p = e.getPoint();
			Crabby crab = CrabGameWorld.crabbies.get(0);
			if (crab.getxPos() + (crab.getBounds().getWidth() / 2) < p.x) {
				CrabGameWorld.moveCrabbyRight();
			} else {
				CrabGameWorld.moveCrabbyLeft();
			}
			CrabGameWorld.moveCrabbyUp();
		} else if (CrabController.game.checkGameStateContinue())
			CrabController.reset();
		else if (CrabController.game.checkGameStateWin())
			CrabController.reset();
		// see if player clicked right answer
		if (CrabController.paused) {
			Point p = e.getPoint();
			int offset = 50;
			int ans = 0;
			for (int i = 0; i < 3; i++) {
				int start = (MenuScreen.frameHeight / 2 + offset) - 18;
				int end = start + 25;
				if (p.getY() > start && p.getY() < end) {
					ans = i;
					break;
				}
				offset += 50;
			}
			String str = Question.questions.get(CrabGamePanel.qNum).getAnswers().get(ans);
			if (Question.questions.get(CrabGamePanel.qNum).isRightAnswer(str)) {
				Question.qaState = 1;
			} else {
				Question.qaState = -1;
			}

		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
