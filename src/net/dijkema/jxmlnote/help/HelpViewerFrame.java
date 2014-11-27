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

package net.dijkema.jxmlnote.help;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.jar.JarFile;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.dijkema.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.dijkema.jxmlnote.interfaces.XMLNotePreferences;
import net.dijkema.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import net.dijkema.jxmlnote.internationalization.XMLNoteTranslator;
import net.dijkema.jxmlnote.toolbar.JXMLNoteToolBar;
import net.dijkema.jxmlnote.widgets.JRecentlyUsedMenu;

import org.omg.CORBA.portable.OutputStream;


public class HelpViewerFrame extends JFrame {
	
	private JHelpViewer			_helpViewer;
	private XMLNoteTranslator 	_tr;
	private XMLNotePreferences  _prefs;
	
	private File					_commandFile;
	private Thread					_commandWorkerThread;
	private CustomCommandProcessor 	_processor;

	public interface CustomCommandProcessor {
		public void process(String info);
	}
	
	class CommandWorker implements Runnable {
		
		private boolean _stop=false;
		
		public synchronized void stop() {
			_stop=true;
		}
		
		public synchronized boolean getStop() {
			return _stop;
		}
		
		public void run() {
			while (!getStop()) {
				if (_commandFile.canRead()) {
					try {
						File lck=new File(_commandFile.getAbsolutePath()+".lck");
						while (lck.exists()) { Thread.sleep(100); }
						
						BufferedReader in=new BufferedReader(new FileReader(_commandFile));
						final String cmd=in.readLine().trim();
						final String par=in.readLine().trim();
						in.close();
						_commandFile.delete();
						
						if (cmd.equals("quithelp")) {
							stop();
							SwingUtilities.invokeLater(new Runnable() { public void run() { quit(); } });
						} else if (cmd.equals("helptitle")) {
							SwingUtilities.invokeLater(new Runnable() { public void run() { setHelpTitle(par); } });
						} else if (cmd.equals("helpfile")) {
							SwingUtilities.invokeLater(new Runnable() { public void run() { setHelpFile(par); } });
						} else if (cmd.equals("helptopic")) {
							SwingUtilities.invokeLater(new Runnable() { public void run() { setHelpTopic(par); } });
						} else if (cmd.equals("custom")) {
							SwingUtilities.invokeLater(new Runnable() { public void run() { customCommand(par); } });
						}
					} catch (Exception e) {
						// do nothing
					}
				} else {
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
					}
				}
			}
		}
	}
	
	
	private void persistPrefs() {
		XMLNotePreferences prefs=_prefs;
		if (prefs!=null) {
			Point p=this.getLocation();
			Dimension d=this.getSize();
			prefs.put("help_x", p.x);
			prefs.put("help_y", p.y);
			prefs.put("help_w", d.width);
			prefs.put("help_h", d.height);
		}
	}
	
	public void quit() {
		HelpViewerFrame.this.firePropertyChange("quit", false, true);
	}
	
	public void setHelpTitle(String title) {
		setTitle(title);
		setVisible(true);
	}
	
	public void setHelpFile(String helpFile) {
		try {
			JarFile fl=new JarFile(helpFile);
			_helpViewer.loadOther(fl);
			this.setVisible(true);
		} catch (Exception E) {
			DefaultXMLNoteErrorHandler.exception(E);
		}
	}
	
	public void customCommand(String par) {
		if (_processor!=null) {
			_processor.process(par);
		}
	}
	
	public void setHelpTopic(String target) {
		this.setVisible(true);
		_helpViewer.setTopic(target);
		this.toFront();
	}
	
	private boolean validCoords(int x,int y,int w,int h) {
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension s=kit.getScreenSize();
		if (x<0 || y<0) { return false; }
		if (((x+w)>s.width) || ((y+h)>s.height)) {
			return false;
		} 
		if (w<50 || h<50) {
			return false;
		}
		return true;
	}
	
	public HelpViewerFrame(String title,JarFile _helpjar,XMLNotePreferences prefs,File cmdfile,CustomCommandProcessor proc) throws Exception {
		super(title);
		_processor=proc;
		_tr=new DefaultXMLNoteTranslator();
		_commandFile=cmdfile;
		_prefs=prefs;
		
		_helpViewer=new JHelpViewer(_helpjar,prefs);

		JPanel panel=new JPanel();
		BorderLayout layout=new BorderLayout();
		layout.setHgap(3);layout.setVgap(3);
		panel.setLayout(layout);
		
		final ActionListener closeAction=new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		};
		
		JMenuBar bar=new JMenuBar();
		JMenu window=JRecentlyUsedMenu.makeMenu(_tr._("_Window"));
		JMenuItem close=JRecentlyUsedMenu.makeMenuItem(_tr._("_Close"), null, "close", closeAction);
		window.add(close);
		bar.add(window);
		
		JPanel menuAndTools=new JPanel();
		menuAndTools.setLayout(new BorderLayout());
		menuAndTools.add(bar,BorderLayout.NORTH);
		JXMLNoteToolBar tbar=_helpViewer.toolbar();
		tbar.initToolBar();
		menuAndTools.add(tbar,BorderLayout.SOUTH);
		panel.add(menuAndTools,BorderLayout.NORTH);
		panel.add(_helpViewer,BorderLayout.CENTER);
		this.add(panel);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		int x=-1,y=-1,w=-1,h=-1;
		if (prefs!=null) {
			x=prefs.getInt("help_x", -1);
			y=prefs.getInt("help_y", -1);
			w=prefs.getInt("help_w",-1);
			h=prefs.getInt("help_h",-1);
		}
		this.pack();
		if (validCoords(x,y,w,h)) {
			this.setLocation(x, y);
			this.setPreferredSize(new Dimension(w,h));
			this.setSize(new Dimension(w,h));
		}
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
		this.addPropertyChangeListener("quit",new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				persistPrefs();
			}
		});
		
		_commandWorkerThread=new Thread(new CommandWorker());
		_commandWorkerThread.start();
	}
}