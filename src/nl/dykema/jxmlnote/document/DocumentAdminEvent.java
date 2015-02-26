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

package nl.dykema.jxmlnote.document;

/**
 * Provides information about the administrative event of the document.
 * 
 * @author Hans Dijkema
 *
 */
public class DocumentAdminEvent {
	
	private XMLNoteDocument _doc;
	
	/**
	 * Returns the changed state of the document.
	 * <b>true</b> if the document has been changed (and needs to be saved).
	 * 
	 * @return
	 */
	public boolean changed() {
		return _doc.changed();
	}
	
	/**
	 * Returns the document that initiated this event.
	 * 
	 * @return
	 */
	public XMLNoteDocument document() {
		return _doc;
	}
	
	
	/**
	 * Constructs the event. 
	 * 
	 * @param d
	 */
	public DocumentAdminEvent(XMLNoteDocument d) {
		_doc=d;
	}
}
