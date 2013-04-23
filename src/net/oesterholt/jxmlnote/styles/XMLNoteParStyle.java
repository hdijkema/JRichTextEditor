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

package net.oesterholt.jxmlnote.styles;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;

import net.oesterholt.jxmlnote.utils.DPIAdjuster;

public class XMLNoteParStyle {
	
	static private Integer 		 styleNumber=0;
	private static StyleContext _context=null;
	private XMLNoteStyles		_styleContainer=null;
	
	private Style  _style=null;

	private String 	_name="Paragraph";
	private String 	_id="par";
	private String 	_font="arial";
	private int    	_point_size=12;
	private int    	_skip_bottom_point_size=10;
	private int    	_skip_top_point_size=0;
	private boolean _italics=false;
	private boolean _bold=false;
	private boolean _underline=false;
	private Color   _color=Color.black;
	private Color   _bg=Color.white;
	private String  _extraCss="";
	private double  _zoomFactor=1.0;
	private String  _align=ALIGN_LEFT;
	private boolean _default=false;
	private TabSet  _tabs;
	private boolean _blockStyleChangeInform=false;
	private boolean _inTransaction=false;
	private boolean _keepWithNext=false;
	private int		_leftIndent=0;
	
	public static final String ALIGN_LEFT="left";
	public static final String ALIGN_RIGHT="right";
	public static final String ALIGN_CENTER="center";
	public static final String ALIGN_JUSTIFY="justify";
	public static final String ALIGN_JUSTIFIED=ALIGN_JUSTIFY;
	
	public void begin() {
		_inTransaction=true;
	}
	
	public void commit() {
		_inTransaction=false;
		this.informStyleChange();
	}
	
	public static StyleContext getStyleContext() {
		return _context;
	}
	
	private static synchronized String getTmpStyleName() {
		styleNumber+=1;
		return String.format("Style%04d", styleNumber);
	}
	
	private boolean blockStyleChangeInform(boolean n) {
		boolean b=_blockStyleChangeInform;
		_blockStyleChangeInform=n;
		return b;
	}
	
	private void informStyleChange() {
		if (!_blockStyleChangeInform) {
			if (_styleContainer!=null) {
				if (!_inTransaction) {
					_styleContainer.styleChanged(this);
				}
			}
		}
	}
	
	public void setContainer(XMLNoteStyles s) {
		_styleContainer=s;
	}
	
	public XMLNoteStyles getContainer() {
		return _styleContainer;
	}
	
	private String contextStyleName(String device) {
		return _styleContainer.stylesContextName()+":"+device+":"+id();
	}
	
	private void fixFontSizeAndTabStops(String device) {
		String stname=contextStyleName(device);
		Style dev=_context.getStyle(stname);
		if (dev==null) {
			_context.addStyle(stname, _style);
			dev=_context.getStyle(stname);
		}
		TabSet stops=StyleConstants.getTabSet(_style);
		Integer sz=StyleConstants.getFontSize(_style);
			
		//FIXME: DPIAdjuster gebruiken voor Screens. Voor printing niet.
		if (sz!=null) {
			double pts=(double) sz;
			if (device.equals(DPIAdjuster.DEVICE_SCREEN)) {
				pts=DPIAdjuster.adjustPointSize(pts);
				StyleConstants.setFontSize(dev,(int) Math.round(pts));
				Vector<TabStop> newStops=new Vector<TabStop>();
				for(int i=0,n=stops.getTabCount();i<n;i++) {
					TabStop tb=stops.getTab(i);
					double tab=DPIAdjuster.adjustPointSize(tb.getPosition());
					int align=tb.getAlignment();
					int leader=tb.getLeader();
					newStops.add(new TabStop((int) Math.round(tab),align,leader));
				}
				TabStop[] sts=new TabStop[newStops.size()];
				newStops.toArray(sts);
				TabSet tabset=new TabSet(sts);
				StyleConstants.setTabSet(dev, tabset);
			}
		}
	}
	
	public MutableAttributeSet getStyle(String device) {
		SimpleAttributeSet set=new SimpleAttributeSet();
		set.addAttributes(getRealStyle(device));
		XMLNoteStyleConstants.setId(set, id());
		return set;
	}
	
	public Style getRealStyle(String device) {
		String stname=contextStyleName(device);
		Style dev=_context.getStyle(stname);
		if (dev==null) {
			fixFontSizeAndTabStops(device);
			return getRealStyle(device);
		} else {
			return dev;
		}
	}
	
	public String name() {
		return _name;
	}
	
	public void name(String n) {
		_name=n;
		((StyleContext.NamedStyle)_style).setName(n); 
	}
	
