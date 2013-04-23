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

package net.oesterholt.jxmlnote.report.elements;

public class Rectangle {
	private float _top;
	private float _left;
	private float _bottom;
	private float _right;
	
	public float left() { return _left; }
	public float right() { return _right; }
	public float top() { return _top; }
	public float bottom() { return _bottom; }
	public float width() { return right()-left(); }
	public float height() { return top()-bottom(); }
	
	public String toString() {
		return String.format("left=%.1f, bottom=%.1f,right=%.1f,top=%.1f,height=%.1f,width=%.1f", left(),bottom(),right(),top(),height(),width());
	}
	
	public Rectangle(float left,float top,float right,float bottom) {
		_top=top;_left=left;_right=right;_bottom=bottom;
	}
}
