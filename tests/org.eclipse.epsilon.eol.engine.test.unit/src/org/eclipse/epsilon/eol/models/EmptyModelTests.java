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
package org.eclipse.epsilon.eol.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EmptyModelTests {

	private static final IModel empty = new EmptyModel();
	
	@Test
	public void containsNoObjects() {
		assertEquals(0, empty.allContents().size());
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void exceptionForGetTypeNameOf() {
		empty.getTypeNameOf("foo");
	}
}
