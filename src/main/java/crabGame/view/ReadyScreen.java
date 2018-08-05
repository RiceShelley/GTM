package main.java.crabGame.view;

import main.java.crabGame.model.Utilities;
import main.java.menu.enums.IMAGES;
import main.java.menu.view.MenuScreen;

import java.awt.*;

public class ReadyScreen {
	static final Color OPAQUE_WHITE = new Color(255, 255, 255, 127);
	static final String startMessage = "Tap to start, touch the screen to move the crab!";
	static final String title = "CRAB RUN";

	/**
	 * What to draw if the gameState is READY
	 *
	 * @param g
	 */
	public static void drawReady(Graphics g) {

		int startScreenLength;
		
		final int TITLE_FONT_SIZE = 100;
		int titleLength;

		g.setColor(OPAQUE_WHITE);
		g.fillRect(
				0,
				0,
				MenuScreen.frameWidth,
				MenuScreen.frameHeight);

		int FONT_SIZE = 20;

		Utilities.titleFont = Utilities.titleFont.deriveFont(Font.PLAIN, FONT_SIZE);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		ge.registerFont(Utilities.titleFont);

		g.drawImage(MenuScreen.IMAGE.get(IMAGES.TUT_CRAB), 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight, null);
		
		g.setColor(Color.BLACK);

		int START_FONT_SIZE = 34;

		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(), START_FONT_SIZE));

		startScreenLength = g.getFontMetrics().stringWidth(startMessage);

		g.drawString(startMessage, (MenuScreen.frameWidth - startScreenLength) / 2, 3 * MenuScreen.frameHeight / 5);

		Utilities.defaultFont = g.getFont();


		g.setFont(Utilities.titleFont);

		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(), TITLE_FONT_SIZE));

		titleLength = g.getFontMetrics().stringWidth(title);

		g.drawString(title, (MenuScreen.frameWidth - titleLength) / 2, MenuScreen.frameHeight / 2);
	}
}
