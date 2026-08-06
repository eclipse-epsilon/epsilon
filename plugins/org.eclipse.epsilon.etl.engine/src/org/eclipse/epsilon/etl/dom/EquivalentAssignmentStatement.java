/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.etl.dom;

import java.util.Collection;

import org.eclipse.epsilon.eol.dom.SpecialAssignmentStatement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.etl.execute.context.IEtlContext;

public class EquivalentAssignmentStatement extends SpecialAssignmentStatement {
	
	@Override
	public Object getValueEquivalent(Object source, Object value, IEolContext context_) throws EolRuntimeException {
		IEtlContext context = (IEtlContext) context_;
		if (value instanceof Collection){
			return context.getTransformationStrategy().getEquivalent(((Collection<?>) value), context, null);
		}
		else {
			return context.getTransformationStrategy().getEquivalent(value,context, null);
		}
	}
	
}
