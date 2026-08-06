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

import java.util.Arrays;

import org.eclipse.epsilon.eol.types.EolSequence;

public class ArrayOperationContributor extends OperationContributor {

	@Override
	public boolean contributesTo(Object target) {
		return target != null && target.getClass().isArray();
	}
	
	public EolSequence<Object> asSequence() {
		EolSequence<Object> sequence = new EolSequence<>();
		sequence.addAll(Arrays.asList(getTarget()));
		return sequence;
	}
}
