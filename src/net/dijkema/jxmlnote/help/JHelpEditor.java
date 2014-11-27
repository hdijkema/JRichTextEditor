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

package net.dijkema.jxmlnote.help;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.dijkema.JXMLNoteEditor;
import net.dijkema.jxmlnote.document.DocumentAdminEvent;
import net.dijkema.jxmlnote.document.DocumentAdminListener;
import net.dijkema.jxmlnote.document.DocumentPreListener;
import net.dijkema.jxmlnote.document.XMLNoteDocument;
import net.dijkema.jxmlnote.document.XMLNoteImageIcon;
import net.dijkema.jxmlnote.document.XMLNoteImageIconSize;
import net.dijkema.jxmlnote.document.XMLNoteMark;
import net.dijkema.jxmlnote.exceptions.BadDocumentException;
import net.dijkema.jxmlnote.exceptions.BadStyleException;
import net.dijkema.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.dijkema.jxmlnote.exceptions.MarkNoExistException;
import net.dijkema.jxmlnote.exceptions.NoSelectionException;
import net.dijkema.jxmlnote.help.data.HelpEntry;
import net.dijkema.jxmlnote.help.data.HelpImage;
import net.dijkema.jxmlnote.help.data.HelpTopic;
import net.dijkema.jxmlnote.icons.AddImage;
import net.dijkema.jxmlnote.icons.AddTopic;
import net.dijkema.jxmlnote.icons.ChangeTopic;
import net.dijkema.jxmlnote.icons.FlamencoIconAdapter;
import net.dijkema.jxmlnote.icons.InsertHyperlink;
import net.dijkema.jxmlnote.icons.InsertLink;
import net.dijkema.jxmlnote.icons.RemoveHyperlink;
import net.dijkema.jxmlnote.icons.RemoveLink;
import net.dijkema.jxmlnote.icons.RemoveTopic;
import net.dijkema.jxmlnote.interfaces.MarkMarkupProvider;
import net.dijkema.jxmlnote.interfaces.MarkMarkupProviderMaker;
import net.dijkema.jxmlnote.interfaces.XMLNotePreferences;
import net.dijkema.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import net.dijkema.jxmlnote.internationalization.XMLNoteTranslator;
import net.dijkema.jxmlnote.toolbar.JXMLNoteIcon;
import net.dijkema.jxmlnote.toolbar.JXMLNoteIconPainter;
import net.dijkema.jxmlnote.toolbar.JXMLNoteToolBar;
import net.dijkema.jxmlnote.utils.BufferedImageBuilder;
import net.dijkema.jxmlnote.widgets.JRecentlyUsedMenu;
import net.dijkema.jxmlnote.widgets.JSizeGroup;
import net.dijkema.jxmlnote.widgets.JTreeMovable;
import net.dijkema.jxmlnote.widgets.JXMLNoteSwingUtils;
import net.dijkema.jxmlnote.widgets.JRecentlyUsedMenu.RecentlyUsedProvider;
import net.dijkema.jxmlnote.widgets.marks.DefaultMarkMarkupProvider;


/**
 * With this class, help can be edited in a given JarFile.
 * 
 * @author Hans Dijkema
 * 
 */
