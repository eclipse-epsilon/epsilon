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
package org.eclipse.epsilon.hutn.parse.spec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import java.util.stream.Collectors;
import org.eclipse.epsilon.hutn.model.hutn.NsUri;
import org.junit.Test;

public class HutnPreambleTests {

	@Test
	public void metamodel() throws Exception {	
		assertContainsNsUris("@Spec { metamodel { nsUri: \"families\" } }", "families");
	}
	
	@Test
	public void metamodelWithConfigFile() throws Exception {	
		assertContainsNsUris("@Spec { metamodel { nsUri: \"families\" configFile: \"families.config\" } }", "families");
	}
	
	@Test
	public void twoMetamodels() throws Exception {	
		assertContainsNsUris("@Spec { metamodel { nsUri: \"families\" configFile: \"families.config\" } metamodel2 { nsUri: \"pets\" configFile: \"pets.config\" } }", "families", "pets");
	}
	
	@Test
	public void modelFile() throws Exception {	
		assertContainsNsUris("@Spec { metamodel { nsUri: \"families\" configFile: \"families.config\" } { model { file: \"output.model\" } }", "families");
	}
	
	@Test
	public void noMetamodels() {
		assertEquals(0, preambleNsUris("@Spec {}").size());
	}
	
	@Test
	public void emptyPreamble() {
		assertEquals(0, preambleNsUris("").size());
	}
	
	@Test
	public void notAPreamble() {
		assertEquals(0, preambleNsUris("families { Family \"The Smiths\" { } }").size());
	}

	
	private static Collection<NsUri> preambleNsUris(String hutn) {
		return new HutnPreamble().process(hutn).getNsUris();
	}
	
	private static void assertContainsNsUris(String preambler, String... expecteds) throws Exception {
		final Collection<String> nspreStr = preambleNsUris(preambler)
			.stream().map(NsUri::getValue).collect(Collectors.toList());
		
		for (String expected : expecteds) {
			assertTrue(nspreStr.contains(expected));
		}
	}
}
