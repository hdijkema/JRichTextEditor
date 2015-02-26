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

package nl.dykema.jxmlnote.report;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;

import nl.dykema.jxmlnote.document.XMLNoteDocument;
import nl.dykema.jxmlnote.document.XMLNoteImageIcon;
import nl.dykema.jxmlnote.document.XMLNoteMark;
import nl.dykema.jxmlnote.exceptions.BadStyleException;
import nl.dykema.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import nl.dykema.jxmlnote.interfaces.MarkMarkupProvider;
import nl.dykema.jxmlnote.interfaces.MarkMarkupProviderMaker;
import nl.dykema.jxmlnote.interfaces.MarkMarkupProvider.MarkupType;
import nl.dykema.jxmlnote.report.elements.Chunk;
import nl.dykema.jxmlnote.report.elements.Paragraph;
import nl.dykema.jxmlnote.report.elements.Tab;
import nl.dykema.jxmlnote.styles.XMLNoteParStyle;
import nl.dykema.jxmlnote.styles.XMLNoteStyleConstants;
import nl.dykema.jxmlnote.styles.XMLNoteStyles;



/**
 * XMLNoteToPdf uses iText to render a XMLNoteDocument to PDF. It can be instantiated stand alone,
 * in which case it sets up all that is needed to create a PDF document from an XMLNoteDocument.
 * <p>
 * It can also be instantiated as part of a larger iText session. In that case, it needs to be
 * instantiated with an existing PDFWriter.
 * <p>
 * This class is not thread safe.
 * <p>
 * @author Hans Dijkema
 *
 */
public class XMLNoteToReport {
	
	private MarkMarkupProviderMaker _maker;
	private XMLNoteMark				_currentMark;
	private MarkMarkupProvider		_currentProvider;
	private MarkTextProvider		_markTextProvider;
	private boolean					_cancelled;
	private Report					_report;

	/**
	 * This interface can be used during printing to provide extra text
	 * before- and after this XMLNoteMark.
	 */
	public interface MarkTextProvider {
		/**
		 * Must provide text <code>XMLNoteToReport.Moment.BEFORE</code>, or <code>XMLNoteToReport.Moment.AFTER</code>
		 * this mark is marked in the text.
		 * 
		 * @param mark			The mark that is reported 
		 * @param moment		The moment of the getting of text
		 * @return	the text
		 */
		public String provideText(XMLNoteMark mark,Moment moment);
	}
	
	/**
	 * Used in the context of MarkTextProvider 
	 */
	public enum Moment {
		BEFORE, AFTER
	}
	
	/**
	 * Render an other XMLNoteDocument to the pdf writer of the XMLNoteToPdf object.
	 * 
	 * @param doc The XMLNoteDocument to render
	 */
	public void addXMLNote(XMLNoteDocument doc,MarkMarkupProviderMaker maker,MarkTextProvider prov) {
		_maker=maker;
		_markTextProvider=prov;
		_cancelled=false;
		
		Element root=doc.getDefaultRootElement();
		int i,n;
		for(i=0,n=getLastNonWhitespacePar(doc,root);i<n && !_cancelled;i++) {
			addParagraph(doc,root.getElement(i));
		}
	}
	
	protected int getLastNonWhitespacePar(XMLNoteDocument doc,Element root) {
		int i;
		for(i=root.getElementCount()-1;i>=0 && doc.isWhiteSpaceParagraph(i);i--);
		return i+1;
	}
	
	protected void addParagraph(XMLNoteDocument doc,Element par) {
		XMLNoteStyles styles=doc.getStyles();
		String styleId=XMLNoteStyleConstants.getId(par.getAttributes());
		
		XMLNoteParStyle style=null;
		if (styleId!=null) { 
			style=styles.parStyle(styleId);
		}

		if (styleId==null) {
			try {
				style=styles.getDefaultStyle();
			} catch (BadStyleException e) {
				DefaultXMLNoteErrorHandler.exception(e);
				return;
			}
		} 

		try {
			Paragraph itpar=_report.createParagraph(style);
			itpar.addAttributes(par.getAttributes());
			addParContent(doc,itpar,par);
			_report.add(itpar);
			/*if (_report.willFit(itpar)) {
				_report.add(itpar);
			} else {
				_report.hardPageBreak();
				_report.add(itpar);
			}*/
		} catch (ReportException e) {
			DefaultXMLNoteErrorHandler.exception(e);
		} catch (BadLocationException e) {
			DefaultXMLNoteErrorHandler.exception(e);
		}
	}

