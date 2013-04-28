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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.TextUI;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import javax.swing.text.View;

import net.oesterholt.jxmlnote.document.XMLNoteDocument;
import net.oesterholt.jxmlnote.interfaces.UpdateViewListener;
import net.oesterholt.jxmlnote.utils.DPIAdjuster;


/**
 * A ruler that shows the tabs for the current paragraph, as well as allowing
 * the user to manipulate the tabs.
 *
 * @author Scott Violet
 */
public class JXMLNoteRuler extends JPanel implements CaretListener, UpdateViewListener {
	
	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;

	// Some defines used in painting the ruler.
	protected int DPI = DPIAdjuster.getScreenDPI();
	protected int H_DPI = DPI / 2;
	protected int Q_DPI = DPI / 4;
	protected int E_DPI = DPI / 8;

	// Sizes for drawing tabs.
	protected static final int TabSize = 2;
	protected static final int TabWidth = 6;
	protected static final int TabHeight = 4;

	/** Shared instance of default border. */
	protected static final Border DefaultBorder = new RulerBorder();

	/** TextPane showing tabs for. */
	private JTextPane     textPane;
	/** Current TabSet showing. */
	private TabSet        tabs;

	/** Current paragraph element at character position. */
	private Element       paragraph;
	/** Offset to start drawing tabs from. */
	private int           xOffset;
	/** If false, the value of xOffset is not valid. */
	private boolean       validOffset;

	/** Font using for the units. */
	private Font          unitsFont;
	/** Total font height. */
	private int           fontHeight;
	/** Font ascent. */
	private int           fontAscent;


	public JXMLNoteRuler() {
		{ 
			JTextField f=new JTextField();
			setBackground(f.getBackground());
		}
		

		//MouseInputListener ml = createMouseInputListener();
		//if (ml != null) {
		//	addMouseListener(ml);
		//	addMouseMotionListener(ml);
		//}
		Border             border = createBorder();

		if (border != null) {
			setBorder(border);
		}
	}

	public JXMLNoteRuler(JTextPane text) {
		this();
		setTextPane(text);
	}

	/**
	 * Sets the text pane tabs are rendered for.
	 */
	public void setTextPane(JTextPane text) {
		if (textPane != null) {
			textPane.removeCaretListener(this);
			if (textPane instanceof JXMLNotePane) {
				((JXMLNotePane) textPane).removeUpdateViewListener(this);
			}
		}
		textPane = text;
		if (text != null) {
			text.addCaretListener(this);
			if (textPane instanceof JXMLNotePane) {
				((JXMLNotePane) textPane).addUpdateViewListener(this);
			}
			updateTabSet(text.getSelectionStart());
		}
		else {
			updateTabSet(0);
		}
	}

	/**
	 * Gets the text pane tabs are being rendered for.
	 */
	public JTextPane getTextPane() {
		return textPane;
	}

	/**
	 * Called when the caret position is updated.
	 *
	 * @param e the caret event
	 */
	public void caretUpdate(CaretEvent e) {
		updateTabSet(Math.min(e.getDot(), e.getMark()));
	}

	/**
	 * Resets the TabSet, which determines what to display, to be
	 * the TabSet in the Paragraph Element at <code>charPosition</code>.
	 */
	protected void updateTabSet(int charPosition) {
		JTextPane        text = getTextPane();
		TabSet           newTabs;

		if (text != null) {
			Element      pe = text.getStyledDocument().
			getParagraphElement(charPosition);

			if (pe != paragraph) {
				Integer     newOffset = determineOffset(pe);

				paragraph = pe;
				newTabs = StyleConstants.getTabSet(pe.getAttributes());
				if (newOffset == null) {
					validOffset = false;
				}
				else if (newOffset.intValue() != xOffset) {
					validOffset = true;
					xOffset = newOffset.intValue();
					if (tabs == newTabs) {
						repaint();
					}
				}
			}
			else {
				newTabs = tabs;
			}
		}
		else {
			newTabs = null;
		}
		if (tabs != newTabs) {
			tabs = newTabs;
			repaint();
		}
	}

	/**
	 * Sets the tabs the receiver represents, forces a repaint.
	 */
	protected void setTabSet(TabSet tabs) {
		this.tabs = tabs;
		repaint();
	}

