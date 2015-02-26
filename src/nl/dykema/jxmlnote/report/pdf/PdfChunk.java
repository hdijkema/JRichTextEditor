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

package nl.dykema.jxmlnote.report.pdf;


import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import nl.dykema.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import nl.dykema.jxmlnote.report.ReportException;
import nl.dykema.jxmlnote.report.elements.Chunk;
import nl.dykema.jxmlnote.utils.BufferedImageBuilder;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfContentByte;

public class PdfChunk extends com.lowagie.text.Chunk implements Chunk {
	
	private boolean _bold=false;
	private boolean _italic=false;
	private boolean _underline=false;
	private boolean _hasImage=false;
	
	public String toString() {
		return String.format("PdfChunk: bold=%b, italic=%b, underline=%b, hasImage=%b", _bold,_italic,_underline,_hasImage);
	}
	
	public static com.lowagie.text.Image getImage(PdfReport rep,Image img,float scalePerc,float scaleWidth) {
		try {
			String instance=img.hashCode()+"-"+scalePerc+"-"+scaleWidth;
			if (rep.hasImageInstance(instance)) {
				return rep.getImageInstance(instance);
			} else {
				//com.lowagie.text.Image pi;
				Image pi=null;
				com.lowagie.text.Image pdi=null;
				if (img instanceof BufferedImage) {
					pdi=com.lowagie.text.Image.getInstance(img,null);
					//if (((BufferedImage) img).getTransparency()== Transparency.OPAQUE) {
						//pi=scaleImg(img,scalePerc,scaleWidth,rep);
						//pdi=com.lowagie.text.Image.getInstance(rep.pdfWriter(),img,rep.jpegQuality());
					//} else {
						/*Image q=BufferedImageBuilder.bufferImage(Color.white,img.getWidth(null)+1,img.getHeight(null)+1);
						Graphics2D dc=(Graphics2D) q.getGraphics();
						dc.drawImage(img,0,0,null);
						dc.dispose();
						pi=scaleImg(q,scalePerc,scaleWidth); */
						//pi=scaleImg(img,scalePerc,scaleWidth,rep);
						//BufferedImageBuilder.getScaledInstance(q, nw,nh);
						//pi=com.lowagie.text.Image.getInstance(rep.pdfWriter(),q,rep.jpegQuality());
					//}
				} else {
					//pi=scaleImg(img,scalePerc,scaleWidth,rep);//BufferedImageBuilder.getScaledInstance(img, nw,nh);
					//pi=com.lowagie.text.Image.getInstance(rep.pdfWriter(),img,rep.jpegQuality());
					pdi=com.lowagie.text.Image.getInstance(img,null);
				}
				
				//com.lowagie.text.Image pdfimg=scaleImage(pi,scalePerc,scaleWidth);
				com.lowagie.text.Image pdfimg;
				if (pdi==null) {
					//pdfimg=com.lowagie.text.Image.getInstance(rep.pdfWriter(),pi,rep.jpegQuality());
					pdfimg=com.lowagie.text.Image.getInstance(pi,null);
				} else {
					pdfimg=scaleImage(pdi,scalePerc,scaleWidth,rep);
				}
				//com.lowagie.text.Image pdfimg=com.lowagie.text.Image.getInstance(pi, Color.white);
				//com.lowagie.text.Image pdfimg=com.lowagie.text.Image.getInstance(pi,Color.white);
				rep.putImageInstance(instance,pdfimg);
				return pdfimg;
			}
		} catch (Exception e) {
			DefaultXMLNoteErrorHandler.exception(e);
			return null;
		}
	}
	
	public static com.lowagie.text.Image getImage(PdfReport rep,File img,float scalePerc,float scaleWidth) {
		try {
			String instance=img.getAbsolutePath()+"-"+scalePerc+"-"+scaleWidth;
			if (rep.hasImageInstance(instance)) {
				return rep.getImageInstance(instance);
			} else {
				com.lowagie.text.Image pdfimg=scaleImage(com.lowagie.text.Image.getInstance(img.getAbsolutePath()),scalePerc,scaleWidth,rep);
				rep.putImageInstance(instance,pdfimg);
				return pdfimg;
			}
		} catch (Exception e) {
			DefaultXMLNoteErrorHandler.exception(e);
			return null;
		}
	}
	
