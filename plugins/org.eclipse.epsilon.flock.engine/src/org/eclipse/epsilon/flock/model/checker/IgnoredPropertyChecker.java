/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.model.checker;

import org.eclipse.epsilon.flock.context.MigrationStrategyCheckingContext;
import org.eclipse.epsilon.flock.execute.exceptions.FlockRuntimeException;

public class IgnoredPropertyChecker {

	private final String originalType;
	private final MigrationStrategyCheckingContext context;

	public IgnoredPropertyChecker(String originalType, MigrationStrategyCheckingContext context) {
		this.originalType = originalType;
		this.context      = context;
	}

	public void check(String property) {
		try {
			if (!context.isPropertyInOriginalMetamodel(originalType, property)) {
				context.addWarning("The " + property + " property should be ignored, "  +  
			                       "but there is no " + property + " property defined " +
			                       "for " + originalType + " in the original metamodel.");
			}
			
		} catch (FlockRuntimeException e) {
			// Ignore, and let checking proceed to next property
		}
	}

}
