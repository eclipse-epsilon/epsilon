/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates.invocation;

import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EOperation.Internal.InvocationDelegate;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.ecore.delegates.DelegateContext.ContextFactory;
import org.eclipse.epsilon.ecore.delegates.EolDelegateContextFactory;
import org.eclipse.epsilon.ecore.delegates.notify.Adapters;
import org.eclipse.epsilon.ecore.delegates.notify.EolAdapters;
import org.eclipse.epsilon.ecore.delegates.notify.EpsilonDelegatesAdapter;

/**
 * The delegate factory for invocation delegates
 * 
 * @since 2.5
 */
public class EolDelegateFactory implements EOperation.Internal.InvocationDelegate.Factory {

	public EolDelegateFactory() {
		this(
				new InvocationUri(), 
				new ContextFactory.Registry.Fast(), 
				EOperation.Internal.InvocationDelegate.Factory.Registry.INSTANCE);
	}
			
	public EolDelegateFactory(
			InvocationUri delegateUri,
			ContextFactory.Registry domainRegistry,
			InvocationDelegate.Factory.Registry delegateRegistry) {
			super();
			this.delegateUri = delegateUri;
			this.adapters = new EolAdapters(
					delegateUri, 
					new EolDelegateContextFactory(),
					domainRegistry,
					this,
					delegateRegistry);
			this.delegates =  new EolDelegates(this.delegateUri, this.adapters);
			
		}

	@Override
	public InvocationDelegate createInvocationDelegate(EOperation operation) {
		return invocationDelegate(operation);
	}

	private final InvocationUri delegateUri;
	private final Adapters adapters;
	private final EolDelegates delegates;

	private InvocationDelegate invocationDelegate(EOperation operation) {
		return this.findAdapter(operation).invocationDelegate();
	}
	
	private EpsilonDelegatesAdapter findAdapter(EOperation operation) {
		EpsilonDelegatesAdapter adapter = (EpsilonDelegatesAdapter) EcoreUtil
			.getAdapter(operation.eAdapters(), EpsilonDelegatesAdapter.class);
		if (adapter == null) {
			adapter = new EpsilonDelegatesAdapter();
			operation.eAdapters().add(adapter);
		}
		if (!adapter.hasEolDelegate()) {
			adapter.useEolDelegate(this.delegates.create(operation));
		}
		return adapter;
	}
}
