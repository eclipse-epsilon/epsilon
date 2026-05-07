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
		assert typeCache != null;
		
		Object typeCacheKey = getCacheKeyForType(type);
		typeCache.putIfPresent(typeCacheKey, instance);
		
		for (String kind : getAllTypeNamesOf(instance)) {
			Object kindCacheKey = getCacheKeyForType(kind);
			kindCache.putIfPresent(kindCacheKey, instance);
		}
	}

	@Override
	protected void removeFromCache(ISimulinkModelElement instance) throws EolModelElementTypeNotFoundException {
		assert kindCache != null;
		assert typeCache != null;
		
		final Object typeCacheKey = getCacheKeyForType(getTypeNameOf(instance));
		typeCache.remove(typeCacheKey, instance);
		
		for (String kind : getAllTypeNamesOf(instance)) {
			final Object kindCacheKey = getCacheKeyForType(kind);
			kindCache.remove(kindCacheKey, instance);
		}
	}

	@Override
	public void deleteElement(Object o) throws EolRuntimeException {		
		if (isCachingEnabled() && o instanceof ISimulinkModelElement) {
			// Subsystems may contain child blocks that also require to
			// be removed from the caches
			if (((ISimulinkModelElement) o).getType().equals("SubSystem")) {
				try {
					removeSimulinkSubsystemFromCaches((SimulinkBlock) o);
				} catch (EolModelElementTypeNotFoundException | MatlabException e) {
					System.err.println("Caches might not have been updated correctly for deleted blocks.");
					e.printStackTrace();
				}
			} else {
				removeFromCache((ISimulinkModelElement) o);
			}
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
		// The caches are stored as mappings to Simulink collections, which rely on
		// the handle of a block to resolve and manage the elements of the collection,
		// hence the deletion of the block from the Simulink model must happen AFTER
		// the caches are updated
		deleteElementInModel(o);
	}

	@Override
	public ISimulinkModelElement createInstance(String type)
			throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		ISimulinkModelElement instance = createInstanceInModel(type);
		if (isCachingEnabled()) {
			try {
				// When instantiating Subsystem blocks, these may create other blocks
				// (e.g., inports and outports) as their children, which would need to
				// be added to the caches as well
				if (instance.getType().equals("SubSystem")) {
					addSimulinkSubsystemToCaches((SimulinkBlock) instance);
				} else {
					addToCache(instance.getType(), instance);
				}
				if (createBlockMap.containsKey(type)) {
					for (String equivalent : createBlockMap.get(type)) {
						kindCache.replaceValues(equivalent, getAllOfTypeFromModel(equivalent)); // refresh for type
					}
				}
			} catch (MatlabException e) {
				System.err.println("Newly created Simulink blocks migth not have been added to caches.");
				e.printStackTrace();
			}
		}
		return instance;
	}

	@Override
	public Object createInstance(String type, Collection<Object> parameters)
			throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		try {
			if (type.startsWith(STATEFLOW) && parameters.size() == 1) {
				Object parentObject = parameters.toArray()[0];
				if (parentObject instanceof StateflowBlock) {
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
				} else {
					throw new EolModelElementTypeNotFoundException(type, null, "invalid parameters");
				}
			} else if (type.contains("/") && parameters.size() == 1) {
				Object parentPath = parameters.toArray()[0];
				ISimulinkModelElement instance = new SimulinkBlock(this, engine, type, (String) parentPath);
				if (isCachingEnabled()) {	
					// When instantiating Subsystem blocks, these may create other blocks
					// (e.g., inports and outports) as their children, which would need to
					// be added to the caches as well
					if (instance.getType().equals("SubSystem")) {
						addSimulinkSubsystemToCaches((SimulinkBlock) instance);
					} else {
						addToCache(instance.getType(), instance);
					}
					if (createBlockMap.containsKey(type)) {
						for (String equivalent : createBlockMap.get(type)) {
							kindCache.replaceValues(equivalent, getAllOfTypeFromModel(equivalent)); // refresh for type
						}
					}
				}
				return instance;
			}
		} catch (MatlabRuntimeException e) {
			throw new EolNotInstantiableModelElementTypeException(getSimulinkModelName(), type);
		} catch (EolRuntimeException | MatlabException e) {
			throw new EolModelElementTypeNotFoundException(type, null, e.getMessage());
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
	protected Collection<ISimulinkModelElement> getAllOfKindOrType(boolean isKind, String modelElementType) throws EolModelElementTypeNotFoundException {
		Collection<ISimulinkModelElement> values = null;
		
		// The code below is to prevent duplicate calls to getAllOf*FromModel.
		// With multiple threads there could be a race condition, so the
		// intent is to block the threads until the cache has been populated
		// by a single thread, and the others can just pick up from the cache
		// rather than recalculating.
		
		// As caching is not currently supported for Simulink ports and lines, we
		// always want to retrieve them from the model
		if (isCachingEnabled()
				&& (modelElementType.equals("Block") || TypeHelper.getKind(modelElementType).getKind().equals("Block"))) {
			final Multimap<Object, ISimulinkModelElement> cache = isKind ? kindCache : typeCache;
			final Object key = getCacheKeyForType(modelElementType);
			
			if ((values = cache.getMutable(key)) == null) synchronized (this) {
				// Could've changed while we were waiting on the lock
				if (!isConcurrent() || (values = cache.getMutable(key)) == null) {
					values = wrap(isKind ?
						getAllOfKindFromModel(modelElementType) :
						getAllOfTypeFromModel(modelElementType)
					);
					cache.putAll(key, values, values == null);
				}
			}
		}
		else if (values == null) {
			values = wrap(isKind ?
				getAllOfKindFromModel(modelElementType) :
				getAllOfTypeFromModel(modelElementType)
			);
		}
		
		return wrapUnmodifiable(values);
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

	/**
	 * Updates the caches for a Simulink block when this is replaced by a
	 * new block. This needs to be called after the new block is created
	 * and before the old block is deleted to ensure the blocks can be
	 * resolved in the caches.
	 * 
	 * @param oldHandle handle of the old block
	 * @param newHandle handle of the new block
	 * @throws EolModelElementTypeNotFoundException
	 * @throws MatlabRuntimeException if any of the blocks could not be instantiated
	 * @throws MatlabException if the block could not be queried for its children
	 */
	public void updateCaches(Double oldHandle, Double newHandle) throws EolModelElementTypeNotFoundException, MatlabRuntimeException, MatlabException {
		if(isCachingEnabled()) {
			SimulinkBlock oldBlock = new SimulinkBlock(this, this.engine, oldHandle);
			if (oldBlock.getType().equals("SubSystem")) {
				removeSimulinkSubsystemFromCaches(oldBlock);
			} else {
				removeFromCache(oldBlock);
			}
				
			SimulinkBlock newBlock = new SimulinkBlock(this, this.engine, newHandle);
			if (newBlock.getType().equals("SubSystem")) {
				addSimulinkSubsystemToCaches(newBlock);
			} else {
				addToCache(newBlock.getType(), newBlock);
			}
		}
	}
	
	/**
	 * Adds a Subsystem block and all its child blocks to the caches.
	 * 
	 * @param subSystem the Subsystem block to be cached
	 * @throws EolModelElementTypeNotFoundException
	 * @throws MatlabException if the block could not be queried for its children
	 */
	private void addSimulinkSubsystemToCaches(SimulinkBlock subSystem) throws EolModelElementTypeNotFoundException, MatlabException {
		for (ISimulinkModelElement child : subSystem.getChildren()) {
			if (!child.equals(subSystem)) {
				if (child.getType().equals("SubSystem")) {
					addSimulinkSubsystemToCaches((SimulinkBlock) child);
				} else {
					addToCache(child.getType(), child);
				}
			}
		}
		addToCache(subSystem.getType(), subSystem);
	}
	
	/**
	 * Removes a Subsystem block and all its child blocks from the caches.
	 * 
	 * @param subSystem the Subsystem block to be removed form caches
	 * @throws EolModelElementTypeNotFoundException
	 * @throws MatlabException if the block could not be queried for its children
	 */
	private void removeSimulinkSubsystemFromCaches(SimulinkBlock subSystem) throws EolModelElementTypeNotFoundException, MatlabException {
		for (ISimulinkModelElement child : subSystem.getChildren()) {
			if (!child.equals(subSystem)) {
				if (child.getType().equals("SubSystem")) {
					removeSimulinkSubsystemFromCaches((SimulinkBlock) child);
				} else {
					removeFromCache(child);
				}
			}
		}
		removeFromCache(subSystem);
	}

}
