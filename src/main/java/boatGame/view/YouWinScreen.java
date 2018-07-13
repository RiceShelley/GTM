package main.java.boatGame.view;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.menu.enums.IMAGES;
import main.java.menu.view.MenuScreen;

public class YouWinScreen extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton menu;
	
	public YouWinScreen(){
		this.setSize(MenuScreen.frameWidth, MenuScreen.frameHeight);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(MenuScreen.IMAGE.get(IMAGES.BOAT_END), 0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight, null);
	}

}
