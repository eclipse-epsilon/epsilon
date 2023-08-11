/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * 
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *   Horacio Hoyos Rodriguez - Initial API and implementation
 *******************************************************************************/
package org.eclipse.epsilon.ecore.delegates;

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.ecore.delegates.notify.Adapters;
import org.eclipse.epsilon.ecore.delegates.notify.DelegateEPackageAdapter;
	
/**
 * A base Delegates implementation 
 * 
 * @param <E> 	the type of ECore elements that use the context
 * @param <R>	the type of Registry used
 * @param <F>	the type of Factory used
 * 
 * @since 2.5
 */
public abstract class AbstractDelegates<E extends EModelElement, D, F> implements Delegates<E, D> {

	public AbstractDelegates(Adapters adapters) {
		super();
		this.adapters = adapters;
	}

	protected final F getFactory(E eObject) {
		EPackage ePackage = getEPackage(eObject);
		DelegateEPackageAdapter adapter = this.adapters.getAdapter(ePackage);
		DelegateContext delegateDomain = adapter.getDelegateDomain();
		String uri = delegateDomain.getURI();
		if (eObject.getEAnnotation(uri) != null) {
			return getFactory(uri, eObject);
		}
		return null;
	}

	protected abstract EPackage getEPackage(E eObject);

	protected abstract F getFactory(String uri, E eObject);
	
	protected final Adapters adapters;

}