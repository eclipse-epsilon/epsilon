/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egx.engine.test.acceptance.contributors;

import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.types.EolNoType;

/**
 * 
 * @author Alfonso de la Vega
 * @since 1.6
 */
public class SimpleOperatorContributor extends OperationContributor {

	@Override
	public boolean contributesTo(Object target) {
		return target == EolNoType.NoInstance;
	}

	public String simpleOperation(int number) {
		return String.format("%d; %d x 2 = %d", number, number, 2 * number);
	}
}
