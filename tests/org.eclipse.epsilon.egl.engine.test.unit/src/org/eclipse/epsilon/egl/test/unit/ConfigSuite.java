/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.unit;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.egl.config.TestXMLConfigFileReader;
import org.eclipse.epsilon.egl.config.TestXMLContentTypeRepository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestXMLConfigFileReader.class,
               TestXMLContentTypeRepository.class})
public class ConfigSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(ConfigSuite.class);
	}
}
