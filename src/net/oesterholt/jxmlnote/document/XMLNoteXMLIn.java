/* ******************************************************************************
 *
 *       Copyright 2008-2013 Hans Oesterholt-Dijkema
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

package net.oesterholt.jxmlnote.document;

import java.awt.Dimension;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.oesterholt.jxmlnote.exceptions.BadDocumentException;
import net.oesterholt.jxmlnote.exceptions.BadMetaException;
import net.oesterholt.jxmlnote.exceptions.BadStyleException;
import net.oesterholt.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.oesterholt.jxmlnote.exceptions.MarkExistsException;
import net.oesterholt.jxmlnote.exceptions.NoStyleException;
import net.oesterholt.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import net.oesterholt.jxmlnote.internationalization.XMLNoteTranslator;


import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLNoteXMLIn extends DefaultHandler {
	
	private class MXMLNoteMark extends XMLNoteMark {

		private int _end=-1;
		private int _offset=-1;

		public String content() {
			return null;
		}

		public Integer offset() {
			return _offset;
		}
		
		public Integer endOffset() {
			return _end;
		}
		
		public void endOffset(int e) {
			_end=e;
		}
		
		public MXMLNoteMark(String _id,int offset) {
			super(_id);
			_offset=offset;
		}

		
	}
	
	private class ImageData {
		
		private String url;
		private String id;
		private String description;
		private XMLNoteImageIconSize size;
		
		
		String getId()  { 
			return id; 
		}
		
		URL getUrl() throws MalformedURLException {
			if (url==null) { return null; }
			else if (url.trim().equals("")) { return null; }
			else {
				return new URL(url);
			}
		}
		
		String getDescription() {
			return description;
		}
		
		XMLNoteImageIconSize getSize() {
			return size;
		}
		
		public ImageData(Attributes a) {
			url=a.getValue("url");
			description=a.getValue("description");
			id=a.getValue("id");


			String width_in_pt=a.getValue("width_in_pt");
			String height_in_pt=a.getValue("height_in_pt");
			String width_in_px=a.getValue("width_in_px");
			String height_in_px=a.getValue("height_in_px");
			if (width_in_pt==null && height_in_pt==null) {
				int w=(width_in_px==null) ? -1 : Integer.parseInt(width_in_px);
				int h=(height_in_px==null) ? -1 : Integer.parseInt(height_in_px);
				if (h==0) { h=-1; }
				if (w==0) { w=-1; }
				size=new XMLNoteImageIconSize(w,h,XMLNoteImageIconSize.TYPE_PX);
			} else {
				int w=(width_in_pt==null) ? -1 : Integer.parseInt(width_in_pt);
				int h=(height_in_pt==null) ? -1 : Integer.parseInt(height_in_pt);
				if (h==0) { h=-1; }
				if (w==0) { w=-1; }
				size=new XMLNoteImageIconSize(w,h,XMLNoteImageIconSize.TYPE_PT);
			}
			
		}
	}
	
	private XMLNoteTranslator				_translator=null;
	private XMLNoteDocument 				_doc=null;
	private static SAXParserFactory 		_factory=null;
	private Stack<DefaultHandler>   		_handlerStack=null;
	private Stack<String>					_xpath=null;
	private Hashtable<String,MXMLNoteMark>	_marks=null;
	private Hashtable<String,MXMLNoteMark>	_incompleteMarks=null;
	
	private Integer getAlign(String align) {
		if (align==null) {
			return null;
		} else if (align.equals("left")) {
			return StyleConstants.ALIGN_LEFT;
		} else if (align.equals("right")) {
			return StyleConstants.ALIGN_RIGHT;
		} else if (align.equals("center")) {
			return StyleConstants.ALIGN_CENTER;
		} else if (align.equals("justify")) {
			return StyleConstants.ALIGN_JUSTIFIED;
		} else {
			return null;
		}
	}
	
	private Integer getIndent(String indent) {
		if (indent==null) { 
			return null;
		} else {
			try {
				return Integer.parseInt(indent);
			} catch (NumberFormatException e) {
				return null;
			}
		}
	}
	
	
	private void unexpected(String qName,String expected) throws SAXException {
		String form=_translator.translate("E10010:xmlnote: unexpected element '%s', expected '%s'");
		if (_xpath.isEmpty()) {
			String msg=String.format(form,"//"+qName,expected);
			throw new SAXException(msg);
		} else {
			String msg=String.format(form,_xpath.peek()+"/"+qName,expected);
			throw new SAXException(msg);
		}
	}
	
	private void wrongAttribute(String qName,String attr,String expected) throws SAXException {
		String form=_translator.translate("E10020:xmlnote: wrong attribute '%s', expected '%s'");
		if (_xpath.isEmpty()) {
			throw new SAXException(String.format(form,"//"+qName+"@"+attr,expected));
		} else {
			throw new SAXException(String.format(form,_xpath.peek()+"/"+qName+"@"+attr,expected));
		}	
	}
	
	private void pushNew(String qName) {
		if (_xpath.isEmpty()) {
			_xpath.push("//"+qName);
		} else {
			_xpath.push(_xpath.peek()+"/"+qName);
		}
	}
	
	private class UnexpectedHandler extends DefaultHandler {
		public void startElement(String uri,String localName,String qName,Attributes attributes) throws SAXException {
			unexpected(qName,"<nothing>");
		}
	}
	
	private class ApplyAttributes {
		
		private int 		 			_start;
		private int 		 			_end;
		private SimpleAttributeSet 		_set;
		private ImageData				_icon;
		
		public void setEnd(int endOffset) {
			_end=endOffset;
		}
		
		private XMLNoteImageIcon getIcon() throws SAXException {
			XMLNoteImageIcon.Provider prov=_doc.getXMLNoteImageIconProvider();
			
			XMLNoteImageIcon icn=null;
			String description="no description";
			XMLNoteImageIconSize size=new XMLNoteImageIconSize(-1,-1,XMLNoteImageIconSize.TYPE_PT);
			
			if (prov==null) {
				try {
					URL url=_icon.getUrl();
					if (url==null) {
						String id=_icon.getId();
						if (id!=null) {
							throw new SAXException("Image with id '"+id+"', but no XMLNoteImageIcon.Provider set on document");
						} else {
							throw new SAXException("Image with no URL, can't load that image");
						}
					} else {
						String d=_icon.getDescription();
						if (d==null) { d=description; }
						XMLNoteImageIconSize s=_icon.getSize();
						icn=new XMLNoteImageIcon(url,d,s);
					} 
				} catch (MalformedURLException e) {
					throw new SAXException(e);
				}
			} else {
				String id=_icon.getId();
				if (id!=null) {
					icn=prov.getIcon(id);
				} else {
					try {
						URL url=_icon.getUrl();
						String d=_icon.getDescription();
						if (d==null) { d=description; }
						XMLNoteImageIconSize s=_icon.getSize();
						if (url==null) {
							throw new SAXException("Image with no URL, can't load that image");
						}
						icn=prov.getIcon(url,d,s);
					} catch (MalformedURLException e) {
						throw new SAXException(e);
					}
				}
			}
			return icn;
		}
		
		public void applyAttributes(XMLNoteDocument doc,int parOffset) throws SAXException {
			if (_icon==null) {
				doc.setCharacterAttributes(parOffset+_start, _end-_start, _set, false);
			} else {
				SimpleAttributeSet set=new SimpleAttributeSet();
				XMLNoteImageIcon icn=getIcon();
				if (icn!=null) {
					StyleConstants.setIcon(set, icn);
					doc.setCharacterAttributes(parOffset+_start, _end-_start, set, true);
				}
			}
		}
		
		public ApplyAttributes(SimpleAttributeSet set,int startOffset) {
			_set=set;
			_start=startOffset;
			_icon=null;
		}
		
		public ApplyAttributes(ImageData icn,int startOffset) {
			_icon=icn;
			_start=startOffset;
			_set=null;
		}
		
	}
	
	private class ParHandler extends DefaultHandler {
		
		private String 						_name;
		private String						_align;
		private String						_indent;
		private String 						_txt;
		private Vector<ApplyAttributes>		_attrs;
		private ApplyAttributes				_bold;
		private ApplyAttributes				_italic;
		private ApplyAttributes				_underline;
		private ApplyAttributes				_image;
		private ParsHandler					_pars;
		private int						_docOffset;
		
		public String getText() {
			return _txt;
		}
		
		public String styleId() {
			return _name;
		}
		
		public String align() {
			return _align;
		}
		
		public String indent() {
			return _indent;
		}
		
		public void applyMarkup(XMLNoteDocument d,int offset) throws SAXException {
			Iterator<ApplyAttributes> it=_attrs.iterator();
			while (it.hasNext()) {
				it.next().applyAttributes(d,offset);
			}
		}

		public void startElement(String uri,String localName,String qName,Attributes attributes) throws SAXException {
			if (qName.equals("enter")) {
				_txt+="\n";
			} else if (qName.equals("tab")) {
				_txt+="\t";
			} else if (qName.equals("space")) {
				_txt+=" ";
			} else if (qName.equals("b")) {
				SimpleAttributeSet s=new SimpleAttributeSet();
				s.addAttribute(StyleConstants.Bold, true);
				_bold=new ApplyAttributes(s,_txt.length());
			} else if (qName.equals("i")) {
				SimpleAttributeSet s=new SimpleAttributeSet();
				s.addAttribute(StyleConstants.Italic,true);
				_italic=new ApplyAttributes(s,_txt.length());
			} else if (qName.equals("u")) {
				SimpleAttributeSet s=new SimpleAttributeSet();
				s.addAttribute(StyleConstants.Underline,true);
				_underline=new ApplyAttributes(s,_txt.length());
			} else if (qName.equals("mark")) {
				String _id=attributes.getValue("id");
				String _class=attributes.getValue("class");
				String _type=attributes.getValue("type");
				if (_id==null) {
					String msg=_translator.translate("id attribute is required");
					wrongAttribute(qName,"id",msg);
				} 
				if (_type==null) {
					String msg=_translator.translate("type attribute is required");
					wrongAttribute(qName,"type",msg);
				}
				if (!_type.equals("end") && !_type.equals("start")) {
					String form=_translator.translate("type attribute must be of value 'start' or 'end', got '%s'");
					wrongAttribute(qName,"type",String.format(form,_type));
				}
				if (_type.equals("start")) {
					//System.out.println("mark[str] = "+_id);
					MXMLNoteMark m=new MXMLNoteMark(_id,_docOffset+_txt.length());
					m.markClass(_class);
					if (_marks.get(_id)==null) {
						MXMLNoteMark m1 = _incompleteMarks.get(_id);
						if (m1 != null) { // found an end mark before a start mark, resolve
							//System.out.println("mark[inc] = "+_id+", end offset = "+m1.endOffset());
							m.endOffset(m1.endOffset());
							_incompleteMarks.remove(_id);
						}
						_marks.put(_id, m);
					} else {
						String form=_translator.translate("E10001:Found a start of mark with id '%s'. A mark with this id already exists");
						throw new SAXException(String.format(form,_id));
					}
				} else if (_type.equals("end")) {
					//System.out.println("mark[end] = "+_id);
					MXMLNoteMark m=_marks.get(_id);
					if (m==null) {
						// Workaround for crash, where no start offset has been found.
						// Normally this is a result of a store of an empty start - end mark, 
						// which can result in the storage of the end mark, followed by the start mark,
						// which isn't what you want. So we just ignore end marks without start marks
						// and ignore start marks that were already encountered as end marks.
						// Maybe we need to Garbage collect some tables for the marks.
						MXMLNoteMark m1 = new MXMLNoteMark(_id,_docOffset+_txt.length());
						m1.endOffset(_docOffset+_txt.length());
						m1.markClass(_class);
						_incompleteMarks.put(_id,  m1);
						//String form=_translator.translate("E10002:Found an end of mark with id '%s' without a start");
						//throw new SAXException(String.format(form,_id));
					} else {
						m.endOffset(_docOffset+_txt.length());
					}
				}
			} else if (qName.equals("image")) {
				_image=new ApplyAttributes(new ImageData(attributes),_txt.length());
				//_txt+="i";
			} else {
				unexpected(qName,"notes");
			}
		}
		
		public void characters(char[] ch, int start, int length) throws SAXException {
			String s=new String(ch,start,length);
			s.replaceAll("\\s", "");
			_txt+=s;
		}
		
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (qName.equals("enter")) {
				// nothing to do
			} else if (qName.equals("tab")) {
				// nothing to do
			} else if (qName.equals("space")) {
				// nothing to do
			} else if (qName.equals("mark")) {
				// nothing to do
			} else if (qName.equals("image")) {
				_image.setEnd(_txt.length());
				_attrs.add(_image);
				_image=null;
			} else if (qName.equals("b")) {
				_bold.setEnd(_txt.length());
				_attrs.add(_bold);
				_bold=null;
			} else if (qName.equals("i")) {
				_italic.setEnd(_txt.length());
				_attrs.add(_italic);
				_italic=null;
			} else if (qName.equals("u")) {
				_underline.setEnd(_txt.length());
				_attrs.add(_underline);
				_underline=null;
			} else { // unhandled situation, next of stack.
				_pars.endElement(uri, localName, qName);
			}
		}
		
		public ParHandler(String parName,String align,String indent,ParsHandler h,int currentTextOffset) {
			_pars=h;
			_name=parName;
			_align=align;
			_indent=indent;
			_txt="";
			_attrs=new Vector<ApplyAttributes>();
			_docOffset=currentTextOffset;
		}
		
	}
	
	private class ParsHandler extends DefaultHandler {
		public void startElement(String uri,String localName,String qName,Attributes attributes) throws SAXException {
			if (qName.equals("par") || qName.equals("h1") || qName.equals("h2") || qName.equals("h3") 
					|| qName.equals("h4") || qName.equals("sp")) {
				String styleId=qName;
				if (qName.equals("sp")) {
					styleId=attributes.getValue("style");
					// be tolerant of a style less sp
					if (styleId==null) { styleId="par"; }
				}
				String align=attributes.getValue("align");
				String indent=attributes.getValue("indent");
				_handlerStack.push(new ParHandler(styleId,align,indent,this,_doc.getLength()));
				pushNew(qName);
			} else {
				unexpected(qName,"par|h1|h2|h3|h4|sp");
			}
		}
		
		public void endElement(String uri, String localName, String qName) throws SAXException {
			DefaultHandler h=_handlerStack.pop();
			_xpath.pop();
			
			if (h instanceof ParHandler) {
				ParHandler p=(ParHandler) h;
				try {
					int offset=_doc.getLength();
					try {
						String align=p.align();
						String indent=p.indent();
						//System.out.println(p.styleId());
						_doc.addParagraph(p.getText(),p.styleId(),getAlign(align),getIndent(indent));
					} catch (NoStyleException e2) {
						throw new SAXException(e2);
					} catch (BadStyleException e3) {
						throw new SAXException(e3);
					}
					p.applyMarkup(_doc,offset);
				} catch (BadLocationException e) {
					throw new SAXException(e);
				}
			}
			
		}
		
	}
	
	
	private class NotesHandler extends DefaultHandler {
		public void startElement(String uri,String localName,String qName,Attributes attributes) throws SAXException {
			if (qName.equals("notes")) {
				_handlerStack.push(new ParsHandler());
				pushNew(qName);
			} else {
				unexpected(qName,"notes");
			}
		}
		
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (qName.equals("notes")) {
				_handlerStack.pop();
				_xpath.pop();
			}
		}
		
	}
	
	private class MetaParamHandler extends DefaultHandler {
		public void startElement(String uri,String localName,String qName,Attributes attributes) throws SAXException {
			if (qName.equals("param")) {
				String name=attributes.getValue("name");
				String type=attributes.getValue("type");
				String value=attributes.getValue("value");
				try {
					_doc.setMeta(name,type,value);
				} catch (BadMetaException e) {
					throw new SAXException(e);
				}
				
			} else {
				unexpected(qName,"param");
			}
		}
			
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (qName.equals("meta")) {
				_handlerStack.pop();
				_handlerStack.peek().endElement(uri, localName, qName);
			}
		}
	}
	
	private class MetaHandler extends DefaultHandler {
		public void startElement(String uri,String localName,String qName,Attributes attributes) throws SAXException {
			if (qName.equals("meta")) {
				_handlerStack.push(new MetaParamHandler());
				pushNew(qName);
			} else {
				unexpected(qName,"meta");
			}
		}
		
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (qName.equals("meta")) {
				_handlerStack.pop();
				_xpath.pop();
			}
		}
	}
	
	public void startElement(String uri,String localName,String qName,Attributes attributes) throws SAXException {
		if (_handlerStack.isEmpty()) {
			if (qName.equals("xmlnote")) {
				String version=attributes.getValue("version");
				if (version==null) {
					String msg=_translator.translate("version attribute is required");
					wrongAttribute(qName,"version",msg);
				} else if (!version.equals("2010.1")) {
					String form=_translator.translate("wrong version value. Got '%s', expected: 2010.1");
					wrongAttribute(qName,"version",String.format(form,version));
				} else {
					_handlerStack.push(new UnexpectedHandler());
					_handlerStack.push(new NotesHandler());_xpath.push("//xmlnote");
					_handlerStack.push(new MetaHandler());_xpath.push("//xmlnote");
				}
			} else {
				unexpected(qName,"//xmlnote");
			}
		} else {
			_handlerStack.peek().startElement(uri, localName, qName, attributes);
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (_handlerStack.isEmpty()) {
			if (!qName.equals("xmlnote")) {
				unexpected(qName,"xmlnote");
			}
		} else {
			_handlerStack.peek().endElement(uri, localName, qName);
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (_handlerStack.isEmpty()) {
			// do nothing
		} else {
			_handlerStack.peek().characters(ch,start,length);
		}
	}
	
	public void fromXML(String xml,XMLNoteImageIcon.Provider prov) throws BadDocumentException {
		//System.out.println(xml);
		InputSource source=new InputSource(new StringReader(xml));
		if (prov!=null) { _doc.setXMLNoteImageIconProvider(prov); }
		boolean ignore=_doc.getUndoManager().setIgnore(true);
		try {
			SAXParser parser=_factory.newSAXParser();
			try {
				parser.parse(source, this);
				Enumeration<MXMLNoteMark> en=_marks.elements();
				while(en.hasMoreElements()) {
					MXMLNoteMark mark=en.nextElement();
					if (mark.endOffset()==-1) {
						throw new BadDocumentException(String.format(_translator.translate("E10003:xmlnote Mark without end (mark id='%s')"),
																	 mark.id()
																	)
													   );
					} else {
						try {
							_doc.insertMark(mark.id(),mark.markClass(), mark.offset(), mark.endOffset()-mark.offset());
						} catch (BadLocationException e) {
							throw new BadDocumentException(e);
						} catch (MarkExistsException e) {
							throw new BadDocumentException(e);
						}
					}
				}
				if (_incompleteMarks.size() > 0) {
					throw new BadDocumentException(String.format(_translator.translate("E10004:%d xmlnote End Mark(s) without start"),
												    			 _incompleteMarks.size()
												    			 )
												    
												    );
				}
			} catch (IOException e) {
				throw new BadDocumentException(e);
			}
		} catch (ParserConfigurationException e) {
			throw new BadDocumentException(e);
		} catch (SAXException e) {
			throw new BadDocumentException(e);
		}
		_doc.getUndoManager().setIgnore(ignore);
		_doc.getUndoManager().discardAllEdits();
	}
	
	public XMLNoteXMLIn(XMLNoteDocument d) {
		_translator=new DefaultXMLNoteTranslator();
		_doc=d;
		if (_factory==null) {
			_factory=SAXParserFactory.newInstance();
		}
		_xpath=new Stack<String>();
		_handlerStack=new Stack<DefaultHandler>();
		_marks=new Hashtable<String,MXMLNoteMark>();
		_incompleteMarks=new Hashtable<String,MXMLNoteMark>();
	}

}
