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
 * This interface is used to delegate error handling to. It will be called for exceptions
 * that result in a fatal, error, or warning situation. Classes implementing this interface
 * must implement these three functions.
 * 
 * Codes:
 * <table><tr><th>code</th><th>description</th></tr>
 * <tr><th colspan=2>Warnings</th></tr>
 * <tr><td>10001</td><td>Cannot undo</td></tr>
 * <tr><td>10002</td><td>Cannot redo</td></tr>
 * <tr><td>10100</td><td>Problem with inserting unordered list (item)</td></tr>
 * <tr><td>10200</td><td>Problem with inserting a newline</td></tr>
 * <tr><td>10800</td><td>CSS File cannot be read (I/O problems)</td></tr>
 * <tr><td>10900</td><td>fatal problem (e.g. null pointer exception)</td></tr>
 * <tr><td></td><td></td></tr>
 * <tr><td></td><td></td></tr>
 * <tr><td></td><td></td></tr>
 * <tr><td></td><td></td></tr>
 * </table>
 *  
 * @author Hans Dijkema
 *
 */
public interface XMLNoteErrorHandler {
	
	/**
	 * Called for a fatal error.
	 * 
	 * @param E The catched exception, may be <code>null</code>.
	 * @param code An error code.
	 * @param msg Describes the situation that occured, in untranslated english.
	 */
	public void handleExceptionFatal(Exception E, int code, String msg);
	
	/**
	 * Called for normal (catchable) errors.
	 * 
	 * @param E The catched exception, may be <code>null</code>.
	 * @param code An error code.
	 * @param msg Describes the situation that occured, in untranslated english.
	 */
	public void handleExceptionError(Exception E, int code, String msg);
	
	/**
	 * Called for warnings (that may be ignored).
	 * 
	 * @param E The catched exception, may be <code>null</code>.
	 * @param code A warning code.
	 * @param msg Describes the situation that occured, in untranslated english.
	 */
	public void handleExceptionWarning(Exception E, int code, String msg);

	/**
	 * Called for info messages (that may be ignored).
	 * 
	 * @param msg Describes the situation that occured, in untranslated english.
	 */
	public void handleInfo(String msg);
	

	/**
	 * Handle general exceptions. These are usually non fatal exceptions. 
	 * @param E
	 */
	public void handleException(Exception E);

}
