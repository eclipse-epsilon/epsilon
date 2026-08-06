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

import static org.junit.Assert.assertEquals;
import org.eclipse.epsilon.egl.patch.*;
import org.junit.Test;

/**
 * 
 * @since 1.6
 */
public class WildcardTests extends PatchTestsBase {
	
	@Test
	public void testKeepWildCard() {
		Patch patch = createPatch("1", "...", "4", "+5");
		TextBlock block = new TextBlock("1","2","3","4");
		assertEquals(new TextBlock("1", "2", "3", "4", "5"), patch.apply(block));
	}
	
	@Test
	public void testKeepWildCard2() {
		Patch patch = createPatch("1", "2", "...", "4", "+5");
		TextBlock block = new TextBlock("1","2","3","4");
		assertEquals(new TextBlock("1", "2", "3", "4", "5"), patch.apply(block));
	}
	
	@Test
	public void testKeepWildCard3() {
		Patch patch = createPatch("1", "2", "...", "+5");
		TextBlock block = new TextBlock("1","2","3","4");
		assertEquals(new TextBlock("1", "2", "3", "4", "5"), patch.apply(block));
	}
	
	@Test
	public void testKeepWildCard4() {
		Patch patch = createPatch("1", "2", "...", "4");
		TextBlock block = new TextBlock("1","2","3","4");
		assertEquals(new TextBlock("1", "2", "3", "4"), patch.apply(block));
	}
	
	@Test
	public void testKeepWildCard5() {
		Patch patch = createPatch("1", "2", "...", "-4");
		TextBlock block = new TextBlock("1","2","3","4");
		assertEquals(new TextBlock("1", "2", "3"), patch.apply(block));
	}
	
	@Test
	public void testKeepWildCard6() {
		Patch patch = createPatch("1", "...", "-4");
		TextBlock block = new TextBlock("1","2","3","4");
		assertEquals(new TextBlock("1", "2", "3"), patch.apply(block));
	}
	
	@Test
	public void testKeepWildCard7() {
		Patch patch = createPatch("+0", "...", "2");
		TextBlock block = new TextBlock("1","2", "3");
		assertEquals(new TextBlock("0", "1", "2", "3"), patch.apply(block));
	}
	
	@Test
	public void testRemoveWildCard() {
		Patch patch = createPatch("1", "---", "3");
		TextBlock block = new TextBlock("1", "2", "3");
		assertEquals(new TextBlock("1", "3"), patch.apply(block));
	}
	
	@Test
	public void testRemoveWildCard1() {
		Patch patch = createPatch("-1", "---", "3");
		TextBlock block = new TextBlock("1", "2", "3");
		assertEquals(new TextBlock("3"), patch.apply(block));
	}
	
	@Test
	public void testRemoveWildCard2() {
		Patch patch = createPatch("-1", "---", "-3");
		TextBlock block = new TextBlock("1", "2", "3");
		assertEquals(new TextBlock(), patch.apply(block));
	}
	
	@Test
	public void testRemoveWildCard3() {
		Patch patch = createPatch("-1", "---", "-3", "+4");
		TextBlock block = new TextBlock("1", "2", "3");
		assertEquals(new TextBlock("4"), patch.apply(block));
	}
	
	@Test
	public void testRemoveWildCard4() {
		Patch patch = createPatch("1", "---");
		TextBlock block = new TextBlock("1", "2", "3");
		assertEquals(new TextBlock("1"), patch.apply(block));
	}
	
}
