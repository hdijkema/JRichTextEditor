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

package net.oesterholt.jxmlnote.interfaces;

public interface JXMLNoteHtmlTidy {
	/**
	 * Tidy up the html. If installed, this interface will be called when HTML is pasted
	 * from the clipboard. It gives one the chance to use e.g. JTidy to tidy up HTML
	 * extra. Not all pasted HTML is sound. JXMLNote does a lot of work to make it better,
	 * but it won't catch all. To be sure, install JXMLNoteHtmlTidy and use e.g. JTidy.
	 * 
	 * @param html
	 * @return
	 */
	String tidy(String html,String charset);
}
