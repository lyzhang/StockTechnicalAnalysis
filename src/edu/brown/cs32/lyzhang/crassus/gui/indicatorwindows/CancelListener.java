package edu.brown.cs32.lyzhang.crassus.gui.indicatorwindows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

public class CancelListener implements ActionListener 
{
	
	private JDialog parent;
	
	public CancelListener(JDialog parent)
	{
		this.parent = parent;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		parent.dispose();
	}

}
