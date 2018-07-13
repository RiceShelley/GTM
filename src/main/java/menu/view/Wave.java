package main.java.menu.view;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

import main.java.menu.enums.IMAGES;

public class Wave extends JPanel implements ActionListener, Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage[][] wave;
	public final int frames = 7;
	private int marker = 0;
	private int cur = 0;
	private Timer timer;
	private int framerate = 100;
	private boolean waveIn = true;
	
	public Wave(){
		wave = new BufferedImage [2][];
		wave[0] = ImageManager.arrayPopulator(IMAGES.WAVE_IN, frames);
		wave[1] = ImageManager.arrayPopulator(IMAGES.WAVE_OUT, frames);
		timer = new Timer(framerate, this);
		this.setBounds(0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight);
	} //Constructor
		
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Rectangle clips = g.getClipBounds();
		g.drawImage(wave[marker][cur], 0, 0, clips.width, clips.height, null);
		System.out.println("painted");
	} //paintComponent

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (++cur >= frames){
			timer.stop();
			this.setVisible(false);
		} else {
			repaint();
		} //if-else
	} //actionPerformed

	@Override
	public void run() {
		// TODO Auto-generated method stub
		marker = waveIn? 0:1;
		System.out.println("Inside run: " + marker);
		this.setVisible(true);
		cur = 0;
		timer.start();
		waveIn = !waveIn;
		System.out.println("Leaving run: " + waveIn);
	} //run
	
	
}
