/* ******************************************************************************
 *
 *       Copyright 2008-2013 Hans Dijkema
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

package net.dijkema.jxmlnote.utils;


public class JXMLNoteConstants {
	
	private static int _indentPoints=36;
	
	public static String infoVersion() {
		return "1.0.0.9";
	}
	
	public static String infoWebSite() {
		return "https://github.com/hdijkema/JRichTextEditor";
	}
	
	public static String infoLicense() {
		return "(c) 2009-2013 Hans Dijkema";
	}
	
	public static int getStandardIndentPoints() {
		return _indentPoints;
	}

	public static void setStandardIndentPoints(int pt) {
		_indentPoints=pt;
	}


}
