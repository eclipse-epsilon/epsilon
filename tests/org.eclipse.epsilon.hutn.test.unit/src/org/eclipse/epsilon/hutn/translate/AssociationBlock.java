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
 * $Id: AssociationBlock.java,v 1.1 2008/08/14 10:50:21 louis Exp $
 */
package org.eclipse.epsilon.hutn.translate;

import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.junit.BeforeClass;
import org.junit.Test;

public class AssociationBlock extends HutnTranslatorTest {
	
	private static ModelWithEolAssertions model;
	
	@BeforeClass
	public static void createAstModel() throws Exception {
		final Node petNode    = createClass("Pet", "Fido");
		final Node familyNode = createClass("Family", "The Smiths");
		
		final Node assocBlock = createAssociationBlock("pets", new Association(new Reference("Family", "The Smiths"),
		                                                                       new Reference("Pet", "Fido")));
		
		
		final Node packageNode = createPackage("FamilyPackage");
		packageNode.getChildren().add(assocBlock);
		packageNode.getChildren().add(petNode);
		packageNode.getChildren().add(familyNode);
		
		final Ast ast = initialiseAst();
		ast.getRoots().add(packageNode);
		
		model = translatorTest(ast);
		model.setVariable("package", "spec.objects.first()");
		model.setVariable("family",  "package.classObjects.selectOne(c : ClassObject | c.type = 'Family')");
		
		model.setVariable("slot", "family.slots.first()");
	}
	
	@Test
	public void packageShouldContainTwoClassObjects() {
		model.assertEquals(2, "package.classObjects.size()");
	}

	@Test
	public void familyShouldContainOneSlot() {
		model.assertEquals(1, "family.slots.size()");
	}

	@Test
	public void slotShouldBeCorrectType() {
		model.assertTrue("ReferenceSlot.isType(slot)");
	}
	
	@Test
	public void slotShouldHaveCorrectFeatureName() {
		model.assertEquals("pets", "slot.feature");
	}
	
	@Test
	public void slotShouldHaveOneIdentifier() {
		model.assertEquals(1, "slot.values.size()");
	}
	
	@Test
	public void slotShouldContainCorrectIdentifier() {
		model.assertEquals("Fido", "slot.values.first()");
	}
}
