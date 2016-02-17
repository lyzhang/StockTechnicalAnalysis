package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.indicator.CrassusIndicatorTable;

public class CrassusTableRowSelector {

	private JTable table;
	private boolean shouldRegisterSelection = true;
	
	public CrassusTableRowSelector(JTable table){
		this.table = table;
	}
	
	private void stopEditing(){
		TableCellEditor editor = table.getCellEditor();
		if(editor!=null)
			editor.stopCellEditing();
	}
	
	public boolean shouldRegisterSelection(){
		return shouldRegisterSelection;
	}
	
	public void select(int i){
		if(i!=-1 && i<table.getRowCount()){
			shouldRegisterSelection = false;
			table.setRowSelectionInterval(i,i);
			shouldRegisterSelection = true;
		}
		stopEditing();
	}

	public void selectLast() {
		select(table.getRowCount()-1);
	}

	public void deselect(int index) {
		if(index != table.getSelectedRow())
			return;
		
		if(table.getRowCount()==0)
			return;
		
		if(table.getRowCount()>1){
			shouldRegisterSelection = false;
			if(index == table.getRowCount()-1)
				table.setRowSelectionInterval(index-1,index-1);
			else
				table.setRowSelectionInterval(index+1,index+1);
			shouldRegisterSelection = true;
		}
		stopEditing();
	}

	public void deselectLast() {
		deselect(table.getRowCount()-1);
	}

	public void clearSelection() {
		shouldRegisterSelection = false;
		table.clearSelection();
		shouldRegisterSelection = true;
		
		stopEditing();
	}
	
	public String getTableType() {
		if(table instanceof CrassusIndicatorTable)
			return "Indicator";
		else
			return "Ticker";
	}

}
