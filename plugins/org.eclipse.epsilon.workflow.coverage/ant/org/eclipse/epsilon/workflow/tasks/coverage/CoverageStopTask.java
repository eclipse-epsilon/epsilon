/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.coverage;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.eclipse.epsilon.coverage.CoverageRecorder;
import org.eclipse.epsilon.coverage.CoverageResources;
import org.eclipse.epsilon.coverage.CoverageStatistics;
import org.eclipse.epsilon.coverage.model.CoverageSession;
import org.eclipse.epsilon.workflow.tasks.EpsilonTask;

/**
 * Stops recording coverage information (see {@link CoverageStartTask})
 * and saves it to an .ecv file. Does nothing if recording has been
 * disabled through the enabled attribute of {@link CoverageStartTask}.
 * 
 * @since 2.10
 */
public class CoverageStopTask extends EpsilonTask {
	
	protected File file;
	protected String name;
	protected boolean append = false;
	
	@Override
	public void executeImpl() throws BuildException {
		if (file == null) {
			fail("The file attribute is required", null);
			return;
		}
		
		Object reference = getProject().getReference(CoverageStartTask.RECORDER_REFERENCE);
		if (CoverageStartTask.DISABLED.equals(reference)) {
			getProject().getReferences().remove(CoverageStartTask.RECORDER_REFERENCE);
			log("Coverage recording is disabled: " + file + " has not been written", Project.MSG_INFO);
			return;
		}
		if (!(reference instanceof CoverageRecorder) || !getExecutionListeners(getProject()).remove(reference)) {
			fail("Coverage recording has not been started (use epsilon.coverage.start first)", null);
			return;
		}
		
		CoverageRecorder recorder = (CoverageRecorder) reference;
		CoverageSession session = recorder.createCoverageSession(name != null ? name : getProject().getName());
		
		try {
			if (append && file.exists()) {
				session = CoverageResources.merge(CoverageResources.load(file), session);
			}
			if (file.getParentFile() != null) file.getParentFile().mkdirs();
			CoverageResources.save(session, file);
		}
		catch (Exception ex) {
			fail("Could not save coverage information to " + file, ex);
			return;
		}
		
		CoverageStatistics.Counter counter = new CoverageStatistics().get(session);
		log(String.format("Coverage: %.1f%% (%s probes) saved to %s", counter.getRatio() * 100, counter, file), Project.MSG_INFO);
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isAppend() {
		return append;
	}
	
	public void setAppend(boolean append) {
		this.append = append;
	}
}
