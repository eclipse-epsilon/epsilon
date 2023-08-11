/*******************************************************************************
 * Copyright (c) 2010, 2018 Willink Transformations and others.
 * Copyright (c) 2023 The University of York.
 *  
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors: 
 *   E.D.Willink - Initial API and implementation
 *   Horacio Hoyos Rodriguez - Adaptation
 *******************************************************************************/
package org.eclipse.epsilon.ecore.delegates.validation;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.ValidationDelegateRegistryImpl;

/**
 * Add support for resetting the Validation Delegate
 * 
 * @since 2.5
 */
public interface EpsilonValidationDelegate extends EValidator.ValidationDelegate {

	/**
	 * A factory for creating delegate domains.
	 */
	interface Factory extends EValidator.ValidationDelegate {

		/**
		 * A <code>Factory</code> wrapper that is used by the
		 * {@link Factory.Registry}.
		 */
		interface Descriptor extends EValidator.ValidationDelegate.Descriptor {

			Factory getValidationDelegate();
		}

		/**
		 * A registry of delegate domain factories.
		 */
		interface Registry extends EValidator.ValidationDelegate.Registry {

			Factory getValidationDelegate(String uri);

			class Smart extends ValidationDelegateRegistryImpl implements Registry {

				public EpsilonValidationDelegate.Factory getValidationDelegate(String uri) {
					return (Factory) get(uri);
				}
				
				private static final long serialVersionUID = -6748383476427465359L;
			}
		}

		EpsilonValidationDelegate createValidationDelegate(EClassifier eClassifier);

	}
	
	void reset();
}