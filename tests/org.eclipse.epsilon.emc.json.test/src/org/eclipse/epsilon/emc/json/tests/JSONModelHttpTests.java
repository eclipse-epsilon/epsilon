/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.json.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.json.JsonModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JSONModelHttpTests {

	private final class StaticJSONWithAuthHandler extends AbstractHandler {
		private final JSONObject root;
		private String username, password;

		private StaticJSONWithAuthHandler(JSONObject root, String user, String password) {
			this.root = root;
			this.username = user;
			this.password = password;
		}

		@Override
		public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			baseRequest.setHandled(true);

			final String expectedChallengePrefix = "Basic ";
			final String challengeResponse = baseRequest.getHeader(HttpHeader.AUTHORIZATION.name());
			if (challengeResponse == null || !challengeResponse.startsWith(expectedChallengePrefix)) {
				requestAuth(response);
			} else {
				String userColonPassword = new String(
					Base64.getDecoder().decode(challengeResponse.substring(expectedChallengePrefix.length())), StandardCharsets.UTF_8);

				String[] parts = userColonPassword.split(":");
				String challengeUser = parts[0];
				String challengePassword = parts[1];

				if (!username.equals(challengeUser) || !password.equals(challengePassword)) {
					response.setStatus(HttpStatus.NOT_FOUND_404);
				} else {
					response.getWriter().print(JSONValue.toJSONString(root));
				}
			}
		}

		private void requestAuth(HttpServletResponse response) {
			response.setStatus(HttpStatus.UNAUTHORIZED_401);
			response.setHeader("WWW-Authenticate",
				String.format("Basic realm=localhost:%d, charset=\"UTF-8\"", serverPort));
		}
	}

	private static class StaticJSONHandler extends AbstractHandler {
		private final String responseText;

		public StaticJSONHandler(Object json) {
			this.responseText = JSONValue.toJSONString(json);
		}

		@Override
		public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			baseRequest.setHandled(true);
			response.getWriter().print(responseText);
		}
	}

	private Server server;
	private String serverUri;
	private int serverPort;

	@Before
	public void setUp() {
		server = new Server(0);
	}

	@After
	public void tearDown() throws Exception {
		server.stop();
		server.join();
	}

	@SuppressWarnings("unchecked") 
	@Test
	public void canLoadHttp() throws Exception {
		JSONObject root = new JSONObject();
		root.put("hello", "world");
		serve(new StaticJSONHandler(root));

		try (JsonModel model = new JsonModel()) {
			model.setName("M");
			model.setUri(serverUri);
			model.setReadOnLoad(true);
			model.setStoredOnDisposal(false);
			model.load();

			assertEquals(root, model.getRoot());
		}
	}

	@SuppressWarnings("unchecked") 
	@Test
	public void canLoadHttpWithProperties() throws Exception {
		JSONObject root = new JSONObject();
		root.put("hello", "world");
		serve(new StaticJSONHandler(root));

		try (JsonModel model = new JsonModel()) {
			model.setName("M");

			StringProperties props = new StringProperties();
			props.put(JsonModel.PROPERTY_URI, serverUri);
			props.put(JsonModel.PROPERTY_READONLOAD, "true");
			props.put(JsonModel.PROPERTY_STOREONDISPOSAL, "false");
			model.load(props);

			assertEquals(root, model.getRoot());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void failureToAuthenticateIsReported() throws Exception {
		JSONObject root = new JSONObject();
		root.put("hello", "world");
		serve(new StaticJSONWithAuthHandler(root, "myuser", "mypassword"));

		try (JsonModel model = new JsonModel()) {
			model.setName("M");
			model.setUri(serverUri);
			model.setReadOnLoad(true);
			model.setStoredOnDisposal(false);
			model.load();

			fail("Expected a model loading exception");
		} catch (EolModelLoadingException ex) {
			// pass!
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void canAuthenticate() throws Exception {
		JSONObject root = new JSONObject();
		root.put("hello", "world");

		final String username = "myuser";
		final String password = "mypassword";
		serve(new StaticJSONWithAuthHandler(root, username, password));

		try (JsonModel model = new JsonModel()) {
			model.setName("M");
			model.setUri(serverUri);
			model.setReadOnLoad(true);
			model.setStoredOnDisposal(false);
			model.setUsername(username);
			model.setPassword(password);
			model.load();

			assertEquals(root, model.getRoot());
		}
	}

	@Test
	public void cannotStoreUri() throws Exception {
		serve(new StaticJSONHandler(new JSONObject()));

		JsonModel model = new JsonModel();
		model.setName("M");
		model.setUri(serverUri);
		model.setReadOnLoad(false);
		model.setStoredOnDisposal(true);

		model.load();

		try {
			model.close();
			fail("Should have thrown an UnsupportedOperationException");
		} catch (UnsupportedOperationException ex) {
			// ok!
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void followsRedirects() throws Exception {
		JSONObject root = new JSONObject();
		root.put("hello", "world");

		serve(new AbstractHandler() {
			@Override
			public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
				baseRequest.setHandled(true);
				if ("/model".equals(target)) {
					response.getWriter().print(JSONValue.toJSONString(root));
				} else if ("/".equals(target)) {
					response.setStatus(HttpStatus.TEMPORARY_REDIRECT_307);
					response.setHeader(HttpHeader.LOCATION.name(), serverUri + "/model");
				} else {
					response.setStatus(HttpStatus.NOT_FOUND_404);
				}
			}
		});

		try (JsonModel model = new JsonModel()) {
			model.setName("M");
			model.setUri(serverUri);
			model.setReadOnLoad(true);
			model.setStoredOnDisposal(false);
			model.load();

			assertEquals(root, model.getRoot());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void usesHeaders() throws Exception {
		JSONObject root = new JSONObject();
		root.put("hello", "world");

		final String authValue = "Bearer TOKEN";
		serve(new AbstractHandler() {
			@Override
			public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
				baseRequest.setHandled(true);
				if (!authValue.equals(baseRequest.getHeader(HttpHeader.AUTHORIZATION.name()))) {
					response.setStatus(HttpStatus.FORBIDDEN_403);
				} else {
					response.getWriter().print(JSONValue.toJSONString(root));
				}
			}
		});

		try (JsonModel model = new JsonModel()) {
			model.setName("M");
			model.setUri(serverUri);
			model.setReadOnLoad(true);
			model.setStoredOnDisposal(false);
			model.setHeader(HttpHeader.AUTHORIZATION.name(), authValue);
			model.load();

			assertEquals(root, model.getRoot());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void usesHeadersThroughProperties() throws Exception {
		JSONObject root = new JSONObject();
		root.put("hello", "world");

		final String authValue = "Bearer TOKEN";
		final String expectedMimeType = "application/vnd.github+json";
		serve(new AbstractHandler() {
			@Override
			public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
				baseRequest.setHandled(true);
				if (!authValue.equals(baseRequest.getHeader(HttpHeader.AUTHORIZATION.name()))) {
					response.setStatus(HttpStatus.FORBIDDEN_403);
				} else if (!expectedMimeType.equals(baseRequest.getHeader(HttpHeader.ACCEPT.name()))) {
					response.setStatus(HttpStatus.BAD_REQUEST_400);
				} else {
					response.getWriter().print(JSONValue.toJSONString(root));
				}
			}
		});

		try (JsonModel model = new JsonModel()) {
			model.setName("M");

			StringProperties props = new StringProperties();
			props.put(JsonModel.PROPERTY_URI, serverUri);
			props.put(JsonModel.PROPERTY_READONLOAD, "true");
			props.put(JsonModel.PROPERTY_STOREONDISPOSAL, "false");
			props.put(JsonModel.PROPERTY_HEADER_PREFIX + "0", "Authorization: " + authValue);
			props.put(JsonModel.PROPERTY_HEADER_PREFIX + "1", "Accept: " + expectedMimeType);

			model.load(props);

			assertEquals(root, model.getRoot());
		}
	}

	private void serve(Handler handler) throws Exception {
		server.setHandler(handler);
		server.start();

		serverPort = ((ServerConnector) server.getConnectors()[0]).getLocalPort();
		serverUri = "http://localhost:" + serverPort;
	}
}
