/*******************************************************************************
 * Copyright (c) 2008-2018 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - refactoring, utility methods, short-circuiting
 ******************************************************************************/
package org.eclipse.epsilon.evl.execute.context;

import java.util.*;
import java.util.stream.Collectors;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.execute.context.IErlContext;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.dom.Constraint;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.evl.trace.ConstraintTrace;

public interface IEvlContext extends IErlContext {
	
	/**
	 * Casts the IModule to IEvlModule
	 * @see org.eclipse.epsilon.eol.execute.context.IEolContext#getModule()
	 * @since 1.6
	 */
	@Override
	IEvlModule getModule();
	
	/**
	 * This collection is written to internally by the engine during execution.
	 * Although {@link IEvlModule#execute()} mandates the results to be a Set, 
	 * for performance optimisation reasons this method is permitted to return
	 * any collection, so long as once execution is complete, the results contain
	 * no duplicates. This collection should therefore only be used to add elements,
	 * and is expected not to be queried during execution.
	 * 
	 * @return A mutable collection of unsatisfied Constraint-element pairs.
	 */
	Collection<UnsatisfiedConstraint> getUnsatisfiedConstraints();
	
	/**
	 * This method is called internally once all constraints have been processed to convert
	 * the underlying collection in {@link #getUnsatisfiedConstraints()} to a unique one.
	 * 
	 * @return The final, filtered set of UnsatisfiedConstraints suitable for post-processing.
	 * @since 1.6
	 */
	default Set<UnsatisfiedConstraint> uniqueUnsatisfiedConstraints() {
		Collection<UnsatisfiedConstraint> uc = getUnsatisfiedConstraints();
		if (uc instanceof Set) return (Set<UnsatisfiedConstraint>) uc;
		return new LinkedHashSet<>(uc);
	}
	
	/**
	 * Used internally to determine evaluated constraints.
	 * 
	 * @return The checked Constraint-element pairs, or <code>null</code> if
	 * the trace is disabled.
	 */
	ConstraintTrace getConstraintTrace();

	/**
	 * @since 1.6
	 */
	public static final String
		OPTIMIZE_CONSTRAINT_TRACE = "optimizeConstraintTrace",
		SHORT_CIRCUIT = "shortCircuit";
	
	/**
	 * Return true if the constraint results cache is optimized. When optimized, constraint results
	 * will only be cached during satisfies operation executions, that is, results will only be
	 * cached if required. If false, constraint results will always be cached.
	 * <p>
	 * The default value is false;
	 * @return The value of the flag.
	 * @since 1.6
	 */
	boolean isOptimizeConstraintTrace();

	/**
	 * Set the flag for using optimized contraint result caching.
	 * @see #isOptimizeConstraintTrace()
	 * @param use	Set to true to use optimzied contraint caching.
	 * @since 1.6
	 */
	void setOptimizeConstraintTrace(boolean optimized);
	
	/**
	 * Option allowing validation to terminate early when any invariant is unsatisfied.
	 * 
	 * @return Whether validation will stop once an UnsatisfiedConstraint is found.
	 * @since 1.6
	 */
	boolean isShortCircuiting();
	
	/**
	 * Sets whether short-circuited validation is enabled.
	 * 
	 * @param shortCircuit The new value for the flag.
	 * @since 1.6
	 */
	void setShortCircuit(boolean shortCircuit);
	
	/**
	 * Checks whether the condition for short-circuiting is met, either by previous invocation returning
	 * true or if there are unsatisfied constraints and the
	 * {@link #isShortCircuiting()} flag is enabled, or if the specified module element has been annotated
	 * with a termination criteria and an unsatisfied constraint containing the type is already present.
	 * 
	 * @param Constraint The rule with the termination annotation.
	 * @return Whether termination should be suspended.
	 * @throws EolRuntimeException
	 * @throws IllegalArgumentException If the rule parameter is not an appropriate type.
	 * @since 1.6
	 */
	default boolean shouldShortCircuit(Constraint constraint) throws EolRuntimeException {
		if (isShortCircuiting() && !getUnsatisfiedConstraints().isEmpty()) {
			return true;
		}
		if (constraint.getBooleanAnnotationValue("terminate", this)) {
			return getUnsatisfiedConstraints()
					.stream()
					.map(UnsatisfiedConstraint::getConstraint)
					.filter(constraint::equals)
					.findAny()
					.isPresent();
		}
		return false;
	}
	
	/**
	 * @since 1.6
	 * @return
	 */
	default boolean hasFixes() {
		return getUnsatisfiedConstraints()
			.stream()
			.map(UnsatisfiedConstraint::getFixes)
			.anyMatch(fixes -> !fixes.isEmpty());
	}
	
	/**
	 * @return A mapping from a Constraint to all of its unsatisfied instances.
	 * @since 1.6
	 */
	default Map<Constraint, Set<Object>> sortUnsatisfiedConstraints() {
		return getUnsatisfiedConstraints()
			.stream()
			.collect(Collectors.groupingBy(
				UnsatisfiedConstraint::getConstraint,
				LinkedHashMap::new, 
				Collectors.mapping(UnsatisfiedConstraint::getInstance, Collectors.toSet())
			));
	}
	
	/**
	 * @return A sorted entry of Constraints with the number of unsatisfied instances, in descending order.
	 * @since 1.6
	 */
	default Map<Constraint, Integer> getUnsatisfiedConstraintsBySize() {
		return sortUnsatisfiedConstraints()
			.entrySet()
			.stream()
			.map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().size()))
			.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
			.collect(Collectors.toMap(
				Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new)
			);
	}
	
	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	default Set<Constraint> getConstraintsDependedOn() {
		return getModule().getConstraints()
			.stream()
			.filter(Constraint::isDependedOn)
			.collect(Collectors.toSet());
	}
}
