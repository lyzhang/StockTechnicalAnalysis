package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.indicator;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class CrassusIndicatorTable extends JTable {

	@Override
	public String getToolTipText(MouseEvent event){
	    Point p = event.getPoint();

	    // Locate the row and column
	    int hitColumnIndex = columnAtPoint(p);
	    int hitRowIndex = rowAtPoint(p);

	    if (hitColumnIndex != -1 && hitRowIndex != -1) {
	    	
	    	if(hitColumnIndex==0)
	    		return "toggle visibility";
	    	
	    	if(hitColumnIndex==1)
	    		return "toggle auto-alert";
	    }

	    // No tip from the renderer get our own tip
	    return getToolTipText();
	}
	
}
