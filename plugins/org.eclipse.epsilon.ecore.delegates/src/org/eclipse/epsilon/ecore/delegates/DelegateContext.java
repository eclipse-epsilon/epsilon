/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.ecore.delegates.notify.DelegateResourceAdapter;

/**
 * Provide caching and functionality for parsing Epsilon code embedded in the ECore model.
 * 
 * @since 2.5
 */
public interface DelegateContext {
	
	/**
	 * A factory for creating delegate domains.
	 */
	interface ContextFactory {
		
		/**
		 * Creates the delegate domain for the specified <tt>ePackage</tt> and the specified delegation URI
		 * @param delegateURI
		 * @param ePackage
		 * @param delegateRA
		 * @param delegation TODO
		 * @return
		 */
		DelegateContext create(
				DelegateUri delegateURI,
				EPackage ePackage,
				DelegateResourceAdapter delegateRA);

		/**
		 * A registry of delegate domain factories.
		 */
		interface Registry extends Map<String, DelegateContext.ContextFactory> {

			DelegateContext.ContextFactory getFactory(String uri);

			final class Fast extends HashMap<String, DelegateContext.ContextFactory> implements Registry {

				private static final long serialVersionUID = 1L;

				public DelegateContext.ContextFactory getFactory(String uri) {
					return get(uri);
				}
			}
		}
	}

	/**
	 * Dispose of this delegate context releasing any resources cached to support
	 * delegated behaviour for the associated package.
	 */
	void dispose();

}
