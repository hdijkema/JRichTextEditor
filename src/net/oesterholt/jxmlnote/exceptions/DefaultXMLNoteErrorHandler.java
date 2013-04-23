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

package net.oesterholt.jxmlnote.exceptions;

import net.oesterholt.jxmlnote.interfaces.XMLNoteErrorHandler;

/**
 * This class provides a default XMLNoteErrorHandler that is used in all classes of XMLNote.
 * It must handle the Exceptions that are thrown by the objects of XMLNote and supports multiple
 * types of Exceptions: fatals, errors and warnings. Also it must be able to handle generic 
 * Exceptions (i.e. not categorized (these are of type 'error')) and info messages.
 * <p>
 * This class instantiates a default (static) handler, which can be changed 
 * by <code>setErrorHandler()</code>.
 * <p>
 * It provides static methods <code>fatal()</code>, <code>error()</code>, <code>warning</code>,
 * <code>info</code> and <code>exception</code> to handle errors.
 * <p>
 * @author Hans Oesterholt-Dijkema
 *
 */
public class DefaultXMLNoteErrorHandler implements XMLNoteErrorHandler {
	
	private static XMLNoteErrorHandler _handler=null;

	/**
	 * Handles a fatal exception, delegates it to the static handler.
	 */
	public void handleExceptionFatal(Exception E, int code, String msg) {
		checkHandler();
		_handler.handleExceptionFatal(E, code, msg);
	}

	/**
	 * Handles an error exception, delegates it to the static handler.
	 */
	public void handleExceptionError(Exception E, int code, String msg) {
		checkHandler();
		_handler.handleExceptionError(E, code, msg);
	}

	/**
	 * Handles a warning exception, delegates it to the static handler.
	 */
	public void handleExceptionWarning(Exception E, int code, String msg) {
		checkHandler();
		_handler.handleExceptionWarning(E, code, msg);
	}

	/**
	 * Handles an info message, delegates it to the static handler.
	 */
	public void handleInfo(String msg) {
		checkHandler();
		_handler.handleInfo(msg);
	}

	/**
	 * Handles a general exception (i.e. type error Exception). Delegates it to the static handler.
	 */
	public void handleException(Exception E) {
		checkHandler();
		_handler.handleException(E);
	}
	
	private static void checkHandler() {
		if (_handler==null) {
			new DefaultXMLNoteErrorHandler();
		}
	}
	
	/**
	 * Handles a fatal exception. Delegates it to the static handler.
	 * 
	 * @param E		The Exception
	 * @param code	The error code
	 * @param msg	The message
	 */
	public static void fatal(Exception E,int code,String msg) {
		checkHandler();
		_handler.handleExceptionFatal(E, code, msg);
	}

	/**
	 * Handles an error exception. Delegates it to the static handler.
	 * @param E		The Exception
	 * @param code	The error code
	 * @param msg	The message
	 */
	public static void error(Exception E,int code,String msg) {
		checkHandler();
		_handler.handleExceptionError(E, code, msg);
	}
	
	/**
	 * Handles a warning. Delegates it to the static handler.
	 * @param E		The Exception
	 * @param code	The warning code
	 * @param msg	The message
	 */
	public static void warning(Exception E,int code,String msg) {
		checkHandler();
		_handler.handleExceptionWarning(E, code, msg);
	}
	
	/**
	 * Handles an info message. Delegates it to the static handler.
	 * @param msg	The message.
	 */
	public static void info(String msg) {
		checkHandler();
		_handler.handleInfo(msg);
	}

	/**
	 * Handles a generic Exception (i.e. error type). Delegates it to the static handler.
	 * @param E 	The Exception
	 */
	public static void exception(Exception E) {
		checkHandler();
		_handler.handleException(E);
	}
	
	/**
	 * Sets the static error handler to a new one. If <code>h==null</code>, the static handler
	 * will be set to <b>null</b> and will be reset to the standard handler of <code>DefaultXMLNoteErrorHandler</code>
	 * on the next call to one of the Exception/Info handling methods. 
	 * <p>
	 * On first use of one of the static methods of this class, the static handler is <b>null</b>, and the same
	 * behaviour is applicable, i.e. the static handler will be set to the standard handler of <code>DefaultXMLNoteErrorHandler</code>. 
	 * @param h
	 */
	public static void setErrorHandler(XMLNoteErrorHandler h) {
		_handler=h;
	}

	/**
	 * Protected constructor. A <code>DefaultXMLNoteErrorHandler</code> cannot be constructed by a
	 * call from a user. It will construct itself, if <code>setErrorHandler()</code> has not been used
	 * before the first call to one of the static Exception/Info handling methods. 
	 */
	protected DefaultXMLNoteErrorHandler() {
		_handler=new XMLNoteErrorHandler() {
			
			private void handle(String type,Exception E,int code, String msg) {
				System.out.println("Type: "+type+", Code: "+code+", Message: "+msg);
				if (E!=null) { E.printStackTrace(); }
			}

			public void handleExceptionFatal(Exception E, int code, String msg) {
				handle("fatal",E,code,msg);
			}

			public void handleExceptionError(Exception E, int code, String msg) {
				handle("error",E,code,msg);
			}

			public void handleExceptionWarning(Exception E, int code, String msg) {
				handle("warning",E,code,msg);
			}

			public void handleInfo(String msg) {
				handle("info",null,0,msg);
			}

			public void handleException(Exception E) {
				handle("exception",E,0,"Exception");
			}
			
		};
	}
}
