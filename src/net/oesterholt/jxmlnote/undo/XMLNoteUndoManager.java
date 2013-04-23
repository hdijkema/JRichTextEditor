/* ******************************************************************************
 *
 *       Copyright 2008-2010 Hans Oesterholt-Dijkema
 *       This file is part of the JDesktop SwingX library
 *       and part of the SwingLabs project
 *
 *   SwingX is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as 
 *   published by the Free Software Foundation, either version 3 of 
 *   the License, or (at your option) any later version.
 *
 *   SwingX is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with SwingX.  If not, see <http://www.gnu.org/licenses/>.
 *   
 * ******************************************************************************/

package net.oesterholt.jxmlnote.undo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

import net.oesterholt.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import net.oesterholt.jxmlnote.internationalization.XMLNoteTranslator;
import net.oesterholt.jxmlnote.undo.MyStack.Operate;


/**
 * XMLNoteUndoManager overrides the standard Swing UndoManager. It adds methods to make the undo manager
 * ignore the next undoable edits. Further, it adds a state updater interface, that is called after 
 * important changes to the undo manager have occurred (the methods that apply will document this).
 * 
 * 
 * @author Hans Oesterholt-Dijkema
 *
 */
public class XMLNoteUndoManager extends UndoManager {
	
	private static final long serialVersionUID = 1L;


	class LongUndoableEdit implements UndoableEdit {
		
		private UndoableEdit _current,_previous;

		public boolean addEdit(UndoableEdit anEdit) {
			return _current.addEdit(anEdit);
		}

		public boolean canRedo() {
			return _current.canRedo();
		}

		public boolean canUndo() {
			return _current.canUndo();
		}

		public void die() {
			_current.die();
			if (_previous!=null) { _previous.die(); }
		}

		public String getPresentationName() {
			return _current.getPresentationName();
		}

		public String getRedoPresentationName() {
			return _current.getRedoPresentationName();
		}

		public String getUndoPresentationName() {
			return _current.getUndoPresentationName();
		}

		public boolean isSignificant() {
			return _current.isSignificant();
		}

		public void redo() throws CannotRedoException {
			if (_previous!=null) { _previous.redo(); }
			_current.redo();			
		}

		public boolean replaceEdit(UndoableEdit anEdit) {
			return _current.replaceEdit(anEdit); 
		}

		public void undo() throws CannotUndoException {
			_current.undo();
			if (_previous!=null) { _previous.undo(); }
		}
		
		public LongUndoableEdit(UndoableEdit e) {
			_current=e;
			_previous=null;
		}
		
		public LongUndoableEdit(UndoableEdit e,UndoableEdit prev) {
			_current=e;
			_previous=prev;
		}
	}
	
	class EndLongUndoableEdit extends LongUndoableEdit {
		public EndLongUndoableEdit(LongUndoableEdit e) {
			super(e._current,e._previous);
		}
	}

	private boolean 			_ignore=false;
	private boolean 			_longEdit=false;
	private LongUndoableEdit 	_le=null;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	// Updater interface
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Stat Updater interface.
	 * @author Hans Oesterholt-Dijkema
	 *
	 */
	public interface StateUpdater {
		public void update(XMLNoteUndoManager m);
	}
	
	private Set<StateUpdater>  _updaters;
	boolean                    _inupdate=false;
	
	private boolean inupdate(boolean b) {
		boolean q=_inupdate;
		_inupdate=b;
		return q;
	}
	
	private void informUpdaters() {
		if (_inupdate) { return; }
		Iterator<StateUpdater> it=_updaters.iterator();
		while(it.hasNext()) {
			it.next().update(this);
		}
	}

	/**
	 * Add state updater that is called when something important occurs with the undo manager that should
	 * be reflected in the state.
	 * @param u
	 */
	public void addStateUpdater(StateUpdater u) {
		_updaters.add(u);
	}

