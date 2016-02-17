package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.indicator;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class CrassusCheckBoxAlert extends JCheckBox {


	private static Icon blank;
	private static Icon alert;
	private static Icon pressed;
	
	{
		try {
			blank = new ImageIcon(ImageIO.read(new File("icons/blankIcon.png")));
			alert = new ImageIcon(ImageIO.read(new File("icons/alertIcon.png")));
			pressed = new ImageIcon(ImageIO.read(new File("icons/pressedIcon.png")));
		} catch (IOException e) {
			e.printStackTrace();
			blank = null;
			alert = null;
		}
	}
	
	public CrassusCheckBoxAlert(){
		this.setIcon(blank);
		this.setSelectedIcon(alert);
		this.setPressedIcon(pressed);
	}
	
}
