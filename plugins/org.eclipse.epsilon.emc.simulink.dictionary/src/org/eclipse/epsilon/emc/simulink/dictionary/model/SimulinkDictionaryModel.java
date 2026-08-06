/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.dictionary.model;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.apache.commons.collections.collection.CompositeCollection;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.simulink.dictionary.model.data.SimulinkDataType;
import org.eclipse.epsilon.emc.simulink.dictionary.model.element.ISimulinkDictionaryModelElement;
import org.eclipse.epsilon.emc.simulink.dictionary.model.element.SectionEnum;
import org.eclipse.epsilon.emc.simulink.dictionary.model.element.SimulinkEntry;
import org.eclipse.epsilon.emc.simulink.dictionary.model.element.SimulinkSection;
import org.eclipse.epsilon.emc.simulink.dictionary.operations.contributor.DictionaryModelOperationContributor;
import org.eclipse.epsilon.emc.simulink.dictionary.util.collection.SimulinkEntryCollection;
import org.eclipse.epsilon.emc.simulink.exception.EpsilonSimulinkInternalException;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.model.AbstractSimulinkModel;
import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.model.element.MatlabHandleElement;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

public class SimulinkDictionaryModel extends AbstractSimulinkModel implements ISimulinkDictionaryModelElement {

	protected static final String CREATE_FILE = "Simulink.data.dictionary.create";
	protected static final String OPEN_FILE = "Simulink.data.dictionary.open";
	protected MatlabHandleElement dictionaryHandle ;
	protected DictionaryModelOperationContributor dicOperationContributor;

