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

package net.oesterholt.jxmlnote.widgets.marks;

import java.awt.Cursor;
import java.awt.event.MouseEvent;

import net.oesterholt.jxmlnote.document.XMLNoteMark;

/**
 * This interface can be used to listen to a XMLNotePane. It reports back
 * that marks have been clicked, double clicked, and that the mouse moves
 * into or out of marks.  
 *  
 * @author Hans Oesterholt-Dijkema
 *
 */
public interface MarkMouseListener {
	
	/**
	 * This reports that a mark has been clicked.
	 * 
	 * @param m
	 */
	public void markClicked(XMLNoteMark m,MouseEvent e);
	
	/**
	 * This reports that a mark has been double clicked.
	 * @param m
	 */
	public void markDoubleClicked(XMLNoteMark m,MouseEvent e);
	
	/**
	 * This function reports that the mouse pointer moves into the mark.
	 * It may return a Cursor that is applicable for the mouse in the mark area,
	 * or null, if the mouse should not change.
	 *  
	 * @param m
	 * @return
	 */
	public Cursor mouseMovedIntoMark(XMLNoteMark m,MouseEvent e);
	
	/**
	 * This function reports that the mouse pointer moves out of the mark.
	 * Moving the cursor out of the mark will always reset the mouse pointer
	 * to the Cursor previously before the mouse moved into the mark.
	 * 
	 * @param m
	 * @return
	 */
	public void mouseMovedOutOfMark(XMLNoteMark m,MouseEvent e);

}
