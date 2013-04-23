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

package net.oesterholt.jxmlnote.report.pdf;

import net.oesterholt.jxmlnote.report.elements.Tab;

import com.lowagie.text.Chunk;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.draw.DrawInterface;


public class PdfTab extends Chunk implements Tab {
	private float _nextTab=0.0f;
	
	public PdfTab(PdfReport rep,float nextTab) {
		super(new DrawInterface() {
					public void draw(PdfContentByte cn,float a1, float a2,float a3, float a4,float a5) {
							// draws nothing
					}
				},nextTab);
		_nextTab=nextTab;
	}
	
	public static String whiteSpace() {
		char [] c={160};
		return new String(c);
	}
	
	public String toString() {
		return String.format("PdfTab:%.1f",_nextTab);
	}
	
	public PdfTab(PdfReport rep) {
		super(whiteSpace());
	}
}
