/*******************************************************************************
 * Copyright (c) 2010 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.eclipse.epsilon.common.util.Multimap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({MultimapTests.PutAndGetTests.class,
                     MultimapTests.PutAllTests.class,
                     MultimapTests.RemoveTests.class,
                     MultimapTests.ClearTests.class,
                     MultimapTests.ContainsKeyTests.class,
                     MultimapTests.ReplaceValuesTests.class})
public class MultimapTests {
	
	public static class PutAndGetTests {
		private static Multimap<String, String> multimap;
		
		@Before
		public void setup() {
			multimap = new Multimap<>();
		}
		
		@Test
		public void shouldHaveNoValuesForAKey() throws Exception {
			assertEquals(0, multimap.get("students").size());
		}
		
		@Test
		public void shouldRecallPutValues() throws Exception {
			multimap.put("students", "Louis");
			
			assertTrue(multimap.get("students").contains("Louis"));
			assertEquals(1, multimap.get("students").size());
		}
		
		@Test
		public void shouldRecallAllPutValues() throws Exception {
			multimap.put("students", "Louis");
			multimap.put("students", "James");
			
			assertTrue(multimap.get("students").containsAll(Arrays.asList("Louis", "James")));
			assertEquals(2, multimap.get("students").size());
		}
		
		@Test
		public void shouldRecallOnlyThoseValuesForSpecifiedKey() throws Exception {
			multimap.put("students", "Louis");
			multimap.put("lecturers", "Dimitris");
			
			assertTrue(multimap.get("students").contains("Louis"));
			assertEquals(1, multimap.get("students").size());
		}
		
		@Test
		public void shouldNotAllowClientsToAffectInteralState() throws Exception {
			multimap.put("students", "Louis");
			try {
				multimap.get("students").add("James");
			}
			catch (UnsupportedOperationException unsupported) {
				// We want this!
			}
			
			assertTrue(multimap.get("students").contains("Louis"));
			assertFalse(multimap.get("students").contains("James"));
		}
		
		@Test
		public void shouldPutIfPresentOnExistingKey() {
			multimap.put("students", "Louis");
			assertTrue(multimap.putIfPresent("students", "James"));
			
			assertTrue(multimap.get("students").containsAll(Arrays.asList("Louis", "James")));
		}
		
		@Test
		public void shouldPutIfPresentOnEmptyCollection() {
			multimap.put("students", "Louis");
			assertTrue(multimap.remove("students", "Louis"));
			
			assertTrue(multimap.putIfPresent("students", "James"));
			
			assertTrue(multimap.get("students").contains("James"));
			assertEquals(1, multimap.get("students").size());
		}
		
		@Test
		public void shouldNotPutIfPresentOnAbsentKey() {
			assertFalse(multimap.putIfPresent("students", "Louis"));
			assertTrue(multimap.get("students").isEmpty());
		}
	}

	public static class PutAllTests {
		private static Multimap<String, String> multimap;
		
		@Before
		public void setup() {
			multimap = new Multimap<>();
		}
		
		@Test
		public void shouldPutValuesInCollection() {
			multimap.putAll("students", Arrays.asList("Louis", "James", "Frank"));
			assertTrue(multimap.get("students").containsAll(Arrays.asList("Louis", "James", "Frank")));
			assertEquals(3, multimap.get("students").size());
			
			multimap.putAll("students", Collections.singleton("Betty"));
			assertEquals(4, multimap.get("students").size());
		}
		
		@Test
		public void shouldPutAllWithoutOverwriteIfPresent() {
			multimap.putAll("students", Arrays.asList("Louis", "James", "Frank"));
			assertTrue(multimap.putAllIfPresent("students", Arrays.asList("Betty", "Ioannis")));
			assertEquals(5, multimap.get("students").size());
		}
		
		@Test
		public void shouldPutAllIfCollectionIsEmpty() {
			multimap.putAll("students", Collections.emptySet());
			assertTrue(multimap.get("students").isEmpty());
			
			assertTrue(multimap.putAll("students", Arrays.asList("Louis", "James", "Frank")));
			assertEquals(3, multimap.get("students").size());
		}
	}
	
	public static class RemoveTests {
		private static Multimap<String, String> multimap;
		
		@Before
		public void setup() {
			multimap = new Multimap<>();
		}
		
		@Test
		public void shouldRemoveTheGivenKeyValuePair() throws Exception {
			multimap.putAll("students", new ArrayList<>(Arrays.asList("Louis", "James", "Frank")));
			multimap.remove("students", "James");
			
			assertTrue(multimap.get("students").containsAll(Arrays.asList("Louis", "Frank")));
			assertEquals(2, multimap.get("students").size());
		}
		
		@Test
		public void shouldRemoveEvenWhenInitialisedWithImmutable() {
			multimap.putAll("students", Collections.unmodifiableCollection(Arrays.asList("Louis", "James", "Frank")));
			multimap.remove("students", "James");
			
			assertTrue(multimap.get("students").containsAll(Arrays.asList("Louis", "Frank")));
			assertEquals(2, multimap.get("students").size());
		}
		
		@Test
		public void shouldDoNothingWhenAKeyHasNoValue() throws Exception {
			multimap.remove("students", "Anyone");
			
			assertEquals(0, multimap.get("students").size());
		}
		
		@Test
		public void removeAllShouldKeepKey() {
			multimap.putAll("students", Arrays.asList("Louis", "James", "Frank"));
			assertTrue(multimap.removeAll("students", Arrays.asList("Louis", "James", "Frank")));
			
			assertTrue(multimap.hasKey("students"));
			assertEquals(0, multimap.get("students").size());
			
			multimap.putAll("students", Arrays.asList("Louis", "James", "Frank"));
			assertTrue(multimap.removeAll("students"));
			
			assertTrue(multimap.hasKey("students"));
			
			multimap.putAll("students", Arrays.asList("Louis", "James", "Frank"));
			assertTrue(multimap.removeAll("students", Collections.singleton("James")));
			assertTrue(multimap.containsKey("students"));
		}
		
		@Test
		public void removeKeyShouldRemoveKey() {
			multimap.putAll("students", Arrays.asList("Louis", "James", "Frank"));
			assertEquals(new ArrayList<>(multimap.remove("students")), Arrays.asList("Louis", "James", "Frank"));
			
			assertFalse(multimap.containsKey("students"));
			
			multimap.put("students", "aStudent");
			multimap.remove("students");
			assertFalse(multimap.containsKey("students"));
		}
	}
	
	public static class ClearTests {
		private static Multimap<String, String> multimap;
		
		@Before
		public void setup() {
			multimap = new Multimap<>();
		}
		
		@Test
		public void clearShouldRemoveAllValues() throws Exception {
			multimap.putAll("students", Arrays.asList("Louis", "James", "Frank"));
			multimap.put("lecturers", "Dimitris");
			
			multimap.clear();
			
			assertEquals(0, multimap.get("students").size());
			assertEquals(0, multimap.get("lecturers").size());
		}
	}
	
	public static class ContainsKeyTests {
		private static Multimap<String, String> multimap;
		
		@Before
		public void setup() {
			multimap = new Multimap<>();
		}
		
		@Test
		public void shouldBeTrueForKeyWithValues() throws Exception {
			multimap.putAll("students", Arrays.asList("Louis", "James", "Frank"));
			
			assertTrue(multimap.containsKey("students"));
		}
		
		@Test
		public void shouldBeFalseForKeyForWhichNoValuesHaveBeenPut() throws Exception {
			assertFalse(multimap.containsKey("students"));
		}
		
		@Test
		public void shouldBeFalseForKeyWhenAllValuesAreRemoved() throws Exception {
			multimap.put("students", "Louis");
			multimap.remove("students", "Louis");
			
			assertFalse(multimap.containsKey("students"));
		}
		
		@Test
		public void shouldBeFalseForKeyWhenEmptyCollectionIsPut() throws Exception {
			multimap.putAll("students", new ArrayList<>());
			
			assertFalse(multimap.containsKey("students"));
		}
		
		@Test
		public void emptyCollectionshouldBeTrueForHasKey() {
			multimap.putAll("students", Collections.emptyList());
			
			assertTrue(multimap.hasKey("students"));
		}
	}
	
	public static class ReplaceValuesTests {
		private static Multimap<String, String> multimap;
		
		@Before
		public void setup() {
			multimap = new Multimap<>();
		}
		
		@Test
		public void shouldReplaceExistingValues() throws Exception {
			multimap.putAll("students", Arrays.asList("Louis", "Nikos"));
			multimap.replaceValues("students", Arrays.asList("James", "Frank"));
			
			assertTrue(multimap.get("students").containsAll(Arrays.asList("James", "Frank")));
			assertEquals(2, multimap.get("students").size());
			
			multimap.put("students", Arrays.asList("Louis", "Nikos"));
			assertTrue(multimap.get("students").size() == 2);
		}
		
		@Test
		public void shouldReplaceExistingValuesWhenThereAreNoValues() throws Exception {
			multimap.putAll("students", new ArrayList<String>());
			multimap.putAll("students", Arrays.asList("James", "Frank"));
			
			assertTrue(multimap.get("students").containsAll(Arrays.asList("James", "Frank")));
		}
		
		@Test
		public void shouldReplaceExistingValuesWhenKeyHasNotBeenUsedYet() throws Exception {
			multimap.putAll("students", Arrays.asList("James", "Frank"));
			
			assertTrue(multimap.get("students").containsAll(Arrays.asList("James", "Frank")));
		}
	}
}
