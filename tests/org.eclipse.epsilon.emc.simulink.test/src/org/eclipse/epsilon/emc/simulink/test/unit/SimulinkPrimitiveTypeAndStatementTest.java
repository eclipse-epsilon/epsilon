/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.test.unit;

import org.eclipse.epsilon.emc.simulink.test.util.AbstractSimulinkTest;
import org.eclipse.epsilon.emc.simulink.common.test.FileUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SimulinkPrimitiveTypeAndStatementTest extends AbstractSimulinkTest {

	@Before
	public void setup() {
		activeCache = false;
		// FIXME: Not working in Maven
		modelFile = FileUtils.getModelFile("sldemo_varsize_basic_copy.slx");		
	}

	@Test
	public void testSID() {
		eol = "M.statement(\"a= Simulink.ID.getSID('sldemo_varsize_basic_copy/Constant')\"); "
				+ "assertEquals('sldemo_varsize_basic_copy:1',M.getWorkspaceVariable('a').println());";
	}
	
	@Test
	public void testSIDwithResult() {
		eol = "var r = M.statementWithResult(\"Simulink.ID.getSID('sldemo_varsize_basic_copy/Constant')\").println();"
				+ "\"assertEquals('sldemo_varsize_basic_copy:1',r)";
	}
	
	@Test
	public void testStatementWithResult() {
		eol = "assert(4 = M.statementWithResult(\"2+2;\")";
	}
	
	@Test
	public void testStatement() {
		eol = "M.statement(\"a = 2+2;\");"
				+ "assert(M.parseMatlabEngineVariable(\"a\") = 4);";
	}
	
	@Test
	public void testComplexTypes() {
		eol = "var selector = Selector.all().first();"
				+ "M.statement('z=roots([1.0, -1.0, 6.0])');"
				+ "var z = M.getWorkspaceVariable('z').println('complex: ');"
				+ "assertEquals(0.49999999999999994d, z.at(0).real);"
				+ "assertEquals(2.3979157616563596d, z.at(0).imag);"
				+ "assertEquals(0.49999999999999994d,z.at(1).real.println());"
				+ "assertEquals(-2.3979157616563596d, z.at(1).imag);"
				;
	}
	
	@Ignore("Not sure what this test does")
	@Test
	public void testNames() {
		eol = 	  "var selector = Selector.all().first();"
				+ "var params = selector.collect(c | c.ObjectParameters).first().println('params: '); "
				+ "var values = params.keySet().println('params: '); "
				+ "for (v in values){ "
				+ " var e = params.get(v); "
				+ " if (e.Type == 'matrix'){ "
				+ " (v + ' ' + e.Type).println();} "
				+ "}"
				+ "params.values().collect(v|v.Type).asSet().println('set: '); ";
	}

	@Ignore("TODO: Check more types")
	@Test
	public void testTypes() {
		eol = 	  "var selector = Selector.all().first();"

				//+ "selector.Capabilities.println('matrix: '); " //matrix UNSUPPORTED BY MATLAB
				+ "selector.ModelParamTableInfo.println('matrix: '); " //matrix
				+ "selector.IndexOptionArray.println('matrix: '); " //matrix
				+ "selector.RuntimeObject.println('matrix: '); " //matrix
				
				+ "selector.Orientation.println('e num: ');" // enum
				
				+ "selector.Handle.println('real: ' );" // real
				+ "selector.FontSize.println('real: ');" // real
				
				+ "selector.MaskObject.println('object: ');" // object
				
				+ "selector.RTWdata.println('list: ');" // list
				;
	}

}