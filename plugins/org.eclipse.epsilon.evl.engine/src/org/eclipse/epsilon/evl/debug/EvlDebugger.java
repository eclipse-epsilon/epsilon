/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.debug;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.dom.Constraint;
import org.eclipse.epsilon.evl.dom.ConstraintContext;
import org.eclipse.epsilon.evl.dom.Fix;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;

public class EvlDebugger extends EolDebugger {
	
	@Override
	protected boolean isStructuralBlock(ModuleElement ast) {
		return super.isStructuralBlock(ast) || ast instanceof ConstraintContext || ast instanceof Constraint || ast instanceof Fix;
	}

	@Override
	public boolean isDoneAfterModuleElement(ModuleElement ast) {
		if (super.isDoneAfterModuleElement(ast)) {
			if (getModule().getUnsatisfiedConstraintFixer() == null) {
				// There is no fixer to apply the fixes
				return true;
			}

			for (UnsatisfiedConstraint unsatisfied : getModule().getContext().getUnsatisfiedConstraints()) {
				if (!unsatisfied.isFixed() && !unsatisfied.getFixes().isEmpty()) {
					// There is at least one unfixed unsatisfied constraint with fixes: leave it running
					return false;
				}
			}

			// no unsatisfied constraints with fixes: EVL script will end as usual
			return true;
		}
		return false;
	}

	@Override
	public EvlModule getModule() {
		return (EvlModule) super.getModule();
	}
	
}
