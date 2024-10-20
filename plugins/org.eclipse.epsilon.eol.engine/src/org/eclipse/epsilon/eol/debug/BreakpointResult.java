/*********************************************************************
 * Copyright (c) 2012-2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.debug;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.epsilon.common.module.IModule;

/**
 * Represents the result of trying to set a breakpoint on a program.
 * To create instances, use one of the provided static methods.
 *
 * @see #failed(BreakpointRequest)
 * @see #pending(BreakpointRequest)
 * @see #verified(BreakpointRequest, int)
 */
public class BreakpointResult {

	public static final int NOT_FOUND = -1;

	private final BreakpointRequest request;

	private final IModule module;
	private final URI moduleURI;
	private final int line;
	private final int column;
	private final String condition;
	private final BreakpointState state;

	private BreakpointResult(BreakpointRequest request, IModule module, URI moduleURI, int line, int column, String condition, BreakpointState state) {
		this.request = request;
		this.module = module;
		this.moduleURI = moduleURI;
		this.line = line;
		this.column = column;
		this.condition = condition;
		this.state = state;
	}

	public static BreakpointResult failed(BreakpointRequest request) {
		return new BreakpointResult(request, null, null, NOT_FOUND, NOT_FOUND, request.getCondition(), BreakpointState.FAILED);
	}

	public static BreakpointResult verified(BreakpointRequest request, IModule module, int actualLine, Integer actualColumn) {
		return new BreakpointResult(request, module, module.getSourceUri(), actualLine, actualColumn, request.getCondition(), BreakpointState.VERIFIED);
	}

	public static BreakpointResult pending(BreakpointRequest request) {
		final Path requestPath = Paths.get(request.getPath());
		final URI requestURI = requestPath.toUri();
		return new BreakpointResult(request, null, requestURI, request.getLine(), request.getColumn(), request.getCondition(), BreakpointState.PENDING);
	}

	public BreakpointRequest getRequest() {
		return request;
	}

	public IModule getModule() {
		return module;
	}

	public URI getModuleURI() {
		return moduleURI;
	}

	public int getLine() {
		return line;
	}

	public Integer getColumn() {
		return column;
	}

	public BreakpointState getState() {
		return state;
	}

	public String getCondition() {
		return condition;
	}

}
