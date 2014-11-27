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

package net.dijkema.jxmlnote.html;

import java.awt.Color;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.dijkema.jxmlnote.document.XMLNoteDocument;
import net.dijkema.jxmlnote.document.XMLNoteMark;
import net.dijkema.jxmlnote.exceptions.BadDocumentException;
import net.dijkema.jxmlnote.exceptions.BadMetaException;
import net.dijkema.jxmlnote.exceptions.BadStyleException;
import net.dijkema.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.dijkema.jxmlnote.interfaces.MarkMarkupProvider;
import net.dijkema.jxmlnote.interfaces.MarkMarkupProviderMaker;
import net.dijkema.jxmlnote.resources.XMLNoteResource;
import net.dijkema.jxmlnote.styles.XMLNoteParStyle;
import net.dijkema.jxmlnote.styles.XMLNoteStyleIdConverter;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class can be used to transform XMLNote XML to Html.
 *   
 * @author Hans Dijkema
 */
public class XMLNoteToHtml {
	
	XMLNoteDocument _doc;
	String			_xml;
	Writer			_html;
	String			_css;
	
	/**
	 * This method converts an <code>XMLNoteDocument</code> to <code>HTML</code>, using the given <code>MarkMarkupProvider</code>,
	 * which may be <b>null</b>. If the <code>MarkMarkupProvider</code> is <b>null</b>, no marks will be exported.
	 * <p>  
	 * @param doc					The document to convert (note: <code>doc.getPart()</code>, will get a selection).
	 * @param maker					The optional MarkMarkupProviderMaker.
	 * @return						Returns the export <code>HTML</code>
	 * @throws BadDocumentException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static String toString(XMLNoteDocument doc,MarkMarkupProviderMaker maker) throws BadDocumentException, IOException, SAXException, ParserConfigurationException {
		StringWriter html=new StringWriter();
		convert(doc,maker,html);
		String h=html.toString();
		return h;
	}
	
	/**
	 * This method converts an <code>XMLNoteDocument</code> to <code>XHTML</code> and writes the output to the given <code>Writer</code>.
	 * The given <code>MarkMarkupProvider</code> is used, which may be <b>null</b>. If the <code>MarkMarkupProvider</code> 
	 * is <b>null</b>, no marks will be exported.
	 * <p>
	 * @param doc				The document to convert (note: <code>doc.getPart()</code>, will get a selection).
	 * @param maker				The optional MarkMarkupProviderMaker.
	 * @param html				The writer to write the XHTML to.
	 * @throws BadDocumentException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static void convert(XMLNoteDocument doc,MarkMarkupProviderMaker maker,Writer html) throws BadDocumentException, IOException, SAXException, ParserConfigurationException {
		String xml=doc.toXML();
		String css=doc.getStyles().asCSS(new XMLNoteStyleIdConverter() {
			public String convert(String id) {
				if (id.equals("h1") || id.equals("h2") || id.equals("h3") || id.equals("h4")) {
					return id;
				} else {
					return "p."+id;
				}
			}
		});
		XMLNoteToHtml obj=new XMLNoteToHtml(doc,xml,html,css);
		obj.toHtml(new StringReader(xml),maker);
	}
	
	private void toHtml(Reader rd,MarkMarkupProviderMaker maker) throws IOException, SAXException, ParserConfigurationException {
		SAXParserFactory factory=SAXParserFactory.newInstance();
		SAXParser parser=factory.newSAXParser();
		XMLReader reader=parser.getXMLReader();
		XMLNoteContentHandler handler=new XMLNoteContentHandler(this,maker);
		reader.setContentHandler(handler);
		InputSource source=new InputSource(rd);
		reader.parse(source);
		handler.toHtml();
	}
	
	protected XMLNoteToHtml(XMLNoteDocument doc,String xml,Writer html,String css) {
		_doc=doc;
		_xml=xml;
		_html=html;
		_css=css;
	}
}

class XMLNoteContentHandler extends DefaultHandler  {

	class Mark extends XMLNoteMark {
		public String content() {
			return null;
		}

		public Integer offset() {
			return -1;
		}

		public Integer endOffset() {
			return -1;
		}
		
		public Mark(String id,String c) {
			super(id,c);
		}
	}
	
	XMLNoteToHtml 				_cvt;
	org.w3c.dom.Document   		_html;
	boolean						_includeMarks;
	MarkMarkupProviderMaker		_maker;
	Stack<org.w3c.dom.Element>	_elemStack;
	Stack<Mark>					_markStack;
	StringBuffer				_buffer;
	XMLNoteParStyle				_currentStyle;
	
	public void toHtml() {
		OutputFormat format=new OutputFormat(_html);
		format.setOmitDocumentType(false);
		format.setOmitXMLDeclaration(true);
		XMLSerializer serial=new XMLSerializer(_cvt._html,format);
		try {
			serial.serialize(_html);
		} catch (IOException e) {
			DefaultXMLNoteErrorHandler.exception(e);
		}
	}
	
	public XMLNoteContentHandler(XMLNoteToHtml cvt,MarkMarkupProviderMaker maker) throws ParserConfigurationException {
		
		_cvt=cvt;
		_currentStyle=null;
		_maker=maker;
		_includeMarks=(_maker!=null);
		
		_markStack=new Stack<Mark>();
		
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
		
		XMLNoteDocument doc=_cvt._doc;
		
		org.w3c.dom.Element root=_html.getDocumentElement();

		org.w3c.dom.Element head=_html.createElement("head");
		root.appendChild(head);
		
		// Character set UTF-8
		Element charset=_html.createElement("meta");
		charset.setAttribute("http-equiv", "content-type");
		charset.setAttribute("content", "text/html; charset=UTF-8");
		head.appendChild(charset);
		
		// meta information
		try {
			Iterator<String> it=doc.metaKeys();
			while (it.hasNext()) {
				String key=it.next();
				String value=doc.metaValue(key);
				String type=doc.metaType(key);
				org.w3c.dom.Element meta=_html.createElement("meta");
				meta.setAttribute("name", key);
				meta.setAttribute("content", type+";"+value);
				head.appendChild(meta);
			}
		} catch (BadMetaException E) { // Unexpected.
			DefaultXMLNoteErrorHandler.exception(E);
		}
		
		// style sheet
		Element style=_html.createElement("style");
		style.setAttribute("type","text/css");
		head.appendChild(style);
		style.setTextContent(_cvt._css);
		
		// marks
		Vector<XMLNoteMark> marks=doc.getOrderedMarks();
		Iterator<XMLNoteMark> it=marks.iterator();
		while (it.hasNext()) {
			Element meta=_html.createElement("meta");
			XMLNoteMark m=it.next();
			meta.setAttribute("name", "mark:"+m.id());
			meta.setAttribute("content", "offset="+m.offset()+";endOffset="+m.endOffset()+";class="+((m.markClass()==null) ? "" : m.markClass()));
			head.appendChild(meta);
		}
		
		// body
		org.w3c.dom.Element body=_html.createElement("body");
		root.appendChild(body);
		
		_elemStack=new Stack<org.w3c.dom.Element>();
		_elemStack.push(body);
	}
	
	private void createPar(String tag,String _class,Attributes attrs,XMLNoteParStyle style) {
		createNode(tag,_class,attrs);
		_currentStyle=style;
	}
	
	private void createNode(String tag,Attributes attrs) {
		createNode(tag,null,attrs);
	}
	
	private void createNode(String tag,String _class,Attributes attrs) {
		textOut();
		org.w3c.dom.Element el=_html.createElement(tag);
		if (_class!=null) {
			el.setAttribute("class", _class);
		}
		String align=attrs.getValue("align");
		String indent=attrs.getValue("indent");
		String style="";
		if (align!=null) { style+="text-align:"+align+";"; }
		if (indent!=null) { style+="text-indent:"+indent+"pt;"; }
		if (!style.equals("")) {
			el.setAttribute("style", style);
		}
		_elemStack.peek().appendChild(el);
		_elemStack.push(el);
		_buffer=new StringBuffer();
	}
	
	private void addText(String s) {
		_buffer.append(s);
	}
	
	private String htmlColor(Color c) {
		return String.format("#%02x%02x%02x",c.getRed(),c.getGreen(),c.getBlue());
	}
	
	private void textOut() {
		if (_buffer!=null) {
			if (_buffer.length()>0) {
				String s=_buffer.toString();
				Text txtnode=_html.createTextNode(s);
				if (_markStack.isEmpty()) {
					_elemStack.peek().appendChild(txtnode);
				} else { // this implies _includeMarks
					Element em=_html.createElement("span");
					Mark mm=_markStack.peek();
					MarkMarkupProvider _provider=_maker.create(mm.id(),mm.markClass());
					Color bg=_provider.markColor(mm);
					MarkMarkupProvider.MarkupType type=_provider.type(mm);
					if (type==MarkMarkupProvider.MarkupType.MARKER) { 
						em.setAttribute("style", "background-color:"+htmlColor(bg)+";");
					} else {
						em.setAttribute("style", "border-bottom: 2px solid "+htmlColor(bg)+";");
					}
					if (mm.markClass()==null) {
						em.setAttribute("id", "id="+mm.id());
					} else {
						em.setAttribute("id", "id="+mm.id()+";class="+mm.markClass());
					}
					em.setAttribute("class","mark");
					_elemStack.peek().appendChild(em);
					em.appendChild(txtnode);
				}
				_buffer=new StringBuffer();
			}
		}
	}
	
	private XMLNoteParStyle getStyle(String styleId) {
		XMLNoteParStyle st=_cvt._doc.getStyles().parStyle(styleId);
		if (st==null) {
			try {
				return _cvt._doc.getStyles().getDefaultStyle();
			} catch (BadStyleException e) {
				DefaultXMLNoteErrorHandler.exception(e);
				return null;
			}
		} else {
			return st;
		}
	}
	
	private void endPar() {
		textOut();
		
		// process nodes of curtyperent paragraph for tabs.
		Element par=_elemStack.pop();
		
		// postprocess stuff
		NodeList nodes=par.getElementsByTagName("tab");
		if (nodes.getLength()>0) {

			if (!_elemStack.peek().getTagName().equals("table")) {
				_elemStack.peek().removeChild(par);
				Element table=_html.createElement("table");
				_elemStack.peek().appendChild(table);
				_elemStack.push(table);
			} else {
				_elemStack.peek().removeChild(par);
			}
			
			Element table=_elemStack.peek();
			
			NodeList parNodes=par.getChildNodes();
			int i,n;
			Vector<Node> v=new Vector<Node>();
			for(i=0,n=parNodes.getLength();i<n;i++) {
				v.add(parNodes.item(i));
			}
			Iterator<Node> it=v.iterator();
			while (it.hasNext()) {
				Node nd=it.next();
				par.removeChild(nd);
			}

			Element row=_html.createElement("tr");
			table.appendChild(row);
			
			Element td=_html.createElement("td");
			Element pp=(Element) par.cloneNode(false);
			
			int tabCount=0;
			TabSet tabs=null;
			if (_currentStyle!=null) {
				tabs=_currentStyle.tabs();
			}

			TabStop stop=null;
			if (tabs!=null) {
				if (tabCount<tabs.getTabCount()) { 
					stop=tabs.getTab(tabCount);
				}
			}
			
			row.appendChild(td);
			if (stop!=null) {
				int a=stop.getAlignment();
				String aa="left";
				if (a==TabStop.ALIGN_RIGHT) { aa="right"; }
				else if (a==TabStop.ALIGN_CENTER) { aa="center"; }
				else if (a==TabStop.ALIGN_DECIMAL) { aa="right"; }
				td.setAttribute("align",aa);
			}
			td.appendChild(pp);
			it=v.iterator();
			while (it.hasNext()) {
				Node ee=it.next();
				if (ee instanceof Element) {
					if (((Element) ee).getTagName().equals("tab")) {
						tabCount+=1;
						td=_html.createElement("td");
						pp=(Element) par.cloneNode(false);
						row.appendChild(td);
						td.appendChild(pp);
						stop=null;
						if (tabs!=null) {
							if (tabCount<tabs.getTabCount()) { 
								stop=tabs.getTab(tabCount);
							}
						}
						if (stop!=null) {
							int a=stop.getAlignment();
							String aa="left";
							if (a==TabStop.ALIGN_RIGHT) { aa="right"; }
							else if (a==TabStop.ALIGN_CENTER) { aa="center"; }
							else if (a==TabStop.ALIGN_DECIMAL) { aa="right"; }
							td.setAttribute("align",aa);
						}
					} else {
						pp.appendChild(ee);
					}
				} else {
					pp.appendChild(ee);
				}
			}
		} else {  // end of tabs 
			if (_elemStack.peek().getTagName().equals("table")) {
				Element table=_elemStack.pop();
				table.removeChild(par);
				_elemStack.peek().appendChild(par);
			}
		}
		
		_currentStyle=null;
	}
	
	private void endNode() {
		textOut();
		_elemStack.pop();
	}
	
	private void createTabNode(String tag,Attributes attrs) {
		textOut();
		Stack<Element> st=new Stack<Element>();
		String tg=_elemStack.peek().getTagName();
		while (tg.equals("b") || tg.equals("i") || tg.equals("u")) {
			st.push(_elemStack.pop());
			tg=_elemStack.peek().getTagName();
		}
		_elemStack.peek().appendChild(_html.createElement("tab"));
		while (!st.isEmpty()) {
			Element n=(Element) st.pop().cloneNode(false);
			_elemStack.peek().appendChild(n);
			_elemStack.push(n);
		}
	}
	
	private void createImageNode(String tag,Attributes attrs) {
		textOut();
		Stack<Element> st=new Stack<Element>();
		String tg=_elemStack.peek().getTagName();
		while (tg.equals("b") || tg.equals("i") || tg.equals("u")) {
			st.push(_elemStack.pop());
			tg=_elemStack.peek().getTagName();
		}
		
		Element img=_html.createElement("img");
		String description=attrs.getValue("description");
		if (description!=null) { img.setAttribute("alt",description); }
		String style="";
		String width_in_pt=attrs.getValue("width_in_pt");
		if (width_in_pt!=null) { 
			double w=Integer.parseInt(width_in_pt)*2.54/72.0;
			style+="width:"+w+"cm;"; 
		}
		String height_in_pt=attrs.getValue("height_in_pt");
		if (height_in_pt!=null) {
			double h=Integer.parseInt(height_in_pt)*2.54/72.0;
			style+="height:"+h+"cm;"; 
		}
		String url=attrs.getValue("url");
		if (url!=null) { img.setAttribute("src",url); }
		if (!style.equals("")) { img.setAttribute("style", style); }
		
		_elemStack.peek().appendChild(img);
		while (!st.isEmpty()) {
			Element n=(Element) st.pop().cloneNode(false);
			_elemStack.peek().appendChild(n);
			_elemStack.push(n);
		}
	}
	
	private void processMark(Attributes attrs) {
		if (_includeMarks) {
			textOut();
			String id=attrs.getValue("id");
			String type=attrs.getValue("type");
			String _class=attrs.getValue("class");
			if (type.equals("start")) {
				Mark m=new Mark(id,_class);
				_markStack.push(m);
			} else {
				_markStack.pop();
			}
		}
	}
	
	public void startElement(String uri, String localName,String qName,Attributes attrs) throws SAXException {
		if (qName.equals("xmlnote") || qName.equals("meta") || qName.equals("param") || qName.equals("notes")) {
			// do nothing
		} else if (qName.equals("par")) { createPar("p","par",attrs,getStyle("par")); }
		else if (qName.equals("h1")) { createPar("h1",null,attrs,getStyle("h1")); }
		else if (qName.equals("h2")) { createPar("h2",null,attrs,getStyle("h2")); }
		else if (qName.equals("h3")) { createPar("h3",null,attrs,getStyle("h3")); }
		else if (qName.equals("h4")) { createPar("h4",null,attrs,getStyle("h4")); }
		else if (qName.equals("sp")) { createPar("p",attrs.getValue("style"),attrs,getStyle(attrs.getValue("style"))); }
		else if (qName.equals("b")) { createNode("b",attrs); }
		else if (qName.equals("i")) { createNode("i",attrs); }
		else if (qName.equals("u")) { createNode("u",attrs); }
		else if (qName.equals("image")) { createImageNode("img",attrs); }
		else if (qName.equals("space")) { addText(" "); }
		else if (qName.equals("tab")) { createTabNode("tab",attrs); }
		else if (qName.equals("enter")) { addText("\n"); }
		else if (qName.equals("mark")) { 
			processMark(attrs);
		}
		else {
			throw new SAXException("Unknown tag: "+qName);
		}
	}
	
	public void endElement(String uri,String localName,String qName) throws SAXException {
		if (qName.equals("notes")) {
			_elemStack.pop(); // pop the body.
		} else if (qName.equals("par")) { endPar(); }
		else if (qName.equals("h1")) { endPar(); }
		else if (qName.equals("h2")) { endPar(); }
		else if (qName.equals("h3")) { endPar(); }
		else if (qName.equals("h4")) { endPar(); }
		else if (qName.equals("sp")) { endPar(); }
		else if (qName.equals("b")) { endNode(); }
		else if (qName.equals("i")) { endNode(); }
		else if (qName.equals("u")) { endNode(); }
		else if (qName.equals("image")) { 
			// do nothing
		} else if (qName.equals("space")) { 
			// do nothing 
		} else if (qName.equals("tab")) { 
			// do nothing
		} else if (qName.equals("enter")) { 
			// do nothing
		} else if (qName.equals("mark")) {
			// do nothing
		} 
	}

	public void characters(char [] ch,int start,int length) throws SAXException {
		_buffer.append(new String(ch,start,length));
	}

}
