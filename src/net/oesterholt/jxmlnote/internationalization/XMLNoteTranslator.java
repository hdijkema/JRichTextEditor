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

package net.oesterholt.jxmlnote.internationalization;

/**
 * This interface provides an interface for translating strings. JXMLNote uses this for localization.
 * All classes are instantiated with a <code>DefaultXMLNoteTranslator</code>, which provides an entry point
 * for localization. See {@DefaultXMLNoteTranslator}.
 * 
 * @author Hans Oesterholt
 *
 */
public interface XMLNoteTranslator {
	/**
	 * Translates textIn to the current locale. textIn will be in english.
	 * 
	 * @param textIn	The text to be translated
	 * @return String 	The translation of textIn 
	 */
	public String translate(String textIn);
	
	/**
	 * Same as <code>translate(textIn)</code>
	 * 
	 * @param textIn	The text to be translated
	 * @return String 	The translation of textIn 
	 */
	public String _(String textIn);
}
