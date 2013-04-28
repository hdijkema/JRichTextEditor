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

package net.oesterholt.jxmlnote.clipboard;

import java.awt.Component;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import javax.swing.text.BadLocationException;

import net.oesterholt.jxmlnote.clipboard.XMLNoteClipboardListener.MarkHandling;
import net.oesterholt.jxmlnote.clipboard.XMLNoteClipboardListener.Moment;
import net.oesterholt.jxmlnote.clipboard.XMLNoteClipboardListener.Type;
import net.oesterholt.jxmlnote.document.XMLNoteDocument;
import net.oesterholt.jxmlnote.document.XMLNoteMark;
import net.oesterholt.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.oesterholt.jxmlnote.html.HtmlToXHtml;
import net.oesterholt.jxmlnote.html.XHtmlToXMLNote;
import net.oesterholt.jxmlnote.html.XMLNoteToHtml;
import net.oesterholt.jxmlnote.widgets.JXMLNotePane;


public class XMLNoteTransferHandler extends TransferHandler implements ClipboardOwner  {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	// Transferable for XMLNote
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	class Range {
		private int start,end;
		XMLNoteDocument doc;
		
		public int getStart() {
			return start;
		}
		
		public int getEnd() {
			return end;
		}
		
		public XMLNoteDocument getDoc() {
			return doc;
		}
		
		public Range(XMLNoteDocument d,int s,int e) {
			doc=d;
			start=s;
			end=e;
		}
	}
	
	class XMLNoteTransferable implements Transferable {
		
		private DataFlavor [] _flavors=null;
		private Object[]	  _data=null;
		private Range		  _selectionRange;
		
		public void removeText() {
			if (_selectionRange!=null) {
				try {
					_selectionRange.getDoc().remove(_selectionRange.getStart(), _selectionRange.getEnd()-_selectionRange.getStart());
				} catch (BadLocationException e) {
					// unexpected
					DefaultXMLNoteErrorHandler.exception(e);
				}
			}
		}
		
		private ByteBuffer makeByteBuffer(String in) {
			try {
				byte[] bytes=in.getBytes("UTF-8");
				return ByteBuffer.wrap(bytes);
			} catch (UnsupportedEncodingException e) {
				// this is unexpected
				DefaultXMLNoteErrorHandler.exception(e);
				return null;
			}
			
		}
		
		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			if (!isDataFlavorSupported(flavor)) {
				throw new UnsupportedFlavorException(flavor);
			} else {
				int i,n;
				for(i=0,n=_flavors.length;i<n && !_flavors[i].equals(flavor);i++);
				if (i==n) {
					throw new IOException("No flavor supported");
				} else {
					return _data[i];
				}
			}
		}

		public DataFlavor[] getTransferDataFlavors() {
			return _flavors;
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			int i;
			for(i=0;i<_flavors.length && !flavor.equals(_flavors[i]);i++);
			return (i<_flavors.length);
		}
		
