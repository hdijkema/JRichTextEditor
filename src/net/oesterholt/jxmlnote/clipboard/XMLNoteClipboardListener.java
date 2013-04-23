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

package net.oesterholt.jxmlnote.clipboard;

import java.util.Vector;

import net.oesterholt.jxmlnote.document.XMLNoteDocument;
import net.oesterholt.jxmlnote.document.XMLNoteMark;
import net.oesterholt.jxmlnote.widgets.JXMLNotePane;


/**
 * This interface can be used to hook into the clipboard handling mechanism of the XMLNoteDocument.
 * It is a much easier way of transferring application specific information with XMLNoteDocuments
 * over the clipboard than the TransferHandler mechanism. 
 * <p>
 * It provides basic support for handing data transfer over the clipboard via the Meta Key mechanism
 * of the XMLNoteDocument. For more enhanced support you need to create your own transferhandlers on the
 * XMLNotePane.
 * <p> 
 * This method is only called for clipboard handling of type {@link XMLNoteDataFlavor}. Not for any
 * other (e.g. <code>text/html</code> or <code>text/plain</code> flavors.
 * <p> 
 * @author Hans Oesterholt-Dijkema
 *
 */
public interface XMLNoteClipboardListener {
	
	/**
	 *	The moment of action 
	 */
	public enum Moment 	{ BEFORE, AFTER };
	
	/**
	 * The type of export
	 */
	public enum Type	{ COPY, MOVE };
	
	/**
	 * Indicates whether marks should be pasted or not. This is only important
	 * when importData() is called with the BEFORE moment. 
	 */
	public enum MarkHandling { DO_NOT_PASTE, PASTE, FORCE_PASTE, NOOP };
	
	
	/**
	 * This method is called just before the document of <code>sourcePane</code> is serialized 
	 * to the clipboard (<code>moment==Moment.BEFORE</code>). At that moment one can add 
	 * his/hers own data to <code>exportDoc</code>.
	 * <p>
	 * Just after the document has been serialized to the clipboard, this function is
	 * also called (<code>moment==Moment.AFTER</code>). 
	 * <p>
	 * <code>sourcePane</code> can be used to among other things, to the original document.
	 * <p>
	 * @param sourcePane	The pane that initiated the copy/cut action.
	 * @param exportDoc		The document that has been prepared for export to the clipboard.
	 * @param moment		Just BEFORE or just AFTER the serialization to the clipboard of exportDoc has taken place.
	 * @param type			The type of clipboard action (COPY or MOVE) (MOVE is the same as CUT).
	 */
	public void exportToClipboard(JXMLNotePane sourcePane,XMLNoteDocument exportDoc,Moment moment,Type type);
	
	/**
	 * This method is called just before the <code>pasteDoc</code> is pasted into the given
	 * <code>XMLNoteDocument</code> of <code>targetPane</code> (<code>moment=Moment.BEFORE</code> 
	 * and just after it has been pasted (<code>moment=Moment.AFTER</code>)
	 * <p>
	 * Note. Pasting the document <code>source</code> into <code>doc</code> is done using
	 * the <code>copyInto</code> method of <code>targetPane.getXMLNoteDoc()</code>.
	 * <p>
	 * If the method is called with <code>Moment.BEFORE</code>, this method must return what to do
	 * with the XMLNoteMarks in the pasteDoc. Either it must paste them into the document straight away,
	 * or it must not paste them, which gives this method the possibility to do it its own way when
	 * it is called with <code>Moment.AFTER</code>. 
	 * <p>
	 * As there may be multiple clipboard listeners, if one clipboard listener returns DO_NOT_PASTE 
	 * before the document is pasted, XMLNoteMarks won't be copied into the target document. Otherwise,
	 * the default value of the target document <code>targetPane.getXMLNoteDoc().getAllowMarksPasted()</code>
	 * is used. The return of <code>FORCE_PASTE</code> overrides this, but not a return of <code>DO_NOT_PASTE</code>.
	 * <p>  
	 * @param pasteDoc		The document that has been serialized from the clipboard to paste, which will also contain all meta keys set on an <code>exportDoc</code>
	 * @param targetPane	The pane that receives the paste operation and that gives access to the document where pasteDoc will be copied into.
	 * @param offset		The target offset where pasteDoc will be pasted. 
	 * @param moment		Just BEFORE or just AFTER <code>pasteDoc</code> has been pasted.
	 * @return PASTE, if the marks in pasteDoc should be pasted; DO_NOT_PASTE, otherwise     
	 */
	public MarkHandling importData(XMLNoteDocument pasteDoc,JXMLNotePane targetPane,int offset,Moment moment);
	
	/**
	 * This method is called just before <code>importData()</code> is called with <code>Moment.AFTER</code>.
	 * It contains the adjusted xmlnote marks that have been returned from the target document's <code>copyInto()</code>
	 * method. Note. If the <code>copyInto()</code> operation has been vetoed, <code>preparedMarks</code> will be <b>null</b>.
	 * <p>
	 * @param pasteDoc			The document that has been pasted.
	 * @param preparedMarks		The marks that have been adjusted to the right offsets in the target document
	 * @param targetPane		The target pane with the target document that receives this paste
	 */
	public void importMarks(XMLNoteDocument pasteDoc,Vector<XMLNoteMark> preparedMarks,JXMLNotePane targetPane);

}
