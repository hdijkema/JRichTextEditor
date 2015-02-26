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

package nl.dykema.jxmlnote.html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import nl.dykema.jxmlnote.document.XMLNoteDocument;
import nl.dykema.jxmlnote.document.XMLNoteImageIcon;
import nl.dykema.jxmlnote.exceptions.BadDocumentException;
import nl.dykema.jxmlnote.exceptions.BadStyleException;
import nl.dykema.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import nl.dykema.jxmlnote.styles.XMLNoteStyles;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
 

/**
 * This class converts a true XHtml source to XMLNote. XHTML is a form of XML, and the precondition
 * for the use of this class, is that the XHtml source is well formed. This class has a couple of
 * standard conversions:
 * 
 * Paragraph stuff:
 * 
 *  <pre>&lt;h1&gt;...&lt;/h1&gt; --&gt; &lt;h1&gt;...&lt;/h1&gt;</pre>
 *  <pre>&lt;h2&gt;...&lt;/h2&gt; --&gt; &lt;h2&gt;...&lt;/h2&gt;</pre>
 *  <pre>&lt;h3&gt;...&lt;/h3&gt; --&gt; &lt;h3&gt;...&lt;/h3&gt;</pre>
 *  <pre>&lt;p&gt;...&lt;/p&gt; --&gt; &lt;par&gt;...&lt;/par&gt;</pre>
 *  <pre>&lt;tr&gt;...&lt;/tr&gt; --&gt; &lt;/tr&gt; --&gt; &lt;enter /&gt;</pre>
 *  <pre>&lt;td&gt;...&lt;/td&gt; --&gt; &lt;/td&gt; --&gt; &lt;tab /&gt;</pre>
 *  
 * Text stuff:
 * 
 *  <pre>&lt;b&gt;...&lt;/b&gt; --&gt; &lt;b&gt;...&lt;/b&gt;</pre>
 *  <pre>&lt;i&gt;...&lt;/i&gt; --&gt; &lt;i&gt;...&lt;/i&gt;</pre>
 *  <pre>&lt;u&gt;...&lt;/u&gt; --&gt; &lt;u&gt;...&lt;/u&gt;</pre>
 *  
 *  All other stuff will be translated to <code>&lt;par&gt;</code> paragraphs.
 *  
 *  This class only exports public static <code>convert()</code> methods. It uses JTidy
 *  to tidy up the HTML and convert it to XHtml. 
 *  
 * @author Hans Dijkema
 */
public class XHtmlToXMLNote {
	
	XMLNoteStyles				_styles;
	XMLNoteImageIcon.Provider 	_prov;
	String						_xmlnote;
	
	protected XMLNoteDocument getDocument() throws BadStyleException, BadDocumentException {
		return new XMLNoteDocument(_xmlnote,_prov,_styles);
	}
	
	protected String getXML() {
		return _xmlnote;
	}
	
	/**
	 * Convert from XHtml, reading from an URL to an XMLNoteDocument.
	 * 
	 * @param url
	 * @param styles
	 * @return
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws BadStyleException
	 * @throws BadDocumentException
	 */
	public static XMLNoteDocument convert(URL url,XMLNoteImageIcon.Provider prov,XMLNoteStyles styles) throws IOException, ParserConfigurationException, SAXException, BadStyleException, BadDocumentException {
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		XHtmlToXMLNote cvt=new XHtmlToXMLNote(prov,styles);
		cvt.parse(in);
		return cvt.getDocument();
	}

	/**
	 * Convert from XHtml, reading from a String to XMLNoteDocument.
	 * 
	 * @param xhtml
	 * @param styles
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws BadStyleException
	 * @throws BadDocumentException
	 */
	public static XMLNoteDocument convert(String xhtml,XMLNoteImageIcon.Provider prov,XMLNoteStyles styles) throws ParserConfigurationException, SAXException, IOException, BadStyleException, BadDocumentException {
		XHtmlToXMLNote cvt=new XHtmlToXMLNote(prov,styles);
		cvt.parse(new StringReader(xhtml));
		//System.out.println(XMLNoteUtils.prettyPrintXML(cvt.getXML()));
		return cvt.getDocument();
	}
	
	/**
	 * Convert from XHtml, reading from a Reader to XMLNoteDocument
	 * 
	 * @param in
	 * @param styles
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws BadStyleException
	 * @throws BadDocumentException
	 */
	public static XMLNoteDocument convert(Reader in,XMLNoteImageIcon.Provider prov,XMLNoteStyles styles) throws ParserConfigurationException, SAXException, IOException, BadStyleException, BadDocumentException {
		XHtmlToXMLNote cvt=new XHtmlToXMLNote(prov,styles);
		cvt.parse(in);
		return cvt.getDocument();
	}
	
