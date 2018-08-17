package main.java.menu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import main.java.boatGame.controller.BoatController;
import main.java.crabGame.CrabController;
import main.java.crabGame.CrabGamePanel;
import main.java.cubeGame.controller.CubeController;
import main.java.cubeGame.controller.SheetManager;
import main.java.menu.controller.MGController;
import main.java.menu.enums.IMAGES;

public class MenuScreen extends JLayeredPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6097571045147215752L; // added in PCM
	public static final ImageManager IMAGE = new ImageManager();
	//private static final Dimension size = new Dimension(1920, 1080);
	private static final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

	public static final int frameWidth = size.width;
	public static final int frameHeight = size.height;
	public final double buttonScale = (MenuScreen.frameHeight * 2.0) / 1340;
	JFrame frame = new JFrame();
	CrabController crabController;
	BoatController boatController;
	CubeController cubeController;
	MGController prevController;
	JPanel gameView = new JPanel();
	Wave wave;
	JPanel menu;
	JPanel scoreboard;
	JButton menuButton;
	Rectangle menuLoc = new Rectangle(10, 10, 125, 30);
	JButton crabButton, boatButton, cubeButton;
	JLabel blankL = new JLabel("");
	JLabel blankR = new JLabel("");

	public MenuScreen() {
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this, BorderLayout.CENTER);
		frame.setSize(new Dimension(frameWidth, frameHeight));
		frame.setVisible(true);

		crabController = new CrabController(this);
		boatController = new BoatController(this);
		cubeController = new CubeController(this);

		wave = new Wave();
		buildMenu();
		this.add(wave, 4, 0);
		this.add(menu, 2, 0);
	}

	private void buildMenu() {

		menu = new JPanel(new GridBagLayout());
		menu.setBounds(0, 0, frameWidth, frameHeight);
		menu.setOpaque(false);

		final int nameFontSize = MenuScreen.frameHeight / 30;

		JPanel crabPanel = new JPanel();
		crabPanel.setLayout(new BoxLayout(crabPanel, BoxLayout.Y_AXIS));
		crabButton = new JButton(new ImageIcon(ImageManager.scaleButton(IMAGES.CRAB_BUTTON, buttonScale)));
		crabButton.addActionListener(actionEvent -> switchGame(crabController));
		ImageManager.tailorButton(crabButton);
		crabPanel.add(crabButton);
		JLabel crabName = new JLabel("Blue Crab Run");
		crabName.setFont(new Font("Sans_Serif", Font.PLAIN, nameFontSize));
		crabPanel.add(crabName);

		JPanel boatPanel = new JPanel();
		boatPanel.setLayout(new BoxLayout(boatPanel, BoxLayout.Y_AXIS));
		boatButton = new JButton(new ImageIcon(ImageManager.scaleButton(IMAGES.BOAT_BUTTON, buttonScale)));
		boatButton.addActionListener(actionEvent -> switchGame(boatController));
		ImageManager.tailorButton(boatButton);
		boatPanel.add(boatButton);
		JLabel boatName = new JLabel("Shoreline Restoration Race");
		boatName.setFont(new Font("Sans_Serif", Font.PLAIN, nameFontSize));
		boatPanel.add(boatName);

		JPanel cubePanel = new JPanel();
		cubePanel.setLayout(new BoxLayout(cubePanel, BoxLayout.Y_AXIS));
		cubeButton = new JButton(new ImageIcon(ImageManager.scaleButton(IMAGES.CUBE_BUTTON, buttonScale)));
		cubeButton.addActionListener(actionEvent -> switchGame(cubeController));
		ImageManager.tailorButton(cubeButton);
		cubePanel.add(cubeButton);
		JLabel cubeName = new JLabel("Estuary Stories");
		cubeName.setFont(new Font("Sans_Serif", Font.PLAIN, nameFontSize));
		cubePanel.add(cubeName);

		GridBagConstraints cons = new GridBagConstraints();
		cons.gridy = 0;
		cons.weighty = .5;
		cons.weightx = .1;
		cons.anchor = GridBagConstraints.SOUTHEAST;
		cons.gridx = 0;
		menu.add(crabPanel, cons);

		cons.gridx = 1;
		cons.anchor = GridBagConstraints.SOUTH;
		menu.add(boatPanel, cons);

		cons.gridx = 2;
		cons.anchor = GridBagConstraints.SOUTHWEST;
		menu.add(cubePanel, cons);

		cons.gridy = 1;
		cons.weighty = .5;
		cons.anchor = GridBagConstraints.NORTH;
		cons.gridx = 1;

		cons.gridy = 1;
		cons.weighty = .5;
		cons.anchor = GridBagConstraints.NORTHEAST;
		// cons.anchor = GridBagConstraints.NORTH;
		cons.gridx = 0;

		cons.gridy = 1;
		cons.weighty = .5;
		cons.anchor = GridBagConstraints.NORTHWEST;
		// cons.anchor = GridBagConstraints.NORTH;
		cons.gridx = 2;

		cons.gridx = 2;
		menu.add(blankR, cons);

		cons.gridx = 0;
		menu.add(blankL, cons);

		menuButton = new JButton("Menu");
		menuButton.setVisible(false);
		menuButton.addActionListener(actionEvent -> menuButtonActionPerformed());

		menuButton.setBounds(menuLoc);
		this.add(menuButton, 4, 0);
	}

	protected void menuButtonActionPerformed() {
		System.out.println("menu action performed");
		// TODO Auto-generated method stub
		if (prevController != null)
			prevController.dispose();
		
		menuButton.setVisible(false);
		crabButton.setVisible(true);
		boatButton.setVisible(true);
		cubeButton.setVisible(true);
	}

	private void switchGame(MGController controller) {
		// TODO wave.run
		if (prevController != null) {
			prevController.dispose();
		}

		prevController = controller;
		if (prevController instanceof BoatController) {
			BoatController temp = (BoatController) prevController;
			temp.timer.start();
		} else if (prevController instanceof CrabController) {
			// Do Nothing
		} else if (prevController instanceof CubeController) {
			CubeController temp = (CubeController) prevController;
			temp.getTimer().start();
			temp.getView().scheduleEndingTimer(60);
		}

		gameView = prevController.getView();

		gameView.setVisible(true);
		menuButton.setVisible(true);
		this.add(gameView, 3, 0);
		// Separate Google Sheets access to new thread to avoid lag
		new Thread(() -> {
			try {
				if (prevController instanceof BoatController) {
					SheetManager.incrementBoatCount();
				} else if (prevController instanceof CrabController) {
					SheetManager.incrementCrabCount();
				} else if (prevController instanceof CubeController) {
					SheetManager.incrementCubeCount();
				}
			} catch (IOException | GeneralSecurityException e) {
				e.printStackTrace();
			}

		}).start();
	}

	// The cube game needs to be able to replay itself from the ending screen
	public void replayCubeGame() {
		switchGame(cubeController);
	}
	public void replayBoatGame() {
		switchGame(boatController);
	}
	public void hideMenuButton() {
		menuButton.setVisible(false);
	}

}