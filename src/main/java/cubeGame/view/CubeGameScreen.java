package main.java.cubeGame.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import main.java.menu.controller.MGView;
import main.java.menu.enums.IMAGES;
import main.java.menu.view.ImageManager;
import main.java.menu.view.MenuScreen;
import main.java.cubeGame.controller.CubeController;
import main.java.cubeGame.enums.STATE;
import main.java.cubeGame.model.Die;

public class CubeGameScreen extends MGView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int MPF = 11;
	public static final int diceFrames = 6;
	private static final boolean DEBUG = false;
	private final int rollExtend = 5;
	private final double buttonScale = (MenuScreen.frameHeight *1.0) / 1340;

	CubeController control;

	BufferedImage bg = MenuScreen.IMAGE.get(IMAGES.CUBE_BG);
	BufferedImage[] diceImage = ImageManager.arrayPopulator(IMAGES.CUBE_ROLL, diceFrames);
	BufferedImage[] movePrompt = ImageManager.arrayPopulator(IMAGES.UP_ARROW, MPF);
	BufferedImage[] endFaces = ImageManager.arrayPopulator(IMAGES.CUBE_FACE, Die.NUMFACE);
	Image[] rollPrompt;

	private int cur = 0;
	JButton rollDice;
	private JButton startRecording;
	private JButton endRecording;

	public boolean showingEnd = false;
	private int rollCur = 0;

	private boolean mDisplay = false;
	private boolean rDisplay = false;
	
	private int mpWidth = Die.WIDTH;
	private int mpHeight = mpWidth*movePrompt[0].getHeight()/movePrompt[0].getWidth();
	

	public CubeGameScreen(CubeController control) {
		this.control = control;
		this.setBounds(0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight);

		rollDice = new JButton(new ImageIcon(MenuScreen.IMAGE.scaleButton(IMAGES.ROLL_BUTTON, buttonScale)));
		rollDice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rollDiceActionPerformed(arg0);
			}
		});
		MenuScreen.IMAGE.tailorButton(rollDice);
		this.add(rollDice);
		Dimension rpDimStart = new Dimension(rollDice.getPreferredSize().width / 3,
				rollDice.getPreferredSize().height / 3);
		Dimension rpDimEnd = new Dimension((int) (rollDice.getPreferredSize().getWidth() + rollExtend),
				(int) (rollDice.getPreferredSize().getHeight() + rollExtend));
		rollPrompt = ImageManager.getScaled(IMAGES.CUBE_TUT_1, rpDimStart, rpDimEnd);

		startRecording = new JButton(new ImageIcon(MenuScreen.IMAGE.scaleButton(IMAGES.START_BUTTON, buttonScale)));
		startRecording.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				startButtonPerformed(arg0);
			}
		});
		MenuScreen.IMAGE.tailorButton(startRecording);
		this.add(startRecording);
		endRecording = new JButton(new ImageIcon(MenuScreen.IMAGE.scaleButton(IMAGES.STOP_BUTTON, buttonScale)));
		endRecording.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				endButtonPerformed(arg0);
			}
		});
		MenuScreen.IMAGE.tailorButton(endRecording);
		this.add(endRecording);
		hideRecordingButtons();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Rectangle clips = g.getClipBounds();
		g.drawImage(bg, 0, 0, clips.width, clips.height, null);
		int width = control.getWorld().getRollWidth();
		int height = MenuScreen.frameHeight - control.getWorld().getRollHeight();
		g.drawRect(0, 0, width, height);
		g.drawImage(bg, 0, 0, width, height, null);
		for (Point p : control.getWorld().markers) {
			g.drawImage(MenuScreen.IMAGE.get(IMAGES.DIE_SIL), p.x - Die.WIDTH, p.y - Die.HEIGHT, Die.WIDTH,
					Die.HEIGHT, null);
		}
		// g.fillRect(0, 0, CRABWORLDWIDTH, CRABWORLDHEIGHT);
		for (Die die : control.getWorld().dice) {
			if (die.isRolling()) {
				g.drawImage(diceImage[die.getCur()], die.bounds.x, die.bounds.y, Die.WIDTH, Die.HEIGHT, null);
			} else {
				g.drawImage(endFaces[die.getEndFace()], die.bounds.x, die.bounds.y, Die.WIDTH, Die.HEIGHT, null);
			}
		}

		if (showingEnd) {
			g.drawImage(MenuScreen.IMAGE.get(IMAGES.CUBE_END), 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight,
					null);
		}

		if (rDisplay) {
			rollCur = ++rollCur % rollPrompt.length;
			g.drawImage(rollPrompt[rollCur],
					(int) (rollDice.getLocation().x
							- ((rollPrompt[rollCur].getWidth(this) - rollDice.getPreferredSize().getWidth()) / 2)),
					(int) (rollDice.getLocation().y
							- ((rollPrompt[rollCur].getHeight(this) - rollDice.getPreferredSize().getHeight()) / 2)),
					null);
		}

		if (mDisplay) {
			boolean flag = false;
			for (Die die : control.getWorld().dice) {
				if (!die.isRolling() && die.bounds.intersects(control.getWorld().rollZone) && !flag) {
					flag = true;
					g.drawImage(movePrompt[++cur % MPF], die.bounds.x, die.bounds.y - mpHeight, mpWidth, mpHeight, null);
				}
			}
		}

	}

	private void rollDiceActionPerformed(ActionEvent e) {
		control.getWorld().rollDice();
		control.getWorld().setState(STATE.MOVE);
	}

	private void startButtonPerformed(ActionEvent e) {
		control.recording = true;
		control.record2();
	}

	protected void endButtonPerformed(ActionEvent arg0) {
		// TODO: control.finish();

		if (DEBUG) System.out.println("endButtonPerformed()");
		control.recording = false;
		control.stopRecorder();
	}

	public void showRecordingButtons() {
		startRecording.setVisible(true);
		endRecording.setVisible(true);
	}

	public void hideRecordingButtons() {
		startRecording.setVisible(false);
		endRecording.setVisible(false);
	}

	public void hideButtons() {
		hideRecordingButtons();
		rollDice.setVisible(false);
	}

	public void setmDisplay(boolean mDisplay) {
		this.mDisplay = mDisplay;
	}

	public void setrDisplay(boolean rDisplay) {
		this.rDisplay = rDisplay;
	}

	public void setOK() {
		mDisplay = false;
		rDisplay = false;
	}
	
	public void reset() {
		showingEnd = false;
		setOK();
		rollDice.setVisible(true);
	}

}