	protected void parse(Reader rd) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory=SAXParserFactory.newInstance();
		SAXParser parser=factory.newSAXParser();
		XMLReader reader=parser.getXMLReader();
		HTMLContentHandler handler=new HTMLContentHandler(this);
		reader.setContentHandler(handler);
		InputSource source=new InputSource(rd);
		reader.parse(source);
		_xmlnote=handler.getXML();
	}
	
	protected XHtmlToXMLNote(XMLNoteImageIcon.Provider prov,XMLNoteStyles styles) {
		_styles=styles;
		_prov=prov;
		_xmlnote=null;
	}

}


class HTMLContentHandler extends DefaultHandler {
	
	private enum ListType { NONE, NUMBER, BULLET };
	
	private XHtmlToXMLNote 				_converter;
	private org.w3c.dom.Document   		_xmlnote;
	private Stack<ListType>     		_list;
	private Stack<Integer>				_listCount;
	private boolean						_ignoreContents;     
	private Stack<String>				_parTags;
	private int							_indent;
	private Stack<org.w3c.dom.Element>	_elemStack;
	
	private static String[] ignoreTags={
		"script",
	};
	
	private boolean ignoreContents() {
		return _ignoreContents;
	}
	
	private boolean ignoreContents(boolean n) {
		_ignoreContents=n;
		return _ignoreContents;
	}
	
	private boolean inIgnores(String s) {
		int i;
		for(i=0;i<ignoreTags.length && !ignoreTags[i].equals(s);i++);
		return (i<ignoreTags.length);
	}
	
	private boolean inParagraph() {
		return !_parTags.isEmpty();
	}
	
	private boolean inParagraph(String parTag) {
		_parTags.push(parTag);
		return inParagraph();
	}
	
	private boolean outParagraph() {
		_parTags.pop();
		return inParagraph();
	}
	
	public String getXML()  {
		Writer out=new StringWriter();
		OutputFormat format=new OutputFormat(_xmlnote);
		XMLSerializer serial=new XMLSerializer(out,format);
		try {
			serial.serialize(_xmlnote);
		} catch (IOException e) {
			DefaultXMLNoteErrorHandler.exception(e);
		}
		return out.toString();
	}
	
	private void startPar(String tag) {
		_elemStack.push(_xmlnote.createElement(tag));
	}
	
	private void startPar(String tag,String style) {
		startPar(tag);
		org.w3c.dom.Element par=(org.w3c.dom.Element) _elemStack.peek();
		par.setAttribute("style", style);
	}
	
	private void startPar(String tag,String style,Integer indent) {
		startPar(tag);
		org.w3c.dom.Element par=(org.w3c.dom.Element) _elemStack.peek();
		par.setAttribute("style", style);
		par.setAttribute("indent", indent.toString());
	}
	
	private void startElem(String tag) {
		_elemStack.push(_xmlnote.createElement(tag));
	}

	private void endPar(String e) {
		outParagraph();
		if (!inParagraph()) {
			org.w3c.dom.Element el=_elemStack.pop(); 
			org.w3c.dom.Node n=el.getLastChild();
			if (n==null) {
				el.appendChild(_xmlnote.createElement("enter"));
			} else {
				if (n instanceof org.w3c.dom.Element) {
					if (!((org.w3c.dom.Element) n).getTagName().equals("enter")) {
						el.appendChild(_xmlnote.createElement("enter"));
					}
				} else {
					el.appendChild(_xmlnote.createElement("enter"));
				}
			}
			_elemStack.peek().appendChild(el);
		}
	}
	
	private void endElem(String e) {
		org.w3c.dom.Element el=_elemStack.pop();
		_elemStack.peek().appendChild(el);
	}
	
	private void addText(String txt) {
		org.w3c.dom.Element el=_elemStack.peek();
		txt=txt.replaceAll("\\s+", " ");
		int i,k,n;
		for(i=0,k=0,n=txt.length();i<n;i++) {
			char c=txt.charAt(i);
			switch (c) {
				case '\t': 	if (k!=i) { el.appendChild(_xmlnote.createTextNode(txt.substring(k,i))); }
							el.appendChild(_xmlnote.createElement("tab"));
							k=i+1;
							break;
				case ' ':   if (k!=i) { el.appendChild(_xmlnote.createTextNode(txt.substring(k,i))); }
							el.appendChild(_xmlnote.createElement("space"));
							k=i+1;
							break;
				case '\n':  if (k!=i) { el.appendChild(_xmlnote.createTextNode(txt.substring(k,i))); }
							el.appendChild(_xmlnote.createElement("enter"));
							k=i+1;
							break;
			}
		}
		if (k!=i) {
			el.appendChild(_xmlnote.createTextNode(txt.substring(k,i)));
		}
	}

