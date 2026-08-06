/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.contributors;

public class ReflectiveOperationContributor extends OperationContributor {

	@Override
	public boolean contributesTo(Object target) {
		return target != null;
	}
	
	@Override
	protected Object getReflectionTarget(Object target) {
		return target;
	}
	
	@Override
	protected boolean includeInheritedMethods() {
		return true;
	}
}
