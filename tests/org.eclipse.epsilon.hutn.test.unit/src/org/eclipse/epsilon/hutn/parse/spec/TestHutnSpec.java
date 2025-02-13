/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.hutn.parse.spec;

import junit.framework.TestCase;
import java.io.*;
import java.lang.reflect.*;
import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import org.eclipse.epsilon.common.parse.IdentifiableCommonTokenStream;

public class TestHutnSpec extends TestCase {
	String stdout;
	String stderr;

	public void testPreamble1() throws Exception {
		// test input: "@Spec {}"
		Object retval = execParser("preamble", "@Spec {}", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "@Spec";

		assertEquals("testing rule "+"preamble", expecting, actual);
	}

	public void testPreamble2() throws Exception {
		// test input: "@Spec { metamodel { nsUri: \"family\" } }"
		Object retval = execParser("preamble", "@Spec { metamodel { nsUri: \"family\" } }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(@Spec (metamodel (nsUri \"family\")))";

		assertEquals("testing rule "+"preamble", expecting, actual);
	}

	public void testPreamble3() throws Exception {
		// test input: "@Spec { metamodel { nsUri: \"family\" configFile: \"family.config\" } }"
		Object retval = execParser("preamble", "@Spec { metamodel { nsUri: \"family\" configFile: \"family.config\" } }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(@Spec (metamodel (nsUri \"family\") (configFile \"family.config\")))";

		assertEquals("testing rule "+"preamble", expecting, actual);
	}

	public void testPreamble4() throws Exception {
		// test input: "@Spec { metamodel1 { nsUri: \"family\" configFile: \"family.config\" } metamodel2 { nsUri: \"pets\" } }"
		Object retval = execParser("preamble", "@Spec { metamodel1 { nsUri: \"family\" configFile: \"family.config\" } metamodel2 { nsUri: \"pets\" } }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(@Spec (metamodel1 (nsUri \"family\") (configFile \"family.config\")) (metamodel2 (nsUri \"pets\")))";

		assertEquals("testing rule "+"preamble", expecting, actual);
	}

	public void testPreamble5() throws Exception {
		// test input: "@Spec { metamodel { nsUri: \"family\" } } families { Family \"The Smiths\" {} }"
		Object retval = execParser("preamble", "@Spec { metamodel { nsUri: \"family\" } } families { Family \"The Smiths\" {} }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(@Spec (metamodel (nsUri \"family\")))";

		assertEquals("testing rule "+"preamble", expecting, actual);
	}

	public void testPreamble6() throws Exception {
		// test input: "families { Family \"The Smiths\" {} }"
		Object retval = execParser("preamble", "families { Family \"The Smiths\" {} }", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"preamble", expecting, actual);
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
			HutnSpecLexer lexer = new HutnSpecLexer(input);
			CommonTokenStream tokens = new IdentifiableCommonTokenStream(lexer);
			HutnSpecParser parser = new HutnSpecParser(tokens);

			parser.prepareForGUnit();

			/** Use Reflection to get rule method from parser */
			Method ruleName = Class.forName("org.eclipse.epsilon.hutn.parse.spec.HutnSpecParser").getMethod(testRuleName);

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
						Class<?> _return = Class.forName("org.eclipse.epsilon.hutn.parse.spec.HutnSpecParser"+"$"+testRuleName+"_return");
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
