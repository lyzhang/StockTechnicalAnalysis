package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.stock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import edu.brown.cs32.lyzhang.crassus.backend.Stock;
import edu.brown.cs32.lyzhang.crassus.backend.StockEventType;
import edu.brown.cs32.lyzhang.crassus.backend.StockList;
import edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.TableColor;

@SuppressWarnings("serial")
public class CrassusStockTableRenderer extends DefaultTableCellRenderer {

	private StockList stocks;
	
	public CrassusStockTableRenderer(){
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
			boolean hasFocus, int row, int column){
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		Stock s = stocks.getStockList().get(row);
		
		c.setBackground(TableColor.getColor(s.isTriggered()));

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(c,BorderLayout.CENTER);
		
		if(isSelected){
			c.setFont(new Font("Arial Bold", Font.BOLD, 9));
			panel.setBackground(Color.BLACK);
		}
		else{
			c.setFont(new Font("Arial", 0, 9));
			panel.setBackground(c.getBackground());
		}
		
		if(column==0)
			panel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(3,3,3,0),
					BorderFactory.createMatteBorder(0, 0, 0, 3, c.getBackground())));
		else if(column==4)
			panel.setBorder(BorderFactory.createEmptyBorder(3,0,3,3));
		else
			panel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(3,0,3,0),
					BorderFactory.createMatteBorder(0, 0, 0, 3, c.getBackground())));

		panel.setToolTipText("<html>"+s.getCompanyName()+"<br>"
				+"52-week high: "+s.getWeek52High()+"<br>"
				+"52-week low:  "+s.getWeek52Low()+"</html>");
		
		return panel;
	}
	

	public void changeStockListTo(StockList stocks) {
		this.stocks = stocks;
	}
}
