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

package net.dijkema.jxmlnote.document;

import java.util.ArrayList;
import java.util.Stack;
import javax.swing.text.MutableAttributeSet;

public class ContentTree
{
	private Node		root=new Node(ROO,null);
	private Stack<Node>	nodeStack=new Stack<Node>();
	private Node		currentNode=root;
	private Node		currentText=null;
	
	/**
	 * returns current node
	 * @return
	 */
	public Node getCurrentNode()
	{
		return currentNode;
	}
	
	/**
	 * returns the root node of this content
	 * @return
	 */
	public Node getRoot()
	{
		return root;
	}
	
	/**
	 * add child node to the current node
	 * @param n
	 */
	protected void addNode(Node n)
	{
		currentNode.addChild(n);
		nodeStack.push(currentNode);
		currentNode=n;		
	}
	
	/**
	 * close the current node (parent node od current node will be the current node)
	 */
	public void endNode()
	{
		currentText=null;		
		currentNode=nodeStack.pop();
	}

	/**
	 * start a new node
	 * @param attr
	 */
	public void startNode(MutableAttributeSet attr,String nodeType)
	{		
		addNode(new Node(nodeType,attr));
	}
	
	/**
	 * add text content (to the current node which should be a paragraph)
	 * @param attr
	 * @param text
	 */
	public void addContent(MutableAttributeSet attr,String text)
	{		
		if(currentText==null || attr!=currentText.attr)
		{
			currentText=new Node(CHR,attr,text);
			currentNode.addChild(currentText);
		}
		else
			currentText.text=currentText.text+text;
	}
	
	public static final String ROO	="root";		//root
	public static final String PAR	="paragraph";	//paragraph
	public static final String CHR	="char";		//char
	
	/**
	 * represents elements within the content tree
	 * @author  Gungor Senyurt
	 */
	public class Node
	{
		private String 			nodeType;							//type of node 
		private MutableAttributeSet	attr;							//node attributes
		private ArrayList<Node>	children=new ArrayList<Node>();		//children
		private	String			text;								//text data, if applicable
		
		/**
		 * constructor
		 * @param nodeType
		 * @param attr
		 */
		public Node(String nodeType, MutableAttributeSet attr)
		{
			super();
			this.nodeType = nodeType;
			this.attr = attr;
		}
		
		/**
		 * constructor
		 * @param nodeType
		 * @param attr
		 * @param text
		 */
		public Node(String nodeType, MutableAttributeSet attr,String text)
		{
			super();
			this.nodeType = nodeType;
			this.attr = attr;
			this.text = text;
		}

		/**
		 * add a child node to the end
		 * @param child
		 */
		public void addChild(Node child)
		{			
			children.add(child);
		}
		
		/**
		 * returns the number of child nodes.
		 * @return
		 */
		public int getChildCount()
		{
			return children.size();
		}
		
		/**
		 * returns the node at the given 0 based index
		 * @param index
		 * @return
		 */
		public Node getChildAt(int index)
		{
			if(index<0 || index>= children.size()) return null;
			return children.get(index);
		}
		
		/**
		 * returns the node type
		 * @return
		 */
		public String getNodeType()
		{
			return nodeType;
		}
		/**
		 * sets node type
		 * @param  nodeType
		 */
		public void setNodeType(String nodeType)
		{
			this.nodeType = nodeType;
		}
		/**
		 * returns node attributes
		 * @return
		 */
		public MutableAttributeSet getAttr()
		{
			return attr;
		}
		/**
		 * sets node attributes
		 * @param  attr
		 */
		public void setAttr(MutableAttributeSet attr)
		{
			this.attr = attr;
		}
		/**
		 * returns node text content
		 * @return
		 */
		public String getText()
		{
			return text;
		}
		/**
		 * sets node text content
		 * @param  text
		 */
		public void setText(String text)
		{
			this.text = text;
		}		
	}
}