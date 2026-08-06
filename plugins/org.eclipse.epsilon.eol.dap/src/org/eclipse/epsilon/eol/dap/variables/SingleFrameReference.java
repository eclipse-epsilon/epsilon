/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.variables;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.SingleFrame;

public class SingleFrameReference extends IdentifiableReference<SingleFrame> {

	public SingleFrameReference(IEolContext context, SingleFrame sf) {
		super(context, sf);
	}

	@Override
	public String getName() {
		final String typeName = target.getType().name();
		if (target.getType() != null) {
			return typeName.substring(0, 1) + typeName.substring(1).toLowerCase();
		}
		return "Unknown";
	}

	@Override
	public List<IVariableReference> getVariables(SuspendedState state) {
		List<IVariableReference> variables = new ArrayList<>();
		for (org.eclipse.epsilon.eol.execute.context.Variable localV : target.getAll().values()) {
			variables.add(state.getValueReference(context, localV.getName(), localV.getValue()));
		}
		return variables;
	}

}
