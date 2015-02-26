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

package nl.dykema.jxmlnote.widgets.marks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;

import javax.swing.text.DefaultHighlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.LayeredHighlighter;
import javax.swing.text.View;

import nl.dykema.jxmlnote.document.XMLNoteMark;
import nl.dykema.jxmlnote.interfaces.MarkMarkupProvider;


public class MarkerHighlightPainter extends LayeredHighlighter.LayerPainter {
	
	public static int WIDTH_FOR_EMPTY_SPACE=6;
	
	XMLNoteMark									_mark;
	MarkMarkupProvider                          _provider;
	Color              							_prev;
	Color										_prev2;
	UnderlineHighlightPainter					_underlinePainter;
	MarkHighlightPainter						_markPainter;

	public Shape paintLayer(Graphics g, int p1, int p2, Shape s,JTextComponent c, View v) {
		if (_provider.type(_mark)==MarkMarkupProvider.MarkupType.UNDERLINED) {
			return _underlinePainter.paintLayer(g, p1, p2, s, c, v);
		} else if (_provider.type(_mark)==MarkMarkupProvider.MarkupType.BOTH) {
			Shape marker=_markPainter.paintLayer(g, p1, p2, s, c, v);
			Shape underl=_underlinePainter.paintLayer(g, p1, p2, s, c, v);
			return marker;	// Extend with underl
		} else {
			_markPainter.setColor(_provider.markColor(_mark));
			return _markPainter.paintLayer(g, p1, p2, s, c, v);
		}
	}

	public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c) {
		if (_provider.type(_mark)==MarkMarkupProvider.MarkupType.UNDERLINED) {
			_underlinePainter.paint(g, p0, p1, bounds, c);
		} else if (_provider.type(_mark)==MarkMarkupProvider.MarkupType.BOTH) {
			_markPainter.setColor(_provider.markColor(_mark));
			_markPainter.paint(g, p0, p1, bounds, c);
			_underlinePainter.paint(g, p0, p1, bounds, c);
		} else {
			_markPainter.setColor(_provider.markColor(_mark));
			_markPainter.paint(g, p0, p1, bounds, c);
		}
	}
	
	public MarkerHighlightPainter(XMLNoteMark mark,MarkMarkupProvider p) {
		_mark=mark;
		Color q=p.markColor(_mark);
		_prev=new Color(q.getRed(),q.getGreen(),q.getBlue(),q.getAlpha());
		q=p.markColor2(_mark);
		if (q!=null) {
			_prev2=new Color(q.getRed(),q.getGreen(),q.getBlue(),q.getAlpha());
		} else {
			_prev2=null;
		}
		_provider=p;
		_underlinePainter=new UnderlineHighlightPainter(_mark,_provider);
		_markPainter=new MarkHighlightPainter();
	}

}
