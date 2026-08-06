/*********************************************************************
 * Copyright (c) 2008 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.TaskContainer;
import org.apache.tools.ant.types.FileSet;

public class ForTask extends EolTask implements TaskContainer {

	protected List<Task> tasks = new ArrayList<>();
	protected FileSet fileset = null;
	protected String var = "it";
	protected String target = null;
	
	@Override
	public void addTask(Task task) {
		tasks.add(task);
	}

	protected String[] getFiles() {
		if (fileset == null || fileset.isEmpty()) return new String[]{};
		return fileset.toString().split(";");
	}

	@Override
	public void executeImpl() throws BuildException {
		
		Collection<?> values;
		
		if (fileset != null) {
			values = Arrays.asList(getFiles());
		}
		else {
			super.executeImpl();
			if (result instanceof Collection<?>) {
				values = (Collection<?>) result;
			}
			else {
				throw new BuildException("Result should be a collection but was " + result);
			}
		}
		
		for (Object value : values) {
			getProject().setProperty(var, value + "");
			if (target != null) {
				getProject().getTargets().get(target).execute();
			}
			else {
				for (Task task : tasks) {
					task.perform();
				}
			}
		}
	}
	
	public void addConfiguredFileset(FileSet fileset) {
		this.fileset = fileset;
	}
	
	public void setVar(String var) {
		this.var = var;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public void setExpr(String expr) {
		this.code = "return " + expr + ";";
	}
}