	/**
	 * Returns the current TabSet, which may be null.
	 */
	protected TabSet getTabSet() {
		return tabs;
	}

	/**
	 * Returns the offset, along the x axis, tabs are to start from.
	 */
	protected int getXOffset() {
		if (!validOffset && getParagraphElement() != null) {
			Integer     offset = determineOffset(getParagraphElement());

			if (offset != null) {
				xOffset = offset.intValue();
				validOffset = true;
				// Force a complete repaint.
				repaint();
			}
			else {
				// Not valid offset, return 0.
				return 0;
			}
		}
		return xOffset;
	}

	/**
	 * Returns the current paragraph element. If the selection extends
	 * across multiple paragraphs this will return the first paragraph.
	 */
	protected Element getParagraphElement() {
		return paragraph;
	}

	//
	// Painting methods
	//

	/**
	 * Messaged to paint the Component, will fill the background and 
	 * message paintUnits and paintTabs.
	 */
	protected void paintComponent(Graphics g) {
		Rectangle        clip = g.getClipBounds();
		Insets           insets = getInsets();

		updateFontIfNecessary();
		g.setColor(getBackground());
		g.fillRect(clip.x, clip.y, clip.width, clip.height);
		paintUnits(g, clip, insets);
		paintTabs(g, clip, insets);
	}

	/**
	 * Paints the unit indicators.
	 */
	protected void paintUnits(Graphics g, Rectangle clip, Insets insets) {
		int        xOffset = getXOffset();
		int        fontY = getUnitsFontAscent();
		int        midY = getUnitsFontHeight() / 2;
		int        dpiOffset = xOffset % DPI;
		double 	   zoom=1.0f;
		
		if (getTextPane() instanceof JXMLNotePane) {
			zoom=((JXMLNotePane) getTextPane()).getZoomFactor();
		}

		if (insets != null) {
			fontY += insets.top;
			midY += insets.top;
		}
		g.setColor(getUnitsColor());
		g.setFont(getUnitsFont());

		FontMetrics fm = g.getFontMetrics();
		
		int nDPI=(int) Math.round(zoom*DPI);
		if ((nDPI%2)==1) { nDPI+=1; }
		int nE_DPI=nDPI/8;
		int nH_DPI=nDPI/2;
		int nQ_DPI=nDPI/4;
		

		for (int x = Math.max(xOffset, clip.x / nDPI * nDPI + dpiOffset),
				maxX = clip.x + clip.width; x <= maxX; x += nE_DPI) {
			int tempX = x - dpiOffset;
			if (tempX % nDPI == 0) {
				String     numString = Integer.toString((x - xOffset) / nDPI);

				g.drawString(numString, x -
						fm.stringWidth(numString) / 2, fontY);
			}
			else if (tempX % nH_DPI == 0) {
				g.drawLine(x, midY - 3, x, midY + 3);
			}
			else if (tempX % nQ_DPI == 0) {
				g.drawLine(x, midY - 2, x, midY + 2);
			}
			else {
				g.drawLine(x, midY - 1, x, midY + 1);
			}
		}
	}

