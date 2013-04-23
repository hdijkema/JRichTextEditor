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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.Stack;
import java.util.WeakHashMap;
import java.util.concurrent.Semaphore;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.oesterholt.JXMLNoteViewer;
import net.oesterholt.jxmlnote.document.XMLNoteDocument;
import net.oesterholt.jxmlnote.document.XMLNoteImageIcon;
import net.oesterholt.jxmlnote.document.XMLNoteImageIconSize;
import net.oesterholt.jxmlnote.document.XMLNoteMark;
import net.oesterholt.jxmlnote.document.XMLNoteImageIcon.LoadedListener;
import net.oesterholt.jxmlnote.exceptions.BadStyleException;
import net.oesterholt.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.oesterholt.jxmlnote.help.data.HelpEntry;
import net.oesterholt.jxmlnote.help.data.HelpImage;
import net.oesterholt.jxmlnote.help.data.HelpTopic;
import net.oesterholt.jxmlnote.help.data.HelpImage.ImageLoadedListener;
import net.oesterholt.jxmlnote.interfaces.MarkMarkupProvider;
import net.oesterholt.jxmlnote.interfaces.MarkMarkupProviderMaker;
import net.oesterholt.jxmlnote.interfaces.XMLNotePreferences;
import net.oesterholt.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import net.oesterholt.jxmlnote.internationalization.XMLNoteTranslator;
import net.oesterholt.jxmlnote.report.ReportException;
import net.oesterholt.jxmlnote.report.ReportProgressBar;
import net.oesterholt.jxmlnote.report.XMLNoteToReport;
import net.oesterholt.jxmlnote.report.ReportProgressBar.Progress;
import net.oesterholt.jxmlnote.report.pdf.PdfReport;
import net.oesterholt.jxmlnote.report.viewers.PdfViewer;
import net.oesterholt.jxmlnote.toolbar.JXMLNoteIcon;
import net.oesterholt.jxmlnote.toolbar.JXMLNoteToolBar;
import net.oesterholt.jxmlnote.widgets.JXMLNotePane;
import net.oesterholt.jxmlnote.widgets.marks.DefaultMarkMarkupProvider;
import net.oesterholt.jxmlnote.widgets.marks.MarkMouseListener;


