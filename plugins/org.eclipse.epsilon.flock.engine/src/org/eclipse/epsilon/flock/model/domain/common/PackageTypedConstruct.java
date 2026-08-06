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
import org.eclipse.epsilon.flock.model.checker.PackageTypedConstructChecker;

public abstract class PackageTypedConstruct extends GuardedConstruct {

	private String originalPackage;
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);

		originalPackage = cst.getFirstChild().getText();
		if (originalPackage == null)
			throw new IllegalArgumentException("originalPackage cannot be null");
	}
	
	public String getOriginalPackage() {
		return originalPackage;
	}
	
	@Override
	public boolean appliesIn(GuardedConstructContext context) throws EolRuntimeException {
		return context.originalBelongsTo(originalPackage) && super.appliesIn(context);
	}
	
	public void check(MigrationStrategyCheckingContext context) {
		new PackageTypedConstructChecker(originalPackage, context).check();
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof PackageTypedConstruct))
			return false;
		
		final PackageTypedConstruct other = (PackageTypedConstruct)object;
		
		return super.equals(other) &&
			   originalPackage.equals(other.originalPackage);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + originalPackage.hashCode();
	}
}
