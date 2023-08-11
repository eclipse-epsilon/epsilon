/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates.notify;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.epsilon.ecore.delegates.DelegateContext.ContextFactory;
import org.eclipse.epsilon.ecore.delegates.validation.EpsilonValidationDelegate;
import org.eclipse.epsilon.ecore.delegates.validation.ValidationUri;
import org.eclipse.epsilon.ecore.delegates.validation.EpsilonValidationDelegate.Factory;

/**
 * An {@link Adapters} implementation for Evl Validation
 * 
 * @since 2.5
 */
public class EvlAdapters extends Adapters {
	
	public EvlAdapters(
		ValidationUri validationURI,
		ContextFactory defaultFactory,
		ContextFactory.Registry defaultRegistry,
		Factory.Registry delegateRegistry,
		EpsilonValidationDelegate.Factory delegateFactory) {
		super(validationURI, defaultFactory, defaultRegistry);
		this.delegateFactory = delegateFactory;
		this.delegateRegistry = delegateRegistry;
	}
	
	
	public DelegateResourceSetAdapter getAdapter(ResourceSet resourceSet) {
		DelegateResourceSetAdapter adapter = findAdapter(resourceSet);
		if (adapter == null) {
			adapter = new DelegateResourceSetAdapter();
			// This mimics the extension point registration, so it will be useful when standalone
			((ValidationUri) this.delegateURI).register(
					this.delegateRegistry,
					this.delegateFactory);
			adapter.putRegistry(EpsilonValidationDelegate.Factory.Registry.class, this.delegateRegistry);
			resourceSet.eAdapters().add(adapter);
		}
		return adapter;
	}
	
	private final Factory.Registry delegateRegistry;
	private final EpsilonValidationDelegate.Factory delegateFactory;

}
