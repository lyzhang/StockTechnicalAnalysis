package edu.brown.cs32.lyzhang.crassus.gui.indicatorwindows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

abstract class AbstractOkListener implements ActionListener 
{
	private JDialog parent;
	
    public AbstractOkListener(JDialog parent)
	{
		this.parent = parent;
	}
	
	abstract public void actionPerformed(ActionEvent e);
	
	public void showErrorDialog(String message)
	{
		JOptionPane.showMessageDialog(parent, message, "Oops!", JOptionPane.ERROR_MESSAGE);
	}
	
	public void showErrorDialog()
	{
		JOptionPane.showMessageDialog(parent, "Input must be a number.", "Oops!", JOptionPane.ERROR_MESSAGE);
	}

}
