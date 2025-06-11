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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.concurrent.EvlModuleParallelAnnotation;
import org.eclipse.epsilon.evl.execute.CommandLineFixer;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;

public class EvlTask extends ExportableModuleTask {
	
	protected String exportConstraintTrace;
	protected boolean fix = false;
	
	public String getExportConstraintTrace() {
		return exportConstraintTrace;
	}

	public void setExportConstraintTrace(String exportConstraintTrace) {
		if (!StringUtil.isEmpty(this.exportConstraintTrace = exportConstraintTrace) && module instanceof IEvlModule) {
			((IEvlModule) module).getContext().setOptimizeConstraintTrace(false);
		}
	}

	public boolean isFix() {
		return fix;
	}

	public void setFix(boolean fix) {
		this.fix = fix;
	}

	@Override
	protected IEvlModule createDefaultModule() {
		return new EvlModuleParallelAnnotation();
	}

	@Override
	protected void initialize() throws Exception {
		IEvlModule evlModule = (IEvlModule) module;
		if (isFix()) {
			CommandLineFixer clf = new CommandLineFixer();
			evlModule.setUnsatisfiedConstraintFixer(clf);
		}
	}

	@Override
	protected void examine() throws Exception {
		super.examine();
		
		IEvlModule evlModule = (IEvlModule) module;
		int errors = 0;
		int warnings = 0;
		for (UnsatisfiedConstraint unsatisfiedConstraint : evlModule.getContext().getUnsatisfiedConstraints()) {
			if (unsatisfiedConstraint.getConstraint().isCritique()) {
				if (!unsatisfiedConstraint.getConstraint().isInfo()) {
					warnings ++;
				}
			}
			else {
				errors ++;
			}
		}
		log("Errors : " + errors);
		
		final String message = errors + " error(s) and " + warnings + " warning(s) found during validation";
		if (errors > 0) {
			fail(message, null);
		}
		else if (warnings > 0) {
			warn(message);
		}
		
		if (exportConstraintTrace != null) {
			getProjectStackFrame().put(exportConstraintTrace, 
					evlModule.getContext().getConstraintTrace());
		}
	}

	@Override
	protected Collection<Class<?>> getClassesForExportedModel() {
		return Collections.singleton(UnsatisfiedConstraint.class);
	}

	/**
	 * @since 1.6
	 */
	@Override
	protected Collection<UnsatisfiedConstraint> getObjectsForExportedModel() {
		return new ArrayList<>(((IEvlModule)module).getContext().getUnsatisfiedConstraints());
	}
}
