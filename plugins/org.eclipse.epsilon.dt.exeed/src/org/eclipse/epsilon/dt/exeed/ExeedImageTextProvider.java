/*******************************************************************************
 * Copyright (c) 2008-2015 The University of York, Antonio Garcia-Dominguez
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - clean up and improve error reporting
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.common.dt.console.EpsilonConsole;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.jface.resource.ImageDescriptor;

public class ExeedImageTextProvider {
	
	protected EObjectImageTextProvider imageTextProvider = null;
	protected ExeedPlugin plugin = null;
	protected ExeedEditor editor = null;
	
	public ExeedImageTextProvider(InMemoryEmfModel model, ExeedPlugin plugin, ExeedEditor editor) {
		this.plugin = plugin;
		this.editor = editor;
		
		this.imageTextProvider = new EObjectImageTextProvider(model, 
				EpsilonConsole.getInstance().getDebugStream(), 
				EpsilonConsole.getInstance().getErrorStream(), 
				plugin.getPreferenceStore().getBoolean(ExeedPreferencePage.SHOW_STRUCTURAL_INFO)) {
			
			@Override
			protected void logException(Exception ex) {
				LogUtil.log(ex);
			}
		};
		
	}

	public Collection<?> getChoiceOfValues(Object object, String filter, Collection<?> def) {
		return imageTextProvider.getChoiceOfValues(object, filter, def);
	}
	
	public String getEStructuralFeatureLabel(EStructuralFeature feature, String def) {
		return imageTextProvider.getEStructuralFeatureLabel(feature, def);
	}

	public String getEObjectLabel(Object object, String def, boolean forReference) {
		return imageTextProvider.getEObjectLabel(object, def, forReference);
	}

	public String getEObjectReferenceLabel(Object object, String def) {
		return imageTextProvider.getEObjectReferenceLabel(object, def);
	}	
	
	public ImageDescriptor getEObjectImageDescriptor(Object object, ImageDescriptor def) {
		if (!(object instanceof EObject)) return def;

		String icon = imageTextProvider.getEObjectImage(object, null);
		ImageDescriptor imageDescriptor = getImageDescriptor(icon);
		if (imageDescriptor != null) {
			return imageDescriptor;
		}
		return def;
	}

	public ImageDescriptor getEClassImageDescriptor(EClass eClass, ImageDescriptor def) {
		String icon = imageTextProvider.getEClassImage(eClass, null);
		ImageDescriptor imageDescriptor = getImageDescriptor(icon);
		if (imageDescriptor != null) {
			return imageDescriptor;
		}
		return def;
	}

	public boolean isShowStructuralInfo() {
		return imageTextProvider.isShowStructuralInfo();
	}

	public void setShowStructuralInfo(boolean showStructuralInfo) {
		imageTextProvider.setShowStructuralInfo(showStructuralInfo);
	}

	private ImageDescriptor getImageDescriptor(String icon) {
		ImageDescriptor imageDescriptor = null;
		// icon might be a platform:/resource uri
		if (icon != null) {
			if (icon.startsWith ("platform:/")) {
				try {
					URI iconuri = URI.createURI(icon, false);
					imageDescriptor = plugin.getImageDescriptor(iconuri);
				}
				catch (IllegalArgumentException ex) {
					// icon is not a valid uri
				}
			}
			else {
				imageDescriptor = plugin.getImageDescriptor("icons/" + icon + ".gif");
				if (imageDescriptor == null) {
					imageDescriptor = plugin.getImageDescriptor("icons/" + icon + ".png");
				}
				
			}
		}
		return imageDescriptor;
	}
}
