/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.evl.execute.atoms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.dom.Constraint;
import org.eclipse.epsilon.evl.dom.ConstraintContext;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.evl.execute.context.IEvlContext;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class ConstraintContextAtom extends EvlAtom<ConstraintContext> {

	public ConstraintContextAtom(ConstraintContext constraintContext, Object modelElement, IEvlContext context) {
		super(constraintContext, modelElement, context);
	}
	
	public ConstraintContextAtom(ConstraintContext constraintContext, Object modelElement) {
		super(constraintContext, modelElement);
	}
	
	public Collection<UnsatisfiedConstraint> executeWithResults() throws EolRuntimeException {
		return executeWithResults(getContext());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<UnsatisfiedConstraint> executeWithResults(IEvlContext context) throws EolRuntimeException {
		if (rule.shouldBeChecked(element, context)) {
			ExecutorFactory executorFactory = context.getExecutorFactory();
			Collection<Constraint> constraints = rule.getConstraints();
			ArrayList<UnsatisfiedConstraint> results = new ArrayList<>(constraints.size());

			for (Constraint constraint : constraints) {
				Object result = executorFactory.execute(constraint, context, element);
				if (result instanceof Optional) {
					((Optional<UnsatisfiedConstraint>) result).ifPresent(results::add);
				}
				else if (result instanceof UnsatisfiedConstraint) {
					results.add((UnsatisfiedConstraint) result);
				}
			}
			return results;
		}
		else return Collections.emptyList();
	}
	
	public List<ConstraintAtom> toConstraintAtoms() {
		return rule.getConstraints().stream().map(c -> new ConstraintAtom(c, this.element)).collect(Collectors.toList());
	}
	
	public static ArrayList<ConstraintContextAtom> getAllJobs(IEvlModule module) throws EolModelElementTypeNotFoundException, EolModelNotFoundException {
		IEvlContext context = module.getContext();
		ArrayList<ConstraintContextAtom> atoms = new ArrayList<>();
		
		for (ConstraintContext constraintContext : module.getConstraintContexts()) {
			Collection<?> allOfKind = constraintContext.getAllOfSourceKind(context);
			atoms.ensureCapacity(atoms.size()+allOfKind.size());
			for (Object element : allOfKind) {
				atoms.add(new ConstraintContextAtom(constraintContext, element, context));
			}
		}
		
		return atoms;
	}
}
