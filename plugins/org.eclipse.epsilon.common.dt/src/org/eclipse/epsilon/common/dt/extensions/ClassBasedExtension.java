/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.extensions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;

/**
 * Class which looks up and creates the instances of the classes referred to
 * through the specified extension point. The extension point must declare an
 * attribute of type "java" and name {@link #CLASS_PROPERTY}. Referenced
 * classes should implement a certain interface or inherit from a certain
 * class.
 *
 * @author Antonio García-Domínguez
 * @version 1.0
 */
public class ClassBasedExtension {

	public static final String CLASS_PROPERTY = "class";
	private final IConfigurationElement configElement;
	private final Class<?> expectedClass;

	/**
	 * Creates a new instance.
	 *
	 * @param configElement Configuration element from the extension element.
	 * @param expectedClass Superclass or interface to which the referenced class should comply.
	 */
	public ClassBasedExtension(IConfigurationElement configElement, Class<?> expectedClass) {
		this.configElement = configElement;
		this.expectedClass = expectedClass;
	}

	/**
	 * Returns a list with the available extensions for this extension point.
	 */
	public static List<ClassBasedExtension> getInstances(String extensionPointID, Class<?> expectedClass) {
		List<ClassBasedExtension> instances = new ArrayList<>();

		IExtensionRegistry registry = RegistryFactory.getRegistry();
		if (registry == null) {
			// We're running the task inside a JUnit test
			return instances;
		}

		IExtensionPoint point = registry.getExtensionPoint(extensionPointID);
		IConfigurationElement[] configElements = point.getConfigurationElements();

		for (IConfigurationElement configElement : configElements) {
			ClassBasedExtension ext = new ClassBasedExtension(configElement, expectedClass);
			instances.add(ext);
		}

		return instances;
	}

	/**
	 * Returns a list with instances of the classes registered in the available extensions for this extension point.
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getImplementations(String extensionPointID, Class<T> expectedClass)
		throws IllegalExtensionException
	{
		List<T> listeners = new ArrayList<>();
		for (ClassBasedExtension ext : getInstances(extensionPointID, expectedClass)) {
			listeners.add((T)ext.createInstance());
		}
		return listeners;
	}

	/**
	 * Returns the fully qualified class name of the referenced class, as set in the extension.
	 */
	public String getClassName() {
		return configElement.getAttribute(CLASS_PROPERTY);
	}

	/**
	 * Instantiates the referenced class.
	 */
	public Object createInstance() throws IllegalExtensionException {
		try {
			Object instance = configElement.createExecutableExtension(CLASS_PROPERTY);
			if (expectedClass.isInstance(instance)) {
				return instance;
			}
			else {
				throw new IllegalExtensionException(
						getClassName()
								+ " was reported to be a " + expectedClass.getCanonicalName()
								+ ", but it was a " + instance.getClass().getCanonicalName());
			}
		} catch (Exception ex) {
			throw new IllegalExtensionException(ex);
		}
	}
}
