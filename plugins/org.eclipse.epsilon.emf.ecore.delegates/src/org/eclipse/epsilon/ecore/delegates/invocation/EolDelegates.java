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
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.ecore.delegates.AbstractDelegates;
import org.eclipse.epsilon.ecore.delegates.Delegates;
import org.eclipse.epsilon.ecore.delegates.EolDelegateContext;
import org.eclipse.epsilon.ecore.delegates.invocation.EpsilonInvocationDelegate.Factory;
import org.eclipse.epsilon.ecore.delegates.notify.Adapters;


/**
 * Handle {@link EolDelegate} creation
 * 
 * @since 2.5
 */
public class EolDelegates 
		extends AbstractDelegates<EOperation, EpsilonInvocationDelegate, EpsilonInvocationDelegate.Factory> 	
		implements Delegates<EOperation, EpsilonInvocationDelegate> {

	public EolDelegates(InvocationUri delegateUri, Adapters adapters) {
		super(delegateUri, adapters);
		this.factory = new Smart();
	}
	
	@Override
	protected EPackage getEPackage(EOperation eOperation) {
		return eOperation.getEContainingClass().getEPackage();
	}


	@Override
	public EpsilonInvocationDelegate create(EOperation eOperation) {
		Factory factory = getFactory(eOperation);
		if (factory != null) {
			return factory.createInvocationDelegate(eOperation);
		}
		return null;
	}

	@Override
	public EpsilonInvocationDelegate create(String uri, EOperation eOperation) {
		Factory factory = this.getFactory(uri, eOperation);
		if (factory != null) {
			return factory.createInvocationDelegate(eOperation);
		}
		return null;
	}

	@Override
	protected Factory getFactory(String delegateURI, EOperation eOperation) {
		if (this.delegateUri.is(delegateURI)) {
			return this.factory;
		}
		return null;
	}
	
	private class Smart implements Factory {

		@Override
		public EpsilonInvocationDelegate createInvocationDelegate(EOperation operation) {
			return new EolInvocationDelegate(
					operation,
					delegateContext(getEPackage(operation)));
		}
	};
	
	private final Factory factory;
	
	private EolDelegateContext delegateContext(EPackage ePackage) {
		return (EolDelegateContext) this.delegateUri.context(this.adapters.getAdapter(ePackage));
	}
}
