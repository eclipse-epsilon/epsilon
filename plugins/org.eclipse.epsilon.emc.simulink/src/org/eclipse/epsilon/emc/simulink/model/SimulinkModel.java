/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.common.util.Multimap;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.exception.MatlabRuntimeException;
import org.eclipse.epsilon.emc.simulink.model.TypeHelper.Kind;
import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.model.element.SimulinkBlock;
import org.eclipse.epsilon.emc.simulink.model.element.StateflowBlock;
import org.eclipse.epsilon.emc.simulink.operations.contributors.ModelOperationContributor;
import org.eclipse.epsilon.emc.simulink.util.SearchPreferences;
import org.eclipse.epsilon.emc.simulink.util.SimulinkUtil;
import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.operations.contributors.IOperationContributorProvider;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

public class SimulinkModel extends AbstractSimulinkModel implements IOperationContributorProvider {

	@Deprecated
	public static final String PROPERTY_SHOW_IN_MATLAB_EDITOR = "hidden_editor";
	
	/** CONSTANTS */
	public static final String PROPERTY_FOLLOW_LINKS = "follow_links";
	public static final String PROPERTY_LOOK_UNDER_MASKS = "look_under_masks";
	public static final String PROPERTY_INCLUDE_COMMENTED = "include_commented";
	public static final String PROPERTY_CURRENT_SIMULINK_MODEL = "current_simulink_model";
	public static final String PROPERTY_FIND_OPTIMISATION = "find_optimisation_enabled";
	
	public static final String BLOCK = "Block";
	public static final String SIMULINK = "Simulink";
	public static final String STATEFLOW = "Stateflow";

	public static final String GET_PARAM = "get_param('?', 'Handle');";
	public static final String LOAD_SYSTEM = "load_system('?')";
	public static final String OPEN_SYSTEM = "open_system('?')";
	public static final String NEW_SYSTEM = "new_system('?', 'Model');";
	public static final String SAVE_SYSTEM = "save_system('?', '?');";

	private static final Multimap<String, String> createBlockMap = new Multimap<>();
	private static final ArrayList<ArrayList<String>> deleteBlockMap = new ArrayList<>();


	static {
		createBlockMap.put("sflib/Chart", "Stateflow.Chart");
		ArrayList<String> chart = new ArrayList<>();
		chart.add("SubSystem");
		chart.add("Stateflow.Chart");
		deleteBlockMap.add(chart);
	}

	/** FIELDS */

	protected SearchPreferences searchPreferences = new SearchPreferences();
	protected ModelOperationContributor simulinkOperationContributor;

	protected boolean useCurrentSimulinkModel = false;
	protected boolean findOptimisationEnabled = true;
	@Deprecated
	protected boolean showInMatlabEditor = false;
	protected double handle = -1;
	protected String simulinkModelName;

	@Override
	protected void loadModel() throws EolModelLoadingException {
		super.loadModel();

		try {
			simulinkOperationContributor = new ModelOperationContributor(engine);
			
			if (isUseCurrentSimulinkModel()) {
				String evalWithResult = (String) engine.evalWithResult("gcs;");
				setSimulinkModelName(evalWithResult);
				this.handle = (Double) engine.evalWithResult("Simulink.ID.getHandle('?');", getSimulinkModelName());
			} else {
				setSimulinkModelName(getFile());
				if (readOnLoad) {
					//String cmd = showInMatlabEditor ? OPEN_SYSTEM : LOAD_SYSTEM;
					try {
						engine.eval(LOAD_SYSTEM, file.getAbsolutePath());
						engine.flush();
					} catch (Exception e) {
						System.out.println("Model file does not exist. Creating new model in specified location: " + file.getAbsolutePath());
						try {
							engine.eval(NEW_SYSTEM, getSimulinkModelName());
						} catch (Exception ex) {
							// Ignore; system already exists
						}
					}
				} else {
					try {
						engine.eval(NEW_SYSTEM, getSimulinkModelName());
					} catch (Exception ex) {
						// Ignore; system already exists
					}
				}
				if (isOpenOnLoad()) {
					try {						
						engine.eval(OPEN_SYSTEM, getSimulinkModelName());
						engine.flush();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				this.handle = (Double) engine.evalWithResult(GET_PARAM, getSimulinkModelName());
			}
			
		} catch (Exception e) {
			throw new EolModelLoadingException(e, this);
		}
	}
	
	@Override
	protected void closeMatlabModel() {
		try {			
			engine.eval("bdclose('?');", getSimulinkModelName());
			engine.flush();
		} catch (Exception e) {
			System.err.println("Unable to close model");
		}
	}

	@Override
	protected ISimulinkModelElement createInstanceInModel(String type)
			throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		if (type.contains("/")) {
			try {
				return new SimulinkBlock(this, engine, type);
			} catch (MatlabRuntimeException e) {
				throw new EolNotInstantiableModelElementTypeException(getSimulinkModelName(), type);
			}
		} else if (type.startsWith(STATEFLOW + ".")) {
			try {
				return new StateflowBlock(this, engine, type);
			} catch (MatlabException e) {
				throw new EolNotInstantiableModelElementTypeException(getSimulinkModelName(), type);
			}
		} else {
			return super.createInstanceInModel(type);
		}
	}

	@Override
	protected void addToCache(String type, ISimulinkModelElement instance) throws EolModelElementTypeNotFoundException {
		assert kindCache != null;
		for (String kind : getAllTypeNamesOf(instance)) {
			Object kindCacheKey = getCacheKeyForType(kind);
			kindCache.putIfPresent(kindCacheKey, instance);
		}
	}

	@Override
	protected void removeFromCache(ISimulinkModelElement instance) throws EolModelElementTypeNotFoundException {
		assert kindCache != null;
		for (String kind : getAllTypeNamesOf(instance)) {
			final Object kindCacheKey = getCacheKeyForType(kind);
			kindCache.remove(kindCacheKey, instance);
		}
	}

	@Override
	public void deleteElement(Object o) throws EolRuntimeException {
		deleteElementInModel(o);
		if (isCachingEnabled() && o instanceof ISimulinkModelElement) {
			removeFromCache((ISimulinkModelElement) o);
			String type = ((ISimulinkModelElement) o).getType();
			for (List<String> specialType : deleteBlockMap) {
				if (specialType.contains(type)) {
					for (String equivalent : specialType) {
						if (!equivalent.equals(type)) {
							kindCache.replaceValues(equivalent, getAllOfTypeFromModel(equivalent)); // refresh for type
						}
					}
				}
			}
		}
	}

	@Override
	public ISimulinkModelElement createInstance(String type)
			throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		ISimulinkModelElement instance = createInstanceInModel(type);
		if (isCachingEnabled()) {
			addToCache(instance.getType(), instance);
			if (createBlockMap.containsKey(type)) {
				for (String equivalent : createBlockMap.get(type)) {
					kindCache.replaceValues(equivalent, getAllOfTypeFromModel(equivalent)); // refresh for type
				}
			}
		}
		return instance;
	}

