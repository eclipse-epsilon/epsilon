/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flock.parse;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.eclipse.epsilon.common.parse.IdentifiableCommonTokenStream;

import junit.framework.TestCase;

public class TestFlock extends TestCase {
	String stdout;
	String stderr;

	public void testFlockModule1() throws Exception {
		// test input: ""
		Object retval = execParser("flockModule", "", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "FLOCKMODULE";

		assertEquals("testing rule "+"flockModule", expecting, actual);
	}

	public void testFlockModule2() throws Exception {
		// test input: "migrate Person { name := nom; }"
		Object retval = execParser("flockModule", "migrate Person { name := nom; }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FLOCKMODULE (MIGRATE Person (BLOCK (:= name nom))))";

		assertEquals("testing rule "+"flockModule", expecting, actual);
	}

	public void testFlockModule3() throws Exception {
		// test input: "migrate Person { name := nom; } migrate Animal { name := nom; }"
		Object retval = execParser("flockModule", "migrate Person { name := nom; } migrate Animal { name := nom; }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FLOCKMODULE (MIGRATE Person (BLOCK (:= name nom))) (MIGRATE Animal (BLOCK (:= name nom))))";

		assertEquals("testing rule "+"flockModule", expecting, actual);
	}

	public void testFlockModule4() throws Exception {
		// test input: "migrate Person { name := nom; } retype Person to Employee"
		Object retval = execParser("flockModule", "migrate Person { name := nom; } retype Person to Employee", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FLOCKMODULE (MIGRATE Person (BLOCK (:= name nom))) (RETYPE Person Employee))";

		assertEquals("testing rule "+"flockModule", expecting, actual);
	}

	public void testFlockModule5() throws Exception {
		// test input: "operation Integer inc() { self := self + 1; }"
		Object retval = execParser("flockModule", "operation Integer inc() { self := self + 1; }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FLOCKMODULE (operation Integer inc (BLOCK (:= self (+ self 1)))))";

		assertEquals("testing rule "+"flockModule", expecting, actual);
	}

	public void testFlockModule6() throws Exception {
		// test input: "@cached operation Integer inc() { self := self + 1; }"
		Object retval = execParser("flockModule", "@cached operation Integer inc() { self := self + 1; }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FLOCKMODULE (ANNOTATIONBLOCK @cached operation Integer inc() { self := self + 1; }))";

		assertEquals("testing rule "+"flockModule", expecting, actual);
	}

	public void testRetyping1() throws Exception {
		// test input: "retype Person to Employee"
		Object retval = execParser("retyping", "retype Person to Employee", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(RETYPE Person Employee)";

		assertEquals("testing rule "+"retyping", expecting, actual);
	}

	public void testRetyping2() throws Exception {
		// test input: "retype Person to Employee when: original.name.isDefined()"
		Object retval = execParser("retyping", "retype Person to Employee when: original.name.isDefined()", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(RETYPE Person Employee (GUARD (. (. original name) (isDefined PARAMETERS))))";

		assertEquals("testing rule "+"retyping", expecting, actual);
	}

	public void testRetyping3() throws Exception {
		// test input: "retype Person Employee"
		Object retval = execParser("retyping", "retype Person Employee", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"retyping", expecting, actual);
	}

	public void testRetyping4() throws Exception {
		// test input: "retype to Employee"
		Object retval = execParser("retyping", "retype to Employee", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"retyping", expecting, actual);
	}

	public void testRetyping5() throws Exception {
		// test input: "retype Person to"
		Object retval = execParser("retyping", "retype Person to", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"retyping", expecting, actual);
	}

	public void testRetyping6() throws Exception {
		// test input: "retype package families to families2"
		Object retval = execParser("retyping", "retype package families to families2", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(RETYPEPACKAGE families families2)";

		assertEquals("testing rule "+"retyping", expecting, actual);
	}

	public void testRetyping7() throws Exception {
		// test input: "retype package families to families2 when: original.name.isDefined()"
		Object retval = execParser("retyping", "retype package families to families2 when: original.name.isDefined()", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(RETYPEPACKAGE families families2 (GUARD (. (. original name) (isDefined PARAMETERS))))";

		assertEquals("testing rule "+"retyping", expecting, actual);
	}

	public void testRetyping8() throws Exception {
		// test input: "retype package families families2"
		Object retval = execParser("retyping", "retype package families families2", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"retyping", expecting, actual);
	}

	public void testRetyping9() throws Exception {
		// test input: "retype package to families2"
		Object retval = execParser("retyping", "retype package to families2", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"retyping", expecting, actual);
	}

	public void testRetyping10() throws Exception {
		// test input: "retype package families to"
		Object retval = execParser("retyping", "retype package families to", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"retyping", expecting, actual);
	}

	public void testDeletion1() throws Exception {
		// test input: "delete Person"
		Object retval = execParser("deletion", "delete Person", false);
		Object actual = examineParserExecResult(10, retval);
		Object expecting = "(DELETE Person)";

		assertEquals("testing rule "+"deletion", expecting, actual);
	}

	public void testDeletion2() throws Exception {
		// test input: "delete Person when: original.name.isUndefined()"
		Object retval = execParser("deletion", "delete Person when: original.name.isUndefined()", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(DELETE Person (GUARD (. (. original name) (isUndefined PARAMETERS))))";

		assertEquals("testing rule "+"deletion", expecting, actual);
	}

	public void testDeletion3() throws Exception {
		// test input: "delete package families"
		Object retval = execParser("deletion", "delete package families", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(DELETEPACKAGE families)";

		assertEquals("testing rule "+"deletion", expecting, actual);
	}

	public void testDeletion4() throws Exception {
		// test input: "delete package families when: original.name.isUndefined()"
		Object retval = execParser("deletion", "delete package families when: original.name.isUndefined()", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(DELETEPACKAGE families (GUARD (. (. original name) (isUndefined PARAMETERS))))";

		assertEquals("testing rule "+"deletion", expecting, actual);
	}

	public void testMigrateRule1() throws Exception {
		// test input: "migrate Person { }"
		Object retval = execParser("migrateRule", "migrate Person { }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(MIGRATE Person BLOCK)";

		assertEquals("testing rule "+"migrateRule", expecting, actual);
	}

	public void testMigrateRule2() throws Exception {
		// test input: "migrate Person { name := nom; "
		Object retval = execParser("migrateRule", "migrate Person { name := nom; ", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"migrateRule", expecting, actual);
	}

	public void testMigrateRule3() throws Exception {
		// test input: "migrate Person { name := nom; }"
		Object retval = execParser("migrateRule", "migrate Person { name := nom; }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(MIGRATE Person (BLOCK (:= name nom)))";

		assertEquals("testing rule "+"migrateRule", expecting, actual);
	}

	public void testMigrateRule4() throws Exception {
		// test input: "migrate Person ignoring name"
		Object retval = execParser("migrateRule", "migrate Person ignoring name", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(MIGRATE Person (IGNORING name))";

		assertEquals("testing rule "+"migrateRule", expecting, actual);
	}

	public void testMigrateRule5() throws Exception {
		// test input: "migrate Person when: original.name.isDefined() { name := nom; }"
		Object retval = execParser("migrateRule", "migrate Person when: original.name.isDefined() { name := nom; }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(MIGRATE Person (GUARD (. (. original name) (isDefined PARAMETERS))) (BLOCK (:= name nom)))";

		assertEquals("testing rule "+"migrateRule", expecting, actual);
	}

	public void testMigrateRule6() throws Exception {
		// test input: "migrate Person when {}"
		Object retval = execParser("migrateRule", "migrate Person when {}", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"migrateRule", expecting, actual);
	}

	public void testMigrateRule7() throws Exception {
		// test input: "migrate Person original.name.isDefined(); {}"
		Object retval = execParser("migrateRule", "migrate Person original.name.isDefined(); {}", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"migrateRule", expecting, actual);
	}

	public void testMigrateRule8() throws Exception {
		// test input: "migrate Person when: original.name.isDefined(); {}"
		Object retval = execParser("migrateRule", "migrate Person when: original.name.isDefined(); {}", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"migrateRule", expecting, actual);
	}

	public void testMigrateRule9() throws Exception {
		// test input: "migrate Person"
		Object retval = execParser("migrateRule", "migrate Person", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"migrateRule", expecting, actual);
	}

	public void testIgnoring1() throws Exception {
		// test input: "ignoring name"
		Object retval = execParser("ignoring", "ignoring name", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(IGNORING name)";

		assertEquals("testing rule "+"ignoring", expecting, actual);
	}

	public void testIgnoring2() throws Exception {
		// test input: "ignoring name, number, address"
		Object retval = execParser("ignoring", "ignoring name, number, address", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(IGNORING name number address)";

		assertEquals("testing rule "+"ignoring", expecting, actual);
	}

	public void testGuard1() throws Exception {
		// test input: "when: original.name.isDefined()"
		Object retval = execParser("guard", "when: original.name.isDefined()", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(GUARD (. (. original name) (isDefined PARAMETERS)))";

		assertEquals("testing rule "+"guard", expecting, actual);
	}

	public void testGuard2() throws Exception {
		// test input: "when { var n := original.name; return n.isDefined(); }"
		Object retval = execParser("guard", "when { var n := original.name; return n.isDefined(); }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(GUARD (BLOCK (:= (var n) (. original name)) (return (. n (isDefined PARAMETERS)))))";

		assertEquals("testing rule "+"guard", expecting, actual);
	}

	public void testGuard3() throws Exception {
		// test input: "original.name.isDefined()"
		Object retval = execParser("guard", "original.name.isDefined()", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"guard", expecting, actual);
	}

	// Invoke target parser.rule
	@SuppressWarnings("resource")
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
			FlockLexer lexer = new FlockLexer(input);
			CommonTokenStream tokens = new IdentifiableCommonTokenStream(lexer);
			FlockParser parser = new FlockParser(tokens);

			parser.prepareForGUnit();

			/** Use Reflection to get rule method from parser */
			Method ruleName = Class.forName("org.eclipse.epsilon.flock.parse.FlockParser").getMethod(testRuleName);

			/** Start of I/O Redirecting */
			PipedInputStream pipedIn = new PipedInputStream();
			PipedOutputStream pipedOut = new PipedOutputStream();
			PipedInputStream pipedErrIn = new PipedInputStream();
			PipedOutputStream pipedErrOut = new PipedOutputStream();
			try {
				pipedOut.connect(pipedIn);
				pipedErrOut.connect(pipedErrIn);
			}
			catch(IOException e) {
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
						Class<?> _return = Class.forName("org.eclipse.epsilon.flock.parse.FlockParser"+"$"+testRuleName+"_return");
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
