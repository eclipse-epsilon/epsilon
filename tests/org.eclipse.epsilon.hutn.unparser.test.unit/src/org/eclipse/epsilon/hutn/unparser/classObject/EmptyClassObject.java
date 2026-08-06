/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.unparser.classObject;

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createClassObject;
import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.hutn.unparser.internal.AbstractClassObjectUnparserTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmptyClassObject extends AbstractClassObjectUnparserTest {

	@BeforeClass
	public static void setup() {
		classObjectUnparserTest(createClassObject("John", "Person"));
	}
	
	@Test
	public void hasNoFeatures() {
		assertEquals(0, numberOfFeatures());
	}
}