	public String description() {
		return _name;
	}
	
	public void description(String d) {
		//_description=d;
	}
	
	public String id() {
		return _id;
	}
	
	public void id(String i) {
		_id=i;
		_style.addAttribute(XMLNoteStyleConstants.IdAttribute, _id);
	}
	
	public String font() {
		return _font;
	}
	
	public void font(String f) {
		_font=f;
		StyleConstants.setFontFamily(_style, f);
		informStyleChange();
	}
	
	public boolean keepWithNext() {
		return _keepWithNext;
	}
	
	public void keepWithNext(boolean b) {
		_keepWithNext=b;
	}
	
	public Font getFont() {
		int style=Font.PLAIN;
		if (bold()) { style=Font.BOLD; }
		if (italics()) {
			if (style==Font.BOLD) { 
				style+=Font.ITALIC; 
			} else {
				style=Font.ITALIC;
			}
		}
		Font f=new Font(font(),style,pointSize());
		return DPIAdjuster.adjustFont(f);
	}
	
	public int pointSize() {
		return (int) (_point_size*_zoomFactor);
	}
	
	public int leftIndent() {
		return _leftIndent;
	}
	
	public void leftIndent(int l) {
		_leftIndent=l;
		StyleConstants.setLeftIndent(_style, l);
		informStyleChange();
	}
	
	public void pointSize(int pt) {
		_point_size=pt;
		StyleConstants.setFontSize(_style, pt);
		fixFontSizeAndTabStops(DPIAdjuster.DEVICE_SCREEN);
		fixFontSizeAndTabStops(DPIAdjuster.DEVICE_PRINT);
		informStyleChange();
	}
	
	public int bottomSkip() {
		return (int) (_skip_bottom_point_size*_zoomFactor);
	}

	public void bottomSkip(int pt) {
		_skip_bottom_point_size=pt;
		StyleConstants.setSpaceBelow(_style, pt);
		informStyleChange();
	}

	public int topSkip() {
		return (int) (_skip_top_point_size*_zoomFactor);
	}
	
	public void topSkip(int pt) {
		_skip_top_point_size=pt;
		StyleConstants.setSpaceAbove(_style,pt);
		informStyleChange();
	}
	
	public boolean bold() {
		return _bold;
	}
	
	public void bold(boolean b) {
		_bold=b;
		StyleConstants.setBold(_style, b);
		informStyleChange();
	}
	
	public boolean italics() {
		return _italics;
	}
	
	public void italics(boolean it) {
		_italics=it;
		StyleConstants.setItalic(_style, it);
		informStyleChange();
	}
	
	public boolean underline() {
		return _underline;
	}
	
	public void tabs(TabSet tabs) {
		_tabs=tabs;
		StyleConstants.setTabSet(_style, tabs);
		informStyleChange();
	}
	
	public TabSet tabs() {
		return _tabs;
	}
	
	public void underline(boolean u) {
		_underline=u;
		StyleConstants.setUnderline(_style, u);
		informStyleChange();
	}
	
	public boolean isDefault() {
		return _default;
	}
	
	public void isDefault(boolean b) {
		_default=b;
	}
	
	public String color2String(Color c) {
		return String.format("#%02x%02x%02x",c.getRed()%256,c.getGreen()%256,c.getBlue()%256);
	}
	
	public Color string2Color(String c) {
		Integer col=Integer.parseInt(c.substring(1),16);
		return new Color(col);
	}
	
	public Color color() {
		return _color;
	}
	
	public void color(Color color) {
		_color=color;
		StyleConstants.setForeground(_style, color);
		informStyleChange();
	}
	
	public Color bgColor() {
		return _bg;
	}
	
	public void bgColor(Color color) {
		_bg=color;
		StyleConstants.setBackground(_style, color);
		informStyleChange();
	}
	
	public String getExtraCSS() {
		return _extraCss;
	}
	
	public void setExtraCSS(String extracss) {
		_extraCss=extracss;
	}
	
	public void zoom(double factor) {
		_zoomFactor=factor;
		informStyleChange();
	}
	
	public void align(String a) {
		a=a.toLowerCase();
		_align=a;
		if (a.equals(ALIGN_LEFT)) {
			StyleConstants.setAlignment(_style, StyleConstants.ALIGN_LEFT);
		} else if (a.equals(ALIGN_RIGHT)) {
			StyleConstants.setAlignment(_style, StyleConstants.ALIGN_RIGHT);
		} else if (a.equals(ALIGN_CENTER)) {
			StyleConstants.setAlignment(_style, StyleConstants.ALIGN_CENTER);
		} else if (a.equals(ALIGN_JUSTIFY)) {
			StyleConstants.setAlignment(_style, StyleConstants.ALIGN_JUSTIFIED);
		} else {
			align("left");
			return;
		}
		informStyleChange();
	}
	
