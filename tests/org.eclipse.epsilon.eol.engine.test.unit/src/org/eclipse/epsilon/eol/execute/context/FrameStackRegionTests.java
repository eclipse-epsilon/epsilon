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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Map;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;
import org.junit.Before;
import org.junit.Test;

public class FrameStackRegionTests {

	private FrameStackRegion region = new FrameStackRegion(); 
	
	@Before
	public void initialise() {
		region = new FrameStackRegion();
		region.enter(FrameType.UNPROTECTED, null);
	}
	
	@Test
	public void variablesCanBeRetrievedByName() throws Exception {
		final Variable v = createStringVariable("name", "Joe Bloggs");
		
		region.put(v);
		assertEquals(v, region.get(v.name));
	}
	
	@Test
	public void gettingAnUnknownVariableReturnsNull() throws Exception {
		assertEquals(null, region.get("foo"));
	}
	
	
	@Test
	public void variablesOverwriteExisting() throws Exception {
		final Variable first = createStringVariable("name", "Joe Bloggs");
		final Variable second = createStringVariable("name", "John Doe");
		
		region.put(first);
		region.put(second);
		
		assertEquals(second, region.get(second.name));
	}
	
	@Test
	public void variablesShadowThoseInParentFrames() throws Exception {
		final Variable first = createStringVariable("name", "Joe Bloggs");
		final Variable second = createStringVariable("name", "John Doe");
		
		region.put(first);
		region.enter(FrameType.UNPROTECTED, new NameExpression());
		region.put(second);
		
		assertEquals(second, region.get(second.name));
	}
	
	@Test
	public void leaveRestoresPreviousVariables() throws Exception {
		final Expression marker = new NameExpression();
		final Variable first = createStringVariable("name", "Joe Bloggs");
		final Variable second = createStringVariable("name", "John Doe");
		
		region.put(first);
		region.enter(FrameType.UNPROTECTED, marker);
		region.put(second);
		region.leave(marker);
		
		assertEquals(first, region.get(first.name));
	}
	
	@Test
	public void variablesInLowerFramesAreVisibleWhenUnprotected() throws Exception {
		final Variable first = createStringVariable("name", "Joe Bloggs");
		
		region.put(first);
		region.enter(FrameType.UNPROTECTED, new NameExpression());
		
		assertEquals(first, region.get(first.name));
	}
	
	@Test
	public void variablesInLowerFramesAreInvisibleWhenProtected() throws Exception {
		final Variable first = createStringVariable("name", "Joe Bloggs");
		
		region.put(first);
		region.enter(FrameType.PROTECTED, new NameExpression());
		
		assertEquals(null, region.get(first.name));
	}
	
	@Test
	public void leaveRemovesFramesToReachMarker() throws Exception {
		final Variable first = createStringVariable("name", "first");
		final Variable second = createStringVariable("name", "second");
		final Variable third = createStringVariable("name", "third");
		
		final Expression marker = new NameExpression();
		
		region.put(first);
		region.enter(FrameType.PROTECTED, marker);
		region.put(second);
		region.enter(FrameType.PROTECTED, new NameExpression());
		region.put(third);
		
		region.leave(marker);
		
		assertEquals(first, region.get(first.name));
	}
	
	@Test
	public void leaveWithoutDisposePreservesMarkedFrame() throws Exception {
		final Variable first = createStringVariable("name", "first");
		final Variable second = createStringVariable("name", "second");
		final Variable third = createStringVariable("name", "third");
		
		final Expression marker = new NameExpression();
		
		region.put(first);
		region.enter(FrameType.PROTECTED, marker);
		region.put(second);
		region.enter(FrameType.PROTECTED, new NameExpression());
		region.put(third);
		
		region.leave(marker, false); // false => don't dispose the marked frame
		
		assertEquals(second, region.get(first.name));
	}
	
	@Test
	public void leaveDisposesVariables() throws Exception {
		final Variable first = mock(Variable.class);
		final Variable second = mock(Variable.class);
		
		when(first.getName()).thenReturn("first");
		when(second.getName()).thenReturn("second");
		
		region.put(first, second);
		region.leave(null);
		
		verify(first).dispose();
		verify(second).dispose();
	}
	
	@Test
	public void leaveDoesNothingWhenThereAreNoFrames() throws Exception {
		new FrameStackRegion().leave(null);
		// No exception should be thrown
	}
	
	@Test
	public void clearAffectsAllFrames() throws Exception {
		final Variable first = createStringVariable("first", "Joe Bloggs");
		final Variable second = createStringVariable("second", "John Doe");
		
		region.put(first);
		region.enter(FrameType.PROTECTED, null);
		region.put(second);
		
		region.clear();
		
		assertEquals(null, region.get(first.name));
		assertEquals(null, region.get(second.name));
	}
	
	@Test
	public void getAllRetrievesFromAllFrames() throws Exception {
		final Variable first = createStringVariable("first", "first");
		final Variable second = createStringVariable("second", "second");
		
		region.put(first);
		region.enter(FrameType.UNPROTECTED, null);
		region.put(second);
		
		Map<String, Variable> all = region.getAll();
		
		assertEquals(first, all.get(first.name));
		assertEquals(second, all.get(second.name));
	}
	
	@Test
	public void getAllRespectsProtectedFrames() throws Exception {
		final Variable invisible = createStringVariable("first", "first");
		final Variable visible = createStringVariable("second", "second");
		
		region.put(invisible);
		region.enter(FrameType.PROTECTED, null);
		region.put(visible);
		
		Map<String, Variable> all = region.getAll();
		
		assertEquals(visible, all.get(visible.name));
		assertEquals(null, all.get(invisible.name));
	}
	
	@Test
	public void getAllFavoursRecentVariables() throws Exception {
		final String nameOfAllVariables = "name";
		final Variable first = createStringVariable(nameOfAllVariables, "first");
		final Variable second = createStringVariable(nameOfAllVariables, "second");
		
		region.put(first);
		region.enter(FrameType.UNPROTECTED, null);
		region.put(second);
		
		Map<String, Variable> all = region.getAll();
		
		assertEquals(second, all.get(nameOfAllVariables));
	}
	
	@Test
	public void removeAffectsAllFrames() throws Exception {
		final String nameOfAllVariables = "name";
		final Variable first = createStringVariable("name", "first");
		final Variable second = createStringVariable("name", "second");
		
		region.put(first);
		region.enter(FrameType.UNPROTECTED, null);
		region.put(second);
		
		region.remove(nameOfAllVariables);
		
		assertEquals(null, region.get(nameOfAllVariables));
		
		region.leave(null);
		assertEquals(null, region.get(nameOfAllVariables));
	}
	
	@Test
	public void removeRespectsProtectedFrames() throws Exception {
		final String nameOfAllVariables = "name";
		final Variable first = createStringVariable("name", "first");
		final Variable second = createStringVariable("name", "second");
		
		region.put(first);
		region.enter(FrameType.PROTECTED, null);
		region.put(second);
		
		region.remove(nameOfAllVariables);
		
		assertEquals(null, region.get(nameOfAllVariables));
		
		region.leave(null);
		assertEquals(first, region.get(nameOfAllVariables));
	}
	
	
	private static Variable createStringVariable(String name, String value) {
		return new Variable(name, value, EolPrimitiveType.String);
	}
}
