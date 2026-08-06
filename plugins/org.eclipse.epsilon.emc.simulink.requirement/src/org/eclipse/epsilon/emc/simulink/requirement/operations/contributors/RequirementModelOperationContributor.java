/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.requirement.operations.contributors;

import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.requirement.introspection.java.SimulinkRequirementObjectMethod;
import org.eclipse.epsilon.emc.simulink.requirement.model.element.ISimulinkRequirementModelElement;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.ObjectMethod;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;

public class RequirementModelOperationContributor extends OperationContributor {

	protected MatlabEngine engine = null;

	public RequirementModelOperationContributor(MatlabEngine engine) {
		this.engine = engine;
	}

	@Override
	public boolean contributesTo(Object target) {
		return (target instanceof ISimulinkRequirementModelElement);
	}

	@Override
	public ObjectMethod findContributedMethodForEvaluatedParameters(Object target, String name, Object[] parameters,
			IEolContext context, boolean overrideContextOperationContributorRegistry) {
		ObjectMethod objectMethod = null;
		if (overrideContextOperationContributorRegistry == false 
				&& context.getOperationFactory().getOperationFor(name) == null) {
			objectMethod = new SimulinkRequirementObjectMethod(engine, target, name);
		}
		if (objectMethod == null) { 
			objectMethod = super.findContributedMethodForEvaluatedParameters(target, name, parameters, context, overrideContextOperationContributorRegistry);
		}
		return objectMethod;
	}
	
	

}