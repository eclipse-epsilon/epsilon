/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.output;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class OutputBufferOffsetTests {

	OutputBuffer buffer;
	
	@Before
	public void setup() {
		buffer = new OutputBuffer();
	}
	
	@Test
	public void zeroWhenEmpty() {
		assertEquals(0, buffer.getOffset());
	}

	@Test
	public void offsetIsLengthOfContents() throws Exception {
		buffer.print("foo");
		assertEquals("foo".length(), buffer.getOffset());
	}
	
	@Test
	public void offsetRespectsLineBreaks() throws Exception {
		buffer.println("foo");
		buffer.print("bar");
		assertEquals(("foo" + buffer.getNewline() + "bar").length(), buffer.getOffset());
	}
}
