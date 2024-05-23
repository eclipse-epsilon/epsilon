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