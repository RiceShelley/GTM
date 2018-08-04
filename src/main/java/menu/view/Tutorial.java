package main.java.menu.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import main.java.menu.enums.IMAGES;

public class Tutorial extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1371224316837671466L;   //added in 
	
	BufferedImage upLeft = ImageManager.get(IMAGES.MENU_BOAT1);
	BufferedImage upRight = ImageManager.get(IMAGES.MENU_BOAT2);
	BufferedImage downLeft = ImageManager.get(IMAGES.MENU_BOAT3);
	BufferedImage downRight = ImageManager.get(IMAGES.MENU_BOAT4);
	BufferedImage sil = ImageManager.get(IMAGES.DIE_SIL);
	private int smaller, gw, gh, bX, bY, bHeight, bWidth;
	private int edgeBuffer = MenuScreen.frameHeight*200/1340;

	public Tutorial(int gw, int gh, int bX, int bY, int bWidth, int bHeight) {
		this.smaller = gw < gh ? gw : gh;
		this.gh = gh;
		this.gw = gh;
		this.bX = bX;
		this.bY = bY;
		this.bHeight = bHeight;
		this.bWidth = bWidth;
		this.setSize(MenuScreen.frameWidth, MenuScreen.frameHeight);

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(upLeft, edgeBuffer, edgeBuffer, smaller, smaller, this);
		g.drawImage(upRight, MenuScreen.frameWidth - smaller - edgeBuffer, edgeBuffer, smaller, smaller, this);
		g.drawImage(downLeft, edgeBuffer, MenuScreen.frameHeight - smaller - edgeBuffer, smaller, smaller, null);
		g.drawImage(downRight, MenuScreen.frameWidth - smaller - edgeBuffer,
				MenuScreen.frameHeight - smaller - edgeBuffer, smaller, smaller, this);
		//g.drawImage(sil, bX, bY, bWidth, bHeight, this);

	}
	
	public void setButton(int bX, int bY, int bWidth, int bHeight) {
		this.bX = bX;
		this.bY = bY;
		this.bHeight = bHeight;
		this.bWidth = bWidth;
	}

}
