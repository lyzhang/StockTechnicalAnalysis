package edu.brown.cs32.lyzhang.crassus.gui.undoable;

import java.util.Deque;
import java.util.LinkedList;

public class UndoableStack {
	
	private Deque<Undoable> undoables = new LinkedList<Undoable>();
	private Deque<Undoable> redoables = new LinkedList<Undoable>();
	private int maximumCapacity;
	
	private int intensity=0;
	
	private UndoableStackListener listener;
	
	public UndoableStack(int maximumCapacity, UndoableStackListener listener){
		this.maximumCapacity = maximumCapacity;
		this.listener = listener;
	}
	
	private void updateListener(){
		
		if(undoables.peek()==null){
			listener.changeUndo(null);
		}
		else{
			listener.changeUndo(undoables.peek().getName());
		}
		
		if(redoables.peek()==null){
			listener.changeRedo(null);
		}
		else{
			listener.changeRedo(redoables.peek().getName());
		}
	}
	
	public void undo(){
		if(undoables.isEmpty())
			return;
		
		Undoable u = undoables.pop();
		u.undo();
		redoables.push(u);
		
		if(u.isIntense())
			intensity--;
		updateListener();
	}
	
	public void redo(){
		if(redoables.isEmpty())
			return;
		
		Undoable u = redoables.pop();
		u.redo();
		undoables.push(u);
		
		if(u.isIntense()) 
			intensity++;
		updateListener();
	}
	
	public void push(Undoable u){
		
		redoables.clear();
		undoables.push(u);
		if(undoables.size()>maximumCapacity)
			undoables.removeLast();
		
		if(u.isIntense()) 
			intensity++;
		updateListener();
	}

	public void clear() {
		undoables.clear();
		redoables.clear();
		
		intensity = 0;
		updateListener();
	}

	public boolean isEmpty() {
		return undoables.isEmpty() && redoables.isEmpty();
	}
	
	public boolean hasNoMajorChanges() {
		return intensity==0;
	}
	
	public void clearChangeState() {
		intensity = 0;
	}
}
