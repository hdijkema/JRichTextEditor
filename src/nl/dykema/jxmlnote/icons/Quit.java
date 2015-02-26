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

package nl.dykema.jxmlnote.icons;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


/**
 * This class has been automatically generated using <a
 * href="https://flamingo.dev.java.net">Flamingo SVG transcoder</a>.
 */
public class Quit implements FlamencoIconAdapter {
	/**
	 * Paints the transcoded SVG image on the specified graphics context. You
	 * can install a custom transformation on the graphics context to scale the
	 * image.
	 * 
	 * @param g
	 *            Graphics context.
	 */
	public  void paint(Graphics2D g) {
        Shape shape = null;
        Paint paint = null;
        Stroke stroke = null;
        
        float origAlpha = 1.0f;
        Composite origComposite = ((Graphics2D)g).getComposite();
        if (origComposite instanceof AlphaComposite) {
            AlphaComposite origAlphaComposite = 
                (AlphaComposite)origComposite;
            if (origAlphaComposite.getRule() == AlphaComposite.SRC_OVER) {
                origAlpha = origAlphaComposite.getAlpha();
            }
        }
        
        AffineTransform defaultTransform_ = g.getTransform();
// 
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0 = g.getTransform();
g.transform(new AffineTransform(0.25f, 0.0f, 0.0f, 0.25f, -0.0f, -0.0f));
// _0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0
g.setTransform(defaultTransform__0_0_0);
g.setTransform(defaultTransform__0_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1
g.setComposite(AlphaComposite.getInstance(3, 0.2f * origAlpha));
AffineTransform defaultTransform__0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_0
paint = new Color(0, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.285, 130.645);
((GeneralPath)shape).curveTo(11.285, 198.111, 66.174, 253.0, 133.643, 253.0);
((GeneralPath)shape).curveTo(201.11, 253.0, 256.0, 198.111, 256.0, 130.645);
((GeneralPath)shape).curveTo(256.0, 63.175, 201.11, 8.286, 133.643, 8.286);
((GeneralPath)shape).curveTo(66.174, 8.286, 11.285, 63.175, 11.285, 130.645);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_1
paint = new Color(102, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(6.449, 125.798);
((GeneralPath)shape).curveTo(6.449, 193.265, 61.338, 248.15399, 128.806, 248.15399);
((GeneralPath)shape).curveTo(196.274, 248.15399, 251.163, 193.26498, 251.163, 125.79799);
((GeneralPath)shape).curveTo(251.163, 58.328987, 196.273, 3.4399872, 128.806, 3.4399872);
((GeneralPath)shape).curveTo(61.338, 3.44, 6.449, 58.33, 6.449, 125.798);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_2
paint = new Color(102, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(128.807, 5.44);
((GeneralPath)shape).curveTo(62.441, 5.44, 8.449, 59.433, 8.449, 125.798);
((GeneralPath)shape).curveTo(8.449, 192.16199, 62.441, 246.15399, 128.806, 246.15399);
((GeneralPath)shape).curveTo(195.17099, 246.15399, 249.163, 192.16199, 249.163, 125.79799);
((GeneralPath)shape).curveTo(249.164, 59.433, 195.172, 5.44, 128.807, 5.44);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_2);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_3
paint = new RadialGradientPaint(new Point2D.Double(50.585899353027344, 55.00979995727539), 259.5079f, new Point2D.Double(50.585899353027344, 55.00979995727539), new float[] {0.0056f,0.5843f,1.0f}, new Color[] {new Color(255, 153, 153, 255),new Color(255, 0, 0, 255),new Color(153, 0, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(14.449, 125.798);
((GeneralPath)shape).curveTo(14.449, 188.955, 65.648, 240.15399, 128.806, 240.15399);
((GeneralPath)shape).curveTo(191.96399, 240.15399, 243.163, 188.95499, 243.163, 125.79799);
((GeneralPath)shape).curveTo(243.163, 62.639988, 191.96399, 11.439987, 128.806, 11.439987);
((GeneralPath)shape).curveTo(65.64801, 11.439987, 14.449, 62.64, 14.449, 125.798);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_3);
g.setComposite(AlphaComposite.getInstance(3, 0.5f * origAlpha));
AffineTransform defaultTransform__0_1_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_4
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.449, 129.813);
((GeneralPath)shape).curveTo(17.449, 66.655, 68.648, 15.455002, 131.806, 15.455002);
((GeneralPath)shape).curveTo(168.43399, 15.455002, 201.03601, 32.677002, 221.966, 59.462);
((GeneralPath)shape).curveTo(201.231, 30.394001, 167.233, 11.440002, 128.806, 11.440002);
((GeneralPath)shape).curveTo(65.647995, 11.440002, 14.4489975, 62.640003, 14.4489975, 125.798004);
((GeneralPath)shape).curveTo(14.4489975, 152.328, 23.485996, 176.744, 38.644997, 196.146);
((GeneralPath)shape).curveTo(25.301, 177.439, 17.449, 154.543, 17.449, 129.813);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_4);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_5
paint = new LinearGradientPaint(new Point2D.Double(127.51509857177734, 12.998000144958496), new Point2D.Double(127.51509857177734, 101.99839782714844), new float[] {0.0056f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 86, 86, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(35.672, 71.752);
((GeneralPath)shape).curveTo(57.685, 81.768, 88.603, 88.0, 122.847, 88.0);
((GeneralPath)shape).curveTo(162.26, 88.0, 197.267, 79.744, 219.35901, 66.966995);
((GeneralPath)shape).curveTo(201.339, 35.605995, 167.49901, 14.439995, 128.807, 14.439995);
((GeneralPath)shape).curveTo(88.179, 14.44, 52.903, 37.779, 35.672, 71.752);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_5);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_6
paint = new Color(102, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(161.307, 41.489);
((GeneralPath)shape).lineTo(161.307, 74.977);
((GeneralPath)shape).curveTo(178.043, 85.717995, 189.164, 104.477, 189.164, 125.798);
((GeneralPath)shape).curveTo(189.164, 159.079, 162.088, 186.15399, 128.807, 186.15399);
((GeneralPath)shape).curveTo(95.52602, 186.15399, 68.45001, 159.079, 68.45001, 125.79799);
((GeneralPath)shape).curveTo(68.45001, 104.47799, 79.572014, 85.71799, 96.307014, 74.97699);
((GeneralPath)shape).lineTo(96.307014, 41.489);
((GeneralPath)shape).curveTo(62.495014, 54.568, 38.450016, 87.425995, 38.450016, 125.798);
((GeneralPath)shape).curveTo(38.450016, 175.621, 78.98402, 216.15399, 128.80702, 216.15399);
((GeneralPath)shape).curveTo(178.63004, 216.15399, 219.16403, 175.62099, 219.16403, 125.79799);
((GeneralPath)shape).curveTo(219.164, 87.426, 195.119, 54.568, 161.307, 41.489);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_6);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_7
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(164.307, 45.986);
((GeneralPath)shape).lineTo(164.307, 73.347);
((GeneralPath)shape).curveTo(181.106, 84.753, 192.164, 104.007996, 192.164, 125.798004);
((GeneralPath)shape).curveTo(192.164, 160.733, 163.742, 189.154, 128.807, 189.154);
((GeneralPath)shape).curveTo(93.87201, 189.154, 65.45001, 160.733, 65.45001, 125.798004);
((GeneralPath)shape).curveTo(65.45001, 104.007, 76.50901, 84.753006, 93.307014, 73.347);
((GeneralPath)shape).lineTo(93.307014, 45.986);
((GeneralPath)shape).curveTo(62.780014, 59.618, 41.450016, 90.263, 41.450016, 125.799);
((GeneralPath)shape).curveTo(41.450016, 173.96701, 80.638016, 213.155, 128.80702, 213.155);
((GeneralPath)shape).curveTo(176.97603, 213.155, 216.16403, 173.967, 216.16403, 125.798996);
((GeneralPath)shape).curveTo(216.164, 90.263, 194.834, 59.618, 164.307, 45.986);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_7);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_8
paint = new Color(102, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(112.307, 33.0);
((GeneralPath)shape).curveTo(110.65, 33.0, 109.307, 34.246, 109.307, 35.783);
((GeneralPath)shape).lineTo(109.307, 158.218);
((GeneralPath)shape).curveTo(109.307, 159.755, 110.65, 161.0, 112.307, 161.0);
((GeneralPath)shape).lineTo(145.307, 161.0);
((GeneralPath)shape).curveTo(146.964, 161.0, 148.307, 159.755, 148.307, 158.218);
((GeneralPath)shape).lineTo(148.307, 35.783);
((GeneralPath)shape).curveTo(148.307, 34.246002, 146.964, 33.0, 145.307, 33.0);
((GeneralPath)shape).lineTo(112.307, 33.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_8);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_9
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(145.307, 158.218);
((GeneralPath)shape).lineTo(112.30701, 158.218);
((GeneralPath)shape).lineTo(112.30701, 35.783);
((GeneralPath)shape).lineTo(145.307, 35.783);
((GeneralPath)shape).lineTo(145.307, 158.218);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_9);
g.setTransform(defaultTransform__0_1);
g.setTransform(defaultTransform__0);
g.setTransform(defaultTransform_);

	}

    /**
     * Returns the X of the bounding box of the original SVG image.
     * 
     * @return The X of the bounding box of the original SVG image.
     */
    public  int getOrigX() {
        return 2;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public  int getOrigY() {
        return 1;
    }

    /**
     * Returns the width of the bounding box of the original SVG image.
     * 
     * @return The width of the bounding box of the original SVG image.
     */
    public  int getOrigWidth() {
        return 63;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * 
     * @return The height of the bounding box of the original SVG image.
     */
    public  int getOrigHeight() {
        return 63;
    }
}

