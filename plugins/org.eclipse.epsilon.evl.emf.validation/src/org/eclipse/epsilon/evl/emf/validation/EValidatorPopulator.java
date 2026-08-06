/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * Copyright (c) 2014 University of Twente.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Maarten Bezemer - finished indicator, extension point additions
 ******************************************************************************/
package org.eclipse.epsilon.evl.emf.validation;

import java.net.URL;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.ui.IStartup;

public class EValidatorPopulator implements IStartup {
	
	protected final String extensionPoint = "org.eclipse.epsilon.evl.emf.validation";

	/** When true the populator is finished and the validation is ready to use */
	static private boolean isFinished = false;

	/** @return true if the populator is finished initializing the validators */
	public static boolean isFinished() {
		return isFinished;
	}

	@Override
	public void earlyStartup() {
		
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extenstionPoint = registry.getExtensionPoint(extensionPoint);
		
		for (IConfigurationElement configurationElement : extenstionPoint.getConfigurationElements()) {
			
			try {
				String ePackageUri = configurationElement.getAttribute("namespaceURI");
				EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(ePackageUri);
				if (ePackage == null) continue;
				
				String bundleId = configurationElement.getAttribute("bundleId");
				
				if (bundleId == null || bundleId.trim().length() == 0) {
					bundleId = configurationElement.getDeclaringExtension().getNamespaceIdentifier();
				}
				
				URL url = Platform.getBundle(bundleId).getResource(configurationElement.getAttribute("constraints"));
				
				if (url == null) {
					LogUtil.log("Constraints file " + 
						configurationElement.getAttribute("constraints") + 
						" not found in bundle " + bundleId, 
						new Exception());
					continue;
				}
				
				EValidator evlValidator = null;
				if (url.toString().endsWith("evl")) {
					String modelName = configurationElement.getAttribute("modelName");
					if (StringUtil.isEmpty(modelName)) {
						modelName = EvlValidator.DEFAULT_MODEL_NAME;
					}

					// Use custom EvlValidator if provided: otherwise, use the default implementation
					if(configurationElement.getAttribute("validator") != null) {
						evlValidator = (EValidator) configurationElement.createExecutableExtension("validator");
						((EvlValidator) evlValidator).initialise(url.toURI(), modelName, ePackageUri, bundleId);
					}
					else {
						evlValidator = new EvlValidator(url.toURI(), modelName, ePackageUri, bundleId);
					}

					// Add variables for propagating EMF Diagnostician context entries
					IConfigurationElement[] diagnosticVariables = configurationElement.getChildren("diagnosticVariable");
					for (IConfigurationElement diagnosticVariable : diagnosticVariables) {
						((EvlValidator) evlValidator).addDiagnosticianVariable(diagnosticVariable.getAttribute("name"));
					}

					// Add additional packages, so their model elements can be
					// used by this validator
					IConfigurationElement[] additionalPackagesUris = configurationElement
							.getChildren("additionalNamespaceURI");
					for (IConfigurationElement additionalPackageUri : additionalPackagesUris) {
						((EvlValidator) evlValidator)
								.addAdditionalPackage(additionalPackageUri
										.getAttribute("namespaceURI"));
					}
				}
				else {
					evlValidator = new OclValidator(url.toURI());
				}
				
				EValidator newValidator = null;
				EValidator existingValidator = EValidator.Registry.INSTANCE.getEValidator(ePackage);
				
				String composeAttributeValue = configurationElement.getAttribute("compose");
				boolean compose = composeAttributeValue == null || Boolean.valueOf(composeAttributeValue).booleanValue();
				
				if (compose) {
					if (existingValidator == null) {
						existingValidator = EObjectValidator.INSTANCE;
					}
					
					if (existingValidator instanceof CompositeEValidator) {
						((CompositeEValidator) existingValidator).getDelegates().add(evlValidator);
						newValidator = existingValidator;
					}
					else {
						newValidator = new CompositeEValidator();
						((CompositeEValidator) newValidator).getDelegates().add(existingValidator);
						((CompositeEValidator) newValidator).getDelegates().add(evlValidator);
					}
				}
				else {
					newValidator = evlValidator;
				}
				
				if (newValidator != existingValidator) {
					EValidator.Registry.INSTANCE.put(ePackage, newValidator);
					// Fixes 369383
					IConfigurationElement[] additionalPackagesUris = configurationElement
							.getChildren("additionalNamespaceURI");
					for (IConfigurationElement additionalPackageUri : additionalPackagesUris) {
						EPackage addEPackage = EPackage.Registry.INSTANCE.getEPackage(
								additionalPackageUri.getAttribute("namespaceURI"));
						if (ePackage != null) {
							EValidator.Registry.INSTANCE.put(addEPackage, newValidator);
						}
						
					}
				}
				
			} catch (Exception e) {
				LogUtil.log(e);
			}
		}

		// Mark populator as finished, so clients can see whether they can use validation
		isFinished = true;
	}
}
