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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import net.oesterholt.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import net.oesterholt.jxmlnote.internationalization.XMLNoteTranslator;
import net.oesterholt.jxmlnote.styles.XMLNoteParStyle;
import net.oesterholt.jxmlnote.styles.XMLNoteStyles;
import net.oesterholt.jxmlnote.utils.BufferedImageBuilder;
import net.oesterholt.jxmlnote.utils.WeakSet;
import net.oesterholt.jxmlnote.widgets.ExpandIcon;


/**
 * Creates the default toolbar for the JXMLNote widget. New Icons can be added
 * to the toolbar. The toolbar consists of sections with icons.
 * 
 * Default tools available in the toolbar:
 * 
 * <b>section "@edit" (menu)</b>:
 * "undo","|","copy-to-clipboard","cut-to-clipboard","paste-from-clipboard"<br />
 * <b>section "@styles" (menu)</b>: "h1","h2","h3","h4","normal"<br />
 * <b>section "fonts" (buttons)</b>: "font-bold","font-italic","font-underline"<br />
 * <b>section "@aligns" (buttons)</b>:
 * "align-left","align-right","align-center","align-justify"<br />
 * <b>section "lists" (buttons)</b>: "lists","unordered-list","numbered-list"<br />
 * 
 * Tools can be enabled or disabled by section,tool search. Sections can be
 * removed or new entries can be added to a section. Een new sections can be
 * added.
 * 
 * @author Hans Oesterholt
 * 
 */
public class JXMLNoteToolBar extends JToolBar implements JXMLNoteIcon.SizeProvider {

	public interface ButtonSizeListener {
		public void buttonSizeSet(int previousValue,int newValue);
	}
	
	private class MenuLabel extends JButton implements ButtonSizeListener {

		private static final long serialVersionUID = 1L;
		
		JLabel _label=new JLabel();
		
		public void buttonSizeSet(int prev,int next) {
			Dimension d=_label.getPreferredSize();
			d.height=next;
			_label.setPreferredSize(d);
			super.invalidate();
		}
		
		public MenuLabel(String txt) {
			this(new JLabel(txt));
		}
		
		protected MenuLabel(JLabel l) {
			super.setLayout(new BorderLayout());
			_label=l;
			Dimension d=_label.getPreferredSize();
			d.height=buttonSize();
			_label.setPreferredSize(d);
			addButtonSizeListener(this);
			super.add(_label,BorderLayout.CENTER);
			ExpandIcon e=new ExpandIcon();
			e.translateY(2);e.translateX(2);
			super.add(new JLabel(e),BorderLayout.EAST);
		}
	}
	
	private static final long serialVersionUID = -2171940162555518924L;
	
	private ActionListener _view;
	private int _size = 18;
	private XMLNoteTranslator _translator;
	private XMLNoteStyles _styles;
	private toolbarSection _stylesSection;
	
	private WeakSet<ButtonSizeListener> _listeners=new WeakSet<ButtonSizeListener>();

	private static final String[] _defaultSections = { "@edit", "@styles","@pars", "fonts","indent", "@zoom" };
	private static final String[] _defaultSectionTexts = { "_Edit", "_Styles","_Paragraph", "Markup","Indenting", "_Zoom" };
	
	private void addButtonSizeListener(ButtonSizeListener l) {
		_listeners.add(l);
	}
	
	private void informButtonSizeSet(int prev,int next) {
		Iterator<ButtonSizeListener> it=_listeners.iterator();
		while (it.hasNext()) {
			ButtonSizeListener l=it.next();
			if (l!=null) { l.buttonSizeSet(prev, next); }
		}
	}
	
	
	/**
	 * Section for the edit actions
	 */
	public static final String SECTION_EDIT = "@edit";

	/**
	 * Section for styles actions for entries in XMLNoteStyles.
	 */
	public static final String SECTION_STYLES = "@styles";

	/**
	 * Section for character style actions like bold, italic and underline.
	 */
	public static final String SECTION_CHARSTYLE = "fonts";

