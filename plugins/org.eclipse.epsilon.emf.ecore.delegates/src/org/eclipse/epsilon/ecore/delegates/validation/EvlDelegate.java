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
 * A revised interface for delegating validation expression evaluation
 * adopting the same Factory, Registry, Descriptor architecture as the
 * invocation and setting delegates.
 * 
 * @since 2.5
 */
public interface EvlDelegate extends EValidator.ValidationDelegate {

	/**
	 * A factory for creating delegate domains.
	 */
	interface Factory extends EValidator.ValidationDelegate {

		/**
		 * A <code>Factory</code> wrapper that is used by the
		 * {@link Factory.Registry}.
		 */
		interface Descriptor extends EValidator.ValidationDelegate.Descriptor {

			EvlDelegate.Factory getValidationDelegate();
		}

		/**
		 * A registry of delegate domain factories.
		 */
		interface Registry extends EValidator.ValidationDelegate.Registry {

			EvlDelegate.Factory getValidationDelegate(String uri);

			class Delegate extends ValidationDelegateRegistryImpl implements Factory.Registry {

				private static final long serialVersionUID = 1L;

				public EvlDelegate.Factory getValidationDelegate(String uri) {
					return (EvlDelegate.Factory) get(uri);
				}
			}
		}

		EvlDelegate createValidationDelegate(EClassifier eClassifier);

		String getURI();

	}
}