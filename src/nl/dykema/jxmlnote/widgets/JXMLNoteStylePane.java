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

package nl.dykema.jxmlnote.widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;

import nl.dykema.jxmlnote.document.XMLNoteDocument;
import nl.dykema.jxmlnote.exceptions.BadStyleException;
import nl.dykema.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import nl.dykema.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import nl.dykema.jxmlnote.internationalization.XMLNoteTranslator;
import nl.dykema.jxmlnote.styles.XMLNoteParStyle;
import nl.dykema.jxmlnote.styles.XMLNoteStyles;
import nl.dykema.jxmlnote.utils.DPIAdjuster;
import nl.dykema.jxmlnote.widgets.JSizeGroup.Exception;


public class JXMLNoteStylePane extends JPanel  {
	
	/**
	 * Versie
	 */
	private static final long serialVersionUID = 1L;
	private static final int  MAX_INT = 2000000000;
	
	private JXMLNotePane		_examplePane;
	private JList				_styles;
	//private JTextField			_description;
	private JTextField			_name;
	//private JButton				_font;
	private JComboBox			_font;
	private JComboBox			_fontSize;
	private JComboBox			_align;
	private JSpinner			_topSkip;
	private JSpinner			_bottomSkip;
	private SpinnerNumberModel	_mTopSkip;
	private SpinnerNumberModel  _mBottomSkip;
	private JColorButton		_foreground;
	//private JColorButton		_background;  // Background coloring doesn't work with swing
	private JCheckBox			_bold;
	private JCheckBox			_italics;
	private JCheckBox			_underline;
	
	private XMLNoteStyles		_noteStyles;
	
	private	XMLNoteTranslator 	_translator;
	
	private JSizeGroup			_labels;
	
	private XMLNoteParStyle		_currentStyle;
	private boolean				_inSetStyle=false;
	private String[]			_fontFamilyNames;
	private Integer[]			_fontSizes;
	
	protected String[] getFontFamilies()
	{
		if (_fontFamilyNames == null)
		{
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			_fontFamilyNames = env.getAvailableFontFamilyNames();
		}
		return _fontFamilyNames;
	}
	
	protected Integer[] getFontSizes() {
		if (_fontSizes==null) {
			_fontSizes=new Integer[]{8,9,10,11,12,14,16,18,20,24,28,32,36,40,50,60,72};
		}
		return _fontSizes;
	}
	
	private void setStyle(XMLNoteParStyle st) {
		boolean prev=_inSetStyle;
		if (!_inSetStyle) {
			_inSetStyle=true;
			_currentStyle=null;		// prevent these state changes to interfere
			MutableAttributeSet s=st.getStyle(DPIAdjuster.DEVICE_SCREEN);
			DefaultStyledDocument d=(DefaultStyledDocument) _examplePane.getDocument();
			d.setParagraphAttributes(5, d.getLength()-10, s, true);
			//_font.setText(st.font());
			_font.setSelectedItem(st.font());
			//_fontSize.setText(String.format("%d",st.pointSize()));
			_fontSize.setSelectedItem(st.pointSize());
			_name.setText(st.name());
			_mTopSkip.setValue(st.topSkip());
			_mBottomSkip.setValue(st.bottomSkip());
			_foreground.setValue(st.color());
			//	_background.setValue(st.bgColor());
			_currentStyle=st;
			_bold.setSelected(st.bold());
			_italics.setSelected(st.italics());
			_underline.setSelected(st.underline());
			_currentStyle=st;
		}
		_inSetStyle=prev;
	}
	
	private void setMaxHeight(Component c) {
		Dimension d=c.getPreferredSize();
		c.setMaximumSize(new Dimension(2000000000,d.height));
	}
	
	private void addle(JPanel p,String label,Component c) {
		addle(p,label,c,null,null);
	}
	
	private void addle(JPanel p,String label,Component c1,Component c2) {
		addle(p,label,c1,null,c2);
	}

