/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dt.debug;

import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.epsilon.common.dt.console.EpsilonConsole;
import org.eclipse.lsp4e.debug.debugmodel.DSPDebugTarget;
import org.eclipse.lsp4e.debug.debugmodel.TransportStreams;
import org.eclipse.lsp4j.debug.OutputEventArguments;
import org.eclipse.lsp4j.debug.OutputEventArgumentsCategory;

@SuppressWarnings("restriction")
class EpsilonConsoleDebugTarget extends DSPDebugTarget {
	protected EpsilonConsoleDebugTarget(ILaunch launch, Supplier<TransportStreams> streamsSupplier, Map<String, Object> dspParameters) {
		super(launch, streamsSupplier, dspParameters);
	}

	@Override
	public void output(OutputEventArguments args) {
		String output = args.getOutput();
		if (args.getCategory() == null || OutputEventArgumentsCategory.CONSOLE.equals(args.getCategory())
				|| OutputEventArgumentsCategory.STDOUT.equals(args.getCategory())) {
			EpsilonConsole.getInstance().getInfoStream().append(output);
		} else if (OutputEventArgumentsCategory.STDERR.equals(args.getCategory())) {
			EpsilonConsole.getInstance().getErrorStream().append(output);
		}
	}
}