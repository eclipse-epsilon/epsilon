/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf.transactions;

import org.eclipse.epsilon.emc.emf.AbstractEmfModel;
import org.eclipse.epsilon.eol.models.transactions.IModelTransaction;
import org.eclipse.epsilon.eol.models.transactions.NestedModelTransactionSupport;

public class EmfModelTransactionSupport extends NestedModelTransactionSupport {
	
	protected AbstractEmfModel model = null;
	
	public EmfModelTransactionSupport(AbstractEmfModel model) {
		this.model = model;
	}

	@Override
	public IModelTransaction createTransaction() {
		return new EmfModelTransaction(model);
	}
	
	
}
