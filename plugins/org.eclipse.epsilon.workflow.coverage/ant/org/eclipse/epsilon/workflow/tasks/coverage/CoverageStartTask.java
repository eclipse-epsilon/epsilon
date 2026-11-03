/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.coverage;

import org.apache.tools.ant.BuildException;
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
 * @since 2.10
 */
public class CoverageStartTask extends EpsilonTask {
	
	/** Name of the Ant project reference holding the active recorder */
	public static final String RECORDER_REFERENCE = "epsilon.coverage.recorder";
	
	@Override
	public void executeImpl() throws BuildException {
		Object existing = getProject().getReference(RECORDER_REFERENCE);
		if (existing != null && getExecutionListeners(getProject()).contains(existing)) {
			warn("Coverage recording has already been started");
			return;
		}
		CoverageRecorder recorder = new CoverageRecorder();
		getProject().addReference(RECORDER_REFERENCE, recorder);
		getExecutionListeners(getProject()).add(recorder);
	}
}
