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

package net.oesterholt.jxmlnote.help;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener; 
import java.awt.image.BufferedImage;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.oesterholt.jxmlnote.interfaces.XMLNotePreferences;
import net.oesterholt.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import net.oesterholt.jxmlnote.internationalization.XMLNoteTranslator;
import net.oesterholt.jxmlnote.toolbar.JXMLNoteIcon;
import net.oesterholt.jxmlnote.toolbar.JXMLNoteToolBar;
import net.oesterholt.jxmlnote.widgets.JRecentlyUsedMenu;


public class HelpEditorApplication {

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (Exception e) {
			System.out.println("Error setting native LAF: " + e);
		}
		
		class EditRun implements Runnable, ActionListener,XMLNotePreferences {

			private JFrame 				_frame;
			private XMLNoteTranslator 	_tr;
			private JHelpEditor			_help;
			private Preferences			_prefs=Preferences.userNodeForPackage(HelpEditorApplication.class);

			public String getString(String key, String _default) {
				return _prefs.get(key, _default);
			}

			public int getInt(String key, int _default) {
				return _prefs.getInt(key, _default);
			}

			public void put(String key, String value) {
				if (value==null) { _prefs.remove(key); }
				else { _prefs.put(key, value); }
			}

			public void put(String key, Integer value) {
				if (value==null) { _prefs.remove(key); }
				else { _prefs.putInt(key, value); }
			}
			
			public void actionPerformed(ActionEvent e) {
				String cmd=e.getActionCommand();
				if (cmd.equals("quit")) {
					quit();
					_frame.setVisible(false);
					System.exit(0);
				}
			}
			
			public void quit() {
				_help.checkTopicsSaved();
				_help.storePrefs();
				storePrefs();
			}
			
			private void storePrefs() {
				Preferences prefs = _prefs;
				Dimension d=_frame.getSize();
				Point p=_frame.getLocation();
				prefs.putInt("x", p.x);
				prefs.putInt("y", p.y);
				prefs.putInt("w", d.width);
				prefs.putInt("h",d.height);
			}
			
			private void applyPrefs() {
				Preferences prefs = _prefs; 
				int x=prefs.getInt("x", 50);
				int y=prefs.getInt("y", 50);
				int w=prefs.getInt("w", 600);
				int h=prefs.getInt("h", 400);
				_frame.setLocation(x,y);
				_frame.setPreferredSize(new Dimension(w,h));
			}
			
			public void run() {
				_frame = new JFrame();
				JXMLNoteIcon appIcn=new JXMLNoteIcon("help",128,128);
				BufferedImage img=new BufferedImage(appIcn.getIconWidth(),appIcn.getIconHeight(),BufferedImage.TYPE_4BYTE_ABGR_PRE);
				Graphics2D g=img.createGraphics();
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				appIcn.paintIcon(null, g, 0, 0);
				g.dispose();
				_frame.setIconImage(img);
				_tr = new DefaultXMLNoteTranslator();
				_help=new JHelpEditor(
					new JHelpEditor.ToolsProvider() {
						public void addTools(JXMLNoteToolBar bar) {
							bar.insertSection("quit",_tr._("Quit operations"));
							bar.add("quit",JXMLNoteToolBar.ACTION_QUIT,_tr._("Quit application"),EditRun.this);
						}
					},
					this
					);
				_help.installMenu(_frame);
				JMenuBar bar=_frame.getJMenuBar();
				JMenu file=bar.getMenu(0);
				file.add(new JSeparator());
				file.add(JRecentlyUsedMenu.makeMenuItem("_Quit", null, "quit", this));
				_frame.add(_help);

				_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				_frame.addWindowListener(new WindowListener() {
					public void windowActivated(WindowEvent e) {
					}

					public void windowClosed(WindowEvent e) {
					}

					public void windowClosing(WindowEvent e) {
						quit();
					}

					public void windowDeactivated(WindowEvent e) {
					}

					public void windowDeiconified(WindowEvent e) {
					}

					public void windowIconified(WindowEvent e) {
					}

					public void windowOpened(WindowEvent e) {
					}
					
				});

				
				applyPrefs();
				_frame.pack();
				_frame.setVisible(true);
			}

		}

		SwingUtilities.invokeLater(new EditRun());
	}
}
