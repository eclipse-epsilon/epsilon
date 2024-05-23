package org.eclipse.epsilon.lsp.test;

import java.util.concurrent.CompletableFuture;

import org.eclipse.epsilon.lsp.EpsilonLanguageServer;
import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.services.LanguageClient;
import org.junit.Test;

public class SyntaxCheckTest {

	protected class TestClient implements LanguageClient {
		@Override
		public void telemetryEvent(Object object) {
			// nothing for now
		}

		@Override
		public void publishDiagnostics(PublishDiagnosticsParams diagnostics) {
			// nothing for now
		}

		@Override
		public void showMessage(MessageParams messageParams) {
			// nothing for now
		}

		@Override
		public CompletableFuture<MessageActionItem> showMessageRequest(ShowMessageRequestParams requestParams) {
			// nothing for now
			return null;
		}

		@Override
		public void logMessage(MessageParams message) {
			// TODO Auto-generated method stub
			
		}
	}

	@Test
	public void goodEOL() {
		final EpsilonLanguageServer server = new EpsilonLanguageServer();
		final TestClient testClient = new TestClient();
		server.connect(testClient);

		
	}

}
