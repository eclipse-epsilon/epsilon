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

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.exceptions.EglStoppedException;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.formatter.CompositeFormatter;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.epsilon.egl.merge.output.RegionType;
import org.eclipse.epsilon.egl.merge.partition.CommentBlockPartitioner;
import org.eclipse.epsilon.egl.merge.partition.CompositePartitioner;
import org.eclipse.epsilon.egl.status.Warning;

public class OutputBuffer implements IOutputBuffer {
	
	protected StringBuffer buffer = new StringBuffer();
	protected IEglContext context;

	Pattern newLinePattern;
	
	protected List<CommentBlockPartitioner> customPartitioners = new LinkedList<>();
	protected boolean contentTypeSet = false;
	protected String lastLine = null;
	
	protected boolean hasProtectedRegions = false;
	protected boolean hasControlledRegions = false;
	protected OutdentationFormatter outdentationFormatter = new OutdentationFormatter(this);
	protected Collection<String> indenters = Arrays.asList("\t", "    ");
	protected IOutputBuffer parent = null;
	
	public OutputBuffer(IEglContext context) {
		this(context, null);
	}
	
	public OutputBuffer() {
		this(null, null);
	}
	
	// For unit tests
	OutputBuffer(IEglContext context, String contents) {
		this.context = context;
		if (contents != null) buffer.append(contents);
	}
	
	@Override
	public void chop(int chars) {
		int limit = Math.min(chars, buffer.length());
		for (int i = 0; i < limit; i++) {
			buffer.deleteCharAt(buffer.length()-1);
		}
	}
	
	@Override
	public void print(Object o) {
		buffer.append(Objects.toString(o));
	}
	
	@Override
	public void printdyn(Object o) {
		final String indentation = calculateIndentationToMatch(getLastLineInBuffer());
		final String newLine = getNewline();
		final String[] lines = StringUtil.toString(o).split(newLine);
		
		for (int i = 0; i < lines.length; i++) {
			if (i == 0) {
				// Any text before the first newline character should not be
				// placed on a newline nor indented  
				buffer.append(lines[i]);
			} else {
				// TODO: Update trace links here
				buffer.append(newLine + indentation + lines[i]);
			}
		}
	}

	public String getLastLineInBuffer() {
		final int indexOfLastLine = buffer.lastIndexOf("\n");
		
		if (indexOfLastLine == -1) {
			return buffer.substring(0, buffer.length());
		}
		else {
			return buffer.substring(indexOfLastLine + 1, buffer.length());
		}
	}
	
	public String calculateIndentationToMatch(String previousLine) {
		final StringBuilder builder = new StringBuilder();
		
		for (char c : previousLine.toCharArray()) {
			if (Character.isWhitespace(c)) {
				builder.append(c);
			}
			else {
				builder.append(' ');
			}
		}
		
		return builder.toString();
	}
	
	@Override
	public void println() {
		buffer.append(getNewline());
	}
	
	@Override
	public void setContentType(String name) throws EglRuntimeException {
		if (contentTypeSet) {
			context.addStatusMessage(new Warning("Cannot set content type to '" + name + "' - content type already specified."));
		}
		else {
			if (!context.usePartitionerFor(name)) {
				throw new EglRuntimeException("'" + name + "' is not a recognised content type.", context.getModule());
			}
			CompositePartitioner partitioner = context.getPartitioner();
			for (CommentBlockPartitioner customPartitioner : customPartitioners) {
				partitioner.addPartitioner(customPartitioner);
			}
			
			contentTypeSet = true;
		}
	}
	
	@Override
	public String startPreserve(String id, boolean enabled) throws EglRuntimeException {
		return startLocate(id, enabled, RegionType.Protected);
	}
	
	@Override
	public String startControl(String id, boolean enabled) throws EglRuntimeException {
		return startLocate(id, enabled, RegionType.Controlled);
	}
	
