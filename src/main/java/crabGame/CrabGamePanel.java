package main.java.crabGame;

import java.awt.Graphics;

import main.java.crabGame.view.*;
import main.java.menu.controller.MGView;

/**
 * @author Zach handles the visuals of the CrabGameWorld
 */
public class CrabGamePanel extends MGView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Initializes the GamePanel
	 */
	CrabGamePanel() {

	}

	/**
	 * Called every frame, draws something depending on the state of the gamePanel
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		WorldRenderer.render(g);

		if (CrabController.game.checkGameStatePlay() || CrabController.game.checkGameStatePlayEntry()) {
			PlayScreen.drawPlay(g);
		} else if (CrabController.game.checkGameStateReady()) {
			ReadyScreen.drawReady(g);
		} else if (CrabController.game.checkGameStateContinue()) {
			ContinueScreen.drawContinueScreen(g);
		} else if (CrabController.game.checkGameStateWin()) {
			WinScreen.drawWin(g);
		}
	}

	/**
	 * Tells the CrabController to start after everything has been loaded
	 */
	@Override
	public void addNotify() {
		super.addNotify();
		requestFocusInWindow();
	}
}
