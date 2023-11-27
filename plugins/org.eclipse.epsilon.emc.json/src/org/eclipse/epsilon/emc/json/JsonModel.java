/*******************************************************************************
 * Copyright (c) 2022-2023 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Sina Madani - initial API and implementation
 *     Antonio Garcia-Dominguez - complete implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.models.CachedModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.json.simple.JSONValue;

public class JsonModel extends CachedModel<Object> {

	public static final String JSON_ARRAY_TYPE = "JSONArray";
	public static final String JSON_OBJECT_TYPE = "JSONObject";

	public static final String PROPERTY_FILE = "file";
	public static final String PROPERTY_URI = "uri";
	public static final String PROPERTY_USERNAME = "username";
	public static final String PROPERTY_PASSWORD = "password";

	/* We use header0, header1, and so on to represent the values of various HTTP headers. */
	public static final String PROPERTY_HEADER_PREFIX = "header";
	/* Separator used between the name and the value of the header. */
	public static final char PROPERTY_HEADER_SEPARATOR = ':';

	protected File file;
	protected String uri;

	protected String username;
	protected String password;
	protected Map<String, String> headers = new HashMap<>();

	/* ONLY SET THIS FIELD VIA setRoot() */
	protected Object _root;

	public JsonModel() {
		propertyGetter = new JsonPropertyGetter();
		propertySetter = new JsonPropertySetter();
	}

	public Object getRoot() {
		return _root;
	}

	public void setRoot(Object root) {
		if (this._root == root) {
			// This is already the root: do nothing
			return;
		}
		
		if (this._root instanceof Contained) {
			((Contained) this._root).removeContainer(this);
		}

		this._root = root;

		if (this._root instanceof Contained) {
			((Contained) this._root).addContainer(this);
		}
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTypeNameOf(Object instance) {
		if (instance instanceof JsonModelObject) {
			return JSON_OBJECT_TYPE;
		} else if (instance instanceof JsonModelArray) {
			return JSON_ARRAY_TYPE;
		} else {
			return instance.getClass().getSimpleName();
		}
	}

	@Override
	public Object getElementById(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getElementId(Object instance) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setElementId(Object instance, String newId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean owns(Object instance) {
		if (instance instanceof Contained) {
			Contained contained = (Contained) instance;

			if (contained.isContainedBy(this)) {
				return true;
			} else if (contained.getContainers().isEmpty() && contained instanceof HasCreatorModel) {
				return ((HasCreatorModel) contained).getCreatorModel() == this;
			}
		}

		return instance instanceof Contained && ((Contained) instance).isContainedBy(this); 
	}

	@Override
	public boolean isInstantiable(String type) {
		return hasType(type);
	}

	@Override
	public boolean hasType(String type) {
		return JSON_OBJECT_TYPE.equals(type) || JSON_ARRAY_TYPE.equals(type);
	}

	@Override
	public boolean store(String location) {
		try {
			FileUtil.setFileContents(JSONValue.toJSONString(getRoot()), new File(location));
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean store() {
		if (file != null) {
			return store(file.getAbsolutePath());
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	protected Collection<Object> allContentsFromModel() {
		List<Object> objects = new ArrayList<>();
		addAllContents(getRoot(), objects,
			(o) -> o instanceof JsonModelObject || o instanceof JsonModelArray);
		return objects;
	}

	private void addAllContents(Object current, List<Object> objects, Predicate<Object> addFilter) {
		if (addFilter.test(current)) {
			objects.add(current);
		}

		if (current instanceof JsonModelObject) {
			for (Object value : ((JsonModelObject) current).values()) {
				addAllContents(value, objects, addFilter);
			}
		} else if (current instanceof JsonModelArray) {
			for (Object child : (JsonModelArray) current) {
				addAllContents(child, objects, addFilter);
			}
		}
	}

	@Override
	protected Collection<Object> getAllOfTypeFromModel(String type) throws EolModelElementTypeNotFoundException {
		List<Object> objects = new ArrayList<>();

		if (JSON_OBJECT_TYPE.equals(type)) {
			addAllContents(getRoot(), objects, o -> o instanceof JsonModelObject);
		} else if (JSON_ARRAY_TYPE.equals(type)) {
			addAllContents(getRoot(), objects, o -> o instanceof JsonModelArray);
		} else {
			throw new EolModelElementTypeNotFoundException(getName(), type);
		}

		return objects;
	}

	@Override
	protected Collection<Object> getAllOfKindFromModel(String kind) throws EolModelElementTypeNotFoundException {
		return getAllOfTypeFromModel(kind);
	}

	@Override
	protected Object createInstanceInModel(String type) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		if (JSON_OBJECT_TYPE.equals(type)) {
			return new JsonModelObject(this);
		} else if (JSON_ARRAY_TYPE.equals(type)) {
			return new JsonModelArray(this);
		}

		throw new EolModelElementTypeNotFoundException(this.getName(), type);
	}

	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		super.load(properties, resolver);

		String filePath = properties.getProperty(JsonModel.PROPERTY_FILE);

		if (filePath != null && filePath.trim().length() > 0) {
			file = new File(resolver.resolve(filePath));
		} else {
			uri = properties.getProperty(JsonModel.PROPERTY_URI);
			username = properties.getProperty(JsonModel.PROPERTY_USERNAME);
			password = properties.getProperty(JsonModel.PROPERTY_PASSWORD);

			for (Entry<Object, Object> e : properties.entrySet()) {
				if (e.getKey().toString().startsWith(PROPERTY_HEADER_PREFIX)) {
					String nameValue = e.getValue().toString();

					int firstColon = nameValue.indexOf(PROPERTY_HEADER_SEPARATOR);
					if (firstColon == -1) {
						throw new IllegalArgumentException(String.format(
							"Could not find separator '%s' in value of header property %s",
							PROPERTY_HEADER_SEPARATOR, e.getKey()));
					}

					String name = nameValue.substring(0, firstColon).trim();
					String value = nameValue.substring(firstColon + 1).trim();
					headers.put(name, value);
				}
				
			}
		}

		load();
	}

	@Override
	protected void loadModel() throws EolModelLoadingException {
		if (!readOnLoad)
			return;

		try {
			if (uri != null) {
				URI parsedUri = new URI(uri);

				if ("http".equals(parsedUri.getScheme()) || "https".equals(parsedUri.getScheme())) {
					loadModelViaHTTP(parsedUri);
				} else {
					// Not HTTP, so we'll rely on Java's standard URL handling (covers file:// and jar:// by default) 
					try (InputStream is = parsedUri.toURL().openStream(); Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
						setRoot(deepClone(JSONValue.parse(reader)));
					}
				}
			} else if (file != null) {
				try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
					setRoot(deepClone(JSONValue.parse(reader)));
				}
			} else {
				throw new IllegalStateException("Neither URI nor file path have been set");
			}
		} catch (Exception ex) {
			throw new EolModelLoadingException(ex, this);
		}
	}

	private void loadModelViaHTTP(URI parsedUri) throws IOException, ClientProtocolException, EolRuntimeException {
		HttpClientBuilder builder = HttpClients.custom();
		if (username != null) {
			BasicCredentialsProvider creds = new BasicCredentialsProvider();
			creds.setCredentials(new AuthScope(parsedUri.getHost(), parsedUri.getPort()),
					new UsernamePasswordCredentials(username, password));

			builder.setDefaultCredentialsProvider(creds);
		}

		try (CloseableHttpClient httpClient = builder.build()) {
			HttpGet httpGet = new HttpGet(uri);
			for (Entry<String, String> e : headers.entrySet()) {
				httpGet.setHeader(e.getKey(), e.getValue());
			}

			HttpResponse httpResponse = httpClient.execute(httpGet);

			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new EolRuntimeException(
					String.format("HTTP request to %s returned a non-200 status code: %d",
							uri,
							httpResponse.getStatusLine().getStatusCode()));
			}

			HttpEntity responseEntity = httpResponse.getEntity();

			Reader reader = new InputStreamReader(responseEntity.getContent(), StandardCharsets.UTF_8);
			setRoot(deepClone(JSONValue.parse(reader)));
		}
	}

	@SuppressWarnings("unchecked")
	public static Object deepClone(Object parsed) {
		if (parsed instanceof Map) {
			JsonModelObject obj = new JsonModelObject();
			for (Entry<String, Object> e : ((Map<String, Object>) parsed).entrySet()) {
				obj.put(e.getKey(), deepClone(e.getValue()));
			}
			return obj;
		} else if (parsed instanceof Iterable) {
			JsonModelArray arr = new JsonModelArray();
			for (Object e : (Iterable<Object>) parsed) {
				arr.add(deepClone(e));
			}
			return arr;
		} else {
			return parsed;
		}
	}

	@Override
	protected void disposeModel() {
		setRoot(null);
	}

	@Override
	protected boolean deleteElementInModel(Object instance) throws EolRuntimeException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Object getCacheKeyForType(String type) throws EolModelElementTypeNotFoundException {
		return type;
	}

	@Override
	protected Collection<String> getAllTypeNamesOf(Object instance) {
		return Collections.singleton(getTypeNameOf(instance));
	}

	@Override
	public boolean isLoaded() {
		return getRoot() != null;
	}

	public String getHeader(String name) {
		return headers.get(name);
	}

	public void setHeader(String name, String value) {
		headers.put(name, value);
	}

	@Override
	public String toString() {
		return "JsonModel [name=" + name + "]";
	}

	/**
	 * Convenience method for loading the JSON content directly from a string,
	 * instead of loading it from a file or URI. The model will consider itself
	 * loaded.
	 */
	public void setJsonContent(String s) {
		setRoot(deepClone(JSONValue.parse(s)));
	}

}
