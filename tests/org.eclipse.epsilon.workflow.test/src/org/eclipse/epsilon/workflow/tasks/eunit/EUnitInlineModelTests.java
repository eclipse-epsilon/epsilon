/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.eunit;

import org.junit.Test;

/**
 * Tests for the inline model support, except for model comparison,
 * which has its own test suite ({@link EUnitModelComparisonTests}).
 *
 * @author Antonio Garcia-Dominguez
 */
public class EUnitInlineModelTests extends EUnitTestCase {

	@Test
	public void inlineHutnModelsWork() throws Exception {
		runTarget(ANT_BUILD_FILE, "inline-hutn");
	}

	@Test
	public void inlineAntTasksWork() throws Exception {
		runTarget(ANT_BUILD_FILE, "inline-emf");
	}

}
