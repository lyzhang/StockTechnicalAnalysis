package edu.brown.cs32.lyzhang.crassus.gui.indicatorwindows;

import java.awt.Dimension;

import javax.swing.InputVerifier;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoManager;

public class CrassusTextField extends JTextField 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String undo = "undo";
    final String redo = "redo";
	final KeyStroke undoKey = KeyStroke.getKeyStroke("control Z");
	final KeyStroke redoKey = KeyStroke.getKeyStroke("control Y");
	
	public CrassusTextField(String initialText, String toolTipText, InputVerifier verifier, UndoManager undoM)
	{
		this.setInputVerifier(verifier);
		this.setSize(50, 20);
		this.setToolTipText(toolTipText);
		this.setPreferredSize(new Dimension(50, 20));
		this.setText(initialText);
		this.getDocument().addUndoableEditListener(new IndicatorUndoableEditListener(undoM));
		this.getActionMap().put(undo, new UndoAction(undoM));
		this.getInputMap().put(undoKey, undo);
		this.getActionMap().put(redo, new RedoAction(undoM));
		this.getInputMap().put(redoKey, redo);
	}

}
