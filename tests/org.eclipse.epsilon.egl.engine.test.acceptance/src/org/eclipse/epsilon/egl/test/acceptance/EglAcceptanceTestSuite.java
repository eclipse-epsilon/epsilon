/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.acceptance;

import org.eclipse.epsilon.egl.test.acceptance.engine.Engine;
import org.eclipse.epsilon.egl.test.acceptance.engine.reset.ResettingTemplates;
import org.eclipse.epsilon.egl.test.acceptance.engine.subtemplates.InvokingSubtemplates;
import org.eclipse.epsilon.egl.test.acceptance.eol.ConsistencyWithEolSuite;
import org.eclipse.epsilon.egl.test.acceptance.exceptions.Exceptions;
import org.eclipse.epsilon.egl.test.acceptance.extensibility.Extensibility;
import org.eclipse.epsilon.egl.test.acceptance.formatters.Formatters;
import org.eclipse.epsilon.egl.test.acceptance.importCaching.ImportCachingTests;
import org.eclipse.epsilon.egl.test.acceptance.merge.Merge;
import org.eclipse.epsilon.egl.test.acceptance.operations.template.TemplateOperations;
import org.eclipse.epsilon.egl.test.acceptance.outdentation.OutdentationTests;
import org.eclipse.epsilon.egl.test.acceptance.output.Output;
import org.eclipse.epsilon.egl.test.acceptance.output.lineNumbers.CurrentLineNumber;
import org.eclipse.epsilon.egl.test.acceptance.output.newlines.OutputNewlines;
import org.eclipse.epsilon.egl.test.acceptance.patch.PatchTestSuite;
import org.eclipse.epsilon.egl.test.acceptance.stop.Stop;
import org.eclipse.epsilon.egl.test.acceptance.traceability.Traceability;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

@RunWith(Suite.class)
@SuiteClasses({Engine.class,
               ConsistencyWithEolSuite.class,
               ResettingTemplates.class,
               InvokingSubtemplates.class,
               Output.class, OutputNewlines.class,
               Merge.class,
               Traceability.class,
               Exceptions.class,
               Stop.class,
               Extensibility.class,
               TemplateOperations.class,
               CurrentLineNumber.class,
               Formatters.class,
               PatchTestSuite.class,
               OutdentationTests.class,
               ImportCachingTests.class})
public class EglAcceptanceTestSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(EglAcceptanceTestSuite.class);
	}
}

