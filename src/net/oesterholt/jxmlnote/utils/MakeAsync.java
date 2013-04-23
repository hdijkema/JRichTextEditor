package net.oesterholt.jxmlnote.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingUtilities;

/**
 * This class executes a runnable, but only if it is the last one
 * of a series. invokeLater() is called with a Runnable R. 
 * This Runnable is wrapped in a wrapper Runnable, which monitors
 * the size of the series. This is executed on the AWT Event Queue/Thread.
 * If the series is of size 1, it will run its runnable otherwise, it
 * wil dispose of itself and doesn't run runnable. 
 * 
 * This class can be used to make events execute asynchonously, where
 * it is only important that the last one of a series of events is
 * eventually executed.
 * 
 * NB. This can result in a starvation situation, where there runnables
 * keep being put on the AWT Queue, and the number of runnables queued
 * never reaches 1. 
 * 
 * @author Hans Oesterholt-Dijkema
 *
 */
public class MakeAsync {
	
	private class SeriesRunnable implements Runnable {
		private Runnable R;
		
		public SeriesRunnable(Runnable r) {
			R=r;
		}
		
		public void run() {
			if (mustExecute()) {
				R.run();
			}
			remove(R);
		}
	}
	
	Set<Runnable> series=new HashSet<Runnable>();
	int           timeout=1000;
	long		  lastExec=-1;
	
	private synchronized void add(Runnable r) {
		series.add(r);
	}
	
	private synchronized void remove(Runnable r) {
		series.remove(r);
	}
	
	private synchronized boolean mustExecute() {
		long time=System.currentTimeMillis();
		if (series.size()==1) {
			lastExec=time;
			return true;
		} else if ((time-lastExec)>timeout) {
			lastExec=time;
			return true;
		} else {
			return false;
		}
	}
	
	public void invokeLater(Runnable r) {
		this.add(r);
		SwingUtilities.invokeLater(new SeriesRunnable(r));
	}
	
	public MakeAsync(int starvationPreventionTimeout) {
		timeout=starvationPreventionTimeout;
	}

}
