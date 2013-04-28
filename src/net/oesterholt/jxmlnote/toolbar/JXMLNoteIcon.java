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

package net.oesterholt.jxmlnote.toolbar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

import net.oesterholt.jxmlnote.icons.Close;
import net.oesterholt.jxmlnote.icons.Document_new;
import net.oesterholt.jxmlnote.icons.Document_open;
import net.oesterholt.jxmlnote.icons.Document_save;
import net.oesterholt.jxmlnote.icons.Edit_copy;
import net.oesterholt.jxmlnote.icons.Edit_cut;
import net.oesterholt.jxmlnote.icons.Edit_paste;
import net.oesterholt.jxmlnote.icons.Edit_redo;
import net.oesterholt.jxmlnote.icons.Edit_select_all;
import net.oesterholt.jxmlnote.icons.Edit_undo;
import net.oesterholt.jxmlnote.icons.First;
import net.oesterholt.jxmlnote.icons.FlamencoIconAdapter;
import net.oesterholt.jxmlnote.icons.Format_indent_less;
import net.oesterholt.jxmlnote.icons.Format_indent_more;
import net.oesterholt.jxmlnote.icons.Help;
import net.oesterholt.jxmlnote.icons.InsertHyperlink;
import net.oesterholt.jxmlnote.icons.Last;
import net.oesterholt.jxmlnote.icons.Next;
import net.oesterholt.jxmlnote.icons.NoImage;
import net.oesterholt.jxmlnote.icons.Previous;
import net.oesterholt.jxmlnote.icons.Print;
import net.oesterholt.jxmlnote.icons.PrintPrefs;
import net.oesterholt.jxmlnote.icons.Quit;
import net.oesterholt.jxmlnote.icons.RemoveHyperlink;
import net.oesterholt.jxmlnote.icons.Zoom100;
import net.oesterholt.jxmlnote.icons.ZoomFitHeight;
import net.oesterholt.jxmlnote.icons.ZoomFitWidth;
import net.oesterholt.jxmlnote.icons.ZoomLess;
import net.oesterholt.jxmlnote.icons.ZoomMore;

/**
 * This icon class paints icons using draw functions instead of bitmaps.
 * 
 * @author Hans Oesterholt
 * 
 */
public class JXMLNoteIcon implements Icon {

	private int _width = 18;
	private int _height = 18;
	private String _iconType = "none";
	private boolean _selected = false;
	private JXMLNoteIconPainter _painter = null;
	private SizeProvider _provider = null;
	private DimensionProvider _dprovider = null;

	private void provideSize() {
		if (_dprovider != null) {
			this.setWHSize(_dprovider.buttonSize());
		} else if (_provider != null) {
			setWHSize(_provider.buttonSize());
		}
	}
	
	/**
	 * Interface that can be used to set the size of an JXMLNoteIcon dynamically
	 * <p>
	 * 
	 * @author Hans Oesterholt
	 */
	public interface SizeProvider {
		public int buttonSize();
	};
	
	/**
	 * Interface that can be used to set the size of an JXMLNoteicon dynamically. width and height individually
	 * @author hans Oesteholt
	 *
	 */
	public interface DimensionProvider {
		public Dimension buttonSize();
	}
	

	/**
	 * This function returns the current painter for an icon. Returns null, if
	 * no painter has been set.
	 * 
	 * @return
	 */
	public JXMLNoteIconPainter getPainter() {
		return _painter;
	}

	/**
	 * This function sets the selection state of the icon.
	 * 
	 * @param x
	 *            <code>true</code> means "selected"; <code>false</code>
	 *            otherwhise.
	 */
	public void setSelected(boolean x) {
		_selected = x;
	}

	/**
	 * This function returns the selected state of the icon.
	 * 
	 * @return <code>true</code> is "selected"; <code>false</code> otherwise.
	 */
	public boolean selected() {
		return _selected;
	}

