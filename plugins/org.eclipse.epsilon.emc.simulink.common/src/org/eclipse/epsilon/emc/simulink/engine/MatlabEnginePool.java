/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.simulink.engine;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.exception.MatlabRuntimeException;
import org.eclipse.epsilon.emc.simulink.util.MatlabEngineUtil;

public class MatlabEnginePool {
	
	private static final String MATLAB_ENGINE_CLASS = "com.mathworks.engine.MatlabEngine";

	protected static MatlabEnginePool instance;
	protected static MatlabClassLoader matlabClassLoader;
	
	protected static String engineJarPath;

	protected Set<MatlabEngine> pool = new LinkedHashSet<>();
	protected Class<?> matlabEngineClass;
	
	private MatlabEnginePool(String engineJarPath) throws MatlabRuntimeException {
		MatlabEnginePool.engineJarPath = engineJarPath;
		
		try {
			matlabEngineClass = matlabClassLoader.loadMatlabClass(MATLAB_ENGINE_CLASS);
			MatlabEngine.setEngineClass(matlabEngineClass);
		}
		catch (Exception ex) {
			throw new MatlabRuntimeException("Make sure to properly configure the library path and MATLAB engine Jar in Epsilon/Simulink preferences", ex);
		}
	}
	
	protected int getJavaVersion() {
	    String version = System.getProperty("java.version");
	    if(version.startsWith("1.")) {
	        version = version.substring(2, 3);
	    } else {
	        int dot = version.indexOf(".");
	        if(dot != -1) { version = version.substring(0, dot); }
	    } return Integer.parseInt(version);
	}
	
	private MatlabEnginePool() throws MatlabRuntimeException {
		this(engineJarPath);
	}
	
	public static MatlabEnginePool getInstance(String engineJarPath) throws MatlabRuntimeException {
		if (instance == null || (instance != null && !engineJarPath.equalsIgnoreCase(instance.getEngineJarPath()))) {
			
			matlabClassLoader = MatlabClassLoader.init(engineJarPath);
			instance = new MatlabEnginePool(engineJarPath);
		}
		return instance;
	}
	
	public static MatlabEnginePool getInstance() throws MatlabRuntimeException {
		if (instance == null) {
			matlabClassLoader = MatlabClassLoader.init(engineJarPath);
			instance = new MatlabEnginePool();
		}
		return instance;
	}
	
	public static void reset() {
		if (instance != null && !instance.pool.isEmpty()) {			
			instance.pool.clear();
			instance.projectEngine.clear();
		}
	}

	protected Map<String, MatlabEngine> projectEngine = new HashMap<>();
	
	public MatlabEngine getEngineForProject(String absoluteLocation) throws MatlabException, Exception {
		if (projectEngine.containsKey(absoluteLocation)) {
			return projectEngine.get(absoluteLocation);
		} else {
			MatlabEngine matlabEngine = getMatlabEngine();
			matlabEngine.setProject(absoluteLocation);
			projectEngine.put(absoluteLocation, matlabEngine);
			return matlabEngine;
		}
	}
	
	public MatlabEngine getMatlabEngine() throws Exception {
		MatlabEngine engine = null;
		if (pool.isEmpty()) {
			engine = new MatlabEngine();
		} else {
			engine = pool.iterator().next();
			pool.remove(engine);
		}
		return engine;
	}

	public void release(MatlabEngine engine) {
		try {
			engine.eval("clear");
			pool.add(engine);
		} catch (MatlabException e) {
			e.printStackTrace();
			try {
				engine.disconnect();
			} catch (MatlabException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public String getEngineJarPath() {
		return engineJarPath;
	}
	
	public static void main(String[] args) throws Exception {
		resolveFromEnv();
		MatlabEnginePool pool = MatlabEnginePool.getInstance();
		System.out.println(pool.pool.size());
		MatlabEngine matlabEngine = pool.getMatlabEngine();
		System.out.println("One Engine");
		System.out.println(matlabEngine.isDisconnected());
		MatlabEngine.startMatlab();
		String[] findMatlab = MatlabEngine.findMatlab();
		System.out.println(Arrays.toString(findMatlab));
		//System.out.println(matlabEngine.connectMatlab(findMatlab[0]));
		pool.getMatlabEngine();
	}

	/**
	 * Attempts to locate a MATLAB installation.
	 * 
	 * @return <code>true</code> iff the paths could be resolved.
	 * @since 1.6
	 */
	public static boolean resolveFromEnv() {
		String[] paths = MatlabEngineUtil.resolvePaths();
		if (paths != null && paths.length >= 3) {
			return resolve(paths[1], paths[2]);
		}
		return false;
	}
	
	/**
	 * 
	 * @param library The MATLAB library path.
	 * @param engineJar The MATLAB engine.jar file path.
	 * @return <code>true</code> iff the paths exist.
	 */
	public static boolean resolve(String library, String engineJar) {
		if ((engineJarPath = engineJar) == null)
			return false;
		return new File(library).exists() && new File(engineJar).exists();
	}
}
