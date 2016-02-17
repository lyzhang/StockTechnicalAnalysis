package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.indicator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import edu.brown.cs32.lyzhang.crassus.backend.Stock;
import edu.brown.cs32.lyzhang.crassus.backend.StockEventType;
import edu.brown.cs32.lyzhang.crassus.gui.mainwindow.CrassusStockWasAlteredListener;
import edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.TableColor;
import edu.brown.cs32.lyzhang.crassus.gui.undoable.UndoableStack;
import edu.brown.cs32.lyzhang.crassus.indicators.Indicator;

@SuppressWarnings("serial")
public class CrassusIndicatorTableEditor extends AbstractCellEditor implements TableCellEditor {

	public class EyeBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			indicator.setVisible(cbe.isSelected());
			listener.plotChanged();
			undoables.push(new EyeBoxUndoable(cbe.isSelected(),listener,table,row));
			
			stopCellEditing();
		}

	}

	public class AlertBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			indicator.setActive(cba.isSelected());
			undoables.push(new AlertBoxUndoable(cba.isSelected(),table,row));
			
			model.setTriggerState(row,cba.isSelected());
			stopCellEditing();
		}

	}

	private CrassusCheckBoxEye cbe;
	private CrassusCheckBoxAlert cba;
	private Stock stock;
	
	private Indicator indicator;
	private JTable table;
	private int row;
	
	private UndoableStack undoables;
	
	private boolean usingEye;
	
	private CrassusStockWasAlteredListener listener;
	
	private CrassusIndicatorTableModel model;
	
	public CrassusIndicatorTableEditor(CrassusStockWasAlteredListener listener, UndoableStack undoables,
			CrassusIndicatorTableModel model){
		cbe = new CrassusCheckBoxEye();
		cbe.addActionListener(new EyeBoxListener());
		cba = new CrassusCheckBoxAlert();
		cba.addActionListener(new AlertBoxListener());
		
		this.listener = listener;
		this.undoables = undoables;
		this.model = model;
	}
	
	@Override 
	public boolean shouldSelectCell(EventObject event) {
		return false;
	}
	
	@Override
	public Object getCellEditorValue() {
		if(usingEye)
			return cbe.isSelected();
		else
			return cba.isSelected();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {

		JCheckBox cb = (column==0) ? cbe : cba;
		usingEye = (column==0);
		this.indicator = stock.getEventList().get(row);
		this.table = table;
		this.row = row;
		
		cb.setBackground(TableColor.getColor(indicator.isTriggered(),indicator.getActive()));

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(cb,BorderLayout.CENTER);
		panel.setBackground(cb.getBackground());

		if (value instanceof Boolean) {
            boolean selected = (boolean) value;
            cb.setSelected(selected);
        }

		if(column==0){
			if(isSelected)
				panel.setBorder(BorderFactory.createMatteBorder(3,3,3,0,Color.BLACK));
			else
				panel.setBorder(BorderFactory.createEmptyBorder(3,3,3,0));
		}
		else if(column==1){
			if(isSelected)
				panel.setBorder(BorderFactory.createMatteBorder(3,0,3,0,Color.BLACK));
			else
				panel.setBorder(BorderFactory.createEmptyBorder(3,0,3,0));
		}
		
        return panel;
	}


	public void changeToStock(Stock stock){
		this.stock = stock;
		this.fireEditingStopped();
	}

	public void indicatorWasRemoved() {
		this.fireEditingStopped();
	}
}
