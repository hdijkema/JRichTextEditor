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

package nl.dykema.jxmlnote.toolbar;

import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;

import nl.dykema.jxmlnote.document.XMLNoteDocument;


public class JXMLNoteToolBarReflect {
	
	private JXMLNoteToolBar _toolbar;
	
	// Reflect in toolbar
	
	private boolean _a_left=false,_a_right=false,_a_center=false,_a_justify=false;
	
	private void reflectAlign(boolean l,boolean r,boolean c,boolean j) {
		if (_a_left!=l)    { _toolbar.setSelected("align-left",l);_a_left=l; }
		if (_a_right!=r)   { _toolbar.setSelected("align-right",r);_a_right=r; }
		if (_a_center!=c)  { _toolbar.setSelected("align-center",c);_a_center=c; }
		if (_a_justify!=j) { _toolbar.setSelected("align-justify",j);_a_justify=j; }
	}
	
	private boolean _a_bold=false,_a_italic=false, _a_underline=false;
	
	private void reflectCharAttr(boolean b,boolean i,boolean u) {
		if (_a_bold!=b) 		{ _toolbar.setSelected("font-bold",b);_a_bold=b; }
		if (_a_italic!=i) 		{ _toolbar.setSelected("font-italic",i);_a_italic=i; }
		if (_a_underline!=u) 	{ _toolbar.setSelected("font-underline",u);_a_underline=u; }
	}
	
	/**
	 * This method takes care of reflecting the toolbar for a given offset in the given document.
	 */
	public void reflect(XMLNoteDocument _doc,int _caretPosition) {
		Element e=_doc.getParagraphElement(_caretPosition);
		AttributeSet s=e.getAttributes(); 

		// alignment
		{
			Object q=s.getAttribute(StyleConstants.Alignment);
			if (q==null) 											{ reflectAlign(true,false,false,false); }
			else {
				if (q.equals(StyleConstants.ALIGN_LEFT)) 			{ reflectAlign(true,false,false,false); }
				else if (q.equals(StyleConstants.ALIGN_RIGHT)) 		{ reflectAlign(false,true,false,false); }
				else if (q.equals(StyleConstants.ALIGN_CENTER)) 	{ reflectAlign(false,false,true,false); }
				else if (q.equals(StyleConstants.ALIGN_JUSTIFIED))  { reflectAlign(false,false,false,true); }
			}
		}
		
		// Character attributes
		{
			Element c=_doc.getCharacterElement(_caretPosition);
			AttributeSet cs=c.getAttributes();
			reflectCharAttr(cs.containsAttribute(StyleConstants.Bold, true),
							cs.containsAttribute(StyleConstants.Italic, true),
							cs.containsAttribute(StyleConstants.Underline, true));
		}
	}
	
	/**
	 * Construct the toolbar reflector.
	 * @param bar
	 */
	public JXMLNoteToolBarReflect(JXMLNoteToolBar bar) {		
		_toolbar=bar;
	}
		

}
