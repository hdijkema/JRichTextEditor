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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Vector;
import java.util.WeakHashMap;

import javax.swing.Icon;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.Position;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

import net.oesterholt.jxmlnote.document.XMLNoteDocument;
import net.oesterholt.jxmlnote.document.XMLNoteImageIcon;
import net.oesterholt.jxmlnote.document.XMLNoteMark;
import net.oesterholt.jxmlnote.exceptions.BadStyleException;
import net.oesterholt.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.oesterholt.jxmlnote.interfaces.MarkMarkupProvider;
import net.oesterholt.jxmlnote.styles.XMLNoteStyleConstants;


public class XMLNoteEditorKit extends StyledEditorKit {

	private static final long serialVersionUID = 1L;
	
	private JXMLNotePane _pane=null;
	
	private WeakHashMap<Icon,Icon>  	_iconMap;
	private WeakHashMap<Icon,Double>	_zoomMap;
	
	public Icon getZoomedInstance(Icon c) {
		Double z=_zoomMap.get(c);
		Double zf=_pane.getZoomFactor();
		if (z==null || !z.equals(zf)) {
			_iconMap.put(c, deriveIconForZoom(c));
			_zoomMap.put(c,zf);
		} else if (z!=null) {
			if (c instanceof XMLNoteImageIcon) {
				XMLNoteImageIcon xicn=(XMLNoteImageIcon) c;
				Icon cc=_iconMap.get(c);
				if (cc!=null && cc instanceof XMLNoteImageIcon) {
					XMLNoteImageIcon nicn=(XMLNoteImageIcon) cc;
					if (!nicn.equalOrig(xicn)) {
						_iconMap.put(c, deriveIconForZoom(c));
						_zoomMap.put(c, zf);
					}
				}
			}
		}
		return _iconMap.get(c);
	}
	
	public Icon deriveIconForZoom(Icon c) {
        if (c instanceof XMLNoteImageIcon) {
        	XMLNoteImageIcon xicn=(XMLNoteImageIcon) c;
        	XMLNoteImageIcon nicn=xicn.getZoomedInstance(_pane,new XMLNoteImageIcon.LoadedListener() {
        		public void loaded() {
        			SwingUtilities.invokeLater(new Runnable() {
        				public void run() { _pane.repaint(); }
        			});
        		}
        	});
        	return nicn;
		} else {
			return c;
        }
	}

	public ViewFactory getViewFactory() {
		if (_pane==null) {
			DefaultXMLNoteErrorHandler.exception(new Exception("Unexpected: get of viewfactory before install of pane"));
			return null;
		} else {
	        return new XMLNoteViewFactory(_pane,this);
		}
	}
	
	public Document createDefaultDocument() {
		try {
			return new XMLNoteDocument();
		} catch (BadStyleException e) {
			DefaultXMLNoteErrorHandler.exception(e);
			return null;
		}
	}
	
	public void install(JEditorPane p) {
		if (p instanceof JXMLNotePane) {
			_pane=(JXMLNotePane) p;
		} else {
			DefaultXMLNoteErrorHandler.exception(new Exception("Unexpected: install of something else than a JXMLNotePane"));
		}
		super.install(p);
	}
	
	public void deinstall(JEditorPane p) {
		_pane=null;
		super.deinstall(p);
	}
	
	public XMLNoteEditorKit(JXMLNotePane p) {
		_pane=p;
		_iconMap=new WeakHashMap<Icon,Icon>();
		_zoomMap=new WeakHashMap<Icon,Double>();
	}
}

class XMLNoteViewFactory implements ViewFactory {

	private JXMLNotePane 		_pane;
	private XMLNoteEditorKit 	_kit;
	
	public View create(Element elem) {
		String kind = elem.getName();
		if (kind != null) {
			if (kind.equals(AbstractDocument.ContentElementName)) {
				return new MyLabelView(elem,_pane);
			}
			else if (kind.equals(AbstractDocument.ParagraphElementName)) {
				return new MyParagraphView(elem,_pane);
			}
			else if (kind.equals(AbstractDocument.SectionElementName)) {
				//return new ScaledView(elem, View.Y_AXIS);
				return new BoxView(elem,View.Y_AXIS);
			}
			else if (kind.equals(StyleConstants.ComponentElementName)) {
				return new ComponentView(elem);
			}
			else if (kind.equals(StyleConstants.IconElementName)) {
				//Element q=getScaledIcon(elem);
				return new MyIconView(elem,_kit);
			}
		}

		// default to text display
		return new LabelView(elem);
	}
	
	
	
