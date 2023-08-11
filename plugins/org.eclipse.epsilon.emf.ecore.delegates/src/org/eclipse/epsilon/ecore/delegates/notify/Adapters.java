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

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.ecore.delegates.DelegateContext;
import org.eclipse.epsilon.ecore.delegates.DelegateContext.ContextFactory;
import org.eclipse.epsilon.ecore.delegates.validation.EvlDelegateFactory;
import org.eclipse.epsilon.ecore.delegates.validation.EvlDelegate;
import org.eclipse.epsilon.ecore.delegates.validation.EvlDelegate.Factory;

/**
 * Manage adapters used by the Epsilon ECore delegates
 * 
 * @since 2.5
 */
public class Adapters {
	
	public Adapters(
		String delegateURI,
		ContextFactory defaultFactory, 
		ContextFactory.Registry defaultRegistry,
		Factory.Registry delegateRegistry) {
		super();
		this.delegateURI = delegateURI;
		this.defaultFactory = defaultFactory;
		this.dlgtRsrcAdptr = new DelegateResourceAdapter(this);
		this.defaultRegistry = defaultRegistry;
		this.delegateRegistry = delegateRegistry;
	}
	
	public DelegateResourceSetAdapter getAdapter(ResourceSet resourceSet) {
		DelegateResourceSetAdapter adapter = findAdapter(resourceSet);
		if (adapter == null) {
			adapter = new DelegateResourceSetAdapter();
			this.delegateRegistry.put(this.delegateURI, new EvlDelegateFactory(this.delegateURI, this.defaultRegistry, this.delegateRegistry));
			adapter.putRegistry(EvlDelegate.Factory.Registry.class, this.delegateRegistry);
			// TODO Add invocation and setting delegates
			resourceSet.eAdapters().add(adapter);
		}
		return adapter;
	}

	/**
	 *	Return the DelegateEPackageAdapter for ePackage, creating
	 *	one if necessary.
	 */
	public DelegateEPackageAdapter getAdapter(EPackage ePackage) {
		DelegateEPackageAdapter adapter = findAdapter(ePackage);
		if (adapter == null) {
			adapter = new DelegateEPackageAdapter(createDomain(ePackage));
			ePackage.eAdapters().add(adapter);
		}
		return adapter;
	}
	
	public DelegateResourceSetAdapter getAdapter(EModelElement modelElement) {
		Resource resource = modelElement.eResource();
		if (resource == null) {
			return null;
		}
		ResourceSet resourceSet = resource.getResourceSet();
		if (resourceSet == null) {
			return null;
		}
		return this.getAdapter(resourceSet);
	}
	
	public <T> T getRegistry(EModelElement modelElement, Class<T> registryClass, T defaultRegistry) {
		Resource resource = modelElement.eResource();
		if (resource == null) {
			return defaultRegistry;
		}
		ResourceSet resourceSet = resource.getResourceSet();
		if (resourceSet == null) {
			return defaultRegistry;
		}
		DelegateResourceSetAdapter adapter = this.getAdapter(resourceSet);
		T registry = null;
		if (adapter != null) {
			registry = adapter.getRegistry(registryClass);
		}
		return registry != null ? registry : defaultRegistry;
	}
	
	
	/**
	 *	Return the DelegateEPackageAdapter for ePackage, if there is one, or null if none.
	 *
	 * @since 3.1
	 */
	DelegateEPackageAdapter findAdapter(EPackage ePackage) {
		return (DelegateEPackageAdapter) EcoreUtil.getAdapter(ePackage.eAdapters(), DelegateEPackageAdapter.class);
	}
	
	private DelegateResourceSetAdapter findAdapter(ResourceSet resourceSet) {
		return (DelegateResourceSetAdapter) EcoreUtil.getAdapter(resourceSet.eAdapters(), DelegateResourceSetAdapter.class);
	}

	private final String delegateURI;
	private final ContextFactory defaultFactory;
	private final DelegateResourceAdapter dlgtRsrcAdptr;
	private final ContextFactory.Registry defaultRegistry;
	private final Factory.Registry delegateRegistry;
	
	private DelegateContext createDomain(EPackage ePackage) {
		DelegateContext.ContextFactory.Registry registry = this.getRegistry(
				ePackage,
				DelegateContext.ContextFactory.Registry.class,
				this.defaultRegistry);
		DelegateContext.ContextFactory factory = registry.getFactory(this.delegateURI);
		if (factory == null) {
			factory = this.defaultFactory;
		}
		return factory.createDelegateDomain(this.delegateURI, ePackage, this.dlgtRsrcAdptr);
	}
}
