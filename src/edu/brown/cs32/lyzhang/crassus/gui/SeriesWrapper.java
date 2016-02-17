package edu.brown.cs32.lyzhang.crassus.gui;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.*;
import java.awt.*;


public class SeriesWrapper 
{
	private TimeSeries series;
	private Color color;
	
	
	public SeriesWrapper(TimeSeries series, Color color)
	{
		this.series = series;
		this.color = color;
	}

	TimeSeries getSeries()
	{
		return series;
	}
	
	Color getColor()
	{
		return color;
	}

}
