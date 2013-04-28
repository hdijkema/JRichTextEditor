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
public class ChangeTopic implements FlamencoIconAdapter {
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
g.transform(new AffineTransform(0.23728813230991364f, 0.0f, 0.0f, 0.23728813230991364f, 0.7627118825912476f, 2.6694915294647217f));
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
g.transform(new AffineTransform(2.071593761444092f, 0.0f, 0.0f, 2.071593761444092f, -56.8007926940918f, -167.1657257080078f));
// _0_1
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_0
paint = new LinearGradientPaint(new Point2D.Double(689.319580078125, 1062.5889892578125), new Point2D.Double(696.187255859375, 720.1293334960938), new float[] {0.0f,1.0f}, new Color[] {new Color(33, 132, 0, 255),new Color(0, 77, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.021705569699406624f, 0.0f, 0.0f, 0.021705569699406624f, 24.20339012145996f, 69.42986297607422f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(42.535294, 86.64606);
((GeneralPath)shape).lineTo(39.702915, 82.11964);
((GeneralPath)shape).curveTo(38.086395, 82.03586, 36.557995, 82.865425, 35.748657, 84.267235);
((GeneralPath)shape).lineTo(33.041542, 88.95613);
((GeneralPath)shape).lineTo(39.16926, 92.493965);
((GeneralPath)shape).lineTo(42.53529, 86.646065);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_1
paint = new Color(0, 105, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(35.537575, 92.086845);
((GeneralPath)shape).lineTo(27.046776, 92.086845);
((GeneralPath)shape).lineTo(30.690117, 94.38375);
((GeneralPath)shape).lineTo(28.741262, 97.51907);
((GeneralPath)shape).lineTo(31.90819, 104.67802);
((GeneralPath)shape).curveTo(31.94032, 104.75062, 31.97446, 104.82238, 32.010555, 104.893135);
((GeneralPath)shape).lineTo(36.114185, 97.78546);
((GeneralPath)shape).lineTo(38.716183, 99.42668);
((GeneralPath)shape).lineTo(35.53758, 92.08684);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_2
paint = new LinearGradientPaint(new Point2D.Double(829.8729858398438, 1747.074951171875), new Point2D.Double(785.8729858398438, 1416.0909423828125), new float[] {0.0f,1.0f}, new Color[] {new Color(142, 254, 2, 255),new Color(24, 140, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.021705569699406624f, 0.0f, 0.0f, 0.021705569699406624f, 24.20339012145996f, 69.42986297607422f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(41.2612, 100.16691);
((GeneralPath)shape).lineTo(34.73927, 100.16691);
((GeneralPath)shape).lineTo(32.010555, 104.893166);
((GeneralPath)shape).curveTo(32.746265, 106.335, 34.228256, 107.24257, 35.846947, 107.24257);
((GeneralPath)shape).lineTo(38.20873, 107.24257);
((GeneralPath)shape).lineTo(41.2612, 107.24257);
((GeneralPath)shape).lineTo(41.2612, 100.16691);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_2);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_3
paint = new LinearGradientPaint(new Point2D.Double(1142.8189697265625, 1323.9886474609375), new Point2D.Double(1422.9923095703125, 1160.64697265625), new float[] {0.0f,1.0f}, new Color[] {new Color(47, 151, 0, 255),new Color(16, 106, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.021705569699406624f, 0.0f, 0.0f, 0.021705569699406624f, 24.20339012145996f, 69.42986297607422f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(55.695023, 95.66982);
((GeneralPath)shape).lineTo(52.98789, 90.98094);
((GeneralPath)shape).lineTo(46.86017, 94.518776);
((GeneralPath)shape).lineTo(50.121124, 100.16693);
((GeneralPath)shape).lineTo(55.57853, 100.16693);
((GeneralPath)shape).curveTo(56.459362, 98.80888, 56.50436, 97.07163, 55.695023, 95.66982);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_3);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_4
paint = new LinearGradientPaint(new Point2D.Double(1039.154541015625, 1851.1400146484375), new Point2D.Double(1068.037109375, 1299.1978759765625), new float[] {0.0f,1.0f}, new Color[] {new Color(142, 254, 2, 255),new Color(27, 144, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.021705569699406624f, 0.0f, 0.0f, 0.021705569699406624f, 24.20339012145996f, 69.42986297607422f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(55.443436, 100.36313);
((GeneralPath)shape).curveTo(55.490276, 100.29903, 55.535316, 100.23355, 55.578552, 100.16691);
((GeneralPath)shape).lineTo(47.37131, 100.16691);
((GeneralPath)shape).lineTo(47.49167, 97.09288);
((GeneralPath)shape).lineTo(42.724434, 103.51558);
((GeneralPath)shape).lineTo(46.969845, 110.86881);
((GeneralPath)shape).lineTo(47.137367, 106.56514);
((GeneralPath)shape).lineTo(50.827072, 106.68525);
((GeneralPath)shape).lineTo(55.443428, 100.36313);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_4);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_5 = g.getTransform();
g.transform(new AffineTransform(0.021705569699406624f, 0.0f, 0.0f, 0.021705569699406624f, 24.20339012145996f, 69.42986297607422f));
// _0_1_5
paint = new RadialGradientPaint(new Point2D.Double(256.529296875, 1343.6842041015625), 228.666f, new Point2D.Double(256.529296875, 1343.6842041015625), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 105, 0, 255),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(7.116979122161865f, 0.33148398995399475f, -0.14814600348472595f, 3.180643081665039f, -1343.9310302734375f, -2934.62890625f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(318.323, 1145.175);
((GeneralPath)shape).lineTo(225.058, 1295.221);
((GeneralPath)shape).lineTo(360.931, 1602.367);
((GeneralPath)shape).lineTo(543.43, 1286.267);
((GeneralPath)shape).lineTo(543.43, 1286.267);
((GeneralPath)shape).lineTo(637.388, 1345.532);
((GeneralPath)shape).lineTo(512.872, 1058.006);
((GeneralPath)shape).lineTo(180.056, 1058.006);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_5);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_6 = g.getTransform();
g.transform(new AffineTransform(0.021705569699406624f, 0.0f, 0.0f, 0.021705569699406624f, 24.20339012145996f, 69.42986297607422f));
// _0_1_6
paint = new LinearGradientPaint(new Point2D.Double(1032.758544921875, 1840.3603515625), new Point2D.Double(1054.0615234375, 1319.7459716796875), new float[] {0.0f,1.0f}, new Color[] {new Color(142, 254, 2, 255),new Color(87, 166, 42, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(1036.623, 1859.57);
((GeneralPath)shape).lineTo(1042.98, 1696.243);
((GeneralPath)shape).lineTo(1219.555, 1701.989);
((GeneralPath)shape).lineTo(1417.615, 1430.748);
((GeneralPath)shape).lineTo(1417.615, 1430.748);
((GeneralPath)shape).lineTo(1052.615, 1430.748);
((GeneralPath)shape).lineTo(1056.96, 1319.746);
((GeneralPath)shape).lineTo(870.214, 1571.342);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_6);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_7
paint = new LinearGradientPaint(new Point2D.Double(660.2730102539062, 1727.9010009765625), new Point2D.Double(659.698974609375, 1590.7640380859375), new float[] {0.0f,1.0f}, new Color[] {new Color(95, 213, 0, 255),new Color(97, 215, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.021705569699406624f, 0.0f, 0.0f, 0.021705569699406624f, 24.20339012145996f, 69.42986297607422f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(40.953545, 100.47455);
((GeneralPath)shape).lineTo(34.56165, 100.47455);
((GeneralPath)shape).lineTo(32.19696, 104.5703);
((GeneralPath)shape).curveTo(32.841267, 106.00896, 34.270576, 106.93491, 35.846924, 106.93491);
((GeneralPath)shape).lineTo(40.95355, 106.93491);
((GeneralPath)shape).lineTo(40.95355, 100.47454);
((GeneralPath)shape).lineTo(40.95355, 100.47454);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_7);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_8
paint = new Color(0, 105, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(53.273987, 84.22397);
((GeneralPath)shape).lineTo(49.463142, 86.230736);
((GeneralPath)shape).lineTo(47.722294, 82.975334);
((GeneralPath)shape).lineTo(39.93901, 82.13847);
((GeneralPath)shape).curveTo(39.86002, 82.129974, 39.7765, 82.123436, 39.69719, 82.11933);
((GeneralPath)shape).lineTo(43.80514, 89.22725);
((GeneralPath)shape).lineTo(41.08281, 90.660034);
((GeneralPath)shape).lineTo(49.028603, 91.5772);
((GeneralPath)shape).lineTo(53.273994, 84.22397);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_8);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_9 = g.getTransform();
g.transform(new AffineTransform(0.021705569699406624f, 0.0f, 0.0f, 0.021705569699406624f, 24.20339012145996f, 69.42986297607422f));
// _0_1_9
paint = new LinearGradientPaint(new Point2D.Double(1089.569091796875, 990.5014038085938), new Point2D.Double(1120.6986083984375, 601.4409790039062), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 105, 0, 255),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(923.146, 917.54);
((GeneralPath)shape).lineTo(824.843, 969.278);
((GeneralPath)shape).lineTo(1136.105, 1005.207);
((GeneralPath)shape).lineTo(1302.513, 716.979);
((GeneralPath)shape).lineTo(1302.513, 716.979);
((GeneralPath)shape).lineTo(1157.888, 793.137);
((GeneralPath)shape).lineTo(1074.578, 637.345);
((GeneralPath)shape).lineTo(740.646, 601.441);
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
        return 60;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * 
     * @return The height of the bounding box of the original SVG image.
     */
    public  int getOrigHeight() {
        return 58;
    }
}

