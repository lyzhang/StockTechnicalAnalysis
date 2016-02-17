package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.indicator;

import edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.CrassusTableRowSelector;
import edu.brown.cs32.lyzhang.crassus.gui.undoable.Undoable;
import edu.brown.cs32.lyzhang.crassus.indicators.Indicator;

public class AddIndicatorUndoable implements Undoable {

	private final CrassusIndicatorTableModel model;
	private final Indicator ind;
	private final int previousIndex;
	private final CrassusTableRowSelector selector;
	
	public AddIndicatorUndoable(CrassusIndicatorTableModel model, Indicator ind, int previousIndex, CrassusTableRowSelector selector){
		this.model = model;
		this.ind = ind;
		this.previousIndex = previousIndex;
		this.selector = selector;
	}
	
	@Override
	public void undo() {
		if(previousIndex==-1)
			selector.clearSelection();
		else
			selector.select(previousIndex);
		model.removeLastIndicator();
	}

	@Override
	public void redo() {
		model.addLastIndicator(ind);
		selector.selectLast();
	}

	@Override
	public boolean isIntense() {
		return true;
	}

	@Override
	public String getName() {
		return "Added "+ind.getName()+" Indicator";
	}

}
