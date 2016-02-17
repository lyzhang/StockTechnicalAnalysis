package edu.brown.cs32.lyzhang.crassus.gui.indicatorwindows;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class UndoAction extends AbstractAction 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UndoManager undo;
	
	public UndoAction(UndoManager undo)
	{
		this.undo = undo;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{		
		try 
		{
	        undo.undo();
	    } 
		catch (CannotUndoException ex) 
		{
	        System.out.println("Unable to undo: " + ex);
	        ex.printStackTrace();
	    }
	}

}
