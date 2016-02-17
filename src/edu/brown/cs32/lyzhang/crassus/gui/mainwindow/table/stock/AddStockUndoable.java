package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.stock;

import edu.brown.cs32.lyzhang.crassus.backend.Stock;
import edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.CrassusTableRowSelector;
import edu.brown.cs32.lyzhang.crassus.gui.undoable.Undoable;

public class AddStockUndoable implements Undoable {

	private final CrassusStockTableModel model;
	private final Stock stock;
	private final int previousIndex;
	private final CrassusTableRowSelector selector;
	
	public AddStockUndoable(CrassusStockTableModel model, Stock stock, int previousIndex, CrassusTableRowSelector selector){
		this.model = model;
		this.stock = stock;
		this.previousIndex = previousIndex;
		this.selector = selector;
	}
	
	@Override
	public void undo() {
		selector.select(previousIndex);
		model.removeLastStock();
	}

	@Override
	public void redo() {
		model.addLastStock(stock);
		selector.selectLast();
	}

	@Override
	public boolean isIntense() {
		return true;
	}

	@Override
	public String getName() {
		return "Addition Of Stock "+stock.getTicker();
	}

}