	private boolean inPx(String val) {
		String v=val.trim().replaceFirst("[0-9.]+", "").trim();
		if (v.equals("") || v.equals("px")) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean inPerc(String val) {
		String v=val.trim().replaceFirst("[0-9.]+", "").trim();
		return v.equals("%");
	}
	
	private Integer toPt(int a,String val) {
		String v=val.trim().replaceFirst("[0-9.]+", "").trim();
		if (v.equals("pt")) { return a; }
		else if (v.equals("cm")) { return (int) Math.round(a*(72.0/2.54)); }
		else if (v.equals("in")) { return (int) Math.round(a*72.0); }
		else if (v.equals("pc")) { return (int) Math.round(a*(72.0/12.0)); }
		else { return a; }
	}
	
	public void startElement(String uri, String localName,String qName,Attributes attrs) throws SAXException {
		if (qName.equals("body")) {
			ignoreContents(false);
		} else if (qName.equals("h1")) {
			if (!inParagraph()) { startPar("h1"); } 
			inParagraph("h1");
		} else if (qName.equals("h2")) {
			if (!inParagraph()) { startPar("h2"); } 
			inParagraph("h2");
		} else if (qName.equals("h3")) {
			if (!inParagraph()) { startPar("h3"); }
			inParagraph("h3");
		} else if (qName.equals("h4")) {
			if (!inParagraph()) { startPar("h4"); }
			inParagraph("h4");
		} else if (qName.equals("ul")) {
			_indent+=36;
			_listCount.push(new Integer(0));
			_list.push(ListType.BULLET);
		} else if (qName.equals("ol")) {
			_indent+=36;
			_listCount.push(new Integer(1));
			_list.push(ListType.NUMBER);
		} else if (qName.equals("li")) {
			if (_list.peek()==ListType.NONE) {
				if (!inParagraph()) { startPar("par"); }
			} else { 
				String s="numbered_list";
				if (_list.peek()==ListType.BULLET) { s="bullet_list"; } 
				if (!inParagraph()) { 
					startPar("sp",s,_indent);
					if (s.equals("numbered_list")) {
						int cnt=_listCount.pop();
						addText(String.format("%d.",cnt));
						startElem("tab");endElem("tab");
						cnt+=1;
						_listCount.push(new Integer(cnt));
					} else {
						addText(String.format("-"));
						startElem("tab");endElem("tab");
					}
				}
			}
			inParagraph("li");
		} else if (qName.equals("b") || qName.equals("strong")) {
			if (inParagraph()) { startElem("b"); }
		} else if (qName.equals("i") || qName.equals("em")) {
			if (inParagraph()) { startElem("i"); }
		} else if (qName.equals("u")) {
			if (inParagraph()) { startElem("u"); }
		} else if (qName.equals("img")) {
			boolean inPar=inParagraph();
			if (!inPar) { startPar("par"); }
			startElem("image");
			String src=attrs.getValue("src");
			String alt=attrs.getValue("alt");
			String style=attrs.getValue("style");
			String width=attrs.getValue("width");
			String height=attrs.getValue("height");
			if (src!=null) {
				try {
					URL url=new URL(src);
					_elemStack.peek().setAttribute("url",url.toString());

					if (alt!=null) { _elemStack.peek().setAttribute("description",alt); }
					if (width!=null) { _elemStack.peek().setAttribute("width_in_px", width); }
					if (height!=null) { _elemStack.peek().setAttribute("height_in_px", height); }
					if (style!=null) {
						String[] keyvals=style.split("[;]");
						for (String keyval : keyvals) {
							String [] kv=keyval.split("[:]");
							if (kv.length==2) {
								String key=kv[0].trim();
								String val=kv[1].trim();
								if (key.compareToIgnoreCase("width")==0) { 	
									Integer w=Integer.parseInt(val);
									if (inPx(val)) {
										_elemStack.peek().setAttribute("width_in_px",w.toString());
									} else if (inPerc(val)) {
										_elemStack.peek().setAttribute("width_in_%",w.toString());
									} else {
										_elemStack.peek().setAttribute("width_in_pt",toPt(w,val).toString());
									}
								}
								if (key.compareToIgnoreCase("height")==0) { 	
									Integer h=Integer.parseInt(val);
									if (inPx(val)) {
										_elemStack.peek().setAttribute("height_in_px",h.toString());
									} else if (inPerc(val)) {
										_elemStack.peek().setAttribute("width_in_%",h.toString());
									} else {
										_elemStack.peek().setAttribute("height_in_px",toPt(h,val).toString());
									}
								}
							}
						}
					}
				} catch (MalformedURLException e) {
					_elemStack.pop(); // skip this image. It won't work.
					if (!inPar) { _elemStack.pop(); }
				}
			}
		} else if (qName.equals("p")) {
			if (!inParagraph()) { startPar("par"); }
			inParagraph("par");
		} else if (qName.equals("br")) {
			// Enters must be added at </br>
			//if (inParagraph()) { _xmlnote.append("<enter />"); }
		} else if (qName.equals("tr")) {
			if (!inParagraph()) { startPar("sp","table"); }
			inParagraph("sp");
		} else if (qName.equals("td")) {
			// Tabs must be added at </td>
			//if (inParagraph()) { _xmlnote.append("<tab />"); }
		} else if (qName.equals("th")) {
			if (inParagraph()) { startElem("b"); }
		} else if (inIgnores(qName)) {
			this.ignoreContents(true);
		}
	}
	
	public void characters(char [] ch,int start,int length) throws SAXException {
		if (!ignoreContents() && inParagraph()) {
			String s=new String(ch,start,length);
			//s=s.replaceAll("\\t", "<tab />");
			//s=s.replaceAll("\\s+"," ");
			//s=s.replaceAll("\\s", "<space />");
			addText(s);
		}
	}
	
	
	public void endElement(String uri,String localName,String qName) throws SAXException {
		if (qName.equals("h1")) {
			endPar("h1");
		} else if (qName.equals("h2")) {
			endPar("h2");
		} else if (qName.equals("h3")) {
			endPar("h3");
		} else if (qName.equals("h4")) {
			endPar("h4");
		} else if (qName.equals("ul")) {
			_indent-=36;
			_listCount.pop();
			_list.pop();
		} else if (qName.equals("ol")) {
			_indent-=36;
			_listCount.pop();
			_list.pop();
		} else if (qName.equals("li")) {
			if (_list.peek()==ListType.NONE) {
				endPar("par");
			} else { 
				endPar("sp");
			}
		} else if (qName.equals("b") || qName.equals("strong")) {
			if (inParagraph()) { endElem("b"); }
		} else if (qName.equals("i") || qName.equals("em")) {
			if (inParagraph()) { endElem("i"); }
		} else if (qName.equals("u")) {
			if (inParagraph()) { endElem("u"); }
		} else if (qName.equals("img")) {
			String txt=_elemStack.peek().getTextContent();
			if (txt==null || txt.equals("")) {
				addText("i");
			}
			endElem("image");
			if (!inParagraph()) { endElem("par"); }
		} else if (qName.equals("p")) {
			endPar("par");
		} else if (qName.equals("br")) {
			if (inParagraph()) { startElem("enter");endElem("enter"); }
		} else if (qName.equals("tr")) {
			endPar("sp");
		} else if (qName.equals("td")) {
			if (inParagraph()) { startElem("tab");endElem("tab");  }
		} else if (qName.equals("th")) {
			if (inParagraph()) { endElem("b");startElem("tab");endElem("tab"); }
		} else if (inIgnores(qName)) {  // TODO: CHeck of dit niet ook recursief moet werken!
			this.ignoreContents(false);
		}
	}

	public HTMLContentHandler(XHtmlToXMLNote converter) throws ParserConfigurationException {
		_converter=converter;
		
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		DocumentBuilder db=dbf.newDocumentBuilder();
		_xmlnote=db.newDocument();
		org.w3c.dom.Element root=_xmlnote.createElement("xmlnote");
		root.setAttribute("version", "2010.1");
		root.appendChild(_xmlnote.createElement("meta"));
		org.w3c.dom.Element notes=_xmlnote.createElement("notes");
		root.appendChild(notes);
		_xmlnote.appendChild(root);

		_elemStack=new Stack<org.w3c.dom.Element>();
		//_elemStack.push(root);
		_elemStack.push(notes);
		
		_list=new Stack<ListType>();
		_listCount=new Stack<Integer>();
		_listCount.push(new Integer(0));
		_list.push(ListType.NONE);
		_ignoreContents=true;
		_parTags=new Stack<String>();
		_indent=0;
		
	}

}