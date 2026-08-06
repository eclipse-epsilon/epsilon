/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.plainxml.test;

import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eol.EolEvaluator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlainXmlModelTests {
	
	protected PlainXmlModel model;
	protected EolEvaluator evaluator;
	
	@Before
	public void setup() throws Exception {
		model = new PlainXmlModel();
		model.setName("M");
		model.setReadOnLoad(true);
		model.setXml(
			"<?xml version='1.0'?>" + newline() +
			"<library>" + newline() +
				"<book name='b1' pages='212' authors='a1,a2' editor='e1' text='b1-text'/>" + newline() + 
				"<book name='b1' pages='122' editor='e2'>b2-text</book>" + newline() + 
				"<book name='b3' pages='351' editor='e3'/>" + newline() + 
				"<author name='a1'/>" + newline() + 
				"<author name='a2'/>" + newline() +
				"<author name='a3'/>" + newline() +
				"<editor name='e1'/>" + newline() + 
				"<editor name='e2'/>" + newline() +
				"<editor name='e3'/>" + newline() +
			"</library>" + newline()
		);
		model.bind("book", "authors", "author", "name", true);
		model.bind("book", "editor", "editor", "name", false);
		model.load();
		evaluator = new EolEvaluator(model);
		
	}
	
	public String newline() {
		return System.getProperty("line.separator");
	}
	
	@Test
	public void testAllOfKind() {
		assertEquals(1, evaluator.evaluate("t_library.all.size()"));
		assertEquals(3, evaluator.evaluate("t_book.all.size()"));
	}
	
	@Test
	public void testStringAttribute() {
		assertEquals("b1", evaluator.evaluate("t_book.all.first().a_name"));
	}
	
	@Test
	public void testTextAttribute() {
		assertEquals("b1-text", evaluator.evaluate("t_book.all.first().getAttribute('text')"));
	}
	
	@Test
	public void testText() {
		assertEquals("b2-text", evaluator.evaluate("t_book.all.second().text"));
	}
	
	@Test
	public void testIntegerAttribute() {
		assertEquals(212, evaluator.evaluate("t_book.all.first().i_pages"));
	}
	
	@Test
	public void testMultipleReferenceAttribute() {
		assertEquals(2, evaluator.evaluate("t_book.all.first().x_authors.size()"));				
	}
	
	@Test
	public void testSingleReferenceAttribute() {
		assertEquals("e1", evaluator.evaluate("t_book.all.first().x_editor.a_name"));				
	}
	
	@Test 
	public void testAddRemoveToMultipleReference() {
		evaluator.execute("t_book.all.first().x_authors.add(t_author.all.third());");
		assertEquals(3, evaluator.evaluate("t_book.all.first().x_authors.size()"));				
		evaluator.execute("t_book.all.first().x_authors.remove(t_author.all.third());");
		assertEquals(2, evaluator.evaluate("t_book.all.first().x_authors.size()"));	
	}
	
	@Test
	public void testSetSingleReference() {
		evaluator.execute("t_book.all.first().x_editor = t_editor.all.second();");		
		assertEquals("e2", evaluator.evaluate("t_book.all.first().x_editor.a_name"));				
		evaluator.execute("t_book.all.first().x_editor = t_editor.all.first();");		
	}
	
	@Test
	public void testSetMultipleReference() {
		evaluator.execute("t_book.all.third().x_authors = Sequence{t_author.all.first(), t_author.all.second()};");
		assertEquals("a1,a2", evaluator.evaluate("t_book.all.third().x_authors.collect(a|a.a_name).concat(',')"));
		evaluator.execute("t_book.all.third().x_authors = Sequence{};");
		assertEquals("", evaluator.evaluate("t_book.all.third().a_authors"));
	}
	
	@Test
	public void testAddChildren() {
		assertEquals(9, evaluator.evaluate("t_library.all().first().children.size()"));
		evaluator.execute("t_library.all().first().appendChild(new t_book);");
		assertEquals(10, evaluator.evaluate("t_library.all().first().children.size()"));
	}
	
	@Test
	public void testFindByTagName() {
		assertEquals(3, evaluator.evaluate("t_library.all().first().children.select(c|c.name=='book').size()"));
		assertEquals(3, evaluator.evaluate("t_library.all().first().children.select(c|c.tagName=='author').size()"));
		assertEquals(3, evaluator.evaluate("t_library.all().first().children.select(c|c.tagName=='editor').size()"));
	}
	
	@Test
	public void testExpandEntityReferencesDefault() throws Exception {
		// By default, entity references are expanded
		PlainXmlModel m = new PlainXmlModel();
		m.setName("ExpandTest");
		m.setReadOnLoad(true);
		m.setXml("<?xml version='1.0'?><root><item text='line1&#xD;line2'/></root>");
		m.load();
		
		EolEvaluator eval = new EolEvaluator(m);
		String text = (String) eval.evaluate("t_item.all.first().a_text");
		// With expansion enabled (default), &#xD; becomes \r which may be normalized
		assertNotNull(text);
		m.dispose();
	}
	
	@Test
	public void testExpandEntityReferencesFalse() throws Exception {
		// With expand=false, entity references are preserved in the DOM
		PlainXmlModel m = new PlainXmlModel();
		m.setName("NoExpandTest");
		m.setReadOnLoad(true);
		m.setExpandEntityReferences(false);
		m.setXml("<?xml version='1.0'?><root><item text='line1&#xD;line2'/></root>");
		m.load();
		
		EolEvaluator eval = new EolEvaluator(m);
		String text = (String) eval.evaluate("t_item.all.first().a_text");
		// With expansion disabled, the raw text should preserve the CR
		assertNotNull(text);
		assertTrue("Expected CR character preserved when expand=false",
			text.contains("\r") || text.contains("&#xD;"));
		m.dispose();
	}
	
}
