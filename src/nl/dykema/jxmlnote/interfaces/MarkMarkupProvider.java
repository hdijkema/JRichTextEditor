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

package nl.dykema.jxmlnote.interfaces;

import java.awt.Color;

import nl.dykema.jxmlnote.document.XMLNoteMark;

public interface MarkMarkupProvider {
	
	public enum MarkupType { 
		UNDERLINED,
		MARKER,
		BOTH
	};
	
	/**
	 * This is a listener interface for color changes in the MarkMarkupProvider.
	 * Documents can listen to changes and update the views accordingly
	 * @author hans
	 *
	 */
	public interface ChangeListener {
		/**
		 * Called when the markup provider has changed its working .
		 */
		public void markMarkupChanged();
	}

	/**
	 * Add a listener for color changes to this MarkMarkupProvider
	 * @param l
	 */
	public void addChangeListener(ChangeListener l);
	
	/**
	 * Remove a previously added listener.
	 * @param l
	 */
	public void removeChangeListener(ChangeListener l);
	
	
	/**
	 * Defines the color for the markup of the given mark. 
	 * @return
	 */
	public Color markColor(XMLNoteMark m);
	
	/**
	 * Returns the second color for the markup of the given mark.
	 * The second color is only relevant with the 'BOTH' type and
	 * is for the DefaultMarkupProvider the color of the underline 
	 * highlighter.
	 *  
	 * @param m
	 * @return
	 */
	public Color markColor2(XMLNoteMark m);

    /**
     * Defines the color for the text of the given mark.
     * @param m
     * @return the color, or <b>null</b> if you don't want to touch the default color.
    */
    public Color textColor(XMLNoteMark m);

	/**
	 * Returns the type of markup for the given mark, i.e. MARKER or UNDERLINED.
	 * @return
	 */
	public MarkupType type(XMLNoteMark m);
}
