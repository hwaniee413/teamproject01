package project1.server;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	private Image bgImage;
	public ImagePanel(Image bgImage) {
		this.bgImage=bgImage;
		setSize(new Dimension(bgImage.getWidth(null), bgImage.getHeight(null)));
		setPreferredSize(new Dimension(bgImage.getWidth(null), bgImage.getHeight(null)));
		setLayout(null);
	}
	public void paintComponent(Graphics g) {
		g.drawImage(bgImage, 0, 0, null);
	}
}