/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.xmi.dt.actions;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.epsilon.common.dt.actions.AbstractObjectActionDelegate;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.hutn.xmi.dt.ConformanceReporter;
import org.eclipse.epsilon.hutn.xmi.dt.XmiConformanceChecker;
import org.eclipse.jface.action.IAction;

public class CheckModelAction extends AbstractObjectActionDelegate {
		
	private final XmiConformanceChecker checker = new XmiConformanceChecker(new DialogReporter());
	
	@Override
	public void run(IAction action) {
		if (getFirstElementInSelection() instanceof IFile) {
			checker.reportConformanceOf((IFile)getFirstElementInSelection());				
		}
	}
	
	private static class DialogReporter implements ConformanceReporter {

		@Override
		public void reportConformant(String name) {
			LogUtil.logInfo(name + " conforms to its registered metamodel.", true);
		}

		@Override
		public void reportNonConformant(String name, Collection<ParseProblem> conformanceProblems) {
			LogUtil.logInfo(name + " does not conform to its registered metamodel. See the Problems view for further details.", true);
		}
		
	}
}
