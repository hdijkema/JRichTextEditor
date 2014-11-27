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

package net.dijkema.jxmlnote.xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.dijkema.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * This class provides some static methods for XMLNote XML handling.
 * <p>
 * @author Hans Dijkema
 */
public class XMLNoteUtils {

	/**
	 * This method inserts the provided string, which should be formatted as XMLNote notes content (paragraphs, etc.)
	 * at the top of the xmlnoteDocument. 
	 * 
	 * @param xmlnoteDocument		An XMLNote XML document.
	 * @param xmlnoteText			XMLNote notes content (paragraphs with text and markup).
	 * @return						string with the xmlnotetext inserted at the top.
	 */
	public static String insertXMLNoteTextAtTheTop(String xmlnoteDocument,String xmlnoteText) {
		xmlnoteDocument=xmlnoteDocument.replaceAll("[<]notes\\s+[/][>]", "<notes></notes>");
		String[] txts=xmlnoteDocument.split("[<]notes[>]",2);
		return txts[0]+"<notes>"+xmlnoteText+txts[1];
	}
	
    /**
     * This method tries to format the input XML. It expects Well Formed XML. If it doesn't get
     * Well Formed XML, it will return null.
     * 
     * @param xml		The well formed xml to pretty print.
     * @return			The string with the pretty printed xml.
     */
	public static String prettyPrintXML(String xml) {
		try {
			return format(xml);
		} catch (Exception e) {
			DefaultXMLNoteErrorHandler.exception(e);
			return null;
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	// private supporting methods
	//////////////////////////////////////////////////////////////////////////////////////////////////

	private static String format(String unformattedXml) throws IOException, ParserConfigurationException, SAXException {
		
		final Document document = parseXmlFile(unformattedXml);

		OutputFormat format = new OutputFormat(document);
		format.setLineWidth(100);
		format.setIndenting(true);
		format.setIndent(2);
		Writer out = new StringWriter();
		XMLSerializer serializer = new XMLSerializer(out, format);
		serializer.serialize(document);

		return out.toString();
    }

    private static Document parseXmlFile(String in) throws ParserConfigurationException, SAXException, IOException {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String arg0, String arg1)
						throws SAXException, IOException {
					return new InputSource(new StringReader(""));
				}
            });
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
    }

}
