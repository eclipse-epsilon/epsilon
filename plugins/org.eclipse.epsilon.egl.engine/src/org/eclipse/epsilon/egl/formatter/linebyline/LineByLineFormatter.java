/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.formatter.linebyline;

import org.eclipse.epsilon.egl.formatter.Formatter;

public abstract class LineByLineFormatter<T extends Line> implements Formatter {

	protected LineByLineNavigator<T> navigator;
	
	@Override
	public final String format(String text) {
		navigator = new LineByLineNavigator<>(text, createLineFactory());

		while (navigator.hasMoreLines()) {
			
			formatLine();
			
			navigator.moveToNextLine();
		}
		
		return navigator.getText();
	}

	protected abstract LineFactory<T> createLineFactory();
	protected abstract void formatLine();
}