		public XMLNoteTransferable(int action) {
			Vector<DataFlavor> v=new Vector<DataFlavor>();
			Vector<Object> d=new Vector<Object>();
			Object o;
			
			_selectionRange=getSelectionRange();
			
			o=getXMLNoteData(action);
			if (o!=null) { 
				v.add(new XMLNoteDataFlavor("String"));
				d.add(o); 
			}
			o=getHtmlData();
			if (o!=null) { 
				v.add(new DataFlavor("text/html;charset=UTF-8;class=java.lang.String","HTML DataFlavor"));
				d.add(o);
			}
			o=getTextData();
			if (o!=null) {
				v.add(DataFlavor.stringFlavor);
				d.add(o);
			}
			
			if (v.size()==0) {
				_flavors=null;
				_data=null;
			} else {
				_flavors=new DataFlavor[v.size()];
				_data=new Object[v.size()];
				v.toArray(_flavors);
				d.toArray(_data);
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	// Private variables
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final long serialVersionUID = 1L;
	
	private TransferHandler					_defaultHandler;
	private JXMLNotePane					_editPane;
	private Hashtable<String,Set<String>>	_acceptedClasses;
	private Hashtable<String,Set<String>>	_acceptedCharsets;
	
	private int								_action;		// briefly used by exportToClipboard.
															// used by createTransferable()!

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	// Clipboard Ownership
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void lostOwnership(Clipboard clip, Transferable t) {
		// Doesn't do anything (yet?)
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	// Support functions
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void throwFatal(JComponent c) {
		try {
			String msg=String.format("Type '%s' is not supported by XMLNoteTransferHandler", c.getClass().getName());
			throw new Exception(msg);
			
		} catch (Exception E) {
			DefaultXMLNoteErrorHandler.fatal(E,-1,E.getMessage());
		}
	}
	
	private boolean validCharset(String charset,String cl) {
		Set<String> charset_set=_acceptedCharsets.get(cl);
		if (charset_set==null) {
			return false;
		} else {
			return charset_set.contains(charset);
		}
	}
	
	private boolean validClass(String baseMimeType,String cl) {
		Set<String> mimes=_acceptedClasses.get(cl);
		if (mimes==null) {
			return false;
		} else {
			return mimes.contains(baseMimeType);
		}
	}
	
	private String getCharset(MimeType mt) {
		return mt.getParameter("charset");
	}
	
	private String getTypeClass(MimeType mt) {
		String cl=mt.getParameter("class");
		if (cl==null) {
			cl=mt.getParameter("representationclass");
			return cl;
		} else {
			return cl;
		}
	}
	
	private DataFlavor flavorToImport(DataFlavor[] flavors) {
		DataFlavor canUse=null;
		int priority=3;
		
		for (DataFlavor f : flavors) {
			try {
				MimeType mt=new MimeType(f.getMimeType());
				//System.out.println(mt);
				String baset=mt.getBaseType();
				if (baset.equals("text/html") && priority>1) {
					String charset=getCharset(mt);
					String cl=getTypeClass(mt);
					if (validCharset(charset,cl) && validClass(baset,cl)) {
						canUse=f;
						priority=1;
					}
				} else if (baset.equals("text/plain") && priority>2) {
					String charset=getCharset(mt);
					String cl=getTypeClass(mt);
					if (validCharset(charset,cl) && validClass(baset,cl)) {
						canUse=f;
						priority=1;
					}
				} else if (baset.equals(XMLNoteDataFlavor.mimetype()) && priority>0) {
					String charset=getCharset(mt);
					String cl=getTypeClass(mt);
					if (validCharset(charset,cl) && validClass(baset,cl)) {
						canUse=f;
						priority=0;
					}
				}
			} catch (MimeTypeParseException e) {
				// does nothing
			}
		}
		return canUse;
	}
	

	private ByteBuffer correctFireFoxBahaviourIfNecessary(ByteBuffer in) {
		// If the byte buffer starts with Unicode Replacement characters for UTF-8 (two times)
		// 0xEF 0xBF 0xBD and the sequence of bytes that follows is of pattern <number> 0 <number> 0 ...
		// This must be firefox clipboard data and we convert it to a new buffer.
		
		// first of all, the byte buffer must contain at least 3+3+2 bytes, i.e. 8.
		
		//CharBuffer b=in.asCharBuffer();
		
		if (in.limit()>=8) {  // we must assume that an exact buffer size has been allocated
			//System.out.println(String.format("%02x %02x %02x",in.get(0),in.get(1),in.get(2)));
			//System.out.println(String.format("%02x %02x %02x",in.get(3),in.get(4),in.get(5)));
			//System.out.println(String.format("%02x %02x %02x",in.get(6),in.get(7),in.get(8)));
			
			int ef=(byte) 0xef;
			int bf=(byte) 0xbf;
			int bd=(byte) 0xbd;
			
			if (in.get(0)==ef && in.get(1)==bf && in.get(2)==bd && 
					in.get(3)==ef && in.get(4)==bf && in.get(5)==bd && in.get(7)==0) {
				// OK, this must be something from firefox, convert it.
				// Filter out all ef bf bd 00 sequences too
				
				int i,n,k;

				// Next get all bytes
				for(i=6,k=0,n=in.limit();i<n;i+=2,k++) {
					if (in.get(i)==ef && in.get(i+1)==bf && in.get(i+2)==bd && in.get(i+3)==0) {
						i+=2;
					} else {
						in.put(k,in.get(i));
					}
				}
				in.limit(k);
				return in;
			} else {
				return in;
			}
		} else {
			return in;
		}
	}
	
	private String getDataAsString(Transferable t,DataFlavor f) throws UnsupportedFlavorException, IOException, MimeTypeParseException {
		Object o=t.getTransferData(f);
		if (o instanceof java.nio.ByteBuffer) {
			
			MimeType mt=new MimeType(f.getMimeType());
			java.nio.ByteBuffer buf=(java.nio.ByteBuffer) o;
			String charset=getCharset(mt);
			
			// Firefox has strange behaviour with java under Linux.
			// Correct that if at all possible.
			buf=correctFireFoxBahaviourIfNecessary(buf);
			
			// Now convert the stuff
			
			Charset cset=Charset.forName(charset);
			CharBuffer cbuf=cset.decode(buf);
			StringBuffer sbuf=new StringBuffer();
			sbuf.append(cbuf.subSequence(0, cbuf.length()));
			
			return sbuf.toString();
			
		} else if (o instanceof java.lang.String) {
			return (String) o;
		} else {
			return null;
		}
	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////
	// Data retrievers for the transferable
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Object getXMLNoteData(int action) {
		XMLNoteDocument doc=_editPane.getXMLNoteDoc();
		int start,end;
		start=_editPane.getSelectionStart();
		end=_editPane.getSelectionEnd();
		try {
			XMLNoteDocument copyDoc=doc.getPart(start,end,doc.getClipboardIncludeMarks());
			Iterator<XMLNoteClipboardListener> it=doc.clipboardListenerIterator(); 
			while (it.hasNext()) {
				Type t=Type.COPY;
				if (action==MOVE) { t=Type.MOVE; }
				it.next().exportToClipboard(_editPane, copyDoc, Moment.BEFORE,t);
			}
			String xml=copyDoc.toXML();
			while (it.hasNext()) {
				Type t=Type.COPY;
				if (action==MOVE) { t=Type.MOVE; }
				it.next().exportToClipboard(_editPane, copyDoc, Moment.AFTER,t);
			}
			return xml;
		} catch (Exception e) {
			return null;
		} 	 
	}

	private Object getHtmlData() {
		XMLNoteDocument doc=_editPane.getXMLNoteDoc();
		int start,end;
		start=_editPane.getSelectionStart();
		end=_editPane.getSelectionEnd();
		try {
			XMLNoteDocument copyDoc=doc.getPart(start,end,XMLNoteDocument.FULL); 
			return XMLNoteToHtml.toString(copyDoc,_editPane.getMarkMarkupProviderMaker());
		} catch (Exception e) {
			return null;
		} 	 
	}
	
	private Object getTextData() {
		XMLNoteDocument doc=_editPane.getXMLNoteDoc();
		int start,end;
		start=_editPane.getSelectionStart();
		end=_editPane.getSelectionEnd();
		try {
			if ((end-start)<=0) {
				return null;
			} else {
				return doc.getText(start, end-start);
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	private Range getSelectionRange() {
		return new Range(_editPane.getXMLNoteDoc(),_editPane.getSelectionStart(),_editPane.getSelectionEnd());
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	// Overridden TransferHandler
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Calls the previous handler of the JXMLNotePane.
	 * Drag is not supported yet
	 */
	public void exportAsDrag(JComponent comp,InputEvent e,int action) {
		_defaultHandler.exportAsDrag(comp,e,action);
	}
	
	/**
	 * Exports the JXMLNoteDocument to the clipboard for the currently selected text.
	 */
	public void exportToClipboard(JComponent comp,Clipboard clip,int action) throws IllegalStateException {
		//System.out.println(_defaultHandler.getClass());
		_action=action;
		 if ((action == COPY || action == MOVE)
				 && (getSourceActions(comp) & action) != 0) {
			 Transferable t = createTransferable(comp);
			 if (t != null) {
				 try {
					 clip.setContents(t, null);	 	// clip.setContents(createTransferable(comp),this);
					 exportDone(comp, t, action);
					 return;
				 } catch (IllegalStateException ise) {
					 exportDone(comp, t, NONE);
					 throw ise;
				 }
			 }
		 }
		 exportDone(comp, null, NONE);
	}

	/**
	 * Imports the data for XMLNote documents (application/xmlnote+xml, text/html, or text/plain) are
	 * supported. If XMLNote is imported, the XMLNote Document that is imported may contain XMLNoteMarks.
	 * These XMLNoteMarks will be reassigned new ids(), if XMLNoteDocument.getMarkIdReallocator() on the
	 * document that the clipboard data is pasted into, is not null.
	 */
	public boolean importData(TransferHandler.TransferSupport support) {
		return importData((JComponent) support.getComponent(),support.getTransferable(),support);
	}

	/**
	 * Imports the data for XMLNote documents (application/xmlnote+xml, text/html, or text/plain) are
	 * supported. 
	 */
	public boolean importData(JComponent comp,Transferable t) {
		return importData(comp,t,null);
	}
	
	
	private boolean importData(JComponent comp,Transferable t,TransferSupport support) {
		DataFlavor flavor=flavorToImport(t.getTransferDataFlavors());
		if (flavor!=null) {
			try {
				MimeType mt=new MimeType(flavor.getMimeType());
				String baset=mt.getBaseType();
				if (baset.equals(XMLNoteDataFlavor.mimetype())) {
					String data=getDataAsString(t,flavor);
					if (importXMLNoteData(data)) {
						return true;
					} else {
						return importText(comp,t,support);
					}
				} else if (baset.equals("text/html")) {
					String data=getDataAsString(t,flavor);
					if (importHtmlData(data)) {
						return true;
					} else {
						return importText(comp,t,support);
					}
				} else {
					return importText(comp,t,support);
				}
			} catch (Exception E) {
				DefaultXMLNoteErrorHandler.exception(E);
				return false;
			}
		} else {
			return false;
		}
	}
	
	private boolean importText(JComponent comp,Transferable t,TransferSupport support) {
		// TODO: Change this to something else.
		if (support==null) {
			return _defaultHandler.importData(comp,t);
		} else {
			return _defaultHandler.importData(support);
		}
	}
	
	private boolean importXMLNoteData(String data) {
		XMLNoteDocument doc=_editPane.getXMLNoteDoc();
		boolean fl=doc.setLongEdit(true);
		try {
			XMLNoteDocument pasteDoc=new XMLNoteDocument(data,doc.getXMLNoteImageIconProvider(),doc.getStyles());
			// Correct behaviour for pasting. Maybe due to a problem with copyInto.
			// TODO: Check problem with copyInto()
			pasteDoc.setMeta("startsWithParagraph", false);
			pasteDoc.setMeta("endsWithParagraph", false);
			if (doc.getMarkIdReassigner()!=null) {
				pasteDoc.reassignMarkIds(doc.getMarkIdReassigner());
			}
			int start=_editPane.getSelectionStart();
			int end=_editPane.getSelectionEnd();
			if (start<end) { doc.remove(start, end-start); }
			if (!doc.operationVetoed()) {
				boolean pasteMarks=doc.getAllowMarksPasted();
				boolean donotpaste=false;
				Iterator<XMLNoteClipboardListener> it=doc.clipboardListenerIterator();
				while (it.hasNext()) { 
					MarkHandling m=it.next().importData(pasteDoc, _editPane, start, Moment.BEFORE);
					if (m==MarkHandling.DO_NOT_PASTE) { donotpaste=true; }
					else if (m==MarkHandling.FORCE_PASTE) { pasteMarks=true; } 
				}
				Vector<XMLNoteMark> adjustedMarks=doc.copyInto(pasteDoc, start, (donotpaste) ? false : pasteMarks);
				it=doc.clipboardListenerIterator();
				while (it.hasNext()) { 
					it.next().importMarks(pasteDoc,adjustedMarks,_editPane);
				}
				it=doc.clipboardListenerIterator();
				while (it.hasNext()) { 
					it.next().importData(pasteDoc, _editPane, start, Moment.AFTER);
				}
			}
			doc.setLongEdit(fl);
			return true;
		} catch (Exception E) {
			doc.setLongEdit(fl);
			DefaultXMLNoteErrorHandler.exception(E);
			return false;
		}
	}
	
	private boolean importHtmlData(String data) {
		XMLNoteDocument doc=_editPane.getXMLNoteDoc();
		boolean fl=doc.setLongEdit(true);
		try {
			String xhtml=HtmlToXHtml.fromHtml(data);
			XMLNoteDocument pasteDoc=XHtmlToXMLNote.convert(xhtml,doc.getXMLNoteImageIconProvider(),doc.getStyles());
			// Correct behaviour for pasting. Maybe due to a problem with copyInto.
			// TODO: Check problem with copyInto()
			pasteDoc.setMeta("startsWithParagraph", false);
			pasteDoc.setMeta("endsWithParagraph", false);
			int start=_editPane.getSelectionStart();
			int end=_editPane.getSelectionEnd();
			if (start<end) { doc.remove(start, end-start); }
			doc.copyInto(pasteDoc, start, doc.getAllowMarksPasted());
			doc.setLongEdit(fl);
			return true;
		} catch (Exception e) {
			doc.setLongEdit(fl);
			return false;
		} 	
	}
	
	/**
	 * Returns <code>canImport((JComponent) support.component(),supprt.getDataFlavors());</code>
	 */
	public boolean canImport(TransferHandler.TransferSupport support) {
		Component c=support.getComponent();
		if (c instanceof JComponent) {
			return canImport((JComponent) c,support.getDataFlavors());
		} else {
			return _defaultHandler.canImport(support);
		}
	}
	
	/**
	 * Looks if one of the dataflavors for text/html, XMLNote or text/plain can be used.
	 */
	public boolean canImport(JComponent comp,DataFlavor[] transferFlavors) {
		return flavorToImport(transferFlavors)!=null;
	}
	
	/**`
	 * @return COPY_OR_MOVE for an instance of JXMLNotePane
	 */
	public int getSourceActions(JComponent c) {
		return _defaultHandler.getSourceActions(c);
	}
	
	/**
	 * @return a transferable for XMLNoteDocument. Supports three dataflavors:
	 * <code>text/plain</code>, <code>text/html</code> and <code>text/xml</code> (xmlnote xml). 
	 */
	protected Transferable createTransferable(JComponent c) {
		return new XMLNoteTransferable(_action);
	}
	
	
	/**
	 * Calls the previous handler of the JXMLNotePane.
	 */
	public Icon getVisualRepresentation(Transferable t) {
		return _defaultHandler.getVisualRepresentation(t);
	}
	
	/**
	 * Removes text after copied to clipboard.
	 */
	protected void exportDone(JComponent source,Transferable data,int action) {
		if (action == MOVE) {
			XMLNoteTransferable t = (XMLNoteTransferable) data;
			t.removeText();
		}
	}
	
	
	/**
	 * Creates a new XMLNoteTransferHandler on a JXMLNotePane. This transferhandler is specific for
	 * JXMLNotePanes, not for any other type of JComponent.
	 * 
	 *  
	 * @param _edit
	 */
	public XMLNoteTransferHandler(JXMLNotePane editPane) {
		_defaultHandler=editPane.getTransferHandler();
		_editPane=editPane;
		editPane.setTransferHandler(this);
	
		// initialize accepted mimetypes per representation class
		_acceptedClasses=new Hashtable<String,Set<String>>();
		Set<String> htmlset=new HashSet<String>();
		htmlset.add("text/html");
		_acceptedClasses.put("java.nio.ByteBuffer",htmlset);
		Set<String> otherset=new HashSet<String>();
		otherset.add("text/plain");
		otherset.add("application/xmlnote+xml");
		_acceptedClasses.put("java.lang.String",otherset);
		
		// initialize accepted charsets per representation class
		_acceptedCharsets=new Hashtable<String,Set<String>>();
		htmlset=new HashSet<String>();
		htmlset.add("UTF-8");
		_acceptedCharsets.put("java.nio.ByteBuffer",htmlset);
		otherset=new HashSet<String>();
		otherset.add("Unicode");
		otherset.add("UTF-8");
		_acceptedCharsets.put("java.lang.String",otherset);
	}

}
