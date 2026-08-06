/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.erl.dom;

import org.eclipse.epsilon.common.util.StringUtil;

public class Pre extends NamedStatementBlockRule {
	
	@Override
	public String toString() {
		String ts = "pre";
		final String name = getName();
		if (!StringUtil.isEmpty(name)) {
			ts += " "+name;
		}
		return ts;
	}
	
	public void accept(IErlVisitor visitor) {
		visitor.visit(this);
	}
	
}
