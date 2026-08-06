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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EValidator.ValidationDelegate;

/**
 * Add support for resetting the Validation Delegate
 * 
 * @since 2.5
 */
public interface EpsilonValidationDelegate extends ValidationDelegate {

	/**
	 * A factory for creating delegate domains.
	 */
	interface Factory extends ValidationDelegate {

		/**
		 * A <code>Factory</code> wrapper that is used by the
		 * {@link Factory.Registry}.
		 */
		interface Descriptor extends ValidationDelegate.Descriptor {

			Factory getValidationDelegate();
		}

		/**
		 * A registry of delegate domain factories.
		 */
		interface Registry extends ValidationDelegate.Registry {

			Factory getValidationDelegate(String uri);

			class Smart implements Registry {

				public Factory getValidationDelegate(String uri) {
					return (Factory) get(uri);
				}

				@Override
				public Set<String> getTargetPlatformFactories() {
					return this.delegate.getTargetPlatformFactories();
				}
				@Override
				public int size() {
					return this.delegate.size();
				}
				@Override
				public boolean isEmpty() {
					return this.delegate.isEmpty();
				}
				@Override
				public boolean containsKey(Object key) {
					return this.delegate.containsKey(key);
				}
				@Override
				public boolean containsValue(Object value) {
					return this.delegate.containsValue(value);
				}
				@Override
				public Object get(Object key) {
					return this.delegate.get(key);
				}
				@Override
				public Object put(String key, Object value) {
					return this.delegate.put(key, value);
				}
				@Override
				public Object remove(Object key) {
					return this.delegate.remove(key);
				}
				@Override
				public void putAll(Map<? extends String, ? extends Object> m) {
					this.delegate.putAll(m);
				}
				@Override
				public void clear() {
					this.delegate.clear();
				}
				@Override
				public Set<String> keySet() {
					return this.delegate.keySet();
				}
				@Override
				public Collection<Object> values() {
					return this.delegate.values();
				}
				@Override
				public Set<Entry<String, Object>> entrySet() {
					return this.delegate.entrySet();
				}
				
				private final ValidationDelegate.Registry delegate = ValidationDelegate.Registry.INSTANCE;
				
			}
		}

		EpsilonValidationDelegate createValidationDelegate(EClassifier eClassifier);

	}
	
	void reset();
}