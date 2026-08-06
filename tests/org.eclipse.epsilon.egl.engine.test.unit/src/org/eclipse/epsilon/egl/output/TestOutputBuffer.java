/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.output;

import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;
import static org.junit.Assert.assertEquals;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.config.XMLContentTypeRepository;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.execute.context.EglContext;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.epsilon.egl.merge.output.RegionType;
import org.eclipse.epsilon.egl.merge.partition.CommentBlockPartitioner;
import org.eclipse.epsilon.egl.merge.partition.CompositePartitioner;
import org.eclipse.epsilon.egl.test.MockContext;
import org.eclipse.epsilon.egl.util.FileUtil;
import org.junit.Before;
import org.junit.Test;

public class TestOutputBuffer {

	private final CommentBlockPartitioner partitioner       = new CommentBlockPartitioner("<!--", "-->");
	private final CompositePartitioner expectedPartitioners = new CompositePartitioner(partitioner);
	
	private OutputBuffer buffer;
	private IEglContext   context;
	
	@Before
	public void setUp() {
		context = new MockContext();
		buffer  = new OutputBuffer(context);
	}
	
	@Test
	public void testPrint() {
		final String expected = "hello";
		buffer.print(expected);
		
		assertEquals(expected, buffer.toString());
	}
	
	@Test
	public void testPrintNull() {
		final String expected = "null";
		buffer.print(null);
		
		assertEquals(expected, buffer.toString());
	}
	
	@Test
	public void testPrintLine() {
		final String expected = FileUtil.NEWLINE;
		buffer.println();
		
		assertEquals(expected, buffer.toString());		
	}
	
	@Test
	public void testPrintAndPrintLine() {
		final String expected = "test data" + FileUtil.NEWLINE + "second line";
		
		buffer.println("test data");
		buffer.print("second line");
		
		assertEquals(expected, buffer.toString());		
	}
	
	@Test
	public void testChop() {
		final OutputBuffer buffer = new OutputBuffer(context, "hello");
		
		final String expected = "he";
		buffer.chop(3);
		
		assertEquals(expected, buffer.toString());
	}
	
	@Test
	public void testChopAll() {
		final OutputBuffer buffer = new OutputBuffer(context, "hello");
		
		final String expected = "";
		buffer.chop(5);
		
		assertEquals(expected, buffer.toString());
	}
	
	@Test
	public void testChopTooMany() {
		final OutputBuffer buffer = new OutputBuffer(context, "hello");
		
		final String expected = "";
		buffer.chop(6);
		
		assertEquals(expected, buffer.toString());
	}
	

	@Test
	public void testStartPreserve() throws EglRuntimeException {
		final String expected = partitioner.getFirstLine("id", true, RegionType.Protected);
		
		assertEquals(expected, buffer.startPreserve(partitioner.getStartComment(), partitioner.getEndComment(), "id", true));
		assertEquals(expectedPartitioners, context.getPartitioner());
	}
	
	@Test (expected=EglRuntimeException.class)
	public void testStartPreserveDouble() throws EglRuntimeException {
		buffer.startPreserve(partitioner.getStartComment(), partitioner.getEndComment(), "id", true);
		buffer.startPreserve(partitioner.getStartComment(), partitioner.getEndComment(), "id2", true);
	}
	
	@Test
	public void testStopPreserve() throws EglRuntimeException {
		final String expected = partitioner.getFirstLine("id", true, RegionType.Protected) + partitioner.getLastLine("id", RegionType.Protected);
		
		assertEquals(expected, buffer.startPreserve(partitioner.getStartComment(), partitioner.getEndComment(), "id", true) +
		                       buffer.stopPreserve());
		assertEquals(expectedPartitioners, context.getPartitioner());
	}
	
	@Test (expected=EglRuntimeException.class)
	public void testStopPreserveWithNoStartPreserve() throws EglRuntimeException {
		buffer.stopPreserve();
	}
	
	@Test
	public void testPreserve() throws EglRuntimeException {
		final String contents = "This text will be preserved";
		final String expected = partitioner.getFirstLine("id", true, RegionType.Protected) + NEWLINE +
		                        contents + NEWLINE +
		                        partitioner.getLastLine("id", RegionType.Protected);
		
		assertEquals(expected, buffer.preserve(partitioner.getStartComment(), partitioner.getEndComment(), "id", true, contents));
		assertEquals(expectedPartitioners, context.getPartitioner());
	}
	
	@Test (expected=EglRuntimeException.class)
	public void testStartPreservePreserve() throws EglRuntimeException {
		final String contents = "This text will be preserved";
		
		buffer.startPreserve(partitioner.getStartComment(), partitioner.getEndComment(), "id", true);
		buffer.preserve(partitioner.getStartComment(), partitioner.getEndComment(), "id", true, contents);
	}
	
