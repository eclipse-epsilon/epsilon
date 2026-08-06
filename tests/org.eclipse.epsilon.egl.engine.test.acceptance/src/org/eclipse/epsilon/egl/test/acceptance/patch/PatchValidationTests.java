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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.epsilon.egl.patch.Patch;
import org.junit.Test;

/**
 * 
 * @since 1.6
 */
public class PatchValidationTests {
	
	public void testInvalidPatchLine() {
		assertFalse(new Patch("foo").isValid());
	}
	
	@Test
	public void testComment() {
		assertTrue(new Patch("#Comment", "1", "#Comment").isValid());
	}
	
	@Test
	public void testWildcardInvalidAtTheBeginning() {
		assertFalse(new Patch("...", "1").isValid());
	}
	
	@Test
	public void testWildcardInvalidAtTheEnd() {
		assertFalse(new Patch("1", "...").isValid());
	}

	@Test
	public void testNoConsecutiveWildcards() {
		assertFalse(new Patch("1", "...", "...", "2").isValid());
	}
	
	
}
