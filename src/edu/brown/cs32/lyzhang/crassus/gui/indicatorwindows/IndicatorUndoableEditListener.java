package edu.brown.cs32.lyzhang.crassus.gui.indicatorwindows;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class IndicatorUndoableEditListener implements UndoableEditListener 
{

	private UndoManager undo;
	
	public IndicatorUndoableEditListener(UndoManager undo)
	{
		this.undo = undo;
	}
	
	@Override
	public void undoableEditHappened(UndoableEditEvent e) 
	{
		undo.addEdit(e.getEdit());
	}

}
