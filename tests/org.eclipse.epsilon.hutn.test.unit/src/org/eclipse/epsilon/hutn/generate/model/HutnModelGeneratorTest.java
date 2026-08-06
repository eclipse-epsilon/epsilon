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
 * $Id: HutnModelGeneratorTest.java,v 1.2 2008/08/07 12:44:20 louis Exp $
 */
package org.eclipse.epsilon.hutn.generate.model;

import org.eclipse.epsilon.hutn.model.hutn.Spec;
import org.eclipse.epsilon.hutn.test.model.HutnTestWithFamiliesMetaModel;
import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.junit.AfterClass;

public class HutnModelGeneratorTest extends HutnTestWithFamiliesMetaModel {

	protected static ModelWithEolAssertions model;
	
	protected static ModelWithEolAssertions modelGeneratorTest(Spec spec) throws Exception {
		return new ModelWithEolAssertions(new ModelGenerator(spec).generate((String)null));
	}
	
	@AfterClass
	public static void disposeModel() {
		if (model != null) model.dispose();
	}
}
