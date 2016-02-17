package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table;

import edu.brown.cs32.lyzhang.crassus.gui.undoable.Undoable;

public class SelectUndoable implements Undoable {

	private final int oldIndex;
	private final int newIndex;
	private final CrassusTableRowSelector selector;
	
	public SelectUndoable(int oldIndex, int newIndex, CrassusTableRowSelector selector){
		this.oldIndex = oldIndex;
		this.newIndex = newIndex;
		this.selector = selector;
	}
	
	@Override
	public void undo() {
		selector.select(oldIndex);
	}

	@Override
	public void redo() {
		selector.select(newIndex);
	}

	@Override
	public boolean isIntense() {
		return false;
	}

	@Override
	public String getName() {
		return "Change Of " + selector.getTableType() + " Selection";
	}
}
