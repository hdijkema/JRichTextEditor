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

package net.oesterholt.jxmlnote.help.data;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import net.oesterholt.jxmlnote.document.XMLNoteImageIconSize;
import net.oesterholt.jxmlnote.utils.BufferedImageBuilder;


public class HelpImage {
	
	public interface ImageLoadedListener {
		public void loaded();
	};

	private BufferedImage img;
	private ImageIcon icon;
	private String id;
	private String description;
	private XMLNoteImageIconSize size;
	private static Integer VERSION=2;
	private int _bytes;
	
	public float sizeInKb() {
		return ((float) _bytes)/1024.0f;
	}
	
	public void write(OutputStream out) throws IOException {
		ObjectOutputStream tout=new ObjectOutputStream(out);
		tout.writeObject(VERSION);
		tout.writeObject(id);
		tout.writeObject(description);
		tout.writeObject(size);
		Dimension imgSize=new Dimension(icon.getIconWidth(),icon.getIconHeight());
		tout.writeObject(imgSize);
		Integer size;
		ByteArrayOutputStream bout=new ByteArrayOutputStream();
		if (icon.getImage() instanceof BufferedImage) {
			img=(BufferedImage) icon.getImage();
		} else {
			BufferedImageBuilder builder=new BufferedImageBuilder();
			img=builder.bufferImage(icon.getImage());
		}
		if (img instanceof BufferedImage) {
			BufferedImage bimg=(BufferedImage) img;
			ImageIO.write(bimg, "jpg", bout);
			bout.close();
			byte[] b=bout.toByteArray();
			size=b.length;
			tout.writeObject(size);
			out.write(b);
		} else {
			size=-1;
			tout.writeObject(size);
			tout.writeObject(icon);
		}
	}
	
	public XMLNoteImageIconSize read(InputStream in) throws IOException, ClassNotFoundException {
		return read(in,new Dimension(),null);
	}
	
	public XMLNoteImageIconSize read(final InputStream in,final Dimension dim,final ImageLoadedListener l) throws IOException, ClassNotFoundException {
		final ObjectInputStream tin=new ObjectInputStream(in);
		Object v=tin.readObject();
		int ver=VERSION;
		if (!(v instanceof Integer)) { ver=1; }
		if (ver==1) {
			id=(String) v;
		} else {
			id=(String) tin.readObject();
		}
		//System.out.println("version="+ver);
		description=(String) tin.readObject();
		size=(XMLNoteImageIconSize) tin.readObject();
		if (ver>1) {
			Dimension d=(Dimension) tin.readObject();
			dim.width=d.width;
			dim.height=d.height;
		}
		_bytes=(Integer) tin.readObject();
		Runnable R=new Runnable() {
			public void run() {
				try {
					Integer imgsize_in_bytes=_bytes;
					if (imgsize_in_bytes==-1) {
						icon=(ImageIcon) tin.readObject();
					} else {
						img=ImageIO.read(in);
						icon=new ImageIcon(img);
					}
				} catch (Exception E) {
					icon=new ImageIcon(new BufferedImage(size.width(),size.height(),BufferedImage.TYPE_INT_RGB));
				}
				if (l!=null) {
					l.loaded();
				}
			}
		};
		if (l==null || ver==1) {
			R.run();
			dim.width=icon.getIconWidth();
			dim.height=icon.getIconHeight();
		} else {
			Thread thr=new Thread(R);
			thr.start();
		}
		return size;
	}

	public XMLNoteImageIconSize getSize() {
		return size;
	}

	public void setSize(XMLNoteImageIconSize size) {
		this.size = size;
	}

	public ImageIcon getImageIcon() {
		return icon;
	}
	
	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icn) {
		icon=icn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public HelpImage(String id, ImageIcon _icon, String description,XMLNoteImageIconSize _size) {
		this.setIcon(_icon);
		this.setId(id);
		this.setDescription(description);
		this.setSize(_size);
		img=null;
	}
	
	public HelpImage() {
		img=null;
	}

}