	/**
	 * Paints the tabs.
	 */
	protected void paintTabs(Graphics g, Rectangle clip, Insets insets) {
		int        xOffset = getXOffset();
		TabSet     tabs = getTabSet();
		int        lastX = clip.x - 10;
		int        maxX = clip.x + clip.width + 10;
		int        maxY = getUnitsFontHeight() + TabHeight;
		double 	   zoom=1.0f;
		
		if (getTextPane() instanceof JXMLNotePane) {
			zoom=((JXMLNotePane) getTextPane()).getZoomFactor();
		}

		if (insets != null) {
			maxY += insets.top;
		}
		
		int nDPI=(int) Math.round(zoom*DPI);
		if ((nDPI%2)==1) { nDPI+=1; }
		
		if (tabs == null) {
			g.setColor(getSynthesizedTabColor());
			// Paragraph treats a null tabset as a tab at every 72 pixels.
			// Different implementations of View used to represent a
			// Paragraph may not due this.
			lastX = Math.max(xOffset, lastX / nDPI * nDPI + xOffset % nDPI);
			while (lastX <= maxX) {
				paintTab(g, clip, null, (float)lastX, maxY,
						TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
				lastX += DPI;
			}
		}
		else {
			TabStop     tab;
			TabStop	[]_tabs=new TabStop[tabs.getTabCount()];
			int i,N;
			for(i=0,N=tabs.getTabCount();i<N;i++) {
				TabStop st=tabs.getTab(i);
				_tabs[i]=new TabStop((int) Math.round(st.getPosition()*zoom),st.getAlignment(),st.getLeader());
			}
			TabSet ttabs=new TabSet(_tabs);

			g.setColor(getTabColor());
			do {
				tab = ttabs.getTabAfter((float)lastX + .01f);
				if (tab != null) {
					lastX = (int)tab.getPosition() + xOffset;
					if (lastX <= maxX) {
						paintTab(g, clip, tab, (float)lastX,
								maxY, tab.getAlignment(),
								tab.getLeader());
					}
					else {
						tab = null;
					}
				}
			} while (tab != null);
		}
	}

	/**
	 * Paints a particular tab. <code>tab</code> may be null, indicating
	 * a synthesized tab is being painted.
	 */
	protected void paintTab(Graphics g, Rectangle clip, TabStop tab,
			float position, int maxY, int alignment,
			int leader) {
		int iPos = (int)position;

		switch (alignment) {
		case TabStop.ALIGN_LEFT:
			g.fillRect(iPos, maxY - TabHeight, TabSize, TabHeight);
			g.fillRect(iPos, maxY - TabSize, TabWidth + TabSize, TabSize);
			break;
		case TabStop.ALIGN_RIGHT:
			g.fillRect(iPos, maxY - TabHeight, TabSize, TabHeight);
			g.fillRect(iPos - TabWidth, maxY - TabSize, TabWidth, TabSize);
			break;
		case TabStop.ALIGN_DECIMAL:
			g.fillRect(iPos, maxY - TabHeight - TabSize - 2, TabSize, TabSize);
		case TabStop.ALIGN_CENTER:
			g.fillRect(iPos, maxY - TabHeight, TabSize, TabHeight);
			g.fillRect(iPos - TabWidth, maxY - TabSize, TabWidth * 2 + TabSize,
					TabSize);
			break;
		default:
			break;
		}
	}

	/**
	 * Returns the color to use for the units and ticks.
	 */
	protected Color getUnitsColor() {
		return Color.black;
	}

	/**
	 * Returns the Font to use for the units. Override this to specify a
	 * different font.
	 */
	protected Font getUnitsFont() {
		return getFont();
	}

	/**
	 * Returns the color to draw the actual tabs in.
	 */
	protected Color getTabColor() {
		return Color.black;
	}

	/**
	 * Returns the color to draw generated tabs in (tabs are generated if
	 * there is no TabSet set on a particular Element).
	 */
	protected Color getSynthesizedTabColor() {
		return Color.lightGray;
	}

	//
	// Component methods
	//

	public Dimension getPreferredSize() {
		updateFontIfNecessary();

		Insets     insets = getInsets();

		if (insets != null) {
			return new Dimension(insets.left + insets.right + 10,
					insets.top + insets.bottom +
					getUnitsFontHeight() + TabHeight);
		}
		return new Dimension(10, getUnitsFontHeight());
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	/**
	 * The ascent of the units font.
	 */
	protected int getUnitsFontAscent() {
		return fontAscent;
	}

	/**
	 * Returns the height of the tray.
	 */
	protected int getUnitsFontHeight() {
		return fontHeight;
	}

	/**
	 * Updates font height information.
	 */
	private void updateFontIfNecessary() {
		Font         font = getUnitsFont();

		if (unitsFont != font) {
			fontHeight = fontAscent = 0;
			if (font != null) {
				Toolkit       tk = getToolkit();

				if (tk != null) {
					FontMetrics fm = tk.getFontMetrics(font);

					if (fm != null) {
						fontHeight = fm.getHeight();
						fontAscent = fm.getAscent();
						unitsFont = font;
					}
				}
			}
		}
	}

	/**
	 * Determines the offset (along the x axis) from which tabs are to begin.
	 * This is obtained from the bounds of the View that represents
	 * <code>paragraph</code>. A return value of null indicates the offset
	 * could not be obtained.
	 */
	protected Integer determineOffset(Element paragraph) {
		JTextPane          text = getTextPane();

		if (text != null) {
			// This is a workaround to avoid a NullPointerException that
			// *** THIS WORKAROUND MADE THIS CODE THROW A NULLPOINTEREXCEPTION!
			// is fixed in post swing 1.1 (JDK1.2).
			/*try {
				if (text.modelToView(paragraph.getStartOffset()) == null) {
					return null;
				}
			} catch (BadLocationException ble) {
				return null;
			}*/

			// This assumes the views are layed out sequentially.
			Insets         insets = text.getInsets();
			Rectangle      alloc = new Rectangle(text.getSize());
			TextUI         ui = text.getUI();
			View           view = ui.getRootView(text);
			int            offset = paragraph.getStartOffset();
			
			int			   parIndent=(int) StyleConstants.getLeftIndent(paragraph.getAttributes());

			alloc.x += insets.left;
			alloc.x += parIndent;
			alloc.y += insets.top;
			alloc.width -= insets.left + insets.right;
			alloc.height -= insets.top + insets.bottom;

			Shape          bounds = alloc;

			while (view != null && view.getElement() != paragraph) {
				int        nchildren = view.getViewCount();
				int        index;
				int        lower = 0; 
				int        upper = nchildren - 1;
				int        mid = 0;
				int        p0 = view.getStartOffset();
				int        p1;

				if (nchildren == 0 || offset >= view.getEndOffset() ||
						offset < view.getStartOffset()) {
					view = null;
				}
				else {
					boolean found = false;
					while (lower <= upper) {
						mid = lower + ((upper - lower) / 2);
						View v = view.getView(mid);
						p0 = v.getStartOffset();
						p1 = v.getEndOffset();
						if ((offset >= p0) && (offset < p1)) {
							// found the location
							found = true;
							bounds = view.getChildAllocation(mid, bounds);
							view = v;
							lower = upper + 1;
						} else if (offset < p0) {        
							upper = mid - 1;
						} else {
							lower = mid + 1;
						}
					}
					if (!found) {
						view = null;
					}
				}
			}
			if (view != null && bounds != null) {
				return new Integer(bounds.getBounds().x);
			}
		}
		return null;
	}

	/**
	 * Returns the TabStop closest to the passed in location. This
	 * may return null, or this may return a synthesized tab if there are
	 * currently no tabs and the location is close to a synthesized tab.
	 */
	protected TabStop getTabClosestTo(int xLocation, int yLocation) {
		TabSet      tabs = getTabSet();

		xLocation -= getXOffset();

		float       xFloat = (float)xLocation;

		if (tabs == null) {
			if (xLocation % DPI <= 5) {
				return new TabStop(xLocation / DPI * DPI);
			}
		}
		else {
			for (int counter = tabs.getTabCount() - 1; counter >= 0;
			counter--) {
				TabStop tab = tabs.getTab(counter);
				switch (tab.getAlignment()) {
				case TabStop.ALIGN_LEFT:
					if (xFloat >= tab.getPosition() &&
							xFloat <= (tab.getPosition() + TabWidth + 2)) {
						return tab;
					}
					break;
				case TabStop.ALIGN_RIGHT:
					if (xFloat <= tab.getPosition() &&
							xFloat >= (tab.getPosition() - TabWidth)) {
						return tab;
					}
					break;
				case TabStop.ALIGN_CENTER:
				case TabStop.ALIGN_DECIMAL:
					if (xFloat >= (tab.getPosition() - TabWidth) &&
							xFloat <= (tab.getPosition() + TabWidth)) {
						return tab;
					}
					break;
				default:
					break;
				}
			}
		}
		return null;
	}

	/**
	 * Creates and returns the listener to use for moving around tabs.
	 */
	protected MouseInputListener createMouseInputListener() {
		return new MouseInputHandler();
	}

	/**
	 * Returns the default border to use.
	 */
	protected Border createBorder() {
		return DefaultBorder;
	}


	/**
	 * Draws a little border around the Ruler.
	 */
	protected static class RulerBorder implements Border {
		protected static final Insets DefaultInsets = new Insets(2, 0, 4, 0);

		/**
		 * Paints the border for the specified component with the specified 
		 * position and size.
		 * @param c the component for which this border is being painted
		 * @param g the paint graphics
		 * @param x the x position of the painted border
		 * @param y the y position of the painted border
		 * @param width the width of the painted border
		 * @param height the height of the painted border
		 */
		public void paintBorder(Component c, Graphics g, int x, int y,
				int width, int height) {
			g.setColor(Color.darkGray);
			g.drawLine(x, y + 1, x + width, y + 1);
			g.setColor(Color.lightGray);
			g.drawLine(x, y, x + width, y);
			g.fillRect(x, y + height - 3, width, 2);
		}

		/**
		 * Returns the insets of the border.  
		 * @param c the component for which this border insets value applies
		 */
		public Insets getBorderInsets(Component c) {
			return (Insets)DefaultInsets.clone();
		}

		/**
		 * Returns whether or not the border is opaque.  If the border
		 * is opaque, it is responsible for filling in it's own
		 * background when painting.
		 */
		public boolean isBorderOpaque() {
			return false;
		}
	}


	/**
	 * MouseInputHandler is responsible for receiving mouse events and
	 * translating that into adjusting the TabSet. A mouse down on an
	 * existing tab allows the user to move that tab around, if the
	 * shift key is held down on the initial click the type of tab will
	 * change to the next type of alignment (cycling through left, right,
	 * centered, and decimal). A mouse down not near an exising tab causes
	 * a new tab to be created. A tab can be removed by dragging it outside
	 * the bounds of the Ruler.
	 */
	protected class MouseInputHandler extends MouseInputAdapter {
		/** The tab the user is dragging, non null indicates
		 * a valid tab has been selected. */
		protected TabStop       tab;
		/** Original TabSet. */
		protected TabSet        originalTabs;
		/** Tab user clicked on. Null indicates the user is creating a
		 * new tab. */
		protected TabStop       originalTab;
		/** While the mouse is down this will be true. */
		protected boolean       dragging;
		/** Specifies the alignment passed into createTabStop. */
		protected int           newAlignment;

		/**
		 * Invoked when a mouse button has been pressed on a component.
		 */
		public void mousePressed(MouseEvent e) {
			dragging = true;
			originalTabs = getTabSet();
			originalTab = getTabClosestTo(e.getX(), e.getY());
			newAlignment = TabStop.ALIGN_LEFT;
			if (originalTab == null) {
				tab = createTabStop(e.getX(), e.getY(), newAlignment);
				resetTabs();
			}
			else {
				tab = originalTab;
				if ((e.getModifiers() & InputEvent.SHIFT_MASK) != 0) {
					// Shift mask changes the alignment of the tab.
					switch(tab.getAlignment()) {
					case TabStop.ALIGN_LEFT:
						newAlignment = TabStop.ALIGN_RIGHT;
						break;
					case TabStop.ALIGN_RIGHT:
						newAlignment = TabStop.ALIGN_CENTER;
						break;
					case TabStop.ALIGN_CENTER:
						newAlignment = TabStop.ALIGN_DECIMAL;
						break;
					default:
						newAlignment = TabStop.ALIGN_LEFT;
					}
					tab = new TabStop(tab.getPosition(), newAlignment,
							tab.getLeader());
					resetTabs();
				}
				else {
					newAlignment = tab.getAlignment();
				}
			}
		}

		/**
		 * Invoked when a mouse button has been released on a component.
		 */
		public void mouseReleased(MouseEvent e) {
			dragging = false;

			// Push the tabs to the text pane (we may not need to do this
			// if the tabs haven't changed).
			TabSet                tabs = getTabSet();

			if (tabs == null) {
				SimpleAttributeSet   sas = new SimpleAttributeSet
				(getParagraphElement().getAttributes());

				sas.removeAttribute(StyleConstants.TabSet);
				getTextPane().setParagraphAttributes(sas, true);
			}
			else {
				SimpleAttributeSet    sas = new SimpleAttributeSet();

				StyleConstants.setTabSet(sas, tabs);
				getTextPane().setParagraphAttributes(sas, false);
			}
		}

		/**
		 * Invoked when a mouse button is pressed on a component and then 
		 * dragged.  Mouse drag events will continue to be delivered to
		 * the component where the first originated until the mouse button is
		 * released (regardless of whether the mouse position is within the
		 * bounds of the component).
		 */
		public void mouseDragged(MouseEvent e) {
			if (dragging) {
				TabStop        newTab = createTabStop(e.getX(), e.getY(),
						newAlignment);

				// Workaround for TabStop.equals not handling null being
				// passed in.
				if (newTab != tab &&
						((newTab != null && tab != null && !newTab.equals(tab)) ||
								(newTab == null || tab == null))) {
					tab = newTab;
					resetTabs();
				}
			}
		}

		/**
		 * Creates a new TabSet and messages setTabSet.
		 */
		protected void resetTabs() {
			TabStop[]       stops;

			if (tab == null) {
				// The tab has been moved off screen, indicating we should
				// remove it.
				if (originalTab != null && originalTabs != null) {
					int     tabCount = originalTabs.getTabCount();

					if (tabCount > 1) {
						stops = new TabStop[tabCount - 1];
						for (int counter = 0, index = 0; counter < tabCount;
						counter++) {
							TabStop    tab = originalTabs.getTab(counter);

							if (tab != originalTab) {
								stops[index++] = tab;
							}
						}
						setTabSet(new TabSet(stops));
					}
					else {
						setTabSet(null);
					}
				}
				else {
					setTabSet(originalTabs);
				}
				return;
			}
			if (originalTabs == null) {
				// No starting TabSet, create a new one.
				stops = new TabStop[1];
				stops[0] = tab;
			}
			else if (originalTab == null) {
				// Adding a new tab.
				int       numTabs = originalTabs.getTabCount();
				int       nextIndex = originalTabs.getTabIndexAfter
				(tab.getPosition());

				stops = new TabStop[numTabs + 1];
				if (nextIndex == -1) {
					for (int counter = 0; counter < numTabs; counter++) {
						stops[counter] = originalTabs.getTab(counter);
					}
					stops[numTabs] = tab;
				}
				else {
					for (int counter = 0; counter < nextIndex; counter++) {
						stops[counter] = originalTabs.getTab(counter);
					}
					stops[nextIndex] = tab;
					for (int counter = nextIndex; counter < numTabs;
					counter++) {
						stops[counter + 1] = originalTabs.getTab(counter);
					}
				}		
			}
			else {
				// Moving an existing tab.
				int       numTabs = originalTabs.getTabCount();
				int       nextIndex = originalTabs.getTabIndexAfter
				(tab.getPosition());
				int       index = 0;

				stops = new TabStop[numTabs];
				if (nextIndex == -1) {
					for (int counter = 0; counter < numTabs; counter++) {
						stops[index] = originalTabs.getTab(counter);
						if (stops[index] != originalTab) {
							index++;
						}
					}
					stops[index] = tab;
				}
				else {
					for (int counter = 0; counter < nextIndex; counter++) {
						stops[index] = originalTabs.getTab(counter);
						if (stops[index] != originalTab) {
							index++;
						}
					}
					stops[index++] = tab;
					for (int counter = nextIndex; counter < numTabs &&
					index < numTabs; counter++) {
						stops[index] = originalTabs.getTab(counter);
						if (stops[index] != originalTab) {
							index++;
						}
					}
				}
			}
			if (stops != null) {
				setTabSet(new TabSet(stops));
			}
			else {
				setTabSet(null);
			}
		}

		/**
		 * Creates a tab stop at the passed in visual location. This should
		 * be offset from any margins.
		 */
		protected TabStop createTabStop(int x, int y, int alignment) {
			if (x < 0 || x > getBounds().width || y < 0 ||
					y > getBounds().height) {
				return null;
			}
			// Constrain the x to 1/8 of an inch.
			x = (x - getXOffset()) / E_DPI * E_DPI;
			return new TabStop((float)x, alignment,
					TabStop.LEAD_NONE);
		}
	}


	public void updateView(DocumentEvent e) {
		if (e instanceof RulerRepaintEvent) {
			this.repaint();
		} else {
			JTextPane pane=getTextPane();
			if (pane instanceof JXMLNotePane) {
				XMLNoteDocument doc=((JXMLNotePane) pane).getXMLNoteDoc();
				Element par=doc.getParagraphElement(e.getOffset());
				Element cpar=doc.getParagraphElement(pane.getCaretPosition());
				if (cpar==par) {
					paragraph=null;
					updateTabSet(pane.getCaretPosition());
				}
			}
		}
	}
}
