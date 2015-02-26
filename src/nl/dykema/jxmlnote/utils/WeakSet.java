/* ******************************************************************************
 *
 *       Copyright 2008-2010 Hans Dijkema
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

package nl.dykema.jxmlnote.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.WeakHashMap;

@SuppressWarnings("rawtypes")
public class WeakSet<T> implements Set<T> {
	
	private WeakHashMap<T,Integer> map=new WeakHashMap<T,Integer>();
	
	public boolean add(T element) {
		if (map.containsKey(element)) {
			return false;
		} else {
			Integer i=1;
			map.put(element, i);
			return true;
		}
	}
	
	public boolean contains(Object element) {
		return map.get(element)!=null;
	}

	public boolean addAll(Collection<? extends T> c) {
		Iterator<? extends T> it=c.iterator();
		boolean b=false;
		while (it.hasNext()) { 
			if (add(it.next())) { b=true; }
		}
		return b;
	}

	public boolean containsAll(Collection<?> c) {
		Iterator<?> it=c.iterator();
		while (it.hasNext()) {
			if (!contains(it.next())) { return false; }
		}
		return true;
	}

	public Iterator<T> iterator() {
		return map.keySet().iterator();
	}

	public boolean removeAll(Collection<?> c) {
		Iterator<?> it=c.iterator();
		boolean b=false;
		while (it.hasNext()) {
			if (remove(it.next())) { b=true; }
		}
		return b;
	}

	public boolean retainAll(Collection<?> c) {
		Iterator<T> it=this.iterator();
		Vector<T> to_remove=new Vector<T>();
		while (it.hasNext()) {
			T o=it.next();
			if (!c.contains(o)) { to_remove.add(o); }
		}
		it=to_remove.iterator();
		while (it.hasNext()) {
			map.remove(it.next());
		}
		return to_remove.size()>0;
	}

	public Object[] toArray() {
		return map.keySet().toArray();
	}

	@SuppressWarnings("unchecked")
	public Object[] toArray(Object[] a) {
		return map.keySet().toArray(a);
	}

	public void clear() {
		map.clear();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean remove(Object o) {
		if (contains(o)) {
			map.remove(o);
			return true;
		} else {
			return false;
		}
	}

	public int size() {
		return map.size();
	}

}
