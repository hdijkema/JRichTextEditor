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

package net.oesterholt;


import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.undo.UndoManager;

import net.oesterholt.jxmlnote.document.XMLNoteDocument;
import net.oesterholt.jxmlnote.document.XMLNoteMark;
import net.oesterholt.jxmlnote.events.JXMLNoteEditorActionListener;
import net.oesterholt.jxmlnote.exceptions.BadStyleException;
import net.oesterholt.jxmlnote.exceptions.MarkExistsException;
import net.oesterholt.jxmlnote.exceptions.MarkNoExistException;
import net.oesterholt.jxmlnote.exceptions.NoSelectionException;
import net.oesterholt.jxmlnote.exceptions.NoStyleException;
import net.oesterholt.jxmlnote.interfaces.MarkMarkupProviderMaker;
import net.oesterholt.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import net.oesterholt.jxmlnote.internationalization.XMLNoteTranslator;
import net.oesterholt.jxmlnote.toolbar.JXMLNoteToolBar;
import net.oesterholt.jxmlnote.toolbar.JXMLNoteToolBarReflect;
import net.oesterholt.jxmlnote.undo.XMLNoteUndoManager;
import net.oesterholt.jxmlnote.utils.JXMLNoteConstants;
import net.oesterholt.jxmlnote.widgets.JXMLNotePane;
import net.oesterholt.jxmlnote.widgets.JXMLNoteRuler;
import net.oesterholt.jxmlnote.widgets.marks.DefaultMarkMarkupProviderMaker;


public class JXMLNoteEditor extends JScrollPane implements ActionListener {
	
	/**
	 * Versie
	 */
	private static final long serialVersionUID = 1L;
	
	private XMLNoteTranslator 			_tr;
	private JXMLNotePane      			_notePane;
	private JXMLNoteToolBar	  			_toolbar;
	private JXMLNoteRuler	  			_ruler;
	private JXMLNoteToolBarReflect		_reflecter;
	
	// Actions
	
	public void actionPerformed(ActionEvent e) {
		try {
		
			String cmd=e.getActionCommand();
			//System.out.println("Command:"+cmd);
			if (cmd.startsWith("style:")) {
				String styleId=cmd.substring(6);
				pane().applyStyle(styleId);
			} else if (cmd.equals("font-bold")) {
				pane().applyBold();
			} else if (cmd.equals("font-italic")) {
				pane().applyItalic();
			} else if (cmd.equals("font-underline")) {
				pane().applyUnderline();
			} else if (cmd.equals("align-left")) {
				pane().applyAlign(StyleConstants.ALIGN_LEFT);
			} else if (cmd.equals("align-right")) {
				pane().applyAlign(StyleConstants.ALIGN_RIGHT);
			} else if (cmd.equals("align-center")) {
				pane().applyAlign(StyleConstants.ALIGN_CENTER);
			} else if (cmd.equals("align-justify")) {
				pane().applyAlign(StyleConstants.ALIGN_JUSTIFIED);
			} else if (cmd.equals("undo")) {
				UndoManager m=pane().getXMLNoteDoc().getUndoManager();
				if (m.canUndo()) { pane().getXMLNoteDoc().getUndoManager().undo(); }
			} else if (cmd.equals("redo")) {
				UndoManager m=pane().getXMLNoteDoc().getUndoManager();
				if (m.canRedo()) { pane().getXMLNoteDoc().getUndoManager().redo(); }
			} else if (cmd.equals("copy-to-clipboard")) {
				pane().copy();
			} else if (cmd.equals("cut-to-clipboard")) {
				pane().cut();
			} else if (cmd.equals("paste-from-clipboard")) {
				pane().paste();
			} else if (cmd.equals("select-all")) {
				pane().selectAll();
			} else if (cmd.equals("indent-more")) {
				pane().indentMore(JXMLNoteConstants.getStandardIndentPoints());
			} else if (cmd.equals("indent-less")) {
				pane().indentLess(JXMLNoteConstants.getStandardIndentPoints());
			} else if (cmd.startsWith("zoom")) {
				Pattern p=Pattern.compile("[0-9]+");
				Matcher m=p.matcher(cmd);
				if (m.find()) {
					String zoom=m.group();
					double zfactor=Double.parseDouble(zoom)/100.0;
					pane().setZoomFactor(zfactor);
				}
			} else if (cmd.equals("set-zoom")) {
				String result=JOptionPane.showInputDialog(pane(), _tr._("Give a zoom factor (in %)"), "Zooming", JOptionPane.QUESTION_MESSAGE);
				if (result!=null) {
					Pattern p=Pattern.compile("[0-9]+");
					Matcher m=p.matcher(result);
					if (m.find()) {
						String zoom=m.group();
						double zfactor=Double.parseDouble(zoom)/100.0;
						if (zfactor<0.1) { zfactor=0.1; }
						else if (zfactor>5.0) { zfactor=5.0; }
						pane().setZoomFactor(zfactor);
					}
				}
			}
		} catch (NoStyleException e1) {
			e1.printStackTrace();
		} catch (NoSelectionException e1) {
			e1.printStackTrace();
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		} catch (BadStyleException e1) {
			e1.printStackTrace();
		}
	}
	
