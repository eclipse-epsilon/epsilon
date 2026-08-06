package org.eclipse.epsilon.evl.engine.test.acceptance;

import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.evl.EvlModule;
import org.junit.Test;

public class EvlParserTests {
	
	@Test
	public void testLexerError() throws Exception {
		// From https://github.com/eclipse-epsilon/epsilon/issues/140
		EvlModule module = new EvlModule();
		module.parse("context Milestone {\n"
				+ "    constraint ShouldFail {\\\n"
				+ "        check : false\n"
				+ "        message : \"This constraint should fail for every Milestone.\"\n"
				+ "    }\n"
				+ "}\n"
				+ "");
		
		assertEquals(1, module.getParseProblems().size());
	}
	
}
