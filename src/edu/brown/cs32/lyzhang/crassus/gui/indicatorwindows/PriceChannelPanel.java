package edu.brown.cs32.lyzhang.crassus.gui.indicatorwindows;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.undo.UndoManager;

import edu.brown.cs32.lyzhang.crassus.indicators.Indicator;
import edu.brown.cs32.lyzhang.crassus.indicators.PriceChannel;
import edu.brown.cs32.lyzhang.crassus.backend.Stock;
import edu.brown.cs32.lyzhang.crassus.backend.StockFreqType;
import edu.brown.cs32.lyzhang.crassus.gui.WindowCloseListener;

public class PriceChannelPanel extends JPanel 
{

	private static final long serialVersionUID = 1L;
	private WindowCloseListener closeListener;
	private Stock stock;
	private JDialog parent;
	private JTextField lookBack;
	private JLabel yearlyGain = new JLabel("Yearly: (Not tested)");
	private JLabel monthlyGain = new JLabel("Monthly: (Not tested)");
	private JLabel weeklyGain = new JLabel("Weekly: (Not tested)");
	private String lookBacktt = "<html>The number of days to use for <br> finding the high and low.</html>";
	
	
	public PriceChannelPanel(WindowCloseListener closeListener, JDialog parent, Stock stock)
	{
		this.closeListener = closeListener;
		this.parent = parent;
		this.stock = stock;
		
		final UndoManager undoM = new UndoManager();
		
		NumberVerifier inputValidator = new NumberVerifier(this);
		//top panel
		JLabel lookBackLabel = new JLabel("Look Back Period:");
		lookBackLabel.setToolTipText(lookBacktt);
		lookBack = new CrassusTextField("20", lookBacktt, inputValidator, undoM);
		
		JPanel lookBackInput = new JPanel();
		lookBackInput.setLayout(new FlowLayout());
		lookBackInput.add(lookBackLabel);
		lookBackInput.add(lookBack);
		
		JPanel parameters = new JPanel();
		parameters.setLayout(new BoxLayout(parameters, BoxLayout.Y_AXIS));
		parameters.add(lookBackInput);
		
		ExpectedGainsPanel ep = new ExpectedGainsPanel(weeklyGain, monthlyGain, yearlyGain);
		
		//middle panel
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		JButton ok = new JButton("Ok");
		ok.addActionListener(new OkListener());
		JButton test = new JButton("Test");
		test.addActionListener(new TestListener());
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CancelListener(parent));
		buttons.add(ok);
		buttons.add(test);
		buttons.add(cancel);
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(parameters);
		this.add(buttons);
		this.add(ep);

		
	}
	
	public void setLookBack(String lookBack)
	{
		this.lookBack.setText(lookBack);
	}
	
	class OkListener extends AbstractOkListener
	{

		public OkListener() 
		{
			super(parent);
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String lookBackArg = lookBack.getText();
			
			if(lookBackArg == null)
			{
				showErrorDialog("You must enter a value.");
			}
			else
			{
				try
				{
					Indicator ind = new PriceChannel(stock.getStockPriceData(stock.getCurrFreq()), 
							Integer.parseInt(lookBackArg));
					parent.dispose();
					closeListener.windowClosedWithEvent(ind);
				}
				catch(NumberFormatException nfe)
				{
					showErrorDialog();
				}
				catch(IllegalArgumentException iae)
				{
					showErrorDialog(iae.getMessage());
				}
			}
			
		}
		
		
	}
	
	class TestListener extends AbstractTestListener
	{
		public TestListener()
		{
			super(parent, yearlyGain, monthlyGain, weeklyGain);
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String lookBackArg = lookBack.getText();
			
			if(lookBackArg == null)
			{
				showErrorDialog("You must enter a value.");
			}
			else
			{
				try
				{
					Indicator indDaily = new PriceChannel(stock.getStockPriceData(StockFreqType.DAILY), Integer.parseInt(lookBackArg));
					
					
					Indicator indWeekly = new PriceChannel(stock.getStockPriceData(StockFreqType.WEEKLY), Integer.parseInt(lookBackArg));
					
					
					Indicator indMonthly = new PriceChannel(stock.getStockPriceData(StockFreqType.MONTHLY), Integer.parseInt(lookBackArg));
					
					
					test(indDaily, indWeekly, indMonthly);
				}
				catch(NumberFormatException nfe)
				{
					showErrorDialog();
				}
				catch(IllegalArgumentException iae)
				{
					showErrorDialog(iae.getMessage());
				}
			}
			
		}
		
	}
	
	
	public String toString()
	{
		return "Price Channel Event";
	}

}
