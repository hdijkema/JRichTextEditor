/* ******************************************************************************
 *
 *       Copyright 2008-2010 Hans Dijkema
  *
 *   JRichTextEditor is free software: you can redistribute it and/or modify
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
 *   along with JRichTextEditor.  If not, see <http://www.gnu.org/licenses/>.
 *   
 * ******************************************************************************/

package nl.dykema.demo.jxmlnote;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.xml.parsers.ParserConfigurationException;

import nl.dykema.JXMLNoteEditor;
import nl.dykema.jxmlnote.document.DocumentAdminEvent;
import nl.dykema.jxmlnote.document.DocumentAdminListener;
import nl.dykema.jxmlnote.document.DocumentPreEvent;
import nl.dykema.jxmlnote.document.DocumentPreListener;
import nl.dykema.jxmlnote.document.XMLNoteDocument;
import nl.dykema.jxmlnote.document.XMLNoteImageIconSize;
import nl.dykema.jxmlnote.document.XMLNoteMark;
import nl.dykema.jxmlnote.document.XMLNoteMarkIdProvider;
import nl.dykema.jxmlnote.document.XMLNoteMarkListener;
import nl.dykema.jxmlnote.document.XMLNoteUndoable;
import nl.dykema.jxmlnote.exceptions.BadDocumentException;
import nl.dykema.jxmlnote.exceptions.BadMetaException;
import nl.dykema.jxmlnote.exceptions.BadOperationException;
import nl.dykema.jxmlnote.exceptions.BadStyleException;
import nl.dykema.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import nl.dykema.jxmlnote.exceptions.MarkExistsException;
import nl.dykema.jxmlnote.exceptions.MarkNoExistException;
import nl.dykema.jxmlnote.exceptions.NoSelectionException;
import nl.dykema.jxmlnote.html.HtmlToXHtml;
import nl.dykema.jxmlnote.html.XHtmlToXMLNote;
import nl.dykema.jxmlnote.html.XMLNoteToHtml;
import nl.dykema.jxmlnote.interfaces.MarkMarkupProvider;
import nl.dykema.jxmlnote.interfaces.MarkMarkupProviderMaker;
import nl.dykema.jxmlnote.interfaces.XMLNotePreferences;
import nl.dykema.jxmlnote.interfaces.MarkMarkupProvider.MarkupType;
import nl.dykema.jxmlnote.internationalization.DefaultXMLNoteTranslator;
import nl.dykema.jxmlnote.internationalization.XMLNoteTranslator;
import nl.dykema.jxmlnote.report.ReportException;
import nl.dykema.jxmlnote.report.ReportProgressBar;
import nl.dykema.jxmlnote.report.XMLNoteToReport;
import nl.dykema.jxmlnote.report.ReportProgressBar.Progress;
import nl.dykema.jxmlnote.report.XMLNoteToReport.Moment;
import nl.dykema.jxmlnote.report.pdf.PdfReport;
import nl.dykema.jxmlnote.report.viewers.PdfViewer;
import nl.dykema.jxmlnote.styles.XMLNoteStyles;
import nl.dykema.jxmlnote.toolbar.JXMLNoteToolBar;
import nl.dykema.jxmlnote.utils.DPIAdjuster;
import nl.dykema.jxmlnote.widgets.JXMLNotePane;
import nl.dykema.jxmlnote.widgets.JXMLNoteStylePane;
import nl.dykema.jxmlnote.widgets.marks.DefaultMarkMarkupProvider;
import nl.dykema.jxmlnote.widgets.marks.MarkMouseListener;
import nl.dykema.jxmlnote.xml.XMLNoteUtils;

import org.xml.sax.SAXException;

public class JXMLNoteDemo {

	private static String readFileAsString(String filePath)
			throws java.io.IOException {
		byte[] buffer = new byte[(int) new File(filePath).length()];
		BufferedInputStream f = new BufferedInputStream(new FileInputStream(
				filePath));
		f.read(buffer);
		f.close();
		return new String(buffer, "UTF-8");
	}

