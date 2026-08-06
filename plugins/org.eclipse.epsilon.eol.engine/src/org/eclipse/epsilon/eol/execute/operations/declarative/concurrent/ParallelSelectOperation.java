/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.execute.operations.declarative.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.concurrent.EolContextParallel;
import org.eclipse.epsilon.eol.execute.context.concurrent.IEolContextParallel;
import org.eclipse.epsilon.eol.execute.operations.declarative.SelectOperation;
import org.eclipse.epsilon.eol.function.CheckedEolPredicate;
import org.eclipse.epsilon.eol.types.EolCollectionType;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class ParallelSelectOperation extends SelectOperation {
	
	@Override
	public Collection<?> execute(boolean returnOnMatch, Object target, NameExpression operationNameExpression, List<Parameter> iterators, Expression expression, IEolContext context_) throws EolRuntimeException {

		Collection<Object> source = resolveSource(target, iterators, context_);
		Collection<Object> resultsCol = EolCollectionType.createSameType(source);
		if (source.isEmpty()) return resultsCol;
		Collection<Callable<Optional<?>>> jobs = new ArrayList<>(source.size());
		IEolContextParallel context = EolContextParallel.convertToParallel(context_);
		CheckedEolPredicate<Object> predicate = resolvePredicate(operationNameExpression, iterators, expression, context);
		AtomicBoolean keepSearching = returnOnMatch ? new AtomicBoolean(true) : null;
		
		for (Object item : source) {
			jobs.add(() -> {
				Optional<?> intermediateResult = null;
				if ((keepSearching == null || keepSearching.get()) && predicate.testThrows(item)) {
					intermediateResult = Optional.ofNullable(item);
					if (returnOnMatch) {
						keepSearching.set(false);
					}
				}
				return intermediateResult;
			});
		}
		
		Stream<Optional<?>> resStream = context.executeAll(expression, jobs).stream().filter(r -> r != null);
		
		if (returnOnMatch) {
			resStream.findAny().ifPresent(r -> resultsCol.add(r.orElse(null)));
		}
		else {
			resStream.map(opt -> opt.orElse(null)).forEach(resultsCol::add);
		}
		
		return resultsCol;
	}
}
