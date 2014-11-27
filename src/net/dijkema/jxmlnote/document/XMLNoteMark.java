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


/**
 * This class implements a Mark. The mark is constructed from the internals of
 * {@link JXMLNoteDocument} or {@link JXMLNoteConstants}. 
 * 
 * This class has three protected methods: 	<code>setMarkOffset(Integer o)</code>,
 * <code>setMarkText(String s)</code> and <code>addMarkText(String s)</code>.
 * 
 * @author Hans Dijkema
 *
 */
public abstract class XMLNoteMark {
	
	private String  			_id;
	private String  			_class;
	
	/**
	 * 
	 * @return the class of mark, defaults to null.
	 */
	public String markClass() {
		return _class;
	}
	
	/**
	 * Sets the mark class to c;
	 * @param c
	 */
	public void markClass(String c) {
		_class=c;
	}
	
	/**
	 * 
	 * @return String The <code>text content</code> of the mark.
	 */
	public abstract String content();
	
	/**
	 * 
	 * @return String The <code>id</code> of the mark
	 */
	public String id() {
		return _id;
	}
	
	/**
	 * 
	 * @return Returns the offset of the mark in the text.
	 */
	public abstract Integer offset();
	
	/**
	 * 
	 * @return Returns the end offset in the document of this Mark
	 */
	public abstract Integer endOffset();
	
	/**
	 * @return <b>true</b>, if endOffset()==offset().
	 */
	public boolean isEmpty() {
		return offset().equals(endOffset());
	}
	
	protected void setId(String id) {
		_id=id;
	}
	
	/**
	 * 
	 * @param e The JXMLNoteMark to compare with the this mark
	 * @return boolean true, if they are equal. false, otherwise.
	 */
	public boolean equals(XMLNoteMark e) {
		return _id.equals(e.id()) && offset().equals(e.offset()) && endOffset().equals(e.endOffset()) && content().equals(e.content()); 
	}

	
	public String toString() {
		return id()+";"+markClass()+";"+offset().toString()+";"+endOffset().toString();
	}
	
	/**
	 * Constructs an XMLNoteMark with the given id and a <b>null</b> class.
	 * 
	 * @param _i	The id of the mark.
	 */
	public XMLNoteMark(String _i) {
		this(_i,null);
	}
	
	/** 
	 * Constructs an XMLNoteMark with given id and class
	 * 
	 * @param _i		The id of the mark
	 * @param _cl		The class of the mark (my be null)
	 */
	public XMLNoteMark(String _i,String _cl) {
		_id=_i;
		_class=_cl;
	}
}
