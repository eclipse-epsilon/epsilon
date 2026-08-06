/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.userinput;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.epsilon.eol.exceptions.EolUserException;

public abstract class AbstractUserInput implements IUserInput {

	public Object choose(String question, Collection<?> choices) {
		return choose(question, choices, null);
	}
	
	public Object chooseMany(String question, Collection<?> choices) {
		return chooseMany(question, choices, new ArrayList<>());
	}
	
	public boolean confirm(String question)
			throws EolUserException {
		return confirm(question, false);
	}

	public String prompt(String question) {
		return prompt(question, "");
	}

	public int promptInteger(String question) {
		return promptInteger(question, 0);
	}

	public double promptReal(String question) {
		return promptReal(question, 0.0f);
	}
}
