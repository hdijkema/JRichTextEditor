/* ******************************************************************************
 *
 *       Copyright 2008-2010 Hans Dijkema
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

package nl.dykema.jxmlnote.undo;

import javax.swing.undo.AbstractUndoableEdit;

import nl.dykema.jxmlnote.widgets.JXMLNotePane;

/**
 * This class implements a way to reposition the caret on a position that is requested using undo or redo.
 * It is used when grouping multiple edits in a LongEdit
 * 
 * @author Hans Dijkema
 *
 */
public class JXMLNotePaneCaretUndoableEdit extends AbstractUndoableEdit {

	private JXMLNotePane 	_edit;
	private int         	_offset;

	public void undo() {
		_edit.setCaretPosition(_offset);
	}

	public void redo() {
		_edit.setCaretPosition(_offset);
	}

	public String getPresentationName() {
		return "Undo operation to reset caret position to last known point";
	}
	
	public JXMLNotePaneCaretUndoableEdit(JXMLNotePane edit) {
		_edit=edit;
		_offset=_edit.getCaretPosition();
	}
}