	@Test (expected=EglRuntimeException.class)
	public void testPreserveStopPreserve() throws EglRuntimeException {
		final String contents = "This text will be preserved";

		buffer.preserve(partitioner.getStartComment(), partitioner.getEndComment(), "id", true, contents);
		buffer.stopPreserve();
	}
	
	@Test
	public void testSetContentType() throws EglRuntimeException {
		final CompositePartitioner    expectedPartitioners = getJavaPartitioner();
		final CommentBlockPartitioner partitioner          = expectedPartitioners.getDefaultPartitioner();
		
		final String contents = "This text will be preserved";
		final String expected = partitioner.getFirstLine("id", true, RegionType.Protected) + NEWLINE +
                                contents + NEWLINE +
                                partitioner.getLastLine("id", RegionType.Protected);
		
		buffer.setContentType("Java");
		
		assertEquals(expected, buffer.preserve("id", true, contents));
		assertEquals(expectedPartitioners, context.getPartitioner());
	}
	
	@Test
	public void testSetContentTypeTwice() throws EglRuntimeException {
		final CompositePartitioner    expectedPartitioners = getJavaPartitioner();
		final CommentBlockPartitioner partitioner          = expectedPartitioners.getDefaultPartitioner();
		
		final String contents = "This text will be preserved";
		final String expected = partitioner.getFirstLine("id", true, RegionType.Protected) + NEWLINE +
                                contents + NEWLINE +
                                partitioner.getLastLine("id", RegionType.Protected);
		
		buffer.setContentType("Java");
		assertEquals(0, context.getStatusMessages().size());
		
		buffer.setContentType("HTML");
		assertEquals(1, context.getStatusMessages().size());
		
		assertEquals(expected, buffer.preserve("id", true, contents));
		assertEquals(expectedPartitioners, context.getPartitioner());
	}
	
	@Test
	public void testSetContentAndPreserveCustom() throws EglRuntimeException {
		final CompositePartitioner    expectedPartitioners = getJavaPartitioner();
		final CommentBlockPartitioner javaPartitioner      = expectedPartitioners.getDefaultPartitioner();
		
		expectedPartitioners.addPartitioner(partitioner);
		
		final String htmlContents = "This html will be preserved";
		final String javaContents = "This java will be preserved";
		final String expected = partitioner.getFirstLine("html", true, RegionType.Protected)      + NEWLINE + htmlContents + NEWLINE + partitioner.getLastLine("html", RegionType.Protected) +
		                        javaPartitioner.getFirstLine("java", false, RegionType.Protected) + NEWLINE + javaContents + NEWLINE + javaPartitioner.getLastLine("java", RegionType.Protected);
		
		buffer.setContentType("Java");
		assertEquals(0, context.getStatusMessages().size());
		
		assertEquals(expected, buffer.preserve(partitioner.getStartComment(), partitioner.getEndComment(), "html", true, htmlContents) +
		                       buffer.preserve("java", false, javaContents));
		assertEquals(expectedPartitioners, context.getPartitioner());
	}
	
	@Test
	public void testPreserveCustomAndSetContent() throws EglRuntimeException {
		final CompositePartitioner    expectedPartitioners = getJavaPartitioner();
		final CommentBlockPartitioner javaPartitioner      = expectedPartitioners.getDefaultPartitioner();
		
		expectedPartitioners.addPartitioner(partitioner);
		
		final String htmlContents = "This html will be preserved";
		final String javaContents = "This java will be preserved";
		final String expected = partitioner.getFirstLine("html", true, RegionType.Protected)      + NEWLINE + htmlContents + NEWLINE + partitioner.getLastLine("html", RegionType.Protected) +
		                        javaPartitioner.getFirstLine("java", false, RegionType.Protected) + NEWLINE + javaContents + NEWLINE + javaPartitioner.getLastLine("java", RegionType.Protected);
		
		
		buffer.print(buffer.preserve(partitioner.getStartComment(), partitioner.getEndComment(), "html", true, htmlContents));
		buffer.setContentType("Java");
		buffer.print(buffer.preserve("java", false, javaContents));
		
		assertEquals(expected, buffer.toString());
		assertEquals(expectedPartitioners, context.getPartitioner());
	}
	
	@Test
	public void formattingReplacesContents() {
		buffer.print("foo");
		buffer.formatWith(new Formatter() {
			
			@Override
			public String format(String text) {
				return "bar";
			}
		});
		
		assertEquals("bar", buffer.toString());
	}

	private static CompositePartitioner getJavaPartitioner() {
		return new XMLContentTypeRepository(new EglContext(new EglTemplateFactory())).partitionerFor("Java");
	}
	
	@Test (expected=EglRuntimeException.class)
	public void testUnknownContent() throws EglRuntimeException {
		buffer.setContentType("UnknownContentType");
	}
	
	@Test (expected=EglRuntimeException.class)
	public void testUnspecifiedContent() throws EglRuntimeException {
		buffer.preserve("java", false, "This java will be protected");
	}
}

