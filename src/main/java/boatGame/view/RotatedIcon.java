package main.java.boatGame.view;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * The RotatedIcon allows you to change the orientation of an Icon by rotating
 * the Icon before it is painted. 
 */
public class RotatedIcon implements Icon {
	private Icon icon;
	private double angle;
	

	/**
	 * Create a RotatedIcon. The icon will rotate about its center.
	 *
	 * @param icon
	 *            the Icon to rotate
	 * @param angle
	 *            the radians of rotation
	 */
	public RotatedIcon(BufferedImage icon, double angle, int width, int height) {
		setAngle(angle);
		this.icon = new ImageIcon(icon.getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	/**
	 * Gets the Icon to be rotated
	 *
	 * @return the Icon to be rotated
	 */
	public Icon getIcon() {
		return icon;
	}
	
	public void setIcon(BufferedImage icon, int width, int height) {
		this.icon = new ImageIcon(icon.getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	/**
	 * Gets the radians of rotation. Only used for Rotate.ABOUT_CENTER.
	 *
	 * @return the radians of rotation
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Set the radians of rotation. Only used for Rotate.ABOUT_CENTER. This
	 * method only sets the degress of rotation, it will not cause the Icon to
	 * be repainted. You must invoke repaint() on any component using this icon
	 * for it to be repainted.
	 *
	 * @param angle
	 *            the radians of rotation
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}

	//
	// Implement the Icon Interface
	//

	/**
	 * Gets the width of this icon.
	 *
	 * @return the width of the icon in pixels.
	 */
	@Override
	public int getIconWidth() {
		double sin = Math.abs(Math.sin(angle));
		double cos = Math.abs(Math.cos(angle));
		int width = (int) Math.floor(icon.getIconWidth() * cos + icon.getIconHeight() * sin);
		return width;
	}

	/**
	 * Gets the height of this icon.
	 *
	 * @return the height of the icon in pixels.
	 */
	@Override
	public int getIconHeight() {
		double sin = Math.abs(Math.sin(angle));
		double cos = Math.abs(Math.cos(angle));
		int height = (int) Math.floor(icon.getIconHeight() * cos + icon.getIconWidth() * sin);
		return height;
	}

	/**
	 * Paint the icons of this compound icon at the specified location
	 *
	 * @param c
	 *            The component on which the icon is painted
	 * @param g
	 *            the graphics context
	 * @param x
	 *            the X coordinate of the icon's top-left corner
	 * @param y
	 *            the Y coordinate of the icon's top-left corner
	 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g.create();

		int cWidth = icon.getIconWidth() / 2;
		int cHeight = icon.getIconHeight() / 2;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setClip(x, y, getIconWidth(), getIconHeight());
		g2.translate((getIconWidth()) / 2 - cWidth, (getIconHeight()) / 2 - cHeight);
		
		g2.rotate(angle, x + cWidth, y + cHeight);
		icon.paintIcon(c, g2, x, y);

		g2.dispose();
	}
}