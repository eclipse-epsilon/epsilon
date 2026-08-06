/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.etl.execute.operations;

import java.util.Collection;
import java.util.List;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.etl.execute.context.IEtlContext;

public class EquivalentsOperation extends AbstractEquivalentOperation {
	
	@Override
	protected Collection<?> executeImpl(Collection<?> source, IEtlContext context, List<String> rules) throws EolRuntimeException {
		return context.getTransformationStrategy().getEquivalents(source, context, rules);
	}
	
	@Override
	protected Collection<?> executeImpl(Object source, IEtlContext context, List<String> rules) throws EolRuntimeException {
		return context.getTransformationStrategy().getEquivalents(source, context, rules);
	}
}
