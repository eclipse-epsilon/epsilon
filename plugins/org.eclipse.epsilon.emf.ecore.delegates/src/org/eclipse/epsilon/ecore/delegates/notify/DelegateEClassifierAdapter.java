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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.ecore.delegates.invocation.InvocationBehavior;
import org.eclipse.epsilon.ecore.delegates.validation.EvlDelegate;
import org.eclipse.epsilon.ecore.delegates.validation.EvlDelegates;

/**
 * DelegateEClassifierAdapter adapts an EClassifier to cache its Validation, Setting and Invocation 
 * Delegates.
 * 
 * @since 2.5
 */
public class DelegateEClassifierAdapter extends AdapterImpl {
	
	public DelegateEClassifierAdapter(EvlDelegates validationBehavior, InvocationBehavior invocationBehavior) {
		super();
		this.evlDelegates = validationBehavior;
		this.invocationBehavior = invocationBehavior;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == DelegateEClassifierAdapter.class;
	}

	@Override
	public void setTarget(Notifier newTarget) {
		super.setTarget(newTarget);
		loadDelegates((EClassifier) newTarget); 
	}

	@Override
	public void notifyChanged(Notification notification) {
		if (notification.getFeature().equals(EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS)) {
			this.loadDelegates((EClassifier) getTarget());
		}
		super.notifyChanged(notification);
	}

	public EvlDelegate getValidationDelegate() {
		return this.evlDelegate;
	}
	
	private final EvlDelegates evlDelegates;
	private final InvocationBehavior invocationBehavior;
	private EvlDelegate evlDelegate;

	
	private void loadDelegates(EClassifier eClassifier) {
		this.evlDelegate = this.evlDelegates.create(eClassifier);
		// TODO Create invocation adapters 
//		if (eClassifier instanceof EClass) {
//			for (EOperation eOperation : ((EClass)eClassifier).getEOperations()) {
//				if (EcoreUtil.isInvariant(eOperation)) {					
//					List<DelegateDomain> opDelegateDomains = this.invocationBehavior.getDelegateDomains(eOperation);
//					for (DelegateDomain opDelegateDomain : opDelegateDomains) {
//						this.validationBehavior.create(opDelegateDomain.getURI(), eClassifier)
//							.forEach((k,v) -> this.validationDelegateMap.putIfAbsent(k, v));
//					}
//				}
//			}
//		}
	}
}