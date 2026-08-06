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

import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.epsilon.emc.emf.AbstractEmfModel;
import org.eclipse.epsilon.eol.models.transactions.IModelTransaction;

public class EmfModelTransaction implements IModelTransaction {
	
	protected ChangeRecorder changeRecorder;
	protected AbstractEmfModel model;
	
	public EmfModelTransaction(AbstractEmfModel model) {
		this.model = model;
	}
	
	@Override
	public void start() {
		Resource impl = model.getResource();
		ResourceSet rs = impl.getResourceSet();
		this.changeRecorder = rs != null ?
			new ChangeRecorder(rs) : new ChangeRecorder(impl);
	}
	
	@Override
	public void commit() {
		if (changeRecorder != null) {
			changeRecorder.endRecording();
			changeRecorder = null;
		}
	}
	
	@Override
	public void rollback() {
		if (changeRecorder != null) {
			changeRecorder.endRecording().applyAndReverse();
			model.clearCache();
			changeRecorder = null;
		}
	}
	
	@Override
	public void dispose() {
		this.model = null;
	}
}
