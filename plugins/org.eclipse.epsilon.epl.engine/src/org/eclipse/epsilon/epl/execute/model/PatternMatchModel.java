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
package org.eclipse.epsilon.epl.execute.model;

import java.util.*;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.execute.operations.contributors.StringOperationContributor;
import org.eclipse.epsilon.eol.models.Model;
import org.eclipse.epsilon.epl.dom.Pattern;
import org.eclipse.epsilon.epl.dom.Role;
import org.eclipse.epsilon.epl.execute.PatternMatch;

public class PatternMatchModel extends Model {
	
	protected final Map<String, Collection<PatternMatch>> matchMap;
	protected final Map<String, Set<Object>> componentMap;
	protected final Set<PatternMatch> matches;
	
	public PatternMatchModel() {
		matchMap = new HashMap<>();
		componentMap = new HashMap<>();
		matches = new HashSet<>();
		setName("P");
		propertyGetter = new PatternMatchPropertyGetter();
		propertySetter = new PatternMatchPropertySetter();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		matches.clear();
		componentMap.values().forEach(Set::clear);
		componentMap.clear();
		matchMap.values().forEach(Collection::clear);
	}
	
	/**
	 * 
	 * @param matches
	 * @since 1.6
	 */
	public void addMatches(Collection<PatternMatch> matches) {
		// TODO: optimize
		for (PatternMatch match : matches) addMatch(match);
	}
	
	public void addMatch(PatternMatch match) {
		String patternName = match.getPattern().getName();
		Collection<PatternMatch> matchMapMatches = matchMap.get(patternName);
		matchMapMatches.add(match);
		matches.add(match);
		
		StringOperationContributor stringOps = new StringOperationContributor();
		for (String componentName : match.getRoleBindings().keySet()) {
			stringOps.setTarget(componentName);
			Set<Object> values = componentMap.get(patternName + stringOps.firstToUpperCase());
			
			if (values != null) {
				values.add(match.getRoleBindings().get(componentName));
			}
		}
	}
	
	public void setPatterns(Collection<Pattern> patterns) {
		StringOperationContributor stringOps = new StringOperationContributor();
		for (Pattern pattern : patterns) {
			String patternName = pattern.getName();
			matchMap.put(patternName, new ArrayList<>());
			for (Role role : pattern.getRoles()) {
				if (role.isNegative()) continue;
				for (String roleName : role.getNames()) {
					stringOps.setTarget(roleName);
					String componentName = patternName + stringOps.firstToUpperCase();
					componentMap.put(componentName, new HashSet<>());
				}
			}
		}
	}
	
	@Override
	public void load() throws EolModelLoadingException {

	}

	@Override
	public Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException {
		return null;
	}

	@Override
	public Collection<?> allContents() {
		return getMatches();
	}
	
	public Set<PatternMatch> getMatches() {
		return matches;
	}
	
	@Override
	public Collection<?> getAllOfType(String type) throws EolModelElementTypeNotFoundException {
		return matchMap.containsKey(type) ? matchMap.get(type) : componentMap.get(type);
	}

	@Override
	public Collection<?> getAllOfKind(String type) throws EolModelElementTypeNotFoundException {
		return getAllOfType(type);
	}

	@Override
	public Object getTypeOf(Object instance) {
		assert instance instanceof PatternMatch;
		return PatternMatch.class;
	}

	@Override
	public String getTypeNameOf(Object instance) {
		if (instance instanceof PatternMatch) {
			return ((PatternMatch) instance).getPattern().getName();
		}
		return null;
	}

	@Override
	public Object createInstance(String type) throws
			EolModelElementTypeNotFoundException,
			EolNotInstantiableModelElementTypeException {
		throw new EolNotInstantiableModelElementTypeException(this.name, type);
	}

	@Override
	public Object getElementById(String id) {
		return null;
	}

	@Override
	public String getElementId(Object instance) {
		return null;
	}

	@Override
	public void setElementId(Object instance, String newId) {
		
	}

	@Override
	public void deleteElement(Object instance) throws EolRuntimeException {
		
	}

	@Override
	public boolean owns(Object instance) {
		return matches.contains(instance);
	}

	@Override
	public boolean isInstantiable(String type) {
		return false;
	}

	@Override
	public boolean isModelElement(Object instance) {
		return owns(instance);
	}

	@Override
	public boolean hasType(String type) {
		return matchMap.containsKey(type) || componentMap.containsKey(type);
	}

	@Override
	public boolean store(String location) {
		return false;
	}

	@Override
	public boolean store() {
		return false;
	}
	
	@Override
	public IPropertyGetter getPropertyGetter() {
		return propertyGetter;
	}
	
	@Override
	public IPropertySetter getPropertySetter() {
		return propertySetter;
	}
	
	@Override
	public String toString() {
		return matches.toString();
	}
}
