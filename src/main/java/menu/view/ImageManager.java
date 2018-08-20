package main.java.menu.view;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import main.java.menu.enums.IMAGES;

/**
 * Creates images for each game and contains a list of all images, which makes
 * the games run faster.
 *
 *
 * This class is essentially static, but is instantiated at startup to load each image into a hashmap
 * @author SeanWhiteman
 */
public class ImageManager {
	private static final int growthRate = 55;
	/**
	 * Used to make sure images are loaded in the fastest format for each
	 * machine running the game. IDK what it actually does
	 */
	@SuppressWarnings("unused")
	private static final GraphicsConfiguration GFX_CONFIG = GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getDefaultScreenDevice().getDefaultConfiguration();
	/**
	 * A HashMap of all images used in the games.
	 */
	static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();

	/**
	 * creates all images in IMAGES enum all at once and stores in HashMap
	 * images
	 */
	public ImageManager() {		
		for (IMAGES i : IMAGES.values()) {
			System.out.println(i.path);
			createImage(i.path); 
		}
	}
	
	/*
	 * Returns the IMAGE enum value corresponding to a bufferedimage input
	 */
	public static IMAGES findImage(BufferedImage bf) throws Exception {
		for (Entry<String, BufferedImage> entry : images.entrySet()) {
			if (entry.getValue().equals(bf)) {
				return IMAGES.getImage(entry.getKey());
			}
		}
		return null;
	}

	/**
	 * private function used generate the BufferedImages from image files
	 *
	 * @param fileName
	 *            The name of the file that contains the image
	 * @return The newly created image
	 */
	private static BufferedImage createImage(String fileName) {
		BufferedImage bufferedImage;// = images.get(fileName);?
		try {
			//System.out.println(ImageManager.class.getResourceAsStream(fileName));
			bufferedImage = ImageIO.read(ImageManager.class.getResourceAsStream(fileName));
			images.put(fileName, bufferedImage);
			return bufferedImage;
		} catch (IOException e) {
			System.out.println(fileName);
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage[] arrayPopulator(IMAGES img, int frames) {
		BufferedImage[] array = new BufferedImage[frames];
		BufferedImage image = ImageManager.get(img);
		int width = image.getWidth() / frames;
		for (int i = 0; i < frames; i++) {
			array[i] = image.getSubimage(width * i, 0, width, image.getHeight());
		}
		return array;
	} // arrayPopulator

	/**
	 * public function used by all classes to receive images from ImageManager
	 *
	 * @param fileName
	 * @return image associated with string
	 */
	public static BufferedImage get(IMAGES img) {
		BufferedImage bufferedImage = images.get(img.path);
		if (bufferedImage != null) {
			return bufferedImage;
		}
		return createImage(img.path);
	}

	public static Image[] getScaled(IMAGES img, Dimension start, Dimension end) {
		// Alternate behaviour would be for the array to NOT have a set size and
		// to determine size from ratio of start/end
		int halfway = growthRate / 2;
		BufferedImage image = ImageManager.get(img);
		Image[] array = new Image[growthRate];
		array[0] = image.getScaledInstance(start.width, start.height, Image.SCALE_SMOOTH);
		double wRatio = (end.width - start.width) / (1.0*halfway);
		double hRatio = (end.height - start.height) / (1.0*halfway);
		for (int i = 0; i < halfway; i++) {
			array[i] = image.getScaledInstance((int) (start.width + (i * wRatio)), (int) (start.height + (i * hRatio)),
					Image.SCALE_SMOOTH);
		}
		for (int i = halfway; i < growthRate; i++) {
			array[i] = image.getScaledInstance((int) (end.width - ((i - halfway) * wRatio)),
					(int) (end.height - ((i - halfway) * hRatio)), Image.SCALE_SMOOTH);
		}

		return array;
	}

	public static Image scaleButton(IMAGES img, double buttonScale) {
		ImageIcon icon = new ImageIcon(get(img));
		int width = (int) (icon.getIconWidth() * buttonScale);
		int height = (int) (icon.getIconHeight() * buttonScale);
		return ((ImageIcon) icon).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
	
	public static void tailorButton(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorder(null);
	}
}
