/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.engine.test.acceptance;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.flock.engine.test.acceptance.migrationlogic.UnsetFeature;
import org.eclipse.epsilon.flock.engine.test.acceptance.migrationlogic.UseAnnotatedOperation;
import org.eclipse.epsilon.flock.engine.test.acceptance.migrationlogic.UseOperation;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({UseOperation.class,
               UseAnnotatedOperation.class,
               UnsetFeature.class})
public class MigrationLogicSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(MigrationLogicSuite.class);
	}
}
