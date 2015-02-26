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
public class Edit_redo implements FlamencoIconAdapter {
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0
g.setComposite(AlphaComposite.getInstance(3, 0.14117648f * origAlpha));
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.4897359609603882f, 0.0f, 0.0f, -1.0012520551681519f, -12.647159576416016f, 75.3125991821289f));
// _0_0_0
paint = new RadialGradientPaint(new Point2D.Double(24.837125778198242, 36.42112731933594), 15.644737f, new Point2D.Double(24.837125778198242, 36.42112731933594), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.5367230176925659f, -5.825328826085385E-14f, 16.87306022644043f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(40.48186, 36.421127);
((GeneralPath)shape).curveTo(40.483814, 39.421745, 37.50237, 42.19488, 32.66107, 43.69549);
((GeneralPath)shape).curveTo(27.81977, 45.196106, 21.854479, 45.196106, 17.01318, 43.69549);
((GeneralPath)shape).curveTo(12.17188, 42.19488, 9.190436, 39.421745, 9.192389, 36.421127);
((GeneralPath)shape).curveTo(9.190436, 33.42051, 12.17188, 30.647373, 17.01318, 29.14676);
((GeneralPath)shape).curveTo(21.854479, 27.646149, 27.81977, 27.646149, 32.66107, 29.14676);
((GeneralPath)shape).curveTo(37.50237, 30.647373, 40.483814, 33.42051, 40.48186, 36.421127);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1
paint = new LinearGradientPaint(new Point2D.Double(33.0, 35.75), new Point2D.Double(31.5, 42.5), new float[] {0.0f,1.0f}, new Color[] {new Color(128, 11, 176, 255),new Color(138, 11, 176, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(38.37476, 45.03437);
((GeneralPath)shape).curveTo(-1.6510485, 46.35551, 4.6747956, 12.29355, 25.49479, 12.49765);
((GeneralPath)shape).lineTo(25.49479, 3.1222396);
((GeneralPath)shape).lineTo(42.143272, 17.708818);
((GeneralPath)shape).lineTo(25.49479, 33.006348);
((GeneralPath)shape).curveTo(25.49479, 33.006348, 25.49479, 23.337969, 25.49479, 23.337969);
((GeneralPath)shape).curveTo(11.43168, 22.751999, 7.3172617, 44.77055, 38.37476, 45.03437);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(33.0, 35.75), new Point2D.Double(31.5, 42.5), new float[] {0.0f,1.0f}, new Color[] {new Color(109, 0, 134, 255),new Color(116, 0, 134, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.0000001f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(38.37476, 45.03437);
((GeneralPath)shape).curveTo(-1.6510485, 46.35551, 4.6747956, 12.29355, 25.49479, 12.49765);
((GeneralPath)shape).lineTo(25.49479, 3.1222396);
((GeneralPath)shape).lineTo(42.143272, 17.708818);
((GeneralPath)shape).lineTo(25.49479, 33.006348);
((GeneralPath)shape).curveTo(25.49479, 33.006348, 25.49479, 23.337969, 25.49479, 23.337969);
((GeneralPath)shape).curveTo(11.43168, 22.751999, 7.3172617, 44.77055, 38.37476, 45.03437);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_1);
g.setComposite(AlphaComposite.getInstance(3, 0.6988636f * origAlpha));
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2
paint = new LinearGradientPaint(new Point2D.Double(17.060806274414062, 11.39501953125), new Point2D.Double(12.624337196350098, 12.583768844604492), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.8134709379429573E-16f, -1.1719260215759277f, 1.1719260215759277f, 1.8134709379429573E-16f, 1.7828010320663452f, 54.10110855102539f));
stroke = new BasicStroke(0.9999997f,0,0,10.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.92492, 39.315517);
((GeneralPath)shape).curveTo(5.2018204, 33.235893, 8.737127, 13.087489, 26.5085, 13.549959);
((GeneralPath)shape).lineTo(26.5085, 5.4508677);
((GeneralPath)shape).curveTo(26.5085, 5.4508677, 40.556236, 17.714588, 40.556236, 17.714588);
((GeneralPath)shape).lineTo(26.5085, 30.658617);
((GeneralPath)shape).curveTo(26.5085, 30.658617, 26.5085, 22.38098, 26.5085, 22.38098);
((GeneralPath)shape).curveTo(11.66865, 22.03271, 12.34859, 35.13858, 16.92492, 39.315517);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_2);
g.setComposite(AlphaComposite.getInstance(3, 0.49431816f * origAlpha));
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3
paint = new RadialGradientPaint(new Point2D.Double(16.5638370513916, 11.132235527038574), 19.0625f, new Point2D.Double(16.5638370513916, 11.132235527038574), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.01290126983076334f, 1.6851969957351685f, 1.713081955909729f, 0.013114750385284424f, -1.0414990186691284f, -10.115710258483887f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.03699, 4.5686097);
((GeneralPath)shape).lineTo(36.72373, 14.798241);
((GeneralPath)shape).curveTo(29.786226, 14.79824, 32.036987, 23.735424, 25.91199, 26.610424);
((GeneralPath)shape).lineTo(25.97449, 22.94361);
((GeneralPath)shape).curveTo(10.786989, 22.88111, 11.661989, 38.443607, 22.72449, 42.693607);
((GeneralPath)shape).curveTo(3.6363413, 37.81168, 6.28699, 13.381109, 25.91199, 12.88111);
((GeneralPath)shape).lineTo(26.03699, 4.5686097);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_3);
g.setTransform(defaultTransform__0_0);
g.setTransform(defaultTransform__0);
g.setTransform(defaultTransform_);

	}

    /**
     * Returns the X of the bounding box of the original SVG image.
     * 
     * @return The X of the bounding box of the original SVG image.
     */
    public  int getOrigX() {
        return 0;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public  int getOrigY() {
        return 3;
    }

    /**
     * Returns the width of the bounding box of the original SVG image.
     * 
     * @return The width of the bounding box of the original SVG image.
     */
    public  int getOrigWidth() {
        return 48;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * 
     * @return The height of the bounding box of the original SVG image.
     */
    public  int getOrigHeight() {
        return 46;
    }
}

