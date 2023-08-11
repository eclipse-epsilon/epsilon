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
 * @since 2.5
 */
public interface DelegateContext {
	
	/**
	 * A factory for creating delegate domains.
	 */
	interface ContextFactory {
		
		/**
		 * Creates the delegate domain for the specified <tt>ePackage</tt>.
		 * @param ePackage
		 *            the package
		 * @param delegateEPackageAdapter TODO
		 * 
		 * @return its delegate domain
		 */
		DelegateContext createDelegateDomain(String delegateURI, EPackage ePackage, DelegateResourceAdapter delegateRA);

		/**
		 * A <code>Factory</code> wrapper that is used by the
		 * {@link ContextFactory.Registry}.
		 */
		interface Descriptor {

			DelegateContext.ContextFactory getFactory();
		}

		/**
		 * A registry of delegate domain factories.
		 */
		interface Registry extends Map<String, DelegateContext.ContextFactory> {

			DelegateContext.ContextFactory getFactory(String uri);

			final class Fast extends HashMap<String, DelegateContext.ContextFactory> implements Registry {

				private static final long serialVersionUID = 1L;

				@Override
				public DelegateContext.ContextFactory get(Object key) {
					DelegateContext.ContextFactory factory = super.get(key);
					if (factory instanceof ContextFactory.Descriptor) {
						ContextFactory.Descriptor factoryDescriptor = (ContextFactory.Descriptor) factory;
						factory = factoryDescriptor.getFactory();
						put((String) key, factory);
						return factory;
					} else {
						return factory;
					}
				}

				public DelegateContext.ContextFactory getFactory(String uri) {
					return get(uri);
				}
			}
		}
	}

	/**
	 * Dispose of this delegate domain releasing any resources cached to support
	 * delegated behaviour for the associated package.
	 */
	void dispose();

	/**
	 * Get the delegate domain name.
	 * 
	 * @return the delegate URI
	 */
	String getURI();

}
