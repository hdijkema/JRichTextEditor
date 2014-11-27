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

import javax.swing.text.AttributeSet;

import net.dijkema.jxmlnote.report.Report;
import net.dijkema.jxmlnote.styles.XMLNoteParStyle;


public interface Paragraph extends ReportElement {
	public Paragraph setStyle(XMLNoteParStyle style);
	public XMLNoteParStyle getStyle();
	public Paragraph addAttributes(AttributeSet set);
	public Paragraph add(Chunk c);
	public Paragraph add(Tab t);
	public Tab  getNextTab();
	public Paragraph setAlignment(Report.Align a);
}
