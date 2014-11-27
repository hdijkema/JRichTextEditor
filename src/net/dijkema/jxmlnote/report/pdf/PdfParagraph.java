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


import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;

import net.dijkema.jxmlnote.report.Report;
import net.dijkema.jxmlnote.report.elements.Chunk;
import net.dijkema.jxmlnote.report.elements.Paragraph;
import net.dijkema.jxmlnote.report.elements.Tab;
import net.dijkema.jxmlnote.styles.XMLNoteParStyle;


import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.FontMapper;


public class PdfParagraph extends com.lowagie.text.Paragraph implements Paragraph {
	
	private PdfReport 		_pdfReport;
	private XMLNoteParStyle	_style;
	private float			_chunks_width;
	private boolean			_hasImage;

	public Paragraph setStyle(XMLNoteParStyle style) {
		//System.out.println(super.getLeading()+","+super.getMultipliedLeading());
		_style=style;
		if (style==null) {
			return this;
		}
		
		int chst=0;
		if (style.bold()) { chst+=Font.BOLD; }
		if (style.italics()) { chst+=Font.ITALIC; }
		if (style.underline()) { chst+=Font.UNDERLINE; }
		Font f=getFont(style.getFont());
		f.setSize(style.pointSize());
		f.setStyle(chst);
		float indentleft;
		indentleft=(float) style.leftIndent();
		super.setIndentationLeft(indentleft);
		super.setSpacingAfter(style.bottomSkip());
		super.setSpacingBefore(style.topSkip());
		//super.setLeading(0.0f); DO DIT NIET!

		//System.out.println(super.getLeading()+","+super.getMultipliedLeading());
		int p1align=getAlign(style);
		super.setAlignment(p1align);
		super.setFont(f);
		//System.out.println(super.getLeading()+","+super.getMultipliedLeading());
		super.setLeading(0.0f,1.2f);
		//System.out.println(super.getLeading()+","+super.getMultipliedLeading());
		
		return this;
	}

	public XMLNoteParStyle getStyle() {
		return _style;
	}
	
	protected int getAlign(XMLNoteParStyle st) {
		String align=st.align();
		if (align.equals(XMLNoteParStyle.ALIGN_LEFT)) {
			return Element.ALIGN_LEFT;
		} else if (align.equals(XMLNoteParStyle.ALIGN_RIGHT)) {
			return Element.ALIGN_RIGHT;
		} else if (align.equals(XMLNoteParStyle.ALIGN_CENTER)) {
			return Element.ALIGN_CENTER;
		} else if (align.equals(XMLNoteParStyle.ALIGN_CENTER)) {
			return Element.ALIGN_JUSTIFIED;
		} else {
			return Element.ALIGN_LEFT;
		}
	}

	
	protected Font getFont(java.awt.Font font) {
		FontMapper map=getFontMapper();
		BaseFont f=map.awtToPdf(font);
		return new Font(f);
	}
	
	private FontMapper getFontMapper() {
		return _pdfReport.getFontMapper();
	}

	public Paragraph addAttributes(AttributeSet set) {
		float indentleft=StyleConstants.getLeftIndent(set);
		super.setIndentationLeft(indentleft);
		int p1align=_pdfReport.getAlign(set);
		super.setAlignment(p1align);
		return this;
	}
	
	public Paragraph setAlignment(Report.Align a) {
		int align=Element.ALIGN_LEFT;
		if (a.equals(Report.Align.RIGHT)) {
			align=Element.ALIGN_RIGHT;
		} else if (a.equals(Report.Align.CENTER)) {
			align=Element.ALIGN_CENTER;
		} else if (a.equals(Report.Align.JUSTIFY)) {
			align=Element.ALIGN_JUSTIFIED;
		}
		super.setAlignment(align);
		return this;
	}
	
	public String toString() {
		String pt=super.getContent();
		pt=pt.substring(0,Math.min(20, pt.length())).trim();
		return String.format("PdfParagraph:%s, hasImage=%b",pt,_hasImage);
	}

	public Paragraph  add(Chunk c) {
		if (c instanceof PdfChunk) {
			PdfChunk cc=(PdfChunk) c;
			Font f=new Font(this.getFont());
			Font cf=cc.getFont();
			if (cf!=null) { 
				BaseColor tc=null;
				if (cf.getColor()!=null) { tc=new BaseColor(cf.getColor()); }
				f.setColor(tc);
			}
			cc.setFont(f);
			if (!_hasImage) { _hasImage=cc.hasImage(); }
			super.add(cc);
			//System.out.println(this+";cw="+_chunks_width);
			_chunks_width+=cc.getWidthPoint();
			//System.out.println(this+";cw="+_chunks_width);
		} else {
			super.add(new com.lowagie.text.Chunk("Mixing implementations:"+c.getClass().getName()));
		}
		return this;
	}
	
	public Paragraph  add(Tab t) {
		if (t instanceof PdfTab) {
			super.add((PdfTab) t);
		} else {
			super.add(new com.lowagie.text.Chunk("Mixing implementations:"+t.getClass().getName()));
		}
		return this;
	}

	public boolean hasImage() {
		return _hasImage;
	}
	
	public Tab getNextTab() {
		float nextTab=getNextTabStop(_chunks_width);
		if (nextTab>0) {
			_chunks_width=nextTab;
			return new PdfTab(_pdfReport,nextTab);
			//return itpar.get(nextTab);
		} else {
			//char [] c={160};
			return new PdfTab(_pdfReport); 
		}
	}
	
	protected float getNextTabStop(float current_position_in_pt) {
		TabSet stops=_style.tabs();
		//float leftMargin=pdfDoc.leftMargin();
		float leftIndent=super.getIndentationLeft();
		float cp=current_position_in_pt-leftIndent; //-max;
		//TabSet stops=StyleConstants.getTabSet(set);
		TabStop stop=stops.getTabAfter(cp);

		//System.out.println(this+"; cp_in_pt="+current_position_in_pt+" leftindent="+leftIndent+" cp="+cp+" stop="+stop.getPosition());
		
		if (stop!=null) {
			float newpos=stop.getPosition();
			return newpos;
		} else {
			return -1.0f;
		}
	}
	
	public PdfParagraph(PdfReport rep,XMLNoteParStyle st) {
		_pdfReport=rep;
		_chunks_width=0.0f;
		_hasImage=false;
		setStyle(st);
	}


}
