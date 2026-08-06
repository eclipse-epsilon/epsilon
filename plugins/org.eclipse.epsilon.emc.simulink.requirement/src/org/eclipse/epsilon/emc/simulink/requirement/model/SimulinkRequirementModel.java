/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.requirement.model;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.collections.collection.CompositeCollection;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.model.AbstractSimulinkModel;
import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.model.element.MatlabHandleElement;
import org.eclipse.epsilon.emc.simulink.requirement.model.element.ISimulinkRequirementModelElement;
import org.eclipse.epsilon.emc.simulink.requirement.model.element.SimulinkJustification;
import org.eclipse.epsilon.emc.simulink.requirement.model.element.SimulinkLink;
import org.eclipse.epsilon.emc.simulink.requirement.model.element.SimulinkLinkSet;
import org.eclipse.epsilon.emc.simulink.requirement.model.element.SimulinkReference;
import org.eclipse.epsilon.emc.simulink.requirement.model.element.SimulinkRequirement;
import org.eclipse.epsilon.emc.simulink.requirement.operations.contributors.RequirementModelOperationContributor;
import org.eclipse.epsilon.emc.simulink.requirement.util.collection.SimulinkJustificationCollection;
import org.eclipse.epsilon.emc.simulink.requirement.util.collection.SimulinkLinkCollection;
import org.eclipse.epsilon.emc.simulink.requirement.util.collection.SimulinkLinkSetCollection;
import org.eclipse.epsilon.emc.simulink.requirement.util.collection.SimulinkRequirementCollection;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.operations.contributors.IOperationContributorProvider;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

public class SimulinkRequirementModel extends AbstractSimulinkModel implements ISimulinkRequirementModelElement, IOperationContributorProvider {

	protected MatlabHandleElement reqSetHandle;
	protected RequirementModelOperationContributor reqOperationContributor;

