/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates.execution;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.epsilon.eol.models.IModel;

/**
 * A wrapper for Epsilon scripts that may have parsing errors.
 *
 * @param <T> the type of execution results
 */
public interface Program<T> {
	
	/**
	 * Execute.
	 *
	 * @param eObject the e object
	 * @param model the model
	 * @return the t
	 * @throws Throwable the throwable
	 */
	public T execute(EObject eObject, IModel model) throws Throwable;
}
