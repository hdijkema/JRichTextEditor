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

package nl.dykema.jxmlnote.styles;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;

import nl.dykema.jxmlnote.document.XMLNoteMark;

/**
 * This class extends the StyleConstants class with some extra style attributes.
 * 
 * @author hans
 *
 */
public class XMLNoteStyleConstants {
	
	static public final Object IdAttribute=(Object) new String("id");
	static public final Object ListFormat=(Object) new String("listformat");
	static public final Object MarkAttribute=(Object) new String("mark");
	
	static public String getId(AttributeSet s) { 
		return (String) s.getAttribute(IdAttribute);
	}
	
	static public void setId(MutableAttributeSet s,String id) {
		if (id==null) {
			s.removeAttribute(IdAttribute);
		} else {
			s.addAttribute(IdAttribute, id);
		}
	}
	
	static public String getListFormat(AttributeSet s) {
		return (String) s.getAttribute(ListFormat);
	}
	
	static public void setListFormat(MutableAttributeSet s,String format) {
		if (format==null) {
			s.removeAttribute(ListFormat);
		} else {
			s.addAttribute(ListFormat,format);
		}
	}
	
	static public Vector<XMLNoteMark> getMarks(AttributeSet s) {
		MarkSetProxy p=(MarkSetProxy) s.getAttribute(MarkAttribute);
		if (p!=null) {
			return p.markSet;
		} else {
			return null;
		}
	}
	
	static public void addMark(MutableAttributeSet s,XMLNoteMark m) {
		Vector<XMLNoteMark> marks=getMarks(s);
		if (marks==null) {
			marks=new Vector<XMLNoteMark>();
			marks.add(m);
			MarkSetProxy p=new MarkSetProxy();
			p.markSet=marks;
			s.addAttribute(MarkAttribute, p);
		} else if (!marks.contains(m)) {
			marks.add(m);
			MarkSetProxy p=new MarkSetProxy();
			p.markSet=marks;
			s.addAttribute(MarkAttribute,p);
		}
	}
	
	static public void removeMark(MutableAttributeSet s,XMLNoteMark m) {
		MarkSetProxy p=(MarkSetProxy) s.getAttribute(MarkAttribute);
		if (p!=null) {
			if (p.markSet.contains(m)) {
				p.markSet.remove(m);
				if (p.markSet.isEmpty()) {
					s.removeAttribute(MarkAttribute);
				}
			}
		}
	}
}


class MarkSetProxy {
	public Vector<XMLNoteMark> markSet;
}

