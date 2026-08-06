/*******************************************************************************
 * Copyright (c) 2013 Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio García-Domínguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ewl.execute;

import java.util.Collection;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.ewl.dom.Wizard;
import org.eclipse.epsilon.ewl.execute.context.IEwlContext;

/**
 * Applies the same EWL wizard to a collection of applicable objects. 
 */
public class WizardLoopInstance extends WizardInstance {

	private Collection<Object> collection;

	public WizardLoopInstance(Wizard wizard, Collection<Object> objects, IEwlContext context) {
		super(wizard, null, context);
		this.collection = objects;
	}

	public Collection<Object> getCollection() {
		return collection;
	}

	public void setCollection(Collection<Object> collection) {
		this.collection = collection;
	}

	@Override
	public void setSelf(Object self) {
		throw new UnsupportedOperationException("Cannot set 'self' for EWL loop instances");
	}

	@Override
	public void process() throws EolRuntimeException {
		try {
			for (Object o : collection) {
				super.setSelf(o);
				super.process();
			}
		} finally {
			super.setSelf(null);
		}
	}

}
