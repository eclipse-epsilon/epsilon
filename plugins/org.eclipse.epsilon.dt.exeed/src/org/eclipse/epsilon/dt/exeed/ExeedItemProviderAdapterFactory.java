/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed;

import java.util.Map;

import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.epsilon.dt.exeed.extensions.IExeedCustomizer;

public class ExeedItemProviderAdapterFactory extends ReflectiveItemProviderAdapterFactory {
	protected ExeedImageTextProvider imageTextProvider = null;

	public ExeedItemProviderAdapterFactory(ExeedPlugin plugin, Map<Class<?>, IExeedCustomizer> resourceClassToCustomizerMap) {
		super();
		reflectiveItemProviderAdapter = new ExeedItemProvider(this, plugin, resourceClassToCustomizerMap);
	}

	public void setImageTextProvider(ExeedImageTextProvider imageTextProvider) {
		this.imageTextProvider = imageTextProvider;
		((ExeedItemProvider) reflectiveItemProviderAdapter)
				.setImageTextProvider(imageTextProvider);
	}

	public ExeedItemProvider getItemProvider() {
		return (ExeedItemProvider) reflectiveItemProviderAdapter;
	}
}
