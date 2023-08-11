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

import org.eclipse.epsilon.ecore.delegates.DelegateUri;

public class ValidationUri extends DelegateUri {

	
	public ValidationUri() {
		super("http://eclipse.dev/epsilon/ecore/EVL");
	}

	public void register(EvlDelegate.Factory.Registry delegateRegistry, EvlDelegate.Factory factory) {
		delegateRegistry.put(this.uri, factory);
	}
}
