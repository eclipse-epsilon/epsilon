/*******************************************************************************
 * Copyright (c) 2008-2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - bug #538175
 ******************************************************************************/
package org.eclipse.epsilon.eol.types;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.m3.MetaClass;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.eol.models.ModelRepository.TypeAmbiguityCheckResult;

public class EolModelElementType extends EolType {
	
	protected String modelName = "";
	protected String typeName = "";
	protected IEolModule module;
	protected ModelRepository modelRepo;
	protected MetaClass metaClass;
	protected IModel cachedModelRef;
	
	public EolModelElementType(MetaClass metaClass) {
		this.metaClass = metaClass;
		this.typeName = metaClass.getName();
	}
	
	public EolModelElementType(String modelAndMetaClass) {
		if (modelAndMetaClass.contains("!")) {
			String[] parts = modelAndMetaClass.split("!");
			modelName = parts[0];
			typeName = parts[1];
		}
		else {
			modelName = "";
			typeName = modelAndMetaClass;
		}
	}
	
	public EolModelElementType(String modelAndMetaClass, IEolContext context) throws EolModelNotFoundException, EolModelElementTypeNotFoundException {
		this(modelAndMetaClass);
		this.module = (IEolModule) context.getModule();
		
		checkAmbiguityOfType(context);
		getModel(true);	// Eager caching, possibly prevents race conditions
		checkAmbiguityOfTypeWithinModel(context);
	}

	private void checkAmbiguityOfType(IEolContext context) {
		if (modelName.isEmpty()) {
			final TypeAmbiguityCheckResult result = context.getModelRepository().checkAmbiguity(typeName);
			if (result.isAmbiguous) {
				issueAmbiguousTypeWarning(context, result);
			}
		}
	}

	private void issueAmbiguousTypeWarning(IEolContext context, TypeAmbiguityCheckResult result) {
		final String potentialTypes = CollectionUtil.join(result.namesOfOwningModels, " ", element -> "'" + element + "!" + typeName+"'");
		
		context.getWarningStream().println(String.format(
			"Warning: The type '%s' is ambiguous and could refer to any of the following: %s. The type '%s!%s' has been assumed. %s",
			typeName, potentialTypes, result.nameOfSelectedModel, typeName,
			determineLocation(context.getFrameStack().getCurrentStatement())
		));
	}

	private void checkAmbiguityOfTypeWithinModel(IEolContext context) {
		if (cachedModelRef != null) {
			// Owning model is not ambiguous, but type may be ambiguous within the model
			org.eclipse.epsilon.eol.models.IModel.AmbiguityCheckResult result = cachedModelRef.checkAmbiguity(typeName);
			if (result.isTypeAmbiguous()) {
				issueAmbiguousTypeWithinModelWarning(context, result);
			}
		}
	}

	private void issueAmbiguousTypeWithinModelWarning(IEolContext context, org.eclipse.epsilon.eol.models.IModel.AmbiguityCheckResult result) {
		final List<String> formattedOptions = result.options.stream()
			.map(option -> String.format("'%s!%s'", cachedModelRef.getName(), option))
			.collect(Collectors.toList());

		context.getWarningStream().println(String.format(
			"Warning: The type '%s' is ambiguous and could refer to any of the following: %s. The type '%s!%s' has been assumed. %s",
			typeName,
			String.join(" ", formattedOptions),
			cachedModelRef.getName(), result.options.get(0),
			determineLocation(context.getFrameStack().getCurrentStatement())
		));
	}

	private static String determineLocation(ModuleElement statement) {
		if (statement == null)
			return "";
		else
			return "(" + statement.getFile() + "@" + statement.getRegion().getStart().getLine() + ":" + statement.getRegion().getStart().getColumn() + ")";
	}

	public String getModelName() {
		return modelName;
	}
	
	public void setModelName(String model) {
		this.modelName = model;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String type) {
		this.typeName = type;
	}
	
