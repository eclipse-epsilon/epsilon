/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

public class InMemoryEmfModel extends EmfModel {
	
	public InMemoryEmfModel(String name, Resource modelImpl, EPackage... ePackages) {
		init(name, modelImpl, Arrays.asList(ePackages), true);
	}
	
	public InMemoryEmfModel(String name, Resource modelImpl, String... nsUris) {
		Collection<EPackage> ePackages = new ArrayList<>();
		for (String nsUri : nsUris) {
			ePackages.add(EPackage.Registry.INSTANCE.getEPackage(nsUri));
		}
		init(name, modelImpl, ePackages, true);
	}

	public InMemoryEmfModel(Resource modelImpl) {
		this(modelImpl, true);
	}

	public InMemoryEmfModel(Resource modelImpl, boolean isContainerListenerEnabled) {
		init("Model", modelImpl, Collections.<EPackage> emptyList(), isContainerListenerEnabled);
	}

	public InMemoryEmfModel(String name, Resource modelImpl) {
		this(name, modelImpl, Collections.<EPackage> emptyList(), true);
	}
	
	public InMemoryEmfModel(String name, Resource modelImpl, boolean isContainerListenerEnabled) {
		init(name, modelImpl, Collections.<EPackage> emptyList(), isContainerListenerEnabled);
	}
	
	public InMemoryEmfModel(String name, Resource modelImpl, Collection<EPackage> ePackages) {
		this(name, modelImpl, ePackages, true);
	}
	
	public InMemoryEmfModel(String name, Resource modelImpl, Collection<EPackage> ePackages, boolean isContainerListenerEnabled) {
		init(name, modelImpl, ePackages, isContainerListenerEnabled);
	}
	
	public InMemoryEmfModel(String name, Resource modelImpl, Collection<EPackage> ePackages, boolean isContainerListenerEnabled, boolean expand) {
		setExpand(expand);
		init(name, modelImpl, ePackages, isContainerListenerEnabled);
	}
	
	protected void init(String name, Resource modelImpl, Collection<EPackage> ePackages, boolean isContainerListenerEnabled) {	
		setName(name);
		this.modelImpl = modelImpl;
		
		// If some packages are provided, create a registry and put them in it
		if (!(ePackages == null || ePackages.isEmpty())) {
			this.registry = new EPackageRegistryImpl();
			for (EPackage ePackage : ePackages) {
				registry.put(ePackage.getNsURI(), ePackage);
				List<EPackage> dependencies =  new ArrayList<>();
				EmfUtil.collectDependencies(ePackage, dependencies);
				for (EPackage dependency : dependencies) {
					registry.put(dependency.getNsURI(), dependency);	
				}
			}
		}
		else {
			// If no packages are provided and registry is null, 
			// fall back to the global package registry
			if (this.registry == null && modelImpl != null && modelImpl.getResourceSet() != null && modelImpl.getResourceSet().getPackageRegistry().isEmpty()) {
				this.registry = EPackage.Registry.INSTANCE;
			}
		}
		
//		// If there is no ResourceSet we cannot register or call the resource creation factory
//		// @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=540424
//		final ResourceSet resourceSet = modelImpl.getResourceSet();
//		if (resourceSet != null) {
//			Resource.Factory.Registry rfReg = resourceSet.getResourceFactoryRegistry();
//			if (rfReg == null) {
//				resourceSet.setResourceFactoryRegistry(Resource.Factory.Registry.INSTANCE);
//			}
//		}
//
//		if (ePackages == null || ePackages.isEmpty()) {
//			
//			registry = EPackage.Registry.INSTANCE;
//			
//			// No additional packages are provided, so if the package registry of 
//			// the local resource registry is empty, use the global registry instead
//			
//			// If there is no ResourceSet available, AbstractEmfModel#getPackageRegistry()
//			// already returns the global registry, so no need to worry about this
//			//if (resourceSet != null && resourceSet.getPackageRegistry().isEmpty()) {
//			//	resourceSet.setPackageRegistry(EPackage.Registry.INSTANCE);
//			//}
//		}
//		else {
//			final EPackage.Registry epReg = getResource().getResourceSet().getPackageRegistry();
//			for (EPackage ePackage : ePackages) {
//				epReg.put(ePackage.getNsURI(), ePackage);
//				
//				//Added : Collect dependencies
//				
//				List<EPackage> dependencies =  new ArrayList<>();
//				EmfUtil.collectDependencies(ePackage, dependencies);
//				for (EPackage dependency : dependencies) {
//					epReg.put(dependency.getNsURI(), dependency);	
//				}
//			}
//		}

		// Since 1.6, having CachedContentsAdapter implies cached=true, otherwise it's inconsistent.
		setCachingEnabled(true);

		if (isContainerListenerEnabled) {
			this.setupContainmentChangeListeners();
		}
	}

	@Override
	public void loadModel() {
		// In-memory models should not be loaded
	}
}
