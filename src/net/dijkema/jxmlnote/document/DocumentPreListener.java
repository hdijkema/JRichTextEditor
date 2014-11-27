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

import javax.swing.event.DocumentEvent;

/**
 * Interface that implements a listener for document changes, before the changes actually happen.
 * With this interface one can prevent changes from occurring by vetoing the change.
 * 
 * @author Hans Dijkema
 *
 */
public interface DocumentPreListener {
	
	/**
	 * Indicates an insert. Return true to veto, false otherwise.
	 * @param e
	 * @return
	 */
	public boolean insertUpdate(DocumentEvent e);
	
	/**
	 * Indicates a change (e.g. application of markup). Return true to veto, false otherwise.
	 * @param e
	 * @return
	 */
	public boolean changeUpdate(DocumentEvent e);
	
	/**
	 * Indicates removal of text. Return true to veto, false otherwise. 
	 * @param e
	 * @return
	 */
	public boolean removeUpdate(DocumentEvent e);
	
}
