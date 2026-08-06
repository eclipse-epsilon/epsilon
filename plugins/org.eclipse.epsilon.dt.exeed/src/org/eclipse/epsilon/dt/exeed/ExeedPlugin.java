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

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.epsilon.common.dt.AbstractEpsilonUIPlugin;
import org.eclipse.jface.resource.ImageDescriptor;

public class ExeedPlugin extends AbstractEpsilonUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.epsilon.dt.exeed";

	public static ExeedPlugin getDefault() {
		return (ExeedPlugin) plugins.get(ExeedPlugin.class);
	}

	public static final String EXEED_EDITOR_ID = "org.eclipse.epsilon.dt.exeed.ExeedEditor";

	public static final String MODELINK_EDITOR_ID = "org.eclipse.epsilon.dt.exeed.modelink.ModeLinkEditor";
	
	public ImageDescriptor getImageDescriptor(URI uri) {
		
		ImageDescriptor imageDescriptor = getImageDescriptorRegistry().getDescriptor(uri.toString());
		if (imageDescriptor == null) {
				try {
					imageDescriptor = ImageDescriptor.createFromURL(new java.net.URI(uri.toString()).toURL());
				} catch (MalformedURLException | URISyntaxException e) {
					// Can be ignored
				}
				// Even if null cache, so we stop trying to create it
				getImageDescriptorRegistry().put(uri.toString(), imageDescriptor);
		}
		
		return imageDescriptor;
	}
}
