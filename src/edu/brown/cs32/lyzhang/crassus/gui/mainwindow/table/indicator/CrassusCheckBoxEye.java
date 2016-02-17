package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.indicator;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class CrassusCheckBoxEye extends JCheckBox {


	private static Icon blank;
	private static Icon eye;
	private static Icon pressed;
	
	{
		try {
			blank = new ImageIcon(ImageIO.read(new File("icons/blankIcon.png")));
			eye = new ImageIcon(ImageIO.read(new File("icons/eyeIcon.png")));
			pressed = new ImageIcon(ImageIO.read(new File("icons/pressedIcon.png")));
		} catch (IOException e) {
			e.printStackTrace();
			blank = null;
			eye = null;
		}
	}
	
	public CrassusCheckBoxEye(){
		this.setIcon(blank);
		this.setSelectedIcon(eye);
		this.setPressedIcon(pressed);
	}
	
}
