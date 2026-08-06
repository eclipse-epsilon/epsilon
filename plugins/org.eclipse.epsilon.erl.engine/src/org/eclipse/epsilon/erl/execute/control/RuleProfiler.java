/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.erl.execute.control;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.control.ExecutionProfiler;
import org.eclipse.epsilon.erl.dom.*;

/**
 * Profiles {@link NamedRule}s.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class RuleProfiler extends ExecutionProfiler {

	@Override
	protected boolean screenAST(ModuleElement ast, IEolContext context) {
		return ast instanceof NamedRule || ast instanceof Pre || ast instanceof Post;
	}
	
}
