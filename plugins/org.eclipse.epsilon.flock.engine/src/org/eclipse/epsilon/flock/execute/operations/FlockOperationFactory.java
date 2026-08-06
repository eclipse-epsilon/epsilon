/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.execute.operations;

import org.eclipse.epsilon.eol.execute.operations.EolOperationFactory;

public class FlockOperationFactory extends EolOperationFactory {
	
	public FlockOperationFactory() {
		operationCache.put("equivalent", new EquivalentOperation());
	}
}
