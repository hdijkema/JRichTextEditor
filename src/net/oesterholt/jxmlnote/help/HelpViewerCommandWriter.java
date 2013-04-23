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

package net.oesterholt.jxmlnote.help;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.UIManager;

import net.oesterholt.jxmlnote.exceptions.DefaultXMLNoteErrorHandler;
import net.oesterholt.jxmlnote.utils.JavaTask;


public class HelpViewerCommandWriter {
	
	class Command implements Comparable<Command> {
		private  String command;
		private  String parameter;
		private  int 	priority;
		
		public String toString() {
			return "Command:"+command+"("+parameter+")";
		}
		
		public void toFile(File f) throws IOException {
			File lck=new File(f.getAbsolutePath()+".lck");
			FileOutputStream fout=new FileOutputStream(lck);
			fout.close();
			BufferedWriter writer=new BufferedWriter(new FileWriter(f));
			writer.write(command+"\n");
			writer.write(parameter+"\n");
			writer.close();
			lck.delete();
		}

		public int compareTo(Command o) {
			if (priority<o.priority) { return -1; }
			else if (priority==o.priority) { return 0; }
			else { return 1;}
		}
		
		public String getCommand() {
			return command;
		}
		
		public Command(String c,String p) {
			command=c;
			parameter=p;
			priority=100;
		}
		
		public Command(int prio,String c,String p) {
			command=c;
			parameter=p;
			priority=prio;
		}

	}
	
	class HelpCommander implements Runnable {
		public void run() {
			boolean stop=false;
			do {
				Command cmd=poll();
				if (cmd!=null) {
					try {
						communicate(cmd);
					} catch (IOException e) {
						DefaultXMLNoteErrorHandler.exception(e);
					}
				} else {
					// does nothing
				}
			} while (!stop);
		}
	}
	
	private PriorityBlockingQueue<Command>  	_commandQueue; 
	private File 								_communicationFile;
	private JavaTask							_helpTask;
	private Thread								_commander;
	private Class<?>							_viewerClassMain;
	private File								_helpFile;
	private File								_startDir;
	private String								_locale;

	private void offer(Command c) {
		_commandQueue.offer(c);
	}
	
	private Command poll() {
		try {
			return _commandQueue.poll(1000,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			return null;
		}
	}
	
	private void clear() {
		_commandQueue.clear();
	}
	
	private void communicate(Command cmd) throws IOException {
		cmd.toFile(_communicationFile);
		while (_communicationFile.exists()) {
			if (!cmd.getCommand().equals("quithelp")) { 
				if (!helpRuns()) { openHelp(cmd); }
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// does nothing
			}
		}
	}
	
	private void openHelp(Command cmd) throws IOException {
		JavaTask task=new JavaTask(_viewerClassMain.getName(),_startDir);
		Vector<String> args=new Vector<String>();
		args.add(_communicationFile.getAbsolutePath());
		String laf="<system>";
		if (UIManager.getLookAndFeel()!=null) {
			laf=UIManager.getLookAndFeel().getClass().getName();
		}
		args.add(laf);
		args.add(_locale);
		task.start(args);
		_helpTask=task;
		if (_helpFile!=null) {
			Command hfile=new Command("helpfile",_helpFile.getAbsolutePath());
			communicate(hfile);
		}
		communicate(cmd);
	}

	private boolean helpRuns() {
		if (_helpTask!=null) {
			return _helpTask.stillRunning();
		} else {
			return false;
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////

	public void quitHelp() {
		if (helpRuns()) {
			offer(new Command("quithelp",""));
		}
	}
	
	public void setHelpTopic(String target) {
		offer(new Command("helptopic",target));
	}
	
	public void setHelpFile(File helpFile) {
		_helpFile=helpFile;
		offer(new Command("helpfile",helpFile.getAbsolutePath()));
	}
	
	public void setHelpTitle(String title) {
		offer(new Command("helptitle",title));
	}
	
	public void customCommand(String info) {
		offer(new Command("custom",info));
	}
	
	public HelpViewerCommandWriter(Class<?> viewerClassMain,File communicationFile,File startDir, String locale) {
		_viewerClassMain=viewerClassMain;
		_commandQueue=new PriorityBlockingQueue<Command>(10000);
		_communicationFile=communicationFile;
		_startDir=startDir;
		_locale=locale;
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				quitHelp();
			}
		}));
		_commander=new Thread(new HelpCommander());
		_commander.start();
	}
}
