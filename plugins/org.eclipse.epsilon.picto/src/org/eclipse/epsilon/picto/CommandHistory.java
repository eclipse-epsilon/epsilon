/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {
	
	protected List<Command> stack = new ArrayList<>();
	protected int stackPointer = 0;
	protected boolean executing = false;
	
	public void execute(Command command) {
		stack = stack.subList(0, stackPointer);
		command.execute();
		stack.add(command);
		stackPointer++;
	}
	
	public void goBack() {
		stackPointer--;
		stack.get(stackPointer-1).execute();
	}
	
	public boolean canGoBack() {
		return stackPointer > 1;
	}
	
	public void goForward() {
		stack.get(stackPointer).execute();
		stackPointer++;
	}
	
	public boolean canGoForward() {
		return stackPointer < stack.size();
	}
	
}
