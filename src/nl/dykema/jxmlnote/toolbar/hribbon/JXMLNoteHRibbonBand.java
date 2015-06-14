package nl.dykema.jxmlnote.toolbar.hribbon;

import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import nl.dykema.hribbon.HRibbonBand;
import nl.dykema.hribbon.HRibbonButton;
import nl.dykema.hribbon.HRibbonMenuButton;
import nl.dykema.jxmlnote.toolbar.JXMLNoteIcon;
import nl.dykema.jxmlnote.toolbar.JXMLNoteToolBar;

public class JXMLNoteHRibbonBand {
	
	public static HRibbonBand ToolBarToBand(JXMLNoteToolBar bar, HRibbonBand band) {
		band.setMaxLowButtonsPerRow(1);
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
			JPopupMenu menu=null;
			if (section.charAt(0)=='@') {
				if (!prevMenu && !first) { band.newGroup(); }
				menu=new JPopupMenu();
				menu.setFocusable(false);
				sectionText.replace("▾".subSequence(0, 1), "");
				prevMenu=true;
			} else {
				if (!first) { band.newGroup(); }
				prevMenu=false;
			}
			first=false;
			for(i=0,n=bar.getNumberOfToolsForSection(section);i<n;i++) {
				if (section.charAt(0)=='@') {
					JMenuItem item=bar.getMenuItem(section,i);
					if (item!=null) {
						JXMLNoteIcon xnicn=(JXMLNoteIcon) item.getIcon();
						JMenuItem mb=new JMenuItem(item.getText(), xnicn);
						mb.setActionCommand(item.getActionCommand());
						//mb.setFocusable(false);
						//mb.getActionModel().setActionCommand(item.getActionCommand());
						//if (key!=null) { mb.setActionKeyTip(key); }
						for (ActionListener l : item.getActionListeners()) {
							mb.addActionListener(l);
						}
						menu.add(mb);
					}
				// 	handle menu
				} else {
					JComponent comp=bar.getComponent(section, i);
					if (comp instanceof JButton) {
						JButton b=(JButton) comp;
						
						String txt = b.getToolTipText();  // NOT b.getText() (= empty).
						String cmd = b.getActionCommand();
						String tooltip = b.getToolTipText();
						
						//System.out.println("action = "+cmd + ", txt = "+ txt + ", tt = " + tooltip);
						
						HRibbonButton cb=new HRibbonButton(txt);
						cb.setName(cmd);
						cb.setActionCommand(cmd);
						cb.setToolTipText(tooltip);
						//if (key!=null) { cb.setActionKeyTip(key); }
						cb.setFocusable(false);
						JXMLNoteIcon xnicn=(JXMLNoteIcon) b.getIcon();
						//ResizableIcon icn=(xnicn==null) ? null : new RIcon(xnicn);
						cb.setIcon(xnicn);
//						//cb.getActionModel().setActionCommand(b.getActionCommand());
						for (ActionListener l : b.getActionListeners()) {
							cb.addActionListener(l);
						}
						int prio = HRibbonBand.Priority.LOW;
						if (section.charAt(0)=='!') { prio = HRibbonBand.Priority.TOP; }
						else if (section.charAt(0)=='+') { prio = HRibbonBand.Priority.MEDIUM; }
						band.addButton(cb, prio);
					} else {
						// don't know what to do.
					}
				}
			}
			
			if (menu!=null) {
				HRibbonMenuButton but = new HRibbonMenuButton(sectionText);
				band.addMenu(but, menu, HRibbonBand.Priority.MEDIUM);
			}
		}
		return band;
		
	}

}
