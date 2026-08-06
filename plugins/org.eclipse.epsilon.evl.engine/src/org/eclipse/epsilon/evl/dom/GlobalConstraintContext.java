/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.evl.dom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.types.EolNoType;

public class GlobalConstraintContext extends ConstraintContext {
	
	protected List<Object> allOfType;
	
	public GlobalConstraintContext() {
		allOfType = new ArrayList<>();
		allOfType.add(EolNoType.Instance);
	}
	
	@Override
	public Collection<?> getAllOfSourceKind(IEolContext context)
			throws EolModelElementTypeNotFoundException,
			EolModelNotFoundException {
		return getAllOfSourceType(context);
	}
	
	@Override
	public Collection<?> getAllOfSourceType(IEolContext context)
			throws EolModelElementTypeNotFoundException,
			EolModelNotFoundException {
		return allOfType;
	}
	
	@Override
	public String getTypeName() {
		return "<Global>";
	}
}