	@Override
	public Object createInstance(String type, Collection<Object> parameters)
			throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		if (type.startsWith(STATEFLOW) && parameters.size() == 1) {
			Object parentObject = parameters.toArray()[0];
			try {
				if (parentObject instanceof StateflowBlock) {
					try {
						StateflowBlock instance = new StateflowBlock(this, engine, type, (StateflowBlock) parentObject);
						if (isCachingEnabled()) {
							addToCache(instance.getType(), instance);
							if (createBlockMap.containsKey(type)) {
								for (String equivalent : createBlockMap.get(type)) {
									kindCache.replaceValues(equivalent, getAllOfTypeFromModel(equivalent)); // refresh
																											// for type
								}
							}
						}
						return instance;
					} catch (MatlabException e) {
						throw new EolModelElementTypeNotFoundException(type, null, e.getMessage());
					}
				} else {
					throw new EolModelElementTypeNotFoundException(type, null, "invalid parameters");
				}
			} catch (EolRuntimeException e) {
				throw new EolModelElementTypeNotFoundException(type, null, e.getMessage());
			}
		} else if (type.contains("/") && parameters.size() == 1) {
			Object parentPath = parameters.toArray()[0];
			try {
				return new SimulinkBlock(this, engine, type, (String) parentPath);
			} catch (MatlabRuntimeException e) {
				throw new EolNotInstantiableModelElementTypeException(getSimulinkModelName(), type);
			}
		}
		throw new EolModelElementTypeNotFoundException(type, null);
	}

	@Override
	protected boolean deleteElementInModel(Object instance) throws EolRuntimeException {
		try {
			if (instance instanceof ISimulinkModelElement)
				return ((ISimulinkModelElement) instance).deleteElementInModel();
			return false;
		} catch (Exception e) {
			throw new EolInternalException(e);
		}
	}

	// COLLECTORS

	@Override
	protected Collection<ISimulinkModelElement> allContentsFromModel() {
		return TypeHelper.getAll(this);
	}

	@Override
	protected Collection<ISimulinkModelElement> getAllOfTypeFromModel(String type)
			throws EolModelElementTypeNotFoundException {
		return TypeHelper.getAllOfType(this, type);
	}

	@Override
	protected Collection<ISimulinkModelElement> getAllOfKindFromModel(String kind_)
			throws EolModelElementTypeNotFoundException {
		try {
			return Kind.get(kind_).getAll(this);
		} catch (Exception e) {
			return getAllOfTypeFromModel(kind_);
		}
	}

	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		super.load(properties, resolver);

		setShowInMatlabEditor(properties.getBooleanProperty(PROPERTY_SHOW_IN_MATLAB_EDITOR, showInMatlabEditor));
		setFollowLinks(properties.getBooleanProperty(PROPERTY_FOLLOW_LINKS, getSearchPreferences().isFollowLinks()));
		setIncludeCommented(properties.getBooleanProperty(PROPERTY_INCLUDE_COMMENTED, getSearchPreferences().isIncludeCommented()));
		setFindOptimisationEnabled(properties.getBooleanProperty(PROPERTY_FIND_OPTIMISATION, findOptimisationEnabled));
		setLookUnderMasks(properties.getProperty(PROPERTY_LOOK_UNDER_MASKS, getSearchPreferences().getLookUnderMasks()));
		setUseCurrentSimulinkModel(properties.getBooleanProperty(PROPERTY_CURRENT_SIMULINK_MODEL, false));
		
		load();
	}

	public void simulate() throws InterruptedException {
		String name = getFile().getName().substring(0, getFile().getName().lastIndexOf("."));
		try {
			engine.evalAsync("simout = sim('" + name + "', []);").get();
		} catch (MatlabException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean hasType(String type) {
		return true; // FIXME No validation?
	}

	@Override
	public String getTypeNameOf(Object instance) {
		if (instance instanceof ISimulinkModelElement) {
			return ((ISimulinkModelElement) instance).getType();
		}
		return instance.getClass().getSimpleName().replace(SIMULINK, "");
	}

	@Override
	public Object getElementById(String id) {
		return null;
	}

	@Override
	public void setElementId(Object instance, String newId) {
	}

	@Override
	public String getElementId(Object instance) {
		try {
			return (String) propertyGetter.invoke(instance, "id", null);
		}
		catch (EolRuntimeException e) {
			return "";
		}
	}

	@Override
	public boolean owns(Object instance) {
		if (instance == null) {
			return false;
		}
		return ((instance instanceof ISimulinkModelElement)
				&& ((ISimulinkModelElement) instance).getOwningModel() == this) || (instance instanceof SimulinkModel)
				|| super.owns(instance);
	}

	@Override
	public boolean store(String location) {
		try {
			engine.eval(SAVE_SYSTEM, getSimulinkModelName(), location);
			engine.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean store() {
		store(file.getAbsolutePath());
		return true;
	}

	@Override
	public boolean isInstantiable(String type) {
		return hasType(type) || super.isInstantiable(type);
	}

	public String getSimulinkModelName() {
		return this.simulinkModelName;
	}
	
	protected void setSimulinkModelName(String name) {
		this.simulinkModelName = name;
	}
	
	protected void setSimulinkModelName(File name) {
		this.simulinkModelName = FileUtil.getFileName(file.getName(), false);
	}

	@Override
	public OperationContributor getOperationContributor() {
		return simulinkOperationContributor;
	}

	public Double getHandle() {
		return handle;
	}
	
	public boolean isUseCurrentSimulinkModel() {
		return useCurrentSimulinkModel;
	}

	public void setUseCurrentSimulinkModel(boolean useCurrentSimulinkModel) {
		this.useCurrentSimulinkModel = useCurrentSimulinkModel;
	}

	/**
	 * If true, the model will be shown in the MATLAB Editor. If the model is
	 * already loaded, it will not open it again. If false, the model will not be
	 * open in the MATLAB editor, but won't close an already open model
	 * 
	 * @Deprecated, use openOnLoad 
	 */
	@Deprecated
	public void setShowInMatlabEditor(boolean openMatlabEditor) {
		this.showInMatlabEditor = openMatlabEditor;
	}
	
	/**
	 * @Deprecated, use openOnLoad
	 */
	@Deprecated
	public boolean isShowInMatlabEditor() {
		return showInMatlabEditor;
	}	

	public SearchPreferences getSearchPreferences() {
		return searchPreferences;
	}
	
	public void setSearchPreferences(SearchPreferences searchPreferences) {
		this.searchPreferences = searchPreferences;
	}
	
	public boolean isFollowLinks() {
		return searchPreferences.isFollowLinks();
	}

	/**
	 * If true, adds the 'Follow_Link' parameter to the 'find_system' method in
	 * MATLAB
	 * 
	 * @Deprecated use SearhPreferences instead
	 */
	public void setFollowLinks(boolean followLinks) {
		searchPreferences.setFollowLinks(followLinks);
	}
	
	public String getLookUnderMasks() {
		return searchPreferences.getLookUnderMasks();
	}

	public void setLookUnderMasks(String lookUnderMasks) {
		searchPreferences.setLookUnderMasks(lookUnderMasks);
	}
	
	public boolean isIncludeCommented() {
		return searchPreferences.isIncludeCommented();
	}

	public void setIncludeCommented(boolean includeCommented) {
		searchPreferences.setIncludeCommented(includeCommented);
	}
	
	public boolean isFindOptimisationEnabled() {
		return findOptimisationEnabled;
	}

	public void setFindOptimisationEnabled(boolean enabled) {
		findOptimisationEnabled = enabled;
	}
	
	public Collection<ISimulinkModelElement> getChildren() throws MatlabException {
		return SimulinkUtil.findBlocks(this, 1);
	}

	public Collection<ISimulinkModelElement> findBlocks() throws MatlabException {
		return SimulinkUtil.findBlocks(this, 1);
	}

	public Collection<ISimulinkModelElement> findBlocks(Integer depth) throws MatlabException {
		return SimulinkUtil.findBlocks(this, depth);
	}

}
