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

package net.oesterholt.jxmlnote.document;

import java.util.Vector;

/**
 * This interface is used to propagate changes in marks.
 * 
 * @author Hans Oesterholt-Dijkema
 */
public interface XMLNoteMarkListener {
	
	public enum ChangedMessageWay {
		FOR_ALL, FOR_CHANGED, DONT_CARE
	}

	/**
	 * Indicates that a mark will be inserted. Return true to veto the insert, false otherwise.
	 * 
	 * @param id
	 * @param offset
	 * @param length
	 * @return
	 */
	public boolean markInsert(String id,int offset,int length);
	
	/**
	 * Indicates that a mark with the given id will be removed. Return true to veto the remove, false otherwise.
	 * This method could also be used to check whether a mark with the given id actually exists.
	 * 
	 * @param id
	 * @return
	 */
	public boolean markRemove(String id);
	
	/**
	 * Indicates that the offsets (<code>offset()</code>, <code>endOffset()</code>) in the text of the mark have changed.
	 * This occurs normally when editing text, and will happen often. <code>indexInOrderedMarks</code> is the index of
	 * m in orderedMarks. orderedMarks are marks sorted ascending by (<code>offset()</code>, <code>id()</code>).
	 * 
	 * Changes in offset will be signaled in the order of orderedMarks. Not changed marks will not be signaled.
	 *  
	 * @param m
	 * @param orderedMarks
	 * @param indexInOrderedMarks
	 */
	public void markOffsetsChanged(XMLNoteMark m,Vector<XMLNoteMark> orderedMarks,int indexInOrderedMarks);
	
	/**
	 * Indicates that the id of a mark has been changed. The changed mark is given, and the id of the mark
	 * before the change is added as parameter 'previousId'. This method is only called if an id of a mark
	 * actually changes (i.e. <code>m.id().equal(previousId)</code> methods returns <b>false</b>).
	 * 
	 * @param m				The changed mark.
	 * @param previousId	The previous id of the mark.
	 */
	public void markIdChanged(XMLNoteMark m,String previousId);
	
	/**
	 * Indicates that a mark has been removed. orderedMarks are the left over marks, sorted ascending 
	 * by (<code>offset()</code>, <code>id()</code>). m is the removed mark.
	 * 
	 * Return true, if mark removal may be undone; false, otherwise. If only one listener returns false,
	 * inserting an UndoableEdit for the removal is canceled.
	 *  
	 * @param m
	 * @param orderedMarks
	 * @return
	 */
	public boolean markRemoved(XMLNoteMark m,Vector<XMLNoteMark> orderedMarks);
	
	/**
	 * Indicates that a mark has been inserted. <code>indexInOrderedMarks</code> is the index of
	 * m in orderedMarks. orderedMarks are marks sorted ascending by (<code>offset()</code>, <code>id()</code>).
	 * 
	 * Return true, if mark insertion may be undone; false, otherwise. If only one listener returns false,
	 * inserting an UndoableEdit for the insertion is canceled.
	 * 
	 * @param m
	 * @param orderedMarks
	 * @param indexInOrderedMarks
	 * @return
	 */
	public boolean markInserted(XMLNoteMark m,Vector<XMLNoteMark> orderedMarks,int indexInOrderedMarks);
	
	
	/**
	 * begin() is called just before a sequence of markOffsetsChanged() starts.
	 * Note: markRemoved(), markInserted(), etc. can be called anytime and never in conjunction with markOffsetsChanged().
	 * Must return whether the change message must go for all marks or only for the changed marks.
	 * FOR_ALL precedes FOR_CHANGED. So when one listener returns FOR_ALL, that will happen to all listeners.
	 * @return
	 */
	public ChangedMessageWay markAdminBegin();
	
	/**
	 * end() is called after a sequence o markOffsetsChanged() ends.
	 * Note: markRemoved(), markInserted(), etc. can be called anytime and never in conjunction with markOffsetsChanged().
	 */
	public void markAdminEnd();
	
	/**
	 * This callback will be called when iterateOverMarks() is called on the document.
	 * It can be used to perform administrative tasks outside the context of the document, more
	 * associated with the view.
	 * @param m
	 * @param info
	 */
	public void iteratorOperation(XMLNoteMark m,Object info);

}
