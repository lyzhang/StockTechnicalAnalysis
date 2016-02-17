package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.indicator;

import edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.CrassusTableRowSelector;
import edu.brown.cs32.lyzhang.crassus.gui.undoable.Undoable;
import edu.brown.cs32.lyzhang.crassus.indicators.Indicator;

public class RemoveIndicatorUndoable implements Undoable {

	private final CrassusIndicatorTableModel model;
	private final Indicator ind;
	private final int index;
	private final CrassusTableRowSelector selector;
	
	public RemoveIndicatorUndoable(CrassusIndicatorTableModel model, Indicator ind, int index, CrassusTableRowSelector selector){
		this.model = model;
		this.ind = ind;
		this.index = index;
		this.selector = selector;
	}
	
	@Override
	public void undo() {
		model.addIndicator(index,ind);
		selector.select(index);
	}

	@Override
	public void redo() {
		selector.deselect(index);
		model.removeIndicator(index);
	}

	@Override
	public boolean isIntense() {
		return true;
	}

	@Override
	public String getName() {
		return "Removed "+ind.getName()+" Indicator";
	}

}
