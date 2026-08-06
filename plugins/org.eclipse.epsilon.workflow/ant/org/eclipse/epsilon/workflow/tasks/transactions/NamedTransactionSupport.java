/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.transactions;

import org.eclipse.epsilon.eol.models.transactions.IModelTransactionSupport;

public class NamedTransactionSupport implements IModelTransactionSupport {
	
	protected String name;
	protected IModelTransactionSupport transactionSupport;
	
	public NamedTransactionSupport(String name, IModelTransactionSupport transactionSupport) {
		this.name = name;
		this.transactionSupport = transactionSupport;
	}
	
	@Override
	public void commitTransaction() {
		transactionSupport.commitTransaction();
	}

	@Override
	public void rollbackTransaction() {
		transactionSupport.rollbackTransaction();
	}

	@Override
	public void startTransaction() {
		transactionSupport.startTransaction();
	}

	public String getName() {
		return name;
	}
}
