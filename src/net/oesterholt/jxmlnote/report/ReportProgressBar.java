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

package net.oesterholt.jxmlnote.report;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import net.oesterholt.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.oesterholt.jxmlnote.interfaces.XMLNotePreferences;
import net.oesterholt.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import net.oesterholt.jxmlnote.internationalization.XMLNoteTranslator;
import net.oesterholt.jxmlnote.report.Report.ReportListener;
import net.oesterholt.jxmlnote.report.elements.ReportElement;
import net.oesterholt.jxmlnote.widgets.JXMLNoteSwingUtils;


import org.jdesktop.swingworker.SwingWorker;

public class ReportProgressBar extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private Report				_report;
	private Window				_parentWindow;
	private Component			_parentComponent;
	private Job					_job;
	private Job					_proxy;
	private boolean				_cancelled;
	private XMLNotePreferences 	_prefs;
	private XMLNoteTranslator   _tr;
	
	private JProgressBar		_progressBar;
	private JLabel				_pageNr;
	private JLabel				_message;
	
	public interface Job {
		public void job(Progress p);
		public void cancelled();
	}
	
	public interface Progress {
		public int    progress(int percentage);
		public String statusMessage(String msg);
	}
	
	public boolean cancelled() {
		return _cancelled;
	}
	
	public static ReportProgressBar runJob(Report report,Component parent,String title,Job job,XMLNotePreferences prefs) {
		Window w=(parent instanceof Window) ? (Window) parent : SwingUtilities.getWindowAncestor(parent); 
		ReportProgressBar bar=new ReportProgressBar(report,w,parent,title,job,prefs);
		bar.run();
		return bar;
	}
	
	public void run() {
		class Worker extends SwingWorker<String,String> implements ReportListener,Progress {

			private String prevMsg="";

			protected String doInBackground() throws Exception {
				_job.job(this);
				return "";
			}

			public int progress(int p) {
				int prev=super.getProgress();
				super.setProgress(p);
				return prev;
			}

			public String statusMessage(String msg) {
				String prev=prevMsg;
				firePropertyChange("message","",msg);
				prevMsg=msg;
				return prev;
			}

			protected void done() {
				ReportProgressBar.this.setVisible(false);
			}

			public void makeInfo(Report rep) {
				try {
					Integer pn1,pn2;
					pn1=rep.getCurrentPageNumber()-1;
					pn2=rep.getCurrentPageNumber();
					firePropertyChange("page",pn1,pn2);
				} catch (ReportException e) {
					DefaultXMLNoteErrorHandler.exception(e);
				}
			}

			public void nextPage(Report rep) {
				makeInfo(rep);
			}

			public void endReport(Report rep) {
				makeInfo(rep);
			}

			public Vector<ReportElement> getHeader(Report rep) {
				return null;
			}

			public Vector<ReportElement> getFooter(Report rep) {
				return null;
			} 
		}

		Worker worker=new Worker();
		worker.addPropertyChangeListener(
				new PropertyChangeListener() {
					public  void propertyChange(PropertyChangeEvent evt) {
						String prop=evt.getPropertyName();
						if ("progress".equals(prop)) {
							_progressBar.setValue((Integer)evt.getNewValue());
						} else if ("page".equals(prop)) {
							_pageNr.setText(Integer.toString((Integer) evt.getNewValue()));
						} else if ("message".equals(prop)) {
							_message.setText((String) evt.getNewValue());
						}
					}
				});

		_report.addReportListener(worker);
		_report.setProperty(Report.Property.PROGRESS,worker);
		super.pack();
		Window owner=super.getOwner();
		if (owner!=null) {
			JXMLNoteSwingUtils.centerOnParent(owner,this);
		} else {
			if (this.getParent()!=null) {
				owner=SwingUtilities.getWindowAncestor(this.getParent());
			} else {
				owner=SwingUtilities.getWindowAncestor(this);
			}
			JXMLNoteSwingUtils.centerOnParent(owner,this);
		}
		worker.execute();
		super.setVisible(true);
		_report.removeReportListener(worker);
		_report.removeProperty(Report.Property.PROGRESS);
		super.dispose();
	}
	

	
	public ReportProgressBar(Report report,Window parentw,Component parent,String title,Job job,XMLNotePreferences prefs) {
		super(parentw,title);
		super.setModal(true);
		super.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		_report=report;
		_parentComponent=parent;
		_parentWindow=parentw;
		_job=job;
		_proxy=new Job() {
			public void cancelled() {
				_cancelled=true;
				_job.cancelled();
			}
			public void job(Progress p) {
				_job.job(p);
			}
		};
		_prefs=prefs;
		_tr=new DefaultXMLNoteTranslator();
		
		_progressBar=new JProgressBar();
		_pageNr=new JLabel();
		_pageNr.setText("          ");
		_message=new JLabel(" ");
		JPanel p=new JPanel();
		//p.setLayout(new BorderLayout());
		p.add(_progressBar);
		p.add(new JLabel(_tr._("Page:")));
		p.add(_pageNr);
		JPanel pp=new JPanel();
		pp.setLayout(new BoxLayout(pp,BoxLayout.Y_AXIS));
		pp.add(p);
		pp.add(_message);
		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(pp,BorderLayout.CENTER);
		JButton cancel=new JButton(new AbstractAction(_tr._("Cancel")) {
			public void actionPerformed(ActionEvent e) {
				_proxy.cancelled();
			}
		});
		p1.add(cancel,BorderLayout.SOUTH);
		this.add(p1);
		//super.add(p);
	}
}
