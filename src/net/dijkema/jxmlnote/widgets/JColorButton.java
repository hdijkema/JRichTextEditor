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

package net.dijkema.jxmlnote.widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JColorChooser;

import net.dijkema.jxmlnote.toolbar.JXMLNoteIcon;
import net.dijkema.jxmlnote.toolbar.JXMLNoteIconPainter;


class Painter implements JXMLNoteIconPainter {
	
	private Color _color;
	
	public void setColor(Color c) {
		_color=c;
	}
	
	public Color getColor() {
		return _color;
	}

	public void paint(int x, int y, int w, int h, Graphics2D g,
			Component u, String iconType) {
		g.setColor(_color);
		g.fillRect(x, y, w, h);
	}
	
	public Painter(Color c) {
		_color=c;
	}
	
}


public class JColorButton extends JButton implements ActionListener {
	
	/**
	 * Version id
	 */
	private static final long serialVersionUID = 1L;
	
	private Painter 			_painter;
	private String  			_title;	
	private Set<ActionListener> _listeners;
	private int                 _id;
	
	public void addActionListener(ActionListener l) {
		_listeners.add(l);
	}
	
	public void removeActionListener(ActionListener l) {
		_listeners.remove(l);
	}

	public void actionPerformed(ActionEvent e) {
		Color c=JColorChooser.showDialog(this, _title, _painter.getColor());
		if (c!=null) {
			_painter.setColor(c);
			super.repaint();
			Iterator<ActionListener> it=_listeners.iterator();
			ActionEvent ee=new ActionEvent(this, _id++, "colorchanged");
			while(it.hasNext()) {
				it.next().actionPerformed(ee);
			}
		}
	}
	
	public void setValue(Color c) {
		_painter.setColor(c);
		super.repaint();
	}
	
	public Color getValue() {
		return _painter.getColor();
	}

	public JColorButton(Color c,String title) {
		super(new JXMLNoteIcon("coloricon",new Painter(c)));
		JXMLNoteIcon i=(JXMLNoteIcon) super.getIcon();
		_painter=(Painter) i.getPainter();
		_id=0;
		_listeners=new HashSet<ActionListener>();
		super.addActionListener(this);
	}

}
