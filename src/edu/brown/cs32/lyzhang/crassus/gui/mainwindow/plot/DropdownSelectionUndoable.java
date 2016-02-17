package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.plot;

import javax.swing.JComboBox;

import edu.brown.cs32.lyzhang.crassus.gui.undoable.Undoable;

public class DropdownSelectionUndoable implements Undoable {

	
	private final SharedState dropdownsRespond;
	private final JComboBox<String> timeFreq;
	private final int timeFreqOldIndex;
	private final int timeFreqNewIndex;
	private final JComboBox<String> timeScale;
	private final int timeScaleOldIndex;
	private final int timeScaleNewIndex;
	
	public DropdownSelectionUndoable(SharedState dropdownsRespond,
			JComboBox<String> timeFreq, int timeFreqOldIndex, int timeFreqNewIndex,
			JComboBox<String> timeScale, int timeScaleOldIndex, int timeScaleNewIndex){
		this.dropdownsRespond = dropdownsRespond;
		this.timeFreq = timeFreq;
		this.timeFreqOldIndex = timeFreqOldIndex;
		this.timeFreqNewIndex = timeFreqNewIndex;
		this.timeScale = timeScale;
		this.timeScaleOldIndex = timeScaleOldIndex;
		this.timeScaleNewIndex = timeScaleNewIndex;
	}
	
	@Override
	public void undo() {
		dropdownsRespond.setState(false);
		timeScale.setSelectedIndex(timeScaleOldIndex);
		timeFreq.setSelectedIndex(timeFreqOldIndex);
		dropdownsRespond.setState(true);
	}

	@Override
	public void redo() {
		dropdownsRespond.setState(false);
		timeScale.setSelectedIndex(timeScaleNewIndex);
		timeFreq.setSelectedIndex(timeFreqNewIndex);
		dropdownsRespond.setState(true);
	}

	@Override
	public boolean isIntense() {
		return false;
	}

	@Override
	public String getName() {
		return "Change Of Plotting Options";
	}

}