	private static Image scaleImg(Image img,float scalePerc,float scaleWidth,PdfReport rep) {
		if (scalePerc==100.0f) {
			return img;
		} else if (scalePerc<=0.0f) {
			if (scaleWidth<=0.0f) {
				return img;
			} else {
				try {
					if (scaleWidth>rep.getTextWidth()) { scaleWidth=rep.getTextWidth(); }
				} catch (ReportException e) {
					DefaultXMLNoteErrorHandler.exception(e);
				}
				float imgwidth=img.getWidth(null);
				float perc=(scaleWidth/imgwidth);
				int nh=Math.round(img.getHeight(null)*perc);
				int nw=Math.round(imgwidth*perc);
				return BufferedImageBuilder.getScaledInstance(img, nw, nh);
			}
		} else {
			int nh=Math.round(img.getHeight(null)*scalePerc);
			int nw=Math.round(img.getWidth(null)*scalePerc);
			return BufferedImageBuilder.getScaledInstance(img, nw, nh);
		}
	}
	
	private static com.lowagie.text.Image scaleImage(com.lowagie.text.Image img,float scalePerc,float scaleWidth,PdfReport rep) {
		if (scalePerc==100.0f) {
			return img;
		} else if (scalePerc<=0.0f) {
			if (scaleWidth<=0.0f) {
				return img;
			} else {
				try {
					if (scaleWidth>rep.getTextWidth()) { scaleWidth=rep.getTextWidth()-10; }
				} catch (ReportException e) {
					DefaultXMLNoteErrorHandler.exception(e);
				}
				float imgwidth=img.getWidth();
				float perc=(scaleWidth/imgwidth)*100.0f;
				img.scalePercent(perc);
				return img;
			}
		} else {
			img.scalePercent(scalePerc);
			return img;
		}
	}

	public PdfChunk setBackground(Color c,float padleft,float padtop,float padright,float padbottom) {
		super.setBackground(new BaseColor(c),padleft,padbottom,padright,padtop);
		return this;
	}
	
	public Chunk setTextColor(Color textColor) {
		Font f=super.getFont();
		f.setColor(new BaseColor(textColor));
		return this;
	}
	
	public Chunk setUnderline(Color c,float thinkness,float yposition) {
		super.setUnderline(new BaseColor(c),thinkness,0.0f,yposition,0.0f,PdfContentByte.LINE_CAP_ROUND);
		return this;
	}
	
	public Chunk setBold(boolean b) {
		_bold=b;
		return this;
	}

	public Chunk setItalic(boolean b) {
		_italic=b;
		return this;
	}

	public Chunk setUnderline(boolean b) {
		_underline=b;
		return this;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	// own methods
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void setFont(Font g) {
		Font f=new Font(g);
		int chst=0;
		if (_bold) { chst+=Font.BOLD; }
		if (_italic) { chst+=Font.ITALIC; }
		if (_underline) { chst+=Font.UNDERLINE; }
		f.setStyle(chst);
		super.setFont(f);
	}
	
	public boolean hasImage() {
		return _hasImage;
	}
	
	
	public PdfChunk(PdfReport rep,String txt) {
		super(txt);
	}
	
	public PdfChunk(PdfReport rep,Image img,float scalePerc,float scaleWidth) {
		super(getImage(rep,img,scalePerc,scaleWidth),0.0f,0.0f,true);
		_hasImage=true;
	}
	
	public PdfChunk(PdfReport rep,File imageFile,float scalePerc,float scaleWidth) {
		super(getImage(rep,imageFile,scalePerc,scaleWidth),0.0f,0.0f,true);
		_hasImage=true;
	}
	
}
