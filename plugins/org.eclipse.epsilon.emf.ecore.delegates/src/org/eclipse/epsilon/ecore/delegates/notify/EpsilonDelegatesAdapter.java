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
import org.eclipse.emf.ecore.EOperation.Internal.InvocationDelegate;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.ecore.delegates.validation.EvlDelegate;

/**
 * DelegateEClassifierAdapter adapts an EClassifier to cache its Validation, Setting and Invocation 
 * Delegates.
 * 
 * @since 2.5
 */
public class EpsilonDelegatesAdapter extends AdapterImpl {
	
	public EpsilonDelegatesAdapter() {
		this(null, null);
	}

	public EpsilonDelegatesAdapter(InvocationDelegate eolDelegate, EvlDelegate evlDelegate) {
		super();
		this.eolDelegate = eolDelegate;
		this.evlDelegate = evlDelegate;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == EpsilonDelegatesAdapter.class;
	}

	@Override
	public void notifyChanged(Notification notification) {
		if (notification.getFeature().equals(EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS)) {
			if (this.evlDelegate != null) {
				this.evlDelegate.reset();
			}
		}
		super.notifyChanged(notification);
	}

	@Override
	public void setTarget(Notifier newTarget) {
		super.setTarget(newTarget);
		if (this.evlDelegate != null) {
			this.evlDelegate.reset();
		}
	}

	@Override
	public void unsetTarget(Notifier oldTarget) {
		super.unsetTarget(oldTarget);
		if (this.evlDelegate != null) {
			this.evlDelegate.reset();
		}
	}

	public EvlDelegate validationDelegate() {
		return this.evlDelegate;
	}
	
	public InvocationDelegate invocationDelegate() {
		return this.eolDelegate;
	}
	
	public boolean hasEvlDelegate() {
		return this.evlDelegate != null;
	}
	
	public void useEvlDelegate(EvlDelegate evlDelegate) {
		this.evlDelegate = evlDelegate;
	}
	
	public boolean hasEolDelegate() {
		return this.eolDelegate != null;
	}
	
	public void useEolDelegate(InvocationDelegate eolDelegate) {
		this.eolDelegate = eolDelegate;
	}
	
	private InvocationDelegate eolDelegate;
	private EvlDelegate evlDelegate;

}