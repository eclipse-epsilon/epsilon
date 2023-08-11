/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates.invocation;

import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EOperation.Internal.InvocationDelegate;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.ecore.delegates.DelegateUri;

/**
 * 
 * @since 2.5
 */
public class InvocationUri extends DelegateUri {

	public InvocationUri() {
		super("http://eclipse.dev/epsilon/ecore/EOL");
	}
	
	public void register(
		InvocationDelegate.Factory.Registry delegateRegistry, 
		InvocationDelegate.Factory factory) {
		delegateRegistry.put(this.uri, factory);
	}

	public String getEannotionValue(EOperation eOperation, String key) {
		String body = EcoreUtil.getAnnotation(eOperation, this.uri, key);
		if (body == null) {
			throw new IllegalArgumentException("No 'body' annotation found in operation");
		}
		return body;
	}

}
