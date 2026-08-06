/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.flock.test.unit;

import org.eclipse.epsilon.flock.emc.wrappers.AttributeValueTests;
import org.eclipse.epsilon.flock.emc.wrappers.CollectionOfModelValuesTests;
import org.eclipse.epsilon.flock.emc.wrappers.EnumValueTests;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElementTests;
import org.eclipse.epsilon.flock.emc.wrappers.ModelTests;
import org.eclipse.epsilon.flock.emc.wrappers.ModelTypeTests;
import org.eclipse.epsilon.flock.emc.wrappers.ModelValueWrapperTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

@RunWith(Suite.class)
@SuiteClasses({ModelTests.class, ModelValueWrapperTests.class,
               AttributeValueTests.class, EnumValueTests.class,
               CollectionOfModelValuesTests.class,
               ModelElementTests.class, ModelTypeTests.class})
public class EmcWrappersSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(EmcWrappersSuite.class);
	}
}
