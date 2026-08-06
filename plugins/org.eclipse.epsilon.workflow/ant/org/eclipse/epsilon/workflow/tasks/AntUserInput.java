/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tools.ant.input.InputHandler;
import org.apache.tools.ant.input.InputRequest;
import org.apache.tools.ant.input.MultipleChoiceInputRequest;
import org.eclipse.epsilon.eol.exceptions.EolUserException;
import org.eclipse.epsilon.eol.userinput.AbstractUserInput;

public class AntUserInput extends AbstractUserInput{
	
	protected InputHandler inputHandler;
	
	public AntUserInput(InputHandler inputHandler) {
		this.inputHandler = inputHandler;
	}
	
	@Override
	public Object choose(String question, Collection<?> choices, Object default_) {
		List<String> choiceLabels = choices.stream().map(c -> c.toString()).collect(Collectors.toList());
		MultipleChoiceInputRequest request = new MultipleChoiceInputRequest(question, choiceLabels);
		inputHandler.handleInput(request);
		int choiceIndex = choiceLabels.indexOf(request.getInput());
		if (choiceIndex >= 0) return new ArrayList<>(choices).get(choiceIndex);
		else return null;
	}

	@Override
	public boolean confirm(String question, boolean default_)
			throws EolUserException {
		BooleanInputRequest request = new BooleanInputRequest(question);
		request.setDefaultValue(default_ + "");
		inputHandler.handleInput(request);
		return Boolean.valueOf(request.getInput());
	}
 
	@Override
	public String prompt(String question, String default_) {
		InputRequest request = new InputRequest(question);
		request.setDefaultValue(default_ + "");
		inputHandler.handleInput(request);
		return request.getInput();
	}

	@Override
	public int promptInteger(String question, int default_) {
		IntegerInputRequest request = new IntegerInputRequest(question);
		request.setDefaultValue(default_ + "");
		inputHandler.handleInput(request);
		return Integer.valueOf(request.getInput());
	}

	@Override
	public float promptReal(String question, float default_) {
		FloatInputRequest request = new FloatInputRequest(question);
		request.setDefaultValue(default_ + "");
		inputHandler.handleInput(request);
		return Float.valueOf(request.getInput());
	}

	@Override
	public double promptReal(String question, double default_) {
		DoubleInputRequest request = new DoubleInputRequest(question);
		request.setDefaultValue(default_ + "");
		inputHandler.handleInput(request);
		return Double.valueOf(request.getInput());
	}
	
	@Override
	public void inform(String message) {
		System.out.println(message);
	}

	@Override
	public Object chooseMany(String question, Collection<?> choices,
			Collection<?> default_) {
		throw new UnsupportedOperationException();
	}
		
	class IntegerInputRequest extends InputRequest {

		public IntegerInputRequest(String prompt) {
			super(prompt);
		}

		@Override
		public boolean isInputValid() {
			try {
				Integer.parseInt(this.getInput());
				return true;
			}
			catch (Exception ex) {
				return false;
			}
		}
		
	}

	class BooleanInputRequest extends InputRequest {

		public BooleanInputRequest(String prompt) {
			super(prompt);
		}

		@Override
		public boolean isInputValid() {
			try {
				Boolean.parseBoolean(this.getInput());
				return true;
			}
			catch (Exception ex) {
				return false;
			}
		}
		
	}
	
	class FloatInputRequest extends InputRequest {

		public FloatInputRequest(String prompt) {
			super(prompt);
		}

		@Override
		public boolean isInputValid() {
			try {
				Float.parseFloat(this.getInput());
				return true;
			}
			catch (Exception ex) {
				return false;
			}
		}
		
	}
	
	class DoubleInputRequest extends InputRequest {

		public DoubleInputRequest(String prompt) {
			super(prompt);
		}

		@Override
		public boolean isInputValid() {
			try {
				Double.parseDouble(this.getInput());
				return true;
			}
			catch (Exception ex) {
				return false;
			}
		}
		
	}
	
}
