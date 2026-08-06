/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.epl.combinations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class CompositeCombinationGenerator<T> implements CombinationGenerator<List<T>> {
	
	protected List<CombinationGenerator<T>> generators = new ArrayList<>();
	protected int currentGeneratorIndex = 0;
	protected Stack<List<T>> currentStack = new Stack<>();
	protected CompositeCombinationValidator<T, EolRuntimeException> validator;
	protected final List<List<T>>
		NEXT = Collections.emptyList(),
		UNKNOWN = Collections.emptyList();
	protected List<List<T>> lookahead = UNKNOWN;
	
	public static List<String> createList(String... strings) {
		ArrayList<String> list = new ArrayList<>(strings.length);
		for (String str : strings) {
			list.add(str);
		}
		return list;
	}
	
	public CompositeCombinationValidator<T, EolRuntimeException> getValidator() {
		return validator;
	}
	
	public void setValidator(CompositeCombinationValidator<T, EolRuntimeException> validator) {
		this.validator = validator;
	}

	public void addCombinationGenerator(CombinationGenerator<T> g) {
		generators.add(g);
		reset();
	}
	
	public boolean removeCombinationGenerator(CombinationGenerator<T> g) {
		boolean removed = generators.remove(g);
		reset();
		return removed;
	}
	
	@Override
	public boolean hasNext() {
		if (lookahead == UNKNOWN) {
			do {
				lookahead = getNextImpl();
			}
			while (lookahead == NEXT);
		}
		
		return lookahead != null;
	}
	
	@Override
	public List<List<T>> next() {
		if (lookahead != UNKNOWN) {
			List<List<T>> result = lookahead;
			lookahead = UNKNOWN;
			return result;
		}
		
		List<List<T>> next;
		do {
			next = getNextImpl();
		}
		while (next == NEXT);

		return next;
	}
	
	protected List<List<T>> getNextImpl() {
		while (!getCurrentGenerator().hasNext()) {
			if (isFirstGenerator()) {
				return null;
			}
			else {
				currentStack.pop();
				getCurrentGenerator().reset();
				setCurrentGenerator(getPreviousGenerator());
			}
		}
		
		if (!currentStack.isEmpty()) currentStack.pop();
		currentStack.push(getCurrentGenerator().next());
		
		boolean validCombination = produceCombinationIfValid();
		
		while (!isLastGenerator() && validCombination) {
			setCurrentGenerator(getNextGenerator());
			currentStack.push(getCurrentGenerator().next());
			validCombination = produceCombinationIfValid();
		}
		 
		return validCombination ? currentStack : NEXT;
	}
	
	private boolean produceCombinationIfValid() {
		boolean validCombination = isValidCombination();
		if (validCombination)
			getCurrentGenerator().producedValidCombination();
		return validCombination;
	}
	
	@Override
	public void reset() {
		for (CombinationGenerator<T> g : generators) {
			g.reset();
		}
		if (!generators.isEmpty()) {
			currentGeneratorIndex = 0;
		}
	}
	
	protected boolean isValidCombination() {
		for (Iterable<T> t : currentStack) {
			if (t == null) return false;
		}
		
		if (validator == null)
			return true;
		
		try {
			return validator.isValid(currentStack);
		}
		catch (EolRuntimeException ex) {
			validator.getExceptionHandler().handleException(ex);
			return false;
		}
	}
	
	protected CombinationGenerator<T> getPreviousGenerator() {
		if (currentGeneratorIndex == 0) return null;
		else return generators.get(currentGeneratorIndex - 1); 
	}
	
	protected CombinationGenerator<T> getNextGenerator() {
		if (currentGeneratorIndex == generators.size()-1) return null;
		else return generators.get(currentGeneratorIndex + 1);
	}
	
	protected CombinationGenerator<T> getCurrentGenerator() {
		return generators.get(currentGeneratorIndex);
	}
	
	protected void setCurrentGenerator(CombinationGenerator<T> g) {
		currentGeneratorIndex = generators.indexOf(g);
	}
	
	protected boolean isFirstGenerator() {
		return getPreviousGenerator() == null;
	}
	
	protected boolean isLastGenerator() {
		return getNextGenerator() == null;
	}

	@Override
	public void producedValidCombination() {
		// TODO Auto-generated method stub
	}
}
