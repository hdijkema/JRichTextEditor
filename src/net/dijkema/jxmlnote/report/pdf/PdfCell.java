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

package net.dijkema.jxmlnote.report.pdf;

import java.awt.Color;

import net.dijkema.jxmlnote.report.elements.Cell;
import net.dijkema.jxmlnote.report.elements.ReportElement;

import com.lowagie.text.Chunk;
import com.lowagie.text.pdf.PdfPCell;


public class PdfCell extends PdfPCell implements Cell {

	public String toString() {
		return  String.format("PdfCell");
	}
	
	public Cell add(ReportElement p) {
		if (p instanceof PdfParagraph) {
			super.addElement((PdfParagraph) p);
		} else if (p instanceof PdfTable) {
			super.addElement((PdfTable) p);
		} else {
			super.addElement(new com.lowagie.text.Paragraph("Do not mix implementations:"+p.getClass().getName()));
		}
		return this;
	}
	
	public Cell setNoBorder() {
		super.setBorder(NO_BORDER);
		return this;
	}

	public Cell setBorder(Color c, float pt) {
		if (pt<=0f) {
			super.setBorder(NO_BORDER);
		} else {
			super.setBorder(BOX);
			super.setBorderColor(new BaseColor(c));
		}
		return this;
	}


	public Cell setBorderTop(Color c, float pt) {
		if (pt<=0f) {
			super.setBorderWidthTop(0.0f);
			super.setBorderColorTop(new BaseColor(Color.white));
		} else {
			super.setBorderWidthTop(pt);
			super.setBorderColorTop(new BaseColor(c));
		}
		return this;
	}
	
	public Cell setBorderLeft(Color c, float pt) {
		if (pt<=0f) {
			super.setBorderWidthLeft(0.0f);
			super.setBorderColorLeft(new BaseColor(Color.white));
		} else {
			super.setBorderWidthLeft(pt);
			super.setBorderColorLeft(new BaseColor(c));
		}
		return this;
	}
	
	public Cell setBorderRight(Color c, float pt) {
		if (pt<=0f) {
			super.setBorderWidthRight(0.0f);
			super.setBorderColorRight(new BaseColor(Color.white));
		} else {
			super.setBorderWidthRight(pt);
			super.setBorderColorRight(new BaseColor(c));
		}
		return this;
	}

	public Cell setBorderBottom(Color c, float pt) {
		if (pt<=0f) {
			super.setBorderWidthBottom(0.0f);
			super.setBorderColorBottom(new BaseColor(Color.white));
		} else {
			super.setBorderWidthBottom(pt);
			super.setBorderColorBottom(new BaseColor(c));
		}
		return this;
	}
	
	public Cell setBackground(Color c) {
		super.setBackgroundColor(new BaseColor(c));
		return this;
	}
	
	public PdfCell(PdfReport rep,ReportElement p) {
		if (p instanceof PdfParagraph) {
			PdfParagraph pp=((PdfParagraph) p);
			super.addElement(pp);
			super.setVerticalAlignment(ALIGN_MIDDLE);
		} else if (p instanceof PdfTable) {
			PdfTable tbl=((PdfTable) p);
			super.addElement(tbl);
			super.setVerticalAlignment(ALIGN_MIDDLE);
		} else {
			super.addElement(new com.lowagie.text.Paragraph(new Chunk("Mixing implementations:"+p.getClass().getName())));
		}
	}

}
