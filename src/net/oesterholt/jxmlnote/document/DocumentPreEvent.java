/* ******************************************************************************
 *
 *       Copyright 2008-2010 Hans Oesterholt-Dijkema
 *       This file is part of the JDesktop SwingX library
 *       and part of the SwingLabs project
 *
 *   SwingX is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as 
 *   published by the Free Software Foundation, either version 3 of 
 *   the License, or (at your option) any later version.
 *
 *   SwingX is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with SwingX.  If not, see <http://www.gnu.org/licenses/>.
 *   
 * ******************************************************************************/

package net.oesterholt.jxmlnote.document;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentEvent.EventType;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

import net.oesterholt.jxmlnote.exceptions.BadOperationException;

public class DocumentPreEvent implements DocumentEvent {
	
	private Document  		_document;
	private int       		_length;
	private int       		_offset;
	private EventType 		_type;
	private String    		_string;
	private AttributeSet	_set;
	
	/**
	 * Creates a  DocumentPreEvent for an INSERT event. if offset<0, or >document length, throws a BadLocationException
	 * @param doc
	 * @param offset
	 * @param s
	 * @param a
	 * @return
	 * @throws BadLocationException
	 */
	public static DocumentPreEvent insertEvent(Document doc,int offset,String s,AttributeSet a) throws BadLocationException {
		if (offset<0 || offset>doc.getLength()) {
			throw new BadLocationException("offset out of range for an INSERT operation",offset);
		}
		DocumentPreEvent e=new DocumentPreEvent();
		e._document=doc;
		e._offset=offset;
		e._length=s.length();
		e._string=s;
		e._set=a;
		e._type=EventType.INSERT;
		return e;
	}

	/**
	 * Creates a DocumentPreEvent for a CHANGE event . If offset<0 or offset+length>document length, throws a BadLocationException
	 * @param doc
	 * @param offset
	 * @param s
	 * @param a
	 * @return
	 * @throws BadLocationException
	 */
	public static DocumentPreEvent changeEvent(Document doc,int offset,String s,AttributeSet a) throws BadLocationException {
		if (offset<0 || (offset+s.length())>doc.getLength()) {
			throw new BadLocationException("offset out of range for the given string for a CHANGE operation",offset);
		}
		DocumentPreEvent e=new DocumentPreEvent();
		e._document=doc;
		e._offset=offset;
		e._length=s.length();
		e._set=a;
		e._string=s;
		e._type=EventType.CHANGE;
		return e;
	}
	
	/**
	 * Creates a DocumentPreEvent for a CHANGE event, for only the AttributeSet a. In line with setCharacterAttributes,
	 * it doesn't have a BadLocationException
	 * @param doc
	 * @param offset
	 * @param length
	 * @param a
	 * @return
	 */
	public static DocumentPreEvent changeEvent(Document doc,int offset,int length,AttributeSet a) {
		DocumentPreEvent e=new DocumentPreEvent();
		e._document=doc;
		e._offset=offset;
		e._length=length;
		e._set=a;
		e._string=null;
		e._type=EventType.CHANGE;
		return e;
	}
	
	/**
	 * Creates a DocumentPreEvent for a REMOVE event. If offset<0 or offset+length>document length, throws a BadLocationException
	 * @param doc
	 * @param offset
	 * @param length
	 * @return
	 * @throws BadLocationException
	 */
	public static DocumentPreEvent removeEvent(Document doc,int offset,int length) throws BadLocationException {
		if (offset<0 || (offset+length)>doc.getLength()) {
			throw new BadLocationException("offset out of range for the given string for a REMOVE operation",offset);
		}
		DocumentPreEvent e=new DocumentPreEvent();
		e._document=doc;
		e._offset=offset;
		e._length=length;
		e._set=null;
		e._string=null;
		e._type=EventType.REMOVE;
		return e;
	}
	
