package edu.brown.cs32.lyzhang.crassus.gui.dialogs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import edu.brown.cs32.lyzhang.crassus.backend.DataSourceType;
import edu.brown.cs32.lyzhang.crassus.gui.CrassusButton;

@SuppressWarnings("serial")
public class DotCrassusDataSourceDialog extends JDialog {
	
	
	private ButtonGroup group;
	
	private int selectedIndex = -1;
	private CrassusButton okButton;
	
	private ClosedWithSourceListener listener;
	
	
	
	public DotCrassusDataSourceDialog(JFrame frame,ClosedWithSourceListener listener){
		super(frame, "Message");
		
		this.listener = listener;
		
		this.setLayout(new FlowLayout());
		
		this.setModal(true);
		
		this.add(new JLabel("Choose a data source"));
		
		JRadioButton yahooButton = new JRadioButton("Yahoo Finance");
		yahooButton.addActionListener(
				new ActionListener(){@Override
					public void actionPerformed(ActionEvent arg0) {selectedIndex = 0;okButton.setEnabled(true);}
				});
		JRadioButton demoDataButton = new JRadioButton("Demo Data");
		demoDataButton.addActionListener(
				new ActionListener(){@Override
					public void actionPerformed(ActionEvent e) {selectedIndex = 1;okButton.setEnabled(true);}
				});
		
		group = new ButtonGroup();
		group.add(yahooButton);
		group.add(demoDataButton);
		
		this.add(yahooButton);
		this.add(demoDataButton);
		
		okButton = new CrassusButton("OK");
		okButton.setEnabled(false);
		okButton.addActionListener(
				new ActionListener(){@Override
					public void actionPerformed(ActionEvent e) {
						outer().dispose();
						switch(selectedIndex){
						case 0:
							outer().listener.closedWithSource(DataSourceType.YAHOOFINANCE);
							break;
						case 1:
							outer().listener.closedWithSource(DataSourceType.DEMODATA);
							break;
						}
					}
				});
		
		this.add(okButton);
		
		this.pack();
	}

	private DotCrassusDataSourceDialog outer() {
		return this;
	}

}
