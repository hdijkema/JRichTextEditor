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

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;

import net.oesterholt.jxmlnote.document.XMLNoteDocument;
import net.oesterholt.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.oesterholt.jxmlnote.interfaces.MarkMarkupProviderMaker;
import net.oesterholt.jxmlnote.report.Report;
import net.oesterholt.jxmlnote.report.ReportException;
import net.oesterholt.jxmlnote.report.XMLNoteToReport;
import net.oesterholt.jxmlnote.report.ReportProgressBar.Progress;
import net.oesterholt.jxmlnote.report.elements.Cell;
import net.oesterholt.jxmlnote.report.elements.Chunk;
import net.oesterholt.jxmlnote.report.elements.Paragraph;
import net.oesterholt.jxmlnote.report.elements.Rectangle;
import net.oesterholt.jxmlnote.report.elements.ReportElement;
import net.oesterholt.jxmlnote.report.elements.Tab;
import net.oesterholt.jxmlnote.report.elements.Table;
import net.oesterholt.jxmlnote.styles.XMLNoteParStyle;


import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.FontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEvent;
import com.lowagie.text.pdf.PdfWriter;


public class PdfReport extends Report {

	private PdfWriter _writer;
	private Document _doc;
	private ColumnText _text;
	private float _jpegQuality = 0.9f;
	private FontMapper _mapper;
	private com.lowagie.text.Rectangle _pdfPageSize;
	private Orientation _orientation=Orientation.PORTRAIT;
	private PageSize _pageSize=PageSize.A4;
	private boolean _canceled = false;
	private File fontmapcacheFile=null;
	private InputStream _fontmapcachein=null;
	private OutputStream _fontmapcacheout=null;
	
	private Hashtable<String,com.lowagie.text.Image> _imageInstances;

	// /////////////////////////////////////////////////////////////////////////////////
	// Support methods
	// /////////////////////////////////////////////////////////////////////////////////
	
	public com.lowagie.text.Image getImageInstance(String key) {
		return _imageInstances.get(key);
	}
	
	public void putImageInstance(String key,com.lowagie.text.Image inst) {
		_imageInstances.put(key,inst);
	}
	
	public boolean hasImageInstance(String key) {
		return _imageInstances.containsKey(key);
	}
	
	public String toString() {
		Rectangle pr=new Rectangle(0.0f, 0.0f, 0.0f, 0.0f);
		Rectangle mg=new Rectangle(0.0f, 0.0f, 0.0f, 0.0f);
		try {
			mg=getMargins();
			pr=getPageRect();
		} catch (ReportException E) { }
		return String.format("PdfReport: PageSize=%s, Margins=%s",pr.toString(),mg.toString());
	}

	public int getPdfAlign(Report.Align a) {
		if (a.equals(Align.LEFT)) {
			return Element.ALIGN_LEFT;
		} else if (a.equals(Align.RIGHT)) {
			return Element.ALIGN_RIGHT;
		} else if (a.equals(Align.CENTER)) {
			return Element.ALIGN_CENTER;
		} else if (a.equals(Align.JUSTIFY)) {
			return Element.ALIGN_JUSTIFIED;
		} else {
			return Element.ALIGN_LEFT;
		}
	}

	public int getAlign(AttributeSet s) {
		if (StyleConstants.getAlignment(s) == StyleConstants.ALIGN_LEFT) {
			return Element.ALIGN_LEFT;
		} else if (StyleConstants.getAlignment(s) == StyleConstants.ALIGN_CENTER) {
			return Element.ALIGN_CENTER;
		} else if (StyleConstants.getAlignment(s) == StyleConstants.ALIGN_RIGHT) {
			return Element.ALIGN_RIGHT;
		} else if (StyleConstants.getAlignment(s) == StyleConstants.ALIGN_JUSTIFIED) {
			return Element.ALIGN_JUSTIFIED;
		} else {
			return Element.ALIGN_LEFT;
		}
	}

	com.lowagie.text.Rectangle getPdfPageSize() {
		return _pdfPageSize;
	}

	public FontMapper getFontMapper() {
		if (_fontmapcacheout!=null) {
			try {
				_mapper=PdfFontMapper.createPdfFontMapper(this,_fontmapcachein,_fontmapcacheout);
			} catch (IOException e) {
				DefaultXMLNoteErrorHandler.exception(e);
				_mapper=PdfFontMapper.createPdfFontMapper(this,null);
			}
		} else {
			_mapper = PdfFontMapper.createPdfFontMapper(this,fontmapcacheFile);
		}
		return _mapper;
	}

