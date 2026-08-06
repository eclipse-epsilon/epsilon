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

import org.eclipse.epsilon.hutn.model.hutn.NsUri;
import org.eclipse.epsilon.hutn.model.hutn.PackageObject;
import org.eclipse.epsilon.hutn.model.hutn.Spec;

public class SpecUnparser extends Unparser {

	private final Spec spec;
	
	public SpecUnparser(Spec spec) {
		this.spec = spec;
	}

	@Override
	protected void doUnparse() {
		appendHeader();
		appendNewLine();
		unparsePackageObjects();
	}
	
	private void appendHeader() {
		if (!spec.getNsUris().isEmpty()) {
			builder.append("@Spec {");
			
			unparseNsUris();
			
			builder.append("}");
			appendNewLine();
		}
	}
	
	private void unparseNsUris() {
		for (NsUri nsUri : spec.getNsUris()) {
			unparseNsUri(nsUri);
		}
	}
	
	private void unparseNsUri(NsUri nsUri) {
		builder.append("metamodel");
		appendSpace();
		appendStringValue(nsUri.getValue());
		appendSpace();
		builder.append('{');
		
		builder.append("nsUri: ");
		appendStringValue(nsUri.getValue());
		
		appendNewLine();
		builder.append('}');
	}

	private void unparsePackageObjects() {
		for (PackageObject po : spec.getObjects()) {
			unparsePackageObject(po);
		}
	}
	
	private void unparsePackageObject(PackageObject po) {
		new PackageObjectUnparser(po, builder).unparse();
		appendNewLine();
	}
	
}
