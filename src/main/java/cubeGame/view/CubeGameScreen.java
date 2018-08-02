package main.java.cubeGame.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import main.java.menu.controller.MGView;
import main.java.menu.enums.IMAGES;
import main.java.menu.view.ImageManager;
import main.java.menu.view.MenuScreen;
import main.java.cubeGame.controller.CubeController;
import main.java.cubeGame.model.Die;

public class CubeGameScreen extends MGView {

	private static final long serialVersionUID = 1L;
	private final int MPF = 11;
	public static final int diceFrames = 6;
	private final int rollExtend = 5;
	private final double buttonScale = (MenuScreen.frameHeight * 1.0) / 1340;

	CubeController control;

	BufferedImage bg = ImageManager.get(IMAGES.CUBE_BG);
	BufferedImage[] diceImage = ImageManager.arrayPopulator(IMAGES.CUBE_ROLL, diceFrames);
	BufferedImage[] movePrompt = ImageManager.arrayPopulator(IMAGES.UP_ARROW, MPF);
	List<BufferedImage> endFaces; // List full of ending images for dice
	Image[] rollPrompt;

	JButton rollDiceButton;
	private JButton submitButton;

	public boolean showingEnd = false;

	
	public CubeGameScreen(CubeController control) {
		this.control = control;
		this.setBounds(0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight);

		// Filters only the images that correspond to dice faces into a list
		endFaces = Arrays.stream(IMAGES.values()).filter(name -> String.valueOf(name).contains("DICE_"))
				.map(ImageManager::get).collect(Collectors.toList());

		rollDiceButton = new JButton(new ImageIcon(ImageManager.scaleButton(IMAGES.ROLL_BUTTON, buttonScale)));
		rollDiceButton.addActionListener(actionEvent -> control.getWorld().rollDice());
		ImageManager.tailorButton(rollDiceButton);
		this.add(rollDiceButton);
		
		Dimension rpDimStart = new Dimension(rollDiceButton.getPreferredSize().width / 3,
				rollDiceButton.getPreferredSize().height / 3);
		Dimension rpDimEnd = new Dimension((int) (rollDiceButton.getPreferredSize().getWidth() + rollExtend),
				(int) (rollDiceButton.getPreferredSize().getHeight() + rollExtend));
		rollPrompt = ImageManager.getScaled(IMAGES.CUBE_TUT_1, rpDimStart, rpDimEnd);

		// Recording buttons
		// TODO: ADD BETTER IMAGE FOR SUBMIT BUTTON
		submitButton = new JButton(new ImageIcon(ImageManager.scaleButton(IMAGES.START_BUTTON, buttonScale)));
		submitButton.addActionListener(actionEvent -> control.submit());
		submitButton.setVisible(false);
		ImageManager.tailorButton(submitButton);
		this.add(submitButton);
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (showingEnd) {
			g.drawImage(ImageManager.get(IMAGES.CUBE_END), 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight, null);
			return;
		} 
		
		Rectangle clips = g.getClipBounds();
		g.drawImage(bg, 0, 0, clips.width, clips.height, null);
		int width = control.getWorld().getRollWidth();
		int height = MenuScreen.frameHeight - control.getWorld().getRollHeight();
		g.drawRect(0, 0, width, height);
		g.drawImage(bg, 0, 0, width, height, null);
		for (Point p : control.getWorld().markers) {
			g.drawImage(ImageManager.get(IMAGES.DIE_SIL), p.x - Die.WIDTH, p.y - Die.HEIGHT, Die.WIDTH, Die.HEIGHT,
					null);
		}
		
		// Draws an image of a rolling or resting die for each die
		for (Die die : control.getWorld().dice) {
			if (die.isRolling()) {
				g.drawImage(diceImage[die.getRollingImageIndex()], die.bounds.x, die.bounds.y, Die.WIDTH, Die.HEIGHT, null);
			} else { // is this repainted every single screen?
				g.drawImage(endFaces.get(die.getEndImageIndex()), die.bounds.x, die.bounds.y, Die.WIDTH, Die.HEIGHT, null);
			}
		}
	}

	public void showSubmitButton(boolean b) {
		submitButton.setVisible(b);
	}
	
	public void showSubmitButton() {
		showSubmitButton(true);
	}

	public void reset() {
		showingEnd = false;
		rollDiceButton.setVisible(true);
		submitButton.setVisible(false);
	}

}
