/*******************************************************************************
 * Copyright (c) 2008-2018 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - refactoring
 ******************************************************************************/
package org.eclipse.epsilon.ecl.execute.operations;

import java.util.Collection;
import java.util.List;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.ecl.execute.context.IEclContext;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class DoMatchOperation extends MatchesOperation {

	public DoMatchOperation() {
		forcedMatch = true;
	}
	
	/**
	 * 
	 * @param leftColFlat
	 * @param rightColFlat
	 * @param context
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	protected boolean matchAll(Collection<?> leftColFlat, Collection<?> rightColFlat, IEclContext context) throws EolRuntimeException {
		boolean match = true;
		
		for (Object left : leftColFlat) {
			for (Object right : rightColFlat) {
				// Note the bitwise operator - we don't want to short-circuit!
				match &= matchInstances(left, right, context);
			}
		}

		return match;
	}
	
	@Override
	public Boolean execute(Object source, List<?> parameters, IEolContext context_, ModuleElement ast) throws EolRuntimeException {
		
		IEclContext context = (IEclContext) context_;
		Object parameter = parameters.get(0);
		if (source == null && parameter == null) return null;
		
		Collection<?> leftColFlat = CollectionUtil.flatten(CollectionUtil.asCollection(source));
		Collection<?> rightColFlat = CollectionUtil.flatten(CollectionUtil.asCollection(parameter));
		
		return matchAll(leftColFlat, rightColFlat, context);
	}
	
}
