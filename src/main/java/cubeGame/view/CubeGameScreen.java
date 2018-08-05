package main.java.cubeGame.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.GeneralSecurityException;
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
import main.java.cubeGame.controller.SheetManager;
import main.java.cubeGame.model.CubeWorld;
import main.java.cubeGame.model.Die;

public class CubeGameScreen extends MGView {

	private static final long serialVersionUID = 1L;
	private final int MPF = 11;
	public static final int diceFrames = 6;
	private final int rollExtend = 5;
	private final double buttonScale = (MenuScreen.frameHeight * 1.0) / 1340;

	CubeController control;

	BufferedImage table = ImageManager.get(IMAGES.CUBE_TABLE);
	BufferedImage backdrop = ImageManager.get(IMAGES.CUBE_BACKGROUND);
	BufferedImage[] diceImage = ImageManager.arrayPopulator(IMAGES.CUBE_ROLL, diceFrames);
	BufferedImage[] movePrompt = ImageManager.arrayPopulator(IMAGES.UP_ARROW, MPF);
	List<BufferedImage> endFaces; // List full of ending images for dice
	Image[] rollPrompt;

	JButton rollDiceButton;
	private JButton submitButton;

	public boolean showingEnd = false;
	private boolean showingTutorial = true;

	public CubeGameScreen(CubeController control) {
		this.control = control;
		this.setBounds(0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight);

		// Filters only the images that correspond to dice faces into a list
		endFaces = Arrays.stream(IMAGES.values()).filter(name -> String.valueOf(name).contains("DICE_"))
				.map(ImageManager::get).collect(Collectors.toList());

		// Button to roll dice
		rollDiceButton = new JButton(new ImageIcon(ImageManager.scaleButton(IMAGES.ROLL_BUTTON, buttonScale)));
		rollDiceButton.addActionListener(actionEvent -> control.getWorld().rollDice());
		ImageManager.tailorButton(rollDiceButton);
		this.add(rollDiceButton);

		Dimension rpDimStart = new Dimension(rollDiceButton.getPreferredSize().width / 3,
				rollDiceButton.getPreferredSize().height / 3);
		Dimension rpDimEnd = new Dimension((int) (rollDiceButton.getPreferredSize().getWidth() + rollExtend),
				(int) (rollDiceButton.getPreferredSize().getHeight() + rollExtend));
		rollPrompt = ImageManager.getScaled(IMAGES.CUBE_TUT_1, rpDimStart, rpDimEnd);

		// TODO: ADD BETTER IMAGE FOR SUBMIT BUTTON
		submitButton = new JButton(new ImageIcon(ImageManager.scaleButton(IMAGES.SUBMIT_BUTTON, buttonScale * 0.6)));
		submitButton.addActionListener(actionEvent -> { // Show ending screen and export results to google sheet

			showingEnd = true;
			new Thread() { // Send data to sheet in a separate thread to avoid lag
				@Override
				public void run() {
					try {
						SheetManager.writeDiceToSheet(control.getWorld().getPlacedDice());
					} catch (GeneralSecurityException e) {
						System.out.println("Not Authorized to Write to Sheet");
						e.printStackTrace();
					} catch (IOException e) {
						System.out.println("Couldn't Connect to Google Sheets");
						e.printStackTrace();
					}
				}
			}.start();
		});

		submitButton.setVisible(false);
		ImageManager.tailorButton(submitButton);
		this.add(submitButton);

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				showingTutorial = false;
				control.getWorld().rollDice();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (showingEnd) { // Ends the game
			rollDiceButton.setVisible(false);
			submitButton.setVisible(false);
			g.drawImage(ImageManager.get(IMAGES.CUBE_END), 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight, null);
			repaint();
			revalidate();
			return;
		} else if (showingTutorial) {
			g.drawImage(ImageManager.get(IMAGES.CUBE_TUTORIAL), 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight,
					null);
			rollDiceButton.setVisible(false);
			submitButton.setVisible(false);
			repaint();
			revalidate();
		} else {
			int width = CubeWorld.rollZone.width;
			int height = MenuScreen.frameHeight - CubeWorld.rollZone.height;

			g.drawImage(backdrop, 0, 0, width, height, null); // Draws the entire background
			g.drawRect(0, 0, width, height);
			g.drawImage(table, 0, height, width, MenuScreen.frameHeight - height, null); // Draws the rollZone image
			for (Point p : control.getWorld().markers) { // Draws slots for each marker
				g.drawImage(ImageManager.get(IMAGES.DIE_SIL), p.x - Die.WIDTH, p.y - Die.HEIGHT, Die.WIDTH, Die.HEIGHT,
						null);
			}

			// Draws an image of a rolling or resting die for each die
			for (Die die : CubeWorld.dice) {
				if (die.isRolling()) {
					g.drawImage(diceImage[die.getRollingImageIndex()], die.bounds.x, die.bounds.y, Die.WIDTH,
							Die.HEIGHT, null);
				} else { // is this repainted every single screen?
					g.drawImage(endFaces.get(die.getEndImageIndex()), die.bounds.x, die.bounds.y, Die.WIDTH, Die.HEIGHT,
							null);
					die.setName(endFaces.get(die.getEndImageIndex())); // Name the die based on its image
				}
				repaint();
				revalidate();
			}
		}
	}

	public void showSubmitButton() {
		submitButton.setVisible(true);
		rollDiceButton.setVisible(false);
	}

	public void hideSubmitButton() {
		submitButton.setVisible(false);
		rollDiceButton.setVisible(true);
	}

	public void reset() {
		showingEnd = false;
		rollDiceButton.setVisible(true);
		submitButton.setVisible(false);
	}

	public void showTutorialScreen() {
		showingTutorial = true;
	}

}
