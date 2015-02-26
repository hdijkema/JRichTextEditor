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

package nl.dykema.jxmlnote.widgets;

import java.awt.Dimension;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * JSizeGroup makes all JComponents added to this group the same width or the same height
 * Usage: first add the components using <code>add()</code>, next call <code>align()</code>.
 *  
 * @author Hans Dijkema
 *
 */
public class JSizeGroup {
	
	public class Exception extends java.lang.Exception {
		/**
		 * Version
		 */
		private static final long serialVersionUID = 1L;

		public Exception(String msg) {
			super(msg);
		}
	}
	
	private Vector<JComponent> _components;
	
	static public int HORIZONTAL=1;
	static public int VERTICAL=2;
	
	private boolean groupWidth=false;
	private boolean groupHeight=false;
	
	/**
	 * Add component c to this sizegroup.
	 * @param c
	 */
	public void add(JComponent c) {
		_components.add(c);
	}
	
	/**
	 * Make all the components in this group the same size for the given dimension(s).
	 */
	public void align() {
		Iterator<JComponent> it=_components.iterator();
		Dimension d=new Dimension(0,0);
		while(it.hasNext()) {
			Dimension h=it.next().getPreferredSize();
			if (groupWidth) {
				if (h.getWidth()>d.getWidth()) { d.setSize(h.getWidth(),d.getHeight()); }
			} else { d.setSize(h.getWidth(),d.getHeight()); }
			if (groupHeight) {
				if (h.getHeight()>d.getHeight()) { d.setSize(d.getWidth(),h.getHeight()); }
			} else { d.setSize(d.getWidth(),h.getHeight()); }
			//System.out.println(d);
		}
		it=_components.iterator();
		String s="";
		while(it.hasNext()) {
			JComponent q=it.next();
			if (q instanceof JLabel) { 
				s=((JLabel) q).getText();
			}
			q.setPreferredSize(d);
			q.setMinimumSize(d);
		}
	}
	
	/**
	 * Construct the sizegroup. Using one or both of the types JSizeGroup.HORIZONTAL and JSizeGroup.VERTICAL. 
	 * E.g. <code>new JSizeGroup(JSizeGroup.VERTICAL|JSizeGroup.HORIZONTAL)</code> will make a sizegroup that
	 * works for both dimensions.
	 * @param type
	 */
	public JSizeGroup(int type)  {
		if ((type&HORIZONTAL)>0) { groupWidth=true; }
		if ((type&VERTICAL)>0) { groupHeight=true; }
		_components=new Vector<JComponent>();
	}

}