	private void addle(JPanel p,String label1,Component c1,String label2,Component c2) {
		JPanel pp=new JPanel();
		pp.setLayout(new BoxLayout(pp,BoxLayout.LINE_AXIS));
		JLabel l1=new JLabel(_translator.translate(label1));
		l1.setAlignmentX(Component.LEFT_ALIGNMENT);
		pp.add(l1);_labels.add(l1);//setMaxHeight(l1);
		pp.add(c1);//setMaxHeight(c1);
		if (label2!=null) {
			JLabel l2=new JLabel(_translator.translate(label2));
			l2.setAlignmentX(Component.LEFT_ALIGNMENT);
			pp.add(l2);_labels.add(l2);//setMaxHeight(l2);
		}
		if (c2!=null) {
			pp.add(c2);//setMaxHeight(c2); 
		} else {
			//pp.add(Box.createHorizontalGlue());
		}
		//if (label2==null) { pp.add(Box.createHorizontalGlue()); }
		setMaxHeight(pp);
		p.add(pp);
	}

	private void init(XMLNoteTranslator t,XMLNoteStyles s) {
		_translator=t;
		_noteStyles=s;
		_labels=new JSizeGroup(JSizeGroup.HORIZONTAL);
		
		if (_translator==null) { _translator=new DefaultXMLNoteTranslator(); }
		try {
			_examplePane=new JXMLNotePane(new XMLNoteDocument(s),new ActionListener() {
				public void actionPerformed(ActionEvent e0) {
					// does nothing
				}
			});
		} catch (BadStyleException e1) {
			// unexpected
			DefaultXMLNoteErrorHandler.exception(e1);
		}
		//_examplePane.setBackground(Color.white);
		String txt=_translator.translate("----#The quick brown fox jumps over the lazy dog#----");
		txt=txt.replace('#', '\n');
		_examplePane.setText(txt);
		_styles=new JList(new AbstractListModel() {
			/**
			 * Version
			 */
			private static final long serialVersionUID = 1L;

			public Object getElementAt(int i) {
				return _noteStyles.getStyleName(i);
			}

			public int getSize() {
				return _noteStyles.getNumberOfStyles();
			}
		});
		//_styles.setPreferredSize(new Dimension(300,50));
		
		_styles.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				//System.out.println(e);
				if (!e.getValueIsAdjusting()) {
					int index=_styles.getSelectedIndex();
					setStyle(_noteStyles.getStyle(index));
				}
			}
			
		});
		
		
		_name=new JTextField();
		_font=new JComboBox(getFontFamilies());
		_font.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()==ItemEvent.SELECTED) {
					String fontName=(String) e.getItem();
					if (fontName!=null) {
						if (_currentStyle!=null) {
							_currentStyle.font(fontName);
						}
					}
				}
			}
		});
		
		_fontSize=new JComboBox(getFontSizes());
		{ 
			Dimension d=_fontSize.getPreferredSize();
			d.width*=1.5;
			_fontSize.setPreferredSize(d);
		}

		_fontSize.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()==ItemEvent.SELECTED) {
					Integer fontSize=(Integer) e.getItem();
					if (fontSize!=null) {
						if (_currentStyle!=null) {
							_currentStyle.pointSize(fontSize);
						}
					}
				}
			}
		});
		
		String[] aligns={_translator.translate("left"),
						 _translator.translate("right"),
						 _translator.translate("center"),
						 _translator.translate("justify")
		};
		
		_underline=new JCheckBox(_translator.translate("Underline"));
		_underline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (_currentStyle!=null) {
					_currentStyle.underline(_underline.isSelected());
					setStyle(_currentStyle);
				}
			}
		});
		_bold=new JCheckBox(_translator.translate("Bold"));
		_bold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (_currentStyle!=null) {
					_currentStyle.bold(_bold.isSelected());
					setStyle(_currentStyle);
				}
			}
		});
		_italics=new JCheckBox(_translator.translate("Italic"));
		_italics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (_currentStyle!=null) {
					_currentStyle.italics(_italics.isSelected());
					setStyle(_currentStyle);
				}
			}
		});
		
		_align=new JComboBox(aligns);
		_align.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (_currentStyle!=null) {
					_currentStyle.align((String) _align.getSelectedItem());
					setStyle(_currentStyle);
				}
			}
		});
		_mTopSkip=new SpinnerNumberModel(0,0,50,1);
		_topSkip=new JSpinner(_mTopSkip);
		_topSkip.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (_currentStyle!=null) {
					_currentStyle.topSkip((Integer) _mTopSkip.getValue());
					setStyle(_currentStyle);
				}
			}
		});
		_mBottomSkip=new SpinnerNumberModel(0,0,50,1);
		_bottomSkip=new JSpinner(_mBottomSkip);
		_bottomSkip.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (_currentStyle!=null) {
					_currentStyle.bottomSkip((Integer) _mBottomSkip.getValue());
					setStyle(_currentStyle);
				}
			}
		});
		_foreground=new JColorButton(Color.black,_translator.translate("Font color"));
		_foreground.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (_currentStyle!=null) {
					_currentStyle.color(_foreground.getValue());
					setStyle(_currentStyle);
				}
			}
		});
		
		int l=BoxLayout.LINE_AXIS;
		this.setLayout(new BoxLayout(this,l));
		{
			JScrollPane sp=new JScrollPane(_styles,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			this.add(sp);
		}
		{
			JPanel edits=new JPanel();
			edits.setLayout(new BoxLayout(edits,BoxLayout.PAGE_AXIS));
			{
				JScrollPane ep=new JScrollPane(_examplePane,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				_examplePane.setPreferredSize(new Dimension(350,75));
				TitledBorder b=new TitledBorder(BorderFactory.createEtchedBorder(), _translator.translate("Sample"), TitledBorder.LEFT, TitledBorder.CENTER);
				ep.setBorder(b);
				edits.add(ep);
			}
			addle(edits,"Stylename :",_name);
			{
				JPanel ppp=new JPanel();
				ppp.setLayout(new FlowLayout(FlowLayout.LEFT));
				ppp.add(_font);
				ppp.add(_fontSize);
				ppp.setAlignmentX(LEFT_ALIGNMENT);
				addle(edits,"Font :",ppp);
			}
			{
				JPanel ppp=new JPanel();
				ppp.setLayout(new FlowLayout(FlowLayout.LEFT));
				ppp.add(_bold);
				ppp.add(_italics);
				ppp.add(_underline);
				addle(edits,"Attributes :",ppp);
			}
			addle(edits,"Alignment :",_align);
			addle(edits,"Skip top :",_topSkip,"Skip bottom :",_bottomSkip);
			{
				JPanel fg=new JPanel();
				fg.add(_foreground);fg.add(Box.createHorizontalGlue());
				fg.setLayout(new BoxLayout(fg,BoxLayout.LINE_AXIS));
				JPanel bg=new JPanel();
				addle(edits,"Foreground color :",fg,Box.createHorizontalGlue()); //,"Background color :",bg);
			}
			this.add(edits);
		}
		
		// correct labels and widgets width
		_labels.align();
		
		
		// Select the default paragraph style
		int defStyleIndex=_noteStyles.getDefaultStyleIndex();
		_styles.setSelectedIndex(defStyleIndex);
	}
	
	/**
	 * Constructs a JXMLNoteStylePane with the given styles.
	 * 
	 * @param styles
	 */
	public JXMLNoteStylePane(XMLNoteStyles styles) {
		init(null,styles);
	}
	
	/**
	 * Constructs a JXMLNoteStylePane with newly generated styles.
	 */
	public JXMLNoteStylePane() {
		this(new XMLNoteStyles());
	}

}
