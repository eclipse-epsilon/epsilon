/*******************************************************************************
 * Copyright (c) 2013 The University of York.
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
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.flock.context.GuardedConstructContext;
import org.eclipse.epsilon.flock.parse.FlockParser;

public abstract class GuardedConstruct extends FlockConstruct {

	private ExecutableBlock<Boolean> guard;
	
	@SuppressWarnings("unchecked")
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		this.guard = (ExecutableBlock<Boolean>) module.createAst(AstUtil.getChild(cst, FlockParser.GUARD), this);
	}
	
	protected ExecutableBlock<Boolean> getGuard() {
		return guard;
	}
	
	public boolean appliesIn(GuardedConstructContext context) throws EolRuntimeException {
		return context.satisfies(guard);
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof GuardedConstruct))
			return false;
		
		final GuardedConstruct other = (GuardedConstruct)object;
		
		return super.equals(other) &&
				guard != null && guard.equals(other.guard);
	}
}
