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

package net.oesterholt.jxmlnote.widgets;

import java.awt.Component;
import java.awt.print.PrinterJob;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

import net.oesterholt.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.oesterholt.jxmlnote.interfaces.XMLNotePreferences;


public class JPrintPreferences {

	private XMLNotePreferences _preferences;
	
	MediaSizeName [] sizes={
			MediaSizeName.A,MediaSizeName.B,MediaSizeName.C,MediaSizeName.D,MediaSizeName.E,
			MediaSizeName.EXECUTIVE,
			MediaSizeName.FOLIO,
			MediaSizeName.INVOICE,
			MediaSizeName.ISO_A0,MediaSizeName.ISO_A1,MediaSizeName.ISO_A2,MediaSizeName.ISO_A3,
			MediaSizeName.ISO_A4,MediaSizeName.ISO_A5,MediaSizeName.ISO_A6,MediaSizeName.ISO_A7,
			MediaSizeName.ISO_A8,MediaSizeName.ISO_A9,MediaSizeName.ISO_A10,
			MediaSizeName.ISO_B0,MediaSizeName.ISO_B1,MediaSizeName.ISO_B2,MediaSizeName.ISO_B3,
			MediaSizeName.ISO_B4,MediaSizeName.ISO_B5,MediaSizeName.ISO_B6,MediaSizeName.ISO_B7,
			MediaSizeName.ISO_B8,MediaSizeName.ISO_B9,MediaSizeName.ISO_B10,
			MediaSizeName.ISO_C0,MediaSizeName.ISO_C1,MediaSizeName.ISO_C2,MediaSizeName.ISO_C3,
			MediaSizeName.ISO_C4,MediaSizeName.ISO_C5,MediaSizeName.ISO_C6,
			MediaSizeName.ISO_DESIGNATED_LONG,
			MediaSizeName.ITALY_ENVELOPE,
			MediaSizeName.JAPANESE_DOUBLE_POSTCARD,MediaSizeName.JAPANESE_POSTCARD,
			MediaSizeName.JIS_B0,MediaSizeName.JIS_B1,MediaSizeName.JIS_B2,MediaSizeName.JIS_B3,
			MediaSizeName.JIS_B4,MediaSizeName.JIS_B5,MediaSizeName.JIS_B6,MediaSizeName.JIS_B7,
			MediaSizeName.JIS_B8,MediaSizeName.JIS_B9,MediaSizeName.JIS_B10,
			MediaSizeName.LEDGER, MediaSizeName.MONARCH_ENVELOPE, 
			MediaSizeName.NA_10X13_ENVELOPE,MediaSizeName.NA_10X14_ENVELOPE,MediaSizeName.NA_10X15_ENVELOPE,
			MediaSizeName.NA_5X7,MediaSizeName.NA_6X9_ENVELOPE,MediaSizeName.NA_7X9_ENVELOPE,
			MediaSizeName.NA_8X10,MediaSizeName.NA_9X11_ENVELOPE,MediaSizeName.NA_9X12_ENVELOPE,
			MediaSizeName.NA_LEGAL,MediaSizeName.NA_LETTER,
			MediaSizeName.NA_NUMBER_9_ENVELOPE,MediaSizeName.NA_NUMBER_10_ENVELOPE,
			MediaSizeName.NA_NUMBER_11_ENVELOPE,MediaSizeName.NA_NUMBER_12_ENVELOPE,
			MediaSizeName.PERSONAL_ENVELOPE,MediaSizeName.QUARTO,MediaSizeName.TABLOID
	};
	
	public void pageDialog() {
		PrinterJob job=PrinterJob.getPrinterJob();
		HashPrintRequestAttributeSet set=new HashPrintRequestAttributeSet();
		fromPrefs(set);
		job.pageDialog(set);
		toPrefs(set);
	}
	
	public void fromPrefs(PrintRequestAttributeSet set) {
		try {
			String val=_preferences.getString("printprefs", null);
			ByteArrayInputStream bin=new ByteArrayInputStream(val.getBytes("UTF-8"));
			XMLDecoder dec=new XMLDecoder(bin);
			Integer n=(Integer) dec.readObject();
			for(int i=0;i<n;i++) {
				String name=(String) dec.readObject();
				if (name.equals("MediaPrintableArea")) {
					int size=(Integer) dec.readObject();
					float[] f=new float[size];
					for (int k=0;k<size;k++) {
						f[k]=(Float) dec.readObject();
					}
					set.add(new MediaPrintableArea(f[0], f[1], f[2], f[3], MediaPrintableArea.MM));
				} else if (name.equals("MediaSizeName")) {
					int value=(Integer) dec.readObject();
					for (MediaSizeName mn : sizes) {
						if (mn.getValue()==value) {
							set.add(mn);
							break;
						}
					}
				} else if (name.equals("OrientationRequested")) {
					int value=(Integer) dec.readObject();
					if (value==OrientationRequested.LANDSCAPE.getValue()) {
						set.add(OrientationRequested.LANDSCAPE);
					} else if (value==OrientationRequested.PORTRAIT.getValue()) {
						set.add(OrientationRequested.PORTRAIT);
					} else if (value==OrientationRequested.REVERSE_LANDSCAPE.getValue()) {
						set.add(OrientationRequested.REVERSE_LANDSCAPE);
					} else if (value==OrientationRequested.REVERSE_PORTRAIT.getValue()) {
						set.add(OrientationRequested.REVERSE_PORTRAIT);
					}
				}
			}
			dec.close();
		} catch (Exception E) {
			DefaultXMLNoteErrorHandler.exception(E);
		}
	}
	
	public void toPrefs(PrintRequestAttributeSet f) {
 		Attribute[] attr=f.toArray();
		
		ByteArrayOutputStream out=new ByteArrayOutputStream ();
		XMLEncoder enc=new XMLEncoder(out);
		enc.writeObject(attr.length);
		for (Attribute a : attr) {
			if (a instanceof MediaSizeName) {
				MediaSizeName nm=(MediaSizeName) a;
				enc.writeObject("MediaSizeName");
				enc.writeObject(nm.getValue());
			} else if (a instanceof OrientationRequested) {
				OrientationRequested or=(OrientationRequested) a;
				enc.writeObject("OrientationRequested");
				enc.writeObject(or.getValue());
			} else if (a instanceof MediaPrintableArea) {
				enc.writeObject("MediaPrintableArea");
				MediaPrintableArea area=(MediaPrintableArea) a;
				float[] margins=area.getPrintableArea(MediaPrintableArea.MM);
				enc.writeObject((Integer) margins.length);
				for (float m : margins) {
					enc.writeObject((Float) m);
				}
			}
		}
		enc.close();
		try {
			String val=out.toString("UTF-8");
			_preferences.put("printprefs",val);
		} catch (Exception E) {
			DefaultXMLNoteErrorHandler.exception(E);
		}
		
	}

	
	public JPrintPreferences(Component parent, XMLNotePreferences prefs) {
		_preferences=prefs;
	}
	

}
