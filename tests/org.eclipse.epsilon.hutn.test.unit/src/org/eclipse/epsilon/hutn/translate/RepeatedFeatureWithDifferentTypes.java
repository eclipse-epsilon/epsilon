/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id: Containment.java,v 1.2 2008/08/07 12:44:21 louis Exp $
 */
package org.eclipse.epsilon.hutn.translate;

import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.junit.BeforeClass;
import org.junit.Test;

public class RepeatedFeatureWithDifferentTypes extends HutnTranslatorTest {
	
	private static ModelWithEolAssertions model;
	
	private static Node createPerson(String name) {
		final Node person = createClass("Person", name);
		person.getChildren().add(createAttribute("name", name));
		return person;
	}
	
	@BeforeClass
	public static void createAstModel() throws Exception {
		final Node member1Node = createPerson("John");
		
		final Node familyNode = createClass("Family", "The Smiths");
		familyNode.getChildren().add(createContainment("members", member1Node));
		familyNode.getChildren().add(createAttribute("members", "Jill"));
		
		final Node packageNode = createPackage("FamilyPackage");
		packageNode.getChildren().add(familyNode);
		
		final Ast ast = initialiseAst();
		ast.getRoots().add(packageNode);
		
		model = translatorTest(ast);
		model.setVariable("package", "spec.objects.first()");
		model.setVariable("family",  "package.classObjects.selectOne(c : ClassObject | c.type = 'Family')");
		
		model.setVariable("slot1", "family.slots.first()");
		model.setVariable("slot2", "family.slots.last()");
	}
	
	@Test
	public void packageShouldContainOneClassObject() {
		model.assertEquals(1, "package.classObjects.size()");
	}
	
	@Test
	public void slot1ShouldHaveCorrectFeatureName() {
		model.assertEquals("members", "slot1.feature");
	}
	
	@Test
	public void slot1ShouldContainOneValue() {
		model.assertEquals(1, "slot1.values.size()");
	}
	
	@Test
	public void slot1ShouldContainOneClassObjects() {
		model.assertEquals(1, "slot1.values.size()");
	}
	
	@Test
	public void slot1ShouldContainFirstClassObject() {
		model.assertEquals("John", "slot1.values.first.identifier");
	}
	
	@Test
	public void slot2ShouldHaveCorrectFeatureName() {
		model.assertEquals("members", "slot2.feature");
	}
	
	@Test
	public void slot2ShouldContainOneValue() {
		model.assertEquals(1, "slot2.values.size()");
	}
	
	@Test
	public void slot2ShouldContainCorrectValue() {
		model.assertEquals("Jill", "slot2.values.first");
	}
}
