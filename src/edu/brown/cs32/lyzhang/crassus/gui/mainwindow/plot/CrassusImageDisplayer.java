package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.plot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CrassusImageDisplayer extends JPanel {

	private static BufferedImage defaultImage;
	{
		try {
			defaultImage = ImageIO.read(new File("img/disabled.png"));
		} catch (IOException e) {
			defaultImage = null;
		}
	}
	
	
	private BufferedImage image;
	
	public CrassusImageDisplayer(){
		super();
		
		
	}
	
	public void setImage(BufferedImage image){
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(image==null){
			g.drawImage(defaultImage, 0, 0, this.getWidth(), this.getHeight(), null);
		}
		else{
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}
}
