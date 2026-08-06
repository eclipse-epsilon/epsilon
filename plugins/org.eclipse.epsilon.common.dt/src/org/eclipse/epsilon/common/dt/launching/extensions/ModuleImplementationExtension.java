/*******************************************************************************
 * Copyright (c) 2018 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.launching.extensions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.epsilon.common.dt.exceptions.EpsilonDtException;
import org.eclipse.epsilon.common.dt.launching.tabs.ModuleConfiguration;
import org.eclipse.epsilon.common.module.IModule;

/**
 * The Module Implementation Extension stores configuration information about existing module
 * implementations.
 *
 * @author Horacio Hoyos Rodriguez
 */
public class ModuleImplementationExtension {
	
	/**
	 * Get the ModuleImplementationExtension for a specific implementation. ModuleImplementationExtensions
	 * created via this method also provide
	 * @param implName the name of the implementation
	 * @return the ModuleImplementationExtension or null if not found in the extension registry
	 */
	public static ModuleImplementationExtension forImplementation(String languageName, String implName) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = registry.getExtensionPoint("org.eclipse.epsilon.common.dt.moduleImplementation");
		IConfigurationElement[] configurationElements =  extensionPoint.getConfigurationElements();
		for (IConfigurationElement configurationElement : configurationElements) {
			if (configurationElement.getAttribute("language").equalsIgnoreCase(languageName) 
					&& configurationElement.getAttribute("name").equals(implName)) {	
				return new ModuleImplementationExtension(configurationElement);
			}
		}
		return null;
	}
	
	/**
	 * Default implementation.
	 * @param languageName 
	 *
	 * @return the module implementation extension
	 */
	public static ModuleImplementationExtension defaultImplementation(String languageName) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = registry.getExtensionPoint("org.eclipse.epsilon.common.dt.moduleImplementation");
		IConfigurationElement[] configurationElements =  extensionPoint.getConfigurationElements();
		for (IConfigurationElement configurationElement : configurationElements){
			if (configurationElement.getAttribute("language").equals(languageName) && Boolean.parseBoolean(configurationElement.getAttribute("default"))) {	
				ModuleImplementationExtension moduleType = null;
				moduleType = new ModuleImplementationExtension(configurationElement);
				return moduleType;
			}
		}
		return null;
	}
	
	
	/** The qualified name of the class that implements the module. */
	private final String clazz;
	
	/** The name of the module. */
	private final String name;
	
	/** The language that the module supports. */
	private final String language;
	
	/** If the implementation is the default one	*/
	private final boolean default_;
	
	/** The configuration element for this extension. */
	private final IConfigurationElement configurationElement;
	
	/**
	 * Instantiates a new module implementation extension.
	 *
	 * @param configurationElement the configuration element
	 */
	public ModuleImplementationExtension(IConfigurationElement configurationElement) {
		this.clazz = configurationElement.getAttribute("class");
		this.name = configurationElement.getAttribute("name");
		this.language = configurationElement.getAttribute("language");
		configurationElement.getAttribute("dialog");
		this.default_ = Boolean.valueOf(configurationElement.getAttribute("default"));
		this.configurationElement = configurationElement;
	}
	
	/**
	 * Gets the clazz.
	 *
	 * @return the clazz
	 */
	public String getClazz() {
		return clazz;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	
	
	public boolean isDefault() {
		return default_;
	}

	/**
	 * Creates the dialog.
	 *
	 * @param <T> the generic type
	 * @return the t
	 * @throws EpsilonDtException the epsilon dt exception
	 */
	@SuppressWarnings("unchecked")
	public <T extends ModuleConfiguration> T createDialog() throws EpsilonDtException {
		try {
			return (T) configurationElement.createExecutableExtension("dialog");
		} catch (CoreException e) {
			throw new EpsilonDtException("Error creating configuraiton dialog for " + this.name, e);
		}
	}
	
	/**
	 * Creates the module.
	 *
	 * @param <T> the generic type
	 * @return the t
	 * @throws CoreException the core exception
	 */
	@SuppressWarnings("unchecked")
	public <T extends IModule> T createModule() throws CoreException {
		return (T) configurationElement.createExecutableExtension("class");
	}

	
 }
