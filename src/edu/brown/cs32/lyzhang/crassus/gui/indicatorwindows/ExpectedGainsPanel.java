package edu.brown.cs32.lyzhang.crassus.gui.indicatorwindows;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class ExpectedGainsPanel extends JPanel
{
	public ExpectedGainsPanel(JLabel daily, JLabel monthly, JLabel yearly)
	{
		
		this.setLayout(new FlowLayout());//new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel expectedGains = new JPanel();
		expectedGains.setLayout(new BoxLayout(expectedGains, BoxLayout.Y_AXIS));
		JLabel title = new JLabel("Expected Gains");
		title.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5,5,5,5), new EtchedBorder()));
		expectedGains.add(title);
		expectedGains.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(20,20,20,20), new EtchedBorder()));
		
		
		JPanel secondaryEGains = new JPanel();
		secondaryEGains.setLayout(new BoxLayout(secondaryEGains, BoxLayout.Y_AXIS));
		yearly.setBorder(new EmptyBorder(5,5,5,5));
		monthly.setBorder(new EmptyBorder(5,5,5,5));
		daily.setBorder(new EmptyBorder(5,5,5,5));
		secondaryEGains.add(yearly);
		secondaryEGains.add(monthly);
		secondaryEGains.add(daily);
		
		expectedGains.add(secondaryEGains);
		this.add(expectedGains);
	}
}
