/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.execute.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.traceability.Template;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({EglExecutionManagerTests.PrepareTests.class, EglExecutionManagerTests.RestoreTests.class})
public class EglExecutionManagerTests {
	
	private static final EglTemplate
		firstSpec = createTemplate("First"),
		secondSpec = createTemplate("Second"),
		thirdSpec  = createTemplate("Third");
	
	static EglTemplate createTemplate(String name) {
		final EglTemplate template = mock(EglTemplate.class);
		when(template.getTemplate()).thenReturn(new Template(name));
		return template;
	}
	
	public static class PrepareTests {
		private final EglExecutionManager manager = new EglExecutionManager(new EglContext());
		
		@Before
		public void prepareForSpecs() {
			manager.prepareFor(firstSpec);
			manager.prepareFor(secondSpec);
			manager.prepareFor(thirdSpec);
		}
		
		@Test
		public void setsBaseAsFirstToBePrepared() throws Exception {
			assertEquals(firstSpec, manager.getBase().template);
		}
		
		@Test
		public void setsCurrentAsLastToBePrepared() throws Exception {
			assertEquals(thirdSpec, manager.getCurrent().template);
		}
	
	}
	
	public static class RestoreTests {
		private final EglExecutionManager manager = new EglExecutionManager(new EglContext());
		
		@Before
		public void prepareForSpecs() {
			manager.prepareFor(firstSpec);
			manager.prepareFor(secondSpec);
			manager.prepareFor(thirdSpec);
		}
		
		@Test
		public void restoresPreviousAsCurrent() throws Exception {
			manager.restore();
			
			assertEquals(secondSpec, manager.getCurrent().template);
		}
		
		@Test
		public void restoresTwoAgoAsCurrent() throws Exception {
			manager.restore();
			manager.restore();
			
			assertEquals(firstSpec, manager.getCurrent().template);
		}
		
		@Test
		public void restoresNullWhenCalledAsManyTimesAsPrepare() throws Exception {
			manager.restore();
			manager.restore();
			manager.restore();
			
			assertNull(manager.getCurrent());
		}
		
		@Test
		public void doesNotDestroyBase() throws Exception {
			manager.restore();
			manager.restore();
			manager.restore();
			
			assertEquals(firstSpec, manager.getBase().template);
		}
	}
}
