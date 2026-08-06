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


import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({BibtexModelTests.AllOfKindTests.class,
	                 BibtexModelTests.PropertyGetterTests.class,
	                 BibtexModelTests.PropertySetterTests.class,
                     BibtexModelTests.AllContentsTests.class})
public class BibtexModelTests {
	
	public static class AllOfKindTests {
		@Test
		public void twoArticles() throws EolModelLoadingException {
			final String bibtex = "@article{apostel60models, " + NEWLINE +
			                      "author    = {Apostel, Leo}," + NEWLINE +
			                      "title     = {Towards the formal study of models in the non-formal sciences},"  + NEWLINE +
			                      "journal   = {Synthese},"  + NEWLINE +
			                      "publisher = {Springer Netherlands}," + NEWLINE +
			                      "pages     = {125--161}," + NEWLINE +
			                      "volume    = {12}," + NEWLINE +
			                      "issue     = {2}," + NEWLINE +
			                      "year      = {1960}" + NEWLINE +
			                      "}" + NEWLINE +
			                      "" + NEWLINE +
			                      "@article{selic05uml2," + NEWLINE + 
			                      "author    = {Bran Selic}, " + NEWLINE +
			                      "title     = {What's new in UML 2.0?}, " + NEWLINE +
			                      "journal   = {IBM Rational software}," + NEWLINE +
			                      "year      = {2005}" + NEWLINE +
			                      "}";
		
			
			final ModelWithEolAssertions model = new ModelWithEolAssertions(createBibtexModel(bibtex));
			
			model.assertEquals(2, "Article.all.size()");
			
			model.assertEquals("Apostel, Leo",                                                  "Article.all.first.author");
			model.assertEquals("Towards the formal study of models in the non-formal sciences", "Article.all.first.title");
			model.assertEquals("Synthese",                                                      "Article.all.first.journal");
			model.assertEquals("Springer Netherlands",                                          "Article.all.first.publisher");
			model.assertEquals("125--161",                                                      "Article.all.first.pages");
			model.assertEquals("12",                                                            "Article.all.first.volume");
			model.assertEquals("2",                                                             "Article.all.first.issue");
			model.assertEquals("1960",                                                          "Article.all.first.year");
			
			model.assertEquals("Bran Selic",             "Article.all.second.author");
			model.assertEquals("What's new in UML 2.0?", "Article.all.second.title");
			model.assertEquals("IBM Rational software",  "Article.all.second.journal");
			model.assertEquals("2005",                   "Article.all.second.year");
		}
		
		@Test
		public void mixedTypes() throws EolModelLoadingException {
			final String bibtex = "@book{pool97society," + NEWLINE +
			                      "author    = {Pool, R.}," + NEWLINE +
			                      "title     = {Beyond Engineering: How Society Shapes Technology}," + NEWLINE +
			                      "year      = {1997}," + NEWLINE +
			                      "publisher = {Oxford University Press}," + NEWLINE +
			                      "}" +
			                      "" +
			                      "@book{gronback09emp," + NEWLINE +
			                      "title     = {Eclipse Modeling Project: A Domain-Specific Languages (DSL) Toolkit}," + NEWLINE +
			                      "publisher = {Addison-Wesley Professional}," + NEWLINE +
			                      "year      = {2009}," + NEWLINE +
			                      "author    = {Gronback, R.C.}" + NEWLINE +
			                      "}" +
			                      "@inproceedings{cicchetti08automating," + NEWLINE +
			                      "author    = {Antonio Cicchetti et al.}," + NEWLINE +
			                      "title     = {Automating Co-evolution in Model-Driven Engineering}," + NEWLINE +
			                      "booktitle = {Proc. EDOC}," + NEWLINE +
			                      "year      = {2008}," + NEWLINE +
			                      "pages     = {222--231}," + NEWLINE +
			                      "publisher = {IEEE Computer Society}" + NEWLINE +
			                      "}";
		
			
			final ModelWithEolAssertions model = new ModelWithEolAssertions(createBibtexModel(bibtex));
			
			model.assertEquals(2, "Book.all.size()");
			model.assertEquals(1, "Inproceedings.all.size()");
			
			model.assertEquals("Pool, R.",                 "Book.all.first.author");
			model.assertEquals("Gronback, R.C.",           "Book.all.second.author");
			model.assertEquals("Antonio Cicchetti et al.", "Inproceedings.all.first.author");
		}
		
		@Test(expected=EolRuntimeException.class)
		public void errorForUnknownType() throws Throwable {
			final String bibtex = "";
			
			final ModelWithEolAssertions model = new ModelWithEolAssertions(createBibtexModel(bibtex));
			
			model.evaluate("Book.all.size()");
		}
	}
	