	public String startLocate(String id, boolean enabled, RegionType regionType) throws EglRuntimeException {
		
		assertNoMixedRegions(regionType);
		
		if (lastLine != null)
			throw new EglRuntimeException("The current region must be stopped before another region may begin.", context.getModule());
		
		CompositePartitioner partitioner = context.getPartitioner();
		if (partitioner.getDefaultPartitioner() == null)
			throw new EglRuntimeException("A content type must be specified before using startPreserve(id, enabled).", context.getModule());
		
		lastLine = partitioner.getDefaultPartitioner().getLastLine(id, regionType);
		
		return partitioner.getDefaultPartitioner().getFirstLine(id, enabled, regionType);	
	}
	
	@Override
	public String startControl(String startComment, String endComment,
			String id, boolean enabled) throws EglRuntimeException {
		return startLocate(startComment, endComment, id, enabled, RegionType.Controlled);
	}
	
	@Override
	public String startPreserve(String startComment, String endComment,
			String id, boolean enabled) throws EglRuntimeException {
		return startLocate(startComment, endComment, id, enabled, RegionType.Protected);
	}
	
	public String startLocate(String startComment,
	                            String endComment,
	                            String id,
	                            boolean enabled, RegionType regionType) throws EglRuntimeException {
		
		assertNoMixedRegions(regionType);
		
		if (lastLine != null)
			throw new EglRuntimeException("The current region must be stopped before another region may begin.", context.getModule());
		
		final CommentBlockPartitioner customPartitioner = new CommentBlockPartitioner(startComment, endComment);
		lastLine = customPartitioner.getLastLine(id, regionType);
		
		context.getPartitioner().addPartitioner(customPartitioner);
		customPartitioners.add(customPartitioner);
		
		return customPartitioner.getFirstLine(id, enabled, regionType);	
	}
	
	protected void assertNoMixedRegions(RegionType regionType) throws EglRuntimeException {
		if (regionType == RegionType.Controlled) hasControlledRegions = true;
		else if (regionType == RegionType.Protected) hasProtectedRegions = true;
		
		if (hasControlledRegions && hasProtectedRegions) {
			throw new EglRuntimeException("Templates cannot contain both protected and controlled regions", context.getModule());
		}
	}
	
	@Override
	public String stopControl() throws EglRuntimeException {
		return stopLocate();
	}
	
	@Override
	public String stopPreserve() throws EglRuntimeException {
		return stopLocate();
	}
	
	public String stopLocate() throws EglRuntimeException {
		if (lastLine == null)
			throw new EglRuntimeException("There is no current region to stop.", context.getModule());

		final String result = lastLine;
		lastLine = null;	
		return result;
	}
	
	@Override
	public void stop() throws EglStoppedException {
		throw new EglStoppedException(context.getModule());
	}
	
	@Override
	public int getCurrentLineNumber() {
		if (newLinePattern == null) newLinePattern = Pattern.compile(getNewline());
		final Matcher newLineMatcher = newLinePattern.matcher(buffer.toString());
		int numberOfLines;
		for (numberOfLines = 1; newLineMatcher.find(); ++numberOfLines);
		return numberOfLines;
	}

	@Override
	public int getOffset() {
		if (parent != null) return parent.getOffset() + buffer.length();
		else return buffer.length();
	}
	
	@Override
	public void formatWith(Formatter formatter) {
		CompositeFormatter composite = new CompositeFormatter(outdentationFormatter, formatter);
		replaceContentsWith(composite.format(buffer.toString()));
	}

	protected void replaceContentsWith(String newContents) {
		buffer.setLength(0);
		buffer.append(newContents);
	}
	
	@Override
	public String toString() {
		return buffer.toString();
	}
	
	public OutdentationFormatter getOutdentationFormatter() {
		return outdentationFormatter;
	}
	
	public void setIndenters(Collection<String> indentation) {
		this.indenters = indentation;
	}
	
	public Collection<String> getIndenters() {
		return indenters;
	}
	
	public void setParent(IOutputBuffer parent) {
		this.parent = parent;
	}
	
	public IOutputBuffer getParent() {
		return parent;
	}

	public int getLength() {
		return buffer.length();
	}
}
