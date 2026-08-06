/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.test.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

public class URIReader {

	private final URI uri;
	
	public URIReader(URI uri) {
		this.uri = uri;
	}

	public String getContents() throws IOException {
		try (BufferedReader reader   = new BufferedReader(new InputStreamReader(URIConverter.INSTANCE.createInputStream(uri)))) {
			final StringBuilder contents = new StringBuilder();

			String line;
			while ((line = reader.readLine()) != null) {
				contents.append(line).append('\n');
			}

			return contents.toString();
		}
	}
}
