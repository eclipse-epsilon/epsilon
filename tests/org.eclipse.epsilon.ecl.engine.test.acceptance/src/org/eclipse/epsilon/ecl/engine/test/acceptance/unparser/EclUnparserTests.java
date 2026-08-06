package org.eclipse.epsilon.ecl.engine.test.acceptance.unparser;

import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.engine.test.acceptance.EclAcceptanceTestSuite;
import org.eclipse.epsilon.ecl.parse.EclUnparser;
import org.eclipse.epsilon.eol.engine.test.acceptance.unparser.UnparserTests;
import org.junit.Test;

public class EclUnparserTests extends UnparserTests {
	@Test
	public void testExistingEclFiles() throws Exception {
		test(EclAcceptanceTestSuite.class,
				"trees/trees.ecl",
				"domain/both.ecl",
				"domain/left.ecl",
				"domain/noDomain.ecl",
				"domain/right.ecl"
				);
	}

	@Override
	public EclModule createModule() {
		return new EclModule();
	}

	@Override
	public EclUnparser createUnparser() {
		return new EclUnparser();
	}
	
	

}
