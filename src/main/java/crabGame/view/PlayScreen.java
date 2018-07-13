package main.java.crabGame.view;

import main.java.crabGame.CrabController;
import main.java.crabGame.model.CrabGameWorld;
import main.java.crabGame.model.SaltCloud;
import main.java.menu.enums.IMAGES;
import main.java.menu.view.MenuScreen;

import java.awt.*;

public class PlayScreen {
	static Color currentColor = Color.RED;
	static Color otherColor = Color.BLACK;
	static double colorCount = 0;
	static double colorMaxDuration = 15;
	static Rectangle heartSize = new Rectangle(50,50);
	static int scoreBuffer = 30;
	
	public static void drawPlay(Graphics g) {
		drawProgress(CrabController.score, g);
		drawHearts(CrabGameWorld.lives, g);
		if (SaltCloud.getTrailState() == SaltCloud.TrailState.DROUGHT_ENTRY) drawDroughtMessage(g);
		if (SaltCloud.getTrailState() == SaltCloud.TrailState.STORM_ENTRY) drawStormMessage(g);
		drawLives(g);
	}

	private static void drawHearts(int lives, Graphics g) {
		// TODO Auto-generated method stub
		int heartOriginX = scoreBuffer;
		int heartOriginY = scoreBuffer*4;
		for (int i = 0; i < lives; i ++) {
			g.drawImage(MenuScreen.IMAGE.get(IMAGES.HEART), heartOriginX + heartSize.width*i, heartOriginY,  heartSize.height, heartSize.width, null);
		}
	}
	
	/**
	 * Draw the score/progress bar
	 *
	 * @param g
	 */
	static void drawProgress(int score, Graphics g) {
		int scoreOriginX = scoreBuffer;
		int scoreOriginY = scoreBuffer * 2;
		int scoreWidth = CrabGameWorld.WORLD_WIDTH - scoreBuffer * 2;
		int scoreHeight = 40;

		double scoreFillPercentage;
		double scoreFill;
		scoreFillPercentage = (double) score / CrabController.MAX_SCORE;
		scoreFill = scoreFillPercentage * scoreWidth;

		g.drawImage(MenuScreen.IMAGE.get(IMAGES.GREEN_BAR), scoreOriginX, scoreOriginY, (int) scoreFill,
				scoreHeight, null);

		int littleBitOfSpace = 10;

		if (!CrabGameWorld.crabbyScoring) {
			g.setColor(currentColor);
			if (currentColor == Color.RED) {
				g.drawImage(MenuScreen.IMAGE.get(IMAGES.RED_BAR), (int) (scoreOriginX + scoreFill), scoreOriginY,
						littleBitOfSpace, scoreHeight, null);
			}
			colorCount++;
			if (colorCount > colorMaxDuration) {
				// Switch colors if one color has been active for a period of
				// time and reset the counter
				Color c = currentColor;
				currentColor = otherColor;
				otherColor = c;
				colorCount = 0;
			}
		}
		g.setColor(Color.BLACK);
		g.drawRect(scoreOriginX, scoreOriginY, scoreWidth, scoreHeight);
	}

	static void drawDroughtMessage(Graphics g) {
		final String droughtMessage = "DROUGHT INCOMING";

		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(), 52));
		int droughtLength = g.getFontMetrics().stringWidth(droughtMessage);
		g.drawString(droughtMessage, (MenuScreen.frameWidth - droughtLength) / 2, MenuScreen.frameHeight / 2);
	}

	static void drawStormMessage(Graphics g) {
		final String stormMessage = "STORM INCOMING";

		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(), 52));
		int stormLength = g.getFontMetrics().stringWidth(stormMessage);
		g.drawString(stormMessage, (MenuScreen.frameWidth - stormLength) / 2, MenuScreen.frameHeight / 2);
	}

	public static void drawLives(Graphics g) {

	}
}
