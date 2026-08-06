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

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.junit.Before;
import org.junit.Test;

public class StopGeneratingCurrentClassObject {

	private static final int DUMMY_LINE_NUMBER = -1;
	
	private SpecGenerator generator;
	
	@Before
	public void setup() {
		generator = new SpecGenerator();
		generator.initialise();
		generator.generateTopLevelClassObject("p", "parent", DUMMY_LINE_NUMBER);
	}
	
	@Test
	public void nullAfterDoneWithLast() {
		generator.stopGeneratingCurrentClassObject();
		
		assertEquals(null, generator.getCurrentClassObject());
	}
	
	@Test
	public void parentAfterSimpleNesting() {
		final ClassObject parent = generator.getCurrentClassObject();
		
		generator.generateContainedClassObject("foos", "f", "foo", DUMMY_LINE_NUMBER);
		generator.stopGeneratingCurrentClassObject();
		
		assertEquals(parent, generator.getCurrentClassObject());
	}
	
	@Test
	public void parentAfterTwoSimpleNestings() {
		final ClassObject parent = generator.getCurrentClassObject();
		
		generator.generateContainedClassObject("foos", "f", "foo", DUMMY_LINE_NUMBER);
		generator.stopGeneratingCurrentClassObject();
		
		generator.generateContainedClassObject("bars", "b", "bar", DUMMY_LINE_NUMBER);
		generator.stopGeneratingCurrentClassObject();
		
		assertEquals(parent, generator.getCurrentClassObject());
	}
	
	@Test
	public void secondLevelAfterThirdLevelNesting() {
		final ClassObject foo;
		
		generator.generateContainedClassObject("foos", "f", "foo", DUMMY_LINE_NUMBER);
		foo = generator.getCurrentClassObject();
		
		generator.generateContainedClassObject("bars", "b", "bar", DUMMY_LINE_NUMBER);
		generator.stopGeneratingCurrentClassObject();
		
		assertEquals(foo, generator.getCurrentClassObject());
	}
	
	@Test(expected=IllegalStateException.class)
	public void throwsExceptionWhenEmpty() {
		generator.stopGeneratingCurrentClassObject();
		generator.stopGeneratingCurrentClassObject();
	}
}
