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

import java.awt.*;
import java.awt.geom.*;


/**
 * This class has been automatically generated using <a
 * href="https://flamingo.dev.java.net">Flamingo SVG transcoder</a>.
 */
public class AddImage implements FlamencoIconAdapter {
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
g.setComposite(AlphaComposite.getInstance(3, 0.7f * origAlpha));
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(0.9489185214042664f, 0.2856546938419342f, -0.2618500888347626f, 0.869841992855072f, 7.713850975036621f, -2.162029981613159f));
// _0_0
paint = new RadialGradientPaint(new Point2D.Double(24.200000762939453, 24.200000762939453), 24.2f, new Point2D.Double(24.200000762939453, 24.200000762939453), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(48.436813, 24.218407);
((GeneralPath)shape).curveTo(48.43953, 37.595783, 37.595783, 48.441734, 24.218407, 48.441734);
((GeneralPath)shape).curveTo(10.841028, 48.441734, -0.0027183052, 37.595783, 0.0, 24.218407);
((GeneralPath)shape).curveTo(-0.0027183052, 10.841029, 10.841028, -0.00492122, 24.218407, -0.00492122);
((GeneralPath)shape).curveTo(37.595783, -0.00492122, 48.43953, 10.841029, 48.436813, 24.218407);
((GeneralPath)shape).lineTo(48.436813, 24.218407);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1 = g.getTransform();
g.transform(new AffineTransform(0.964477002620697f, -0.26416680216789246f, 0.26416680216789246f, 0.964477002620697f, 21.29530906677246f, 86.05313873291016f));
// _0_1
paint = new Color(238, 238, 236, 255);
shape = new RoundRectangle2D.Double(1.5, -77.5, 38.0, 22.0, 1.9299999475479126, 1.7200000286102295);
g.setPaint(paint);
g.fill(shape);
paint = new Color(136, 138, 133, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(1.5, -77.5, 38.0, 22.0, 1.9299999475479126, 1.7200000286102295);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_2 = g.getTransform();
g.transform(new AffineTransform(0.964477002620697f, -0.26416680216789246f, 0.26416680216789246f, 0.964477002620697f, 21.29530906677246f, 86.05313873291016f));
// _0_2
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(2.5, -76.5, 36.0, 20.0);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_2);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_3 = g.getTransform();
g.transform(new AffineTransform(0.964477002620697f, -0.26416680216789246f, 0.26416680216789246f, 0.964477002620697f, 21.29530906677246f, 86.05313873291016f));
// _0_3
paint = new LinearGradientPaint(new Point2D.Double(21.799999237060547, -73.0), new Point2D.Double(21.799999237060547, -63.0), new float[] {0.0f,1.0f}, new Color[] {new Color(114, 159, 207, 255),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new Rectangle2D.Double(4.5, -74.5, 32.0, 16.0);
g.setPaint(paint);
g.fill(shape);
paint = new Color(114, 159, 207, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(4.5, -74.5, 32.0, 16.0);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_3);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_4 = g.getTransform();
g.transform(new AffineTransform(0.964477002620697f, -0.26416680216789246f, 0.26416680216789246f, 0.964477002620697f, 21.29530906677246f, 86.05313873291016f));
// _0_4
paint = new LinearGradientPaint(new Point2D.Double(12.0, -53.599998474121094), new Point2D.Double(12.0, -65.25), new float[] {0.0f,1.0f}, new Color[] {new Color(233, 185, 110, 255),new Color(101, 63, 1, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(5.0, -58.5);
((GeneralPath)shape).curveTo(6.437499, -66.0, 8.9476795, -63.298523, 11.1875, -69.6875);
((GeneralPath)shape).curveTo(12.500716, -68.915665, 12.986696, -69.42048, 14.0, -68.5625);
((GeneralPath)shape).curveTo(15.008661, -67.31016, 15.472316, -65.33115, 16.0, -63.4375);
((GeneralPath)shape).curveTo(18.902977, -68.0, 19.560827, -62.0, 23.1875, -61.0625);
((GeneralPath)shape).curveTo(27.0, -62.47312, 27.5, -71.0, 29.5, -70.0);
((GeneralPath)shape).curveTo(30.317656, -70.761635, 31.063694, -75.57773, 32.0, -72.0);
((GeneralPath)shape).curveTo(33.0, -68.0, 35.664333, -63.743, 36.5, -60.0);
((GeneralPath)shape).lineTo(36.5, -58.5);
((GeneralPath)shape).lineTo(5.0, -58.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(35.0, -59.279998779296875), new Point2D.Double(35.0, -60.5), new float[] {0.0f,1.0f}, new Color[] {new Color(143, 89, 2, 255),new Color(143, 89, 2, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(5.0, -58.5);
((GeneralPath)shape).curveTo(6.437499, -66.0, 8.9476795, -63.298523, 11.1875, -69.6875);
((GeneralPath)shape).curveTo(12.500716, -68.915665, 12.986696, -69.42048, 14.0, -68.5625);
((GeneralPath)shape).curveTo(15.008661, -67.31016, 15.472316, -65.33115, 16.0, -63.4375);
((GeneralPath)shape).curveTo(18.902977, -68.0, 19.560827, -62.0, 23.1875, -61.0625);
((GeneralPath)shape).curveTo(27.0, -62.47312, 27.5, -71.0, 29.5, -70.0);
((GeneralPath)shape).curveTo(30.317656, -70.761635, 31.063694, -75.57773, 32.0, -72.0);
((GeneralPath)shape).curveTo(33.0, -68.0, 35.664333, -63.743, 36.5, -60.0);
((GeneralPath)shape).lineTo(36.5, -58.5);
((GeneralPath)shape).lineTo(5.0, -58.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_4);
g.setComposite(AlphaComposite.getInstance(3, 0.525f * origAlpha));
AffineTransform defaultTransform__0_5 = g.getTransform();
g.transform(new AffineTransform(0.964477002620697f, -0.26416680216789246f, 0.26416680216789246f, 0.964477002620697f, 21.29530906677246f, 86.05313873291016f));
// _0_5
paint = new RadialGradientPaint(new Point2D.Double(7.099999904632568, -81.0), 15.5f, new Point2D.Double(7.099999904632568, -81.0), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.138821601867676f, 0.0f, 0.0f, 0.7813069820404053f, -10.156447410583496f, -10.683515548706055f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(5.0, -74.0);
((GeneralPath)shape).lineTo(36.0, -74.0);
((GeneralPath)shape).lineTo(36.0, -67.0);
((GeneralPath)shape).curveTo(19.658484, -70.06675, 9.583181, -65.2791, 5.0, -60.0);
((GeneralPath)shape).lineTo(5.0, -74.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_5);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_6 = g.getTransform();
g.transform(new AffineTransform(0.9659258127212524f, 0.25881901383399963f, -0.25881901383399963f, 0.9659258127212524f, -54.17770767211914f, 80.86833190917969f));
// _0_6
paint = new Color(238, 238, 236, 255);
shape = new RoundRectangle2D.Double(41.5, -77.5, 38.0, 22.0, 1.9299999475479126, 1.7200000286102295);
g.setPaint(paint);
g.fill(shape);
paint = new Color(136, 138, 133, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(41.5, -77.5, 38.0, 22.0, 1.9299999475479126, 1.7200000286102295);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_6);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_7 = g.getTransform();
g.transform(new AffineTransform(0.9659258127212524f, 0.25881901383399963f, -0.25881901383399963f, 0.9659258127212524f, -54.17770767211914f, 80.86833190917969f));
// _0_7
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(42.5, -76.5, 36.0, 20.0);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_7);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_8 = g.getTransform();
g.transform(new AffineTransform(0.9659258127212524f, 0.25881901383399963f, -0.25881901383399963f, 0.9659258127212524f, -54.17770767211914f, 80.86833190917969f));
// _0_8
paint = new LinearGradientPaint(new Point2D.Double(21.799999237060547, -73.30000305175781), new Point2D.Double(21.799999237060547, -63.0), new float[] {0.0f,1.0f}, new Color[] {new Color(114, 159, 207, 255),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 40.0f, 0.0f));
shape = new Rectangle2D.Double(44.5, -74.5, 32.0, 16.0);
g.setPaint(paint);
g.fill(shape);
paint = new Color(114, 159, 207, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(44.5, -74.5, 32.0, 16.0);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_8);
g.setComposite(AlphaComposite.getInstance(3, 0.5f * origAlpha));
AffineTransform defaultTransform__0_9 = g.getTransform();
g.transform(new AffineTransform(0.9659258127212524f, 0.25881901383399963f, -0.25881901383399963f, 0.9659258127212524f, -54.17770767211914f, 80.86833190917969f));
// _0_9
paint = new RadialGradientPaint(new Point2D.Double(7.099999904632568, -81.0), 15.5f, new Point2D.Double(7.099999904632568, -81.0), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.138821601867676f, 0.0f, 0.0f, 0.7813069820404053f, 29.84355354309082f, -10.683515548706055f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(45.0, -74.0);
((GeneralPath)shape).lineTo(76.0, -74.0);
((GeneralPath)shape).lineTo(76.0, -67.0);
((GeneralPath)shape).curveTo(59.658485, -70.06675, 49.583183, -65.2791, 45.0, -60.0);
((GeneralPath)shape).lineTo(45.0, -74.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_9);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_10 = g.getTransform();
g.transform(new AffineTransform(0.9659258127212524f, 0.25881901383399963f, -0.25881901383399963f, 0.9659258127212524f, -54.17770767211914f, 80.86833190917969f));
// _0_10
paint = new LinearGradientPaint(new Point2D.Double(66.375, -61.75), new Point2D.Double(65.875, -68.375), new float[] {0.0f,1.0f}, new Color[] {new Color(115, 210, 22, 255),new Color(115, 210, 22, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.5, -63.4375);
((GeneralPath)shape).curveTo(52.611107, -65.71528, 64.46847, -66.119934, 76.5, -66.4375);
((GeneralPath)shape).lineTo(76.5, -58.5);
((GeneralPath)shape).lineTo(44.5, -58.5);
((GeneralPath)shape).lineTo(44.5, -63.4375);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(62.375, -58.5), new Point2D.Double(61.5, -64.375), new float[] {0.0f,1.0f}, new Color[] {new Color(78, 154, 6, 255),new Color(78, 154, 6, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.5, -63.4375);
((GeneralPath)shape).curveTo(52.611107, -65.71528, 64.46847, -66.119934, 76.5, -66.4375);
((GeneralPath)shape).lineTo(76.5, -58.5);
((GeneralPath)shape).lineTo(44.5, -58.5);
((GeneralPath)shape).lineTo(44.5, -63.4375);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_10);
g.setComposite(AlphaComposite.getInstance(3, 0.2f * origAlpha));
AffineTransform defaultTransform__0_11 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_11
paint = new LinearGradientPaint(new Point2D.Double(86.5, 28.0), new Point2D.Double(79.0, 35.79999923706055), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -70.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(6.5, 16.46875);
((GeneralPath)shape).lineTo(8.46875, 35.40625);
((GeneralPath)shape).curveTo(8.594085, 36.6055, 9.718166, 37.36763, 10.84375, 37.25);
((GeneralPath)shape).lineTo(41.1875, 34.0625);
((GeneralPath)shape).lineTo(42.9375, 27.5625);
((GeneralPath)shape).curveTo(43.1398, 26.807507, 42.63292, 26.046833, 41.875, 25.84375);
((GeneralPath)shape).lineTo(7.03125, 16.53125);
((GeneralPath)shape).curveTo(6.852297, 16.4833, 6.676447, 16.453056, 6.5, 16.46875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_11);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_12 = g.getTransform();
g.transform(new AffineTransform(0.9945833086967468f, -0.10394249856472015f, 0.10394249856472015f, 0.9945833086967468f, 17.14148712158203f, 121.35810852050781f));
// _0_12
paint = new LinearGradientPaint(new Point2D.Double(33.0, -68.5999984741211), new Point2D.Double(12.3100004196167, -115.5999984741211), new float[] {0.0f,1.0f}, new Color[] {new Color(211, 215, 207, 255),new Color(238, 238, 236, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new RoundRectangle2D.Double(1.5, -107.5, 38.0, 22.0, 1.940000057220459, 1.7200000286102295);
g.setPaint(paint);
g.fill(shape);
paint = new Color(136, 138, 133, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(1.5, -107.5, 38.0, 22.0, 1.940000057220459, 1.7200000286102295);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_12);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_13 = g.getTransform();
g.transform(new AffineTransform(0.9945833086967468f, -0.10394249856472015f, 0.10394249856472015f, 0.9945833086967468f, 17.14148712158203f, 121.35810852050781f));
// _0_13
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(2.5, -106.5, 36.0, 20.0);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_13);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_14 = g.getTransform();
g.transform(new AffineTransform(0.9945833086967468f, -0.10394249856472015f, 0.10394249856472015f, 0.9945833086967468f, 17.14148712158203f, 121.35810852050781f));
// _0_14
paint = new LinearGradientPaint(new Point2D.Double(23.93000030517578, -104.1500015258789), new Point2D.Double(23.93000030517578, -94.36000061035156), new float[] {0.0f,1.0f}, new Color[] {new Color(245, 121, 0, 255),new Color(252, 233, 79, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new Rectangle2D.Double(4.5, -104.5, 32.0, 16.0);
g.setPaint(paint);
g.fill(shape);
paint = new Color(245, 121, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(4.5, -104.5, 32.0, 16.0);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_14);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_15 = g.getTransform();
g.transform(new AffineTransform(0.9945833086967468f, -0.10394249856472015f, 0.10394249856472015f, 0.9945833086967468f, 17.14148712158203f, 121.35810852050781f));
// _0_15
paint = new RadialGradientPaint(new Point2D.Double(22.0, -96.0), 4.0f, new Point2D.Double(22.0, -96.0), new float[] {0.0f,0.81f,0.84f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 255),new Color(253, 244, 167, 255),new Color(252, 233, 79, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -5.0f, 2.0f));
shape = new Rectangle2D.Double(13.0, -98.0, 8.0, 8.0);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_15);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_16 = g.getTransform();
g.transform(new AffineTransform(0.9945833086967468f, -0.10394249856472015f, 0.10394249856472015f, 0.9945833086967468f, 17.14148712158203f, 121.35810852050781f));
// _0_16
paint = new Color(0, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(4.5, -89.0);
((GeneralPath)shape).curveTo(6.2654963, -90.89532, 7.234162, -91.12136, 11.8125, -92.8125);
((GeneralPath)shape).curveTo(14.329963, -90.71539, 17.444279, -89.559204, 20.375, -91.25);
((GeneralPath)shape).curveTo(24.028078, -93.65671, 26.31287, -91.05881, 29.8125, -93.875);
((GeneralPath)shape).curveTo(31.411842, -92.979164, 32.56761, -92.020836, 36.5, -91.0);
((GeneralPath)shape).lineTo(36.5, -88.5);
((GeneralPath)shape).lineTo(4.5, -88.5);
((GeneralPath)shape).lineTo(4.5, -89.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(4.5, -89.0);
((GeneralPath)shape).curveTo(6.2654963, -90.89532, 7.234162, -91.12136, 11.8125, -92.8125);
((GeneralPath)shape).curveTo(14.329963, -90.71539, 17.444279, -89.559204, 20.375, -91.25);
((GeneralPath)shape).curveTo(24.028078, -93.65671, 26.31287, -91.05881, 29.8125, -93.875);
((GeneralPath)shape).curveTo(31.411842, -92.979164, 32.56761, -92.020836, 36.5, -91.0);
((GeneralPath)shape).lineTo(36.5, -88.5);
((GeneralPath)shape).lineTo(4.5, -88.5);
((GeneralPath)shape).lineTo(4.5, -89.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_16);
g.setComposite(AlphaComposite.getInstance(3, 0.75f * origAlpha));
AffineTransform defaultTransform__0_17 = g.getTransform();
g.transform(new AffineTransform(0.9945833086967468f, -0.10394249856472015f, 0.10394249856472015f, 0.9945833086967468f, 17.14148712158203f, 121.35810852050781f));
// _0_17
paint = new RadialGradientPaint(new Point2D.Double(6.670000076293945, -77.0), 15.5f, new Point2D.Double(6.670000076293945, -77.0), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.5831756591796875f, 0.0f, 0.0f, 0.9436286091804504f, -13.121665954589844f, -28.1901798248291f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(5.0, -104.0);
((GeneralPath)shape).lineTo(36.0, -104.0);
((GeneralPath)shape).lineTo(36.0, -97.0);
((GeneralPath)shape).curveTo(19.658484, -100.06675, 9.583181, -95.2791, 5.0, -90.0);
((GeneralPath)shape).lineTo(5.0, -104.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_17);
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
        return 0;
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
        return 48;
    }
}

