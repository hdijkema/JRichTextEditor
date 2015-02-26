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

import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * Creates a new jvm process given the provided classname and using the current JVM settings.
 * I.e. the classname must exist in the current classpath
 * 
 * @author Hans Dijkema
 *
 */
public class JavaTask {
	
	public static final int NO_EXIT_VALUE=-1000000000;
	
	private String 	_className;
	private File	_jvm;
	private File	_startDir;
	private String 	_classpath;
	private Process _task;
	
	public void start(Vector<String> arguments) throws IOException {
		Vector<String> myargs=new Vector<String>();
		myargs.add(_jvm.getAbsolutePath());
		myargs.add("-cp");
		myargs.add(_classpath);
		myargs.add(_className);
		myargs.addAll(arguments);
		String[] args=new String[myargs.size()];
		myargs.toArray(args);
		_task=Runtime.getRuntime().exec(args,null,_startDir);
	}
	
	public boolean stillRunning() {
		if (_task!=null) {
			try {
				_task.exitValue();
				return false;
			} catch (IllegalThreadStateException e) {
				return true;
			} 
		} else {
			return false;
		}
	}
	
	public int exitValue() {
		if (stillRunning()) {
			return NO_EXIT_VALUE;
		} else {
			return _task.exitValue();
		}
	}
	
	public JavaTask(String fullyQualifiedClassName,File startDir) throws RuntimeException {
		_className=fullyQualifiedClassName;
		_classpath=System.getProperty("java.class.path");
		_startDir=startDir;
		String javaHome=System.getProperty("java.home");
		File jvm=new File(javaHome,"bin/javaw.exe");
		if (jvm.canExecute()) {
			_jvm=jvm;
		} else {
			jvm=new File(javaHome,"bin/java.exe");
			if (jvm.canExecute()) {
				_jvm=jvm;
			} else {
				jvm=new File(javaHome,"bin/java");
				if (jvm.canExecute()) {
					_jvm=jvm;
				} else {
					throw new RuntimeException("Cannot find JVM");
				}
			}
		}
	}

}
