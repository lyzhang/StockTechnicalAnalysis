package edu.brown.cs32.lyzhang.crassus.indicators;

import java.util.*;

import edu.brown.cs32.lyzhang.crassus.backend.StockEventType;
import edu.brown.cs32.lyzhang.crassus.backend.StockTimeFrameData;
import edu.brown.cs32.lyzhang.crassus.gui.StockPlot;

public interface Indicator {
	
	/**
	 * Adds the indicator data to the plot.
	 */
	void addToPlot(StockPlot stockPlot, Date startTime, Date endTime);
	
	/**
	 * Refreshes the indicator data values.
	 */
	void refresh(List<StockTimeFrameData> data);
	
        void incrementalUpdate(StockTimeFrameData datum);
	/*
	 * Tests the indicator against data which should have been
	 * passed into the Indicator's constructor.
	 */
	double getTestResults();
	
	/**
	 * Returns boolean whether Indicator is visible
	 */
	boolean getVisible();
	
	/**
	 * Sets whether Indicator is visible on the stock graph.
	 */
	void setVisible(boolean isVisible);
	
	/**
	 * Returns boolean whether Indicator is active.
	 */
	boolean getActive();
	
	/*
	 * Sets whether the current Indicator is active meaning user
	 * will be alerted to triggered events.
	 */
	void setActive(boolean isActive);
	
	/**
	 * Checks whether the indicator is triggered.
	 * 
	 * @return	StockEventType which is either BUY, SELL, or NONE
	 */
	StockEventType isTriggered();
	
	/**
	 * Gets the name of an Indicator
	 * 
	 * @return		String name of Indicator
	 */
	String getName();
	
}