	// toolbar state
	
	private void updateToolbarState() {
		//System.out.println("updatetoolbarstate");
		UndoManager m=pane().getXMLNoteDoc().getUndoManager();
		_toolbar.enable("@edit","undo",m.canUndo());
		_toolbar.enable("@edit","redo",m.canRedo());
	}
	
	// Getters, Setters
	
	/**
	 * @return Returns the toolbar widget associated with this editor 
	 */
	public JXMLNoteToolBar toolbar() {
		return _toolbar;
	}
	
	/**
	 * @return Returns the document associated with this editor
	 */
	public XMLNoteDocument document() {
		return pane().getXMLNoteDoc();
	}
	
	/**
	 * @return Returns the XMLNote editor pane (outside the scrollpane).
	 */
	public JXMLNotePane pane() {
		return _notePane;
	}
	
	/**
	 * Proxy for pane().setDocument().
	 * @param d
	 */
	public void setDocument(XMLNoteDocument d) {
		pane().setDocument(d);
	}
	
	/**
	 * Proxy for pane().getXMLNoteDoc().
	 * @return
	 */
	public XMLNoteDocument getDocument() {
		return pane().getXMLNoteDoc();
	}
	
	/**
	 * Proxy for pane().getCaretPosition()
	 * @return
	 */
	public int getCaretPosition() {
		return pane().getCaretPosition();
	}
	
	/**
	 * Proxy for pane().setCaretPosition()
	 * @param offset
	 */
	public void setCaretPosition(int offset) {
		pane().setCaretPosition(offset);
	}
	
	/**
	 * Proxy for pane().setCaretPositionHome()
	 */
	public void setCaretPositionHome() {
		pane().setCaretPositionHome();
	}
	
	/**
	 * Proxy for pane().getSelectionStart()
	 * @return the start of a selection on pane().
	 */
	public int getSelectionStart() {
		return pane().getSelectionStart();
	}
	
	/**
	 * Proxy for pane().getSelectionEnd()
	 * @return the end of a selection on pane().
	 */
	public int getSelectionEnd() {
		return pane().getSelectionEnd();
	}
	
	/**
	 * Proxy for pane().getPositionForOffset()
	 * @param offset
	 * @return
	 * @throws BadLocationException
	 */
	public Point getPositionForOffset(int offset) throws BadLocationException {
		return pane().getPositionForOffset(offset);
	}
	
	/**
	 * Sets the responsiveness of the editor to commands. If the editor is unresponsive,
	 * it won't accept edits.
	 * 
	 * @param a
	 */
	public void setUnresponsive(boolean a) {
		_notePane.setEditable(!a);
	}
	
	/********************************************************************************************
	 * Marks and Marker markup 
	 ********************************************************************************************/
	
	/**
	 * Proxy for pane().setMarkMarkupProviderMaker()
	 * 
	 * @param m
	 */
	public void setMarkMarkupProviderMaker(MarkMarkupProviderMaker m) {
		pane().setMarkMarkupProviderMaker(m);
	}
	
	/**
	 * Proxy for pane().getMarkMarkupProviderMaker()
	 * 
	 * @return The associated MarkMarkupProviderMaker.
	 */
	public MarkMarkupProviderMaker getMarkMarkupProviderMaker() {
		return pane().getMarkMarkupProviderMaker();
	}
	
	/**
	 * Proxy for {@link net.oesterholt.jxmlnote.widgets.JXMLNotePane#getMarksForSelection  pane().getMarksForSelection()}
	 * 
	 * @throws NoSelectionException
	 * @return
	 */
	public Vector<XMLNoteMark> getMarksForSelection() throws NoSelectionException {
		return pane().getMarksForSelection();
	}
	
