package org.eclipse.epsilon.common.parse;

import org.eclipse.epsilon.eol.EolModule;
import org.junit.Test;

public class TokenStreamParsersTests {

	@Test
	public void testMemoryLeak() throws Exception {
		
		// Test that keys of EpsilonParser.tokenStreamParsers
		// are removed by the garbage collector at some point
		int keys = EpsilonParser.tokenStreamParsers.keySet().size();
		
		while (true) {
			EolModule module = new EolModule();
			module.parse("return;");
			int newKeys = EpsilonParser.tokenStreamParsers.keySet().size();
			
			if (newKeys < keys) {
				return;
			}
			else {
				keys = newKeys;
			}
		}
	}
	
	// Run this method using a profiler to manually check
	// for other possible memory leaks related to parsing
	public static void main(String[] args) throws Exception {
		while (true) {
			EolModule module = new EolModule();
			module.parse("return;");
			System.out.print("");
		}
	}
	
}
