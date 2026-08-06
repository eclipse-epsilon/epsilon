/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.exceptions.models;

import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.models.IModel;

public class EolModelLoadingException extends EolInternalException {
	
	protected IModel model = null;
	
	public EolModelLoadingException(Exception internal, IModel model) {
		super(internal);
		this.model = model;
	}
	
	@Override
	public String getReason() {
		return "Error whilst loading model " + model.getName() + ": " + super.getReason();
	}
	
}
