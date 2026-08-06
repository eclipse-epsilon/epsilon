/*********************************************************************
* Copyright (c) 2018-2019 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.execute.operations.declarative;

import java.util.Collection;
import java.util.List;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolIllegalOperationParametersException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.function.CheckedEolPredicate;

/**
 * Operation based on whether <i>n</i> elements satisfy the predicate.
 * The semantics are defined by {@link MatchMode}. <i>n</i> can be user-defined
 * or specified once for this short-circuiting operation.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class NMatchOperation extends FirstOrderOperation {

	/**
	 * Determines the interpretation of <i>n</i>.</br>
	 * {@link #EXACT}: Whether the number of elements satisfying the predicate is equal to <i>n</i>,<br/>
	 * {@link #MINIMUM}: Whether the number of elements satisfying the predicate is greater than or equal to <i>n</i>,<br/>
	 * {@link #MAXIMUM}: Whether the number of elements satisfying the predicate is less than or equal to <i>n</i>.<br/>
	 */
	public enum MatchMode {
		EXACT, MINIMUM, MAXIMUM;
	}
	
	private final int n;
	protected final MatchMode mode;
	
	/**
	 * nMatch operation where target matches is specified on a per-invocation basis.
	 */
	public NMatchOperation(MatchMode behaviour) {
		n = -1;	// Negative signifies that no initial value is specified
		this.mode = behaviour;
	}
	
	/**
	 * nMatch operation with the target matches pre-specified.
	 * 
	 * @param behaviour How to interpret what <i>n</i> means.
	 * @param n The number of target matches.
	 * @throws IllegalArgumentException If n is less than zero.
	 */
	public NMatchOperation(MatchMode behaviour, int n) throws IllegalArgumentException {
		if ((this.n = n) < 0)
			throw new IllegalArgumentException("Target matches can't be negative!");
		this.mode = behaviour;
	}

	@Override
	public final Boolean execute(Object target, NameExpression operationNameExpression, List<Parameter> iterators, List<Expression> expressions, IEolContext context) throws EolRuntimeException {
		int targetMatches;
		if (n >= 0) {
			targetMatches = n;
		}
		else if (expressions.size() > 1) {
			Object userDefinedN = expressions.get(1).execute(context);
			if (userDefinedN instanceof Integer) {
				targetMatches = (int) userDefinedN;
			}
			else {
				String actualType = userDefinedN != null ? userDefinedN.getClass().getTypeName() : "null";
				throw new EolIllegalOperationParametersException("nMatch", "Integer", actualType, expressions.get(1));
			}
		}
		else {
			throw new EolIllegalOperationParametersException("nMatch");
		}
		
		final Collection<Object> source = resolveSource(target, iterators, context);
		final int sourceSize = source.size();

		if (mode != MatchMode.MAXIMUM && sourceSize < targetMatches)
			return false;
		
		return execute(sourceSize, targetMatches, source, operationNameExpression, iterators, expressions.get(0), context);
	}
	
	protected boolean shouldShortCircuit(int sourceSize, int targetMatches, int currentMatches, int currentIndex) {
		// Short-circuit if we met / exceeded the number OR already failed to meet threshold
		return
			// First check whether we've already met the criteria
			(currentMatches > targetMatches || (mode == MatchMode.MINIMUM && currentMatches == targetMatches)) ||
			// # of remaining elements  vs   # of remaining matches
			(sourceSize - currentIndex) < (targetMatches - currentMatches);
	}
	
	protected boolean determineResult(int currentMatches, int targetMatches) {
		switch (mode) {
			case EXACT: return currentMatches == targetMatches;
			case MINIMUM: return currentMatches >= targetMatches;
			case MAXIMUM: return currentMatches <= targetMatches;
			default: return false;
		}
	}
	
	protected boolean execute(int sourceSize, int targetMatches, Collection<Object> source, NameExpression operationNameExpression, List<Parameter> iterators, Expression expression, IEolContext context) throws EolRuntimeException {
		
		CheckedEolPredicate<Object> predicate = resolvePredicate(operationNameExpression, iterators, expression, context);
		int currentIndex = 0, currentMatches = 0;
		
		for (Object item : source) {
			++currentIndex;
			
			if (predicate.testThrows(item)) {
				++currentMatches;
			}
			
			if (shouldShortCircuit(sourceSize, targetMatches, currentMatches, currentIndex)) {
				break;
			}
		}
		
		return determineResult(currentMatches, targetMatches);
	}
}
