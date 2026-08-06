/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.execute.introspection.java;

import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;

/**
 * 
 * @author Sina Madani
 * @since 2.2
 */
abstract class DisposableObject implements AutoCloseable {

	protected Object object;
	
	@Override
	public void close() {
		if (object instanceof OperationContributor) {
			((OperationContributor) object).close();
		}
	}

}