	protected void addParContent(XMLNoteDocument doc,Paragraph itpar,Element par) throws BadLocationException, ReportException {
		int i,n;
		for(i=0,n=par.getElementCount();i<n;i++) {
			Element chunk=par.getElement(i);
			AttributeSet chunkAttrs=chunk.getAttributes();
			Icon icn=StyleConstants.getIcon(chunkAttrs);
			if (icn!=null) {
				if (icn instanceof XMLNoteImageIcon) {
					XMLNoteImageIcon ico=(XMLNoteImageIcon) icn;
					try {
						//Image pdfImage=getImage(ico);
						Image img=getImage(ico);
						Dimension sizeInPt=ico.getSizeInPt();
						Chunk itchunk=_report.createChunkForWidth(img,(float) sizeInPt.getWidth());
						itpar.add(itchunk);
					} catch (Exception e) {
						DefaultXMLNoteErrorHandler.exception(e);
					}
				}
			} else {
				boolean bold=StyleConstants.isBold(chunkAttrs);
				boolean italic=StyleConstants.isItalic(chunkAttrs);
				boolean underline=StyleConstants.isUnderline(chunkAttrs);

				int start=chunk.getStartOffset();
				int end=chunk.getEndOffset();
				String txt=doc.getText(start,end-start);
				int k,j,m;
				for(k=0,j=0,m=txt.length();j<m;j++) {
					Vector<XMLNoteMark> marks=doc.getMarksForOffset(start+j);
					
					if (!marks.isEmpty()) {
						if (_currentMark==null) {
							
							if (k!=j) {
								String c=txt.substring(k,j);
								Chunk itchunk=_report.createChunk(c,bold,italic,underline);
								itpar.add(itchunk);
								k=j;
							}
							_currentMark=marks.get(0);
							_currentProvider=_maker.create(_currentMark.id(), _currentMark.markClass());
							if (_markTextProvider!=null) {
								String t=_markTextProvider.provideText(_currentMark,Moment.BEFORE);
								if (t!=null) {
									outputMarkedText(itpar,t,bold,italic,underline);
								}
							}
							
						} else if (_currentMark!=marks.get(0)) {
						
							if (k!=j) {
								String c=txt.substring(k,j);
								if (_currentMark!=null) {
									if (_markTextProvider!=null) {
										String t=_markTextProvider.provideText(_currentMark,Moment.AFTER);
										if (t!=null) { c+=t; }
									}
								}
								outputMarkedText(itpar,c,bold,italic,underline);
								k=j;
							}
							_currentMark=marks.get(0);
							_currentProvider=_maker.create(_currentMark.id(), _currentMark.markClass());
							if (_markTextProvider!=null) {
								String t=_markTextProvider.provideText(_currentMark,Moment.BEFORE);
								if (t!=null) { outputMarkedText(itpar,t,bold,italic,underline); }
							}
						}
						
					} else {
						if (_currentMark!=null) {
							String c;
							if (k!=j) {
								c=txt.substring(k,j);
							} else {
								c="";
								if (_markTextProvider!=null) {
									String t=_markTextProvider.provideText(_currentMark,Moment.AFTER);
									if (t!=null) { c+=t; }
								}
								outputMarkedText(itpar,c,bold,italic,underline);
								k=j;
							}
						}
						_currentMark=null;
						_currentProvider=null;
					}
					
					switch (txt.charAt(j)) {
						case '\t': 	String c=txt.substring(k,j);
									Chunk itchunk=_report.createChunk(c,bold,italic,underline);
									setChunkMarkup(itchunk);
									itpar.add(itchunk);
									Tab tabchunk=_report.createTab(itpar);
									itpar.add(tabchunk);
									k=j+1;
							break;
					}
				}
				
				if (k<j) {
					Chunk itchunk=_report.createChunk(txt.substring(k,j),bold,italic,underline);
					setChunkMarkup(itchunk);
					itpar.add(itchunk);
				}
			}
		}
	}
	
	protected void outputMarkedText(Paragraph itpar,String t,boolean bold,boolean italic,boolean underline) throws ReportException {
		Chunk itchunk=_report.createChunk(t,bold,italic,underline);
		Color col=_currentProvider.markColor(_currentMark);
		MarkMarkupProvider.MarkupType tp=_currentProvider.type(_currentMark);
		if (tp==MarkupType.MARKER) {
			itchunk.setBackground(col,1.0f,1.0f,1.0f,1.0f);
		} else {
			itchunk.setUnderline(col, 2.0f, -3.0f );
		}
		Color textCol=_currentProvider.textColor(_currentMark);
		if (textCol!=null) {
			itchunk.setTextColor(textCol);
		}
		itpar.add(itchunk); 
	}

	void setChunkMarkup(Chunk itchunk) {
		if (_currentMark!=null) {
			Color col=_currentProvider.markColor(_currentMark);
			MarkMarkupProvider.MarkupType tp=_currentProvider.type(_currentMark);
			if (tp==MarkupType.MARKER) {
				itchunk.setBackground(col,1.0f,1.0f,1.0f,1.0f);
			} else {
				itchunk.setUnderline(col, 2.0f, -3.0f);
			}
			Color textCol=_currentProvider.textColor(_currentMark);
			if (textCol!=null) {
				itchunk.setTextColor(textCol);
			}
		}
	}
	
	protected Image getImage(XMLNoteImageIcon icn) throws IOException {
		Dimension sizeInPt=icn.getSizeInPt();
		Image pdfImage;
		if (icn.getOriginal()==null) {
			pdfImage=icn.getImage();
		} else {
			pdfImage=icn.getOriginal().getImage();
		}
		//return BufferedImageBuilder.getScaledInstance(pdfImage, (int) sizeInPt.getWidth(), (int) sizeInPt.getHeight());
		return pdfImage;
		//pdfImage.getScaledInstance((int) sizeInPt.getWidth(),(int) sizeInPt.getHeight(), Image.SCALE_SMOOTH);
	}
	

	/**
	 * Cancels addXMLNote() in the middle of a session 
	 */
	public void cancel() {
		_cancelled=true;
	}
	
	
	/**
	 * Writer/Document constructor
	 * 
	 * @param wrt	The PdfWriter to use 
	 * @param doc	The iText Document to complement
	 *  
	 */
	public XMLNoteToReport(Report rep) {
		_report=rep;
		_maker=null;
		_cancelled=false;
	}

}