	public String align() {
		return _align;
	}
	
	/*public String toLabelHtml(String label) {
		String s=String.format("<html><font size=\"%d\" face=\"%s\" color=\"%s\" background=\"%s\">",
								_point_size,_font,
								color2String(_color),
								color2String(_bg)
								);
		s=s+((_bold) ? "<b>" : "");
		s=s+((_underline) ? "<u>" : "");
		s=s+((_italics) ? "<i>" : "");
		s=s+label;
		s=s+((_italics) ? "</i>" : "");
		s=s+((_underline) ? "</u>" : "");
		s=s+((_bold) ? "</b>" : "");
		s+="</font></html>";
		return s;
	}*/
	
	public String toString() {
		int i,n;
		String dotcomma="";
		StringBuffer stops=new StringBuffer();
		for(i=0,n=_tabs.getTabCount();i<n;i++) {
			TabStop s=_tabs.getTab(i);
			String tab=String.format("%d,%d,%d", (int) s.getPosition(),s.getAlignment(),s.getLeader());
			stops.append(dotcomma);
			stops.append(tab);
			dotcomma=";";
		}
		return String.format("id=%s!nm=%s!font=%s!pt=%d!bottom=%d!top=%d!bold=%d!underl=%d!it=%d!color=%s!bgcolor=%s!align=%s!default=%d!tabs=%s!keepwithnext=%d!lindent=%d!extracss=%s",
							_id,_name,
							_font,_point_size,_skip_bottom_point_size,_skip_top_point_size,
							(_bold) ? 1 : 0,(_underline) ? 1 : 0,(_italics) ? 1 : 0,
							color2String(_color),color2String(_bg),
							_align,
							(_default) ? 1 : 0,
							stops.toString(),
							(_keepWithNext) ? 1 : 0,leftIndent(),
							_extraCss
							);
	}
	
	public String getIdForString(String f) {
		String [] d=f.split("[!]");
		int i;
		String name=getTmpStyleName();
		for(i=0;i<d.length;i++) {
			String[] q=d[i].split("[=]",2);
			if (q[0].equals("name")) {
				name=q[1].trim();
			} else if (q[0].equals("id")) {
				name=q[1].trim();
				return name;
			}
		}
		return name;
	}
	
	public void fromString(String f) {
		boolean blocked=blockStyleChangeInform(true);
		String[] d=f.split("[!]");
		int i;
		for(i=0;i<d.length;i++) {
			String[] q=d[i].split("[=]",2);
			if (q[0].equals("font")) { font(q[1].trim()); }
			else if (q[0].equals("pt")) { pointSize(Integer.parseInt(q[1].trim())); }
			else if (q[0].equals("bottom")) { bottomSkip(Integer.parseInt(q[1].trim())); }
			else if (q[0].equals("top")) { topSkip(Integer.parseInt(q[1].trim())); }
			else if (q[0].equals("id")) { id(q[1].trim()); }
			else if (q[0].equals("nm")) { name(q[1].trim()); }
			else if (q[0].equals("name")) { id(q[1].trim()); }
			else if (q[0].equals("descr")) { name(q[1].trim()); }
			else if (q[0].equals("color")) { color(string2Color(q[1].trim())); }
			else if (q[0].equals("bgcolor")) { bgColor(string2Color(q[1].trim())); }
			else if (q[0].equals("bold"))  { int b=Integer.parseInt(q[1].trim());bold((b==1) ? true : false); }
			else if (q[0].equals("underl")) { int u=Integer.parseInt(q[1].trim());underline((u==1)? true : false); }
			else if (q[0].equals("it")) { int it=Integer.parseInt(q[1].trim());italics((it==1) ? true : false); }
			else if (q[0].equals("align")) { align(q[1].trim()); }
			else if (q[0].equals("default")) { int dd=Integer.parseInt(q[1].trim());_default=((dd==1) ? true : false); }
			else if (q[0].equals("tabs")) { 
				String stops=q[1].trim();
				Vector<TabStop> v=new Vector<TabStop>();
				String[] tabs=stops.split("[;]");
				int k;
				for(k=0;k<tabs.length;k++) {
					String[] stop=tabs[k].trim().split("[,]");
					TabStop st=new TabStop((float) Integer.parseInt(stop[0]), Integer.parseInt(stop[1]), Integer.parseInt(stop[2]));
					v.add(st);
				}
				TabStop[] sts=new TabStop[v.size()];
				v.toArray(sts);
				TabSet tabset=new TabSet(sts);
				tabs(tabset);
			} else if (q[0].equals("keepwithnext")) { int bb=Integer.parseInt(q[1].trim());_keepWithNext=((bb==1) ? true : false); }
			else if (q[0].equals("lindent")) { _leftIndent=Integer.parseInt(q[1].trim()); }
			else if (q[0].equals("extracss")) { setExtraCSS(q[1].trim()); }
		}
		blockStyleChangeInform(blocked);
		informStyleChange();
	}
	
