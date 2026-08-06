/*******************************************************************************
 * Copyright (c) 2022 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotAnEnumerationValueException;
import org.eclipse.epsilon.eol.execute.introspection.IReflectivePropertySetter;
import org.eclipse.epsilon.eol.models.IReflectiveModel;

public abstract class AbstractReflectiveEmfModel extends AbstractEmfModel implements IReflectiveModel {

	@Override
	public IReflectivePropertySetter getPropertySetter() {
		return (IReflectivePropertySetter) propertySetter;
	}

	@Override
	public Collection<String> getPropertiesOf(String type) throws EolModelElementTypeNotFoundException {
		Collection<EStructuralFeature> features = featuresForType(type);
		final Collection<String> properties = new ArrayList<>(features.size());
		for (EStructuralFeature feature : features) {
			properties.add(feature.getName());
		}
		return properties;
	}

	@Override
	public boolean preventLoadingOfExternalModelElements() {
		if (isExpand()) {
			setExpand(false);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasProperty(String type, String property) throws EolModelElementTypeNotFoundException {
		return getPropertiesOf(type).contains(property);
	}

	@Override
	public boolean isEnumerationValue(Object object) {
		return object instanceof Enumerator;
	}

	@Override
	public String getEnumerationTypeOf(Object literal) throws EolNotAnEnumerationValueException {
		if (!isEnumerationValue(literal))
			throw new EolNotAnEnumerationValueException(literal);
		
		if (literal instanceof EEnumLiteral) {
			return ((EEnumLiteral)literal).getEEnum().getName();
		} else {
			return ((Enumerator)literal).getClass().getSimpleName();
		}		
	}

	@Override
	public String getEnumerationLabelOf(Object literal) throws EolNotAnEnumerationValueException {
		if (!isEnumerationValue(literal))
			throw new EolNotAnEnumerationValueException(literal);
			
		return ((Enumerator)literal).getName();
	}

	@Override
	public boolean hasPackage(String packageName) {
		return packageForName(packageName) != null;
	}

	private EPackage packageForName(String name) {
		final String[] parts = name.split("::");
		
		int partIndex = 0;
		EPackage current;
		Collection<EPackage> next = getTopLevelPackages();
		
		do {
			if ((current = packageForName(parts[partIndex++], next)) != null) {
				next = current.getESubpackages();
			}
		}
		while (current != null && partIndex < parts.length);
		
		return current;
	}


	private Collection<EPackage> getTopLevelPackages() {
		return getPackageRegistry().values()
			.stream()
			.filter(pkg -> pkg instanceof EPackage)
			.map(pkg -> (EPackage) pkg)
			.collect(Collectors.toCollection(LinkedList::new));
	}
	
	private static EPackage packageForName(String name, Collection<EPackage> packages) {
		return packages.stream()
			.filter(pkg -> name.equals(pkg.getName()))
			.findAny()
			.orElse(null);
	}

	private EList<EStructuralFeature> featuresForType(String type) throws EolModelElementTypeNotFoundException {
		return classForName(type).getEAllStructuralFeatures();
	}

}