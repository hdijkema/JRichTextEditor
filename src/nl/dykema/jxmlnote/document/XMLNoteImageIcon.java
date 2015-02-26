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

package nl.dykema.jxmlnote.document;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import nl.dykema.jxmlnote.toolbar.JXMLNoteIcon;
import nl.dykema.jxmlnote.utils.BufferedImageBuilder;
import nl.dykema.jxmlnote.utils.BufferedImageBuilder.ScaleListener;
import nl.dykema.jxmlnote.widgets.JXMLNotePane;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLNoteImageIcon implements Icon { //, Serializable {
	
	public interface LoadedListener {
		public void loaded();
	};
	
	private static final long 				serialVersionUID = 1L;
	private String 							resource=null;
	private XMLNoteImageIconSize			size=null;	
	private String							id=null;
	private ImageIcon						_imageIcon=null;
	private Image							_original=null;
	private Icon							_otherIcon=null;
	private String							description=null;
	
	public enum Type { IMAGEICON, OTHER };
	
	public Type type() {
		return (_imageIcon==null) ? Type.OTHER : Type.IMAGEICON;
	}

	public ImageIcon get_imageIcon() {
		return _imageIcon;
	}

	public void set_imageIcon(ImageIcon _imageIcon) {
		this._imageIcon = _imageIcon;
	}
	
	public int getIconHeight() {
		if (_imageIcon==null) {
			return _otherIcon.getIconHeight();
		} else {
			return _imageIcon.getIconHeight();
		}
	}

	public int getIconWidth() {
		if (_imageIcon==null) {
			return _otherIcon.getIconWidth();
		} else {
			return _imageIcon.getIconWidth();
		}
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (_imageIcon==null) {
			_otherIcon.paintIcon(c,g,x,y);
		} else {
			_imageIcon.paintIcon(c,g,x,y);
		}
	}
	
	public ImageIcon getOriginal() {
		if (_original==null) {
			return null;
		} else {
			return new ImageIcon(_original);
		}
	}
	
	public boolean equalOrig(XMLNoteImageIcon icn) {
		return icn._original==_original;
	}
	
	public Image getImage() {
		if (_imageIcon!=null) {
			return _imageIcon.getImage();
		} else {
			Dimension sz=size.getSizeInPx(_otherIcon.getIconWidth(),_otherIcon.getIconHeight());
			BufferedImage img=new BufferedImage(sz.width,sz.height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = img.createGraphics();
			_otherIcon.paintIcon(null, g, size.width(),size.height());
			g.dispose();
			return img;
		}
	}
	
	public XMLNoteImageIcon getZoomedInstance(JXMLNotePane pn,LoadedListener l) {
		if (pn.getZoomFactor()==1.0) { return this; } 
		else { return this.copyForZoom(pn.getZoomFactor(),l); }
	}

	public XMLNoteImageIcon getZoomedInstance(JXMLNotePane pane) {
		return getZoomedInstance(pane,null);
	}
	
	
	/**
	 * With this interface it is possible to provide XMLNoteImageIcons on basis
	 * of ids. 
	 *  
	 * @author Hans Dijkema
	 */
	public interface Provider {
		/**
		 * Returns the XMLNoteImageIcon for the given id. The id will be part of the XMLNoteImageIcon 
		 * @param id	The id of the image to get.
		 * @return 	the associated XMLNoteImageIcon
		 */
		public XMLNoteImageIcon getIcon(String id);
		
		/**
		 * Returns the XMLNoteImageIcon for the given url, description and sizeInPt. This makes it
		 * possible to manipulate the given parameters or cash/store the image somehow.
		 * @param url			the url to retrieve
		 * @param description	the description
		 * @param size			the requested size in pt (1/72 inch) or px (screen pixels).
		 * @return	the associated XMLNoteImageIcon
		 */
		public XMLNoteImageIcon getIcon(URL url,String description,XMLNoteImageIconSize size);
		
	}
	
	public void setId(String id) {
		this.id=id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getResource() {
		return resource;
	}

	public void setSize(XMLNoteImageIconSize size) {
		this.size = size;
	}

	public XMLNoteImageIconSize getSize() {
		return size;
	}
	
	public Dimension getSizeInPx() {
		if (_imageIcon!=null) {
			return size.getSizeInPx(_imageIcon);
		} else {
			return size.getSizeInPx(_otherIcon.getIconWidth(),_otherIcon.getIconHeight());
		}
	}
	
	public Dimension getSizeInPt() {
		if (_imageIcon!=null) {
			return size.getSizeInPt(_imageIcon);
		} else {
			return size.getSizeInPt(_otherIcon.getIconWidth(),_otherIcon.getIconHeight());
		}
	}
	
	public Element toXML(Document xmlDOM) {
		Element el=xmlDOM.createElement("image");
		if (getId()!=null) {
			el.setAttribute("id",getId());
		}
		el.setAttribute("url",resource);
		el.setAttribute("description",this.getDescription());
		if (size.type()==XMLNoteImageIconSize.TYPE_PT) {
			el.setAttribute("width_in_pt",Integer.toString(size.width()));
			el.setAttribute("height_in_pt",Integer.toString(size.height()));
		} else {
			el.setAttribute("width_in_px",Integer.toString(size.width()));
			el.setAttribute("height_in_px",Integer.toString(size.height()));
		}
		return el;
	}
	
	public void setDescription(String s) {
		description=s;
	}
	
	public String getDescription() {
		return description;
	}

	public XMLNoteImageIcon copyForZoom(double d) {
		return copyForZoom(d,null);
	}
	
	public XMLNoteImageIcon copyForZoom(double d,LoadedListener l) {
		return new XMLNoteImageIcon(getId(),getOriginal(),getDescription(),getSize().copyForZoom(d),l);
	}

	public XMLNoteImageIcon copyForDevice(String device) {
		return new XMLNoteImageIcon(getId(),getOriginal(),getDescription(),getSize().copyForDevice(device));
	}
	
	public XMLNoteImageIcon(URL url,String description,XMLNoteImageIconSize _size) {
		try {
			_original=ImageIO.read(url);
		} catch (IOException e) {
			// can't read ==> create a null image
			e.printStackTrace();
			_otherIcon=new JXMLNoteIcon("NoImage",50,50);
			_original=null;
		}
		
		if (description==null) { this.setDescription("no description"); }
		else { this.setDescription(description); }

		resource=url.toString();
		size=_size;
		
		if (_original==null) {
			size=new XMLNoteImageIconSize(_otherIcon.getIconWidth(),_otherIcon.getIconHeight(),XMLNoteImageIconSize.TYPE_PX);
		} else {
			Dimension sizeInPixels=size.getSizeInPx(_original);
			Image img=BufferedImageBuilder.getScaledInstance(_original,sizeInPixels.width, sizeInPixels.height);
			_imageIcon=new ImageIcon(img);
		}
	}
	
	public XMLNoteImageIcon(String id,ImageIcon orig_icn,String description,XMLNoteImageIconSize _size) {
		//set_imageIcon(icn);
		_original=orig_icn.getImage();
		Dimension sizeInPixels=_size.getSizeInPx(_original);
		ImageIcon icn=new ImageIcon(BufferedImageBuilder.getScaledInstance(_original,sizeInPixels.width, sizeInPixels.height));
		set_imageIcon(icn);
		setId(id);
		setDescription(description);
		setSize(_size);
	}
	
	public XMLNoteImageIcon(String id,XMLNoteImageIconSize sz,Dimension origSizeInPx,String description) {
		_original=new BufferedImage(
				origSizeInPx.width,
				origSizeInPx.height,
				BufferedImage.TYPE_INT_RGB
				);
		Graphics g=_original.getGraphics();
		g.setColor(new Color(234,232,227));
		g.fillRect(0, 0, origSizeInPx.width, origSizeInPx.height);
		g.dispose();
		
		setSize(sz);
		setDescription(description);
		setId(id);
		Dimension sizeInPixels=sz.getSizeInPx(origSizeInPx);
		Image img=new BufferedImage(sizeInPixels.width, sizeInPixels.height,BufferedImage.TYPE_INT_RGB);
		g=img.getGraphics();
		g.setColor(new Color(234,232,227));
		g.fillRect(0, 0, origSizeInPx.width, origSizeInPx.height);
		g.dispose();
		set_imageIcon(new ImageIcon(img));
	}
	
	public void initialize(ImageIcon orig_icn,final LoadedListener l) {
		_original=orig_icn.getImage();
		Dimension sizeInPixels=size.getSizeInPx(_original);
		ImageIcon icn=new ImageIcon(
				BufferedImageBuilder.getScaledInstance(
						_original,sizeInPixels.width, sizeInPixels.height,
						new ScaleListener() {
							public void scaled() {
								l.loaded();
							}
						}
					)
				);
		set_imageIcon(icn);
	}

	public XMLNoteImageIcon(String id,ImageIcon orig_icn,String description,XMLNoteImageIconSize _size,final LoadedListener l) {
		//set_imageIcon(icn);
		_original=orig_icn.getImage();
		Dimension sizeInPixels=_size.getSizeInPx(_original);
		ImageIcon icn=new ImageIcon(
				BufferedImageBuilder.getScaledInstance(
						_original,sizeInPixels.width, sizeInPixels.height,
						new ScaleListener() {
							public void scaled() {
								l.loaded();
							}
						}
					)
				);
		set_imageIcon(icn);
		setId(id);
		setDescription(description);
		setSize(_size);
	}
	
	public XMLNoteImageIcon(Icon img) {
		_otherIcon=img;
		_imageIcon=null;
		size=new XMLNoteImageIconSize(img.getIconWidth(),img.getIconHeight(),XMLNoteImageIconSize.TYPE_PX);
		this.setDescription("no description");
	}

}
