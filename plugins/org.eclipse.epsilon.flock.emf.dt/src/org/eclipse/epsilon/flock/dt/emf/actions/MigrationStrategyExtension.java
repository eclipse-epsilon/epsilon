/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.dt.emf.actions;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class MigrationStrategyExtension {
	
	private final String pluginId;
	private final URI strategyFile;
	private final String originalMetamodelFile;
	private final String evolvedMetamodelFile;
	
	public MigrationStrategyExtension(IConfigurationElement extPoint) throws InvalidRegistryObjectException, URISyntaxException {
		final Bundle bundle = Platform.getBundle(extPoint.getDeclaringExtension().getNamespaceIdentifier());
		
		this.pluginId              = extPoint.getDeclaringExtension().getNamespaceIdentifier();
		this.strategyFile          = bundle.getEntry(extPoint.getAttribute("strategyFile")).toURI();
		this.originalMetamodelFile = extPoint.getAttribute("originalMetamodelFile");
		this.evolvedMetamodelFile  = extPoint.getAttribute("migratedMetamodelFile");
	}
	
	public URI getStrategyFile() {
		return strategyFile;
	}

	public org.eclipse.emf.common.util.URI getOriginalMetamodel() {
		return getMetamodelUri(originalMetamodelFile);
	}

	public org.eclipse.emf.common.util.URI getEvolvedMetamodel() {
		return getMetamodelUri(evolvedMetamodelFile);
	}
	
	private org.eclipse.emf.common.util.URI getMetamodelUri(String path) {
		return org.eclipse.emf.common.util.URI.createPlatformPluginURI("/" + pluginId + (path.startsWith("/") ? "" : "/") + path, true);
	}
}