	public PdfWriter pdfWriter() {
		return _writer;
	}

	public ColumnText text() {
		return _text;
	}

	public Document document() {
		return _doc;
	}

	public float jpegQuality() {
		return _jpegQuality;
	}

	private void checkWriter() throws ReportException {
		if (_writer == null) {
			throw new ReportException("Begin report first");
		}
	}
	
	private void checkIsNotOpen() throws ReportException {
		checkWriter();
		if (_doc.isOpen()) {
			throw new ReportException("Implementation specific: Add meta information before the first ReportElement is added");
		}
	}
	
	private void checkDocOpen() {
		if (!_doc.isOpen()) { _doc.open(); }
	}
	
	String informStatus(String msg) {
		Progress prg=(Progress) super.getProperty(Report.Property.PROGRESS);
		if (prg!=null) {
			return prg.statusMessage(msg);
		} else {
			return msg;
		}
	}
	
	int informProgress(int p) {
		Progress prg=(Progress) super.getProperty(Report.Property.PROGRESS);
		if (prg!=null) {
			return prg.progress(p);
		} else {
			return p;
		}
	}

	// /////////////////////////////////////////////////////////////////////////////////
	// Interface methods
	// /////////////////////////////////////////////////////////////////////////////////

	public void setMetaAuthor(String author) throws ReportException {
		checkIsNotOpen();
		_doc.addAuthor(author);
	}
	
	public void setMetaTitle(String title) throws ReportException {
		checkIsNotOpen();
		_doc.addTitle(title);
	}
	
	public void	setMetaCreator(String creator) throws ReportException {
		checkIsNotOpen();
		_doc.addCreator(creator);
	}
	
	public void	setMetaCreationDate() throws ReportException {
		checkIsNotOpen();
		_doc.addCreationDate();
	}
	
	public void setMetaDescription(String description) throws ReportException {
		checkIsNotOpen();
		_doc.addSubject(description);
	}

	public void	setMetaKeywords(Vector<String> keys) throws ReportException {
		checkIsNotOpen();
		Iterator<String> it=keys.iterator();
		String comma="";
		StringBuffer b=new StringBuffer();
		while(it.hasNext()) { b.append(comma);b.append(it.next());comma=","; }
		_doc.addKeywords(b.toString());
	}
	
	public Chunk createChunk(String txt) {
		return new PdfChunk(this, txt);
	}

	public Chunk createChunk(String txt, boolean bold, boolean italic,boolean underline) throws ReportException {
		Chunk c = new PdfChunk(this, txt);
		c.setBold(bold);
		c.setItalic(italic);
		c.setUnderline(underline);
		return c;
	}

	public Tab createTab(Paragraph itpar) throws ReportException {
		checkWriter();
		return itpar.getNextTab();
	}

	public Chunk createChunk(Image img) throws ReportException {
		return createChunk(img,100.0f);
	}

	public Chunk createChunk(File imageFile) throws ReportException {
		return createChunk(imageFile,100.0f);
	}

	public Chunk createChunk(Image img,float scalePercentage) throws ReportException {
		checkWriter();
		return new PdfChunk(this, img,scalePercentage,-1);
	}

	public Chunk createChunk(File imageFile,float scalePercentage) throws ReportException {
		checkWriter();
		return new PdfChunk(this, imageFile,scalePercentage,-1);
	}
	
	public Chunk createChunkForWidth(Image img,float scaleToWidthInPt) throws ReportException  {
		checkWriter();
		return new PdfChunk(this,img,-1.0f,scaleToWidthInPt);
	}

	public Chunk createChunkForWidth(File imageFile,float scaleToWidthInPt) throws ReportException  {
		checkWriter();
		return new PdfChunk(this,imageFile,-1.0f,scaleToWidthInPt);
	}
	
	public Paragraph createParagraph(XMLNoteParStyle style)
			throws ReportException {
		checkWriter();
		return new PdfParagraph(this, style);
	}
	
	public Paragraph createParagraph() throws ReportException {
		return createParagraph(null);
	}

