/*******************************************************************************
 * Copyright (c) 2012-2018 The University of York, Aston University.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - provide context for checking if an operation is contributed.
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.contributors;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.ObjectMethod;
import org.eclipse.epsilon.eol.execute.operations.contributors.compatibility.StringCompatibilityOperationContributor;

public class OperationContributorRegistry {
	
	private final List<OperationContributor> operationContributorsCache = getDefaultOperationContributors();

	/**
	 * <p>Adds the specified {@link OperationContributor} to the list of contributors
	 * used to discover contributed operations by the registry. Contributors added
	 * with this method have a lower precedence than the default operation 
	 * contributors.</p>
	 * 
	 * <p>To add an operation contributor with a higher precedence than one or more of
	 * the default operation contributors, define a subclass that overrides the 
	 * {@link #getDefaultOperationContributors()} method.</p>
	 */
	public void add(OperationContributor operationContributor) {
		synchronized (operationContributorsCache) {
			operationContributorsCache.add(operationContributor);
		}
	}
	
	/**
	 * The list of {@link OperationContributor}s used to discover contributed
	 * operations by the registry. Subclasses may override this method to add, 
	 * remove or change the order of the {@link OperationContributor}s used 
	 * by the registry. 
	 */
	protected List<OperationContributor> getDefaultOperationContributors() {
		return new ArrayList<>(
			Arrays.asList(
				new StringCompatibilityOperationContributor(),
                new ReflectiveOperationContributor(),
                new WrapperOperationContributor(),
                new StringOperationContributor(),
                new IntegerOperationContributor(),
                new NumberOperationContributor(),
                new BooleanOperationContributor(),
                new DateOperationContributor(),
                new ArrayOperationContributor(),
                new IterableOperationContributor(),
                new ScalarOperationContributor(),
                new AnyOperationContributor(),
                new BasicEUnitOperationContributor(),
                new ModelElementOperationContributor()
			)
		);
	}
	
	/**
	 * Finds a contributed operation that is invoked without prior evaluation
	 * (i.e. the contributed operation evaluates its own AST). This category 
	 * of contributed operation is rare, but can be used for rewriting parts 
	 * of the AST at runtime or for selective logging or tracing. See, for 
	 * example, EGL's contributor for OutputBuffer's print operations.
	 */
	public ObjectMethod findContributedMethodForUnevaluatedParameters(Object target, String name, List<Expression> parameterExpressions, IEolContext context) {
		for (OperationContributor c : getOperationContributorsFor(target, context)) {
			ObjectMethod objectMethod = c.findContributedMethodForUnevaluatedParameters(target, name, parameterExpressions, context);
			if (objectMethod != null) {
				return objectMethod;
			}
		}
		return null;
	}
	
	/**
	 * Finds a contributed operation for the given target, name and parameters.
	 * This is the most common mechanism for contributing an operation. Operations
	 * contributed in this manner are invoked after their parameters have been
	 * evaluated.
	 */
	public ObjectMethod findContributedMethodForEvaluatedParameters(Object target, String name, Object[] parameters, IEolContext context) {
		for (OperationContributor c : getOperationContributorsFor(target, context)) {
			ObjectMethod objectMethod = c.findContributedMethodForEvaluatedParameters(target, name, parameters, context, false);
			if (objectMethod != null) {
				return objectMethod;
			}
		}
		return null;
	}
	
	protected Collection<OperationContributor> getOperationContributorsFor(Object target, IEolContext context) {
		return stream().filter(oc -> {
			oc.setContext(context);
			try (OperationContributor opContributor = oc) {
				return opContributor.contributesTo(target);
			}
		})
		.collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	public Stream<OperationContributor> stream() {
		return operationContributorsCache.stream();
	}
}
