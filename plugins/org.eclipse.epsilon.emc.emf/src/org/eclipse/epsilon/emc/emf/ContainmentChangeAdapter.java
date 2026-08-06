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

import java.util.Collection;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;

public class ContainmentChangeAdapter implements Adapter {
	
	protected EObject instance;
	protected Resource resource;
	
	public ContainmentChangeAdapter(EObject instance, Resource resource) {
		this.instance = instance;
		this.resource = resource;
	}
	
	@Override
	public Notifier getTarget() {
		return null;
	}

	@Override
	public boolean isAdapterForType(Object arg0) {
		return false;
	}

	@Override
	public void notifyChanged(Notification notification) {
		
		if (!(notification instanceof ENotificationImpl)) return;
		
		ENotificationImpl notificationImpl = (ENotificationImpl) notification;
		
		if (notificationImpl.getFeature() instanceof EReference) {
			EReference reference = (EReference) notification.getFeature();
			Collection<EObject> resContents = resource.getContents();
			
			if (reference.isContainer()) {
				if (instance.eContainer() != null && resource != null && resContents.contains(instance)) {
					resContents.remove(instance);
				}
			}
			else if (reference.isContainment()) {
				Object value = instance.eGet(reference);
				if (value instanceof EObject) {
					EObject eObject = (EObject) value;
					if (resource!= null && resContents.contains(eObject)) {
						resContents.remove(eObject);
					}
				}
				else if (value instanceof Collection) {
					for (Object next : (Collection<?>) value) {
						if (next instanceof EObject) {
							EObject eObject = (EObject) next;
							if (resource != null && resContents.contains(eObject)) {
								resContents.remove(eObject);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void setTarget(Notifier arg0) {
		
	}
	
}