	@Override
	protected void loadModel() throws EolModelLoadingException {
		super.loadModel();
		dicOperationContributor = new DictionaryModelOperationContributor(engine);

		if (!file.exists()) {
			try {
				dictionaryHandle = new MatlabHandleElement(this, this.engine, (HandleObject) engine.fevalWithResult(CREATE_FILE, file.getAbsolutePath()));
			} catch (MatlabException e) {
				throw new EolModelLoadingException(e, this);
			}
		} else {			
			try {
				dictionaryHandle = new MatlabHandleElement(this, this.engine, (HandleObject) engine.fevalWithResult(OPEN_FILE, file.getAbsolutePath()));
			} catch (MatlabException e) {
				throw new EolModelLoadingException(e, this);
			}
		}		
	}

	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		super.load(properties, resolver);
		load();
	}
	
	@Override
	public String getTypeNameOf(Object instance) {
		if (instance instanceof ISimulinkModelElement && ((ISimulinkModelElement)instance).getOwningModel().getClass().equals(getClass())) {
			return ((ISimulinkModelElement)instance).getType();
		}
		return null;
	}

	@Override
	public Object getElementById(String id) {
		throw new IllegalAccessError("Not allowed to get element by id");
	}

	@Override
	public String getElementId(Object instance) {
		throw new IllegalAccessError("Not allowed to get element by id");
	}

	@Override
	public void setElementId(Object instance, String newId) {
		throw new IllegalAccessError("Not allowed to set element id");
	}

	@Override
	public boolean owns(Object instance) {
		if (instance instanceof ISimulinkModelElement && ((ISimulinkModelElement)instance).getOwningModel().getClass().equals(getClass())) {
			return true;
		}
		return super.owns(instance);
	}

	@Override
	public boolean isInstantiable(String type) {
		return Arrays.asList("DesignDataEntry", "OtherDataEntry", "ConfigurationsEntry"/*, "EmbeddedCoderEntry"*/).contains(type) 
				|| super.isInstantiable(type); //|| type.startsWith("Simulink."); TODO Move upwards
	}

	@Override
	public boolean hasType(String type) {
		return Arrays.asList("Dictionary", "Section", "Entry").contains(type) 
				|| Arrays.asList("DesignDataEntry", "OtherDataEntry", "ConfigurationsEntry"/*, "EmbeddedCoderEntry"*/).contains(type);
	}

	@Override
	public boolean store(String location) {
		throw new IllegalStateException("Not allowed to save in different location");
	}

	@Override
	protected void closeMatlabModel() {
		try {
			if (!isStoredOnDisposal()) {				
				engine.feval(0,"discardChanges", dictionaryHandle.getHandle());
				System.out.println("Discarding changes");
			}
			engine.feval(0,"close", dictionaryHandle.getHandle());
			System.out.println("Closing model");
		} catch (Exception e) {
			System.err.println("Unable to close model");
		}	
	}
	
	@Override
	public boolean store() {
		try {
			System.out.println("Saving changes");
			engine.feval(0,"saveChanges", dictionaryHandle.getHandle());
			return true;
		} catch (MatlabException | EpsilonSimulinkInternalException e) {
			e.printStackTrace();
			return false;
		} 
	}

	@Override
	protected Collection<ISimulinkModelElement> allContentsFromModel() {
		try {
			CompositeCollection compositeCollection = new CompositeCollection();
			compositeCollection.addComposited(getAllOfKind("Section"));
			compositeCollection.addComposited(getAllOfKind("Entry"));
			compositeCollection.addComposited(Arrays.asList(this));
			return (Collection<ISimulinkModelElement>) compositeCollection;
		} catch (EolModelElementTypeNotFoundException e) {
			throw new IllegalStateException("We should know the Entry type");
		}
	}

	@Override
	protected Collection<ISimulinkModelElement> getAllOfTypeFromModel(String type)
			throws EolModelElementTypeNotFoundException {
			
		switch (type) {
		case "DesignDataEntry":
		case "OtherDataEntry":
		case "ConfigurationsEntry":			
		//case "EmbedderCoderEntry":
			SectionEnum sectionType = SectionEnum.forEpsilonEntryType(type);
			SimulinkSection section = sectionType.getFromModel(this);
			try {
				Object collection = engine.fevalWithResult("find", section.getHandle());
				return new SimulinkEntryCollection(collection, this);
			} catch (MatlabException e) {
				e.printStackTrace();
				return null;
			}
		default:
			break;
		}
		/*if (type.startsWith("Simulink.")) {
			try {
				Object value = engine.fevalWithResult("char", "-value");
				Object collection = engine.fevalWithResult(3,"find",getSection().getHandle(), value, "-class", type);
				return new SimulinkDataTypeCollection(collection, this);
			} catch (MatlabException e) {
				e.printStackTrace();
				return null;
			} 
		}*/
		return Collections.emptyList();
	}

	@Override
	protected Collection<ISimulinkModelElement> getAllOfKindFromModel(String kind)
			throws EolModelElementTypeNotFoundException {
		switch (kind) {
		case "Dictionary":
			return Arrays.asList(this);
		case "Section":
			Collection<ISimulinkModelElement> sections = new HashSet<ISimulinkModelElement>();
			for (SectionEnum section : SectionEnum.values()) {
				sections.add(section.getFromModel(this));
			}
			return sections;
		case "Entry":
			SimulinkEntryCollection entries = new SimulinkEntryCollection(null, this);
			for (SectionEnum sectionType : SectionEnum.values()) {
				SimulinkSection section = sectionType.getFromModel(this);
				try {
					Object collection = engine.fevalWithResult("find", section.getHandle());
					entries.addPrimitive(collection);
				} catch (MatlabException e) {
					e.printStackTrace();
					throw new IllegalStateException(e.getMessage());
				}
			}
			return entries;
		default:
			break;
		}
		return getAllOfTypeFromModel(kind);
	}

	@Override
	protected ISimulinkModelElement createInstanceInModel(String type)
			throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		switch (type) {
		case "Dictionary":
		case "Section":
		case "Entry":
			throw new EolNotInstantiableModelElementTypeException(this.name, type);
		case "DesignDataEntry":
		case "OtherDataEntry":
		case "ConfigurationsEntry":
		//case "EmbeddedCoderEntry":
			return new SimulinkEntry(this, engine, SectionEnum.forEpsilonEntryType(type));
		default:
			break;
		}
		if (type.startsWith("Simulink.")) {
			return new SimulinkDataType(this, engine, type);
		}
		return super.createInstanceInModel(type);
	}

	@Override
	protected boolean deleteElementInModel(Object instance) throws EolRuntimeException {
		if (owns(instance)) {
			return ((ISimulinkModelElement)instance).deleteElementInModel();
		}
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		SimulinkDictionaryModel dictionaryModel = new SimulinkDictionaryModel();
		dictionaryModel.setReadOnLoad(true);
		dictionaryModel.setStoredOnDisposal(false);
		dictionaryModel.setFile(new File("/Users/bea/Documents/AppDev/epsilon/plugins/org.eclipse.epsilon.emc.simulink.dictionary/resources/dd5.sldd"));
		dictionaryModel.setLibraryPath("/Applications/MATLAB_R2018b.app/bin/maci64/");
		dictionaryModel.setEngineJarPath("/Applications/MATLAB_R2018b.app/extern/engines/java/jar/engine.jar");
		
		System.out.println("Loading");
		dictionaryModel.load();
		
		EolModule module = new EolModule();
		module.getContext().getModelRepository().addModel(dictionaryModel);
		module.parse("'Starting'.println();\n"
				+ "Dictionary.all.first().println();\n "
				+ "Section.all.first().println();\n "
				+ "Entry.all.size().println('Entries: ');");
		
		System.out.println("Executing");
		module.execute();
	}

	@Override
	public IModel getOwningModel() {
		return this;
	}

	@Override
	public Object getProperty(String property) throws EolRuntimeException {
		return dictionaryHandle.getProperty(property);
	}

	@Override
	public void setProperty(String property, Object value) throws EolRuntimeException {
		dictionaryHandle.setProperty(property, value);
	}
	
	@Override
	public Collection<String> getAllTypeNamesOf() {
		return Arrays.asList(getType());
	}

	@Override
	public boolean deleteElementInModel() throws EolRuntimeException {
		return false;
	}

	@Override
	public String getType() {
		return "Dictionary";
	}

	@Override
	public MatlabHandleElement getHandle() {
		return dictionaryHandle;
	}
}
