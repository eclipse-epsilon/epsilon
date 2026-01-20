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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Internal.SettingDelegate;

/**
 * Add support for resetting the Setting Delegate
 * 
 * @since 2.5
 */
public interface EpsilonSettingDelegate extends SettingDelegate {

	interface Factory extends SettingDelegate.Factory {

		interface Descriptor extends SettingDelegate.Factory.Descriptor {

			Factory getFactory();

		}

		interface Registry extends SettingDelegate.Factory.Registry {

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
				
				private final SettingDelegate.Factory.Registry delegate = SettingDelegate.Factory.Registry.INSTANCE;

			}

			Factory getFactory(String uri);

		}

		EpsilonSettingDelegate createSettingDelegate(EStructuralFeature eStructuralFeature);
	}

	void reset();

}
