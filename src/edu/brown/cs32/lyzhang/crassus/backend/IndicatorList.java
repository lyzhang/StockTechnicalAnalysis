package edu.brown.cs32.lyzhang.crassus.backend;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs32.lyzhang.crassus.indicators.Indicator;

public class IndicatorList {

	List<Indicator> indicatorList;
	
	public IndicatorList() {
		indicatorList = new ArrayList<Indicator>();
	}
	
	/**
	 * Adds the indicator to the indicator list.
	 * 
	 * @param indicator		Indicator to add
	 */
	void add(Indicator indicator) {
		indicatorList.add(indicator);
	}
	
	/**
	 * Removes the indicator from the indicator.
	 * 
	 * @param indicator		Indicator to remove
	 * @return				true if contains Indicator
	 */
	boolean remove(Indicator indicator) {
		return indicatorList.remove(indicator);
	}
}