/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id: ModelWithEolAssertions.java,v 1.4 2008/08/12 08:58:18 louis Exp $
 */
package org.eclipse.epsilon.test.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.epsilon.emc.emf.AbstractEmfModel;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.exceptions.EolEvaluatorException;
import org.eclipse.epsilon.eol.models.CachedModel;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.EolEvaluator;

public class ModelWithEolAssertions {
	
	private final IModel model;
	private final EolEvaluator evaluator;
	
	private static Resource createResourceFor(EObject eObject) {
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());
		final Resource resource = resourceSet.createResource(URI.createFileURI("debug.model"));
		resource.getContents().add(eObject);
		return resource;
	}
	
	public ModelWithEolAssertions(EPackage ePackage, EObject... eObjects) {
		this(new InMemoryEmfModel("Model", createResourceFor(eObjects[0]), ePackage));
		
		if (eObjects.length > 1) {
			for (int index = 1; index < eObjects.length; index++) {
				add(eObjects[index]);
			}
		}
	}
	
	public ModelWithEolAssertions(EObject eObject, EPackage... ePackages) {
		this(new InMemoryEmfModel("Model", createResourceFor(eObject), ePackages));
	}
	
	public ModelWithEolAssertions(IModel model) {
		this.model = model;
		evaluator  = new EolEvaluator(model);
		
		// As add can introduce new EObjects later,
		// don't cache the model's contents
		if (this.model instanceof CachedModel<?>) {
			((CachedModel<?>)this.model).setCachingEnabled(false);
		}
	}
	
	public void importEol(File file) {
		evaluator.importFile(file);
	}
	
	public void add(EObject... eObjects) {
		if (model instanceof AbstractEmfModel) {
			((AbstractEmfModel) model).getResource().getContents().addAll(Arrays.asList(eObjects));
		}
	}
	
	public void dispose() {
		model.dispose();
	}
	
	public void assertEmpty() {
		assertNumberOfModelElementsIs(0);
	}
	

	public void assertNumberOfModelElementsIs(int expected) {
		int actual = (int) evaluator.evaluate(model.getName() + ".allInstances.size()");
		org.junit.Assert.assertEquals(expected, actual);
	}
	
	public void assertEquals(Object expected, String actual) {
		assertEquals(null, expected, actual);
	}
	
	public void assertEquals(String message, Object expected, String actual) {
		final Object expectedResult = evaluator.evaluate(convert(expected));
		final Object actualResult   = evaluator.evaluate(actual);
		
		if (message == null)
			org.junit.Assert.assertEquals(expectedResult, actualResult);
		else
			org.junit.Assert.assertEquals(message, expectedResult, actualResult);
	
	}

	private Object convert(Object expected) {
		if (expected instanceof String) {
			return convertString((String) expected);
		}
		else if (expected instanceof Collection<?>) {
			return convertCollection((Collection<?>) expected);
		}
 		return expected;
	}

	private String convertString(String expected) {
		// When the expected string contains a hash symbol assume that
		// it is an enumeration literal, so don't wrap it in quotes
		// Unless the expected string *starts* with a hash, then it's a
		// positive adjective, so do wrap it in quotes
		if (expected.startsWith("#") || !expected.contains("#")) {
			expected = "'" + escapeQuotes(expected) + "'";
		}
		
		return expected;
	}
	
	private static String escapeQuotes(String unescaped) {
		return unescaped.replaceAll("'", "\\\\'");
	}

	private Collection<?> convertCollection(Collection<?> expected) {
		final Collection<Object> converted  = new ArrayList<>(expected.size());
		
		for (Object element : expected) {
			converted.add(convert(element));
		}
		
		return converted;
	}
	
	public void assertTrue(String eolStatement) {
		assertTrue(null, eolStatement);
	}
	
	public void assertTrue(String message, String eolStatement) {
		final Object result = evaluator.evaluate(eolStatement);
		
		if (result instanceof Boolean)
			if (message == null)
				org.junit.Assert.assertTrue((boolean) result);
			else
				org.junit.Assert.assertTrue(message, (boolean) result);
		else
			throw new IllegalArgumentException("Could not determine a boolean value for '" + eolStatement + "'");
	}
	
	public void assertFalse(String eolStatement) {
		assertFalse(null, eolStatement);
	}
	
	public void assertFalse(String message, String eolStatement) {
		assertTrue(null, "not " + eolStatement);
	}
	
	public void assertDefined(String eolStatement) {
		assertDefined(null, eolStatement);
	}
	
	public void assertDefined(String message, String eolStatement) {	
		final Object result = evaluator.evaluate(eolStatement);
		
		if (message == null)
			org.junit.Assert.assertNotNull(result);
		else
			org.junit.Assert.assertNotNull(message, result);
	
	}
	
	public void assertUndefined(String eolStatement) {
		assertUndefined(null, eolStatement);
	}
	
	public void assertUndefined(String message, String eolStatement) {	
		final Object result = evaluator.evaluate(eolStatement);
		
		if (message == null)
			org.junit.Assert.assertNull(result);
		else
			org.junit.Assert.assertNull(message, result);
	}
	
	public void assertInstanceOf(String expectedType, String actual) {
		assertTrue(expectedType + ".isKind(" + actual + ")");
	}
	
	public Object evaluate(String eolStatement) throws Throwable {
		try {
			return evaluator.evaluate(eolStatement);
		}
		catch (EolEvaluatorException e) {
			throwCauseOf(e);
			return null; // Never reached
		}
	}
	
	public void execute(String statement) throws Throwable {
		try {
			evaluator.execute(statement);
		}
		catch (EolEvaluatorException e) {
			throwCauseOf(e);
		}
	}

	private void throwCauseOf(EolEvaluatorException e) throws Throwable {
		if (e.getCause() != null) {
			throw e.getCause();
		}
		else {
			throw e;
		}
	}
	
	public void setVariable(String name, String eolStatement) {
		evaluator.setVariable(name, eolStatement);
	}
	
	public void addAndSetVariable(String name, EObject value) {
		add(value);
		evaluator.setVariable(name, value);
	}
	
	public void store(String path) {
		if (model instanceof AbstractEmfModel) {
			((AbstractEmfModel) model).getResource().setURI(URI.createFileURI(path));
			model.store();
		}
		else {
			model.store(path);
		}
	}
	
	public Resource getResource() {
		if (model instanceof AbstractEmfModel)
			return ((AbstractEmfModel) model).getResource();
		else
			return null;
	}
}
