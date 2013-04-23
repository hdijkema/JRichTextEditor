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

package net.oesterholt.jxmlnote.report.viewers;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import net.oesterholt.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.oesterholt.jxmlnote.interfaces.XMLNotePreferences;
import net.oesterholt.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import net.oesterholt.jxmlnote.internationalization.XMLNoteTranslator;
import net.oesterholt.jxmlnote.report.viewers.PdfPagePanel.ZoomListener;
import net.oesterholt.jxmlnote.toolbar.JXMLNoteIcon;
import net.oesterholt.jxmlnote.toolbar.JXMLNoteToolBar;
import net.oesterholt.jxmlnote.widgets.JPrintPreferences;
import net.oesterholt.jxmlnote.widgets.JRecentlyUsedMenu;
import net.oesterholt.jxmlnote.widgets.JXMLNoteSwingUtils;


import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;

/**
 * With this class it is possible to view- and print a PDF File. This can be used to view the output
 * of XMLNoteToPdf. This class   
 * 
 * @author Hans Oesterholt-Dijkema
 *
 */
public class PdfViewer extends JDialog implements Printable, ActionListener {
	
	private static final long serialVersionUID = 1L;
	private XMLNoteTranslator	_tr;
	private JXMLNoteToolBar 	_toolbar;
	private JMenuBar			_bar;
	private RandomAccessFile 	_raf;
	private FileChannel			_channel;
	private PDFFile 			_pdffile;
	private PdfPagePanel		_pdfpanel;
	private int					_pageNr;
	private JLabel				_pagesLabel;
	private JLabel				_pageNrLabel;
	private JLabel				_zoomLabel;
	private PageFormat 			_pageFormat;
	private double				_zoom=1.0;
	private int					_mode;
	private XMLNotePreferences 	_preferences;
	private File				_fileToView;
	private File				_tmpFileToView;
	private boolean				_closed;
	
	private static int			_scrollUnitIncrement=16;

	
	private class StatusBar extends JPanel {
		private static final long serialVersionUID = 1L;

		public StatusBar() {
			super.setLayout(new FlowLayout(FlowLayout.RIGHT));
			super.setBorder(BorderFactory.createEtchedBorder());
		}
	}
	
	private void setZoom(double z) {
		_zoom=z;
		_zoomLabel.setText(String.format("%.1f%%", _zoom*100.0));
	}
	
	private double getZoom() {
		return _zoom;
	}
	
