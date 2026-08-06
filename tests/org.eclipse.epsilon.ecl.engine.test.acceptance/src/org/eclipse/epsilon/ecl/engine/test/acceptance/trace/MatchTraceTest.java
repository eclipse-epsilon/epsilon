/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecl.engine.test.acceptance.trace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.epsilon.ecl.dom.MatchRule;
import org.eclipse.epsilon.ecl.trace.Match;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.junit.Test;

public class MatchTraceTest {

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void addRemove() {
		MatchTrace trace = new MatchTrace();

		String left = "hello";
		String right = "world";
		MatchRule mr = new MatchRule();
		Match m = new Match(left, right, true, mr);

		assertTrue("Trace should start empty", trace.isEmpty());
		assertFalse("Trace should not contain a match that has not been added", trace.contains(m));
		assertFalse("Trace should not contain something that is not a Match", trace.contains("not a match"));

		trace.add(m);
		assertTrue("Trace should contain the match that was just added", trace.contains(m));
		assertFalse("Trace should not be considered empty after an add", trace.isEmpty());
		trace.remove(m);
		assertTrue("Trace should be considered empty after removing its only element", trace.isEmpty());
	}

	@Test
	public void addRemoveViaIterator() {
		MatchTrace trace = new MatchTrace();

		String left = "hello";
		String right = "world";
		MatchRule mr = new MatchRule();
		Match m = new Match(left, right, true, mr);
		trace.add(m);

		Iterator<Match> itMatches = trace.iterator();
		assertSame("Iterator should return the only element", m, itMatches.next());

		// Guava iterators over views do not support modification
		assertThrows(UnsupportedOperationException.class, () -> itMatches.remove());
	}

	@Test
	public void addClear() {
		MatchTrace trace = new MatchTrace();

		trace.add("hello", "world", true, new MatchRule());
		assertFalse("After an add, the trace should not be empty", trace.isEmpty());
		trace.clear();
		assertTrue("After a clear, the trace should be empty", trace.isEmpty());
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void removeNonMatch() {
		MatchTrace trace = new MatchTrace();

		// Shouldn't raise an error
		trace.remove("not a match");
	}

	@Test(expected=NoSuchElementException.class)
	public void nextBeyondEnd() {
		MatchTrace trace = new MatchTrace();
		trace.iterator().next();
	}

	@Test
	public void streamLeft() {
		MatchTrace trace = new MatchTrace();

		MatchRule mr = new MatchRule();
		trace.add(1, 3, false, mr);
		trace.add(2, 4, true, mr);

		Set<Object> lefts = trace.stream().map(m -> m.getLeft()).collect(Collectors.toSet());
		assertEquals(new HashSet<>(Arrays.asList(1, 2)), lefts);
	}

	@Test
	public void parallelStreamRight() {
		MatchTrace trace = new MatchTrace();

		MatchRule mr = new MatchRule();
		trace.add(1, 3, false, mr);
		trace.add(2, 4, true, mr);

		Set<Object> rights = trace.parallelStream().map(m -> m.getRight()).collect(Collectors.toSet());
		assertEquals(new HashSet<>(Arrays.asList(3, 4)), rights);
	}

	@Test
	public void getMatches() {
		MatchTrace trace = new MatchTrace();

		String a = "a", b = "b", c = "c", d = "d";
		MatchRule mr1 = new MatchRule(), mr2 = new MatchRule();

		assertNull("No matches should be returned on an empty trace", trace.getMatch(c));

		trace.add(a, b, false, mr1);
		trace.add(a, c, true, mr1);
		trace.add(b, c, true, mr2);
		trace.add(b, d, false, mr2);

		assertEquals(new HashSet<>(Arrays.asList(b, c)),
			trace.getMatches(a).stream().map(m -> m.getRight()).collect(Collectors.toSet()));

		assertEquals(new HashSet<>(Arrays.asList(a, b)),
				trace.getMatches(c).stream().map(m -> m.getLeft()).collect(Collectors.toSet()));

		assertSame(c, trace.getMatch(b).getRight());
		assertNotNull(trace.getMatch(c));
		assertNull(trace.getMatch(d));

		assertSame(c, trace.getMatch(a, mr1).getRight());
		assertNull(trace.getMatch(a, mr2));
		assertNull(trace.getMatch("nonexistent", mr1));

		assertTrue(trace.hasBeenMatched(a));
		assertTrue(trace.hasBeenMatched(c));
		assertFalse(trace.hasBeenMatched("nonexistent"));
	}

	@Test
	public void batchOperations() {
		MatchTrace trace = new MatchTrace();

		String a = "hello", b = "happy", c = "world", d = "nothing";
		MatchRule mr1 = new MatchRule(), mr2 = new MatchRule();

		Match m1 = new Match(a, b, false, mr1);
		Match m2 = new Match(a, c, true, mr1);
		Match m3 = new Match(b, c, true, mr2);
		Match m4 = new Match(b, d, false, mr2);

		trace.addAll(Arrays.asList(m1, m2, m3));

		assertTrue(trace.containsAll(Arrays.asList(m2, m3)));
		assertFalse(trace.containsAll(Arrays.asList(m3, m4)));

		assertTrue(trace.removeAll(Arrays.asList(m3, m4)));
		assertFalse(trace.removeAll(Arrays.asList(m3, m4)));
		assertTrue(trace.contains(m1));
	}

	@Test
	public void retainAll() {
		MatchTrace trace = new MatchTrace();

		String a = "hello", b = "happy", c = "world", d = "nothing";
		MatchRule mr1 = new MatchRule(), mr2 = new MatchRule();

		Match m1 = new Match(a, b, false, mr1);
		Match m2 = new Match(a, c, true, mr1);
		Match m3 = new Match(b, c, true, mr2);
		Match m4 = new Match(b, d, false, mr2);

		trace.addAll(Arrays.asList(m1, m2, m3));

		assertTrue(trace.retainAll(Arrays.asList(m2, m3, m4)));
		assertFalse(trace.contains(m1));
		assertTrue(trace.contains(m2));
		assertTrue(trace.contains(m3));
		assertFalse(trace.contains(m4));
	}

	@Test
	public void copyConstructor() {
		MatchTrace original = new MatchTrace();

		String a = "hello", b = "happy", c = "world", d = "nothing";
		MatchRule mr1 = new MatchRule(), mr2 = new MatchRule();
		original.add(a, b, false, mr1);
		original.add(a, c, true, mr1);
		original.add(b, c, true, mr2);
		original.add(b, d, false, mr2);

		MatchTrace copy = new MatchTrace(original);

		assertEquals("The copy should be equal to the original", original, copy);
		assertEquals("The copy should be have equal hashCode to the original", original.hashCode(), copy.hashCode());

		MatchTrace backup = new MatchTrace(original);

		copy.remove(original.iterator().next());
		assertEquals("Modifying the copy should not impact the original", backup, original);
	}

	@Test
	public void multipleMatches() {
		MatchTrace trace = new MatchTrace();

		String a = "hello", b = "world";
		MatchRule mr1 = new MatchRule(), mr2 = new MatchRule();
		Match m1 = new Match(a, b, false, mr1);
		Match m2 = new Match(a, b, true, mr2);
		trace.add(m1);
		trace.add(m2);

		assertSame("Matching order should be preserved", m1, trace.getMatch(a, b));
		assertEquals("All matches should be available - left side", new HashSet<>(Arrays.asList(m1, m2)), new HashSet<>(trace.getMatches(a)));
		assertEquals("All matches should be available - right side", new HashSet<>(Arrays.asList(m1, m2)), new HashSet<>(trace.getMatches(b)));
	}
}
