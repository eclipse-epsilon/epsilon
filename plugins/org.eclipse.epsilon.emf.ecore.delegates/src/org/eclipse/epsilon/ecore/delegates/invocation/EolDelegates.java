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
import org.eclipse.emf.ecore.EOperation.Internal.InvocationDelegate.Factory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.ecore.delegates.AbstractDelegates;
import org.eclipse.epsilon.ecore.delegates.Delegates;
import org.eclipse.epsilon.ecore.delegates.EolDelegateContext;
import org.eclipse.epsilon.ecore.delegates.notify.Adapters;


/**
 * Handle {@link EolDelegate} creation
 * 
 * @since 2.5
 */
public class EolDelegates 
		extends AbstractDelegates<EOperation, InvocationDelegate, InvocationDelegate.Factory> 	
		implements Delegates<EOperation, InvocationDelegate> {

	public EolDelegates(InvocationUri delegateUri, Adapters adapters) {
		super(delegateUri, adapters);
	}
	
	@Override
	protected EPackage getEPackage(EOperation eOperation) {
		return eOperation.getEContainingClass().getEPackage();
	}


	@Override
	public InvocationDelegate create(EOperation eOperation) {
		Factory factory = getFactory(eOperation);
		if (factory != null) {
			return factory.createInvocationDelegate(eOperation);
		}
		return null;
	}

	@Override
	public InvocationDelegate create(String uri, EOperation eOperation) {
		Factory factory = this.getFactory(uri, eOperation);
		if (factory != null) {
			return factory.createInvocationDelegate(eOperation);
		}
		return null;
	}

	@Override
	protected Factory getFactory(String delegateURI, EOperation eOperation) {
		return new Factory() {

			@Override
			public InvocationDelegate createInvocationDelegate(EOperation operation) {
				return new OperationEolDelegate(
						eOperation,
						delegateContext(getEPackage(eOperation)));
			}
		};
	}
	
	private EolDelegateContext delegateContext(EPackage ePackage) {
		return (EolDelegateContext) this.delegateUri.context(this.adapters.getAdapter(ePackage));
	}
}
