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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.flexmi.FlexmiResource;

public class FeatureComputation extends Computation {
	
	protected EStructuralFeature eStructuralFeature;
	protected String attribute;
	
	public FeatureComputation(EObject eObject, EStructuralFeature eStructuralFeature, String attribute, String expression, URI uri, int lineNumber) {
		super();
		this.eObject = eObject;
		this.eStructuralFeature = eStructuralFeature;
		this.expression = expression;
		this.attribute = attribute;
		this.lineNumber = lineNumber;
		this.uri = uri;
	}
	
	public void compute(FlexmiResource resource) throws Exception {
		
		InMemoryFlexmiModel model = new InMemoryFlexmiModel(resource);
		EolModule module = new EolModule();
		String code = expression;
		if (!attribute.endsWith("_")) code = "return " + code + ";";
		module.parse(code);
		if (!module.getParseProblems().isEmpty()) {
			throw new Exception(module.getParseProblems().get(0).getReason());
		}
		for (Resource r : resource.getResourceSet().getResources()) {
			if (r instanceof FlexmiResource)
				module.getOperations().addAll(((FlexmiResource)r).getOperations());
		}
		module.getContext().getModelRepository().addModel(model);
		module.getContext().setFrameStack(resource.getFrameStack());
		module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("self", eObject));
		eObject.eSet(eStructuralFeature, module.execute());
	}
	
	public EStructuralFeature geteStructuralFeature() {
		return eStructuralFeature;
	}
	
	public String getAttribute() {
		return attribute;
	}
	
}
