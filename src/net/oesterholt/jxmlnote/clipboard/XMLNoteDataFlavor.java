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

import java.awt.datatransfer.DataFlavor;

/**
 * This is the dataflavor to use with XMLNote native documents, for copy/paste actions between
 * JXMLNotePane's, even between different application contexts.
 * 
 * @author Hans Oesterholt-Dijkema
 *
 */
public class XMLNoteDataFlavor extends DataFlavor {
	
	private String _charset;
	
	public static String mimetype() {
		return "application/xmlnote+xml";
	}
	
	public XMLNoteDataFlavor() {
		this("String");
	}
	
	public String getParameter(String name) {
		if (name.equals("charset")) {
			return _charset;
		} else {
			return super.getParameter(name);
		}
	}
	
	public XMLNoteDataFlavor(String type) {
		this(	mimetype(),
				type.equals("String") ? "java.lang.String" : "java.nio.ByteBuffer",
				type.equals("String") ? "Unicode" : "UTF-8"
			);
	}
	
	protected XMLNoteDataFlavor(String mimetype,String _class,String charset) {
		super(mimetype()+";charset="+charset+";class="+_class,"XMLNote");
		_charset=charset;
	}
}