	public void actionPerformed(ActionEvent e) {
		String c=e.getActionCommand();
		if (c.equals(JXMLNoteToolBar.ACTION_NEXT)) {
			_pageNr+=1;
			if (_pageNr>_pdffile.getNumPages()) {
				_pageNr-=1;
			} 
		} else if (c.equals(JXMLNoteToolBar.ACTION_PREVIOUS)) {
			_pageNr-=1;
			if (_pageNr<1) { 
				_pageNr+=1; 
			} 
		} else if (c.equals(JXMLNoteToolBar.ACTION_FIRST)) {
			_pageNr=1;
		} else if (c.equals(JXMLNoteToolBar.ACTION_LAST)) {
			_pageNr=_pdffile.getNumPages();
		} else if (c.equals(JXMLNoteToolBar.ACTION_PRINT)) {
			doPrint();
		} else if (c.equals(JXMLNoteToolBar.ACTION_PRINT_PREFS)) {
			pageSettings();
		} else if (c.equals(JXMLNoteToolBar.ACTION_ZOOM_FIT_WIDTH)) {
			_pdfpanel.zoomFitWidth(new ZoomListener() { public void zoomed(double z) { setZoom(z); } });
			_mode=PdfPagePanel.PAGE_FIT_WIDTH;
		} else if (c.equals(JXMLNoteToolBar.ACTION_ZOOM_FIT_HEIGHT)) {
			_pdfpanel.zoomFitHeight(new ZoomListener() { public void zoomed(double z) { setZoom(z); } });
			_mode=PdfPagePanel.PAGE_FIT_HEIGHT;
		} else if (c.equals(JXMLNoteToolBar.ACTION_ZOOM_100)) {
			setZoom(1.0);
			_pdfpanel.zoomFitNone(getZoom());
			_mode=PdfPagePanel.PAGE_FIT_NONE;
		} else if (c.equals(JXMLNoteToolBar.ACTION_ZOOM_MORE)) {  
			setZoom(getZoom()*1.1);
			_pdfpanel.zoomFitNone(getZoom());
			_mode=PdfPagePanel.PAGE_FIT_NONE;
		} else if (c.equals(JXMLNoteToolBar.ACTION_ZOOM_LESS)) {  
			setZoom(getZoom()/1.1);
			_pdfpanel.zoomFitNone(getZoom());
			_mode=PdfPagePanel.PAGE_FIT_NONE;
		} else if (c.equals(JXMLNoteToolBar.ACTION_CLOSE)) {
			storePrefs();
			try {
				closePdf();
			} catch (IOException e1) {
				DefaultXMLNoteErrorHandler.exception(e1);
			}
			super.setVisible(false);
		} else if (c.equals(JXMLNoteToolBar.ACTION_SAVE)) {
			save();
		}
		
		if (_pdfpanel.getPage().getPageNumber()!=(_pageNr)) {
			_pdfpanel.showPage(_pdffile.getPage(_pageNr));
		}
		_pageNrLabel.setText(Integer.toString(_pageNr));
		_pagesLabel.setText(Integer.toString(_pdffile.getNumPages()));

	}

	private void storePrefs() {
		Point p=this.getLocation();
		Dimension s=this.getSize();
		_preferences.put("pdfviewer.x", p.x);
		_preferences.put("pdfviewer.y", p.y);
		_preferences.put("pdfviewer.w", s.width);
		_preferences.put("pdfviewer.h", s.height);
		_preferences.put("pdfviewer.mode", _mode);
		_preferences.put("pdfviewer.zoom", (int) (getZoom()*10000.0));
	}
	
	private void applyPreferences() {
		Point p=new Point(_preferences.getInt("pdfviewer.x", -1),_preferences.getInt("pdfviewer.y", -1));
		Dimension s=new Dimension(_preferences.getInt("pdfviewer.w", 500),_preferences.getInt("pdfviewer.h", 700));
		if (p.x<0 || p.y<0) {
			// do nothing
		} else {
			this.setLocation(p);
		}
		this.setPreferredSize(s);
		_mode=_preferences.getInt("pdfviewer.mode", PdfPagePanel.PAGE_FIT_NONE);
		_zoom=(double) (_preferences.getInt("pdfviewer.zoom", 10000)/10000.0);
		if (_zoom<0.2) { _zoom=0.2; }
		if (_mode==PdfPagePanel.PAGE_FIT_WIDTH) {
			_pdfpanel.zoomFitWidth(new ZoomListener() { public void zoomed(double z) { setZoom(z); } });
		} else if (_mode==PdfPagePanel.PAGE_FIT_HEIGHT) {
			_pdfpanel.zoomFitHeight(new ZoomListener() { public void zoomed(double z) { setZoom(z); } });
		} else if (_mode==PdfPagePanel.PAGE_FIT_NONE) {
			_pdfpanel.zoomFitNone(_zoom);
		}
	}
	
	public JXMLNoteToolBar toolbar() {
		return _toolbar;
	}
	
