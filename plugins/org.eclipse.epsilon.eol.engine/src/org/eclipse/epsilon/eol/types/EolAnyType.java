/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.types;

import java.util.List;

import org.eclipse.epsilon.eol.exceptions.EolIllegalOperationParametersException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.types.EolNoType.EolNoTypeInstance;

public class EolAnyType extends EolType {

	public static final EolAnyType Instance = new EolAnyType();

	@Override
	public boolean isType(Object o) {
		return false;
	}

	@Override
	public boolean isKind(Object o) {
		// For consistency with AnyOperationContributor
		return !(o instanceof EolNoTypeInstance);
	}

	@Override
	public Object createInstance() throws EolRuntimeException {
		return new EolAny();
	}
	
	@Override
	public String getName() {
		return "Any";
	}

	@Override
	public Object createInstance(List<Object> parameters) throws EolRuntimeException {
		throw new EolIllegalOperationParametersException("createInstance");
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	protected EolType getParentType() {
		return null;
	}
}
