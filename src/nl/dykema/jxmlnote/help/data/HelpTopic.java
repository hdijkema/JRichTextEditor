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
import java.util.UUID;
import java.util.Vector;

public class HelpTopic implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String key;
	private String topicId;
	
	public void writeObject(ObjectOutputStream out) throws IOException {
		String classname = "HelpTopic";
		Long version = serialVersionUID;
		out.writeObject(classname);
		out.writeObject(version);
		out.writeObject(name);
		out.writeObject(key);
		out.writeObject(topicId);
	}
	
	public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		String classname;
		Long version;
		classname = (String) in.readObject();
		if (!classname.equals("HelpTopic")) {
			throw new ClassNotFoundException(classname + " read instead of HelpTopic");
		} 
		version = (Long) in.readObject();
		if (version > serialVersionUID) {
			throw new ClassNotFoundException("Cannot read version "+version+", don't know how to process it");
		}
		name = (String) in.readObject();
		key = (String) in.readObject();
		topicId = (String) in.readObject();
	}

	public String getName() {
		return name;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String toString() {
		return getName();
	}
	
	public HelpTopic() {
		setName("");
		setKey("");
		UUID id = UUID.randomUUID();
		setTopicId(id.toString());
	}

	public HelpTopic(String name, String key) {
		setName(name);
		setKey(key);
		UUID id = UUID.randomUUID();
		setTopicId(id.toString());
	}

}
