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
public class Last 
implements FlamencoIconAdapter {
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
g.setComposite(AlphaComposite.getInstance(3, 0.2994652f * origAlpha));
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.2711859941482544f, 0.0f, 0.0f, 1.2711859941482544f, -9.619376182556152f, -12.278570175170898f));
// _0_0_0
paint = new RadialGradientPaint(new Point2D.Double(24.837125778198242, 36.42112731933594), 15.644737f, new Point2D.Double(24.837125778198242, 36.42112731933594), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.5367230176925659f, 1.2463999821596384E-15f, 16.87306022644043f));
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
paint = new RadialGradientPaint(new Point2D.Double(22.58818817138672, 34.462799072265625), 16.9562f, new Point2D.Double(22.58818817138672, 34.462799072265625), new float[] {0.0f,1.0f}, new Color[] {new Color(115, 210, 22, 255),new Color(78, 154, 6, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.8430219888687134f, 1.871884976963574E-16f, -2.26522788707826E-16f, 1.0201679468154907f, 4.413859844207764f, 0.606440007686615f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(45.5, 6.5);
((GeneralPath)shape).lineTo(39.50379, 6.5);
((GeneralPath)shape).lineTo(39.50379, 21.625);
((GeneralPath)shape).lineTo(21.5, 6.53125);
((GeneralPath)shape).lineTo(21.4375, 14.5);
((GeneralPath)shape).lineTo(8.5, 14.5);
((GeneralPath)shape).lineTo(8.5, 32.46875);
((GeneralPath)shape).lineTo(21.4375, 32.46875);
((GeneralPath)shape).lineTo(21.4375, 40.5);
((GeneralPath)shape).lineTo(39.50379, 25.125);
((GeneralPath)shape).lineTo(39.50379, 40.498104);
((GeneralPath)shape).lineTo(45.5, 40.498104);
((GeneralPath)shape).lineTo(45.5, 6.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(58, 115, 4, 255);
stroke = new BasicStroke(1.0000004f,1,1,10.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(45.5, 6.5);
((GeneralPath)shape).lineTo(39.50379, 6.5);
((GeneralPath)shape).lineTo(39.50379, 21.625);
((GeneralPath)shape).lineTo(21.5, 6.53125);
((GeneralPath)shape).lineTo(21.4375, 14.5);
((GeneralPath)shape).lineTo(8.5, 14.5);
((GeneralPath)shape).lineTo(8.5, 32.46875);
((GeneralPath)shape).lineTo(21.4375, 32.46875);
((GeneralPath)shape).lineTo(21.4375, 40.5);
((GeneralPath)shape).lineTo(39.50379, 25.125);
((GeneralPath)shape).lineTo(39.50379, 40.498104);
((GeneralPath)shape).lineTo(45.5, 40.498104);
((GeneralPath)shape).lineTo(45.5, 6.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_1);
g.setComposite(AlphaComposite.getInstance(3, 0.5080214f * origAlpha));
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2
paint = new RadialGradientPaint(new Point2D.Double(18.968265533447266, 3.0045177936553955), 17.171415f, new Point2D.Double(18.968265533447266, 3.0045177936553955), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.04672908782959f, -3.7494270752236227E-16f, 2.853404124580298E-16f, 1.557610034942627f, -19.663209915161133f, 2.389970064163208f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(21.81717, 7.1863875);
((GeneralPath)shape).lineTo(21.81717, 15.149112);
((GeneralPath)shape).lineTo(9.0, 15.149112);
((GeneralPath)shape).lineTo(9.0, 24.033575);
((GeneralPath)shape).curveTo(23.75, 28.283575, 28.133537, 18.203287, 44.883537, 23.203287);
((GeneralPath)shape).lineTo(44.866943, 7.105312);
((GeneralPath)shape).lineTo(40.037853, 7.069837);
((GeneralPath)shape).lineTo(40.021263, 22.721863);
((GeneralPath)shape).lineTo(21.81717, 7.1863875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_2);
g.setComposite(AlphaComposite.getInstance(3, 0.4812834f * origAlpha));
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(1.0000004f,0,0,10.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(9.5, 15.448055);
((GeneralPath)shape).lineTo(9.5, 31.347898);
((GeneralPath)shape).lineTo(22.49295, 31.347898);
((GeneralPath)shape).lineTo(22.49295, 38.156895);
((GeneralPath)shape).lineTo(39.519936, 23.654839);
((GeneralPath)shape).lineTo(39.48797, 22.764585);
((GeneralPath)shape).lineTo(22.44953, 8.5);
((GeneralPath)shape).lineTo(22.471626, 15.320369);
((GeneralPath)shape).lineTo(9.5, 15.448055);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_3);
g.setComposite(AlphaComposite.getInstance(3, 0.48099998f * origAlpha));
AffineTransform defaultTransform__0_0_4 = g.getTransform();
g.transform(new AffineTransform(0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f));
// _0_0_4
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(1.0000002f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(7.5, -44.50946807861328, 32.01188278198242, 4.009467124938965);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_4);
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
        return 3;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public  int getOrigY() {
        return 6;
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
        return 40;
    }
}

