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
 * $Id: HutnConfigFactoryImpl.java,v 1.2 2008/07/30 11:13:12 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.config.hutnConfig.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.epsilon.hutn.model.config.hutnConfig.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HutnConfigFactoryImpl extends EFactoryImpl implements HutnConfigFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static HutnConfigFactory init() {
		try {
			HutnConfigFactory theHutnConfigFactory = (HutnConfigFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.eclipse.org/gmt/epsilon/hutnConfig"); 
			if (theHutnConfigFactory != null) {
				return theHutnConfigFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new HutnConfigFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HutnConfigFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case HutnConfigPackage.CONFIGURATION: return createConfiguration();
			case HutnConfigPackage.IDENTIFIER_RULE: return createIdentifierRule();
			case HutnConfigPackage.DEFAULT_VALUE_RULE: return createDefaultValueRule();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Configuration createConfiguration() {
		ConfigurationImpl configuration = new ConfigurationImpl();
		return configuration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IdentifierRule createIdentifierRule() {
		IdentifierRuleImpl identifierRule = new IdentifierRuleImpl();
		return identifierRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DefaultValueRule createDefaultValueRule() {
		DefaultValueRuleImpl defaultValueRule = new DefaultValueRuleImpl();
		return defaultValueRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HutnConfigPackage getHutnConfigPackage() {
		return (HutnConfigPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static HutnConfigPackage getPackage() {
		return HutnConfigPackage.eINSTANCE;
	}

} //HutnConfigFactoryImpl
