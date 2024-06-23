/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.egl.internal;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.debug.BreakpointRequest;
import org.eclipse.epsilon.eol.debug.BreakpointResult;
import org.eclipse.epsilon.eol.debug.EolDebugger;

class EglDebugger extends EolDebugger {
	private static final Logger LOGGER = Logger.getLogger(EglDebugger.class.getName());

	@Override
	public BreakpointResult verifyBreakpoint(BreakpointRequest request) {
		try {
			// Step 1. Map from request path (likely a file opened in the IDE) to an IEolModule
			IModule resolvedModule = resolveModule(request.getUriToPathMappings(), request.getPath());

			// Step 2. Try to resolve (while considering some EGL internals)
			if (resolvedModule != null) {
				int actualLine = findFirstLineGreaterThanOrEqualTo(resolvedModule, request.getLine());
				if (actualLine < 1) {
					/*
					 * For EGL, we'll need to fall back to the main() block, as getChildren() does
					 * not list anything.
					 */
					final IEolModule eolModule = (IEolModule) resolvedModule;
					actualLine = findFirstLineGreaterThanOrEqualTo(eolModule.getMain(), request.getLine());
				}
				if (actualLine >= 1) {
					return BreakpointResult.verified(request, resolvedModule, actualLine);
				}
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Failed to verify breakpoint due to error: " + e.getMessage(), e);
		}

		return BreakpointResult.failed(request);
	}
}