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

package net.oesterholt.jxmlnote.utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

public class DPIAdjuster {
	
	private static double getFactor() {
		return getScreenDPI()/72.0;
	}
	
	public static int getScreenDPI() {
		return Toolkit.getDefaultToolkit().getScreenResolution();
		//if (screenResolutionInDPI>96) { screenResolutionInDPI=96; }
	}

	
	public static double pointsToScreenFactor() {
		return getFactor();
	}
	
	public static double screenToPointsFactor() {
		return 1.0/getFactor();
	}
	
	public static Font adjustFont(Font f) {
		AffineTransform tr=new AffineTransform();
		double factor=getFactor();
		tr.scale(factor,factor);
		return f.deriveFont(tr);
	}
	
	public static double adjustPointSize(double in) {
		return in*getFactor();
	}

	public static Dimension getDimensionForScreenDPI(Dimension sizeInPt) {
		double factor=getFactor();
		return new Dimension((int) Math.round(sizeInPt.width*factor),(int) Math.round(sizeInPt.height*factor));
	}

	public static Dimension getDimensionInPtForScreenDPI(Dimension sizeInPixels) {
		double factor=getFactor();
		return new Dimension((int) Math.round(sizeInPixels.width/factor),(int) Math.round(sizeInPixels.height/factor));
	}
	public static int getFontSizeForScreenDPI(int pt) {
		return (int) Math.round(getFactor()*pt);
	}

	public static final String DEVICE_PRINT="print";
	public static final String DEVICE_PDF=DEVICE_PRINT;
	public static final String DEVICE_SCREEN="screen";
	public static final String DEVICE_NONE=DEVICE_SCREEN;
}
