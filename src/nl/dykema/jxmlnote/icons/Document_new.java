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
public class Document_new implements FlamencoIconAdapter {
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
g.setComposite(AlphaComposite.getInstance(3, 0.47252747f * origAlpha));
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.1731840372085571f, 0.0f, 0.0f, 0.6862750053405762f, -4.651497840881348f, 14.26159954071045f));
// _0_0_0
paint = new RadialGradientPaint(new Point2D.Double(24.306795120239258, 42.077980041503906), 15.821514f, new Point2D.Double(24.306795120239258, 42.077980041503906), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.2849160134792328f, -6.340413775299638E-16f, 30.089279174804688f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(40.128307, 42.07798);
((GeneralPath)shape).curveTo(40.130272, 43.688835, 37.115135, 45.17757, 32.219135, 45.98316);
((GeneralPath)shape).curveTo(27.323137, 46.78875, 21.290451, 46.78875, 16.394451, 45.98316);
((GeneralPath)shape).curveTo(11.498452, 45.17757, 8.483318, 43.688835, 8.485281, 42.07798);
((GeneralPath)shape).curveTo(8.483318, 40.467125, 11.498452, 38.97839, 16.394451, 38.172802);
((GeneralPath)shape).curveTo(21.290451, 37.36721, 27.323137, 37.36721, 32.219135, 38.172802);
((GeneralPath)shape).curveTo(37.115135, 38.97839, 40.130272, 40.467125, 40.128307, 42.07798);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0);
g.setTransform(defaultTransform__0_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_0
paint = new RadialGradientPaint(new Point2D.Double(33.966678619384766, 35.736915588378906), 86.70845f, new Point2D.Double(33.966678619384766, 35.736915588378906), new float[] {0.0f,1.0f}, new Color[] {new Color(250, 250, 250, 255),new Color(187, 187, 187, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9604930281639099f, 0.0f, 0.0f, 1.0411319732666016f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(7.7526016, 3.6464462);
((GeneralPath)shape).lineTo(31.199615, 3.6308212);
((GeneralPath)shape).curveTo(31.199615, 3.6308212, 41.478554, 13.174533, 41.478554, 13.811106);
((GeneralPath)shape).lineTo(41.478554, 43.417892);
((GeneralPath)shape).curveTo(41.478554, 44.054466, 40.966076, 44.56694, 40.329502, 44.56694);
((GeneralPath)shape).lineTo(7.7526016, 44.56694);
((GeneralPath)shape).curveTo(7.116029, 44.56694, 6.603553, 44.054466, 6.603553, 43.417892);
((GeneralPath)shape).lineTo(6.603553, 4.795495);
((GeneralPath)shape).curveTo(6.603553, 4.1589217, 7.116029, 3.6464462, 7.7526016, 3.6464462);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new RadialGradientPaint(new Point2D.Double(8.824419021606445, 3.7561285495758057), 37.751713f, new Point2D.Double(8.824419021606445, 3.7561285495758057), new float[] {0.0f,1.0f}, new Color[] {new Color(163, 163, 163, 255),new Color(138, 138, 138, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9682729840278625f, 0.0f, 0.0f, 1.0327670574188232f, 3.353553056716919f, 0.6464470028877258f));
stroke = new BasicStroke(1.0f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(7.7526016, 3.6464462);
((GeneralPath)shape).lineTo(31.199615, 3.6308212);
((GeneralPath)shape).curveTo(31.199615, 3.6308212, 41.478554, 13.174533, 41.478554, 13.811106);
((GeneralPath)shape).lineTo(41.478554, 43.417892);
((GeneralPath)shape).curveTo(41.478554, 44.054466, 40.966076, 44.56694, 40.329502, 44.56694);
((GeneralPath)shape).lineTo(7.7526016, 44.56694);
((GeneralPath)shape).curveTo(7.116029, 44.56694, 6.603553, 44.054466, 6.603553, 43.417892);
((GeneralPath)shape).lineTo(6.603553, 4.795495);
((GeneralPath)shape).curveTo(6.603553, 4.1589217, 7.116029, 3.6464462, 7.7526016, 3.6464462);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_1_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_1_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_1
paint = new RadialGradientPaint(new Point2D.Double(8.143556594848633, 7.26789665222168), 38.158695f, new Point2D.Double(8.143556594848633, 7.26789665222168), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(248, 248, 248, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9682729840278625f, 0.0f, 0.0f, 1.0327670574188232f, 3.353553056716919f, 0.6464470028877258f));
stroke = new BasicStroke(1.0f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(7.8151026, 4.583946);
((GeneralPath)shape).lineTo(32.691494, 4.583946);
((GeneralPath)shape).curveTo(32.691494, 4.583946, 40.44194, 12.605373, 40.44194, 12.687946);
((GeneralPath)shape).lineTo(40.44194, 43.381283);
((GeneralPath)shape).curveTo(40.44194, 43.463856, 40.375465, 43.53033, 40.292892, 43.53033);
((GeneralPath)shape).lineTo(7.8151026, 43.53033);
((GeneralPath)shape).curveTo(7.7325296, 43.53033, 7.666054, 43.463856, 7.666054, 43.381283);
((GeneralPath)shape).lineTo(7.666054, 4.732995);
((GeneralPath)shape).curveTo(7.666054, 4.650422, 7.7325296, 4.583946, 7.8151026, 4.583946);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_1_1);
g.setTransform(defaultTransform__0_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_2
g.setComposite(AlphaComposite.getInstance(3, 0.35714284f * origAlpha));
AffineTransform defaultTransform__0_2_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_2_0
paint = new RadialGradientPaint(new Point2D.Double(37.03035354614258, 12.989150047302246), 4.2929163f, new Point2D.Double(37.03035354614258, 12.989150047302246), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.7446529865264893f, 2.31355106799301E-22f, -1.662999946215822E-22f, 1.2838330268859863f, -26.58255958557129f, -3.4783589839935303f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(40.985188, 13.861445);
((GeneralPath)shape).curveTo(40.25683, 12.514817, 34.88222, 10.130934, 32.084637, 9.3314085);
((GeneralPath)shape).curveTo(32.254143, 10.904354, 31.961857, 15.649439, 31.961857, 15.649439);
((GeneralPath)shape).curveTo(34.024357, 14.274439, 40.204487, 13.699331, 40.985188, 13.861445);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_2_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_2_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_2_1
paint = new RadialGradientPaint(new Point2D.Double(30.65381622314453, 14.937299728393555), 86.70845f, new Point2D.Double(30.65381622314453, 14.937299728393555), new float[] {0.0f,1.0f}, new Color[] {new Color(250, 250, 250, 255),new Color(187, 187, 187, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.14835500717163086f, 0.010091369971632957f, -0.011044380255043507f, 0.16236500442028046f, 25.060110092163086f, 12.817060470581055f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(41.41056, 13.739267);
((GeneralPath)shape).curveTo(41.423725, 12.324125, 35.058025, 3.5320141, 31.175442, 3.6354935);
((GeneralPath)shape).curveTo(32.14848, 3.8684883, 32.925797, 9.803523, 31.536076, 12.616023);
((GeneralPath)shape).curveTo(34.286076, 12.616023, 40.446693, 11.881093, 41.41056, 13.739267);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new RadialGradientPaint(new Point2D.Double(31.863327026367188, 2.3667306900024414), 37.751713f, new Point2D.Double(31.863327026367188, 2.3667306900024414), new float[] {0.0f,1.0f}, new Color[] {new Color(163, 163, 163, 255),new Color(138, 138, 138, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.3317349851131439f, -2.344900037707571E-17f, 2.5010869574808535E-17f, 0.3538309931755066f, 20.105260848999023f, 9.582300186157227f));
stroke = new BasicStroke(1.0f,0,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(41.41056, 13.739267);
((GeneralPath)shape).curveTo(41.423725, 12.324125, 35.058025, 3.5320141, 31.175442, 3.6354935);
((GeneralPath)shape).curveTo(32.14848, 3.8684883, 32.925797, 9.803523, 31.536076, 12.616023);
((GeneralPath)shape).curveTo(34.286076, 12.616023, 40.446693, 11.881093, 41.41056, 13.739267);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_2_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_2_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_2_2
paint = new LinearGradientPaint(new Point2D.Double(33.39600372314453, 36.92133331298828), new Point2D.Double(34.170047760009766, 38.07038116455078), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.032779380679130554f, -0.9994630217552185f, 0.9994630217552185f, -0.032779380679130554f, -0.7096459865570068f, 45.062740325927734f));
stroke = new BasicStroke(1.0000002f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(39.121563, 11.586207);
((GeneralPath)shape).curveTo(38.3932, 10.239579, 34.963028, 6.516658, 33.04044, 5.2796316);
((GeneralPath)shape).curveTo(33.27938, 6.7054806, 33.577496, 8.96206, 32.961857, 11.524439);
((GeneralPath)shape).curveTo(32.961857, 11.524439, 38.34086, 11.424093, 39.121563, 11.586207);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_2_2);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_2_3 = g.getTransform();
g.transform(new AffineTransform(1.1498500108718872f, 0.0f, 0.0f, 1.1498500108718872f, -7.595327854156494f, 0.49089500308036804f));
// _0_2_3
paint = new RadialGradientPaint(new Point2D.Double(38.65885543823242, 9.341144561767578), 8.341651f, new Point2D.Double(38.65885543823242, 9.341144561767578), new float[] {0.0f,0.25f,0.5f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 222, 234),new Color(245, 243, 40, 255),new Color(245, 243, 45, 31)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(47.000507, 9.341145);
((GeneralPath)shape).curveTo(47.000507, 13.948112, 43.265823, 17.682796, 38.658855, 17.682796);
((GeneralPath)shape).curveTo(34.051888, 17.682796, 30.317204, 13.948112, 30.317204, 9.341145);
((GeneralPath)shape).curveTo(30.317204, 4.7341776, 34.051888, 0.99949265, 38.658855, 0.99949265);
((GeneralPath)shape).curveTo(43.265823, 0.99949265, 47.000507, 4.7341776, 47.000507, 9.341145);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_2_3);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_2_4 = g.getTransform();
g.transform(new AffineTransform(0.6741160154342651f, 0.299576997756958f, -0.299576997756958f, 0.6741160154342651f, 15.464130401611328f, -7.192469120025635f));
// _0_2_4
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.520054, 15.50279);
((GeneralPath)shape).curveTo(44.012882, 16.381235, 39.92535, 15.341967, 38.998703, 15.754538);
((GeneralPath)shape).curveTo(38.072056, 16.167109, 36.109287, 19.900143, 35.11711, 19.689249);
((GeneralPath)shape).curveTo(34.124935, 19.478355, 33.850224, 15.26973, 33.171494, 14.515926);
((GeneralPath)shape).curveTo(32.492767, 13.762123, 28.335913, 13.048993, 28.229885, 12.040207);
((GeneralPath)shape).curveTo(28.123857, 11.031421, 32.041607, 9.469617, 32.54878, 8.59117);
((GeneralPath)shape).curveTo(33.05595, 7.7127237, 32.44964, 3.5389507, 33.376286, 3.1263807);
((GeneralPath)shape).curveTo(34.302933, 2.7138102, 36.998947, 5.957187, 37.991127, 6.168081);
((GeneralPath)shape).curveTo(38.983303, 6.3789744, 42.765434, 4.512571, 43.444164, 5.266374);
((GeneralPath)shape).curveTo(44.12289, 6.0201774, 41.871372, 9.586499, 41.977398, 10.595285);
((GeneralPath)shape).curveTo(42.083427, 11.604071, 45.027225, 14.624343, 44.520054, 15.50279);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_2_4);
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
        return 6;
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
        return 42;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * 
     * @return The height of the bounding box of the original SVG image.
     */
    public  int getOrigHeight() {
        return 45;
    }
}

