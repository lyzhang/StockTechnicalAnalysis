package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table;

import java.awt.Color;

import edu.brown.cs32.lyzhang.crassus.backend.StockEventType;

public class TableColor {

	public final static Color BUY = new Color(130,230,130);
	public final static Color BUY_INACTIVE = new Color(210,255,210);
	public final static Color SELL = Color.red;//new Color(255,150,150);
	public final static Color SELL_INACTIVE = new Color(255,230,230);
	public final static Color CONFLICT = new Color(120,120,0);
	public final static Color NONE = Color.white;
	
	public static Color getColor(StockEventType state){
		if(state==null)
			return NONE;
		switch(state){
		case BUY:
			return BUY;
		case SELL:
			return SELL;
		case CONFLICT:
			return CONFLICT;
		case NONE:
		default:
			return NONE;
		}
	}
	
	public static Color getColor(StockEventType state, boolean isActive){
		if(state==null)
			return NONE;
		if(isActive)
			return getColor(state);
		
		switch(state){
		case BUY:
			return BUY_INACTIVE;
		case SELL:
			return SELL_INACTIVE;
		case NONE:
		default:
			return NONE;
		}
	}
	
	
}
