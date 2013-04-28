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
public class Edit_copy implements FlamencoIconAdapter {
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
g.setComposite(AlphaComposite.getInstance(3, 0.08522728f * origAlpha));
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0
paint = new Color(0, 0, 0, 255);
shape = new Rectangle2D.Double(20.14221954345703, 33.991329193115234, 13.019603729248047, 2.0012319087982178);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0);
g.setComposite(AlphaComposite.getInstance(3, 0.5f * origAlpha));
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1
paint = new LinearGradientPaint(new Point2D.Double(22.308330535888672, 18.99213981628418), new Point2D.Double(35.78529357910156, 39.49823760986328), new float[] {0.0f,0.59928656f,0.82758623f,1.0f}, new Color[] {new Color(240, 240, 239, 255),new Color(232, 232, 232, 255),new Color(255, 255, 255, 255),new Color(216, 216, 211, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.067305088043213f, 0.0f, 0.0f, 0.9882033467292786f, -8.61123275756836f, -4.957775115966797f));
shape = new RoundRectangle2D.Double(1.5007537603378296, 1.500844120979309, 30.998233795166016, 35.99885177612305, 1.1330162286758423, 1.1330167055130005);
g.setPaint(paint);
g.fill(shape);
paint = new Color(26, 26, 26, 255);
stroke = new BasicStroke(1.0000001f,0,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(1.5007537603378296, 1.500844120979309, 30.998233795166016, 35.99885177612305, 1.1330162286758423, 1.1330167055130005);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_1);
g.setComposite(AlphaComposite.getInstance(3, 0.5f * origAlpha));
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2
paint = new LinearGradientPaint(new Point2D.Double(26.076091766357422, 26.69667625427246), new Point2D.Double(30.811172485351562, 42.00735092163086), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0000096559524536f, 0.0f, 0.0f, 0.9988609552383423f, -7.030924320220947f, -4.958963871002197f));
stroke = new BasicStroke(1.0000004f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(2.486309766769409, 2.4991238117218018, 29.014429092407227, 34.00198745727539);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_2);
g.setComposite(AlphaComposite.getInstance(3, 0.24073175f * origAlpha));
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3
paint = new Color(0, 0, 0, 255);
shape = new Rectangle2D.Double(6.976677417755127, 9.976545333862305, 21.031667709350586, 2.0012319087982178);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_3);
g.setComposite(AlphaComposite.getInstance(3, 0.24073175f * origAlpha));
AffineTransform defaultTransform__0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4
paint = new Color(0, 0, 0, 255);
shape = new Rectangle2D.Double(6.976677417755127, 13.979009628295898, 20.030160903930664, 2.0012319087982178);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_4);
g.setComposite(AlphaComposite.getInstance(3, 0.24073175f * origAlpha));
AffineTransform defaultTransform__0_0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_5
paint = new Color(0, 0, 0, 255);
shape = new Rectangle2D.Double(6.976677417755127, 17.981473922729492, 18.027143478393555, 2.0012319087982178);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_5);
g.setComposite(AlphaComposite.getInstance(3, 0.24073175f * origAlpha));
AffineTransform defaultTransform__0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_6
paint = new Color(0, 0, 0, 255);
shape = new Rectangle2D.Double(6.976677417755127, 21.983938217163086, 21.031667709350586, 2.0012319087982178);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_6);
g.setComposite(AlphaComposite.getInstance(3, 0.24073175f * origAlpha));
AffineTransform defaultTransform__0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7
paint = new Color(0, 0, 0, 255);
shape = new Rectangle2D.Double(6.976677417755127, 25.986400604248047, 13.019603729248047, 2.0012319087982178);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_7);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_8
paint = new LinearGradientPaint(new Point2D.Double(22.308330535888672, 18.99213981628418), new Point2D.Double(35.78529357910156, 39.49823760986328), new float[] {0.0f,0.59928656f,0.82758623f,1.0f}, new Color[] {new Color(240, 240, 239, 255),new Color(232, 232, 232, 255),new Color(255, 255, 255, 255),new Color(216, 216, 211, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0672359466552734f, 0.0f, 0.0f, 0.9892759919166565f, 4.391684055328369f, 4.035226821899414f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(15.072946, 10.500852);
((GeneralPath)shape).lineTo(44.92933, 10.500852);
((GeneralPath)shape).curveTo(45.245068, 10.500852, 45.499256, 10.753944, 45.499256, 11.068323);
((GeneralPath)shape).lineTo(45.499256, 38.235687);
((GeneralPath)shape).curveTo(45.499256, 40.71214, 38.619446, 46.538773, 36.231323, 46.538773);
((GeneralPath)shape).lineTo(15.072945, 46.538773);
((GeneralPath)shape).curveTo(14.757205, 46.538773, 14.503018, 46.28568, 14.503018, 45.9713);
((GeneralPath)shape).lineTo(14.503018, 11.068321);
((GeneralPath)shape).curveTo(14.503018, 10.7539425, 14.757204, 10.500849, 15.072945, 10.500849);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0000002f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(15.072946, 10.500852);
((GeneralPath)shape).lineTo(44.92933, 10.500852);
((GeneralPath)shape).curveTo(45.245068, 10.500852, 45.499256, 10.753944, 45.499256, 11.068323);
((GeneralPath)shape).lineTo(45.499256, 38.235687);
((GeneralPath)shape).curveTo(45.499256, 40.71214, 38.619446, 46.538773, 36.231323, 46.538773);
((GeneralPath)shape).lineTo(15.072945, 46.538773);
((GeneralPath)shape).curveTo(14.757205, 46.538773, 14.503018, 46.28568, 14.503018, 45.9713);
((GeneralPath)shape).lineTo(14.503018, 11.068321);
((GeneralPath)shape).curveTo(14.503018, 10.7539425, 14.757204, 10.500849, 15.072945, 10.500849);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_8);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_9
paint = new LinearGradientPaint(new Point2D.Double(26.076091766357422, 26.69667625427246), new Point2D.Double(30.811172485351562, 42.00735092163086), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9994210004806519f, 0.0f, 0.0f, 1.0f, 5.991319179534912f, 4.033411026000977f));
stroke = new BasicStroke(1.0000008f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(15.502950668334961, 11.5, 28.99734878540039, 34.04076385498047);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_9);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_10 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_10
paint = new LinearGradientPaint(new Point2D.Double(35.99658203125, 40.458221435546875), new Point2D.Double(33.664920806884766, 37.770721435546875), new float[] {0.0f,1.0f}, new Color[] {new Color(124, 124, 124, 255),new Color(184, 184, 184, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 6.16183614730835f, 4.033411026000977f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(36.220917, 46.536964);
((GeneralPath)shape).curveTo(38.251335, 46.866863, 45.809708, 42.007034, 45.50533, 38.03912);
((GeneralPath)shape).curveTo(43.942066, 40.462215, 40.746807, 39.32586, 36.63805, 39.484867);
((GeneralPath)shape).curveTo(36.63805, 39.484867, 37.03342, 46.036964, 36.22092, 46.536964);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0000002f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(36.220917, 46.536964);
((GeneralPath)shape).curveTo(38.251335, 46.866863, 45.809708, 42.007034, 45.50533, 38.03912);
((GeneralPath)shape).curveTo(43.942066, 40.462215, 40.746807, 39.32586, 36.63805, 39.484867);
((GeneralPath)shape).curveTo(36.63805, 39.484867, 37.03342, 46.036964, 36.22092, 46.536964);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_10);
g.setComposite(AlphaComposite.getInstance(3, 0.36931816f * origAlpha));
AffineTransform defaultTransform__0_0_11 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_11
paint = new LinearGradientPaint(new Point2D.Double(33.39600372314453, 36.92133331298828), new Point2D.Double(34.170047760009766, 38.07038116455078), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 6.16183614730835f, 3.6584110260009766f));
stroke = new BasicStroke(0.9999998f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(37.671356, 44.345463);
((GeneralPath)shape).curveTo(39.041134, 43.661633, 42.099606, 42.198997, 43.398987, 40.317993);
((GeneralPath)shape).curveTo(41.802895, 40.998047, 40.451176, 40.52749, 37.69665, 40.5084);
((GeneralPath)shape).curveTo(37.69665, 40.5084, 37.858974, 43.570496, 37.671352, 44.345463);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_11);
g.setComposite(AlphaComposite.getInstance(3, 0.37979093f * origAlpha));
AffineTransform defaultTransform__0_0_12 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_12
paint = new Color(0, 0, 0, 255);
shape = new Rectangle2D.Double(20.0, 19.033414840698242, 21.0, 2.0);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_12);
g.setComposite(AlphaComposite.getInstance(3, 0.37979093f * origAlpha));
AffineTransform defaultTransform__0_0_13 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_13
paint = new Color(0, 0, 0, 255);
shape = new Rectangle2D.Double(20.0, 23.033414840698242, 19.992233276367188, 2.0);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_13);
g.setComposite(AlphaComposite.getInstance(3, 0.37979093f * origAlpha));
AffineTransform defaultTransform__0_0_14 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_14
paint = new Color(0, 0, 0, 255);
shape = new Rectangle2D.Double(20.0, 27.033414840698242, 17.976701736450195, 2.0);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_14);
g.setComposite(AlphaComposite.getInstance(3, 0.37979093f * origAlpha));
AffineTransform defaultTransform__0_0_15 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_15
paint = new Color(0, 0, 0, 255);
shape = new Rectangle2D.Double(20.0, 31.033414840698242, 21.0, 2.0);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_15);
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
        return 2;
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
        return 46;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * 
     * @return The height of the bounding box of the original SVG image.
     */
    public  int getOrigHeight() {
        return 47;
    }
}

