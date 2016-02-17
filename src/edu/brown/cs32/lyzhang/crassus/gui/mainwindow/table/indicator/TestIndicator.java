package edu.brown.cs32.lyzhang.crassus.gui.mainwindow.table.indicator;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

import edu.brown.cs32.lyzhang.crassus.backend.StockEventType;
import edu.brown.cs32.lyzhang.crassus.backend.StockTimeFrameData;
import edu.brown.cs32.lyzhang.crassus.gui.StockPlot;
import edu.brown.cs32.lyzhang.crassus.indicators.Indicator;

public class TestIndicator implements Indicator {

	private StockEventType idealState = StockEventType.NONE;
	
	private JFrame frame;
	
	public TestIndicator(){
		frame = new JFrame("Test Indicator");
		frame.setLayout(new FlowLayout());
		
		ButtonGroup bGroup = new ButtonGroup();
		
		JRadioButton buyButton = new JRadioButton("Buy");
		buyButton.addActionListener(
				new ActionListener(){@Override
					public void actionPerformed(ActionEvent arg0) {idealState=StockEventType.BUY;}
				});
		bGroup.add(buyButton);
		frame.add(buyButton);
		
		JRadioButton noneButton = new JRadioButton("None");
		noneButton.addActionListener(
				new ActionListener(){@Override
					public void actionPerformed(ActionEvent arg0) {idealState=StockEventType.NONE;}
				});
		bGroup.add(noneButton);
		frame.add(noneButton);
		
		JRadioButton sellButton = new JRadioButton("Sell");
		sellButton.addActionListener(
				new ActionListener(){@Override
					public void actionPerformed(ActionEvent arg0) {idealState=StockEventType.SELL;}
				});
		bGroup.add(sellButton);
		frame.add(sellButton);
		
		frame.pack();
		frame.setResizable(false);

		frame.setVisible(true);
		
	}
	
	@Override
	public void addToPlot(StockPlot stockPlot, Date startTime, Date endTime) {
	
	}

	private StockEventType state = StockEventType.NONE;
	
	@Override
	public void refresh(List<StockTimeFrameData> data) {
		state = idealState;
	}

	@Override
	public void incrementalUpdate(StockTimeFrameData datum) {
		state = idealState;
	}

	@Override
	public double getTestResults() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	private boolean visible;
	@Override
	public boolean getVisible() {
		return visible;
	}

	@Override
	public void setVisible(boolean isVisible) {
		visible = isVisible;
	}

	private boolean active;
	@Override
	public boolean getActive() {
		return active;
	}

	@Override
	public void setActive(boolean isActive) {
		active = isActive;
	}

	@Override
	public StockEventType isTriggered() {
		return state;
	}

	@Override
	public String getName() {
		return "Test Indicator";
	}

}
