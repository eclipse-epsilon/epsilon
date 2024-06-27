/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test;

import org.eclipse.epsilon.eol.dap.test.ecl.EclDebugTest;
import org.eclipse.epsilon.eol.dap.test.egl.EglDebugTest;
import org.eclipse.epsilon.eol.dap.test.egl.ForCallEglTest;
import org.eclipse.epsilon.eol.dap.test.egl.ImportingEglTest;
import org.eclipse.epsilon.eol.dap.test.egx.ClasspathEgxTest;
import org.eclipse.epsilon.eol.dap.test.egx.EgxDebugTest;
import org.eclipse.epsilon.eol.dap.test.emg.EmgDebugTest;
import org.eclipse.epsilon.eol.dap.test.eml.EmlDebugTest;
import org.eclipse.epsilon.eol.dap.test.eol.ClasspathEolTest;
import org.eclipse.epsilon.eol.dap.test.eol.ErrorReportingEolTest;
import org.eclipse.epsilon.eol.dap.test.eol.ForLoopEolTest;
import org.eclipse.epsilon.eol.dap.test.eol.HttpEolTest;
import org.eclipse.epsilon.eol.dap.test.eol.ImportingEolTest;
import org.eclipse.epsilon.eol.dap.test.eol.LargeCollectionTest;
import org.eclipse.epsilon.eol.dap.test.eol.ModelElementPropertiesTest;
import org.eclipse.epsilon.eol.dap.test.eol.SmallCollectionTest;
import org.eclipse.epsilon.eol.dap.test.eol.StandaloneEolTest;
import org.eclipse.epsilon.eol.dap.test.eol.TupleTest;
import org.eclipse.epsilon.eol.dap.test.epl.EplDebugTest;
import org.eclipse.epsilon.eol.dap.test.etl.EtlDebugTest;
import org.eclipse.epsilon.eol.dap.test.eunit.EUnitDebugTest;
import org.eclipse.epsilon.eol.dap.test.evl.EvlDebugTest;
import org.eclipse.epsilon.eol.dap.test.flock.FlockDebugTest;
import org.eclipse.epsilon.eol.dap.test.pinset.PinsetDebugTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

@RunWith(Suite.class)
@SuiteClasses({
    EpsilonDebugServerTest.class,
    ImportingEolTest.class,
    StandaloneEolTest.class,
    ForLoopEolTest.class,
    ClasspathEolTest.class,
    HttpEolTest.class,
    ModelElementPropertiesTest.class,
    SmallCollectionTest.class,
    LargeCollectionTest.class,
    TupleTest.class,
    ClientLacksVariableTypeSupportTest.class,
    ErrorReportingEolTest.class,
    EglDebugTest.class,
    ForCallEglTest.class,
    ImportingEglTest.class,
    EgxDebugTest.class,
    ClasspathEgxTest.class,
    EvlDebugTest.class,
    EtlDebugTest.class,
    EclDebugTest.class,
    EmlDebugTest.class,
    EplDebugTest.class,
    EmgDebugTest.class,
    PinsetDebugTest.class,
    FlockDebugTest.class,
    EUnitDebugTest.class
})
public class EpsilonDebugAdapterTestSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(EpsilonDebugAdapterTestSuite.class);
	}
}