	private void drawTextAttr(int x, int y, int w, int h, Graphics2D g,
			String _type) {
		Font F;

		Rectangle2D r = g.getFontMetrics().getStringBounds(_type, g);
		double sWidth = r.getWidth();
		double sHeight = r.getHeight();
		double currentPointSize = g.getFont().getSize2D();
		double scaleFactorW = w / sWidth;
		double scaleFactorH = h / sHeight;
		double newPointSize = currentPointSize
				* ((scaleFactorW < scaleFactorH) ? scaleFactorW : scaleFactorH);

		if (_type.equals("B")) {
			F = g.getFont().deriveFont(Font.BOLD, (float) newPointSize);
		} else if (_type.equals("U")) {
			F = g.getFont().deriveFont((float) newPointSize);
		} else if (_type.equals("I")) {
			F = g.getFont().deriveFont(Font.ITALIC, (float) newPointSize);
		} else {
			F = g.getFont();
		}

		g.setFont(F);
		FontMetrics met = g.getFontMetrics();
		// int l=met.getLeading();
		// int a=met.getAscent();
		double d = ((double) met.getDescent()) / 2.0;
		r = met.getStringBounds(_type, g);
		sHeight = r.getHeight();
		sWidth = r.getWidth();

		double yOffset = (h - sHeight) / 2 + d;
		double xOffset = (w - sWidth) / 2;
		// yOffset+=(double) g.getFontMetrics().getLeading();

		g.setColor(Color.DARK_GRAY);
		g.drawString(_type, (int) (x + xOffset), (int) (y + h - yOffset));
		if (_type.equals("U")) {
			g.drawLine((int) (x + xOffset - 2), (int) (y + h - yOffset + 2),
					(int) (x + xOffset + sWidth), (int) (y + h - yOffset + 2));
		}
	}

	private void drawList(int x, int y, int w, int _h, Graphics2D g,
			String _type) {
		int N = _h / 4;

		int i;
		int stepH = (_h / N);
		int indent = (int) (w * 0.1);
		int rw = w - indent;
		if (stepH <= 1) {
			stepH = 1;
		}
		int h = y + (stepH / 2 + 1);
		int R = stepH / 2;

		int type = 0;
		if (_type.equals("bullits")) {
			type = 0;
		} else if (_type.equals("numbers")) {
			type = 1;
		}

		if (type == 1) {
			Font F = g.getFont().deriveFont((float) 4.0);
			g.setFont(F);
		}

		g.setColor(Color.DARK_GRAY);

		for (i = 0; i < N; i++) {
			switch (type) {
			case 0: {
				int RW = rw;
				g.fillArc(x + indent, h - R / 2, R, R, 0, 360);
				g.drawLine(x + indent + 2 * R, h, x + RW, h);
				break;
			}
			case 1: {
				int M = g.getFontMetrics().getMaxAdvance();
				int H = g.getFontMetrics().getHeight() / 2;
				g.drawString(String.format("%d", (i + 1) % 10), x + indent, h
						+ H);
				g.drawLine(x + indent + M, h, x + rw, h);
				break;
			}
			}

			h += stepH;
		}
	}

	private void drawAlign(int x, int y, int w, int _h, Graphics2D g,
			String _type) {
		int N = _h / 2;

		int i;
		int stepH = (_h / N);
		int indent = (int) (w * 0.1);
		int rw = w - indent;
		if (stepH <= 1) {
			stepH = 1;
		}
		int h = y + (stepH / 2 + 1);

		int type;
		if (_type.equals("left")) {
			type = 0;
		} else if (_type.equals("right")) {
			type = 1;
		} else if (_type.equals("center")) {
			type = 2;
		} else {
			type = 3;
		}

		g.setColor(Color.DARK_GRAY);

		for (i = 0; i < N; i++) {
			double rnd = 0.3 * Math.random();
			switch (type) {
			case 0: {
				int RW = (int) (rw - (rw * rnd));
				g.drawLine(x + indent, h, x + RW, h);
				break;
			}
			case 1: {
				int M = (int) (rw * rnd);
				g.drawLine(x + indent + M, h, x + rw, h);
				break;
			}
			case 2: {
				int M = (int) (rw * rnd);
				int RW = (int) (rw - M);
				g.drawLine(x + indent + M, h, x + RW, h);
				break;
			}
			case 3: {
				g.drawLine(x + indent, h, x + rw, h);
				break;
			}
			}

			h += stepH;
		}
	}

	public static Graphics2D prepareG(int x, int y, int w, int h, int ww,
			int hh, Graphics2D gg) {
		Graphics2D g = (Graphics2D) gg.create();
		g.translate(x, y);
		g.scale(((float) w) / ((float) ww), ((float) h) / ((float) hh));
		return g;
	}

