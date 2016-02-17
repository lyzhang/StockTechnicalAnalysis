package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.indicator;

import javax.swing.JTable;

import edu.brown.cs32.lyzhang.crassus.gui.mainwindow.CrassusStockWasAlteredListener;
import edu.brown.cs32.lyzhang.crassus.gui.undoable.Undoable;

public class EyeBoxUndoable implements Undoable {

	private final boolean selected;
	private final CrassusStockWasAlteredListener plotListener;
	private final JTable table;
	private final int row;
	
	public EyeBoxUndoable(boolean selected, CrassusStockWasAlteredListener plotListener, JTable table, int row) {
		this.selected = selected;
		this.plotListener = plotListener;
		this.table = table;
		this.row = row;
	}

	@Override
	public void undo() {
		table.getModel().setValueAt(!selected, row, 0);
		if(table.getCellEditor()!=null)
			table.getCellEditor().cancelCellEditing();
		plotListener.plotChanged();
	}

	@Override
	public void redo() {
		table.getModel().setValueAt(selected, row, 0);
		if(table.getCellEditor()!=null)
			table.getCellEditor().cancelCellEditing();
		plotListener.plotChanged();
	}

	@Override
	public boolean isIntense() {
		return true;
	}

	@Override
	public String getName() {
		return "Toggled Indicator Visibility";
	}

}
