/*********************************************************************
* Copyright (c) 2019 The University of York.
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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEnginePool;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.introspection.java.SimulinkPropertyGetter;
import org.eclipse.epsilon.emc.simulink.introspection.java.SimulinkPropertySetter;
import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.types.CellStr;
import org.eclipse.epsilon.emc.simulink.types.Complex;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.emc.simulink.types.Struct;
import org.eclipse.epsilon.emc.simulink.util.MatlabEngineUtil;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.models.CachedModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

public abstract class AbstractSimulinkModel extends CachedModel<ISimulinkModelElement> implements IGenericSimulinkModel {

	public static final String PROPERTY_WORKING_DIR = "working_dir";
	public static final String PROPERTY_PATHS = "paths";
	public static final String PROPERTY_FILE = "file";
	public static final String PROPERTY_MATLAB_PATH = "matlab_path";
	public static final String PROPERTY_LIBRARY_PATH = "library_path";
	public static final String PROPERTY_ENGINE_JAR_PATH = "engine_jar_path";
	public static final String PROPERTY_SIMULINK_PROJECT= "project";
	public static final String PROPERTY_OPEN_ON_LOAD= "openOnLoad";
	public static final String PROPERTY_CLOSE_ON_DISPOSE = "closeOnDispose";
	public static final String PROPERTY_ENABLE_TRY_CATCH = "tryCatch";
	public static final String PROPERTY_CURRENT_SIMULINK_PROJECT= "use_current_project";
	public static final String PROPERTY_ENGINE_POOL_SIZE = "engine_max_pool_size";
	public static final String PROPERTY_TRACK_API = "track_API";
	public static final String PROPERTY_REDUCE_EXCHANGES = "reduce_matlab_exchanges";

	public static final String ENV_MATLAB_PATH = ENV_PREFIX + PROPERTY_MATLAB_PATH;
	public static final String ENV_LIBRARY_PATH = ENV_PREFIX + PROPERTY_LIBRARY_PATH;
	public static final String ENV_ENGINE_JAR_PATH = ENV_PREFIX + PROPERTY_ENGINE_JAR_PATH;
	
	protected File file;
	protected String matlabPath;
	protected String libraryPath;
	protected String engineJarPath;
	protected MatlabEngine engine;
	protected File simulinkProject;
	protected boolean useCurrentProject = false;
	protected boolean openOnLoad = false;
	protected boolean closeOnDispose = false;
	protected boolean enableTryCatch = true;
	protected Integer enginePoolSize = 2;
	boolean trackApi = false;

	protected File workingDir = null;
	protected List<String> paths = new ArrayList<>();
	protected boolean reduceExchanges = false;

	public AbstractSimulinkModel() {
		propertyGetter = new SimulinkPropertyGetter();
		propertySetter = new SimulinkPropertySetter();
	}
	
	public void flush() throws EolRuntimeException {
		if (engine != null) {
			try {				
				engine.flush();
			} catch (Exception e) {
				throw new EolRuntimeException(e);
			}
		}
	}
	
	@Override
	protected void loadModel() throws EolModelLoadingException { 
		try {
			resolvePaths();
			
			if (isUseCurrentProject()) {
				engine = MatlabEnginePool.getInstance().getEngineForProject("current");
				engine.trackApi(trackApi);
				engine.enableTryCatch(isEnableTryCatch());
				engine.addModel(this);
			} else {
				if (getProject() != null && getProject().exists()) {
					try {
						engine = MatlabEnginePool.getInstance().getEngineForProject(getProject().getAbsolutePath());
						engine.addModel(this);					
					} catch (Exception ex) {
						throw new EolModelLoadingException(ex, this);
					}
				} 
			}
			if (engine == null) {				
				engine = MatlabEnginePool.getInstance().getMatlabEngine();
				engine.enableTryCatch(isEnableTryCatch());
				engine.trackApi(trackApi);
			}
			if (!isUseCurrentProject() && getProject() == null) {
				if ((getWorkingDir() != null && getWorkingDir().exists())) {
					try {
						engine.eval("cd '?';", getWorkingDir());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			
			for (String path : getPaths()) {
				engine.eval("addpath ?", path);
			}
			engine.setReduceExchanges(reduceExchanges);
		}
		catch (Exception e) {
			throw new EolModelLoadingException(e, this);
		}
	}
	
	@Override
	protected void disposeModel() { 
		if (isCloseOnDispose()) {
			closeMatlabModel();
		}
		try {
			if (isReduceExchanges()) {
				 if (!engine.isEvalCommandQueueEmpty()) {
					 throw new IllegalStateException("There were MATLAB commands that were not submitted during the execution! \n "
					 		+ "When enabling the \"reduce MATLAB exchanges\" option in the Simulink model, you must ensure to call the `ModelName.flush()` "
					 		+ "method in the model at the end of the execution, e.g. within a post{} block.");
				 }
			}
			engine.release(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected abstract void closeMatlabModel();
	
	@Override
	protected Object getCacheKeyForType(String type) throws EolModelElementTypeNotFoundException { 
		return type;		
	}
	
	@Override
	protected Collection<String> getAllTypeNamesOf(Object instance) { 
		if (instance instanceof ISimulinkModelElement) {
			return ((ISimulinkModelElement) instance).getAllTypeNamesOf();
		}
		else {
			return Arrays.asList(getTypeNameOf(instance));
		}
	}
	
	@Override
	public Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException {  
		throw new UnsupportedOperationException();
	}
	
	@Override
	public File getFile() { 
		return file;
	}

	@Override
	public void setFile(File file) { 
		this.file = file;
	}

	@Override
	public MatlabEngine getEngine() { 
		return engine;
	}
	
	public void setEngine(MatlabEngine engine) {
		this.engine = engine;
	}

	@Override
	public String getLibraryPath() { 
		return libraryPath;
	}

	@Override
	public void setLibraryPath(String libraryPath) { 
		this.libraryPath = libraryPath;
	}

	@Override
	public String getEngineJarPath() { 
		return engineJarPath;
	}

	@Override
	public void setEngineJarPath(String engineJarPath) { 
		this.engineJarPath = engineJarPath;
	}

	@Override
	public String getMatlabPath() {
		return matlabPath;
	}
	
	@Override
	public void setMatlabPath(String matlabPath) {
		this.matlabPath = matlabPath;
	}
	
	@Override
	public void setTrackApi(boolean track){
		this.trackApi = track;
	}
	
	@Override
	public boolean isTrackApi() {
		return trackApi;
	}
	
	/*
	@Override
	public Boolean isMustConnect() {
		return mustConnect;
	}

	@Override
	public void setMustConnect(Boolean mustConnect) {
		this.mustConnect = mustConnect;
	}

	@Override
	public String getEngineSharedSessionName() {
		return engineSharedSessionName;
	}

	@Override
	public void setEngineSharedSessionName(String connectingSession) {
		this.engineSharedSessionName = connectingSession;
	}
	
	@Override
	public Integer getEnginePoolSize() {
		return enginePoolSize;
	}

	@Override
	public void setEnginePoolSize(Integer enginePoolSize) {
		this.enginePoolSize = enginePoolSize;
	}
	*/
	
	@Override
	public File getProject() {
		return simulinkProject;
	}

	@Override
	public void setProject(String simulinkProject) {
		this.simulinkProject = new File(simulinkProject);
	}
	
	public void setProject(File simulinkProject) {
		this.simulinkProject = simulinkProject;
	}

	@Override
	public Boolean isUseCurrentProject() {
		return useCurrentProject;
	}

	@Override
	public void setUseCurrentProject(Boolean currentSimulinkProject) {
		this.useCurrentProject = currentSimulinkProject;
	}
	
	@Override
	public void setCloseOnDispose(Boolean closeOnDispose) {
		this.closeOnDispose = closeOnDispose;
	}
	
	@Override
	public void setOpenOnLoad(Boolean openOnLoad) {
		this.openOnLoad = openOnLoad;
	}
	
	@Override
	public Boolean isCloseOnDispose() {
		return closeOnDispose;
	}
	
	@Override
	public Boolean isOpenOnLoad() {
		return openOnLoad;
	}

	public void setEnableTryCatch(boolean enableTryCatch) {
		this.enableTryCatch = enableTryCatch;
	}
	
	public boolean isEnableTryCatch() {
		return this.enableTryCatch;
	}

	public Object parseMatlabEngineVariable(String variableName) throws MatlabException { 
		return MatlabEngineUtil.parseMatlabEngineVariable(engine, variableName);
	}
	
	public void statement(String statement) throws EolRuntimeException {
		try {
			engine.eval(statement);
		}
		catch (MatlabException e) {
			throw new EolRuntimeException(e.getMessage());
		}
	}
	
	public Object statementWithResult(String statement) throws EolRuntimeException {
		try {
			return engine.evalWithResult(statement);
		}
		catch (MatlabException e) {
			throw new EolRuntimeException(e.getMessage());
		}
	}
	
	public Object getWorkspaceVariable(String value) {
		try {
			return MatlabEngineUtil.parseMatlabEngineVariable(engine, value);
		} catch (MatlabException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean owns(Object instance) {
		return instance instanceof Struct || instance instanceof Complex
				|| instance instanceof HandleObject || instance instanceof CellStr;
	}
	
	@Override
	public boolean isInstantiable(String type) {
		return Arrays.asList("Struct", "Complex", "CellStr").contains(type);
	}
	
	@Override
	protected ISimulinkModelElement createInstanceInModel(String type)
			throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		switch (type) {
			case "Struct":
				return new Struct(getEngine());
			case "Complex":
				return new Complex(getEngine());
			case "CellStr":
				return new CellStr(getEngine());	
		}
		throw new EolModelElementTypeNotFoundException(this.getName(), type);
	}
	
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		super.load(properties, resolver);
		
		String workingDirPath = properties.getProperty(PROPERTY_WORKING_DIR);
		String filePath = properties.getProperty(PROPERTY_FILE);
		if (filePath != null && filePath.trim().length() > 0)
			setFile(new File(resolver.resolve(filePath)));
		
		if (!StringUtil.isEmpty(workingDirPath)) {
			// if the user has set a working directory, we will use it
			setWorkingDir(new File(resolver.resolve(workingDirPath)));
		} else {
			// otherwise we will use the directory the model file is in
			setWorkingDir(new File(resolver.resolve(filePath)).getParentFile());
		}

		String paths = properties.getProperty(PROPERTY_PATHS);
		if (!StringUtil.isEmpty(paths)) {
			Arrays.stream(paths.trim().split(";")).forEach(this::addPath);
		}
		
		String project = properties.getProperty(PROPERTY_SIMULINK_PROJECT, "");
		if (project != null && project.trim().length() > 0)
			setProject(new File(resolver.resolve(project)));
		
		setUseCurrentProject(properties.getBooleanProperty(PROPERTY_CURRENT_SIMULINK_PROJECT, false));
		setOpenOnLoad(properties.getBooleanProperty(PROPERTY_OPEN_ON_LOAD, false));
		setCloseOnDispose(properties.getBooleanProperty(PROPERTY_CLOSE_ON_DISPOSE, false));
		setEnableTryCatch(properties.getBooleanProperty(PROPERTY_ENABLE_TRY_CATCH, true));
		setReduceExchanges(properties.getBooleanProperty(PROPERTY_REDUCE_EXCHANGES, reduceExchanges));
		
		//setMustConnect(properties.getBooleanProperty(PROPERTY_MUST_CONNECT, false));
		//setEngineSharedSessionName(properties.getProperty(PROPERTY_ENGINE_SHARED_SESSION_NAME, ""));
		//setEnginePoolSize(properties.getIntegerProperty(PROPERTY_CURRENT_SIMULINK_PROJECT, 2));
		
		setMatlabPath(properties.getProperty(PROPERTY_MATLAB_PATH, matlabPath));
		setLibraryPath(properties.getProperty(PROPERTY_LIBRARY_PATH, libraryPath));
		setEngineJarPath(properties.getProperty(PROPERTY_ENGINE_JAR_PATH, engineJarPath));
		resolvePaths();
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public boolean isLoaded() {
		return file != null && engine != null;
	}
	
	@Override
	public File getWorkingDir() {
		return workingDir;
	}

	@Override
	public void setWorkingDir(File workingDir) {
		this.workingDir = workingDir;
	}

	@Override
	public void addPath(String path) {
		paths.add(path);
	}
	
	@Override
	public void addPath(File path) {
		paths.add(path.getAbsolutePath());
	}
	
	@Override
	public List<String> getPaths() {
		return paths;
	}
	
	/**
	 * 
	 * @return <code>true</code> iff the paths were successfully resolved.
	 */
	protected boolean resolvePaths() {
		String[] allPaths = {matlabPath, libraryPath, engineJarPath};
		MatlabEngineUtil.resolvePaths(allPaths);
		matlabPath = allPaths[0];
		libraryPath = allPaths[1];
		engineJarPath = allPaths[2];
		return MatlabEnginePool.resolve(libraryPath, engineJarPath);
	}

	public boolean isReduceExchanges() {
		return reduceExchanges;
	}

	public void setReduceExchanges(boolean reduceExchanges) {
		this.reduceExchanges = reduceExchanges;
	}
}