	/**
	 * See DocumentEvent. As there have not been any changes to the document,
	 * this will always return null.
	 * @return
	 */
	public ElementChange getChange(Element arg0) {
		return null;
	}

	/**
	 * Returns the associated document with the change.
	 * @return
	 */
	public Document getDocument() {
		return _document;
	}

	/**
	 * Returns the length of the change
	 * @return
	 */
	public int getLength() {
		return _length;
	}

	/**
	 * Returns the offset of the change
	 * @return
	 */
	public int getOffset() {
		return _offset;
	}

	/**
	 * Returns the type of change
	 * @return
	 */
	public EventType getType() {
		return _type;
	}
	
	/**
	 * Returns the associated string. This does not apply if the EventType equals REMOVE. Will throw a BadOperationException,
	 * if this applies.
	 * @return
	 * @throws BadOperationException
	 */
	public String getString() throws BadOperationException {
		if (_type==DocumentEvent.EventType.REMOVE) { 
			throw new BadOperationException("getString doesn't apply when eventtype equals REMOVE");
		}
		return _string;
	}
	
	/**
	 * Returns the associated attributeset. This does not apply if the EventType equals REMOVE. Will throw a BadOperationException,
	 * if this applies.
	 * @return
	 * @throws BadOperationException
	 */
	public AttributeSet getAttributeSet() throws BadOperationException {
		if (_type==DocumentEvent.EventType.REMOVE) { 
			throw new BadOperationException("getAttributeSet doesn't apply when eventtype equals REMOVE");
		}
		return _set;
	}
	
	/**
	 * Sets the String to change. This does not apply if the EventType equals REMOVE. Will throw a BadOperationException 
	 * if this applies. If event type equals CHANGE and length of the string goes out of range of the document,
	 * a BadLocationException is thrown.
	 * @param s
	 * @throws BadLocationException
	 * @throws BadOperationException
	 */
	public void setString(String s) throws BadOperationException,BadLocationException {
		if (_type==DocumentEvent.EventType.REMOVE) { 
			throw new BadOperationException("setString doesn't apply when eventtype equals REMOVE");
		}
		if ((_type==DocumentEvent.EventType.CHANGE) && ((_offset+s.length())>_document.getLength())) {
			throw new BadLocationException("Cannot set a string bigger than document length when not inserting",_offset);
		}
		_string=s;
		_length=s.length();
	}
	
	/**
	 * Sets the offset of the Change in the document. Throws a BadLocationException if the eventtype equals CHANGE or REMOVE
	 * and the offset+length goes out of range of the Document, or offset<0.
	 * @param offset
	 * @throws BadLocationException
	 */
	public void setOffset(int offset) throws BadLocationException {
		if (offset<0 || (((offset+_length)>_document.getLength()) && _type!=DocumentEvent.EventType.INSERT)) {
			throw new BadLocationException("Offset out of range for this change/document combination",offset); 
		}
		_offset=offset;
	}
	
	/**
	 * Sets the length of the change in the document. This can only be done, when eventtype equals REMOVE.
	 * 
	 * @param length
	 * @throws BadOperationException
	 * @throws BadLocationException
	 */
	public void setLength(int length) throws BadOperationException,BadLocationException {
		if (_type!=DocumentEvent.EventType.REMOVE) {
			throw new BadOperationException("setLength only applies to eventtype 'REMOVE'");
		}
		if ((_offset+length)>_document.getLength()) {
			throw new BadLocationException("Offset+length out of range for this change/docment combination",_offset);
		}
		_length=length;
	}
	
	/**
	 * Sets the attribute set for this change. Only applies to CHANGE and INSERT changes.
	 * @param s
	 * @throws BadOperationException
	 */
	public void setAttributeSet(AttributeSet s) throws BadOperationException {
		if (_type==DocumentEvent.EventType.REMOVE) {
			throw new BadOperationException("setAttributeSet doesn't apply to 'REMOVE' events");
		}
		_set=s;
	}
}
