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

import static org.junit.Assert.*;
import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.epsilon.egl.merge.output.Output;
import org.eclipse.epsilon.egl.merge.output.LocatedRegion;
import org.eclipse.epsilon.egl.merge.output.Region;

public abstract class TestCommentBlockPartitioner {
	
	private final Output output;
	private final Output emptyOutput = new Output();
	
	private final CommentBlockPartitioner partitioner;
	private final String startComment;
	private final String endComment;
	
	protected TestCommentBlockPartitioner(CommentBlockPartitioner partitioner) {
		this.partitioner  = partitioner;
		this.startComment = partitioner.getStartComment().length() == 0 ? "" : partitioner.getStartComment() + " ";
		this.endComment   = partitioner.getEndComment().length()   == 0 ? "" : " " + partitioner.getEndComment();
		
		
		final List<Region> regions = new LinkedList<>();
		
		final LocatedRegion secondPR = partitioner.new CommentedProtectedRegion("second", 0, false, "");
		secondPR.setContents("Second protected region" + NEWLINE);
		
		regions.add(new Region("Preamble" + NEWLINE));
		regions.add(partitioner.new CommentedProtectedRegion("first", 0, true, "First protected region" + NEWLINE));
		regions.add(new Region(NEWLINE + "Content" + NEWLINE));
		regions.add(secondPR);
		regions.add(new Region(NEWLINE + "Postamble"));
		
		output = new Output(regions);
	}

	public void testOutputToString() {
		final String expected = "Preamble"                                                      + NEWLINE + 
		                        startComment + "protected region first on begin"   + endComment + NEWLINE +
		                        "First protected region"                                        + NEWLINE +
		                        startComment + "protected region first end"        + endComment + NEWLINE +
		                        "Content"                                                       + NEWLINE +
		                        startComment + "protected region second off begin" + endComment + NEWLINE +
		                        startComment + "protected region second end"       + endComment + NEWLINE +
		                        "Postamble";
		
		assertEquals(expected, output.toString());
	}

	public void testEmptyOutputToString() {
		assertEquals("", emptyOutput.toString());
	}

	public void testPartitionSingleRegion() {
		final String text     = "Simple" + NEWLINE + "Input";
		final Output expected = new Output(new Region(text));
		
		assertEquals(expected, partitioner.partition(text));
	}
	
	public void testPartitionSingleProtectedRegion() {
		final String regionContents = "This text is protected" + NEWLINE;
		final String text           = startComment + "protected region test on begin" + endComment + NEWLINE +
		                              regionContents +
		                              startComment + "protected region test end" + endComment;
		
		final Output expected = new Output(partitioner.new CommentedProtectedRegion("test", 0, true, regionContents));
		
		assertEquals(expected, partitioner.partition(text));
	}
	
	public void testPartitionSingleProtectedRegionWithComplexId() {
		final String regionContents = "This text is protected" + NEWLINE;
		final String id             = "'this_is a *more* complicated" + endComment + "id' on";
		final String text           = startComment + "protected region " + id + " on begin" + endComment + NEWLINE + regionContents + startComment + "protected region " + id + " end"
				+ endComment;
		
		final Output expected = new Output(partitioner.new CommentedProtectedRegion(id, 0, true, regionContents));
		
		assertEquals(expected, partitioner.partition(text));
	}

	public void testPartitionSingleProtectedRegionOff() {
		final String regionContents = "This text is protected" + NEWLINE;
		final String text           = startComment + "protected region test off begin" + endComment + NEWLINE +
		                              regionContents +
		                              startComment + "protected region test end" + endComment;
		
		final Output expected = new Output(partitioner.new CommentedProtectedRegion("test", 0, false, regionContents));
		final Output actual = partitioner.partition(text);
		
		assertEquals(expected, actual);
	}

	public void testPartitionSingleProtectedRegionEmpty() {
		final String text = startComment + "protected region test on begin" + endComment + NEWLINE +
		                    startComment + "protected region test end"      + endComment;
		
		final Output expected = new Output(partitioner.new CommentedProtectedRegion("test", 0, true, ""));
		
		assertEquals(expected, partitioner.partition(text));
	}
	
