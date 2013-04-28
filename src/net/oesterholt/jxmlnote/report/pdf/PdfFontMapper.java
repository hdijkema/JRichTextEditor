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

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import net.oesterholt.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.oesterholt.jxmlnote.exceptions.FontPathException;
import net.oesterholt.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import net.oesterholt.jxmlnote.internationalization.XMLNoteTranslator;


// THIS IS AN INTERNAL API OF SUN!
//import sun.font.FontManager;

import com.lowagie.text.pdf.FontMapper;


/**
 * This class provides a font mapper for iText that inserts all font directories from the font path
 * of the sun.font.FontMapper. If it cannot insert them, it yields a Warning via XMLNoteErrorHandler
 * and returns the default FontMapper of iText. 
 * <p>
 * This class works via a static method. The constructor is protected.  
 * 
 * @author Hans Oesterholt-Dijkema
 *
 */
public class PdfFontMapper extends MyDefaultFontMapper {
	
	enum OS { WINDOWS, UNIX, MACOSX, UNKNOWN };
	
	static public OS getOS() {
		String name_os=System.getProperty("os.name").trim().toLowerCase();
		if (name_os.contains("windows")) {
			return OS.WINDOWS;
		} else if (name_os.contains("linux")) {
			return OS.UNIX;
		} else if (name_os.contains("unix")) {
			return OS.UNIX;
		} else if (name_os.contains("aix")) {
			return OS.UNIX;
		} else if (name_os.contains("solaris")) {
			return OS.UNIX;
		} else if (name_os.contains("sunos")) {
			return OS.UNIX;
		} else if (name_os.contains("mac")) {
			return OS.MACOSX;
		} else {
			return OS.UNKNOWN;
		}
	}
	
	Hashtable<String,Object[]> _cachedNames; 
	Hashtable<String,Integer>  _cachedCounts;
	Set<String>				   _paths;
	
	public void insertNames(Object[] names,String path) {
		super.insertNames(names,path);
		_cachedNames.put(path,names);
	}
	
	public int insertFile(File fontFile,int count) {
		String path=fontFile.getPath();
		Object[] names=_cachedNames.get(path);
		if (names!=null) {
			insertNames(names,path);
			_paths.add(path);
			count+=_cachedCounts.get(path);
			return count;
		} else {
			count=super.insertFile(fontFile, count);
			_cachedCounts.put(path, new Integer(count));
			_paths.add(path);
			return count;
		}
	}
	
	private XMLNoteTranslator	_tr;
	private static FontMapper   _fontmapper=null;
	
	/**
	 * This method creates an instance of this fontmapper, or reuses the existing one if 
	 * it has already been created. 
	 * 
	 * @return the fontmapper for iText. 
	 */
	public static synchronized FontMapper createPdfFontMapper(PdfReport rep,File fontmapcache) {
		if (_fontmapper==null) {
			_fontmapper=new PdfFontMapper(rep,fontmapcache);
		}
		return _fontmapper;
	}
	
	/**
	 * This method creates an instance of this fontmapper, or reuses the existing on if
	 * it has already been created. A font cache is read from in (which may be null) and
	 * written to out (which may not be null).
	 * 
	 * @param rep
	 * @param in
	 * @param out
	 * @return
	 * @throws IOException
	 */
	public static synchronized FontMapper createPdfFontMapper(PdfReport rep,InputStream in,OutputStream out) throws IOException {
		if (_fontmapper!=null) { return _fontmapper; }
		File f=File.createTempFile("PdfFontMapper", "cache");
		byte []b=new byte[1024];
		int l;
		if (in!=null) {
			FileOutputStream fout=new FileOutputStream(f);
			while ((l=in.read(b))>0) {
				fout.write(b,0,l);
			}
			fout.close();
		}
		FontMapper fm=createPdfFontMapper(rep,f);
		FileInputStream fin=new FileInputStream(f);
		while ((l=fin.read(b))>0) {
			out.write(b,0,l);
		}
		fin.close();
		return fm;
	}
	
