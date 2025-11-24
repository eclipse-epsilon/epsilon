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
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;

// Try to keep the logic flow in here the same as the RDFGraphResourceNotificationAdapterChangeRDF
public class RDFGraphResourceNotificationAdapterTrace extends EContentAdapter implements IDisableable {

	private StringBuilder processTrace;
	private boolean isDisabled = false;
	private final RDFGraphResourceImpl initialRDFGraphResource;
	
	public RDFGraphResourceNotificationAdapterTrace(RDFGraphResourceImpl rdfGraphResource) {
		this.initialRDFGraphResource = rdfGraphResource;
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	@Override
	public void notifyChanged(Notification notification) {
		if (isDisabled) {
			super.notifyChanged(notification);
			return;
		}

		// Decode the notification
		processTrace = new StringBuilder();
		processTrace.append(String.format("\n[ Notification ]"));

		Object feature = notification.getFeature();
		if (null != feature) {
			// Work out the change based on feature
			featureNotification(feature, notification);
		} else {
			// Notification is not for a feature
			processTrace.append(String.format("\n - Not a Feature notification \n - %s ", notification));
			processTrace.append(String.format("Event: %s", eventTypeToString(notification.getEventType())));

			if (null != notification.getNewValue()) {
				processTrace.append(String.format("NewValue: %s", notification.getNewValue()));
				if (notification.getNewValue() instanceof EObject && notification.getEventType() == Notification.ADD) {
					processTrace.append("\nEObject ADDED to Model");
					processTrace.append(
							String.format("EObject: %s#%s", ((EObject) notification.getNewValue()).eClass().getName(),
									((EObject) notification.getNewValue()).hashCode()));
				}
				if (notification.getNewValue() instanceof List el && notification.getEventType() == Notification.ADD_MANY) {
					processTrace.append("\nEObjects ADDED to Model:");
					for (Object o : el) {
						processTrace.append(
							String.format("\n - EObject: %s#%s", ((EObject) o).eClass().getName(),
									((EObject) o).hashCode()));
					}
				}
			}

			if (null != notification.getOldValue()) {
				processTrace.append(String.format("OldValue: %s", notification.getOldValue()));
				if (notification.getOldValue() instanceof EObject
						&& notification.getEventType() == Notification.REMOVE) {
					processTrace.append(String.format("\nEObject REMOVED from Model "));
					processTrace.append(
							String.format("EObject: %s#%s", ((EObject) notification.getOldValue()).eClass().getName(),
									((EObject) notification.getOldValue()).hashCode()));
				}
				if (notification.getNewValue() instanceof List el && notification.getEventType() == Notification.REMOVE_MANY) {
					processTrace.append("\nEObjects REMOVED from Model:");
					for (Object o : el) {
						processTrace.append(
							String.format("\n - EObject: %s#%s", ((EObject) o).eClass().getName(),
									((EObject) o).hashCode()));
					}
				}
			}
		}

		System.out.println(processTrace + "\n\n");
		super.notifyChanged(notification);
	}

	private void featureNotification (Object feature, Notification notification){		
		Class<? extends Object> featureClass = feature.getClass();

		if (feature instanceof EAttribute) {
			eStructuralFeatureNotification((EAttribute) feature, notification);
			return;
		}

		if (feature instanceof EReference) {
			eStructuralFeatureNotification((EReference) feature, notification);
			return;
		}

		if (feature instanceof EObject) {
			//eObjectNotification((EObject) feature, notification);
			return;
		}
		
		processTrace.append(String.format(" [!] - Feature class not handled:\n%s", featureClass.getName()));
		return;
	}
	
	private void eStructuralFeatureNotification(EStructuralFeature eStructuralFeature, Notification notification) {		
		EObject onEObject = (EObject) notification.getNotifier(); 	// RDF node
		EStructuralFeature changedFeature = eStructuralFeature; 	// RDF property
		// eAttribute's is value 									// RDF object (node/literal)
	
		Object oldValue = notification.getOldValue();
		Object newValue = notification.getNewValue();
		
		RDFGraphResourceImpl graphResource = (RDFGraphResourceImpl) onEObject.eResource();
		if(null == graphResource) {
			processTrace.append("  Adapter firing for EObject with no Graph Resource, using initial Graph Resource");
			graphResource = initialRDFGraphResource;
		}

		if (changedFeature instanceof EAttribute) {
			processTrace.append(String.format("\n EAttribute "));
		} else if (changedFeature instanceof EReference) {
			processTrace.append(String.format("\n EReference "));
		} else {
			processTrace.append(String.format("\n This is new?! -- %s ", changedFeature.getClass()));
		}
		
		boolean isOrdered = changedFeature.isOrdered(); // If this is set then there is Order to the values.
		boolean isUnique = changedFeature.isUnique();
		boolean isMany = changedFeature.isMany();
		processTrace.append(String.format( "(ordered is %s, unique is %s, many is %s) - ",
		isOrdered, isUnique, isMany));
		

		// Decode the notification event type
		switch (notification.getEventType()) {
		case Notification.ADD:
			processTrace.append("ADD");
			reportEObjectIdentity(onEObject);
			reportEStructuralFeatureChange(changedFeature, oldValue, newValue);
			break;
		case Notification.ADD_MANY:
			processTrace.append("ADD_MANY");
			if(isMany && null != onEObject) {
				reportEObjectIdentity(onEObject);
				reportEStructuralFeatureChange(changedFeature, oldValue, newValue);
			} else {
				
			}
			break;
		case Notification.SET:
			processTrace.append("SET");
			reportEObjectIdentity(onEObject);
			reportEStructuralFeatureChange(changedFeature, oldValue, newValue);
			if (null == oldValue) {
				// Create new statement
				if (null == newValue) {
					// Create new statement for null value
					processTrace.append(String.format("\n ** Create new statement set to null"));
				} else {
					// Create new statement for value
					processTrace.append(String.format("\n ** Create new statement set to value %s", newValue));
				}
			} else {
				// Update existing statement
				if (null == newValue) {
					// Update existing statement to null
					processTrace.append(String.format("\n ** Update statement set to null"));
				} else {
					// Update existing statement value
					processTrace.append(String.format("\n ** Update statement set to value %s", newValue));
				}
			}
			break;
		case Notification.REMOVE:
			processTrace.append("REMOVE");
			reportEObjectIdentity(onEObject);
			reportEStructuralFeatureChange(changedFeature, oldValue, newValue);
			break;
		case Notification.REMOVE_MANY:
			processTrace.append("REMOVE_MANY");			
			reportEObjectIdentity(onEObject);
			reportEStructuralFeatureChange(changedFeature, oldValue, newValue);
			break;
		case Notification.UNSET:
			processTrace.append("UNSET");
			if(isMany) { processTrace.append(" isMany() true"); } else { processTrace.append(" is Many false"); }
			if(isOrdered) {
				notImplmentedWarning(notification, isOrdered);
			}
			reportEObjectIdentity(onEObject);
			reportEStructuralFeatureChange(changedFeature, oldValue, newValue);
			break;
		default:
			break;
		}
	}
	
	private void notImplmentedWarning (Notification notification, boolean isOrdered) {
		String feature = notification.getFeature().getClass().getSimpleName();
		
		String operation = eventTypeToString(notification.getEventType());

		String order = "";
		if(isOrdered) {
			order = "ordered";
		} else {
			order = "unordered";
		}
		processTrace.append(String.format("\n [!] %s %s (%s) not implmented", feature, operation, order));
	}

	private Resource identifyEObjectsRDFnode(EObject eObject) {
		if (eObject instanceof RDFGraphResourceImpl) {
			RDFGraphResourceImpl rdfGraphResource = (RDFGraphResourceImpl) eObject.eResource();
			Resource rdfNode = rdfGraphResource.getRDFResource(eObject);

			processTrace.append(reportNamedModelsContaining(eObject));
			return rdfNode;
		}
		processTrace.append(String.format(" ! No RDFGraphResourceImpl for : %s ", eObject));
		return null;

	}
	
	private String eventTypeToString(int eventType) {
		// Decode the notification event type
		switch (eventType) {
		case Notification.ADD:
			return "ADD";
		case Notification.ADD_MANY:
			return "ADD_MANY";
		case Notification.SET:
			return "SET";
		case Notification.REMOVE:
			return "REMOVE";
		case Notification.REMOVE_MANY:
			return "REMOVE_MANY";
		case Notification.UNSET:
			return "UNSET";
		default:
			return "N/A";
		}
	}
	
	// REPORTING code

	private void reportEStructuralFeatureChange(EStructuralFeature eStructuralFeature, Object oldValue, Object newValue) {		
		String multi = "";
		if(eStructuralFeature.isMany()) {
			multi = "many";
		} else {
			multi = "not many";
		}
		
		String order = "";
		if (eStructuralFeature.isOrdered()) {
			order = "ordered";
		} else {
			order = "not ordered";
		}
		
		String unique = "";
		if (eStructuralFeature.isUnique()) {
			unique = "unique";
		} else {
			unique = "not unique";
		}
		
		String referenceContainment = "N/A";
		if(eStructuralFeature instanceof EReference) {
			EReference reference = (EReference) eStructuralFeature;
			if (reference.isContainment()) {
				referenceContainment = "containment";
				//reportEObject(eStructuralFeature.getEContainingClass(), true, i);
			} else {
				referenceContainment = "not containment";
			}
		}
		
		if (eStructuralFeature instanceof EAttribute) {
			EAttribute eAttributeChanged = (EAttribute) eStructuralFeature;
			processTrace.append(String.format("\n - EAttribute changed: (%s, %s, %s)\n\t%s - %s : %s -> %s", order, unique, multi,
					eAttributeChanged.getEAttributeType().getName(), eAttributeChanged.getName(), oldValue, newValue));	
			return;
		}
		
		if (eStructuralFeature instanceof EReference) {
			EReference eReferenceChanged = (EReference) eStructuralFeature;
			// If things blow up here, then something other than EObjects can be referenced...
			
			EObject oldValueEob = (EObject) oldValue;
			int oldValueHash = 0;
			if (null != oldValueEob) {
				oldValueHash = oldValueEob.hashCode();
			}

			EObject newValueEob = (EObject) newValue;
			int newValueHash = 0;
			if (null != newValueEob) {
				newValueHash = newValueEob.hashCode();
			}
			
			processTrace.append(String.format(
					"\n - EReference changed: (%s, %s, %s, %s)\n\t%s - %s \n\twas: (#%s) %s\n\tnow: (#%s) %s", order,
					unique, multi, referenceContainment, eReferenceChanged.getEReferenceType().getName(),
					eReferenceChanged.getName(), oldValueHash, getEObjectLocation(oldValueEob), newValueHash,
					getEObjectLocation((newValueEob))));

			if (oldValue instanceof List) {
				System.out.println ("old value is a list here...");
			}
			
			return;
		}
		
	}
	
	private String getEObjectLocation(EObject eobject) {
		if (null != eobject) {
			String frag = EcoreUtil.getURI(eobject).fragment();
			if (frag.equals("")) {
				frag = "'blank'";
			}
			return String.format("%s", frag);
		}
		return "null";
	}
	
	@SuppressWarnings("unchecked")
	private void reportReferences(EObject eObject, EReference reference, int pad) {
		String p = "\n";
		for (int i = 0; i < pad; i++) {
			p = p + "\t";
		}
		final String prefix = p;

		List<EObject> listOfreferences = (List<EObject>) eObject.eGet(reference);
		if (!listOfreferences.isEmpty()) {
			listOfreferences.forEach(r -> {
				processTrace.append(String.format("%s-> {", prefix));
				reportEObject(r, false, pad);
				processTrace.append(String.format("%s   }", prefix));

			});
		} else {
			processTrace.append(String.format("\n * Empty *"));
		}
		return;
	}

	private void reportEObjectIdentity(EObject eObject) {
		processTrace.append(  String.format("\n - EObject : (#%s) %s - %s ",eObject.hashCode() , eObject.eClass().getName(),
				EcoreUtil.getIdentification(eObject)));
	}
	
	@SuppressWarnings("unchecked")
	private void reportEObject(EObject eObject, boolean followReference, int pad) {
		String p = "\n";
		for (int i = 0; i < pad; i++) {
			p = p + "\t";
		}
		final String prefix = p;

		reportEObjectRDFnode(eObject, pad);
		processTrace.append(String.format("%s - EObject : %s  %s ", prefix, eObject.eClass().getName(),
				EcoreUtil.getIdentification(eObject)));

		processTrace.append(String.format("%s   * All EAttributes: ", prefix));

		eObject.eClass().getEAllAttributes().forEach(e -> {
			processTrace.append(String.format("%s\t%s  %s  %s", prefix, e.getEAttributeType().getName(), e.getName(),
					eObject.eGet(e)));
		});

		processTrace.append(String.format("%s   * All EReferences: ", prefix));
		eObject.eClass().getEAllReferences().forEach(e -> {
			processTrace.append(String.format("%s\t%s  %s:", prefix, e.getEReferenceType().getName(), e.getName()));
			try {
				List<EObject> listOfreferences = (List<EObject>) eObject.eGet(e);
				if (!listOfreferences.isEmpty()) {
					if (followReference) {
						reportReferences(eObject, e, pad + 1);
					} else {
						listOfreferences.forEach(r -> {
							processTrace.append(String.format("%s\t-> {", prefix));
							processTrace.append(String.format("%s\t - %s ", prefix, EcoreUtil.getIdentification(r)));
							reportEObjectRDFnode(r, pad + 1);
							processTrace.append(String.format("%s\t   }", prefix));
						});
					}
				} else {
					processTrace.append(String.format("%s\t-> * Empty *", prefix));
				}
			} catch (Exception e2) {
				processTrace.append(String.format("%s\t Error with EReference (probably not an instance yet) : %s ",
						prefix, eObject.eGet(e)));
			}
		});
	}
	
	private void reportEObjectRDFnode(EObject eObject, int pad) {
		String p = "\n";
		for (int i = 0; i < pad; i++) {
			p = p + "\t";
		}
		final String prefix = p;

		if (eObject instanceof RDFGraphResourceImpl) {
			Resource rdfNode = identifyEObjectsRDFnode(eObject);
			processTrace.append(String.format("%s + RDF Node : %s ", prefix, rdfNode));
			processTrace.append(String.format("%s %s ", prefix, reportNamedModelsContaining(eObject)));
		} else {
			processTrace.append(String.format("%s + No RDFGraphResourceImpl for : %s ", prefix, eObject));
		}

	}
	
	private String reportNamedModelsContaining(EObject eObject) {
		StringBuilder namedModels = new StringBuilder();
		// List<Model> list = identifyNamedModelsContaining(eObject);

		if (eObject instanceof RDFGraphResourceImpl) {
			RDFGraphResourceImpl rdfGraphResource = (RDFGraphResourceImpl) eObject.eResource();
			List<Resource> list = rdfGraphResource.getResourcesForNamedModelsContaining(eObject);
			namedModels.append(String.format(" + On Named Model(s) : "));
			list.forEach(m -> namedModels.append(String.format(" [ %s ] ", m.getLocalName())));
			return namedModels.toString();
		}
		return "";

	}
}
