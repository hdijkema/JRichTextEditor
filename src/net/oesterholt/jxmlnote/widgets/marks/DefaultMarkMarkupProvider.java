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

package net.oesterholt.jxmlnote.widgets.marks;

import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.oesterholt.jxmlnote.document.XMLNoteMark;
import net.oesterholt.jxmlnote.interfaces.MarkMarkupProvider;


public class DefaultMarkMarkupProvider implements MarkMarkupProvider {
	
	private Color 				_color;
	private Color				_color2;
    private Color               _textColor=null;
	private MarkupType			_type;
	private Set<ChangeListener> _listeners;
	
	/**
	 * See MarkMarkupProvider
	 */
	public Color markColor(XMLNoteMark m) {
		return _color;
	}
	
	public Color markColor2(XMLNoteMark m) {
		return _color2;
	}

    /**
     * See MarkMarkupProvider
     */
    public Color textColor(XMLNoteMark m) {
            return _textColor;
    }
	
	/**
	 * See MarkMarkupProvider
	 */
	public MarkupType type(XMLNoteMark m) {
		return _type;
	}
	
	/**
	 * See MarkMarkupProvider
	 */
	public void addChangeListener(ChangeListener l) {
		_listeners.add(l);
	}
	
	/**
	 * See MarkMarkupProvider
	 */
	public void removeChangeListener(ChangeListener l) {
		_listeners.remove(l);
	}
	
	protected void fireChangedEvent() {
		Iterator<ChangeListener> it=_listeners.iterator();
		while(it.hasNext()) {
			it.next().markMarkupChanged();
		}
	} 
	
	/**
	 * Changes the color of this markup provider; all associated markers in the document will change color to c,
	 * if it is different from the previous one (a fireChangedEvent() will happen).
	 * 
	 * @param c
	 */
	public void setColor(Color c) {
		if (_color==null && c!=null) {
			_color=c;
			fireChangedEvent();
		} else if (c==null) {
			_color=c;
			fireChangedEvent();
		} else if (!c.equals(_color)) {
			_color=c;
			fireChangedEvent();
		}
	}
	
	/**
	 * Changes the second color of this markup provider. The second color is only
	 * relevant for type 'BOTH' and will control the underline highlighter in that
	 * case. 
	 * 
	 * @param c
	 */
	public void setColor2(Color c) {
		if (_color2==null && c!=null) {
			_color2=c;
			fireChangedEvent();
		} else if (c==null) {
			_color2=c;
			fireChangedEvent();
		} else if (!c.equals(_color2)) {
			_color2=c;
			fireChangedEvent();
		}
	}

	/**
	 * Changes the Text color of this markup provider; all associated markers in the document will change Text color to c,
	 * if it is different from the previous one (a fireChangedEvent() will happen).
	 *
	 * @param c
	 */
	public void setTextColor(Color c) {
		if (_textColor==null && c!=null) {
			_textColor=c;
			fireChangedEvent();
		} else if (c==null) {
			_textColor=c;
			fireChangedEvent();
		} else if (!c.equals(_color)) {
			_textColor=c;
			fireChangedEvent();
		}
	}
	
	/**
	 * Changes the markup type of this markup provider; all associated markers in the document will change the
	 * type to the geven type, if it is different from the previous one (a fireChangedEvent() will happen).
	 * @param t
	 */
	public void setType(MarkupType t) {
		if (!_type.equals(t)) {
			_type=t;
			fireChangedEvent();
		}
	}
	
	/**
	 * Constructs a default markup provider with color Yellow.
	 */
	public DefaultMarkMarkupProvider() {
		this(MarkupType.MARKER,Color.yellow);
	}
	
	/**
	 * Constructus a markup provider with color c
	 * @param c
	 */
	public DefaultMarkMarkupProvider(Color c) {
		this(MarkupType.MARKER,c);
	}

	
	/**
	 * Constructs a default markup provider with MarkupType and Color.
	 * @param type
	 * @param c
	 */
	public DefaultMarkMarkupProvider(MarkupType type,Color c) {
		_color=c;
		_color2=null;
		_type=type;
		_listeners=new HashSet<ChangeListener>();
	}
}
