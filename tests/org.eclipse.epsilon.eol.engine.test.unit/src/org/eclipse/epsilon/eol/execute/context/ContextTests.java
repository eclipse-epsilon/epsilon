package org.eclipse.epsilon.eol.execute.context;

import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.eol.EolModule;
import org.junit.Test;

public class ContextTests {
	
	@Test
	public void testModuleOfContextIsModuleItself() {
		
		EolModule module = new EolModule();
		assertEquals(module, module.getContext().getModule());
	}

}
