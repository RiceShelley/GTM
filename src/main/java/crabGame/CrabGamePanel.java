package main.java.crabGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Timer;

import main.java.crabGame.controller.CrabGameListeners;
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

	public static int fontSize;

	/**
	 * Initializes the GamePanel
	 */
	CrabGamePanel() {

		if (MenuScreen.frameWidth < 2000) {
			fontSize = 40;
		} else {
			fontSize = 90;
		}

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
			g.drawImage(ImageManager.get(IMAGES.CRAB_TUTORIAL), 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight,
					null);
		} else if (CrabController.game.checkGameStateContinue()) {
			ContinueScreen.drawContinueScreen(g);
		} else if (CrabController.game.checkGameStateWin()) {
			CrabGameWorld.crabbies.get(0).setState(Mover.State.SAFE);
			WinScreen.drawWin(g);
		}

		// draw question
		if (CrabController.paused) {
			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(), fontSize));
			Question q = Question.questions.get(qNum);
			String q_str = q.getPrompt();
			int titleLength = g.getFontMetrics().stringWidth(q_str);

			// draw bg
			g.drawImage(ImageManager.get(IMAGES.TUT_BG), 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight, null);

			g.setColor(Color.BLACK);
			g.drawString(q_str, (MenuScreen.frameWidth - titleLength) / 2, MenuScreen.frameHeight / 3);
			// draw possible answers
			g.setFont(g.getFont().deriveFont(g.getFont().getStyle(), fontSize));
			ArrayList<String> ans = q.getAnswers();
			int offset = 0;
			for (String s : ans) {
				titleLength = g.getFontMetrics().stringWidth(s);
				g.setColor(Color.BLACK);
				g.drawString(s, (MenuScreen.frameWidth - titleLength) / 2, MenuScreen.frameHeight / 2 + offset);
				offset += MenuScreen.frameHeight / 10;
			}
			/*try {
				for (int i = 0; i < 3; i++) {
					g.drawRect(0, CrabGameListeners.r[i].x, MenuScreen.frameWidth,
							CrabGameListeners.r[i].y - CrabGameListeners.r[i].x);
				}
			} catch (NullPointerException e) {
			}*/

			if (Question.qaState == -1) {
				g.setColor(Color.BLACK);
				g.drawString("Wrong answer. Try again", (MenuScreen.frameWidth - titleLength) / 2,
						(int) (MenuScreen.frameHeight / 1.2));
			} else if (Question.qaState > 0) {
				g.drawString("Correct!", (MenuScreen.frameWidth - titleLength) / 2,
						(int) (MenuScreen.frameHeight / 1.2));
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
