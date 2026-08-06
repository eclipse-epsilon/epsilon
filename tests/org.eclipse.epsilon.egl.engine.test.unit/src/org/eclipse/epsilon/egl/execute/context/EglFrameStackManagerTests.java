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
import org.eclipse.epsilon.egl.output.OutputBuffer;
import org.eclipse.epsilon.egl.traceability.Template;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({EglFrameStackManagerTests.PrepareTests.class, EglFrameStackManagerTests.RestoreTests.class})
public class EglFrameStackManagerTests {
	
	private static final OutputBuffer outputBuffer = new OutputBuffer();

	private static final Variable outVariable         = Variable.createReadOnlyVariable("out", outputBuffer);
	private static final Variable firstLocalVariable  = Variable.createReadOnlyVariable("foo", "bar");
	private static final Variable secondLocalVariable = Variable.createReadOnlyVariable("num", 42);
	
	private static final Template templateWithVariables = createTemplateWithVariables();
	private static final EglTemplate spec = createTemplate(templateWithVariables);

	private static EglContext context;
	private static EglExecutionManager manager;		
	
	public static class PrepareTests {
		@Before
		public void clearFrameStack() {
			manager = new EglExecutionManager(context = new EglContext());
		}
		
		@Test
		public void prepareShouldSetOutputBufferGlobalVariable() {
			manager.prepareFor(spec, outputBuffer);
			assertFrameStackHasGlobal(outVariable);
		}
		
		@Test
		public void prepareShouldOverwriteExitingOutVariable() {
			context.getFrameStack().putGlobal(Variable.createReadOnlyVariable("out", new OutputBuffer()));
			
			manager.prepareFor(spec, outputBuffer);
			
			assertFrameStackHasGlobal(outVariable);
		}
		
		@Test
		public void prepareShouldSetTemplateVariablesAsLocals() {
			manager.prepareFor(spec, outputBuffer);
			
			assertFrameStackHasLocal(firstLocalVariable);
			assertFrameStackHasLocal(secondLocalVariable);
		}
		
		@Test
		public void prepareShouldOverwriteLocalVariablesWithTemplateVariables() {
			context.getFrameStack().put(firstLocalVariable);
			context.getFrameStack().put(secondLocalVariable);
			
			manager.prepareFor(spec, outputBuffer);
			
			assertFrameStackHasLocal(firstLocalVariable);
			assertFrameStackHasLocal(secondLocalVariable);
		}
	}
	
	
	public static class RestoreTests {	
		@Before
		public void clearFrameStack() {
			manager = new EglExecutionManager(context = new EglContext());
		}
		
		@Test
		public void restoreShouldRemoveOut() {
			manager.prepareFor(spec, outputBuffer);
			manager.restore();
			
			assertFrameStackHasNoSuchGlobal(outVariable);		
		}
		
		@Test
		public void restoreShouldRestoreExistingOutToFrameStack() {
			final Variable existingOut = Variable.createReadOnlyVariable("out", new OutputBuffer());
			context.getFrameStack().putGlobal(existingOut);
			
			manager.prepareFor(spec, outputBuffer);
			manager.restore();
			
			assertFrameStackHasGlobal(existingOut);		
		}
		
		@Test
		public void restoreShouldRemoveLocalVariables() {
			manager.prepareFor(spec, outputBuffer);
			manager.restore();
			
			assertFrameStackHasNoSuchLocal(firstLocalVariable);		
			assertFrameStackHasNoSuchLocal(secondLocalVariable);		
		}
		
		@Test
		public void restoreShouldRestoreExistingLocalVariables() {
			final Variable existingLocal = Variable.createReadOnlyVariable("name", "Franz");
			context.getFrameStack().put(existingLocal);
			
			manager.prepareFor(spec, outputBuffer);
			manager.restore();
			
			assertFrameStackHasLocal(existingLocal);
		}
	}
	
	private static void assertFrameStackHasGlobal(Variable variable) {
		assertEquals(variable.getValue(), getGlobalVariable(variable.getName()).getValue());
	}
	
	private static void assertFrameStackHasNoSuchGlobal(Variable variable) {
		assertNull(getGlobalVariable(variable.getName()));
	}
	
	private static void assertFrameStackHasLocal(Variable variable) {
		assertEquals(variable.getValue(), getLocalVariable(variable.getName()).getValue());
	}
	
	private static void assertFrameStackHasNoSuchLocal(Variable variable) {
		assertNull(getLocalVariable(variable.getName()));
	}
	
	private static Variable getGlobalVariable(String name) {
		return context.getFrameStack().getGlobal(name);
	}

	private static Variable getLocalVariable(String name) {
		return context.getFrameStack().get(name);
	}

	private static Template createTemplateWithVariables() {
		final Template templateWithVariables = new Template();
		templateWithVariables.addVariable(firstLocalVariable.getName(), firstLocalVariable.getValue());
		templateWithVariables.addVariable(secondLocalVariable.getName(), secondLocalVariable.getValue());
		return templateWithVariables;
	}
	
	static EglTemplate createTemplate(Template template) {
		final EglTemplate t = mock(EglTemplate.class);
		when(t.getTemplate()).thenReturn(template);
		return t;
	}
}