	public Collection<?> getAllOfKind() {
		try {
			return getModel().getAllOfKind(typeName);
		}
		catch (EolModelElementTypeNotFoundException ex) {
			ex.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	public Collection<?> getAllOfType() {
		try {
			return getModel().getAllOfType(typeName);
		}
		catch (EolModelElementTypeNotFoundException ex) {
			ex.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	public Collection<?> getAll() {
		return getAllOfKind();
	}
	
	public Collection<?> all() {
		return getAllOfKind();
	}
	
	public Collection<?> getAllInstances() {
		return getAllOfKind();
	}
	
	public Collection<?> allInstances() {
		return getAllOfKind();
	}
	
	public boolean isInstantiable() throws EolModelElementTypeNotFoundException {
		return getModel().isInstantiable(typeName);
	}
	
	@Override
	public boolean isType(Object o) {
		try {
			return getModel().isOfType(o, typeName);
		}
		catch (EolModelElementTypeNotFoundException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Object createInstance() throws EolRuntimeException {
		return getModel().createInstance(typeName);
	}

	@Override
	public Object createInstance(List<Object> parameters) throws EolRuntimeException {
		return getModel().createInstance(typeName, parameters);
	}
	
	@Override
	public boolean isKind(Object o) {
		try {
			return getModel().isOfKind(o, typeName);
		}
		catch (EolModelElementTypeNotFoundException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public String getName() {
		String name = "";
		if (!modelName.isEmpty()) {
			name = modelName + "!";
		}
		return name + typeName; 
	}
	
	public IModel getModel() throws EolModelElementTypeNotFoundException {
		return getModel(module.getContext().getModelRepository() != modelRepo);
	}
	
	/**
	 * Fetches the model from the module's context.
	 * 
	 * @param updateCached Whether to re-acquire reference to the model and model repository.
	 * @return The model
	 * @throws EolModelElementTypeNotFoundException
	 * @since 1.6
	 */
	public IModel getModel(boolean updateCached) throws EolModelElementTypeNotFoundException {
		if (cachedModelRef == null || updateCached) {
			modelRepo = module.getContext().getModelRepository();
			try {
				cachedModelRef = modelRepo.getModelByName(modelName);
			}
			catch (EolModelNotFoundException ex) {
				Variable modelVariable = module.getContext().getFrameStack().get(modelName);
				if (modelVariable != null && modelVariable.getValue() instanceof IModel) {
					cachedModelRef = (IModel) modelVariable.getValue();
				}
			}
			
			if (cachedModelRef == null || !cachedModelRef.hasType(typeName)) {
				throw new EolModelElementTypeNotFoundException(modelName, typeName);
			}
		}
		
		return cachedModelRef;
	}
	
	public MetaClass getMetaClass() {
		return metaClass;
	}
	
	public void setMetaClass(MetaClass metaClass) {
		this.metaClass = metaClass;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(
			super.hashCode(),
			getName(),
			module,
			metaClass != null ? metaClass.getName() : metaClass
		);
	}
	
	@Override
	public boolean equals(Object other) {
		boolean eq = super.equals(other);
		if (!eq) return false;
		
		EolModelElementType eme = (EolModelElementType) other;
		eq = Objects.equals(this.getName(), eme.getName());
		
		if (eq && !(this.metaClass == null && eme.metaClass == null)) {
			if (this.metaClass == null || eme.metaClass == null)
				return false;
			
			eq = Objects.equals(this.metaClass.getName(), eme.metaClass.getName());
		}
		eq = eq && Objects.equals(this.module, eme.module);
		
		return eq;
	}

	@Override
	public List<EolType> getParentTypes() {
		if (this.metaClass != null && !this.metaClass.getSuperTypes().isEmpty()) {
			return this.metaClass.getSuperTypes().stream().map(s -> new EolModelElementType(s)).collect(Collectors.toList());
		} else {
			return super.getParentTypes();
		}
	}
}