	/**
	 * Section for Indenting actions.
	 */
	public static final String SECTION_INDENT = "indent";

	/**
	 * Section for alignment actions on paragraphs.
	 */
	public static final String SECTION_ALIGN = "@pars";

	/**
	 * Section for Zoom actions on the views
	 */
	public static final String SECTION_ZOOM = "@zoom";
	
	
	/**
	 * Undo action
	 */
	public static final String ACTION_UNDO = "undo";

	/**
	 * Redo action
	 */
	public static final String ACTION_REDO = "redo";

	/**
	 * Copy to clipboard action
	 */
	public static final String ACTION_COPY = "copy-to-clipboard";

	/**
	 * Cut to clipboard action
	 */
	public static final String ACTION_CUT = "cut-to-clipboard";

	/**
	 * Paste from clipboard action
	 */
	public static final String ACTION_PASTE = "paste-from-clipboard";

	/**
	 * Select all action
	 */
	public static final String ACTION_SELECT_ALL = "select-all";

	/**
	 * Bold action
	 */
	public static final String ACTION_BOLD = "font-bold";

	/**
	 * Italic action
	 */
	public static final String ACTION_ITALIC = "font-italic";

	/**
	 * Underline action
	 */
	public static final String ACTION_UNDERLINE = "font-underline";

	/**
	 * Indent more action
	 */
	public static final String ACTION_INDENT_MORE = "indent-more";

	/**
	 * Indent less action
	 */
	public static final String ACTION_INDENT_LESS = "indent=less";

	/**
	 * Align left action
	 */
	public static final String ACTION_ALIGN_LEFT = "align-left";

	/**
	 * Align right action
	 */
	public static final String ACTION_ALIGN_RIGHT = "align-right";

	/**
	 * Align center action
	 */
	public static final String ACTION_ALIGN_CENTER = "align-center";

	/**
	 * Align justify action
	 */
	public static final String ACTION_ALIGN_JUSTIFY = "align-justify";

	/**
	 * File/New action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_NEW = "new";

	/**
	 * File/Load action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_LOAD = "load";

	/**
	 * File/Save action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_SAVE = "save";

	/**
	 * Quit action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_QUIT = "quit";

	/**
	 * Help action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_HELP = "help";
	
	/**
	 * Zoom 100% action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_ZOOM_100 = "zoom-100";

	/**
	 * Zoom More action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_ZOOM_MORE = "zoom-more";
	
	/**
	 * Zoom Less action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_ZOOM_LESS = "zoom-less";
	
	/**
	 * Zoom Fit Width action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_ZOOM_FIT_WIDTH = "zoom-fit-width";
	
	/**
	 * Zoom Fit Height action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_ZOOM_FIT_HEIGHT = "zoom-fit-height";

	/**
	 * Next action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_NEXT = "next";

	/**
	 * Previous action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_PREVIOUS = "previous";

	/**
	 * Last action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_LAST = "last";

	/**
	 * First action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_FIRST = "first";
	
	/**
	 * Print action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_PRINT = "print";
	
	/**
	 * Print preferences action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_PRINT_PREFS = "print-prefs";
	

	/**
	 * Close action. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_CLOSE = "close";
	
	/**
	 * Zoom 100% action on XMLNotePane. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_ZOOM_100P = "zoom-100%";

	/**
	 * Zoom 50% action on XMLNotePane. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_ZOOM_50P = "zoom-50%";

	/**
	 * Zoom 75% action on XMLNotePane. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_ZOOM_75P = "zoom-75%";

	/**
	 * Zoom 125% action on XMLNotePane. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_ZOOM_125P = "zoom-125%";

	/**
	 * Zoom 150% action on XMLNotePane. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_ZOOM_150P = "zoom-150%";

	/**
	 * Zoom 200% action on XMLNotePane. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_ZOOM_200P = "zoom-200%";

	/**
	 * Set Zoom action on XMLNotePane. This action only has a standard icon attached to it, no
	 * action in JXMLNoteEditor.
	 */
	public static final String ACTION_SET_ZOOM = "set-zoom";
	
	
	class toolbarSection {
		private String _name;
		private String _text;
		private ArrayList<JComponent> _tools;
		private ArrayList<JMenuItem> _items;
		private JPopupMenu _toolsMenu;
		//private JButton _label;
		private MenuLabel	_label;

