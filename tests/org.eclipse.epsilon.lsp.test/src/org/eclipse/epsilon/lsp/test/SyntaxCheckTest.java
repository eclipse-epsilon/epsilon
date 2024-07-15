package org.eclipse.epsilon.lsp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.eclipse.epsilon.lsp.EpsilonLanguageServer;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.junit.After;
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

	protected EpsilonLanguageServer server;
	protected TextDocumentService docService;
	protected TestClient testClient;

	@Before
	public void setUp() throws Exception {
		server = new EpsilonLanguageServer();
		testClient = new TestClient();
		server.connect(testClient);

		InitializeResult initResults = server.initialize(new InitializeParams()).get(5, TimeUnit.SECONDS);
		assertNotNull("Initialisation should have completed in 5s", initResults);
		assertEquals("Should be using full-text synchronisation",
			TextDocumentSyncKind.Full, initResults.getCapabilities().getTextDocumentSync().getLeft());

		docService = server.getTextDocumentService();
	}

	@After
	public void tearDown() throws Exception {
		Object shutdownResult = server.shutdown().get();
		assertNull("LSP 3.17 spec: result of shutdown() should be null", shutdownResult);

		/*
		 * No .exit() as that implies exiting the process (which would impact the test.
		 */
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
