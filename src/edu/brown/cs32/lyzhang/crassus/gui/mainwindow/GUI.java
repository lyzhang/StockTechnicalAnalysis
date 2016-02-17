package edu.brown.cs32.lyzhang.crassus.gui.mainwindow;

public interface GUI {

	/**
	 * Launches the graphical display
	 */
	public void launch();
	
	/**
	 * Should be called when there is new data to draw to the screen
	 */
	public void update();

	public void updateTables();

}
