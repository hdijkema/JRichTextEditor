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

package net.oesterholt.jxmlnote.report.pdf;

import java.io.File;

import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.DefaultFontMapper;


public class MyDefaultFontMapper extends DefaultFontMapper {

	//////////////////////////////////////////////////////////////////////////////////////////////////
	// OVERRIDE methods in DefaultFontMapper with a different implementation   
	// That allows for a more efficient implementation 
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int insertFile(File file,int count) {
		String name = file.getPath().toLowerCase();
		try {
			if (name.endsWith(".ttf") || name.endsWith(".otf") || name.endsWith(".afm")) {
				Object allNames[] = BaseFont.getAllFontNames(file.getPath(), BaseFont.CP1252, null);
				insertNames(allNames, file.getPath());
				++count;
			}
			else if (name.endsWith(".ttc")) {
				String ttcs[] = BaseFont.enumerateTTCNames(file.getPath());
				for (int j = 0; j < ttcs.length; ++j) {
					String nt = file.getPath() + "," + j;
					Object allNames[] = BaseFont.getAllFontNames(nt, BaseFont.CP1252, null);
					insertNames(allNames, nt);
				}
				++count;
			}
		}
		catch (Exception e) {
		}
		return count;
	}

	/** Inserts all the fonts recognized by iText in the
	 * <CODE>directory</CODE> into the map. The encoding
	 * will be <CODE>BaseFont.CP1252</CODE> but can be
	 * changed later.
	 * @param dir the directory to scan
	 * @return the number of files processed
	 */
	public int insertDirectory(String dir) {
		File file = new File(dir);
		if (!file.exists() || !file.isDirectory())
			return 0;
		File files[] = file.listFiles();
		if (files == null)
			return 0;
		int count = 0;
		for (int k = 0; k < files.length; ++k) {
			count=insertFile(files[k],count);
		}
		return count;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////
	// OVERRIDE methods in DefaultFontMapper with a different implementation   
	// That allows for a more efficiënt implementation 
	//////////////////////////////////////////////////////////////////////////////////////////////////

	
}