	private String expandPath1(File part) {
		FileFilter f=new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			} 
		};
		File [] dirs=part.listFiles(f);
		if (dirs==null) { return null; }
		String path=null;
		for(File d : dirs) {
			if (path==null) { path=d.getAbsolutePath(); }
			else { path=path.concat(":").concat(d.getAbsolutePath()); }
			String subs=expandPath1(d);
			if (subs!=null) {
				path=path.concat(":").concat(subs);
			}
		}
		return path;
	}
	
	private String expandPath(String parts) {
		String[] pathParts=parts.split(":");
		String path=null;
		for(String p : pathParts) {
			if (path==null) { path=p; }
			else { path=path.concat(":").concat(p); }
			
			String ep=expandPath1(new File(p));
			if (ep!=null) {
				path=path.concat(":").concat(ep);
			}
		}
		return path;
	}
	
	private static String _fontPath=null;
	
	/**
	 * This function also looks for fonts in OS dependent places.
	 * It also looks to the environment variable 'FONTPATH'. If this
	 * environment variable is set (<path>[:<path>]*), it is prepended.
	 *  
	 * @return
	 */
	private String[] getFontPath() {
		
		// java 1.7
		// We're not going to make us dependent on java 1.7 specific constructions.
		// So we're introducing some new dependencies here, and some knowledge about
		// font locations, depending on the operating system we're on.
		String fontPath="";
		if (_fontPath==null) {
			OS os=getOS();
			String env_where=System.getenv("FONTPATH");
			if (env_where!=null) {
				env_where=env_where.trim();
				if (env_where.equals("")) { env_where=null; }
			}
			if (os.equals(OS.WINDOWS)) {
				String where=System.getenv("SYSTEMROOT").concat("/fonts");
				if (env_where!=null) { where=env_where.concat(":").concat(where); }
				fontPath=expandPath(where);
			} else if (os.equals(OS.UNIX)) {
				String where="/usr/share/fonts";
				if (env_where!=null) { where=env_where.concat(":").concat(where); }
				fontPath=expandPath(where);
			} else if (os.equals(OS.MACOSX)) {
				String where=System.getenv("HOME").concat("/Libraries/fonts").concat(":/Library/Fonts:/System/Library/Fonts");
				if (env_where!=null) { where=env_where.concat(":").concat(where); }
				fontPath=expandPath(where);
			}
			_fontPath=fontPath;
		} else {
			fontPath=_fontPath;
		}
		// java 1.7 end
		// java 1.6
		//String fontPath=FontManager.getFontPath(true);
		// java 1.6 end
		
		if (fontPath==null) { return null; }
		else if (fontPath.indexOf(':')>=0) {
			String[] pathParts=fontPath.split(":");
			return pathParts;
		} else if (fontPath.indexOf(';')>=0) {
			String[] pathParts=fontPath.split(";");
			return pathParts;
		} else {
			String []pathPaths={ fontPath };
			return pathPaths;
		}
	}
	
	private void readInCache(File f) {
		long l=f.length();
		
		if (f.canRead() && f.isFile() && f.length()>0) {
			try {
				ObjectInputStream in=new ObjectInputStream(new FileInputStream(f));
				readInCache(in);
				in.close();
			} catch (Exception e) {
				DefaultXMLNoteErrorHandler.exception(e);
			}
		}
	}
	
	private void readInCache(ObjectInputStream in) {
		_cachedNames.clear();
		_cachedCounts.clear();
		try {
			int size=(Integer) in.readObject();
			for (int i=0;i<size;i++) {
				String path=(String) in.readObject();
				Object[] names=(Object []) in.readObject();
				_cachedNames.put(path,names);
			}
			size=(Integer) in.readObject();
			for (int i=0;i<size;i++) {
				String path=(String) in.readObject();
				Integer count=(Integer) in.readObject();
				_cachedCounts.put(path, count);
			}
		} catch (Exception e) {
			DefaultXMLNoteErrorHandler.exception(e);
		}
	}
	
	private void writeOutCache(File f) {
		try {
			ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(f));
			writeOutCache(out);
			out.close();
		} catch(Exception e) {
			DefaultXMLNoteErrorHandler.exception(e);
		}
	}
	
	private void writeOutCache(ObjectOutputStream out) {
		try {
			Set<String> keys=_cachedNames.keySet();
			Iterator<String> it=keys.iterator();
			out.writeObject(keys.size());
			while (it.hasNext()) {
				String path=it.next();
				out.writeObject(path);
				Object [] names=_cachedNames.get(path);
				out.writeObject(names);
			}
			keys=_cachedCounts.keySet();
			it=keys.iterator();
			out.writeObject(keys.size());
			while (it.hasNext()) {
				String path=it.next();
				out.writeObject(path);
				Integer count=_cachedCounts.get(path);
				out.writeObject(count);
			}
		} catch(Exception e) {
			DefaultXMLNoteErrorHandler.exception(e);
		}
	}
	
	private void initializeFonts(PdfReport rep,File persistedCacheWithFontMapInfo) throws FontPathException {
		if (persistedCacheWithFontMapInfo!=null) { readInCache(persistedCacheWithFontMapInfo); }
		_paths.clear();
		String[] paths=getFontPath();
		if (paths==null) {
			throw new FontPathException(_tr._("There seems to be no font path configured for this platform"));
		} else {
			String prev=null; 
			if (rep!=null) {
				prev=rep.informStatus(_tr._("Initializing PDF Fonts"));
			}
			float progr=0.0f;
			float step=100.0f/((float) paths.length);
			int prevp=0;
			if (rep!=null) {
				prevp=rep.informProgress((int) progr);
			}
			for(String path : paths) {
				progr+=step;
				if (progr>100.0f) { progr=100.0f; }
				if (rep!=null) { rep.informProgress((int) progr); }
				super.insertDirectory(path);
			}
			if (rep!=null) {
				rep.informStatus(prev);
				rep.informProgress(prevp);
			}
		}
		// clear out cached fonts that have not been read in
		Set<String> cachedPaths=_cachedNames.keySet();
		Iterator<String> it=cachedPaths.iterator();
		Vector<String> clearPaths=new Vector<String>();
		while (it.hasNext()) {
			String p=it.next();
			if (!_paths.contains(p)) {
				clearPaths.add(p);
			}
		}
		it=clearPaths.iterator();
		while (it.hasNext()) {
			String p=it.next();
			_cachedNames.remove(p);
			_cachedCounts.remove(p);
		}
		// persist new cache
		if (persistedCacheWithFontMapInfo!=null) { writeOutCache(persistedCacheWithFontMapInfo); }
	}
	
	protected PdfFontMapper(PdfReport rep,File persistedCacheWithFontMapInfo) {
		_tr=new DefaultXMLNoteTranslator();
		_cachedNames=new Hashtable<String,Object[]>();
		_cachedCounts=new Hashtable<String,Integer>();
		_paths=new HashSet<String>();
		try {
			initializeFonts(rep,persistedCacheWithFontMapInfo);
		} catch (FontPathException e) {
			DefaultXMLNoteErrorHandler.warning(e, -1, e.getMessage());
		}
	}
}
