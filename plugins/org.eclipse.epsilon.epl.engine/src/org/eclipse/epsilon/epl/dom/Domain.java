/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.epl.dom;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.types.EolType;

public class Domain extends ExecutableBlock<Object> {
	
	protected boolean dynamic = false;
	protected Role role;
	
	public Domain() {
		super(Object.class);
	}
	
	public void setRole(Role role) {
		this.dynamic = "from".equals(getText());
		this.role = role;
	}
	
	public Role getEplRole() {
		return role;
	}
	
	public boolean isDynamic() {
		return dynamic;
	}

	public List<Object> getValues(IEolContext context, EolType type) throws EolRuntimeException {
		if (!getEplRole().isActive(context, true)) {
			return NoMatch.asList();
		}
		
		Object result =  context.getExecutorFactory().execute(this, context);
		
		if (!(result instanceof Collection)) {
			result = Collections.singleton(result);
		}
		
		Collection<?> resultCol = (Collection<?>) result;
		
		return StreamSupport.stream(
				resultCol.spliterator(),
				false//resultCol.size() > 2 << 20
			)
			.filter(type::isKind)
			.collect(Collectors.toList());
	}
}
