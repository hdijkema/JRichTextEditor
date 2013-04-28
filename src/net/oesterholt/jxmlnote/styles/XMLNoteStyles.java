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

package net.oesterholt.jxmlnote.styles;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.prefs.Preferences;

import javax.swing.text.StyleContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.oesterholt.jxmlnote.exceptions.BadStyleException;
import net.oesterholt.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import net.oesterholt.jxmlnote.internationalization.XMLNoteTranslator;
import net.oesterholt.jxmlnote.utils.WeakSet;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;


public class XMLNoteStyles {

	private interface JXMLNotePreferences {
		public String get(String key,String defaultValue);
		public void   put(String key,String value);
	}

	private Hashtable<String,XMLNoteParStyle> 					_styles;
	private Vector<String> 										_keys;
	private WeakSet<XMLNoteStylesChangedListener> 				_listeners;
	private XMLNoteTranslator									_translator;
	private String												_contextName;
	
	public void addStyleChangedListener(XMLNoteStylesChangedListener l) {
		_listeners.add(l);
	}
	
	public void removeStyleChangedListener(XMLNoteStylesChangedListener l) {
		_listeners.remove(l);
	}
	
	public void informStylesChanged() {
		Iterator<XMLNoteStylesChangedListener> it=_listeners.iterator();
		while (it.hasNext()) {
			XMLNoteStylesChangedListener l=it.next();
			if (l!=null) {
				l.stylesChanged();
			}
		}
	}
	
	public void styleChanged(XMLNoteParStyle p) {
		informStylesChanged();
	}

	public void resetTo(XMLNoteStyles from) {
		Iterator<Entry<String,XMLNoteParStyle>> it=from.iterator();
		_styles.clear();
		while (it.hasNext()) {
			Entry<String,XMLNoteParStyle> e=it.next();
			XMLNoteParStyle p=e.getValue().copy();
			p.setContainer(this);
			_styles.put(e.getKey(), p);
		}
		informStylesChanged();
	} 

	public String toXML() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder ();
			org.w3c.dom.Document doc = db.newDocument ();
			org.w3c.dom.Element root = doc.createElement ("xmlnotestyles");
			Writer		out = new StringWriter();

			doc.appendChild (root);
			root.setAttribute("version", "2010.1");
			org.w3c.dom.Element styles=doc.createElement("styles");
			root.appendChild(styles);

			Iterator<Entry<String,XMLNoteParStyle>> it=this.iterator();
			while (it.hasNext()) {
				Entry<String,XMLNoteParStyle> e=it.next();
				org.w3c.dom.Element style=doc.createElement("style");
				style.setAttribute("id",e.getKey());
				style.setTextContent(e.getValue().toString());
				styles.appendChild(style);
			}

			//Serialize DOM
			OutputFormat format    = new OutputFormat (doc); 
			// as a String
			XMLSerializer serial   = new XMLSerializer (out,format);
			try {
				serial.serialize(doc);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String xml=out.toString();
			return xml;
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		return null;
	}



	private synchronized void toPrefs(JXMLNotePreferences prefs) {
		Iterator<String> it=_styles.keySet().iterator();
		while (it.hasNext()) {
			String key=it.next();
			prefs.put(key, _styles.get(key).toString());
		}
	}

	public synchronized void toPrefs(final Preferences prefs) {
		toPrefs(new JXMLNotePreferences() {
			public String get(String key, String defaultValue) {
				return prefs.get(key, defaultValue);
			}

			public void put(String key, String value) {
				prefs.put(key, value);
			}
		});
	}

	/**
	 * Returns an iterator over all entries in JXMLNoteStyles.
	 * @return
	 */
	public Iterator<Entry<String,XMLNoteParStyle>> iterator() {
		Set<Entry<String,XMLNoteParStyle>> s=_styles.entrySet();
		Vector<Entry<String,XMLNoteParStyle>> v=new Vector<Entry<String,XMLNoteParStyle>>();
		Iterator<Entry<String,XMLNoteParStyle>> it=s.iterator();
		while(it.hasNext()) {
			v.add(it.next());
		}
		return v.iterator();
	}

