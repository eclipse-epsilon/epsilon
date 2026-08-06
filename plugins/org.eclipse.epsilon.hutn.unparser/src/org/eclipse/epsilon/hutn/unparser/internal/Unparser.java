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
package org.eclipse.epsilon.hutn.unparser.internal;

import org.eclipse.epsilon.hutn.model.hutn.Object;


abstract class Unparser {

	protected final StringBuilder builder;
	
	public Unparser() {
		this.builder = new StringBuilder();
	}
	
	public Unparser(StringBuilder builder) {
		this.builder = builder;
	}
	

	public String unparse() {
		doUnparse();
		
		return builder.toString();
	}

	protected abstract void doUnparse();

	
	
	protected void appendSpace() {
		builder.append(' ');
	}
	
	protected void appendNewLine() {
		builder.append('\n');
	}
	
	protected void appendStringValue(String value) {
		builder.append('"');
		builder.append(value);
		builder.append('"');
	}
	
	
	protected void appendSignature(Object co) {
		appendSignature(co.getType(), co.getIdentifier());
	}
	
	protected void appendSignature(String type, String identifier) {
		unparseType(type);
		unparseIdentifier(identifier);
	}
	
	private void unparseType(String type) {
		builder.append(type);
		appendSpace();
	}
	
	private void unparseIdentifier(String identifier) {
		if(identifier != null) {
			appendStringValue(identifier);
		}
	}
}
