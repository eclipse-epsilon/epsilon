/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.egl.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.eclipse.epsilon.egl.merge.partition.CompositePartitioner;
import org.junit.Test;

public class TestDefaultContentTypeRepository {

	@Test
	public void matchesDefaultConfigXml() throws PersistenceException {
		final Map<String, CompositePartitioner> fromXml = new XMLConfigFileReader()
			.read(XMLContentTypeRepository.class.getResourceAsStream("DefaultConfig.xml"));
		final ContentTypeRepository inCode = new DefaultContentTypeRepository();

		assertFalse(fromXml.isEmpty());
		for (Map.Entry<String, CompositePartitioner> contentType : fromXml.entrySet()) {
			assertNotNull(contentType.getKey(), inCode.partitionerFor(contentType.getKey()));
			assertEquals(contentType.getKey(), contentType.getValue(), inCode.partitionerFor(contentType.getKey()));
		}
	}
}
