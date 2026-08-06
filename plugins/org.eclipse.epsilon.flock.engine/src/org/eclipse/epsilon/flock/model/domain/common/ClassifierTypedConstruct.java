/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.flock.model.domain.common;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.flock.context.GuardedConstructContext;
import org.eclipse.epsilon.flock.context.MigrationStrategyCheckingContext;
import org.eclipse.epsilon.flock.model.checker.ClassifierTypedConstructChecker;

public abstract class ClassifierTypedConstruct extends GuardedConstruct {

	private String originalType;
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		originalType = cst.getFirstChild().getText();
		if (originalType == null) throw new IllegalStateException("originalType cannot be null");
	}
	
	public String getOriginalType() {
		return originalType;
	}
	
	protected boolean isStrict() {
		return hasAnnotation("strict");
	}
	
	@Override
	public boolean appliesIn(GuardedConstructContext context) throws EolRuntimeException {
		return typedFor(context) && super.appliesIn(context);
	}

	protected boolean typedFor(GuardedConstructContext context) {
		return context.originalConformsTo(originalType, isStrict());
	}
	
	public void check(MigrationStrategyCheckingContext context) {
		new ClassifierTypedConstructChecker(originalType, context).check();
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ClassifierTypedConstruct))
			return false;
		
		final ClassifierTypedConstruct other = (ClassifierTypedConstruct)object;
		
		return super.equals(other) &&
			   originalType.equals(other.originalType);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + originalType.hashCode();
	}
}
