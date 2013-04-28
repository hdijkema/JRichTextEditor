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

package net.oesterholt.jxmlnote.icons;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
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
public class AddTopic implements FlamencoIconAdapter {
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -0.0f, -0.0f));
// _0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(0.2640499472618103f, 0.0f, 0.0f, 0.2640499472618103f, 0.7359500527381897f, 1.103925108909607f));
// _0_0
g.setComposite(AlphaComposite.getInstance(3, 0.36f * origAlpha));
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0
paint = new Color(153, 153, 153, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(3.629, 30.802);
((GeneralPath)shape).curveTo(3.629, 46.075, 16.048, 58.5, 31.313, 58.5);
((GeneralPath)shape).curveTo(46.579, 58.5, 59.0, 46.075, 59.0, 30.802);
((GeneralPath)shape).curveTo(59.0, 15.53, 46.579, 3.106, 31.314, 3.106);
((GeneralPath)shape).curveTo(16.048, 3.106, 3.629, 15.53, 3.629, 30.802);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1
paint = new Color(0, 102, 204, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(1.0, 29.197);
((GeneralPath)shape).curveTo(1.0, 44.469, 13.419, 56.894, 28.685, 56.894);
((GeneralPath)shape).curveTo(43.954, 56.894, 56.368, 44.469, 56.368, 29.197);
((GeneralPath)shape).curveTo(56.368, 13.925, 43.954, 1.5, 28.685, 1.5);
((GeneralPath)shape).curveTo(13.419, 1.5, 1.0, 13.925, 1.0, 29.197);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2
paint = new RadialGradientPaint(new Point2D.Double(66.70600128173828, 22.941999435424805), 59.341f, new Point2D.Double(66.70600128173828, 22.941999435424805), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 102, 204, 255),new Color(0, 51, 153, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7171400189399719f, 0.0f, 0.0f, 0.7174400091171265f, -32.79399871826172f, 1.5f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(3.005, 28.796);
((GeneralPath)shape).curveTo(3.005, 42.984, 14.503, 54.487, 28.684002, 54.487);
((GeneralPath)shape).curveTo(42.864002, 54.487, 54.36, 42.984, 54.36, 28.796);
((GeneralPath)shape).curveTo(54.36, 14.608, 42.864, 3.105999, 28.685001, 3.105999);
((GeneralPath)shape).curveTo(14.503001, 3.105999, 3.005001, 14.607999, 3.005001, 28.796);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_2);
g.setComposite(AlphaComposite.getInstance(3, 0.36f * origAlpha));
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(5.814, 31.305);
((GeneralPath)shape).curveTo(5.814, 16.239, 16.573, 3.9349995, 30.113998, 3.1469994);
((GeneralPath)shape).curveTo(29.640999, 3.1209993, 29.164999, 3.1049993, 28.684998, 3.1049993);
((GeneralPath)shape).curveTo(14.503998, 3.1049993, 3.0059967, 14.606999, 3.0059967, 28.795);
((GeneralPath)shape).curveTo(3.0059967, 35.349, 5.463997, 41.325, 9.499996, 45.863);
((GeneralPath)shape).curveTo(7.162, 41.613, 5.814, 36.631, 5.814, 31.304);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_3);
g.setComposite(AlphaComposite.getInstance(3, 0.5f * origAlpha));
AffineTransform defaultTransform__0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4
paint = new LinearGradientPaint(new Point2D.Double(85.76000213623047, 12.789999961853027), new Point2D.Double(85.76000213623047, 44.68199920654297), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(13, 54, 146, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7171400189399719f, 0.0f, 0.0f, 0.7174400091171265f, -32.79399871826172f, 1.5f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(29.079, 27.535);
((GeneralPath)shape).curveTo(37.773003, 27.535, 45.776, 26.271, 52.13, 24.152);
((GeneralPath)shape).curveTo(50.1, 12.182, 40.383, 3.106, 28.686, 3.106);
((GeneralPath)shape).curveTo(17.075, 3.106, 7.406, 12.054, 5.2840004, 23.897999);
((GeneralPath)shape).curveTo(11.752001, 26.169998, 20.041, 27.535, 29.079, 27.535);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_4);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_5
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.724, 42.469);
((GeneralPath)shape).curveTo(32.724, 45.690002, 32.724, 47.565002, 35.884, 47.565002);
((GeneralPath)shape).lineTo(35.884, 48.619003);
((GeneralPath)shape).lineTo(21.484, 48.619003);
((GeneralPath)shape).lineTo(21.484, 47.565002);
((GeneralPath)shape).curveTo(24.703999, 47.565002, 24.703999, 45.691, 24.703999, 42.469);
((GeneralPath)shape).lineTo(24.703999, 28.18);
((GeneralPath)shape).curveTo(24.703999, 25.077, 24.703999, 22.969, 21.484, 22.969);
((GeneralPath)shape).lineTo(21.484, 21.914999);
((GeneralPath)shape).lineTo(32.724, 21.914999);
((GeneralPath)shape).lineTo(32.724, 42.469);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(28.685, 8.973);
((GeneralPath)shape).curveTo(31.085, 8.973, 33.075, 10.964, 33.075, 13.365);
((GeneralPath)shape).curveTo(33.075, 15.766, 31.085001, 17.757, 28.685001, 17.757);
((GeneralPath)shape).curveTo(26.285002, 17.757, 24.295002, 15.766, 24.295002, 13.365);
((GeneralPath)shape).curveTo(24.295002, 10.964, 26.285002, 8.973, 28.685001, 8.973);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_5);
g.setTransform(defaultTransform__0_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1 = g.getTransform();
g.transform(new AffineTransform(0.33360761404037476f, 0.0f, 0.0f, 0.33360761404037476f, 3.5520875453948975f, 4.986170291900635f));
// _0_1
g.setComposite(AlphaComposite.getInstance(3, 0.40909088f * origAlpha));
AffineTransform defaultTransform__0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_0
paint = new RadialGradientPaint(new Point2D.Double(17.3125, 25.53125), 9.6875f, new Point2D.Double(17.3125, 25.53125), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(8.2122163772583f, 0.0f, 0.0f, 2.887521743774414f, -61.277950286865234f, 82.37240600585938f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(160.45189, 156.09447);
((GeneralPath)shape).curveTo(160.45189, 171.54344, 124.83353, 184.06732, 80.89604, 184.06732);
((GeneralPath)shape).curveTo(36.958553, 184.06732, 1.3401947, 171.54346, 1.3401947, 156.09447);
((GeneralPath)shape).curveTo(1.3401947, 140.64548, 36.958553, 128.1216, 80.89604, 128.1216);
((GeneralPath)shape).curveTo(124.83353, 128.1216, 160.45189, 140.64548, 160.45189, 156.09447);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_1
paint = new Color(117, 236, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(159.14754, 80.93425);
((GeneralPath)shape).curveTo(159.14754, 124.1302, 124.13028, 159.14743, 80.934296, 159.14743);
((GeneralPath)shape).curveTo(37.73832, 159.14743, 2.7210617, 124.1302, 2.7210617, 80.93425);
((GeneralPath)shape).curveTo(2.7210612, 37.7383, 37.738323, 2.7210617, 80.934296, 2.7210617);
((GeneralPath)shape).curveTo(124.13028, 2.7210617, 159.14754, 37.738297, 159.14754, 80.93425);
((GeneralPath)shape).lineTo(159.14754, 80.93425);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(63, 127, 0, 255);
stroke = new BasicStroke(3.5551453f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(159.14754, 80.93425);
((GeneralPath)shape).curveTo(159.14754, 124.1302, 124.13028, 159.14743, 80.934296, 159.14743);
((GeneralPath)shape).curveTo(37.73832, 159.14743, 2.7210617, 124.1302, 2.7210617, 80.93425);
((GeneralPath)shape).curveTo(2.7210612, 37.7383, 37.738323, 2.7210617, 80.934296, 2.7210617);
((GeneralPath)shape).curveTo(124.13028, 2.7210617, 159.14754, 37.738297, 159.14754, 80.93425);
((GeneralPath)shape).lineTo(159.14754, 80.93425);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_1_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_2
paint = new Color(255, 254, 255, 78);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(155.30664, 77.15268);
((GeneralPath)shape).curveTo(159.55234, 119.78494, 132.53809, 60.95256, 85.05141, 78.60629);
((GeneralPath)shape).curveTo(37.18871, 96.3998, 3.9719014, 123.48093, 7.6787705, 77.15268);
((GeneralPath)shape).curveTo(11.051903, 34.99544, 40.74741, 3.3387592, 81.49271, 3.3387592);
((GeneralPath)shape).curveTo(122.23799, 3.3387592, 155.30664, 36.407394, 155.30664, 77.15268);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_2);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_3
paint = new Color(168, 255, 79, 255);
stroke = new BasicStroke(3.440193f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(157.1429, 80.59072);
((GeneralPath)shape).curveTo(157.1429, 122.38997, 123.257904, 156.27496, 81.45863, 156.27496);
((GeneralPath)shape).curveTo(39.659367, 156.27496, 5.7743683, 122.38997, 5.7743683, 80.59072);
((GeneralPath)shape).curveTo(5.774368, 38.791466, 39.659367, 4.906479, 81.45863, 4.906479);
((GeneralPath)shape).curveTo(123.257904, 4.906479, 157.1429, 38.79147, 157.1429, 80.59072);
((GeneralPath)shape).lineTo(157.1429, 80.59072);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_1_3);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_4
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(66.24961, 36.331203);
((GeneralPath)shape).lineTo(66.24961, 66.11718);
((GeneralPath)shape).lineTo(36.46364, 66.11718);
((GeneralPath)shape).lineTo(36.46364, 94.78618);
((GeneralPath)shape).lineTo(66.24961, 94.78618);
((GeneralPath)shape).lineTo(66.24961, 124.57215);
((GeneralPath)shape).lineTo(94.91861, 124.57215);
((GeneralPath)shape).lineTo(94.91861, 94.78618);
((GeneralPath)shape).lineTo(124.70459, 94.78618);
((GeneralPath)shape).lineTo(124.70459, 66.11718);
((GeneralPath)shape).lineTo(94.91861, 66.11718);
((GeneralPath)shape).lineTo(94.91861, 36.331203);
((GeneralPath)shape).lineTo(66.24961, 36.331203);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_4);
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
        return 1;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public  int getOrigY() {
        return 2;
    }

    /**
     * Returns the width of the bounding box of the original SVG image.
     * 
     * @return The width of the bounding box of the original SVG image.
     */
    public  int getOrigWidth() {
        return 57;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * 
     * @return The height of the bounding box of the original SVG image.
     */
    public  int getOrigHeight() {
        return 59;
    }
}

