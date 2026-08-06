/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.actions;

import org.eclipse.epsilon.flexmi.FlexmiResource;

public abstract class Computation extends Action {
	protected String expression;
	
	public String getExpression() {
		return expression;
	}
	
	@Override
	public void perform(FlexmiResource resource) throws Exception {
		compute(resource);
	}
	
	public abstract void compute(FlexmiResource resource) throws Exception;
}
