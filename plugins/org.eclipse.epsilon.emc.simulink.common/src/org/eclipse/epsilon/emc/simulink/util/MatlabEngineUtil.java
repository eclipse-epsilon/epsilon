/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.util;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.epsilon.common.util.OperatingSystem;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.types.*;
import static org.eclipse.epsilon.emc.simulink.model.AbstractSimulinkModel.*;

public class MatlabEngineUtil {

	/**
	 * Convenience method providing an array of length 3 with the required paths.
	 * @return {{@link #PROPERTY_MATLAB_PATH}, {@link #PROPERTY_LIBRARY_PATH}, {@link #PROPERTY_ENGINE_JAR_PATH}}
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 * @see #resolvePaths(String[])
	 * @since 1.6
	 */
	public static String[] resolvePaths() throws IllegalStateException, IllegalArgumentException {
		String[] pathsArr = new String[]{"", "", ""};
		resolvePaths(pathsArr);
		return pathsArr;
	}
	
	/**
	 * Resolves paths. The array is only written to if this method succeeds.
	 * 
	 * @param currentPaths Contains the paths to determine in the following order: <br/>
	 * - {@link #PROPERTY_MATLAB_PATH}
	 * - {@link #PROPERTY_LIBRARY_PATH}
	 * - {@link #PROPERTY_ENGINE_JAR_PATH}
	 * 
	 * @throws IllegalArgumentException If the paths array is null or not length 3.
	 * @throws IllegalStateException If the paths couldn't be resolved.
	 * @author Sina Madani
	 * @since 1.6
	 */
	public static void resolvePaths(String[] currentPaths) throws IllegalStateException, IllegalArgumentException {
		if (currentPaths == null || currentPaths.length < 3)
			throw new IllegalArgumentException("Must provide ["
				+PROPERTY_MATLAB_PATH+"], ["
				+PROPERTY_LIBRARY_PATH+"], ["
				+PROPERTY_ENGINE_JAR_PATH+"]."
			);
		
		String
			matlabPath = currentPaths[0],
			libraryPath = currentPaths[1],
			engineJarPath = currentPaths[2];
		
		boolean emptyMatlabPath = StringUtil.isEmpty(matlabPath);
		boolean emptyLibraryPath = StringUtil.isEmpty(libraryPath);
		boolean emptyEngineJarPath = StringUtil.isEmpty(engineJarPath);
		
		if (emptyMatlabPath) {
			emptyMatlabPath = StringUtil.isEmpty(matlabPath = getMatlabPathFromEnv());
		}
		if (!emptyMatlabPath) {
			if (emptyLibraryPath) {
				emptyLibraryPath = StringUtil.isEmpty(libraryPath = getLibraryPathFromRoot(matlabPath));
			}
			if (emptyEngineJarPath) {
				emptyEngineJarPath = StringUtil.isEmpty(engineJarPath = getEngineJarPathFromRoot(matlabPath));
			}
		}
		if (emptyLibraryPath) {
			emptyLibraryPath = StringUtil.isEmpty(libraryPath = getLibraryPathFromEnv());
		}
		if (emptyEngineJarPath) {
			emptyEngineJarPath = StringUtil.isEmpty(engineJarPath = getEngineJarPathFromEnv());
		}
		if (emptyLibraryPath || emptyEngineJarPath) {
			String errMsg = "Unresolved MATLAB environment variables."
				+ "Please ensure that '"+PROPERTY_MATLAB_PATH+"' points to a valid MATLAB installation."
				+ "Alternatively, specify the '",
				singleSuffix = "' property.",
				multiSuffix = "' properties.";
			
			if (emptyEngineJarPath && !emptyLibraryPath) {
				errMsg += PROPERTY_ENGINE_JAR_PATH + singleSuffix;
			}
			if (emptyLibraryPath && !emptyEngineJarPath) {
				errMsg += PROPERTY_LIBRARY_PATH + singleSuffix;
			}
			if (emptyEngineJarPath && emptyLibraryPath) {
				errMsg += PROPERTY_ENGINE_JAR_PATH + " and " + PROPERTY_LIBRARY_PATH + multiSuffix;
			}
			
			throw new IllegalStateException(errMsg);
		}
		else {
			currentPaths[0] = matlabPath;
			currentPaths[1] = libraryPath;
			currentPaths[2] = engineJarPath;
		}
	}
	
