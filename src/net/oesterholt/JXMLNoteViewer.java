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

package net.oesterholt;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import net.oesterholt.jxmlnote.document.XMLNoteDocument;
import net.oesterholt.jxmlnote.interfaces.MarkMarkupProviderMaker;
import net.oesterholt.jxmlnote.toolbar.JXMLNoteToolBar;
import net.oesterholt.jxmlnote.widgets.marks.DefaultMarkMarkupProviderMaker;


public class JXMLNoteViewer extends JXMLNoteEditor {
	

	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;

	public void actionPerformed(ActionEvent e) {
		// Allow only copy and selectAll and zooming
		String cmd=e.getActionCommand();
		if (cmd.equals("copy-to-clipboard")) {
			pane().copy();
		} else if (cmd.equals("select-all")) {
			pane().selectAll();
		} else if (cmd.startsWith("zoom")) {
			super.actionPerformed(e);
		} else if (cmd.equals("set-zoom")) {
			super.actionPerformed(e);
		}
	}
	
	// Initialization
	
	private void init() {
		
		// Strip the edit related sections from the toolbar
		JXMLNoteToolBar _toolbar=super.toolbar(); 
		Iterator<String> it=_toolbar.editRelatedSections().iterator();
		while (it.hasNext()) {
			_toolbar.removeSection(it.next());
		}
		
		// Make unresponsive
		super.setUnresponsive(true);
	}
	
	
	/**
	 * Constructs a JXMLNoteViewer with given document and a DefaultMarkMarkupProviderMaker.
	 * 
	 * @param doc
	 */
	public JXMLNoteViewer(XMLNoteDocument doc) {
		this(doc,new DefaultMarkMarkupProviderMaker());
	}
		
	/**
	 * Constructs a JXMLNoteViewer with given docment and MarkMarkupProviderMaker.
	 * @param doc
	 * @param maker
	 */
	public JXMLNoteViewer(XMLNoteDocument doc,MarkMarkupProviderMaker maker) {
		super(doc,maker,false); // without ruler
		init();
	}

}