	private void drawIcon(FlamencoIconAdapter adapter, int x, int y, int w,
			int h, Graphics2D g) {
		Graphics2D gg = prepareG(x, y, w, h, adapter.getOrigWidth(), adapter
				.getOrigHeight(), g);
		adapter.paint(gg);
	}

	/**
	 * This function paints the icon. It is implements the paintIcon function of
	 * {@link Icon}. This function knows how to paint the following standard
	 * iconTypes:
	 * 
	 * <ul>
	 * <li>align-left,align-right,align-center,align-justify</li>
	 * <li>unordered-list,numbered-list</li>
	 * <li>font-bold,font-italic,font-underline</li>
	 * <li>style:h1,style:h2,style:h3,style:h4,style:par</li>
	 * <li>save,load</li>
	 * <ul>
	 * 
	 * If JXMLNoteIcon has been initialized with a {@link JXMLNoteIconPainter}.
	 * This {@link JXMLNoteIconPainter#paint} member will be called with the
	 * appropriate parameters.
	 * 
	 * If the iconType is not known and no {@link JXMLNoteIconPainter} as been
	 * given, the icon will be filled with the color red, indicating there is no
	 * icon.
	 * 
	 */
	public void paintIcon(Component c, Graphics _g, int x, int y) {
		provideSize();

		Graphics2D g = (Graphics2D) _g;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		x = x - 2;
		y = y - 2;

		if (_selected) {
			g.draw3DRect(x, y, _width + 2, _width + 2, false);
		}

		x += 2;
		y += 2;
		int w = _width - 2;
		int h = _height - 2;

		if (_painter != null) {
			_painter.paint(x, y, w, h, g, c, _iconType);
		} else {
			if (_iconType == "none") {
				g.setColor(Color.white);
				g.fillRect(x, y, _width, _width);
			} else if (_iconType.equals("align-left")) {
				drawAlign(x, y, w, h, g, "left");
			} else if (_iconType.equals("align-right")) {
				drawAlign(x, y, w, h, g, "right");
			} else if (_iconType.equals("align-center")) {
				drawAlign(x, y, w, h, g, "center");
			} else if (_iconType.equals("align-justify")) {
				drawAlign(x, y, w, h, g, "justify");
			} else if (_iconType.equals("unordered-list")) {
				drawList(x, y, w, h, g, "bullits");
			} else if (_iconType.equals("numbered-list")) {
				drawList(x, y, w, h, g, "numbers");
			} else if (_iconType.equals("font-bold")) {
				drawTextAttr(x, y, w, h, g, "B");
			} else if (_iconType.equals("font-italic")) {
				drawTextAttr(x, y, w, h, g, "I");
			} else if (_iconType.equals("font-underline")) {
				drawTextAttr(x, y, w, h, g, "U");
			} else if (_iconType.equals("style:h1")) {
				drawTextAttr(x, y, w, h, g, "h1");
			} else if (_iconType.equals("style:h2")) {
				drawTextAttr(x, y, w, h, g, "h2");
			} else if (_iconType.equals("style:h3")) {
				drawTextAttr(x, y, w, h, g, "h3");
			} else if (_iconType.equals("style:h4")) {
				drawTextAttr(x, y, w, h, g, "h4");
			} else if (_iconType.equals("style:par")) {
				drawTextAttr(x, y, w, h, g, "-");
			} else if (_iconType.startsWith("style:")) {
				// draw nothing
			} else if (_iconType.equals("new")) {
				drawIcon(NEW, x, y, w, h, g);
			} else if (_iconType.equals("save")) {
				drawIcon(SAVE, x, y, w, h, g);
			} else if (_iconType.equals("load")) {
				drawIcon(LOAD, x, y, w, h, g);
			} else if (_iconType.equals("copy-to-clipboard")) {
				drawIcon(COPY, x, y, w, h, g);
			} else if (_iconType.equals("cut-to-clipboard")) {
				drawIcon(CUT, x, y, w, h, g);
			} else if (_iconType.equals("paste-from-clipboard")) {
				drawIcon(PASTE, x, y, w, h, g);
			} else if (_iconType.equals("redo")) {
				drawIcon(REDO, x, y, w, h, g);
			} else if (_iconType.equals("undo")) {
				drawIcon(UNDO, x, y, w, h, g);
			} else if (_iconType.equals("select-all")) {
				drawIcon(SELECT_ALL, x, y, w, h, g);
			} else if (_iconType.equals("indent-more")) {
				drawIcon(INDENTMORE, x, y, w, h, g);
			} else if (_iconType.equals("indent-less")) {
				drawIcon(INDENTLESS, x, y, w, h, g);
			} else if (_iconType.equals("NoImage")) {
				drawIcon(NOIMAGE, x, y, w, h, g);
			} else if (_iconType.equals("quit")) {
				drawIcon(QUIT, x, y, w, h, g);
			} else if (_iconType.equals("help")) {
				drawIcon(HELP, x, y, w, h, g);
			} else if (_iconType.equals("zoom-100")
					|| _iconType.equals("zoom-100%")) {
				drawIcon(ZOOM_100, x, y, w, h, g);
			} else if (_iconType.equals("zoom-more")
					|| _iconType.equals("zoom-125%")
					|| _iconType.equals("zoom-150%")
					|| _iconType.equals("zoom-200%")) {
				drawIcon(ZOOM_MORE, x, y, w, h, g);
			} else if (_iconType.equals("zoom-less")
					|| _iconType.equals("zoom-75%")
					|| _iconType.equals("zoom-50%")) {
				drawIcon(ZOOM_LESS, x, y, w, h, g);
			} else if (_iconType.equals("zoom-fit-width")
					|| _iconType.equals("set-zoom")) {
				drawIcon(ZOOM_FIT_WIDTH, x, y, w, h, g);
			} else if (_iconType.equals("zoom-fit-height")) {
				drawIcon(ZOOM_FIT_HEIGHT, x, y, w, h, g);
			} else if (_iconType.equals("next")) {
				drawIcon(NEXT, x, y, w, h, g);
			} else if (_iconType.equals("previous")) {
				drawIcon(PREVIOUS, x, y, w, h, g);
			} else if (_iconType.equals("last")) {
				drawIcon(LAST, x, y, w, h, g);
			} else if (_iconType.equals("first")) {
				drawIcon(FIRST, x, y, w, h, g);
			} else if (_iconType.equals("close")) {
				drawIcon(CLOSE, x, y, w, h, g);
			} else if (_iconType.equals("print")) {
				drawIcon(PRINT, x, y, w, h, g);
			} else if (_iconType.equals("print-prefs")) {
				drawIcon(PRINT_PREFS, x, y, w, h, g);
			} else if (_iconType.equals("insert-hyperlink")) {
				drawIcon(INSERT_HYPERLINK, x, y, w, h, g);
			} else if (_iconType.equals("remove-hyperlink")) {
				drawIcon(REMOVE_HYPERLINK, x, y, w, h, g);
			} else {
				g.setColor(Color.red);
				g.fillRect(x, y, _width, _width);
			}
		}
	}

