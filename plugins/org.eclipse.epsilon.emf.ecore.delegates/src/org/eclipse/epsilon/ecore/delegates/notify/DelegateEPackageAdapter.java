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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.ecore.delegates.DelegateContext;

/**
 * DelegateEPackageAdapter extends an EPackage to cache its DelegateDomain
 * that supervises installation of Epsilon annotations in ECore metamodel.
 * 
 * @since 2.5
 */
public class DelegateEPackageAdapter extends AdapterImpl {
	
	public DelegateEPackageAdapter() {
		this(new HashMap<>());
	}
	
	public DelegateEPackageAdapter(Map<String, DelegateContext> contexts) {
		super();
		this.contexts = contexts;
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

	public DelegateContext delegateContext(String uri) {
		return this.contexts.get(uri);
	}
	
	public void addDelegate(String uri, Function<String, DelegateContext> factory) {
		this.contexts.computeIfAbsent(uri, factory);
	}
	
	
	public void unloadDelegates() {
		if (contexts != null) {
			this.contexts.values().forEach(DelegateContext::dispose);			
		}
		EPackage ePackage = getTarget();
		if (ePackage != null) {
			ePackage.eAdapters().remove(this);
		}
	}

	private final Map<String, DelegateContext> contexts;

}