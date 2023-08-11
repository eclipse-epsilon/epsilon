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

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.ecore.delegates.DelegateContext;

/**
 * DelegateEPackageAdapter extends an EPackage to cache its DelegateDomain
 * that supervises installation of EVL annotations in ECore metamodel.
 * 
 * @since 2.5
 */
public class DelegateEPackageAdapter extends AdapterImpl {
	
	public DelegateEPackageAdapter(DelegateContext delegateDomain) {
		super();
		this.delegateDomain = delegateDomain;
	}

	@Override
	public EPackage getTarget() {
		return (EPackage) super.getTarget();
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == DelegateEPackageAdapter.class;
	}

	@Override
	public void setTarget(Notifier newTarget) {
		EPackage resourceSet = (EPackage) newTarget;
		super.setTarget(resourceSet);
	}

	/**
	 * Return the DelegateDomain for this package and for delegateURI, returning null it does not exist. 
	 */
	public DelegateContext getDelegateDomain() {
		return delegateDomain;
	}


	public void unloadDelegates() {
		this.delegateDomain.dispose();
		EPackage ePackage = getTarget();
		if (ePackage != null) {
			ePackage.eAdapters().remove(this);
		}
	}

	private final DelegateContext delegateDomain;

}