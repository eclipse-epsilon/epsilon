package org.eclipse.epsilon.examples.eol.streamhandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.spi.URLStreamHandlerProvider;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * <p>
 * Stream handler provider which first resolves URLs in the {@link #PROTOCOL} by
 * looking up the path in an in-memory map, and then falls back to the file with
 * the same path if no entry exists.
 * </p>
 * 
 * <p>
 * This is designed to be used with {@link URL} via the {@link ServiceLoader}
 * mechanism. The <code>resources/META-INF/services</code> folder contains a file
 * that registers this stream handler provider.
 * </p>
 * 
 * <p>
 * To register entries, retrieve the singleton {@link Registry} via
 * {@link Registry#getInstance()} and then use its
 * {@link Registry#putCode(String, String)} method.
 * </p>
 */
public class SingletonMapStreamHandlerProvider extends URLStreamHandlerProvider {

	public static final String PROTOCOL = "mapentry";

	public static class Registry {
		private static Registry INSTANCE;
		private Registry() {}

		public synchronized static Registry getInstance() {
			if (INSTANCE == null) {
				INSTANCE = new Registry();
			}
			return INSTANCE;
		}

		private final Map<String, String> PATH_TO_CODE = new HashMap<>();

		public String getCode(String path) {
			return PATH_TO_CODE.get(path);
		}

		public void putCode(String path, String code) {
			PATH_TO_CODE.put(path, code);
		}

		public void removeCode(String path) {
			PATH_TO_CODE.remove(path);
		}

		public void clear() {
			PATH_TO_CODE.clear();
		}
	}

	/**
	 * Dummy URL connection fed by an in-memory map entry.
	 */
	protected static class MapEntryURLConnection extends URLConnection {
		private final byte[] content;

		protected MapEntryURLConnection(URL url, String content) {
			super(url);
			this.content = content.getBytes(StandardCharsets.UTF_8);
		}

		@Override
		public void connect() throws IOException {
			// nothing to do!
		}

		@Override
		public String getContentType() {
			return "text/plain";
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(content);
		}

		@Override
		public long getContentLengthLong() {
			return content.length;
		}

		@Override
		public String getContentEncoding() {
			return StandardCharsets.UTF_8.name();
		}
	}


	@Override
	public URLStreamHandler createURLStreamHandler(String protocol) {
		if (!PROTOCOL.equals(protocol)) {
			return null;
		}

		return new URLStreamHandler() {
			@Override
			protected URLConnection openConnection(URL u) throws IOException {
				String code = Registry.getInstance().getCode(u.getPath());
				if (code != null) {
					return new MapEntryURLConnection(u, code);
				} else {
					try {
						// Fall back to a file URL if we have no entry for this
						return new URI("file", u.getHost(), u.getPath(), null).toURL().openConnection();
					} catch (URISyntaxException e) {
						e.printStackTrace();
						return null;
					}
				}
			}
		};
	}

}
