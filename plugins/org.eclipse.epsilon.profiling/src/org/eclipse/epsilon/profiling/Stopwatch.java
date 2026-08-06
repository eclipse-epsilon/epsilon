/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.profiling;

import java.util.concurrent.TimeUnit;

public class Stopwatch {
	
	protected long lastPauseTime;
	protected long startTime;
	protected long pauseDuration;
	protected boolean paused;
	
	public Stopwatch() {
		startTime = System.nanoTime();
		paused = false;
		pause();
	}
	
	public synchronized void pause() {
		if (!paused) {
			lastPauseTime = System.nanoTime();
			paused = true;
		}
	}
	
	public synchronized void resume() {
		if (paused) {
			pauseDuration += System.nanoTime() - lastPauseTime;
			paused = false;
		}
	}
	
	public long getElapsed() {
		return getElapsed(TimeUnit.MILLISECONDS);
	}
	
	public long getElapsed(TimeUnit unit) {
		long time;
		if (paused) {
			time = lastPauseTime - pauseDuration - startTime;
		}
		else {
			time = System.nanoTime() - pauseDuration - startTime;
		}
		return unit.convert(time, TimeUnit.NANOSECONDS);
	}
}
