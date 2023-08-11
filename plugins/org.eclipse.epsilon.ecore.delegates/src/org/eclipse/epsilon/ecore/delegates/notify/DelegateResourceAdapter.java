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

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * DelegateResourceAdapter extends a Resource to load and unload DelegateDomains
 * for each package with a delegate annotation, when the resource is loaded and
 * unloaded.
 * 
 * @since 2.5
 */
public class DelegateResourceAdapter extends AdapterImpl {

	public DelegateResourceAdapter(Adapters adapters) {
		super();
		this.adapters = adapters;
	}

	@Override
	public Resource getTarget() {
		return (Resource) super.getTarget();
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == DelegateResourceAdapter.class;
	}

	@Override
	public void notifyChanged(Notification notification) {
		int featureID = notification.getFeatureID(Resource.class);
		int eventType = notification.getEventType();
		if (featureID == Resource.RESOURCE__IS_LOADED) {
			if (eventType == Notification.SET) {
				Resource resource = getTarget();
				EList<EObject> contents = resource.getContents();
				if (notification.getNewBooleanValue()) {
					EPackage.Registry packageRegistry = resource.getResourceSet().getPackageRegistry();
					installPackages(packageRegistry, contents);
				}
			}
		} else if (featureID == Resource.RESOURCE__CONTENTS) {
			if (eventType == Notification.REMOVE) {
				unloadDelegate((EObject) notification.getOldValue());
			} else if (eventType == Notification.REMOVE_MANY) {
				@SuppressWarnings("unchecked")
				List<? extends EObject> oldValue = (List<? extends EObject>) notification.getOldValue();
				unloadDelegates(oldValue);
			}
		}
	}
	
	@Override
	public void setTarget(Notifier newTarget) {
		Resource resource = (Resource) newTarget;
		super.setTarget(resource);
	}
	
	public void adapt(Resource resource) {
		DelegateResourceAdapter adapter = (DelegateResourceAdapter) EcoreUtil.getAdapter(resource.eAdapters(),
				DelegateResourceAdapter.class);
		if (adapter == null) {
			resource.eAdapters().add(this);
		}
	}
	
	protected void installPackages(EPackage.Registry packageRegistry, List<? extends EObject> contents) {
		for (EObject eObject : contents) {
			if (eObject instanceof EPackage) {
				EPackage ePackage = (EPackage) eObject;
				packageRegistry.put(ePackage.getNsURI(), ePackage);
				installPackages(packageRegistry, ePackage.getESubpackages());
			}
		}
	}

	/**
	 * @since 3.2
	 */
	protected void unloadDelegate(EObject eObject) {
		if (eObject instanceof EPackage) {
			EPackage ePackage = (EPackage) eObject;
			DelegateEPackageAdapter adapter = this.adapters.findAdapter(ePackage);
			if (adapter != null) {
				adapter.unloadDelegates();
			}
			unloadDelegates(ePackage.getESubpackages());
		}
		Resource resource = getTarget();
		if (resource != null) {
			resource.eAdapters().remove(this);
		}
	}

	protected void unloadDelegates(List<? extends EObject> contents) {
		for (EObject eObject : contents) {
			unloadDelegate(eObject);
		}
	}
	
	private final Adapters adapters;
}