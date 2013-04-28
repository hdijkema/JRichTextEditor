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
public class NoImage implements FlamencoIconAdapter {
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -0.7544785737991333f, -3.45806884765625f));
// _0_0
paint = new Color(255, 255, 254, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(55.224354, 159.5521);
((GeneralPath)shape).curveTo(99.08121, 57.822712, 221.48985, 9.101504, 328.6466, 50.724316);
((GeneralPath)shape).curveTo(435.80334, 92.34712, 487.13913, 208.55576, 443.31454, 310.29767);
((GeneralPath)shape).curveTo(399.48993, 412.03958, 277.09674, 460.79578, 169.9268, 419.20358);
((GeneralPath)shape).curveTo(62.756878, 377.61142, 11.38424, 261.41745, 55.17657, 159.66302);
g.setPaint(paint);
g.fill(shape);
paint = new Color(255, 0, 0, 255);
stroke = new BasicStroke(40.0f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(55.224354, 159.5521);
((GeneralPath)shape).curveTo(99.08121, 57.822712, 221.48985, 9.101504, 328.6466, 50.724316);
((GeneralPath)shape).curveTo(435.80334, 92.34712, 487.13913, 208.55576, 443.31454, 310.29767);
((GeneralPath)shape).curveTo(399.48993, 412.03958, 277.09674, 460.79578, 169.9268, 419.20358);
((GeneralPath)shape).curveTo(62.756878, 377.61142, 11.38424, 261.41745, 55.17657, 159.66302);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1
paint = new LinearGradientPaint(new Point2D.Double(295.0257873535156, 180.7627410888672), new Point2D.Double(222.90087890625, 197.0261993408203), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 0, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(246.99619, 112.27504);
((GeneralPath)shape).curveTo(246.99619, 112.27504, 220.83324, 110.15371, 213.76218, 129.2456);
((GeneralPath)shape).curveTo(206.69112, 148.33748, 251.23883, 301.77966, 251.23883, 301.77966);
((GeneralPath)shape).curveTo(251.23883, 301.77966, 292.65628, 142.86914, 282.35153, 124.29585);
((GeneralPath)shape).curveTo(274.1535, 109.51972, 247.7033, 112.27504, 246.99619, 112.27504);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_2 = g.getTransform();
g.transform(new AffineTransform(3.2495810985565186f, 0.0f, 0.0f, 3.159604787826538f, -535.93359375f, -723.4107666015625f));
// _0_2
paint = new RadialGradientPaint(new Point2D.Double(244.3710479736328, 340.02447509765625), 8.486861f, new Point2D.Double(244.3710479736328, 340.02447509765625), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 0, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.1281938999891281f, -1.041663646697998f, 0.9686771035194397f, -0.12609800696372986f, -53.17018127441406f, 636.3316040039062f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(233.97768, 335.55438);
((GeneralPath)shape).curveTo(235.75967, 331.0401, 240.71718, 328.88498, 245.05116, 330.74057);
((GeneralPath)shape).curveTo(249.38516, 332.59613, 251.4548, 337.7599, 249.6741, 342.27472);
((GeneralPath)shape).curveTo(247.8934, 346.78958, 242.93652, 348.94623, 238.60199, 347.092);
((GeneralPath)shape).curveTo(234.26747, 345.2378, 232.19633, 340.0747, 233.97574, 335.5593);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_2);
g.setTransform(defaultTransform__0);
g.setTransform(defaultTransform_);

	}

    /**
     * Returns the X of the bounding box of the original SVG image.
     * 
     * @return The X of the bounding box of the original SVG image.
     */
    public  int getOrigX() {
        return 11;
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
        return 476;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * 
     * @return The height of the bounding box of the original SVG image.
     */
    public  int getOrigHeight() {
        return 463;
    }
}

