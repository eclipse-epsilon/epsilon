/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.validation.model;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.ModelElement;
import org.eclipse.epsilon.hutn.model.hutn.Slot;
import org.eclipse.epsilon.hutn.validation.AbstractFixer;

class HutnFixer extends AbstractFixer {

	@Override
	protected ParseProblem interpretUnsatisfiedConstraint(UnsatisfiedConstraint constraint) {
		if (constraint.getInstance() instanceof ModelElement) {			
			final ModelElement modelElement = (ModelElement) constraint.getInstance();
			return new ParseProblem(modelElement.getLine(), modelElement.getCol(), constraint.getMessage());
		}
		else {
			throw new IllegalArgumentException("Constraint instance was not a ModelElement");
		}
	}

	@Override
	protected boolean applyFix(UnsatisfiedConstraint constraint) throws EolRuntimeException {
		// Assume all fixes are for ClassMustSpecifyRequiredReferences constraint
		return applyFixForClassMustSpecifyRequiredReferences(constraint);
	}
	
	private boolean applyFixForClassMustSpecifyRequiredReferences(UnsatisfiedConstraint constraint) throws EolRuntimeException {
		final ClassObject object = (ClassObject) constraint.getInstance();
		
		final List<Slot<?>> originalSlots = defensiveCopy(object.getSlots());
		
		constraint.getFixes().iterator().next().perform();
		
		// Return true only if fix caused a change.
		return !originalSlots.equals(object.getSlots());
	}
	
	private static <T> List<T> defensiveCopy(List<T> original) {
		return new LinkedList<>(original);
	}
}
