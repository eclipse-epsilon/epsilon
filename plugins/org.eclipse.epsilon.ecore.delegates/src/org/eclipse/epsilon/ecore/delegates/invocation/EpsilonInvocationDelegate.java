/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.ecore.delegates.invocation;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EOperation.Internal.InvocationDelegate;

/**
 * Add support for resetting the Invocation Delegate
 * 
 * @since 2.5
 */
public interface EpsilonInvocationDelegate extends InvocationDelegate {

	interface Factory extends EOperation.Internal.InvocationDelegate.Factory {

		interface Descriptor {
			Factory getFactory();
		}

		interface Registry extends InvocationDelegate.Factory.Registry {
	
			class Smart implements Registry {

				public Factory getFactory(String uri) {
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
				
				private final InvocationDelegate.Factory.Registry delegate = InvocationDelegate.Factory.Registry.INSTANCE;

			}
			
			Factory getFactory(String uri);
			
		}
		
		EpsilonInvocationDelegate createInvocationDelegate(EOperation operation);

	}

	void reset();

}
