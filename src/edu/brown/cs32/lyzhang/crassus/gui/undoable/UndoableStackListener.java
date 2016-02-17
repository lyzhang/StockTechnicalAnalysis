package edu.brown.cs32.lyzhang.crassus.gui.undoable;

public interface UndoableStackListener {

	public void changeUndo(String string);	
	public void changeRedo(String string);
	
}
