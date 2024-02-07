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

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.epsilon.egl.merge.output.Output;
import org.eclipse.epsilon.egl.merge.output.LocatedRegion;
import org.eclipse.epsilon.egl.merge.output.Region;
import org.eclipse.epsilon.egl.merge.output.RegionType;
import org.eclipse.epsilon.egl.util.FileUtil;

public class CommentBlockPartitioner implements Partitioner {
	
	private final String startComment, endComment;
	private Pattern regionPattern;
	
	private static String escape(String text) {
		return text.replaceAll("\\*", "\\\\*");
	}
	
	public CommentBlockPartitioner(String startComment, String endComment) {
		this.startComment = startComment == null ? "" : startComment;
		this.endComment   = endComment   == null ? "" : endComment;
		init();
	}
	
	private void init() {
		initPattern();
	}
	
	private void initPattern() {
		final StringBuilder regex = new StringBuilder();
		
		if (startComment.length() > 0) {
			// The start comment delimiter
			regex.append(escape(startComment));
			
			// whitespace
			regex.append("[\\s]*");
		}
		
		// The protected region literal
		regex.append("(controlled|protected) region ");
		
		// The region's id, matched reluctantly and terminated with a space
		regex.append("(.*?) ");
		
		// end or (on or off followed by begin)
		regex.append("(end|(on|off) begin)");
		
		if (endComment.length() > 0) {
			// whitespace
			regex.append("[\\s]*");
	
			// The end comment delimiter
			regex.append(escape(endComment));
		}
		
		regionPattern = Pattern.compile(regex.toString());
	}
	
	public String getStartComment() {
		return startComment;
	}
	
	public String getEndComment() {
		return endComment;
	}
	
	public String getFirstLine(String id, boolean enabled, RegionType regionType) {
		final StringBuilder builder = new StringBuilder();
		
		// Build starting comment
		if (startComment.length() > 0) {
			builder.append(startComment).append(' ');
		}
		
		builder.append(regionTypeToString(regionType) + " region ")
			.append(id)
			.append(" ")
			.append(enabled ? "on" : "off")
			.append(" begin");
		
		if (endComment.length() > 0) {
			builder.append(' ').append(endComment);
		}
		
		return builder.toString();
	}

	protected String regionTypeToString(RegionType regionType) {
		if (regionType == RegionType.Controlled) return "controlled";
		else return "protected";
	}
	
	protected RegionType regionTypeFromString(String regionType) {
		if ("controlled".equals(regionType)) return RegionType.Controlled;
		else return RegionType.Protected;
	}
	
	public String getLastLine(String id, RegionType regionType) {
		final StringBuilder builder = new StringBuilder();
		
		// Build ending comment
		if (startComment.length() > 0) {
			builder.append(startComment).append(' ');
		}
		
		builder.append(regionTypeToString(regionType) + " region ")
			.append(id)
			.append(" end");
		
		if (endComment.length() > 0) {
			builder.append(' ').append(endComment);
		}
		
		return builder.toString();
	}
	
	public Output partition(String text) {
		return partition(text, 0);
	}
	
	public Output partition(String text, int offset) {
		final List<Region> regions = new LinkedList<>();

		// We will read the text line by line, as this is the most performent way to process the text. 
		final Scanner scanner = new Scanner(text);
		final StringBuilder buffer = new StringBuilder();
		
		// Keep track of variables that will change as we read the text
		boolean isRegionEnabled = false;
		String regionId = null;
		RegionType regionType = null;
		int regionOffset = 0;
		
		// We need to keep a track of the offset within the text
		int textOffset = 0;
		
		// Loop over each line
		while (scanner.hasNextLine()) {
			// Get the line (will not include line terminators)
			String line = scanner.nextLine();
			int currentLineOffset = 0;
			
			// Add the line terminators back in if appropriate
			if (scanner.hasNextLine() || text.endsWith(System.lineSeparator())) {
				line += System.lineSeparator();
			}
			
			// Match the line against the region pattern
			Matcher matcher = regionPattern.matcher(line);
			
			boolean didMatchPreviously = false;
			while(true) {
				boolean doesMatch = matcher.find();
				
				if (doesMatch) {
					didMatchPreviously = true;
					
					// If there is any content before the region marker, add it to the buffer
					if (currentLineOffset < matcher.start()) {
						buffer.append(line.substring(currentLineOffset, matcher.start()));
					}
					
					// Keep a track of where we are in the line
					currentLineOffset = matcher.start();
					
					// This is a region marker
					if (matcher.group(3).equals("end")) {
						// This is the end of a region
						
						// Pull the contents from the buffer and reset it
						final String regionContents = buffer.toString();
						buffer.setLength(0);
						
						// Create the protected region
						final LocatedRegion region = new CommentedProtectedRegion(regionId, regionOffset, isRegionEnabled, regionContents);
						region.setType(regionType);
						regions.add(region);
						
						// Keep a track of where we are in the line
						currentLineOffset = matcher.end();
					} else {
						// This is the start of a region
						isRegionEnabled = matcher.group(4) != null && matcher.group(4).equals("on");
						regionType = regionTypeFromString(matcher.group(1));
						regionId = matcher.group(2);
						regionOffset = offset + textOffset + currentLineOffset;
						
						// If the buffer contains any content, create a region for it and reset it
						if (buffer.length() > 0) {
							regions.add(new Region(buffer.toString()));
							buffer.setLength(0);
						}
						
						// Keep a track of where we are in the line
						// There is always a new line at the end of a starting region
						currentLineOffset = matcher.end() + System.lineSeparator().length();
					}
				} else {
					if (didMatchPreviously) {
						// If there was a match on this line and there is additional content after the match, add it to the buffer
						if (currentLineOffset < line.length()) {
							buffer.append(line.substring(currentLineOffset));							
						}
					} else {
						// Nothing on this line was matched, add the whole line to the buffer
						buffer.append(line);
					}
					// Break out of the while loop as there are no more matches in this line
					break;
				}
			}
			
			// Increase the offset number
			textOffset += line.length();
		}
		
		// Close the scanner to release resources
		scanner.close();
		
		// If the buffer still has length, we need to add it as another region
		if (buffer.length() > 0) {
			regions.add(new Region(buffer.toString()));
			buffer.setLength(0);
		}
		
		return new Output(regions);
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + 
		       "{start=\"" + startComment + "\", " + 
		       "end=\"" + endComment + "\"}";
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CommentBlockPartitioner))
			return false;
		
		final CommentBlockPartitioner that = (CommentBlockPartitioner)o;
		
		return Objects.equals(this.startComment, that.startComment) &&
		       Objects.equals(this.endComment, that.endComment);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(startComment, endComment);
	}
	
	
	class CommentedProtectedRegion extends LocatedRegion {

		CommentedProtectedRegion(String id, int offset, boolean enabled, String contents) {
			super(id, offset, enabled, contents);
		}

		@Override
		public String toString() {
			String result = getFirstLine(getId(), isEnabled(), type) + FileUtil.NEWLINE;
			result += isEnabled() ? getContents() : getDefaultValue();
			result += getLastLine(getId(), type);
			return result;
		}
	}
	
}
