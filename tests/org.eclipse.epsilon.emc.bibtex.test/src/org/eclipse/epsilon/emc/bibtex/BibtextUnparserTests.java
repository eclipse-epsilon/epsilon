/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.bibtex;
/*******************************************************************************
 * Copyright (c) 2010 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.eclipse.epsilon.emc.bibtex.domain.Bibliography;
import org.eclipse.epsilon.emc.bibtex.domain.Publication;
import org.eclipse.epsilon.emc.bibtex.parser.BibtexUnparser;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class BibtextUnparserTests {

	@Test
	public void singleProperty() throws Throwable {
		final String bibtex = unparse(bibliography(publication("article", property("author", "Joe Bloggs"))));

		assertTrue(bibtex.startsWith("@article{"));
		assertTrue(bibtex.contains("author = {Joe Bloggs},"));
		assertTrue(bibtex.endsWith("}"));
	}
	
	@Test
	public void identifier() throws Throwable {
		final String bibtex = unparse(bibliography(publication("article", "doe10theories", property("author", "Joe Bloggs"))));

		assertTrue(bibtex.startsWith("@article{doe10theories"));
		assertTrue(bibtex.contains("author = {Joe Bloggs},"));
		assertTrue(bibtex.endsWith("}"));
	}
	
	@Test
	public void severalProperties() throws Throwable {
		final String bibtex = unparse(bibliography(publication("article",
		                                                       property("author", "Joe Bloggs"),
		                                                       property("year",   "2010"),
		                                                       property("issue",  "1"))));

		assertTrue(bibtex.startsWith("@article{"));
		assertTrue(bibtex.contains("author = {Joe Bloggs},"));
		assertTrue(bibtex.contains("year = {2010},"));
		assertTrue(bibtex.contains("issue = {1},"));
		assertTrue(bibtex.endsWith("}"));
	}
	
	@Test
	public void severalPublications() throws Throwable {
		final String bibtex = unparse(bibliography(publication("article", property("author", "Joe Bloggs")),
		                                           publication("journal", property("author", "John Doe")),
		                                           publication("book",    property("author", "A.N. Other"))));

		final String[] entries = Pattern.compile("^" + "\\}" + "\\s" + "*", Pattern.MULTILINE).split(bibtex);
		
		assertEquals(3, entries.length);
		
		assertTrue(entries[0].startsWith("@article{"));
		assertTrue(entries[0].contains("author = {Joe Bloggs},"));
		
		assertTrue(entries[1].startsWith("@journal{"));
		assertTrue(entries[1].contains("author = {John Doe},"));
		
		assertTrue(entries[2].startsWith("@book{"));
		assertTrue(entries[2].contains("author = {A.N. Other},"));
	}
	
	private static String unparse(Bibliography bibliography) {
		return new BibtexUnparser(bibliography).unparse().trim();
	}

	private static Bibliography bibliography(Publication... publications) {
		final Bibliography bibliography = new Bibliography();
		
		for (Publication publication : publications) {
			bibliography.add(publication);
		}
		
		return bibliography;
	}
	
	private static Publication publication(String type, Pair<String,String>... properties) {
		return new Publication(type, map(properties));
	}
	
	private static Publication publication(String type, String id, Pair<String,String>... properties) {
		return new Publication(type, id, map(properties));
	}
	
	private static <K,V> Map<K,V> map(Pair<K,V>... entries) {
		final Map<K,V> map = new HashMap<>();

		for (Pair<K, V> entry : entries) {
			map.put(entry.first, entry.second);
		}

		return map;
	}

	private static <K,V> Pair<K,V> property(K key, V value) {
		return new Pair<>(key, value);
	}

	private static class Pair<A,B> {
		public final A first;
		public final B second;

		public Pair(A first, B second) {
			this.first = first;
			this.second = second;
		}
	}

}
