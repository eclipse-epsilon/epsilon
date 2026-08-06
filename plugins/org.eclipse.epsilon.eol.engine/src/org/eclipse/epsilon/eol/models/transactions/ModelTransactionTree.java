/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.models.transactions;

import java.util.List;


public class ModelTransactionTree {
	
	protected IModelTransaction transaction;
	protected ModelTransactionTree parent;
	protected List<ModelTransactionTree> nested;
	
	public ModelTransactionTree(IModelTransaction transaction, ModelTransactionTree parent) {
		this.transaction = transaction;
		this.parent = parent;
	}

	public IModelTransaction getTransaction() {
		return transaction;
	}

	public ModelTransactionTree getParent() {
		return parent;
	}

	public List<ModelTransactionTree> getNested() {
		return nested;
	}
	
}