	/**
	 * @return _height the current icon height in (screen) pixels.
	 */
	public int getIconHeight() {
		provideSize();
		return _height;
	}

	/**
	 * @return _width the current icon width in (screen) pixels.
	 */
	public int getIconWidth() {
		provideSize();
		return _width;
	}

	/**
	 * Sets the size of an icon (width==height) to size. Does not override a
	 * SizeProvider!
	 * 
	 * @param size
	 *            The size of the width and the height (pixels).
	 */
	public void setWHSize(int size) {
		_width = size;
		_height = size;
	}
	
	/**
	 * Sets the size of an icon to dimension d. Does not override a SizeProvider!
	 * @param d
	 */
	public void setWHSize(Dimension d) {
		_width=d.width;
		_height=d.height;
	}

	/**
	 * Set a size provider on this icon. To be able to dynamically adjust the
	 * size.
	 * 
	 * @param provider
	 */
	public void setSizeProvider(SizeProvider provider) {
		_provider = provider;
	}
	
	/**
	 * Sets a dimension size provider. Overrides setSizeProvider for the SizeProvider interface.
	 * @param prov
	 */
	public void setSizeProvider(DimensionProvider prov) {
		_dprovider=prov;
	}
	

	// Constructors

	private static boolean initialized = false;
	private static FlamencoIconAdapter NEW;
	private static FlamencoIconAdapter SAVE;
	private static FlamencoIconAdapter LOAD;
	private static FlamencoIconAdapter CUT;
	private static FlamencoIconAdapter COPY;
	private static FlamencoIconAdapter PASTE;
	private static FlamencoIconAdapter UNDO;
	private static FlamencoIconAdapter REDO;
	private static FlamencoIconAdapter SELECT_ALL;
	private static FlamencoIconAdapter INDENTMORE;
	private static FlamencoIconAdapter INDENTLESS;
	private static FlamencoIconAdapter NOIMAGE;
	private static FlamencoIconAdapter QUIT;
	private static FlamencoIconAdapter HELP;
	private static FlamencoIconAdapter ZOOM_100;
	private static FlamencoIconAdapter ZOOM_MORE;
	private static FlamencoIconAdapter ZOOM_LESS;
	private static FlamencoIconAdapter ZOOM_FIT_WIDTH;
	private static FlamencoIconAdapter ZOOM_FIT_HEIGHT;
	private static FlamencoIconAdapter NEXT;
	private static FlamencoIconAdapter PREVIOUS;
	private static FlamencoIconAdapter LAST;
	private static FlamencoIconAdapter FIRST;
	private static FlamencoIconAdapter CLOSE;
	private static FlamencoIconAdapter PRINT;
	private static FlamencoIconAdapter PRINT_PREFS;
	private static FlamencoIconAdapter INSERT_HYPERLINK;
	private static FlamencoIconAdapter REMOVE_HYPERLINK;

