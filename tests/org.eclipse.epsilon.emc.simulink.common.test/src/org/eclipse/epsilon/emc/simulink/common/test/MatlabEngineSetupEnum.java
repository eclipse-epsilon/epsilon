/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.common.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.epsilon.emc.simulink.util.MatlabEngineUtil;

public enum MatlabEngineSetupEnum {

	ENGINE_JAR("C:/Program Files/MATLAB/%s/extern/engines/java/jar/engine.jar",
			"/Applications/MATLAB_%s.app/extern/engines/java/jar/engine.jar", ""), // TODO
	MATLAB("C:/Program Files/MATLAB/%s",
			"/Applications/MATLAB_%s.app", ""),
	LIBRARY_PATH("C:/Program Files/MATLAB/%s/bin/win64", MatlabEngineUtil.getLibraryPathFromRoot("/Applications/MATLAB_%s.app"), ""); // TODO

	private static final String OS_PROPERTY = "os.name";
	private static final String WINDOWS = "windows";
	private static final String MAC = "mac";

	// LIST OF SUPPORTED VERSIONS

	public static final List<String> VERSIONS; 
	
	static {
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		List<String> v= new ArrayList<>();
		for (int i = 2016; i <= currYear; i++) {
			v.add("R" + i + "a");
			v.add("R" + i + "b");
		}
		VERSIONS = v;
	}

	private String win;
	private String osx;
	private String lin;

	private String os;

	MatlabEngineSetupEnum(String win, String osx, String lin) {
		this.win = win;
		this.osx = osx;
		this.lin = lin;
		this.os = System.getProperty(OS_PROPERTY).toLowerCase();
	}

	public File file(String version) throws Exception {
		if (os.contains(WINDOWS)) {
			return getWindowsFile(version);
		} else if (os.contains(MAC)) {
			return getMacOSFile(version);
		} else {
			return getLinuxFile(version);
		}
	}

	public String path(String version) throws Exception {
		if (os.contains(WINDOWS)) {
			return getWindowsPath(version);
		} else if (os.contains(MAC)) {
			return getMacOSPath(version);
		} else {
			return getLinuxPath(version);
		}
	}

	public static boolean exists(String version) {
		try {
			return (ENGINE_JAR.file(version).exists() && LIBRARY_PATH.file(version).exists());
		} catch (Exception e) {
			return false;
		}
	}

	public static List<String> availableVersions() throws Exception {
		List<String> versions = VERSIONS.stream().filter(v -> exists(v)).collect(Collectors.toList());
		if (!versions.isEmpty()) {
			return versions;
		} else {
			throw new Exception("No MATLAB version installed");
		}
	}

	public static String availableVersion() throws Exception {
		return availableVersions().get(0);
	}

	public File file() throws Exception {
		return file(availableVersion());
	}

	public String path() throws Exception {
		return path(availableVersion());
	}

	public String getWindowsPath() throws Exception {
		return String.format(this.win, availableVersion());
	}

	public String getMacOSPath() throws Exception {
		return String.format(this.osx, availableVersion());
	}

	public String getLinuxPath() throws Exception {
		return String.format(this.lin, availableVersion());
	}

	public File getWindowsFile() throws Exception {
		return new File(getWindowsPath());
	}

	public File getMacOSFile() throws Exception {
		return new File(getMacOSPath());
	}

	public File getLinuxFile() throws Exception {
		return new File(getLinuxPath());
	}

	public String getWindowsPath(String version) throws Exception {
		if (VERSIONS.contains(version)) {
			return String.format(this.win, version);
		} else {
			throw new Exception();
		}
	}

	public String getMacOSPath(String version) throws Exception {
		if (VERSIONS.contains(version)) {
			return String.format(this.osx, version);
		} else {
			throw new Exception();
		}
	}

	public String getLinuxPath(String version) throws Exception {
		if (VERSIONS.contains(version)) {
			return String.format(this.lin, version);
		} else {
			throw new Exception();
		}
	}

	public File getWindowsFile(String version) throws Exception {
		if (VERSIONS.contains(version)) {
			return new File(getWindowsPath(version));
		} else {
			throw new Exception();
		}
	}

	public File getMacOSFile(String version) throws Exception {
		if (VERSIONS.contains(version)) {
			return new File(getMacOSPath(version));
		} else {
			throw new Exception();
		}
	}

	public File getLinuxFile(String version) throws Exception {
		if (VERSIONS.contains(version)) {
			return new File(getLinuxPath(version));
		} else {
			throw new Exception();
		}
	}

}