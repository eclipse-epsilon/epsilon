/*******************************************************************************
 * Copyright (c) 2008-2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - concurrency support
 ******************************************************************************/
package org.eclipse.epsilon.evl;

import java.util.Set;
import java.util.List;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.IErlModule;
import org.eclipse.epsilon.evl.dom.Constraint;
import org.eclipse.epsilon.evl.dom.ConstraintContext;
import org.eclipse.epsilon.evl.dom.GlobalConstraintContext;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.evl.execute.context.IEvlContext;
import org.eclipse.epsilon.evl.execute.exceptions.EvlConstraintNotFoundException;

public interface IEvlModule extends IErlModule {
	
	List<Constraint> getConstraints();
	
	List<ConstraintContext> getDeclaredConstraintContexts();
	
	List<ConstraintContext> getConstraintContexts();
	
	/**
	 * @since 1.6 returns Set instead of List
	 */
	@Override
	Set<UnsatisfiedConstraint> execute() throws EolRuntimeException;
	
	void setUnsatisfiedConstraintFixer(IEvlFixer fixer);
	
	IEvlFixer getUnsatisfiedConstraintFixer();
	
	@Override
	default IEvlContext getContext() {
		return (IEvlContext) ((IErlModule)this).getContext();
	}
	
	// UTILS
	
	/**
	 * Searches for a {@link ConstraintContext} by the specified type name.
	 * 
	 * @param name The name of the model element type.
	 * @return The first ConstraintContext matching the name, or <code>null</code> if not found.
	 * @since 1.6
	 */
	default ConstraintContext getConstraintContext(String name) {
		return getConstraintContexts()
			.stream()
			.filter(cc -> cc.getTypeName().equals(name))
			.findFirst()
			.orElse(null);
	}
	
	/**
	 * Finds a Constraint instance in this module based on the specified filters.
	 * 
	 * @param constraintName The constraint name to search for (mandatory).
	 * @param contextName The name of the model element type (ConstraintContext) that the constraint is declared in (optional).
	 * @param modelElement A model element which the Constraint's context should be applicable to (optional).
	 * @param ast The AST that is calling this operation (optional).
	 * @return A Constraint matching the specified criteria, or <code>null</code> if not found.
	 * @throws EolRuntimeException If there are problems finding the constraint from the specified parameters.
	 * @throws EvlConstraintNotFoundException If the constraint could not be found.
	 * @since 1.6
	 */
	default Constraint getConstraint(String constraintName, String contextName, Object modelElement, ModuleElement ast) throws EolRuntimeException {
		IEvlContext context = getContext();
		for (Constraint constraint : getConstraints()) {
			ConstraintContext cc = constraint.getConstraintContext();
			if (constraint.getName().equals(constraintName) &&
				(StringUtil.isEmpty(contextName) || cc.getTypeName().equals(contextName)) &&
				(cc instanceof GlobalConstraintContext || modelElement == null || cc.isOfSourceKind(modelElement, context))
			) {
				return constraint;
			}
		}
		throw new EvlConstraintNotFoundException(constraintName, ast);
	}
}