		public void add(JComponent b) {
			_tools.add(b);
		}

		public void add(String tool, String tt, ActionListener v, Icon icon) {

			class painter implements JXMLNoteIconPainter {
				private ImageIcon img;
				private Icon _source;
				private int _mySize;

				public void paint(int x, int y, int w, int h, Graphics2D g,Component c, String tool) {
					if (_mySize!=w) {
						init(c,w);
					}
					img.paintIcon(c, g, x, y);
				}
				
				private void init(Component c,int width) {
					_mySize=width;
					if (_source instanceof ImageIcon) {
						img = new ImageIcon(BufferedImageBuilder.getScaledInstance(((ImageIcon) _source).getImage(), _mySize, _mySize)); 
								//((ImageIcon) _source).getImage().getScaledInstance(_mySize,_mySize, Image.SCALE_SMOOTH));
					} else {
						BufferedImage bimg=new BufferedImage(_source.getIconWidth(),_source.getIconHeight(),BufferedImage.TYPE_4BYTE_ABGR_PRE|BufferedImage.OPAQUE);
						Graphics2D g=bimg.createGraphics();
						g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						_source.paintIcon(c, g, 0, 0);
						g.dispose();
						img=new ImageIcon(BufferedImageBuilder.getScaledInstance(bimg, _mySize, _mySize));
					}
				}

				public painter(Icon i) {
					_source=i;
					_mySize=-1;
				}
			}
			;

			painter p = new painter(icon);
			JXMLNoteIcon _icon = new JXMLNoteIcon(tool, p);
			_icon.setSizeProvider(JXMLNoteToolBar.this);
			//_icon.setWHSize(buttonSize());

			addButton(tool, tt, v, _icon);
			addItem(tool, tt, v, _icon);
		}

		public void add(String tool, String tt, ActionListener v,JXMLNoteIcon icon) {
			addButton(tool, tt, v, icon);
			addItem(tool, tt, v, icon);
		}

		private void addButton(String tool, String tt, ActionListener v,JXMLNoteIcon _icon) {
			if (!tool.equals("|")) {
				JButton b = new JButton(_icon);
				b.setToolTipText(_translator.translate(tt));
				b.setActionCommand(tool);
				b.setName(tool);
				b.addActionListener(v);
				b.setFocusable(false);
				_tools.add(b);
			} else {
				_tools.add(null);
			}
		}

		private void addItem(String tool, String tt, ActionListener v,JXMLNoteIcon _icon) {
			if (tool.equals("|")) {
				_toolsMenu.add(new JSeparator());
				_items.add(null);
			} else {
				String ttt = _translator.translate("menu:" + tt);
				JMenuItem item;
				int i = ttt.indexOf('_');
				if (i >= 0) {
					String c = ttt.substring(i + 1, i + 2);
					String q = ttt.substring(0, i) + ttt.substring(i + 1);
					item = new JMenuItem(q, _icon);
					item.setMnemonic(c.charAt(0));
				} else {
					item = new JMenuItem(ttt, _icon);
				}
				item.addActionListener(v);
				item.setFocusable(false);
				item.setName("menu:" + tool);
				item.setActionCommand(tool);
				_toolsMenu.add(item);
				_items.add(item);
			}
		}

		public void clear() {
			_toolsMenu.removeAll();
			_tools.clear();
			_items.clear();
		}

		public void add(String tool, String tt, ActionListener v) {
			JXMLNoteIcon icon = new JXMLNoteIcon(tool); //, new IconSizeProvider() {
			icon.setSizeProvider(JXMLNoteToolBar.this);
			addButton(tool, tt, v, icon);
			addItem(tool, tt, v, icon);
		}

