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

package net.oesterholt.jxmlnote.interfaces;

/**
 * Provides an interface for applications to get/set preferences
 * 
 * @author Hans Oesterholt-Dijkema
 */
public interface XMLNotePreferences {
	/**
	 * @param key		key to get
	 * @param _default	default value to return if key not present
	 * @return	the value for key
	 */
	public String getString(String key,String _default);
	
	/**
	 * @param key		key to get
	 * @param _default	default value to return if key not present
	 * @return	the value for key
	 */
	public int    getInt(String key,int _default);
	
	/**
	 * @param key		key to set
	 * @param value		value to store. null means erase value.
	 */
	public void put(String key,String value);
	
	/**
	 * @param key		key to set
	 * @param value		value to store. null means erase value.
	 */
	public void put(String key,Integer value);
}