	public JXMLNotePane getPane() {
		return _pane;
	}
	
	public XMLNoteViewFactory(JXMLNotePane p,XMLNoteEditorKit kit) {
		_pane=p;
		_kit=kit;
	}

}

class MyIconView extends View {

		private XMLNoteEditorKit 	_kit;
		private Icon				_icn;
		private Icon				_orig;
	
	    public MyIconView(Element elem,XMLNoteEditorKit kit) {
	        super(elem);
	        _kit=kit;
	        AttributeSet attr = elem.getAttributes();
	        _orig=StyleConstants.getIcon(attr);
	        _icn=_kit.getZoomedInstance(_orig);
	    }

	    public void paint(Graphics g, Shape a) {
	        Rectangle alloc = a.getBounds();
	        _icn=_kit.getZoomedInstance(_orig);
	        _icn.paintIcon(getContainer(), g, alloc.x, alloc.y);
	    }

	    public float getPreferredSpan(int axis) {
	    	_icn=_kit.getZoomedInstance(_orig);
	        switch (axis) {
	        case View.X_AXIS:
	            return _icn.getIconWidth();
	        case View.Y_AXIS:
	            return _icn.getIconHeight();
	        default:
	            throw new IllegalArgumentException("Invalid axis: " + axis);
	        }
	    }

	    public float getAlignment(int axis) {
	        switch (axis) {
	        case View.Y_AXIS:
	            return 1;
	        default:
	            return super.getAlignment(axis);
	        }
	    }

	    public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
	        int p0 = getStartOffset();
	        int p1 = getEndOffset();
	        if ((pos >= p0) && (pos <= p1)) {
	            Rectangle r = a.getBounds();
	            if (pos == p1) {
	                r.x += r.width;
	            }
	            r.width = 0;
	            return r;
	        }
	        throw new BadLocationException(pos + " not in range " + p0 + "," + p1, pos);
	    }

	    public int viewToModel(float x, float y, Shape a, Position.Bias[] bias) {
	        Rectangle alloc = (Rectangle) a;
	        if (x < alloc.x + (alloc.width / 2)) {
	            bias[0] = Position.Bias.Forward;
	            return getStartOffset();
	        }
	        bias[0] = Position.Bias.Backward;
	        return getEndOffset();
	    }

}

class MyParagraphView extends ParagraphView {

	JXMLNotePane _pane;
	Element		 _par;
	TabSet       _tabs;
	float        _zoom=-1.0f;
	
	protected TabSet getTabSet() {
		TabSet ts=StyleConstants.getTabSet(_par.getAttributes());
		if (ts!=null) {
			
			float zoom=(float) _pane.getZoomFactor();
			if (zoom!=_zoom || _tabs==null) {
				TabStop[] stp=new TabStop[ts.getTabCount()];
				int i,N;
				for(i=0,N=ts.getTabCount();i<N;i++) {
					TabStop s=ts.getTab(i);
					stp[i]=new TabStop(s.getPosition()*zoom,s.getAlignment(),s.getLeader());
				}
				_zoom=zoom;
				_tabs=new TabSet(stp);
			}
			return _tabs;
		} else {
			return null;
		}
	}
	
	public MyParagraphView(Element p,JXMLNotePane pn) {
		super(p);
		_pane=pn;
		_par=p;
	}
}

class MyLabelView extends LabelView {
	
	JXMLNotePane _pane;
	Element		 _elem;
	Color		 _textColor=null;
	
	private Font	_myFont=null;
	private float   _myPoints=-1.0f;
	private double  _myZoom=-1.0;
	private Font    _myDerived=null;
	
	public Font getFont() {
		Font f=super.getFont();
		float ps=f.getSize2D();
		double zoom=_pane.getZoomFactor();
		if (_myFont==null || ps!=_myPoints || zoom!=_myZoom || _myDerived==null) {
			_myFont=f;
			_myPoints=ps;
			_myZoom=zoom;
			_myDerived=_myFont.deriveFont((float) (ps*_myZoom));
		}
		return _myDerived;
	}
	
