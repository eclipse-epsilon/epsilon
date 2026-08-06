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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.epsilon.egl.patch.Match;
import org.eclipse.epsilon.egl.patch.Patch;
import org.eclipse.epsilon.egl.patch.TextBlock;
import org.junit.Test;

/**
 * 
 * @since 1.6
 */
public class PatchTests extends PatchTestsBase {
	
	@Test
	public void testTextBlockEquals() {
		assertEquals(new TextBlock("foo","bar"), new TextBlock("foo", "bar"));
	}
	
	@Test
	public void testKeepsAndRemoves() {
		assertEquals(2, createPatch("+insert","keep","-remove").keepsAndRemoves().getLines().size());
	}
	
	@Test
	public void testOneMatch() {
		Patch patch = createPatch("do","+inject","+inject again","end do");
		TextBlock block = new TextBlock("irrelevant1","do","end do","irrelevant2");
		Match match = patch.match(block).get(0);
		assertEquals(1, patch.match(block).size());
		assertEquals(2, match.getStartLine().getNumber());
		assertEquals(3, match.getEndLine().getNumber());
	}
	
	@Test
	public void testNoMatch() {
		Patch patch = createPatch("if","+inject","else","end if");
		TextBlock block = new TextBlock("if","end if");
		assertEquals(0, patch.match(block).size());
	}
	
	@Test
	public void testMerge() {
		Patch patch = createPatch("if","+inject","end if");
		TextBlock block = new TextBlock("if","end if");
		assertEquals(new TextBlock("if", "inject", "end if"), patch.apply(block));
	}
	
	@Test
	public void testMergeWithComment() {
		Patch patch = createPatch("#Comment", "if","+inject","end if");
		TextBlock block = new TextBlock("if","end if");
		assertEquals(new TextBlock("if", "inject", "end if"), patch.apply(block));
	}
	
	@Test
	public void testMergeWithCommentAndEmptyLine() {
		Patch patch = createPatch("", "#Comment", "if", "", "+inject", "", "end if");
		TextBlock block = new TextBlock("if","end if");
		assertEquals(new TextBlock("if", "inject", "end if"), patch.apply(block));
	}
	
	@Test
	public void testMergeTwoInjects() {
		Patch patch = createPatch("if","+inject", "else", "+inject", "end if");
		TextBlock block = new TextBlock("if", "else", "end if");
		assertEquals(new TextBlock("if", "inject", "else", "inject", "end if"), patch.apply(block));
	}
	
	@Test
	public void testMergeInjectRemove() {
		Patch patch = createPatch("if","+inject", "-else", "+inject", "end if");
		TextBlock block = new TextBlock("if", "else", "end if");
		assertEquals(new TextBlock("if", "inject", "inject", "end if"), patch.apply(block));
	}
	
	@Test
	public void testMergeAnchorAtTheEnd() {
		Patch patch = createPatch("+1", "2");
		TextBlock block = new TextBlock("2");
		assertEquals(new TextBlock("1", "2"), patch.apply(block));
	}
	
	@Test
	public void testMergeAnchorAtTheBeginning() {
		Patch patch = createPatch("1", "+2");
		TextBlock block = new TextBlock("1");
		assertTrue(patch.appliesTo(block));
		assertEquals(new TextBlock("1", "2"), patch.apply(block));
	}
	
	@Test
	public void testMergeAnchorAtTheBeginningWithExtraLines() {
		Patch patch = createPatch("1", "+2");
		TextBlock block = new TextBlock("1", "3");
		assertTrue(patch.appliesTo(block));
		assertEquals(new TextBlock("1", "2", "3"), patch.apply(block));
	}
	
	@Test
	public void testMergeAnchorInTheMiddle() {
		Patch patch = createPatch("1", "+2");
		TextBlock block = new TextBlock("0", "1", "3");
		assertTrue(patch.appliesTo(block));
		assertEquals(new TextBlock("0", "1", "2", "3"), patch.apply(block));
	}
	
	@Test
	public void testMergeAnchorInTheMiddleWithRemove() {
		Patch patch = createPatch("1", "-2");
		TextBlock block = new TextBlock("0", "1", "2", "3");
		assertTrue(patch.appliesTo(block));
		assertEquals(new TextBlock("0", "1", "3"), patch.apply(block));
	}
	
	@Test
	public void testTwoMatches() {
		Patch patch = createPatch("1", "+2");
		TextBlock block = new TextBlock("1", "1");
		assertEquals(new TextBlock("1", "2", "1", "2"), patch.apply(block));
	}
	
	@Test
	public void testTwoMatchesWithRemove() {
		Patch patch = createPatch("-1", "+2");
		TextBlock block = new TextBlock("1", "1");
		assertEquals(new TextBlock("2", "2"), patch.apply(block));
	}
	
	@Test
	public void testTwoMatchesWithKeepAndRemove() {
		Patch patch = createPatch("1", "+2", "-3");
		TextBlock block = new TextBlock("1", "3", "1", "3");
		assertEquals(new TextBlock("1", "2", "1", "2"), patch.apply(block));
	}
	
	@Test
	public void testIncompleteMatch() {
		Patch patch = createPatch("1", "+2", "-3");
		TextBlock block = new TextBlock("1");
		assertFalse(patch.appliesTo(block));
	}
	
	@Test
	public void testPreserveWhitespace() {
		Patch patch = createPatch("1", "+2");
		TextBlock block = new TextBlock("   1");
		assertEquals(new TextBlock("   1", "2"), patch.apply(block));
	}
	
	@Test
	public void testEmptyPatch() {
		Patch patch = createPatch();
		TextBlock block = new TextBlock("1");
		assertFalse(patch.appliesTo(block));
	}
	
	@Test
	public void testEmptyBlock() {
		Patch patch = createPatch("1");
		TextBlock block = new TextBlock();
		assertFalse(patch.appliesTo(block));
	}
	
	@Test
	public void testEmptyPatchAndBlock() {
		Patch patch = createPatch();
		TextBlock block = new TextBlock();
		assertFalse(patch.appliesTo(block));
	}
	
}
