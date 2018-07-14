package main.java.menu.view;

import java.awt.Graphics;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.java.menu.enums.IMAGES;

public class Wave extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private BufferedImage[][] wave;
	public final int frames = 7;
	private int marker = 0;
	private int cur = 0;
	private Timer timer;
	private int framerate = 100;
	private boolean waveIn = true;

	public Wave() {
		wave = new BufferedImage[2][];
		wave[0] = ImageManager.arrayPopulator(IMAGES.WAVE_IN, frames);
		wave[1] = ImageManager.arrayPopulator(IMAGES.WAVE_OUT, frames);
		timer = new Timer(framerate, this);
		timer.start();
		this.setBounds(0, 0, MenuScreen.frameWidth, MenuScreen.frameHeight);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Rectangle clips = g.getClipBounds();
		g.drawImage(wave[marker][cur], 0, 0, clips.width, clips.height, null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (++cur >= frames) {
			if (waveIn) {
				cur = 0;
				marker = 1;
				waveIn = false;
			} else {
				timer.stop();
				this.setVisible(false);
			}
		} else {
			repaint();
		}
	}
}