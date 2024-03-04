/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.emf.xmi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

public class PartialXMIResource extends XMIResourceImpl {
	
	public static final String OPTION_PARTIAL_LOADING_CONFIGURATION = "PARTIAL_LOADING_CONFIGURATION";
	
	protected PartialXMILoadConfiguration configuration;
	
	public PartialXMIResource(URI uri) {
		super(uri);
	}

	public static void main(String[] args) throws Exception {
		
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new PartialXMIResourceFactory());
		resourceSet.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		XMIResource resource = (XMIResource) resourceSet.createResource(URI.createFileURI("src/org/eclipse/epsilon/emc/emf/xmi/psl-with-ids.ecore"));
		
		PartialXMILoadConfiguration configuration = new PartialXMILoadConfiguration();
		configuration.addAllOfKind(EcorePackage.Literals.EREFERENCE);
		configuration.addFeature(EcorePackage.Literals.ESTRUCTURAL_FEATURE, EcorePackage.Literals.ETYPED_ELEMENT__ETYPE);
		configuration.addFeature(EcorePackage.Literals.ESTRUCTURAL_FEATURE, EcorePackage.Literals.ENAMED_ELEMENT__NAME);
		
		HashMap<String, Object> loadOptions = new HashMap<>();
		loadOptions.put(OPTION_PARTIAL_LOADING_CONFIGURATION, configuration);
		resource.load(loadOptions);
		
		resource.save(System.out, null);
	}
	
	@Override
	protected XMLLoad createXMLLoad(Map<?,?> options) {
		return new PartialXMILoadImpl(new PartialXMIHelper(this, options));
	};
	
	@Override
	public void load(Map<?, ?> options) throws IOException {
		PartialXMILoadConfiguration configuration = null; 
		if (options!=null) {
			configuration = (PartialXMILoadConfiguration) options.get(OPTION_PARTIAL_LOADING_CONFIGURATION);
		}
		super.load(options);
		if (configuration != null) EcoreUtil.deleteAll(configuration.getPlaceholders(), true);
	}
}
