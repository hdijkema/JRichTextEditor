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

package net.oesterholt.jxmlnote.report.pdf;

import net.oesterholt.jxmlnote.report.Report;
import net.oesterholt.jxmlnote.report.elements.Cell;
import net.oesterholt.jxmlnote.report.elements.Table;


import com.lowagie.text.Chunk;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class PdfTable extends PdfPTable implements Table {

	public Table add(Cell cel) {
		if (cel instanceof PdfCell) {
			super.addCell((PdfCell) cel);
		} else {
			PdfPCell c=new PdfPCell();
			c.addElement(new Paragraph(new Chunk("Cannot mix different implementations")));
			super.addCell(c);
		}
		return this;
	}
	
	public Table setVSpace(float ptBefore, float ptAfter) {
		super.setSpacingBefore(ptBefore);
		super.setSpacingAfter(ptAfter);
		return this;
	}
	
	public String toString() {
		return String.format("PdfTable: columns=%d, current row=%d", super.getNumberOfColumns(),super.currentRowIdx);
	}
	
	public PdfTable(PdfReport rep,Report.Align align,float percentageOfWidth,float[] relativeCellWidths) {
		super(relativeCellWidths);
		super.setWidthPercentage(percentageOfWidth);
		super.setHorizontalAlignment(rep.getPdfAlign(align));
	}

}
