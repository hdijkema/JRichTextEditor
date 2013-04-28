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


import java.awt.*;
import java.awt.geom.*;

/**
 * This class has been automatically generated using <a
 * href="https://flamingo.dev.java.net">Flamingo SVG transcoder</a>.
 */
public class Close implements FlamencoIconAdapter{
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
g.setComposite(AlphaComposite.getInstance(3, 0.40641713f * origAlpha));
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(2.5158157348632812f, 0.0f, 0.0f, 2.5158157348632812f, -51.54910659790039f, -70.57294464111328f));
// _0_0_0
paint = new RadialGradientPaint(new Point2D.Double(30.203561782836914, 44.56548309326172), 6.5659914f, new Point2D.Double(30.203561782836914, 44.56548309326172), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.3384619951248169f, 0.0f, 29.481779098510742f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(36.769554, 44.565483);
((GeneralPath)shape).curveTo(36.769554, 45.792847, 33.82986, 46.78782, 30.203564, 46.78782);
((GeneralPath)shape).curveTo(26.577267, 46.78782, 23.637571, 45.792847, 23.637571, 44.565483);
((GeneralPath)shape).curveTo(23.637571, 43.33812, 26.577267, 42.343147, 30.203564, 42.343147);
((GeneralPath)shape).curveTo(33.82986, 42.343147, 36.769554, 43.33812, 36.769554, 44.565483);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1
paint = new RadialGradientPaint(new Point2D.Double(24.445690155029297, 35.878170013427734), 20.530962f, new Point2D.Double(24.445690155029297, 35.878170013427734), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(220, 220, 220, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0402839183807373f, 0.0f, 0.0f, 2.0402839183807373f, -25.895885467529297f, -37.578712463378906f));
shape = new RoundRectangle2D.Double(3.495220899581909, 2.5346779823303223, 40.97008514404297, 40.97008514404297, 11.15695858001709, 11.15695858001709);
g.setPaint(paint);
g.fill(shape);
paint = new Color(155, 155, 155, 255);
stroke = new BasicStroke(1.022669f,0,2,10.0f,null,0.0f);
shape = new RoundRectangle2D.Double(3.495220899581909, 2.5346779823303223, 40.97008514404297, 40.97008514404297, 11.15695858001709, 11.15695858001709);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(1.0226687f,0,2,10.0f,null,0.0f);
shape = new RoundRectangle2D.Double(4.70469331741333, 3.744150400161743, 38.55112838745117, 38.55112838745117, 8.677630424499512, 8.677630424499512);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_2);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3
paint = new Color(204, 204, 204, 255);
shape = new RoundRectangle2D.Double(7.0506672859191895, 6.201202869415283, 33.85917663574219, 33.63701629638672, 5.612478733062744, 5.612478733062744);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_3);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_4 = g.getTransform();
g.transform(new AffineTransform(0.7339153289794922f, 0.0f, 0.0f, 0.7339153289794922f, 6.018784046173096f, 5.681077480316162f));
// _0_0_4
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_4_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4_0
paint = new Color(32, 74, 135, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.25, 15.25);
((GeneralPath)shape).lineTo(32.5, 31.5);
g.setPaint(paint);
g.fill(shape);
paint = new Color(238, 238, 236, 255);
stroke = new BasicStroke(6.967213f,1,1,10.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.25, 15.25);
((GeneralPath)shape).lineTo(32.5, 31.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_4_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_4_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4_1
paint = new Color(32, 74, 135, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.5, 15.25);
((GeneralPath)shape).lineTo(16.25, 31.5);
g.setPaint(paint);
g.fill(shape);
paint = new Color(238, 238, 236, 255);
stroke = new BasicStroke(6.967213f,1,1,10.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.5, 15.25);
((GeneralPath)shape).lineTo(16.25, 31.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_4_1);
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
        return 3;
    }

    /**
     * Returns the width of the bounding box of the original SVG image.
     * 
     * @return The width of the bounding box of the original SVG image.
     */
    public  int getOrigWidth() {
        return 42;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * 
     * @return The height of the bounding box of the original SVG image.
     */
    public  int getOrigHeight() {
        return 46;
    }
}

