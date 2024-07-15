/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.lsp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
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
import org.junit.rules.Timeout;

public class AbstractEpsilonLanguageServerTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10);

	protected EpsilonLanguageServer server;
	protected TextDocumentService docService;
	protected TestClient testClient;

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

	protected void assertPublishedEmptyDiagnostics(final String fileURI) throws Exception {
		// Get and reset the 'publishedDiagnostics' future
		PublishDiagnosticsParams diagnostics = testClient.publishedDiagnostics.get(5, TimeUnit.SECONDS);
		assertNotNull("Diagnostic should have been published within 5s", diagnostics);
		testClient.publishedDiagnostics = new CompletableFuture<PublishDiagnosticsParams>();

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