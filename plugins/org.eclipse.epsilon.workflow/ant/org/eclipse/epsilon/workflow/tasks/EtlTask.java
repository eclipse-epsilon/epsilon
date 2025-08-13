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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.etl.IEtlModule;
import org.eclipse.epsilon.etl.dom.TransformationRule;
import org.eclipse.epsilon.etl.trace.Transformation;
import org.eclipse.epsilon.etl.trace.TransformationTrace;

public class EtlTask extends ExportableModuleTask {
	
	protected String exportTransformationTrace;
	
	public String getExportTransformationTrace() {
		return exportTransformationTrace;
	}

	public void setExportTransformationTrace(String exportTransformationTrace) {
		this.exportTransformationTrace = exportTransformationTrace;
	}
	
	@Override
	protected void initialize() throws Exception {}
	
	@Override
	protected IEtlModule createDefaultModule() {
		return new EtlModule();
	}
	
	@Override
	protected void examine() throws Exception {
		super.examine();
		
		final IEtlModule etlModule = (IEtlModule) module;

		if (exportTransformationTrace != null) {
			getProjectStackFrame().put(exportTransformationTrace,
					etlModule.getContext().getTransformationTrace());
		}
	}
	
	@Override
	protected Collection<? extends Object> getObjectsForExportedModel() {
		final TransformationTrace trace = ((IEtlModule)module).getContext().getTransformationTrace();
		return Collections.singleton(trace);
	}

	@Override
	protected Collection<Class<?>> getClassesForExportedModel() {
		return Arrays.asList(
			TransformationTrace.class, Transformation.class, TransformationRule.class
		);
	}
}
