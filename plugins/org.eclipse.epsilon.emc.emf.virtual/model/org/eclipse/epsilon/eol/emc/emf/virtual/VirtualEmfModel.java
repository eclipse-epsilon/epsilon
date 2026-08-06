/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.emc.emf.virtual;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.AbstractEmfModel;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.virtual.VirtualFactory;
import org.eclipse.epsilon.emc.emf.virtual.VirtualModel;
import org.eclipse.epsilon.emc.emf.virtual.VirtualObject;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

public class VirtualEmfModel extends AbstractEmfModel {

	private VirtualModel model = VirtualFactory.eINSTANCE.createVirtualModel();
	
	protected String modelFile;
	protected URI modelFileUri;
	
	public VirtualEmfModel() {
		propertyGetter = new VirtualPropertyGetter();
	}
	
	@Override
	public IPropertySetter getPropertySetter() {
		return new VirtualPropertySetter();
	}
	
	@Override
	public boolean isOfKind(Object instance, String metaClass) throws EolModelElementTypeNotFoundException {
		if (instance instanceof VirtualObject) {
			return metaClass.equals(((VirtualObject)instance).getType());
		}
		return super.isOfKind(instance, metaClass);
	}

	@Override
	public boolean isOfType(Object instance, String metaClass) throws EolModelElementTypeNotFoundException {
		if (instance instanceof VirtualObject) {
			return metaClass.equals(((VirtualObject)instance).getType());
		}
		
		return super.isOfType(instance, metaClass);
	}
	
	protected List<String> types = new ArrayList<>();
	
	@Override
	public boolean hasType(String type) {
		String firstChar = type.substring(0,1);
		return firstChar.equals(firstChar.toUpperCase());
	}
	
	@Override
	protected EObject createInstanceInModel(String type) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		final VirtualObject instance = VirtualFactory.eINSTANCE.createVirtualObject();
		instance.setType(type);
		
		model.getObjects().add(instance);
		
		return instance;
	}
	
	@Override
	public boolean owns(Object instance) {
		return model.getObjects().contains(instance);
	}
	
	
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		super.load(properties, resolver);
		
		this.modelFile = resolver.resolve(properties.getProperty(EmfModel.PROPERTY_MODEL_FILE));
		this.modelFileUri = URI.createFileURI(modelFile);
		
		/*
		if (StringUtil.isEmpty(basePath)) {
			this.modelFileUri = URI.createFileURI(modelFile);
		
		} else {
			this.modelFileUri = URI.createPlatformResourceURI(properties.getProperty(EmfModel.PROPERTY_MODEL_FILE), true);
		}*/
		
		load();
	}
	
	@Override
	protected void loadModel() throws EolModelLoadingException {
		model = VirtualFactory.eINSTANCE.createVirtualModel();
		
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());

		modelImpl = resourceSet.createResource(modelFileUri);
		modelImpl.getContents().add(model);
	}

	@Override
	public boolean store() {
		return store(modelFile);
	}
}
