/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.validation;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.evl.IEvlFixer;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;

public abstract class AbstractFixer implements IEvlFixer {

	private IEvlModule module = null;
	private boolean changedModel = false;
	
	@Override
	public void fix(IEvlModule module) throws EolRuntimeException {
		this.module = module; 
		applyFixes();
	}
	
	private void applyFixes() throws EolRuntimeException {
		reset();

		for (UnsatisfiedConstraint constraint : module.getContext().getUnsatisfiedConstraints()) {
			if (!constraint.getFixes().isEmpty()) {				
				changedModel = applyFix(constraint);
			}
		}
	}

	public void reset() {
		changedModel = false;
	}

	public boolean hasChangedModel() {
		return changedModel ;
	}
	
	/**
	 * Clients should override this method if fixes are used by the
	 * validator. Returning true causes validation to be invoked again.
	 * 
	 * @param constraint The unsatisifed constraint for which a fix will be applied. 
	 * @return true if and only if the fix caused the model to be changed.
	 * @throws EolRuntimeException 
	 */
	protected boolean applyFix(UnsatisfiedConstraint constraint) throws EolRuntimeException {
		return false;
	}
	
	protected abstract ParseProblem interpretUnsatisfiedConstraint(UnsatisfiedConstraint constraint);
}
