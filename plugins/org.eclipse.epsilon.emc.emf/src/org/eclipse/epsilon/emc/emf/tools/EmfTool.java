/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf.tools;

import static org.eclipse.epsilon.emc.emf.EmfUtil.createFileBasedURI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.CrossReferencer;
import org.eclipse.epsilon.emc.emf.EmfModelResourceSet;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.tools.AbstractTool;

public class EmfTool extends AbstractTool {
	
	public EcoreUtil getECoreUtil() {
		return new EcoreUtil();
	}
	
	public Map<EObject, Collection<EStructuralFeature.Setting>> getCrossReferences(Object eObject) {
		ArrayList<Object> list = new ArrayList<>();
		list.add(eObject);
		return CrossReferencer.find(list);
	}
	
	public void createModel(EObject eObject, String name) {
		IModel model = context.getModelRepository().getModelByNameSafe(name);
		if (model == null) {
			model = new InMemoryEmfModel(name, eObject.eResource());
			context.getModelRepository().addModel(model);
		}
	}
	
	public IModel createModel2(EObject eObject, String name, String uri) {
		ResourceSet rs = new ResourceSetImpl();
		Resource r = rs.createResource(EmfUtil.createPlatformResourceURI(uri));
		r.getContents().add(eObject);
		IModel model = new InMemoryEmfModel(name, r);
		context.getModelRepository().addModel(model);
		return model;
	}
	
	public void registerEPackage(EPackage ePackage) {
		EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);
	}
	
	public EPackage getEPackage(String nsUri) {
		return EPackage.Registry.INSTANCE.getEPackage(nsUri);
	}
	
	public Object validate(EObject eObject) {
		return Diagnostician.INSTANCE.validate(eObject);
	}
	
	
	public boolean resourceExists(String resource) {
		return new EmfModelResourceSet().getURIConverter().exists(createFileBasedURI(resource), null);
	}

	public boolean modelElementExists(String uri) throws IOException {
		try {
			return getEObject(uri) != null;
		
		} catch (IllegalArgumentException e) {
			if (e.getMessage().endsWith("is not a valid feature")) {
				return false;
			
			} else {
				throw e;
			}
		}
	}
	
	public EObject getEObject(String uri) throws IOException {
		final String modelUri   = uri.split("#")[0];
		final String elementUri = uri.split("#")[1];
		
		final Resource model = new EmfModelResourceSet().createResource(createFileBasedURI(modelUri));
		
		if (model == null) return null;
		
		model.load(null); // null, as we're not setting any load options
		
		return model.getEObject(elementUri);
	}
	
	public String resolveURI(String target, String base) {		
		final URI targetUri = URI.createURI(target);
		final String resolved;
		
		if (targetUri.isRelative()) {
			resolved = resolveRelativeUri(target, base);
		} else {
			resolved = targetUri.toString();
		}
		
		return resolved;
	}

	private String resolveRelativeUri(String target, String base) {
		final String targetPath, targetFragment;
		
		if (target.contains("#")) {
			targetPath     = target.split("#")[0];
			targetFragment = "#" + target.split("#")[1];
		
		} else {
			targetPath     = target;
			targetFragment = "";
		}
		
		return URI.createFileURI(targetPath).resolve(URI.createFileURI(base)).toFileString() + targetFragment;
	}
}
