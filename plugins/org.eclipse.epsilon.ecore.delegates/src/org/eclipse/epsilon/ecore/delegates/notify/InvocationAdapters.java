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
import org.eclipse.epsilon.ecore.delegates.invocation.EpsilonInvocationDelegate;
import org.eclipse.epsilon.ecore.delegates.invocation.InvocationUri;

/**
 * An {@link Adapters} implementation for Eol Invocation
 * 
 * @since 2.5
 */
public class InvocationAdapters extends Adapters {

	public InvocationAdapters(
		InvocationUri invocationUri,
		ContextFactory defaultCtxFctry,
		ContextFactory.Registry defaultCtxFctryRegistry,
		EpsilonInvocationDelegate.Factory delegateFactory,
		EpsilonInvocationDelegate.Factory.Registry delegateRegistry) {
		super(invocationUri, defaultCtxFctry, defaultCtxFctryRegistry);
		this.delegateFactory = delegateFactory;
		this.delegateRegistry = delegateRegistry;
	}

	@Override
	public DelegateResourceSetAdapter getAdapter(ResourceSet resourceSet) {
		DelegateResourceSetAdapter adapter = findAdapter(resourceSet);
		if (adapter == null) {
			adapter = new DelegateResourceSetAdapter();
			// This mimics the extension point registration, so it will be useful when standalone
			((InvocationUri) this.delegateURI).register(
					this.delegateRegistry,
					this.delegateFactory);
			adapter.putRegistry(EpsilonInvocationDelegate.Factory.Registry.class, this.delegateRegistry);
			resourceSet.eAdapters().add(adapter);
		}
		return adapter;
	}
	
	private final EpsilonInvocationDelegate.Factory.Registry delegateRegistry;
	private final EpsilonInvocationDelegate.Factory delegateFactory;

}
