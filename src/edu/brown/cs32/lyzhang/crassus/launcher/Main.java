package edu.brown.cs32.lyzhang.crassus.launcher;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.UIManager;

import edu.brown.cs32.lyzhang.crassus.backend.StockList;
import edu.brown.cs32.lyzhang.crassus.backend.StockListImpl;
import edu.brown.cs32.lyzhang.crassus.gui.mainwindow.CrassusGUI;
import edu.brown.cs32.lyzhang.crassus.gui.mainwindow.GUI;

public class Main {

	private static GUI gui;
	private static StockList stocks;

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{

		UIManager.getDefaults().put("Button.background",Color.WHITE);//make JButtons less ugly
		UIManager.getDefaults().put("OptionPane.background", Color.WHITE);//change dialog box color
		UIManager.getDefaults().put("Panel.background", Color.WHITE);//
		UIManager.getDefaults().put("RadioButton.background", Color.WHITE);
		
		UIManager.getDefaults().put("MenuItem.background",new Color(240,240,255));
		UIManager.getDefaults().put("MenuBar.background",new Color(240,240,255));
		gui = new CrassusGUI();
		gui.launch();
	
	}

}
