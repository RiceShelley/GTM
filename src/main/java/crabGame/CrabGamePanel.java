package main.java.crabGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Timer;

import main.java.crabGame.model.CrabGameWorld;
import main.java.crabGame.model.Mover;
import main.java.crabGame.model.Question;
import main.java.crabGame.view.*;
import main.java.menu.controller.MGView;
import main.java.menu.enums.IMAGES;
import main.java.menu.view.ImageManager;
import main.java.menu.view.MenuScreen;

/**
 * @author Zach handles the visuals of the CrabGameWorld
 */
public class CrabGamePanel extends MGView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int qNum = 0;

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
			g.drawImage(ImageManager.get(IMAGES.CRAB_TUTORIAL), 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight, null);
		} else if (CrabController.game.checkGameStateContinue()) {
			ContinueScreen.drawContinueScreen(g);
		} else if (CrabController.game.checkGameStateWin()) {
			CrabGameWorld.crabbies.get(0).setState(Mover.State.SAFE);
			WinScreen.drawWin(g);
		}
		// draw question
		if (CrabController.paused) {
			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(), 40));
			Question q = Question.questions.get(qNum);
			String q_str = q.getPrompt();
			int titleLength = g.getFontMetrics().stringWidth(q_str);
			int yBoxW = titleLength + 100;
			int yBoxX = (MenuScreen.frameWidth - titleLength) / 2 - 50;
			
			// draw bg
			g.drawImage(ImageManager.get(IMAGES.TUT_BG), 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight, null);
			
			g.setColor(Color.BLACK);
			g.drawString(q_str, (MenuScreen.frameWidth - titleLength) / 2, MenuScreen.frameHeight / 2);
			// draw possible answers
			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(), 40));
			ArrayList<String> ans = q.getAnswers();
			int offset = 50;
			for (String s : ans) {
				titleLength = g.getFontMetrics().stringWidth(s);
				//g.setColor(Color.CYAN);
				//g.fillRect(yBoxX, (MenuScreen.frameHeight / 2 + offset) - 18, yBoxW, 25);
				g.setColor(Color.BLACK);
				g.drawString(s, (MenuScreen.frameWidth - titleLength) / 2, MenuScreen.frameHeight / 2 + offset);
				offset += 50;
			}

			if (Question.qaState == -1) {
				g.setColor(Color.BLACK);
				g.drawString("Wrong answer. Try again", (MenuScreen.frameWidth - titleLength) / 2,
						MenuScreen.frameHeight / 2 + 250);
			} else if (Question.qaState > 0) {
				g.drawString("Correct!", (MenuScreen.frameWidth - titleLength) / 2, MenuScreen.frameHeight / 2 + 250);
				if (Question.qaState == 1) {
					Timer unPause = null;
					unPause = new Timer(700, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							CrabController.paused = false;
							qNum++;
							Question.qaState = 0;
						}
					});
					Question.qaState = 2;
					unPause.start();
					unPause.setRepeats(false);
				}
			}
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
