/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.launching;

import org.eclipse.epsilon.eol.dt.launching.EolLaunchConfigurationAttributes;

public interface EglLaunchConfigurationAttributes extends EolLaunchConfigurationAttributes {

	public static final String EGL_GENERATE_TO      = "generateTo";
	public static final String EGX_GENERATE_TO      = "egxGenerateTo";
	
	public static final String OUTPUT_FILE_PATH = "outputFilePath";
	public static final String OUTPUT_DIR_PATH = "outputDirPath";
	public static final String APPEND_TO_FILE   = "appendToFile";
	
	public static final int GENERATE_TO_CONSOLE = 1;
	public static final int GENERATE_TO_FILE    = 2;
	
	public static final int GENERATE_TO_DEFAULT_DIR = 1;
	public static final int GENERATE_TO_CUSTOM_DIR = 2;
	
	public static final String PRODUCE_TRACE     = "produceTrace";
	public static final String TRACE_DESTINATION = "traceDestination";
	
	public static final String DEFAULT_FORMATTERS    = "defaultFormatters";
	public static final String TEMPLATE_FACTORY_TYPE = "templateFactoryType";
}
