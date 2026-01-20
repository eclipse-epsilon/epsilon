/*******************************************************************************
 * Copyright (c) 2020-2022 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors: Sina Madani
 *******************************************************************************/
package org.eclipse.epsilon.test;

import org.eclipse.epsilon.commons.test.CommonsTestSuite;
import org.eclipse.epsilon.ecl.engine.test.acceptance.EclAcceptanceTestSuite;
import org.eclipse.epsilon.egl.dt.test.EglDevelopmentToolsTestSuite;
import org.eclipse.epsilon.egl.dt.traceability.editor.EglTraceabilityEditorTestSuite;
import org.eclipse.epsilon.egl.engine.traceability.fine.test.acceptance.EglFineGrainedTraceabilityAcceptanceTestSuite;
import org.eclipse.epsilon.egl.test.acceptance.EglAcceptanceTestSuite;
import org.eclipse.epsilon.egl.test.unit.EglUnitTestSuite;
import org.eclipse.epsilon.egx.engine.test.acceptance.EgxAcceptanceTestSuite;
import org.eclipse.epsilon.emc.bibtex.BibtexModelTestSuite;
import org.eclipse.epsilon.emc.csv.test.CsvModelTestSuite;
import org.eclipse.epsilon.emc.emf.test.EmfTestSuite;
import org.eclipse.epsilon.emc.graphml.tests.GraphmlTestSuite;
import org.eclipse.epsilon.emc.hutn.test.HutnEmcDriverTestSuite;
import org.eclipse.epsilon.emc.json.tests.JSONModelTestSuite;
import org.eclipse.epsilon.emc.plainxml.test.PlainXmlTestSuite;
import org.eclipse.epsilon.emc.spreadsheets.test.SpreadsheetDriverTestSuite;
import org.eclipse.epsilon.emc.yaml.test.YamlTestSuite;
import org.eclipse.epsilon.eml.engine.test.acceptance.EmlAcceptanceTestSuite;
import org.eclipse.epsilon.eol.dap.test.EpsilonDebugAdapterTestSuite;
import org.eclipse.epsilon.eol.engine.test.acceptance.EolAcceptanceTestSuite;
import org.eclipse.epsilon.eol.test.unit.EolUnitTestSuite;
import org.eclipse.epsilon.epl.engine.test.acceptance.EplAcceptanceTestSuite;
import org.eclipse.epsilon.etl.engine.test.acceptance.EtlAcceptanceTestSuite;
import org.eclipse.epsilon.eunit.junit.test.EUnitJUnitTestSuite;
import org.eclipse.epsilon.evl.engine.test.acceptance.EvlAcceptanceTestSuite;
import org.eclipse.epsilon.ewl.engine.test.acceptance.EwlAcceptanceTestSuite;
import org.eclipse.epsilon.flexmi.test.FlexmiTestSuite;
import org.eclipse.epsilon.flock.engine.test.acceptance.FlockEngineAcceptanceTestSuite;
import org.eclipse.epsilon.hutn.test.HutnTestSuite;
import org.eclipse.epsilon.hutn.unparser.HutnUnparserUnitTestSuite;
import org.eclipse.epsilon.hutn.xmi.test.HutnXmiTestSuite;
import org.eclipse.epsilon.pinset.engine.test.acceptance.PinsetTestSuite;
import org.eclipse.epsilon.workflow.test.WorkflowTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

/**
 * Test suite to be run on CI server:
 * <code>mvn -f tests/org.eclipse.epsilon.test surefire:test -P ci</code>
 * 
 * @author Sina Madani
 * @since 1.6
 */
@RunWith(Suite.class)
@SuiteClasses({
	HutnTestSuite.class,
	HutnUnparserUnitTestSuite.class,
	HutnXmiTestSuite.class,
	HutnEmcDriverTestSuite.class,
	FlockEngineAcceptanceTestSuite.class,
	//FlockEngineUnitTestSuite.class,	// FIXME
	EglTraceabilityEditorTestSuite.class,
	//EglFineGrainedTraceabilityUnitTestSuite.class,	// FIXME
	EglAcceptanceTestSuite.class,
	EglUnitTestSuite.class,
	EglDevelopmentToolsTestSuite.class,
	EglFineGrainedTraceabilityAcceptanceTestSuite.class,
	EgxAcceptanceTestSuite.class,
	WorkflowTestSuite.class,
	CommonsTestSuite.class,
	EolUnitTestSuite.class,
	EolAcceptanceTestSuite.class,
	EvlAcceptanceTestSuite.class,
	EtlAcceptanceTestSuite.class,
	EclAcceptanceTestSuite.class,
	EmlAcceptanceTestSuite.class,
	EwlAcceptanceTestSuite.class,
	EplAcceptanceTestSuite.class,
	PlainXmlTestSuite.class,
	YamlTestSuite.class,
	BibtexModelTestSuite.class,
	GraphmlTestSuite.class,
	EmfTestSuite.class,
	SpreadsheetDriverTestSuite.class,
	CsvModelTestSuite.class,
	FlexmiTestSuite.class,
	PinsetTestSuite.class,
	EUnitJUnitTestSuite.class,
	JSONModelTestSuite.class,
	EpsilonDebugAdapterTestSuite.class
})
public class EpsilonJenkinsTestSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(EpsilonJenkinsTestSuite.class);
	}
}
