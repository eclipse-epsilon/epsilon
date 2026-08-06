/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.test.acceptance.patch;

import static org.junit.Assert.assertTrue;

import org.eclipse.epsilon.egl.patch.Patch;

/**
 * 
 * @since 1.6
 */
public class PatchTestsBase {
	
	protected Patch createPatch(String... lines) {
		Patch patch = new Patch(lines);
		assertTrue(patch.isValid());
		return patch;
	}
	
}
