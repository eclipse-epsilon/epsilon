/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.model.domain.rules;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import org.eclipse.epsilon.flock.context.MigrationStrategyCheckingContext;
import org.eclipse.epsilon.flock.model.checker.IgnoredPropertyChecker;

public class IgnoredProperties {

	private final Collection<String> ignoredProperties = new LinkedList<>();
	
	public IgnoredProperties(String... ignoredProperties) {
		this(Arrays.asList(ignoredProperties));
	}
	
	public IgnoredProperties(Collection<String> ignoredProperties) {
		this.ignoredProperties.addAll(ignoredProperties);
	}

	public void add(String ignoredProperty) {
		this.ignoredProperties.add(ignoredProperty);
	}
	
	public void addAll(IgnoredProperties other) {
		this.ignoredProperties.addAll(other.ignoredProperties);
	}

	public boolean isNotIgnored(String propertyName) {
		return !isIgnored(propertyName);
	}

	private boolean isIgnored(String propertyName) {
		return ignoredProperties.contains(propertyName);
	}
	
	public boolean isEmpty() {
		return ignoredProperties.isEmpty();
	}
	
	public void check(String originalType, MigrationStrategyCheckingContext context) {
		final IgnoredPropertyChecker checker = new IgnoredPropertyChecker(originalType, context);
		
		for (String ignoredProperty : ignoredProperties) {
			checker.check(ignoredProperty);
		}
	}
	
	
	@Override
	public String toString() {
		return ignoredProperties.toString();
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof IgnoredProperties))
			return false;
		
		final IgnoredProperties other = (IgnoredProperties)object;
		
		return ignoredProperties.equals(other.ignoredProperties);
	}
	
	@Override
	public int hashCode() {
		return ignoredProperties.hashCode();
	}
}
