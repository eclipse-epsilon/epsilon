/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.nestedelements;

import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.egl.formatter.Formatter;

public class EglDefaultFormatterNestedElement {

	private Class<? extends Formatter> implementation;

	public Class<? extends Formatter> getImplementation() {
		return implementation;
	}

	public void setImplementation(Class<? extends Formatter> implementation) {
		if (Formatter.class.isAssignableFrom(implementation)) {
			this.implementation = implementation;
			
		} else {
			throw new BuildException("The implementation parameter must be a class that subtypes org.eclipse.epsilon.egl.formatter.Formatter.");
		}
	}
}