	public static String getLibraryPathFromRoot(String matlabPath) {
		String osBin;
		switch (OperatingSystem.getOSFamily()) {
			case WINDOWS: osBin = "win64";
				break;
			case MAC: { 
				if (OperatingSystem.isARM64()) {
					osBin = "maca64";
				}
				else {
					osBin = "maci64";
				}
			}
				break;
			default: osBin = "glnxa64";
				break;
		}
		
		return Paths.get(
			matlabPath, "bin", osBin
		).toAbsolutePath().toString();
	}
	
	public static String getEngineJarPathFromRoot(String matlabPath) {
		return Paths.get(
			matlabPath, "extern", "engines", "java", "jar", "engine.jar"
		).toAbsolutePath().toString();
	}
	
	public static String getLibraryPathFromEnv() {
		return System.getenv(ENV_LIBRARY_PATH);
	}
	
	public static String getEngineJarPathFromEnv() {
		return System.getenv(ENV_ENGINE_JAR_PATH);
	}
	
	public static String getMatlabPathFromEnv() {
		String matlabPath = null;
		// No harm in trying I suppose...
		if (StringUtil.isEmpty(matlabPath = System.getenv(ENV_MATLAB_PATH)))
		if (StringUtil.isEmpty(matlabPath = System.getenv("MATLAB_HOME")))
		if (StringUtil.isEmpty(matlabPath = System.getenv("matlabroot"))) {
			File root;
			switch (OperatingSystem.getOSFamily()) {
				case WINDOWS: root = Paths.get("C:", "Program Files", "MATLAB").toFile();
					break;
				case MAC: root = new File("/Applications");
					break;
				default: root = new File("/usr/local/MATLAB");
					break;
			}
			if (root != null && root.exists()) {
				File[] subDirs = root.listFiles(OperatingSystem.isMac() ?
					(file, name) -> name.startsWith("MATLAB_") && name.endsWith(".app") && file.isDirectory()
					: null
				);
				
				if (subDirs.length > 0) {
					matlabPath = subDirs[subDirs.length - 1].getAbsolutePath().toString();
				}
			}
		}
		return matlabPath;
	}
	
	
	public static List<Double> matlabArrayToList(double[] value) {
		ArrayList<Double> result = new ArrayList<>(value.length);
		for (int i = 0; i < value.length; i++) {
			result.add(value[i]);
		}
		return result;
	}

	public static List<Long> matlabArrayToList(long[] value) {
		ArrayList<Long> result = new ArrayList<>(value.length);
		for (int i = 0; i < value.length; i++) {
			result.add(value[i]);
		}
		return result;
	}

	public static List<Integer> matlabArrayToList(int[] value) {
		ArrayList<Integer> result = new ArrayList<>(value.length);
		for (int i = 0; i < value.length; i++) {
			result.add(value[i]);
		}
		return result;
	}

	public static List<Float> matlabArrayToList(float[] value) {
		ArrayList<Float> result = new ArrayList<>(value.length);
		for (int i = 0; i < value.length; i++) {
			result.add(value[i]);
		}
		return result;
	}

	public static List<Boolean> matlabArrayToList(boolean[] value) {
		ArrayList<Boolean> result = new ArrayList<>(value.length);
		for (int i = 0; i < value.length; i++) {
			result.add(value[i]);
		}
		return result;
	}

	public static List<Byte> matlabArrayToList(byte[] value) {
		ArrayList<Byte> result = new ArrayList<>(value.length);
		for (int i = 0; i < value.length; i++) {
			result.add(value[i]);
		}
		return result;
	}

