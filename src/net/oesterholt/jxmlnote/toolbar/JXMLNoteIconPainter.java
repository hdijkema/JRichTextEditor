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

package net.oesterholt.jxmlnote.toolbar;

import java.awt.Graphics2D;
import java.awt.Component;

/**
 * This interface can be used to implement a JXMLNoteIconPainter.
 * 
 * @author Hans Oesterholt
 *
 */
public interface JXMLNoteIconPainter {
	
	/**
	 * Can be used to draw an icon in the given space (x,y)-(x+w,y+h).
	 * 
	 * @param x The left side
	 * @param y The upper side
	 * @param w The width (x+w = the right side)
	 * @param h The height (y+h = the bottom side)
	 * @param g The Graphics2D drawing context
	 * @param iconType The icon type, a string indicating what Icon must be painted.
	 */
	public void paint(int x,int y,int w,int h,Graphics2D g,Component u,String iconType);

}