		public String name() {
			return _name;
		}
		
		public String getSectionId() {
			return _name;
		}
		
		public String getSectionText() {
			return _text;
		}

		public String getName(int i) {
			JComponent comp=_tools.get(i);
			if (comp instanceof JButton) {
				JButton b = (JButton) comp;
				if (b == null) {
					return null;
				} else {
					return b.getActionCommand();
				}
			} else {
				return comp.getName();
			}
		}

		public int size() {
			return _tools.size();
		}

		public JComponent get(int i) {
			return _tools.get(i);
		}

		public JComponent getComponent(int i) {
			return _tools.get(i);
		}

		public JMenuItem getMenuItem(int i) {
			return _items.get(i);
		}

		public JPopupMenu menu() {
			return _toolsMenu;
		}

		public JButton menuText() {
			return _label;
		}

		public boolean isMenu() {
			return _name.substring(0, 1).equals("@");
		}

		public toolbarSection(String n, String tt) {
			_name = n;
			_tools = new ArrayList<JComponent>();
			_items = new ArrayList<JMenuItem>();

			{
				String ttt = _translator.translate("menu:" + tt);
				int i = ttt.indexOf('_');
				if (i >= 0) {
					String c = ttt.substring(i + 1, i + 2);
					String q = ttt.substring(0, i) + ttt.substring(i + 1);
					
					_toolsMenu = new JPopupMenu();
					_label = new MenuLabel(q);
					_label.setMnemonic(c.charAt(0));
				} else {
					_toolsMenu = new JPopupMenu();
					_label = new MenuLabel(ttt);
				}
				
				_text=ttt;
			}
			_label.setFocusable(false);

			_label.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					_toolsMenu.show(_label, 0, _label.getHeight());
				}
			});

		}
	}

	private ArrayList<toolbarSection> _tb;

	/**
	 * Sets a icon with a given name (<code>tool</code>). The <code>tool</code>
	 * must be equal to the action command (see
	 * {@linkplain JButton#getActionCommand()}.
	 * 
	 * @param tool
	 * @param yn
	 */
	public void setSelected(String tool, boolean yn) {
		int N = this.getComponentCount();
		int i = 0;
		for (i = 0; i < N
				&& !this.getComponent(i).getClass().equals(JButton.class); i++)
			;
		if (i < N) {
			Object o = this.getComponent(i);
			JButton b = (JButton) o;
			while (i < N && !b.getActionCommand().equals(tool)) {
				i += 1;
				if (i < N) {
					o = this.getComponent(i);
					if (o.getClass().equals(JButton.class)) {
						b = (JButton) o;
					}
				}
			}

			if (i < N) {
				JXMLNoteIcon icon = (JXMLNoteIcon) b.getIcon();
				icon.setSelected(yn);
				b.updateUI();
			}
		}
	}
	

	/**
	 * Returns the tooltip text for the given section
	 * 
	 * @param sect
	 * @return
	 */
	public String getSectionTextForSection(String sect) {
		Iterator<toolbarSection> it=_tb.iterator();
		while (it.hasNext()) {
			toolbarSection s=it.next();
			if (s.getSectionId().equals(sect)) {
				return s.getSectionText();
			}
		}
		return null;
	}

	
	/**
	 * Returns the number of tools in a section, or 0 if section isn't found.
	 * @param sect
	 * @return
	 */
	public int getNumberOfToolsForSection(String sect) {
		Iterator<toolbarSection> it=_tb.iterator();
		while (it.hasNext()) {
			toolbarSection s=it.next();
			if (s.getSectionId().equals(sect)) {
				return s.size();
			}
		}
		return 0; 
	}
	
	/**
	 * Returns the i'th tool in section sect.
	 * @param sect
	 * @param toolI
	 * @return
	 */
	private toolbarSection _prevSect=null;
	public JComponent getComponent(String sect,int toolI) {
		if (_prevSect!=null && _prevSect.getSectionId().equals(sect)) {
			return _prevSect.get(toolI);
		} else {
			Iterator<toolbarSection> it=_tb.iterator();
			while (it.hasNext()) {
				toolbarSection s=it.next();
				if (s.getSectionId().equals(sect)) {
					_prevSect=s;
					return getComponent(sect,toolI);
				}
			}
			return null;
		}
	}
	
	public JMenuItem getMenuItem(String sect,int toolI) {
		if (_prevSect!=null && _prevSect.getSectionId().equals(sect)) {
			return _prevSect.getMenuItem(toolI);
		} else {
			Iterator<toolbarSection> it=_tb.iterator();
			while (it.hasNext()) {
				toolbarSection s=it.next();
				if (s.getSectionId().equals(sect)) {
					_prevSect=s;
					return getMenuItem(sect,toolI);
				}
			}
			return null;
		}
	}

	/**
	 * Returns the section for a tool T, or 'null' if tool doesn't exists. If
	 * multiple (section,tool=T) exist, the first one is found
	 * 
	 * @param T
	 * @return
	 */
	public String getSectionForTool(String T) {
		int i;
		for (i = 0; i < _tb.size(); i++) {
			int j;
			toolbarSection sec = _tb.get(i);
			for (j = 0; j < sec.size()
					&& (sec.getName(j) == null || !sec.getName(j).equals(T)); j++)
				;
			if (j < sec.size()) {
				return sec.name();
			}
		}
		return null;
	}
	
	private final String [] _defaultTools={ 
			"@edit", "undo", "redo", "|", "copy-to-clipboard","cut-to-clipboard", "paste-from-clipboard", "|", "select-all",null, 
			"@styles", null, 
			"fonts", "font-bold", "font-italic","font-underline", null, 
			"indent", "indent-less", "indent-more",null, 
			"@pars", "align-left", "align-right", "align-center","align-justify", null,
			"@zoom", "zoom-50%", "zoom-75%", "zoom-100%", "zoom-125%", "zoom-150%", "zoom-200%", "|", "set-zoom", null
	};
	
	private final String[] _defaultTexts={ "_Edit", "_Undo", "_Redo", "|", "_Copy", "Cu_t","_Paste", "|", "Select _All", null, 
			"_Styles", null, 
			"_Markup", "Bold", "Italics", "Underlined", null, 
			"_Indent", "Indent less", "Indent more", null, 
			"_Paragraph","_Left aligned text", "_Right aligned text", "_Centered text","_Justified text", null,
			"_Zoom", "50%","75%","100%","125%","150%","200%","|","_Set Zoom",null
	};

	private void initDefaultButtons() {
		int i;
		for (i = 0; i < _defaultSections.length; i++) {
			this.addSection(_defaultSections[i],_defaultSectionTexts[i]);
		}
		this.setRollover(true);
		this.setFocusable(false);
	}

	/**
	 * Adds a given command, tooltip and actionlistener to section
	 * <code>section</code>. The section must already exist. Using the given
	 * Icon.
	 * 
	 * @param section
	 *            The section where the tool belongs
	 * @param command
	 *            The command to use
	 * @param tooltip
	 *            The tooltip to associate
	 * @param n
	 *            The ActionListener to fire events to
	 * @param icon
	 *            The icon to draw
	 */
	public void add(String section, String command, String tooltip,ActionListener n, Icon icon) {
		int i, N;
		for (i = 0, N = _tb.size(); i < N && !_tb.get(i).name().equals(section); i++);
		if (i != N) {
			_tb.get(i).add(command, tooltip, n, icon);
		}
	}

	/**
	 * Adds a given command, tooltip and actionlistener to section
	 * <code>section</code>. The section must already exist. Using the given
	 * JXMLNote Icon.
	 * 
	 * @param section
	 *            The section where the tool belongs
	 * @param command
	 *            The command to use
	 * @param tooltip
	 *            The tooltip to associate
	 * @param n
	 *            The ActionListener to fire events to
	 * @param icon
	 *            The icon to draw
	 */
	public void add(String section, String command, String tooltip,ActionListener n, JXMLNoteIcon icon) {
		int i, N;
		for (i = 0, N = _tb.size(); i < N && !_tb.get(i).name().equals(section); i++);
		if (i != N) {
			//icon.setWHSize(buttonSize());
			icon.setSizeProvider(this);
			_tb.get(i).add(command, tooltip, n, icon);
		}
	}

	/**
	 * Adds a given component (e.g. a JButton) to section <code>section</code>. The
	 * section must already exist.
	 * <p>
	 * @param section	The section to use
	 * @param comp 		The component to add
	 */
	public void add(String section, JComponent comp) {
		int i, N;
		for (i = 0, N = _tb.size(); i < N && !_tb.get(i).name().equals(section); i++);
		if (i != N) {
			_tb.get(i).add(comp);
		}
	}
	
	/**
	 * Adds a given command, tooltip and actionlistener to section
	 * <code>section</code>. The section must already exist. The
	 * <code>command</code> must be available from {@link JXMLNoteIcon}.
	 * 
	 * @param section
	 * @param command
	 * @param tooltip
	 * @param n
	 */
	public void add(String section, String command, String tooltip,ActionListener n) {
		int i, N;
		for (i = 0, N = _tb.size(); i < N && !_tb.get(i).name().equals(section); i++)
			;
		if (i != N) {
			_tb.get(i).add(command, tooltip, n);
		}
	}

	/**
	 * Inserts a new section before the given section (which must exist).
	 * 
	 * @param section
	 * @param newSection
	 */
	public void insertSectionBefore(String section, String tt, String newSection) {
		int i, N;
		for (i = 0, N = _tb.size(); i < N && !_tb.get(i).name().equals(section); i++)
			;
		if (i == N) {
			this.addSection(newSection, tt);
		} else {
			_tb.add(i, new toolbarSection(newSection, tt));
		}
	}

	/**
	 * Adds a new section after the given section (which must exist).
	 * 
	 * @param section
	 * @param newSection
	 */
	public void insertSectionAfter(String section, String tt, String newSection) {
		int i, N;
		for (i = 0, N = _tb.size(); i < N && !_tb.get(i).name().equals(section); i++)
			;
		if (i == N) {
			this.addSection(newSection, tt);
		} else {
			_tb.add(i + 1, new toolbarSection(newSection, tt));
		}
	}

	/**
	 * Resets the styles menu from the current XMLNoteStyles object
	 */
	public void resetStyles() {
		_stylesSection.clear();
		int N = _styles.getNumberOfStyles();
		int i;
		for (i = 0; i < N; i++) {
			XMLNoteParStyle st = _styles.getStyle(i);
			_stylesSection.add("style:" + st.id(), st.name(), _view);
		}
	}

	/**
	 * Add a default section, i.e. a section that is pre-defined in JXMLNoteToolBar. The tooltip text will
	 * be automatically fetched.
	 * <p>
	 * @param defaultSection	The default section to use.
	 */
	public void addSection(String defaultSection) {
		if (isStandardSection(defaultSection)) {
			int i,N;
			for(i=0,N=_defaultSections.length;i<N && !defaultSection.equals(_defaultSections[i]);i++);
			addSection(defaultSection,_defaultSectionTexts[i]);
		} else {
			addSection(defaultSection,defaultSection);
		}
	}
	
	/**
	 * Adds a new section (at the end of the toolbar).
	 * Will initialize the default actions if one of the standard sections
	 * is used.
	 * 
	 * @param section
	 */
	public void addSection(String section, String tt) {
		if (section.equals("@styles") && _styles != null) {
			toolbarSection s = new toolbarSection(section, tt);
			_stylesSection = s;
			resetStyles();
			_tb.add(s);
		} else {
			toolbarSection s = new toolbarSection(section, tt);
			_tb.add(s);
			if (isStandardSection(section)) {
				int i,N;
				boolean inSec=false;
				for(i=0,N=_defaultTools.length;i<N;i++) {
					if (section.equals(_defaultTools[i])) {
						inSec=true;
					} else if (inSec) {
						if (_defaultTools[i]==null) {
							return;
						} else {
							this.add(section, _defaultTools[i], _defaultTexts[i], _view);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param section
	 * @return true, is section is one of the standard sections
	 */
	public boolean isStandardSection(String section) {
		int i,N;
		for(i=0,N=_defaultSections.length;i<N;i++) {
			if (section.equals(_defaultSections[i])) { return true; }
		}
		return false;
	}

	/**
	 * Inserts a new section (at the beginning of the toolbar).
	 * 
	 * @param section
	 */
	public void insertSection(String section, String tt) {
		_tb.add(0, new toolbarSection(section, tt));
	}

	/**
	 * Returns the button size (w==h).
	 * 
	 * @return int The button size in pixels.
	 */
	public int buttonSize() {
		return _size;
	}

	/**
	 * Set the button size
	 * 
	 * @param s
	 *            the size (w==h) in pixels.
	 */
	public void setButtonSize(int s) {
		int p=_size;
		_size = s;
		informButtonSizeSet(p,s);
	}

	/**
	 * Enable/disable tools in the menu or toolbar.
	 * 
	 * @param tool
	 * @param active
	 * @return Object The found JButton or JMenuItem, depending on section or
	 *         null if none found.
	 */
	public Object enable(String section, String tool, boolean active) {
		int i;
		for (i = 0; i < _tb.size() && !_tb.get(i).name().equals(section); i++)
			;
		if (i == _tb.size()) {
			return null;
		} else {
			toolbarSection sec = _tb.get(i);
			for (i = 0; i < sec.size()
					&& (sec.get(i) == null ? true : (!sec.get(i).getName()
							.equals(tool))); i++)
				;
			if (i == sec.size()) {
				return null;
			} else {
				sec.getMenuItem(i).setEnabled(active);
				sec.get(i).setEnabled(active);
				if (section.substring(0, 1).equals("@")) {
					return sec.getMenuItem(i);
				} else {
					return sec.get(i);
				}
			}
		}
	}

	/**
	 * Set an accelerator for a menuitems. If it is part of a button, no
	 * accelerator can be set.
	 * 
	 * @param section - the section that contains the menuitem
	 * @param tool - the menu item
	 * @param key - the key to use.
	 * @return
	 */
	public Object setAccelerator(String section,String tool,KeyStroke key) {
		int i;
		for(i=0;i<_tb.size() && !_tb.get(i).name().equals(section);i++);
		if (i==_tb.size()) {
			return null;
		} else {
			toolbarSection sec=_tb.get(i);
			for(i=0;i<sec.size() && (sec.get(i)==null ? true : (!sec.get(i).getName().equals(tool))) ;i++);
			if (i==sec.size()) {
				return null;
			} else {
				if (section.substring(0,1).equals("@")) {
					sec.getMenuItem(i).setAccelerator(key);
					return sec.getMenuItem(i);
				} else {
					return null;
				}
			}
		}
	}
	
	/**
	 * Sets a mnemonic on a button. Doesn't work for menu items. 
	 * 
	 * @param section - the section that contains the button
	 * @param tool - the button to use
	 * @param key - the key to use
	 * @return
	 */
	public Object setMnemonic(String section,String tool,int key) {
		int i;
		for(i=0;i<_tb.size() && !_tb.get(i).name().equals(section);i++);
		if (i==_tb.size()) {
			return null;
		} else {
			toolbarSection sec=_tb.get(i);
			for(i=0;i<sec.size() && (sec.get(i)==null ? true : (!sec.get(i).getName().equals(tool))) ;i++);
			if (i==sec.size()) {
				return null;
			} else {
				if (section.substring(0,1).equals("@")) {
					return null;
				} else {
					JComponent comp=sec.getComponent(i);
					if (comp instanceof JButton) {
						JButton but=(JButton) comp;
						but.setMnemonic(key);
						return but;
					} else {
						return null;
					}
				}
			}
		}
	}

	/**
	 * Initializes the toolbar. This method must be called after all sections
	 * and buttons have been added.
	 */
	public void initToolBar() {
		int i, j, N, M;
		boolean insSep = false;
		String lastSection = "";
		for (i = 0, N = _tb.size(); i < N; i++) {
			if (insSep) {
				JToolBar.Separator sep = new JToolBar.Separator();
				sep.setName(lastSection);
				this.add(sep);
			}
			toolbarSection s = _tb.get(i);
			lastSection = s.name();
			if (s.isMenu()) {
				this.add(s.menuText());
				// this.add(s.menu());
			} else {
				for (j = 0, M = s.size(); j < M; j++) {
					this.add(s.get(j));
				}
			}
			insSep = true;
		}
	}

	/**
	 * Removes a section from an uninitialized toolbar.
	 * 
	 * @param section
	 */
	public void removeSection(String section) {
		int i, N;
		for (i = 0, N = _tb.size(); i < N && !_tb.get(i).name().equals(section); i++)
			;
		if (i != N) {
			_tb.remove(i);
		}
	}

	/**
	 * Removes all sections in an uninitialized toolbar. Makes the toolbar
	 * empty.
	 */
	public void removeAllSections() {
		int i, N;
		Vector<String> sections = currentSections();
		Iterator<String> it = sections.iterator();
		while (it.hasNext()) {
			removeSection(it.next());
		}
	}

	/**
	 * Returns all the sections in the toolbar.
	 * 
	 * @return A vector of section names
	 */
	public Vector<String> currentSections() {
		int i, N;
		Vector<String> v = new Vector<String>();
		for (i = 0, N = _tb.size(); i < N; i++) {
			v.add(_tb.get(i).name());
		}
		return v;
	}

	/**
	 * Returns a vector with the default sections
	 * 
	 * @return
	 */
	public Vector<String> defaultSections() {
		int i;
		Vector<String> q = new Vector<String>();
		for (i = 0; i < _defaultSections.length; i++) {
			q.add(_defaultSections[i]);
		}
		return q;
	}

	/**
	 * This returns a vector with only the edit related sections, not the
	 * viewing related ones.
	 * 
	 * @return
	 */
	public Vector<String> editRelatedSections() {
		return defaultSections();
	}

	/**
	 * Creates an empty toolbar.
	 * 
	 * @param listener - the action listener for buttons and menus of this toolbar
	 * @return the newly created toolbar
	 */
	public static JXMLNoteToolBar emptyToolBar(ActionListener listener) {
		JXMLNoteToolBar bar=new JXMLNoteToolBar(listener,null);
		bar.removeAllSections();
		return bar;
	}



	/**
	 * Creates a toolbar with given ActionListener and XMLNoteStyles, with
	 * default icon size (18 pixels). <code>st</code> may be null.
	 * 
	 * @param v
	 *            An action listener.
	 * @param st
	 *            XMLNoteStyles to use in the styles menu
	 */
	public JXMLNoteToolBar(ActionListener v, XMLNoteStyles st) {
		this(v, st, 18);
	}

	/**
	 * Creates a toolbar with given ActionListener and XMLNoteStyles, with given
	 * icon size (pixels). <code>st</code> may be null.
	 * 
	 * @param v
	 * @param st
	 */
	public JXMLNoteToolBar(ActionListener v, XMLNoteStyles st, int buttonSize) {
		_size = buttonSize;
		_view = v;
		_translator = new DefaultXMLNoteTranslator();
		_tb = new ArrayList<toolbarSection>();
		_styles = st;
		super.setFloatable(false);
		super.setBorder(BorderFactory.createEtchedBorder());
		initDefaultButtons();
	}

}
