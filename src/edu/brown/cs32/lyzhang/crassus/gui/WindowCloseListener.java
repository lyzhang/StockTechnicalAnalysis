package edu.brown.cs32.lyzhang.crassus.gui;

import edu.brown.cs32.lyzhang.crassus.indicators.Indicator;

public interface WindowCloseListener {

	public void windowClosedWithEvent(Indicator e);
	
	public void windowClosedWithCancel();
	
}
