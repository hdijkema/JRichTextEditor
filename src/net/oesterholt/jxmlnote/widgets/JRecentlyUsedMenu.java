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

package net.oesterholt.jxmlnote.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

/**
 * This class provides a recently used list as a JMenu component that
 * can be reused in a menu context.
 * 
 * @author Hans Oesterholt-Dijkema
 */
public class JRecentlyUsedMenu extends JMenu implements ActionListener {
	
	/**
	 * Used to provide basic list functions  
	 *  
	 * @author Hans Oesterholt-Dijkema
	 *
	 */
	public interface RecentlyUsedProvider {
		
		/**
		 * @return the maximum number of items in the list
		 */
		public int getMaxCount(); 
		
		/**
		 * @return The provided list by the environment
		 */
		Vector<String> getList();
		
		/**
		 * @param list The list to store in the environment
		 */
		void           putList(Vector<String> list);
		
		/**
		 * @return Returns the string for clearing the list. 
		 */
		String			clearListText();
	}
	
	private RecentlyUsedProvider _provider;
	private ActionListener		 _listener;
	
	private void storeList() {
		Vector<String> v=new Vector<String>();
		int i,n;
		for(i=0,n=this.getItemCount()-2;i<n;i++) {
			v.add(this.getItem(i).getText());
		}
		_provider.putList(v);
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd=e.getActionCommand();
		if (cmd.equals("clear-recently-used")) {
			while (this.getItemCount()>2) {
				this.remove(0);
			}
			storeList();
		}
	}
	
	private void addRecentUse(String item,boolean front,boolean store) {
		int i,n;
		for(i=0,n=this.getItemCount()-2;i<n && !this.getItem(i).getText().equals(item);i++);
		if (i==n) {
			while (n>=_provider.getMaxCount()) {
				if (front) { this.remove(n-1); }
				else { this.remove(0); }
				n-=1;
			}
			JMenuItem mitem=new JMenuItem(item);
			mitem.setActionCommand("recent:"+item);
			mitem.addActionListener(_listener);
			if (front) { this.insert(mitem,0); }
			else { this.insert(mitem,this.getItemCount()-2); }
		} else {
			JMenuItem mitem=this.getItem(i);
			this.remove(i);
			if (front) { this.insert(mitem, 0); }
			else { this.insert(mitem,this.getItemCount()-2); }
		}
		if (store) { storeList(); }
	}
	
	/**
	 * Adds a new item in the recently used list, or, if it is already
	 * there, it will be put in front of the rest.
	 * 
	 * @param item	Any string that suits the case
	 */
	public void addRecentUse(String item) {
		addRecentUse(item,true,true);
	}
	
	/**
	 * Removes the item (if it exists in the menu) from the menu and
	 * stores the new list.
	 * 
	 * @param item	the item to remove.
	 */
	public void removeRecentUse(String item) {
		int i,n;
		for(i=0,n=this.getItemCount()-2;i<n && !this.getItem(i).getText().equals(item);i++);
		if (i<n) { this.remove(i); }
		storeList();
	}
	
	/**
	 * Creates a menu item, using mnemonics provided by characters preceded by '_'.
	 * 
	 * @param label
	 * @param icon
	 * @param actionCommand
	 * @param listener
	 * @return
	 */
	public static JMenuItem makeMenuItem(String label,Icon icon,String actionCommand,ActionListener listener) {
		int index=label.indexOf("_");
		KeyStroke ks=null;
		if (index>=0) {
			String c=label.substring(index+1,index+2);
			ks=KeyStroke.getKeyStroke(c);
			label=label.replaceFirst("[_]", "");
		}
		JMenuItem item;
		if (icon!=null) {
			item=new JMenuItem(label,icon);
		} else {
			item=new JMenuItem(label);
		}
		if (ks!=null) {
			item.setMnemonic(ks.getKeyCode());
		}
		item.setActionCommand(actionCommand);
		item.addActionListener(listener);
		return item;
	}

	/** 
	 * Creates a menu, using mnemonics provided by characters preceded by '_'.
	 * @param label
	 * @return
	 */
	public static JMenu makeMenu(String label) {
		int index=label.indexOf("_");
		KeyStroke ks=null;
		if (index>=0) {
			String c=label.substring(index+1,index+2);
			ks=KeyStroke.getKeyStroke(c);
			label=label.replaceFirst("[_]", "");
		}
		JMenu menu=new JMenu(label);
		if (ks!=null) {
			menu.setMnemonic(ks.getKeyCode());
		}
		return menu;
	}

	public JRecentlyUsedMenu(String label,RecentlyUsedProvider provider,ActionListener l) {
		super();
		
		int index=label.indexOf("_");
		KeyStroke ks=null;
		if (index>=0) {
			String c=label.substring(index+1,index+2);
			ks=KeyStroke.getKeyStroke(c);
			label=label.replaceFirst("[_]", "");
		}
		super.setText(label);
		if (ks!=null) {
			super.setMnemonic(ks.getKeyCode());
		}
		
		_provider=provider;
		_listener=l;
		
		this.add(new JSeparator());
		String txt=_provider.clearListText();
		if (txt==null) { txt="Clear recently used list"; }
		JMenuItem m=new JMenuItem(txt);
		m.setActionCommand("clear-recently-used");
		m.addActionListener(this);
		this.add(m);
		
		Vector<String> items=provider.getList();
		Iterator<String> it=items.iterator();
		int n;
		n=provider.getMaxCount();
		while (it.hasNext() && (getItemCount()-2)<n) {
			String item=it.next();
			if (item!=null) { 
				item=item.trim();
				if (!item.equals("")) {
					addRecentUse(item,false,false);
				}
			}
		}
	}

}
