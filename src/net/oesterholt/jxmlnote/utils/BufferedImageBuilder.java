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

package net.oesterholt.jxmlnote.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class BufferedImageBuilder {
	
	public interface ScaleListener {
		public void scaled();
	};

    private static final int DEFAULT_IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;  // FIXME: Check if this works

    static public BufferedImage bufferImage(Image image) {
        return bufferImage(image, DEFAULT_IMAGE_TYPE);
    }

    static public BufferedImage bufferImage(Image image, int type) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, null, null);
        g.dispose();
        return bufferedImage;
    }
    
    static public BufferedImage bufferImage(Color background,int width,int height) {
    	BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    	Graphics2D g=bufferedImage.createGraphics();
    	g.setColor(background);
    	g.fillRect(0, 0, width, height);
    	g.dispose();
    	return bufferedImage;
    }
    
    /**
     * Convenience method that returns a scaled instance of the
     * provided {@code BufferedImage}.
     *
     * @param img the original image to be scaled
     * @param targetWidth the desired width of the scaled instance,
     *    in pixels
     * @param targetHeight the desired height of the scaled instance,
     *    in pixels
     * @param hint one of the rendering hints that corresponds to
     *    {@code RenderingHints.KEY_INTERPOLATION} (e.g.
     *    {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
     * @param higherQuality if true, this method will use a multi-step
     *    scaling technique that provides higher quality than the usual
     *    one-step technique (only useful in downscaling cases, where
     *    {@code targetWidth} or {@code targetHeight} is
     *    smaller than the original dimensions, and generally only when
     *    the {@code BILINEAR} hint is specified)
     * @return a scaled version of the original {@code BufferedImage}
     */
    static public BufferedImage getScaledInstance(Image img,
                                           int targetWidth,
                                           int targetHeight,
                                           final Object hint,
                                           final boolean higherQuality,
                                           final ScaleListener l) {
    	
    	int type=BufferedImage.TYPE_INT_ARGB;
    	if (img instanceof BufferedImage) {
    		type = (((BufferedImage) img).getTransparency() == Transparency.OPAQUE) ?
    							BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	}
        
    	BufferedImage ret = null;
        if (img instanceof BufferedImage) {
        	ret=(BufferedImage) img;
        } else {
        	ret=bufferImage(img,type);
        }
        
        // adjust target w/h
        if (targetWidth<=0 && targetHeight<=0) {
        	throw new IllegalArgumentException("Target width ("+targetWidth+") and height ("+targetHeight+") cannot be <=0");
        }
        
        if (targetWidth<=0) {
        	int rwidth=ret.getWidth();
        	int rheight=ret.getHeight();
        	float scale=((float) targetHeight)/((float) rheight);
        	targetWidth=(int) (Math.round(scale*rwidth));
        } else if (targetHeight<=0) {
        	int rwidth=ret.getWidth();
        	int rheight=ret.getHeight();
        	float scale=((float) targetWidth)/((float) rwidth);
        	targetHeight=(int) (Math.round(scale*rheight));
        }
        
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = ret.getWidth();
            h = ret.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }
        
        final BufferedImage end=new BufferedImage(targetWidth,targetHeight,type);
        if (type!=BufferedImage.TYPE_INT_ARGB) {  
        	Graphics g=end.getGraphics();
        	g.setColor(new Color(234,232,227));
        	g.fillRect(0, 0, targetWidth, targetHeight);
        	g.dispose();
        }
        
        final int twidth=targetWidth;
        final int theight=targetHeight;
        final int ww=w;
        final int hh=h;
        final int ttype=type;
        final BufferedImage rret=ret;
        
        Runnable R=new Runnable() {
        	public void run() {
        		int w=ww;
        		int h=hh;
        		BufferedImage ret=rret;
                do {
                    if (higherQuality && w > twidth) {
                        w /= 2;
                        if (w < twidth) {
                            w = twidth;
                        }
                    } else if (higherQuality && w<twidth) {
                    	w=twidth;
                    } else {
                    	w=twidth;
                    }

                    if (higherQuality && h > theight) {
                        h /= 2;
                        if (h < theight) {
                            h = theight;
                        }
                    } else if (higherQuality && h>twidth) {
                    	h=theight;
                    } else {
                    	h=theight;
                    }
                    
                    BufferedImage tmp; 
                    if (w==twidth && h==theight) {
                    	tmp=end;
                    } else {
                    	tmp= new BufferedImage(w, h, ttype);
                    }
                    Graphics2D g2 = tmp.createGraphics();
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
                    g2.drawImage(ret, 0, 0, w, h, null);
                    g2.dispose();

                    ret = tmp;
                } while (w != twidth || h != theight);
                
                if (l!=null) { l.scaled(); }
        	}
        	
        };
        
        if (l!=null) {
        	Thread thr=new Thread(R);
        	thr.setName("BufferedImageBuilder.getScaledInstance()"); 
        	thr.start();
        } else {
        	R.run();
        }

        return end;
    }
    
    static public BufferedImage getScaledInstance(Image img,int targetWidth,int targetHeight) {
    	return getScaledInstance(
    			img,
    			targetWidth,targetHeight,
    			RenderingHints.VALUE_INTERPOLATION_BILINEAR, 
    			true,
    			null
    			);
    }
    
    static public BufferedImage getScaledInstance(Image img,int tw,int th,ScaleListener l) {
    	return getScaledInstance(
    			img,
    			tw,th,
    			RenderingHints.VALUE_INTERPOLATION_BILINEAR,
    			true,
    			l);
    }

}
