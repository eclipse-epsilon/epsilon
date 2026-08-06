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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.flexmi.FlexmiResource;

public class ObjectInitialization extends Computation {
	
	public ObjectInitialization(EObject eObject, String expression, URI uri, int lineNumber) {
		this.eObject = eObject;
		this.expression = expression;
		this.uri = uri;
		this.lineNumber = lineNumber;
	}
	
	@Override
	public void compute(FlexmiResource resource) throws Exception {
		InMemoryEmfModel model = new InMemoryEmfModel(resource);
		EolModule module = new EolModule();
		module.parse(expression);
		if (!module.getParseProblems().isEmpty()) {
			throw new Exception("Parse problem " + module.getParseProblems().get(0).getReason());
		}
		module.getContext().getModelRepository().addModel(model);
		module.getContext().setFrameStack(resource.getFrameStack());
		module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("self", eObject));
		module.execute();
	}
	
}
