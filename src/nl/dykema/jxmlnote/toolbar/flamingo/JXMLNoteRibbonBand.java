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

package nl.dykema.jxmlnote.toolbar.flamingo;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;

import nl.dykema.jxmlnote.toolbar.JXMLNoteIcon;
import nl.dykema.jxmlnote.toolbar.JXMLNoteToolBar;
import nl.dykema.jxmlnote.toolbar.JXMLNoteIcon.DimensionProvider;

import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.JCommandMenuButton;
import org.pushingpixels.flamingo.api.common.RichTooltip;
import org.pushingpixels.flamingo.api.common.JCommandButton.CommandButtonKind;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import org.pushingpixels.flamingo.api.common.popup.JCommandPopupMenu;
import org.pushingpixels.flamingo.api.common.popup.JPopupPanel;
import org.pushingpixels.flamingo.api.common.popup.PopupPanelCallback;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.RibbonElementPriority;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.flamingo.api.ribbon.resize.RibbonBandResizePolicy;

class RIcon extends JXMLNoteIcon implements ResizableIcon {

	public void setDimension(Dimension d) {
		final Dimension size=d;
		super.setSizeProvider(new DimensionProvider() {
			public Dimension buttonSize() {
				return size;
			}
		});
	}
	
	public RIcon(JXMLNoteIcon icn) {
		super(icn);
	}
}

public class JXMLNoteRibbonBand  {

	private static final long serialVersionUID = 1L;
	
	public static JRibbonBand ToolBarToBand(JXMLNoteToolBar bar,JRibbonBand band) {
		ArrayList<RibbonBandResizePolicy> A=new ArrayList<RibbonBandResizePolicy>();
		A.add(new CoreRibbonResizePolicies.Mirror(band.getControlPanel()));
		band.setResizePolicies(A);
		Vector<String> sections=bar.currentSections();
		Iterator<String> it=sections.iterator();
		boolean first=true;
		boolean prevMenu=false;
		while (it.hasNext()) {
			String section=it.next();
			String sectionText=bar.getSectionTextForSection(section);
			
			int index=sectionText.indexOf('_');
			String key=null;
			if (index>=0) {
				key=sectionText.substring(index+1,index+2);
				sectionText=sectionText.substring(0,index)+sectionText.substring(index+1);
			}
			
			int i,n;
			JCommandPopupMenu menu=null;
			if (section.charAt(0)=='@') {
				if (!prevMenu && !first) { band.startGroup(); }
				menu=new JCommandPopupMenu();
				menu.setFocusable(false);
				sectionText.replace("▾".subSequence(0, 1), "");
				prevMenu=true;
			} else {
				if (!first) { band.startGroup(); }
				prevMenu=false;
			}
			first=false;
			for(i=0,n=bar.getNumberOfToolsForSection(section);i<n;i++) {
				if (section.charAt(0)=='@') {
					JMenuItem item=bar.getMenuItem(section,i);
					if (item!=null) {
						JXMLNoteIcon xnicn=(JXMLNoteIcon) item.getIcon();
						ResizableIcon icn=(xnicn==null) ? null : new RIcon(xnicn);
						JCommandMenuButton mb=new JCommandMenuButton(item.getText(), icn);
						mb.setFocusable(false);
						mb.getActionModel().setActionCommand(item.getActionCommand());
						if (key!=null) { mb.setActionKeyTip(key); }
						for (ActionListener l : item.getActionListeners()) {
							mb.addActionListener(l);
						}
						menu.addMenuButton(mb);
					}
				// 	handle menu
				} else {
					JComponent comp=bar.getComponent(section,i);
					if (comp instanceof JButton) {
						JButton b=(JButton) comp;
						JCommandButton cb=new JCommandButton(b.getToolTipText());
						cb.setName(b.getActionCommand());
						cb.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
						RichTooltip tip=new RichTooltip(band.getTitle(),b.getToolTipText());
						cb.setActionRichTooltip(tip);
						if (key!=null) { cb.setActionKeyTip(key); }
						cb.setFocusable(false);
						JXMLNoteIcon xnicn=(JXMLNoteIcon) b.getIcon();
						ResizableIcon icn=(xnicn==null) ? null : new RIcon(xnicn);
						cb.setIcon(icn);
						cb.getActionModel().setActionCommand(b.getActionCommand());
						for (ActionListener l : b.getActionListeners()) {
							cb.addActionListener(l);
						}
						RibbonElementPriority prio=RibbonElementPriority.LOW;
						if (section.charAt(0)=='!') { prio=RibbonElementPriority.TOP; }
						else if (section.charAt(0)=='+') { prio=RibbonElementPriority.MEDIUM; }
						band.addCommandButton(cb, prio);
					} else {
						// don't know what to do.
					}
				}
			}
			if (menu!=null) {
				JCommandButton but=new JCommandButton(sectionText);
				but.setName(section);
				but.setFocusable(false);
				but.setCommandButtonKind(CommandButtonKind.POPUP_ONLY);
				if (key!=null) { but.setActionKeyTip(key); }
				final JCommandPopupMenu mn=menu;
				but.setPopupCallback(new PopupPanelCallback() {
					public JPopupPanel getPopupPanel(JCommandButton commandButton) {
						return mn;
					}
				});
				band.addCommandButton(but,RibbonElementPriority.MEDIUM);
			}
		}
		return band;
	}

}
