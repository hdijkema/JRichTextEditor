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

package net.oesterholt.jxmlnote.help.data;

import java.io.Serializable;
import java.net.URL;
import java.util.Vector;

import net.oesterholt.jxmlnote.document.XMLNoteDocument;

public class HelpEntry implements Serializable {

	private String xmlnote;
	private Vector<URL> images;

	public String getXmlnote() {
		return xmlnote;
	}

	public void setXmlnote(String xmlnote) {
		this.xmlnote = xmlnote;
	}

	public Vector<URL> getImages() {
		return images;
	}

	public void setImages(Vector<URL> images) {
		this.images = images;
	}

	public HelpEntry() {
		xmlnote = XMLNoteDocument.emptyXML();
		images = new Vector<URL>();
	}
}