	public static class PropertyGetterTests {
		@Test
		public void propertyContainingABracket() throws EolModelLoadingException {
			final String bibtex = "@article{selic," + NEWLINE +
			                      "title     = {What's new in {UML} 2.0?}," + NEWLINE +
			                      "}";
		
			final ModelWithEolAssertions model = new ModelWithEolAssertions(createBibtexModel(bibtex));
	
			model.assertEquals("What's new in {UML} 2.0?", "Article.all.first.title");
		}
		
		@Test
		public void propertyContainingSeveralBrackets() throws EolModelLoadingException {
			final String bibtex = "@article{kohler," + NEWLINE +
			                      "author     = {Christian K{\"o}hler and G{\"u}nter Kuhns}," + NEWLINE +
			                      "}";
		
			final ModelWithEolAssertions model = new ModelWithEolAssertions(createBibtexModel(bibtex));
	
			model.assertEquals("Christian K{\"o}hler and G{\"u}nter Kuhns", "Article.all.first.author");
		}
		
		@Test
		public void bibtexId() throws EolModelLoadingException {
			final String bibtex = "@article{kohler92models," + NEWLINE +
			                      "author     = {Christian K{\"o}hler and G{\"u}nter Kuhns}," + NEWLINE +
			                      "}";
		
			final ModelWithEolAssertions model = new ModelWithEolAssertions(createBibtexModel(bibtex));
	
			model.assertEquals("kohler92models", "Article.all.first.id");
		}
	}
	
	public static class AllContentsTests {
		@Test
		public void shouldContainElementsOfAllTypes() throws Throwable {
			final String bibtex = "@book{pool97society," + NEWLINE +
			                      "author    = {Pool, R.}," + NEWLINE +
			                      "title     = {Beyond Engineering: How Society Shapes Technology}," + NEWLINE +
			                      "year      = {1997}," + NEWLINE +
			                      "publisher = {Oxford University Press}," + NEWLINE +
			                      "}" +
			                      "" +
			                      "@book{gronback09emp," + NEWLINE +
                                  "title     = {Eclipse Modeling Project: A Domain-Specific Languages (DSL) Toolkit}," + NEWLINE +
                                  "publisher = {Addison-Wesley Professional}," + NEWLINE +
                                  "year      = {2009}," + NEWLINE +
                                  "author    = {Gronback, R.C.}" + NEWLINE +
                                  "}" +
                                  "@inproceedings{cicchetti08automating," + NEWLINE +
                                  "author    = {Antonio Cicchetti et al.}," + NEWLINE +
                                  "title     = {Automating Co-evolution in Model-Driven Engineering}," + NEWLINE +
                                  "booktitle = {Proc. EDOC}," + NEWLINE +
                                  "year      = {2008}," + NEWLINE +
                                  "pages     = {222--231}," + NEWLINE +
                                  "publisher = {IEEE Computer Society}" + NEWLINE +
                                  "}";


			final ModelWithEolAssertions model = new ModelWithEolAssertions(createBibtexModel(bibtex));

			model.assertEquals(3, "MyBib.allContents().size()");

			model.assertEquals("Pool, R.",                 "MyBib.allContents().first.author");
			model.assertEquals("Gronback, R.C.",           "MyBib.allContents().second.author");
			model.assertEquals("Antonio Cicchetti et al.", "MyBib.allContents().third.author");
		}
	}
	
	public static class PropertySetterTests {
		@Test
		public void changeAProperty() throws Throwable {
			final String bibtex = "@article{kohler," + NEWLINE +
			                      "author = {Christian K{\"o}hler and G{\"u}nter Kuhns}," + NEWLINE +
			                      "}";
		
			final ModelWithEolAssertions model = new ModelWithEolAssertions(createBibtexModel(bibtex));
	
			model.execute("Article.all.first.author = 'Joe Bloggs';");
			model.assertEquals("Joe Bloggs", "Article.all.first.author");
		}
		
		@Test
		public void addAProperty() throws Throwable {
			final String bibtex = "@article{kohler," + NEWLINE +
			                      "author = {Christian K{\"o}hler and G{\"u}nter Kuhns}," + NEWLINE +
			                      "}";
		
			final ModelWithEolAssertions model = new ModelWithEolAssertions(createBibtexModel(bibtex));
	
			model.execute("Article.all.first.title = 'A paper';");
			model.assertEquals("A paper", "Article.all.first.title");
		}
	}
	
	private static BibtexModel createBibtexModel(final String bibtex) throws EolModelLoadingException {
		final BibtexModel model = new BibtexModel();
		model.setName("MyBib");
		model.setBibtex(bibtex);
		model.load();
		return model;
	}	
}
