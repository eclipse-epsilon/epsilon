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

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.ecore.delegates.DelegateContext.ContextFactory;
import org.eclipse.epsilon.ecore.delegates.EolDelegateContextFactory;
import org.eclipse.epsilon.ecore.delegates.notify.DerivedSettingAdapters;
import org.eclipse.epsilon.ecore.delegates.notify.EpsilonDelegatesAdapter;

/**
 * A Factory for setting delegates
 * 
 * Delegates are created using the {@link DerivedSettingAdapters} that are cached in the {@link EStructuralFeature}
 * adapters.
 * 
 * @since 2.5
 */
public class DelegateFactory implements EpsilonSettingDelegate.Factory {
	
	public DelegateFactory() {
		this(
				new SettingUri(), 
				new ContextFactory.Registry.Fast(), 
				new EpsilonSettingDelegate.Factory.Registry.Smart());
	}
			
	public DelegateFactory(
			SettingUri delegateUri,
			ContextFactory.Registry domainRegistry,
			EpsilonSettingDelegate.Factory.Registry delegateRegistry) {
			super();
			this.delegates =  new DerivedSettingDelegates(
					delegateUri,
					new DerivedSettingAdapters(
							delegateUri, 
							new EolDelegateContextFactory(),
							domainRegistry,
							this,
							delegateRegistry));
			
		}

	/**
	 *  Epsilon setting delegates are only supported for features marked as derived and volatile, 
	 *  transient, and non-changeable. 
	 */
	@Override
	public EpsilonSettingDelegate createSettingDelegate(EStructuralFeature eStructuralFeature) {
		if (eStructuralFeature.isDerived()
				&& eStructuralFeature.isVolatile()
				&& eStructuralFeature.isTransient()
				&& !eStructuralFeature.isChangeable()) {
			return this.findAdapter(eStructuralFeature).derivedFeatureDelegate();			
		}
		return null;
	}
	
	private final DerivedSettingDelegates delegates;
	
	private EpsilonDelegatesAdapter findAdapter(EStructuralFeature eStructuralFeature) {
		EpsilonDelegatesAdapter adapter = (EpsilonDelegatesAdapter) EcoreUtil
			.getAdapter(eStructuralFeature.eAdapters(), EpsilonDelegatesAdapter.class);
		if (adapter == null) {
			adapter = new EpsilonDelegatesAdapter();
			eStructuralFeature.eAdapters().add(adapter);
		}
		if (!adapter.hasDerivedSettingDelegate()) {
			adapter.useDerivedSettingDelegate(this.delegates.create(eStructuralFeature));
		}
		return adapter;
	}
	
	

}
