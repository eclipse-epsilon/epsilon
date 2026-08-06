/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.ecore.delegates.DelegateContext.ContextFactory;
import org.eclipse.epsilon.ecore.delegates.notify.DelegateResourceAdapter;

/**
 * A factory for creating EolOperationDelegateContext objects.
 * 
 * @since 2.5
 */
public class EolDelegateContextFactory implements ContextFactory {

	@Override
	public DelegateContext create(
		DelegateUri delegateURI,
		EPackage ePackage,
		DelegateResourceAdapter delegateRA) {
		return new EolOperationDelegateContext(delegateURI, ePackage, delegateRA);
	}

}
