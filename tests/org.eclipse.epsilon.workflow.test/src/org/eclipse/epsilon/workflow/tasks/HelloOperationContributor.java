/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Joern Guy Suess - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;

/**
 * A trivial {@link OperationContributor} used to verify that operation
 * contributors registered through the
 * <code>org.eclipse.epsilon.common.dt.operationContributor</code> extension
 * point are discovered when a script is executed headlessly through the
 * <code>org.eclipse.epsilon.workflow</code> Ant tasks (issue #235).
 *
 * <p>It contributes a single <code>sayHello()</code> operation to
 * {@link String} targets, mirroring the reproduction in the issue.</p>
 */
public class HelloOperationContributor extends OperationContributor {

	@Override
	public boolean contributesTo(Object target) {
		return target instanceof String;
	}

	public String sayHello() {
		return "Hello, " + getTarget();
	}
}
