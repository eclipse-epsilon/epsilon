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
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.ecore.delegates.invocation.EpsilonInvocationDelegate;
import org.eclipse.epsilon.ecore.delegates.setting.EpsilonSettingDelegate;
import org.eclipse.epsilon.ecore.delegates.validation.EpsilonValidationDelegate;

/**
 * DelegateEClassifierAdapter adapts an EClassifier to cache its Validation, Setting and Invocation 
 * Delegates.
 * 
 * @since 2.5
 */
public class EpsilonDelegatesAdapter extends AdapterImpl {

	@Override
	public boolean isAdapterForType(Object type) {
		return type == EpsilonDelegatesAdapter.class;
	}

	@Override
	public void notifyChanged(Notification notification) {
		if (notification.getFeature().equals(EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS)) {
			resetDelegates();
		}
		super.notifyChanged(notification);
	}

	@Override
	public void setTarget(Notifier newTarget) {
		super.setTarget(newTarget);
		resetDelegates();
	}

	@Override
	public void unsetTarget(Notifier oldTarget) {
		super.unsetTarget(oldTarget);
		resetDelegates();
	}

	public EpsilonValidationDelegate validationDelegate() {
		return this.evlDelegate;
	}
	
	public EpsilonInvocationDelegate invocationDelegate() {
		return this.eolDelegate;
	}
	
	public EpsilonSettingDelegate derivedFeatureDelegate() {
		return this.derivedDelegate;
	}
	
	public boolean hasValidationDelegate() {
		return this.evlDelegate != null;
	}
	
	public void useValidationDelegate(EpsilonValidationDelegate evlDelegate) {
		this.evlDelegate = evlDelegate;
	}
	
	public boolean hasInvocationDelegate() {
		return this.eolDelegate != null;
	}
	
	public void useInvocationDelegate(EpsilonInvocationDelegate eolDelegate) {
		this.eolDelegate = eolDelegate;
	}
	
	public boolean hasDerivedSettingDelegate() {
		return this.derivedDelegate != null;
	}
	
	public void useDerivedSettingDelegate(EpsilonSettingDelegate derivedDelegate) {
		this.derivedDelegate = derivedDelegate;
	}
	
	private EpsilonInvocationDelegate eolDelegate;
	private EpsilonValidationDelegate evlDelegate;
	private EpsilonSettingDelegate derivedDelegate;
	
	private void resetDelegates() {
		if (this.evlDelegate != null) {
			this.evlDelegate.reset();
		}
		if (this.eolDelegate != null) {
			this.eolDelegate.reset();
		}
		if (this.derivedDelegate != null) {
			this.derivedDelegate.reset();
		}
	}

}