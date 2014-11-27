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

package net.dijkema.jxmlnote.icons;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;


/**
 * This class has been automatically generated using <a
 * href="https://flamingo.dev.java.net">Flamingo SVG transcoder</a>.
 */
public class Edit_paste implements FlamencoIconAdapter {
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
g.setComposite(AlphaComposite.getInstance(3, 0.352941f * origAlpha));
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(0.7245029807090759f, 0.0f, 0.0f, 0.4468590021133423f, 6.171689987182617f, 23.280399322509766f));
// _0_0_0
paint = new Color(0, 0, 0, 255);
shape = new RoundRectangle2D.Double(19.51453399658203, 38.438262939453125, 35.125, 6.5, 8.971664428710938, 6.5);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0);
g.setComposite(AlphaComposite.getInstance(3, 0.417647f * origAlpha));
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 0.6153849959373474f, 0.0f, 15.798100471496582f));
// _0_0_1
paint = new Color(0, 0, 0, 255);
shape = new RoundRectangle2D.Double(6.874999523162842, 35.875, 35.125, 6.5, 6.499999523162842, 6.5);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f));
// _0_0_2
paint = new LinearGradientPaint(new Point2D.Double(25.5, -13.625), new Point2D.Double(26.0, -39.125), new float[] {0.0f,1.0f}, new Color[] {new Color(226, 179, 105, 255),new Color(199, 155, 85, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new RoundRectangle2D.Double(9.5, -40.5, 29.999996185302734, 31.999998092651367, 5.499999523162842, 5.5);
g.setPaint(paint);
g.fill(shape);
paint = new Color(143, 89, 2, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(9.5, -40.5, 29.999996185302734, 31.999998092651367, 5.499999523162842, 5.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_2);
g.setComposite(AlphaComposite.getInstance(3, 0.441176f * origAlpha));
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0502500534057617f, 0.0f, 0.0f, 1.0502500534057617f, -1.2378699779510498f, -1.6664700508117676f));
// _0_0_3
paint = new Color(0, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.722015, 14.506832);
((GeneralPath)shape).lineTo(32.354477, 14.506832);
((GeneralPath)shape).curveTo(33.32675, 14.551026, 33.381283, 12.617748, 33.381283, 12.617748);
((GeneralPath)shape).lineTo(30.497072, 10.307271);
((GeneralPath)shape).lineTo(30.506163, 9.552748);
((GeneralPath)shape).curveTo(30.506163, 9.552748, 18.491032, 9.532198, 18.491032, 9.532198);
((GeneralPath)shape).lineTo(18.491032, 10.422399);
((GeneralPath)shape).lineTo(15.890165, 12.665036);
((GeneralPath)shape).curveTo(15.890165, 12.665036, 15.838132, 14.462638, 16.722015, 14.506832);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_3);
g.setComposite(AlphaComposite.getInstance(3, 0.417647f * origAlpha));
AffineTransform defaultTransform__0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f));
// _0_0_4
paint = new LinearGradientPaint(new Point2D.Double(24.499998092651367, -38.5000114440918), new Point2D.Double(24.499998092651367, -1.6250113248825073), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(10.635226249694824, -39.289100646972656, 27.729543685913086, 29.578182220458984, 2.4999990463256836, 2.4999990463256836);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_4);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_5
paint = new LinearGradientPaint(new Point2D.Double(24.635435104370117, 7.202692985534668), new Point2D.Double(24.635435104370117, 12.380688667297363), new float[] {0.0f,1.0f}, new Color[] {new Color(191, 188, 179, 255),new Color(156, 152, 138, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.722015, 12.506832);
((GeneralPath)shape).lineTo(32.354477, 12.506832);
((GeneralPath)shape).curveTo(33.32675, 12.551026, 33.381283, 10.617748, 33.381283, 10.617748);
((GeneralPath)shape).lineTo(30.497072, 8.307271);
((GeneralPath)shape).lineTo(30.506163, 4.5527477);
((GeneralPath)shape).curveTo(30.506163, 3.7301333, 29.81436, 2.6194692, 28.807358, 2.6194692);
((GeneralPath)shape).lineTo(20.287313, 2.5310807);
((GeneralPath)shape).curveTo(19.074886, 2.5310807, 18.491032, 3.719232, 18.491032, 4.5321975);
((GeneralPath)shape).lineTo(18.491032, 8.422399);
((GeneralPath)shape).lineTo(15.890165, 10.665036);
((GeneralPath)shape).curveTo(15.890165, 10.665036, 15.838132, 12.462638, 16.722015, 12.506832);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(85, 87, 83, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.722015, 12.506832);
((GeneralPath)shape).lineTo(32.354477, 12.506832);
((GeneralPath)shape).curveTo(33.32675, 12.551026, 33.381283, 10.617748, 33.381283, 10.617748);
((GeneralPath)shape).lineTo(30.497072, 8.307271);
((GeneralPath)shape).lineTo(30.506163, 4.5527477);
((GeneralPath)shape).curveTo(30.506163, 3.7301333, 29.81436, 2.6194692, 28.807358, 2.6194692);
((GeneralPath)shape).lineTo(20.287313, 2.5310807);
((GeneralPath)shape).curveTo(19.074886, 2.5310807, 18.491032, 3.719232, 18.491032, 4.5321975);
((GeneralPath)shape).lineTo(18.491032, 8.422399);
((GeneralPath)shape).lineTo(15.890165, 10.665036);
((GeneralPath)shape).curveTo(15.890165, 10.665036, 15.838132, 12.462638, 16.722015, 12.506832);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_5);
g.setComposite(AlphaComposite.getInstance(3, 0.464706f * origAlpha));
AffineTransform defaultTransform__0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_6
paint = new Color(255, 255, 255, 255);
shape = new RoundRectangle2D.Double(17.0, 10.0, 15.0, 1.0416321754455566, 1.0416321754455566, 1.0416321754455566);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_6);
g.setComposite(AlphaComposite.getInstance(3, 0.535294f * origAlpha));
AffineTransform defaultTransform__0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7
paint = new Color(255, 255, 255, 255);
shape = new RoundRectangle2D.Double(20.0, 4.0, 9.0, 1.0416321754455566, 1.0416321754455566, 1.0416321754455566);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_7);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -64.0f, 1.34660005569458f));
// _0_0_8
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8_0 = g.getTransform();
g.transform(new AffineTransform(0.5454549789428711f, 0.0f, 0.0f, 0.5454549789428711f, 106.45899963378906f, 17.135099411010742f));
// _0_0_8_0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8_0_0 = g.getTransform();
g.transform(new AffineTransform(1.100000023841858f, 0.0f, 0.0f, 1.0f, -43.741600036621094f, 5.918960094451904f));
// _0_0_8_0_0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 0.5555559992790222f, -4.54999991461591E-7f, 13.888899803161621f));
// _0_0_8_0_0_0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8_0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_8_0_0_0_0
g.setComposite(AlphaComposite.getInstance(3, 0.4f * origAlpha));
AffineTransform defaultTransform__0_0_8_0_0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0526299476623535f, 0.0f, 0.0f, 1.2857099771499634f, -1.263159990310669f, -13.428500175476074f));
// _0_0_8_0_0_0_0_0
g.setTransform(defaultTransform__0_0_8_0_0_0_0_0);
g.setTransform(defaultTransform__0_0_8_0_0_0_0);
g.setTransform(defaultTransform__0_0_8_0_0_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8_0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 50.68870162963867f, 6.214990139007568f));
// _0_0_8_0_0_1
g.setTransform(defaultTransform__0_0_8_0_0_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8_0_0_2 = g.getTransform();
g.transform(new AffineTransform(0.18670299649238586f, 0.0f, 0.0f, 0.18670299649238586f, 29.58139991760254f, 63.8380012512207f));
// _0_0_8_0_0_2
g.setTransform(defaultTransform__0_0_8_0_0_2);
g.setTransform(defaultTransform__0_0_8_0_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_8_0_1
g.setTransform(defaultTransform__0_0_8_0_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_8_0_2
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8_0_2_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_8_0_2_0
paint = new LinearGradientPaint(new Point2D.Double(37.9266357421875, 37.6689338684082), new Point2D.Double(5.495975494384766, 10.982666015625), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(221, 221, 221, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9716100096702576f, 0.0f, 0.0f, 1.283329963684082f, -40.47809982299805f, -6.131050109863281f));
shape = new RoundRectangle2D.Double(-36.59162139892578, 5.418942451477051, 38.4999885559082, 38.4999885559082, 5.248562335968018, 5.248562812805176);
g.setPaint(paint);
g.fill(shape);
paint = new Color(147, 147, 147, 255);
stroke = new BasicStroke(1.83333f,0,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(-36.59162139892578, 5.418942451477051, 38.4999885559082, 38.4999885559082, 5.248562335968018, 5.248562812805176);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_8_0_2_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8_0_2_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_8_0_2_1
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(1.83333f,0,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(-34.7582893371582, 7.252274036407471, 34.83332443237305, 34.83332443237305, 2.1003313064575195, 2.1003308296203613);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_8_0_2_1);
g.setTransform(defaultTransform__0_0_8_0_2);
g.setTransform(defaultTransform__0_0_8_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_8_1
paint = new Color(173, 174, 171, 255);
shape = new Rectangle2D.Double(89.00000762939453, 23.590904235839844, 16.0, 2.062500476837158);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_8_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_8_2
paint = new Color(173, 174, 171, 255);
shape = new Rectangle2D.Double(89.00000762939453, 27.590904235839844, 16.0, 2.062500476837158);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_8_2);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_8_3
paint = new Color(173, 174, 171, 255);
shape = new Rectangle2D.Double(89.00000762939453, 31.646360397338867, 16.0, 2.0070443153381348);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_8_3);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_8_4
paint = new Color(173, 174, 171, 255);
shape = new Rectangle2D.Double(89.00000762939453, 35.590904235839844, 9.000000953674316, 2.062500476837158);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_8_4);
g.setTransform(defaultTransform__0_0_8);
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
        return 4;
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
        return 44;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * 
     * @return The height of the bounding box of the original SVG image.
     */
    public  int getOrigHeight() {
        return 43;
    }
}

