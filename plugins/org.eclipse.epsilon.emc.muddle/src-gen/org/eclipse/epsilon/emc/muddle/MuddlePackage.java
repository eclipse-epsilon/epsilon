/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 ******************************************************************************/
/**
 */
package org.eclipse.epsilon.emc.muddle;

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
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.epsilon.emc.muddle.MuddleFactory
 * @model kind="package"
 *        annotation="emf.gen basePackage='org.eclipse.epsilon.emc' modelDirectory='/org.eclipse.epsilon.emc.muddle/src-gen'"
 * @generated
 */
public interface MuddlePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "muddle";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/epsilon/emc/muddle";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MuddlePackage eINSTANCE = org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.muddle.impl.MuddleImpl <em>Muddle</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddleImpl
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getMuddle()
	 * @generated
	 */
	int MUDDLE = 0;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE__TYPES = 0;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE__ELEMENTS = 1;

	/**
	 * The number of structural features of the '<em>Muddle</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Muddle</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddleElementImpl
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getMuddleElement()
	 * @generated
	 */
	int MUDDLE_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT__ID = 0;

	/**
	 * The feature id for the '<em><b>Slots</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT__SLOTS = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT__TYPE = 2;

	/**
	 * The feature id for the '<em><b>Muddle</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT__MUDDLE = 3;

	/**
	 * The feature id for the '<em><b>Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT__STYLE = 4;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementStyleImpl <em>Element Style</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddleElementStyleImpl
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getMuddleElementStyle()
	 * @generated
	 */
	int MUDDLE_ELEMENT_STYLE = 2;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_STYLE__COLOR = 0;

	/**
	 * The feature id for the '<em><b>Shape</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_STYLE__SHAPE = 1;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_STYLE__WIDTH = 2;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_STYLE__HEIGHT = 3;

	/**
	 * The feature id for the '<em><b>Border Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_STYLE__BORDER_WIDTH = 4;

	/**
	 * The feature id for the '<em><b>Label Font Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_STYLE__LABEL_FONT_SIZE = 5;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_STYLE__X = 6;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_STYLE__Y = 7;

	/**
	 * The number of structural features of the '<em>Element Style</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_STYLE_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>Element Style</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_STYLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.muddle.impl.SlotImpl <em>Slot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.muddle.impl.SlotImpl
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getSlot()
	 * @generated
	 */
	int SLOT = 3;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT__VALUES = 0;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT__FEATURE = 1;

	/**
	 * The feature id for the '<em><b>Owning Element</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT__OWNING_ELEMENT = 2;

	/**
	 * The number of structural features of the '<em>Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.muddle.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.muddle.impl.TypeImpl
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getType()
	 * @generated
	 */
	int TYPE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.muddle.impl.FeatureImpl <em>Feature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.muddle.impl.FeatureImpl
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getFeature()
	 * @generated
	 */
	int FEATURE = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Many</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__MANY = 1;

	/**
	 * The feature id for the '<em><b>Primary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__PRIMARY = 2;

	/**
	 * The feature id for the '<em><b>Runtime</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__RUNTIME = 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__TYPE = 4;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__OWNING_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Slots</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE__SLOTS = 6;

	/**
	 * The number of structural features of the '<em>Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementTypeImpl <em>Element Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddleElementTypeImpl
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getMuddleElementType()
	 * @generated
	 */
	int MUDDLE_ELEMENT_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_TYPE__NAME = TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Instances</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_TYPE__INSTANCES = TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Features</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_TYPE__FEATURES = TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Super Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_TYPE__SUPER_TYPES = TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Sub Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_TYPE__SUB_TYPES = TYPE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Element Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Element Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MUDDLE_ELEMENT_TYPE_OPERATION_COUNT = TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.muddle.impl.LinkElementTypeImpl <em>Link Element Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.muddle.impl.LinkElementTypeImpl
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getLinkElementType()
	 * @generated
	 */
	int LINK_ELEMENT_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ELEMENT_TYPE__NAME = MUDDLE_ELEMENT_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Instances</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ELEMENT_TYPE__INSTANCES = MUDDLE_ELEMENT_TYPE__INSTANCES;

	/**
	 * The feature id for the '<em><b>Features</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ELEMENT_TYPE__FEATURES = MUDDLE_ELEMENT_TYPE__FEATURES;

	/**
	 * The feature id for the '<em><b>Super Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ELEMENT_TYPE__SUPER_TYPES = MUDDLE_ELEMENT_TYPE__SUPER_TYPES;

	/**
	 * The feature id for the '<em><b>Sub Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ELEMENT_TYPE__SUB_TYPES = MUDDLE_ELEMENT_TYPE__SUB_TYPES;

	/**
	 * The feature id for the '<em><b>Source Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ELEMENT_TYPE__SOURCE_FEATURE = MUDDLE_ELEMENT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ELEMENT_TYPE__TARGET_FEATURE = MUDDLE_ELEMENT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Role In Source Feature</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ELEMENT_TYPE__ROLE_IN_SOURCE_FEATURE = MUDDLE_ELEMENT_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Role In Target Feature</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ELEMENT_TYPE__ROLE_IN_TARGET_FEATURE = MUDDLE_ELEMENT_TYPE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Link Element Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ELEMENT_TYPE_FEATURE_COUNT = MUDDLE_ELEMENT_TYPE_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Link Element Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_ELEMENT_TYPE_OPERATION_COUNT = MUDDLE_ELEMENT_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.muddle.impl.PrimitiveTypeImpl <em>Primitive Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.muddle.impl.PrimitiveTypeImpl
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getPrimitiveType()
	 * @generated
	 */
	int PRIMITIVE_TYPE = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_TYPE__NAME = TYPE__NAME;

	/**
	 * The number of structural features of the '<em>Primitive Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Primitive Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_TYPE_OPERATION_COUNT = TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.muddle.impl.IntegerTypeImpl <em>Integer Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.muddle.impl.IntegerTypeImpl
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getIntegerType()
	 * @generated
	 */
	int INTEGER_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE__NAME = PRIMITIVE_TYPE__NAME;

	/**
	 * The number of structural features of the '<em>Integer Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE_FEATURE_COUNT = PRIMITIVE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Integer Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE_OPERATION_COUNT = PRIMITIVE_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.muddle.impl.StringTypeImpl <em>String Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.muddle.impl.StringTypeImpl
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getStringType()
	 * @generated
	 */
	int STRING_TYPE = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__NAME = PRIMITIVE_TYPE__NAME;

	/**
	 * The number of structural features of the '<em>String Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE_FEATURE_COUNT = PRIMITIVE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>String Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE_OPERATION_COUNT = PRIMITIVE_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.muddle.impl.BooleanTypeImpl <em>Boolean Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.muddle.impl.BooleanTypeImpl
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getBooleanType()
	 * @generated
	 */
	int BOOLEAN_TYPE = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TYPE__NAME = PRIMITIVE_TYPE__NAME;

	/**
	 * The number of structural features of the '<em>Boolean Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TYPE_FEATURE_COUNT = PRIMITIVE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Boolean Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TYPE_OPERATION_COUNT = PRIMITIVE_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.muddle.impl.RealTypeImpl <em>Real Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.muddle.impl.RealTypeImpl
	 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getRealType()
	 * @generated
	 */
	int REAL_TYPE = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_TYPE__NAME = PRIMITIVE_TYPE__NAME;

	/**
	 * The number of structural features of the '<em>Real Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_TYPE_FEATURE_COUNT = PRIMITIVE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Real Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_TYPE_OPERATION_COUNT = PRIMITIVE_TYPE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.muddle.Muddle <em>Muddle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Muddle</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Muddle
	 * @generated
	 */
	EClass getMuddle();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.emc.muddle.Muddle#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Muddle#getTypes()
	 * @see #getMuddle()
	 * @generated
	 */
	EReference getMuddle_Types();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.emc.muddle.Muddle#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Muddle#getElements()
	 * @see #getMuddle()
	 * @generated
	 */
	EReference getMuddle_Elements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.muddle.MuddleElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElement
	 * @generated
	 */
	EClass getMuddleElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.emc.muddle.MuddleElement#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElement#getId()
	 * @see #getMuddleElement()
	 * @generated
	 */
	EAttribute getMuddleElement_Id();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.emc.muddle.MuddleElement#getSlots <em>Slots</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Slots</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElement#getSlots()
	 * @see #getMuddleElement()
	 * @generated
	 */
	EReference getMuddleElement_Slots();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.epsilon.emc.muddle.MuddleElement#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElement#getType()
	 * @see #getMuddleElement()
	 * @generated
	 */
	EReference getMuddleElement_Type();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.epsilon.emc.muddle.MuddleElement#getMuddle <em>Muddle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Muddle</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElement#getMuddle()
	 * @see #getMuddleElement()
	 * @generated
	 */
	EReference getMuddleElement_Muddle();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.epsilon.emc.muddle.MuddleElement#getStyle <em>Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Style</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElement#getStyle()
	 * @see #getMuddleElement()
	 * @generated
	 */
	EReference getMuddleElement_Style();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle <em>Element Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element Style</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElementStyle
	 * @generated
	 */
	EClass getMuddleElementStyle();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getColor <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Color</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getColor()
	 * @see #getMuddleElementStyle()
	 * @generated
	 */
	EAttribute getMuddleElementStyle_Color();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getShape <em>Shape</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Shape</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getShape()
	 * @see #getMuddleElementStyle()
	 * @generated
	 */
	EAttribute getMuddleElementStyle_Shape();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getWidth()
	 * @see #getMuddleElementStyle()
	 * @generated
	 */
	EAttribute getMuddleElementStyle_Width();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getHeight()
	 * @see #getMuddleElementStyle()
	 * @generated
	 */
	EAttribute getMuddleElementStyle_Height();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getBorderWidth <em>Border Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Border Width</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getBorderWidth()
	 * @see #getMuddleElementStyle()
	 * @generated
	 */
	EAttribute getMuddleElementStyle_BorderWidth();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getLabelFontSize <em>Label Font Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label Font Size</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getLabelFontSize()
	 * @see #getMuddleElementStyle()
	 * @generated
	 */
	EAttribute getMuddleElementStyle_LabelFontSize();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getX()
	 * @see #getMuddleElementStyle()
	 * @generated
	 */
	EAttribute getMuddleElementStyle_X();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getY()
	 * @see #getMuddleElementStyle()
	 * @generated
	 */
	EAttribute getMuddleElementStyle_Y();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.muddle.Slot <em>Slot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Slot</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Slot
	 * @generated
	 */
	EClass getSlot();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.epsilon.emc.muddle.Slot#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Values</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Slot#getValues()
	 * @see #getSlot()
	 * @generated
	 */
	EAttribute getSlot_Values();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.epsilon.emc.muddle.Slot#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Slot#getFeature()
	 * @see #getSlot()
	 * @generated
	 */
	EReference getSlot_Feature();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.epsilon.emc.muddle.Slot#getOwningElement <em>Owning Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owning Element</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Slot#getOwningElement()
	 * @see #getSlot()
	 * @generated
	 */
	EReference getSlot_OwningElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.muddle.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.emc.muddle.Type#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Type#getName()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.muddle.Feature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Feature
	 * @generated
	 */
	EClass getFeature();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.emc.muddle.Feature#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Feature#getName()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.emc.muddle.Feature#isMany <em>Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Many</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Feature#isMany()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Many();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.emc.muddle.Feature#isPrimary <em>Primary</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primary</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Feature#isPrimary()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Primary();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.emc.muddle.Feature#isRuntime <em>Runtime</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Runtime</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Feature#isRuntime()
	 * @see #getFeature()
	 * @generated
	 */
	EAttribute getFeature_Runtime();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.epsilon.emc.muddle.Feature#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Feature#getType()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_Type();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.epsilon.emc.muddle.Feature#getOwningType <em>Owning Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owning Type</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Feature#getOwningType()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_OwningType();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.epsilon.emc.muddle.Feature#getSlots <em>Slots</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Slots</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.Feature#getSlots()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_Slots();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.muddle.MuddleElementType <em>Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element Type</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElementType
	 * @generated
	 */
	EClass getMuddleElementType();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.epsilon.emc.muddle.MuddleElementType#getInstances <em>Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Instances</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElementType#getInstances()
	 * @see #getMuddleElementType()
	 * @generated
	 */
	EReference getMuddleElementType_Instances();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.emc.muddle.MuddleElementType#getFeatures <em>Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Features</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElementType#getFeatures()
	 * @see #getMuddleElementType()
	 * @generated
	 */
	EReference getMuddleElementType_Features();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.epsilon.emc.muddle.MuddleElementType#getSuperTypes <em>Super Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Super Types</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElementType#getSuperTypes()
	 * @see #getMuddleElementType()
	 * @generated
	 */
	EReference getMuddleElementType_SuperTypes();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.epsilon.emc.muddle.MuddleElementType#getSubTypes <em>Sub Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sub Types</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElementType#getSubTypes()
	 * @see #getMuddleElementType()
	 * @generated
	 */
	EReference getMuddleElementType_SubTypes();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.muddle.LinkElementType <em>Link Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link Element Type</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.LinkElementType
	 * @generated
	 */
	EClass getLinkElementType();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.epsilon.emc.muddle.LinkElementType#getSourceFeature <em>Source Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Feature</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.LinkElementType#getSourceFeature()
	 * @see #getLinkElementType()
	 * @generated
	 */
	EReference getLinkElementType_SourceFeature();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.epsilon.emc.muddle.LinkElementType#getTargetFeature <em>Target Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Feature</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.LinkElementType#getTargetFeature()
	 * @see #getLinkElementType()
	 * @generated
	 */
	EReference getLinkElementType_TargetFeature();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.epsilon.emc.muddle.LinkElementType#getRoleInSourceFeature <em>Role In Source Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Role In Source Feature</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.LinkElementType#getRoleInSourceFeature()
	 * @see #getLinkElementType()
	 * @generated
	 */
	EReference getLinkElementType_RoleInSourceFeature();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.epsilon.emc.muddle.LinkElementType#getRoleInTargetFeature <em>Role In Target Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Role In Target Feature</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.LinkElementType#getRoleInTargetFeature()
	 * @see #getLinkElementType()
	 * @generated
	 */
	EReference getLinkElementType_RoleInTargetFeature();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.muddle.PrimitiveType <em>Primitive Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Primitive Type</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.PrimitiveType
	 * @generated
	 */
	EClass getPrimitiveType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.muddle.IntegerType <em>Integer Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Type</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.IntegerType
	 * @generated
	 */
	EClass getIntegerType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.muddle.StringType <em>String Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Type</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.StringType
	 * @generated
	 */
	EClass getStringType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.muddle.BooleanType <em>Boolean Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Type</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.BooleanType
	 * @generated
	 */
	EClass getBooleanType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.muddle.RealType <em>Real Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Real Type</em>'.
	 * @see org.eclipse.epsilon.emc.muddle.RealType
	 * @generated
	 */
	EClass getRealType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MuddleFactory getMuddleFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.muddle.impl.MuddleImpl <em>Muddle</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddleImpl
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getMuddle()
		 * @generated
		 */
		EClass MUDDLE = eINSTANCE.getMuddle();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MUDDLE__TYPES = eINSTANCE.getMuddle_Types();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MUDDLE__ELEMENTS = eINSTANCE.getMuddle_Elements();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddleElementImpl
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getMuddleElement()
		 * @generated
		 */
		EClass MUDDLE_ELEMENT = eINSTANCE.getMuddleElement();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MUDDLE_ELEMENT__ID = eINSTANCE.getMuddleElement_Id();

		/**
		 * The meta object literal for the '<em><b>Slots</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MUDDLE_ELEMENT__SLOTS = eINSTANCE.getMuddleElement_Slots();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MUDDLE_ELEMENT__TYPE = eINSTANCE.getMuddleElement_Type();

		/**
		 * The meta object literal for the '<em><b>Muddle</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MUDDLE_ELEMENT__MUDDLE = eINSTANCE.getMuddleElement_Muddle();

		/**
		 * The meta object literal for the '<em><b>Style</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MUDDLE_ELEMENT__STYLE = eINSTANCE.getMuddleElement_Style();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementStyleImpl <em>Element Style</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddleElementStyleImpl
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getMuddleElementStyle()
		 * @generated
		 */
		EClass MUDDLE_ELEMENT_STYLE = eINSTANCE.getMuddleElementStyle();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MUDDLE_ELEMENT_STYLE__COLOR = eINSTANCE.getMuddleElementStyle_Color();

		/**
		 * The meta object literal for the '<em><b>Shape</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MUDDLE_ELEMENT_STYLE__SHAPE = eINSTANCE.getMuddleElementStyle_Shape();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MUDDLE_ELEMENT_STYLE__WIDTH = eINSTANCE.getMuddleElementStyle_Width();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MUDDLE_ELEMENT_STYLE__HEIGHT = eINSTANCE.getMuddleElementStyle_Height();

		/**
		 * The meta object literal for the '<em><b>Border Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MUDDLE_ELEMENT_STYLE__BORDER_WIDTH = eINSTANCE.getMuddleElementStyle_BorderWidth();

		/**
		 * The meta object literal for the '<em><b>Label Font Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MUDDLE_ELEMENT_STYLE__LABEL_FONT_SIZE = eINSTANCE.getMuddleElementStyle_LabelFontSize();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MUDDLE_ELEMENT_STYLE__X = eINSTANCE.getMuddleElementStyle_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MUDDLE_ELEMENT_STYLE__Y = eINSTANCE.getMuddleElementStyle_Y();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.muddle.impl.SlotImpl <em>Slot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.muddle.impl.SlotImpl
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getSlot()
		 * @generated
		 */
		EClass SLOT = eINSTANCE.getSlot();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLOT__VALUES = eINSTANCE.getSlot_Values();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLOT__FEATURE = eINSTANCE.getSlot_Feature();

		/**
		 * The meta object literal for the '<em><b>Owning Element</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLOT__OWNING_ELEMENT = eINSTANCE.getSlot_OwningElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.muddle.impl.TypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.muddle.impl.TypeImpl
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getType()
		 * @generated
		 */
		EClass TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__NAME = eINSTANCE.getType_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.muddle.impl.FeatureImpl <em>Feature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.muddle.impl.FeatureImpl
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getFeature()
		 * @generated
		 */
		EClass FEATURE = eINSTANCE.getFeature();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__NAME = eINSTANCE.getFeature_Name();

		/**
		 * The meta object literal for the '<em><b>Many</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__MANY = eINSTANCE.getFeature_Many();

		/**
		 * The meta object literal for the '<em><b>Primary</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__PRIMARY = eINSTANCE.getFeature_Primary();

		/**
		 * The meta object literal for the '<em><b>Runtime</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE__RUNTIME = eINSTANCE.getFeature_Runtime();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE__TYPE = eINSTANCE.getFeature_Type();

		/**
		 * The meta object literal for the '<em><b>Owning Type</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE__OWNING_TYPE = eINSTANCE.getFeature_OwningType();

		/**
		 * The meta object literal for the '<em><b>Slots</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE__SLOTS = eINSTANCE.getFeature_Slots();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementTypeImpl <em>Element Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddleElementTypeImpl
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getMuddleElementType()
		 * @generated
		 */
		EClass MUDDLE_ELEMENT_TYPE = eINSTANCE.getMuddleElementType();

		/**
		 * The meta object literal for the '<em><b>Instances</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MUDDLE_ELEMENT_TYPE__INSTANCES = eINSTANCE.getMuddleElementType_Instances();

		/**
		 * The meta object literal for the '<em><b>Features</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MUDDLE_ELEMENT_TYPE__FEATURES = eINSTANCE.getMuddleElementType_Features();

		/**
		 * The meta object literal for the '<em><b>Super Types</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MUDDLE_ELEMENT_TYPE__SUPER_TYPES = eINSTANCE.getMuddleElementType_SuperTypes();

		/**
		 * The meta object literal for the '<em><b>Sub Types</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MUDDLE_ELEMENT_TYPE__SUB_TYPES = eINSTANCE.getMuddleElementType_SubTypes();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.muddle.impl.LinkElementTypeImpl <em>Link Element Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.muddle.impl.LinkElementTypeImpl
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getLinkElementType()
		 * @generated
		 */
		EClass LINK_ELEMENT_TYPE = eINSTANCE.getLinkElementType();

		/**
		 * The meta object literal for the '<em><b>Source Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK_ELEMENT_TYPE__SOURCE_FEATURE = eINSTANCE.getLinkElementType_SourceFeature();

		/**
		 * The meta object literal for the '<em><b>Target Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK_ELEMENT_TYPE__TARGET_FEATURE = eINSTANCE.getLinkElementType_TargetFeature();

		/**
		 * The meta object literal for the '<em><b>Role In Source Feature</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK_ELEMENT_TYPE__ROLE_IN_SOURCE_FEATURE = eINSTANCE.getLinkElementType_RoleInSourceFeature();

		/**
		 * The meta object literal for the '<em><b>Role In Target Feature</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK_ELEMENT_TYPE__ROLE_IN_TARGET_FEATURE = eINSTANCE.getLinkElementType_RoleInTargetFeature();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.muddle.impl.PrimitiveTypeImpl <em>Primitive Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.muddle.impl.PrimitiveTypeImpl
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getPrimitiveType()
		 * @generated
		 */
		EClass PRIMITIVE_TYPE = eINSTANCE.getPrimitiveType();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.muddle.impl.IntegerTypeImpl <em>Integer Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.muddle.impl.IntegerTypeImpl
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getIntegerType()
		 * @generated
		 */
		EClass INTEGER_TYPE = eINSTANCE.getIntegerType();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.muddle.impl.StringTypeImpl <em>String Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.muddle.impl.StringTypeImpl
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getStringType()
		 * @generated
		 */
		EClass STRING_TYPE = eINSTANCE.getStringType();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.muddle.impl.BooleanTypeImpl <em>Boolean Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.muddle.impl.BooleanTypeImpl
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getBooleanType()
		 * @generated
		 */
		EClass BOOLEAN_TYPE = eINSTANCE.getBooleanType();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.muddle.impl.RealTypeImpl <em>Real Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.muddle.impl.RealTypeImpl
		 * @see org.eclipse.epsilon.emc.muddle.impl.MuddlePackageImpl#getRealType()
		 * @generated
		 */
		EClass REAL_TYPE = eINSTANCE.getRealType();

	}

} //MuddlePackage
