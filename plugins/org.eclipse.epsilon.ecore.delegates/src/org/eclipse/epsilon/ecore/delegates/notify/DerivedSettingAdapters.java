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
import org.eclipse.epsilon.ecore.delegates.DelegateUri;
import org.eclipse.epsilon.ecore.delegates.setting.EpsilonSettingDelegate;
import org.eclipse.epsilon.ecore.delegates.setting.SettingUri;

/**
 * An {@link Adapters} implementation for Derived Settings
 */
public class DerivedSettingAdapters extends Adapters {

	public DerivedSettingAdapters(
			DelegateUri delegateUri,
			ContextFactory defaultCtxFctry,
			ContextFactory.Registry defaultCtxFctryRegistry,
			EpsilonSettingDelegate.Factory delegateFactory,
			EpsilonSettingDelegate.Factory.Registry delegateRegistry) {
		super(delegateUri, defaultCtxFctry, defaultCtxFctryRegistry);
		this.delegateFactory = delegateFactory;
		this.delegateRegistry = delegateRegistry;
	}

	@Override
	public DelegateResourceSetAdapter getAdapter(ResourceSet resourceSet) {
		DelegateResourceSetAdapter adapter = findAdapter(resourceSet);
		if (adapter == null) {
			adapter = new DelegateResourceSetAdapter();
			// This mimics the extension point registration, so it will be useful when standalone
			((SettingUri) this.delegateURI).register(
					this.delegateRegistry,
					this.delegateFactory);
			adapter.putRegistry(EpsilonSettingDelegate.Factory.Registry.class, this.delegateRegistry);
			resourceSet.eAdapters().add(adapter);
		}
		return adapter;
	}
	
	private final EpsilonSettingDelegate.Factory.Registry delegateRegistry;
	private final EpsilonSettingDelegate.Factory delegateFactory;

}
