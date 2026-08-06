/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf.m0;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertySetter;
import org.eclipse.epsilon.eol.execute.introspection.IReflectivePropertySetter;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

public class EmfM0Model extends EmfModel {
	
	protected File m0SpecificationFile;
	protected IEolModule eolModule;
	
	public EmfM0Model() {
		propertyGetter = new EmfM0PropertyGetter();
	}
	
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		this.m0SpecificationFile = new File(resolver.resolve(properties.getProperty("m0SpecificationFile")));
		super.load(properties, resolver);
	}

	@Override
	protected void loadModel() throws EolModelLoadingException {
		super.loadModel();
		eolModule = new EolModule();
		
		try {
			eolModule.parse(m0SpecificationFile);
			eolModule.execute();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		
		EmfModel copy = new EmfModel();
		
		copy.getAliases().addAll(this.getAliases());
		copy.setMetamodelFiles(this.getMetamodelFiles());
		copy.setMetamodelUris(this.getMetamodelUris());
		copy.setModelFile(this.getModelFile());
		copy.setMetamodelFileBased(this.isMetamodelFileBased());
		copy.setResource(this.getResource());
		copy.setReadOnLoad(this.isReadOnLoad());
		copy.setStoredOnDisposal(this.isStoredOnDisposal());
		copy.setName("Model");
		
		eolModule.getContext().getModelRepository().addModel(copy);
	}
	
	public Operation getHelper(String name) {
		return eolModule.getDeclaredOperations()
			.stream()
			.filter(helper -> helper.getName().equals(name))
			.findAny()
			.orElse(null);
	}
	
	class EmfM0PropertyGetter extends AbstractPropertyGetter {

		@Override
		public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
			ArrayList<Object> parameterValues = new ArrayList<>();
			parameterValues.add(property);
			Operation propertyGetter = eolModule.getDeclaredOperations().getOperation(object, "getProperty", parameterValues, eolModule.getContext());
			if (propertyGetter != null) {
				return propertyGetter.execute(object,parameterValues, eolModule.getContext());
			}
			else {
				return null;
			}
		}
	}
	
	class EmfM0PropertySetter extends AbstractPropertySetter implements IReflectivePropertySetter {

		@Override
		public void invoke(Object target, String property, Object value, IEolContext context) throws EolRuntimeException {
			ArrayList<Object> parameterValues = new ArrayList<>();
			parameterValues.add(property);
			parameterValues.add(value);
			Operation propertySetter = eolModule.getDeclaredOperations().getOperation(target, "setProperty", parameterValues, eolModule.getContext());
			if (propertySetter != null) {
				propertySetter.execute(target, parameterValues, eolModule.getContext());
			}
		}

		@Override
		public Object coerce(Object target, String property, Object value, IEolContext context) throws EolIllegalPropertyException {
			return value;
		}

		@Override
		public boolean conforms(Object target, String property, Object value, IEolContext context) throws EolIllegalPropertyException {
			return true;
		}
	}

	@Override
	public IReflectivePropertySetter getPropertySetter() {
		return new EmfM0PropertySetter();
	}

	@Override
	protected Collection<EObject> getAllOfTypeFromModel(String metaClass) throws EolModelElementTypeNotFoundException {
		Operation allOfTypeHelper = getHelper("allOfType");
		Collection<EObject> allOfType = null;
		
		try {
			allOfType = (Collection<EObject>) allOfTypeHelper.execute(metaClass, new ArrayList<>(), eolModule.getContext());
		}
		catch (EolRuntimeException rex){
			eolModule.getContext().getErrorStream().print(rex);
		}
		return allOfType;
	}
	
	@Override
	public Object getCacheKeyForType(String type) throws EolModelElementTypeNotFoundException {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Collection<EObject> getAllOfKindFromModel(String metaClass) throws EolModelElementTypeNotFoundException {
		Operation allOfKindHelper = getHelper("allOfKind");
		Collection<EObject> allOfKind = null;	
		try {
			allOfKind = (Collection<EObject>) allOfKindHelper.execute(metaClass, new ArrayList<>(), eolModule.getContext());
		}
		catch (EolRuntimeException rex) {
			eolModule.getContext().getErrorStream().print(rex);
		}
		return allOfKind;
	}
	
	//FIXME : Actually check is such a type is present
	@Override
	public boolean hasType(String type) {
		Operation hasTypeHelper = getHelper("hasType");
		boolean hasType = false;
		try {
			hasType = (boolean) hasTypeHelper.execute(type, new ArrayList<>(0), eolModule.getContext());
		}
		catch (EolRuntimeException rex){
			eolModule.getContext().getErrorStream().print(rex);
		}
		return hasType;
	}
	
	public File getM0SpecificationFile() {
		return m0SpecificationFile;
	}

	public void setM0SpecificationFile(File specification) {
		m0SpecificationFile = specification;
	}
	
	public IEolModule getEolModule() {
		return eolModule;
	}

	public void setEolModule(IEolModule eolModule) {
		this.eolModule = eolModule;
	}
}
