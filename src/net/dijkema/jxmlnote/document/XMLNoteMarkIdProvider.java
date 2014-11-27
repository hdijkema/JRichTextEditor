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
 * This interface is used to get new ids for existing marks in an XMLNoteDocument.
 * 
 * @author hans
 *
 */
public interface XMLNoteMarkIdProvider {
	/**
	 * This method should provide a new id (which may be the same as the current)
	 * for a given mark.  If <b>null</b> is returned, or the id returned is equal to
	 * the current id of the mark, nothing will change. 
	 * 
	 * @param mark		The mark for which a new id can be given
	 * @return			Returns the new id, or <b>null</b> 
	 */
	public String getNewId(XMLNoteMark mark);
}
