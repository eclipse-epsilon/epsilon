/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.evl.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.evl.dom.Constraint;
import org.eclipse.epsilon.evl.dom.ConstraintContext;
import org.eclipse.epsilon.evl.execute.context.concurrent.*;

/**
 * Provides data-parallelism, executing all applicable constraints for a given
 * model element in a separate job.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EvlModuleParallelElements extends EvlModuleParallel {

	public EvlModuleParallelElements() {
		super();
	}
	
	public EvlModuleParallelElements(EvlContextParallel context) {
		super(context);
	}
	
	@Override
	protected void checkConstraints() throws EolRuntimeException {
		final EvlContextParallel context = (EvlContextParallel) getContext();
		
		for (final ConstraintContext constraintContext : getConstraintContexts()) {
			final Collection<Constraint> constraintsToCheck = preProcessConstraintContext(constraintContext);
			final Collection<?> allOfKind = constraintContext.getAllOfSourceKind(context);
			final Collection<Callable<?>> jobs = new ArrayList<>(allOfKind.size());
			
			for (Object element : allOfKind) {
				jobs.add(() -> constraintContext.execute(constraintsToCheck, element, context.getShadow()));
			}
			
			context.executeAll(constraintContext, jobs);
		}
	}

}