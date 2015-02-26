/* ******************************************************************************
 *
 *       Copyright 2008-2013 Hans Dijkema
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

package nl.dykema.jxmlnote.document;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import nl.dykema.jxmlnote.exceptions.BadDocumentException;
import nl.dykema.jxmlnote.exceptions.BadMetaException;
import nl.dykema.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import nl.dykema.jxmlnote.styles.XMLNoteStyleConstants;
import nl.dykema.jxmlnote.utils.DPIAdjuster;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.DOMException;
import org.w3c.dom.Text;

public class XMLNoteXMLOut {
	
	private XMLNoteDocument _doc;
	
	private class MyElement {
		private AttributeSet _set;
		private int 		 _start;
		private int          _end;
		private String		 _markId;
		
		public int getStartOffset() {
			return _start;
		}
		
		public int getEndOffset() {
			return _end;
		}
				
		public boolean equalsAttr(Element q) {
			AttributeSet qs=q.getAttributes();
			return qs.isEqual(_set);
		}
		
		/**
		 * precondition: equalsAttr(e) 
		 * @param e
		 * @return
		 */
		public boolean canBeMerged(Element e) {
			if ((e.getEndOffset()==_start)) {
				return true;
			}
			if ((e.getStartOffset()==_end)) {
				return true;
			}
			return false;
		}
		
		public AttributeSet getAttributes() {
			return _set;
		}
		
		public void merge(Element e) {
			if (e.getEndOffset()==_start) { _start=e.getStartOffset(); }
			if (e.getStartOffset()==_end) { _end=e.getEndOffset(); }
		}
		
		public MyElement(Element e) {
			_set=e.getAttributes();
			_start=e.getStartOffset();
			_end=e.getEndOffset();
		}
	}
	
	// converts tabs and newlines to xml elements
	private void convertTextPartToXML(org.w3c.dom.Document doc,org.w3c.dom.Element xmlElement,String txt,int start,int end) {
		int i,n,k;
		for(k=0,i=0,n=txt.length();i<n;i++) {
			// marks
			Vector<XMLNoteMark> enders=_doc.getMarksEndingWith(i+start);
			Vector<XMLNoteMark> starters=_doc.getMarksStartingWith(i+start);
			Vector<XMLNoteMark> es = new Vector<XMLNoteMark>();
			
			// check if we have enders with the same Id as starters, because we don't want
			// to get the end --> start situation. We need to first save the start mark
			// and next the end mark. ==> This is a special case where the mark has 
			// size 0.
			
			Hashtable<String,XMLNoteMark> ends = new Hashtable<String,XMLNoteMark>();
			{
				Iterator<XMLNoteMark> it = enders.iterator();
				while (it.hasNext()) {
					XMLNoteMark m = it.next();
					ends.put(m.id(),m);
				}
				it = starters.iterator();
				while (it.hasNext()) {
					XMLNoteMark m = it.next();
					if (ends.get(m.id()) != null) {
						es.add(m);
					}
				}
				it = es.iterator();
				while (it.hasNext()) {
					XMLNoteMark m = it.next();
					enders.remove(m);
					starters.remove(m);
				}
			}
			
			// Now output all marks. 
			if ((enders.size()>0) || starters.size()>0 || es.size()>0) {
				if (k<i) {
					String s=txt.substring(k,i);
					k=i;
					Text node=doc.createTextNode(s);
					xmlElement.appendChild(node);
				}
				Iterator<XMLNoteMark> it=enders.iterator();
				while(it.hasNext()) {
					XMLNoteMark m=it.next();
					String c=m.markClass();
					String id=m.id();
					org.w3c.dom.Element mark=doc.createElement("mark");
					mark.setAttribute("type","end");
					mark.setAttribute("id", id);
					if (c!=null) { mark.setAttribute("class", c); }
					xmlElement.appendChild(mark);
				}
				it=starters.iterator();
				while(it.hasNext()) {
					XMLNoteMark m=it.next();
					String c=m.markClass();
					String id=m.id();
					org.w3c.dom.Element mark=doc.createElement("mark");
					mark.setAttribute("type","start");
					mark.setAttribute("id", id);
					if (c!=null) { mark.setAttribute("class",c); }
					xmlElement.appendChild(mark);
				}
				it=es.iterator();
				while(it.hasNext()) {
					XMLNoteMark m=it.next();
					String c=m.markClass();
					String id=m.id();
					{
						org.w3c.dom.Element mark=doc.createElement("mark");
						mark.setAttribute("type","start");
						mark.setAttribute("id", id);
						if (c!=null) { mark.setAttribute("class",c); }
						xmlElement.appendChild(mark);
					}
					{
						org.w3c.dom.Element mark=doc.createElement("mark");
						mark.setAttribute("type","end");
						mark.setAttribute("id", id);
						if (c!=null) { mark.setAttribute("class", c); }
						xmlElement.appendChild(mark);
					}
				}
			}
			
			// tabs, enters
			char chr=txt.charAt(i);
			if (chr=='\t') { 
				String s=txt.substring(k,i);
				if (s.length()>0) {
					Text node=doc.createTextNode(s);
					xmlElement.appendChild(node);
				}
				org.w3c.dom.Element tab=doc.createElement("tab");
				xmlElement.appendChild(tab);
				k=i+1;
			} else if (chr=='\n') {
				String s=txt.substring(k,i);
				if (s.length()>0) {
					Text node=doc.createTextNode(s);
					xmlElement.appendChild(node);
				}
				org.w3c.dom.Element enter=doc.createElement("enter");
				xmlElement.appendChild(enter);
				k=i+1;
			} else if (chr==' ') {
				String s=txt.substring(k,i);
				if (s.length()>0) {
					Text node=doc.createTextNode(s);
					xmlElement.appendChild(node);
				}
				org.w3c.dom.Element enter=doc.createElement("space");
				xmlElement.appendChild(enter);
				k=i+1;
			}
		} 
		if (k!=i) {
			String s=txt.substring(k,i);
			if (s.length()>0) {
				Text node=doc.createTextNode(s);
				xmlElement.appendChild(node);
			}
		}
	}
	
	// Expected Document structure: See precondition for 'toXML()'
	// This function exports the contents of the styled paragraphds to XMLNote XML.
	// It looks for bold, italics, underline and center/right/left/justify attributes to export.
	// Also it looks if (parts of) the sentence are part of a highlight (XMLNoteMark). 
	private void convertSentenceToXML(org.w3c.dom.Document doc,org.w3c.dom.Element xmlElement,Element parElement) throws BadDocumentException {
		
		int n=parElement.getElementCount();
		Element e;
		AttributeSet s;
		int i;
		boolean bold,italic,underline;

		if (n>0) {
			
			// first merge elements for as far as possible
			Vector<MyElement> merged=new Vector<MyElement>();
			
			e=parElement.getElement(0);
			s=e.getAttributes();
			merged.add(new MyElement(e));
			
			for(i=1;i<n;i++) {
				e=parElement.getElement(i);
				if (merged.lastElement().equalsAttr(e)) {
					if (merged.lastElement().canBeMerged(e)) {
						merged.lastElement().merge(e);
					} else {
						merged.add(new MyElement(e));
					}
				} else {
					merged.add(new MyElement(e));
				}
			}
			
			// Next, write them out.
			for(i=0;i<merged.size();i++) {
				s=merged.get(i).getAttributes();
				
				if (StyleConstants.getIcon(s)!=null) {
					// handle XMLNoteImageIcon
					XMLNoteImageIcon icn=(XMLNoteImageIcon) StyleConstants.getIcon(s);
					org.w3c.dom.Element img=icn.toXML(doc);
					xmlElement.appendChild(img);
					MyElement part=merged.get(i);
					String txt;
					try {
						txt = _doc.getText(part.getStartOffset(),part.getEndOffset()-part.getStartOffset());
					} catch (BadLocationException e1) {
						throw new BadDocumentException(e1.getMessage());
					}
					convertTextPartToXML(doc,img,txt,part.getStartOffset(),part.getEndOffset());
				} else {
					// handle normal text
					bold=(Boolean) s.getAttribute(StyleConstants.Bold);
					italic=(Boolean) s.getAttribute(StyleConstants.Italic);
					underline=(Boolean) s.getAttribute(StyleConstants.Underline);
					MyElement part=merged.get(i);
				
					org.w3c.dom.Element c=xmlElement;
					if (bold) {
						org.w3c.dom.Element el=doc.createElement("b");
						c.appendChild(el);
						c=el;
					}
					if (italic) {
						org.w3c.dom.Element el=doc.createElement("i");
						c.appendChild(el);
						c=el;
					}
					if (underline) {
						org.w3c.dom.Element el=doc.createElement("u");
						c.appendChild(el);
						c=el;
					}
				
					String txt;
					try {
						txt = _doc.getText(part.getStartOffset(),part.getEndOffset()-part.getStartOffset());
					} catch (BadLocationException e1) {
						throw new BadDocumentException(e1.getMessage());
					}
					convertTextPartToXML(doc,c,txt,part.getStartOffset(),part.getEndOffset());
				}
			}
			
			
		} else {
			// Write out paragraph without markup
			try {
				String txt=_doc.getText(parElement.getStartOffset(),parElement.getEndOffset()-parElement.getStartOffset());
				convertTextPartToXML(doc,xmlElement,txt,parElement.getStartOffset(),parElement.getEndOffset());
			} catch (BadLocationException e1) {
				throw new BadDocumentException(e1.getMessage());
			}
		}
	}
	
	// Expected Document structure: See precondition for 'toXML()'.
	// This function exports the styled paragraphs to XMLNote XML.
	private void convertParToXML(org.w3c.dom.Document doc,org.w3c.dom.Element xmlElement,Element docElement) throws BadDocumentException {
		AttributeSet s=docElement.getAttributes();
		// style id
		String styleId=(String) s.getAttribute(XMLNoteStyleConstants.IdAttribute);
		org.w3c.dom.Element nelement;
		if (styleId.equals("h1") || styleId.equals("h2") || styleId.equals("h3") || styleId.equals("h4") || styleId.equals("par")) {
			nelement=doc.createElement(styleId);
		} else {
			nelement=doc.createElement("sp");
			nelement.setAttribute("style", styleId);
		}
		
		// check alignment
		int alignment=StyleConstants.getAlignment(s);
		//Style st=_doc.getStyles().parStyle(styleId).getStyle(DPIAdjuster.DEVICE_NONE);
		AttributeSet st=_doc.getStyles().parStyle(styleId).getStyle(DPIAdjuster.DEVICE_NONE);
		int st_alignment=StyleConstants.getAlignment(st);
		if (alignment!=st_alignment) {
			String align="left";
			if (alignment==StyleConstants.ALIGN_RIGHT) { align="right"; }
			else if (alignment==StyleConstants.ALIGN_CENTER) { align="center"; }
			else if (alignment==StyleConstants.ALIGN_JUSTIFIED) { align="justify"; }
			nelement.setAttribute("align",align);
		}
		
		// check indenting
		int st_indent=(int) StyleConstants.getLeftIndent(st);
		int par_indent=(int) StyleConstants.getLeftIndent(s);
		if (st_indent!=par_indent) {
			String indent=Integer.toString(par_indent);
			nelement.setAttribute("indent", indent);
		}
		
		// convert paragraph text to xml
		convertSentenceToXML(doc,nelement,docElement);
		
		// append paragraph
		xmlElement.appendChild(nelement);
	}
	
	private void createMetaStuff(org.w3c.dom.Document doc,org.w3c.dom.Element meta) {
		Iterator<String> it=_doc.metaKeys();
		while (it.hasNext()) {
			org.w3c.dom.Element em=doc.createElement("param");
			String key=it.next();
			em.setAttribute("name", key);
			try {
				em.setAttribute("type", _doc.metaType(key));
				em.setAttribute("value", _doc.metaValue(key));
			} catch (DOMException e) {
				DefaultXMLNoteErrorHandler.exception(e);
			} catch (BadMetaException e) {
				DefaultXMLNoteErrorHandler.exception(e);
			}
			meta.appendChild(em);
		}
	}
	
	// Precondition: XMLNoteDocument exists as follows (sloppy notation):
	// Doc -- paragraph 1
	//        paragraph 2 -- tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt (text)
	//                      [AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA] (possible character attributesets)
	//                      [MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM] (possible XMLNoteMarks) 
	//        ...
	//        paragraph N
	public String toXML() throws BadDocumentException {
		String xml;
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder ();
			org.w3c.dom.Document doc = db.newDocument ();
			org.w3c.dom.Element root = doc.createElement ("xmlnote");
			Writer		out = new StringWriter();

			doc.appendChild (root);
			root.setAttribute("version", "2010.1");
			org.w3c.dom.Element meta=doc.createElement("meta");
			createMetaStuff(doc,meta);
			root.appendChild(meta);
			org.w3c.dom.Element notes=doc.createElement("notes");
			root.appendChild(notes);
			
			Element eroot=_doc.getDefaultRootElement();
			int nelements=eroot.getElementCount();
			int i;
			for(i=0;i<nelements;i++) {
				convertParToXML(doc,notes,eroot.getElement(i));
			}
			
			//Serialize DOM
		    OutputFormat format    = new OutputFormat (doc); 
		    // as a String
		    XMLSerializer serial   = new XMLSerializer (out,format);
		    try {
				serial.serialize(doc);
			} catch (IOException e) {
				e.printStackTrace();
			}

			xml=out.toString();
			return xml;
		
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public XMLNoteXMLOut(XMLNoteDocument d) {
		_doc=d;
	}
	
}
