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

import java.util.Collection;

import org.eclipse.epsilon.eol.exceptions.EolUserException;

public interface IUserInput {
	
	public void inform(String message);
	
	public boolean confirm(String question, boolean default_) throws EolUserException;
	
	public Object chooseMany(String question, Collection<?> choices, Collection<?> default_);
	
	public Object choose(String question, Collection<?> choices, Object default_);
	
	public String prompt(String question, String default_);
	
	public int promptInteger(String question, int default_);

	public float promptReal(String question, float default_);

	public double promptReal(String question, double default_);

}