	/**
	 * Proxy for {@link net.oesterholt.jxmlnote.widgets.JXMLNotePane#getMarksForCaret()  pane().getMarksForCaret()}.
	 * 
	 * @return
	 */
	public Vector<XMLNoteMark> getMarksForCaret() {
		return pane().getMarksForCaret();
	}
	
	/**
	 * Proxy for {@link net.oesterholt.jxmlnote.widgets.JXMLNotePane#getMarksContainedInSelection() pane().getMarksContainedInSelection()}.
	 * @return
	 * @throws NoSelectionException
	 */
	public Vector<XMLNoteMark> getMarksContainedInSelection() throws NoSelectionException {
		return pane().getMarksContainedInSelection();
	}
	
	/**
	 * Proxy for {@link net.oesterholt.jxmlnote.widgets.JXMLNotePane#insertMark(String) pane().insertMark()}.
	 * @param id		The id of the mark
	 * @param _class	The class of the mark
	 * @return
	 * @throws MarkExistsException
	 * @throws NoSelectionException
	 * @throws BadLocationException
	 */
	public boolean insertMark(String id,String _class) throws MarkExistsException, NoSelectionException, BadLocationException {
		return pane().insertMark(id,_class);
	}
	

	/**
	 * 
	 * Proxy for {@link net.oesterholt.jxmlnote.widgets.JXMLNotePane#insertMark(String) pane().insertMark()}.
	 * @param id		The id of the mark
	 * @param _class	The class of the mark
	 * @param offset	The offset in the text
	 * @param length	The length of the mark
	 * @return
	 * @throws MarkExistsException
	 * @throws NoSelectionException
	 * @throws BadLocationException
	 */
	public boolean insertMark(String id,String _class,int offset,int length) 
	       throws MarkExistsException, NoSelectionException, BadLocationException {
		return pane().insertMark(id, _class, offset, length);
	}
	
	/**
	 * Proxy for {@link net.oesterholt.jxmlnote.widgets.JXMLNotePane#removeMark(String) pane().removeMark()}.
	 * @param id
	 * @return
	 * @throws MarkNoExistException
	 */
	public boolean removeMark(String id) throws MarkNoExistException {
		return pane().removeMark(id);
	}

	/********************************************************************************************
	 * Reflect changes for the current caret position in the toolbar 
	 ********************************************************************************************/
	
	/**
	 * Reflects the style under the caret position in the toolbar
	 */
	public void reflectCaretInToolbar() {
		_reflecter.reflect(document(), getCaretPosition());
	}

	/********************************************************************************************
	 * Other stuff 
	 ********************************************************************************************/
	
	/**
	 * Sets the {@link net.oesterholt.jxmlnote.widgets.JXMLNotePane#setComponentPopupMenu(JPopupMenu) popup menu for  XMLNotePane} .
	 */
	public void setPopupMenu(JPopupMenu m) {
		pane().setComponentPopupMenu(m);
	}

	/**
	 * Proxy for {@link net.oesterholt.jxmlnote.widgets.JXMLNotePane#selectionSize() JXMLNotePane.selectionSize()}.
	 * @return
	 */
	public int selectionSize() {
		return pane().selectionSize();
	} 

	
	/********************************************************************************************
	 * initialization 
	 ********************************************************************************************/
	