	public Table createTable(Report.Align align, float percentageOfWidth,
			float[] relativeWidths) throws ReportException {
		checkWriter();
		return new PdfTable(this, align, percentageOfWidth, relativeWidths);
	}

	public Cell createCell(ReportElement p) throws ReportException {
		checkWriter();
		return new PdfCell(this, p);
	}
	
	public Cell textCell(XMLNoteParStyle st, String txt) throws ReportException {
		return textCell(st,txt,Report.Align.LEFT);
	}

	public Cell textCell(XMLNoteParStyle st, String txt, Align a) throws ReportException {
		checkWriter();
		return createCell(createParagraph(st).setAlignment(a).add(createChunk(txt)));
	}	


	public void beginReport(File output) throws ReportException {
		try {
			_canceled = false;
			_doc = new Document();
			_writer = PdfWriter.getInstance(_doc, new FileOutputStream(output));
			setPageSize(PageSize.A4);
			setOrientation(Orientation.PORTRAIT);
			setMargins(new Rectangle(72.0f, 72.0f, 72.0f, 72.0f));

			_writer.setPageEvent(new PdfPageEvent() {

				public void onChapter(PdfWriter arg0, Document arg1,
						float arg2, com.lowagie.text.Paragraph arg3) {
				}

				public void onChapterEnd(PdfWriter arg0, Document arg1,
						float arg2) {
				}

				public void onCloseDocument(PdfWriter arg0, Document arg1) {
					PdfReport.super.getReportListeners().informEndReport(PdfReport.this); 
				}

				public void onGenericTag(PdfWriter arg0, Document arg1,
						com.lowagie.text.Rectangle arg2, String arg3) {
				}

				public void onOpenDocument(PdfWriter arg0, Document arg1) {
				}

				public void onParagraph(PdfWriter arg0, Document arg1,
						float arg2) {
				}

				public void onParagraphEnd(PdfWriter arg0, Document arg1,
						float arg2) {
				}

				public void onSection(PdfWriter arg0, Document arg1,
						float arg2, int arg3, com.lowagie.text.Paragraph arg4) {
				}

				public void onSectionEnd(PdfWriter arg0, Document arg1,
						float arg2) {
				}

				public void onStartPage(PdfWriter wrt, Document doc) {
				}

				public void onEndPage(PdfWriter wrt, Document doc) {
					try {
						ReportListeners _listeners=PdfReport.super.getReportListeners();
						_listeners.informNextPage(PdfReport.this);

						Vector<ReportElement> vhdr = _listeners.getHeader(PdfReport.this);
						Vector<ReportElement> vftr = _listeners.getFooter(PdfReport.this);

						PdfContentByte cb=wrt.getDirectContent();

						// write headers on top of each other
						if (vhdr!=null) {
							Iterator<ReportElement> it=vhdr.iterator();
							
							while (it.hasNext()) {
								ReportElement hdr=it.next();
								
								Rectangle pageSize=PdfReport.this.getPageRect();
								Rectangle margins=PdfReport.this.getMargins();
								float ytop=pageSize.top()-margins.top();
								float hdrHeight=PdfReport.this.getHeight(hdr, null);
								float rpos=(margins.top()-hdrHeight)/2;
								float hytop=ytop+rpos+hdrHeight;
							
								if (hdr instanceof PdfTable) {
									int firstRow=0,lastRow=-1;
									PdfTable phdr=(PdfTable) hdr;
									phdr.writeSelectedRows(firstRow,lastRow,margins.left(),hytop,cb);
								} else if (hdr instanceof PdfParagraph) {
									PdfParagraph ppar=(PdfParagraph) hdr;
									ColumnText ct = new ColumnText(cb);
									float textWidth=PdfReport.this.getTextWidth();
									ct.addElement(ppar);
									ct.setSimpleColumn(margins.left(), ytop+rpos, margins.left()+textWidth, hytop);
									ct.go();
								}
							}
						}
						
						// write footers on top of each other
						
						if (vftr!=null) {
							Iterator<ReportElement> it=vftr.iterator();
							
							while (it.hasNext()) {
								ReportElement ftr=it.next();
								
								Rectangle margins=PdfReport.this.getMargins();
								float ytop=margins.bottom();
								float hdrHeight=PdfReport.this.getHeight(ftr, null);
								float rpos=(margins.bottom()-hdrHeight)/2;
								float hytop=ytop-rpos;
							
								if (ftr instanceof PdfTable) {
									int firstRow=0,lastRow=-1;
									PdfTable pftr=(PdfTable) ftr;
									pftr.writeSelectedRows(firstRow,lastRow,margins.left(),hytop,cb);
								} else if (ftr instanceof PdfParagraph) {
									PdfParagraph ppar=(PdfParagraph) ftr;
									ColumnText ct = new ColumnText(cb);
									float textWidth=PdfReport.this.getTextWidth();
									ct.addElement(ppar);
									ct.setSimpleColumn(margins.left(), rpos, margins.left()+textWidth, hytop);
									ct.go();
								}
							}
						}
						

					} catch (ReportException e) {
						DefaultXMLNoteErrorHandler.exception(e);
					} catch (DocumentException e) {
						DefaultXMLNoteErrorHandler.exception(e);
					}
					
				}

			});

		} catch (Exception e) {
			throw new ReportException(e);
		}
	}

