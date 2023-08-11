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
 * A factory for creating EvlDelegateContext objects.
 * 
 * @since 2.5
 */
public class EvlDelegateContextFactory implements ContextFactory {

	/**
	 * Creates a new EvlDelegateContext object.
	 *
	 * @param delegateURI the delegate URI
	 * @param ePackage the e package
	 * @param delegateRA the delegate RA
	 * @return the delegate context
	 */
	@Override
	public DelegateContext create(
		DelegateUri delegateURI, 
		EPackage ePackage, 
		DelegateResourceAdapter delegateRA) {
		return new EvlDelegateContext(delegateURI, ePackage, delegateRA);
	}
	
}
