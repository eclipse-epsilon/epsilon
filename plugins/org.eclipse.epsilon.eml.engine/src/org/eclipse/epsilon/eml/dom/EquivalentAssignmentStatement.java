/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eml.dom;

import java.util.Collection;

import org.eclipse.epsilon.eml.execute.context.EmlContext;
import org.eclipse.epsilon.eol.dom.SpecialAssignmentStatement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class EquivalentAssignmentStatement extends SpecialAssignmentStatement {
	
	@Override
	public Object getValueEquivalent(Object source, Object value,
			IEolContext context_) throws EolRuntimeException {
		EmlContext context = (EmlContext) context_;
		if (value instanceof Collection){
			return context.getMergingStrategy().getEquivalent(((Collection<?>) value), context, null);
		}
		else {
			return context.getMergingStrategy().getEquivalent(value,context, null);
		}
	}
}
