/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.merge;

import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;
import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.egl.merge.partition.CommentBlockPartitioner;
import org.eclipse.epsilon.egl.merge.partition.Partitioner;
import org.junit.Test;

public class TestControlledRegionMerging {
	
	private final Partitioner partitioner = new CommentBlockPartitioner("<!--", "-->");
	
	private String generated = "Generated first line" + NEWLINE +
							   "<!-- controlled region anId on begin -->" + NEWLINE +
	                           "This text is generated." + NEWLINE +
	                           "<!-- controlled region anId end -->" + NEWLINE +
	                           "Generated last line";
	
	private String existing = "Preserved first line" + NEWLINE +
			   				  "<!-- controlled region anId on begin -->" + NEWLINE +
                              "This text is modified." + NEWLINE +
                              "<!-- controlled region anId end -->" + NEWLINE +
                              "Preserved last line";
	
	private String expected = "Preserved first line" + NEWLINE +
				  			  "<!-- controlled region anId on begin -->" + NEWLINE +
				              "This text is generated." + NEWLINE +
				              "<!-- controlled region anId end -->" + NEWLINE +
				              "Preserved last line";
	
	@Test
	public void testMerge() {
		final Merger merger = new Merger(partitioner, generated, existing);
		assertEquals(expected, merger.merge());
		assertEquals(0, merger.getMergeWarnings().size());
	}
	
}
