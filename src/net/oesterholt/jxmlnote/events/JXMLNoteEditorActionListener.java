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

package net.oesterholt.jxmlnote.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import net.oesterholt.JXMLNoteEditor;

public class JXMLNoteEditorActionListener implements ActionListener {
	
	private Vector<ActionEvent>   v=new Vector<ActionEvent>();
	private JXMLNoteEditor 		_me=null;
	
	public void setMe(JXMLNoteEditor me) { 
		_me=me;
		Iterator<ActionEvent> it=v.iterator();
		while (it.hasNext()) {
			actionPerformed(it.next());
		}
	}
	
	public void actionPerformed(ActionEvent e) { 
		if (_me==null) {
			v.add(e);
		} else {
			_me.actionPerformed(e); 
		} 
	}
}
