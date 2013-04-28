/* ******************************************************************************
 *
 *       Copyright 2008-2010 Hans Oesterholt-Dijkema
 *
 *   JRichTextEditor is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as 
 *   published by the Free Software Foundation, either version 3 of 
 *   the License, or (at your option) any later version.
 *
 *   JRichTextEditor is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with JRichTextEditor.  If not, see <http://www.gnu.org/licenses/>.
 *   
 * ******************************************************************************/

package net.oesterholt.jxmlnote.widgets;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.print.PrinterException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import net.oesterholt.jxmlnote.clipboard.XMLNoteTransferHandler;
import net.oesterholt.jxmlnote.document.DocumentAdminEvent;
import net.oesterholt.jxmlnote.document.DocumentAdminListener;
import net.oesterholt.jxmlnote.document.XMLNoteDocument;
import net.oesterholt.jxmlnote.document.XMLNoteMark;
import net.oesterholt.jxmlnote.exceptions.BadStyleException;
import net.oesterholt.jxmlnote.exceptions.MarkExistsException;
import net.oesterholt.jxmlnote.exceptions.MarkNoExistException;
import net.oesterholt.jxmlnote.exceptions.NoSelectionException;
import net.oesterholt.jxmlnote.exceptions.NoStyleException;
import net.oesterholt.jxmlnote.interfaces.MarkMarkupProviderMaker;
import net.oesterholt.jxmlnote.interfaces.UpdateViewListener;
import net.oesterholt.jxmlnote.toolbar.JXMLNoteToolBar;
import net.oesterholt.jxmlnote.undo.JXMLNotePaneCaretUndoableEdit;
import net.oesterholt.jxmlnote.widgets.marks.DefaultMarkMarkupProviderMaker;
import net.oesterholt.jxmlnote.widgets.marks.MarkMouseListener;


class TrackMouseMark implements MouseListener, MouseMotionListener {

	public void mouseDragged(MouseEvent e)  { } 
	public void mouseEntered(MouseEvent e)  { }
	public void mouseExited(MouseEvent e)   { }
	public void mousePressed(MouseEvent e)  { }
	public void mouseReleased(MouseEvent e) { }
	
	public enum Type {
		MOVE_IN, MOVE_OUT, CLICKED, DOUBLE_CLICKED
	}
	
	private JXMLNotePane 			_pane;
	private XMLNoteMark  			_trackedMark;
	private Cursor		 			_markCursor;

