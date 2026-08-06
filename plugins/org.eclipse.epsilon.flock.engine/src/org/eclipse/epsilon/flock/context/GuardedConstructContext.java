/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.context;

import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;

public class GuardedConstructContext {

	private final ModelElement element;
	private final IEolContext context;
	
	public GuardedConstructContext(ModelElement element, IEolContext context) {
		this.element = element;
		this.context = context;
	}
	
	public boolean originalConformsTo(String originalType, boolean strict) {
		if (strict)
			return element.isTypeOf(originalType);
		else
			return element.isKindOf(originalType);
	}
	
	public boolean originalBelongsTo(String originalPackage) {
		return element.belongsTo(originalPackage);
	}
	
	public boolean satisfies(ExecutableBlock<Boolean> guard) throws EolRuntimeException {
		if (guard == null) {
			return true;
		} else {
			return guard.execute(context, element.createReadOnlyVariable("original"));
		}
	}

	/**
	 * Returns true if and only if it's possible to create a context for the parent of
	 * the model element in this context. Returns false otherwise, such as when the 
	 * model element in this context has no parent model element.
	 */
	public boolean isContextForParentElement() {
		return element.getContainer() != null;
	}
	
	public GuardedConstructContext getContextForParentElement() {
		return new GuardedConstructContext(element.getContainer(), context);
	}
}
