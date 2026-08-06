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
package org.eclipse.epsilon.flock.engine.test.acceptance.copying;

import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class CopyEnumerationValue extends FlockAcceptanceTest {

	private static final String strategy = "";

	private static final String originalModel = "Families {"              +
	                                            "	Dog {"                +
	                                            "       breed: labrador"  +
	                                            "	}"                    +
	                                            "}";
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, originalModel);
		
		migrated.setVariable("dog", "Dog.all.first");
	}
	
	@Test
	public void shouldProduceALabrador() {
		migrated.assertEquals("DogBreed#labrador", "dog.breed");
	}
}
