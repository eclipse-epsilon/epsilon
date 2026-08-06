/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates.validation;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.epsilon.ecore.delegates.AbstractDelegates;
import org.eclipse.epsilon.ecore.delegates.DelegateUri;
import org.eclipse.epsilon.ecore.delegates.Delegates;
import org.eclipse.epsilon.ecore.delegates.notify.Adapters;
import org.eclipse.epsilon.ecore.delegates.validation.EpsilonValidationDelegate.Factory;

/**
 * Handle {@link EpsilonValidationDelegate} creation
 * 
 * @since 2.5
 */
public class ValidationDelegates
		extends AbstractDelegates<EClassifier, EpsilonValidationDelegate, Factory> 	
		implements Delegates<EClassifier, EpsilonValidationDelegate> {

	public ValidationDelegates(
		DelegateUri delegateUri,
		EpsilonValidationDelegate.Factory.Registry defaultRegistry,
		Adapters adapters) {
		super(delegateUri, adapters);
		this.defaultRegistry = defaultRegistry;
	}


	@Override
	public EPackage getEPackage(EClassifier eClassifier) {
		return eClassifier.getEPackage();
	}
	
	@Override
	public EpsilonValidationDelegate create(EClassifier eClassifier) {
		Factory factory = getFactory(eClassifier);
		if (factory != null) {
			return factory.createValidationDelegate(eClassifier);
		}
		return null;
	}
	

//	@Override
//	public EpsilonValidationDelegate create(String uri, EClassifier eClassifier) {
//		Factory factory = this.getFactory(uri, eClassifier);
//		if (factory != null) {
//			return factory.createValidationDelegate(eClassifier);
//		}
//		return null;
//	}
//	
	@Override
	public Factory getFactory(String delegateURI, EClassifier eClassifier) {
		EValidator.ValidationDelegate.Registry registry = this.adapters.getRegistry(
				eClassifier,
				EpsilonValidationDelegate.Registry.class,
				this.defaultRegistry);
	    return (Factory) registry.getValidationDelegate(delegateURI);
	}

	private final EpsilonValidationDelegate.Factory.Registry defaultRegistry;
	
}
