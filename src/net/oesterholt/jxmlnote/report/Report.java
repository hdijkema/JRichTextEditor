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

package net.oesterholt.jxmlnote.report;

import java.awt.Image;
import java.io.File;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import net.oesterholt.jxmlnote.document.XMLNoteDocument;
import net.oesterholt.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.oesterholt.jxmlnote.interfaces.MarkMarkupProviderMaker;
import net.oesterholt.jxmlnote.report.elements.Cell;
import net.oesterholt.jxmlnote.report.elements.Chunk;
import net.oesterholt.jxmlnote.report.elements.Paragraph;
import net.oesterholt.jxmlnote.report.elements.Rectangle;
import net.oesterholt.jxmlnote.report.elements.ReportElement;
import net.oesterholt.jxmlnote.report.elements.Tab;
import net.oesterholt.jxmlnote.report.elements.Table;
import net.oesterholt.jxmlnote.styles.XMLNoteParStyle;


public abstract class Report {

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Exported types, interfaces and constants 
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public enum Align 		{ LEFT, RIGHT, CENTER, JUSTIFY };
	public enum PageSize 	{ LETTER, A4 };
	public enum Orientation { PORTRAIT, LANDSCAPE };
	public enum Property	{ PROGRESS };
	
	public interface ReportListener {
		public void nextPage(Report rep);
		public void endReport(Report rep);
		public Vector<ReportElement> getHeader(Report rep) throws ReportException;
		public Vector<ReportElement> getFooter(Report rep) throws ReportException;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Private classes, variables and definitions 
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	protected class ReportListeners {
		private Set<ReportListener>	_listeners;
		
		public void addReportListener(ReportListener l) { _listeners.add(l); }
		
		public void removeReportListener(ReportListener l) { _listeners.remove(l); }

		public void informNextPage(Report rep) {
			Iterator<ReportListener> it=_listeners.iterator();
			while (it.hasNext()) { it.next().nextPage(rep); }
		}
		
		public void informEndReport(Report rep) {
			Iterator<ReportListener> it=_listeners.iterator();
			while (it.hasNext()) { it.next().endReport(rep); }
		}
		
		public Vector<ReportElement> getHeader(Report rep) {
			Iterator<ReportListener> it=_listeners.iterator();
			Vector<ReportElement> tb=null;
			while (it.hasNext()) { 
				try {
					tb=it.next().getHeader(rep);
				} catch (ReportException e) {
					DefaultXMLNoteErrorHandler.exception(e);
				}
				if (tb!=null) { return tb; }
			}
			return null;
		}

		public Vector<ReportElement> getFooter(Report rep) {
			Iterator<ReportListener> it=_listeners.iterator();
			Vector<ReportElement> tb=null;
			while (it.hasNext()) { 
				try {
					tb=it.next().getFooter(rep);
				} catch (ReportException e) {
					DefaultXMLNoteErrorHandler.exception(e);
				}
				if (tb!=null) { return tb; }
			}
			return null;
		}
		
		public ReportListeners() {
			_listeners=new HashSet<ReportListener>();;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Private variables 
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private Hashtable<Object,Object>	_properties=new Hashtable<Object,Object>();
	private ReportListeners				_reportListeners=new ReportListeners();

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Default implementations 
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void	setProperty(Object key,Object value) {
		_properties.put(key, value);
	}
	
	public Object getProperty(Object key) {
		return _properties.get(key);
	}
	
	public void	removeProperty(Object key) {
		_properties.remove(key);
	}
	
	protected ReportListeners getReportListeners() {
		return _reportListeners;
	}
	
	public void addReportListener(ReportListener l) {
		_reportListeners.addReportListener(l);
	}
	
	public void removeReportListener(ReportListener l) {
		_reportListeners.removeReportListener(l);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Abstract methods 
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	public abstract Chunk 		createChunk(String txt) throws ReportException;
	public abstract Chunk 		createChunk(String txt,boolean bold,boolean italic,boolean underline) throws ReportException;
	public abstract Chunk 		createChunk(Image img) throws ReportException;
	public abstract Chunk 		createChunk(File imageFile) throws ReportException;
	public abstract Chunk		createChunk(Image img,float scalePercentage) throws ReportException;
	public abstract Chunk		createChunk(File imageFile,float scalePercentage) throws ReportException;
	public abstract Chunk		createChunkForWidth(Image img,float scaleToWidthInPt) throws ReportException;
	public abstract Chunk		createChunkForWidth(File imageFile,float scaleToWidthInPt) throws ReportException;
	public abstract Tab			createTab(Paragraph p) throws ReportException;
	
	public abstract Paragraph 	createParagraph(XMLNoteParStyle style) throws ReportException;
	public abstract Paragraph	createParagraph() throws ReportException;
	
	public abstract Table 		createTable(Align align,float percentageOfWidth,float [] relativeWidths) throws ReportException;
	public abstract Cell		createCell(ReportElement p) throws ReportException;
	public abstract Cell		textCell(XMLNoteParStyle st,String txt) throws ReportException;
	public abstract Cell		textCell(XMLNoteParStyle st,String txt,Align a) throws ReportException;
	
	public abstract void		add(ReportElement el) throws ReportException;
	public abstract void		add(ReportElement el,boolean keepWithNext) throws ReportException;
	public abstract void		addPageBreak() throws ReportException;
	
	public abstract void		setPageSize(PageSize pg) throws ReportException;
	public abstract void 		setMargins(Rectangle m) throws ReportException;
	public abstract Rectangle 	getPageRect() throws ReportException;
	public abstract Rectangle	getMargins() throws ReportException;
	public abstract float		getTextWidth() throws ReportException;
	public abstract void        setOrientation(Orientation o) throws ReportException;
	
	public abstract int			getCurrentPageNumber() throws ReportException;
	
	public abstract void		beginReport(File output) throws ReportException;
	public abstract void		endReport() throws ReportException;
	
	public abstract void		setMetaAuthor(String author) throws ReportException;
	public abstract void		setMetaCreator(String creator) throws ReportException;
	public abstract void 		setMetaCreationDate() throws ReportException;
	public abstract void 		setMetaDescription(String description) throws ReportException;
	public abstract void		setMetaTitle(String title) throws ReportException;
	public abstract void		setMetaKeywords(Vector<String> keys) throws ReportException;

	public abstract void		cancel() throws ReportException;
	public abstract boolean		canceled() throws ReportException;

	public abstract void		addXMLNote(XMLNoteDocument doc,MarkMarkupProviderMaker maker,XMLNoteToReport.MarkTextProvider prov) throws ReportException;
}
