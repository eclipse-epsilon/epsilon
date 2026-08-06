/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.xmi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;


public class UriContentReader {

	private static final String NEW_LINE = System.getProperty("line.separator");
	
	private final URI uri;
	
	public UriContentReader(URI uri) {
		this.uri = uri;
	}
	
	private BufferedReader reader;
	
	public String readContents() throws HutnXmiBridgeException {
		try {
			prepareReader();
			
			return readContentsOfUri();
			
		} catch (MalformedURLException e) {
			throw new HutnXmiBridgeException(e);

		} catch (IOException e) {
			throw new HutnXmiBridgeException(e);
		
		} finally {
			closeReader();
		}
	}

	private void prepareReader() throws IOException, MalformedURLException {
		reader = new BufferedReader(new InputStreamReader(uri.toURL().openStream()));
	}
	
	private String readContentsOfUri() throws IOException {
		final StringBuilder contents = new StringBuilder();
		
		String line = null;
		while ((line = reader.readLine()) != null) {
			contents.append(line);
			contents.append(NEW_LINE);
		}
		
		return contents.toString();
	}

	private void closeReader() throws HutnXmiBridgeException {
		try {
			if (reader != null) {
				reader.close();
			}
			
		} catch (IOException e) {
			throw new HutnXmiBridgeException(e);
		}
	}
}
