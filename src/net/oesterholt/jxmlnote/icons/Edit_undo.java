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
public class Edit_undo implements FlamencoIconAdapter {
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
g.transform(new AffineTransform(-1.4897359609603882f, 0.0f, 0.0f, -1.0012520551681519f, 60.6043586730957f, 75.3125991821289f));
// _0_0_0
paint = new RadialGradientPaint(new Point2D.Double(24.837125778198242, 36.42112731933594), 15.644737f, new Point2D.Double(24.837125778198242, 36.42112731933594), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.5367230176925659f, -6.227264933095569E-14f, 16.87306022644043f));
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
paint = new LinearGradientPaint(new Point2D.Double(26.5, 34.25), new Point2D.Double(26.25, 43.57183074951172), new float[] {0.0f,1.0f}, new Color[] {new Color(174, 0, 237, 255),new Color(185, 0, 237, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(9.582441, 45.03437);
((GeneralPath)shape).curveTo(49.60825, 46.35551, 43.282406, 12.29355, 22.462412, 12.49765);
((GeneralPath)shape).lineTo(22.462412, 3.1222396);
((GeneralPath)shape).lineTo(5.81393, 17.708818);
((GeneralPath)shape).lineTo(22.462412, 33.006348);
((GeneralPath)shape).curveTo(22.462412, 33.006348, 22.462412, 23.337969, 22.462412, 23.337969);
((GeneralPath)shape).curveTo(36.52552, 22.751999, 40.63994, 44.77055, 9.582441, 45.03437);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(26.5, 34.25), new Point2D.Double(26.25, 43.57183074951172), new float[] {0.0f,1.0f}, new Color[] {new Color(167, 0, 196, 255),new Color(157, 0, 196, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.0000001f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(9.582441, 45.03437);
((GeneralPath)shape).curveTo(49.60825, 46.35551, 43.282406, 12.29355, 22.462412, 12.49765);
((GeneralPath)shape).lineTo(22.462412, 3.1222396);
((GeneralPath)shape).lineTo(5.81393, 17.708818);
((GeneralPath)shape).lineTo(22.462412, 33.006348);
((GeneralPath)shape).curveTo(22.462412, 33.006348, 22.462412, 23.337969, 22.462412, 23.337969);
((GeneralPath)shape).curveTo(36.52552, 22.751999, 40.63994, 44.77055, 9.582441, 45.03437);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_1);
g.setComposite(AlphaComposite.getInstance(3, 0.6988636f * origAlpha));
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2
paint = new LinearGradientPaint(new Point2D.Double(17.060806274414062, 11.39501953125), new Point2D.Double(12.624337196350098, 12.583768844604492), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-1.8134709379429573E-16f, -1.1719260215759277f, -1.1719260215759277f, 1.8134709379429573E-16f, 46.174400329589844f, 54.10110855102539f));
stroke = new BasicStroke(0.9999997f,0,0,10.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.032282, 39.315517);
((GeneralPath)shape).curveTo(42.75538, 33.235893, 39.220074, 13.087489, 21.448702, 13.549959);
((GeneralPath)shape).lineTo(21.448702, 5.4508677);
((GeneralPath)shape).curveTo(21.448702, 5.4508677, 7.400963, 17.714588, 7.400963, 17.714588);
((GeneralPath)shape).lineTo(21.448702, 30.658617);
((GeneralPath)shape).curveTo(21.448702, 30.658617, 21.448702, 22.38098, 21.448702, 22.38098);
((GeneralPath)shape).curveTo(36.28855, 22.03271, 35.608612, 35.13858, 31.032282, 39.315517);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_2);
g.setComposite(AlphaComposite.getInstance(3, 0.5113636f * origAlpha));
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3
paint = new RadialGradientPaint(new Point2D.Double(15.094030380249023, 13.282720565795898), 10.16466f, new Point2D.Double(15.094030380249023, 13.282720565795898), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.4960310459136963f, -1.1519049865639486E-16f, 1.0617559979066125E-16f, 2.3006889820098877f, -25.124019622802734f, -17.82636070251465f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(6.629126, 17.682796);
((GeneralPath)shape).lineTo(12.28598, 23.074486);
((GeneralPath)shape).curveTo(18.561552, 22.897709, 15.733126, 16.710526, 26.958447, 13.616933);
((GeneralPath)shape).lineTo(22.0087, 12.998214);
((GeneralPath)shape).lineTo(21.92031, 4.3361564);
((GeneralPath)shape).lineTo(6.629126, 17.682796);
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
        return 1;
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

