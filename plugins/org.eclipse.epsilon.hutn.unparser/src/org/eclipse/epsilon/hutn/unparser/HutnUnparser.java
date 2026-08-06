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
package org.eclipse.epsilon.hutn.unparser;

import org.eclipse.epsilon.hutn.model.hutn.Spec;
import org.eclipse.epsilon.hutn.unparser.formatter.HutnFormatter;
import org.eclipse.epsilon.hutn.unparser.internal.SpecUnparser;

public class HutnUnparser {

	private final SpecUnparser   unparser;
	private final HutnFormatter beautifier = new HutnFormatter();
	
	public HutnUnparser(Spec spec) {
		this.unparser = new SpecUnparser(spec);
	}
	
	public String unparse() {
		return beautifier.format(unparser.unparse());
	}
}
