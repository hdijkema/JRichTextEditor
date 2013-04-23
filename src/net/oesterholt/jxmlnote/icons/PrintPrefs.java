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
public class PrintPrefs implements FlamencoIconAdapter {
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
g.transform(new AffineTransform(0.5833659172058105f, 0.0f, 0.0f, 0.5620610117912292f, 0.9987779855728149f, 0.13012300431728363f));
// _0_0_0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -0.3119190037250519f, -2.006727933883667f));
// _0_0_0_0
paint = new RadialGradientPaint(new Point2D.Double(24.0, 41.875), 19.125f, new Point2D.Double(24.0, 41.875), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.33333298563957214f, 0.0f, 27.916669845581055f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(43.125, 41.875);
((GeneralPath)shape).curveTo(43.125, 45.395817, 34.562447, 48.25, 24.0, 48.25);
((GeneralPath)shape).curveTo(13.437554, 48.25, 4.875, 45.395817, 4.875, 41.875);
((GeneralPath)shape).curveTo(4.875, 38.354183, 13.437554, 35.5, 24.0, 35.5);
((GeneralPath)shape).curveTo(34.562447, 35.5, 43.125, 38.354187, 43.125, 41.875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1
paint = new LinearGradientPaint(new Point2D.Double(1.8456430435180664, 88.29492950439453), new Point2D.Double(18.972126007080078, 88.29492950439453), new float[] {0.0f,0.27586207f,1.0f}, new Color[] {new Color(142, 141, 135, 255),new Color(203, 201, 193, 255),new Color(142, 141, 135, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.302720069885254f, 0.0f, 0.0f, 0.4379180073738098f, 0.0f, 0.5840340256690979f));
shape = new RoundRectangle2D.Double(4.75, 36.004188537597656, 38.4375, 6.491594314575195, 3.4230966567993164, 3.423095464706421);
g.setPaint(paint);
g.fill(shape);
paint = new Color(89, 89, 89, 255);
stroke = new BasicStroke(0.9999998f,0,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(4.75, 36.004188537597656, 38.4375, 6.491594314575195, 3.4230966567993164, 3.423095464706421);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_2
paint = new LinearGradientPaint(new Point2D.Double(1.8456430435180664, 88.29492950439453), new Point2D.Double(18.972126007080078, 88.29492950439453), new float[] {0.0f,1.0f}, new Color[] {new Color(220, 220, 218, 255),new Color(186, 185, 183, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.2918241024017334f, 0.0f, 0.0f, 0.4342690110206604f, 0.08855178952217102f, 2.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(7.130896, 21.5);
((GeneralPath)shape).lineTo(40.870617, 21.5);
((GeneralPath)shape).curveTo(41.25566, 21.5, 41.74765, 21.788155, 42.051052, 22.223919);
((GeneralPath)shape).curveTo(42.354454, 22.659683, 43.78752, 24.83394, 44.10945, 25.297964);
((GeneralPath)shape).curveTo(44.43138, 25.761988, 44.5024, 26.201853, 44.5024, 26.77405);
((GeneralPath)shape).lineTo(44.5024, 38.850952);
((GeneralPath)shape).curveTo(44.5024, 39.764526, 43.770405, 40.5, 42.861153, 40.5);
((GeneralPath)shape).lineTo(5.140362, 40.5);
((GeneralPath)shape).curveTo(4.2311115, 40.5, 3.499116, 39.764523, 3.499116, 38.850952);
((GeneralPath)shape).lineTo(3.499116, 26.77405);
((GeneralPath)shape).curveTo(3.499116, 26.280031, 3.600282, 25.571642, 3.9455223, 25.120718);
((GeneralPath)shape).curveTo(4.381169, 24.551712, 5.5498686, 22.57277, 5.8581295, 22.153118);
((GeneralPath)shape).curveTo(6.1663885, 21.733467, 6.732446, 21.5, 7.130896, 21.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(103, 103, 103, 255);
stroke = new BasicStroke(1.0000004f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(7.130896, 21.5);
((GeneralPath)shape).lineTo(40.870617, 21.5);
((GeneralPath)shape).curveTo(41.25566, 21.5, 41.74765, 21.788155, 42.051052, 22.223919);
((GeneralPath)shape).curveTo(42.354454, 22.659683, 43.78752, 24.83394, 44.10945, 25.297964);
((GeneralPath)shape).curveTo(44.43138, 25.761988, 44.5024, 26.201853, 44.5024, 26.77405);
((GeneralPath)shape).lineTo(44.5024, 38.850952);
((GeneralPath)shape).curveTo(44.5024, 39.764526, 43.770405, 40.5, 42.861153, 40.5);
((GeneralPath)shape).lineTo(5.140362, 40.5);
((GeneralPath)shape).curveTo(4.2311115, 40.5, 3.499116, 39.764523, 3.499116, 38.850952);
((GeneralPath)shape).lineTo(3.499116, 26.77405);
((GeneralPath)shape).curveTo(3.499116, 26.280031, 3.600282, 25.571642, 3.9455223, 25.120718);
((GeneralPath)shape).curveTo(4.381169, 24.551712, 5.5498686, 22.57277, 5.8581295, 22.153118);
((GeneralPath)shape).curveTo(6.1663885, 21.733467, 6.732446, 21.5, 7.130896, 21.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_2);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_3
paint = new Color(251, 251, 251, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(7.424621, 21.975533);
((GeneralPath)shape).curveTo(6.921893, 21.975533, 6.3048773, 22.053783, 6.0546017, 22.46703);
((GeneralPath)shape).lineTo(4.154252, 25.604816);
((GeneralPath)shape).curveTo(3.8721282, 26.070648, 4.188198, 26.868141, 5.0873103, 26.868141);
((GeneralPath)shape).lineTo(42.730785, 26.868141);
((GeneralPath)shape).curveTo(44.04073, 26.868141, 43.95053, 25.858072, 43.663845, 25.42804);
((GeneralPath)shape).lineTo(41.89608, 22.77639);
((GeneralPath)shape).curveTo(41.575546, 22.29559, 41.4592, 21.975533, 40.65864, 21.975533);
((GeneralPath)shape).lineTo(7.4246216, 21.975533);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_3);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_4
paint = new LinearGradientPaint(new Point2D.Double(15.387969017028809, 32.53923797607422), new Point2D.Double(15.487822532653809, 58.83126449584961), new float[] {0.0f,0.10344828f,1.0f}, new Color[] {new Color(255, 255, 255, 32),new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.4925689697265625f, 0.0f, 0.0f, 0.66874098777771f, 0.08188071846961975f, 2.0f));
stroke = new BasicStroke(0.9469671f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(7.60536, 22.445757);
((GeneralPath)shape).lineTo(40.43267, 22.445757);
((GeneralPath)shape).curveTo(40.798347, 22.445757, 41.26559, 22.71863, 41.55373, 23.131285);
((GeneralPath)shape).curveTo(41.84187, 23.54394, 42.84996, 25.160946, 43.155697, 25.60036);
((GeneralPath)shape).curveTo(43.461433, 26.039776, 43.591267, 26.456312, 43.591267, 26.998165);
((GeneralPath)shape).lineTo(43.591267, 38.279263);
((GeneralPath)shape).curveTo(43.591267, 39.144386, 43.457542, 39.52836, 42.59403, 39.52836);
((GeneralPath)shape).lineTo(5.5322227, 39.52836);
((GeneralPath)shape).curveTo(4.668707, 39.52836, 4.472601, 39.144386, 4.472601, 38.279263);
((GeneralPath)shape).lineTo(4.472601, 26.998165);
((GeneralPath)shape).curveTo(4.472601, 26.530346, 4.693446, 25.859524, 5.0213213, 25.432514);
((GeneralPath)shape).curveTo(5.435059, 24.893684, 6.103854, 23.461634, 6.3966103, 23.064238);
((GeneralPath)shape).curveTo(6.6893663, 22.666842, 7.2269516, 22.445757, 7.60536, 22.445757);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_4);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_5
paint = new LinearGradientPaint(new Point2D.Double(25.056711196899414, 3.6785457134246826), new Point2D.Double(24.78970718383789, 25.247310638427734), new float[] {0.0f,0.4054697f,0.5344828f,1.0f}, new Color[] {new Color(224, 224, 224, 255),new Color(255, 255, 255, 255),new Color(205, 205, 205, 255),new Color(73, 73, 73, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9457100033760071f, 0.0f, 0.0f, 1.076032042503357f, 0.05016683042049408f, 4.095404148101807f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.672962, 4.4999475);
((GeneralPath)shape).lineTo(36.32512, 4.4999475);
((GeneralPath)shape).curveTo(36.975883, 4.4999475, 37.499783, 5.010078, 37.499783, 5.6437373);
((GeneralPath)shape).lineTo(37.499783, 24.348177);
((GeneralPath)shape).lineTo(10.498301, 24.348177);
((GeneralPath)shape).lineTo(10.498301, 5.643738);
((GeneralPath)shape).curveTo(10.498301, 5.0100784, 11.0222, 4.499948, 11.672964, 4.499948);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(137, 137, 137, 255);
stroke = new BasicStroke(1.0000004f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.672962, 4.4999475);
((GeneralPath)shape).lineTo(36.32512, 4.4999475);
((GeneralPath)shape).curveTo(36.975883, 4.4999475, 37.499783, 5.010078, 37.499783, 5.6437373);
((GeneralPath)shape).lineTo(37.499783, 24.348177);
((GeneralPath)shape).lineTo(10.498301, 24.348177);
((GeneralPath)shape).lineTo(10.498301, 5.643738);
((GeneralPath)shape).curveTo(10.498301, 5.0100784, 11.0222, 4.499948, 11.672964, 4.499948);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_5);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_6
paint = new LinearGradientPaint(new Point2D.Double(20.771228790283203, 25.1402530670166), new Point2D.Double(20.71780014038086, 19.33746337890625), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 0),new Color(248, 248, 248, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.198768973350525f, 0.0f, 0.0f, 0.853564977645874f, -0.1430860012769699f, 2.034512996673584f));
stroke = new BasicStroke(1.0000002f,1,1,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(11.498513221740723, 5.499246597290039, 25.00057601928711, 18.836374282836914, 0.35355344414711, 0.35355350375175476);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_6);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_7
paint = new LinearGradientPaint(new Point2D.Double(10.33823299407959, 64.65225982666016), new Point2D.Double(10.33823299407959, 54.136138916015625), new float[] {0.0f,1.0f}, new Color[] {new Color(247, 246, 245, 255),new Color(247, 246, 245, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.3698439598083496f, 0.0f, 0.0f, 0.4219689965248108f, 0.0f, 2.0f));
shape = new RoundRectangle2D.Double(6.875, 27.375, 33.75, 5.1875, 3.4230966567993164, 3.4230966567993164);
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(9.731653213500977, 70.7249755859375), new Point2D.Double(9.705278396606445, 62.282466888427734), new float[] {0.0f,1.0f}, new Color[] {new Color(102, 102, 102, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.3698439598083496f, 0.0f, 0.0f, 0.4219689965248108f, 0.0f, 2.0f));
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(6.875, 27.375, 33.75, 5.1875, 3.4230966567993164, 3.4230966567993164);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_7);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 2.0f));
// _0_0_0_8
paint = new RadialGradientPaint(new Point2D.Double(9.129549026489258, 26.925594329833984), 2.1227016f, new Point2D.Double(9.129549026489258, 26.925594329833984), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(255, 255, 253, 255),new Color(187, 187, 185, 255),new Color(0, 0, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(10.871767, 27.626486);
((GeneralPath)shape).curveTo(10.871767, 28.33431, 10.297961, 28.908117, 9.590136, 28.908117);
((GeneralPath)shape).curveTo(8.88231, 28.908117, 8.308504, 28.33431, 8.308504, 27.626486);
((GeneralPath)shape).curveTo(8.308504, 26.918661, 8.88231, 26.344854, 9.590136, 26.344854);
((GeneralPath)shape).curveTo(10.297961, 26.344854, 10.871767, 26.918661, 10.871767, 27.626486);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_8);
g.setComposite(AlphaComposite.getInstance(3, 0.36571428f * origAlpha));
AffineTransform defaultTransform__0_0_0_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_9
paint = new LinearGradientPaint(new Point2D.Double(9.869808197021484, 57.2276496887207), new Point2D.Double(9.912813186645508, 72.06431579589844), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 60),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.7720859050750732f, 0.0f, 0.0f, 0.36073899269104004f, 0.6187180280685425f, 2.883882999420166f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.743718, 25.416054);
((GeneralPath)shape).lineTo(37.306217, 25.478554);
((GeneralPath)shape).curveTo(37.993713, 25.480253, 38.294037, 25.10756, 38.243717, 24.478554);
((GeneralPath)shape).lineTo(38.118717, 22.916054);
((GeneralPath)shape).lineTo(39.984833, 22.916054);
((GeneralPath)shape).curveTo(40.797333, 22.916054, 40.975033, 23.108616, 41.172333, 23.478554);
((GeneralPath)shape).lineTo(41.672333, 24.416054);
((GeneralPath)shape).curveTo(42.199127, 25.403793, 43.483505, 26.390165, 42.170494, 26.390165);
((GeneralPath)shape).curveTo(37.66778, 26.390165, 13.993717, 26.041054, 11.743717, 25.416054);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_9);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_10 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_10
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(0.99999994f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(43.488808, 26.5);
((GeneralPath)shape).lineTo(4.511181, 26.5);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_0_10);
g.setComposite(AlphaComposite.getInstance(3, 0.43575415f * origAlpha));
AffineTransform defaultTransform__0_0_0_11 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 2.0f));
// _0_0_0_11
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_11_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_11_0
paint = new Color(0, 0, 0, 75);
shape = new Rectangle2D.Double(14.0, 7.0, 19.0, 1.0);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_11_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_11_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_11_1
paint = new Color(0, 0, 0, 75);
shape = new Rectangle2D.Double(14.0, 9.0, 19.0, 1.0);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_11_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_11_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_11_2
paint = new Color(0, 0, 0, 75);
shape = new Rectangle2D.Double(14.0, 11.0, 19.0, 1.0);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_11_2);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_11_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_11_3
paint = new Color(0, 0, 0, 75);
shape = new Rectangle2D.Double(14.0, 13.0, 11.0, 1.0);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_11_3);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_11_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_11_4
paint = new Color(0, 0, 0, 75);
shape = new Rectangle2D.Double(14.0, 17.0, 19.0, 1.0);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_11_4);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_0_11_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_11_5
paint = new Color(0, 0, 0, 75);
shape = new Rectangle2D.Double(14.0, 19.0, 19.0, 1.0);
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_0_11_5);
g.setTransform(defaultTransform__0_0_0_11);
g.setTransform(defaultTransform__0_0_0);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1
g.setComposite(AlphaComposite.getInstance(3, 0.19886367f * origAlpha));
AffineTransform defaultTransform__0_0_1_0 = g.getTransform();
g.transform(new AffineTransform(0.751118004322052f, 0.0f, 0.0f, 0.578702986240387f, 17.040870666503906f, 19.36341094970703f));
// _0_0_1_0
paint = new RadialGradientPaint(new Point2D.Double(24.8125, 39.125), 17.6875f, new Point2D.Double(24.8125, 39.125), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.3745580017566681f, 7.272829020065711E-15f, 24.470409393310547f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(42.5, 39.125);
((GeneralPath)shape).curveTo(42.5, 42.783886, 34.581036, 45.75, 24.8125, 45.75);
((GeneralPath)shape).curveTo(15.043963, 45.75, 7.125, 42.783886, 7.125, 39.125);
((GeneralPath)shape).curveTo(7.125, 35.466114, 15.043963, 32.5, 24.8125, 32.5);
((GeneralPath)shape).curveTo(34.581036, 32.5, 42.5, 35.466114, 42.5, 39.125);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_1_0);
g.setComposite(AlphaComposite.getInstance(3, 0.3125f * origAlpha));
AffineTransform defaultTransform__0_0_1_1 = g.getTransform();
g.transform(new AffineTransform(0.8360710144042969f, 0.0f, 0.0f, 0.6854360103607178f, -7.959607124328613f, 15.717809677124023f));
// _0_0_1_1
paint = new RadialGradientPaint(new Point2D.Double(24.8125, 39.125), 17.6875f, new Point2D.Double(24.8125, 39.125), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.3745580017566681f, 7.19433278277776E-15f, 24.470409393310547f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(42.5, 39.125);
((GeneralPath)shape).curveTo(42.5, 42.783886, 34.581036, 45.75, 24.8125, 45.75);
((GeneralPath)shape).curveTo(15.043963, 45.75, 7.125, 42.783886, 7.125, 39.125);
((GeneralPath)shape).curveTo(7.125, 35.466114, 15.043963, 32.5, 24.8125, 32.5);
((GeneralPath)shape).curveTo(34.581036, 32.5, 42.5, 35.466114, 42.5, 39.125);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_1_1);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_1_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_2
paint = new LinearGradientPaint(new Point2D.Double(19.64834213256836, 42.25360107421875), new Point2D.Double(20.631223678588867, 6.775803089141846), new float[] {0.0f,0.5f,0.6761296f,0.8405172f,0.875f,1.0f}, new Color[] {new Color(182, 182, 182, 255),new Color(242, 242, 242, 255),new Color(250, 250, 250, 255),new Color(216, 216, 216, 255),new Color(242, 242, 242, 255),new Color(219, 219, 219, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.8782699704170227f, 0.0f, 0.0f, 0.8782699704170227f, 2.5369880199432373f, 4.967680931091309f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.906713, 21.215675);
((GeneralPath)shape).lineTo(36.899303, 40.6474);
((GeneralPath)shape).curveTo(37.66779, 41.52567, 40.102814, 42.20446, 41.729786, 40.6474);
((GeneralPath)shape).curveTo(43.300915, 39.143787, 42.93741, 37.024536, 41.400436, 35.487564);
((GeneralPath)shape).lineTo(23.176332, 15.946056);
((GeneralPath)shape).curveTo(25.426332, 9.696056, 20.872444, 4.446488, 14.997444, 5.571488);
((GeneralPath)shape).lineTo(13.73493, 6.7242174);
((GeneralPath)shape).lineTo(17.687145, 10.456865);
((GeneralPath)shape).lineTo(17.906713, 13.750381);
((GeneralPath)shape).lineTo(14.955871, 16.443983);
((GeneralPath)shape).lineTo(11.429472, 16.05584);
((GeneralPath)shape).lineTo(7.8066087, 12.652544);
((GeneralPath)shape).curveTo(7.8066087, 12.652544, 6.536487, 13.907448, 6.536487, 13.907448);
((GeneralPath)shape).curveTo(5.945724, 19.548765, 11.844213, 24.590675, 17.906713, 21.215675);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(136, 138, 133, 255);
stroke = new BasicStroke(0.9999997f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.906713, 21.215675);
((GeneralPath)shape).lineTo(36.899303, 40.6474);
((GeneralPath)shape).curveTo(37.66779, 41.52567, 40.102814, 42.20446, 41.729786, 40.6474);
((GeneralPath)shape).curveTo(43.300915, 39.143787, 42.93741, 37.024536, 41.400436, 35.487564);
((GeneralPath)shape).lineTo(23.176332, 15.946056);
((GeneralPath)shape).curveTo(25.426332, 9.696056, 20.872444, 4.446488, 14.997444, 5.571488);
((GeneralPath)shape).lineTo(13.73493, 6.7242174);
((GeneralPath)shape).lineTo(17.687145, 10.456865);
((GeneralPath)shape).lineTo(17.906713, 13.750381);
((GeneralPath)shape).lineTo(14.955871, 16.443983);
((GeneralPath)shape).lineTo(11.429472, 16.05584);
((GeneralPath)shape).lineTo(7.8066087, 12.652544);
((GeneralPath)shape).curveTo(7.8066087, 12.652544, 6.536487, 13.907448, 6.536487, 13.907448);
((GeneralPath)shape).curveTo(5.945724, 19.548765, 11.844213, 24.590675, 17.906713, 21.215675);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_1_2);
g.setComposite(AlphaComposite.getInstance(3, 0.4261364f * origAlpha));
AffineTransform defaultTransform__0_0_1_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_3
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(0.99999917f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.117386, 19.9401);
((GeneralPath)shape).lineTo(37.320267, 39.967712);
((GeneralPath)shape).curveTo(37.915173, 40.647606, 39.800194, 41.173077, 41.05968, 39.967712);
((GeneralPath)shape).curveTo(42.275932, 38.803722, 41.994534, 37.16315, 40.80472, 35.97334);
((GeneralPath)shape).lineTo(22.313189, 16.352182);
((GeneralPath)shape).curveTo(23.813189, 9.852183, 20.454401, 6.3475456, 15.454401, 6.4725456);
((GeneralPath)shape).lineTo(15.18427, 6.7459226);
((GeneralPath)shape).lineTo(18.787193, 9.982189);
((GeneralPath)shape).lineTo(18.917358, 14.163983);
((GeneralPath)shape).lineTo(15.303442, 17.462465);
((GeneralPath)shape).lineTo(11.061136, 17.004257);
((GeneralPath)shape).lineTo(7.884554, 14.012776);
((GeneralPath)shape).lineTo(7.5319166, 14.442835);
((GeneralPath)shape).curveTo(7.2194166, 20.411585, 14.023635, 23.1276, 18.117386, 19.9401);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_1_3);
g.setComposite(AlphaComposite.getInstance(3, 0.17045456f * origAlpha));
AffineTransform defaultTransform__0_0_1_4 = g.getTransform();
g.transform(new AffineTransform(0.6979380249977112f, 0.7161579728126526f, -0.7161579728126526f, 0.6979380249977112f, 0.0f, 0.0f));
// _0_0_1_4
paint = new LinearGradientPaint(new Point2D.Double(50.152931213378906, -3.6324477195739746), new Point2D.Double(25.291086196899414, -4.300265312194824), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(0, 0, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.8782699704170227f, -1.3759440364424086E-15f, 1.3759440364424086E-15f, 0.8782699704170227f, 5.328299045562744f, 1.6502430438995361f));
stroke = new BasicStroke(0.9999972f,0,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(28.185335159301758, -2.6184492111206055, 23.26827621459961, 2.0554912090301514, 1.767761468887329, 1.767761468887329);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_1_4);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_1_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_5
paint = new LinearGradientPaint(new Point2D.Double(38.22765350341797, 13.602526664733887), new Point2D.Double(37.535369873046875, 6.628589630126953), new float[] {0.0f,1.0f}, new Color[] {new Color(152, 160, 169, 255),new Color(195, 208, 221, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.8782699704170227f, 0.0f, 0.0f, 0.8782699704170227f, 2.8475029468536377f, 5.588712215423584f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.498795, 30.12538);
((GeneralPath)shape).curveTo(23.332335, 29.410917, 35.782627, 16.67687, 35.782627, 16.67687);
((GeneralPath)shape).lineTo(38.85657, 16.457302);
((GeneralPath)shape).lineTo(43.687057, 9.76049);
((GeneralPath)shape).lineTo(39.66273, 6.1752987);
((GeneralPath)shape).lineTo(33.405056, 11.554705);
((GeneralPath)shape).lineTo(33.405056, 14.628651);
((GeneralPath)shape).lineTo(20.670141, 27.857594);
((GeneralPath)shape).curveTo(20.066332, 28.461403, 21.730309, 30.784082, 22.498795, 30.12538);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(135, 143, 157, 255);
stroke = new BasicStroke(0.9999997f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.498795, 30.12538);
((GeneralPath)shape).curveTo(23.332335, 29.410917, 35.782627, 16.67687, 35.782627, 16.67687);
((GeneralPath)shape).lineTo(38.85657, 16.457302);
((GeneralPath)shape).lineTo(43.687057, 9.76049);
((GeneralPath)shape).lineTo(39.66273, 6.1752987);
((GeneralPath)shape).lineTo(33.405056, 11.554705);
((GeneralPath)shape).lineTo(33.405056, 14.628651);
((GeneralPath)shape).lineTo(20.670141, 27.857594);
((GeneralPath)shape).curveTo(20.066332, 28.461403, 21.730309, 30.784082, 22.498795, 30.12538);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_1_5);
g.setComposite(AlphaComposite.getInstance(3, 0.53977275f * origAlpha));
AffineTransform defaultTransform__0_0_1_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_6
paint = new LinearGradientPaint(new Point2D.Double(31.177404403686523, 19.821514129638672), new Point2D.Double(40.85917663574219, 9.656853675842285), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.0000002f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.401987, 29.085455);
((GeneralPath)shape).curveTo(23.04876, 28.531078, 35.426388, 15.855648, 35.426388, 15.855648);
((GeneralPath)shape).lineTo(38.354973, 15.607649);
((GeneralPath)shape).lineTo(42.568886, 9.945584);
((GeneralPath)shape).lineTo(39.679157, 7.3965945);
((GeneralPath)shape).lineTo(34.20258, 12.114067);
((GeneralPath)shape).lineTo(34.357838, 14.965022);
((GeneralPath)shape).lineTo(21.68173, 28.257345);
((GeneralPath)shape).curveTo(21.213213, 28.725863, 21.805693, 29.596565, 22.401987, 29.085455);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_1_6);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_1_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_7
//paint = new LinearGradientPaint(new Point2D.Double(9.750324249267578, 32.28376007080078), new Point2D.Double(16.91529655456543, 39.44321823120117), new float[] {0.0f,1.0E-9f,0.0f,0.75f,1.0f}, new Color[] {new Color(52, 101, 164, 255),new Color(159, 188, 225, 255),new Color(107, 149, 202, 255),new Color(61, 106, 165, 255),new Color(56, 110, 180, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.8782699704170227f, 0.0f, 0.0f, 0.8782699704170227f, 2.5369880199432373f, 4.967680931091309f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(8.465311, 43.61156);
((GeneralPath)shape).curveTo(9.7818985, 45.07679, 13.438996, 45.739727, 15.060755, 42.901646);
((GeneralPath)shape).curveTo(15.767862, 41.66421, 17.154697, 38.198845, 23.341883, 32.63038);
((GeneralPath)shape).curveTo(24.38103, 31.696207, 25.481792, 29.55924, 24.54863, 28.406511);
((GeneralPath)shape).lineTo(22.133387, 25.991268);
((GeneralPath)shape).curveTo(21.145334, 24.893433, 18.398973, 25.40552, 17.272211, 26.942144);
((GeneralPath)shape).curveTo(13.913455, 31.53834, 8.42614, 35.197025, 7.1887026, 35.638966);
((GeneralPath)shape).curveTo(4.8207827, 36.484653, 5.0872917, 39.975117, 6.653879, 41.635452);
((GeneralPath)shape).lineTo(8.465311, 43.61156);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(32, 74, 135, 255);
stroke = new BasicStroke(0.9999997f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(8.465311, 43.61156);
((GeneralPath)shape).curveTo(9.7818985, 45.07679, 13.438996, 45.739727, 15.060755, 42.901646);
((GeneralPath)shape).curveTo(15.767862, 41.66421, 17.154697, 38.198845, 23.341883, 32.63038);
((GeneralPath)shape).curveTo(24.38103, 31.696207, 25.481792, 29.55924, 24.54863, 28.406511);
((GeneralPath)shape).lineTo(22.133387, 25.991268);
((GeneralPath)shape).curveTo(21.145334, 24.893433, 18.398973, 25.40552, 17.272211, 26.942144);
((GeneralPath)shape).curveTo(13.913455, 31.53834, 8.42614, 35.197025, 7.1887026, 35.638966);
((GeneralPath)shape).curveTo(4.8207827, 36.484653, 5.0872917, 39.975117, 6.653879, 41.635452);
((GeneralPath)shape).lineTo(8.465311, 43.61156);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_1_7);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_1_8 = g.getTransform();
g.transform(new AffineTransform(0.8782699704170227f, 0.0f, 0.0f, 0.8782699704170227f, 2.427203893661499f, 5.0774641036987305f));
// _0_0_1_8
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(43.25, 37.5);
((GeneralPath)shape).curveTo(43.25, 38.25939, 42.63439, 38.875, 41.875, 38.875);
((GeneralPath)shape).curveTo(41.11561, 38.875, 40.5, 38.25939, 40.5, 37.5);
((GeneralPath)shape).curveTo(40.5, 36.74061, 41.11561, 36.125, 41.875, 36.125);
((GeneralPath)shape).curveTo(42.63439, 36.125, 43.25, 36.74061, 43.25, 37.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(161, 161, 161, 255);
stroke = new BasicStroke(1.1386017f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(43.25, 37.5);
((GeneralPath)shape).curveTo(43.25, 38.25939, 42.63439, 38.875, 41.875, 38.875);
((GeneralPath)shape).curveTo(41.11561, 38.875, 40.5, 38.25939, 40.5, 37.5);
((GeneralPath)shape).curveTo(40.5, 36.74061, 41.11561, 36.125, 41.875, 36.125);
((GeneralPath)shape).curveTo(42.63439, 36.125, 43.25, 36.74061, 43.25, 37.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_1_8);
g.setComposite(AlphaComposite.getInstance(3, 0.60227275f * origAlpha));
AffineTransform defaultTransform__0_0_1_9 = g.getTransform();
g.transform(new AffineTransform(0.5708760023117065f, 0.0f, 0.0f, 0.5708760023117065f, 9.154848098754883f, 11.251110076904297f));
// _0_0_1_9
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(20.771261, 28.20101);
((GeneralPath)shape).curveTo(20.771261, 29.17732, 19.979805, 29.968777, 19.003494, 29.968777);
((GeneralPath)shape).curveTo(18.027184, 29.968777, 17.235727, 29.17732, 17.235727, 28.20101);
((GeneralPath)shape).curveTo(17.235727, 27.224699, 18.027184, 26.433243, 19.003494, 26.433243);
((GeneralPath)shape).curveTo(19.979805, 26.433243, 20.771261, 27.224699, 20.771261, 28.20101);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
g.setTransform(defaultTransform__0_0_1_9);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
AffineTransform defaultTransform__0_0_1_10 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_10
paint = new LinearGradientPaint(new Point2D.Double(12.0046968460083, 35.68846130371094), new Point2D.Double(10.650805473327637, 33.19496536254883), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0072540044784546f, -0.026365259662270546f, 0.026365259662270546f, 1.0072540044784546f, 1.5934109687805176f, 0.07919099926948547f));
stroke = new BasicStroke(2.2945092f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.678905, 29.624807);
((GeneralPath)shape).curveTo(18.678905, 29.624807, 11.509014, 36.92442, 8.150257, 38.161858);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_1_10);
g.setComposite(AlphaComposite.getInstance(3, 0.19886364f * origAlpha));
AffineTransform defaultTransform__0_0_1_11 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_11
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(0.99999946f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(8.806002, 42.48669);
((GeneralPath)shape).curveTo(10.247267, 44.232307, 13.405535, 44.64792, 14.397161, 42.1161);
((GeneralPath)shape).curveTo(15.078468, 40.37659, 17.730783, 36.450314, 22.594746, 32.072746);
((GeneralPath)shape).curveTo(23.411654, 31.338364, 24.277002, 29.658419, 23.543411, 28.752218);
((GeneralPath)shape).lineTo(21.644705, 26.853512);
((GeneralPath)shape).curveTo(20.867962, 25.990463, 18.708952, 26.393032, 17.823164, 27.601028);
((GeneralPath)shape).curveTo(15.182728, 31.214256, 9.33982, 35.940582, 7.9274144, 36.406654);
((GeneralPath)shape).curveTo(5.7406197, 37.128265, 6.150422, 39.627953, 7.3819714, 40.933205);
((GeneralPath)shape).lineTo(8.806002, 42.48669);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_1_11);
g.setComposite(AlphaComposite.getInstance(3, 0.27840912f * origAlpha));
AffineTransform defaultTransform__0_0_1_12 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_12
paint = new LinearGradientPaint(new Point2D.Double(14.017541885375977, 36.942543029785156), new Point2D.Double(15.415793418884277, 38.268367767333984), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.8780990242958069f, -0.017323700711131096f, 0.017323700711131096f, 0.8780990242958069f, 2.163686990737915f, 4.067899227142334f));
stroke = new BasicStroke(2.2945092f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(20.824602, 31.261024);
((GeneralPath)shape).curveTo(20.824602, 31.261024, 13.501839, 37.87843, 11.910849, 42.12107);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setTransform(defaultTransform__0_0_1_12);
g.setTransform(defaultTransform__0_0_1);
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
        return 48;
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

