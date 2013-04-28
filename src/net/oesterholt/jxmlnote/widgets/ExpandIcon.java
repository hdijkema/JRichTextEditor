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

package net.oesterholt.jxmlnote.widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.Icon;

public class ExpandIcon implements Icon {

	private int 	 height=10;
	private int 	 width=10;
	private boolean  expanded=false;
	private Color    color=Color.gray;
	private Polygon _p=new Polygon();
	private int      _translateY=0;
	private int		 _translateX=0;
	
	public ExpandIcon() {
		this(true);
	}
	
	public ExpandIcon(boolean exp) {
		this(10,exp);
	}
	
	public ExpandIcon(int edge,boolean exp) {
		_p.addPoint(0,0);
		_p.addPoint(0,0);
		_p.addPoint(0,0);
		expanded=exp;
		height=edge;
		width=edge;
	}
	
	public void setExpanded(boolean b) {
		expanded=b;
	}
	
	public int getIconHeight() {
		return height; 
	}

	public int getIconWidth() {
		return width; 
	}

	private void drawRight(int x,int y, int w,int h, Graphics2D g) {
		g.setColor(color);
		_p.xpoints[0]=x;_p.ypoints[0]=y;
		_p.xpoints[1]=x+w;_p.ypoints[1]=y+(h/2);
		_p.xpoints[2]=x;_p.ypoints[2]=y+h;
		g.fillPolygon(_p);
	}

	private void drawDown(int x,int y, int w,int h, Graphics2D g) {
		g.setColor(color);
		_p.xpoints[0]=x;_p.ypoints[0]=y;
		_p.xpoints[1]=x+w;_p.ypoints[1]=y;
		_p.xpoints[2]=x+(w/2);_p.ypoints[2]=y+h;
		g.fillPolygon(_p);
	}
	
	public void translateY(int ty) {
		_translateY=ty;
	}
	
	public void translateX(int tx) {
		_translateX=tx;
	}
	
	public void paintIcon(Component c, Graphics _g, int x, int y) {
		
		Graphics2D g=(Graphics2D) _g;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		x+=_translateX;
		y+=_translateY;
		/*int w=width-2;
		int h=height-2;*/
		int w=width-_translateX;
		int h=height-_translateY;
		
		if (expanded) {
			drawDown(x,y,w,h,g);
		} else {
			drawRight(x,y,w,h,g);
		}
	}
	
}