	/**
	 * Returns all keys of the JXMLNoteParStyle styles in this styles, sorted alphabetically on key (or styleId).
	 * @return
	 */
	public Vector<String> getStyleKeys() {
		Vector<String> v=new Vector<String>();
		Enumeration<String> en=_styles.keys();
		while(en.hasMoreElements()) {
			v.add(en.nextElement());
		}
		Collections.sort(v);
		_keys=v;
		return v;
	}

	public int getNumberOfStyles() {
		if (_keys==null) { getStyleKeys(); }
		return _keys.size();
	}

	public String getStyleName(int i) {
		if (_keys==null) { getStyleKeys(); }
		return _styles.get(_keys.get(i)).name();
	}

	public XMLNoteParStyle getStyle(int i) {
		if (_keys==null) { getStyleKeys(); }
		return _styles.get(_keys.get(i));
	}

	/**
	 * Returns the default style for paragraphs
	 * @return
	 * @throws BadStyleException
	 */
	public XMLNoteParStyle getDefaultStyle() throws BadStyleException {
		int i=getDefaultStyleIndex();
		if (i==-1) {
			throw new BadStyleException("No default style in XMLNoteStyles");
		}
		return getStyle(i);
	}

	/**
	 * Returns the index for the default style or -1 if there is none.
	 * 
	 * @return
	 */
	public int getDefaultStyleIndex() {
		int i,n;
		for(i=0,n=getNumberOfStyles();i<n && !getStyle(i).isDefault();i++);
		if (i==n) { return -1; }
		else { return i; }
	}


	/**
	 * Returns all JXMLNoteParStyles as a CSS String.
	 * @return
	 */
	public String asCSS(XMLNoteStyleIdConverter cvt) {
		Enumeration<XMLNoteParStyle> en=_styles.elements(); 
		StringBuffer buf=new StringBuffer();
		while (en.hasMoreElements()) {
			buf.append(en.nextElement().asCSS(cvt));
			buf.append("\n");
		}
		return buf.toString();
	}

	/**
	 * Zooms all styles with the given factor.
	 * @param factor
	 */
	public void zoom(double factor) {
		Vector<String> keys=getStyleKeys();
		Iterator<String> it=keys.iterator();
		while(it.hasNext()) {
			String key=it.next();
			XMLNoteParStyle p=_styles.get(key);
			p.zoom(factor);
		}
	}

	/**
	 * Returns the JXMLNoteParStyle with the given key; null, if key doesn't exist.
	 * @param key
	 * @return
	 */
	public XMLNoteParStyle parStyle(String key) {
		return _styles.get(key);
	}

	/**
	 * Adds a JXMLNoteParStyle with the given key to the styles.
	 * @param key
	 * @param s
	 */
	public void addParStyle(String key,XMLNoteParStyle s) throws StyleContainedException {
		XMLNoteStyles q=s.getContainer();
		if (q!=null && q!=this) {
			String form=_translator.translate("The given paragraph style with id '%s' is already part of another style container");
			throw new StyleContainedException(String.format(form,s.id()));
		}
		_styles.put(key,s);
		_keys=null;
		informStylesChanged();
	}

	/**
	 * Removes the JXMLNoteParStyle with the given key from the styles.
	 * @param key
	 */
	public void removeParStyle(String key) {
		XMLNoteParStyle p=_styles.get(key);
		if (p!=null) {
			p.setContainer(null);
			_styles.remove(key);
			_keys=null;
		}
		informStylesChanged();
	}
	
	private void initContextName() {
		_contextName=Integer.toHexString(this.hashCode());
	}

	/**
	 * Creates the default styles for a JXMLNote viewer (the styles used in the JXMLNoteDocument).
	 */
	public XMLNoteStyles() {
		initContextName();
		init(new JXMLNotePreferences() {
			public String get(String key, String defaultValue) {
				return defaultValue;
			}
			public void put(String key, String value) {
			}
		});
	}