	private void init(XMLNoteDocument doc,boolean addRuler,ActionListener ll) {
		
		_tr=new DefaultXMLNoteTranslator();
		
		// get the text pane
		JViewport v=(JViewport) super.getComponent(0);
		_notePane=(JXMLNotePane) v.getComponent(0);
		
		// toolbar and actions and reflecter
		_toolbar=new JXMLNoteToolBar(ll,pane().getXMLNoteDoc().getStyles());
		_reflecter=new JXMLNoteToolBarReflect(_toolbar);
		_notePane.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				reflectCaretInToolbar();
			}
		});

		// Undo/Redo
		//KeyStroke ctrlz=KeyStroke.getKeyStroke(KeyEvent.VK_Z,KeyEvent.CTRL_MASK);
		//_toolbar.setAccelerator("@edit","undo",ctrlz);
		//KeyStroke ctrly=KeyStroke.getKeyStroke(KeyEvent.VK_Z,KeyEvent.CTRL_MASK);
		//_toolbar.setAccelerator("@edit","redo",ctrly);
		

		/*
		 * KEYSTROKES ARE PART OF THE KEYMAP OF JXMLNotePane
		// Copy/Paste/Cut
		KeyStroke ctrlv=KeyStroke.getKeyStroke(KeyEvent.VK_V,KeyEvent.CTRL_MASK);
		_toolbar.setAccelerator(JXMLNoteToolBar.SECTION_EDIT,JXMLNoteToolBar.ACTION_PASTE,ctrlv);
		KeyStroke ctrlc=KeyStroke.getKeyStroke(KeyEvent.VK_C,KeyEvent.CTRL_MASK);
		_toolbar.setAccelerator(JXMLNoteToolBar.SECTION_EDIT,JXMLNoteToolBar.ACTION_COPY, ctrlc);
		KeyStroke ctrlx=KeyStroke.getKeyStroke(KeyEvent.VK_X,KeyEvent.CTRL_MASK);
		_toolbar.setAccelerator(JXMLNoteToolBar.SECTION_EDIT,JXMLNoteToolBar.ACTION_CUT,ctrlx);
		KeyStroke ctrla=KeyStroke.getKeyStroke(KeyEvent.VK_A,KeyEvent.CTRL_MASK);
		_toolbar.setAccelerator(JXMLNoteToolBar.SECTION_EDIT,JXMLNoteToolBar.ACTION_SELECT_ALL,ctrla);
		
		// bold, italic, underline
		KeyStroke ctrlb=KeyStroke.getKeyStroke(KeyEvent.VK_B,KeyEvent.CTRL_MASK);
		_toolbar.setAccelerator(JXMLNoteToolBar.SECTION_CHARSTYLE,JXMLNoteToolBar.ACTION_BOLD,ctrlb);
		KeyStroke ctrli=KeyStroke.getKeyStroke(KeyEvent.VK_I,KeyEvent.CTRL_MASK);
		_toolbar.setAccelerator(JXMLNoteToolBar.SECTION_CHARSTYLE,JXMLNoteToolBar.ACTION_ITALIC, ctrli);
		KeyStroke ctrlu=KeyStroke.getKeyStroke(KeyEvent.VK_U,KeyEvent.CTRL_MASK);
		_toolbar.setAccelerator(JXMLNoteToolBar.SECTION_CHARSTYLE,JXMLNoteToolBar.ACTION_UNDERLINE,ctrlu);
		*/
		
		// Ruler
		if (addRuler) {
			_ruler=new JXMLNoteRuler(_notePane.textPane());
			super.setColumnHeaderView(_ruler);
		}
		
		// Get notified when the state of the undo manager changes
		pane().getXMLNoteDoc().getUndoManager().addStateUpdater(new XMLNoteUndoManager.StateUpdater() {
			public void update(XMLNoteUndoManager m) {
				updateToolbarState();
			}
		});
	}
	
	/**
	 * Constructs a new JXMLNoteEditor with given document and a DefaultMarkMarkupProviderMaker.
	 * @param doc
	 */
	public JXMLNoteEditor(XMLNoteDocument doc) {
		this(doc,new DefaultMarkMarkupProviderMaker(),true);
	}
	
	/**
	 * Constructs a new JXMLNoteEditor with given document and a DefaultMarkMarkupProviderMaker and controls whether a ruler
	 * is created or not.
	 * @param doc
	 */
	public JXMLNoteEditor(XMLNoteDocument doc,boolean createRuler) {
		this(doc,new DefaultMarkMarkupProviderMaker(),createRuler);
	}
	
	/**
	 * Constructs a new JXMLNoteEditor with given document and MarkMarkupProviderMaker.
	 * @param doc
	 * @param maker
	 */
	public JXMLNoteEditor(XMLNoteDocument doc,MarkMarkupProviderMaker maker) {
		this(doc,maker,true);
	}
	
	/**
	 * Contructs a new JXMLNoteEditor with given document, MarkMarkupProviderMaker and controls whether a ruler
	 * is created or not.
	 * @param doc
	 * @param maker
	 * @param createRuler
	 */
	public JXMLNoteEditor(XMLNoteDocument doc,MarkMarkupProviderMaker maker,boolean createRuler) {
		this(new JXMLNoteEditorActionListener(),doc,maker,createRuler);
	}
	
	protected JXMLNoteEditor(JXMLNoteEditorActionListener l,XMLNoteDocument doc,MarkMarkupProviderMaker maker,boolean createRuler) {
		super(new JXMLNotePane(doc,maker,l),ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		l.setMe(this); 
		init(doc,createRuler,l);
	}

}


