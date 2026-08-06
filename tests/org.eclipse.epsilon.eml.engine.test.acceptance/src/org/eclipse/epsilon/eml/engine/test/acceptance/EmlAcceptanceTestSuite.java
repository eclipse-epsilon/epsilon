/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml.engine.test.acceptance;

import org.eclipse.epsilon.eml.engine.test.acceptance.builtins.EmlCanAccessBuiltinsTests;
import org.eclipse.epsilon.eml.engine.test.acceptance.trees.TestXmlTreeMerging;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({EmlCanAccessBuiltinsTests.class, TestXmlTreeMerging.class})
public class EmlAcceptanceTestSuite {

}
