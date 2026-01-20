/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.execute;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.userinput.IUserInput;
import org.eclipse.epsilon.evl.IEvlFixer;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.execute.context.IEvlContext;

public class CommandLineFixer implements IEvlFixer {

	@Override
	public void fix(IEvlModule module) throws EolRuntimeException {
		IEvlContext context = module.getContext();
		IUserInput userInput = context.getUserInput();
		
		for (UnsatisfiedConstraint unsatisfiedConstraint : context.getUnsatisfiedConstraints()) {
			context.getOutputStream().println(unsatisfiedConstraint.getMessage());
			boolean fixIt = unsatisfiedConstraint.getFixes().size() > 0 && userInput.confirm("Fix error?", true);
			
			if (fixIt) {
				FixInstance fixInstance = (FixInstance) userInput.choose(unsatisfiedConstraint.getMessage(), unsatisfiedConstraint.getFixes(), null);
				if (fixInstance != null) {
					fixInstance.perform();
				}
			}
		}
	}

}
