package org.eclipse.epsilon.lsp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.epsilon.lsp.EpsilonLanguageServer;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class SyntaxCheckTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    private static final String GOOD_SYNTAX_EOL_PATH = "../org.eclipse.epsilon.lsp.test/epsilon/01-good-syntax.eol";

	protected class TestClient implements LanguageClient {
		protected CompletableFuture<PublishDiagnosticsParams> publishedDiagnostics = new CompletableFuture<>();

		@Override
		public void telemetryEvent(Object object) {
			// nothing for now
		}

		@Override
		public void publishDiagnostics(PublishDiagnosticsParams diagnostics) {
			this.publishedDiagnostics.complete(diagnostics);
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
			// nothing for now
		}
	}

	protected TextDocumentService docService;
	protected TestClient testClient;

	@Before
	public void setUp() {
		final EpsilonLanguageServer server = new EpsilonLanguageServer();
		testClient = new TestClient();
		server.connect(testClient);
		docService = server.getTextDocumentService();
	}

	@Test
	public void goodEOL() throws Exception {
		final int version = 1;
		final String fileURI = didOpen(new File(GOOD_SYNTAX_EOL_PATH), version);
		assertNoProblems(fileURI);
	}

	protected void assertNoProblems(final String fileURI) throws InterruptedException, ExecutionException {
		PublishDiagnosticsParams diagnostics = testClient.publishedDiagnostics.get();
		assertNotNull("Diagnostics should have been published",	diagnostics);
		assertEquals("The diagnostics should be related to the expected file", fileURI, diagnostics.getUri());
		assertEquals("No specific diagnostics should be listed", 0, diagnostics.getDiagnostics().size());
	}

	protected String didOpen(final File eolFile, final int version) throws IOException {
		final DidOpenTextDocumentParams openParameters = new DidOpenTextDocumentParams();
		final String fileURI = eolFile.getCanonicalFile().toURI().toString();
		final String fileContents = new String(Files.readAllBytes(eolFile.toPath()), StandardCharsets.UTF_8);
		openParameters.setTextDocument(new TextDocumentItem(fileURI, "eol", version, fileContents));
		docService.didOpen(openParameters);

		return fileURI;
	}

}
