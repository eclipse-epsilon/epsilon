/*********************************************************************
* Copyright (c) 2018 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.execute.operations.declarative;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 * @param <O>
 */
public abstract class DelegateBasedOperation<O extends FirstOrderOperation> extends FirstOrderOperation {

	protected DelegateBasedOperation(O delegate) {
		this.delegate = delegate;
	}

	private O delegate;
	
	public void setDelegateOperation(O delegateOp) {
		this.delegate = delegateOp;
	}

	public O getDelegateOperation() {
		return delegate;
	}
}
