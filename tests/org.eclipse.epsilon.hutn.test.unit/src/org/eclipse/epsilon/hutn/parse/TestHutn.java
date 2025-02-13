/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.hutn.parse;

import junit.framework.TestCase;
import java.io.*;
import java.lang.reflect.*;
import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import org.eclipse.epsilon.common.parse.IdentifiableCommonTokenStream;

public class TestHutn extends TestCase {
	String stdout;
	String stderr;

	public void testDocument1() throws Exception {
		// test input: "FamilyPackage {}\nVehiclePackage {}"
		Object retval = execParser("document", "FamilyPackage {}\nVehiclePackage {}", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "FamilyPackage VehiclePackage";

		assertEquals("testing rule "+"document", expecting, actual);
	}

	public void testPkg1() throws Exception {
		// test input: "FamilyPackage {}"
		Object retval = execParser("pkg", "FamilyPackage {}", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "FamilyPackage";

		assertEquals("testing rule "+"pkg", expecting, actual);
	}

	public void testPkg2() throws Exception {
		// test input: "FamilyPackage \n\t{}"
		Object retval = execParser("pkg", "FamilyPackage \n\t{}", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "FamilyPackage";

		assertEquals("testing rule "+"pkg", expecting, actual);
	}

	public void testPkg3() throws Exception {
		// test input: "FamilyPackage \"id-001\" {}"
		Object retval = execParser("pkg", "FamilyPackage \"id-001\" {}", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FamilyPackage \"id-001\")";

		assertEquals("testing rule "+"pkg", expecting, actual);
	}

	public void testPkg4() throws Exception {
		// test input: "FamilyPackage { Family {} }"
		Object retval = execParser("pkg", "FamilyPackage { Family {} }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FamilyPackage Family)";

		assertEquals("testing rule "+"pkg", expecting, actual);
	}

	public void testPkg5() throws Exception {
		// test input: "FamilyPackage { Family \"id-002\" {} }"
		Object retval = execParser("pkg", "FamilyPackage { Family \"id-002\" {} }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FamilyPackage (Family \"id-002\"))";

		assertEquals("testing rule "+"pkg", expecting, actual);
	}

	public void testPkg6() throws Exception {
		// test input: "FamilyPackage { Family {} Vehicle {} }"
		Object retval = execParser("pkg", "FamilyPackage { Family {} Vehicle {} }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FamilyPackage Family Vehicle)";

		assertEquals("testing rule "+"pkg", expecting, actual);
	}

	public void testPkg7() throws Exception {
		// test input: "FamilyPackage { Family.nuclear = true; }"
		Object retval = execParser("pkg", "FamilyPackage { Family.nuclear = true; }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FamilyPackage (CLS_LVL_ATTRIBUTE (Family (nuclear true))))";

		assertEquals("testing rule "+"pkg", expecting, actual);
	}

	public void testPkg8() throws Exception {
		// test input: "FamilyPackage {  Family {} Family.nuclear = true; Vehicle {} }"
		Object retval = execParser("pkg", "FamilyPackage {  Family {} Family.nuclear = true; Vehicle {} }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FamilyPackage Family (CLS_LVL_ATTRIBUTE (Family (nuclear true))) Vehicle)";

		assertEquals("testing rule "+"pkg", expecting, actual);
	}

	public void testPkg9() throws Exception {
		// test input: "FamilyPackage {\n\t\t\tvehicles {\n\t\t\t\tFamily \"The Smiths\"\n\t\t\t\tVehicle \"Honda\"\n\t\t\t}\n\t\t\tFamily \"The Smiths\" {}\n\t\t\tVehicle \"Honda\" {}\n\t}"
		Object retval = execParser("pkg", "FamilyPackage {\n\t\t\tvehicles {\n\t\t\t\tFamily \"The Smiths\"\n\t\t\t\tVehicle \"Honda\"\n\t\t\t}\n\t\t\tFamily \"The Smiths\" {}\n\t\t\tVehicle \"Honda\" {}\n\t}", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FamilyPackage (vehicles (Family \"The Smiths\") (Vehicle \"Honda\")) (Family \"The Smiths\") (Vehicle \"Honda\"))";

		assertEquals("testing rule "+"pkg", expecting, actual);
	}

	public void testPkg10() throws Exception {
		// test input: "FamilyPackage {\n\t\t\tFamily \"The Smiths\" vehicles Vehicle \"Honda\";\n\t\t\tFamily \"The Smiths\" {}\n\t\t\tVehicle \"Honda\" {}\n\t}"
		Object retval = execParser("pkg", "FamilyPackage {\n\t\t\tFamily \"The Smiths\" vehicles Vehicle \"Honda\";\n\t\t\tFamily \"The Smiths\" {}\n\t\t\tVehicle \"Honda\" {}\n\t}", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FamilyPackage (vehicles (Family \"The Smiths\") (Vehicle \"Honda\")) (Family \"The Smiths\") (Vehicle \"Honda\"))";

		assertEquals("testing rule "+"pkg", expecting, actual);
	}

	public void testCls1() throws Exception {
		// test input: "Family {}"
		Object retval = execParser("cls", "Family {}", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "Family";

		assertEquals("testing rule "+"cls", expecting, actual);
	}

	public void testCls2() throws Exception {
		// test input: "Family \"id-002\" {}"
		Object retval = execParser("cls", "Family \"id-002\" {}", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Family \"id-002\")";

		assertEquals("testing rule "+"cls", expecting, actual);
	}

	public void testCls3() throws Exception {
		// test input: "\n\tFamily \"id-002\" {} \n"
		Object retval = execParser("cls", "\n\tFamily \"id-002\" {} \n", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Family \"id-002\")";

		assertEquals("testing rule "+"cls", expecting, actual);
	}

	public void testCls4() throws Exception {
		// test input: "Family;"
		Object retval = execParser("cls", "Family;", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "Family";

		assertEquals("testing rule "+"cls", expecting, actual);
	}

	public void testCls5() throws Exception {
		// test input: "Family \"The Smiths\";"
		Object retval = execParser("cls", "Family \"The Smiths\";", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Family \"The Smiths\")";

		assertEquals("testing rule "+"cls", expecting, actual);
	}

	public void testCls6() throws Exception {
		// test input: "rich ~nuclear #migrant Family \"id-002\" {}"
		Object retval = execParser("cls", "rich ~nuclear #migrant Family \"id-002\" {}", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Family rich ~nuclear #migrant \"id-002\")";

		assertEquals("testing rule "+"cls", expecting, actual);
	}

	public void testCls7() throws Exception {
		// test input: "Family { #migrant\n name: \"The Smiths\"\n averageAge: 23.4 }"
		Object retval = execParser("cls", "Family { #migrant\n name: \"The Smiths\"\n averageAge: 23.4 }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Family #migrant (name \"The Smiths\") (averageAge 23.4))";

		assertEquals("testing rule "+"cls", expecting, actual);
	}

	public void testCls_contents1() throws Exception {
		// test input: "Address: \"7 Main Street\""
		Object retval = execParser("cls_contents", "Address: \"7 Main Street\"", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Address \"7 Main Street\")";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents2() throws Exception {
		// test input: "Address = \"7 Main Street\""
		Object retval = execParser("cls_contents", "Address = \"7 Main Street\"", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Address \"7 Main Street\")";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents3() throws Exception {
		// test input: "Address = \"\""
		Object retval = execParser("cls_contents", "Address = \"\"", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Address \"\")";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents4() throws Exception {
		// test input: "Formula = \"a > b and b < c\""
		Object retval = execParser("cls_contents", "Formula = \"a > b and b < c\"", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Formula \"a > b and b < c\")";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents5() throws Exception {
		// test input: "Formula = \"(a and b) or c)\""
		Object retval = execParser("cls_contents", "Formula = \"(a and b) or c)\"", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Formula \"(a and b) or c)\")";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents6() throws Exception {
		// test input: "Address = \"The House, 120 Main Street\""
		Object retval = execParser("cls_contents", "Address = \"The House, 120 Main Street\"", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Address \"The House, 120 Main Street\")";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents7() throws Exception {
		// test input: "Name = \"John \\\"Invisible\\\" Doe\""
		Object retval = execParser("cls_contents", "Name = \"John \\\"Invisible\\\" Doe\"", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Name \"John \"Invisible\" Doe\")";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents8() throws Exception {
		// test input: "Address: null"
		Object retval = execParser("cls_contents", "Address: null", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Address null)";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents9() throws Exception {
		// test input: "Nuclear = true"
		Object retval = execParser("cls_contents", "Nuclear = true", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Nuclear true)";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents10() throws Exception {
		// test input: "Nuclear = false"
		Object retval = execParser("cls_contents", "Nuclear = false", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Nuclear false)";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents11() throws Exception {
		// test input: "NumberOfPets = 2"
		Object retval = execParser("cls_contents", "NumberOfPets = 2", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(NumberOfPets 2)";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents12() throws Exception {
		// test input: "AverageAge   = 25.3"
		Object retval = execParser("cls_contents", "AverageAge   = 25.3", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(AverageAge 25.3)";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents13() throws Exception {
		// test input: "Address = \"7 Main Street\", \"17 Fulford Road\""
		Object retval = execParser("cls_contents", "Address = \"7 Main Street\", \"17 Fulford Road\"", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Address \"7 Main Street\" \"17 Fulford Road\")";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents14() throws Exception {
		// test input: "Address = \"The House, 120 Main Street\", \"17 Fulford Road\""
		Object retval = execParser("cls_contents", "Address = \"The House, 120 Main Street\", \"17 Fulford Road\"", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Address \"The House, 120 Main Street\" \"17 Fulford Road\")";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents15() throws Exception {
		// test input: "Nuclear = true, false, false, true"
		Object retval = execParser("cls_contents", "Nuclear = true, false, false, true", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Nuclear true false false true)";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents16() throws Exception {
		// test input: "migrant"
		Object retval = execParser("cls_contents", "migrant", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "migrant";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents17() throws Exception {
		// test input: "~nuclear"
		Object retval = execParser("cls_contents", "~nuclear", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "~nuclear";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents18() throws Exception {
		// test input: "#migrant"
		Object retval = execParser("cls_contents", "#migrant", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "#migrant";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents19() throws Exception {
		// test input: "breed = labrador"
		Object retval = execParser("cls_contents", "breed = labrador", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(breed labrador)";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents20() throws Exception {
		// test input: "Friends: Family \"The Does\""
		Object retval = execParser("cls_contents", "Friends: Family \"The Does\"", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Friends (Family \"The Does\"))";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents21() throws Exception {
		// test input: "Friends: Family \"The Does\", Family \"The Bloggs\""
		Object retval = execParser("cls_contents", "Friends: Family \"The Does\", Family \"The Bloggs\"", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Friends (Family \"The Does\") (Family \"The Bloggs\"))";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents22() throws Exception {
		// test input: "Friends: #migrant Family \"The Does\""
		Object retval = execParser("cls_contents", "Friends: #migrant Family \"The Does\"", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents23() throws Exception {
		// test input: "Pets: Pet \"Fido\" { Name: \"Fido\" }"
		Object retval = execParser("cls_contents", "Pets: Pet \"Fido\" { Name: \"Fido\" }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Pets (Pet \"Fido\" (Name \"Fido\")))";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents24() throws Exception {
		// test input: "Pets: #Male Pet \"Fido\" { Name: \"Fido\" }"
		Object retval = execParser("cls_contents", "Pets: #Male Pet \"Fido\" { Name: \"Fido\" }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Pets (Pet #Male \"Fido\" (Name \"Fido\")))";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_contents25() throws Exception {
		// test input: "Pets: Pet { Name: \"Fido\" }"
		Object retval = execParser("cls_contents", "Pets: Pet { Name: \"Fido\" }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(Pets (Pet (Name \"Fido\")))";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}


	public void testCls_contents26() throws Exception {
		// test input: "AverageAge   = 2.53e1"
		Object retval = execParser("cls_contents", "AverageAge   = 2.53e1", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(AverageAge 2.53e1)";

		assertEquals("testing rule "+"cls_contents", expecting, actual);
	}

	public void testCls_level_attribute1() throws Exception {
		// test input: "Family.nuclear = true;"
		Object retval = execParser("cls_level_attribute", "Family.nuclear = true;", false);
		Object actual = examineParserExecResult(10, retval);
		Object expecting = "(CLS_LVL_ATTRIBUTE (Family (nuclear true)))";

		assertEquals("testing rule "+"cls_level_attribute", expecting, actual);
	}

	public void testCls_level_attribute2() throws Exception {
		// test input: "Family.nuclear: true;"
		Object retval = execParser("cls_level_attribute", "Family.nuclear: true;", false);
		Object actual = examineParserExecResult(10, retval);
		Object expecting = "(CLS_LVL_ATTRIBUTE (Family (nuclear true)))";

		assertEquals("testing rule "+"cls_level_attribute", expecting, actual);
	}

	public void testCls_level_attribute3() throws Exception {
		// test input: "Family.address = \"123 Main Street\", \"34 Fulford Road\";"
		Object retval = execParser("cls_level_attribute", "Family.address = \"123 Main Street\", \"34 Fulford Road\";", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(CLS_LVL_ATTRIBUTE (Family (address \"123 Main Street\" \"34 Fulford Road\")))";

		assertEquals("testing rule "+"cls_level_attribute", expecting, actual);
	}

	public void testCls_level_attribute4() throws Exception {
		// test input: "Family.nuclear = true"
		Object retval = execParser("cls_level_attribute", "Family.nuclear = true", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"cls_level_attribute", expecting, actual);
	}

	public void testAssoc_block1() throws Exception {
		// test input: "vehicles { Family \"The Smiths\" Vehicle \"Honda\" }"
		Object retval = execParser("assoc_block", "vehicles { Family \"The Smiths\" Vehicle \"Honda\" }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(vehicles (Family \"The Smiths\") (Vehicle \"Honda\"))";

		assertEquals("testing rule "+"assoc_block", expecting, actual);
	}

	public void testAssoc_block2() throws Exception {
		// test input: "vehicles { Family \"The Smiths\" Vehicle \"Honda\" Family \"The Smiths\" Vehicle \"Toyota\" }"
		Object retval = execParser("assoc_block", "vehicles { Family \"The Smiths\" Vehicle \"Honda\" Family \"The Smiths\" Vehicle \"Toyota\" }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(vehicles (Family \"The Smiths\") (Vehicle \"Honda\") (Family \"The Smiths\") (Vehicle \"Toyota\"))";

		assertEquals("testing rule "+"assoc_block", expecting, actual);
	}

	public void testAssoc_block3() throws Exception {
		// test input: "vehicles {}"
		Object retval = execParser("assoc_block", "vehicles {}", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"assoc_block", expecting, actual);
	}

	public void testInfix_assoc1() throws Exception {
		// test input: "Family \"The Smiths\" vehicles Vehicle \"Honda\";"
		Object retval = execParser("infix_assoc", "Family \"The Smiths\" vehicles Vehicle \"Honda\";", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(vehicles (Family \"The Smiths\") (Vehicle \"Honda\"))";

		assertEquals("testing rule "+"infix_assoc", expecting, actual);
	}

	public void testInfix_assoc2() throws Exception {
		// test input: "Family \"The Smiths\" vehicles Vehicle \"Honda\""
		Object retval = execParser("infix_assoc", "Family \"The Smiths\" vehicles Vehicle \"Honda\"", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"infix_assoc", expecting, actual);
	}

	public void testFeature_contents1() throws Exception {
		// test input: "Family \"The Smiths\" { name: \"The Smiths\" }, Family \"The Does\""
		Object retval = execParser("feature_contents", "Family \"The Smiths\" { name: \"The Smiths\" }, Family \"The Does\"", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"feature_contents", expecting, actual);
	}

	// Invoke target parser.rule
	public Object execParser(String testRuleName, String testInput, boolean isFile) throws Exception {
		CharStream input;
		/** Set up ANTLR input stream based on input source, file or String */
		if ( isFile==true ) {
			input = new ANTLRFileStream(testInput);
		}
		else {
			input = new ANTLRStringStream(testInput);
		}
		try {
			HutnLexer lexer = new HutnLexer(input);
			CommonTokenStream tokens = new IdentifiableCommonTokenStream(lexer);
			HutnParser parser = new HutnParser(tokens);

			parser.prepareForGUnit();

			/** Use Reflection to get rule method from parser */
			Method ruleName = Class.forName("org.eclipse.epsilon.hutn.parse.HutnParser").getMethod(testRuleName);

			/** Start of I/O Redirecting */
			PipedInputStream pipedIn = new PipedInputStream();
			PipedOutputStream pipedOut = new PipedOutputStream();
			PipedInputStream pipedErrIn = new PipedInputStream();
			PipedOutputStream pipedErrOut = new PipedOutputStream();
			try {
				pipedOut.connect(pipedIn);
				pipedErrOut.connect(pipedErrIn);
			}
			catch (IOException e) {
				System.err.println("connection failed...");
			}
			PrintStream console = System.out;
			PrintStream consoleErr = System.err;
			PrintStream ps = new PrintStream(pipedOut);
			PrintStream ps2 = new PrintStream(pipedErrOut);
			System.setOut(ps);
			System.setErr(ps2);
			/** End of redirecting */

			/** Invoke grammar rule, and store if there is a return value */
			Object ruleReturn = ruleName.invoke(parser);
			String astString = null;
			/** If rule has return value, determine if it's an AST */
			if ( ruleReturn!=null ) {
				/** If return object is instanceof AST, get the toStringTree */
				if ( ruleReturn.toString().indexOf(testRuleName+"_return")>0 ) {
					try {	// NullPointerException may happen here...
						Class<?> _return = Class.forName("org.eclipse.epsilon.hutn.parse.HutnParser"+"$"+testRuleName+"_return");
						Method[] methods = _return.getDeclaredMethods();
                		for(Method method : methods) {
			                if ( method.getName().equals("getTree") ) {
			                	Method returnName = _return.getMethod("getTree");
		                    	CommonTree tree = (CommonTree) returnName.invoke(ruleReturn);
		                    	astString = tree.toStringTree();
			                }
			            }
					}
					catch(Exception e) {
                		System.err.println(e);
                	}
				}
			}

			org.antlr.gunit.gUnitExecuter.StreamVacuum stdoutVacuum = new org.antlr.gunit.gUnitExecuter.StreamVacuum(pipedIn);
			org.antlr.gunit.gUnitExecuter.StreamVacuum stderrVacuum = new org.antlr.gunit.gUnitExecuter.StreamVacuum(pipedErrIn);
			ps.close();
			ps2.close();
			System.setOut(console);			// Reset standard output
			System.setErr(consoleErr);		// Reset standard err out
			this.stdout = null;
			this.stderr = null;
			stdoutVacuum.start();
			stderrVacuum.start();			
			stdoutVacuum.join();
			stderrVacuum.join();
			// retVal could be actual return object from rule, stderr or stdout
			if ( stderrVacuum.toString().length()>0 ) {
				this.stderr = stderrVacuum.toString();
				return this.stderr;
			}
			if ( stdoutVacuum.toString().length()>0 ) {
				this.stdout = stdoutVacuum.toString();
			}
			if ( astString!=null ) {	// Return toStringTree of AST
				return astString;
			}
			if ( ruleReturn!=null ) {
				return ruleReturn;
			}
			if ( stderrVacuum.toString().length()==0 && stdoutVacuum.toString().length()==0 ) {
				return null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return stdout;
	}

	// Modify the return value if the expected token type is OK or FAIL
	public Object examineParserExecResult(int tokenType, Object retVal) {	
		if ( tokenType==27 ) {	// expected Token: OK
			if ( this.stderr==null ) {
				return "OK";
			}
			else {
				return "FAIL";
			}
		}
		else if ( tokenType==28 ) {	// expected Token: FAIL
			if ( this.stderr!=null ) {
				return "FAIL";
			}
			else {
				return "OK";
			}
		}
		else {	// return the same object for the other token types
			return retVal;
		}		
	}

}
