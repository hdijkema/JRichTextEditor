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
public class RemoveTopic implements FlamencoIconAdapter {
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
g.transform(new AffineTransform(0.2818911671638489f, 0.0f, 0.0f, 0.2818911671638489f, 0.7181088328361511f, 1.0771633386611938f));
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
g.transform(new AffineTransform(1.1053587198257446f, 0.0f, 0.0f, 1.1053587198257446f, 2.792531728744507f, 4.764782428741455f));
// _0_1
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_0
g.setComposite(AlphaComposite.getInstance(3, 0.6f * origAlpha));
AffineTransform defaultTransform__0_1_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0705549716949463f, 0.0f, 0.0f, 0.5249999761581421f, -0.8927549719810486f, 22.5f));
// _0_1_0_0
paint = new RadialGradientPaint(new Point2D.Double(23.85714340209961, 40.0), 17.142857f, new Point2D.Double(23.85714340209961, 40.0), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.5f, 0.0f, 20.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(41.0, 40.0);
((GeneralPath)shape).curveTo(41.0, 44.733868, 33.324883, 48.571426, 23.857143, 48.571426);
((GeneralPath)shape).curveTo(14.389405, 48.571426, 6.714287, 44.733868, 6.714287, 40.0);
((GeneralPath)shape).curveTo(6.714287, 35.266132, 14.389405, 31.428572, 23.857143, 31.428572);
((GeneralPath)shape).curveTo(33.324883, 31.428572, 41.0, 35.266132, 41.0, 40.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_0_0);
g.setTransform(defaultTransform__0_1_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_1
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_1_0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_1_0_0 = g.getTransform();
g.transform(new AffineTransform(0.9204879999160767f, 0.0f, 0.0f, 0.9204879999160767f, 2.3685319423675537f, 0.9740800261497498f));
// _0_1_1_0_0
paint = new LinearGradientPaint(new Point2D.Double(36.91797637939453, 66.2880630493164), new Point2D.Double(19.071495056152344, 5.541010856628418), new float[] {0.0f,1.0f}, new Color[] {new Color(164, 0, 0, 255),new Color(255, 23, 23, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(46.857143, 23.928572);
((GeneralPath)shape).curveTo(46.857143, 36.828365, 36.399796, 47.285713, 23.5, 47.285713);
((GeneralPath)shape).curveTo(10.600206, 47.285713, 0.1428566, 36.828365, 0.1428566, 23.92857);
((GeneralPath)shape).curveTo(0.1428566, 11.028776, 10.600206, 0.5714264, 23.5, 0.5714264);
((GeneralPath)shape).curveTo(36.399796, 0.5714264, 46.857143, 11.028776, 46.857143, 23.92857);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(178, 0, 0, 255);
stroke = new BasicStroke(1.08638f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(46.857143, 23.928572);
((GeneralPath)shape).curveTo(46.857143, 36.828365, 36.399796, 47.285713, 23.5, 47.285713);
((GeneralPath)shape).curveTo(10.600206, 47.285713, 0.1428566, 36.828365, 0.1428566, 23.92857);
((GeneralPath)shape).curveTo(0.1428566, 11.028776, 10.600206, 0.5714264, 23.5, 0.5714264);
((GeneralPath)shape).curveTo(36.399796, 0.5714264, 46.857143, 11.028776, 46.857143, 23.92857);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_1_1_0_0);
g.setComposite(AlphaComposite.getInstance(3, 0.34659088f * origAlpha));
AffineTransform defaultTransform__0_1_1_0_1 = g.getTransform();
g.transform(new AffineTransform(0.8560929894447327f, 0.0f, 0.0f, 0.8560929894447327f, 1.818274974822998f, 0.19776900112628937f));
// _0_1_1_0_1
paint = new Color(204, 0, 0, 0);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(49.901535, 26.635273);
((GeneralPath)shape).curveTo(49.901535, 39.885204, 39.160343, 50.626396, 25.910412, 50.626396);
((GeneralPath)shape).curveTo(12.66048, 50.626396, 1.9192886, 39.885204, 1.9192886, 26.635273);
((GeneralPath)shape).curveTo(1.9192886, 13.385342, 12.6604805, 2.6441498, 25.910412, 2.6441498);
((GeneralPath)shape).curveTo(39.160343, 2.6441498, 49.901535, 13.385342, 49.901535, 26.635273);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(43.93581008911133, 53.83598327636719), new Point2D.Double(20.064685821533203, -8.562670707702637), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 230, 155, 255),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.1680961f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(49.901535, 26.635273);
((GeneralPath)shape).curveTo(49.901535, 39.885204, 39.160343, 50.626396, 25.910412, 50.626396);
((GeneralPath)shape).curveTo(12.66048, 50.626396, 1.9192886, 39.885204, 1.9192886, 26.635273);
((GeneralPath)shape).curveTo(1.9192886, 13.385342, 12.6604805, 2.6441498, 25.910412, 2.6441498);
((GeneralPath)shape).curveTo(39.160343, 2.6441498, 49.901535, 13.385342, 49.901535, 26.635273);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_1_1_0_1);
g.setTransform(defaultTransform__0_1_1_0);
g.setTransform(defaultTransform__0_1_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_2
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_2_0 = g.getTransform();
g.transform(new AffineTransform(0.7071068286895752f, -0.7071068286895752f, 0.7071068286895752f, 0.7071068286895752f, 0.0f, 0.0f));
// _0_1_2_0
paint = new Color(239, 239, 239, 255);
shape = new Rectangle2D.Double(-13.292923927307129, 30.234006881713867, 28.000001907348633, 6.000087261199951);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_2_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_2_1 = g.getTransform();
g.transform(new AffineTransform(0.7071068286895752f, 0.7071068286895752f, -0.7071068286895752f, 0.7071068286895752f, 0.0f, 0.0f));
// _0_1_2_1
paint = new Color(239, 239, 239, 255);
shape = new Rectangle2D.Double(19.23404884338379, -3.707120180130005, 28.000001907348633, 6.000087261199951);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_2_1);
g.setTransform(defaultTransform__0_1_2);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_3
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_3_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_3_0
paint = new LinearGradientPaint(new Point2D.Double(21.993772506713867, 33.955299377441406), new Point2D.Double(20.917078018188477, 15.81460189819336), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 254, 255, 85),new Color(255, 254, 255, 55)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0029939413070679f, 0.0f, 0.0f, 1.0029939413070679f, -0.07185900211334229f, 0.019683560356497765f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(43.42868, 21.800186);
((GeneralPath)shape).curveTo(43.42868, 32.66323, 33.04335, 15.515116, 24.69803, 22.18773);
((GeneralPath)shape).curveTo(16.547379, 28.704695, 4.039398, 34.414776, 4.039398, 23.551733);
((GeneralPath)shape).curveTo(4.039398, 12.434495, 12.760829, 2.120758, 23.623875, 2.120758);
((GeneralPath)shape).curveTo(34.48692, 2.120758, 43.42868, 10.93714, 43.42868, 21.800186);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1_3_0);
g.setTransform(defaultTransform__0_1_3);
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
        return 53;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * 
     * @return The height of the bounding box of the original SVG image.
     */
    public  int getOrigHeight() {
        return 57;
    }
}

