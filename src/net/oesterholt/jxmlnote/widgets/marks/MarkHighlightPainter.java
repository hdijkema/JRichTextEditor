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

package net.oesterholt.jxmlnote.widgets.marks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import javax.swing.text.View;

public class MarkHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
	
	private Color _color;
	
	public Rectangle calcShape(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c, View view) {
		Rectangle r;

		if (offs0 == view.getStartOffset() && offs1 == view.getEndOffset()) {
			// Contained in view, can just use bounds.
			if (bounds instanceof Rectangle) {
				r = (Rectangle) bounds;
			}
			else {
				r = bounds.getBounds();
			}
		}
		else {
			// Should only render part of View.
			try {
				// --- determine locations ---
				Shape shape = view.modelToView(offs0, Position.Bias.Forward,offs1,Position.Bias.Backward,bounds);
				r = (shape instanceof Rectangle) ?
						(Rectangle) shape : shape.getBounds();
			} catch (BadLocationException e) {
				// can't render
				r = null;
			}
		}

		if (r != null) {
			r.width = Math.max(r.width, MarkerHighlightPainter.WIDTH_FOR_EMPTY_SPACE);
		}
		
		return r;
	}
	
	public Shape paintLayer(Graphics g, int offs0, int offs1,Shape bounds, JTextComponent c, View view) {
		
		Color color = getColor();if (color==null) { color=c.getSelectionColor(); }
		Color selectionColor=getSelectionColor();if (selectionColor==null) { selectionColor=c.getSelectionColor(); }
		
		//System.out.println("offs0,offs1="+offs0+","+offs1);

		int start=c.getSelectionStart();
		int end=c.getSelectionEnd();
		
		Rectangle r;
		
		if (start==end) {
			g.setColor(color);
			r=calcShape(g,offs0,offs1,bounds,c,view);
			g.fillRect(r.x, r.y, r.width, r.height);
		} else {
			// determine max three ranges
			int s1=-1,e1=-1,s2=-1,e2=-1,s3=-1,e3=-1;
			Color c1=null,c2=null,c3=null;
			if (offs1<=start || offs0>=end) {		// out of selection range
				s1=offs0;e1=offs1;c1=color;
			} else if (offs0>=start) {
				s1=offs0;e1=Math.min(offs1, end);c1=selectionColor;
				if (offs1>end) {
					s2=end;e2=offs1;c2=color;
				}
			} else if (offs0<start) {
				s1=offs0;e1=Math.min(offs1,start);c1=color;
				if (offs1>start) {
					s2=start;e2=Math.min(offs1,end);c2=selectionColor;
					if (offs1>end) {
						s3=end;e3=offs1;c3=color;
					}
				}
			}
			
			r=calcShape(g,s1,e1,bounds,c,view);
			g.setColor(c1);
			g.fillRect(r.x, r.y, r.width, r.height);
			if (s2!=-1) { 
				r=calcShape(g,s2,e2,bounds,c,view);
				g.setColor(c2);
				g.fillRect(r.x, r.y, r.width, r.height);
				if (s3!=-1) {
					r=calcShape(g,s3,e3,bounds,c,view);
					g.setColor(c3);
					g.fillRect(r.x, r.y, r.width, r.height);
				}
			}
			r=calcShape(g,offs0,offs1,bounds,c,view);
		}

		return r;
	}

	
	public Color getColor() {
		return _color;
	}
	
	public Color getSelectionColor() {
		return _color.darker();
	}
	
	public void setColor(Color c) {
		_color=c;
	}
	
	public MarkHighlightPainter() {
		this(Color.yellow);
	}
	
	public MarkHighlightPainter(Color c) {
		super(c);
		_color=c;
	}
	
}
