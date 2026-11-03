/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.coverage;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.eclipse.epsilon.coverage.CoverageRecorder;
import org.eclipse.epsilon.workflow.tasks.EpsilonTask;

/**
 * Starts recording coverage information for all Epsilon programs executed
 * by subsequent Epsilon tasks of the build, until a matching
 * {@link CoverageStopTask} (epsilon.coverage.stop) is executed. Example:
 * 
 * <pre>
 * &lt;epsilon.coverage.start/&gt;
 * &lt;epsilon.eunit src="tests.eunit"/&gt;
 * &lt;epsilon.coverage.stop file="coverage.ecv"/&gt;
 * </pre>
 * 
 * Recording can be switched off without removing the tasks through the
 * {@code enabled} attribute (true by default), typically bound to an Ant
 * property that can be overridden from the command line or a launch
 * configuration (e.g. {@code ant -Dcoverage=false}):
 * 
 * <pre>
 * &lt;property name="coverage" value="true"/&gt;
 * &lt;epsilon.coverage.start enabled="${coverage}"/&gt;
 * </pre>
 * 
 * When disabled, the matching epsilon.coverage.stop task does nothing.
 * 
 * @since 2.10
 */
public class CoverageStartTask extends EpsilonTask {
	
	/** Name of the Ant project reference holding the active recorder */
	public static final String RECORDER_REFERENCE = "epsilon.coverage.recorder";
	
	/** Value of {@link #RECORDER_REFERENCE} when recording has been disabled */
	public static final String DISABLED = "disabled";
	
	protected boolean enabled = true;
	
	@Override
	public void executeImpl() throws BuildException {
		Object existing = getProject().getReference(RECORDER_REFERENCE);
		if (existing != null && getExecutionListeners(getProject()).contains(existing)) {
			warn("Coverage recording has already been started");
			return;
		}
		if (!enabled) {
			getProject().addReference(RECORDER_REFERENCE, DISABLED);
			log("Coverage recording is disabled", Project.MSG_VERBOSE);
			return;
		}
		CoverageRecorder recorder = new CoverageRecorder();
		getProject().addReference(RECORDER_REFERENCE, recorder);
		getExecutionListeners(getProject()).add(recorder);
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
