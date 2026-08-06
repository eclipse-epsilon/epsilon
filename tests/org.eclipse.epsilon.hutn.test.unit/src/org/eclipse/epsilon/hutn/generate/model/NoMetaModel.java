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
 * $Id: NoMetaModel.java,v 1.2 2008/08/07 12:44:20 louis Exp $
 */
package org.eclipse.epsilon.hutn.generate.model;

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createSpec;

import org.junit.Test;

public class NoMetaModel extends HutnModelGeneratorTest {

	@Test(expected=IllegalArgumentException.class)
	public void modelShouldHaveNoFamilies() throws Exception {
		modelGeneratorTest(createSpec());
	}
}
