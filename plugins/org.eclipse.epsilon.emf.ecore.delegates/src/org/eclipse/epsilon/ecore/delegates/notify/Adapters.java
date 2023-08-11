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
import org.eclipse.epsilon.ecore.delegates.DelegateUri;

/**
 * Manage adapters used by the Epsilon ECore delegates
 * 
 * @since 2.5
 */
public abstract class Adapters {

	public Adapters(
		DelegateUri delegateUri,
		ContextFactory defaultCtxFctry, 
		ContextFactory.Registry defaultCtxFctryRegistry) {
		super();
		this.delegateURI = delegateUri;
		this.defaultCtxFctry = defaultCtxFctry;
		this.defaultCtxFctryRegistry = defaultCtxFctryRegistry;
		this.dlgtRsrcAdptr = new DelegateResourceAdapter(this);
	}
	
	public abstract DelegateResourceSetAdapter getAdapter(ResourceSet resourceSet);

	/**
	 *	Return the DelegateEPackageAdapter for ePackage, creating
	 *	one if necessary.
	 */
	public DelegateEPackageAdapter getAdapter(EPackage ePackage) {
		DelegateEPackageAdapter adapter = findAdapter(ePackage);
		if (adapter == null) {
			adapter = new DelegateEPackageAdapter();
			ePackage.eAdapters().add(adapter);
		}
		this.delegateURI.addContext(adapter, () -> this.createContext(ePackage));
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
	
	public boolean hasAdapter(EModelElement eObject) {
		return this.delegateURI.isUsedBy(eObject);
	}
	

	protected final DelegateUri delegateURI;
	
	protected DelegateResourceSetAdapter findAdapter(ResourceSet resourceSet) {
		return (DelegateResourceSetAdapter) EcoreUtil.getAdapter(resourceSet.eAdapters(), DelegateResourceSetAdapter.class);
	}
	
	DelegateEPackageAdapter findAdapter(EPackage ePackage) {
		return (DelegateEPackageAdapter) EcoreUtil.getAdapter(ePackage.eAdapters(), DelegateEPackageAdapter.class);
	}

	private final ContextFactory defaultCtxFctry;
	private final DelegateResourceAdapter dlgtRsrcAdptr;
	private final ContextFactory.Registry defaultCtxFctryRegistry;
	
	
	private DelegateContext createContext(EPackage ePackage) {
		DelegateContext.ContextFactory.Registry registry = this.getRegistry(
				ePackage,
				DelegateContext.ContextFactory.Registry.class,
				this.defaultCtxFctryRegistry);
		DelegateContext.ContextFactory factory = this.delegateURI.factory(registry);
		if (factory == null) {
			factory = this.defaultCtxFctry;
		}
		return factory.create(this.delegateURI, ePackage, this.dlgtRsrcAdptr);
	}
	
	
}
