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

package net.dijkema.jxmlnote.document;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentEvent.ElementChange;
import javax.swing.event.DocumentEvent.EventType;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Segment;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import net.dijkema.jxmlnote.clipboard.XMLNoteClipboardListener;
import net.dijkema.jxmlnote.document.ContentTree.Node;
import net.dijkema.jxmlnote.document.XMLNoteMarkListener.ChangedMessageWay;
import net.dijkema.jxmlnote.exceptions.BadDocumentException;
import net.dijkema.jxmlnote.exceptions.BadMetaException;
import net.dijkema.jxmlnote.exceptions.BadOperationException;
import net.dijkema.jxmlnote.exceptions.BadStyleException;
import net.dijkema.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.dijkema.jxmlnote.exceptions.MarkExistsException;
import net.dijkema.jxmlnote.exceptions.MarkNoExistException;
import net.dijkema.jxmlnote.exceptions.NoStyleException;
import net.dijkema.jxmlnote.interfaces.MarkMarkupProvider;
import net.dijkema.jxmlnote.interfaces.MarkMarkupProviderMaker;
import net.dijkema.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import net.dijkema.jxmlnote.internationalization.XMLNoteTranslator;
import net.dijkema.jxmlnote.styles.XMLNoteParStyle;
import net.dijkema.jxmlnote.styles.XMLNoteStyleConstants;
import net.dijkema.jxmlnote.styles.XMLNoteStyles;
import net.dijkema.jxmlnote.styles.XMLNoteStylesChangedListener;
import net.dijkema.jxmlnote.undo.XMLNoteMarkInsertUndoableEdit;
import net.dijkema.jxmlnote.undo.XMLNoteMarkRemoveUndoableEdit;
import net.dijkema.jxmlnote.undo.XMLNoteUndoManager;
import net.dijkema.jxmlnote.undo.XMLNoteUndoableForOperation;
import net.dijkema.jxmlnote.utils.DPIAdjuster;
import net.dijkema.jxmlnote.utils.MakeAsync;
import net.dijkema.jxmlnote.widgets.JXMLNotePane;
import net.dijkema.jxmlnote.widgets.marks.MarkerHighlightPainter;



/***********************************************************************************************
 * This is an internal class for date representation for meta key/values.
 ***********************************************************************************************/
class Date extends java.util.Date {

	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;
	
	private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"); 
	
	public String toString() {
		return format.format(this);
	}
	
	public java.util.Date dt() {
		return new java.util.Date(this.getTime());
	}
	
	public static java.util.Date parseDate(String txt) {
		try {
			return format.parse(txt);
		} catch (ParseException e) {
			DefaultXMLNoteErrorHandler.exception(e);
			return new java.util.Date();
		}
	}
	
	public Date(java.util.Date dt) {
		super(dt.getTime());
	}
	
}


/**
 * XMLNoteDocument implements the XMLNote document structure. It inherits from DefaultStyledDocument. 
 * <p>
 * Note! Don't use <code>addDocumentListener()</code> for <code>XMLNoteDocument</code>; use <code>addDocumentPostListener()</code>
 * instead.
 * <p>
 * <h3>Meta related methods:</h3>
 * <ul><li>{@see setMeta(String,int)}</li></ul>
 * <p>
 * @author Hans Dijkema
 *
 */
