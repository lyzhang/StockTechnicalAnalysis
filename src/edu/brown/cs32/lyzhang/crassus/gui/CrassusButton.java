package edu.brown.cs32.lyzhang.crassus.gui;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class CrassusButton extends JButton {

	/*
	 * Credit for this code to http://www.javaprogrammingforums.com/java-swing-tutorials/3171-jbutton-enter-key-keyboard-action.html
	 */
	private void setEnterKey(){
		this.registerKeyboardAction(this.getActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                JComponent.WHEN_FOCUSED);

		this.registerKeyboardAction(this.getActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                JComponent.WHEN_FOCUSED);
	}
	
	public CrassusButton(){
		super();
		this.setEnterKey();
	}
	
	public CrassusButton(Action action){
		super(action);
		this.setEnterKey();
	}
	
	public CrassusButton(Icon icon){
		super(icon);
		this.setEnterKey();
	}
	
	public CrassusButton(String text){
		super(text);
		this.setEnterKey();
	}
	
	public CrassusButton( String text, Icon icon){
		super(text,icon);
		this.setEnterKey();
	}
	
}