	public static void main(String[] args) {

		try {
			//System.setProperty("awt.useSystemAAFontSettings", "lcd_vbgr");
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
			
		} catch (Exception e) {
			System.out.println("Error setting native LAF: " + e);
		}
		
		class EditRun implements Runnable, ActionListener {

			private Integer 					_id = 0;
			private JXMLNotePane 				_notes;
			private XMLNoteDocument 			_document;
			private MarkerProvider			 	_provider;
			private MarkerProvider				_provider2;
			private XMLNoteTranslator 			_translator;
			private JFrame			  			_frame;
			private JMenuBar					_menu;
			private JXMLNoteEditor				_editor;
			private JXMLNoteEditor				_viewer;
			private Preferences					_preferences=Preferences.userNodeForPackage(JXMLNoteDemo.class);
			private File						_saveFile=new File("/tmp/xmlnote.jxmlnote");
			private boolean						_canceled;
			
			
			private float convertToPixels(int pt) {
				return (float) DPIAdjuster.adjustPointSize((double) pt);
			}

			public void createPdf() {
				final PdfReport pdf;
				final XMLNoteToReport report;
				try {

					pdf = new PdfReport(new File("/tmp/fontcache.dat"));
					final File reportFile=new File("/tmp/pdf_output.pdf");
					pdf.beginReport(reportFile);
					
					report=new XMLNoteToReport(pdf);
					_canceled=false;
					ReportProgressBar bar=ReportProgressBar.runJob(pdf, _editor, "Printing", new ReportProgressBar.Job() {
						public void job(Progress p) {
							try {
								p.progress(0);
								p.statusMessage("Creating "+reportFile);
								pdf.addXMLNote(_editor.getDocument(),new MarkMarkupProviderMaker() {
									public MarkMarkupProvider create(String markId,String markClass) {
										return _provider2;
									}
								}, new XMLNoteToReport.MarkTextProvider() {

									public String provideText(
											XMLNoteMark mark,
											XMLNoteToReport.Moment moment) {
										if (moment.equals(Moment.BEFORE)) {
											return "(BEFORE)";
										} else {
											return "(AFTER)";
										}
									}
									
								});
								p.progress(100);
								pdf.endReport();
							} catch (ReportException e) {
								DefaultXMLNoteErrorHandler.exception(e);
							}
						}

						public void cancelled() {
							try {
								pdf.cancel();
								_canceled=true;
							} catch (ReportException e) {
								DefaultXMLNoteErrorHandler.exception(e);
							}
						}
					}, new XMLNotePreferences() {
						public String getString(String key, String _default) { return _preferences.get(key, _default); }
						public int getInt(String key, int _default) { return _preferences.getInt(key,_default); }
						public void put(String key, String value) { _preferences.put(key, value); }
						public void put(String key, Integer value) { _preferences.putInt(key, value); }
					});
				} catch (ReportException e) {
					e.printStackTrace();
				}

			}
			
			
			protected java.awt.Rectangle getVisibleEditorRect(JTextPane ta) {
				java.awt.Rectangle alloc = ta.getBounds();
				if ((alloc.width > 0) && (alloc.height > 0)) {
					alloc.x = alloc.y = 0;
					Insets insets = ta.getInsets();
					alloc.x += insets.left;
					alloc.y += insets.top;
					alloc.width -= insets.left + insets.right;
					alloc.height -= insets.top + insets.bottom;
					return alloc;
				}
				return null;
			}


			
			private void doPrint() {
				createPdf();
				if (_canceled) { return; }
				try {
					PdfViewer.showPdfViewer(_frame, "View resulting pdf", new File("/tmp/pdf_output.pdf"),
											new XMLNotePreferences() {
												public String getString(String key,String _default) { 
													return _preferences.get(key,_default); 
												}
												public int getInt(String key,int _default) {
													return _preferences.getInt(key,_default); 
												}
												public void put(String key,String value) {
													_preferences.put(key,value);
												}
												public void put(String key,Integer value) {
													_preferences.putInt(key,value);
												}
					});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			class MarkerProvider extends DefaultMarkMarkupProvider {
				
				private Color _c1,_c2;
				private MarkupType _type;
				
				public Color markColor(XMLNoteMark m) {
					int id=Integer.parseInt(m.id());
					if ((id%2)==0) {
						return _c1;
					} else {
						return _c2;
					}
				}
				
				public void setColors(Color c1,Color c2) {
					_c1=c1;_c2=c2;
					super.fireChangedEvent();
				}
				
				public Color getColor1() {
					return _c1;
				}
				
				public Color getColor2() {
					return _c2;
				}
				
				public MarkupType type() {
					return _type;
				}
				
				public void setType(MarkupType t) {
					_type=t;
					super.setType(t);
				}
				
				public MarkerProvider(MarkupType t,Color c1,Color c2) {
					super(t,c1);
					_c1=c1;_c2=c2;_type=t;
				}
			}
			
			public void actionPerformed(ActionEvent e) {
				String cmd = e.getActionCommand();
				System.out.println("command: " + cmd);
				if (cmd.equals("prefs")) {
					JDialog dlg=new JDialog(_frame,"Style preferences",true);
					dlg.setLocationRelativeTo(_frame);
					JXMLNoteStylePane pane=new JXMLNoteStylePane(_document.getStyles());
					dlg.add(pane);
					dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dlg.pack();
					dlg.setVisible(true);
				} else if (cmd.equals("mark")) {
					_id += 1;
					boolean le=_notes.getXMLNoteDoc().setLongEdit(true);
					XMLNoteUndoable u=new XMLNoteUndoable() {
						public boolean operation() {
							System.out.println("HI THERE, DOING 'operation'");
							return true;
						}
						public void inverseOperation() {
							System.out.println("HI THERE, UNDOING 'operation'");
						}
					};
					_notes.getXMLNoteDoc().addUndoable(u);
					
					try {
						_notes.insertMark(_id.toString(),null);
					} catch (NoSelectionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (MarkExistsException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					_notes.getXMLNoteDoc().setLongEdit(le);
				} else if (cmd.equals("unmark")) {
					Vector<XMLNoteMark> marks = _notes.getMarksForCaret();
					Iterator<XMLNoteMark> it = marks.iterator();
					while (it.hasNext()) {
						try {
							_notes.removeMark(it.next().id());
						} catch (MarkNoExistException e1) {
							e1.printStackTrace();
						}
					}
				} else if (cmd.equals("lighters")) {
					if (_provider.getColor1().equals(Color.yellow)) {
						_provider.setColors(Color.gray,Color.gray);
					} else {
						_provider.setColors(Color.yellow,Color.green);
					}
					if (_provider2.type()==MarkupType.UNDERLINED) {
						_provider2.setType(MarkupType.MARKER); 
						_provider2.setColors(_provider2.getColor2(), _provider2.getColor1());
					} else {
						_provider2.setType(MarkupType.UNDERLINED);
					}
				} else if (cmd.equals("save")) {
					String xml;
					try {
						_document.setMeta("last_id", _id);
						_document.setMeta("save_time",new Date());
						xml = _document.toXML();
					} catch (BadDocumentException e1) {
						e1.printStackTrace();
						xml = null;
					}
					System.out.println(XMLNoteUtils.prettyPrintXML(xml));
					try {
						OutputStream sout = new FileOutputStream(_saveFile);
						sout.write(xml.getBytes("UTF-8"));
						sout.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (UnsupportedEncodingException e3) {
						e3.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					xml = _document.getStyles().toXML();
					System.out.println(XMLNoteUtils.prettyPrintXML(xml));
				} else if (cmd.startsWith("loadurl")) {
					try {
						String U;
						U="http://www.homeoint.org/books/boericmm/n/nat-ar.htm";
						//U="http://www.dijkema.net";  //TODO: Wat te doen met dit soort stuff?
						//U="http://www.ns.nl";  // TODO: Check wat er gebeurt hier!
						//U="http://nl.wikipedia.org/wiki/Rich_Text_Format";
						//U="http://java.sun.com/j2se/1.4.2/docs/api/java/io/InputStreamReader.html";
						//U="http://nos.nl/artikel/174291-burgemeester-duisburg-uitgejouwd.html";
						//U="http://i-to-i.irexnet.com/";
						//U="http://i-to-i.irexnet.com/2010/06/10/2-0-rc2-software-available-for-dr800sg-and-dr1000s/";
						//U="http://debs.oolite.org/";
						//U="http://www.oolite.org/about";
						URL url=new URL(U);
						String xhtml=HtmlToXHtml.fromHtml(url);
						System.out.println(xhtml);
						XMLNoteDocument d=XHtmlToXMLNote.convert(xhtml,null,new XMLNoteStyles());
						d.trim();
						if (cmd.equals("loadurl1") || cmd.equals("loadurl3")) {
							d.setMeta("startsWithParagraph", false);
						}
						if (cmd.equals("loadurl2") || cmd.equals("loadurl3")) {
							d.setMeta("endsWithParagraph",false);
						}
						if (!cmd.equals("loadurl4")) {
							_document.copyInto(d, _editor.getCaretPosition() , true);	
						} else {
							_document.resetFromDocument(d);
						}
						
						System.out.println(d.toXML());
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (BadDocumentException e3) {
						e3.printStackTrace();
					} catch (ParserConfigurationException e2) {
						e2.printStackTrace();
					} catch (SAXException e2) {
						e2.printStackTrace();
					} catch (BadStyleException e2) {
						e2.printStackTrace();
					} catch (MarkExistsException e2) {
						e2.printStackTrace();
					}
				} else if (cmd.equals("take_part")) {
					try {
						XMLNoteDocument part=_document.getPart(_editor.getSelectionStart(),_editor.getSelectionEnd(),XMLNoteDocument.PARTIAL);
						if (part==null) {
							System.out.println("Part = null");
						} else {
							System.out.println("Part:");
							System.out.println(XMLNoteUtils.prettyPrintXML(part.toXML()));
						}
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					} catch (BadDocumentException e1) {
						e1.printStackTrace();
					}
				} else if (cmd.equals("part_to_html")) {
					try {
						XMLNoteDocument part=_document.getPart(_editor.getSelectionStart(),_editor.getSelectionEnd(),XMLNoteDocument.PARTIAL);
						if (part==null) {
							System.out.println("Part = null");
						} else {
							String xhtml=XMLNoteToHtml.toString(part, _editor.getMarkMarkupProviderMaker());
							System.out.println(XMLNoteUtils.prettyPrintXML(xhtml));
							System.out.println(xhtml);
							OutputStream sout = new FileOutputStream("/tmp/xmlnote.html");
							sout.write(xhtml.getBytes("UTF-8"));
							sout.close();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (cmd.equals("to_html")) {
					try {
						String xhtml=XMLNoteToHtml.toString(_document, _viewer.getMarkMarkupProviderMaker());
						System.out.println(XMLNoteUtils.prettyPrintXML(xhtml));
						System.out.println(xhtml);
						OutputStream sout = new FileOutputStream("/tmp/xmlnote.html");
						sout.write(xhtml.getBytes("UTF-8"));
						sout.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (cmd.equals("load")) {
					String xml=XMLNoteDocument.emptyXML();
					try {
						File lastpath=new File(_preferences.get("lastpath", "."));
						JFileChooser chooser = new JFileChooser(lastpath);
						FileNameExtensionFilter filter = new FileNameExtensionFilter("XMLNote files", "jxmlnote");
						chooser.setFileFilter(filter);
						int returnVal = chooser.showOpenDialog(_frame);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							lastpath=chooser.getCurrentDirectory();
							_preferences.put("lastpath", lastpath.getAbsolutePath());
							File file = chooser.getSelectedFile();
							String fl = file.getAbsolutePath();
							if (!fl.endsWith(".jxmlnote")) {
								file = new File(fl + ".jxmlnote");
							}
							_saveFile=file;
							xml = readFileAsString(file.getAbsolutePath());
						}
						_document.resetFromXML(xml);
						_id=_document.getIntMeta("last_id");
						System.out.println("last id: "+_id);
						System.out.println("last save date: "+_document.getDateMeta("save_time"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BadDocumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BadMetaException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (cmd.equals("new")) {
					_document.clear();
				} else if (cmd.equals("undo")) {
					_document.getUndoManager().undo();
				} else if (cmd.equals("trim")) {
					_document.trim();
				} else if (cmd.equals("print")) {
					doPrint();
				} else {
					System.out.println(cmd);
					if (cmd.equals("undo")) {
						_document.getUndoManager().undo();
					}
				}
			}

			public void run() {
				GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
				GraphicsDevice gd=ge.getDefaultScreenDevice();
				GraphicsConfiguration gc=gd.getDefaultConfiguration();
				AffineTransform at=gc.getDefaultTransform();
				System.out.println(at);
				at.setToScale(0.4, 0.4);
				System.out.println(at);
				System.out.println(gc.getDefaultTransform());
				System.out.println(gc.getClass());
				//sun.awt.X11GraphicsConfig gcx=(sun.awt.X11GraphicsConfig) gc;
				at=gc.getNormalizingTransform();
				System.out.println(at);
				//AffineTransform at=gc.getDefaultTransform();
				//at.setToScale(2.0, 2.0);
				//AffineTransform at=gc.getNormalizingTransform();
				//at.setToScale(2.0, 2.0);
				
				_frame = new JFrame();
				_translator = new DefaultXMLNoteTranslator();
				try {
					_document = new XMLNoteDocument();
				} catch (BadStyleException e3) {
					e3.printStackTrace();
				}
				_provider  = new MarkerProvider(MarkupType.MARKER,Color.yellow,Color.green);
				_provider2 = new MarkerProvider(MarkupType.UNDERLINED,Color.blue,Color.cyan);
				_document.setMarkIdReassigner(new XMLNoteMarkIdProvider() {
					public String getNewId(XMLNoteMark mark) {
						if (_document.markExists(mark.id())) {
							_id+=1;
							return _id.toString();
						} else {
							return null;
						}
					}
				});

				_viewer = new JXMLNoteEditor(_document,
															new MarkMarkupProviderMaker() {

																public MarkMarkupProvider create(
																		String markId,
																		String markClass) {
																	return _provider2;
																}		
					
															});
				
				
				_editor = new JXMLNoteEditor(_document,
											new MarkMarkupProviderMaker() {
													public MarkMarkupProvider create(String markId,String markClass) {
																	return _provider;
																}
													}
											);
				// JXMLNotePane _pane=new JXMLNotePane(_document);
				JXMLNoteToolBar _bar = _editor.toolbar();
				_bar.setButtonSize(24);
				_notes = _editor.pane();
				JPanel _panel = new JPanel();
				JPanel _editorPanel=new JPanel();
				_editorPanel.setLayout(new BoxLayout(_editorPanel, BoxLayout.PAGE_AXIS));
				_panel.setLayout(new BorderLayout());
				
				try {
					_document.insertString(0, "één extra graag", null);
				} catch (BadLocationException e2) {
					e2.printStackTrace();
				}

				_document.addDocumentPreListener(new DocumentPreListener() {

					public boolean changeUpdate(DocumentEvent e) {
						System.out.println("pre change");
						return false;
					}

					public boolean insertUpdate(DocumentEvent e) {
						System.out.println("pre insert");
						DocumentPreEvent ee = (DocumentPreEvent) e;
						try {
							System.out.println("insert=" + ee.getString());
						} catch (BadOperationException e1) {
							e1.printStackTrace();
						}
						return false;
					}

					public boolean removeUpdate(DocumentEvent e) {
						System.out.println("pre remove");
						XMLNoteDocument d = (XMLNoteDocument) e.getDocument();
						if (d.isMarkInRange(e.getOffset(), e.getLength())) {
							if (JOptionPane.showConfirmDialog(_frame,
															  "This selection contains marks, remove?","Remove marks",
															  JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
								return false;
							} else {
								return true; // VETO
							}
						}
						return false;
					}

				});

				_document.addDocumentAdminListener(new DocumentAdminListener() {

					public boolean veto(String msg, String title) {
						System.out.println("pre clear/reset");
						if (JOptionPane.showConfirmDialog(_frame, msg, title,
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							return false;
						}
						return true;
					}

					public boolean documentWillBeReset(DocumentAdminEvent e) {
						return veto("Sure to load new XMLNote document?",
								"Loading document");
					}

					public void documentHasBeenReset(DocumentAdminEvent e) {
						System.out.println("admin: reset");
					}

					public boolean documentWillBeCleared(DocumentAdminEvent e) {
						return veto("Sure to clear the document?",
								"Clearing document");
					}

					public void documentHasBeenCleared(DocumentAdminEvent e) {
						System.out.println("admin: clear");
					}
					
					public void documentChangedState(DocumentAdminEvent e) {
						System.out.println("document changed event");
					}
				});

				_document.addDocumentPostListener(new DocumentListener() {

					public void changedUpdate(DocumentEvent arg0) {
						System.out.println("post change");
					}

					public void insertUpdate(DocumentEvent arg0) {
						System.out.println("post insert");
					}

					public void removeUpdate(DocumentEvent arg0) {
						System.out.println("post remove");
					}

				});

				_document.addMarkListener(new XMLNoteMarkListener() {
					
					public ChangedMessageWay markAdminBegin() {
						return ChangedMessageWay.FOR_CHANGED;
					}
					
					public void markAdminEnd() {
					}
					
					public boolean markInsert(String id, int offset, int length) {
						System.out.println(String.format("markInsert(%s,%d)",
								id, offset));
						return false;
					}

					public boolean markRemove(String id) {
						System.out.println(String.format("markRemove(%s)", id));
						return false;
					}

					public void markOffsetsChanged(XMLNoteMark m,
							Vector<XMLNoteMark> orderedMarks,
							int indexInOrderedMarks) {
						System.out.println(String.format(
								"markOffsetChanged(%s,%d) - %d", m.id(),
								m.offset(), indexInOrderedMarks));
					}

					public boolean markRemoved(XMLNoteMark m,
							Vector<XMLNoteMark> orderedMarks) {
						System.out.println(String.format("markRemoved(%s)",
								m.id()));
						return false;
					}

					public boolean markInserted(XMLNoteMark m,
							Vector<XMLNoteMark> orderedMarks,
							int indexInOrderedMarks) {
						System.out.println(String.format("markInserted(%s,%d)",
								m.id(), m.offset()));
						return false;
					}

					public void iteratorOperation(XMLNoteMark m, Object info) {
					}

					public void markIdChanged(XMLNoteMark m, String previousId) {
					}

				});
				
				_viewer.pane().addMarkMouseListener(new MarkMouseListener() {

					public void markClicked(XMLNoteMark m,MouseEvent e) {
						e.consume();
						System.out.println("Clicked!id="+m.id());
					}

					public void markDoubleClicked(XMLNoteMark m,MouseEvent e) {
						e.consume();
						System.out.println("Double Clicked! id="+m.id());
					}

					public Cursor mouseMovedIntoMark(XMLNoteMark m,MouseEvent e) {
						System.out.println("Mouse in id="+m.id());
						return Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
					}

					public void mouseMovedOutOfMark(XMLNoteMark m,MouseEvent e) {
						System.out.println("Mouse out id="+m.id());
					}
					
				});

				_bar.insertSection("io", "&File");
				_bar.add("io", "new", _translator
						.translate("Clear the document and create a new one"),
						this);
				_bar.add("io", "save", "Save this document", this);
				_bar.add("io", "load", "Load the document from file", this);

				_bar.insertSection("mark", "_Mark");
				{
					URL url=this.getClass().getResource("/net/dijkema/demo/jxmlnote/resources/mark.png");
					ImageIcon imark = new ImageIcon(url);
					_bar.add("mark", "mark", "mark", this, imark);
				}
				{
					URL url=this.getClass().getResource("/net/dijkema/demo/jxmlnote/resources/unmark.png");
					ImageIcon iumark = new ImageIcon(url);
					_bar.add("mark", "unmark", "unmark", this, iumark);
				}
				{
					URL url=this.getClass().getResource("/net/dijkema/demo/jxmlnote/resources/style.png");
					ImageIcon lighters = new ImageIcon(url);
					_bar.add("mark", "lighters", "lighters", this, lighters);
				}
				{
					URL url=this.getClass().getResource("/net/dijkema/demo/jxmlnote/resources/prefs.png");
					ImageIcon prefs = new ImageIcon(url);
					_bar.add("mark", "prefs", "Style preferences", this, prefs);
				}
				
				_bar.initToolBar();

				Toolkit kit=Toolkit.getDefaultToolkit();
				System.out.println("Screen resolution:"+kit.getScreenResolution());
				System.out.println("Screen size:"+kit.getScreenSize());

				try {
					_document.insertString(_document.getLength(), "\n", null);
					XMLNoteImageIconSize size=new XMLNoteImageIconSize(3*72,-1,XMLNoteImageIconSize.TYPE_PT);
					URL image=this.getClass().getResource("/net/dijkema/demo/jxmlnote/resources/image.jpg");
					_document.insertImage(_document.getLength(),image,"cover",size);
					_document.applyAlign(StyleConstants.ALIGN_CENTER, _document.getLength(),1);
					_document.insertString(_document.getLength(), "\n", null);
				} catch (Exception E) {
					E.printStackTrace();
				}
				
				_menu=new JMenuBar();
				JMenu menu=new JMenu("Test");
				
				menu.add(new JMenuItem(new AbstractAction("Insert URL at caret (paragraph)") {
					public void actionPerformed(ActionEvent e) {
						EditRun.this.actionPerformed(new ActionEvent(e.getSource(),e.getID(),"loadurl"));						
					}
				}));
				menu.add(new JMenuItem(new AbstractAction("Insert URL at caret (!startsWithParagraph)") {
					public void actionPerformed(ActionEvent e) {
						EditRun.this.actionPerformed(new ActionEvent(e.getSource(),e.getID(),"loadurl1"));						
					}
				}));
				menu.add(new JMenuItem(new AbstractAction("Insert URL at caret (!endsWithParagraph)") {
					public void actionPerformed(ActionEvent e) {
						EditRun.this.actionPerformed(new ActionEvent(e.getSource(),e.getID(),"loadurl2"));						
					}
				}));
				menu.add(new JMenuItem(new AbstractAction("Insert URL at caret (!start & !end)") {
					public void actionPerformed(ActionEvent e) {
						EditRun.this.actionPerformed(new ActionEvent(e.getSource(),e.getID(),"loadurl3"));						
					}
				}));
				menu.add(new JMenuItem(new AbstractAction("Load URL as new Document") {
					public void actionPerformed(ActionEvent e) {
						EditRun.this.actionPerformed(new ActionEvent(e.getSource(),e.getID(),"loadurl4"));						
					}
				}));
				menu.add(new JMenuItem(new AbstractAction("Get part of the document") {
					public void actionPerformed(ActionEvent e) {
						EditRun.this.actionPerformed(new ActionEvent(e.getSource(),e.getID(),"take_part"));						
					}
				}));
				menu.add(new JMenuItem(new AbstractAction("To HTML") {
					public void actionPerformed(ActionEvent e) {
						EditRun.this.actionPerformed(new ActionEvent(e.getSource(),e.getID(),"to_html"));						
					}
				}));
				menu.add(new JMenuItem(new AbstractAction("Part to HTML") {
					public void actionPerformed(ActionEvent e) {
						EditRun.this.actionPerformed(new ActionEvent(e.getSource(),e.getID(),"part_to_html"));						
					}
				}));
				menu.add(new JMenuItem(new AbstractAction("Trim document") {
					public void actionPerformed(ActionEvent e) {
						EditRun.this.actionPerformed(new ActionEvent(e.getSource(),e.getID(),"trim"));						
					}
				}));
				menu.add(new JMenuItem(new AbstractAction("Print document") {
					public void actionPerformed(ActionEvent e) {
						EditRun.this.actionPerformed(new ActionEvent(e.getSource(),e.getID(),"print"));						
					}
				}));

				menu.addActionListener(this);
				_menu.add(menu);

				_frame.setJMenuBar(_menu);
				_panel.add(_bar,BorderLayout.NORTH);
				// _editor.setPreferredSize(new Dimension(700,700));
				_editorPanel.setPreferredSize(new Dimension(550, 600));
				_editorPanel.add(_editor);
				_editorPanel.add(Box.createRigidArea(new Dimension(0,10)));
				_editorPanel.add(_viewer);
				_panel.add(_editorPanel);
				// _panel.add(_pane);
				_frame.add(_panel);
				_frame.pack();
				_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				_frame.setVisible(true);
			}
		}

		SwingUtilities.invokeLater(new EditRun());

	}

}
