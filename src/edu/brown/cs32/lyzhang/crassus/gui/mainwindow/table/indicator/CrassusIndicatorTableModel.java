package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.indicator;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import edu.brown.cs32.lyzhang.crassus.backend.Stock;
import edu.brown.cs32.lyzhang.crassus.gui.mainwindow.CrassusStockWasAlteredListener;
import edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.CrassusTableRowSelector;
import edu.brown.cs32.lyzhang.crassus.indicators.Indicator;

@SuppressWarnings("serial")
public class CrassusIndicatorTableModel extends AbstractTableModel {
	
	private Stock stock;
	private CrassusTableRowSelector selector;
	
	private CrassusStockWasAlteredListener listener;
	
	public CrassusIndicatorTableModel(CrassusTableRowSelector selector, CrassusStockWasAlteredListener listener){
		this.selector = selector;
		this.listener = listener;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}
	
	@Override
	public String getColumnName(int col) {
		switch(col){
		case 0:
		case 1:
			return "";
		case 2:
			return "Event Type";
		default:
			return "ERR";
		}
	}
	
	@Override
	public Class<?> getColumnClass(int col) {
		switch(col){
		case 0:
		case 1:
			return Boolean.class;
		case 2:
			return String.class;
		default:
			return String.class;
		}
	}

	@Override
	public int getRowCount() {
		if(stock==null)
			return 0;
		else
			return stock.getEventList().size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		switch(col){
		case 0:
			return stock.getEventList().get(row).getVisible();
		case 1:
			return stock.getEventList().get(row).getActive();
		case 2:
			return stock.getEventList().get(row).getName();
		default:
			return "ERR";
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int col){
		return col <= 1;
	}
	
	@Override
	public void setValueAt(Object value, int row, int col) {
		if(stock==null || row >= stock.getEventList().size())
			return;
		
		switch(col){
		case 0:
			stock.getEventList().get(row).setVisible(((Boolean)value).booleanValue());
			break;
		case 1:
			stock.getEventList().get(row).setActive(((Boolean)value).booleanValue());
			break;
		}
		this.fireTableCellUpdated(row, col);
	}

	public void addIndicator(int i, Indicator ind) {
		stock.getEventList().add(i,ind);
		this.fireTableRowsInserted(i, i);
		selector.select(i);
		
		if(ind.getVisible())
			listener.plotChanged();
	}

	public void addLastIndicator(Indicator ind) {
		stock.addEvent(ind);
		int i = stock.getEventList().size()-1;
		this.fireTableRowsInserted(i,i);
		selector.select(i);
		
		if(ind.getVisible())
			listener.plotChanged();
	}

	public Indicator removeIndicator(int i) {
		if(i==-1)
			return null;

		selector.deselect(i);
		Indicator ind = stock.getEventList().remove(i);
		this.fireTableRowsDeleted(i, i);
		
		if(ind.getVisible()) 
			listener.plotChanged();
		
		return ind;
	}

	public Indicator removeLastIndicator() {
		return removeIndicator(stock.getEventList().size()-1);
	}

	public void changeToStock(Stock stock) {
		this.stock = stock;
		selector.clearSelection();
		this.fireTableDataChanged();
	}

	public void refresh() {
		this.fireTableRowsUpdated(0, getRowCount()-1);
	}

	public void setTriggerState(int row, boolean selected) {
		this.fireTableRowsUpdated(row, row);
		listener.stockBoxChanged();
	}

}
