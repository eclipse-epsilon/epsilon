/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.profiling;

public class ProfilerTargetSummary {
	
	protected int index = 0;
	protected String name;
	protected long executionCount = 0l;
	protected ExecutionTime executionTime = new ExecutionTime();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public long getExecutionCount() {
		return executionCount;
	}
	
	public void setExecutionCount(long executionCount) {
		this.executionCount = executionCount;
	}
	
	public ExecutionTime getExecutionTime() {
		return executionTime;
	}
	
	public void setExecutionTime(ExecutionTime executionTime) {
		this.executionTime = executionTime;
	}
	
}
