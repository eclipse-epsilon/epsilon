/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.merge.partition;

import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;
import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.epsilon.egl.merge.output.Output;
import org.eclipse.epsilon.egl.merge.output.LocatedRegion;
import org.eclipse.epsilon.egl.merge.output.Region;
import org.junit.Test;

public class TestCompositePartitioner {

	private final Output output;
	private final Output emptyOutput = new Output();
	
	private final CommentBlockPartitioner htmlPartitioner = new CommentBlockPartitioner("<!--", "-->");
	private final CommentBlockPartitioner javaPartitioner = new CommentBlockPartitioner("//",   null);
	
	private final CompositePartitioner partitioner = new CompositePartitioner(htmlPartitioner, javaPartitioner);
	
	public TestCompositePartitioner() {		
		final List<Region> regions = new LinkedList<>();
		
		final LocatedRegion secondPR = javaPartitioner.new CommentedProtectedRegion("second", 0, false, "");
		secondPR.setContents("Second protected region" + NEWLINE);
		
		regions.add(new Region("Preamble" + NEWLINE));
		regions.add(htmlPartitioner.new CommentedProtectedRegion("first", 0, true, "First protected region" + NEWLINE));
		regions.add(new Region(NEWLINE + "Content" + NEWLINE));
		regions.add(secondPR);
		regions.add(new Region(NEWLINE + "Postamble"));
		
		output = new Output(regions);
	}

	@Test
	public void testOutputToString() {
		final String expected = "Preamble"                                 + NEWLINE + 
		                        "<!-- protected region first on begin -->" + NEWLINE +
		                        "First protected region"                   + NEWLINE +
		                        "<!-- protected region first end -->"      + NEWLINE +
		                        "Content"                                  + NEWLINE +
		                        "// protected region second off begin"     + NEWLINE +
		                        "// protected region second end"           + NEWLINE +
		                        "Postamble";
		
		assertEquals(expected, output.toString());
	}

	@Test
	public void testEmptyOutputToString() {
		assertEquals("", emptyOutput.toString());
	}
	

	@Test
	public void testPartitionSingleRegion() {
		final String text     = "Simple" + NEWLINE + "Input";
		final Output expected = new Output(new Region(text));
		
		assertEquals(expected, partitioner.partition(text));
	}
	
	@Test
	public void testPartitionJavaThenHTMLProtectedRegions() {
		final String javaRegionContents = "This java code is protected" + NEWLINE;
		final String htmlRegionContents = "This html code is protected" + NEWLINE;
		final String text               = "// protected region java on begin" + NEWLINE +
		                                  javaRegionContents +
		                                  "// protected region java end" +
		                                  "<!-- protected region html on begin -->" + NEWLINE +
		                                  htmlRegionContents +
		                                  "<!-- protected region html end -->";
		
		final Output expected = new Output(javaPartitioner.new CommentedProtectedRegion("java", 0, true, javaRegionContents),
		                                   htmlPartitioner.new CommentedProtectedRegion("html", text.indexOf("<!-- protected region html"), true, htmlRegionContents));
		
		assertEquals(expected, partitioner.partition(text));
	}

	@Test
	public void testPartitionHTMLThenJavaProtectedRegions() {
		final String javaRegionContents = "This java code is protected" + NEWLINE;
		final String htmlRegionContents = "This html code is protected" + NEWLINE;
		final String text               = "<!-- protected region html on begin -->" + NEWLINE +
                                          htmlRegionContents +
                                          "<!-- protected region html end -->" +
                                          "// protected region java on begin" + NEWLINE +
		                                  javaRegionContents +
		                                  "// protected region java end";
		                                  
		
		final Output expected = new Output(htmlPartitioner.new CommentedProtectedRegion("html", 0, true, htmlRegionContents),
		                                   javaPartitioner.new CommentedProtectedRegion("java", text.indexOf("// protected region java"), true, javaRegionContents));

		assertEquals(expected, partitioner.partition(text));
	}
	
	@Test
	public void testPartitionComplex() {
		final String preamble           = "Preamble"  + NEWLINE;
		final String content1           = "Content 1" + NEWLINE;
		final String content2           = "Content 2" + NEWLINE;
		final String content3           = "Content 3" + NEWLINE;
		final String postamble          = "Postabmle" + NEWLINE;
		
		final String java1RegionContents = "This java code is protected"      + NEWLINE;
		final String html1RegionContents = "This html code is protected"      + NEWLINE;
		final String html2RegionContents = "This html code is also protected" + NEWLINE;
		
		final String text               = preamble +
		                                  "<!-- protected region html1 on begin -->" + NEWLINE +
                                          html1RegionContents +
                                          "<!-- protected region html1 end -->" +
                                          content1 +
                                          "// protected region java1 on begin" + NEWLINE +
                                          java1RegionContents +
		                                  "// protected region java1 end" +
		                                  content2 +
		                                  "<!-- protected region html2 on begin -->" + NEWLINE +
                                          html2RegionContents +
                                          "<!-- protected region html2 end -->" +
		                                  content3 +
		                                  "// protected region java2 off begin" + NEWLINE +
		                                  "// protected region java2 end" +
		                                  postamble;
		                                  
		
		final Output expected = new Output(new Region(preamble),
		                                   htmlPartitioner.new CommentedProtectedRegion("html1", preamble.length(), true, html1RegionContents),
		                                   new Region(content1),
		                                   javaPartitioner.new CommentedProtectedRegion("java1", text.indexOf("// protected region java1"), true, java1RegionContents),
		                                   new Region(content2),
		                                   htmlPartitioner.new CommentedProtectedRegion("html2", text.indexOf("<!-- protected region html2"), true, html2RegionContents),
		                                   new Region(content3),
		                                   javaPartitioner.new CommentedProtectedRegion("java2", text.indexOf("// protected region java2"), false, "any text"),
		                                   new Region(postamble));
		
		assertEquals(expected, partitioner.partition(text));
	}
	
	@Test
	public void testNoPartitioners() {
		final String javaRegionContents = "This java code is protected" + NEWLINE;
		final String htmlRegionContents = "This html code is protected" + NEWLINE;
		final String text               = "<!-- protected region test on begin -->" + NEWLINE +
                                          htmlRegionContents +
                                          "<!-- protected region test end -->" +
                                          "// protected region test on begin" + NEWLINE +
		                                  javaRegionContents +
		                                  "// protected region test end";
		                                  
		
		final CompositePartitioner partitioner = new CompositePartitioner();
		
		final Output expected = new Output(new Region(text));
		
		assertEquals(expected, partitioner.partition(text));
	}
}
