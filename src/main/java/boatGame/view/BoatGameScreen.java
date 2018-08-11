package main.java.boatGame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JLabel;
import main.java.boatGame.controller.BoatController;
import main.java.boatGame.enums.ITEM;
import main.java.boatGame.model.BoatWorld;
import main.java.boatGame.model.Boaty;
import main.java.boatGame.model.Quest;
import main.java.boatGame.model.Wake;
import main.java.boatGame.model.Dock;
import main.java.menu.controller.MGView;
import main.java.menu.enums.IMAGES;
import main.java.menu.view.ImageManager;
import main.java.menu.view.MenuScreen;

public class BoatGameScreen extends MGView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int MIN = 60000; // number of milliseconds in a minute
	private final int SEC = 1000; // number of seconds in a milliseconds
	private final int NWF = 3; // number of wake frames
	private final int NQF = 5; // number of non-active quest frames
	public final int NFF = 21; // number of fade frames (arrow and moveTut)
	private final int gsScale = 5; //grow shrink scale
	private final int erosionIncr = 10;
	BoatController controller;
	Font defaultFont;
	JLabel score;
	JLabel time;
	JLabel oysterHUD;
	JLabel rockHUD;
	JLabel cordgrassHUD;
	
	//Location of each HUD item
	Rectangle scoreLoc = new Rectangle(10, 100, 250, 50);
	Rectangle timeLoc = new Rectangle(10, 450, 350, 500);
	Rectangle oysterLoc = new Rectangle(120, 220, 250, 50);
	Rectangle rockLoc = new Rectangle(120, 370, 250, 50);
	Rectangle cordgrassLoc = new Rectangle(120, 555, 250, 50);
	Rectangle endScoreLoc = new Rectangle(250, 250, MenuScreen.frameWidth - 500, MenuScreen.frameHeight - 500);

	BufferedImage sea = ImageManager.get(IMAGES.WATER);
	BufferedImage tut = ImageManager.get(IMAGES.BOAT_TUTORIAL);
	BufferedImage dock = ImageManager.get(IMAGES.DOCK);
	BufferedImage land = ImageManager.get(IMAGES.BOATS_BG);
	BufferedImage[] questAction = ImageManager.arrayPopulator(IMAGES.QUEST_ACTION, NQF);
	BufferedImage[][] wake = { ImageManager.arrayPopulator(IMAGES.WAKE_RIGHT, NWF),
			ImageManager.arrayPopulator(IMAGES.WAKE_LEFT, NWF) };

	RotatedIcon boatIcon;
	JLabel boatLabel;
	ArrayList<RotatedIcon> wakeIcons = new ArrayList<RotatedIcon>();
	ArrayList<JLabel> wakeLabels = new ArrayList<JLabel>();
	int wakesCur = 0;

	YouWinScreen youWin = new YouWinScreen();

	BufferedImage x = ImageManager.get(IMAGES.X);
	BufferedImage[] moveTut = ImageManager.arrayPopulator(IMAGES.BOAT_TUT_1, NFF);
	BufferedImage[] arrow = ImageManager.arrayPopulator(IMAGES.BOAT_TUT_2, NFF);
	BufferedImage[] shore = ImageManager.arrayPopulator(IMAGES.BOAT_TUT_5, NFF);
	Image[] cgAlert = ImageManager.getScaled(IMAGES.CORDGRASS, new Dimension(ITEM.WIDTH, ITEM.HEIGHT),
			new Dimension(ITEM.WIDTH * gsScale, ITEM.HEIGHT * gsScale));
	Image[] rAlert = ImageManager.getScaled(IMAGES.ROCK, new Dimension(ITEM.WIDTH, ITEM.HEIGHT),
			new Dimension(ITEM.WIDTH * gsScale, ITEM.HEIGHT * gsScale));
	Image[] oAlert = ImageManager.getScaled(IMAGES.OYSTER, new Dimension(ITEM.WIDTH, ITEM.HEIGHT),
			new Dimension(ITEM.WIDTH * gsScale, ITEM.HEIGHT * gsScale));
	int mCur = 0;
	int dCur = 0;
	int qCur = 0;
	int sCur = 0;
	private boolean mDisplay = false;
	private boolean dDisplay = false;
	private boolean qDisplay = false;
	private boolean sDisplay = false;

	/**
	 * initializes variables and adds them to their respective layers
	 * 
	 * @param controller
	 *            BoatController the game came from
	 */
	public BoatGameScreen(BoatController controller) {
		this.controller = controller;
		this.setSize(MenuScreen.frameWidth, MenuScreen.frameHeight);
		this.setLayout(null);

		boatIcon = new RotatedIcon(ImageManager.get(controller.world.boat.holding.boatImg),
				controller.world.boat.angle, Boaty.WIDTH, Boaty.HEIGHT);
		boatLabel = new JLabel(boatIcon);
		updateBoatBounds();
		this.add(boatLabel);
		
		score = new JLabel();
		score.setBounds(scoreLoc);
		score.setForeground(Color.white);
		scaleFont(score, scoreLoc);
		this.add(score);
		
		oysterHUD = new JLabel();
		oysterHUD.setBounds(oysterLoc);
		oysterHUD.setForeground(Color.white);
		scaleFont(oysterHUD, oysterLoc);
		this.add(oysterHUD);
		
		rockHUD = new JLabel();
		rockHUD.setBounds(rockLoc);
		rockHUD.setForeground(Color.white);
		scaleFont(rockHUD, rockLoc);
		this.add(rockHUD);
		
		cordgrassHUD = new JLabel();
		cordgrassHUD.setBounds(cordgrassLoc);
		cordgrassHUD.setForeground(Color.white);
		scaleFont(cordgrassHUD, cordgrassLoc);
		this.add(cordgrassHUD);

		time = new JLabel();
		time.setBounds(timeLoc);
		time.setForeground(Color.white);
		scaleFont(time, timeLoc);
		this.add(time);

		youWin.setVisible(false);
		this.add(youWin);
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (BoatController.paused) {
			g.drawImage(tut, 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight, null);
			boatLabel.setVisible(false);
		}
		else if (BoatController.paused != true){
			g.drawImage(sea, 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight, null);
			
			boatLabel.setVisible(true);
			
			int size = controller.world.hits.size();
			if (size < erosionIncr) {
				g.drawImage(land, 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight, null);
			} else if (size < erosionIncr*2) {
				g.drawImage(ImageManager.get(IMAGES.ERO1), 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight, null);
			}  else if (size < erosionIncr*3) {
				g.drawImage(ImageManager.get(IMAGES.ERO2), 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight, null);
			} else {
				g.drawImage(ImageManager.get(IMAGES.ERO3), 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight, null);
			}
			//g.drawImage(land, 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight, null);
			
			/*for (Point p : controller.world.hits) {
				g.drawImage(x, (int) ((p.x - x.getWidth() / 2) * BoatWorld.widthRatio),
						(int) ((p.y - x.getHeight() / 2) * BoatWorld.heightRatio), null);
			}*/
			for (Dock w : controller.world.docks) {
				g.drawImage(dock, (int) ((w.pos.x - Dock.WIDTH / 2) * BoatWorld.widthRatio),
						(int) ((w.pos.y - Dock.HEIGHT / 2) * BoatWorld.heightRatio), Dock.WIDTH, Dock.HEIGHT, null);

				g.drawImage(ImageManager.get(w.stored.img),
						(int) ((w.pos.x - ITEM.WIDTH / 2) * BoatWorld.widthRatio),
						(int) ((w.pos.y - ITEM.HEIGHT / 2) * BoatWorld.heightRatio), ITEM.WIDTH, ITEM.HEIGHT, null);
			}
			for (Quest q : controller.world.quests) {
				if (!q.active) {
					g.drawImage(questAction[q.curFrame], (int) ((q.pos.x - Quest.WIDTH / 2) * BoatWorld.widthRatio),
							(int) ((q.pos.y - Quest.HEIGHT / 2) * BoatWorld.heightRatio), Quest.WIDTH, Quest.HEIGHT, null);
				}

				g.drawImage(ImageManager.get(q.wanted.img),
						(int) ((q.pos.x - ITEM.WIDTH / 2) * BoatWorld.widthRatio),
						(int) ((q.pos.y - ITEM.HEIGHT / 2) * BoatWorld.heightRatio), ITEM.WIDTH, ITEM.HEIGHT, null);
				q.updateCur(NQF);
			}
			
			g.drawImage(ImageManager.get(IMAGES.OYSTER), 10, 200, 97, 97, null);
			g.drawImage(ImageManager.get(IMAGES.ROCK), 10, 350, 97, 97, null);
			g.drawImage(ImageManager.get(IMAGES.CORDGRASS), 10, 525, 97, 97, null);
		}
		
		
		drawTutorial(g);
		
	}

	private void drawTutorial(Graphics g) {
		if (mDisplay) {
			mCur = (mCur + 1) % moveTut.length;
			g.drawImage(moveTut[mCur],
					(int) (controller.world.boat.getXCoord() * BoatWorld.widthRatio) - Boaty.WIDTH / 2
							- moveTut[mCur].getWidth(),
					(int) (controller.world.boat.getYCoord() * BoatWorld.heightRatio) - Boaty.HEIGHT / 2
							- moveTut[mCur].getHeight(),
					moveTut[mCur].getWidth(), moveTut[mCur].getHeight(), null);
		} else if (dDisplay) {
			dCur = (dCur + 1) % arrow.length;
			g.drawImage(arrow[dCur], (int) (controller.world.docks.get(0).pos.x * BoatWorld.widthRatio + Dock.WIDTH),
					(int) (controller.world.docks.get(0).pos.y * BoatWorld.heightRatio),
					Dock.HEIGHT * (arrow[dCur].getWidth() / arrow[dCur].getHeight()), Dock.HEIGHT, null);
		} else if (qDisplay) {
			switch (controller.world.boat.holding) {
			case CORDGRASS:
				qCur = (qCur + 1) % cgAlert.length;
				if (controller.world.firstC != null) {
					g.drawImage(cgAlert[qCur],
							(int) (controller.world.firstC.pos.x * BoatWorld.widthRatio
									- cgAlert[qCur].getWidth(this) / 2),
							(int) (controller.world.firstC.pos.y * BoatWorld.heightRatio
									- cgAlert[qCur].getHeight(this) / 2),
							null);
					g.drawImage(cgAlert[qCur],
							(int) (controller.world.boat.getXCoord() * BoatWorld.widthRatio
									- cgAlert[qCur].getWidth(this) / 2),
							(int) (controller.world.boat.getYCoord() * BoatWorld.heightRatio
									- cgAlert[qCur].getHeight(this) / 2),
							null);
				}
				break;
			case OYSTER:
				qCur = (qCur + 1) % oAlert.length;
				if (controller.world.firstO != null) {
					g.drawImage(oAlert[qCur],
							(int) (controller.world.firstO.pos.x * BoatWorld.widthRatio
									- cgAlert[qCur].getWidth(this) / 2),
							(int) (controller.world.firstO.pos.y * BoatWorld.heightRatio
									- cgAlert[qCur].getHeight(this) / 2),
							null);
					g.drawImage(oAlert[qCur],
							(int) (controller.world.boat.getXCoord() * BoatWorld.widthRatio
									- oAlert[qCur].getWidth(this) / 2),
							(int) (controller.world.boat.getYCoord() * BoatWorld.heightRatio
									- oAlert[qCur].getHeight(this) / 2),
							null);
				}
				break;
			case ROCK:
				qCur = (qCur + 1) % rAlert.length;
				if (controller.world.firstR != null) {
					g.drawImage(rAlert[qCur],
							(int) (controller.world.firstR.pos.x * BoatWorld.widthRatio
									- cgAlert[qCur].getWidth(this) / 2),
							(int) (controller.world.firstR.pos.y * BoatWorld.heightRatio
									- cgAlert[qCur].getHeight(this) / 2),
							null);
					g.drawImage(rAlert[qCur],
							(int) (controller.world.boat.getXCoord() * BoatWorld.widthRatio
									- rAlert[qCur].getWidth(this) / 2),
							(int) (controller.world.boat.getYCoord() * BoatWorld.heightRatio
									- rAlert[qCur].getHeight(this) / 2),
							null);
				}
				break;
			default:
				break;
			} //switch
		} //else if
		if (sDisplay) {
			sCur = (sCur + 1) % shore.length;
			g.drawImage(shore[sCur],
					(int) (controller.world.boat.getXCoord() * BoatWorld.widthRatio) - Boaty.WIDTH / 2
							- shore[sCur].getWidth(),
					(int) (controller.world.boat.getYCoord() * BoatWorld.heightRatio) - Boaty.HEIGHT / 2
							- shore[sCur].getHeight(),
					shore[sCur].getWidth(), shore[sCur].getHeight(), null);
		}
	}

	/**
	 * changes the visibility of the You Win Screen to true;
	 */
	public void displayEnd() {
		defaultFont = score.getFont();
		youWin.setVisible(true);
		time.setVisible(false);
		oysterHUD.setVisible(false);
		rockHUD.setVisible(false);
		cordgrassHUD.setVisible(false);
		scaleFont(score, endScoreLoc);
		
		boatLabel.setVisible(false);
	}
	
	private void scaleFont(JLabel label, Rectangle scaleLoc) {
		label.setBounds(scaleLoc);

		Font labelFont = label.getFont();
		String labelText = label.getText();

		int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
		int componentWidth = label.getWidth();

		// Find out how much the font can grow in width.
		double widthRatio = (double) componentWidth / (double) stringWidth;

		int newFontSize = (int) (labelFont.getSize() * widthRatio);
		int componentHeight = label.getHeight();

		// Pick a new font size so it will not be larger than the height of
		// label.
		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		// Set the label's font size to the newly determined size.
		label.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
	}

	public void reset() {
		score.setBounds(scoreLoc);
		oysterHUD.setBounds(oysterLoc);
		rockHUD.setBounds(rockLoc);
		cordgrassHUD.setBounds(cordgrassLoc);
		
		youWin.setVisible(false);
		score.setVisible(true);
		time.setVisible(true);
		oysterHUD.setVisible(true);
		rockHUD.setVisible(true);
		cordgrassHUD.setVisible(true);
		boatLabel.setVisible(true);
		score.setFont(defaultFont);
		scaleFont(score, scoreLoc);
		
		mDisplay = false;
		dDisplay = false;
		qDisplay = false;
		sDisplay = false;
		
		BoatController.paused = true;
	}

	/**
	 * finds the time left from the controller and returns it in m:s format
	 * 
	 * @return string of the milliseconds left before timer ends game
	 */
	private String getTimeLeft() {
		int time = controller.milliTimeLimit - controller.getMillisPassed();
		double min = time / MIN;
		double sec = (time - min * MIN) / SEC;
		return (int) min + ":" + (String.format("%.2f", sec));
	}

	/**
	 * updates the score and time labels with their current game values
	 */
	public void updateLabels() {
		if (BoatController.paused == false) {
			scaleFont(time, timeLoc);
			score.setText("Score: " + controller.world.score + "");
			oysterHUD.setText(controller.world.oysters + " / " + " 3");
			rockHUD.setText(controller.world.rocks + " / " + " 3");
			cordgrassHUD.setText(controller.world.cordgrass + " / " + " 3");
			
			time.setText("Time Left: " + getTimeLeft());
			boatIcon.setAngle(controller.world.boat.angle);
			boatIcon.setIcon(ImageManager.get(controller.world.boat.holding.boatImg), Boaty.WIDTH, Boaty.HEIGHT);
			updateBoatBounds();
			addWakes();
		}
	}

	public void addWakes() {
		if (wakeLabels.size() > controller.world.wakes.size()) {
			for (int i = controller.world.wakes.size(); i < wakeLabels.size(); i++) {
				wakeLabels.get(i).setVisible(false);
			}
		} else if (wakeLabels.size() < controller.world.wakes.size()) {
			for (int i = wakeLabels.size(); i < controller.world.wakes.size(); i++) {
				RotatedIcon r = new RotatedIcon(wake[controller.world.wakes.get(i).dir][wakesCur],
						controller.world.wakes.get(i).angle * -1, Wake.WIDTH, Wake.HEIGHT);
				JLabel l = new JLabel(r);
				updateWakeBounds(l, i);

				this.add(l);
				wakeIcons.add(r);
				wakeLabels.add(l);
			}
		}
		for (int i = 0; i < controller.world.wakes.size(); i++) {
			wakeLabels.get(i).setVisible(true);
			wakeIcons.get(i).setAngle(controller.world.wakes.get(i).angle * -1);
			wakeIcons.get(i).setIcon(wake[controller.world.wakes.get(i).dir][wakesCur], Wake.WIDTH, Wake.HEIGHT);
			updateWakeBounds(wakeLabels.get(i), i);
		}
		wakesCur = (wakesCur + 1) % NWF;
	}

	public void updateBoatBounds() {
		boatLabel.setBounds((int) (controller.world.boat.getXCoord() * BoatWorld.widthRatio - Boaty.WIDTH / 2),
				(int) (controller.world.boat.getYCoord() * BoatWorld.heightRatio - Boaty.HEIGHT / 2), Boaty.WIDTH,
				Boaty.HEIGHT);
	}

	public void updateWakeBounds(JLabel l, int i) {
		l.setBounds((int) (controller.world.wakes.get(i).pos.x * BoatWorld.widthRatio - Wake.WIDTH / 2),
				(int) (controller.world.wakes.get(i).pos.y * BoatWorld.heightRatio - Wake.HEIGHT / 2), Wake.WIDTH,
				Wake.HEIGHT);
	}

	public void setmDisplay(boolean mDisplay) {
		this.mDisplay = mDisplay;
	}

	public void setdDisplay(boolean dDisplay) {
		this.dDisplay = dDisplay;
	}

	public void setqDisplay(boolean qDisplay) {
		this.qDisplay = qDisplay;
	}
	
	public void setsDisplay(boolean sDisplay) {
		this.sDisplay = sDisplay;
	}

	public void setOK() {
		mDisplay = false;
		dDisplay = false;
		qDisplay = false;
		sDisplay = false;
	}

}
