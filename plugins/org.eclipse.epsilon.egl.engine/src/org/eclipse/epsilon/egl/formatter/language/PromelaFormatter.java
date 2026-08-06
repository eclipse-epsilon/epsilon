/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.formatter.language;

import org.eclipse.epsilon.egl.formatter.CompositeFormatter;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.epsilon.egl.formatter.linebyline.Line;
import org.eclipse.epsilon.egl.formatter.linebyline.LineByLineFormatter;
import org.eclipse.epsilon.egl.formatter.linebyline.LineFactory;

public class PromelaFormatter implements Formatter {

	private final CompositeFormatter composite = new CompositeFormatter(new PromelaBlockFormatter(),
	                                                                    new PromelaIfBlockFormatter());
	
	@Override
	public String format(String text) {
		return composite.format(text);
	}
	
	static class PromelaBlockFormatter extends LanguageFormatter {
		
		// Increase indentation after every line that contains an open bracket
		private static final String increasePattern = "\\{";
		
		// Decrease indentation after every line that contains a close bracket
		private static final String decreasePattern = "\\}";
		
		public PromelaBlockFormatter() {
			super(increasePattern, decreasePattern);
		}	
	}

	static class PromelaIfBlockFormatter extends LineByLineFormatter<PromelaIfBlockFormatter.PromelaLine> {

		private boolean insideAnIfBlock = false;
		
		@Override
		protected LineFactory<PromelaLine> createLineFactory() {
			return new PromelaLineFactory();
		}

		@Override
		protected void formatLine() {
			if (navigator.getCurrentLine().isStartOfIfBlock()) {
				insideAnIfBlock = true;
			
			} else if (navigator.getCurrentLine().isEndOfAnIfBlock()) {
				insideAnIfBlock = false;
			}
			
			if (insideAnIfBlock) {
				formatContentsOfIfBlock();
			}
		}

		private void formatContentsOfIfBlock() {
			if (betweenTheEndOfOneCaseAndTheStartOfAnother()) {
				navigator.insertBeforeCurrentLine(new PromelaLine(""));
								
			} else if (navigator.getCurrentLine().isMiddleOfACase()) {					
				navigator.getCurrentLine().addPrefix("\t");
			}
		}

		private boolean betweenTheEndOfOneCaseAndTheStartOfAnother() {
			return navigator.getCurrentLine().isStartOfACase() && 
			       navigator.getPreviousLine().isMiddleOfACase();
		}
		
		
		static class PromelaLineFactory implements LineFactory<PromelaLine> {

			@Override
			public PromelaLine createLine(String rawLine) {
				return new PromelaLine(rawLine);
			}
			
		}
		
		static class PromelaLine extends Line {

			public PromelaLine(String rawLine) {
				super(rawLine);
			}

			public boolean isStartOfIfBlock() {
				return rawLine.trim().equals("if");
			}

			public boolean isEndOfAnIfBlock() {
				return rawLine.trim().equals("fi;");
			}

			public boolean isStartOfACase() {
				return rawLine.trim().startsWith("::");
			}
			
			public boolean isMiddleOfACase() {
				return !isStartOfACase() && !isStartOfIfBlock() && !isEndOfAnIfBlock();
			}
		}
	}
}