	private void initIcons() {
		if (!initialized) {
			initialized = true;
			NEW = new Document_new();
			SAVE = new Document_save();
			LOAD = new Document_open();
			CUT = new Edit_cut();
			COPY = new Edit_copy();
			PASTE = new Edit_paste();
			UNDO = new Edit_undo();
			REDO = new Edit_redo();
			SELECT_ALL = new Edit_select_all();
			INDENTMORE = new Format_indent_more();
			INDENTLESS = new Format_indent_less();
			NOIMAGE = new NoImage();
			QUIT = new Quit();
			HELP = new Help();
			ZOOM_100 = new Zoom100();
			ZOOM_MORE = new ZoomMore();
			ZOOM_LESS = new ZoomLess();
			ZOOM_FIT_WIDTH = new ZoomFitWidth();
			ZOOM_FIT_HEIGHT = new ZoomFitHeight();
			NEXT = new Next();
			PREVIOUS = new Previous();
			LAST = new Last();
			FIRST = new First();
			CLOSE = new Close();
			PRINT = new Print();
			PRINT_PREFS=new PrintPrefs();
			INSERT_HYPERLINK=new InsertHyperlink();
			INSERT_HYPERLINK=new RemoveHyperlink();
		}
	}

	/**
	 * Constructs a JXMLNoteIcon of type <code>type</code> with width
	 * <code>w</code> and height <code>h</code>
	 * 
	 * @param type
	 * @param w
	 * @param h
	 */
	public JXMLNoteIcon(String type, int w, int h) {
		_width = w;
		_height = h;
		_iconType = type;
		initIcons();
	}

	/**
	 * 
	 * Constructs a JXMLNoteIcon of type <code>type</code> with standard width
	 * and height (18,18).
	 * 
	 * @param type
	 */
	public JXMLNoteIcon(String type) {
		_iconType = type;
		initIcons();
	}

	/**
	 * 
	 * Constructs a JXMLNoteIcon of type <code>type</code> with width
	 * <code>w</code> and height <code>h</code> and an associated
	 * {@link JXMLNoteIconPainter} that is called to paint the icon, instead of
	 * the standard paint functions.
	 * 
	 * @param type
	 * @param p
	 * @param w
	 * @param h
	 * 
	 */
	public JXMLNoteIcon(String type, JXMLNoteIconPainter p, int w, int h) {
		_painter = p;
		_iconType = type;
		_width = w;
		_height = h;
		initIcons();
	}

	/**
	 * Constructs a JXMLNoteIcon of type <code>type</code> with standard width
	 * and height (18,18) and an associated {@link JXMLNoteIconPainter} that is
	 * called to paint the icon, instead of the standard paint functions.
	 * 
	 * @param type
	 * @param p
	 */
	public JXMLNoteIcon(String type, JXMLNoteIconPainter p) {
		_painter = p;
		_iconType = type;
		initIcons();
	}
	
	protected JXMLNoteIcon(JXMLNoteIcon icn) {
		this._painter=icn._painter;
		this._iconType=icn._iconType;
		this._width=icn._width;
		this._height=icn._height;
		initIcons();
	}

}
