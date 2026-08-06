/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.tools;

import java.util.Objects;
import org.eclipse.epsilon.profiling.Profiler;


public class ProfilerTool extends AbstractTool {
	
	public void start(String targetName, Object data) {
		Profiler.INSTANCE.start(targetName, Objects.toString(data, ""), context.getExecutorFactory().getActiveModuleElement());
	}
	
	public void refresh() {
		Profiler.INSTANCE.refresh();
	}
	
	public void reset() {
		Profiler.INSTANCE.reset();
	}
	
	public void start(String targetName) {
		start(targetName, "");
	}
	
	public void stop(String targetName) {
		Profiler.INSTANCE.stop(targetName);
	}
	
	public void stop() {
		Profiler.INSTANCE.stop(null);
	}
}
