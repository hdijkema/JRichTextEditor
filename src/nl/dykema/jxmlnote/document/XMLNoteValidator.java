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

package nl.dykema.jxmlnote.document;

import nl.dykema.jxmlnote.exceptions.BadDocumentException;
import nl.dykema.jxmlnote.exceptions.BadStyleException;
import nl.dykema.jxmlnote.interfaces.MarkMarkupProvider;
import nl.dykema.jxmlnote.interfaces.MarkMarkupProviderMaker;
import nl.dykema.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import nl.dykema.jxmlnote.internationalization.XMLNoteTranslator;
import nl.dykema.jxmlnote.widgets.marks.DefaultMarkMarkupProvider;


/**
 * This class validates an XML document against the xmlnote parser.
 *  
 * @author Hans Dijkema
 *
 */
public class XMLNoteValidator extends XMLNoteXMLIn {
	
	XMLNoteTranslator _translator;
	String            _message;
	String			  _details;
	
	String message() {
		return _message;
	}
	
	String details() {
		return _details;
	}
	
	public boolean validate(String xml) {
		_message="";
		_details="";
		try {
			super.fromXML(xml,null); 
		} catch (BadDocumentException e) {
			_message=e.getMessage();
			_details=e.getLocalizedMessage();
			return false;
		}
		return true;
	}
	
	protected XMLNoteValidator(XMLNoteDocument d,XMLNoteImageIcon.Provider prov) {
		super(d);
		d.setXMLNoteImageIconProvider(prov);
		_translator=new DefaultXMLNoteTranslator();
	}
	
    public XMLNoteValidator(XMLNoteImageIcon.Provider prov) throws BadStyleException {
    	this(new XMLNoteDocument(),prov);
    }
    
}
