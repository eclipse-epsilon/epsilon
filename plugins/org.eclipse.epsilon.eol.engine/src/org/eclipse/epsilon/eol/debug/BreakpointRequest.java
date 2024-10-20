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
import java.util.Map;
import java.util.Objects;

/**
 * Represents a request to set a breakpoint in a given line.
 */
public class BreakpointRequest {

	private final Map<URI, Path> uriToPathMappings;
	private final String path;
	private final int line;
	private final int column;
	private final String condition;

	/**
	 * Constructs a new request.
	 * 
	 * @param uriToPathMappings Mappings from module URIs to filesystem paths.
	 *                          Useful when debugging code that is loaded from a
	 *                          non-file URI. The URIs must have a trailing slash.
	 * @param path              Absolute path of the IDE file with the breakpoint.
	 * @param line              1-based index of the line of the file where the
	 *                          breakpoint has been set.
	 * @param column            0-based index of the column within the line where
	 *                          the breakpoint has been set.
	 * @param condition         EOL-based condition that must hold in order to stop
	 *                          at the line. Can be <code>null</code> if no
	 *                          condition is to be used.
	 */
	public BreakpointRequest(Map<URI, Path> uriToPathMappings, String path, int line, int column, String condition) {
		this.uriToPathMappings = uriToPathMappings;
		this.path = path;
		this.line = line;
		this.column = column;
		this.condition = condition;
	}

	public String getPath() {
		return path;
	}

	public int getLine() {
		return line;
	}

	public Map<URI, Path> getUriToPathMappings() {
		return uriToPathMappings;
	}

	public String getCondition() {
		return condition;
	}

	public Integer getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		return Objects.hash(column, line, path, uriToPathMappings);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BreakpointRequest other = (BreakpointRequest) obj;
		return Objects.equals(column, other.column) && line == other.line && Objects.equals(path, other.path)
				&& Objects.equals(uriToPathMappings, other.uriToPathMappings);
	}

}