	public static List<Short> matlabArrayToList(short[] value) {
		ArrayList<Short> result = new ArrayList<>(value.length);
		for (int i = 0; i < value.length; i++) {
			result.add(value[i]);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> matlabArrayToList(Object[] value) {
		T[] newValue = (T[]) value;
		ArrayList<T> result = new ArrayList<>(value.length);
		for (int i = 0; i < newValue.length; i++) {
			result.add(newValue[i]);
		}
		return result;
	}

	public static Object parseMatlabEngineVariable(MatlabEngine engine, String variableName) throws MatlabException {
		return engine.getVariable(variableName);
	}

	// FIXME ENSURE THAT WE COVER ALL TYPES
	public static Object formatForMatlabEngine(Object value) {
		if (value == null)
			return null;
		if (value instanceof ISimulinkModelElement)
			return ((ISimulinkModelElement) value).getHandle();
		if (value instanceof HandleObject)
			return ((HandleObject)value).getHandleObject();
		if (value instanceof String) 
			return ((String) value).toCharArray();
		if (value instanceof Collection) {
			Collection<?> collection = (Collection<?>) value;
			int size = collection.size();
			Object first = collection.iterator().next();
			if (first instanceof Byte)
				return collection.toArray(new Byte[size]);
			if (first instanceof Short)
				return collection.toArray(new Short[size]); 
			if (first instanceof Integer)
				return collection.toArray(new Integer[size]); 
			if (first instanceof Long)
				return collection.toArray(new Long[size]); 
			if (first instanceof Long)
				return collection.toArray(new Long[size]);
			if (first instanceof Double)
				return collection.toArray(new Double[size]); 
			if (first instanceof Boolean)
				return collection.toArray(new Boolean[size]);
			if (first instanceof String)
				return collection.toArray(new String[size]);
			
			if (first instanceof HandleObject)
				return collection.stream()
						.map(c -> ((HandleObject)c).getHandleObject())
						.toArray();
			if (first instanceof ISimulinkModelElement)
				return collection.stream()
						.map(s -> ((ISimulinkModelElement) s).getHandle())
						.toArray();
		}
		return value;
	}
	
	public static Object parseMatlabEngineVariable(Object value) {
		if (value == null)
			return null;
		if (HandleObject.is(value))
			return new HandleObject(value);
		if (value instanceof byte[])
			return MatlabEngineUtil.matlabArrayToList((byte[]) value);
		if (value instanceof short[])
			return MatlabEngineUtil.matlabArrayToList((short[]) value);
		if (value instanceof int[])
			return MatlabEngineUtil.matlabArrayToList((int[]) value);
		if (value instanceof long[])
			return MatlabEngineUtil.matlabArrayToList((long[]) value);
		if (value instanceof float[])
			return MatlabEngineUtil.matlabArrayToList((float[]) value);
		if (value instanceof double[])
			return MatlabEngineUtil.matlabArrayToList((double[]) value);
		if (value instanceof boolean[])
			return MatlabEngineUtil.matlabArrayToList((boolean[]) value);
		if (value instanceof String[])
			return MatlabEngineUtil.matlabArrayToList((String[]) value);
		if (value instanceof Object[]) {
			List<Object> l = Arrays.asList((Object[])value);
			if (l.stream().allMatch(HandleObject::is)) {
				 return l.stream().map(HandleObject::new).collect(Collectors.toList());
			} else if (l.stream().allMatch(Complex::is)) {
				return l.stream().map(Complex::new).collect(Collectors.toList());
			} else if (l.stream().allMatch(Struct::is)) {
				return l.stream().map(Struct::new).collect(Collectors.toList());
			} else if (l.stream().allMatch(CellStr::is)) {
				return l.stream().map(CellStr::new).collect(Collectors.toList());
			}
		}
		if (value instanceof Character) 
			return String.valueOf(value);
		if (value instanceof String) 
			return String.valueOf(value);
		
		if (Struct.is(value))
			return new Struct(value);
		if (Complex.is(value))
			return new Complex(value);
		if (CellStr.is(value))
			return new CellStr(value);
		return value;
	}
	
}
