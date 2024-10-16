/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.preprocessor;

import static org.junit.Assert.*;
import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;
import static org.eclipse.epsilon.egl.util.FileUtil.ESCAPED_NEWLINE;
import java.io.File;
import org.eclipse.epsilon.common.parse.EpsilonTreeAdaptor;
import org.eclipse.epsilon.egl.parse.EglLexer;
import org.eclipse.epsilon.egl.parse.EglParser;
import org.junit.Test;

public class TestPreprocessor {
	
	private final Preprocessor preprocessor = new Preprocessor();
	
	private String preprocess(String program) {
		final EglLexer lexer = new EglLexer(program);
		final EglParser parser = new EglParser(lexer, new EpsilonTreeAdaptor((File)null));

		parser.parse();
		
		return preprocessor.convertToEol(parser.getAST());
	}
	
	@Test
	public void testProgram() {
		final String egl = "[%var x : String;%]";
		final String eol = "var x : String;";

		assertEquals(eol, preprocess(egl));
	}
	
	@Test
	public void testText() {
		final String egl = "Hello World!";
		final String eol = "out.prinx('Hello World!');";

		assertEquals(eol, preprocess(egl));
	}
	
	@Test
	public void testTextMultiline() {
		final String egl = "Hello World!" + NEWLINE +
		                   "foo";
		final String eol = "out.prinx('Hello World!');" + NEWLINE +
		                   "out.prinx('" + ESCAPED_NEWLINE + "');" + NEWLINE +
		                   "out.prinx('foo');";
		
		assertEquals(eol, preprocess(egl));
	}
	
	@Test
	public void testEscapeQuote() {
		final String egl = "'";
		final String eol = "out.prinx('\\'');";

		assertEquals(eol, preprocess(egl));
	}
	
	@Test
	public void testEscapeDoubleQuote() {
		final String egl = "\"";
		final String eol = "out.prinx('\\\"');";

		assertEquals(eol, preprocess(egl));
	}
	
	@Test
	public void testEscapeBackslash() {
		final String egl = "\\";
		final String eol = "out.prinx('\\\\');";

		assertEquals(eol, preprocess(egl));
	}
	
	@Test
	public void testEscapeTab() {
		final String egl = "\t";
		final String eol = "out.prinx('\\t');";

		assertEquals(eol, preprocess(egl));
	}
	
	@Test
	public void testEscapeNewLine() {
		final String egl = NEWLINE;
		final String eol = "out.prinx('"+ESCAPED_NEWLINE+"');";

		assertEquals(eol, preprocess(egl));
	}
	
	@Test
	public void testOutput() {
		final String egl = "[%=foo%]";
		final String eol = "out.printdyn(foo);";

		assertEquals(eol, preprocess(egl));
	}
	
	@Test
	public void testGobbleWhitespaceBeforeStartTag() {
		final String egl = " \t[%var x : String;%]";
		final String eol = "var x : String;";
		
		assertEquals(eol, preprocess(egl));
	}
	
	@Test
	public void testMultipleLines() {
		final String egl = "[%for (i in Sequence{1..10}) {" + NEWLINE + 
		                   "out.prinx(i);" + NEWLINE + 
		                   "}%]";
		
		final String eol = "for (i in Sequence{1..10}) {" + NEWLINE +
		                   "out.prinx(i);" + NEWLINE +
		                   "}";
		
		assertEquals(eol, preprocess(egl));
	}
	
	@Test
	public void testMultipleTagsOnOneLineGenerateMultipleLinesOfEol() {
		final String egl = "[%for (i in Sequence{1..10}) {%][%out.print(i);%][%}%]";
		final String eol = "for (i in Sequence{1..10}) {" + NEWLINE +
		                   "out.print(i);" + NEWLINE +
		                   "}";
		
		assertEquals(eol, preprocess(egl));
	}
	
