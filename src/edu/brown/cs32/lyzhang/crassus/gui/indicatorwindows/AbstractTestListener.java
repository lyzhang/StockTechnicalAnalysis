package edu.brown.cs32.lyzhang.crassus.gui.indicatorwindows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import edu.brown.cs32.lyzhang.crassus.backend.Stock;
import edu.brown.cs32.lyzhang.crassus.indicators.Indicator;

public abstract class AbstractTestListener implements ActionListener 
{
	private JDialog parent;
	private JLabel outputYearly;
	private JLabel outputMonthly;
	private JLabel outputDaily;
	
	public AbstractTestListener(JDialog parent, JLabel outputYearly, JLabel outputMonthly, JLabel outputDaily)
	{
		this.parent = parent;
		this.outputYearly = outputYearly;
		this.outputMonthly = outputMonthly;
		this.outputDaily = outputDaily;
	}
	
	abstract public void actionPerformed(ActionEvent e);
	
	public void test(Indicator indYearly, Indicator indMonthly, Indicator indDaily)
	{
		String yearlyGain = "Five Year: " + Double.toString(indYearly.getTestResults()) + "%";
		String monthlyGain = "Monthly: " + Double.toString(indMonthly.getTestResults()) + "%";
		String dailyGain = "Daily Gain: " + Double.toString(indDaily.getTestResults()) + "%";
		
		System.out.println("testCalled");
		
		outputYearly.setText(yearlyGain);
		System.out.println(yearlyGain);
		outputMonthly.setText(monthlyGain);
		System.out.println(monthlyGain);
		outputDaily.setText(dailyGain);
		System.out.println(dailyGain);
		outputYearly.setVisible(true);
		outputMonthly.setVisible(true);
		outputDaily.setVisible(true);
	}
	
	public void showErrorDialog(String message)
	{
		JOptionPane.showMessageDialog(parent, message, "Oops!", JOptionPane.ERROR_MESSAGE);
	}
	
	public void showErrorDialog()
	{
		JOptionPane.showMessageDialog(parent, "Input must be a number.", "Oops!", JOptionPane.ERROR_MESSAGE);
	}
}
