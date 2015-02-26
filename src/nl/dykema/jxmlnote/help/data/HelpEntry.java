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

package nl.dykema.jxmlnote.help.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Vector;

import nl.dykema.jxmlnote.document.XMLNoteDocument;

public class HelpEntry implements Serializable {

	/**
	 * Version 
	 */
	private static final long serialVersionUID = 1558595532476998565L;
	
	private String xmlnote;
	private Vector<URL> images;

	public void writeObject(ObjectOutputStream out) throws IOException {
		String classname = "HelpEntry";
		Long version = serialVersionUID;
		out.writeObject(classname);
		out.writeObject(version);
		out.writeObject(xmlnote);
		out.writeObject(images);
	}
	
	public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		String classname;
		Long version;
		classname = (String) in.readObject();
		if (!classname.equals("HelpEntry")) {
			throw new ClassNotFoundException(classname + " read instead of HelpEntry");
		} 
		version = (Long) in.readObject();
		if (version > serialVersionUID) {
			throw new ClassNotFoundException("Cannot read version "+version+", don't know how to process it");
		}
		xmlnote = (String) in.readObject();
		images = (Vector<URL>) in.readObject();
	}
	
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
