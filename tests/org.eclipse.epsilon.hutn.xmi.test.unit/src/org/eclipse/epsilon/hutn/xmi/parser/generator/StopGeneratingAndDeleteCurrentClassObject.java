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
package org.eclipse.epsilon.hutn.xmi.parser.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.junit.Before;
import org.junit.Test;

public class StopGeneratingAndDeleteCurrentClassObject {

	private static final int DUMMY_LINE_NUMBER = -1;
	
	private SpecGenerator generator;
	
	@Before
	public void setup() {
		generator = new SpecGenerator();
		generator.initialise();
		generator.generateTopLevelClassObject("p", "parent", DUMMY_LINE_NUMBER);
	}
	
	@Test
	public void parentShouldNoLongerContainSlot() {
		final ClassObject parent = generator.getCurrentClassObject();
		
		generator.generateContainedClassObject("f", "foos", "foo", DUMMY_LINE_NUMBER);
		generator.stopGeneratingAndDeleteCurrentClassObject();
		
		assertEquals(parent, generator.getCurrentClassObject());
		assertNull(generator.getCurrentClassObject().findSlot("foos"));
	}
	
	@Test
	public void shouldWorkForTopLevelClassObject() {
		generator.stopGeneratingCurrentClassObject();
		
		assertEquals(null, generator.getCurrentClassObject());
	}
}
