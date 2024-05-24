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

import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.Map;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.InitializeRequestArguments;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.junit.Test;

/**
 * Tests that we honour the case when the client's "supports variable types" flag
 * is set to false.
 */
public class ClientLacksVariableTypeSupportTest extends AbstractEpsilonDebugAdapterTest {

	@Override
	protected void setupModule() throws Exception {
		this.module = new EolModule();
		module.parse(new File(BASE_RESOURCE_FOLDER, "07-tuples.eol"));
	}

	@Override
	protected void setupInitializeArguments(InitializeRequestArguments args) {
		args.setSupportsVariableType(false);
	}

	@Test
	public void doesNotShowVariableType() throws Exception {
		adapter.setBreakpoints(createBreakpoints(createBreakpoint(5))).get();
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		Variable tupleVariable = topVariables.get("t");
		assertNull(tupleVariable.getType());

		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}
	
}