	public String asCssStyle() {
		return String.format("font-family: \"%s\"; font-size: %dpt; margin-bottom: %dpt; margin-top: %dpt; "+
				 "font-weigth: %s; font-style: %s; text-decoration: %s; text-align: %s;" +
				 "color: %s; background-color: %s; %s",
				 font(),pointSize(),bottomSkip(),topSkip(),
				 (bold()) ? "bold" : "normal", (italics()) ? "italic" : "normal", (underline()) ? "underline" : "none",
				 _align,
				 color2String(color()), color2String(bgColor()),
				 _extraCss
				 );
	}
	
	public String asCSS(XMLNoteStyleIdConverter cvt) {
		return String.format("%s { %s }", cvt.convert(id()),asCssStyle());
	}
	
	/**
	 * Returns a copy of the current paragraph style, without associating it with a XMLNoteStyles container.
	 * @return
	 */
	public XMLNoteParStyle copy() {
		return new XMLNoteParStyle(null,toString());
	}
	
	private void init(String description,String name,String fnt,int pt,int bottomPt,int topPt,
			boolean bld,boolean italic,boolean underl, String algn,boolean isDef,TabSet ts,boolean keepwn,int lindent,
	 		Color fg,Color bg) {
		boolean blocked=blockStyleChangeInform(true);
		description(description);
		name(name);
		font(fnt);
		pointSize(pt);
		bottomSkip(bottomPt);
		topSkip(topPt);
		bold(bld);
		italics(italic);
		underline(underl);
		align(algn);
		isDefault(isDef);
		tabs(ts);
		keepWithNext(keepwn);
		color(fg);
		bgColor(bg);
		leftIndent(lindent);
		blockStyleChangeInform(blocked);
	}

	private void defInit(XMLNoteStyles container,String name) {
		if (_context==null) {
			_context=new StyleContext();
		}
		_styleContainer=container;
		String stname=contextStyleName("");
		_style=_context.addStyle(stname,_context.getStyle(StyleContext.DEFAULT_STYLE));
		Vector<TabStop> v=new Vector<TabStop>();
		int i;
		for(i=0;i<7;i++) {
			float tab=((float) (i+1))*72.0f;
			v.add(new TabStop(tab,TabStop.ALIGN_LEFT,TabStop.LEAD_NONE));
		}
		TabStop[] sts=new TabStop[i];
		v.toArray(sts);
		tabs(new TabSet(sts));
	}
	
	public XMLNoteParStyle(	XMLNoteStyles container,
							String description,String name,String fnt,int pt,int bottomPt,int topPt,
							boolean bold,boolean italic,boolean underline,
							Color foreground,Color background,
							TabSet tabs,boolean keepWithNext,int leftIndent,
							boolean isDefaultStyle
							) {
		defInit(container,name);
		init(description,
			 name,fnt,pt,
			 bottomPt,topPt,
			 bold,italic,underline,
			 "left",
			 isDefaultStyle,
			 tabs,keepWithNext,leftIndent,
			 foreground,background
			 );
	}

	public XMLNoteParStyle( XMLNoteStyles container,
							String description,String name,String fnt,int pt,int bottomPt,int topPt,
							boolean bold,boolean italic,boolean underline, String align,
							Color foreground,Color background,
							TabSet tabs,boolean keepWithNext,int leftIndent,
							boolean isDefaultStyle
							) {
		defInit(container,name);
		init(description,
			 name,fnt,pt,
			 bottomPt,topPt,
			 bold,italic,underline,
			 align,
			 isDefaultStyle,
			 tabs,keepWithNext,leftIndent,
			 foreground,background
			 );
	}
	
	public XMLNoteParStyle( XMLNoteStyles container, String _fromString) {
		defInit(container,getIdForString(_fromString));
		_styleContainer=container;
		boolean blocked=blockStyleChangeInform(true);
		fromString(_fromString);
		blockStyleChangeInform(blocked);
	}
	
}