	public void save() {
		class MyFileChooser extends JFileChooser {
			private static final long serialVersionUID = 1L;
			
			public MyFileChooser(String dir) {
				super(dir);
			}
			
			protected JDialog createDialog(Component parent) throws HeadlessException {
				JDialog dlg=super.createDialog(parent);
				if (parent instanceof Window) {
					JXMLNoteSwingUtils.centerOnParent((Window) parent, dlg);
				} else {
					JXMLNoteSwingUtils.centerOnParent(SwingUtilities.getWindowAncestor(parent), dlg);
				}
				return dlg;
			}
		}
		
		String prevDir=_preferences.getString("previousSaveDir", System.getProperty("user.dir"));
		
		JFileChooser dlg=new MyFileChooser(prevDir);
		dlg.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".pdf");
			}
			public String getDescription() {
				return _tr._(".pdf - PDF files");
			}
		});

		int result=dlg.showSaveDialog(this);
		if (result==JFileChooser.APPROVE_OPTION) {
			File copyTo=dlg.getSelectedFile();
			if (!copyTo.getName().toLowerCase().endsWith(".pdf")) {
				String f=copyTo.getAbsolutePath()+".pdf";
				copyTo=new File(f);
			}
			try {
				byte[] buf=new byte[8192];
				//InputStream in=new FileInputStream(_fileToView);
				long seek=_raf.getFilePointer();		// we already have the pdf open
				OutputStream out=new FileOutputStream(copyTo);
				int len;
				_raf.seek(0);
				while ((len=_raf.read(buf))>0) {
					out.write(buf,0, len);
				}
				out.close();
				_raf.seek(seek);
				String parent=copyTo.getParent();
				if (parent!=null) { _preferences.put("previousSaveDir",parent); }
			} catch (Exception e) {
				DefaultXMLNoteErrorHandler.exception(e);
			}
		}
	}
	
	public int print (Graphics g, PageFormat format, int index) throws PrinterException {
		int pagenum = index+1;
		if (pagenum < 1 || pagenum > _pdffile.getNumPages ()) {
			return NO_SUCH_PAGE;
		}

		Graphics2D g2d = (Graphics2D) g;
		AffineTransform at = g2d.getTransform ();

		PDFPage pdfPage = _pdffile.getPage (pagenum);

		Dimension dim;
		dim = pdfPage.getUnstretchedSize ((int) format.getImageableWidth (),
				(int) format.getImageableHeight (),
				pdfPage.getBBox ());

		Rectangle bounds = new Rectangle ((int) format.getImageableX (),
				(int) format.getImageableY (),
				dim.width,
				dim.height);

		PDFRenderer rend = new PDFRenderer (pdfPage, (Graphics2D) g, bounds,null, null);
		try {
			pdfPage.waitForFinish ();
			rend.run ();
		} catch (InterruptedException ie) {
			JOptionPane.showMessageDialog (this, ie.getMessage ());
		}

		/*g2d.setTransform (at);
		g2d.draw (new Rectangle2D.Double (format.getImageableX (),
				format.getImageableY (),
				format.getImageableWidth (),
				format.getImageableHeight ()));*/

		return PAGE_EXISTS;
	}
	
	private void pageSettings() {
		JPrintPreferences prefs=new JPrintPreferences(this,_preferences);
		prefs.pageDialog();
	}
	
	
	private void doPrint() {
		// TODO: Hier iets met landscape/rotation doen.
		PrinterJob job = PrinterJob.getPrinterJob ();
		JPrintPreferences prefs=new JPrintPreferences(this,_preferences);
		HashPrintRequestAttributeSet set=new HashPrintRequestAttributeSet();
		prefs.fromPrefs(set);
        job.setPrintable (this);
        Date dt=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yy-mm-dd HH:MM:SS");
        job.setJobName(format.format(dt)+" - "+_fileToView.getName());
        try
        {
        	/*PrintService service=job.getPrintService();
        	DocFlavor flavor=DocFlavor.SERVICE_FORMATTED.PAGEABLE;
        	final GraphicsConfiguration gc =GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        	JDialog dlg=new ServiceDialog(gc,10,10,service,flavor,set,this);
        	dlg.setModal(true);
        	dlg.setVisible(true);*/
            if (job.printDialog (set)) {
            	prefs.toPrefs(set);
                job.print(set);
            }
        }
        catch (PrinterException pe) {
            JOptionPane.showMessageDialog (this, pe.getMessage ());
        }
	}
	
	
	static public void setScrollUnitIncrement(int pixels) {
		_scrollUnitIncrement=pixels;
	}

	static public int getScrollUnitIncrement() {
		return _scrollUnitIncrement;
	}
	
	static public void showPdfViewer(JComponent parent,String title,File pdfFile,XMLNotePreferences prefs) throws IOException {
		showPdfViewer(SwingUtilities.getWindowAncestor(parent),parent,title,pdfFile,prefs);
	}
	
	static public void showPdfViewer(Window parent,String title,File pdfFile,XMLNotePreferences prefs) throws IOException {
		showPdfViewer(parent,null,title,pdfFile,prefs);
	}
	
	public void closePdf() throws IOException {
		if (!_closed) {
			this._channel.close();
			this._raf.close();
			_closed=true;
		}
	}

	static public void showPdfViewer(Window parent,Component relativeTo,String title,File pdfFile,XMLNotePreferences prefs) throws IOException {
		PdfViewer viewer=createPdfViewer(parent,relativeTo,title,pdfFile,prefs);
		viewer.setVisible(true);
		viewer.dispose();
	}
	
	public static PdfViewer createPdfViewer(Window parent,Component relativeTo,String title,File pdfFile,XMLNotePreferences prefs) throws IOException {
		final PdfViewer viewer=new PdfViewer(parent,title,true,pdfFile,prefs);
		
		viewer.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setScrollUnitIncrement(getScrollUnitIncrement());
		
		if (relativeTo!=null) {
			viewer.setLocationRelativeTo(relativeTo);
		}

		viewer.pack();
		
		// Sizes are known now.
		PDFPage page = viewer._pdffile.getPage(0);
		viewer._pdfpanel.showPage(page);
		return viewer;
	}
	
	
	private void copyFile(File from,File to) throws IOException {
	    InputStream in = new FileInputStream(from);
	    OutputStream out = new FileOutputStream(to);

	    // Transfer bytes from in to out
	    byte[] buf = new byte[8192];
	    int len;
	    while ((len = in.read(buf)) > 0) {
	        out.write(buf, 0, len);
	    }
	    in.close();
	    out.close();
	}
	
	protected PdfViewer(Window frame,String title,boolean modal,File fileToView,XMLNotePreferences prefs) throws IOException {
		super(frame,title);
		super.setModal(true);

		_fileToView=fileToView;
		_tmpFileToView=File.createTempFile("HOD",".pdf");
		_tmpFileToView.deleteOnExit();
		
		copyFile(_fileToView,_tmpFileToView);
		
		//int format=PageFormat.PORTRAIT;
		//format=PageFormat.LANDSCAPE;
		//PrinterJob.getPrinterJob().defaultPage().setOrientation(PageFormat.LANDSCAPE);
		_pageFormat = PrinterJob.getPrinterJob().defaultPage();
		//_pageFormat.setOrientation(format);
		//_pageFormat.setOrientation(PageFormat.LANDSCAPE);
		
		_tr=new DefaultXMLNoteTranslator();
		_toolbar=new JXMLNoteToolBar(this,null);
		_toolbar.setButtonSize(24);
		_toolbar.removeAllSections();
		_toolbar.addSection("file",_tr._("Save PDF document"));
		_toolbar.add("file",JXMLNoteToolBar.ACTION_SAVE,_tr._("save PDF document"),this);
		_toolbar.addSection("navigating", _tr._("Navigate the PDF document"));
		_toolbar.add("navigating", JXMLNoteToolBar.ACTION_FIRST, _tr._("First page"), this);
		_toolbar.add("navigating", JXMLNoteToolBar.ACTION_PREVIOUS, _tr._("Previous page"), this);
		_toolbar.add("navigating", JXMLNoteToolBar.ACTION_NEXT, _tr._("Next page"), this);
		_toolbar.add("navigating", JXMLNoteToolBar.ACTION_LAST, _tr._("Last page"), this);
		_toolbar.addSection("zooming", _tr._("Zooming"));
		_toolbar.add("zooming", JXMLNoteToolBar.ACTION_ZOOM_FIT_WIDTH, _tr._("Fit Width"), this);
		_toolbar.add("zooming", JXMLNoteToolBar.ACTION_ZOOM_FIT_HEIGHT, _tr._("Fit Height"), this);
		_toolbar.add("zooming", JXMLNoteToolBar.ACTION_ZOOM_100, _tr._("100%"), this);
		_toolbar.add("zooming", JXMLNoteToolBar.ACTION_ZOOM_LESS, _tr._("10% less"), this);
		_toolbar.add("zooming", JXMLNoteToolBar.ACTION_ZOOM_MORE, _tr._("10% more"), this);
		_toolbar.addSection("printing", _tr._("Printing"));
		_toolbar.add("printing",JXMLNoteToolBar.ACTION_PRINT,_tr._("Print this document"),this);
		_toolbar.add("printing",JXMLNoteToolBar.ACTION_PRINT_PREFS,_tr._("Printer preferences"),this);
		_toolbar.initToolBar();
		
		_bar=new JMenuBar();
		JMenu window=JRecentlyUsedMenu.makeMenu(_tr._("_Window"));
		JXMLNoteIcon icn=new JXMLNoteIcon(JXMLNoteToolBar.ACTION_CLOSE);
		JMenuItem close=JRecentlyUsedMenu.makeMenuItem(_tr._("_Close"), icn, JXMLNoteToolBar.ACTION_CLOSE, this);
		_bar.add(window);
		window.add(close);
		
		JPanel mb=new JPanel(new BorderLayout());
		mb.add(_bar,BorderLayout.NORTH);
		
		JPanel view=new JPanel(new BorderLayout());
		view.add(_toolbar,BorderLayout.NORTH);
		
		_pdfpanel=new PdfPagePanel(_pageFormat);
		//_scrollpane=new JScrollPane(_pdfpanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		view.add(_pdfpanel,BorderLayout.CENTER);
		mb.add(view,BorderLayout.CENTER);
	
		StatusBar sbar=new StatusBar();
		_zoomLabel=new JLabel();
		_pageNrLabel=new JLabel();
		_pagesLabel=new JLabel();
		sbar.add(_zoomLabel);
		sbar.add(new JLabel("|"));
		sbar.add(_pageNrLabel);
		//sbar.add(new JSeparator(JSeparator.VERTICAL));
		sbar.add(new JLabel("/"));
		sbar.add(_pagesLabel);
		mb.add(sbar,BorderLayout.SOUTH);
		
		
		this.add(mb);
		
		_raf = new RandomAccessFile(_tmpFileToView, "r");
		_channel = _raf.getChannel();
		ByteBuffer buf = _channel.map(FileChannel.MapMode.READ_ONLY,0, _channel.size());
		_pdffile = new PDFFile(buf);
		_closed=false;
		
		_pageNr=1;
		_pageNrLabel.setText(Integer.toString(_pageNr));
		_pagesLabel.setText(Integer.toString(_pdffile.getNumPages()));
		
		_preferences=prefs;
		applyPreferences();
		
		this.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent arg0) { }
			public void windowClosed(WindowEvent arg0) { }
			public void windowDeactivated(WindowEvent arg0) { }
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowOpened(WindowEvent arg0) {}
			public void windowClosing(WindowEvent arg0) {
				storePrefs();
				try {
					closePdf();
				} catch (IOException e) {
					DefaultXMLNoteErrorHandler.exception(e);
				}
			}
		});
	}
}
