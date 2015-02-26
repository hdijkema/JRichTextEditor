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

package nl.dykema.jxmlnote.widgets;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;

public class JXMLNoteSwingUtils {

	public static final void centerOnParent(Window _owner,Window client) {
		Dimension pd;
		Point     pp;
		Dimension dd=client.getSize();
		
		if (_owner!=null) {
			pd=_owner.getSize();
			pp=_owner.getLocationOnScreen(); 
		} else {
			Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize() ;
			pd=screenSize;
			pp=new Point(0,0);
		}
		int h=(pd.height-dd.height)/2;
		int w=(pd.width-dd.width)/2;
		
		client.setLocation(pp.x+w,pp.y+h);
	}

}
