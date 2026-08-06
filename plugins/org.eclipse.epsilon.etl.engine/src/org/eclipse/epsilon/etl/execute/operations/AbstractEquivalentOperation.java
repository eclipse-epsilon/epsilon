/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.etl.execute.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.operations.simple.SimpleOperation;
import org.eclipse.epsilon.etl.execute.context.IEtlContext;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
abstract class AbstractEquivalentOperation extends SimpleOperation {

	@Override
	public Object execute(Object source, List<?> parameters, IEolContext context,
			ModuleElement ast) throws EolRuntimeException {
		
		if (source == null) return null;
		
		int paramsSize = parameters.size();
		List<String> rules = null;
		if (paramsSize > 0) {
			rules = new ArrayList<>(paramsSize);
			for (Object parameter : parameters) {
				rules.add(Objects.toString(parameter, ""));
			}
		}
		
		if (source instanceof Collection) {
			return executeImpl((Collection<?>) source, (IEtlContext) context, rules);
		}
		else {
			return executeImpl(source, (IEtlContext) context, rules);
		}
	}

	protected abstract Object executeImpl(Collection<?> source, IEtlContext context, List<String> rules) throws EolRuntimeException;
	
	protected abstract Object executeImpl(Object source, IEtlContext context, List<String> rules) throws EolRuntimeException;
	
}
