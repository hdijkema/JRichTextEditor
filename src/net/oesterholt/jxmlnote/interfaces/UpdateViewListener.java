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

package net.oesterholt.jxmlnote.interfaces;

import javax.swing.event.DocumentEvent;

/**
 * This listener is called when the document of an XMLNotePane has changed.
 * 
 * @author Hans Oesterholt-Dijkema
 *
 */
public interface UpdateViewListener {
	/**
	 * This function may update parts of the view if the document has changed.
	 * The documentEvent is sent along with the updateView() request. 
	 * 
	 * @param e
	 */
	public void updateView(DocumentEvent e);
}
