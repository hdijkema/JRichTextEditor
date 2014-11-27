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

package net.dijkema.jxmlnote.html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;

import net.dijkema.jxmlnote.exceptions.BadDocumentException;
import net.dijkema.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.dijkema.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import net.dijkema.jxmlnote.internationalization.XMLNoteTranslator;


import org.w3c.tidy.Tidy;

public class HtmlToXHtml {
	
	static XMLNoteTranslator translator=new DefaultXMLNoteTranslator();
	
	/**
	 * This method converts html to xhtml, using JTidy. It calls <code>fromHtml(url,"Windows-1252",false)</code>.
	 * Windows-1252 is a character encoding almost equal to ISO-8859-1 with a few extra characters. Most browsers
	 * will treat ISO-8859-1 (latin-1) encoding as Windows-1252.
	 * 
	 * @param url				The URL to fetch
	 * @return					The converted HTML as XHTML (i.e. sanitized HTML with JTidy, readable as XML, in UTF-8)
	 * @throws IOException
	 * @throws BadDocumentException
	 */
	static public String fromHtml(URL url) throws IOException, BadDocumentException {
		return fromHtml(url,"Windows-1252",false);
	}

	/**
	 * This method converts html to xhtml, using JTidy. It tries to detect the encoding of the HTML, and will
	 * default to defaultEncoding if it cannot detect the encoding.
	 * 
	 * @param url					The URL to fetch
	 * @param defaultEncoding		The default encoding to use
	 * @param overrideEncoding		true, if the default encoding must always be used
	 * @return						The converted HTML as XHTML (i.e. sanitized HTML with JTidy, readable as XML, in UTF-8)
	 * @throws IOException
	 * @throws BadDocumentException
	 */
	static public String fromHtml(URL url,String defaultEncoding,boolean overrideEncoding) throws IOException, BadDocumentException {
		URLConnection conn=url.openConnection();
		String contentType=conn.getContentType();
		String encoding=conn.getContentEncoding();
		if (encoding==null) { 
			int i=contentType.indexOf("charset");
			if (i>=0) {
				String s=contentType.substring(i);
				i=s.indexOf('=');
				if (i>=0) {
					s=contentType.substring(i+1).trim();
					encoding=s.replace("\'", "").replace("\"", "").trim();
					if (encoding.equals("")) {
						encoding=defaultEncoding;
					}
				}
			} else { // guess defaultEncoding
				encoding=defaultEncoding;
			}
		}
		String expected="text/html";
		if (contentType==null) { // guess html/text
			DefaultXMLNoteErrorHandler.warning(null, 90190, "Returned content type for url.openConnection() is null" );
			contentType=expected;
		}
		int index=contentType.indexOf(';');
		if (index>=0) {
			contentType=contentType.substring(0,index).trim();
		}
		if (!contentType.equals(expected)) {
			String msg=translator.translate("The content type of url '%s' is not '%s', it is '%s'");
			throw new BadDocumentException(String.format(msg,url.toString(),expected,contentType));
		}
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),encoding));
		return fromHtml(in,encoding);
	}
	
	/**
	 * This method reads HTML from a string and expects UTF-8 encoding.
	 *  
	 * @param html
	 * @return
	 * @throws IOException
	 */
	static public String fromHtml(String html) throws IOException {
		Reader in=new StringReader(html);
		return fromHtml(in,"UTF-8");
	}
	
	static protected String fromHtml(Reader in,String inEncoding) throws IOException {
		Writer out=new StringWriter();
		Tidy tidy=new Tidy();
		tidy.setXHTML(true);
		tidy.setDocType("strict");
		tidy.setNumEntities(true);
		tidy.setDropFontTags(true);
		tidy.setDropProprietaryAttributes(true);
		tidy.setFixBackslash(true);
		tidy.setWord2000(true);
		//tidy.setHideComments(true);		// do not hide comments
		tidy.setEncloseText(true);
		tidy.setEncloseBlockText(true);
		tidy.setForceOutput(false);			// don't force output if the HTML cannot be cleaned
		tidy.setOutputEncoding("UTF-8");
		tidy.setInputEncoding(inEncoding);
		//tidy.setOnlyErrors(true);
		tidy.setShowWarnings(false);
		tidy.setQuiet(true);
		tidy.parse(in, out);
		String s=out.toString();
		//return s.replaceAll("[<][!]DOCTYPE[^>]*[>]", "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").replaceAll("[&]nbsp[;]", "&#160;");
		return s.replaceAll("[<][!]DOCTYPE[^>]*[>]", "").replaceAll("[&]nbsp[;]", "&#160;");
		//return s;
		//return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"+s;
	}
}
