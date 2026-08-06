/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.epl.execute;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.epl.dom.Pattern;

public class PatternMatch {
	
	protected final Pattern pattern;
	protected Map<String, Object> roleBindings = new HashMap<>();
	
	public PatternMatch(Pattern pattern) {
		this.pattern = pattern;
	}
	
	public Pattern getPattern() {
		return pattern;
	}
	
	public Map<String, Object> getRoleBindings() {
		return roleBindings;
	}
	
	public Object getRoleBinding(String name) {
		return getRoleBindings().get(name);
	}
	
	public void putRoleBinding(Variable variable) {
		roleBindings.put(variable.getName(), variable.getValue());
	}
	
	@Override
	public String toString() {
		return "Pattern '"+pattern+"':"+System.lineSeparator()+"\t"+
			roleBindings.entrySet().stream()
				.map(Object::toString)
				.collect(Collectors.joining(System.lineSeparator()));
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PatternMatch)) return false;
		PatternMatch that = (PatternMatch) o;
		return
			Objects.equals(this.pattern, that.pattern) &&
			Objects.equals(this.roleBindings.keySet(), that.roleBindings.keySet());
	}
}
