/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt;

import java.net.URL;
import java.util.HashMap;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public abstract class AbstractEpsilonUIPlugin extends AbstractUIPlugin implements EpsilonPlugin {
	
	protected static HashMap<Class<?>, AbstractEpsilonUIPlugin> plugins = new HashMap<>();
	protected ImageRegistry imageDescriptorRegistry;
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugins.put(this.getClass(), this);
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugins.remove(this.getClass());
		if (imageDescriptorRegistry != null) imageDescriptorRegistry.dispose();
	}
	
	protected ImageRegistry getImageDescriptorRegistry() {
		if (imageDescriptorRegistry == null) {
			imageDescriptorRegistry = createImageRegistry();
		}
		return imageDescriptorRegistry;
	}
	
	public ImageDescriptor getImageDescriptor(String path) {
		
		ImageDescriptor imageDescriptor = getImageDescriptorRegistry().getDescriptor(path);
		
		if (imageDescriptor == null) {
			URL url = FileLocator.find(getBundle(), new Path(path), new HashMap<String, String>());
			if (url != null) {
				imageDescriptor = ImageDescriptor.createFromURL(url);
				if (imageDescriptor != null) getImageDescriptorRegistry().put(path, imageDescriptor);
			}
		}
		
		return imageDescriptor;
	}
	
	@Override
	public Image createImage(String path) {
		
		Image image = getImageRegistry().get(path);
		
		if (image == null) {
			try {
				ImageDescriptor imageDescriptor = getImageDescriptor(path);
				if (imageDescriptor != null) {
					image = getImageDescriptor(path).createImage();
					if (image != null) { getImageRegistry().put(path, image); }
				}
			}
			catch(Exception e) { LogUtil.log(e); }
		}
		
		return image;
	}
	
}
