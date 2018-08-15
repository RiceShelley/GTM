package main.java.crabGame.view;

import main.java.crabGame.model.CrabGameWorld;
import main.java.crabGame.model.Utilities;
import main.java.menu.view.MenuScreen;

import java.awt.*;

public class WinScreen {

	/**
	 * What to draw if the gameState is WIN
	 *
	 * @param g
	 */
	public static void drawWin(Graphics g) {
		final String winMessage = "YOU WIN!";
		final int winMessageFontSize = 218; // Font size

		g.setColor(new Color(255, 255, 255, 127));
		g.fillRect(
				CrabGameWorld.WORLD_ORIGIN_X,
				CrabGameWorld.WORLD_ORIGIN_Y,
				CrabGameWorld.WORLD_WIDTH,
				CrabGameWorld.WORLD_HEIGHT);
		g.setFont(Utilities.titleFont);

		g.setColor(Color.BLACK);
		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(), winMessageFontSize));
		int titleLength = g.getFontMetrics().stringWidth(winMessage);
		g.drawString(winMessage, (MenuScreen.frameWidth - titleLength) / 2, MenuScreen.frameHeight / 2);
		//moveUp.setEnabled(false);
		//moveUp.setVisible(false);
	}
}
