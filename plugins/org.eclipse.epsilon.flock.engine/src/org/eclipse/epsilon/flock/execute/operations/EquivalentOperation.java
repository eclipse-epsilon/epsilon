/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.execute.operations;

import java.util.List;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.operations.simple.SimpleOperation;
import org.eclipse.epsilon.flock.execute.context.IFlockContext;
import org.eclipse.epsilon.flock.execute.exceptions.ConservativeCopyException;


public class EquivalentOperation extends SimpleOperation {
	
	@Override
	public Object execute(Object originalModelElement, List<?> parameters, IEolContext context, ModuleElement ast) throws ConservativeCopyException {
		return ((IFlockContext)context).getConservativeCopyContext().getEquivalent(originalModelElement);
	}
}
