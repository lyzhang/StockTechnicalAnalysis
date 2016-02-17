package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.indicator;

import javax.swing.JTable;

import edu.brown.cs32.lyzhang.crassus.gui.mainwindow.CrassusStockWasAlteredListener;
import edu.brown.cs32.lyzhang.crassus.gui.undoable.Undoable;

public class AlertBoxUndoable implements Undoable {

	private final boolean selected;
	private final JTable table;
	private final int row;
	
	public AlertBoxUndoable(boolean selected, JTable table, int row) {
		this.selected = selected;
		this.table = table;
		this.row = row;
	}

	@Override
	public void undo() {
		table.getModel().setValueAt(!selected, row, 1);
		if(table.getCellEditor()!=null)
			table.getCellEditor().cancelCellEditing();
	}

	@Override
	public void redo() {
		table.getModel().setValueAt(selected, row, 1);
		if(table.getCellEditor()!=null)
			table.getCellEditor().cancelCellEditing();
	}

	@Override
	public boolean isIntense() {
		return true;
	}

	@Override
	public String getName() {
		return "Toggled Alerts For Indicator";
	}
	

}
