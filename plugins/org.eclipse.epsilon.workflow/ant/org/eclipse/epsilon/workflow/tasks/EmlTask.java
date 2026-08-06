/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.eml.EmlModule;
import org.eclipse.epsilon.eml.IEmlModule;
import org.eclipse.epsilon.eml.execute.context.IEmlContext;
import org.eclipse.epsilon.eol.execute.context.Variable;

public class EmlTask extends ExecutableModuleTask {

	protected String useMatchTrace;
	protected String exportMergeTrace;
	protected String exportTransformationTrace;
	
	public String getExportMergeTrace() {
		return exportMergeTrace;
	}

	public void setExportMergeTrace(String exportMergeTrace) {
		this.exportMergeTrace = exportMergeTrace;
	}

	public String getExportTransformationTrace() {
		return exportTransformationTrace;
	}

	public void setExportTransformationTrace(String exportTransformationTrace) {
		this.exportTransformationTrace = exportTransformationTrace;
	}

	public String getUseMatchTrace() {
		return useMatchTrace;
	}

	public void setUseMatchTrace(String useMatchTrace) {
		this.useMatchTrace = useMatchTrace;
	}

	@Override
	protected IEmlModule createDefaultModule() {
		return new EmlModule();
	}

	@Override
	protected void examine() throws Exception {
		IEmlContext context = ((IEmlModule) module).getContext();
		if (exportTransformationTrace != null) {
			getProjectStackFrame().put(exportTransformationTrace, context.getTransformationTrace());
		}
		if (exportMergeTrace != null) {
			getProjectStackFrame().put(exportMergeTrace, context.getMergeTrace());
		}
	}

	@Override
	protected void initialize() throws Exception {
		IEmlContext context = ((IEmlModule) module).getContext();
		
		if (useMatchTrace != null) {
			Variable v = getProjectStackFrame().get(useMatchTrace);
			if (v != null) {
				Object matchTraceImpl = v.getValue();
				if (matchTraceImpl instanceof MatchTrace) {
					context.setMatchTrace(((MatchTrace) matchTraceImpl).getReduced());
				}
			}
		}
	}
}
