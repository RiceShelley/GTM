package main.java.crabGame.model;

import java.awt.*;
import java.io.File;
import java.io.InputStream;

public class Utilities {
	public static Font titleFont; // Title font
	public static Font defaultFont;// other font

	static int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}


	public static void loadFonts() {
		// Try to lead font
		try {
			//File file = new File("src/main/resources/fonts/FindetNemo.ttf");
			//File file = new File("/main/resources/fonts/FindetNemo.ttf");
			InputStream file = Utilities.class.getResourceAsStream("/main/resources/fonts/FindetNemo.ttf");
			//File file = new File(Utilities.class.getResource("/Coalition_v2.ttf"));
			titleFont = Font.createFont(Font.TRUETYPE_FONT, file);
			//defaultFont = Font.createFont(Font.TRUETYPE_FONT, file);
			defaultFont = titleFont; 
		} catch (Exception e) {

			// Exit if font cannot be loaded
			e.printStackTrace();
			System.err.println("Could not load font!");
			System.exit(-1);
		}
	}
}
