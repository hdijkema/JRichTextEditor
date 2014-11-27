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
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;


/**
 * This class has been automatically generated using <a
 * href="https://flamingo.dev.java.net">Flamingo SVG transcoder</a>.
 */
public class Format_indent_more implements FlamencoIconAdapter {
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
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 120.0f, 0.0f));
// _0_0_0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0
paint = new RadialGradientPaint(new Point2D.Double(10.125, 5.710937976837158), 21.0f, new Point2D.Double(10.125, 5.710937976837158), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(85, 87, 83, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.6369048357009888f, 0.0f, 0.0f, 1.247165560722351f, -125.44866180419922f, 1.752515196800232f));
shape = new RoundRectangle2D.Double(-118.5, 3.5, 24.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0f,1,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(-118.5, 3.5, 24.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1
paint = new RadialGradientPaint(new Point2D.Double(10.125, 5.710937976837158), 21.0f, new Point2D.Double(10.125, 5.710937976837158), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(85, 87, 83, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.9525262117385864f, 0.0f, 0.0f, 1.4876389503479004f, -130.6443328857422f, 0.37918639183044434f));
shape = new RoundRectangle2D.Double(-106.5, 10.5, 33.0, 3.0, 3.0090909004211426, 2.84375);
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0f,1,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(-106.5, 10.5, 33.0, 3.0, 3.0090909004211426, 2.84375);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_2
paint = new RadialGradientPaint(new Point2D.Double(10.125, 5.710937976837158), 21.0f, new Point2D.Double(10.125, 5.710937976837158), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(85, 87, 83, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.6369048357009888f, 0.0f, 0.0f, 1.247165560722351f, -127.44866180419922f, 1.752515196800232f));
shape = new RoundRectangle2D.Double(-106.5, 17.5, 24.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0f,1,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(-106.5, 17.5, 24.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_2);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_3
paint = new RadialGradientPaint(new Point2D.Double(10.125, 5.710937976837158), 21.0f, new Point2D.Double(10.125, 5.710937976837158), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(85, 87, 83, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.6369048357009888f, 0.0f, 0.0f, 1.247165560722351f, -127.44866180419922f, 1.752515196800232f));
shape = new RoundRectangle2D.Double(-106.5, 24.5, 28.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0f,1,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(-106.5, 24.5, 28.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_3);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_4
paint = new RadialGradientPaint(new Point2D.Double(10.125, 5.710937976837158), 21.0f, new Point2D.Double(10.125, 5.710937976837158), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(85, 87, 83, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.6369048357009888f, 0.0f, 0.0f, 1.247165560722351f, -125.44866180419922f, 1.752515196800232f));
shape = new RoundRectangle2D.Double(-118.5, 31.5, 17.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0f,1,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(-118.5, 31.5, 17.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_4);
g.setComposite(AlphaComposite.getInstance(3, 0.55f * origAlpha));
AffineTransform defaultTransform__0_0_0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_5
paint = new RadialGradientPaint(new Point2D.Double(13.52646541595459, -26.50746726989746), 18.000013f, new Point2D.Double(13.52646541595459, -26.50746726989746), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0002598762512207f, 0.0f, 0.0f, 0.2226119041442871f, -135.61892700195312f, 8.644060134887695f));
stroke = new BasicStroke(0.99999994f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(-117.5, 4.995455);
((GeneralPath)shape).curveTo(-117.5, 4.715937, -117.27497, 4.49091, -116.99546, 4.49091);
((GeneralPath)shape).lineTo(-96.00452, 4.49091);
((GeneralPath)shape).curveTo(-95.725, 4.49091, -95.49997, 4.715937, -95.49997, 4.995455);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_5);
g.setComposite(AlphaComposite.getInstance(3, 0.55f * origAlpha));
AffineTransform defaultTransform__0_0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_6
paint = new RadialGradientPaint(new Point2D.Double(13.52646541595459, -26.50746726989746), 18.000013f, new Point2D.Double(13.52646541595459, -26.50746726989746), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0002598762512207f, 0.0f, 0.0f, 0.2226119041442871f, -126.61892700195312f, 15.644060134887695f));
stroke = new BasicStroke(0.99999994f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(-105.5, 11.995455);
((GeneralPath)shape).curveTo(-105.5, 11.715937, -105.27497, 11.49091, -104.99546, 11.49091);
((GeneralPath)shape).lineTo(-75.00452, 11.49091);
((GeneralPath)shape).curveTo(-74.725, 11.49091, -74.49997, 11.715937, -74.49997, 11.995455);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_6);
g.setComposite(AlphaComposite.getInstance(3, 0.55f * origAlpha));
AffineTransform defaultTransform__0_0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_7
paint = new RadialGradientPaint(new Point2D.Double(13.52646541595459, -26.50746726989746), 18.000013f, new Point2D.Double(13.52646541595459, -26.50746726989746), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0002598762512207f, 0.0f, 0.0f, 0.2226119041442871f, -126.61892700195312f, 22.644060134887695f));
stroke = new BasicStroke(0.99999994f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(-105.5, 18.995455);
((GeneralPath)shape).curveTo(-105.5, 18.715937, -105.27497, 18.49091, -104.99546, 18.49091);
((GeneralPath)shape).lineTo(-84.00452, 18.49091);
((GeneralPath)shape).curveTo(-83.725, 18.49091, -83.49997, 18.715937, -83.49997, 18.995455);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_7);
g.setComposite(AlphaComposite.getInstance(3, 0.55f * origAlpha));
AffineTransform defaultTransform__0_0_0_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_8
paint = new RadialGradientPaint(new Point2D.Double(13.52646541595459, -26.50746726989746), 18.000013f, new Point2D.Double(13.52646541595459, -26.50746726989746), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0002598762512207f, 0.0f, 0.0f, 0.2226119041442871f, -126.61892700195312f, 29.644060134887695f));
stroke = new BasicStroke(0.99999994f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(-105.5, 25.995455);
((GeneralPath)shape).curveTo(-105.5, 25.715937, -105.27497, 25.49091, -104.99546, 25.49091);
((GeneralPath)shape).lineTo(-80.00452, 25.49091);
((GeneralPath)shape).curveTo(-79.725, 25.49091, -79.49997, 25.715937, -79.49997, 25.995455);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_8);
g.setComposite(AlphaComposite.getInstance(3, 0.55f * origAlpha));
AffineTransform defaultTransform__0_0_0_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_9
paint = new RadialGradientPaint(new Point2D.Double(13.52646541595459, -26.50746726989746), 18.000013f, new Point2D.Double(13.52646541595459, -26.50746726989746), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0002598762512207f, 0.0f, 0.0f, 0.2226119041442871f, -135.61892700195312f, 36.64405822753906f));
stroke = new BasicStroke(0.99999994f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(-117.5, 32.995457);
((GeneralPath)shape).curveTo(-117.5, 32.71594, -117.27497, 32.49091, -116.99546, 32.49091);
((GeneralPath)shape).lineTo(-103.00452, 32.49091);
((GeneralPath)shape).curveTo(-102.725, 32.49091, -102.49997, 32.71594, -102.49997, 32.995457);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_9);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_10 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_10
paint = new RadialGradientPaint(new Point2D.Double(10.125, 5.710937976837158), 21.0f, new Point2D.Double(10.125, 5.710937976837158), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(85, 87, 83, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.6369048357009888f, 0.0f, 0.0f, 1.247165560722351f, -125.44866180419922f, 1.752515196800232f));
shape = new RoundRectangle2D.Double(-118.5, 38.5, 24.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0f,1,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(-118.5, 38.5, 24.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_10);
g.setComposite(AlphaComposite.getInstance(3, 0.55f * origAlpha));
AffineTransform defaultTransform__0_0_0_11 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_11
paint = new RadialGradientPaint(new Point2D.Double(13.52646541595459, -26.50746726989746), 18.000013f, new Point2D.Double(13.52646541595459, -26.50746726989746), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0002598762512207f, 0.0f, 0.0f, 0.2226119041442871f, -135.61892700195312f, 43.64405822753906f));
stroke = new BasicStroke(0.99999994f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(-117.5, 39.995457);
((GeneralPath)shape).curveTo(-117.5, 39.71594, -117.27497, 39.49091, -116.99546, 39.49091);
((GeneralPath)shape).lineTo(-96.00452, 39.49091);
((GeneralPath)shape).curveTo(-95.725, 39.49091, -95.49997, 39.71594, -95.49997, 39.995457);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_11);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_12 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_12
paint = new RadialGradientPaint(new Point2D.Double(3.750610828399658, 20.101964950561523), 4.4999995f, new Point2D.Double(3.750610828399658, 20.101964950561523), new float[] {0.0f,1.0f}, new Color[] {new Color(252, 175, 62, 255),new Color(245, 121, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.835355758666992f, 0.0f, 0.0f, 3.780474901199341f, -127.34142303466797f, -55.92618942260742f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(-118.5, 13.500001);
((GeneralPath)shape).lineTo(-110.5, 19.000002);
((GeneralPath)shape).lineTo(-118.5, 24.500002);
((GeneralPath)shape).lineTo(-118.5, 13.500001);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(206, 92, 0, 255);
stroke = new BasicStroke(0.99999994f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(-118.5, 13.500001);
((GeneralPath)shape).lineTo(-110.5, 19.000002);
((GeneralPath)shape).lineTo(-118.5, 24.500002);
((GeneralPath)shape).lineTo(-118.5, 13.500001);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_12);
g.setComposite(AlphaComposite.getInstance(3, 0.6f * origAlpha));
AffineTransform defaultTransform__0_0_0_13 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -120.0f, 0.0f));
// _0_0_0_13
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(0.99999994f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(2.5, 15.40625);
((GeneralPath)shape).lineTo(2.5, 22.59375);
((GeneralPath)shape).lineTo(7.71875, 19.0);
((GeneralPath)shape).lineTo(2.5, 15.40625);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_13);
g.setTransform(defaultTransform__0_0_0);
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
        return 47;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * 
     * @return The height of the bounding box of the original SVG image.
     */
    public  int getOrigHeight() {
        return 39;
    }
}