public class JHelpViewer extends JPanel implements TreeSelectionListener,
		XMLNoteImageIcon.Provider, MarkMouseListener, ActionListener {

	private static final long serialVersionUID = 1L;
	
	private DefaultTreeModel _treeModel;
	private JTree _tree;
	private HelpTopic _current;
	private XMLNoteDocument _document;
	private JXMLNoteViewer _viewer;
	private JXMLNoteToolBar _toolbar;
	private JarFile _helpjar;
	private JSplitPane _splitpane;
	private XMLNotePreferences _prefs;
	private DefaultMutableTreeNode _topics;
	private Stack<HelpTopic> _pastTopics;
	private Stack<HelpTopic> _futureTopics;
	private XMLNoteTranslator _tr;
	private DefaultMarkMarkupProvider _markProvider;
	private DefaultMarkMarkupProvider _hyperlinkProvider;
	private Color	_cTopic=Color.blue;
	private Color   _cHyperlink=new Color(101,0,168);
	private boolean	_inSession=false;
	private boolean _canceled=false;
	

	private String getPrefKey(String k) {
		String name = JHelpViewer.this.getName();
		if (name == null) {
			name = "default";
		}
		String nm = this.getClass().getName() + "." + name + "." + k;
		nm.replaceAll("[.]", "_");
		return nm;
	}

	private void createGUI() throws BadStyleException {
		BorderLayout layout = new BorderLayout();
		layout.setHgap(3);
		layout.setVgap(3);
		super.setLayout(layout);

		_tree = new JTree(_treeModel);
		_tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		_tree.setRootVisible(false);
		_tree.addTreeSelectionListener(this);

		_document = new XMLNoteDocument();
		_document.setImageSupport(true);
		_document.setXMLNoteImageIconProvider(this);

		_markProvider = new DefaultMarkMarkupProvider(
				MarkMarkupProvider.MarkupType.UNDERLINED, _cTopic);
		_markProvider.setTextColor(_cTopic);

		_hyperlinkProvider = new DefaultMarkMarkupProvider(
				MarkMarkupProvider.MarkupType.UNDERLINED, _cHyperlink);
		_hyperlinkProvider.setTextColor(_cHyperlink);


		_viewer = new JXMLNoteViewer(_document, new MarkMarkupProviderMaker() {
			public MarkMarkupProvider create(String markId, String markClass) {
				if (markClass.startsWith("TopicLink:")) {
					return _markProvider;
				} else {
					return _hyperlinkProvider;
				}
			}
		});
		_viewer.pane().addMarkMouseListener(this);
		_viewer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		_splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(_tree), _viewer);
		_splitpane.setContinuousLayout(true);
		_splitpane.addPropertyChangeListener(
				JSplitPane.DIVIDER_LOCATION_PROPERTY,
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
						Object o = e.getNewValue();
						if (o instanceof Integer) {
							_prefs.put(getPrefKey("Dividerlocation"),
									(Integer) o);
						}
					}
				});
		_splitpane.setDividerLocation(_prefs.getInt(getPrefKey("Dividerlocation"), 150));
		

		_toolbar = _viewer.toolbar();
		_toolbar.setButtonSize(24);
		_toolbar.removeAllSections();
		_toolbar.addSection("navigate");
		_toolbar.add("navigate", "previous", _tr._("Previous topic"), this);
		_toolbar.add("navigate", "next", _tr._("Future topic"), this);
		_toolbar.addSection("printing");
		_toolbar.add("printing","print",_tr._("Print topic"),this);
		_toolbar.addSection("@zoom");

		double z=Double.parseDouble(_prefs.getString(getPrefKey("Zoom"), "1.0"));
		if (z<0.25) { z=0.25; }
		_viewer.pane().setZoomFactor(z);
		
		_viewer.pane().addPropertyChangeListener(JXMLNotePane.PROPERTY_ZOOM, new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				Object o=e.getNewValue();
				if (o instanceof Double) {
					_prefs.put(getPrefKey("Zoom"),Double.toString((Double) o));
				}
			}
		});
		

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		super.add(_splitpane, BorderLayout.CENTER);
	}

	
	public void createPdf(File output) {
		final PdfReport pdf;
		final XMLNoteToReport report;
		try {

			pdf = new PdfReport(null);			// Help is part of an application, so expect the font mapper 
												// to initialize fonts elsewhere 
			final File reportFile=output;
			pdf.beginReport(reportFile);
			
			report=new XMLNoteToReport(pdf);
			_canceled=false;
			ReportProgressBar bar=ReportProgressBar.runJob(pdf, this, _tr._("Printing"), new ReportProgressBar.Job() {
				public void job(Progress p) {
					try {
						p.progress(0);
						p.statusMessage(String.format(_tr._("Creating %s ..."), reportFile.getName()));
						pdf.addXMLNote(_document,new MarkMarkupProviderMaker() {
							public MarkMarkupProvider create(String markId,String markClass) {
								return _markProvider;
							}
						}, null);							
						p.progress(100);
						pdf.endReport();
					} catch (ReportException e) {
						DefaultXMLNoteErrorHandler.exception(e);
					}
				}

				public void cancelled() {
					try {
						pdf.cancel();
						_canceled=true;
					} catch (ReportException e) {
						DefaultXMLNoteErrorHandler.exception(e);
					}
				}
			}, _prefs);
		} catch (ReportException e) {
			DefaultXMLNoteErrorHandler.exception(e);
			_canceled=true;
		}
	}
	
	boolean validCoord(int x,int y,int w,int h) {
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension s=kit.getScreenSize();
		if (x<0 || y<0) { return false; }
		if (((x+w)>s.width) || ((y+h)>s.height)) {
			return false;
		}
		return true;
	}
	
	private void doPrint() {
		try {
			File output=File.createTempFile("help", "pdf");
			createPdf(output);
			if (_canceled) { return; }
			Window parent=SwingUtilities.getWindowAncestor(this);
			int x=_prefs.getInt("pdfView_x", -1);
			int y=_prefs.getInt("pdfView_y", -1);
			int w=_prefs.getInt("pdfView_w", -1);
			int h=_prefs.getInt("pdfView_h", -1);
			PdfViewer viewer=PdfViewer.createPdfViewer(parent,this,_tr._("Print preview"),output,_prefs);
			if (validCoord(x,y,w,h)) {
				viewer.setLocation(x, y);
				viewer.setPreferredSize(new Dimension(w,h));
			}
			viewer.setVisible(true);
			x=viewer.getLocation().x;
			y=viewer.getLocation().y;
			w=viewer.getSize().width;
			h=viewer.getSize().height;
			if (validCoord(x,y,w,h)) {
				_prefs.put("pdfView_x", x);
				_prefs.put("pdfView_y", y);
				_prefs.put("pdfView_w", w);
				_prefs.put("pdfView_h", h);
			}
			viewer.closePdf();
			viewer.dispose();
			output.delete();
		} catch (IOException e) {
			DefaultXMLNoteErrorHandler.exception(e);
		}
	}
	
	public JXMLNoteToolBar toolbar() {
		return _toolbar;
	}

	private void createData() {
		_topics = new DefaultMutableTreeNode(new HelpTopic("Help Topics", ""));
		_treeModel = new DefaultTreeModel(_topics);
		loadHelp();
	}

	
	
	public void valueChanged(TreeSelectionEvent e) {
		TreePath path = e.getPath();
		DefaultMutableTreeNode topic = (DefaultMutableTreeNode) path.getLastPathComponent();
		HelpTopic previous=_current;
		HelpTopic ht = (HelpTopic) topic.getUserObject();
		loadTopic(ht);
		if (!_inSession && previous!=null) {
			_futureTopics.clear();
			_pastTopics.push(previous);
		}
	}

	private XMLNoteImageIcon _nullImage = null;

	private XMLNoteImageIcon nullImage() {
		if (_nullImage == null) {
			_nullImage = new XMLNoteImageIcon(new JXMLNoteIcon("NoImage", 50,
					50));
			_nullImage.setId("__null__");
		}
		return _nullImage;
	}

	public XMLNoteImageIcon getIcon(String id) {
		XMLNoteImageIcon img = loadImage(id);
		if (img == null) {
			return nullImage();
		} else {
			return img;
		}
	}

	// This cannot occur in help files
	public XMLNoteImageIcon getIcon(URL url, String description,XMLNoteImageIconSize sz) {
		return nullImage();
	}
	
	WeakHashMap<String,XMLNoteImageIcon> cache=new WeakHashMap<String,XMLNoteImageIcon>();

	private XMLNoteImageIcon loadImage(String id) {
		XMLNoteImageIcon cicn=cache.get(id);
		if (cicn!=null) {
			return cicn;
		} else {
			ZipEntry ize = _helpjar.getEntry(id + ".img");
			try {
				final InputStream in = _helpjar.getInputStream(ize);
				final HelpImage img = new HelpImage(); // (HelpImage) dec.readObject();
				final Semaphore sem=new Semaphore(0);
				final Dimension imageDim=new Dimension();
				XMLNoteImageIconSize sz=img.read(in,imageDim,new ImageLoadedListener() {
					public void loaded() {
						try {
							in.close();
						} catch (Exception E) {
							DefaultXMLNoteErrorHandler.exception(E);
						}
						sem.release();
					}
				});
				final XMLNoteImageIcon icn = new XMLNoteImageIcon(id,sz,imageDim,"");
				Runnable R=new Runnable() {
					public void run() {
						try {
							sem.acquire();
						} catch (InterruptedException e) {
							// unexpected,
							// however, do nothing
						}
						icn.initialize(
								img.getImageIcon(), 
								new LoadedListener() {
									public void loaded() {
										icn.setDescription(img.getDescription());
										SwingUtilities.invokeLater(new Runnable() {
											public void run() {
												//System.out.println("firechanged");
												//_document.fireChanged();
												JHelpViewer.this.repaint();
											}
										});
									}
								}
						);
					}
				};
				Thread thr=new Thread(R);
				thr.start();
				cache.put(id, icn);
				return icn;
			} catch (Exception E) {
				DefaultXMLNoteErrorHandler.exception(E);
				return null;
			}
		}
	}

	/**
	 * External interface to the help viewer, to programmatically set the help
	 * topic.
	 * 
	 * @param topicKey
	 */
	public void setTopic(String topicKey) {
		Enumeration<DefaultMutableTreeNode> en = _topics.breadthFirstEnumeration();
		while (en.hasMoreElements()) {
			DefaultMutableTreeNode nd = en.nextElement();
			HelpTopic tp = (HelpTopic) nd.getUserObject();
			if (tp.getKey().equals(topicKey)) {
				if (_current != null) {
					_futureTopics.clear();
					_pastTopics.push(_current);
				}
				_inSession=true;
				TreePath path = new TreePath(nd.getPath());
				_tree.setSelectionPath(path);
				_inSession=false;
				return;
			}
		}
	}

	private void setTopicToTopicId(String topicId, boolean notFromNavigateButtons) {
		Enumeration<DefaultMutableTreeNode> en = _topics.breadthFirstEnumeration();
		while (en.hasMoreElements()) {
			DefaultMutableTreeNode nd = en.nextElement();
			HelpTopic tp = (HelpTopic) nd.getUserObject();
			if (tp.getTopicId().equals(topicId)) {
				if (_current != null) {
					if (notFromNavigateButtons) {
						_futureTopics.clear();
						_pastTopics.push(_current);
					}
				}
				_inSession=true;
				TreePath path = new TreePath(nd.getPath());
				_tree.setSelectionPath(path);
				_inSession=false;
				return;
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("previous")) {
			if (!_pastTopics.isEmpty()) {
				HelpTopic c=_current;
				HelpTopic tp = _pastTopics.pop();
				if (c!=null) { _futureTopics.push(c); }
				setTopicToTopicId(tp.getTopicId(), false);
			} 
		} else if (cmd.equals("next")) {
			if (!_futureTopics.isEmpty()) {
				HelpTopic c=_current;
				HelpTopic tp = _futureTopics.pop();
				if (c!=null) { _pastTopics.push(c); }
				setTopicToTopicId(tp.getTopicId(), false);
			} 
		} else if (cmd.equals("print")) {
			doPrint();
		}
	}

	public void markClicked(XMLNoteMark m, MouseEvent e) {
		e.consume();
		String cl=m.markClass();
		if (cl.startsWith("TopicLink:")) {
			String topicId = cl.replaceFirst("TopicLink[:]", "");
			setTopicToTopicId(topicId, true);
		} else if (cl.startsWith("HyperLink:")) {
			String link = cl.replaceFirst("HyperLink[:]", "");
			try {
				URI uri=new URI(link);
				Desktop.getDesktop().browse(uri);
			} catch (Exception E) {
				JOptionPane.showMessageDialog(this,_tr._("Cannot show the requested hyperlink"), _tr._("Hyperlink (WWW) choosen"),JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public void markDoubleClicked(XMLNoteMark m, MouseEvent e) {
		e.consume();
	}

	public Cursor mouseMovedIntoMark(XMLNoteMark m, MouseEvent e) {
		return Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	}

	public void mouseMovedOutOfMark(XMLNoteMark m, MouseEvent e) {
	}

	private void loadTopic(HelpTopic ht) {
		_current = ht;
		JarFile hj = _helpjar;
		ZipEntry hze = hj.getEntry(ht.getTopicId() + ".top");
		try {
			InputStream in = hj.getInputStream(hze);
			ObjectInputStream dec = new ObjectInputStream(in);
			HelpEntry he = (HelpEntry) dec.readObject();
			in.close();
			_document.resetFromXML(he.getXmlnote());
		} catch (Exception E) {
			DefaultXMLNoteErrorHandler.exception(E);
		}
	}

	private void loadHelp() {
		_topics = new DefaultMutableTreeNode(new HelpTopic("Help Topics", ""));
		
		_pastTopics.clear();
		_futureTopics.clear();

		try {
			JarFile hj = _helpjar;
			ZipEntry ze = hj.getEntry("HelpTopics.xnhlp");
			if (ze != null) {
				InputStream in = hj.getInputStream(ze);
				ObjectInputStream dec = new ObjectInputStream(in);
				_topics = (DefaultMutableTreeNode) dec.readObject();
				in.close();
			}
		} catch (Exception e) {
			DefaultXMLNoteErrorHandler.exception(e);
		}

		_treeModel.setRoot(_topics);
		if (_tree != null && _tree.getRowCount() > 1) {
			_tree.setSelectionRow(1);
		}

	}

	public void loadOther(JarFile helpfile) {
		_helpjar = helpfile;
		loadHelp();
	}

	public JHelpViewer(JarFile helpfile, XMLNotePreferences prefs)
			throws BadStyleException, IOException {
		_helpjar = helpfile;
		_prefs = prefs;
		_pastTopics = new Stack<HelpTopic>();
		_futureTopics = new Stack<HelpTopic>();
		_tr = new DefaultXMLNoteTranslator();
		_current = null;
		createData();
		createGUI();
		if (_tree.getRowCount() > 0) {
			_tree.setSelectionRow(0);
		}
	}

}
