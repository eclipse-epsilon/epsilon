package org.eclipse.epsilon.eol.dap.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.Callback;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.junit.Test;

/**
 * Tests for debugging an EOL script loaded from an non-file URL. This test is
 * needed in order to reliably test this scenario, as {@link ClasspathEolTest}
 * works differently when run from Eclipse and when run from Tycho.
 */
public class HttpEolTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "01-hello.eol");
	private String serverUrl;

	@Override
	protected void setupModule() throws Exception {
		Server server = new Server(0);
		server.setHandler(new Handler.Abstract() {
			@Override
			public boolean handle(Request request, Response response, Callback callback) throws Exception {
				response.setStatus(HttpStatus.OK_200);
				response.getHeaders().put(HttpHeader.CONTENT_TYPE, "text/plain; charset=UTF-8");
				final byte[] eolBytes = Files.readAllBytes(SCRIPT_FILE.toPath());
				response.write(true, ByteBuffer.wrap(eolBytes), callback);
				return true;
			}
		});
		server.start();

		int serverPort = ((ServerConnector) server.getConnectors()[0]).getLocalPort();
		this.serverUrl = "http://localhost:" + serverPort + "/";
		String moduleUrl = serverUrl + "01-hello.eol";

		this.module = new EolModule();
		module.parse(new URL(moduleUrl));
	}

	@Override
	protected void setupAdapter() throws Exception {
		adapter.getUriToPathMappings().put(new URI(this.serverUrl),
			SCRIPT_FILE.getParentFile().toPath());
	}

	@Test
	public void breakThenContinue() throws Exception {
		SetBreakpointsResponse breakpoints = adapter.setBreakpoints(
			createBreakpoints(SCRIPT_FILE.getCanonicalPath(), createBreakpoint(1))).get();

		assertTrue("The file-based breakpoint was mapped to a script loaded from the classpath",
			breakpoints.getBreakpoints()[0].isVerified());
		assertEquals("The file-based breakpoint used the URI mappings",
			SCRIPT_FILE.getCanonicalPath(),
			breakpoints.getBreakpoints()[0].getSource().getPath());

		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		StackTraceResponse stackTrace = getStackTrace();
		assertEquals("The stack trace  used the URI mappings",
				SCRIPT_FILE.getCanonicalPath(),
				stackTrace.getStackFrames()[0].getSource().getPath());

		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}

	
}
