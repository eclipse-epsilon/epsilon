/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.patch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @since 1.6
 */
public class Patch extends TextBlock {
	
	public Patch() {}
	
	public Patch(String... lines) {
		LineFactory lineFactory = new LineFactory();
		for (int i=0; i<lines.length; i++) {
			if (!lines[i].trim().isEmpty())
			this.lines.add(lineFactory.createLine(lines[i], i+1));
		}
	}
	
	public List<PatchValidationDiagnostic> validate() {
		List<PatchValidationDiagnostic> diagnostics = new ArrayList<>();
		
		for (Line line : lines) {
			if (line.is(LineType.KEEP_WILDCARD)) {
				if (isFirstLine(line)) {
					diagnostics.add(new PatchValidationDiagnostic(line, "Wildcards are not allowed at the beginning of a patch"));
				}
				else if (isLastLine(line)) {
					diagnostics.add(new PatchValidationDiagnostic(line, "Wildcards are not allowed at the end of a patch"));					
				}
				
				if (!isFirstLine(line) && getPreviousLine(line).is(LineType.KEEP_WILDCARD)) {
					diagnostics.add(new PatchValidationDiagnostic(line, "Consecutive wildcards are not allowed"));					
				}
				if (!isLastLine(line) && getNextLine(line).is(LineType.KEEP_WILDCARD)) {
					diagnostics.add(new PatchValidationDiagnostic(line, "Consecutive wildcards are not allowed"));
				}
			}
		}
		
		return diagnostics;
	}
	
	public boolean isValid() {
		return validate().isEmpty();
	}
	
	public List<Match> match(TextBlock block) {
				
		Line startMatchBlockLine = null;
		List<Match> matches = new ArrayList<>();
		Patch keepsAndRemoves = this.keepsAndRemoves();
		
		if (keepsAndRemoves.getLines().isEmpty()) return matches;
		
		LineMap lineMap = new LineMap();
		Line blockLine = block.getFirstLine();
		Line patchLine = keepsAndRemoves.getFirstLine();
		
		while (blockLine != null) {
			
			if (patchLine.is(LineType.KEEP_WILDCARD) || patchLine.is(LineType.REMOVE_WILDCARD)) {
				Line matchLine = keepsAndRemoves.getNextLine(patchLine);
				
				if (matchLine == null) {
					if (startMatchBlockLine != null) {
						while (blockLine != null) {
							lineMap.put(patchLine, blockLine);
							blockLine = block.getNextLine(blockLine);
						}
						matches.add(new Match(block, startMatchBlockLine, block.getLastLine(), this, lineMap));
					}
					break;
				}
				else {
					if (startMatchBlockLine == null) startMatchBlockLine = blockLine;
					
					while (blockLine != null && !matchLine.getTrimmedText().contentEquals(blockLine.getTrimmedText())) {
						lineMap.put(patchLine, blockLine);
						blockLine = block.getNextLine(blockLine);
					}
					
					patchLine = matchLine;
					continue;
				}
			}
			else if (patchLine.getTrimmedText().contentEquals(blockLine.getTrimmedText())) {
				lineMap.put(patchLine, blockLine);
				if (startMatchBlockLine == null) {
					startMatchBlockLine = blockLine;
				}
				
				if (keepsAndRemoves.isLastLine(patchLine)) {
					matches.add(new Match(block, startMatchBlockLine, blockLine, this, lineMap));
					patchLine = keepsAndRemoves.getFirstLine();
					startMatchBlockLine = null;
					lineMap = new LineMap();
				}
				else {
					patchLine = keepsAndRemoves.getNextLine(patchLine);
				}
				blockLine = block.getNextLine(blockLine);
			}
			else {
				blockLine = block.getNextLine(blockLine);
				patchLine = keepsAndRemoves.getFirstLine();
				startMatchBlockLine = null;
				lineMap = new LineMap();
			}
			
		}
		
		return matches;
	}
	
	public boolean appliesTo(TextBlock block) {
		return match(block).size() > 0;
	}
	
	public TextBlock apply(TextBlock block) {
		List<Match> matches = match(block);
		if (matches.isEmpty()) return block;
		
		TextBlock merged = new TextBlock();
		Iterator<Match> matchIterator = matches.iterator();
		Match match = matchIterator.next();
		
		Line originalLine = block.getFirstLine();
		
		while (originalLine != null) {
			if (match != null && originalLine == match.getStartLine()) {
				for (Line patchLine : match.getPatch().getLines()) {
					if (patchLine.is(LineType.COMMENT)) {}
					else if (patchLine.is(LineType.KEEP_WILDCARD)) {
						for (Line blockLine : match.getLineMap().get(patchLine)) {
							merged.getLines().add(blockLine);
						}
					}
					else if (patchLine.is(LineType.REMOVE_WILDCARD)) {}
					else if (patchLine.isNot(LineType.REMOVE)) {
						String text;
						if (patchLine.is(LineType.INSERT)) text = patchLine.getText();
						else text = match.getLineMap().get(patchLine).get(0).getText();
						merged.getLines().add(new Line(LineType.REGULAR, text, -1));
					}
				}
				
				originalLine = block.getNextLine(match.getEndLine());
				if (matchIterator.hasNext()) match = matchIterator.next();
				else match = null;
			}
			else {
				merged.getLines().add(originalLine);
				originalLine = block.getNextLine(originalLine);
			}
		}
		
		return merged;
	}
	
	public Patch keepsAndRemoves() {
		Patch patch = new Patch();
		patch.getLines().addAll(this.getLines().stream().
				filter(l -> l.getType() == LineType.REGULAR 
						 || l.getType() == LineType.REMOVE
						 || l.getType() == LineType.KEEP_WILDCARD
						 || l.getType() == LineType.REMOVE_WILDCARD).
				collect(Collectors.toList()));
		return patch;
	}
	
}
