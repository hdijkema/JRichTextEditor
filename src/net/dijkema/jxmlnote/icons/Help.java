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

/**
 * This class has been automatically generated using <a
 * href="https://flamingo.dev.java.net">Flamingo SVG transcoder</a>.
 */
public class Help implements FlamencoIconAdapter {
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
g.setComposite(AlphaComposite.getInstance(3, 0.40909088f * origAlpha));
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(2.1829121112823486f, 0.0f, 0.0f, 2.1829121112823486f, -13.5037202835083f, -14.350119590759277f));
// _0_0_0
paint = new RadialGradientPaint(new Point2D.Double(17.3125, 25.53125), 9.6875f, new Point2D.Double(17.3125, 25.53125), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.35161298513412476f, 0.0f, 16.55413055419922f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(27.0, 25.53125);
((GeneralPath)shape).curveTo(27.0, 27.41247, 22.66276, 28.9375, 17.3125, 28.9375);
((GeneralPath)shape).curveTo(11.962241, 28.9375, 7.625, 27.41247, 7.625, 25.53125);
((GeneralPath)shape).curveTo(7.625, 23.65003, 11.962241, 22.125, 17.3125, 22.125);
((GeneralPath)shape).curveTo(22.66276, 22.125, 27.0, 23.65003, 27.0, 25.53125);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(0.9446300268173218f, 0.0f, 0.0f, 0.9800530076026917f, 1.504173994064331f, -1.5569119453430176f));
// _0_0_1
paint = new Color(245, 121, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(46.138718, 23.42804);
((GeneralPath)shape).curveTo(46.138718, 35.14377, 36.285088, 44.641243, 24.130018, 44.641243);
((GeneralPath)shape).curveTo(11.974949, 44.641243, 2.1213188, 35.14377, 2.1213188, 23.42804);
((GeneralPath)shape).curveTo(2.1213188, 11.712311, 11.974949, 2.2148361, 24.130018, 2.2148361);
((GeneralPath)shape).curveTo(36.285088, 2.2148361, 46.138718, 11.712311, 46.138718, 23.42804);
((GeneralPath)shape).lineTo(46.138718, 23.42804);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(145, 73, 0, 255);
stroke = new BasicStroke(1.0f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(46.138718, 23.42804);
((GeneralPath)shape).curveTo(46.138718, 35.14377, 36.285088, 44.641243, 24.130018, 44.641243);
((GeneralPath)shape).curveTo(11.974949, 44.641243, 2.1213188, 35.14377, 2.1213188, 23.42804);
((GeneralPath)shape).curveTo(2.1213188, 11.712311, 11.974949, 2.2148361, 24.130018, 2.2148361);
((GeneralPath)shape).curveTo(36.285088, 2.2148361, 46.138718, 11.712311, 46.138718, 23.42804);
((GeneralPath)shape).lineTo(46.138718, 23.42804);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(0.9140859842300415f, 0.0f, 0.0f, 0.9483640193939209f, 2.3805758953094482f, -0.9058150053024292f));
// _0_0_2
paint = new Color(252, 175, 62, 255);
stroke = new BasicStroke(1.0f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(46.138718, 23.42804);
((GeneralPath)shape).curveTo(46.138718, 35.14377, 36.285088, 44.641243, 24.130018, 44.641243);
((GeneralPath)shape).curveTo(11.974949, 44.641243, 2.1213188, 35.14377, 2.1213188, 23.42804);
((GeneralPath)shape).curveTo(2.1213188, 11.712311, 11.974949, 2.2148361, 24.130018, 2.2148361);
((GeneralPath)shape).curveTo(36.285088, 2.2148361, 46.138718, 11.712311, 46.138718, 23.42804);
((GeneralPath)shape).lineTo(46.138718, 23.42804);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_2);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(24.671131, 9.525544);
((GeneralPath)shape).curveTo(23.76643, 9.525565, 22.836452, 9.641486, 21.868017, 9.848951);
((GeneralPath)shape).curveTo(20.900177, 10.056332, 19.834698, 10.354652, 18.664474, 10.789766);
((GeneralPath)shape).curveTo(18.655607, 10.788636, 18.646645, 10.788636, 18.637775, 10.789766);
((GeneralPath)shape).curveTo(18.627466, 10.797891, 18.618452, 10.80782, 18.611067, 10.819171);
((GeneralPath)shape).curveTo(18.600773, 10.827287, 18.591751, 10.837221, 18.584383, 10.848571);
((GeneralPath)shape).curveTo(18.583357, 10.858342, 18.583357, 10.868198, 18.584383, 10.877968);
((GeneralPath)shape).curveTo(18.583357, 10.887743, 18.583357, 10.897602, 18.584383, 10.907373);
((GeneralPath)shape).lineTo(18.584383, 15.758464);
((GeneralPath)shape).curveTo(18.583357, 15.768244, 18.583357, 15.778108, 18.584383, 15.787869);
((GeneralPath)shape).curveTo(18.583357, 15.797631, 18.583357, 15.807514, 18.584383, 15.817271);
((GeneralPath)shape).curveTo(18.591751, 15.828617, 18.600773, 15.83855, 18.611067, 15.846678);
((GeneralPath)shape).curveTo(18.618452, 15.858014, 18.627466, 15.867948, 18.637775, 15.876079);
((GeneralPath)shape).curveTo(18.646645, 15.877214, 18.655607, 15.877214, 18.664474, 15.876079);
((GeneralPath)shape).curveTo(18.673342, 15.877214, 18.682293, 15.877214, 18.691158, 15.876079);
((GeneralPath)shape).curveTo(18.700031, 15.877214, 18.708994, 15.877214, 18.717867, 15.876079);
((GeneralPath)shape).curveTo(18.726732, 15.877214, 18.735687, 15.877214, 18.74455, 15.876079);
((GeneralPath)shape).curveTo(19.720528, 15.199586, 20.652464, 14.683917, 21.547676, 14.347247);
((GeneralPath)shape).curveTo(22.443075, 14.000312, 23.293068, 13.818046, 24.057116, 13.81803);
((GeneralPath)shape).curveTo(24.868015, 13.818046, 25.462345, 14.016115, 25.899158, 14.406043);
((GeneralPath)shape).curveTo(26.333752, 14.784335, 26.566559, 15.330911, 26.566566, 16.023071);
((GeneralPath)shape).curveTo(26.56656, 16.47529, 26.443396, 16.918182, 26.192818, 17.375498);
((GeneralPath)shape).curveTo(25.951069, 17.833693, 25.560432, 18.345013, 25.01818, 18.87493);
((GeneralPath)shape).lineTo(24.083815, 19.75695);
((GeneralPath)shape).curveTo(23.057428, 20.773487, 22.384443, 21.630138, 22.0549, 22.314802);
((GeneralPath)shape).curveTo(21.727615, 22.984138, 21.57436, 23.744204, 21.574362, 24.608046);
((GeneralPath)shape).lineTo(21.574362, 25.372454);
((GeneralPath)shape).curveTo(21.573334, 25.382227, 21.573334, 25.392094, 21.574362, 25.401861);
((GeneralPath)shape).curveTo(21.573334, 25.411629, 21.573334, 25.42149, 21.574362, 25.431261);
((GeneralPath)shape).curveTo(21.581736, 25.442604, 21.590748, 25.45254, 21.601057, 25.460665);
((GeneralPath)shape).curveTo(21.608423, 25.472008, 21.617453, 25.481943, 21.627762, 25.49006);
((GeneralPath)shape).curveTo(21.636623, 25.4912, 21.645584, 25.4912, 21.654451, 25.49006);
((GeneralPath)shape).curveTo(21.663322, 25.4912, 21.672283, 25.4912, 21.681156, 25.49006);
((GeneralPath)shape).lineTo(26.886929, 25.49006);
((GeneralPath)shape).curveTo(26.895794, 25.4912, 26.904747, 25.4912, 26.913618, 25.49006);
((GeneralPath)shape).curveTo(26.922493, 25.4912, 26.931444, 25.4912, 26.940315, 25.49006);
((GeneralPath)shape).curveTo(26.950613, 25.481943, 26.959639, 25.472008, 26.967012, 25.460665);
((GeneralPath)shape).curveTo(26.977322, 25.45254, 26.986334, 25.442604, 26.99371, 25.431261);
((GeneralPath)shape).curveTo(26.99475, 25.42149, 26.99475, 25.411629, 26.99371, 25.401861);
((GeneralPath)shape).curveTo(26.99475, 25.392094, 26.99475, 25.382227, 26.99371, 25.372454);
((GeneralPath)shape).lineTo(26.99371, 24.666847);
((GeneralPath)shape).curveTo(26.993702, 24.226358, 27.12046, 23.826828, 27.314062, 23.461418);
((GeneralPath)shape).curveTo(27.503977, 23.093292, 27.918962, 22.567303, 28.568796, 21.932592);
((GeneralPath)shape).lineTo(29.476465, 21.050573);
((GeneralPath)shape).curveTo(30.387632, 20.126402, 31.040367, 19.277456, 31.425291, 18.463324);
((GeneralPath)shape).curveTo(31.809092, 17.641003, 32.012592, 16.700775, 32.012615, 15.670273);
((GeneralPath)shape).curveTo(32.012592, 13.669839, 31.377216, 12.157224, 30.117172, 11.113176);
((GeneralPath)shape).curveTo(28.856949, 10.058501, 27.036425, 9.525565, 24.671131, 9.525544);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(21.627762, 27.5481);
((GeneralPath)shape).curveTo(21.617453, 27.556227, 21.608423, 27.566158, 21.601057, 27.577496);
((GeneralPath)shape).curveTo(21.590748, 27.58562, 21.581736, 27.59555, 21.574362, 27.606903);
((GeneralPath)shape).curveTo(21.573334, 27.616674, 21.573334, 27.626534, 21.574362, 27.636307);
((GeneralPath)shape).curveTo(21.573334, 27.646076, 21.573334, 27.655935, 21.574362, 27.66571);
((GeneralPath)shape).lineTo(21.574362, 33.16361);
((GeneralPath)shape).curveTo(21.573334, 33.173378, 21.573334, 33.18325, 21.574362, 33.193012);
((GeneralPath)shape).curveTo(21.573334, 33.202766, 21.573334, 33.212654, 21.574362, 33.222416);
((GeneralPath)shape).curveTo(21.581736, 33.23376, 21.590748, 33.243694, 21.601057, 33.251816);
((GeneralPath)shape).curveTo(21.608423, 33.263165, 21.617453, 33.2731, 21.627762, 33.281216);
((GeneralPath)shape).curveTo(21.636623, 33.282356, 21.645584, 33.282356, 21.654451, 33.281216);
((GeneralPath)shape).curveTo(21.663322, 33.282356, 21.672283, 33.282356, 21.681156, 33.281216);
((GeneralPath)shape).lineTo(26.886929, 33.281216);
((GeneralPath)shape).curveTo(26.895794, 33.282356, 26.904747, 33.282356, 26.913618, 33.281216);
((GeneralPath)shape).curveTo(26.922493, 33.282356, 26.931444, 33.282356, 26.940315, 33.281216);
((GeneralPath)shape).curveTo(26.950613, 33.2731, 26.959639, 33.263165, 26.967012, 33.251816);
((GeneralPath)shape).curveTo(26.977322, 33.243694, 26.986334, 33.23376, 26.99371, 33.222416);
((GeneralPath)shape).curveTo(26.99475, 33.212654, 26.99475, 33.202766, 26.99371, 33.193012);
((GeneralPath)shape).curveTo(26.99475, 33.18325, 26.99475, 33.173378, 26.99371, 33.16361);
((GeneralPath)shape).lineTo(26.99371, 27.66571);
((GeneralPath)shape).curveTo(26.99475, 27.655935, 26.99475, 27.646076, 26.99371, 27.636307);
((GeneralPath)shape).curveTo(26.99475, 27.626534, 26.99475, 27.616674, 26.99371, 27.606903);
((GeneralPath)shape).curveTo(26.986334, 27.59555, 26.977322, 27.58562, 26.967012, 27.577496);
((GeneralPath)shape).curveTo(26.959639, 27.566158, 26.950613, 27.556227, 26.940315, 27.5481);
((GeneralPath)shape).curveTo(26.931444, 27.546968, 26.922493, 27.546968, 26.913618, 27.5481);
((GeneralPath)shape).curveTo(26.904747, 27.546968, 26.895794, 27.546968, 26.886929, 27.5481);
((GeneralPath)shape).lineTo(21.681156, 27.5481);
((GeneralPath)shape).curveTo(21.672283, 27.546968, 21.663322, 27.546968, 21.654451, 27.5481);
((GeneralPath)shape).curveTo(21.645584, 27.546968, 21.636623, 27.546968, 21.627762, 27.5481);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_3);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4
paint = new Color(255, 254, 255, 55);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(43.676426, 20.47678);
((GeneralPath)shape).curveTo(43.676426, 31.307396, 37.624256, 16.170582, 25.001688, 20.863169);
((GeneralPath)shape).curveTo(12.279172, 25.592913, 4.4350533, 31.307396, 4.4350533, 20.47678);
((GeneralPath)shape).curveTo(4.4350533, 9.646163, 13.22512, 0.8560977, 24.05574, 0.8560977);
((GeneralPath)shape).curveTo(34.88636, 0.8560977, 43.676426, 9.646163, 43.676426, 20.47678);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
        return 4;
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
        return 43;
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

