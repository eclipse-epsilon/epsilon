/********************************************************************************
 * Copyright (c) 2025 University of York
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garcia-Dominguez - initial API and implementation
 ********************************************************************************/
package org.eclipse.epsilon.rdf.emf;


import java.util.List;

import org.apache.jena.rdf.model.Resource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentAdapter;

public class RDFGraphResourceNotificationAdapterChangeRDF extends EContentAdapter implements IDisableable {

	private boolean isDisabled = false;
	private final RDFGraphResourceImpl initialRDFGraphResource;
	
	public RDFGraphResourceNotificationAdapterChangeRDF(RDFGraphResourceImpl rdfGraphResource) {
		this.initialRDFGraphResource = rdfGraphResource;
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	private RDFGraphResourceImpl getGraphResourceForEObject(EObject eObject) {
		RDFGraphResourceImpl graphResource = (RDFGraphResourceImpl) eObject.eResource();
		if (null == graphResource) {
			graphResource = initialRDFGraphResource;
		}
		return graphResource;
	}

	private List<Resource> getNamedModelsToUpdate(EObject eObject, RDFGraphResourceImpl graphResource) {
		List<Resource> namedModelResources = graphResource.getResourcesForNamedModelsContaining(eObject);
		if (!namedModelResources.isEmpty()) {
			return namedModelResources;
		} else {
			// No named RDF models contain the object yet - fall back on the first one
			namedModelResources = graphResource.getResourcesForAllNamedModels();
			if (!namedModelResources.isEmpty()) {
				Resource first = namedModelResources.get(0);
				namedModelResources.clear();
				namedModelResources.add(first);
				return namedModelResources;
			} else {
				// This is a problem...
				System.err.println("no model resource?");
				return null;
			}
		}
	}

	@Override
	public void notifyChanged(Notification notification) {
		if (!isDisabled) {
			Object feature = notification.getFeature();
			if (null != feature) {
				featureNotification(feature, notification);
			} else {
				// Check for adding and removing of an EObject to the resource (model root)
				handleNonFeatureNotification(notification.getNewValue(), notification);
				handleNonFeatureNotification(notification.getOldValue(), notification);
			}
		}
		super.notifyChanged(notification);
	}
	
	private void handleNonFeatureNotification(Object value, Notification notification) {
		if (value instanceof EObject eObjectValue) {
			handleEObjectAttachOrDetach(notification, eObjectValue);
		}
		if (value instanceof List eObjectList) {
			for (Object o : eObjectList) {
				if (o instanceof EObject eob) {
					handleEObjectAttachOrDetach(notification, eob);
				}
			}
		}
	}

	protected void handleEObjectAttachOrDetach(Notification notification, EObject eObjectValue) {
		RDFGraphResourceImpl graphResource = getGraphResourceForEObject(eObjectValue);

		switch (notification.getEventType()) {
		case Notification.ADD:
		case Notification.ADD_MANY: {
			graphResource.ensureGraphExists();
			RDFGraphResourceUpdate rdfUpdater = graphResource.getRDFGraphUpdater();
			for (Resource namedModelResource : getNamedModelsToUpdate(eObjectValue, graphResource)) {
				rdfUpdater.addToResource(eObjectValue, graphResource.getNamedModel(namedModelResource));
			}
			break;
		}
		case Notification.REMOVE:
		case Notification.REMOVE_MANY: {
			graphResource.ensureGraphExists();
			RDFGraphResourceUpdate rdfUpdater = graphResource.getRDFGraphUpdater();
			for (Resource namedModelResource : getNamedModelsToUpdate(eObjectValue, graphResource)) {
				rdfUpdater.removeFromResource(eObjectValue, graphResource.getNamedModel(namedModelResource));
			}
			break;
		}
		}
	}

	private void featureNotification (Object feature, Notification notification){		
		if (feature instanceof EStructuralFeature) {
			eStructuralFeatureNotification((EStructuralFeature) feature, notification);
		} else {
			System.err.printf("unhandled additive change : %s\n", feature.getClass().getName());
		}
	}

	private void eStructuralFeatureNotification(EStructuralFeature eStructuralFeature, Notification notification) {		
		EObject onEObject = (EObject) notification.getNotifier(); 	// RDF node
		EStructuralFeature changedFeature = eStructuralFeature; 	// RDF property
		// eAttribute's values are the objects						// RDF object (node/literal)
		Object oldValue = notification.getOldValue();
		Object newValue = notification.getNewValue();
		int position = notification.getPosition();
		
		RDFGraphResourceImpl graphResource = (RDFGraphResourceImpl) onEObject.eResource();
		if (null == graphResource) {
			System.err.println("The Graph resource has been removed, using the initial graph resource instead");
			graphResource = initialRDFGraphResource;
		}
		
		RDFGraphResourceUpdate rdfUpdater = graphResource.getRDFGraphUpdater();
		List<Resource> namedModelURIs = graphResource.getResourcesForNamedModelsContaining(onEObject);

		// Decode the notification event type
		switch (notification.getEventType()) {
		case Notification.ADD_MANY:
		case Notification.ADD:
			// Watch out for none-multi value attributes being added through here
			rdfUpdater.addMultiValueEStructuralFeature(namedModelURIs, onEObject, changedFeature, 
					newValue, oldValue, position);
			break;
		case Notification.SET:
			// Single values, don't need to worry about order
			if (null == oldValue) {
				// Create new statement
				if (null == newValue) {
					// No old value and no new value - nothing to do
				} else {
					// Create new statement for value					
					namedModelURIs = graphResource.getResourcesForNamedModelsContaining(onEObject);
					if (namedModelURIs.isEmpty()) {
						// No named RDF models contain the object yet - fall back on the first one
						namedModelURIs = graphResource.getResourcesForAllNamedModels();
						if (!namedModelURIs.isEmpty()) {
							Resource first = namedModelURIs.get(0);
							namedModelURIs.clear();
							namedModelURIs.add(first);
						}
					}
					rdfUpdater.addSingleValueEStructuralFeatureStatements(namedModelURIs, onEObject,
						changedFeature, newValue);
				}
			} else {
				// Update existing statement
				if (null == newValue) {
					// Update existing statement to null
					namedModelURIs = graphResource.getResourcesForNamedModelsContaining(onEObject);
					rdfUpdater.removeSingleValueEStructuralFeatureStatements(namedModelURIs, onEObject,
							changedFeature, notification.getOldValue());
				} else {
					// Update existing statement value
					namedModelURIs = graphResource.getResourcesForNamedModelsContaining(onEObject);
					rdfUpdater.updateSingleValueEStructuralFeatureStatements(namedModelURIs, onEObject,
							changedFeature, newValue, oldValue);
				}
			}
			break;
		case Notification.REMOVE_MANY:
		case Notification.REMOVE:
			rdfUpdater.removeMultiEStructuralFeature(namedModelURIs, onEObject, changedFeature, oldValue);
			break;
		case Notification.UNSET:
			// Single values, don't need to worry about order
			namedModelURIs = graphResource.getResourcesForNamedModelsContaining(onEObject);
			rdfUpdater.removeSingleValueEStructuralFeatureStatements(namedModelURIs, 
					onEObject, changedFeature, notification.getOldValue());
			break;
		default:
			break;
		}
	}
	
}

	