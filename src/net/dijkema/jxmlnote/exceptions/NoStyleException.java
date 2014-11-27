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

package net.dijkema.jxmlnote.exceptions;


/**
 * This exception is thrown when a XMLNoteStyle is being applied that doesn't exist
 * in the XMLNoteStyles object of the XMLNoteDocument. 
 * @author hans
 *
 */
public class NoStyleException extends Exception {
	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;

	public NoStyleException(String styleId) {
		super(String.format("There's no style with id '%s'",styleId));
	}
}
