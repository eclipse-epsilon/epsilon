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

import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.epsilon.ecore.delegates.notify.DelegateEPackageAdapter;

/**
 * A URI identifying the type of invocation delegate.
 * 
 * @since 2.5
 */
public abstract class DelegateUri {

	public DelegateUri(String uri) {
		super();
		this.uri = uri;
	}
	
	public boolean isUsedBy(EModelElement target) {
		return target.getEAnnotation(this.uri) != null;
	}
	
	public DelegateContext context(DelegateEPackageAdapter adapter) {
		return adapter.delegateContext(this.uri);
	}
	
	public DelegateContext.ContextFactory factory(DelegateContext.ContextFactory.Registry registry) {
		 return registry.getFactory(this.uri);
	}
	
	public void addContext(DelegateEPackageAdapter adapter, Supplier<DelegateContext> factory) {
		adapter.addDelegate(this.uri, uri -> factory.get());
	}
	
	public boolean is(String delegateURI) {
		return Objects.equals(this.uri, delegateURI);
	}
	
	@Override
	public String toString() {
		return this.uri;
	}

	protected final String uri;

}