	@Test
	public void testComment() {
		final String egl = "[%for (i in Sequence{1..10}) {%][* [% j := i + 2 %] i plus 2 is [%=j%] *][%out.print(i);%][%}%]";
		final String eol = "for (i in Sequence{1..10}) {" + NEWLINE +
		                   "out.print(i);" + NEWLINE +
		                   "}";
		
//		System.err.println(preprocess(egl));
		
		assertEquals(eol, preprocess(egl));
	}
	
	
	/// Traceability tests \\\
	@Test
	public void testTraceabilityStatic() {
		final String program = "Only static";
		//                      12345678901
		
		
		// out.print("Only static");
		// 123456789012
		
		preprocess(program);
//		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		assertEquals(1, preprocessor.getTrace().getEglColumnNumberFor(1, 12));
	}
	
	
	@Test
	public void testTraceabilityStaticInTheMiddleOfALine() {
		final String program = "[%var x = 1;%]this is static[%=foo.bar()%]";
		//                      12345678901234567890123456789012
				
		preprocess(program);
//		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		assertEquals(1, preprocessor.getTrace().getEglLineNumberFor(2));
		assertEquals(15, preprocessor.getTrace().getEglColumnNumberFor(2, 12));
		
		assertEquals(1, preprocessor.getTrace().getEglLineNumberFor(3));
		assertEquals(32, preprocessor.getTrace().getEglColumnNumberFor(3, 14));
	}
	
	
	@Test
	public void testTraceabilityOneLine() {
		final String program = "[%for (i in Sequence{1..10}) {%][%out.print(i);%][%}%]";
		
		preprocess(program);
//		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		assertEquals(1, preprocessor.getTrace().getEglLineNumberFor(1));
		assertEquals(1, preprocessor.getTrace().getEglLineNumberFor(2));
		assertEquals(1, preprocessor.getTrace().getEglLineNumberFor(3));
	}
	
	@Test
	public void testTraceabilityMultiLineMultiTag() {
		final String program = "[%for (i in Sequence{1..10}) {%]" + NEWLINE +
		                       "[%out.print(i);%]" + NEWLINE + 
		                       "[%}%]";

		preprocess(program);
//		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		assertEquals(1, preprocessor.getTrace().getEglLineNumberFor(1));
		assertEquals(2, preprocessor.getTrace().getEglLineNumberFor(2));
		assertEquals(3, preprocessor.getTrace().getEglLineNumberFor(3));
	}
	
	@Test
	public void testTraceabilityMultiLineSingleTag() {
		final String program = "[%for (i in Sequence{1..10}) {" + NEWLINE +
		                       "out.print(i);" + NEWLINE + 
		                       "}%]";

		preprocess(program);
//		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		assertEquals(1, preprocessor.getTrace().getEglLineNumberFor(1));
		assertEquals(2, preprocessor.getTrace().getEglLineNumberFor(2));
		assertEquals(3, preprocessor.getTrace().getEglLineNumberFor(3));
	}
	
	@Test
	public void testTraceabilityAfterTag() {
		final String program = "[%" + NEWLINE +
		                       "  var x : Integer := 5;%]" + NEWLINE +
		                       "[% x := x + 1; %]";
		
		preprocess(program);
//		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		assertEquals(1, preprocessor.getTrace().getEglLineNumberFor(1));
		assertEquals(2, preprocessor.getTrace().getEglLineNumberFor(2));
		assertEquals(3, preprocessor.getTrace().getEglLineNumberFor(3));
	}
	
	@Test
	public void testTraceabilityTagsOnSeparateLines() {
		final String program = "[%" + NEWLINE +
		                       "  var x : Integer := 5;" + NEWLINE + 
		                       "%]";
		
		preprocess(program);
//		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		assertEquals(1, preprocessor.getTrace().getEglLineNumberFor(1));
		assertEquals(2, preprocessor.getTrace().getEglLineNumberFor(2));
	}
	
	@Test
	public void testTraceabilityAfterTagsOnSeparateLines() {
		final String program = "[%" + NEWLINE +
		                       "  var x : Integer := 5;" + NEWLINE + 
		                       "%]" + NEWLINE +
		                       "[% x := x + 1; %]";
		
		preprocess(program);
//		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		assertEquals(1, preprocessor.getTrace().getEglLineNumberFor(1));
		assertEquals(2, preprocessor.getTrace().getEglLineNumberFor(2));
		assertEquals(4, preprocessor.getTrace().getEglLineNumberFor(3));
	}
	
	@Test
	public void testTraceabilityAfterMultiLineComment() {
		final String program = "[*"                  + NEWLINE +
		                       " This is a"          + NEWLINE +
		                       " multi-line comment" + NEWLINE +
		                       "*]"                  + NEWLINE +
		                       "[%var x := 5;%]";

		preprocess(program);
//		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		assertEquals(5, preprocessor.getTrace().getEglLineNumberFor(1));
	}
	
	
	@Test
	public void testTraceabilityColumnSimple() {
		final String program = "[%out.print('foo');%]";
		//                      1234567890123456789
		//                      0        1         
		
		preprocess(program);
//		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		assertEquals(3, preprocessor.getTrace().getEglColumnNumberFor(1, 1));
		assertEquals(7, preprocessor.getTrace().getEglColumnNumberFor(1, 5));
	}
	
