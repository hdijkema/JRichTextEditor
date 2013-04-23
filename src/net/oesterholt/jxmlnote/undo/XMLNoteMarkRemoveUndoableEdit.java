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

import javax.swing.text.BadLocationException;
import javax.swing.undo.AbstractUndoableEdit;

import net.oesterholt.jxmlnote.document.XMLNoteDocument;
import net.oesterholt.jxmlnote.document.XMLNoteMark;
import net.oesterholt.jxmlnote.exceptions.MarkExistsException;
import net.oesterholt.jxmlnote.exceptions.MarkNoExistException;
import net.oesterholt.jxmlnote.interfaces.MarkMarkupProvider;


/**
 * This class implements an undoable edit for <code>removeMark()</code> for XMLNoteDocument.
 * 
 * @author Hans Oesterholt-Dijkema
 *
 */
public class XMLNoteMarkRemoveUndoableEdit extends AbstractUndoableEdit {
	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;
	
	private XMLNoteDocument 	_doc;
	private String          	_id;
	private String				_class;
	private int					_offset;
	private int					_length;
	
	public void redo() {
		super.redo();
		boolean b=_doc.getUndoManager().setIgnore(true);
		try {
			_doc.removeMark(_id);
		} catch (MarkNoExistException e) {
			e.printStackTrace();
		}
		_doc.getUndoManager().setIgnore(b);
	}

	public void undo() {
		super.undo();
		boolean b=_doc.getUndoManager().setIgnore(true);
		try {
			_doc.insertMark(_id,_class,_offset,_length);
		} catch (MarkExistsException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		_doc.getUndoManager().setIgnore(b);
	}
	
	public String getPresentationName() {
		return "Undo/Redo operation for removeMark()";
	}
	
	public XMLNoteMarkRemoveUndoableEdit(XMLNoteDocument doc,XMLNoteMark m) {
		_doc=doc;
		_id=m.id();
		_class=m.markClass();
		_offset=m.offset();
		_length=m.endOffset()-_offset;
	}


}
