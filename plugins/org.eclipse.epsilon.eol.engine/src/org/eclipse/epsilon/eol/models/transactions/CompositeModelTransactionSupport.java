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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.eol.models.IModel;

public class CompositeModelTransactionSupport implements IModelTransactionSupport {
	
	protected List<IModel> models = new ArrayList<>();
	
	@Override
	public void commitTransaction() {
		for (IModel model : getModels()) {
			model.getTransactionSupport().commitTransaction();
		}
	}

	@Override
	public void rollbackTransaction() {
		for (IModel model : getModels()) {
			model.getTransactionSupport().rollbackTransaction();
		}
	}

	@Override
	public void startTransaction() {
		for (IModel model : getModels()) {
			model.getTransactionSupport().startTransaction();
		}
	}
	
	public List<IModel> getModels() {
		return models;
	}
}