	public static void main(String[] args) throws Exception {
		SimulinkRequirementModel reqModel = new SimulinkRequirementModel();
		reqModel.setReadOnLoad(true);
		reqModel.setStoredOnDisposal(false);
		reqModel.setFile(new File("/Applications/MATLAB_R2018b.app/examples/slrequirements/AutopilotRequirements.slreqx"));
		reqModel.setLibraryPath("/Applications/MATLAB_R2018b.app/bin/maci64/");
		reqModel.setEngineJarPath("/Applications/MATLAB_R2018b.app/extern/engines/java/jar/engine.jar");
		
		System.out.println("Loading");
		reqModel.load();
		
		EolModule module = new EolModule();
		module.getContext().getModelRepository().addModel(reqModel);
		module.parse("'Starting'.println();\n"
				+ "for (r in Requirement.all){ r.Id.println('req: '); }\n "
				+ "for (j in Justification.all){ j.Id.println('jus: '); }\n "
				+ "for (ref in Reference.all){ ls.Id.println('ref: '); }\n "
		);
		
		System.out.println("Executing");
		module.execute();
	}
	
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		super.load(properties, resolver);
		load();
	}
	
	@Override
	protected void loadModel() throws EolModelLoadingException {
		super.loadModel();
		reqOperationContributor = new RequirementModelOperationContributor(engine);

		if (!file.exists()) {
			try {
				reqSetHandle = new MatlabHandleElement(this, this.engine, (HandleObject) engine.fevalWithResult("slreq.new", file.getAbsolutePath()));
			} catch (MatlabException e) {
				throw new EolModelLoadingException(e, this);
			}
		} else {			
			try {
				reqSetHandle = new MatlabHandleElement(this, this.engine, (HandleObject) engine.fevalWithResult("slreq.load", file.getAbsolutePath()));
			} catch (MatlabException e) {
				throw new EolModelLoadingException(e, this);
			}
		}
		if (isOpenOnLoad()) {
			try {
				engine.feval("slreq.open", file.getAbsolutePath());
			} catch (MatlabException e) {
				throw new EolModelLoadingException(e, this);
			}
		}
	}
	
	@Override
	public Object getProperty(String property) throws EolRuntimeException {
		return reqSetHandle.getProperty(property);
	}

	@Override
	public void setProperty(String property, Object value) throws EolRuntimeException {
		reqSetHandle.setProperty(property, value);
	}

	@Override
	public Collection<String> getAllTypeNamesOf() {
		return Arrays.asList(getType());
	}

	@Override
	public boolean deleteElementInModel() throws EolRuntimeException {
		throw new EolRuntimeException("Can't delete RequirementSet model");
	}

	@Override
	public String getType() {
		return "RequirementSet";
	}

	@Override
	public MatlabHandleElement getHandle() {
		return reqSetHandle;
	}

	@Override
	public IModel getOwningModel() {
		return this;
	}

	@Override
	public String getTypeNameOf(Object instance) {
		if (instance instanceof ISimulinkModelElement)
			return ((ISimulinkRequirementModelElement) instance).getType();
		return null;
	}

	@Override
	public Object getElementById(String id) {
		throw new IllegalAccessError("Not allowed to get element by id");
	}

	@Override
	public String getElementId(Object instance) {
		if (instance instanceof ISimulinkRequirementModelElement) {
			try {
				((ISimulinkRequirementModelElement) instance).getProperty("SID");
			} catch (EolRuntimeException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public OperationContributor getOperationContributor() { 
		return reqOperationContributor;
	}
	
	@Override
	public void setElementId(Object instance, String newId) {
		throw new IllegalAccessError("Not allowed to get element by id from here");		
	}

	@Override
	public boolean owns(Object instance) {
		return instance instanceof ISimulinkRequirementModelElement;
	}

	@Override
	public boolean isInstantiable(String type) {
		return Arrays.asList("Justification", "Requirement", "Link", "Reference").contains(type) 
				|| type.startsWith("RQ_")
				|| super.isInstantiable(type);
	}

	@Override
	public boolean hasType(String type) {
		return Arrays.asList("Justification", "Requirement", "Link", "Reference", "LinkSet", "RequirementSet").contains(type) 
				|| type.startsWith("RQ_") || type.startsWith("RF_") || type.startsWith("L_");
	}
	
	@Override
	protected void closeMatlabModel() {
		try {
			engine.feval(0,"close", reqSetHandle.getHandle());
		} catch (Exception e) {
			System.err.println("Unable to close model");
		}
	}
	
	@Override
	public boolean store(String location) {
		try{
			engine.feval(0,"save", reqSetHandle.getHandle(), location);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean store() {
		try{
			engine.feval(0,"save", reqSetHandle.getHandle());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	protected Collection<ISimulinkModelElement> allContentsFromModel() {
		try {
			CompositeCollection compositeCollection = new CompositeCollection();
			compositeCollection.addComposited(Arrays.asList(this));
			compositeCollection.addComposited(getAllOfKind("Requirement"));
			compositeCollection.addComposited(getAllOfKind("Justification"));
			compositeCollection.addComposited(getAllOfKind("Reference"));
			compositeCollection.addComposited(getAllOfKind("Link"));
			compositeCollection.addComposited(getAllOfKind("LinkSet"));
			return (Collection<ISimulinkModelElement>) compositeCollection;
		} catch (EolModelElementTypeNotFoundException e) {
			throw new IllegalStateException("We should know the Entry type");
		}
	}

	@Override
	protected Collection<ISimulinkModelElement> getAllOfTypeFromModel(String type)
			throws EolModelElementTypeNotFoundException {
		if (type.startsWith("RQ_")) {			
			try {
				Object collection = engine.fevalWithResult("find", getHandle().getHandle(), "Type", "Requirement", "ReqType", type.substring(3));
				return new SimulinkRequirementCollection(collection, this);
			} catch (MatlabException e) {
				throw new EolModelElementTypeNotFoundException(this.getName(), type, e.getMessage());
			}
		} else if (type.startsWith("RF_")) {
			try {
				Object collection = engine.fevalWithResult("find", getHandle().getHandle(), "Type", "Reference", "ReqType", type.substring(3));
				return new SimulinkRequirementCollection(collection, this);
			} catch (MatlabException e) {
				throw new EolModelElementTypeNotFoundException(this.getName(), type, e.getMessage());
			}
		} else if (type.startsWith("L_")) {
			try {
				Object collection = engine.fevalWithResult("slreq.find", "Type", "Link", "LinkType", type.substring(2));
				return new SimulinkLinkCollection(collection, this);
			} catch (MatlabException e) {
				throw new EolModelElementTypeNotFoundException(this.getName(), type, e.getMessage());
			}
		}
		throw new EolModelElementTypeNotFoundException(this.getName(), type);
	}

	@Override
	protected Collection<ISimulinkModelElement> getAllOfKindFromModel(String kind)
			throws EolModelElementTypeNotFoundException {
		switch (kind) {
		case "Requirement":
			try {
				Object collection = engine.fevalWithResult("find", getHandle().getHandle(), "Type", "Requirement");
				return new SimulinkRequirementCollection(collection, this);
			} catch (MatlabException e) {
				return Collections.emptyList();
			}
		case "Justification":
			try {
				Object collection = engine.fevalWithResult("find", getHandle().getHandle(), "Type", "Justification");
				return new SimulinkJustificationCollection(collection, this);
			} catch (MatlabException e) {
				return Collections.emptyList();
			}
		case "Reference":
			try {
				Object collection = engine.fevalWithResult("find", getHandle().getHandle(), "Type", "Reference");
				return new SimulinkJustificationCollection(collection, this);
			} catch (MatlabException e) {
				return Collections.emptyList();
			}
		case "Link":
			try {
				Object collection = engine.fevalWithResult("slreq.find", "Type", "Link");
				return new SimulinkLinkCollection(collection, this);
			} catch (MatlabException e) {
				return Collections.emptyList();
			}
		case "LinkSet":
			try {
				Object collection = engine.fevalWithResult("slreq.find", "Type", "LinkSet");
				return new SimulinkLinkSetCollection(collection, this);
			} catch (MatlabException e) {
				return Collections.emptyList();
			}
		case "RequirementSet":
			return Arrays.asList(this);
		}
			
		return getAllOfTypeFromModel(kind);
	}

	@Override
	protected ISimulinkModelElement createInstanceInModel(String type)
			throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		switch (type) {
		case "Justification":
			return new SimulinkJustification(this, engine);
		case "Requirement":
			return new SimulinkRequirement(this, engine);
		case "Link":
			return new SimulinkLink(this, engine);
		case "Reference":
			return new SimulinkReference(this, engine);
		default:
			break;
		}
		if (type.startsWith("RQ_")) {
			return new SimulinkRequirement(this, engine, type.substring(3));
		}
		return super.createInstanceInModel(type);
	}
	
	@Override
	public Object createInstance(String type, Collection<Object> parameters)
			throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		switch (type) {
		case "Reference":
			if (parameters.size() == 1) {
				String artifact = (String) parameters.toArray(new Object[0])[0];
				return new SimulinkReference(this, engine, artifact);
			}
			break;
		case "LinkSet":
			if (parameters.size() == 1) {
				String artifact = (String) parameters.toArray(new Object[0])[0];
				return new SimulinkLinkSet(this, engine, artifact);
			}
			break;
		default:
			break;
		}
		return null;
	}

	@Override
	protected boolean deleteElementInModel(Object instance) throws EolRuntimeException {
		if (instance instanceof ISimulinkRequirementModelElement)
			return ((ISimulinkRequirementModelElement) instance).deleteElementInModel();
		return false;
	}

}
