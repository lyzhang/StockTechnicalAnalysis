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
import edu.brown.cs32.lyzhang.crassus.indicators.MACD;
import edu.brown.cs32.lyzhang.crassus.backend.Stock;
import edu.brown.cs32.lyzhang.crassus.backend.StockFreqType;
import edu.brown.cs32.lyzhang.crassus.gui.WindowCloseListener;

/**
 * 
 * @author mlazos
 *This is the window for MACD indicator.
 */
public class MACDPanel extends JPanel 
{

	private static final long serialVersionUID = 1L;
	private WindowCloseListener closeListener;
	private JDialog parent;
	private Stock stock;
	private JTextField signalP;
	private JTextField shortP;
	private JTextField longP;
	private JLabel yearlyGain = new JLabel("Yearly: (Not tested)");
	private JLabel monthlyGain = new JLabel("Monthly: (Not tested)");
	private JLabel weeklyGain = new JLabel("Weekly: (Not tested)");
	private String signalPtt = "<html>The number of days used to calculate the moving average of the difference <br> between the shorter and longer moving averages</html>";
	private String longPtt = "<html>The number of days used to calculate <br> the shorter period moving average.</html>";
	private String shortPtt = "<html>The number of days used to calculate <br> the longer period moving average.</html>";
	
	public MACDPanel(WindowCloseListener closeListener, JDialog parent, Stock stock)
	{
		this.closeListener = closeListener;
		this.parent = parent;
		this.stock = stock;
	
		final UndoManager undoM = new UndoManager();
		
		NumberVerifier inputValidator = new NumberVerifier(this);
		
		//top panel
		JLabel signalLabel = new JLabel("Signal Period:");
		signalLabel.setToolTipText(signalPtt);
		JLabel shortLabel = new JLabel("Shorter Period:");
		shortLabel.setToolTipText(shortPtt);
		JLabel longLabel = new JLabel("Longer Period:");
		longLabel.setToolTipText(longPtt);
		
		signalP = new CrassusTextField("12", signalPtt, inputValidator, undoM);
		shortP = new CrassusTextField("9", shortPtt, inputValidator, undoM);
		longP = new CrassusTextField("26", longPtt, inputValidator, undoM);
		
		
		JPanel signalInput = new JPanel();
		signalInput.setLayout(new FlowLayout());
		signalInput.add(signalLabel);
		signalInput.add(signalP);
		
		JPanel shorterInput = new JPanel();
		shorterInput.setLayout(new FlowLayout());
		shorterInput.add(shortLabel);
		shorterInput.add(shortP);
		
		JPanel longerInput = new JPanel();
		longerInput.setLayout(new FlowLayout());
		longerInput.add(longLabel);
		longerInput.add(longP);
		
		JPanel parameters = new JPanel();
		parameters.setLayout(new BoxLayout(parameters, BoxLayout.Y_AXIS));
		parameters.add(signalInput);
		parameters.add(shorterInput);
		parameters.add(longerInput);
		
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
	
	public void setSignal(String signalPeriod)
	{
		this.signalP.setText(signalPeriod);
	}
	
	public void setShort(String shortPeriod)
	{
		this.shortP.setText(shortPeriod);
	}
	
	public void setLong(String longPeriod)
	{
		this.longP.setText(longPeriod);
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
			
			String signalPArg = signalP.getText();
			String shortPArg = shortP.getText();
			String longPArg = longP.getText();
			
			if(signalPArg == null || shortPArg == null || longPArg == null)
			{
				showErrorDialog("You must enter values.");
			}
			else
			{
				try
				{
					Indicator ind = new MACD(stock.getStockPriceData(stock.getCurrFreq()), Integer.parseInt(signalPArg), 
							Integer.parseInt(shortPArg), Integer.parseInt(longPArg));
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
			String signalPArg = signalP.getText();
			String shortPArg = shortP.getText();
			String longPArg = longP.getText();
			
			if(signalPArg == null || shortPArg == null || longPArg == null)
			{
				showErrorDialog("You must enter values.");
			}
			else
			{
				try
				{
					Indicator indDaily = new MACD(stock.getStockPriceData(StockFreqType.DAILY), Integer.parseInt(signalPArg), 
							Integer.parseInt(shortPArg), Integer.parseInt(longPArg));
					
					Indicator indWeekly = new MACD(stock.getStockPriceData(StockFreqType.WEEKLY), Integer.parseInt(signalPArg), 
							Integer.parseInt(shortPArg), Integer.parseInt(longPArg));
					
					Indicator indMonthly = new MACD(stock.getStockPriceData(StockFreqType.MONTHLY), Integer.parseInt(signalPArg), 
							Integer.parseInt(shortPArg), Integer.parseInt(longPArg));
					
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
		return "Moving Avg. Convergence Event";
	}
}
