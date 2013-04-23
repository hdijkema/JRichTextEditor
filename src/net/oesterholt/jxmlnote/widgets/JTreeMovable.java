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

package net.oesterholt.jxmlnote.widgets;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * Implements a subclass of JTree that supports drag and drop on its own elements. 
 * Using this drag/drop, users can move nodes around in the tree.
 * 
 * @author Hans Oesterholt-Dijkema
 */
public class JTreeMovable extends JTree implements MouseMotionListener {

	private static final long serialVersionUID = 1L;

	private static DataFlavor treePathFlavor=new DataFlavor(Integer.class,"Source HashCode of JTreeMovable");
	
	private TreePath		_sourceOfDrag=null;
	private Vector<Integer>	_sourceOfDragVector=null;
	//private JTreeMovable	_sourceTree;

	class MoveTransferHandler extends TransferHandler {

		private static final long serialVersionUID = 1L;
		
		private TransferHandler _default;
		
		public void exportAsDrag(JComponent c,InputEvent e,int a) {
			if (e instanceof MouseEvent) {
				MouseEvent me=(MouseEvent) e;
				
				// These are copied TreeNodes
				_sourceOfDrag=JTreeMovable.this.getPathForLocation(me.getX(), me.getY());
				if (_sourceOfDrag!=null) {
					// Map the treepath straight to the model
					Vector<Integer> indices=new Vector<Integer>();
					TreeNode p=(TreeNode) _sourceOfDrag.getLastPathComponent();
					while (p.getParent()!=null) {
						Integer index=p.getParent().getIndex(p);
						indices.insertElementAt(index, 0);
						p=p.getParent();
					}
					_sourceOfDragVector=indices;
				}
			}
			_default.exportAsDrag(c, e, a);
		}
		
        public boolean canImport(TransferHandler.TransferSupport info) {
        	//System.out.println(info);
        	// only support drop here
        	if (!info.isDrop()) { return false; }

            //info.setShowDropLocation(true);  //TODO: check if necessary

            // we only import treepaths
            if (!info.isDataFlavorSupported(treePathFlavor)) { return false; }

            return true;
        }

        public boolean importData(TransferHandler.TransferSupport info) {
        	// if we can't handle the import, say so
        	if (!canImport(info)) { return false; }

        	// fetch the drop location
        	JTree.DropLocation dl = (JTree.DropLocation)info.getDropLocation();


        	// fetch the path and child index from the drop location
        	TreePath path = dl.getPath();
        	int childIndex = dl.getChildIndex();

        	// fetch the data and bail if this fails
        	Vector<Integer> sourcePath;
        	Object sourceT;

        	try {
        		sourceT = info.getTransferable().getTransferData(treePathFlavor);
        	} catch (UnsupportedFlavorException e) {
        		return false;
        	} catch (IOException e) {
        		return false;
        	}

        	if (sourceT instanceof Integer) {
        		int sourceHash=(Integer) sourceT;
        		if (sourceHash==JTreeMovable.this.hashCode()) { 
        			JTreeMovable tree=JTreeMovable.this;
        			sourcePath=tree._sourceOfDragVector;
        			if (sourcePath==null) {
        				return false;
        			}

        			// if child index is -1, the drop was on top of the path, so we'll
        			// treat it as inserting at the end of that path's list of children
        			if (childIndex == -1) {
        				childIndex = JTreeMovable.this.getModel().getChildCount(path.getLastPathComponent());
        			}

        			// Move the node. Remove it at the source location, insert it at the drop location

        			TreeModel dmodel=tree.getModel();
        			if (dmodel instanceof DefaultTreeModel) {
        				DefaultTreeModel model=(DefaultTreeModel) dmodel;
        				//	Object dnode=sourcePath.getLastPathComponent();
        				TreeNode source=(TreeNode) model.getRoot();
        				Iterator<Integer> it=sourcePath.iterator();
        				int sourceIndex=-1;
        				while (it.hasNext()) {
        					sourceIndex=it.next();
        					source=source.getChildAt(sourceIndex);
        				}
        				Object tnode=path.getLastPathComponent();
        				if (tnode instanceof MutableTreeNode && source instanceof MutableTreeNode) {
        					MutableTreeNode sourceNode=(MutableTreeNode) source;
        					MutableTreeNode targetNode=(MutableTreeNode) tnode;

        					TreeNode sourceParent=sourceNode.getParent();
        					model.removeNodeFromParent(sourceNode);
        					if (sourceParent==targetNode && sourceIndex<childIndex) { childIndex-=1; }
        					model.insertNodeInto(sourceNode, targetNode, childIndex);

        					TreePath p=path.pathByAddingChild(sourceNode);
        					JTreeMovable.this.makeVisible(p);
        					JTreeMovable.this.scrollPathToVisible(p);
        					//tree.scrollRectToVisible(tree.getPathBounds(path.pathByAddingChild(newNode)));

        					tree._sourceOfDrag=null;
        					tree._sourceOfDragVector=null;
        				} else {
        					return false;
        				}
        			} else {
        				return false;
        			} 
        		} else {
        			return false;
        		}
        	} else {
        		return false;
        	}

        	return true;
        }
        
        protected Transferable createTransferable(JComponent c) {
        	return new Transferable() {

				public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
					if (!isDataFlavorSupported(flavor)) {
						throw new UnsupportedFlavorException(flavor);
					} else if (_sourceOfDrag==null) {
						throw new IOException("No source");
					}
					return (Integer) JTreeMovable.this.hashCode();
				}

				public DataFlavor[] getTransferDataFlavors() {
					return new DataFlavor[] { treePathFlavor };
				}

				public boolean isDataFlavorSupported(DataFlavor flavor) {
					return (flavor==treePathFlavor);
				}
        		
        	};
        }
        
        
        
        public MoveTransferHandler(TransferHandler old) {
        	_default=old;
        }
	}

	public void mouseDragged(MouseEvent e) {
		this.getTransferHandler().exportAsDrag(this, e,  TransferHandler.COPY);
	}


	public void mouseMoved(MouseEvent e) {
	}
	
	public JTreeMovable(DefaultTreeModel model) {
		super(model);
		super.setTransferHandler(new MoveTransferHandler(super.getTransferHandler()));
		super.setDragEnabled(true);
		super.setDropMode(DropMode.ON_OR_INSERT);
		super.addMouseMotionListener(this);
	}
}
