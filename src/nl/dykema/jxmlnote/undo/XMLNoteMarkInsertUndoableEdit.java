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

import javax.swing.text.BadLocationException;
import javax.swing.undo.AbstractUndoableEdit;

import nl.dykema.jxmlnote.document.XMLNoteDocument;
import nl.dykema.jxmlnote.exceptions.MarkExistsException;
import nl.dykema.jxmlnote.exceptions.MarkNoExistException;
import nl.dykema.jxmlnote.interfaces.MarkMarkupProvider;


/**
 * This class implements an undoable edit for <code>insertMark()</code> for XMLNoteDocument.
 * 
 * @author Hans Dijkema
 *
 */
public class XMLNoteMarkInsertUndoableEdit extends AbstractUndoableEdit {
	
	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;
	
	private XMLNoteDocument 	_doc;
	private String          	_id;
	private String				_class;
	private MarkMarkupProvider 	_provider;
	private int					_offset;
	private int					_length;
	
	public void redo() {
		super.redo();
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

	public void undo() {
		super.undo();
		boolean b=_doc.getUndoManager().setIgnore(true);
		try {
			_doc.removeMark(_id);
		} catch (MarkNoExistException e) {
			e.printStackTrace();
		}
		_doc.getUndoManager().setIgnore(b);
	}
	
	public String getPresentationName() {
		return "Undo/Redo operation for insertMark()";
	}
	
	public XMLNoteMarkInsertUndoableEdit(XMLNoteDocument doc,String id, String _cl, int offset,int length) {
		_doc=doc;
		_id=id;
		_class=_cl;
		_offset=offset;
		_length=length;
	}
}
