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


public abstract class NestedModelTransactionSupport implements IModelTransactionSupport{
	
	protected ModelTransactionTree activeTransactionTree;
	
	public void commitTransaction() {
		if (activeTransactionTree != null) {
			activeTransactionTree.getTransaction().commit();
			activeTransactionTree.getTransaction().dispose();
			activeTransactionTree = activeTransactionTree.getParent();
		}		
	}

	public void rollbackTransaction() {
		if (activeTransactionTree != null) {
			activeTransactionTree.getTransaction().rollback();
			activeTransactionTree.getTransaction().dispose();
			activeTransactionTree = activeTransactionTree.getParent();
		}
	}

	public void startTransaction() {
		
		IModelTransaction newTransaction = createTransaction();
		
		if (activeTransactionTree == null) {
			activeTransactionTree = new ModelTransactionTree(newTransaction, null);
		}
		else {
			activeTransactionTree = new ModelTransactionTree(newTransaction, activeTransactionTree);
		}
		activeTransactionTree.getTransaction().start();
	}
	
	public abstract IModelTransaction createTransaction();
	
}
