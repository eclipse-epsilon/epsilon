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
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

	public static File getModelFile(String fileName) {
		String resource = getResource("models/" + fileName);
		return resource != null ? new File(resource) : null;
	}

	public static String getScript(String fileName) {
		return getResource("scripts/" + fileName);
	}
	
	public static String getResource(String resourceLocation) {
		Path resources = Paths.get(System.getProperty("user.dir")).resolve("resources");
		return resources.resolve(resourceLocation).toFile().getAbsolutePath();
	}
}