public class XMLNoteDocument extends DefaultStyledDocument 
	implements MarkMarkupProvider.ChangeListener, UndoableEditListener, XMLNoteStylesChangedListener {

	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;

	//////////////////////////////////////////////////////////////////////////////////////////////////
	// Constants
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static final int FULL=1;
	public static final int PARTIAL=2;
	public static final int NONE=0;
	public static final int ASYNC_STARVATION_TIMEOUT=15000; // 15 seconds until events must be processed
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	// Interfaces and inner classes
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	interface HighlightInfo {
		public int 				startOffset();
		public int 				endOffset();
		public HighlightPainter painter();
	};
	
	
	class DocumentPostListener implements DocumentListener {
		
		private boolean blocked=false;
		
		public boolean setBlocked(boolean b) { boolean bb=blocked;blocked=b;return bb; }

		public void changedUpdate(DocumentEvent e) {
			if (blocked) { return; }
			ElementChange ec=e.getChange(e.getDocument().getDefaultRootElement());
			//System.out.println(ec);
			if (ec!=null) { setChanged(true); }
			if (!_blockAllListeners) {
				Iterator<DocumentListener> it=_postListeners.iterator();
				while(it.hasNext()) { it.next().changedUpdate(e); }
				administerMarkChanges();
			}
		}
		public void insertUpdate(DocumentEvent e) {
			if (blocked) { return; }
			setChanged(true);
			if (!_blockAllListeners) {
				Iterator<DocumentListener> it=_postListeners.iterator();
				while(it.hasNext()) { it.next().insertUpdate(e); }
				administerMarkChanges();
			}
		}
		public void removeUpdate(DocumentEvent e) {
			if (blocked) { return; }
			setChanged(true);
			if (!_blockAllListeners) {
				Iterator<DocumentListener> it=_postListeners.iterator();
				while(it.hasNext()) { it.next().removeUpdate(e); }
				administerMarkChanges();
			}
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////
	// DXMLNoteMark 
	//////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * DXMLNoteMark needs to manage the connection between Highlights for a given 
	 * Highlighter (associated view) and the mark itself. The views are all associated
	 * with this document. 
	 * 
	 * As a consequence, each highlight will contain the same offsets for the
	 * highlight, as they mark sections in the same document.
	 * 
	 * A DXMLNoteMark must be able to exist, without any highlighters attached.
	 * This is necessary when instantiating a document that has no view connected
	 * to it. 
	 * 
	 * At the moment a highlighter (view) is installed to this document, it must
	 * be connected through highlights to all DXMLNoteMarks. At that very moment,
	 * the offsets of a DXMLNoteMark can change, as there is a view in which users
	 * can interact with the document. So then we don't return offset values of
	 * the DXMLNoteMark itself, but of the first Highlight in a row.
	 * 
	 *  As views can be destroyed, without the document being destroyed, Highlighters
	 *  and Highlights must be implemented as weak references. In this way, the view
	 *  will be garbage collected without destroying the document. however we need
	 *  to make sure that the weak references self are cleaned regularly too.
	 * 
	 * @author Hans Dijkema
	 *
	 */
	class DXMLNoteMark extends XMLNoteMark {

		private WeakHashMap<Highlighter,Highlight>	_highlights;
		private Document 							_doc;
		private int									_offset;
		private int									_endOffset;
		private boolean								_offsetsChanged;

		// can return null, i.e. no associated Highlight (anymore).
		private Highlight getAHighlight() {
			Set<Highlighter> keys=_highlights.keySet();
			Iterator<Highlighter> it=keys.iterator();
			Highlight H=null;
			if (it.hasNext()) { H=_highlights.get(it.next()); }
			return H;
		}
		
		private void correctOffsets() {
			Highlight h=getAHighlight();
			if (h!=null) {
				int offset=h.getStartOffset();
				if (offset!=_offset) { 
					_offset=offset;
					_offsetsChanged=true;
				}
				int end_offset=h.getEndOffset();
				if (end_offset!=_endOffset) {
					_endOffset=end_offset;
					_offsetsChanged=true;
				}
			}
		}
		
		public String content() {
			try {
				return _doc.getText(offset(), endOffset()-offset());
			} catch (BadLocationException e) {
				DefaultXMLNoteErrorHandler.exception(e);
				return null;
			}
		}

		public Integer offset() {
			correctOffsets();
			return _offset;
		}
		
		public Integer endOffset() {
			correctOffsets();
			return _endOffset;
		}
		
		public boolean offsetInRange(int _offset) {
			return (_offset>=offset() && _offset<endOffset());
		}
		
		public boolean intersectsWith(int offset,int length) {
			int _offset=offset();
			int _end=endOffset();
			if ((offset>=_offset) && (offset<_end)) {
				return true;
			}
			int end=offset+length;
			if ((end>_offset) && (end<=_end)) {
				return true;
			}
			if (_offset>=offset && _end<=end) {
				return true;
			}
			return false;
		}
		
		public boolean offsetChanged() {
			return _offsetsChanged;
		}
		
		public void clearOffsetChanged() {
			_offsetsChanged=false;
		}
		
		public void associateHighlight(Highlighter h, Highlight hh) {
			_highlights.put(h, hh);
		}
		
		public Highlight removeHighlight(Highlighter h) {
			Highlight hh=_highlights.get(h);
			_highlights.remove(h);
			return hh;
		}
		
		public DXMLNoteMark(String id, String _class,Document d,int offset,int length) { 
			super(id,_class);
			_offset=offset;
			_endOffset=offset+length;
			_doc=d;
			_highlights=new WeakHashMap<Highlighter,Highlight>();
			_offsetsChanged=false;
		}
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Private variables
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Set<DocumentPreListener> 						_preListeners;
	private Set<DocumentListener>	 						_postListeners;
	private Set<DocumentAdminListener>						_adminListeners;
	private Set<XMLNoteClipboardListener>					_clipboardListeners;
	private DocumentPostListener     						_postListenerProxy;
	private Set<XMLNoteMarkListener> 						_markListeners;
	
	private XMLNoteStyles 									_styles;
	private String											_device;
	private Hashtable<String, DXMLNoteMark> 				_marks;
	private Vector<XMLNoteMark>								_orderedMarks;
	private WeakHashMap<Highlighter,MarkMarkupProviderMaker> _highlighters;
	private XMLNoteTranslator  								_translator;
	private XMLNoteUndoManager		  						_undoManager;
	private boolean											_blockAllListeners;
	private boolean											_changed;
	private Hashtable<String,Object>						_meta;
	private int												_clipboardIncludeMarks;
	private boolean											_allowMarksPasted;
	private XMLNoteMarkIdProvider							_markidReassigner;
	private boolean											_lastOperationVetoed;
	private XMLNoteImageIcon.Provider						_xmlnoteImageIconProvider;
	
	/** 
	 * Will block all listeners from emitting signals 
	 * 
	 * @param b
	 * @return
	 */
	private boolean setBlockAllListeners(boolean b) {
		boolean bb=_blockAllListeners;
		_blockAllListeners=b;
		return bb;
	}
	
	/**
	 * Returns the XMLNoteStyles associated with this document
	 * 
	 * @return
	 */
	public XMLNoteStyles getStyles() {
		return _styles;
	}


	/**
	 * This installs the highlighter from the associated view to
	 * this document. The highlighter is stored in the document as a
	 * weak reference, so when the highlighter is finalized, this 
	 * reference will be cleared.
	 * 
	 * precondition: highlighters for associated views will all be
	 * using this document for their document. So if you install
	 * a completely different document into the view, be sure to
	 * remove the highlighter from this document.
	 * 
	 * Next with the highlighter, it also installs a MarkMarkupProviderMaker 
	 * with this document. This makes it possible to let each view have it's
	 * own markup (underlined, different colour, or even different colour per markid)
	 * provider for a painter. 
	 * 
	 * The MarkMarkupProviderMaker is also a weak reference.
	 * 
	 * TODO: remove cleared weak references regularly from the internal WeakHashMap,
	 * although there probably won't be a lot.
	 * 
	 * @param h
	 */
	public void installHighlighterForView(Highlighter h,MarkMarkupProviderMaker p) {
		
		_highlighters.put(h,p);		

		// update the view to have all the highlights we know of.
		Enumeration<DXMLNoteMark> it=_marks.elements();
		while (it.hasMoreElements()) {
			DXMLNoteMark m=it.nextElement();
			try {
				MarkerHighlightPainter painter=new MarkerHighlightPainter(m,p.create(m.id(), m.markClass()));
				Highlight hh=(Highlight) h.addHighlight(m.offset(), m.endOffset(), painter);
				m.associateHighlight(h,hh);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Removes a previously added highlighter from this document and
	 * removes all highlighters from the marks that are associated with
	 * this highlighter.
	 *  
	 * @param h
	 */
	public void removeHighlighterForView(Highlighter h) {
		_highlighters.remove(h);
		
		// remove all Highlights from h and from all marks
		Enumeration<DXMLNoteMark> en=_marks.elements();
		while(en.hasMoreElements()) {
			DXMLNoteMark m=en.nextElement();
			Highlight hh=m.removeHighlight(h);
			h.removeHighlight(hh);
		}
	} 
	

	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Undo stuff.
	/////////////////////////////////////////////////////////////////////////////////////////////////


	/**
	 * Returns the undo manager associated with this document.
	 * @return
	 */
	public XMLNoteUndoManager getUndoManager() {
		return _undoManager;
	}
	
	/**
	 * Internal use.
	 */
	public void undoableEditHappened(UndoableEditEvent e) {
		if (!_undoManager.ignores()) {
			//System.out.println(e.toString());
			_undoManager.addEdit(e.getEdit());
			Iterator<UndoableEditListener> it=_undoableEditListeners.iterator();
			while(it.hasNext()) {
				it.next().undoableEditHappened(e);
			}
		}
	}
	
	private Set<UndoableEditListener> _undoableEditListeners;
	
	/**
	 * Overrides <code>addUndoableEditListener()</code>. Makes sure that when an undoable edit occurs, it is
	 * first added to the undoManager and then the other added undoableEditListners are called.
	 */
	public void addUndoableEditListener(UndoableEditListener l) {
		_undoableEditListeners.add(l);
	}
	
	/**
	 * Overrides <code>removeUndoableEditListener()</code>. Removes the undoable edit listener previously added.
	 */
	public void removeUndoableEditListener(UndoableEditListener l) {
		_undoableEditListeners.remove(l);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Listen for style changes and change the associated document accordingly.
	/////////////////////////////////////////////////////////////////////////////////////////////////


	/**
	 * This method works through all style changes, if the document styles
	 * have changed. The document styles are the styles used for paragraphs.
	 * When a paragraph style is changed in any way, in the associated document(s)
	 * this method is called. All XMLNoteDocuments will register themselves 
	 * automatically as listeners to style changes in the XMLNoteStyles container.
	 * 
	 * The algorithm is as follows:
	 * 
	 * this algorithm iterates over the paragraphs in the document. It
	 * gets the associated styleId and the associated align, and indent
	 * attributes (which can also be set on the paragraph through the 
	 * document). It will reapply each style to the document. and reapply
	 * the align/indents as well.  
	 */
	public void stylesChanged() {
		// iterate over the paragraph and reinstate the styles.
		boolean ui=_undoManager.setIgnore(true);
		boolean ba=setBlockAllListeners(true);
		boolean tl=_postListenerProxy.setBlocked(true); // Yeah, really block all, even the proxy itself
		
		Element root=this.getDefaultRootElement();
		int i,n;
		for(i=0,n=root.getElementCount();i<n;i++) {
			Element paragraph=root.getElement(i);

			AttributeSet set=paragraph.getAttributes();

			int align=StyleConstants.getAlignment(set);
			float indent=StyleConstants.getLeftIndent(set);
			String styleId=XMLNoteStyleConstants.getId(set);
			//int offset=paragraph.getStartOffset();
				
			XMLNoteParStyle st=_styles.parStyle(styleId);
			if (st==null) { 
				try {
					st=_styles.getDefaultStyle();
				} catch (BadStyleException e) {
					DefaultXMLNoteErrorHandler.exception(e);
					st=null;
				} 
			}
			if (st!=null) {
				MutableAttributeSet nst=st.getStyle(_device);
				StyleConstants.setAlignment(nst,align);
				StyleConstants.setLeftIndent(nst, indent);
				super.setParagraphAttributes(paragraph.getStartOffset(),0,nst,true);
			}
			
		}
			
		_undoManager.setIgnore(ui);
		setBlockAllListeners(ba);
		_postListenerProxy.setBlocked(tl); // Yeah, really block all, even the proxy itself
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Meta information.
	/////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * @return the value of the stored meta key as a string
	 * @throws BadMetaException 
	 */
	public String metaValue(String key) throws BadMetaException {
		return getMeta(key).toString();
	}
	
	/**
	 * This method returns the name type of the type of the value stored or a meta key. This is one of:
	 * <ul>
	 * <li>String</li>
	 * <li>Integer</li>
	 * <li>Float</li>
	 * <li>Double</li>
	 * <li>Boolean</li>
	 * <li>Date</li>
	 * </ul>
	 *  
	 * @return the name of the type of the value stored for a meta key 
	 * @throws BadMetaException 
	 */
	public String metaType(String key) throws BadMetaException {
		return getMeta(key).getClass().getName().replaceAll(".*[.]","");
	}
	
	/**
	 * Returns an iterator for all meta keys. 
	 * 
	 * @return iterator for all meta keys
	 */
	public Iterator<String> metaKeys() {
		Set<String> keys=_meta.keySet();
		return keys.iterator();
	}
	
	/**
	 * Sets a meta key from String values, given a certain type. This method is mainly used by <code>XMLNoteXMLIn</code>.  
	 * 
	 * @param key			The meta key
	 * @param type			The type of the value 
	 * @param value			The value as String 
	 * @throws BadMetaException
	 */
	public void setMeta(String key,String type,String value) throws BadMetaException {
		if (type.equals("Integer")) { setMeta(key,Integer.parseInt(value)); }
		else if (type.equals("String")) { setMeta(key,value); }
		else if (type.equals("Float")) { setMeta(key,Float.parseFloat(value)); }
		else if (type.equals("Double")) { setMeta(key,Double.parseDouble(value)); }
		else if (type.equals("Boolean")) { setMeta(key,Boolean.parseBoolean(value)); }
		else if (type.equals("Date"))    { setMeta(key,Date.parseDate(value)); }
		else { 
			String msg=_translator.translate("Cannot interpret type '%s' (key = '%s')");
			throw new BadMetaException(String.format(msg, type,key));
		}
	}
	
	/**
	 * Sets a meta key to an integer value.
	 * 
	 * @param key 		The meta key
	 * @param value 	The integer value.
	 */
	public void setMeta(String key,int value) {
		_meta.put(key, (Integer) value);
	}
	
	/**
	 * Sets a meta key to a String value. Clones the String.
	 * @param key 		The meta key 
	 * @param value 	The value to set (may not be null)
	 * @throws BadMetaException
	 */
	public void setMeta(String key,String value) throws BadMetaException {
		if (value==null) { 
			throw new BadMetaException("value may not be null");
		}
		_meta.put(key, new String(value));
	}
	
	/**
	 * Sets a meta key to a boolean value.
	 * @param key		The meta key
	 * @param value 	The value to set
	 */
	public void setMeta(String key,boolean value) {
		_meta.put(key, (Boolean) value);
	}
	
	/**
	 * Sets a meta key to a float value.
	 * @param key		The meta key
	 * @param value 	The value to set
	 */
	public void setMeta(String key,float value) {
		_meta.put(key, (Float) value);
	}
	
	/**
	 * Sets a meta key to a double value.
	 * @param key 		The meta key
	 * @param value 	The value to set
	 */
	public void setMeta(String key,double value) {
		_meta.put(key, (Double) value);
	}
	
	
	/**
	 * Sets a meta key to a java.util.Date value. Clones the date.
	 * @param key		The meta key
	 * @param value		The date value
	 */
	public void setMeta(String key,java.util.Date value) {
		_meta.put(key, new Date(value));
	}
	
	/**
	 * Returns if a given key exists as meta key.
	 * @param key The key to be found.
	 * @return true, if found; false, otherwise
	 */
	public boolean metaExists(String key) {
		return _meta.containsKey(key);
	}
	
	private Object getMeta(String key) throws BadMetaException {
		Object o=_meta.get(key);
		if (o==null) {
			String msg=_translator.translate("There is no meta key '%s' in this document");
			throw new BadMetaException(String.format(msg,key));
		}
		return o;
	}
	
	private void throwTypeException(String key,String type) throws BadMetaException {
		String msg=_translator.translate("The value of key '%s' is not of type '%s'");
		throw new BadMetaException(String.format(msg,key,type));
	}
	
	/**
	 * Returns an integer value associated with key. If key doesn't exist, throws BadMetaException.
	 * If value isn't of type 'int', throws BadMetaException.
	 * 
	 * @param key The key to be read.
	 * @return The Integer to be read
	 * @throws BadMetaException
	 */
	public int getIntMeta(String key) throws BadMetaException {
		Object o=getMeta(key);
		if (o instanceof Integer) {
			return (Integer) o;
		} else {
			throwTypeException(key,"int");
		}
		return 0;
	}
	
	/**
	 * Returns a String value associated with key. If key doesn't exist, throws BadMetaException.
	 * If value isn't of type 'String', throws BadMetaException.
	 * 
	 * @param key The key to be read.
	 * @return The String to be read
	 * @throws BadMetaException
	 */
	public String getStringMeta(String key) throws BadMetaException {
		Object o=getMeta(key);
		if (o instanceof String) {
			return (String) o;
		} else {
			throwTypeException(key,"String");
		}
		return null;
	}

	/**
	 * Returns a boolean value associated with key. If key doesn't exist, throws BadMetaException.
	 * If value isn't of type 'boolean', throws BadMetaException.
	 * 
	 * @param key The key to be read.
	 * @return The Float result
	 * @throws BadMetaException
	 */
	public boolean getBooleanMeta(String key) throws BadMetaException {
		Object o=getMeta(key);
		if (o instanceof Boolean) {
			return (Boolean) o;
		} else {
			throwTypeException(key,"boolean");
		}
		return false;
	}
	
	/**
	 * Returns a float value associated with key. If key doesn't exist, throws BadMetaException.
	 * If value isn't of type 'float', throws BadMetaException.
	 * 
	 * @param key The key to be read.
	 * @return The float result
	 * @throws BadMetaException
	 */
	public float getFloatMeta(String key) throws BadMetaException {
		Object o=getMeta(key);
		if (o instanceof Float) {
			return (Float) o;
		} else {
			throwTypeException(key,"float");
		}
		return 0.0f;
	}
	
	/**
	 * Returns a double value associated with key. If key doesn't exist, throws BadMetaException.
	 * If value isn't of type 'double', throws BadMetaException.
	 * @param key	The key to be read
	 * @return		The double result
	 * @throws BadMetaException
	 */
	public double getDoubleMeta(String key) throws BadMetaException {
		Object o=getMeta(key);
		if (o instanceof Double) {
			return (Double) o;
		} else {
			throwTypeException(key,"double");
		}
		return 0.0;
	}
	

	/**
	 * Returns a java.util.Date value associated with key. If key doesn't exist, throws BadMetaException.
	 * If value isn't of type 'double', throws BadMetaException.
	 * @param key	The key to be read
	 * @return		The double result
	 * @throws BadMetaException
	 */
	public java.util.Date getDateMeta(String key) throws BadMetaException {
		Object o=getMeta(key);
		if (o instanceof Date) {
			return ((Date) o).dt();
		} else {
			throwTypeException(key,"Date");
		}
		return null;
	}
	
	/**
	 * Removes a key/value from the meta information of this document.
	 * 
	 * @param key					The key to remove
	 * @throws BadMetaException		Thrown when the given key doesn't exist as meta key
	 */
	public void removeMeta(String key) throws BadMetaException {
		getMeta(key);
		_meta.remove(key);
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Altering the document contents.
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Is used to start and end a long edit. During a long edit, the undo manager of XMLNoteDocument will
	 * collect all edits as one undoable transaction. Returns the previous value of the long edit flag.
	 * This makes it possible to call this function multiple times in a recursive fashion.
	 * <p>
	 * @return the previous value of the long edit flag.
	 */
	public boolean setLongEdit(boolean newFlag) {
		return _undoManager.setLongEdit(newFlag);
	}
	
	/**
	 * Returns true if the last operation was vetoed or not. Call this function immediately after an
	 * operation that can be vetoed (like <code>remove()</code> or <code>insertString()</code>).
	 * <p>
	 * @return true, if the last operation was vetoed. 
	 */
	public boolean operationVetoed() {
		return _lastOperationVetoed;
	}
	
	/** 
	 * Returns whether this document has been changed. <b>true</b> means the document has changed.
	 */
	public boolean changed() {
		return _changed;
	}
	
	/**
	 * Resets the changed state to false.
	 */
	public void resetChanged() {
		boolean b=_changed;
		_changed=false;
		if (b!=_changed) {
			fireDocumentChangeEvent();
		}
	}
	
	/**
	 * Resets the changed state to <code>newc</code> and returns the previous state.
	 * 
	 * @param newc
	 * @return
	 */
	public boolean setChanged(boolean newc) {
		/*try {
			if (newc) { 
				throw new Exception("Document set to changed");
			}
		} catch (Exception E) {
			E.printStackTrace();
		}*/
		boolean b=_changed;
		_changed=newc;
		if (b!=newc) {
			fireDocumentChangeEvent();
		}
		return b;
	}
	
	private void fireDocumentChangeEvent() {
		Iterator<DocumentAdminListener> it=_adminListeners.iterator();
		DocumentAdminEvent e=new DocumentAdminEvent(this);
		while (it.hasNext()) {
			it.next().documentChangedState(e);
		}
	}
	
	
	
	/**
	 * Applies the given style to the paragraph(s) that are in range of 'offset, offset+length'.
	 * 
	 * @param styleId
	 * @param offset
	 * @param length
	 * @throws NoStyleException
	 * @throws BadLocationException
	 */
	public void applyStyle(String styleId, int offset, int length)
			throws NoStyleException, BadLocationException {
		XMLNoteParStyle st = _styles.parStyle(styleId);
		if (st == null) {
			throw new NoStyleException(styleId);
		}
		Element oe = super.getParagraphElement(offset);
		Element ee = super.getParagraphElement(offset + length);
		offset = oe.getStartOffset();
		length = ee.getEndOffset() - offset;

		setParagraphAttributes(offset, length, st.getStyle(_device), true);
	}
	
	/**
	 * Applies the given alignment to the paragraph(s) that are in range of 'offset, offset+length'.
	 * This method adds the given alignment to the style of the paragraph. If it is the same as the style of
	 * the paragraph, effectually it doesn't do anything. If the alignment differs, it results in an added
	 * alignment, that is reflected in the XML of the document (<code>toXML()</code>).
	 * 
	 * <code>alignment</code> must be one of StyleConstants.ALIGN_LEFT,ALIGN_RIGHT,ALIGN_CENTER or ALIGN_JUSTIFIED.
	 * @param alignment
	 * @param offset
	 * @param length
	 * @throws BadLocationException
	 */
	public void applyAlign(int alignment, int offset,int length) throws BadLocationException {
		Element oe = super.getParagraphElement(offset);
		Element ee = super.getParagraphElement(offset + length);
		offset = oe.getStartOffset();
		length = ee.getEndOffset() - offset;
		SimpleAttributeSet set=new SimpleAttributeSet();
		StyleConstants.setAlignment(set, alignment);

		setParagraphAttributes(offset, length, set, false);
	}
	
	/**
	 * Indent more. Indents the paragraph for the given offset more with the given points. 
	 * 
	 * @param offset	The offset in the document
	 * @param points	The number of points (1/72 inch)
	 * @throws BadLocationException 
	 */
	public void indentMore(int offset,int length,int points) throws BadLocationException {
		Element el=this.getParagraphElement(offset);
		AttributeSet set=el.getAttributes();
		int indent=(int) StyleConstants.getLeftIndent(set);
		indent+=points;
		applyIndent(indent,offset,length);
	}
	
	/**
	 * Indent less. Indents the paragraph for the given offset less with the given points.
	 * 
	 * @param offset	The offset in the document
	 * @param points	The number of points (1/72 inch)
	 * @throws BadLocationException
	 */
	public void indentLess(int offset,int length,int points) throws BadLocationException {
		Element el=this.getParagraphElement(offset);
		AttributeSet set=el.getAttributes();
		int indent=(int) StyleConstants.getLeftIndent(set);
		indent-=points;
		if (indent<0) { indent=0; }
		applyIndent(indent,offset,length);
	}
	
	/**
	 * Applies the given indentation (in points, i.e. indentInPoints/72 inch) that are in range of 'offset, offset+length'.
	 * This method adds the given alignment to the style of the paragraph. If it is the same as the style of
	 * the paragraph, effectually it doesn't do anything. If the alignment differs, it results in an added
	 * alignment, that is reflected in the XML of the document (<code>toXML()</code>).
	 * 
	 * @param indentInPoints
	 * @param offset
	 * @param length
	 * @throws BadLocationException
	 */
	public void applyIndent(int indentInPoints,int offset,int length) throws BadLocationException {
		Element oe = super.getParagraphElement(offset);
		Element ee = super.getParagraphElement(offset + length);
		offset = oe.getStartOffset();
		length = ee.getEndOffset() - offset-1;
		SimpleAttributeSet set=new SimpleAttributeSet();
		StyleConstants.setLeftIndent(set, (float) indentInPoints);

		setParagraphAttributes(offset, length, set, false);
	}

	/**
	 * Applies attribute set with the characters in the offset until
	 * offset+length The character at offset is leading for the way to apply the
	 * style. If this character already has the given style, it will be removed
	 * from the selection, otherwise, it will be added to the selection
	 * 
	 * precondition: AttributeSet has only 1 attribute
	 * 
	 * @param set
	 * @param offset
	 * @param length
	 * @throws BadLocationException
	 */
	public void applyOneCharStyle(SimpleAttributeSet set, int offset, int length)
			throws BadStyleException, BadLocationException {
		int i,k;
		boolean add = true;
		if (set.getAttributeCount() > 1) {
			throw new BadStyleException(
					"Only one character attribute can be set per time with this method");
		}

		{
			Element e = super.getCharacterElement(offset);
			AttributeSet eset = e.getAttributes();
			if (eset.containsAttributes(set)) {
				add = false;
			}
		}

		boolean le=_undoManager.setLongEdit(true);
		SimpleAttributeSet tset=null;
		for (i = 0, k = 0; i < length; i++) {
			Element e = super.getCharacterElement(offset + i);
			if (tset==null) { tset=new SimpleAttributeSet(e.getAttributes()); }
			SimpleAttributeSet eset = new SimpleAttributeSet(e.getAttributes());
			if (!eset.equals(tset)) { 
				if (add) {
					tset.addAttributes(set);
				} else {
					tset.removeAttributes(set);
				}
				setCharacterAttributes(offset + k, i-k, tset, true);
				tset=eset;
				k=i;
			}
		}
		if (i!=k) {
				if (add) {
					tset.addAttributes(set);
				} else {
					tset.removeAttributes(set);
				}
				setCharacterAttributes(offset + k, i-k, tset, true);
		}
		_undoManager.setLongEdit(le);
	}
	

	/**
	 * Clears the document and all it's markers. Generates one DocumentAdminEvent before- and after the
	 * clearing. Further, it doesn't emit any signals to listeners.
	 *  
	 * This operation can be vetoed by a <code>DocumentAdminListener</code>. 
	 * 
	 * Note! external administrations to this Document (e.g. with mark ids etc) may need to be cleared
	 * as well.
	 * 
	 * Note! <code>resetFromXML()</code> will also clear the document, but this won't result in any
	 * DocumentAdminListener events for the clearing of the document, only the reset will be signaled. 
	 * 
	 * A clear() operation cannot be undone and the <code>changed()</code> flag has been reset. 
	 */
	public void clear() {
		Iterator<DocumentAdminListener> dit;
		DocumentAdminEvent e;
		if (!_blockAllListeners) {
			e=new DocumentAdminEvent(this);
			dit=_adminListeners.iterator();
			boolean veto=false;
			while(dit.hasNext()) {
				boolean b=dit.next().documentWillBeCleared(e);
				if (b) { veto=true; }
			}
			_lastOperationVetoed=veto;
			if (veto) { return; }
		}

		boolean ignore=_undoManager.setIgnore(true);
		boolean ba=setBlockAllListeners(true);
		boolean tl=_postListenerProxy.setBlocked(true); // Yeah, really block all, even the proxy itself
		
		Vector<String> markIds=this.getMarkIds();
		Iterator<String> it=markIds.iterator();
		while (it.hasNext()) {
			try {
				this.removeMark(it.next());
			} catch (MarkNoExistException e1) {
				e1.printStackTrace();
			}
		}
			
		final int end=super.getLength();
		try {
			this.remove(0, end);
		} catch (BadLocationException ee) {
			ee.printStackTrace();
		}
		_undoManager.setIgnore(ignore);
		_undoManager.discardAllEdits();
		
		setBlockAllListeners(ba);
		_postListenerProxy.setBlocked(tl);	// Yeah, really, even block the proxy!
		
		setChanged(false);
		
		if (!_blockAllListeners) {
			e=new DocumentAdminEvent(this);
			dit=_adminListeners.iterator();
			while(dit.hasNext()) {
				dit.next().documentHasBeenCleared(e);
			}
		}
	}
	
	/**
	 * Resets the contents of this document from an other document. It will <b>not</b>
	 * reset the associated styles also! It will keep using the styles associated.'
	 * <p>
	 * This method will not fire any <code>DocumentPreListener</code> or <code>DocumentListener</code>
	 * or <code>XMLNoteMarkListener</code> events. It only will fire a <code>DocumentWillBeReset()</code>
	 * and <code>DocumentHasBeenReset()</code> event. Inserted XMLNoteMarks are thus not signalled to
	 * any listener. External administrations on XMLNoteMarks must be synced manually. 
	 * <p> 
	 * @param doc
	 * @throws BadStyleException
	 */
	public void resetFromDocument(XMLNoteDocument doc) throws BadStyleException {
		// May we reset this document from a new Document?
		boolean veto=false;
		Iterator<DocumentAdminListener> it=_adminListeners.iterator();
		DocumentAdminEvent e=new DocumentAdminEvent(this);
		while (it.hasNext()) {
			boolean b=it.next().documentWillBeReset(e);
			if (b) { veto=b; }
		}
		_lastOperationVetoed=veto;
		if (veto) { return; }

		// block listeners
		boolean i=_undoManager.setIgnore(true);
		boolean bl=setBlockAllListeners(true);
		boolean tl=_postListenerProxy.setBlocked(true);

		// perform action
		clear();
		doc.setMeta("startsWithParagraph", false);
		try {
			copyInto(doc, 0, true);
		} catch (MarkExistsException e1) {		// this is not expected, as the current document has been cleared. 
			DefaultXMLNoteErrorHandler.exception(e1);
		}

		// unblock listeners
		_undoManager.setIgnore(i);
		setBlockAllListeners(bl);
		_postListenerProxy.setBlocked(tl);
		_undoManager.discardAllEdits();

		setChanged(false);

		it=_adminListeners.iterator();
		e=new DocumentAdminEvent(this);
		while (it.hasNext()) {
			it.next().documentHasBeenReset(e); 
		}
	}
	
	
	/**
	 * Returns true, if the character is whitespace according to java and also if it is non-breakable
	 * whitespace (i.e. stuff that takes in space, but is invisible otherwise).
	 * @param c the character to test
	 * @return true, if whitespace || non-breakable whitespace.
	 */
	public static final boolean isWhiteSpaceInclNonBreakable(char c) {
		return Character.isWhitespace(c) || (c=='\u00A0') || (c=='\u2007') || (c=='\u202F');
	}
	
	/**
	 * Trims the document from the beginning and from the end. Removes any white spaced paragraphs.
	 * 
	 * This method may result in two remove events and possibly XMLNoteMarks that get removed.
	 * The removes can be vetoed using a DocumentPreChangeListener
	 * 
	 */
	public void trim() {
		Segment seg=new Segment();
		seg.setPartialReturn(true);
		try {
			if (this.getLength()>0) {
				this.getText(0, this.getLength(),seg);
				int i,n;
				for(i=seg.length()-1;i>=0 && isWhiteSpaceInclNonBreakable(seg.charAt(i));i--);
				if (i<0) {
					this.remove(0, this.getLength());
				} else {
					i+=1;
					int len=this.getLength()-i;
					if (len>0) { this.remove(i,len); }

					this.getText(0, this.getLength(),seg);
					for(i=0,n=seg.length();i<n && isWhiteSpaceInclNonBreakable(seg.charAt(i));i++);
					if (i>0) {
						this.remove(0, i);
					}
				}
			}
		} catch (BadLocationException e) {
			DefaultXMLNoteErrorHandler.exception(e);
		}
	}
	
	/**
	 * Returns whether the paragraph Element with the given index in the root Element
	 * of this document (root=this.getDefaultRootElement()), is full of whitespace
	 * (including non breakable whitespace).
	 *  
	 * @param paragraphIndexInRootElement
	 * @return
	 */
	public boolean isWhiteSpaceParagraph(int paragraphIndexInRootElement) {
		Element root=this.getDefaultRootElement();
		Element par=root.getElement(paragraphIndexInRootElement);
		Segment seg=new Segment();
		int start=par.getStartOffset();
		int end=par.getEndOffset();
		try {
			this.getText(start,end-start,seg);
			int i,n;
			for(i=0,n=seg.length();i<n && isWhiteSpaceInclNonBreakable(seg.charAt(i));i++);
			return i==n;
		} catch (BadLocationException e) {
			// unexpected
			DefaultXMLNoteErrorHandler.exception(e);
			return false;
		}
	}
	
	
	/**
	 * This method copies an existing XMLNoteDocument into the current document, including all styles and
	 * attributes. With or without the marks it already has. One must make sure that existing marks do not 
	 * have the same id as the marks in the existing document.
	 * <p>
	 * Two meta key parameters of type <b>boolean</b> apply:
	 * <p>
	 * <table>
	 * <tr><td><b>startsWithParagraph</b></td><td><ul><li>If set to false, the document will be added, while not creating a new paragraph
	 * where the text begins. So if the text begins in the middle of a paragraph, that paragraph will be split in two and the 
	 * document will be inserted without a paragraph break at the offset.</li><li>If true, a new paragraph will be started at the offset.</li> 
	 * <li>If not existing, <b>true</b> will be assumed.</li></ul></td></tr>
	 * <tr><td><b>endsWithParagraph</b></td><ul><li>If set to false, the document will be added, while not inserting a paragraph break at the end of 
	 * the inserted document. So, if the text begins in the middle of a paragraph, that paragraph will be split in two and the document will
	 * be inserted. At the end, without a paragraph break, the end of the document will continue into the second half of the spit paragraph.</li>
	 * <li>If true, a paragraph break will be inserted at the end of the inserted document.</li>
	 * <li>If not existing as a meta parameter of the document, <b>true</b> will be assumed.</li><ul></td></tr>
	 * </table> 
	 * <p>
	 * This method will first fire a <code>DocumentPreListener.insertUpdate()</code> event on the current document, which will
	 * provide the ability to veto the <code>copyInto()</code> operation. After this operation, it will
	 * <p>
	 * This method returns a vector with copies of the XMLNoteMarks contained in the <code>source</code>, with the offsets completely
	 * adjusted to the locations in this document. Whether they are added or not, it doesn't matter.
	 * <p>
	 * If the operation is vetoed, null is returned.
	 * <p>
	 * @param source				The source document
	 * @param offset				The offset where 
	 * @param includeMarks			Tells whether the marks of the source document must also be inserted in the given document.
	 * @throws BadStyleException	Thrown when getDefaultStyle() of the current document throws BadStyleException.
	 * @throws MarkExistsException	Thrown when the id of a mark of the source already exists in the current document.
	 * @return Copies of the XMLNoteMarks of the source XMLNoteDocument, that may or may not have been pasted into this document, with adjusted offsets for this document.
	 *    
	 */
	public Vector<XMLNoteMark> copyInto(final XMLNoteDocument source,final int offset,boolean includeMarks) throws BadStyleException, MarkExistsException {

		// If this operation is vetoed, return immediately
		DocumentEvent e;
		try {
			e = DocumentPreEvent.insertEvent(this, offset, source.getText(0, source.getLength()), null);
			if (vetoPreUpdate(e)) { return null; }
		} catch (BadLocationException e1) {
			// This is an unexpected exception
			DefaultXMLNoteErrorHandler.exception(e1);
		}
		
		Vector<XMLNoteMark> adjustedMarks=new Vector<XMLNoteMark>();
		boolean le=_undoManager.setLongEdit(true);
			
		try {
			boolean startsWithParagraph=true;
			if (source.metaExists("startsWithParagraph")) { 
				startsWithParagraph=source.getBooleanMeta("startsWithParagraph");
			}
			boolean endsWithParagraph=true;
			if (source.metaExists("endsWithParagraph")) {
				endsWithParagraph=source.getBooleanMeta("endsWithParagraph");
			}
			
			// Thankfully reusing the code of Gungor Senyurt
			
			ContentTree content=new ContentTree();
			
			// Add an empty paragraph if startsWithParagraph = true (because insertContent() is called with
			// join = true always.
			if (startsWithParagraph) { 
				content.startNode(null,AbstractDocument.ParagraphElementName);
				content.endNode();
			}
			
			// Handle document
			Element root=source.getDefaultRootElement();
			// Handle paragraphs
			int i,n;
			for(i=0,n=root.getElementCount();i<n;i++) {
				
				// Get the paragraph
				Element paragraph=root.getElement(i);
				int soffset=paragraph.getStartOffset();
				int eoffset=paragraph.getEndOffset();
				
				if (i==n-1 && !endsWithParagraph && (soffset+1)==eoffset) {  // paragraphs end with \n
					// do nothing
				} else {

					// Get the style of the paragraph
					AttributeSet set=paragraph.getAttributes();
					String styleId=XMLNoteStyleConstants.getId(set);
					XMLNoteParStyle st=this.getStyles().parStyle(styleId);
					if (st==null) { st=this.getStyles().getDefaultStyle(); }
					//Style S=st.getStyle(_device);
					AttributeSet S=st.getStyle(_device);

					// Add indent and alignment
					SimpleAttributeSet parSet=new SimpleAttributeSet(S);
					StyleConstants.setAlignment(parSet, StyleConstants.getAlignment(set));
					StyleConstants.setLeftIndent(parSet, StyleConstants.getLeftIndent(set));

					// Start the paragraph
					content.startNode(parSet, AbstractDocument.ParagraphElementName);

					// Iterate over the elements in the paragraph
					int j,m;
					for(j=0,m=paragraph.getElementCount();j<m;j++) {
						Element cont=paragraph.getElement(j);
						SimpleAttributeSet cset=new SimpleAttributeSet(cont.getAttributes());
						int start=cont.getStartOffset();
						int len=cont.getEndOffset()-start;
						String txt=source.getText(start, len);
						if (j==m-1) { // strip the '\n' from the end of a paragraph, to correct behaviour of insertContent.
							// insertContent will add the '\n'.
							txt=txt.substring(0,len-1);
						}
						content.addContent(cset,txt);
					}

					// End the paragraph
					content.endNode();
				}
			}

			// Add an empty paragraph if endsWithParagraph = true (because insertContent() is called with
			// join = true always.
			if (endsWithParagraph) { 
				content.startNode(null,AbstractDocument.ParagraphElementName);
				content.endNode();
			}
			
			// insert the content into the document
			insertContent(offset,content,true);
			
			// fire an insert event after the content has changed
			// NOT NECESSARY. DefaultStyledDocument.insert() will fire an insert update itself.
			//DocumentEvent ie=new DocumentEvent() {
			//	public ElementChange getChange(Element e) 	{ return null; }
			//	public Document getDocument() 				{ return XMLNoteDocument.this; }
			//	public int getLength() 						{ return source.getLength(); } 
			//	public int getOffset() 						{ return offset; }
			//	public EventType getType()					{ return DocumentEvent.EventType.INSERT; }
			//};
			//super.fireInsertUpdate(ie);
			
			// Insert marks of the source document if asked.
			
			Vector<XMLNoteMark> sourceMarks=source.getOrderedMarks();
			Iterator<XMLNoteMark> it=sourceMarks.iterator();
			while(it.hasNext()) {
				XMLNoteMark m=it.next();
				int mOffset=m.offset();
				int mEndOffset=m.endOffset();
				String id=m.id();
				String _cl=m.markClass();
				
				AdjustedMark adjustedMark=new AdjustedMark(id,_cl,mOffset+offset,mEndOffset+offset);
				adjustedMarks.add(adjustedMark);
				
				if (includeMarks) {
					this.insertMark(id, _cl,mOffset+offset, mEndOffset-mOffset);
				}
			}
			
		} catch (BadMetaException E) {
			DefaultXMLNoteErrorHandler.exception(E);
		} catch (BadLocationException E) {
			DefaultXMLNoteErrorHandler.exception(E);
		}
		
		_undoManager.setLongEdit(le);
		
		return adjustedMarks;
	}
	
	/**
	 * Appends a paragraph to the document. Useful when creating documents. If styleId cannot be found,
	 * it will apply the default paragraph style. If no default style exists, NoStyleException or
	 * BadStyleException is thrown.
	 * 
	 * If <code>align!=null</code>, it will be applied using <code>applyAlign()</code>.
	 * If <code>indent!=null</code>, it will be applied using <code>applyIndent()</code>.
	 * 
	 * @param par
	 * @param styleId
	 * @param align
	 * @param indent
	 * @throws BadLocationException
	 * @throws BadStyleException 
	 * @throws NoStyleException
	 */
	public void addParagraph(String par,String styleId,Integer align,Integer indent) 
						throws BadLocationException, NoStyleException, BadStyleException {
		boolean le=_undoManager.setLongEdit(true);
		int offset=this.getLength();
		this.insertString(offset,par,null);
		try {
			this.applyStyle(styleId, offset, par.length());
		} catch (NoStyleException e) {
			this.applyStyle(_styles.getDefaultStyle().id(),offset,par.length());
		}
		if (align!=null) {
			applyAlign(align,offset,par.length());
		}
		if (indent!=null) {
			applyIndent(indent,offset,par.length());
		}
		_undoManager.setLongEdit(le);
	}
	
	/**
	 * Shortcut for addParagraph(par,styleId,null,null).
	 * 
	 * @param par
	 * @param styleId
	 * @throws BadLocationException
	 * @throws NoStyleException
	 * @throws BadStyleException
	 */
	public void addParagraph(String par,String styleId) throws BadLocationException, NoStyleException, BadStyleException {
		addParagraph(par,styleId,null,null);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Image support
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final XMLNoteImageIcon.Provider nullImageProvider=new XMLNoteImageIcon.Provider() {
		public XMLNoteImageIcon getIcon(String id) {
			return null;
		}

		public XMLNoteImageIcon getIcon(URL url, String description,XMLNoteImageIconSize size) {
			return null;
		}
	};
	
	private static boolean 				globalImageSupport=true;
	private XMLNoteImageIcon.Provider 	previousProvider=null;

	/**
	 * Sets the support for Images for this document.
	 * 
	 * @param yesno 	if <b>true</b>, image support is enabled, otherwise a default XMLNoteImageIcon.Provider is installed that returns null for all image requests.
	 * 					
	 */
	public void setImageSupport(boolean yesno) {
		if (yesno) {
			setXMLNoteImageIconProvider(previousProvider);
			previousProvider=null;
		} else {
			XMLNoteImageIcon.Provider prov=setXMLNoteImageIconProvider(nullImageProvider);
			if (prov!=nullImageProvider) {
				previousProvider=prov;
			}
		}
	}
	
	/**
	 * Sets the global support for Images for all documents. This can be overridden by individual
	 * calls to <code>imageSupport()</code>. If this is set to <b>false</b>, all XMLNoteDocument instances will default to
	 * <code>XMLNoteImageIcon.Provider</code> that returns <b>null</b> for all image requests. 
	 * 
	 * @param yesno	if <b>true</b>, XMLNoteDocument instances default to image support. If <b>false</b> they don't.
	 */
	public static void setGlobalImageSupport(boolean yesno) {
		globalImageSupport=yesno;
	}

	/**
	 * Inserts XMLNoteImageIcon icon at the given offset.
	 * 
	 * @param offset  The offset in the text.
	 * @param icon    The icon given by XMLNoteImageIcon
	 * @throws BadLocationException
	 */
	public void insertImage(int offset,XMLNoteImageIcon icon) throws BadLocationException, BadOperationException {
		SimpleAttributeSet set=new SimpleAttributeSet();
		StyleConstants.setIcon(set, icon);
		if (icon==null) {
			throw new BadOperationException("The given icon to insert icon=null");
		}
		this.insertString(offset, "i", set);
	}
	
	/**
	 * Inserts a new XMLNoteImageIcon at the given offset. It will construct the XMLNoteImageIcon from the
	 * given parameters. If an XMLNoteImageIcon.Provider has been set on the document, this will be called to 
	 * get the XMLNoteImageIcon based on the url, description and size; otherwise, this method will
	 * get the icon directly from the url.
	 * <p>
	 * Note: if the XMLNoteImageIcon.Provider function returns <b>null</b>, no image will be inserted.
	 * <p>
	 * @param offset		The offset in the text
	 * @param imageUrl		The URL of the image
	 * @param description	The description to use for the image
	 * @param sizeInPoints	The size (Dimension) in points (1/72 inch). Determines the width and height.
	 * @throws BadLocationException
	 * @throws BadOperationException 
	 */
	public void insertImage(int offset,URL imageUrl,String description,XMLNoteImageIconSize size) throws BadLocationException, BadOperationException {
		XMLNoteImageIcon img;
		if (getXMLNoteImageIconProvider()!=null) {
			img=getXMLNoteImageIconProvider().getIcon(imageUrl,description,size);
			if (img==null) { return; }
		} else {
			img=new XMLNoteImageIcon(imageUrl,description,size);
		}
		insertImage(offset,img);
	}
	
	/*
	 * Inserts a new XMLNoteImageIcon at the given offset. It will construct this icon from the given parameters.
	 * If an XMLNoteImageIcon.Provider has been set on the document, this will be called to 
	 * get the XMLNoteImageIcon based on the url, description and width; otherwise, this method will
	 * get the icon directly from the url.
	 * <p>
	 * Note: if the XMLNoteImageIcon.Provider function returns <b>null</b>, no image will be inserted.
	 * <p>
	 * @param offset		The offset in the text
	 * @param imageUrl		The URL of the image
	 * @param description	The description to use for the image
	 * @param widthInPt		The width in points (1/72 inch) to use for this image. height will be determined automatically.
	 * @throws BadLocationException
	 */
	/*
	public void insertImage(int offset,URL imageUrl,String description,int widthInPt) throws BadLocationException {
		XMLNoteImageIcon icon;
		if (getXMLNoteImageIconProvider()!=null) {
			icon=getXMLNoteImageIconProvider().getIcon(imageUrl,description,widthInPt);
		} else {
			icon=new XMLNoteImageIcon(imageUrl,description,widthInPt);
		}
		insertImage(offset,icon);
	}*/
	
	/**
	 * Sets the current XMLNoteImageIcon Provider. If prov==null, the current provider will be cleared.
	 * Returns the previous one.
	 * @param prov
	 * @return 
	 */
	public XMLNoteImageIcon.Provider setXMLNoteImageIconProvider(XMLNoteImageIcon.Provider prov) {
		XMLNoteImageIcon.Provider prev=_xmlnoteImageIconProvider;
		_xmlnoteImageIconProvider=prov;
		return prev;
	}
	
	/**
	 * 
	 * @return The current XMLNoteImageIcon.Provider. This will be null, if no XMLNoteImageIcon.Provider has
	 * been set with setXMLNoteImageIconProvider().
	 */
	public XMLNoteImageIcon.Provider getXMLNoteImageIconProvider() {
		return _xmlnoteImageIconProvider;
	}
	
	/**
	 * Inserts an image based on a given id. This only works if an XMLNoteImageIcon.Provider has been
	 * set on this document using <code>setXMLNoteImageIconProvider()</code>.
	 * <p>
	 * Note: XMLNoteImageIcon.Provider implementations must set the Id of an XMLNoteImageIcon. Only using this way,
	 * the XMLNoteDocument serializers will reload the image via the provider on the document.
	 * <p>
	 * Note2: if the image returned by the XMLNoteImageIcon.Provider = <b>null</b>, no image will be inserted.
	 * <p> 
	 * @param offset		the offset in the text
	 * @param id			the id for which the icon will be created.
	 * @throws BadLocationException
	 * @throws BadOperationException
	 */
	public void insertImage(int offset,String id) throws BadLocationException,BadOperationException {
		if (_xmlnoteImageIconProvider==null) {
			throw new BadOperationException(
					         _translator._(
					        		 "You must set a XMLNoteImageIcon.Provider on this document to be able to use this method"
					        		 )
					        );
		} else {
			XMLNoteImageIcon icon=_xmlnoteImageIconProvider.getIcon(id);
			if (icon==null) { return; }
			insertImage(offset,icon);
		}
	}
	
	
	/**
	 * Returns the XMLNoteImageIcons that exist in the given range of the document.
	 * <p>
	 * @param offset	The offset in the text
	 * @param length	The length of the range
	 * @return A vector with XMLNoteImageIcons, that can be of zero length.
	 * @throws BadLocationException
	 * <p>
	 * TODO: This may not be the most efficient implementation.
	 */
	public Vector<XMLNoteImageIcon> imagesInRange(int offset,int length) throws BadLocationException {
		int i,n;
		Vector<XMLNoteImageIcon> v=new Vector<XMLNoteImageIcon>(); 
		for(i=offset,n=offset+length;i<n;i++) {
			Element charElement=this.getCharacterElement(i);
			if (StyleConstants.getIcon(charElement.getAttributes())!=null) {
				v.add((XMLNoteImageIcon) StyleConstants.getIcon(charElement.getAttributes()));
			}
		}
		return v;
	}

	/**
	 * @return Returns a vector with all associated XMLNoteImageIcons with this document.
	 */
	public Vector<XMLNoteImageIcon> getAllImages() {
		try {
			return imagesInRange(0,this.getLength());
		} catch (BadLocationException E) {
			// unexpected.
			DefaultXMLNoteErrorHandler.exception(E);
			return new Vector<XMLNoteImageIcon>();
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Adding things as Undoable
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 *  Add an undoable operation for this document.
	 *   
	 *  The given interface provides an undoable operation. Which will be added this document. 
	 * It will first execute the operation() and than register this action in the undoManager.
	 * On undo, inverseOperation() will be called. On redo, operation() will be called. 
	 * <p>
	 * Note: XMLNoteDocument().addUndoable() can sometimes best be combined with setLongEdit(),
	 * if multiple edits are one edit transaction.
	 * 
	 * @param u  The undoable operation
	 * @return <b>true</b> if the operation succeded and has been added to the undo manager. 
	 */
	public boolean addUndoable(XMLNoteUndoable u) {
		if (u.operation()) {
			getUndoManager().addEdit(new XMLNoteUndoableForOperation(this,u));
			return true;
		} else {
			return false;
		}
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Overriden document altering methods
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Overridden method insertString() does the same as the super class DefaultStyledDocument insertString,
	 * However, fires an DocumentPreEvent before the change is done. If this event returns true,
	 * it effectually vetoes the operation, i.e. the operation will not be committed.
	 */
	public void insertString(int offset, String s, AttributeSet a) throws BadLocationException {
		DocumentEvent e=DocumentPreEvent.insertEvent(this, offset, s, a);
		if (!vetoPreUpdate(e)) { 
			if (a==null) { a=new SimpleAttributeSet(); }
			super.insertString(offset, s, a);
		}
	}
	
	/**
	 * Overridden method remove() does the same as the super class DefaultStyledDocument remove,
	 * However, fires an DocumentPreEvent before the change is done. If this event returns true,
	 * it effectually vetoes the operation, i.e. the operation will not be committed.
	 * <p>
	 * Also, if any marks become empty because of this remove, i.e. they have lengths of 0, they 
	 * also will be removed.
	 */
	public void remove(int offset,int length) throws BadLocationException {
		DocumentEvent e=DocumentPreEvent.removeEvent(this, offset, length);
		if (!vetoPreUpdate(e)) {
			boolean bl=_undoManager.setLongEdit(true);
			
			// first remove the marks, fully contained in the selection
			Vector<XMLNoteMark> v=this.getMarksContainedInSelection(offset, offset+length);
			Iterator<XMLNoteMark> it=v.iterator();
			while (it.hasNext()) {
				XMLNoteMark m=it.next();
				try {
					this.removeMark(m.id());
				} catch (MarkNoExistException e1) {
					// this should not occur.
					DefaultXMLNoteErrorHandler.exception(e1);
				}
			}
			super.remove(offset, length);
			_undoManager.setLongEdit(bl);
		}
	}
	
	/**
	 * Overridden method setCharacterAttributes() does the same as the super class DefaultStyledDocument setCharacterAttributes.
	 * However, fires an DocumentPreEvent before the change is done. If this event returns true,
	 * it effectually vetoes the operation, i.e. the operation will not be committed. 
	 */
	public void setCharacterAttributes(int offset,int length,AttributeSet set,boolean replace) {
		DocumentEvent e=DocumentPreEvent.changeEvent(this,offset,length,set);
		if (!vetoPreUpdate(e)) {
			super.setCharacterAttributes(offset,length,set,replace);
		}
	}

	/**
	 * Overridden method setParagraphAttributes() does the same as the super class DefaultStyledDocument setCharacterAttributes.
	 * However, fires an DocumentPreEvent before the change is done. If this event returns true,
	 * it effectually vetoes the operation, i.e. the operation will not be committed. 
	 */
	public void setParagraphAttributes(int offset,int length,AttributeSet set,boolean replace) {
		DocumentEvent e=DocumentPreEvent.changeEvent(this,offset,length,set);
		if (!vetoPreUpdate(e)) {
			super.setParagraphAttributes(offset,length,set,replace);
		}
	}
	
	/**
	 * Overridden only for cosmetic reasons. The default implementation does a remove/insert operation,
	 * so there's no need to override this method to be able to veto a replace with a DocumentPreListener.
	 */
	public void replace(int offset, int length, String text,AttributeSet attrs) throws BadLocationException {
		super.replace(offset, length, text, attrs);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Getting parts of a document
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * This method gets a part of an XMLNoteDocument. It sets the meta keys <b>startsWithParagraph</b> and
	 * <b>endsWithParagraph</b> to <b>false</b>, when <code>startOffset</code> and <code>endOffset</code> are
	 * in the middle of a paragraph. If this behaviour is not what is wanted, one can always change the
	 * meta parameters after on the returned document.  
	 * 
	 * @param startOffset	The start of the segment to get
	 * @param endOffset		The end of the segment to get
	 * @param includeMarks  If XMLNoteDocument.FULL, include all marks that are fully contained in the given range. If XMLNoteDocument.PARTIAL, include all marks that are partially and fully contained. If XMLNoteDocument.NONE, include no marks.
	 * @return 				Returns a new XMLNoteDocument with the part to get. If <code>startOffset==endOffset</code>, returns null.
	 * @throws BadLocationException	E.g., if endOffset<startOffset or one of the offsets is out of range.
	 */
	public XMLNoteDocument getPart(int startOffset,int endOffset,int includeMarks) throws BadLocationException {
		try {
			if (startOffset==endOffset) {
				return null;
			} else if (endOffset<startOffset) {
				throw new BadLocationException("endOffset < startOffset",startOffset);
			}
			
			XMLNoteDocument newDoc=new XMLNoteDocument(this.getStyles());
			
			//System.out.println("startOffset="+startOffset+", endOffset="+endOffset);
			
			// Get start and end paragraph and adjust start- and endOffset if that
			// seems necessary
			Element startE=this.getParagraphElement(startOffset);
			Element endE=this.getParagraphElement(endOffset);
			
			boolean startWithPar=false;
			boolean endWithPar=false;
			
			if (startE.getStartOffset()==startOffset) {
				startWithPar=true;
			} else if (startE.getEndOffset()-1==startOffset) {
				startWithPar=true;
				startOffset+=1;
				startE=this.getParagraphElement(startOffset);
			}
			
			if (endE.getStartOffset()==endOffset) {
				endE=this.getParagraphElement(endOffset-1);
				endWithPar=true;
			} else if (endE.getEndOffset()==endOffset+1) {
				endWithPar=true;
			}
			
			//System.out.println("parStartOffset="+startE.getStartOffset()+", parEndOffset="+endE.getEndOffset());
			//System.out.println("endStartOffset="+endE.getStartOffset());
			//System.out.println("endEndOffset="+endE.getEndOffset()+", endOffset (modified?)="+endOffset);
			//System.out.println("StartStartOffset="+startE.getStartOffset()+", startOffset (modified?)="+startOffset);

			// It could be that the corrected offsets are equal
			if (startOffset==endOffset) { return null; }
			
			// Determine if the part starts or ends on a paragraph border
			newDoc.setMeta("startsWithParagraph", startWithPar);
			newDoc.setMeta("endsWithParagraph", endWithPar);
			
			// Create the new document
			if (startE.equals(endE)) {
				// handle the case that startOffset and endOffset are the same paragraph.
				insertPartForPar(newDoc,startE,startOffset,endOffset);
			} else {
				// multiple paragraphs. Iterate over all paragraphs and find the ones from startE until endE
				int i,n;
				Element root=this.getDefaultRootElement();
				Vector<Element> ve=new Vector<Element>();
				for(i=0,n=root.getElementCount();i<n && !root.getElement(i).equals(startE);i++);
				// found it (because startE exists in this document, it must be found)
				if (i==n) { throw new BadLocationException("Unexpected: cannot find start paragraph element",startOffset); }
				for(;i<n && !root.getElement(i).equals(endE);i++) {
					ve.add(root.getElement(i));
				}
				if (i==n) { throw new BadLocationException("Unexpected: cannot find end paragraph element",endOffset); }
				ve.add(endE);
				
				// iterate over the elements in the vector.
				Iterator<Element> it=ve.iterator();
				while (it.hasNext()) {
					insertPartForPar(newDoc,it.next(),startOffset,endOffset);
				}
			}
			
			// include marks if requested
			if (includeMarks==XMLNoteDocument.NONE) {
				// do nothing
			} else {
				try {
				Enumeration<DXMLNoteMark> en=_marks.elements();
				int llen=endOffset-startOffset;
				while(en.hasMoreElements()) {
					DXMLNoteMark m=en.nextElement();
					if (m.offset()>=startOffset && m.endOffset()<endOffset) { // fully contained
						newDoc.insertMark(m.id(),m.markClass(), m.offset()-startOffset, m.endOffset()-m.offset());
					} else if (includeMarks==XMLNoteDocument.PARTIAL && m.intersectsWith(startOffset, llen)) {
						// adjust the mark offsets
						int o=m.offset();
						if (o<startOffset) { o=startOffset; }
						int e=m.endOffset();
						if (e>=endOffset) { e=endOffset; }
						newDoc.insertMark(m.id(), m.markClass(), o-startOffset,e-o);
					}
				}
				} catch (Exception E) { // this is unexpected
					DefaultXMLNoteErrorHandler.exception(E);
				}
			}
			
			// return the new document
			return newDoc;
			
		} catch (BadStyleException E) {
			// Unexpected, as the current document must have valid styles. 
			DefaultXMLNoteErrorHandler.exception(E);
			return null;
		}
	}
	
	private void insertPartForPar(XMLNoteDocument newDoc,Element parEl,int startOffset,int endOffset) throws BadLocationException {
		int elStart=parEl.getStartOffset();
		int elEnd=parEl.getEndOffset();
		
		// get the text and the paragraph style first, and insert it into the newDoc
		AttributeSet set=parEl.getAttributes();
		String styleId=XMLNoteStyleConstants.getId(set);
		int start=(elStart<startOffset) ? startOffset : elStart;
		int end=(elEnd>endOffset) ? endOffset : elEnd;
		int len=end-start;
		String text=this.getText(start, len);
		int offset=newDoc.getLength();
		newDoc.insertString(offset, text, set);
		try {
			newDoc.applyStyle(styleId, offset, len);
		} catch (NoStyleException e1) {
			try {
				newDoc.applyStyle(newDoc.getStyles().getDefaultStyle().id(), offset, len);
			} catch (Exception e) { // unexpected exception
				DefaultXMLNoteErrorHandler.exception(e);
			} 
		}
		newDoc.setParagraphAttributes(offset, len, set, false); // add the other attributes
		
		// Now iterate over the other character attributes in the paragraph and add them to the
		// inserted paragraph.
		int i,n;
		for(i=0,n=parEl.getElementCount();i<n;i++) {
			Element charEl=parEl.getElement(i);
			if (charEl.getEndOffset()<start) {
				// do nothing
			} else if (charEl.getStartOffset()>=end){
				// do nothing
			} else {
				AttributeSet charset=charEl.getAttributes();
				int s=(charEl.getStartOffset()<start) ? start : charEl.getStartOffset();
				int e=(charEl.getEndOffset()>=end) ? end : charEl.getEndOffset(); 
				s-=elStart;
				e-=elStart;
				newDoc.setCharacterAttributes(offset+s, e-s, charset, true);
			}
		}
	}
	
	/**
	 * Returns <code>XMLNoteDocument.FULL</code>, <code>PARTIAL</code> if marks are to be copied to the clipboard. 
	 * <code>FULL</code> means, that only marks that are fully contained in the selection are copied. 
	 * <code>PARTIAL></code> means that also marks that are not fully contained are copied, but they are 
	 * truncated to the selection. 
	 * <code>NONE</code> means that no marks are copied.
	 * <p>
	 * Defaults to <code>XMLNoteDocument.FULL</code>.
	 * <p>
	 * @return	FULL,PARTIAL or NONE
	 */
	public int getClipboardIncludeMarks() {
		return _clipboardIncludeMarks;
	}
	
	/**
	 * Set the value of ClipboardIncludeMarks to the given value. 
	 * Supported are <code>XMLNoteDocument.FULL</code>, <code>PARTIAL</code> and <code>NONE</code>.
	 * @param value
	 * @throws NoSuchMethodException
	 */
	public void setClipboardIncludeMarks(int value) throws NoSuchMethodException {
		if (value==FULL || value==PARTIAL || value==NONE) {
			_clipboardIncludeMarks=value;
		} else {
			throw new NoSuchMethodException("only FULL,PARTIAL or NONE is supported for Clipboard Include Marks.");
		}
	}
	
	/**
	 * Returns <b>true</b>, if marks can be pasted from the clipboard into this document. 
	 * Returns <b>false</b>, otherwise. 
	 * <p>
	 * Defaults to <b>true</b>
	 * <p>
	 * @return	true, if marks can be pasted from the clipboard into this document.
	 */
	public boolean getAllowMarksPasted() {
		return _allowMarksPasted;
	}
	
	/**
	 * Set the value of AllowMarksPasted to <b>true</b> or <b>false</b>.
	 * <p>
	 * Defaults to <b>true</b>
	 * <p>
	 * @param allow	true, if pasting of marks from the clipboard is allowed on this document.
	 */
	public void setAllowMarksPasted(boolean allow) {
		_allowMarksPasted=allow;
	}
	
	/**
	 * Add a clipboard listener ({@link XMLNoteClipboardListener}) to this document.
	 * If it has been already added, it will not be added another time.
	 * <p>
	 * @param l The listener to add.
	 */
	public void addClipboardListener(XMLNoteClipboardListener l) {
		_clipboardListeners.add(l);
	}
	
	/**
	 * Remove clipboard listener <code>l</code> ({@link XMLNoteClipboardListener}) from this document.
	 * <p>
	 * @param l The listener to remove
	 */
	public void removeClipboardListener(XMLNoteClipboardListener l) {
		_clipboardListeners.remove(l);
	}
	
	/**
	 * Get an iterator of the clipboard listeners associated with this document.
	 * <p>
	 * Note. this method is primarily targeted towards the XMLNoteTransferHandler.
	 * <p>
	 * @return	the iterator
	 */
	public Iterator<XMLNoteClipboardListener> clipboardListenerIterator() {
		return _clipboardListeners.iterator();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Mark handling
	/////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Adds a mark change listener. If a marks get removed, moved or inserted or
	 * it's contents updated, the mark listener gets called.
	 * 
	 * @param l   the mark listener.
	 */
	public void addMarkListener(XMLNoteMarkListener l) {
		_markListeners.add(l);
	}
	
	/**
	 * Removes the given mark listener that must have been added previously.
	 * 
	 * @param l   the mark listener.
	 */
	public void removeMarkListener(XMLNoteMarkListener l) {
		_markListeners.remove(l);
	}
	
	/**
	 * Inserts an XMLNoteMark with the given id into this document at the given
	 * offset, with the color provided by the MarkColorProvider. Signals XMLNoteMarkListeners.
	 * <p>
	 * returns false if the mark insertion has been vetoed, true otherwise.
	 * 
	 * @param id		The id of the mark
	 * @param _class	The class of the mark (may be null).
	 * @param offset	The offset of the mark
	 * @param length	The length of the mark
	 * @throws BadLocationException
	 * @throws MarkExistsException
	 * @return
	 */
	public boolean insertMark(String id, String _class,int offset,int length) 
						throws MarkExistsException, BadLocationException {
		
		// Check if mark may be inserted
		Iterator<XMLNoteMarkListener> it;
		if (!_blockAllListeners) {
			it=_markListeners.iterator();
			boolean veto=false;
			while(it.hasNext()) {
				boolean b=it.next().markInsert(id, offset, length);
				if (b) { veto=true; }
			}
			_lastOperationVetoed=veto;
			if (veto) { return false; }
		}
		
		// Check if mark can be inserted
		if (_marks.containsKey(id)) {
			String form=_translator.translate("A mark with id '%s' already exists");
			throw new MarkExistsException(String.format(form,id));
		}
		
		DXMLNoteMark m=new DXMLNoteMark(id,_class,this,offset,length);
		// associate highlighters for all associated highlighters right now
		Set<Highlighter> H=_highlighters.keySet();
		Iterator<Highlighter> hit=H.iterator();
		while (hit.hasNext()) {
			Highlighter hh=hit.next();
			MarkMarkupProviderMaker p=_highlighters.get(hh);
			MarkMarkupProvider prov=p.create(m.id(), m.markClass());
			prov.addChangeListener(this);
			Highlight h=(Highlight) hh.addHighlight(offset,
													offset+length,
													new MarkerHighlightPainter(m,prov)
													);
			m.associateHighlight(hh, h);
		}
		_marks.put(id, m);
		_orderedMarks=null;		// clear the ordered marks after mutating _marks
		
		// THIS CODE INTERLOCKS WITH THE XMLNoteEditoKit --> MyLabelView.
		// By setting the character attributes for this mark to the mark, we
		// introduce a new element, the size of the mark.
		{
			int i,eo;
			Element el=this.getCharacterElement(m.offset());
			for (i=m.offset()+1,eo=m.endOffset();i<eo;i++) {
				Element nel=this.getCharacterElement(i);
				if (nel!=el) {
					SimpleAttributeSet set=new SimpleAttributeSet();
					set.addAttributes(el.getAttributes());
					XMLNoteStyleConstants.addMark(set, m);
					int no=Math.max(el.getStartOffset(), m.offset());
					int oe=Math.min(el.getEndOffset(), m.endOffset());
					this.setCharacterAttributes(no, oe-no, set, true);
					el=nel;
				}
			}
			SimpleAttributeSet set=new SimpleAttributeSet();
			set.addAttributes(el.getAttributes());
			XMLNoteStyleConstants.addMark(set, m);
			int no=Math.max(el.getStartOffset(), m.offset());
			int oe=Math.min(el.getEndOffset(), m.endOffset());
			this.setCharacterAttributes(no,oe-no, set, true);
		}
		// THIS CODE INTERLOCKS WITH THE XMLNoteEditoKit --> MyLabelView.
		
		setChanged(true);
			
		// signal listeners for the insertion
		if (!_blockAllListeners) {
			it=_markListeners.iterator();
			boolean vetoUndo=false;
			Vector<XMLNoteMark> orderedMarks=getOrderedMarks();
			int indexInOrderedMarks=getIndexInOrderedMarks(orderedMarks,m);
			while(it.hasNext()) {
				boolean b=it.next().markInserted(m, orderedMarks, indexInOrderedMarks);
				if (b) { vetoUndo=true; }
			}
		
			// add undo action if applicable
			if (!vetoUndo) {
				_undoManager.addEdit(new XMLNoteMarkInsertUndoableEdit(this,id,_class,offset,length));
			}
		}
		
		return true;
	}

	/**
	 * Removes a mark with the given id. Signals XMLNoteMarkListeners
	 * 
	 * Returns false, if removeMark() has been vetoed; true, otherwise. 
	 * 
	 * @param id
	 * @return
	 */
	public boolean removeMark(String id) throws MarkNoExistException {
		// check if marks may be removed
		
		boolean le=this.setLongEdit(true);
		
		Iterator<XMLNoteMarkListener> it;
		if (!_blockAllListeners) {
			it=_markListeners.iterator();
			boolean veto=false;
			while(it.hasNext()) {
				boolean b=it.next().markRemove(id);
				if (b) { veto=true; }
			}
			_lastOperationVetoed=veto;
			if (veto) {
				setLongEdit(le);
				return false; 
			}
		}
		
		// try to remove the mark
		DXMLNoteMark m = _marks.get(id);
		if (m != null) {
			// remove all installed highlighters from the mark and destroy the highlights.
			Set<Highlighter> H=_highlighters.keySet();
			Iterator<Highlighter> hit=H.iterator();
			while (hit.hasNext()) {
				Highlighter h=hit.next();
				Highlight hh=m.removeHighlight(h);
				h.removeHighlight(hh);
			}
			_marks.remove(id);
			_orderedMarks=null; // clear the ordered marks after mutating _marks
			
			// THIS CODE INTERLOCKS WITH THE XMLNoteEditoKit --> MyLabelView.
			// We rremove the mark character attributes, to revoke the mark stuff
			// from the MyLabelView.
			{
				int i,eo;
				Element el=this.getCharacterElement(m.offset());
				for (i=m.offset()+1,eo=m.endOffset();i<eo;i++) {
					Element nel=this.getCharacterElement(i);
					if (nel!=el) {
						SimpleAttributeSet set=new SimpleAttributeSet();
						set.addAttributes(el.getAttributes());
						XMLNoteStyleConstants.removeMark(set, m);
						this.setCharacterAttributes(el.getStartOffset(), el.getEndOffset()-el.getStartOffset(), set, true);
						el=nel;
					}
				}
				SimpleAttributeSet set=new SimpleAttributeSet();
				set.addAttributes(el.getAttributes());
				XMLNoteStyleConstants.removeMark(set, m);
				this.setCharacterAttributes(el.getStartOffset(), el.getEndOffset()-el.getStartOffset(), set, true);
			}			
			// THIS CODE INTERLOCKS WITH THE XMLNoteEditoKit --> MyLabelView.
			
			setChanged(true);
			
			// signal listener that mark has been removed
			if (!_blockAllListeners) {
				boolean vetoUndo=false;
				it=_markListeners.iterator();
				Vector<XMLNoteMark> orderedMarks=getOrderedMarks();
				while (it.hasNext()) {
					boolean b=it.next().markRemoved(m, orderedMarks);
					if (b) { vetoUndo=true; }
				}

				// add undo action if applicable
				if (!vetoUndo) {
					_undoManager.addEdit(new XMLNoteMarkRemoveUndoableEdit(this,m));
				}
			}
		} else {
			String form=_translator.translate("A mark with the given id ('%s') doesn't exist");
			setLongEdit(le);
			throw new MarkNoExistException(String.format(form,id));
		}
		
		setLongEdit(le);
		
		return true;
	}
	
	/**
	 * Returns the mark for the given id, or null if there is no mark with the given id.
	 * @param markId
	 * @return
	 */
	public XMLNoteMark getMarkForId(String markId) {
		return _marks.get(markId);
	}
	
	/**
	 * Returns if a mark with the given id exists.
	 * 
	 * @param markId	The id of the mark to be queried
	 * @return			true, if a mark with the id exists; false, otherwise.
	 */
	public boolean markExists(String markId) {
		return getMarkForId(markId)!=null;
	}

	/**
	 * Returns if any XMLNoteMark falls within the given range.
	 * @param offset
	 * @param length
	 * @return
	 */
	public boolean isMarkInRange(int offset,int length) {
		Enumeration<DXMLNoteMark> it=_marks.elements();
		while (it.hasMoreElements()) {
			if (it.nextElement().intersectsWith(offset,length)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns a vector of Marks starting at the given offset. Empty vector if no marks have been found.
	 * @param offset
	 * @return
	 */
	public Vector<XMLNoteMark> getMarksStartingWith(int offset) {
		Enumeration<DXMLNoteMark> it=_marks.elements();
		Vector<XMLNoteMark> v=new Vector<XMLNoteMark>();
		while(it.hasMoreElements()) {
			XMLNoteMark m=it.nextElement();
			if (m.offset()==offset) {
				v.add(m);
			}
		}
		return v;
	}
	
	/**
	 * Returns a vector of Marks ending at the given offset. Empty vector if no marks have been found.
	 * @param offset
	 * @return
	 */
	public Vector<XMLNoteMark> getMarksEndingWith(int offset) {
		Enumeration<DXMLNoteMark> it=_marks.elements();
		Vector<XMLNoteMark> v=new Vector<XMLNoteMark>();
		while(it.hasMoreElements()) {
			XMLNoteMark m=it.nextElement();
			if (m.endOffset()==offset) {
				v.add(m);
			}
		}
		return v;
	}
	
	/**
	 * Returns a vector of Marks that contain the given offset. Empty vector if no marks have been found.
	 * @param offset
	 * @return
	 */
	public Vector<XMLNoteMark> getMarksForOffset(int offset) { 
		Enumeration<DXMLNoteMark> it=_marks.elements();
		Vector<XMLNoteMark> v=new Vector<XMLNoteMark>();
		while(it.hasMoreElements()) {
			DXMLNoteMark m=it.nextElement();
			if (m.offsetInRange(offset)) { v.add(m); }
		}
		return v;
	}
	
	/**
	 * Returns all marks that overlap with the given selection.
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public Vector<XMLNoteMark> getMarksForSelection(int begin,int end) {
		Enumeration<DXMLNoteMark> it=_marks.elements();
		Vector<XMLNoteMark> v=new Vector<XMLNoteMark>();
		int len=end-begin;
		while(it.hasMoreElements()) {
			DXMLNoteMark m=it.nextElement();
			if (m.intersectsWith(begin,len)) {
				v.add(m);
			}
		}
		return v;
	}

	/**
	 * Returns all marks that are fully contained in the given selection, i.e. for which <code>mark.offset()&gt;=offset</code>
	 * and <code>mark.endOffset()&lt;end</code>.
	 * 
	 * @param offset
	 * @param end
	 * @return
	 */
	public Vector<XMLNoteMark> getMarksContainedInSelection(int offset, int end) {
		Enumeration<DXMLNoteMark> it=_marks.elements();
		Vector<XMLNoteMark> v=new Vector<XMLNoteMark>();
		while (it.hasMoreElements()) {
			XMLNoteMark m=it.nextElement();
			if (m.offset()>=offset && m.endOffset()<end) {
				v.add(m);
			}
		}
		return v;
	}

	
	/**
	 * Returns all identifiers for the marks of this document.
	 * @return
	 */
	public Vector<String> getMarkIds() {
		Enumeration<DXMLNoteMark> it=_marks.elements();
		Vector<String> ids=new Vector<String>();
		while(it.hasMoreElements()) {
			ids.add(it.nextElement().id());
		}
		return ids;
	}
	
	/**
	 * Returns all marks ordered ascending by (<code>offset()</code>,<code>id()</code>).
	 * @return
	 */
	public Vector<XMLNoteMark> getOrderedMarks() {
		if (_orderedMarks==null) { createOrderedMarks(); }
		return _orderedMarks;
	}
	
	private void createOrderedMarks() {
		Enumeration<DXMLNoteMark> it=_marks.elements();
		Vector<XMLNoteMark> v=new Vector<XMLNoteMark>();
		while(it.hasMoreElements()) {
			v.add(it.nextElement());
		}
		Collections.sort(v,new Comparator<XMLNoteMark>() {
			public int compare(XMLNoteMark o1, XMLNoteMark o2) {
				if (o1.offset()<o2.offset()) { return -1; }
				else if (o1.offset()==o2.offset()) {
					return o1.id().compareToIgnoreCase(o2.id());
				} else {
					return 1;
				}
			}
		});
		_orderedMarks=v;
	}
	
	/**
	 * Returns the index of m in the given vector. Returns the first index of it in vector.
	 * It assumes that m is placed in the vector by reference. So it will check for 
	 * <code>m==orderedMarks.get(i)</code>.
	 * 
	 * Returns the index, or -1 if not found.
	 * 
	 * @param orderedMarks
	 * @param m
	 * @return
	 */
	public int getIndexInOrderedMarks(Vector<XMLNoteMark> orderedMarks,XMLNoteMark m) {
		int i,n;
		for(i=0,n=orderedMarks.size();i<n && m!=orderedMarks.get(i);i++);
		if (i==n) { return -1; }
		else { return i; }
	}
	
	
	/**
	 * Returns the first XMLNoteMark after the given offset. If offset equals the offset()
	 * of an existing XMLNoteMark, this function will skip to the next.
	 * 
	 * @param offset
	 * @param wrap
	 * @return
	 */
	public XMLNoteMark getNextMark(int offset, boolean wrap) {
		Vector<XMLNoteMark> orderedMarks=getOrderedMarks();
		int i,n;
		for(i=0,n=orderedMarks.size();i<n && orderedMarks.get(i).offset()<=offset;i++);
		if (i==n && wrap) { i=0; }
		if (i>=n) { return null; }
		else { return orderedMarks.get(i); }
	}

	/**
	 * Returns the first XMLNoteMark before the given offset. If offset equals the offset()
	 * of an existing XMLNoteMark, this function will skip to the one before that.
	 * 
	 * @param offset
	 * @param wrap
	 * @return
	 */
	public XMLNoteMark getPreviousMark(int offset, boolean wrap) {
		Vector<XMLNoteMark> orderedMarks=getOrderedMarks();
		int i,n;
		for(i=0,n=orderedMarks.size();i<n && orderedMarks.get(i).offset()<offset;i++);
		if (i==0 && wrap) { i=n-1; }
		else if (i==n) { i=n-1; }
		else { i-=1;; }
		if (i<0 || i>=n) { return null; }
		else { return orderedMarks.get(i); }
	}

	
	/**
	 * This method will iterator over all marks (via getOrderedMarks()) and will allow
	 * the listening XMLNoteMarkListeners to do administrative tasks. The callback
	 * iteratorOperation() of XMLNoteMarkListener will be called with info.
	 */
	public void iterateOverMarks(Object info) {
		Vector<XMLNoteMark> orderedMarks=getOrderedMarks();
		Iterator<XMLNoteMark> it=orderedMarks.iterator();
		while (it.hasNext()) {
			XMLNoteMark m=it.next();
			Iterator<XMLNoteMarkListener> lit=_markListeners.iterator();
			while (lit.hasNext()) {
				lit.next().iteratorOperation(m,info);
			}
		}
	}
	
	
	// Internal use. This is part of the default document listener. if blocked == false in the default
	// DocumentPostListener, the changes to the marks will be propagated using this function.
	// This function could also be called from other places. Changed marks will be signaled in ascending order
	// this is setup between two calls of XMLNoteMarkListener.begin() and end()
	private MakeAsync _markChangesAsync=new MakeAsync(ASYNC_STARVATION_TIMEOUT);
	
	private void administerMarkChanges() {
		Runnable R=new Runnable() {
			public void run() {
				Vector<XMLNoteMark> marks=getOrderedMarks();
				int i,n;
				Iterator<XMLNoteMarkListener> mit=_markListeners.iterator();
				XMLNoteMarkListener.ChangedMessageWay way=ChangedMessageWay.FOR_CHANGED;
				while (mit.hasNext()) {
					ChangedMessageWay c=mit.next().markAdminBegin();
					if (c==ChangedMessageWay.FOR_ALL) { way=ChangedMessageWay.FOR_ALL; } 
				}
				for(i=0,n=marks.size();i<n;i++) {
					DXMLNoteMark m=(DXMLNoteMark) marks.get(i);
					if (m.offsetChanged() || way==ChangedMessageWay.FOR_ALL) {
						mit=_markListeners.iterator();
						while(mit.hasNext()) {
							mit.next().markOffsetsChanged(m, marks, i);
						}
						m.clearOffsetChanged();
					}
				}
				mit=_markListeners.iterator();
				while(mit.hasNext()) {
					mit.next().markAdminEnd();
				}
			}
		};
		
		
		// Make sure we are on the AWT Event Thread, because we want serial
		// behaviour here. The datastructures aren't prepared for parallel
		// access.
		if (!_markListeners.isEmpty()) {
			if (!SwingUtilities.isEventDispatchThread()) {
				throw new RuntimeException("If there are any XMLNoteMarkListeners, administerMarkChanges() may only be called from the EventDispatchThread");
			} else {
				_markChangesAsync.invokeLater(R);
			}
		}
	}
	
	// Interal use. This object hooks in to the MarkMarkupProvider and listens to changes.
	// It fires a Document Changed Event to the listeners and makes the views update
	// BUG: The function should not be called from within an caret update event.
	// It can result in a ArrayOutOfBound Error.
	// TRIED RESOLUTION: Post a runnable on the awt event queue. Only post a new one
	// if this one hasn't been handled. With invokeLater(), the posted runnable will be
	// handled after all events on the queue have been dispatched. 
	//private Set<Runnable> markMarkupChangedEvents=new HashSet<Runnable>();
	private MakeAsync _markMarkupChangedAsync=new MakeAsync(ASYNC_STARVATION_TIMEOUT);
	
	public void markMarkupChanged() {
		Runnable r=new Runnable() {
			public void run() {
				// only handle the last changedupdate. skip all others.
				XMLNoteDocument.super.fireChangedUpdate(new DocumentEvent() {

					public ElementChange getChange(Element e) {
						return null;
					}

					public Document getDocument() {
						return XMLNoteDocument.this;
					}

					public int getLength() {
						return XMLNoteDocument.this.getLength();
					}

					public int getOffset() {
						return 0;
					}

					public EventType getType() {
						return DocumentEvent.EventType.CHANGE;
					}
				});
			}
				//System.out.println();
		};
		if (!SwingUtilities.isEventDispatchThread()) {
			throw new RuntimeException("markMarkupChanged() may only be called from the EventDispatchThread");
		} else {
			_markMarkupChangedAsync.invokeLater(r);
		}
	}

	/**
	 * Sometimes it is necessary to get your view synchronized with a document manually.
	 * This can be done by triggering the document listeners with a changed event. Which 
	 * in the case of XMLNoteDocument will also trigger a MarkOffsetsChanged sequence.
	 * And that can be helpfull when there are widgets associated with marks that need
	 * relayouting on insertion or removal.
	 * 
	 */
	public void fireChanged() {
		markMarkupChanged();
	}
	
	public void fireZoomChanged() {
		boolean ui=_undoManager.setIgnore(true);
		boolean ba=setBlockAllListeners(true);
		boolean tl=_postListenerProxy.setBlocked(true); // Yeah, really block all, even the proxy itself

		super.fireChangedUpdate(new DocumentEvent() {

			public ElementChange getChange(final Element e) {
				final Element [] childs=new Element[e.getElementCount()];
				for(int i=0,n=e.getElementCount();i<n;i++) {
					childs[i]=e.getElement(i);
				}
				return new ElementChange() {
					public Element[] getChildrenAdded() {
						return childs;
					}
					public Element[] getChildrenRemoved() {
						return childs;
					}
					public Element getElement() {
						return e;
					}
					public int getIndex() {
						return 0;
					}
					
				};
			}

			public Document getDocument() {
				return XMLNoteDocument.this;
			}

			public int getLength() {
				return XMLNoteDocument.this.getLength();
			}

			public int getOffset() {
				return 0;
			}

			public EventType getType() {
				return DocumentEvent.EventType.CHANGE;
			}
		});
		
		_undoManager.setIgnore(ui);
		setBlockAllListeners(ba);
		_postListenerProxy.setBlocked(tl); // Yeah, really block all, even the proxy itself

	}
	
	/**
	 * This method makes it possible to change the ids of marks in the current document.
	 * It iterates over the marks and calls <code>p.getNewId(mark)</code> for a new id
	 * for the given mark. If this method returns an id that is not equal to <code>mark.id()</code> and
	 * not <b>null</b> the mark will be given a new id, and for that mark, all marklisteners will
	 * receive a <code>markidChanged()</code> event.
	 * 
	 * @param p
	 */
	public void reassignMarkIds(XMLNoteMarkIdProvider p) {
		Set<String> ids=_marks.keySet();
		// This needs to be done to be able to iterator over all mark ids
		// in the hashtable and mutate the hashtable as well.
		Vector<String> markIds=new Vector<String>();
		Iterator<String> it=ids.iterator();
		while (it.hasNext()) { markIds.add(it.next()); }
		
		// Change the marks and notify listeners when it has actually occured
		it=markIds.iterator();
		while(it.hasNext()) {
			String previousId=it.next();
			DXMLNoteMark m=_marks.get(previousId);
			String newId=p.getNewId(m);
			if (newId==null || newId.equals(previousId)) {
				// do nothing
			} else {
				m.setId(newId);
				_marks.remove(previousId);
				_marks.put(newId, m);
				_orderedMarks=null; // clear the ordered marks after mutating _marks;
				Iterator<XMLNoteMarkListener> lit=_markListeners.iterator();
				while (lit.hasNext()) {
					lit.next().markIdChanged(m, previousId);
				}
			}
		}
	}
	
	/**
	 * Gets the current mark id reassigner (interface XMLNoteMarkIdProvider). This will
	 * be used to reassign ids to marks into an xmlnote document (see getPart()) that is
	 * pasted into this document.
	 * <p>
	 * Defaults to <b>null</b>
	 * <p>
	 * @return the current mark id reassigner. 
	 */
	public XMLNoteMarkIdProvider getMarkIdReassigner() {
		return _markidReassigner;
	}
	
	/**
	 * Sets the current mark id reassigner (interface XMLNoteMarkIdProvider).
	 * 
	 * @param p The new XMLNoteMarkIdProvider.
	 */
	public void setMarkIdReassigner(XMLNoteMarkIdProvider p) {
		_markidReassigner=p;
	}
	
	/**
	 * This method returns an iterator over the marks in the document in offset order.
	 * This can be used to iterate over all marks. However. If you insert or remove marks
	 * during this iteration, that won't be reflected in the iterator, so be in for surprises
	 * if you do that. 
	 * 
	 * @return an iterator over the marks in this document in offset order. 
	 */
	public Iterator<XMLNoteMark> markIterator() {
		return getOrderedMarks().iterator();
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Write and/or read XMLNote XML.
	/////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the xml for an empty XMLNote document.
	 */
	public static String emptyXML() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xmlnote version=\"2010.1\"><meta /><notes /></xmlnote>";
	}
	
	/**
	 * Creates a XMLNote document from a simple flat text.
	 * 
	 * @param text
	 * @return
	 */
	static public String XMLfromText(final String text) {
		String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?><xmlnote version=\"2010.1\"><meta /><notes>\n";
		Pattern p=Pattern.compile("^(.*)$");
		Matcher m=p.matcher(text);
		while (m.find()) {
			String t=m.group(1);
			t.replaceAll("\\t", "<tab />");
			t.replaceAll("\\s", "<space />");
			xml+="<par>"+t+"<enter />"+"</par>\n";
		}
		xml+="</notes></xmlnote>";
		return xml;
	}

	
	/**
	 * Writes the current document contents to xmlnote xml.
	 */
	public String toXML() throws BadDocumentException {
		XMLNoteXMLOut w=new XMLNoteXMLOut(this);
		return w.toXML();
	}

	/**
	 * Reads a document from xml and returns a new XMLNoteDocument without listeners attached, using the
	 * current XMLNoteStyles attached to this document.
	 * @param xml
	 * @return
	 * @throws BadDocumentException
	 * @throws BadStyleException
	 */
	public XMLNoteDocument fromXML(String xml) throws BadDocumentException, BadStyleException {
		return this.fromXML(xml,_styles);
	}

	/**
	 * Reads a document from xml and returns a new XMLNoteDocument without listeners attached.
	 * Sets changed() to false.
	 * 
	 * @param xml
	 * @param styles
	 * @return
	 * @throws BadDocumentException
	 * @throws BadStyleException
	 */
	public XMLNoteDocument fromXML(String xml,XMLNoteStyles styles) throws BadDocumentException, BadStyleException {
		XMLNoteDocument doc=new XMLNoteDocument(styles);
		XMLNoteXMLIn w=new XMLNoteXMLIn(doc);
		w.fromXML(xml,getXMLNoteImageIconProvider());
		setChanged(false);
		return doc;
	}
	
	/**
	 * Clears the document and reads in new XMLnote from XML. Signals via DocumentAdminListener, and
	 * clears all undo/redo possibilities and the changed() state has been reset to false.
 	 * 
	 * @param xml
	 * @param m
	 * @throws BadDocumentException
	 */
	public void resetFromXML(String xml) throws BadDocumentException {
		// May we reset this document from XML?
		boolean veto=false;
		Iterator<DocumentAdminListener> it=_adminListeners.iterator();
		DocumentAdminEvent e=new DocumentAdminEvent(this);
		while (it.hasNext()) {
			boolean b=it.next().documentWillBeReset(e);
			if (b) { veto=b; }
		}
		_lastOperationVetoed=veto;
		if (veto) { return; }

		// block listeners
		boolean i=_undoManager.setIgnore(true);
		boolean bl=setBlockAllListeners(true);
		boolean tl=_postListenerProxy.setBlocked(true);	// Yeah, really, even block the proxy!
		
		// perform action
		clear();
		XMLNoteXMLIn w=new XMLNoteXMLIn(this);
		w.fromXML(xml,null);
		
		// unblock listeners
		_undoManager.setIgnore(i);
		setBlockAllListeners(bl);
		_postListenerProxy.setBlocked(tl);	// Yeah, really, even unblock the proxy!
		_undoManager.discardAllEdits();
		
		setChanged(false);
		
		it=_adminListeners.iterator();
		e=new DocumentAdminEvent(this);
		while (it.hasNext()) {
			it.next().documentHasBeenReset(e); 
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Document listeners	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Clones all DocumentListeners, UndoableEditListeners, DocumentPreListeners and 
	 * XMLNoteMarkListeners from this document to 'toDocument' 
	 * 
	 * Note! This method will not clone DocumentAdminListeners!
	 * 
	 * @param toDocument
	 */
	public void cloneListeners(XMLNoteDocument toDocument) {
		Iterator<DocumentListener> pit=this._postListeners.iterator();
		while (pit.hasNext()) {
			toDocument.addDocumentPostListener(pit.next());
		}
		
		Iterator<DocumentPreListener> it=this._preListeners.iterator();
		while(it.hasNext()) {
			toDocument.addDocumentPreListener(it.next());
		}
		
		Iterator<XMLNoteMarkListener> mit=this._markListeners.iterator();
		while(mit.hasNext()) {
			toDocument.addMarkListener(mit.next());
		}
		
		Iterator<UndoableEditListener> uit=this._undoableEditListeners.iterator();
		while(uit.hasNext()) {
			toDocument.addUndoableEditListener(uit.next());
		}
	}
	
	/**
	 * Clears all DocumentListeners added through <code>addDocumentPostListener()</code>, 
	 * DocumentPreListeners, UndoableEditListeners and XMLNoteMarkListeners from this document.
	 * NOTE: External administrations e.g. for marks may need to be cleared as well!
	 * 
	 * Note! This method will not clear DocumentAdminListeners! 
	 */
	public void clearListeners() {
		_postListeners.clear();
		_preListeners.clear();
		_markListeners.clear();
		_undoableEditListeners.clear();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Override mutation methods of Document to be able to track changes to the
	// document before they are actually made
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Add document admin listener l to this document. If the listener already exists, it will not be added twice. 
	 *  
	 * @param l
	 */
	public void addDocumentAdminListener(DocumentAdminListener l) {
		_adminListeners.add(l);
	}
	
	/**
	 * Removes the document admin listener l from this document.
	 * @param l
	 */
	public void removeDocumentAdminListener(DocumentAdminListener l) {
		_adminListeners.remove(l);
	}

	/**
	 * Add a document pre listener, i.e. a listener that is called before the actual change to the document occurs
	 * If the listener already exists, it will not be added twice. 
	 */
	public void addDocumentPreListener(DocumentPreListener l) {
		_preListeners.add(l);
	}
	
	/**
	 * Add a document post listener, i.e. a listener that is equal to DocumentListener and called after the actual
	 * change to the document occurs. USE THIS METHOD INSTEAD OF <code>addDocumentListener</code>, to add your own
	 * document listeners, because it will allow you to use <code>cloneListeners()</code> and <code>clearListeners()</code>.
	 * If the listener already exists, it will not be added twice.
	 *  
	 * @param l
	 */
	public void addDocumentPostListener(DocumentListener l) {
		_postListeners.add(l);
	}
	
	/**
	 * Remove a document pre listener, i.e. a listener that is called before the actual change to the document occurs 
	 */
	public void removeDocumentPreListener(DocumentPreListener l) {
		_preListeners.remove(l);
	}

	private boolean vetoPreUpdate(DocumentEvent e) {
		boolean vetoChange=false;
		if (!_blockAllListeners) {
			Iterator<DocumentPreListener> it=_preListeners.iterator();
			while(it.hasNext()) {
				if (e.getType()==DocumentEvent.EventType.CHANGE) {
					boolean b=it.next().changeUpdate(e);
					if (b) { vetoChange=b; }
				} else if (e.getType()==DocumentEvent.EventType.INSERT) {
					boolean b=it.next().insertUpdate(e);
					if (b) { vetoChange=b; }
				} else if (e.getType()==DocumentEvent.EventType.REMOVE) {
					boolean b=it.next().removeUpdate(e);
					if (b) { vetoChange=b; }
				}
			}
		}
		_lastOperationVetoed=vetoChange;
		return vetoChange;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Supporting methods for copyInto
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	private class AdjustedMark extends XMLNoteMark {
		private int _offset;
		private int _endoffset;

		public String content() {
			try {
				return XMLNoteDocument.this.getText(offset(), endOffset()-offset());
			} catch (BadLocationException e) {
				DefaultXMLNoteErrorHandler.exception(e);
				return null;
			}
		}

		public Integer offset() {
			return _offset;
		}

		public Integer endOffset() {
			return _endoffset;
		}
		
		public AdjustedMark(String id,String _class,int offset,int endOffset) {
			super(id,_class);
			_offset=offset;
			_endoffset=endOffset;
			
		}
	}
	
	/**
	 * Inserts content described in ContentTree into the given offset
	 * 
	 * @param offset		insert location
	 * @param data			the content
	 * @param join			join to the previous branch
	 * @throws BadLocationException
	 */
	protected void insertContent(int offset,ContentTree data,boolean join) throws BadLocationException
	{
		boolean contentOnly=true;
		boolean atStart=false;
		boolean singleLine=false;
		int childs=data.getRoot().getChildCount();
		
		for(int i=0;i<childs;i++)
		{
			if(data.getRoot().getChildAt(i).getChildCount()>0)
				contentOnly=false;
		}
		//is is a single paragraph insert?
		if(data.getRoot().getChildCount()==1 && !contentOnly)
			singleLine=true;
		Element par=this.getParagraphElement(offset);
		//insert a a paragraph start?
		if(par.getStartOffset()==offset)
			atStart=true;
		//create a buffer
		ArrayList<ElementSpec> buffer=new ArrayList<ElementSpec>();
		if(atStart && !(contentOnly && offset==0))
			handleInsertAtParaStart(buffer, offset);

		if(contentOnly)
		{
			if(atStart && offset!=0)
				buffer.add(createSpecStart(null, ElementSpec.JoinNextDirection));				
			for(int i=0;i<childs;i++)
			{
				Node nd=data.getRoot().getChildAt(i);
				handleChar(buffer, nd,ElementSpec.OriginateDirection);
			}			
		}
		else //paragraphs
		{
			if(atStart)
			{
				for(int i=0;i<childs;i++)
				{
					Node nd=data.getRoot().getChildAt(i);
					short startDir=ElementSpec.OriginateDirection;
					if((i==childs-1)&&join&&offset>0) startDir=ElementSpec.JoinNextDirection;
					if((i==childs-1)&&join&&offset==0) startDir=ElementSpec.JoinFractureDirection;
					handlePara(buffer, nd, startDir,!((i==childs-1)&&join));
				}
			}
			else //in the middle
			{
				if(!join)
				{
					buffer.add(createContent(null, "\n", ElementSpec.JoinPreviousDirection));
					buffer.add(createSpecEnd());					
				}
				for(int i=0;i<childs;i++)
				{
					Node nd=data.getRoot().getChildAt(i);
					short startDir=ElementSpec.OriginateDirection;
					if(i==0 && join) startDir=-1;
					if((i==childs-1) && !singleLine && join) startDir=ElementSpec.JoinFractureDirection;
					handlePara(buffer, nd, startDir,!((i==childs-1)&&join));
				}
				if(!join)
					buffer.add(createSpecStart(null, ElementSpec.JoinFractureDirection));
			}			
		}
		//create the actual buffer
		ElementSpec[] inserts = new ElementSpec[buffer.size()];
		buffer.toArray(inserts);
		this.insert(offset, inserts);				
	}
	
	private void handleChar(ArrayList<ElementSpec> buffer,Node node,short direction) {
		buffer.add(createContent(node.getAttr(), node.getText(), direction));
	}
	
	private void handlePara(ArrayList<ElementSpec> buffer,Node node,short startDir,boolean end)
	{
		Node nd=null;
		if(startDir>0)
			buffer.add(createSpecStart(node.getAttr(), startDir));
		for(int i=0;i<node.getChildCount();i++)
		{
			nd=node.getChildAt(i);
			handleChar(buffer,nd,ElementSpec.OriginateDirection);
		}
		if(end)
		{
			buffer.add(createContent(nd!=null?nd.getAttr():new SimpleAttributeSet(), "\n", ElementSpec.JoinPreviousDirection));			
			buffer.add(createSpecEnd());
		}
	}
	
	private void handleInsertAtParaStart(ArrayList<ElementSpec> buffer, int offset) {
		Element el = this.getParagraphElement(offset - 1);
		Element el2 = this.getParagraphElement(offset);
		Element el2s=el2;
		if(offset>0)
		{
			while (el != null && el.getEndOffset() <= offset)
			{
				buffer.add(createSpecEnd());
				el = el.getParentElement();
			}
		}
		else
			buffer.add(createSpecEnd());
			
		while (el2 != null && el2 != el)
		{
			if (el2!=el2s) 
				buffer.add(createSpecStart(null, ElementSpec.JoinNextDirection));
			el2 = el2.getParentElement();
		}
	}
	
	private ElementSpec createSpecStart(MutableAttributeSet attr,short direction) {
		ElementSpec spec=new ElementSpec(attr,ElementSpec.StartTagType);
		spec.setDirection(direction);
		return spec;
	}

	private ElementSpec createSpecEnd()
	{
		ElementSpec spec=new ElementSpec(null,ElementSpec.EndTagType);
		return spec;
	}

	private ElementSpec createContent(MutableAttributeSet attr,String str,short direction)
	{
		ElementSpec spec=new ElementSpec(attr,ElementSpec.ContentType,str.toCharArray(),0,str.length());
		spec.setDirection(direction);
		return spec;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////
	// Constructors
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Constructs an empty XMLNoteDocument.
	 */
	public XMLNoteDocument() throws BadStyleException {
		this(new XMLNoteStyles());
	}
	
	
	/**
	 * Constructs an XMLNoteDocument from the given xml
	 * 
	 * @param xml
	 * @throws BadStyleException
	 * @throws BadDocumentException
	 */
	public XMLNoteDocument(String xml,XMLNoteImageIcon.Provider prov) throws BadStyleException, BadDocumentException {
		this(xml,prov,new XMLNoteStyles());
	}
	
	/**
	 * Constructs an XMLNoteDocument from the given xml and styles.
	 * 
	 * @param xml
	 * @param st
	 * @throws BadStyleException
	 * @throws BadDocumentException
	 */
	public XMLNoteDocument(String xml,XMLNoteImageIcon.Provider prov,XMLNoteStyles st) throws BadStyleException, BadDocumentException {
		this(st);
		XMLNoteXMLIn in=new XMLNoteXMLIn(this);
		this.setXMLNoteImageIconProvider(prov);
		in.fromXML(xml,null);
	}
	
	/**
	 * Constructs an XMLNoteDocument using given XMLNoteStyles (that can be shared amoung multiple documents).
	 */
	public XMLNoteDocument(XMLNoteStyles _st) throws BadStyleException {
		this(_st,DPIAdjuster.DEVICE_SCREEN);
	}
	
	protected XMLNoteDocument(XMLNoteStyles _st,String device) throws BadStyleException {
		super(_st.getStyleContext());
		
		//this.putProperty("i18n", Boolean.TRUE);			// TODO: CHECK of dit goed is.
		
		_st.addStyleChangedListener(this);
		
		_xmlnoteImageIconProvider=(globalImageSupport) ? null : nullImageProvider;
		_blockAllListeners=false;
		_translator=new DefaultXMLNoteTranslator();
		_device=device;
		_styles = _st;
		super.setLogicalStyle(0,_styles.getDefaultStyle().getRealStyle(device));
		_marks = new Hashtable<String, DXMLNoteMark>();
		_meta  = new Hashtable<String, Object>();
		_highlighters=new WeakHashMap<Highlighter,MarkMarkupProviderMaker>();
		_postListeners=new HashSet<DocumentListener>();
		_preListeners=new HashSet<DocumentPreListener>();
		_markListeners=new HashSet<XMLNoteMarkListener>();
		_adminListeners=new HashSet<DocumentAdminListener>();
		_clipboardListeners=new HashSet<XMLNoteClipboardListener>();
		_postListenerProxy=new DocumentPostListener();
		super.addDocumentListener(_postListenerProxy);
		
		_undoableEditListeners=new HashSet<UndoableEditListener>();
		_undoManager=new XMLNoteUndoManager();
		super.addUndoableEditListener(this);
		
		_changed=false;
		
		// Clipboard stuff
		_clipboardIncludeMarks=FULL;
		_allowMarksPasted=true;
		_markidReassigner=null;
		
		_lastOperationVetoed=false;
	}


}


