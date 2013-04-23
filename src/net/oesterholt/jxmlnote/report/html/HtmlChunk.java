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

package net.oesterholt.jxmlnote.report.html;

import java.awt.Color;
import java.awt.Image;
import java.io.File;

import net.oesterholt.jxmlnote.report.Report;
import net.oesterholt.jxmlnote.report.elements.Chunk;
import net.oesterholt.jxmlnote.report.elements.Rectangle;


import org.w3c.dom.Element;

public class HtmlChunk implements Chunk {
	
	private HtmlReport  _report;
	private String		_txt=null;
	private File		_imageFile=null;
	private Image		_image=null;
	private Color		_bgColor=null;
	private Rectangle   _padding=null;
	private Color		_textColor=null;
	private Color       _underlineMarkColor=null;
	private float       _underlineMarkThickness=2.0f;
	private float		_underlineMarkYpos=-3.0f;
	private boolean     _bold=false;
	private boolean     _underline=false;
	private boolean     _italic=false;
	private boolean		_tab=false;

	///////////////////////////////////////////////////////////////////////
	// Interface methods
	///////////////////////////////////////////////////////////////////////
	
	public Chunk setBackground(Color c, float padleft, float padtop,float padright, float padbottom) {
		_bgColor=c;
		_padding=new Rectangle(padleft,padtop,padright,padbottom);
		return this;
	}
	
	public Chunk setTextColor(Color c) {
		_textColor=c;
		return this;
	}

	public Chunk setUnderline(Color c, float thickness, float yposition) {
		_underlineMarkColor=c;
		_underlineMarkThickness=thickness;
		_underlineMarkYpos=yposition;
		return this;
	}

	public Chunk setBold(boolean b) {
		_bold=b;
		return this;
	}

	public Chunk setItalic(boolean b) {
		_italic=b;
		return this;
	}

	public Chunk setUnderline(boolean b) {
		_underline=b;
		return this;
	}
	
	///////////////////////////////////////////////////////////////////////
	// Generation
	///////////////////////////////////////////////////////////////////////
	
	public void generate(org.w3c.dom.Element parent) {
		Element span=_report.html().createElement("span");
		String style="";
		if (_bgColor!=null) { style+="background:"+_report.htmlColor(_bgColor)+";"; }
		if (_underlineMarkColor!=null) { 
			style+="border-bottom: "+_report.htmlColor(_underlineMarkColor)+" solid "+_underlineMarkThickness+"pt;";
			style+="padding-bottom: "+_underlineMarkYpos+"pt;";
		}
		Element e=span;
		if (_bold) { Element g=_report.html().createElement("strong");e.appendChild(g);e=g; }
		if (_italic) { Element g=_report.html().createElement("em");e.appendChild(g);e=g; }
		if (_underline) {  Element g=_report.html().createElement("u");e.appendChild(g);e=g; }
		if (_txt!=null) {
			e.setTextContent(_txt);
		} else if (_tab) {
			
		} else if (_image!=null) {
			
		} else if (_imageFile!=null) {
			
		}
	}
	
	///////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////
	
	public HtmlChunk(HtmlReport rep,String txt,boolean bold,boolean italic,boolean u) {
		_bold=bold;
		_italic=italic;
		_underline=u;
		_report=rep;
		_txt=txt;
	}
	
	
	

}
