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

package net.oesterholt.jxmlnote.document;

/**
 * This listener will be activated administrative document changes occur.
 * <code>JXMLNotePane</code>s will register this listener to <code>XMLNoteDocument</code>s, to 
 * be able to react to these administrative stuff (e.g. to update the caret position).
 * 
 * @author Hans Oesterholt-Dijkema
 *
 */
public interface DocumentAdminListener {
	
	/**
	 * This method will be called before a document is reset from XML. When this method returns <bold>true</bold>,
	 * the method will veto the reset, i.e. the reset will not occur. One veto is enough to prevent the 
	 * document from being reset.
	 * 
	 * @param e
	 * @return
	 */
	public boolean documentWillBeReset(DocumentAdminEvent e);
	
	/**
	 * This method will be called after a document has been reset from XML. 
	 * 
	 * @param e
	 */
	public void documentHasBeenReset(DocumentAdminEvent e);
	
	/**
	 * This method will be called before a document is cleared. When this method returns <bold>true</bold>,
	 * the method will veto the clear, i.e. the clear will not occur. One veto is enough to prevent the 
	 * document from being reset.
	 * 
	 * @param e
	 * @return
	 */
	public boolean documentWillBeCleared(DocumentAdminEvent e);
	
	/**
	 * This method will be called after a document has been cleared.
	 * 
	 * @param e
	 */
	public void documentHasBeenCleared(DocumentAdminEvent e);
	
	
	/**
	 * If the changed() state of the document changes (from true to false or from false to true),
	 * this event is fired. Use <code>e.changed()</code> to get the state.
	 * 
	 * @param e
	 */
	public void documentChangedState(DocumentAdminEvent e);
	
	
}
