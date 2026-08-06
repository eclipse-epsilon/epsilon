/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
******************************************************************************/
package org.eclipse.epsilon.emc.emf.test;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import org.eclipse.epsilon.emc.emf.*;
import org.eclipse.epsilon.emc.emf.xml.XmlModelTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({URITest.class, AbstractEmfModelTests.class, EmfModelTestSuite.class, XmlModelTests.class,
               EmfPropertySetterConformsTests.class, EmfPropertySetterCoerceTests.class,
               EmfModelPackageForNameTests.class, CachedResourceSetTests.class,
               EmfModelDeleteTests.class, EmfModelIsPropertySetTests.class,
               EmfModelCachedContentsTests.class, EmfToolResolveUriTests.class})
public class EmfTestSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(EmfTestSuite.class);
	}
}
 
