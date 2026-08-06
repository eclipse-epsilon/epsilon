/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flock.model.domain;

import org.eclipse.epsilon.eol.dom.SpecialAssignmentStatement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.flock.execute.context.IFlockContext;

public class EquivalentAssignmentStatement extends SpecialAssignmentStatement {
	
	@Override
	public Object getValueEquivalent(Object source, Object value, IEolContext context_) throws EolRuntimeException {
		return ((IFlockContext) context_).getConservativeCopyContext().getEquivalent(value);
	}
}
