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


import java.awt.*;
import java.awt.geom.*;

/**
 * This class has been automatically generated using <a
 * href="https://flamingo.dev.java.net">Flamingo SVG transcoder</a>.
 */
public class ZoomFitWidth implements FlamencoIconAdapter{
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
g.transform(new AffineTransform(1.3986140489578247f, 0.0f, 0.0f, 1.3986140489578247f, -6.135949611663818f, -8.210569381713867f));
// _0_0_0
paint = new RadialGradientPaint(new Point2D.Double(18.240928649902344, 21.8179874420166), 8.308505f, new Point2D.Double(18.240928649902344, 21.8179874420166), new float[] {0.0f,1.0f}, new Color[] {new Color(114, 159, 207, 53),new Color(114, 159, 207, 172)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(25.897785, 18.478292);
((GeneralPath)shape).curveTo(25.897785, 23.066954, 22.17794, 26.786797, 17.58928, 26.786797);
((GeneralPath)shape).curveTo(13.00062, 26.786797, 9.280775, 23.066954, 9.280775, 18.478292);
((GeneralPath)shape).curveTo(9.280775, 13.889632, 13.00062, 10.169787, 17.58928, 10.169787);
((GeneralPath)shape).curveTo(22.17794, 10.169787, 25.897785, 13.889632, 25.897785, 18.478292);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(48, 99, 163, 255);
stroke = new BasicStroke(0.71499395f,1,0,10.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(25.897785, 18.478292);
((GeneralPath)shape).curveTo(25.897785, 23.066954, 22.17794, 26.786797, 17.58928, 26.786797);
((GeneralPath)shape).curveTo(13.00062, 26.786797, 9.280775, 23.066954, 9.280775, 18.478292);
((GeneralPath)shape).curveTo(9.280775, 13.889632, 13.00062, 10.169787, 17.58928, 10.169787);
((GeneralPath)shape).curveTo(22.17794, 10.169787, 25.897785, 13.889632, 25.897785, 18.478292);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0);
g.setComposite(AlphaComposite.getInstance(3, 0.8342246f * origAlpha));
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1
paint = new RadialGradientPaint(new Point2D.Double(15.4143705368042, 13.078408241271973), 6.65625f, new Point2D.Double(15.4143705368042, 13.078408241271973), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 63)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.5929629802703857f, 0.0f, 0.0f, 2.2521040439605713f, -24.97136116027832f, -18.852611541748047f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.245302, 7.485082);
((GeneralPath)shape).curveTo(13.037712, 7.485082, 8.820756, 11.702039, 8.820756, 16.90963);
((GeneralPath)shape).curveTo(8.820756, 18.413605, 9.241063, 19.797403, 9.867928, 21.059532);
((GeneralPath)shape).curveTo(11.120307, 21.521145, 12.450684, 21.835215, 13.862694, 21.835215);
((GeneralPath)shape).curveTo(20.03365, 21.835215, 24.961975, 16.973577, 25.3428, 10.898086);
((GeneralPath)shape).curveTo(23.611837, 8.852555, 21.132763, 7.485082, 18.245302, 7.485082);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_1);
g.setComposite(AlphaComposite.getInstance(3, 0.5630631f * origAlpha));
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2
paint = new Color(0, 24, 146, 255);
shape = new Rectangle2D.Double(9.452648162841797, 15.114628791809082, 18.217811584472656, 14.859052658081055);
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 123, 199);
stroke = new BasicStroke(0.19019037f,1,1,3.8f,null,0.0f);
shape = new Rectangle2D.Double(9.452648162841797, 15.114628791809082, 18.217811584472656, 14.859052658081055);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_2);
g.setComposite(AlphaComposite.getInstance(3, 0.17112301f * origAlpha));
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.446431040763855f, 0.0f, 0.0f, 1.5199899673461914f, -10.974530220031738f, -17.751680374145508f));
// _0_0_3
paint = new RadialGradientPaint(new Point2D.Double(24.13001823425293, 37.96792221069336), 16.528622f, new Point2D.Double(24.13001823425293, 37.96792221069336), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.23796799778938293f, 0.0f, 28.93277931213379f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(40.65864, 37.967922);
((GeneralPath)shape).curveTo(40.65864, 40.140213, 33.258526, 41.901203, 24.13002, 41.901203);
((GeneralPath)shape).curveTo(15.001514, 41.901203, 7.6013985, 40.140213, 7.6013985, 37.967922);
((GeneralPath)shape).curveTo(7.6013985, 35.79563, 15.001514, 34.03464, 24.13002, 34.03464);
((GeneralPath)shape).curveTo(33.258526, 34.03464, 40.65864, 35.79563, 40.65864, 37.967922);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_3);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4
paint = new Color(220, 220, 220, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.62757, 3.1435547);
((GeneralPath)shape).curveTo(10.48844, 3.1435547, 3.8827686, 9.749226, 3.8827686, 17.888355);
((GeneralPath)shape).curveTo(3.8827686, 26.027485, 10.48844, 32.633156, 18.62757, 32.633156);
((GeneralPath)shape).curveTo(22.107124, 32.633156, 25.17857, 31.248762, 27.701292, 29.230509);
((GeneralPath)shape).curveTo(27.495914, 30.23739, 27.623262, 31.265877, 28.457436, 31.990433);
((GeneralPath)shape).lineTo(39.42152, 41.51784);
((GeneralPath)shape).curveTo(40.654938, 42.58917, 42.508984, 42.448803, 43.58031, 41.215385);
((GeneralPath)shape).curveTo(44.651638, 39.981968, 44.51127, 38.127922, 43.277855, 37.056595);
((GeneralPath)shape).lineTo(32.31377, 27.529188);
((GeneralPath)shape).curveTo(31.642242, 26.94591, 30.820892, 26.77322, 30.007532, 26.886467);
((GeneralPath)shape).curveTo(31.994232, 24.374044, 33.37237, 21.337664, 33.37237, 17.888357);
((GeneralPath)shape).curveTo(33.37237, 9.749228, 26.7667, 3.1435556, 18.627571, 3.1435556);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(18.551949, 4.369738);
((GeneralPath)shape).curveTo(26.191408, 4.369738, 31.843723, 9.158689, 31.843723, 17.661512);
((GeneralPath)shape).curveTo(31.843723, 26.336624, 26.027033, 30.953287, 18.551949, 30.953287);
((GeneralPath)shape).curveTo(11.249, 30.953287, 5.2601748, 25.475195, 5.2601748, 17.661512);
((GeneralPath)shape).curveTo(5.2601748, 9.677405, 11.084813, 4.3697376, 18.551949, 4.3697376);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(27.36634063720703, 26.58029556274414), new Point2D.Double(31.33596420288086, 30.557771682739258), new float[] {0.0f,1.0f}, new Color[] {new Color(138, 138, 138, 255),new Color(72, 72, 72, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(2.000001f,1,0,10.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.62757, 3.1435547);
((GeneralPath)shape).curveTo(10.48844, 3.1435547, 3.8827686, 9.749226, 3.8827686, 17.888355);
((GeneralPath)shape).curveTo(3.8827686, 26.027485, 10.48844, 32.633156, 18.62757, 32.633156);
((GeneralPath)shape).curveTo(22.107124, 32.633156, 25.17857, 31.248762, 27.701292, 29.230509);
((GeneralPath)shape).curveTo(27.495914, 30.23739, 27.623262, 31.265877, 28.457436, 31.990433);
((GeneralPath)shape).lineTo(39.42152, 41.51784);
((GeneralPath)shape).curveTo(40.654938, 42.58917, 42.508984, 42.448803, 43.58031, 41.215385);
((GeneralPath)shape).curveTo(44.651638, 39.981968, 44.51127, 38.127922, 43.277855, 37.056595);
((GeneralPath)shape).lineTo(32.31377, 27.529188);
((GeneralPath)shape).curveTo(31.642242, 26.94591, 30.820892, 26.77322, 30.007532, 26.886467);
((GeneralPath)shape).curveTo(31.994232, 24.374044, 33.37237, 21.337664, 33.37237, 17.888357);
((GeneralPath)shape).curveTo(33.37237, 9.749228, 26.7667, 3.1435556, 18.627571, 3.1435556);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(18.551949, 4.369738);
((GeneralPath)shape).curveTo(26.191408, 4.369738, 31.843723, 9.158689, 31.843723, 17.661512);
((GeneralPath)shape).curveTo(31.843723, 26.336624, 26.027033, 30.953287, 18.551949, 30.953287);
((GeneralPath)shape).curveTo(11.249, 30.953287, 5.2601748, 25.475195, 5.2601748, 17.661512);
((GeneralPath)shape).curveTo(5.2601748, 9.677405, 11.084813, 4.3697376, 18.551949, 4.3697376);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_4);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_5
paint = new Color(220, 220, 220, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.602905, 3.0803552);
((GeneralPath)shape).curveTo(10.437466, 3.0803552, 3.810441, 9.707379, 3.810441, 17.87282);
((GeneralPath)shape).curveTo(3.810441, 26.03826, 10.437466, 32.665283, 18.602905, 32.665283);
((GeneralPath)shape).curveTo(22.093708, 32.665283, 25.175083, 31.276417, 27.70596, 29.251638);
((GeneralPath)shape).curveTo(27.499918, 30.261774, 27.627668, 31.293585, 28.464546, 32.020485);
((GeneralPath)shape).lineTo(39.464073, 41.57869);
((GeneralPath)shape).curveTo(40.701477, 42.65348, 42.561516, 42.51266, 43.636307, 41.275253);
((GeneralPath)shape).curveTo(44.711098, 40.03785, 44.570274, 38.17781, 43.33287, 37.10302);
((GeneralPath)shape).lineTo(32.333347, 27.544815);
((GeneralPath)shape).curveTo(31.659649, 26.959652, 30.835644, 26.786402, 30.019653, 26.900017);
((GeneralPath)shape).curveTo(32.012775, 24.379473, 33.39537, 21.333277, 33.39537, 17.87282);
((GeneralPath)shape).curveTo(33.39537, 9.70738, 26.768347, 3.0803556, 18.602905, 3.0803556);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(18.527046, 6.266424);
((GeneralPath)shape).curveTo(24.808155, 6.266424, 29.905865, 11.364135, 29.905865, 17.645243);
((GeneralPath)shape).curveTo(29.905865, 23.92635, 24.808155, 29.02406, 18.527046, 29.02406);
((GeneralPath)shape).curveTo(12.245938, 29.02406, 7.1482277, 23.92635, 7.1482277, 17.64524);
((GeneralPath)shape).curveTo(7.1482277, 11.364133, 12.245938, 6.2664223, 18.527046, 6.2664223);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_5);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_6
paint = new LinearGradientPaint(new Point2D.Double(30.65625, 34.0), new Point2D.Double(33.21875, 31.0625), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(125, 125, 125, 255),new Color(177, 177, 177, 255),new Color(104, 104, 104, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.3345930576324463f, 0.0f, 0.0f, 1.2912919521331787f, -6.973842144012451f, -7.460658073425293f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(39.507004, 41.57769);
((GeneralPath)shape).curveTo(39.02833, 39.304504, 40.904335, 36.76627, 43.091057, 36.789314);
((GeneralPath)shape).curveTo(43.091057, 36.789314, 32.33069, 27.531204, 32.33069, 27.531204);
((GeneralPath)shape).curveTo(29.385897, 27.474495, 28.061186, 29.80382, 28.553875, 32.131126);
((GeneralPath)shape).lineTo(39.507004, 41.57769);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_6);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.2457430362701416f, 0.0f, 0.0f, 1.2457430362701416f, -3.4253458976745605f, -6.177032947540283f));
// _0_0_7
paint = new LinearGradientPaint(new Point2D.Double(18.292673110961914, 13.602121353149414), new Point2D.Double(17.500892639160156, 25.74346923828125), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 56),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.8027336f,1,0,10.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.549437, 18.920233);
((GeneralPath)shape).curveTo(28.549437, 25.022175, 23.602835, 29.968777, 17.500893, 29.968777);
((GeneralPath)shape).curveTo(11.398951, 29.968777, 6.4523487, 25.022175, 6.4523487, 18.920233);
((GeneralPath)shape).curveTo(6.4523487, 12.818291, 11.398951, 7.871689, 17.500893, 7.871689);
((GeneralPath)shape).curveTo(23.602835, 7.871689, 28.549437, 12.818291, 28.549437, 18.920233);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_7);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_8 = g.getTransform();
g.transform(new AffineTransform(0.497763991355896f, 0.0f, 0.0f, 0.609620988368988f, 8.973526000976562f, 15.619290351867676f));
// _0_0_8
paint = new RadialGradientPaint(new Point2D.Double(24.13001823425293, 37.96792221069336), 16.528622f, new Point2D.Double(24.13001823425293, 37.96792221069336), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.23796799778938293f, 0.0f, 28.93277931213379f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(40.65864, 37.967922);
((GeneralPath)shape).curveTo(40.65864, 40.140213, 33.258526, 41.901203, 24.13002, 41.901203);
((GeneralPath)shape).curveTo(15.001514, 41.901203, 7.6013985, 40.140213, 7.6013985, 37.967922);
((GeneralPath)shape).curveTo(7.6013985, 35.79563, 15.001514, 34.03464, 24.13002, 34.03464);
((GeneralPath)shape).curveTo(33.258526, 34.03464, 40.65864, 35.79563, 40.65864, 37.967922);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_8);
g.setComposite(AlphaComposite.getInstance(3, 0.43315506f * origAlpha));
AffineTransform defaultTransform__0_0_9 = g.getTransform();
g.transform(new AffineTransform(0.7529857754707336f, 0.6580368280410767f, -0.6489019989967346f, 0.7608720064163208f, 0.0f, 0.0f));
// _0_0_9
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(1.0000312f,1,0,10.0f,null,0.0f);
shape = new RoundRectangle2D.Double(40.373348236083984, 0.14086054265499115, 19.048444747924805, 4.440478324890137, 4.273323059082031, 3.7758729457855225);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_9);
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
        return 44;
    }
}