	@Test
	public void testTraceabilityColumnMultipleTags() {
		final String program = "[%for (i in Sequence{1..10})%] [%var x : Integer := i+2%]    [%out.print(x)%][%}%]";
		//                      123456789012345678901234567890123456789012345678901234567890123456789012345678901234
		//                      0        1         2         3         4         5         6         7         8
		
		preprocess(program);
//		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		assertEquals( 3, preprocessor.getTrace().getEglColumnNumberFor(1, 1));
		assertEquals(34, preprocessor.getTrace().getEglColumnNumberFor(2, 1));
		assertEquals(64, preprocessor.getTrace().getEglColumnNumberFor(3, 1));
		assertEquals(80, preprocessor.getTrace().getEglColumnNumberFor(4, 1));
	}
	
	@Test
	public void testTraceabilityColumnMultipleTagsWithComment() {
		final String program = "[%for (i in Sequence{1..10})%] [%var x : Integer := i+2%]    [* foo *][%out.print(x)%][%}%]";
		//                      1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901
		//                      0        1         2         3         4         5         6         7         8
		
		preprocess(program);
//		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		assertEquals( 3, preprocessor.getTrace().getEglColumnNumberFor(1, 1));
		assertEquals(34, preprocessor.getTrace().getEglColumnNumberFor(2, 1));
		assertEquals(73, preprocessor.getTrace().getEglColumnNumberFor(3, 1));
		assertEquals(89, preprocessor.getTrace().getEglColumnNumberFor(4, 1));
	}
	
	
	@Test
	public void testTraceabilityColumnTabs() {
		final String program = "[%for (i in Sequence{1..10})%]\t\t[%var x : Integer := i+2%] \t[%out.print(x)%][%}%]";
		//                      12345678901234567890123456789011223456789012345678901234567890012345678901234567890123
		//                      0        1         2         3           4         5          6         7         8
		
		preprocess(program);
//		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		assertEquals( 3, preprocessor.getTrace().getEglColumnNumberFor(1, 1));
		assertEquals(35, preprocessor.getTrace().getEglColumnNumberFor(2, 1));
		assertEquals(63, preprocessor.getTrace().getEglColumnNumberFor(3, 1));
		assertEquals(79, preprocessor.getTrace().getEglColumnNumberFor(4, 1));
	}
	
	
	@Test
	public void testTraceabilityColumnOutput() {
		final String program = "[%=1+1%][%=2+2%][%var hello : String := 'hello';%] [%=hello%]";
		//                      1234567890123456789012345678901234567890123456789012345678901
		//                      0        1         2         3         4         5         6
		
		preprocess(program);
//		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		final int offset = "out.printdyn(".length();
		
		assertEquals( 4, preprocessor.getTrace().getEglColumnNumberFor(1, offset + 1));
		assertEquals(12, preprocessor.getTrace().getEglColumnNumberFor(2, offset + 1));
		assertEquals(19, preprocessor.getTrace().getEglColumnNumberFor(3, 1));
		assertEquals(55, preprocessor.getTrace().getEglColumnNumberFor(5, offset + 1));
	}
	
	
	@Test
	public void testTraceabilityColumnMultiline() {
		final String program = "[%for (i in Sequence{1..10})%]" + NEWLINE +
		                       "  [% var x : Integer := i+2%]" + NEWLINE + 
		                       "  [%=x%]" + NEWLINE +
		                       "[%}%]";
		//                      1234567890123456789012345678901
		//                      0        1         2         3
		
		preprocess(program);
		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		assertEquals(1, preprocessor.getTrace().getEglLineNumberFor(1));
		assertEquals(2, preprocessor.getTrace().getEglLineNumberFor(2));
		assertEquals(3, preprocessor.getTrace().getEglLineNumberFor(3));
		assertEquals(3, preprocessor.getTrace().getEglLineNumberFor(4));
		assertEquals(3, preprocessor.getTrace().getEglLineNumberFor(5));
		assertEquals(4, preprocessor.getTrace().getEglLineNumberFor(6));
		
		assertEquals(3,  preprocessor.getTrace().getEglColumnNumberFor(1, 1));
		assertEquals(6,  preprocessor.getTrace().getEglColumnNumberFor(2, 2));
		assertEquals(1,  preprocessor.getTrace().getEglColumnNumberFor(3, 12));
		assertEquals(6,  preprocessor.getTrace().getEglColumnNumberFor(4, "out.printdyn('".length()));
		assertEquals(8, preprocessor.getTrace().getEglColumnNumberFor(5, "out.print('".length()));
		assertEquals(3,  preprocessor.getTrace().getEglColumnNumberFor(6, 1));
	}
	
	
	@Test
	public void testTraceabilityColumnTrailingCloseTag() {
		final String program = "[%for (i in Sequence{1..10})" + NEWLINE + 
		                       "  var y : String;  %] [%var x : Integer := i+2%]    [%out.print(x)%][%}%]";
		//                      12345678901234567890123456789012345678901234567890123456789012345678901234
		//                      0        1         2         3         4         5         6         7      
		
		preprocess(program);
//		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		assertEquals(1, preprocessor.getTrace().getEglLineNumberFor(1));
		assertEquals(2, preprocessor.getTrace().getEglLineNumberFor(2));
		assertEquals(2, preprocessor.getTrace().getEglLineNumberFor(3));
		assertEquals(2, preprocessor.getTrace().getEglLineNumberFor(4));
		assertEquals(2, preprocessor.getTrace().getEglLineNumberFor(5));
		
		assertEquals( 3, preprocessor.getTrace().getEglColumnNumberFor(1, 1));
		assertEquals( 1, preprocessor.getTrace().getEglColumnNumberFor(2, 1));
		assertEquals(25, preprocessor.getTrace().getEglColumnNumberFor(3, 1));
		assertEquals(55, preprocessor.getTrace().getEglColumnNumberFor(4, 1));
		assertEquals(71, preprocessor.getTrace().getEglColumnNumberFor(5, 1));
	}
	
	
	@Test
	public void testTraceabilityComments() {
		final String program = "[% -- This is a comment" + NEWLINE +
				               "for (i in Sequence{1..10})" + NEWLINE + 
		                       "  var y : String;  %] [%var x : Integer := i+2%]    [%out.print(x)%][%}%]";
		//                      12345678901234567890123456789012345678901234567890123456789012345678901234
		//                      0        1         2         3         4         5         6         7      
		
		preprocess(program);
//		System.err.println(preprocess(program));
//		System.err.println(preprocessor.getTrace());
		
		assertEquals(1, preprocessor.getTrace().getEglLineNumberFor(1));
		assertEquals(2, preprocessor.getTrace().getEglLineNumberFor(2));
		assertEquals(3, preprocessor.getTrace().getEglLineNumberFor(3));
		assertEquals(3, preprocessor.getTrace().getEglLineNumberFor(4));
		assertEquals(3, preprocessor.getTrace().getEglLineNumberFor(5));
		assertEquals(3, preprocessor.getTrace().getEglLineNumberFor(6));
		assertEquals(3, preprocessor.getTrace().getEglLineNumberFor(7));
		
		assertEquals( 3, preprocessor.getTrace().getEglColumnNumberFor(1, 1));
		assertEquals( 1, preprocessor.getTrace().getEglColumnNumberFor(2, 1));
		assertEquals( 1, preprocessor.getTrace().getEglColumnNumberFor(3, 1));
		assertEquals(25, preprocessor.getTrace().getEglColumnNumberFor(4, 1));
		assertEquals(55, preprocessor.getTrace().getEglColumnNumberFor(5, 1));
		assertEquals(71, preprocessor.getTrace().getEglColumnNumberFor(6, 1));
	}
	
	@Test
	public void testMarker() {
		final String egl = "[*-Marker*]\n[%var x : String;%]";
		final String eol = "var x : String;";

		assertEquals(eol, preprocess(egl));
	}

	@Test
	public void testDoubleQuotes() {
		final String program = "foo[x=\"bar\"]";
		String preprocessed = preprocess(program);
		System.err.println(preprocessed);
		System.err.println(preprocessor.getTrace());

		assertEquals(1, preprocessor.getTrace().getEglColumnNumberFor(1, "out.printx('".length()));
		assertEquals(1 + program.length(), preprocessor.getTrace().getEglColumnNumberFor(1, preprocessed.length()));
	}

	@Test
	public void testSingleQuotes() {
		final String program = "foo[x='bar']";
		String preprocessed = preprocess(program);
		System.err.println(preprocessed);
		System.err.println(preprocessor.getTrace());

		assertEquals(1, preprocessor.getTrace().getEglColumnNumberFor(1, "out.printx('".length()));
		assertEquals(1 + program.length(), preprocessor.getTrace().getEglColumnNumberFor(1, preprocessed.length()));
	}

}
