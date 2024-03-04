/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
/*******************************************************************************
 * Copyright (c) 2010, 2018 Willink Transformations and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors: 
 *   E.D.Willink - Initial API and implementation
 *******************************************************************************/
package org.eclipse.epsilon.ecore.delegates.notify;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * DelegateResourceSetAdapter extends a ResourceSet to support a registry of local
 * registries.
 * 
 * @since 2.5
 */
public class DelegateResourceSetAdapter extends AdapterImpl
{	
	
	public DelegateResourceSetAdapter() {
		super();
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return (type instanceof Class<?>) && ((Class<?>)type).isAssignableFrom(getClass());
	}
	
	@Override
	public void setTarget(Notifier newTarget) {
		ResourceSet resourceSet = (ResourceSet)newTarget;
		super.setTarget(resourceSet);
	}
	
	public <T> T getRegistry(Class<T> registryClass) {
		@SuppressWarnings("unchecked")
		T registry = (T) registryRegistry.get(registryClass);
		return registry;
	}


	public <T> T putRegistry(Class<T> registryClass, T newRegistry) {
		@SuppressWarnings("unchecked")
		T oldRegistry = (T) registryRegistry.put(registryClass, newRegistry);
		return oldRegistry;
	}

	private Map<Class<? extends Object>, Object> registryRegistry = new HashMap<Class<? extends Object>, Object>();
}