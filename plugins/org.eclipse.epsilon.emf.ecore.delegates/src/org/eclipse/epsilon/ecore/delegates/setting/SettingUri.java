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

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.ecore.delegates.DelegateUri;

/**
 * URI for Setting delegates.
 * 
 * @since 2.5
 */
public class SettingUri extends DelegateUri {

	public SettingUri() {
		super("http://eclipse.dev/epsilon/ecore/EOL");
	}

	public void register(
		EpsilonSettingDelegate.Factory.Registry delegateRegistry,
		EpsilonSettingDelegate.Factory factory) {
		delegateRegistry.put(this.uri, factory);
	}

	public String getEannotionValue(EModelElement element, String key) {
		String body = EcoreUtil.getAnnotation(element, this.uri, key);
		if (body == null) {
			throw new IllegalArgumentException("No " + key + " for uri " + this.uri + "found in element");
		}
		return body;
	}

}
