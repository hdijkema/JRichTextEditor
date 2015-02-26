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

import java.awt.*;
import java.awt.geom.*;

/**
 * This class has been automatically generated using <a
 * href="https://flamingo.dev.java.net">Flamingo SVG transcoder</a>.
 */
public class InsertLink implements FlamencoIconAdapter {
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
g.setComposite(AlphaComposite.getInstance(3, 0.24073175f * origAlpha));
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0
paint = new Color(255, 255, 255, 0);
shape = new RoundRectangle2D.Double(0.5, 0.5, 47.375, 47.25, 1.133015751838684, 1.1330164670944214);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1
g.setComposite(AlphaComposite.getInstance(3, 0.2f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.068703055381775f, 0.0f, 0.0f, 1.4117649793624878f, -2.073499917984009f, -27.205900192260742f));
// _0_0_0_1_0
paint = new RadialGradientPaint(new Point2D.Double(23.8125, 43.0), 16.375f, new Point2D.Double(23.8125, 43.0), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.19465599954128265f, 0.0f, 34.62976837158203f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(40.1875, 43.0);
((GeneralPath)shape).curveTo(40.1875, 44.760406, 32.856163, 46.1875, 23.8125, 46.1875);
((GeneralPath)shape).curveTo(14.768837, 46.1875, 7.4375, 44.760406, 7.4375, 43.0);
((GeneralPath)shape).curveTo(7.4375, 41.239594, 14.768837, 39.8125, 23.8125, 39.8125);
((GeneralPath)shape).curveTo(32.856163, 39.8125, 40.1875, 41.239594, 40.1875, 43.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_1_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_1
paint = new Color(0, 255, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(15.375002, 25.500004);
((GeneralPath)shape).lineTo(32.37501, 25.500004);
((GeneralPath)shape).lineTo(23.875008, 34.499996);
((GeneralPath)shape).lineTo(15.375003, 25.500004);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(17.25, 41.147342681884766), new Point2D.Double(17.25, 36.10343933105469), new float[] {0.0f,1.0f}, new Color[] {new Color(11, 162, 61, 255),new Color(25, 224, 64, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -0.125f, -10.0f));
stroke = new BasicStroke(1.0000005f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(15.375002, 25.500004);
((GeneralPath)shape).lineTo(32.37501, 25.500004);
((GeneralPath)shape).lineTo(23.875008, 34.499996);
((GeneralPath)shape).lineTo(15.375003, 25.500004);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_1);
g.setComposite(AlphaComposite.getInstance(3, 0.7f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -0.5f, -7.879499912261963f));
// _0_0_0_1_2
paint = new Color(0, 255, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.8125, 34.5);
((GeneralPath)shape).lineTo(24.0, 41.0625);
((GeneralPath)shape).lineTo(30.1875, 34.5);
((GeneralPath)shape).lineTo(17.8125, 34.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(18.875, 35.183414459228516), new Point2D.Double(18.875, 43.97861099243164), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.0f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.8125, 34.5);
((GeneralPath)shape).lineTo(24.0, 41.0625);
((GeneralPath)shape).lineTo(30.1875, 34.5);
((GeneralPath)shape).lineTo(17.8125, 34.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_2);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3 = g.getTransform();
g.transform(new AffineTransform(0.8727245926856995f, 0.0f, 0.0f, 1.0f, 2.992109775543213f, -10.0f));
// _0_0_0_1_3
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_0
paint = new LinearGradientPaint(new Point2D.Double(23.467105865478516, 20.20192527770996), new Point2D.Double(23.467105865478516, 17.015748977661133), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(238, 238, 236, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new Rectangle2D.Double(15.499966621398926, 16.499998092651367, 17.000036239624023, 5.000001430511475);
g.setPaint(paint);
g.fill(shape);
paint = new Color(85, 87, 83, 255);
stroke = new BasicStroke(0.9999995f,1,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(15.499966621398926, 16.499998092651367, 17.000036239624023, 5.000001430511475);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_3_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_1
paint = new LinearGradientPaint(new Point2D.Double(32.30957794189453, 15.875), new Point2D.Double(44.18773651123047, 15.875), new float[] {0.0f,1.0f}, new Color[] {new Color(85, 87, 83, 255),new Color(211, 215, 207, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.5, 22.5);
((GeneralPath)shape).curveTo(26.5, 25.5, 31.701962, 27.5, 33.4375, 27.5);
((GeneralPath)shape).lineTo(50.5, 27.5);
((GeneralPath)shape).lineTo(50.5, 22.5);
((GeneralPath)shape).lineTo(26.5, 22.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(37.12425231933594, 13.375), new Point2D.Double(47.5625, 13.375), new float[] {0.0f,1.0f}, new Color[] {new Color(85, 87, 83, 255),new Color(136, 138, 133, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.99999917f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.5, 22.5);
((GeneralPath)shape).curveTo(26.5, 25.5, 31.701962, 27.5, 33.4375, 27.5);
((GeneralPath)shape).lineTo(50.5, 27.5);
((GeneralPath)shape).lineTo(50.5, 22.5);
((GeneralPath)shape).lineTo(26.5, 22.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_3_1);
g.setComposite(AlphaComposite.getInstance(3, 0.7f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_2
paint = new LinearGradientPaint(new Point2D.Double(37.551124572753906, 26.433242797851562), new Point2D.Double(46.82101058959961, 26.433242797851562), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.99999917f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(49.5, 23.5);
((GeneralPath)shape).lineTo(34.90625, 23.5);
((GeneralPath)shape).curveTo(34.503277, 23.8241, 34.028915, 24.0, 33.53125, 24.0);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_3_2);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_3
paint = new Color(211, 215, 207, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.1875, 18.5);
((GeneralPath)shape).curveTo(10.768143, 19.213373, 10.5, 20.075256, 10.5, 21.0);
((GeneralPath)shape).curveTo(10.5, 23.492998, 12.266265, 25.5, 14.46875, 25.5);
((GeneralPath)shape).lineTo(33.53125, 25.5);
((GeneralPath)shape).curveTo(35.733734, 25.5, 37.5, 23.493, 37.5, 21.0);
((GeneralPath)shape).curveTo(37.5, 20.075256, 37.231857, 19.213373, 36.8125, 18.5);
((GeneralPath)shape).lineTo(34.0, 18.5);
((GeneralPath)shape).curveTo(32.788124, 18.5, 31.794853, 19.35874, 31.5625, 20.5);
((GeneralPath)shape).lineTo(30.0, 20.5);
((GeneralPath)shape).lineTo(25.53125, 20.5);
((GeneralPath)shape).lineTo(18.0, 20.5);
((GeneralPath)shape).curveTo(16.788124, 20.500002, 15.794854, 19.64126, 15.5625, 18.5);
((GeneralPath)shape).lineTo(11.1875, 18.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(136, 138, 133, 255);
stroke = new BasicStroke(0.9999991f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.1875, 18.5);
((GeneralPath)shape).curveTo(10.768143, 19.213373, 10.5, 20.075256, 10.5, 21.0);
((GeneralPath)shape).curveTo(10.5, 23.492998, 12.266265, 25.5, 14.46875, 25.5);
((GeneralPath)shape).lineTo(33.53125, 25.5);
((GeneralPath)shape).curveTo(35.733734, 25.5, 37.5, 23.493, 37.5, 21.0);
((GeneralPath)shape).curveTo(37.5, 20.075256, 37.231857, 19.213373, 36.8125, 18.5);
((GeneralPath)shape).lineTo(34.0, 18.5);
((GeneralPath)shape).curveTo(32.788124, 18.5, 31.794853, 19.35874, 31.5625, 20.5);
((GeneralPath)shape).lineTo(30.0, 20.5);
((GeneralPath)shape).lineTo(25.53125, 20.5);
((GeneralPath)shape).lineTo(18.0, 20.5);
((GeneralPath)shape).curveTo(16.788124, 20.500002, 15.794854, 19.64126, 15.5625, 18.5);
((GeneralPath)shape).lineTo(11.1875, 18.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_3_3);
g.setComposite(AlphaComposite.getInstance(3, 0.5f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_4
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.5, 17.5);
((GeneralPath)shape).lineTo(30.5, 17.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_3_4);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_5
paint = new LinearGradientPaint(new Point2D.Double(36.12220764160156, 15.875), new Point2D.Double(47.31273651123047, 15.875), new float[] {0.0f,1.0f}, new Color[] {new Color(186, 189, 182, 255),new Color(211, 215, 207, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.4375, 13.5);
((GeneralPath)shape).curveTo(30.326786, 13.5, 26.639368, 16.58704, 26.5, 20.5);
((GeneralPath)shape).lineTo(35.46875, 20.5);
((GeneralPath)shape).lineTo(35.46875, 18.5);
((GeneralPath)shape).lineTo(50.5, 18.5);
((GeneralPath)shape).lineTo(50.5, 13.5);
((GeneralPath)shape).lineTo(32.4375, 13.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(41.125, 13.375), new Point2D.Double(47.5625, 13.375), new float[] {0.0f,1.0f}, new Color[] {new Color(85, 87, 83, 255),new Color(136, 138, 133, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.99999917f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.4375, 13.5);
((GeneralPath)shape).curveTo(30.326786, 13.5, 26.639368, 16.58704, 26.5, 20.5);
((GeneralPath)shape).lineTo(35.46875, 20.5);
((GeneralPath)shape).lineTo(35.46875, 18.5);
((GeneralPath)shape).lineTo(50.5, 18.5);
((GeneralPath)shape).lineTo(50.5, 13.5);
((GeneralPath)shape).lineTo(32.4375, 13.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_3_5);
g.setComposite(AlphaComposite.getInstance(3, 0.8f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_6
paint = new RadialGradientPaint(new Point2D.Double(31.34980583190918, 14.914701461791992), 12.0f, new Point2D.Double(31.349794387817383, 14.914676666259766), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(238, 238, 236, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0271389484405518f, 7.04976173437899E-7f, -2.3416760086547583E-7f, 0.3411790132522583f, -0.8572689890861511f, 11.254770278930664f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.4375, 13.96875);
((GeneralPath)shape).curveTo(31.610666, 13.96875, 30.20175, 14.649802, 29.0625, 15.84375);
((GeneralPath)shape).curveTo(28.029016, 16.926853, 27.225348, 18.415245, 27.03125, 20.03125);
((GeneralPath)shape).lineTo(35.0, 20.03125);
((GeneralPath)shape).lineTo(35.0, 18.5);
((GeneralPath)shape).curveTo(35.000027, 18.241127, 35.209877, 18.031277, 35.46875, 18.03125);
((GeneralPath)shape).lineTo(50.03125, 18.03125);
((GeneralPath)shape).lineTo(50.03125, 13.96875);
((GeneralPath)shape).lineTo(32.4375, 13.96875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_1_3_6);
g.setComposite(AlphaComposite.getInstance(3, 0.6f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_7
paint = new LinearGradientPaint(new Point2D.Double(35.17623519897461, 17.240854263305664), new Point2D.Double(47.651092529296875, 17.240854263305664), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.99999917f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(30.65625, 22.0);
((GeneralPath)shape).curveTo(30.562069, 21.681452, 30.5, 21.350164, 30.5, 21.0);
((GeneralPath)shape).curveTo(30.5, 19.078297, 32.078297, 17.5, 34.0, 17.5);
((GeneralPath)shape).lineTo(49.5, 17.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_3_7);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_8
paint = new LinearGradientPaint(new Point2D.Double(35.253414154052734, 14.589204788208008), new Point2D.Double(48.08325958251953, 14.589204788208008), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.99999917f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(49.5, 14.5);
((GeneralPath)shape).lineTo(32.4375, 14.5);
((GeneralPath)shape).curveTo(31.869661, 14.5, 30.504368, 15.100658, 29.4375, 16.21875);
((GeneralPath)shape).curveTo(28.370632, 17.336842, 27.55885, 18.847694, 27.5, 20.5);
((GeneralPath)shape).lineTo(27.5, 22.0);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_3_8);
g.setComposite(AlphaComposite.getInstance(3, 0.4f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_9
paint = new LinearGradientPaint(new Point2D.Double(37.551124572753906, 26.433242797851562), new Point2D.Double(46.25851058959961, 26.433242797851562), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.99999917f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.21875, 24.0);
((GeneralPath)shape).curveTo(28.621471, 24.450693, 29.155735, 24.871288, 29.8125, 25.25);
((GeneralPath)shape).curveTo(31.187094, 26.042633, 32.953857, 26.5, 33.4375, 26.5);
((GeneralPath)shape).lineTo(49.5, 26.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_3_9);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_10 = g.getTransform();
g.transform(new AffineTransform(-1.0f, 0.0f, 0.0f, 1.0f, 48.0f, 0.0f));
// _0_0_0_1_3_10
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_10_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_10_0
paint = new LinearGradientPaint(new Point2D.Double(34.12220764160156, 15.8125), new Point2D.Double(44.8535270690918, 16.0625), new float[] {0.0f,1.0f}, new Color[] {new Color(85, 87, 83, 255),new Color(211, 215, 207, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.5, 22.5);
((GeneralPath)shape).curveTo(26.5, 25.5, 31.701962, 27.5, 33.4375, 27.5);
((GeneralPath)shape).lineTo(50.5, 27.5);
((GeneralPath)shape).lineTo(50.5, 22.5);
((GeneralPath)shape).lineTo(26.5, 22.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(37.5, 13.4375), new Point2D.Double(47.5625, 13.375), new float[] {0.0f,1.0f}, new Color[] {new Color(85, 87, 83, 255),new Color(136, 138, 133, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.99999917f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.5, 22.5);
((GeneralPath)shape).curveTo(26.5, 25.5, 31.701962, 27.5, 33.4375, 27.5);
((GeneralPath)shape).lineTo(50.5, 27.5);
((GeneralPath)shape).lineTo(50.5, 22.5);
((GeneralPath)shape).lineTo(26.5, 22.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_3_10_0);
g.setComposite(AlphaComposite.getInstance(3, 0.7f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_10_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_10_1
paint = new LinearGradientPaint(new Point2D.Double(37.551124572753906, 26.433242797851562), new Point2D.Double(44.822086334228516, 26.433242797851562), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.99999917f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(49.5, 23.5);
((GeneralPath)shape).lineTo(34.90625, 23.5);
((GeneralPath)shape).curveTo(34.503277, 23.8241, 34.028915, 24.0, 33.53125, 24.0);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_3_10_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_10_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_10_2
paint = new LinearGradientPaint(new Point2D.Double(29.86118507385254, 15.875), new Point2D.Double(46.56273651123047, 15.875), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(143, 147, 136, 255),new Color(198, 202, 194, 255),new Color(211, 215, 207, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.4375, 13.5);
((GeneralPath)shape).curveTo(30.326786, 13.5, 26.639368, 16.58704, 26.5, 20.5);
((GeneralPath)shape).lineTo(35.46875, 20.5);
((GeneralPath)shape).lineTo(35.46875, 18.5);
((GeneralPath)shape).lineTo(50.5, 18.5);
((GeneralPath)shape).lineTo(50.5, 13.5);
((GeneralPath)shape).lineTo(32.4375, 13.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(41.125, 13.375), new Point2D.Double(47.5625, 13.375), new float[] {0.0f,1.0f}, new Color[] {new Color(85, 87, 83, 255),new Color(136, 138, 133, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.99999917f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.4375, 13.5);
((GeneralPath)shape).curveTo(30.326786, 13.5, 26.639368, 16.58704, 26.5, 20.5);
((GeneralPath)shape).lineTo(35.46875, 20.5);
((GeneralPath)shape).lineTo(35.46875, 18.5);
((GeneralPath)shape).lineTo(50.5, 18.5);
((GeneralPath)shape).lineTo(50.5, 13.5);
((GeneralPath)shape).lineTo(32.4375, 13.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_3_10_2);
g.setComposite(AlphaComposite.getInstance(3, 0.7f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_10_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_10_3
paint = new RadialGradientPaint(new Point2D.Double(35.97337341308594, 14.05235481262207), 12.0f, new Point2D.Double(35.97336196899414, 14.052330017089844), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(238, 238, 236, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7512239813804626f, 2.3459410840587225E-6f, -7.164230169109942E-7f, 0.22941400110721588f, 8.25611400604248f, 12.931550025939941f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.4375, 13.96875);
((GeneralPath)shape).curveTo(31.610666, 13.96875, 30.20175, 14.649802, 29.0625, 15.84375);
((GeneralPath)shape).curveTo(28.029016, 16.926853, 27.225348, 18.415245, 27.03125, 20.03125);
((GeneralPath)shape).lineTo(35.0, 20.03125);
((GeneralPath)shape).lineTo(35.0, 18.5);
((GeneralPath)shape).curveTo(35.000027, 18.241127, 35.209877, 18.031277, 35.46875, 18.03125);
((GeneralPath)shape).lineTo(50.03125, 18.03125);
((GeneralPath)shape).lineTo(50.03125, 13.96875);
((GeneralPath)shape).lineTo(32.4375, 13.96875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_1_3_10_3);
g.setComposite(AlphaComposite.getInstance(3, 0.6f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_10_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_10_4
paint = new LinearGradientPaint(new Point2D.Double(35.17623519897461, 17.240854263305664), new Point2D.Double(47.651092529296875, 17.240854263305664), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.99999917f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(30.65625, 22.0);
((GeneralPath)shape).curveTo(30.562069, 21.681452, 30.5, 21.350164, 30.5, 21.0);
((GeneralPath)shape).curveTo(30.5, 19.078297, 32.078297, 17.5, 34.0, 17.5);
((GeneralPath)shape).lineTo(49.5, 17.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_3_10_4);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_10_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_10_5
paint = new LinearGradientPaint(new Point2D.Double(35.253414154052734, 14.589204788208008), new Point2D.Double(48.08325958251953, 14.589204788208008), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.99999917f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(49.5, 14.5);
((GeneralPath)shape).lineTo(32.4375, 14.5);
((GeneralPath)shape).curveTo(31.869661, 14.5, 30.504368, 15.100658, 29.4375, 16.21875);
((GeneralPath)shape).curveTo(28.370632, 17.336842, 27.55885, 18.847694, 27.5, 20.5);
((GeneralPath)shape).lineTo(27.5, 22.0);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_3_10_5);
g.setComposite(AlphaComposite.getInstance(3, 0.5f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_10_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_10_6
paint = new LinearGradientPaint(new Point2D.Double(37.551124572753906, 26.433242797851562), new Point2D.Double(44.5712890625, 26.433242797851562), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.99999917f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.21875, 24.0);
((GeneralPath)shape).curveTo(28.621471, 24.450693, 29.155735, 24.871288, 29.8125, 25.25);
((GeneralPath)shape).curveTo(31.187094, 26.042633, 32.953857, 26.5, 33.4375, 26.5);
((GeneralPath)shape).lineTo(49.5, 26.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_3_10_6);
g.setTransform(defaultTransform__0_0_0_1_3_10);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_11 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_11
paint = new LinearGradientPaint(new Point2D.Double(20.0, 24.0), new Point2D.Double(20.0, 19.121248245239258), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(238, 238, 236, 255),new Color(206, 206, 200, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.1875, 18.5);
((GeneralPath)shape).curveTo(10.768143, 19.213373, 10.5, 20.075256, 10.5, 21.0);
((GeneralPath)shape).curveTo(10.499999, 23.493, 12.266265, 25.5, 14.46875, 25.5);
((GeneralPath)shape).lineTo(33.53125, 25.5);
((GeneralPath)shape).curveTo(35.733734, 25.5, 37.5, 23.493, 37.5, 21.0);
((GeneralPath)shape).curveTo(37.5, 20.075256, 37.231857, 19.213373, 36.8125, 18.5);
((GeneralPath)shape).lineTo(34.0, 18.5);
((GeneralPath)shape).curveTo(32.788124, 18.5, 31.794853, 19.35874, 31.5625, 20.5);
((GeneralPath)shape).lineTo(16.4375, 20.5);
((GeneralPath)shape).curveTo(16.205147, 19.35874, 15.211875, 18.5, 14.0, 18.5);
((GeneralPath)shape).lineTo(11.1875, 18.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(85, 87, 83, 255);
stroke = new BasicStroke(0.9999991f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.1875, 18.5);
((GeneralPath)shape).curveTo(10.768143, 19.213373, 10.5, 20.075256, 10.5, 21.0);
((GeneralPath)shape).curveTo(10.499999, 23.493, 12.266265, 25.5, 14.46875, 25.5);
((GeneralPath)shape).lineTo(33.53125, 25.5);
((GeneralPath)shape).curveTo(35.733734, 25.5, 37.5, 23.493, 37.5, 21.0);
((GeneralPath)shape).curveTo(37.5, 20.075256, 37.231857, 19.213373, 36.8125, 18.5);
((GeneralPath)shape).lineTo(34.0, 18.5);
((GeneralPath)shape).curveTo(32.788124, 18.5, 31.794853, 19.35874, 31.5625, 20.5);
((GeneralPath)shape).lineTo(16.4375, 20.5);
((GeneralPath)shape).curveTo(16.205147, 19.35874, 15.211875, 18.5, 14.0, 18.5);
((GeneralPath)shape).lineTo(11.1875, 18.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1_3_11);
g.setComposite(AlphaComposite.getInstance(3, 0.5f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_12 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_12
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(35.8125, 18.0);
((GeneralPath)shape).lineTo(35.8125, 18.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(11.5625, 19.0);
((GeneralPath)shape).curveTo(11.340174, 19.430874, 11.122207, 19.902367, 11.03125, 20.40625);
((GeneralPath)shape).curveTo(10.996251, 20.60014, 11.0, 20.795284, 11.0, 21.0);
((GeneralPath)shape).curveTo(10.999999, 22.128418, 11.401404, 23.125854, 12.03125, 23.84375);
((GeneralPath)shape).curveTo(12.188542, 24.023521, 12.346598, 24.1703, 12.53125, 24.3125);
((GeneralPath)shape).curveTo(12.715902, 24.4547, 12.917774, 24.587786, 13.125, 24.6875);
((GeneralPath)shape).curveTo(13.539199, 24.886185, 13.994066, 25.0, 14.46875, 25.0);
((GeneralPath)shape).lineTo(33.53125, 25.0);
((GeneralPath)shape).curveTo(34.00729, 25.0, 34.46055, 24.88693, 34.875, 24.6875);
((GeneralPath)shape).curveTo(35.082226, 24.58779, 35.2841, 24.4547, 35.46875, 24.3125);
((GeneralPath)shape).curveTo(35.6534, 24.1703, 35.81146, 24.023521, 35.96875, 23.84375);
((GeneralPath)shape).curveTo(36.598595, 23.125854, 37.0, 22.128418, 37.0, 21.0);
((GeneralPath)shape).curveTo(37.0, 20.816582, 36.98893, 20.644896, 36.96875, 20.46875);
((GeneralPath)shape).curveTo(36.96555, 20.44919, 36.97225, 20.4257, 36.96875, 20.40625);
((GeneralPath)shape).curveTo(36.877792, 19.902367, 36.659824, 19.430874, 36.4375, 19.0);
((GeneralPath)shape).lineTo(35.375, 19.0);
((GeneralPath)shape).curveTo(35.75219, 19.511976, 36.0, 20.196918, 36.0, 21.0);
((GeneralPath)shape).curveTo(36.0, 22.794054, 34.817078, 24.0, 33.53125, 24.0);
((GeneralPath)shape).lineTo(14.46875, 24.0);
((GeneralPath)shape).curveTo(13.182922, 23.999998, 12.0, 22.794052, 12.0, 21.0);
((GeneralPath)shape).curveTo(12.0, 20.196918, 12.247812, 19.511976, 12.625, 19.0);
((GeneralPath)shape).lineTo(11.5625, 19.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_1_3_12);
g.setComposite(AlphaComposite.getInstance(3, 0.8f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_3_13 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_3_13
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(15.0, 19.25);
((GeneralPath)shape).lineTo(15.0, 20.5625);
((GeneralPath)shape).curveTo(15.0, 21.350225, 15.649774, 22.0, 16.4375, 22.0);
((GeneralPath)shape).lineTo(31.5625, 22.0);
((GeneralPath)shape).curveTo(32.350227, 22.0, 33.0, 21.350225, 33.0, 20.5625);
((GeneralPath)shape).lineTo(33.0, 19.25);
((GeneralPath)shape).curveTo(32.518272, 19.528997, 32.1791, 20.021034, 32.0625, 20.59375);
((GeneralPath)shape).curveTo(32.04967, 20.66098, 32.00479, 20.695864, 31.96875, 20.75);
((GeneralPath)shape).curveTo(31.904537, 20.908346, 31.750769, 21.0, 31.5625, 21.0);
((GeneralPath)shape).lineTo(16.4375, 21.0);
((GeneralPath)shape).curveTo(16.262617, 21.0031, 16.123356, 20.88835, 16.03125, 20.75);
((GeneralPath)shape).curveTo(15.99521, 20.69586, 15.95033, 20.66098, 15.9375, 20.59375);
((GeneralPath)shape).curveTo(15.820899, 20.021034, 15.481727, 19.528997, 15.0, 19.25);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_1_3_13);
g.setTransform(defaultTransform__0_0_0_1_3);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_4
paint = new RadialGradientPaint(new Point2D.Double(7.012699127197266, 22.255550384521484), 13.0f, new Point2D.Double(7.012699127197266, 22.255550384521484), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(238, 238, 236, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.2122499942779541f, 0.0f, 0.0f, 0.2548069953918457f, 12.011560440063477f, 16.329130172729492f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.5, 19.0);
((GeneralPath)shape).curveTo(11.198127, 19.592213, 11.0, 20.27145, 11.0, 21.0);
((GeneralPath)shape).curveTo(10.999999, 23.256836, 12.570013, 25.0, 14.46875, 25.0);
((GeneralPath)shape).lineTo(33.53125, 25.0);
((GeneralPath)shape).curveTo(35.42999, 25.0, 37.0, 23.256836, 37.0, 21.0);
((GeneralPath)shape).curveTo(37.0, 20.27145, 36.801872, 19.592213, 36.5, 19.0);
((GeneralPath)shape).lineTo(34.0, 19.0);
((GeneralPath)shape).curveTo(33.024326, 19.0, 32.24906, 19.677406, 32.0625, 20.59375);
((GeneralPath)shape).curveTo(32.016888, 20.83279, 31.805817, 21.004288, 31.5625, 21.0);
((GeneralPath)shape).lineTo(16.4375, 21.0);
((GeneralPath)shape).curveTo(16.194183, 21.004288, 15.98311, 20.83279, 15.9375, 20.59375);
((GeneralPath)shape).curveTo(15.750938, 19.677406, 14.975674, 19.0, 14.0, 19.0);
((GeneralPath)shape).lineTo(11.5, 19.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_1_4);
g.setTransform(defaultTransform__0_0_0_1);
g.setComposite(AlphaComposite.getInstance(3, 0.24073175f * origAlpha));
AffineTransform defaultTransform__0_0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_2
g.setTransform(defaultTransform__0_0_0_2);
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
        return 1;
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

