/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates.setting;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.ecore.delegates.AbstractDelegates;
import org.eclipse.epsilon.ecore.delegates.DelegateUri;
import org.eclipse.epsilon.ecore.delegates.Delegates;
import org.eclipse.epsilon.ecore.delegates.EolOperationDelegateContext;
import org.eclipse.epsilon.ecore.delegates.notify.Adapters;
import org.eclipse.epsilon.ecore.delegates.setting.EpsilonSettingDelegate.Factory;

/**
 * Handle {@link EpsilonSettingDelegate} creation
 * 
 * @since 2.5
 */
public class DerivedSettingDelegates
		extends AbstractDelegates<EStructuralFeature, EpsilonSettingDelegate, EpsilonSettingDelegate.Factory>
		implements Delegates<EStructuralFeature, EpsilonSettingDelegate> {

	public DerivedSettingDelegates(DelegateUri delegateUri, Adapters adapters) {
		super(delegateUri, adapters);
		this.factory = new FactoryImpl();
	}

	@Override
	public EpsilonSettingDelegate create(EStructuralFeature eStructuralFeature) {
		Factory factory = getFactory(eStructuralFeature);
		if (factory != null) {
			return factory.createSettingDelegate(eStructuralFeature);
		}
		return null;
	}

//	@Override
//	public EpsilonSettingDelegate create(String uri, EStructuralFeature eStructuralFeature) {
//		Factory factory = getFactory(eStructuralFeature);
//		if (factory != null) {
//			return factory.createSettingDelegate(eStructuralFeature);
//		}
//		return null;
//	}

	@Override
	protected EPackage getEPackage(EStructuralFeature eStructuralFeature) {
		return eStructuralFeature.getEContainingClass().getEPackage();
	}

	@Override
	protected Factory getFactory(String delegateURI, EStructuralFeature eObject) {
		if (this.delegateUri.is(delegateURI)) {
			return this.factory;
		}
		return null;
	}
	
	private class FactoryImpl implements Factory {

		@Override
		public EpsilonSettingDelegate createSettingDelegate(EStructuralFeature eStructuralFeature) {
			return new EolDerivedSettingDelegate(
					eStructuralFeature,
					delegateContext(getEPackage(eStructuralFeature)),
					(SettingUri) delegateUri);
		}
		
	};
	
	private final Factory factory;
	
	private EolOperationDelegateContext delegateContext(EPackage ePackage) {
		return (EolOperationDelegateContext) this.delegateUri.context(this.adapters.getAdapter(ePackage));
	}


}