	// override the foreground colour, if (part of) this text is Marked 
	protected void setPropertiesFromAttributes() {
		super.setPropertiesFromAttributes();
		
	}
	
	public Color getForeground() {
		if (_elem!=null) {
			AttributeSet set=_elem.getAttributes();
			Vector<XMLNoteMark> marks=XMLNoteStyleConstants.getMarks(set);
			if (marks!=null && !marks.isEmpty()) {
				// catch the first mark. See documentation in XMLNoteDocument.
				XMLNoteMark mark=marks.get(0);
				MarkMarkupProvider prov=_pane.getMarkMarkupProviderMaker().create(mark.id(), mark.markClass());
				_textColor=prov.textColor(mark);
			} else {
				_textColor=null;
			}
		}
		
		if (_textColor!=null) {
			return _textColor;
		} else {
			return super.getForeground();
		}
	}
	
	public MyLabelView(Element e,JXMLNotePane pane) {
		super(e);
		_elem=e;
		_pane=pane;
	}
}

/*class MyIconView extends IconView {
	
	JXMLNotePane _pane;
	
	public static Element deriveIconForZoom(Element e,JXMLNotePane p) {
		return p.getXMLNoteDoc().deriveIconForZoom(e,p);
	}
	
	public MyIconView(Element e,JXMLNotePane pane) {
		_pane=pane;
	}
}*/

class ScaledView extends BoxView {
   
	public ScaledView(Element elem, int axis) {
        super(elem, axis);
    }

    public double getZoomFactor() {
    	XMLNoteViewFactory v=(XMLNoteViewFactory) super.getViewFactory();
        double scale = v.getPane().getZoomFactor();
        return scale;
    }

    public void paint(Graphics g, Shape allocation) {
        Graphics2D g2d = (Graphics2D) g;
        double zoomFactor = getZoomFactor();
        AffineTransform old = g2d.getTransform();
        g2d.scale(zoomFactor, zoomFactor);
        super.paint(g2d, allocation);
        g2d.setTransform(old);
    }

    public float getMinimumSpan(int axis) {
        float f = super.getMinimumSpan(axis);
        f *= getZoomFactor();
        return f;
    }

    public float getMaximumSpan(int axis) {
        float f = super.getMaximumSpan(axis);
        f *= getZoomFactor();
        return f;
    }

    public float getPreferredSpan(int axis) {
        float f = super.getPreferredSpan(axis);
        f *= getZoomFactor();
        return f;
    }

    protected void layout(int width, int height) {
    	double zoom=getZoomFactor();
        super.layout((int) (((double) width)/zoom),(int) (((double) height)/zoom));
    }

    public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
    	Rectangle alloc;
    	double zoomFactor = getZoomFactor();
        alloc = a.getBounds();
        Shape s = super.modelToView(pos, alloc, b);
        alloc = s.getBounds();
        double x=alloc.getX()*zoomFactor;
        double y=alloc.getY()*zoomFactor;
        double w=alloc.getWidth()*zoomFactor;
        double h=alloc.getHeight()*zoomFactor;
        alloc.x=(int) Math.round(x);
        alloc.y=(int) Math.round(y);
        alloc.width=(int) Math.round(w);
        alloc.height=(int) Math.round(h);
        return alloc;
    }

    public int viewToModel(float x, float y, Shape a,Position.Bias[] bias) {
        double zoomFactor = getZoomFactor();
        Rectangle alloc = a.getBounds();
        x /= zoomFactor;
        y /= zoomFactor;
        double xx=alloc.getX()/zoomFactor;
        double yy=alloc.getY()/zoomFactor;
        double w=alloc.getWidth()/zoomFactor;
        double h=alloc.getHeight()/zoomFactor;
        //alloc.x /= zoomFactor;
        //alloc.y /= zoomFactor;
        //alloc.width /= zoomFactor;
        //alloc.height /= zoomFactor;
        alloc.x=(int) Math.round(xx);
        alloc.y=(int) Math.round(yy);
        alloc.width=(int) Math.round(w);
        alloc.height=(int) Math.round(h);

        return super.viewToModel(x, y, alloc, bias);
    }

}

