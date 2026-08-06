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
package org.eclipse.epsilon.flock.engine.test.acceptance;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.flock.engine.test.acceptance.copying.ignoring.BodyTakesPriorityOverIgnore;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.ignoring.GuardedIgnore;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.ignoring.BodyAndIgnored;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.ignoring.IgnoreAccumalatesOverTypeHierachy;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.ignoring.IgnoreEscapedProperty;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.ignoring.IgnoreFullyQualifiedType;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.ignoring.IgnoreOneProperty;
import org.eclipse.epsilon.flock.engine.test.acceptance.copying.ignoring.IgnoreSeveralProperties;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({IgnoreOneProperty.class,
               IgnoreEscapedProperty.class,
               IgnoreSeveralProperties.class,
               GuardedIgnore.class,
               BodyAndIgnored.class,
               BodyTakesPriorityOverIgnore.class,
               IgnoreAccumalatesOverTypeHierachy.class,
               IgnoreFullyQualifiedType.class})
public class IgnoringSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(IgnoringSuite.class);
	}
}