	public void mouseMoved(MouseEvent e) {
		Point p=e.getPoint();
		int offset=_pane.viewToModel(p);
		Vector<XMLNoteMark> v=_pane.getXMLNoteDoc().getMarksForOffset(offset);
		if (v.isEmpty()) {
			if (_trackedMark!=null) {
				if (_markCursor!=null) { 
					_pane.setCursor(_markCursor);
					_markCursor=null;
				}
				_pane.informMarkMouseListeners(Type.MOVE_OUT,_trackedMark,e);
				_trackedMark=null;
			}
		} else {
			XMLNoteMark m=v.get(0);
			if (_trackedMark==null) {
				_markCursor=_pane.getCursor();
				_trackedMark=m;
				Cursor q=_pane.informMarkMouseListeners(Type.MOVE_IN, m,e);
				if (q!=null) {
					_pane.setCursor(q);
				}
			} else if (_trackedMark!=m) {
				_pane.setCursor(_markCursor);
				_trackedMark=m;
				Cursor q=_pane.informMarkMouseListeners(Type.MOVE_IN, m,e);
				if (q!=null) {
					_pane.setCursor(q);
				}
			} else {
				// _trackedMark == m, do nothing
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		Point p=e.getPoint();
		int offset=_pane.viewToModel(p);
		Vector<XMLNoteMark> v=_pane.getXMLNoteDoc().getMarksForOffset(offset);
		if (!v.isEmpty()) {
			XMLNoteMark m=v.get(0);
			int count=e.getClickCount();
			if (count>1) {
				_pane.informMarkMouseListeners(Type.DOUBLE_CLICKED,m,e);
			} else {
				_pane.informMarkMouseListeners(Type.CLICKED,m,e);
			}
		}
	}
	
	public TrackMouseMark(JXMLNotePane p) {
		_pane=p;
		_trackedMark=null;
	}
}

public class JXMLNotePane extends JTextPane implements DocumentAdminListener, DocumentListener {
	
	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;
	
	private MarkMarkupProviderMaker		_maker;
	private Set<MarkMouseListener>		_markMouseListeners;
	private TrackMouseMark				_mouseMarkTracker;
	
	/**
	 * Use this for the property change listener for zooming factor (see setZoomFactor()).
	 */
	public final static String PROPERTY_ZOOM="zoom";

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Mouse listener stuff 
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Add a MarkMouseListener to this pane, so that you can track whether marks are clicked,
	 * double clicked, etc.
	 * 
	 * @param l
	 */
	public void addMarkMouseListener(MarkMouseListener l) {
		_markMouseListeners.add(l);
	}
	
	/**
	 * Remove a previously added MarkMouseListener.
	 * 
	 * @param l
	 */
	public void removeMarkMouseListener(MarkMouseListener l) {
		_markMouseListeners.remove(l);
	}
	
	Cursor informMarkMouseListeners(TrackMouseMark.Type type,XMLNoteMark m,MouseEvent e) {
		Iterator<MarkMouseListener> it=_markMouseListeners.iterator();
		Cursor q=null;
		while (it.hasNext()) {
			switch (type) {
				case MOVE_IN: 
					Cursor a=it.next().mouseMovedIntoMark(m,e);
					if (a!=null) { q=a; }
					break;
				case MOVE_OUT:
					it.next().mouseMovedOutOfMark(m,e);
					break;
				case CLICKED:
					it.next().markClicked(m,e);
					break;
				case DOUBLE_CLICKED:
					it.next().markDoubleClicked(m,e);
					break;
			}
		}
		return q;
	}
	
	

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Methods for manipulating the document 
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Indent the paragraph at the caret position more (to next tab stop).
	 * 
	 * @param points The number of points (1/72 inch) to indent.
	 * @throws BadLocationException 
	 */
	public void indentMore(int points) throws BadLocationException {
		int offset=getCaretPosition();
		int start=this.getSelectionStart();
		int end=this.getSelectionEnd();
		boolean b=getXMLNoteDoc().setLongEdit(true);
		getXMLNoteDoc().getUndoManager().addEdit(new JXMLNotePaneCaretUndoableEdit(this));
		if (start==end) {
			getXMLNoteDoc().indentMore(offset,0,points);
		} else {
			getXMLNoteDoc().indentMore(start,end-start,points);
		}
		getXMLNoteDoc().getUndoManager().setLongEdit(b);
	}
	
	/**
	 * Indent the paragraph at the caret position less (to previous tab stop).
	 * 
	 * @param points The number of points (1/72 inch) to indent less.
	 * @throws BadLocationException 
	 */
	public void indentLess(int points) throws BadLocationException {
		int offset=getCaretPosition();
		int start=this.getSelectionStart();
		int end=this.getSelectionEnd();
		boolean b=getXMLNoteDoc().setLongEdit(true);
		getXMLNoteDoc().getUndoManager().addEdit(new JXMLNotePaneCaretUndoableEdit(this));
		if (start==end) {
			getXMLNoteDoc().indentLess(offset,0,points);
		} else {
			getXMLNoteDoc().indentLess(start,end-start,points);
		}
		getXMLNoteDoc().getUndoManager().setLongEdit(b);
	}
	
	/**
	 * Apply the style with id styleId to the currently selected text. This is a paragraph style. It will always 
	 * apply to the whole paragraph with the selected text. If no text is selected, this is also OK. It will apply
	 * to the paragraph where the caret is in.
	 * @param styleId
	 * @throws NoSelectionException
	 * @throws BadLocationException
	 */
	public void applyStyle(String styleId) throws NoStyleException,NoSelectionException,BadLocationException {
		int offset=getSelectionStart();
		int end=getSelectionEnd();
		int len=end-offset;
		if (len<0) { throw new NoSelectionException(); }
		boolean b=getXMLNoteDoc().setLongEdit(true);
		getXMLNoteDoc().getUndoManager().addEdit(new JXMLNotePaneCaretUndoableEdit(this));
		getXMLNoteDoc().applyStyle(styleId,offset,len);
		getXMLNoteDoc().getUndoManager().setLongEdit(b);
	}
	
	/**
	 * Apply the <code>alignment</code> to the currently selected text. This is a paragraph style. It will always 
	 * apply to the whole paragraph with the selected text. If no text is selected, this is also OK. It will apply
	 * to the paragraph where the caret is in.
	 * 
	 * <code>alignment</code> must be one of StyleConstants.ALIGN_LEFT,ALIGN_RIGHT,ALIGN_CENTER or ALIGN_JUSTIFIED.
	 * @param alignment
	 * @throws NoSelectionException
	 * @throws BadLocationException
	 */
	public void applyAlign(int alignment) throws NoSelectionException,BadLocationException {
		int offset=getSelectionStart();
		int end=getSelectionEnd();
		int len=end-offset;
		if (len<0) { throw new NoSelectionException(); }
		boolean b=getXMLNoteDoc().setLongEdit(true);
		getXMLNoteDoc().getUndoManager().addEdit(new JXMLNotePaneCaretUndoableEdit(this));
		getXMLNoteDoc().applyAlign(alignment,offset,len);
		getXMLNoteDoc().getUndoManager().setLongEdit(b);
	}
	
	/**
	 * Applies bold character style to the current selection of the document.
	 *  
	 * @throws BadStyleException
	 * @throws NoSelectionException
	 * @throws BadLocationException
	 */
	public void applyBold() throws BadStyleException,BadLocationException {
		try {
			applyCharStyle(StyleConstants.Bold);
		} catch (NoSelectionException e) {
			changeCaretStyle(StyleConstants.Bold);
		}
	}

	
	/**
	 * Applies italic character style to the current selection of the document.
	 * 
	 * @throws BadStyleException
	 * @throws NoSelectionException
	 * @throws BadLocationException
	 */
	public void applyItalic() throws BadStyleException,BadLocationException {
		try {
			applyCharStyle(StyleConstants.Italic);
		} catch (NoSelectionException e) {
			changeCaretStyle(StyleConstants.Italic);
		}
	}
	

	/**
	 * Applies underline character style to the current selection of the document.
	 * 
	 * @throws BadStyleException
	 * @throws NoSelectionException
	 * @throws BadLocationException
	 */
	public void applyUnderline() throws BadStyleException,BadLocationException {
		try {
			applyCharStyle(StyleConstants.Underline);
		} catch (NoSelectionException e) {
			changeCaretStyle(StyleConstants.Underline);
		}
	}

	private void applyCharStyle(Object cstyle) throws BadStyleException,NoSelectionException,BadLocationException {
		SimpleAttributeSet set=new SimpleAttributeSet();
		set.addAttribute(cstyle, true);
		int offset=getSelectionStart();
		int end=getSelectionEnd();
		int len=end-offset;
		if (len<=0) { throw new NoSelectionException(); }
		boolean b=getXMLNoteDoc().setLongEdit(true);
		getXMLNoteDoc().getUndoManager().addEdit(new JXMLNotePaneCaretUndoableEdit(this));
		getXMLNoteDoc().applyOneCharStyle(set,offset,len);
		getXMLNoteDoc().getUndoManager().setLongEdit(b);
	}

	// special case for keyboard map
	private void changeCaretStyle(Object cstyle) {
		SimpleAttributeSet s=new SimpleAttributeSet();
		s.addAttribute(cstyle,true);
		AttributeSet set=super.getInputAttributes();
		SimpleAttributeSet s1=new SimpleAttributeSet(set);
		if (set.containsAttributes(s)) {
			s1.removeAttributes(s);
		} else {
			s1.addAttributes(s);
		}
		super.setCharacterAttributes(s1, true);
	}
	
	/*
	private void applyCharStyle(Object cstyle,int len) {
		SimpleAttributeSet set=new SimpleAttributeSet();
		set.addAttribute(cstyle, true);
		int offset=getCaretPosition();
		boolean b=getXMLNoteDoc().setLongEdit(true);
		getXMLNoteDoc().getUndoManager().addEdit(new JXMLNotePaneCaretUndoableEdit(this));
		try {
			getXMLNoteDoc().applyOneCharStyle(set,offset,len);
		} catch (Exception e) {
			// ignore
		}
		getXMLNoteDoc().getUndoManager().setLongEdit(b);
	}	*/
	
	
	/**
	 * Insert a mark of a given color for the currently selected text
	 * @param id		The id of the mark
	 * @param _class	The class of the mark
	 * @throws NoSelectionException
	 * @throws BadLocationException
	 * @return
	 */
	public boolean insertMark(String id,String _class) throws MarkExistsException, NoSelectionException,BadLocationException {
		int offset=this.getSelectionStart();
		int end=this.getSelectionEnd();
		int len=end-offset;
		return insertMark(id,_class,offset,len);
	}
	
	/**
	 * 
	 * Insert a mark of a given color for the currently selected text
	 * @param id		The id of the mark
	 * @param _class	The class of the mark
	 * @param offset    The offset in the text
	 * @param length    The length of the mark
	 * @return
	 * @throws MarkExistsException
	 * @throws NoSelectionException
	 * @throws BadLocationException
	 */
	public boolean insertMark(String id,String _class,int offset,int length) 
			throws MarkExistsException, NoSelectionException,BadLocationException {
		if (length<=0) { throw new NoSelectionException(); }
		return getXMLNoteDoc().insertMark(id, _class,offset,length);
	}
	
	/**
	 * Remove the mark with the given id.
	 * @param id
	 * @return
	 */
	public boolean removeMark(String id) throws MarkNoExistException {
		return getXMLNoteDoc().removeMark(id);
	}

	/**
	 * Returns all marks that are part of the position of the caret.
	 * see {@link net.oesterholt.jxmlnote.document.XMLNoteDocument#getMarksForOffset(int) XMLNoteDocument.getMarksForOffset}. 
	 * @return
	 */
	public Vector<XMLNoteMark> getMarksForCaret() {
		return getXMLNoteDoc().getMarksForOffset(this.getCaretPosition());
	}

	/**
	 * Returns all marks that are part of the selection of caret.
	 * see {@link net.oesterholt.jxmlnote.document.XMLNoteDocument#getMarksForSelection(int,int) XMLNoteDocument.getMarksForSelection}.
	 * This member throws a NoSelectionException if nothing has been selected.  
	 * 
	 * @throws NoSelectionException  
	 * @return
	 */
	public Vector<XMLNoteMark> getMarksForSelection() throws NoSelectionException {
		int offset=this.getSelectionStart();
		int end=this.getSelectionEnd();
		int len=end-offset;
		if (len<=0) { throw new NoSelectionException(); }
		return getXMLNoteDoc().getMarksForSelection(offset,end);
	}

	/**
	 * Returns all marks that are contained in the selection, i.e. for which <code>mark.offset()>=selectionStart</code>
	 * and <code>mark.endOffset()<selectionEnd</code>.
	 * 
	 * see {@link net.oesterholt.jxmlnote.document.XMLNoteDocment.getMarksContainedInSelection(int,in) XMLNoteDocument.getMarksContainedInSelection).
	 * 
	 * @throws NoSelectionException
	 * @return
	 */
	public Vector<XMLNoteMark> getMarksContainedInSelection() throws NoSelectionException {
		int offset=this.getSelectionStart();
		int end=this.getSelectionEnd();
		int len=end-offset;
		if (len<=0) { throw new NoSelectionException(); }
		return getXMLNoteDoc().getMarksContainedInSelection(offset,end);
	}



	/**
	 * Get the associated text pane (JTextPane)
	 * @return
	 */
	public JTextPane textPane() {
		return this; //_textPane;
	}
	
	
	/********************************************************************************************
	 * Model to view, etc., caret positioning, etc.
	 ********************************************************************************************/
	
	/**
	 * Returns the position (point) in the view for an offset in the text. 
	 * 
	 * @param offset
	 * @return
	 * @throws BadLocationException 
	 */
	public Point getPositionForOffset(int offset) throws BadLocationException {
		Rectangle r;
		r=this.modelToView(offset);
		Point p=new Point(r.x,r.y);
		return p;
	}
	
	/**
	 * Sets the caret on position 0 in the document and also clears any selection.
	 */
	public void setCaretPositionHome() {
		setCaretPosition(0);
		setSelectionStart(0);
		setSelectionEnd(0);
	}
	
	/**
	 * Returns the size of the current selection (0 if there is no selection).
	 * @return
	 */
	public int selectionSize() {
		return getSelectionEnd()-getSelectionStart();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Document Administration 
	////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean documentWillBeReset(DocumentAdminEvent e) {
		// does nothing
		return false;
	}


	public void documentHasBeenReset(DocumentAdminEvent e) {
		this.setCaretPosition(0);
	}
	
	public boolean documentWillBeCleared(DocumentAdminEvent e) {
		// does nothing
		return false;
	}


	public void documentHasBeenCleared(DocumentAdminEvent e) {
		// does nothing
	}
	
	public void documentChangedState(DocumentAdminEvent e) {
		// does nothing
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// View updating 
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Set<UpdateViewListener> _updateViewListeners;
	
	/**
	 * Adds an UpdateViewListener to this pane, which is called for every document change.
	 */
	public void addUpdateViewListener(UpdateViewListener l) {
		_updateViewListeners.add(l);
	}

	/**
	 * Removes a previously added UpdateViewListener from this pane.
	 */
	public void removeUpdateViewListener(UpdateViewListener l) {
		_updateViewListeners.remove(l);
	}
	
	public void changedUpdate(DocumentEvent e) {
		informUpdateViewListeners(e);
	}

	public void insertUpdate(DocumentEvent e) {
		informUpdateViewListeners(e);
	}

	public void removeUpdate(DocumentEvent e) {
		informUpdateViewListeners(e);
	}
	
	private void informUpdateViewListeners(DocumentEvent e) {
		Iterator<UpdateViewListener> it=_updateViewListeners.iterator();
		while (it.hasNext()) {
			it.next().updateView(e);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Document handling 
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the associated document as XMLNoteDocument. 
	 * @return
	 */
	public XMLNoteDocument getXMLNoteDoc() {
		return (XMLNoteDocument) super.getDocument();
	}
	
	
	/**
	 * Sets the document for this JXMLNotePane (please don't use textPane().setDocument().
	 * Note! external administrations to the current Document (e.g. with mark ids etc) may need to be cleared
	 * as well.
	 * @param d
	 */
	public void setDocument(XMLNoteDocument d) {
		if (getXMLNoteDoc()!=null) {
			getXMLNoteDoc().removeHighlighterForView(this.getHighlighter());
			getXMLNoteDoc().removeDocumentAdminListener(this);
			getXMLNoteDoc().removeDocumentListener(this);
		}
		
		super.setDocument(d);
		
		getXMLNoteDoc().installHighlighterForView(this.getHighlighter(),_maker);
		getXMLNoteDoc().addDocumentAdminListener(this);
		getXMLNoteDoc().addDocumentListener(this);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Mark markup 
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Sets a new MarkMarkupProvider for this pane. Removes the previous one and reinstalls
	 * the next one. 
	 * @param maker
	 */
	public void setMarkMarkupProviderMaker(MarkMarkupProviderMaker maker) {
		getXMLNoteDoc().removeHighlighterForView(this.getHighlighter());
		_maker=maker;
		getXMLNoteDoc().installHighlighterForView(this.getHighlighter(),_maker);
	}
	
	/**
	 * Returns the MarkMarkupProviderMaker associated with this pane.
	 * 
	 * @return the current MarkMarkupProviderMaker
	 */
	public MarkMarkupProviderMaker getMarkMarkupProviderMaker() {
		return _maker;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Scaling for real screen DPI's 
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private double _zoomFactor;
	
	public double getZoomFactor() {
		return _zoomFactor;
	}
	
	public double setZoomFactor(double _new) {
		double n=_zoomFactor;
		_zoomFactor=_new;
		if (super.isVisible()) {
			this.getXMLNoteDoc().fireZoomChanged();
			this.informUpdateViewListeners(new RulerRepaintEvent());
			this.firePropertyChange(JXMLNotePane.PROPERTY_ZOOM, n, _new);
		}
		return n;
	}
	
	public void initZoomFactor() {
		_zoomFactor=1.0;
	}
	
	/*public void repaint(int x, int y, int width, int height) {
		super.repaint(0, 0, getWidth(), getHeight());
	}*/
	
	/*protected EditorKit createDefaultEditorKit() {
		return new XMLNoteEditorKit(this);
	}*/
	
	public boolean print() throws PrinterException {
		double previousFactor=setZoomFactor(1.0);
		boolean b=super.print();
		setZoomFactor(previousFactor);
		return b;
	}

/*	public void paint(Graphics g) {
		if (this.isVisible()) {
			AffineTransform at=((Graphics2D) g).getTransform();
			at.concatenate(((Graphics2D) g).getDeviceConfiguration().getNormalizingTransform());
			((Graphics2D) g).setTransform(at);
			super.paint(g);
		}
	}*/

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Construction 
	////////////////////////////////////////////////////////////////////////////////////////////////////

	protected void addKeyBinding(int keyEvent,int keyMask,final String actionCommand) {
		KeyStroke k=KeyStroke.getKeyStroke(keyEvent,keyMask);
		final Action prevAction=super.getKeymap().getAction(k);
		super.getKeymap().addActionForKeyStroke(k, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				ActionEvent e1=new ActionEvent(e.getSource(), e.getID(), actionCommand, e.getWhen(), e.getModifiers());
				if (e.getSource() instanceof JXMLNotePane) {
					((JXMLNotePane) e.getSource()).getKeyBindingActionListener().actionPerformed(e1);
				} else {
					if (prevAction!=null) {
						prevAction.actionPerformed(e1);
					}
				}
			}
		});
	}
	
	protected void addCtrlKey(int keyEvent,String actionCommand) {
		addKeyBinding(keyEvent,KeyEvent.CTRL_MASK,actionCommand);
	}
	
	private ActionListener _keyBindingActionListener;
	
	public ActionListener getKeyBindingActionListener() {
		return _keyBindingActionListener;
	}
	
	protected void initKeyBindings(ActionListener l) {
		_keyBindingActionListener=l;
		addCtrlKey(KeyEvent.VK_V,JXMLNoteToolBar.ACTION_PASTE);
		addCtrlKey(KeyEvent.VK_C,JXMLNoteToolBar.ACTION_COPY);
		addCtrlKey(KeyEvent.VK_X,JXMLNoteToolBar.ACTION_CUT);
		addCtrlKey(KeyEvent.VK_A,JXMLNoteToolBar.ACTION_SELECT_ALL);
		addCtrlKey(KeyEvent.VK_Z,JXMLNoteToolBar.ACTION_UNDO);
		addCtrlKey(KeyEvent.VK_Y,JXMLNoteToolBar.ACTION_REDO);
		addCtrlKey(KeyEvent.VK_B,JXMLNoteToolBar.ACTION_BOLD);
		addCtrlKey(KeyEvent.VK_I,JXMLNoteToolBar.ACTION_ITALIC);
		addCtrlKey(KeyEvent.VK_U,JXMLNoteToolBar.ACTION_UNDERLINE);
	}
	
	/**
	 * Constructor for JXMLNotePane. Is called with an XMLNoteDocument.
	 * This will construct this pane with a DefaultMarkMarkerProviderMaker.
	 * @param d
	 */
	public JXMLNotePane(XMLNoteDocument d,ActionListener l) {
		this(d,new DefaultMarkMarkupProviderMaker(),l);
	}
	
	
	/**
	 * Constructor for JXMLNotePane. Is called with an XMLNoteDocument and
	 * a MarkMarkupProviderMaker, that creates MarkMarkupProviders for XMLNoteMarks 
	 * and provides markup for XMLNoteMarks that is custom for this view.
	 * 
	 * Once constructed, a MarkMarkupProviderMaker is cannot be changed anymore.
	 * 
	 * @param d
	 * @param maker
	 */
	public JXMLNotePane(XMLNoteDocument d,MarkMarkupProviderMaker maker,ActionListener l) {
		super();
		super.setEditorKit(new XMLNoteEditorKit(this));
		super.setDocument(d);
		initZoomFactor();
		{
			Color c=UIManager.getColor("TextField.background");
			this.setOpaque(false);
			this.setBackground(c);
		}
		
		initKeyBindings(l);
		
		DefaultCaret caret=new DefaultCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		caret.setBlinkRate(700);
		this.setCaret(caret);
		this.putClientProperty("caretWidth",2);
		this.putClientProperty("caretAspectRatio", -1);
		this.setPreferredSize(new Dimension(300,200));
		
		_updateViewListeners=new HashSet<UpdateViewListener>();
		new XMLNoteTransferHandler(this);
		
		getXMLNoteDoc().installHighlighterForView(this.getHighlighter(),maker);
		_maker=maker;
		
		// add DocumentAdminListener to reposition the caret after reset and other stuff
		getXMLNoteDoc().addDocumentAdminListener(this);
		getXMLNoteDoc().addDocumentListener(this);
		
		// Track mouse movement regarding marks
		_markMouseListeners=new HashSet<MarkMouseListener>();
		_mouseMarkTracker=new TrackMouseMark(this);
		this.addMouseMotionListener(_mouseMarkTracker);
		this.addMouseListener(_mouseMarkTracker);
	}

}


