/*******************************************************************************
 * Copyright (c) 2011-2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.context;

import static org.junit.Assert.*;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;
import org.junit.Test;

public class FrameStackTests {

	private final FrameStack frameStack = new FrameStack();
	
	@Test
	public void initialisedToContainOnlyGlobals() throws Exception {
		final Variable v = createStringVariable("name", "Joe Bloggs");
		
		frameStack.put(v);
		assertTrue(frameStack.containsGlobal(v.name));
		assertEquals(1, frameStack.countGlobalFrames());
	}
	
	@Test
	public void globalsCanBeAccessedEvenWhenThereAreProtectedLocalFrames() throws Exception {
		final Variable global = createStringVariable("name", "Joe Bloggs");
		
		frameStack.put(global);
		frameStack.enterLocal(FrameType.PROTECTED, null);
		
		assertEquals(global, frameStack.get(global.name));
	}
	
	@Test
	public void localsShadowGlobals() throws Exception {
		final Variable global = createStringVariable("name", "Joe Bloggs");
		final Variable local = createStringVariable("name", "John Doe");
		
		frameStack.put(global);
		frameStack.enterLocal(FrameType.PROTECTED, null);
		frameStack.put(local);
				
		assertEquals(local, frameStack.get(global.name));
	}
	
	@Test
	public void globalsAreAccessibleWhenShadowingVariablesAreRemoved() throws Exception {
		final Variable global = createStringVariable("name", "Joe Bloggs");
		final Variable local = createStringVariable("name", "John Doe");
		
		frameStack.put(global);
		frameStack.enterLocal(FrameType.PROTECTED, null);
		frameStack.put(local);
		frameStack.leaveLocal(null);
		
		assertEquals(global, frameStack.get(global.name));
	}
	
	@Test
	public void getGlobalIgnoresShadowing() throws Exception {
		final Variable global = createStringVariable("name", "Joe Bloggs");
		final Variable local = createStringVariable("name", "John Doe");
		
		frameStack.put(global);
		frameStack.enterLocal(FrameType.PROTECTED, null);
		frameStack.put(local);
		
		assertEquals(global, frameStack.getGlobal(global.name));
	}
	
	@Test
	public void getLocalIgnoresShadowing() throws Exception {
		final Variable local = createStringVariable("null", "John Doe");
		
		assertNotNull("Test now invalid: frameStack should define a built-in variable called 'null' for this test to be valid", frameStack.get(local.name));
		
		frameStack.enterLocal(FrameType.PROTECTED, null);
		frameStack.put(local);
		
		assertEquals(local, frameStack.getLocal(local.name));
	}
	
	@Test
	public void leaveGlobalRemovesGlobalVariables() throws Exception {
		final Variable global = createStringVariable("name", "Joe Bloggs");

		frameStack.enterGlobal(FrameType.UNPROTECTED, null);
		frameStack.put(global);
		frameStack.leaveGlobal(null);
		
		assertEquals(null, frameStack.get(global.name));
	}
	
	@Test
	public void leaveGlobalDoesNotLeaveTheFinalGlobalFrame() throws Exception {
		final Variable global = createStringVariable("name", "Joe Bloggs");

		frameStack.put(global);
		frameStack.leaveGlobal(null);
		
		assertEquals(global, frameStack.get(global.name));
		assertEquals(1, frameStack.countGlobalFrames());
	}
	
	@Test
	public void leaveDoesNotLeaveGlobalFrames() throws Exception {
		final Variable global = createStringVariable("name", "Joe Bloggs");

		frameStack.enterGlobal(FrameType.UNPROTECTED, null);
		frameStack.put(global);
		frameStack.leaveLocal(null);
		
		assertEquals(global, frameStack.get(global.name));
	}
	
	@Test
	public void removeVariable() throws Exception {
		final Variable global = createStringVariable("name", "Joe Bloggs");

		frameStack.put(global);
		frameStack.remove(global.name);
		
		assertEquals(null, frameStack.get(global.name));
	}
	
	@Test
	public void removeVariableAffectsOnlyTopMostFrame() throws Exception {
		final Variable global = createStringVariable("name", "Joe Bloggs");

		frameStack.put(global);
		frameStack.enterGlobal(FrameType.UNPROTECTED, null);
		frameStack.remove(global.name);
		
		assertEquals(global, frameStack.get(global.name));
	}

	@Test
	public void cloneIsSeparate() throws Exception {
		assertEquals("Size before cloning is 1", 1, frameStack.size());
		frameStack.clone();
		assertEquals("Size after cloning is still 1", 1, frameStack.size());
	}

	private static Variable createStringVariable(String name, String value) {
		return new Variable(name, value, EolPrimitiveType.String);
	}
}
