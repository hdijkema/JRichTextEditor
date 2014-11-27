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

package net.dijkema.jxmlnote.report.html;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.Stack;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.dijkema.jxmlnote.document.XMLNoteDocument;
import net.dijkema.jxmlnote.interfaces.MarkMarkupProviderMaker;
import net.dijkema.jxmlnote.report.Report;
import net.dijkema.jxmlnote.report.ReportException;
import net.dijkema.jxmlnote.report.XMLNoteToReport.MarkTextProvider;
import net.dijkema.jxmlnote.report.elements.Cell;
import net.dijkema.jxmlnote.report.elements.Chunk;
import net.dijkema.jxmlnote.report.elements.Paragraph;
import net.dijkema.jxmlnote.report.elements.Rectangle;
import net.dijkema.jxmlnote.report.elements.ReportElement;
import net.dijkema.jxmlnote.report.elements.Tab;
import net.dijkema.jxmlnote.report.elements.Table;
import net.dijkema.jxmlnote.resources.XMLNoteResource;
import net.dijkema.jxmlnote.styles.XMLNoteParStyle;
import net.dijkema.jxmlnote.styles.XMLNoteStyleIdConverter;
import net.dijkema.jxmlnote.styles.XMLNoteStyles;


import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class HtmlReport extends Report {
	
	private org.w3c.dom.Document _html;
	private org.w3c.dom.Element  _metaElement;
	private org.w3c.dom.Element  _bodyElement;
	private XMLNoteStyles		 _styles;
	private Stack<Element>		 _elemStack;
	private int					 _maximumWidthInPt;					
	
	///////////////////////////////////////////////////////////////////////
	// Support methods
	///////////////////////////////////////////////////////////////////////
	
	public org.w3c.dom.Document html() {
		return _html;
	}
	
	public org.w3c.dom.Element meta() {
		return _metaElement;
	}
	
	public org.w3c.dom.Element body() {
		return _bodyElement;
	}
	
	public int textWidth() {
		return _maximumWidthInPt;
	}
	
	public String htmlColor(Color c) {
		return String.format("#%02x%02x%02x", c.getRed(),c.getGreen(),c.getBlue());
	}

	///////////////////////////////////////////////////////////////////////
	// Generation
	///////////////////////////////////////////////////////////////////////
	
	
	public void generateStyles() {
		// style sheet
		Element style=_html.createElement("style");
		style.setAttribute("type","text/css");
		meta().appendChild(style);
		style.setTextContent(_styles.asCSS(new XMLNoteStyleIdConverter() {
			public String convert(String id) {
				if (id.equals("h1") || id.equals("h2") || id.equals("h3") || id.equals("h4")) {
					return id;
				} else {
					return "p."+id;
				}
			}
		}));
	}
	
	public void generate(ReportElement el) {
		
	}
	
	///////////////////////////////////////////////////////////////////////
	// Interface methods
	///////////////////////////////////////////////////////////////////////
	

	public Chunk createChunk(String txt) throws ReportException {
		return new HtmlChunk(this,txt,false,false,false);
	}

	public Chunk createChunk(String txt, boolean bold, boolean italic,boolean underline) throws ReportException {
		return new HtmlChunk(this,txt,false,false,false);	
	}

	public Chunk createChunk(Image img) throws ReportException {
		// TODO Auto-generated method stub
		return null;
	}

	public Chunk createChunk(File imageFile) throws ReportException {
		// TODO Auto-generated method stub
		return null;
	}

	public Chunk createChunk(Image img, float scalePercentage)
			throws ReportException {
		// TODO Auto-generated method stub
		return null;
	}

	public Chunk createChunk(File imageFile, float scalePercentage)
			throws ReportException {
		// TODO Auto-generated method stub
		return null;
	}

	public Chunk createChunkForWidth(Image img, float scaleToWidthInPt)
			throws ReportException {
		// TODO Auto-generated method stub
		return null;
	}

	public Chunk createChunkForWidth(File imageFile, float scaleToWidthInPt)
			throws ReportException {
		// TODO Auto-generated method stub
		return null;
	}

	public Tab createTab(Paragraph p) throws ReportException {
		// TODO Auto-generated method stub
		return null;
	}

	public Paragraph createParagraph(XMLNoteParStyle style)
			throws ReportException {
		// TODO Auto-generated method stub
		return null;
	}

	public Paragraph createParagraph() throws ReportException {
		// TODO Auto-generated method stub
		return null;
	}

	public Table createTable(Align align, float percentageOfWidth,
			float[] relativeWidths) throws ReportException {
		// TODO Auto-generated method stub
		return null;
	}

	public Cell createCell(ReportElement p) throws ReportException {
		// TODO Auto-generated method stub
		return null;
	}

	public Cell textCell(XMLNoteParStyle st, String txt) throws ReportException {
		return null;
	}

	public Cell textCell(XMLNoteParStyle st, String txt, Align a)
			throws ReportException {
		return null;
	}	

	public void add(ReportElement el) throws ReportException {
		// TODO Auto-generated method stub
		
	}

	public void add(ReportElement el, boolean keepWithNext)
			throws ReportException {
		// TODO Auto-generated method stub
		
	}

	public void addPageBreak() throws ReportException {
		// TODO Auto-generated method stub
		
	}

	public void setPageSize(PageSize pg) throws ReportException {
		// TODO Auto-generated method stub
	}
	
	public void setOrientation(Orientation o) throws ReportException {
		// not implemented
	}

	public void setMargins(Rectangle m) throws ReportException {
		// TODO Auto-generated method stub
		
	}

	public Rectangle getPageRect() throws ReportException {
		// TODO Auto-generated method stub
		return null;
	}

	public Rectangle getMargins() throws ReportException {
		// TODO Auto-generated method stub
		return null;
	}

	public float getTextWidth() throws ReportException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getCurrentPageNumber() throws ReportException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void beginReport(File output) throws ReportException {
		// TODO Auto-generated method stub
		
	}

	public void endReport() throws ReportException {
		generateStyles();
		// Create HTML File.
	}

	public void cancel() throws ReportException {
		// TODO Auto-generated method stub
		
	}

	public boolean canceled() throws ReportException {
		// TODO Auto-generated method stub
		return false;
	}

	public void addXMLNote(XMLNoteDocument doc, MarkMarkupProviderMaker maker,
			MarkTextProvider prov) throws ReportException {
		// TODO Auto-generated method stub
		
	}

	public void setMetaAuthor(String author) throws ReportException {
		// TODO Auto-generated method stub
		
	}

	public void setMetaCreator(String creator) throws ReportException {
		// TODO Auto-generated method stub
		
	}

	public void setMetaCreationDate() throws ReportException {
		// TODO Auto-generated method stub
		
	}

	public void setMetaDescription(String description) throws ReportException {
		// TODO Auto-generated method stub
		
	}

	public void setMetaTitle(String title) throws ReportException {
		// TODO Auto-generated method stub
		
	}

	public void setMetaKeywords(Vector<String> keys) throws ReportException {
		// TODO Auto-generated method stub
		
	}

	///////////////////////////////////////////////////////////////////////
	// Constructor
	///////////////////////////////////////////////////////////////////////
	
	
	public HtmlReport(int maxWidthInPt,File outputFile,File imageDir,XMLNoteStyles styles) throws ParserConfigurationException  {
		
		_styles=styles;
		_maximumWidthInPt=maxWidthInPt;
		
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		DocumentBuilder db=dbf.newDocumentBuilder();
		db.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(java.lang.String publicId, java.lang.String systemId) 
			throws SAXException, java.io.IOException {
				return new InputSource(XMLNoteResource.get("xhtml4-strict.dtd"));
			}
		});

		DOMImplementation impl=db.getDOMImplementation();
		DocumentType xmlDocType = impl.createDocumentType("html", "-//W3C//DTD HTML 4.01//EN", "http://www.w3.org/TR/html4/strict.dtd");
		_html=impl.createDocument(null,"html", xmlDocType);

		org.w3c.dom.Element root=_html.getDocumentElement();

		org.w3c.dom.Element head=_html.createElement("head");
		root.appendChild(head);
		_metaElement=head;

		// Character set UTF-8
		Element charset=_html.createElement("meta");
		charset.setAttribute("http-equiv", "content-type");
		charset.setAttribute("content", "text/html; charset=UTF-8");
		head.appendChild(charset);

		// body
		org.w3c.dom.Element body=_html.createElement("body");
		body.setAttribute("style","width:"+maxWidthInPt+"pt;");
		root.appendChild(body);
		_bodyElement=body;

		_elemStack=new Stack<org.w3c.dom.Element>();
		_elemStack.push(body);
	}

}