	public void endReport() throws ReportException {
		try {
			if (keeps!=null && !keeps.isEmpty()) {
				addToDocument(keeps);
			}
			_doc.close();
			_imageInstances.clear();
		} catch (Exception e) {
			throw new ReportException(e);
		}
	}
	
	private void setPageAndOrientation() throws ReportException {
		_pdfPageSize = (_pageSize.equals(PageSize.A4)) ? 
							com.lowagie.text.PageSize.A4
							: com.lowagie.text.PageSize.LETTER;
		if (_orientation.equals(Orientation.PORTRAIT)) { 
			_doc.setPageSize(_pdfPageSize); 
		} else {
			_pdfPageSize=_pdfPageSize.rotate();
			_doc.setPageSize(_pdfPageSize);
		}
	}

	public void setPageSize(PageSize pg) throws ReportException {
		checkWriter();
		_pageSize=pg;
		setPageAndOrientation();
	}
	
	public void setOrientation(Orientation o) throws ReportException {
		checkWriter();
		_orientation=o;
		setPageAndOrientation();
	}

	public void setMargins(Rectangle m) throws ReportException {
		_doc.setMargins(m.left(), m.right(), m.top(), m.bottom());
	}

	public Rectangle getPageRect() throws ReportException {
		checkWriter();
		return new Rectangle(_pdfPageSize.getLeft(),
				_pdfPageSize.getTop(), _pdfPageSize.getRight(),
				_pdfPageSize.getBottom());
	}

	public Rectangle getMargins() throws ReportException {
		checkWriter();
		Rectangle pr=getPageRect();
		return new Rectangle(_doc.left(), pr.top()-_doc.top(), pr.right()-_doc.right(),_doc.bottom());
	}

	public float getTextWidth() throws ReportException {
		Rectangle m = getMargins();
		return getPageRect().width() - m.left() - m.right();
	}

	public int getCurrentPageNumber() throws ReportException {
		checkWriter();
		return _writer.getPageNumber();
	}
	
	private Vector<ReportElement> keeps=null;
	
	public void add(ReportElement el,boolean keepWithNext) throws ReportException {
		checkWriter();
		checkDocOpen();
		if (keeps==null) { keeps=new Vector<ReportElement>(); }
		if (el instanceof PdfPageBreak) {
			addToDocument(keeps);
			_doc.newPage();
		} else if (el instanceof PdfParagraph) {
			PdfParagraph par=((PdfParagraph) el);
			XMLNoteParStyle st=par.getStyle();
			//System.out.println("keepwithnext="+st.keepWithNext()+";"+par.getContent().substring(0,Math.min(15, par.getContent().length())).trim());
			if (keepWithNext || st.keepWithNext()) { 
				keeps.add(par); 
			} else {
				keeps.add(par);
				addToDocument(keeps);
			}
		} else if (el instanceof PdfTable) {
			PdfTable tbl=((PdfTable) el);
			if (keepWithNext) {
				keeps.add(tbl);
			} else {
				keeps.add(tbl);
				addToDocument(keeps);
			}
		} else {
			throw new ReportException(
					"You are trying to mix Report implementations");
		}
	}
	
	public void add(ReportElement el) throws ReportException {
		add(el,false);
	}
	
	public void addPageBreak() throws ReportException {
		add(new PdfPageBreak());
	}
	
