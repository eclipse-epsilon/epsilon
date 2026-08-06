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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.epsilon.eol.exceptions.EolUserException;

public abstract class ConsoleUserInput extends AbstractUserInput {

	public void inform(String message) {
		getOutputStream().println(message);
	}

	protected String initial = "_initial";
	
	public ConsoleUserInput() {
		super();
	}

	public abstract InputStream getInputStream();
	
	public abstract PrintStream getOutputStream();
	
	public abstract PrintStream getErrorStream();
	
	public boolean confirm(String question, boolean default_) throws EolUserException {
		String response = initial;
		while (!response.equalsIgnoreCase("Y") && !response.equalsIgnoreCase("N") && !response.equalsIgnoreCase("C")){
			if (response != initial) {
				getErrorStream().println("Unrecognized choice : " + response);
				getErrorStream().flush();
			}
			getOutputStream().println(question + " (Y/N/C)");
			getOutputStream().flush();
			response = readString();	
			if (response.equalsIgnoreCase("Y")) {
				return true;
			}
			else if (response.equalsIgnoreCase("N")) {
				return false;
			}
			else if (response.equalsIgnoreCase("C")) {
				throw new EolUserException("Cancelled by the user");
			}
		}
		return true;
	}
	
	public Object chooseMany(String question, Collection<?> choices,
			Collection<?> default_) {
		throw new UnsupportedOperationException();
	}

	public Object choose(String question, Collection<?> choices, Object default_) {
		String response = initial;
		int index = -2;
		
		while (index <= 0 || index > choices.size()){
			try {
				if (index != -2) {
					getErrorStream().println("Please select a choice from 1 to " + choices.size());
				}
				Iterator<?> it = choices.iterator();
				getOutputStream().println(question);
				int i = 1;
				while (it.hasNext()) {
					getOutputStream().println(i + ") " + it.next());
					i++;
				}
				response = readString();
				index = Integer.parseInt(response);
				
				if (index > 0 && index <= choices.size()){
					if (choices instanceof List) {
						return ((List<?>) choices).get(index-1);
					}
					else {
						return new ArrayList<Object>(choices).get(index-1);
				
					}
				}
			}
			catch (NumberFormatException e) {
				getErrorStream().println("Not a valid number : " + response);
				index = -1;
			}
		}
		
		return null;
	}

	public String prompt(String question, String deault_) {
		getOutputStream().println(question);
		return readString();
	}

	public int promptInteger(String question, int default_) {
		
		boolean validResponse = false;
		int response = 0;
		String responseStr = "";
		
		while (!validResponse) {
			try {
				getOutputStream().println(question);
				responseStr = readString().trim();
				response = Integer.parseInt(responseStr);
				validResponse = true;
			}
			catch (NumberFormatException ex) {
				getErrorStream().println("Not a valid number : " + responseStr);
			}
		}
		return response;
	}

	public float promptReal(String question, float default_) {
		// For backwards compatibility
		return (float) promptReal(question, (double)default_);
	}

	public double promptReal(String question, double default_) {
		
		boolean validResponse = false;
		double response = 0;
		String responseStr = "";
		
		while (!validResponse) {
			try {
				getOutputStream().println(question);
				responseStr = readString().trim();
				response = Double.parseDouble(responseStr);
				validResponse = true;
			}
			catch (NumberFormatException ex) {
				getErrorStream().println("Not a valid number : " + responseStr);
			}
		}
		return response;
	}
	
	protected String readString(){
		byte[] response = new byte[100];
		try {
			getInputStream().read(response);
		} catch (IOException e) {
			return "";
		}
		return new String(response).trim();
		
	}
}
