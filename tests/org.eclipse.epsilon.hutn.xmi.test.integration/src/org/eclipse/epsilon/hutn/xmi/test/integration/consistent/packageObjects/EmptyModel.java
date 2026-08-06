/*******************************************************************************
 * Copyright (c) 2008 The University of York.
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
package org.eclipse.epsilon.hutn.xmi.test.integration.consistent.packageObjects;

import static org.junit.Assert.assertTrue;

import org.eclipse.epsilon.hutn.xmi.HutnXmiBridgeException;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmptyModel extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() throws HutnXmiBridgeException {
		integrationTest("<?xml version=\"1.0\" encoding=\"ASCII\"?>\n");
	}
	
	@Test
	public void hasNoNsUris() {
		assertTrue(spec.getNsUris().isEmpty());
	}
	
	@Test
	public void hasNoPackageObjects() {
		assertTrue(spec.getObjects().isEmpty());
	}
}
