/* ******************************************************************************
 *
 *       Copyright 2008-2010 Hans Oesterholt-Dijkema
 *       This file is part of the JDesktop SwingX library
 *       and part of the SwingLabs project
 *
 *   SwingX is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as 
 *   published by the Free Software Foundation, either version 3 of 
 *   the License, or (at your option) any later version.
 *
 *   SwingX is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with SwingX.  If not, see <http://www.gnu.org/licenses/>.
 *   
 * ******************************************************************************/

package net.oesterholt.jxmlnote.icons;

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
public class Format_indent_less implements FlamencoIconAdapter {
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0
paint = new RadialGradientPaint(new Point2D.Double(10.125, 5.710937976837158), 21.0f, new Point2D.Double(10.125, 5.710937976837158), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(85, 87, 83, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.6369048357009888f, 0.0f, 0.0f, 1.247165560722351f, -7.448660850524902f, 1.752515196800232f));
shape = new RoundRectangle2D.Double(22.5, 3.5, 24.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0f,1,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(22.5, 3.5, 24.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1
paint = new RadialGradientPaint(new Point2D.Double(10.125, 5.710937976837158), 21.0f, new Point2D.Double(10.125, 5.710937976837158), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(85, 87, 83, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.9525262117385864f, 0.0f, 0.0f, 1.4876389503479004f, -8.644327163696289f, 0.37918639183044434f));
shape = new RoundRectangle2D.Double(1.5, 10.5, 33.0, 3.0, 3.0090909004211426, 2.84375);
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0f,1,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(1.5, 10.5, 33.0, 3.0, 3.0090909004211426, 2.84375);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_2
paint = new RadialGradientPaint(new Point2D.Double(10.125, 5.710937976837158), 21.0f, new Point2D.Double(10.125, 5.710937976837158), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(85, 87, 83, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.6369048357009888f, 0.0f, 0.0f, 1.247165560722351f, -5.448660850524902f, 1.752515196800232f));
shape = new RoundRectangle2D.Double(1.5, 17.5, 24.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0f,1,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(1.5, 17.5, 24.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_2);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_3
paint = new RadialGradientPaint(new Point2D.Double(10.125, 5.710937976837158), 21.0f, new Point2D.Double(10.125, 5.710937976837158), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(85, 87, 83, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.6369048357009888f, 0.0f, 0.0f, 1.247165560722351f, -5.448660850524902f, 1.752515196800232f));
shape = new RoundRectangle2D.Double(1.5, 24.5, 28.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0f,1,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(1.5, 24.5, 28.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_3);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_4
paint = new RadialGradientPaint(new Point2D.Double(10.125, 5.710937976837158), 21.0f, new Point2D.Double(10.125, 5.710937976837158), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(85, 87, 83, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.6369048357009888f, 0.0f, 0.0f, 1.247165560722351f, -7.448660850524902f, 1.752515196800232f));
shape = new RoundRectangle2D.Double(22.5, 31.5, 17.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0f,1,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(22.5, 31.5, 17.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_4);
g.setComposite(AlphaComposite.getInstance(3, 0.55f * origAlpha));
AffineTransform defaultTransform__0_0_0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_5
paint = new RadialGradientPaint(new Point2D.Double(13.52646541595459, -26.50746726989746), 18.000013f, new Point2D.Double(13.52646541595459, -26.50746726989746), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0002598762512207f, 0.0f, 0.0f, 0.2226119041442871f, 5.381068229675293f, 8.644060134887695f));
stroke = new BasicStroke(0.99999994f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.5, 4.995455);
((GeneralPath)shape).curveTo(23.5, 4.715937, 23.725027, 4.49091, 24.004545, 4.49091);
((GeneralPath)shape).lineTo(44.995483, 4.49091);
((GeneralPath)shape).curveTo(45.275, 4.49091, 45.500027, 4.715937, 45.500027, 4.995455);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_5);
g.setComposite(AlphaComposite.getInstance(3, 0.55f * origAlpha));
AffineTransform defaultTransform__0_0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_6
paint = new RadialGradientPaint(new Point2D.Double(13.52646541595459, -26.50746726989746), 18.000013f, new Point2D.Double(13.52646541595459, -26.50746726989746), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0002598762512207f, 0.0f, 0.0f, 0.2226119041442871f, -18.618932723999023f, 15.644060134887695f));
stroke = new BasicStroke(0.99999994f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(2.5, 11.995455);
((GeneralPath)shape).curveTo(2.5, 11.715937, 2.725027, 11.49091, 3.004545, 11.49091);
((GeneralPath)shape).lineTo(32.995483, 11.49091);
((GeneralPath)shape).curveTo(33.275, 11.49091, 33.500027, 11.715937, 33.500027, 11.995455);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_6);
g.setComposite(AlphaComposite.getInstance(3, 0.55f * origAlpha));
AffineTransform defaultTransform__0_0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_7
paint = new RadialGradientPaint(new Point2D.Double(13.52646541595459, -26.50746726989746), 18.000013f, new Point2D.Double(13.52646541595459, -26.50746726989746), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0002598762512207f, 0.0f, 0.0f, 0.2226119041442871f, -18.618932723999023f, 22.644060134887695f));
stroke = new BasicStroke(0.99999994f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(2.5, 18.995455);
((GeneralPath)shape).curveTo(2.5, 18.715937, 2.725027, 18.49091, 3.004545, 18.49091);
((GeneralPath)shape).lineTo(23.995481, 18.49091);
((GeneralPath)shape).curveTo(24.275, 18.49091, 24.500027, 18.715937, 24.500027, 18.995455);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_7);
g.setComposite(AlphaComposite.getInstance(3, 0.55f * origAlpha));
AffineTransform defaultTransform__0_0_0_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_8
paint = new RadialGradientPaint(new Point2D.Double(13.52646541595459, -26.50746726989746), 18.000013f, new Point2D.Double(13.52646541595459, -26.50746726989746), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0002598762512207f, 0.0f, 0.0f, 0.2226119041442871f, -18.618932723999023f, 29.644060134887695f));
stroke = new BasicStroke(0.99999994f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(2.5, 25.995455);
((GeneralPath)shape).curveTo(2.5, 25.715937, 2.725027, 25.49091, 3.004545, 25.49091);
((GeneralPath)shape).lineTo(27.995481, 25.49091);
((GeneralPath)shape).curveTo(28.275, 25.49091, 28.500027, 25.715937, 28.500027, 25.995455);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_8);
g.setComposite(AlphaComposite.getInstance(3, 0.55f * origAlpha));
AffineTransform defaultTransform__0_0_0_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_9
paint = new RadialGradientPaint(new Point2D.Double(13.52646541595459, -26.50746726989746), 18.000013f, new Point2D.Double(13.52646541595459, -26.50746726989746), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0002598762512207f, 0.0f, 0.0f, 0.2226119041442871f, 5.381068229675293f, 36.64405822753906f));
stroke = new BasicStroke(0.99999994f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.5, 32.995457);
((GeneralPath)shape).curveTo(23.5, 32.71594, 23.725027, 32.49091, 24.004545, 32.49091);
((GeneralPath)shape).lineTo(37.995483, 32.49091);
((GeneralPath)shape).curveTo(38.275, 32.49091, 38.500027, 32.71594, 38.500027, 32.995457);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_9);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_10 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_10
paint = new RadialGradientPaint(new Point2D.Double(10.125, 5.710937976837158), 21.0f, new Point2D.Double(10.125, 5.710937976837158), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(85, 87, 83, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.6369048357009888f, 0.0f, 0.0f, 1.247165560722351f, -7.448660850524902f, 1.752515196800232f));
shape = new RoundRectangle2D.Double(22.5, 38.5, 24.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0f,1,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(22.5, 38.5, 24.0, 3.0, 3.0090909004211426, 2.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_10);
g.setComposite(AlphaComposite.getInstance(3, 0.55f * origAlpha));
AffineTransform defaultTransform__0_0_0_11 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_11
paint = new RadialGradientPaint(new Point2D.Double(13.52646541595459, -26.50746726989746), 18.000013f, new Point2D.Double(13.52646541595459, -26.50746726989746), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0002598762512207f, 0.0f, 0.0f, 0.2226119041442871f, 5.381068229675293f, 43.64405822753906f));
stroke = new BasicStroke(0.99999994f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.5, 39.995457);
((GeneralPath)shape).curveTo(23.5, 39.71594, 23.725027, 39.49091, 24.004545, 39.49091);
((GeneralPath)shape).lineTo(44.995483, 39.49091);
((GeneralPath)shape).curveTo(45.275, 39.49091, 45.500027, 39.71594, 45.500027, 39.995457);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_11);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_12 = g.getTransform();
g.transform(new AffineTransform(-1.0f, 0.0f, 0.0f, 1.0f, 48.0f, 0.0f));
// _0_0_0_12
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_12_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_12_0
paint = new RadialGradientPaint(new Point2D.Double(3.750610828399658, 20.101964950561523), 4.4999995f, new Point2D.Double(3.750610828399658, 20.101964950561523), new float[] {0.0f,1.0f}, new Color[] {new Color(252, 175, 62, 255),new Color(245, 121, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-2.835355758666992f, 0.0f, 0.0f, 3.780474901199341f, 18.34142303466797f, -55.92618942260742f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(1.5000005, 13.500001);
((GeneralPath)shape).lineTo(9.5, 19.000002);
((GeneralPath)shape).lineTo(1.5000005, 24.500002);
((GeneralPath)shape).lineTo(1.5000005, 13.500001);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(206, 92, 0, 255);
stroke = new BasicStroke(0.99999994f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(1.5000005, 13.500001);
((GeneralPath)shape).lineTo(9.5, 19.000002);
((GeneralPath)shape).lineTo(1.5000005, 24.500002);
((GeneralPath)shape).lineTo(1.5000005, 13.500001);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_12_0);
g.setComposite(AlphaComposite.getInstance(3, 0.6f * origAlpha));
AffineTransform defaultTransform__0_0_0_12_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_12_1
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
g.setTransform(defaultTransform__0_0_0_12_1);
g.setTransform(defaultTransform__0_0_0_12);
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

