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

package net.oesterholt.jxmlnote.document;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;

import net.oesterholt.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.oesterholt.jxmlnote.utils.DPIAdjuster;


public class XMLNoteImageIconSize implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int TYPE_PX=1;
	public static final int TYPE_PT=2;
	public static final int TYPE_PERCENTAGE=3;
	
	public String _device=DPIAdjuster.DEVICE_SCREEN;
	
	public Dimension get_size() {
		return _size;
	}

	public void set_size(Dimension _size) {
		this._size = _size;
	}

	public int get_type() {
		return _type;
	}

	public void set_type(int _type) {
		this._type = _type;
	}

	private Dimension _size;
	private int       _type;
	
	
	
	public int type() {
		return _type;
	}
	
	public boolean fullSize() {
		return _size.height>0 && _size.width>0;
	}
	
	public boolean noSize() {
		return _size.width<0 && _size.height<0;
	}
	
	public Dimension getSizeInPx(int icn_w,int icn_h) {
		int h=height();
		int w=width();
		
		if (w<=0 && h<=0) {
			return new Dimension(icn_w,icn_h);		// do anything if we can't determine anything.
		} else if (w<=0) {
			double hfactor=((double) icn_h)/((double) h);
			w=(int) Math.round(icn_w/hfactor);
		} else if (h<=0) {
			double wfactor=((double) icn_w)/((double) w);
			h=(int) Math.round(icn_h/wfactor);
		}
		
		// convert if TYPE_PT
		
		if (type()==TYPE_PT) {
			if (_device.equals(DPIAdjuster.DEVICE_SCREEN)) {
				return DPIAdjuster.getDimensionForScreenDPI(new Dimension(w,h));
			} else {
				return new Dimension(w,h);
			}
		} else if (type()==TYPE_PERCENTAGE) {
			double hp=((double) h)/100.0;
			double wp=((double) w)/100.0;
			return new Dimension((int) Math.round(wp*icn_w),(int) Math.round(hp*icn_h));
		}	else {
			return new Dimension(w,h);
		}
	}
	
	// TODO: Is dit goed gegaan?
	public Dimension getSizeInPt(int w_in_px,int h_in_px) {
		int h=height();
		int w=width();
		
		if (w<=0 && h<=0) {
			return new Dimension(w_in_px,h_in_px);	// do anything if we can't determine anything.
		} else if (w<=0) {
			double hfactor=((double) h_in_px)/((double) h);
			w=(int) Math.round(w_in_px/hfactor);
		} else if (h<=0) {
			double wfactor=((double) w_in_px)/((double) w);
			h=(int) Math.round(h_in_px/wfactor);
		}
		
		// convert if TYPE_PX
		
		if (type()==TYPE_PX) {
			if (_device.equals(DPIAdjuster.DEVICE_SCREEN)) {
				return DPIAdjuster.getDimensionInPtForScreenDPI(new Dimension(w,h));
			} else {
				return new Dimension(w,h);
			}
		} else if (type()==TYPE_PERCENTAGE) {
			double hp=((double) h)/100.0;
			double wp=((double) w)/100.0;
			return new Dimension((int) Math.round(wp*w_in_px),(int) Math.round(hp*w_in_px));
		}	else {
			return new Dimension(w,h);
		}
	}
	
	public Dimension getSizeInPx(ImageIcon icn) {
		int icn_w=icn.getIconWidth();
		int icn_h=icn.getIconHeight();
		return getSizeInPx(icn_w,icn_h);
	}
	
	public Dimension getSizeInPx(Image img) {
		Observe ob=new Observe();
		Dimension imgSize=ob.getImageSize(img);
		return getSizeInPx(imgSize.width,imgSize.height);
	}

	public Dimension getSizeInPx(Dimension origSizeInPx) {
		return getSizeInPx(origSizeInPx.width,origSizeInPx.height);
	}
	
	public Dimension getSizeInPt(ImageIcon icn) {
		return getSizeInPt(icn.getIconWidth(),icn.getIconHeight());
	}
	
	
	
	private class Observe implements ImageObserver {
		
		private int 			_width=-1;
		private int 			_height=-1;
		private Semaphore 		_sem;
		private int				_flags;
		
		public boolean imageUpdate(Image img, int infoflags, int x, int y,int width, int height) {
			if ((infoflags&_flags)!=0) {
				if (_flags==ImageObserver.WIDTH) { _width=width; }
				else if (_flags==ImageObserver.HEIGHT) { _height=height; } 
				_sem.release();
				return false;
			} else {
				return true;
			}
		}
		
		public Dimension getImageSize(Image img) {
			Dimension size=new Dimension(-1,-1);
			try {
				_width=-1;
				_sem=new Semaphore(0);
				_flags=ImageObserver.WIDTH;
				_width=img.getWidth(this);
				if (_width==-1) {
					_sem.acquire();
				}
				_height=-1;
				_sem=new Semaphore(0);
				_flags=ImageObserver.HEIGHT;
				_height=img.getHeight(this);
				if (_height==-1) { 
					_sem.acquire();
				}
				return new Dimension(_width,_height);
			} catch (InterruptedException e) {
				DefaultXMLNoteErrorHandler.exception(e);
			}
			return size;
		}
	}
	
	
	public int width() {
		return _size.width;
	}
	
	public int height() {
		return _size.height;
	}
	
	public Dimension size() {
		return _size;
	}
	
	public void setDevice(String device) {
		_device=device;
	}
	
	public XMLNoteImageIconSize copyForDevice(String dev) {
		return new XMLNoteImageIconSize(dev,_size.width,_size.height,_type);
	}
	
	public XMLNoteImageIconSize copyForZoom(double d) {
		int w=(_size.width==-1) ? -1 : (int) Math.round(_size.width*d);
		int h=(_size.height==-1) ? -1 : (int) Math.round(_size.height*d);
		return new XMLNoteImageIconSize(_device,w,h,_type);
	}

	
	public XMLNoteImageIconSize(String device,int width,int height,int type) {
		_device=device;
		_type=type;
		_size=new Dimension(width,height);
	}

	public XMLNoteImageIconSize(int width,int height,int type) {
		this(DPIAdjuster.DEVICE_SCREEN,width,height,type);
	}
	
	public XMLNoteImageIconSize(int width,int type) {
		this(width,-1,type);
	}

}