	/**
	 * Creates the default styles for a JXMLNote viewer (the styles used in the XMLNoteDocument),
	 * read from the Preferences.
	 * @param prefs
	 */
	public XMLNoteStyles(final Preferences prefs) {
		initContextName();
		init(new JXMLNotePreferences() {
			public String get(String key, String defaultValue) {
				return prefs.get(key, defaultValue);
			}
			public void put(String key, String value) {
				prefs.put(key, value);
			}
		});
	}
	
	/**
	 * Returns the context name for this XMLNoteStyles. All XMLNoteDocuments associated with
	 * this XMLNoteStyles use this context and will therefore change their paragraph styles
	 * on changes in this container.
	 * 
	 * @return The context name of this XMLNoteStyles container
	 */
	public String stylesContextName() {
		return _contextName;
	}

	/**
	 * Returns the associated style context for all XMLNoteStyles. For internal use only.
	 * @return
	 */
	public StyleContext getStyleContext() {
		return XMLNoteParStyle.getStyleContext();
	}
	
	public XMLNoteParStyle paragraphStyle() {
		return this.parStyle("par");
	}
	
	public XMLNoteParStyle lineStyle() {
		return this.parStyle("line");
	}
	
	public XMLNoteParStyle h1Style() {
		return this.parStyle("h1");
	}

	public XMLNoteParStyle h2Style() {
		return this.parStyle("h2");
	}

	public XMLNoteParStyle h3Style() {
		return this.parStyle("h3");
	}
	
	public XMLNoteParStyle h4Style() {
		return this.parStyle("h4");
	}
	
	
	private void init(JXMLNotePreferences prefs) {
		_translator=new DefaultXMLNoteTranslator();
		_listeners=new WeakSet<XMLNoteStylesChangedListener>(); 
		_styles=new Hashtable<String,XMLNoteParStyle>();
		_keys=null;
		_styles.put("par", new XMLNoteParStyle(this,
				prefs.get(
						"par", 
						"id=par!nm=Paragraph!font=Arial!pt=10!bottom=10!top=0!bold=0!underl=0!it=0!color=#000000!bgcolor=#ffffff!default=1!extracss="
				)
		)
		);
		_styles.put("line", new XMLNoteParStyle(this,
				prefs.get(
						"line", 
						"id=line!nm=Paragraph (Linestyle)!font=Arial!pt=10!bottom=0!top=0!bold=0!underl=0!it=0!color=#000000!bgcolor=#ffffff!default=0!extracss="
				)
		)
		);
		_styles.put("h1",new XMLNoteParStyle(this,
				prefs.get(
						"h1", 
						"id=h1!nm=Header 1!font=Arial!pt=18!bottom=12!top=10!bold=0!underl=0!it=0!color=#000000!bgcolor=#ffffff!keepwithnext=1!extracss="
				)
		)
		);
		_styles.put("h2",new XMLNoteParStyle(this,
				prefs.get(
						"h2", 
						"id=h2!nm=Header 2!font=Arial!pt=16!bottom=10!top=6!bold=0!underl=0!it=0!color=#000000!bgcolor=#ffffff!keepwithnext=1!extracss="
				)
		)
		);
		_styles.put("h3",new XMLNoteParStyle(this,
				prefs.get(
						"h3", 
						"id=h3!nm=Header 3!font=Arial!pt=14!bottom=10!top=6!bold=0!underl=0!it=0!color=#000000!bgcolor=#ffffff!keepwithnext=1!extracss="
				)
		)
		);
		_styles.put("h4",new XMLNoteParStyle(this,
				prefs.get(
						"h4", 
						"id=h4!nm=Header 4!font=Arial!pt=12!bottom=10!top=6!bold=0!underl=0!it=0!color=#000000!bgcolor=#ffffff!keepwithnext=1!extracss="
				)
		)
		);

	}

}
