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

package net.dijkema.jxmlnote.report.elements;

import java.awt.Color;

public interface Chunk {
	public Chunk setBackground(Color c,float padleft,float padtop,float padright,float padbottom);
	public Chunk setTextColor(Color textColor);
	public Chunk setUnderline(Color c,float thickness,float yposition); //,float padleft,float padtop,float padright,float padbottom);
	public Chunk setBold(boolean b);
	public Chunk setItalic(boolean b);
	public Chunk setUnderline(boolean b);
}
