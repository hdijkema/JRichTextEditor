/* ******************************************************************************
 *
 *       Copyright 2008-2010 Hans Oesterholt-Dijkema
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

package net.oesterholt.jxmlnote.report.viewers;


/*
 * $Id: Jzc3ImagePanel.java,v 1.13 2009/04/15 21:22:45 cvs Exp $
 * 
 * This source code is copyright of Hans Oesterholt-Dijkema.
 * © 2008-2009 Hans Oesterholt-Dijkema. All rights reserved.
 * 
 * This source code is property of it's author: Hans Oesterholt-Dijkema.
 * Nothing of this code may be copied, (re)used or multiplied without
 * permission of the author. 
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.oesterholt.jxmlnote.utils.DPIAdjuster;

import org.jdesktop.swingworker.SwingWorker;


import com.sun.pdfview.PDFPage;


public class PdfPagePanel extends JScrollPane {
	
	private static final long serialVersionUID = 1L;
	
	public static final int PAGE_FIT_WIDTH=1;
	public static final int PAGE_FIT_HEIGHT=2;
	public static final int PAGE_FIT_NONE=3;

	private Image 				_img;
	private JImagePanel 		_image;
	private Set<ActionListener> _listeners;
	
	private int	 				_fit=PAGE_FIT_NONE;
	private double				_zoom=1.0;
	
	private PageFormat			_pageFormat;
	private int					_imageWidth;
	private int					_imageHeight;
	private PDFPage				_page;
	private ZoomListener		_zoomListener;
	
	
	public interface ZoomListener {
		public void zoomed(double z);
	}

	private Color panelColorBg() {
		return Color.gray;
	}

	class JImagePanel extends JPanel {
		public void paint(Graphics _g) {
			Graphics2D g=(Graphics2D) _g;
			g.setColor(panelColorBg());
			Dimension d=PdfPagePanel.this.getViewport().getExtentSize();
			g.fillRect(0, 0, d.width,d.height);
			if (_img!=null) {
				g.drawImage(_img,0,0,_img.getWidth(this),_img.getHeight(this),this);
			}
		}

		public JImagePanel() {
		}
	}

	public interface ImageProvider {
		public BufferedImage image() throws Exception;
		public void done();
	}


	public void addActionListener(ActionListener a) {
		_listeners.add(a);
	}

	public void removeActionListener(ActionListener a) {
		_listeners.remove(a);
	}
	
	public void showPage(PDFPage page) {
		Dimension d=new Dimension((int) _pageFormat.getWidth(),(int) _pageFormat.getHeight());
		Dimension dScreen=DPIAdjuster.getDimensionForScreenDPI(d);
		int width=dScreen.width;
		int height=dScreen.height;
		_imageWidth=width;
		_imageHeight=height;
		_page=page;
		doIt();
	}
	
	public PDFPage getPage() {
		return _page;
	}
	
	public int getImageWidth() {
		return _imageWidth;
	}
	
	public int getImageHeight() {
		return _imageHeight;
	}
	

	private void doIt() {
		class worker extends SwingWorker<String,String> {
			private Dimension _panelSize;
			private Image     _ww=null;
			
			public String doInBackground() {
				if (_page!=null) {
					//Dimension sz=_page.getUnstretchedSize((int) _pageFormat.getWidth (),
					//		(int) _pageFormat.getHeight (),_page.getBBox ());
					
					//Rectangle wholePage=new Rectangle(0,0,(int) _page.getBBox().getWidth(),(int) _page.getBBox().getHeight());
					Rectangle wholePage;
					if (_page.getRotation()==90) {
						wholePage=new Rectangle(0,0,(int) _page.getHeight(),(int) _page.getWidth());
					} else {
						wholePage=new Rectangle(0,0,(int) _page.getWidth(),(int) _page.getHeight());
					}
					//wholePage=new Rectangle(0,0,(int) _page.getHeight(),(int) _page.getWidth());
					//Fixed: HIER MOETEN WE IETS MEE (MET ROTATIE, LANDSCAPE, ETC.).
					if (_fit==PAGE_FIT_NONE) {
						int w,h;
						if (_page.getRotation()==90) {
							w=(int) Math.round(getImageHeight()*_zoom);
							h=(int) Math.round(getImageWidth()*_zoom);
						} else {
							w=(int) Math.round(getImageWidth()*_zoom);
							h=(int) Math.round(getImageHeight()*_zoom);
						}
						_img=_page.getImage(w, h, wholePage, null, true, true);
						_ww=_img;
					} else if (_fit==PAGE_FIT_WIDTH) {
						int w=_panelSize.width;
						int h=(int) (w/_page.getAspectRatio());
						_zoom=((double) w)/wholePage.getWidth()/DPIAdjuster.pointsToScreenFactor();
						_img=_page.getImage(w, h, wholePage, null,true,true);
						_ww=_img;
					} else {
						int h=_panelSize.height;
						int w=(int) (h*_page.getAspectRatio());
						_zoom=((double) h)/wholePage.getHeight()/DPIAdjuster.pointsToScreenFactor();;
						_img=_page.getImage(w, h, wholePage, null,true,true);
						_ww=_img;
					}
				}
				return "done";
			}

			protected void done() {
				if (_ww!=null) {
					Dimension d=new Dimension(_ww.getWidth(null),_ww.getHeight(null));
					_image.setSize(d);
					_image.setPreferredSize(d);
					PdfPagePanel.this.setViewportView(_image);
					if (_zoomListener!=null) {
						_zoomListener.zoomed(_zoom);
					}
				}
			}

			public worker() {
				_panelSize=PdfPagePanel.this.getViewport().getExtentSize();
			}
		};

		worker w=new worker();
		w.execute();
	}

	public void zoomFitWidth(ZoomListener l) {
		_fit=PAGE_FIT_WIDTH;
		_zoomListener=l;
		doIt();
	}
	
	public void zoomFitHeight(ZoomListener l) {
		_fit=PAGE_FIT_HEIGHT;
		_zoomListener=l;
		doIt();
	}
	
	public void zoomFitNone(double zoom) {
		_fit=PAGE_FIT_NONE;
		_zoom=zoom;
		_zoomListener=null;
		doIt();
	}
	
	public void setScrollUnitIncrement(int points) {
		super.getVerticalScrollBar().setUnitIncrement(points);
	}
	
	public int getScrollUnitIncrement() {
		return super.getVerticalScrollBar().getUnitIncrement();
	}


	public PdfPagePanel(PageFormat f) {
		super(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		_pageFormat=f;
		_img=null;
		_image=new JImagePanel();
		super.setViewportView(_image);
		_listeners=new HashSet<ActionListener>();
		
		setScrollUnitIncrement(16);

		this.addComponentListener(new ComponentListener() {  
						public void componentResized(ComponentEvent evt) {
							doIt();
						}
						public void componentHidden(ComponentEvent arg0) { }
						public void componentMoved(ComponentEvent arg0)  { }
						public void componentShown(ComponentEvent arg0)  { }
		});
		
	}
}
