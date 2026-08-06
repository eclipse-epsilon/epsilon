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
 * $Id: HutnConfigPackage.java,v 1.2 2008/07/30 11:13:13 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.config.hutnConfig;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.HutnConfigFactory
 * @model kind="package"
 * @generated
 */
public interface HutnConfigPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "hutnConfig";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/gmt/epsilon/hutnConfig";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "hutnConfig";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	HutnConfigPackage eINSTANCE = org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.HutnConfigPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.ConfigurationImpl <em>Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.ConfigurationImpl
	 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.HutnConfigPackageImpl#getConfiguration()
	 * @generated
	 */
	int CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__RULES = 0;

	/**
	 * The number of structural features of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.RuleImpl <em>Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.RuleImpl
	 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.HutnConfigPackageImpl#getRule()
	 * @generated
	 */
	int RULE = 1;

	/**
	 * The feature id for the '<em><b>Classifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__CLASSIFIER = 0;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__ATTRIBUTE = 1;

	/**
	 * The number of structural features of the '<em>Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.IdentifierRuleImpl <em>Identifier Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.IdentifierRuleImpl
	 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.HutnConfigPackageImpl#getIdentifierRule()
	 * @generated
	 */
	int IDENTIFIER_RULE = 2;

	/**
	 * The feature id for the '<em><b>Classifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_RULE__CLASSIFIER = RULE__CLASSIFIER;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_RULE__ATTRIBUTE = RULE__ATTRIBUTE;

	/**
	 * The number of structural features of the '<em>Identifier Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_RULE_FEATURE_COUNT = RULE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.DefaultValueRuleImpl <em>Default Value Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.DefaultValueRuleImpl
	 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.HutnConfigPackageImpl#getDefaultValueRule()
	 * @generated
	 */
	int DEFAULT_VALUE_RULE = 3;

	/**
	 * The feature id for the '<em><b>Classifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFAULT_VALUE_RULE__CLASSIFIER = RULE__CLASSIFIER;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFAULT_VALUE_RULE__ATTRIBUTE = RULE__ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFAULT_VALUE_RULE__DEFAULT_VALUE = RULE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Default Value Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFAULT_VALUE_RULE_FEATURE_COUNT = RULE_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.config.hutnConfig.Configuration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration</em>'.
	 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.Configuration
	 * @generated
	 */
	EClass getConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.hutn.model.config.hutnConfig.Configuration#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.Configuration#getRules()
	 * @see #getConfiguration()
	 * @generated
	 */
	EReference getConfiguration_Rules();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.config.hutnConfig.Rule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule</em>'.
	 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.Rule
	 * @generated
	 */
	EClass getRule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.model.config.hutnConfig.Rule#getClassifier <em>Classifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Classifier</em>'.
	 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.Rule#getClassifier()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_Classifier();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.model.config.hutnConfig.Rule#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attribute</em>'.
	 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.Rule#getAttribute()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_Attribute();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.config.hutnConfig.IdentifierRule <em>Identifier Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identifier Rule</em>'.
	 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.IdentifierRule
	 * @generated
	 */
	EClass getIdentifierRule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.config.hutnConfig.DefaultValueRule <em>Default Value Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Default Value Rule</em>'.
	 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.DefaultValueRule
	 * @generated
	 */
	EClass getDefaultValueRule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.model.config.hutnConfig.DefaultValueRule#getDefaultValue <em>Default Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Value</em>'.
	 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.DefaultValueRule#getDefaultValue()
	 * @see #getDefaultValueRule()
	 * @generated
	 */
	EAttribute getDefaultValueRule_DefaultValue();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	HutnConfigFactory getHutnConfigFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.ConfigurationImpl <em>Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.ConfigurationImpl
		 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.HutnConfigPackageImpl#getConfiguration()
		 * @generated
		 */
		EClass CONFIGURATION = eINSTANCE.getConfiguration();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION__RULES = eINSTANCE.getConfiguration_Rules();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.RuleImpl <em>Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.RuleImpl
		 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.HutnConfigPackageImpl#getRule()
		 * @generated
		 */
		EClass RULE = eINSTANCE.getRule();

		/**
		 * The meta object literal for the '<em><b>Classifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__CLASSIFIER = eINSTANCE.getRule_Classifier();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__ATTRIBUTE = eINSTANCE.getRule_Attribute();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.IdentifierRuleImpl <em>Identifier Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.IdentifierRuleImpl
		 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.HutnConfigPackageImpl#getIdentifierRule()
		 * @generated
		 */
		EClass IDENTIFIER_RULE = eINSTANCE.getIdentifierRule();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.DefaultValueRuleImpl <em>Default Value Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.DefaultValueRuleImpl
		 * @see org.eclipse.epsilon.hutn.model.config.hutnConfig.impl.HutnConfigPackageImpl#getDefaultValueRule()
		 * @generated
		 */
		EClass DEFAULT_VALUE_RULE = eINSTANCE.getDefaultValueRule();

		/**
		 * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFAULT_VALUE_RULE__DEFAULT_VALUE = eINSTANCE.getDefaultValueRule_DefaultValue();

	}

} //HutnConfigPackage