	public void testPartitionSingleProtectedRegionWithEnd() {
		final String regionContents = "This text is protected" + NEWLINE;
		final String text           = startComment + "protected region test endpoint off begin" + endComment + NEWLINE +
		                              regionContents +
		                              startComment + "protected region test endpoint end" + endComment;
		
		final Output expected = new Output(partitioner.new CommentedProtectedRegion("test endpoint", 0, false, regionContents));
		final Output actual = partitioner.partition(text);
		
		assertEquals(expected, actual);
	}

	public void testPartitionSingleProtectedRegionWhiteSpace() {
		final String start = (startComment.length() > 0 ? startComment + "\t" : "");
		final String end   = (endComment.length()   > 0 ? "    " + endComment : "");  
		
		final String regionContents = "\tThis text is protected   " + NEWLINE +"\t";
		final String text           = start + "protected region test on begin" + end + NEWLINE +
		                              regionContents +
		                              start + "protected region test end"      + end;
		
		final Output expected = new Output(partitioner.new CommentedProtectedRegion("test", 0, true, regionContents));
		
		assertEquals(expected, partitioner.partition(text));
	}

	public void testPartitionRegionThenProtectedRegion() {
		final String unprotected    = "Preamble" + NEWLINE;
		final String regionContents = "This text is protected" + NEWLINE;
		final String text           = unprotected + 
		                              startComment + "protected region test on begin" + endComment + NEWLINE +
		                              regionContents +
		                              startComment + "protected region test end" + endComment;
		
		final Output expected = new Output(new Region(unprotected), partitioner.new CommentedProtectedRegion("test", unprotected.length(), true, regionContents));
		
		assertEquals(expected, partitioner.partition(text));
	}

	public void testPartitionProtectedRegionThenRegion() {
		final String unprotected    = "Postamble" + NEWLINE;
		final String regionContents = "This text is protected" + NEWLINE;
		final String text           = startComment + "protected region test on begin" + endComment + NEWLINE +
		                              regionContents +
		                              startComment + "protected region test end"      + endComment +
		                              unprotected;
		
		final Output expected = new Output(partitioner.new CommentedProtectedRegion("test", 0, true, regionContents), new Region(unprotected));
		final Output actual = partitioner.partition(text);
		
		assertEquals(expected, actual);
	}

	public void testPartitionTwoProtectedRegions() {
		final String firstRegionContents  = "This text is protected" + NEWLINE;
		final String secondRegionContents = "Also, this text is protected" + NEWLINE;
		final String text                 = startComment + "protected region test on begin"  + endComment + NEWLINE +
		                                    firstRegionContents +
		                                    startComment + "protected region test end"       + endComment +
		                                    startComment + "protected region test2 on begin" + endComment + NEWLINE +
		                                    secondRegionContents +
		                                    startComment + "protected region test2 end"      + endComment;
		                              
		
		final Output expected = new Output(partitioner.new CommentedProtectedRegion("test",  0, true, firstRegionContents),
		                                   partitioner.new CommentedProtectedRegion("test2", text.indexOf(startComment + "protected region test2"), true, secondRegionContents));
		final Output actual = partitioner.partition(text);
		
		assertEquals(expected, actual);
	}

	public void testPartitionComplex() {
		final String unprotectedPre       = "Preamble"  + NEWLINE;
		final String unprotectedMid       = NEWLINE + "Contents"  + NEWLINE;
		final String unprotectedPost      = NEWLINE + "Postamble" + NEWLINE;
		final String firstRegionContents  = "This text is protected" + NEWLINE;
		final String secondRegionContents = "Also, this text is protected" + NEWLINE;
		final String text                 = unprotectedPre +
		                                    startComment + "protected region test on begin"  + endComment + NEWLINE +
		                                    firstRegionContents +
		                                    startComment + "protected region test end"       + endComment +
		                                    unprotectedMid +
		                                    startComment + "protected region test2 on begin" + endComment + NEWLINE +
		                                    secondRegionContents +
		                                    startComment + "protected region test2 end"      + endComment +
		                                    unprotectedPost;
		
		final Output expected = new Output(new Region(unprotectedPre),
		                                   partitioner.new CommentedProtectedRegion("test",  unprotectedPre.length(), true, firstRegionContents),
		                                   new Region(unprotectedMid),
		                                   partitioner.new CommentedProtectedRegion("test2", text.indexOf(startComment + "protected region test2"), true, secondRegionContents),
		                                   new Region(unprotectedPost));
		
		
		
		assertEquals(expected, partitioner.partition(text));
	}
}
