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

package net.dijkema.jxmlnote.interfaces;

/**
 * This class extends Exception and provides a JXMLNoteException. 
 * JXMLNoteException is used for JXMLNote specific messages.
 *  
 * @author Hans Dijkema
 *
 */
public class JXMLNoteException extends Exception {
	
	private static final long serialVersionUID = -6641332030979830944L;
	
	private String _message;
	private String _details;
	
	/**
	 * @return String The short message about the exception.
	 */
	public String getLocalizedMessage() {
		return _message;
	}
	
	/**
	 * @return String The detail about the exception. 
	 */
	public String getMessage() {
		return _details;
	}
	
	/**
	 * Constructs the exception from the msg and details. msg must be localized
	 * on construction.
	 * 
	 * @param msg
	 * @param details
	 */
	public JXMLNoteException(String msg,String details) {
		_message=msg;
		_details=details;
	}
}