	/**
	 * Remove the previously added state updater.
	 * @param u
	 */
	public void removeStateUpdater(StateUpdater u) {
		_updaters.remove(u);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	// Reimplementation of UndoManager
	//////////////////////////////////////////////////////////////////////////////////////////////////
	

	private XMLNoteTranslator   _tr=new DefaultXMLNoteTranslator();
	private MyStack<UndoableEdit> _undoStack=new MyStack<UndoableEdit>();
	private MyStack<UndoableEdit> _redoStack=new MyStack<UndoableEdit>();
	private int _limit=250;
	
	private void clearStack(MyStack<UndoableEdit> s) {
		while (!s.isEmpty()) {
			s.pop().die();
		}
	}
	
	private void limitStack(MyStack<UndoableEdit> s) {
		if (s.size()>=_limit) { 
			int toDrop=s.size()-_limit;
			s.dropUntilLimit(toDrop, new Operate<UndoableEdit>() {
				public void doIt(UndoableEdit e) {
					e.die();
				}
			});
		}
	}
	
	private void limitStacks() {
		limitStack(_undoStack);
		limitStack(_redoStack);
	}
	
	/**
	 * Overrides <code>addEdit()</code> to implement ignore behaviour.
	 * 
	 * @return
	 */
	public synchronized boolean addEdit(UndoableEdit anEdit) {
		if (_ignore) {
			return true;
		} else {
			clearStack(_redoStack);
			limitStacks();
			
			boolean b=inupdate(true);
			if (_longEdit) {
				if (_undoStack.isEmpty()) {
					_undoStack.push(new LongUndoableEdit(anEdit));
				} else if (_undoStack.peek() instanceof EndLongUndoableEdit) {
					_undoStack.push(new LongUndoableEdit(anEdit));
				} else if (_undoStack.peek() instanceof LongUndoableEdit) {
					_undoStack.push(new LongUndoableEdit(anEdit,_undoStack.pop()));
				} else {
					_undoStack.push(new LongUndoableEdit(anEdit));
				}
			} else {
				_undoStack.push(anEdit);
			}
			inupdate(b);
			informUpdaters();
			
			return true;
		}
	}
	
	/**
	 * Overrides <code>discardAllEdits()</code>. Calls the state updaters after it calls the super method.
	 */
	public synchronized void discardAllEdits() {
		boolean b=inupdate(true);
		clearStack(_redoStack);
		clearStack(_undoStack);
		inupdate(b);
		informUpdaters();
	}
	
	/**
	 * Overrides <code>undo()</code>. Calls the state updaters after it calls the super method.
	 */
	public synchronized void undo() {
		boolean b=inupdate(true);
		//super.undo();
		if (!_undoStack.isEmpty()) {
			UndoableEdit e=_undoStack.pop();
			e.undo();
			_redoStack.push(e);
		}
		limitStacks();
		inupdate(b);
		informUpdaters();
	}
	
	/**
	 * Overrides <code>redo()</code>. Calls the state updaters after it calls the super method.
	 */
	public synchronized void redo() {
		boolean b=inupdate(true);
		if (!_redoStack.isEmpty()) {
			UndoableEdit e=_redoStack.pop();
			e.redo();
			_undoStack.push(e);
		}
		limitStacks();
		inupdate(b);
		informUpdaters();
	}
	
	public synchronized boolean canRedo() {
		return !_redoStack.isEmpty() && _redoStack.peek().canRedo();
	}
	
	public synchronized boolean canUndo() {
		return !_undoStack.isEmpty() && _undoStack.peek().canUndo();
	}
	
	public synchronized boolean canUndoOrRedo() {
		return canRedo() || canUndo();
	}
	
	public synchronized void end() {
		while(!_redoStack.isEmpty()) {
			_redoStack.pop().die();
		}
	}
	
	public synchronized int getLimit() {
		return _limit;
	}
	
	public synchronized String getRedoPresentationName() {
		if (!_redoStack.isEmpty()) {
			return _redoStack.peek().getRedoPresentationName();
		} else {
			return _tr._("Nothing to redo");
		}
	}
	
	public synchronized String getUndoPresentationName() {
		if (_undoStack.isEmpty()) {
			return _undoStack.peek().getUndoPresentationName();
		} else {
			return _tr._("Nothing to undo");
		}
	}
	
	public synchronized String getUndoOrRedoPresentationName() {
		if (_undoStack.isEmpty()) {
			return getRedoPresentationName();
		} else{
			return getUndoPresentationName();
		}
	}
	
	public synchronized void setLimit(int l) {
		_limit=l;
	}
	
	public String toString() {
		return "XMLNoteUndoManager:Limit="+Integer.toString(_limit)+";Redo="+_redoStack.toString()+";Undo="+_undoStack.toString();
	}
	
	public void undoableEditHappened(UndoableEditEvent e) {
		addEdit(e.getEdit());
	}
	
	public void undoOrRedo() {
		boolean b=inupdate(true);
		if (_undoStack.isEmpty()) {
			redo();
		} else {
			undo();
		}
		inupdate(b);
		informUpdaters();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	//  Ignore edits for undo
	//////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * If set to true, all addEdit() operations will be ignored until it is set to false.
	 * The previous value is returned.
	 * 
	 * @param i
	 */
	public boolean setIgnore(boolean i) {
		boolean ii=_ignore;
		_ignore=i;
		return ii;
	}

	/**
	 * Returns true, if addEdit() will ignore addEdits.
	 * 
	 * @return
	 */
	public boolean ignores() {
		return _ignore;
	}
	/**
	 * Sets long edit mode. If b==true, subsequent addEdit() calls will be assembled in one.
	 * When b==false, the combined edit will be committed using an actual internal addEdit()
	 * that makes the changes.
	 * 
	 * @param b
	 * @return
	 */
	public boolean setLongEdit(boolean b) {
		boolean q=_longEdit;
		_longEdit=b;
		if (!_longEdit) {
			if (!_undoStack.isEmpty()) {
				if (_undoStack.peek() instanceof LongUndoableEdit) {
					if (_undoStack.peek() instanceof EndLongUndoableEdit) {
						// do nothing
					} else {
						LongUndoableEdit a=(LongUndoableEdit) _undoStack.pop();
						EndLongUndoableEdit ea=new EndLongUndoableEdit(a);
						_undoStack.push(ea);
					}
				}
			}
		}
		return q;
	}
	
	/**
	 * Constructs the XMLNoteUpdateManager.
	 */
	public XMLNoteUndoManager() {
		_updaters=new HashSet<StateUpdater>();
	}
}

class MyStackException extends RuntimeException {
	public MyStackException(String msg) {
		super(msg);
	}
}


class MyStack<T> extends Vector<T> {
	
	public interface Operate<T> {
		public void doIt(T e);
	}
	
	private void checkEmpty() {
		if (isEmpty()) { 
			throw new MyStackException("Empty Stack");
		}
	}
	
	public T peek() {
		checkEmpty();
		return super.get(super.size()-1);
	}
	
	public T pop() {
		T elem=peek();
		super.remove(super.size()-1);
		return elem;
	}
	
	public void push(T e) {
		super.add(e);
	}
	
	public void dropUntilLimit(int limit,Operate<T> o) {
		int i;
		for(i=0;i<limit;i++) { o.doIt(super.get(i)); }
		super.removeRange(0, limit);
	}
}
