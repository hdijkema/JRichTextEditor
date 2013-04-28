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

package net.oesterholt.jxmlnote.undo;

import javax.swing.undo.AbstractUndoableEdit;

import net.oesterholt.jxmlnote.document.XMLNoteDocument;
import net.oesterholt.jxmlnote.document.XMLNoteUndoable;

/**
 * This class implements an undoable edit for a generic <code>XMLNoteUndoable</code> operation.
 * 
 * @author Hans Oesterholt-Dijkema
 *
 */

public class XMLNoteUndoableForOperation extends AbstractUndoableEdit {
	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;
	
	private XMLNoteDocument 	_doc;
	private XMLNoteUndoable		_undoable;
	
	public void redo() {
		super.redo();
		boolean b=_doc.getUndoManager().setIgnore(true);
		_undoable.operation();
		_doc.getUndoManager().setIgnore(b);
	}

	public void undo() {
		super.undo();
		boolean b=_doc.getUndoManager().setIgnore(true);
		_undoable.inverseOperation();
		_doc.getUndoManager().setIgnore(b);
	}
	
	public String getPresentationName() {
		return "Undo/Redo operation for an XMLNoteUndoable operation";
	}
	
	public XMLNoteUndoableForOperation(XMLNoteDocument doc,XMLNoteUndoable u) {
		_doc=doc;
		_undoable=u;
	}
}
