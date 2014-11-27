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

package net.dijkema.jxmlnote.widgets.marks;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import javax.swing.SwingUtilities;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.LayeredHighlighter;
import javax.swing.text.Position;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.View;

import net.dijkema.jxmlnote.document.XMLNoteMark;
import net.dijkema.jxmlnote.interfaces.MarkMarkupProvider;


public class UnderlineHighlightPainter extends LayeredHighlighter.LayerPainter {

	private XMLNoteMark		   _mark;
    private MarkMarkupProvider _provider; 

    public void paint(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c) {
    }

    public void doText(final int offs0,final int offs1,JTextComponent c) {
        // This won't work. The software hangs on changing the character attributes
        // directly during highlight painting. So maybe it is possible to post an event
        // here. Yes, let's do that.
        // NO That doesn't provide us with the wanted results either!
    	// WE SOLVED THIS ELSEWHERE!
        
      /*Color textColor=_provider.textColor(_mark);
      if (textColor==null) { return; }
      Document doc=c.getDocument();
      if (doc instanceof StyledDocument) {
        final StyledDocument sdoc=(StyledDocument) doc;
        final SimpleAttributeSet set=new SimpleAttributeSet();
        StyleConstants.setForeground(set, textColor);
        Runnable r=new Runnable() {
            public void run() {
                sdoc.setCharacterAttributes(offs0, offs1, set, false);
            }
        };
        SwingUtilities.invokeLater(r);
      }*/
    }

    public Shape paintLayer(Graphics g, int offs0, int offs1, Shape bounds,JTextComponent c, View view) {
    	Color color;
    	if (_provider.type(_mark)==MarkMarkupProvider.MarkupType.BOTH) {
    		color=_provider.markColor2(_mark);
    		if (color==null) {
    			color=_provider.markColor(_mark);
    		}
    	} else {
    		color=_provider.markColor(_mark);
    	}
      
      g.setColor(color == null ? c.getSelectionColor() : color);

      Rectangle alloc = null;
      if (offs0 == view.getStartOffset() && offs1 == view.getEndOffset()) {
        if (bounds instanceof Rectangle) {
          alloc = (Rectangle) bounds;
        } else {
          alloc = bounds.getBounds();
        }
      } else {
        try {
          Shape shape = view.modelToView(offs0,Position.Bias.Forward, offs1,Position.Bias.Backward, bounds);
          alloc = (shape instanceof Rectangle) ? (Rectangle) shape : shape.getBounds();
        } catch (BadLocationException e) {
          return null;
        }
      }

      FontMetrics fm = c.getFontMetrics(c.getFont());
      int baseline = alloc.y + alloc.height - fm.getDescent() + 1;
      alloc.width=Math.max(alloc.width, MarkerHighlightPainter.WIDTH_FOR_EMPTY_SPACE);
      g.drawLine(alloc.x, baseline, alloc.x + alloc.width, baseline);
      g.drawLine(alloc.x, baseline + 1, alloc.x + alloc.width,baseline + 1);

      doText(offs0,offs1,c);

      return alloc;
    }

    public UnderlineHighlightPainter(XMLNoteMark m,MarkMarkupProvider p) {
    	_mark=m;
        _provider=p;
    }
}
