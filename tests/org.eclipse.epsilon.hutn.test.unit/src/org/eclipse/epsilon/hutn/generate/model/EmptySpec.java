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
 * $Id: EmptySpec.java,v 1.2 2008/08/07 12:44:20 louis Exp $
 */
package org.eclipse.epsilon.hutn.generate.model;

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class EmptySpec extends HutnModelGeneratorTest {

	@BeforeClass
	public static void generateModel() throws Exception {
		model = modelGeneratorTest(createSpec("families"));
	}
	
	@Test
	public void modelShouldHaveNoFamilies() {
		model.assertEquals(0, "Family.allInstances().size()");
	}
	
	@Test
	public void modelShouldHaveNoPets() {
		model.assertEquals(0, "Pet.allInstances().size()");
	}
}
