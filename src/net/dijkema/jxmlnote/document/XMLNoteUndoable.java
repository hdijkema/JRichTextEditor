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

package net.dijkema.jxmlnote.document;

/**
 * This interface provides an undoable operation. Which can be added to XMLNoteDocument.
 * It will first execute the operation() and than register this action in the undoManager.
 * On undo, inverseOperation() will be called. On redo, operation() will be called. 
 * <p>
 * Note: XMLNoteDocument().addUndoable() can sometimes best be combined with setLongEdit(),
 * if multiple edits are one edit transaction.
 * <p>
 * @author Hans Dijkema
 *
 */
public interface XMLNoteUndoable {
	
	/**
	 * An operation to perform
	 * 
	 * @return  true, if the operation succeded and must be added to the undomanager. false, otherwise.
	 */
	public boolean operation();
	
	/**
	 * The inverse of operation().
	 */
	public void inverseOperation();

}
