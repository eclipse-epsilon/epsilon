/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.egl.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.epsilon.egl.merge.partition.CommentBlockPartitioner;
import org.eclipse.epsilon.egl.merge.partition.CompositePartitioner;

/**
 * The content types of DefaultConfig.xml, defined in code so that creating an
 * EGL context does not need an XML parser. Further configuration can still be
 * loaded from XML with {@link #load(InputStream)}.
 *
 * @since 2.10
 */
public class DefaultContentTypeRepository implements ContentTypeRepository {

	private Map<String, CompositePartitioner> partitioners = new HashMap<>();

	public DefaultContentTypeRepository() {
		// Keep in sync with DefaultConfig.xml (a missing endsWith attribute is null)
		partitioners.put("Java", new CompositePartitioner(
			new CommentBlockPartitioner("//", null),
			new CommentBlockPartitioner("/*", "*/")));
		partitioners.put("HTML", new CompositePartitioner(new CommentBlockPartitioner("<!--", "-->")));
		partitioners.put("VisualBasic", new CompositePartitioner(new CommentBlockPartitioner("'", null)));
		partitioners.put("EGL", new CompositePartitioner(new CommentBlockPartitioner("--", null)));
		partitioners.put("Perl", new CompositePartitioner(new CommentBlockPartitioner("#", null)));
	}

	@Override
	public void load(InputStream stream) throws PersistenceException {
		partitioners = new XMLConfigFileReader().read(stream);
	}

	@Override
	public CompositePartitioner partitionerFor(String contentType) {
		return partitioners.get(contentType);
	}
}
