/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.console;

import org.eclipse.epsilon.common.parse.Position;
import org.eclipse.epsilon.common.parse.Region;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.console.IPatternMatchListener;
import org.eclipse.ui.console.PatternMatchEvent;
import org.eclipse.ui.console.TextConsole;

public class EolRuntimeExceptionHyperlinkListener implements IPatternMatchListener {
	
	protected IOConsole ioConsole;
	
	public EolRuntimeExceptionHyperlinkListener(IOConsole ioConsole) {
		this.ioConsole = ioConsole;
	}
	
	@Override
	public String getPattern() {
		return "\\(.*?@(\\d+):(\\d+)-(\\d+):(\\d+)\\)";
	}

	@Override
	public int getCompilerFlags() {
		return 0;
	}

	@Override
	public String getLineQualifier() {
		return null;
	}

	@Override
	public void connect(TextConsole console) {
		
	}

	@Override
	public void disconnect() {
		
	}

	@Override
	public void matchFound(PatternMatchEvent event) {
		
		try {
			matchFoundImpl(event);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//try {
			//messageConsole.addHyperlink(new TextEditorHyperlink(), event.getOffset(),event.getLength());
		//} catch (BadLocationException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
	}
	
	private void matchFoundImpl(PatternMatchEvent event) throws Exception {
		String matched = ioConsole.getDocument().get(event.getOffset(), event.getLength());
		matched = matched.substring(1,matched.length() -1);
		String[] parts = matched.split("@");
		String file = parts[0];
		
		String[] regionParts = parts[1].split("-");
		
		Region region = new Region();
		region.setStart(parsePosition(regionParts[0]));
		region.setEnd(parsePosition(regionParts[1]));
		
		ioConsole.addHyperlink(new ConsoleHyperlink(file, region), event.getOffset()+1, event.getLength()-2);
	}
	
	protected Position parsePosition(String s) {
		String[] parts = s.split(":");
		return new Position(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
	}
	
}
