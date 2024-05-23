/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.lsp;

import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.services.WorkspaceService;

class DummyWorkspaceService implements WorkspaceService {

	@Override
	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
		// nothing to do
	}

	@Override
	public void didChangeConfiguration(DidChangeConfigurationParams params) {
		// nothing to do
	}

}