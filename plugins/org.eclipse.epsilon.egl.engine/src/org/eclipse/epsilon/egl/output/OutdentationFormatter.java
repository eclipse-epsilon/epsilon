/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.eclipse.epsilon.egl.formatter.Formatter;

/**
 * Formats the text by outdenting -%] sections
 */
public class OutdentationFormatter implements Formatter {

	protected int outdentation = 0;
	protected Map<Integer, Integer> outdentations = new LinkedHashMap<>();
	protected OutputBuffer outputBuffer;

	public OutdentationFormatter(OutputBuffer outputBuffer) {
		this.outputBuffer = outputBuffer;
	}

	@Override
	public String format(String text) {
		
		// If -%] has not been used in the template
		// just return the original text
		if (outdentations.isEmpty()) return text;

		// The buffer to collect the outdented text
		StringBuffer buffer = new StringBuffer();

		int startOffset = 0;
		
		// If there's no indentation for the end of the text
		// set it to zero
		if (outdentations.get(text.length()) == null) {
			outdentations.put(text.length(), 0);
		}
		
		// Sort the offsets in case they were inserted out of order
		List<Integer> offsets = new ArrayList<>(outdentations.keySet());
		Collections.sort(offsets);

		// Break the text down to chunks by offset and 
		// outdent each chunk according to its outdentation size
		for (int offset : offsets) {
			
			// The chunk to outdent
			String toOutdent = text.substring(startOffset, offset);
			// The outdentation for that chunk
			int outdentation = outdentations.containsKey(startOffset) ? outdentations.get(startOffset) : 0;
			
			// Break the chunk down to lines
			Scanner scanner = new Scanner(toOutdent);
			while (scanner.hasNextLine()) {
				// Outdent each line
				buffer.append(outdent(scanner.nextLine(), outdentation));
				
				// Append a new line character where appropriate
				if (scanner.hasNextLine() || toOutdent.endsWith(outputBuffer.getNewline())) {
					buffer.append(outputBuffer.getNewline());
				}
			}
			scanner.close();
			
			// Move to the next chunk
			startOffset = offset;
		}

		return buffer.toString();
	}

	protected String outdent(String line, int outdentation) {
		
		for (int i = 0; i < outdentation; i++) {
			
			final String l = line;
			String indenter = outputBuffer.getIndenters().stream().
					filter(in -> l.startsWith(in)).findAny().orElse(null);
			
			if (indenter != null) {
				line = line.substring(indenter.length(), line.length());
			}
		}
		return line;
	}

	public void outdent(int offset) {
		outdentation++;
		outdentations.put(offset, outdentation);
	}

	public void indent(int offset) {
		outdentation--;
		outdentations.put(offset, outdentation);
	}

}
