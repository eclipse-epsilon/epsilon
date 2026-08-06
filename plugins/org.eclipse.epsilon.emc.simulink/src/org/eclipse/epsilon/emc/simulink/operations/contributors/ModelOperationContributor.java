/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.operations.contributors;

import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.introspection.java.SimulinkObjectMethod;
import org.eclipse.epsilon.emc.simulink.introspection.java.StateflowObjectMethod;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.emc.simulink.model.element.SimulinkElement;
import org.eclipse.epsilon.emc.simulink.model.element.SimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.model.element.StateflowBlock;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.ObjectMethod;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;

public class ModelOperationContributor extends OperationContributor {

	protected MatlabEngine engine;

	public ModelOperationContributor(MatlabEngine engine) {
		this.engine = engine;
	}

	@Override
	public boolean contributesTo(Object target) {
		return (target instanceof SimulinkModelElement
				|| target instanceof SimulinkModel);
	}

	@Override
	public ObjectMethod findContributedMethodForEvaluatedParameters(Object target, String name, Object[] parameters,
			IEolContext context, boolean overrideContextOperationContributorRegistry) {
		ObjectMethod objectMethod = null;
		if (overrideContextOperationContributorRegistry == false 
				&& context.getOperationFactory().getOperationFor(name) == null) {
			if (target instanceof StateflowBlock) {
				objectMethod = new StateflowObjectMethod(engine, target, name);
			} else if (target instanceof SimulinkElement || target instanceof SimulinkModel) {
				objectMethod = new SimulinkObjectMethod(engine, target, name);
			}
		}
		if (objectMethod == null) { 
			objectMethod = super.findContributedMethodForEvaluatedParameters(target, name, parameters, context, overrideContextOperationContributorRegistry);
		}
		return objectMethod;
	}
	
}