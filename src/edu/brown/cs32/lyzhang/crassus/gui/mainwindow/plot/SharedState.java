package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.plot;

public class SharedState {

	private boolean b;
	
	public SharedState(boolean b){
		this.b = b;
	}
	
	public boolean getState(){
		return b;
	}
	
	public void setState(boolean b){
		this.b = b;
	}
}