	private void addToDocument(Vector<ReportElement> keeps) throws ReportException {
		float totalHeight=0.0f;
		Iterator<ReportElement> it=keeps.iterator();
		ReportElement last=keeps.lastElement();
		while (it.hasNext()) {
			totalHeight+=getHeight(it.next(),last);
		}
		if (!willFit(totalHeight)) {
			_doc.newPage();
		} 
		it=keeps.iterator(); 
		while (it.hasNext()) {
			ReportElement el=it.next();
			try {
				if (el instanceof PdfParagraph) {
					_doc.add((PdfParagraph) el);
				} else if (el instanceof PdfTable) {
					_doc.add((PdfTable) el);
				} else {
					throw new ReportException("Unknown ReportElement:"+el.getClass().getName());
				}
			} catch (Exception e) {
				throw new ReportException(e);
			}
		}
		
		keeps.clear();
	}
	
	private float getHeight(ReportElement el,ReportElement last) throws ReportException {
		try {
			ColumnText ct=new ColumnText(_writer.getDirectContent());
			if (el instanceof PdfParagraph) {
				PdfParagraph par=((PdfParagraph) el);
				if ((el!=last) || (el==last && par.hasImage())) {
					int status = ColumnText.START_COLUMN;
					Rectangle m=getMargins();//System.out.println(m);
					Rectangle p=getPageRect();//System.out.println(p);
					float leading=par.getLeading();
					int align=par.getAlignment();
					ct.setSimpleColumn(m.left(), m.bottom(),p.width()-m.right(),p.height()-m.top(),leading,align); //,Element.ALIGN_JUSTIFIED);
					ct.addElement(par);
					float pos = ct.getYLine();
					status = ct.go(true);
					float npos=ct.getYLine();
					return pos-npos;
				} else {
					float lineheight=par.getFont().getSize()*par.getMultipliedLeading();
					float threelines=3*lineheight;
					//System.out.println("par:"+par+";lineheight:"+lineheight+";threelines:"+threelines);
					return threelines;
				}
			} else if (el instanceof PdfTable) {
				PdfTable tbl=((PdfTable) el);
				int status=ColumnText.START_COLUMN;
				Rectangle m=getMargins();//System.out.println(m);
				Rectangle p=getPageRect();//System.out.println(p);
				float leading=0.0f; //tbl.getLeading();
				int align=tbl.getHorizontalAlignment();
				ct.setSimpleColumn(m.left(), m.bottom(),p.width()-m.right(),p.height()-m.top(),leading,align); //,Element.ALIGN_JUSTIFIED);
				ct.addElement(tbl);
				float pos = ct.getYLine();
				status = ct.go(true);
				float npos=ct.getYLine();
				return pos-npos;
			} else {
				throw new ReportException("Unknown ReportElement:"+el.getClass().getName());
			}
		}
		catch (Exception e) {
			throw new ReportException(e);
		}
	}
	
	public boolean willFit(float height) throws ReportException {
		float ypos=_writer.getVerticalPosition(false);
		float bottomMargin=this.getMargins().bottom();
		if ((ypos-bottomMargin)<height) { 
			return false; 
		} else {
			return true;
		} 
	}

	public void addXMLNote(XMLNoteDocument doc, MarkMarkupProviderMaker maker,XMLNoteToReport.MarkTextProvider prov)
			throws ReportException {
		checkWriter();
		XMLNoteToReport cvt = new XMLNoteToReport(this);
		cvt.addXMLNote(doc, maker, prov);
	}

	public void cancel() throws ReportException {
		checkWriter();
		_canceled = true;
	}

	public boolean canceled() throws ReportException {
		checkWriter();
		return _canceled;
	}

	
	// /////////////////////////////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////////////////////////////

	public PdfReport(File fontmapcache) {
		fontmapcacheFile=fontmapcache;
		_fontmapcachein=null;
		_fontmapcacheout=null;
		_imageInstances=new Hashtable<String,com.lowagie.text.Image>();
	}
	
	public PdfReport(InputStream fontmapCacheIn,OutputStream fontmapCacheOut) {
		fontmapcacheFile=null;
		_fontmapcachein=fontmapCacheIn;
		_fontmapcacheout=fontmapCacheOut;
		_imageInstances=new Hashtable<String,com.lowagie.text.Image>();
	}
	
}