public class JHelpEditor extends JPanel implements ActionListener,
		TreeSelectionListener, RecentlyUsedProvider, XMLNoteImageIcon.Provider {

	// ////////////////////////////////////////////////////////////////////////////////
	// Private variables
	// ////////////////////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 1L;

	private File _helpjar;
	private JXMLNoteEditor _editor;
	private XMLNoteDocument _document;
	private JXMLNoteToolBar _toolbar;
	private JXMLNoteToolBar _helpToolbar;
	private JTreeMovable _tree;
	private DefaultTreeModel _treeModel;
	private DefaultMutableTreeNode _topics;
	private XMLNoteTranslator _tr;
	private Hashtable<String, HelpEntry> _help;
	private Hashtable<String, XMLNoteImageIcon> _images;
	private Hashtable<String, XMLNoteImageIcon> _imageBackingStore;
	private TreePath _selected;
	private boolean _saved;
	private boolean _newfile;
	private int _next;
	private JFrame _frame;
	private JRecentlyUsedMenu _recent;
	private JSplitPane _splitpane;
	private MyIconPainter _painter;
	private XMLNotePreferences _preferences;
    private DefaultMarkMarkupProvider _markProvider;
    private DefaultMarkMarkupProvider _hyperlinkProvider;
    private Color	_cTopic=Color.blue;
	private Color   _cHyperlink=new Color(101,0,168);


	private class MyIconPainter implements JXMLNoteIconPainter {

		public void paint(int x, int y, int w, int h, Graphics2D g,
				Component u, String iconType) {
			FlamencoIconAdapter fia = null;
			if (iconType.equals("add-topic")) {
				fia = new AddTopic();
			} else if (iconType.equals("remove-topic")) {
				fia = new RemoveTopic();
			} else if (iconType.equals("change-topic")) {
				fia = new ChangeTopic();
			} else if (iconType.equals("add-image")) {
				fia = new AddImage();
			} else if (iconType.equals("insert-link")) {
				fia = new InsertLink();
			} else if (iconType.equals("remove-link")) {
				fia = new RemoveLink();
			} else if (iconType.equals("insert-hyperlink")) {
				fia = new InsertHyperlink();
			} else if (iconType.equals("remove-hyperlink")) {
				fia = new RemoveHyperlink();
			}

			Graphics2D gg = JXMLNoteIcon.prepareG(x, y, w, h,
					fia.getOrigWidth(), fia.getOrigHeight(), g);
			fia.paint(gg);
		}

	}

	// ////////////////////////////////////////////////////////////////////////////////
	// interfaces
	// ////////////////////////////////////////////////////////////////////////////////

	interface DoSomethingWithNode {
		public void anything(DefaultMutableTreeNode node);
	}

	// ////////////////////////////////////////////////////////////////////////////////
	// Supporting methods
	// ////////////////////////////////////////////////////////////////////////////////

	private JMenu makeMenu(String label) {
		return JRecentlyUsedMenu.makeMenu(label);
	}

	private JMenuItem makeItem(String label, Icon icon, String actionCommand) {
		return JRecentlyUsedMenu.makeMenuItem(label, icon, actionCommand, this);
	}

	private void writeObject(JarOutputStream jout, Object obj)
			throws IOException {
		ObjectOutputStream oout = new ObjectOutputStream(jout);
		oout.writeObject(obj);
	}
	
	private Window getWindow(Component c) {
		return SwingUtilities.getWindowAncestor(c);
	}
	
	// ////////////////////////////////////////////////////////////////////////////////
	// Image handling
	// ////////////////////////////////////////////////////////////////////////////////

	private XMLNoteImageIcon nullImage = null;

	public XMLNoteImageIcon getIcon(String id) {
		if (nullImage == null) {
			nullImage = new XMLNoteImageIcon(
					new JXMLNoteIcon("NoImage", 50, 50));
			nullImage.setId("__null__");
		}

		XMLNoteImageIcon img = _images.get(id);
		if (img == null) {
			img = _imageBackingStore.get(id); // To support undo/redo
			if (img == null) {
				_images.put("__null__", nullImage);
				return nullImage;
			} else {
				_images.put(id, img);
				_imageBackingStore.remove(id);
				return img;
			}
		} else {
			return img;
		}
	}
	
	public XMLNoteImageIcon getIcon(URL url,String description,XMLNoteImageIconSize size) {
		XMLNoteImageIcon icn=new XMLNoteImageIcon(url,description,size);
		String id=UUID.randomUUID().toString();
		icn.setId(id);
		_images.put(id, icn);
		return icn;
	}
	
	private Vector<XMLNoteImageIcon> _imagesToBeRemoved = null;

	private void addImageDocListeners() {
		_document.addDocumentPreListener(new DocumentPreListener() {
			public boolean insertUpdate(DocumentEvent e) {
				return false; // no veto
			}

			public boolean changeUpdate(DocumentEvent e) {
				return false; // no veto
			}

			public boolean removeUpdate(DocumentEvent e) {
				int offset = e.getOffset();
				int len = e.getLength();
				try {
					_imagesToBeRemoved = _document.imagesInRange(offset, len);
					return false;
				} catch (BadLocationException e1) {
					// unexpected
					DefaultXMLNoteErrorHandler.exception(e1);
					return true; // let nothing happen
				}
			}
		});

		_document.addDocumentPostListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent e) {
			}

			public void insertUpdate(DocumentEvent e) {
				int offset=e.getOffset();
				int length=e.getLength();
				try {
					Vector<XMLNoteImageIcon> v=_document.imagesInRange(offset, length);
					Iterator<XMLNoteImageIcon> it = v.iterator();
					while (it.hasNext()) {
						XMLNoteImageIcon icn = it.next();
						_imageBackingStore.remove(icn.getId());
						_images.put(icn.getId(),icn);
					}
				} catch (BadLocationException E) {
					DefaultXMLNoteErrorHandler.exception(E);
				}
			}

			public void removeUpdate(DocumentEvent e) {
				if (_imagesToBeRemoved != null) {
					Iterator<XMLNoteImageIcon> it = _imagesToBeRemoved.iterator();
					while (it.hasNext()) {
						XMLNoteImageIcon icn = it.next();
						_imageBackingStore.put(icn.getId(), icn);
						_images.remove(icn.getId());
					}
					_imagesToBeRemoved=null;
				}
			}
		});
	}

	// ////////////////////////////////////////////////////////////////////////////////
	// GUI Creation
	// ////////////////////////////////////////////////////////////////////////////////

	public interface ToolsProvider {
		public void addTools(JXMLNoteToolBar bar);
	}
	
	public int getMaxCount() {
		return 6;
	}

	@SuppressWarnings("unchecked")
	public Vector<String> getList() {
		XMLNotePreferences prefs = _preferences;
		Vector<String> vec = new Vector<String>();
		String val = prefs.getString("recently_used_files", null);
		if (val != null) {
			ByteArrayInputStream bin = new ByteArrayInputStream(val.getBytes());
			XMLDecoder dec = new XMLDecoder(bin);
			vec = (Vector<String>) dec.readObject();
			dec.close();
		}
		return vec;
	}

	public void putList(Vector<String> list) {
		XMLNotePreferences prefs = _preferences;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		XMLEncoder enc = new XMLEncoder(bout);
		enc.writeObject(list);
		enc.flush();
		enc.close();
		String xml = bout.toString();
		prefs.put("recently_used_files", xml);
	}

	public String clearListText() {
		return _tr._("Clear");
	}

	private void createGui(ToolsProvider toolprovider) {
		BorderLayout layout = new BorderLayout();
		layout.setVgap(3);
		layout.setHgap(3);

		super.setLayout(layout);

		try {
			_document = new XMLNoteDocument();
		} catch (BadStyleException e) {
			DefaultXMLNoteErrorHandler.fatal(e, -1,
					"Unexpected: bad style exception");
		}
		_document.setXMLNoteImageIconProvider(this);
		_document.addDocumentAdminListener(new DocumentAdminListener() {
			public boolean documentWillBeReset(DocumentAdminEvent e) {
				return false;
			}

			public void documentHasBeenReset(DocumentAdminEvent e) {
			}

			public boolean documentWillBeCleared(DocumentAdminEvent e) {
				return false;
			}

			public void documentHasBeenCleared(DocumentAdminEvent e) {
			}

			public void documentChangedState(DocumentAdminEvent e) {
				//System.out.println("document changed="+_document.changed());
			}
			
		});
		_editor = new JXMLNoteEditor(_document,new MarkMarkupProviderMaker() {
                    public MarkMarkupProvider create(String markId, String markClass) {
                    	if (markClass.startsWith("TopicLink:")) {
                    		return _markProvider;
                    	} else {
                    		return _hyperlinkProvider;
                    	}
                    }
                });
		_toolbar = _editor.toolbar();
		_toolbar.initToolBar();

		_treeModel = new DefaultTreeModel(null);
		loadTopics();
		_tree = new JTreeMovable(_treeModel);
		_tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		JPanel editPane = new JPanel();
		editPane.setLayout(new BorderLayout());
		editPane.add(_toolbar, BorderLayout.NORTH);
		editPane.add(_editor, BorderLayout.CENTER);

		JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(_tree), editPane);
		_splitpane = pane;
		pane.setContinuousLayout(true);

		_helpToolbar = new JXMLNoteToolBar(this, null, 36);
		_helpToolbar.removeAllSections();
		_helpToolbar.addSection("file", _tr._("File operations"));
		_helpToolbar.add("file", JXMLNoteToolBar.ACTION_NEW,
				_tr._("Begin new Help File"), this);
		_helpToolbar.add("file", JXMLNoteToolBar.ACTION_SAVE,
				_tr._("Save a Help File"), this);
		_helpToolbar.add("file", JXMLNoteToolBar.ACTION_LOAD,
				_tr._("Open a Help File"), this);
		_helpToolbar.addSection("topics", _tr._("Topic actions"));
		_helpToolbar.add("topics", "add-topic", _tr._("Add a new topic"), this,
				new JXMLNoteIcon("add-topic", _painter));
		_helpToolbar.add("topics", "change-topic", _tr._("Change a topic"),
				this, new JXMLNoteIcon("change-topic", _painter));
		_helpToolbar.add("topics", "remove-topic", _tr._("Remove a topic"),
				this, new JXMLNoteIcon("remove-topic", _painter));
		_helpToolbar.add("topics", "insert-link", _tr._("Insert link"),
				this, new JXMLNoteIcon("insert-link", _painter));
		_helpToolbar.add("topics", "remove-link", _tr._("Remove link"),
				this, new JXMLNoteIcon("remove-link", _painter));
		_helpToolbar.add("topics", "insert-hyperlink", _tr._("Insert hyperlink"),
				this, new JXMLNoteIcon("insert-hyperlink", _painter));
		_helpToolbar.add("topics", "remove-hyperlink", _tr._("Remove hyperlink"),
				this, new JXMLNoteIcon("remove-hyperlink", _painter));
		_helpToolbar.addSection("image", _tr._("Image handling"));
		_helpToolbar.add("image", "add-image", _tr._("Add an image"), this,
				new JXMLNoteIcon("add-image", _painter));
		_helpToolbar.addSection("test", _tr._("Test help"));
		_helpToolbar.add("test",JXMLNoteToolBar.ACTION_HELP,_tr._("Test the help file"), this);
		toolprovider.addTools(_helpToolbar);
		_helpToolbar.initToolBar();
		super.add(_helpToolbar, BorderLayout.NORTH);

		super.add(pane, BorderLayout.CENTER);

		_tree.addTreeSelectionListener(this);
	}

	private void setTitle() {
		if (_frame != null) {
			if (_helpjar == null) {
				_frame.setTitle(_tr._("Help File Editor"));
			} else {
				String title = _tr._("Help File Editor: %s");
				_frame.setTitle(String.format(title, _helpjar.getName()));
			}
		}
	}

	// ////////////////////////////////////////////////////////////////////////////////
	// Loading and saving
	// ////////////////////////////////////////////////////////////////////////////////

	private void loadTopics() {

		cleanup(false);

		class R extends SwingWorker<String,String> {
			
			private DefaultListModel _list;
			private JDialog 		 _dlg;
			private boolean 		 _finished;
			
			public String doInBackground() {
				try {
					if (_helpjar != null && _helpjar.canRead()) {
						publish(String.format(_tr._("Opening Help File %s"),_helpjar));
						JarFile hj = new JarFile(_helpjar);
						publish(String.format(_tr._("Reading Help Topics")));
						ZipEntry ze = hj.getEntry("HelpTopics.xnhlp");
						if (ze != null) {
							try {
								InputStream in = hj.getInputStream(ze);
								ObjectInputStream dec = new ObjectInputStream(in);
								_topics = (DefaultMutableTreeNode) dec.readObject();
								in.close();
							} catch (Exception e) {
								DefaultXMLNoteErrorHandler.exception(e);
							}

							@SuppressWarnings("unchecked")
							Enumeration<DefaultMutableTreeNode> en = (Enumeration<DefaultMutableTreeNode>) _topics
							.breadthFirstEnumeration();
							while (en.hasMoreElements()) {
								DefaultMutableTreeNode topic = en.nextElement();
								HelpTopic ht = (HelpTopic) topic.getUserObject();
								publish(String.format(_tr._("Help Topic %s (id=%s)"),ht.getName(),ht.getTopicId()));
								ZipEntry hze = hj.getEntry(ht.getTopicId() + ".top");
								try {
									InputStream in = hj.getInputStream(hze);
									ObjectInputStream dec = new ObjectInputStream(in);
									HelpEntry he = (HelpEntry) dec.readObject();
									in.close();
									if (he != null) {
										_help.put(ht.getTopicId(), he);
									}
								} catch (Exception e) {
									DefaultXMLNoteErrorHandler.exception(e);
								}
							}

							{
								ZipEntry kze = hj.getEntry("ImageIds.vec");
								publish(String.format(_tr._("Reading Image Vector")));
								try {
									InputStream in = hj.getInputStream(kze);
									ObjectInputStream dec = new ObjectInputStream(in);
									@SuppressWarnings("unchecked")
									Vector<String> keys = (Vector<String>) dec.readObject();
									int nimg=keys.size();
									int iimg=0;
									Iterator<String> it = keys.iterator();
									while (it.hasNext()) {
										String id = it.next();
										ZipEntry ize = hj.getEntry(id + ".img");
										in = hj.getInputStream(ize);
										//dec = new ObjectInputStream(in);
										HelpImage himg = new HelpImage();
										himg.read(in);
										iimg+=1;
										publish(String.format(_tr._("Reading Image %d of %d (size=%.1fkb) (%s)"),iimg,nimg,himg.sizeInKb(),id));
										XMLNoteImageIcon img = new XMLNoteImageIcon(
												himg.getId(), himg.getImageIcon(),
												himg.getDescription(),
												himg.getSize());
										_images.put(img.getId(), img);
									}
								} catch (Exception e) {
									DefaultXMLNoteErrorHandler.exception(e);
								}
							}
							
							publish(String.format(_tr._("Done reading Help File")));

						}
						hj.close();

						_newfile = false;

					}
				} catch (Exception E) {
					DefaultXMLNoteErrorHandler.exception(E);
				}
				
				return "done" ;
			}
			
			protected void process(List<String> stuff) {
				Iterator<String> it=stuff.iterator(); 
				while (it.hasNext()) {
					String s=it.next();
					_list.insertElementAt(s,0);
				}
			}
			
			protected void done() {
				_finished=true;
				try {
					Thread.sleep(1000);
				} catch (Exception E) {}
				_dlg.setVisible(false);
			}
			
			public boolean finished() {
				return _finished;
			}
			
			public R(DefaultListModel l,JDialog dlg) {
				_list=l;
				_dlg=dlg;
				_finished=false;
			}
		}
		
		JDialog dlg=new JDialog(SwingUtilities.getWindowAncestor(this),_tr._("Loading..."));
		DefaultListModel model=new DefaultListModel();
		JList list=new JList(model);
		dlg.add(new JScrollPane(list));
		dlg.setModal(true);
		dlg.setMinimumSize(new Dimension(700,400));
		R worker=new R(model,dlg);
		worker.execute();
		dlg.pack();
		JXMLNoteSwingUtils.centerOnParent(SwingUtilities.getWindowAncestor(this), dlg);
		if (!worker.finished()) {
			dlg.setVisible(true);
		}

		_treeModel.setRoot(_topics);
		if (_tree!=null && _tree.getRowCount()>1) {
			_tree.setSelectionRow(1);
		}
	}

	private void writeTopics() {
		writeTopics(_helpjar);
	}

	private void writeTopics(File file) {

		if (_newfile) {
			File lastpath=new File(_preferences.getString("lastpath", "."));
			JFileChooser chooser = new JFileChooser(lastpath);
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Help files", "xnhlp");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				lastpath=chooser.getCurrentDirectory();
				_preferences.put("lastpath", lastpath.getAbsolutePath());
				file = chooser.getSelectedFile();
				String fl = file.getAbsolutePath();
				if (!fl.endsWith(".xnhlp")) {
					file = new File(fl + ".xnhlp");
				}
			} else {
				return;
			}
		}

		String jarName = file.getAbsolutePath();
		try {
			FileOutputStream fout = new FileOutputStream(jarName);
			JarOutputStream jout = new JarOutputStream(fout);

			{
				ZipEntry ze = new ZipEntry("HelpTopics.xnhlp");
				jout.putNextEntry(ze);
				writeObject(jout, _topics);
			}

			{
				@SuppressWarnings("unchecked")
				Enumeration<DefaultMutableTreeNode> en = _topics
						.breadthFirstEnumeration();
				while (en.hasMoreElements()) {
					DefaultMutableTreeNode topic = en.nextElement();
					HelpTopic ht = (HelpTopic) topic.getUserObject();
					HelpEntry he = (HelpEntry) _help.get(ht.getTopicId());
					ZipEntry hze = new ZipEntry(ht.getTopicId() + ".top");
					jout.putNextEntry(hze);
					writeObject(jout, he);
				}
			}

			{
				Set<String> skeys = _images.keySet();
				Vector<String> keys = new Vector<String>();
				Iterator<String> it = skeys.iterator();
				while (it.hasNext()) {
					String id = it.next();
					XMLNoteImageIcon icn = _images.get(id);
					if (icn.type() == XMLNoteImageIcon.Type.IMAGEICON) {
						keys.add(id);
					}
				}
				ZipEntry kze = new ZipEntry("ImageIds.vec");
				jout.putNextEntry(kze);
				writeObject(jout, keys);

				it = keys.iterator();
				while (it.hasNext()) {
					XMLNoteImageIcon img = _images.get(it.next());
					HelpImage himg = new HelpImage(img.getId(),
							img.getOriginal(), img.getDescription(),
							img.getSize());
					ZipEntry ize = new ZipEntry(img.getId() + ".img");
					jout.putNextEntry(ize);
					himg.write(jout);
					//writeObject(jout, himg);
				}
			}

			jout.close();
			fout.close();

			_helpjar = new File(jarName);
			setTitle();
			_saved = true;
			_newfile = false;

		} catch (IOException e) {
			DefaultXMLNoteErrorHandler.exception(e);
		}
	}

	private void loadTopic(HelpTopic topic) {
		_imageBackingStore.clear();
		if (topic == null) {
			_document.clear();
		} else {
			HelpEntry entry = (HelpEntry) _help.get(topic.getTopicId());
			if (entry != null) {
				try {
					//String xml = XMLNoteUtils.prettyPrintXML(entry.getXmlnote());
					//System.out.println(xml);
					_document.resetFromXML(entry.getXmlnote());
				} catch (BadDocumentException e) {
					DefaultXMLNoteErrorHandler.exception(e);
				}
			} else {
				_document.clear();
			}
		}
	}

	private void saveDocument() {
		if (_selected!=null) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) _selected.getLastPathComponent();
			HelpTopic t = (HelpTopic) node.getUserObject();
			String id = t.getTopicId();
			HelpEntry he = (HelpEntry) _help.get(id);
			if (he != null) {
				try {
					he.setXmlnote(_document.toXML());
					_document.setChanged(false);
					_saved = false; // the helpfile needs saving
				} catch (BadDocumentException e1) {
					DefaultXMLNoteErrorHandler.exception(e1);
					he.setXmlnote(XMLNoteDocument.emptyXML());
				}
			}
		}
	}

	private HelpEntry getSelectedEntry() {
		if (_selected == null) {
			return null;
		} else {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) _selected
					.getLastPathComponent();
			HelpTopic t = (HelpTopic) node.getUserObject();
			String id = t.getTopicId();
			HelpEntry he = (HelpEntry) _help.get(id);
			return he;
		}
	}

	public void checkDocumentSaved() {
		if (_helpjar != null) {
			if (_document.changed()) {
				int result = JOptionPane
						.showConfirmDialog(
								this,
								_tr._("Current topic is changed, do you want to store it?"),
								_tr._("Save Topic"), JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					saveDocument();
				}
			}
		}
	}

	public void checkTopicsSaved() {
		if (_helpjar != null) {
			checkDocumentSaved();
			if (!_saved) {
				int result = JOptionPane.showConfirmDialog(this,
						_tr._("This help file has changed, store it?"),
						_tr._("Save Help File"), JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					writeTopics();
				}
			}
		}
	}

	public boolean cleanup(boolean affectHelpJar) {
		if (_helpjar != null) {
			checkTopicsSaved();
		}
		_topics = new DefaultMutableTreeNode(new HelpTopic("Help Topics",""));
		_treeModel.setRoot(_topics);
		if (affectHelpJar) {
			_helpjar = null;
		}
		_selected = null;
		_help = new Hashtable<String, HelpEntry>();
		_images = new Hashtable<String, XMLNoteImageIcon>();
		_imageBackingStore = new Hashtable<String, XMLNoteImageIcon>();
		_document.clear();
		if (_tree!=null) {
			_tree.setSelectionRow(0);
		}
		return true;
	}

	// ////////////////////////////////////////////////////////////////////////////////
	// Handle actions
	// ////////////////////////////////////////////////////////////////////////////////

	public void actionPerformed(ActionEvent e) {
		//System.out.println(e);
		String cmd = e.getActionCommand();
		if (cmd.equals("load")) {
			openHelp();
		} else if (cmd.equals("new")) {
			newHelp();
		} else if (cmd.equals("save")) {
			saveHelp();
		} else if (cmd.equals("add-topic")) {
			newTopic();
		} else if (cmd.equals("remove-topic")) {
			removeTopic();
		} else if (cmd.equals("change-topic")) {
			changeTopic();
		} else if (cmd.equals("insert-link")) {
			insertLink();
		} else if (cmd.equals("remove-link")) {
			removeLink();
		} else if (cmd.equals("insert-hyperlink")) {
			insertHyperLink();
		} else if (cmd.equals("remove-hyperlink")) {
			removeHyperLink();
		} else if (cmd.startsWith("recent:")) {
			openRecent(cmd.substring(7));
		} else if (cmd.equals("add-image")) {
			addImage();
		} else if (cmd.equals("help")) {
			testHelp();
		}
	}

	public void valueChanged(TreeSelectionEvent e) {
		TreePath path = e.getPath();
		DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) path
				.getLastPathComponent();

		if (_selected != null) {
			checkDocumentSaved();
		}

		_selected = path;
		if (dnode != _topics) {
			loadTopic((HelpTopic) dnode.getUserObject());
			_editor.setUnresponsive(false);
		} else {
			_document.clear();
			_editor.setUnresponsive(true);
		}
	}
	

	private void newHelp() {
		if (!cleanup(true)) {
			return;
		}
		_newfile = true;
		_helpjar = new File(String.format("Help_%d.xnhlp", _next++));
		setTitle();
	}

	private void saveHelp() {
		if (_helpjar != null) {
			saveDocument();
			writeTopics();
			if (_recent != null && _helpjar != null) {
				_recent.addRecentUse(_helpjar.getAbsolutePath());
			}
		} else {
			JOptionPane.showMessageDialog(this,
					_tr._("Please open a help file or create a new one first"),
					_tr._("Save Help"), JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void openRecent(String file) {
		File f = new File(file);
		if (f.canRead()) {
			if (!cleanup(true)) {
				return;
			}
			_helpjar = f;
			loadTopics();
			setTitle();
			if (_recent != null) {
				_recent.addRecentUse(f.getAbsolutePath());
			}
		} else {
			_recent.removeRecentUse(file);
		}
	}

	private void openHelp() {
		File lastpath=new File(_preferences.getString("lastpath", "."));
		JFileChooser chooser = new JFileChooser(lastpath);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Help files", "xnhlp");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			lastpath=chooser.getCurrentDirectory();
			_preferences.put("lastpath", lastpath.getAbsolutePath());
			File file = chooser.getSelectedFile();
			String fl = file.getAbsolutePath();
			if (!fl.endsWith(".xnhlp")) {
				file = new File(fl + ".xnhlp");
			}

			if (file.canRead()) {
				if (!cleanup(true)) {
					return;
				}
				_helpjar = file;
				loadTopics();
				setTitle();
				if (_recent != null) {
					_recent.addRecentUse(file.getAbsolutePath());
				}
			}
		}
	}

	
	private void newTopic() {
		if (_selected == null) {
			JOptionPane.showMessageDialog(this,
					_tr._("Select a parent topic first"), _tr._("New Topic"),
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		HelpTopic tp=TopicDlg.showDlg(getWindow(this), _tr._("Create a new Topic"));
		if (tp!=null) {
			if (!tp.getName().equals("")) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) _selected
						.getLastPathComponent();
				DefaultMutableTreeNode n = new DefaultMutableTreeNode(tp);
				_treeModel.insertNodeInto(n, node, node.getChildCount());

				HelpEntry he = new HelpEntry();
				_help.put(tp.getTopicId(), he);
				TreePath p = _selected.pathByAddingChild(n);

				_tree.scrollPathToVisible(p);
				_tree.setSelectionPath(p);

			} else {
				JOptionPane.showMessageDialog(this,
						_tr._("Topic text may not be empty"),
						_tr._("New Topic"), JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}

	private void changeTopic() {
		if (_selected == null) {
			JOptionPane.showMessageDialog(this, _tr._("Select a topic first"),
					_tr._("Change Topic"), JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) _selected
				.getLastPathComponent();
		HelpTopic topic = (HelpTopic) node.getUserObject();
		HelpTopic tp=TopicDlg.showDlg(getWindow(this), _tr._("Change Topic"),topic);
		if (tp != null) {
			if (!tp.getName().equals("")) {
				topic.setName(tp.getName());
				topic.setKey(tp.getKey());
				_treeModel.nodeChanged(node);
				_saved = false;
			} else {
				JOptionPane.showMessageDialog(this,
						_tr._("Topic text may not be empty"),
						_tr._("Change Topic"), JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private void removeTopic() {
		checkDocumentSaved();
		if (_selected == null) {
			JOptionPane.showMessageDialog(this, _tr._("Select a topic first"),
					_tr._("Remove Topic"), JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) _selected.getLastPathComponent();
		if (node.isLeaf()) {
			int result = JOptionPane.showConfirmDialog(this,
					_tr._("Do you want to remove the selected topic?"),
					_tr._("Delete Topic"), JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				_treeModel.removeNodeFromParent(node);
				// Remove all associated images in the document
				Vector<XMLNoteImageIcon> images=_document.getAllImages();
				Iterator<XMLNoteImageIcon> it=images.iterator();
				while (it.hasNext()) { _images.remove(it.next().getId()); }
				_document.clear();
			}
		} else {
			JOptionPane.showMessageDialog(this, _tr._("Remove the children of this topic first"),
					_tr._("Remove Topic"), JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void insertHyperLink() {
		if (_editor.getSelectionEnd()>_editor.getSelectionStart()) {
			String link=JOptionPane.showInputDialog(this, _tr._("Please input a hyperlink" ), _tr._("Insert Hyperlink"));
			if (link!=null && !link.trim().equals("")) {
				UUID newid=UUID.randomUUID();
				try {
					_editor.insertMark(newid.toString(),"HyperLink:"+link);
				} catch (Exception e1) {
					DefaultXMLNoteErrorHandler.exception(e1);
				}
			} else {
				JOptionPane.showMessageDialog(this, _tr._("Please insert a hyperlink"),
						_tr._("Insert Hyperlink"), JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, _tr._("You need to select text to be able to insert a hyperlink"),
					_tr._("Insert Hyperlink"), JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void removeHyperLink() {
		removeLink();
	}

	
	private void insertLink() {
		if (_editor.getSelectionEnd()>_editor.getSelectionStart()) {
			final JJDialog dlg=new JJDialog(getWindow(this),_tr._("Insert link to Topic"));
			final JTree tree = new JTree(_treeModel);
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			tree.setMinimumSize(new Dimension(350,500));
			JScrollPane pane=new JScrollPane(tree,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			JPanel pnl=new JPanel();
			pnl.setLayout(new BorderLayout());
			pnl.add(pane,BorderLayout.CENTER);
			
			JButton cancel=new JButton(_tr._("Cancel"));
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tree.clearSelection();
					dlg.setVisible(false);
				}
			});
			JButton ok=new JButton(_tr._("Ok"));
			ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dlg.setVisible(false);
				}
			});
			ok.setDefaultCapable(true);
			JPanel p=new JPanel();
			p.setLayout(new FlowLayout(FlowLayout.RIGHT));
			p.add(cancel);p.add(ok);
			this.getRootPane().setDefaultButton(ok);
			pnl.add(p,BorderLayout.SOUTH);
			
			dlg.add(pnl);
			dlg.pack();
			dlg.centerOnParent();
			dlg.setModal(true);
			dlg.setVisible(true);
			
			if (tree.getSelectionPath()!=null) {
				DefaultMutableTreeNode o=(DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
				HelpTopic tp=(HelpTopic) o.getUserObject();
				UUID newid=UUID.randomUUID();
				try {
					_editor.insertMark(newid.toString(),"TopicLink:"+tp.getTopicId());
				} catch (Exception e1) {
					DefaultXMLNoteErrorHandler.exception(e1);
				} 
			}
		} else {
			JOptionPane.showMessageDialog(this, _tr._("You need to select text to be able to insert a link to a topic"),
					_tr._("Insert link"), JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void removeLink() {
        Vector<XMLNoteMark> marks;
        if (_editor.getSelectionEnd()>_editor.getSelectionStart()) {
            try {
                marks=_editor.getMarksForSelection();
            } catch (NoSelectionException e) {
                // Unexpected
                DefaultXMLNoteErrorHandler.exception(e);
                marks=null;
            }
        } else {
            marks=_editor.getMarksForCaret();
        }

        if (marks==null || marks.size()==0) {
            JOptionPane.showMessageDialog(this, _tr._("You need to have the caret in a link or have a link selected to be able to remove it"),
					_tr._("Remove link"), JOptionPane.INFORMATION_MESSAGE);
        } else {
            int i,n;
            for(i=0,n=marks.size();i<n;i++) {
                XMLNoteMark m=marks.get(i);
                try {
                    _document.removeMark(m.id());
                } catch (MarkNoExistException e) {
                        // again: unexpected
                    DefaultXMLNoteErrorHandler.exception(e);
                }
            }
        }
	}

	private void addImage() {
		if (_selected == null) {
			return;
		}

		File lastPath=new File(_preferences.getString("imagedir","~"));
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(lastPath);
		chooser.setAccessory(new ImagePreview(chooser));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images",
				"jpg", "png", "gif");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			File dir=chooser.getCurrentDirectory();
			_preferences.put("imagedir", dir.getAbsolutePath());
			if (file.canRead()) {
				String id = UUID.randomUUID().toString();
				URL url;
				String width = JOptionPane.showInputDialog(this, _tr
						._("Please give the desired width of the image in cm"),
						_tr._("3"));
				if (width != null && !width.trim().equals("")) {
					int widthInCm = Integer.parseInt(width);
					if (widthInCm < 1) {
						widthInCm = 1;
					}
					double points = ((72.0 / 2.54) * widthInCm);
					int widthInPt = (int) Math.round(points);
					try {
						url = new URL("file://" + file.getCanonicalPath());
						XMLNoteImageIconSize sz=new XMLNoteImageIconSize(widthInPt,-1,XMLNoteImageIconSize.TYPE_PT);
						XMLNoteImageIcon icon = new XMLNoteImageIcon(url,file.getName(), sz);
						icon.setId(id);
						_images.put(id, icon);
						int offset = _editor.getCaretPosition();
						_editor.getDocument().insertImage(offset, id);
					} catch (Exception e) {
						DefaultXMLNoteErrorHandler.exception(e);
					}
				}
			}
		}
	}
	
	private void testHelp() {
		try {
			final JDialog dlg=new JDialog(_frame,_tr._("Testing Help"),true);
			
			JarFile jarfile;
			jarfile = new JarFile(_helpjar.getAbsolutePath());
			JHelpViewer viewer=new JHelpViewer(jarfile,_preferences);
			
			JPanel panel=new JPanel();
			BorderLayout layout=new BorderLayout();
			layout.setHgap(3);layout.setVgap(3);
			panel.setLayout(layout);
			
			final ActionListener closeAction=new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					_preferences.put("testHelp.w", dlg.getSize().width);
					_preferences.put("testHelp.h", dlg.getSize().height);
					_preferences.put("testHelp.x", dlg.getLocation().x);
					_preferences.put("testHelp.y", dlg.getLocation().y);
					dlg.setVisible(false);
				}
			};
			
			JMenuBar bar=new JMenuBar();
			JMenu window=JRecentlyUsedMenu.makeMenu("_Window");
			JMenuItem close=JRecentlyUsedMenu.makeMenuItem("_Close", null, "close", closeAction);
			window.add(close);
			bar.add(window);
			
			JPanel menuAndTools=new JPanel();
			menuAndTools.setLayout(new BorderLayout());
			//menuAndTools.setLayout(new BoxLayout(menuAndTools,BoxLayout.Y_AXIS));
			menuAndTools.add(bar,BorderLayout.NORTH);
			JXMLNoteToolBar tbar=viewer.toolbar();
			tbar.initToolBar();
			menuAndTools.add(tbar,BorderLayout.SOUTH);
			
			panel.add(menuAndTools,BorderLayout.NORTH);
			panel.add(viewer,BorderLayout.CENTER);
			dlg.add(panel);

			dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dlg.setPreferredSize(new Dimension(_preferences.getInt("testHelp.w", 500),_preferences.getInt("testHelp.h",400)));
			dlg.setLocation(_preferences.getInt("testHelp.x",_frame.getLocation().x+30),
					        _preferences.getInt("testHelp.y",_frame.getLocation().y+30)
					        );
			
			dlg.pack();
			dlg.setVisible(true);
		} catch (Exception e) {
			DefaultXMLNoteErrorHandler.exception(e);
		}
	}


	// ////////////////////////////////////////////////////////////////////////////////
	// Install menu on frame
	// ////////////////////////////////////////////////////////////////////////////////

	/**
	 * Instals the JHelpEditor menu on the frame.
	 * 
	 * @param frame
	 */
	public void installMenu(JFrame frame) {
		_frame = frame;
		setTitle();
		JMenuBar _bar = new JMenuBar();

		JMenu file = makeMenu("_File");
		_bar.add(file);
		file.add(makeItem("_New help file", null, "new"));
		file.add(makeItem("_Open help file", null, "load"));
		file.add(makeItem("_Save help file", null, "save"));
		_recent = new JRecentlyUsedMenu("_Recent help files", this, this);
		file.add(_recent);

		JMenu topics = makeMenu("_Topics");
		_bar.add(topics);
		topics.add(makeItem("_New topic", null, "add-topic"));
		topics.add(makeItem("_Remove topic", null, "remove-topic"));
		topics.add(makeItem("_Change topic", null, "change-topic"));
		topics.addSeparator();
		topics.add(makeItem("_Insert link", null, "insert-link"));
		topics.add(makeItem("Remove _link", null, "remove-link"));
		topics.add(makeItem("_Hyperlink", null, "insert-hyperlink"));
		topics.add(makeItem("Remove hyperlink", null, "remove-hyperlink"));
		topics.addSeparator();
		topics.add(makeItem("_Add image", null, "add-image"));

		frame.setJMenuBar(_bar);
	}

	// ////////////////////////////////////////////////////////////////////////////////
	// Preferences
	// ////////////////////////////////////////////////////////////////////////////////

	public void storePrefs() {
		XMLNotePreferences prefs = _preferences;
		prefs.put("splitbar", _splitpane.getDividerLocation());
	}

	public void applyPrefs() {
		XMLNotePreferences prefs = _preferences;
		int div = prefs.getInt("splitbar", 150);
		_splitpane.setDividerLocation(div);
	}

	// ////////////////////////////////////////////////////////////////////////////////
	// Constructor
	// ////////////////////////////////////////////////////////////////////////////////

	public JHelpEditor(ToolsProvider prov,XMLNotePreferences prefs) {
		_preferences=prefs;
		_helpjar = null;
		_tr = new DefaultXMLNoteTranslator();
		_saved = true;
		_next = 1;
		_painter = new MyIconPainter();
        _markProvider=new DefaultMarkMarkupProvider(MarkMarkupProvider.MarkupType.UNDERLINED,_cTopic);
        _markProvider.setTextColor(_cTopic);
        _hyperlinkProvider=new DefaultMarkMarkupProvider(MarkMarkupProvider.MarkupType.UNDERLINED,_cHyperlink);
        _hyperlinkProvider.setTextColor(_cHyperlink);
		createGui(prov);
		addImageDocListeners();
		applyPrefs();
		newHelp();
	}

}

class ImagePreview extends JComponent implements PropertyChangeListener {
	ImageIcon thumbnail = null;
	File file = null;
	static int _iconWidth = 140;

	public ImagePreview(JFileChooser fc) {
		setPreferredSize(new Dimension(150, 150));
		fc.addPropertyChangeListener(this);
		super.setBorder(BorderFactory.createEtchedBorder());
	}

	public void loadImage() {
		if (file == null) {
			thumbnail = null;
			return;
		}

		// Don't use createImageIcon (which is a wrapper for getResource)
		// because the image we're trying to load is probably not one
		// of this program's own resources.
		ImageIcon tmpIcon = new ImageIcon(file.getPath());
		if (tmpIcon != null) {
			if (tmpIcon.getIconWidth() > _iconWidth) {
				thumbnail = new ImageIcon(BufferedImageBuilder.getScaledInstance(tmpIcon.getImage(), _iconWidth, -1));
			} else { // no need to miniaturize
				thumbnail = tmpIcon;
			}
		}
	}

	public void propertyChange(PropertyChangeEvent e) {
		boolean update = false;
		String prop = e.getPropertyName();

		// If the directory changed, don't show an image.
		if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
			file = null;
			update = true;

			// If a file became selected, find out which one.
		} else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
			file = (File) e.getNewValue();
			update = true;
		}

		// Update the preview accordingly.
		if (update) {
			thumbnail = null;
			if (isShowing()) {
				loadImage();
				repaint();
			}
		}
	}

	protected void paintComponent(Graphics g) {
		if (thumbnail == null) {
			loadImage();
		}
		if (thumbnail != null) {
			int x = getWidth() / 2 - thumbnail.getIconWidth() / 2;
			int y = getHeight() / 2 - thumbnail.getIconHeight() / 2;

			if (y < 0) {
				y = 0;
			}

			if (x < 5) {
				x = 5;
			}
			thumbnail.paintIcon(this, g, x, y);
		}
	}
}

class JJDialog extends JDialog {
	
	private Window _parent=null;
	
	public void centerOnParent () {
		int x;
		int y;

		// Find out our parent 
		Point topLeft = _parent.getLocationOnScreen();
		Dimension parentSize = _parent.getSize();

		Dimension mySize = this.getSize();

		if (parentSize.width > mySize.width) 
			x = ((parentSize.width - mySize.width)/2) + topLeft.x;
		else 
			x = topLeft.x;

		if (parentSize.height > mySize.height) 
			y = ((parentSize.height - mySize.height)/2) + topLeft.y;
		else 
			y = topLeft.y;

		this.setLocation (x, y);
	}  
	
	public JJDialog(Window parent,String title) {
		super(parent,title);
		_parent=parent;
	}
	
}

class TopicDlg extends JJDialog implements ActionListener {
	private JTextField _topicName;
	private JTextField _topicKey;
	private JLabel     _topicId;
	private boolean	   _canceled=false;
	
	public String topicName() {
		return _topicName.getText().trim();
	}
	
	public String topicKey() {
		return _topicKey.getText().trim();
	}
	
	public void actionPerformed(ActionEvent e) {
		String c=e.getActionCommand();
		if (c.equals("cancel")) {
			_canceled=true;
			this.setVisible(false);
		} else {
			this.setVisible(false);
		}
	}
	
	public boolean canceled() {
		return _canceled;
	}
	
	public static HelpTopic showDlg(Window parent,String title) {
		return showDlg(parent,title,new HelpTopic("",""));
	}
	
	public static HelpTopic showDlg(Window parent,String title,HelpTopic tp) {
		TopicDlg dlg=new TopicDlg(parent,title,tp);
		dlg.pack();
		dlg.centerOnParent();
		dlg.setVisible(true);
		if (!dlg.canceled()) {
			HelpTopic top=new HelpTopic(dlg.topicName(),dlg.topicKey());
			top.setTopicId(tp.getTopicId());
			return top;
		} else {
			return null;
		}
		
		
	}
	
	protected TopicDlg(Window parent,String title,HelpTopic ht) {
		super(parent,title);
		super.setModal(true);
		super.setLocationRelativeTo(parent);
		
		XMLNoteTranslator _tr=new DefaultXMLNoteTranslator();
		
		JPanel dlgPane=new JPanel();
		JSizeGroup labels=new JSizeGroup(JSizeGroup.HORIZONTAL);
		BorderLayout bdr=new BorderLayout();
		dlgPane.setLayout(bdr);
		
		JLabel l;
		
		JPanel f1=new JPanel();
		f1.setLayout(new FlowLayout(FlowLayout.LEFT));
		l=new JLabel(_tr._("Topic name :"));f1.add(l);labels.add(l);
		_topicName=new JTextField(30);f1.add(_topicName);
		_topicName.setText(ht.getName());
		
		JPanel f2=new JPanel();
		f2.setLayout(new FlowLayout(FlowLayout.LEFT));
		l=new JLabel(_tr._("Topic key :"));f2.add(l);labels.add(l);
		_topicKey=new JTextField(20);f2.add(_topicKey);
		_topicKey.setText(ht.getKey());
		
		JPanel p=new JPanel();
		BoxLayout bl=new BoxLayout(p,BoxLayout.PAGE_AXIS);
		p.setLayout(bl);
		p.add(f1,BorderLayout.CENTER);
		p.add(f2,BorderLayout.CENTER);
		
		dlgPane.add(p,BorderLayout.CENTER);
		
		labels.align();
		
		JButton cancel=new JButton(_tr._("cancel"));cancel.addActionListener(this);
		JButton ok=new JButton(_tr._("Ok"));ok.addActionListener(this);ok.setDefaultCapable(true);
		this.getRootPane().setDefaultButton(ok);
		JPanel buttons=new JPanel();
		FlowLayout fll=new FlowLayout(FlowLayout.RIGHT);
		buttons.setLayout(fll);
		buttons.add(cancel);
		buttons.add(ok);
		
		dlgPane.add(buttons,BorderLayout.SOUTH);
		this.add(dlgPane);
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
}
