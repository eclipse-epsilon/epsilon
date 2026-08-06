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
 * $Id: HutnPackage.java,v 1.4 2008/08/15 10:05:56 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutn;

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
 * @see org.eclipse.epsilon.hutn.model.hutn.HutnFactory
 * @model kind="package"
 * @generated
 */
public interface HutnPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "hutn";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/gmt/epsilon/hutn";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "hutn";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	HutnPackage eINSTANCE = org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.SpecImpl <em>Spec</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.SpecImpl
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getSpec()
	 * @generated
	 */
	int SPEC = 0;

	/**
	 * The feature id for the '<em><b>Ns Uris</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPEC__NS_URIS = 0;

	/**
	 * The feature id for the '<em><b>Objects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPEC__OBJECTS = 1;

	/**
	 * The feature id for the '<em><b>Model File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPEC__MODEL_FILE = 2;

	/**
	 * The feature id for the '<em><b>Source File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPEC__SOURCE_FILE = 3;

	/**
	 * The number of structural features of the '<em>Spec</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPEC_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.ModelElementImpl <em>Model Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.ModelElementImpl
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getModelElement()
	 * @generated
	 */
	int MODEL_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__LINE = 0;

	/**
	 * The feature id for the '<em><b>Col</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__COL = 1;

	/**
	 * The number of structural features of the '<em>Model Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.NsUriImpl <em>Ns Uri</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.NsUriImpl
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getNsUri()
	 * @generated
	 */
	int NS_URI = 1;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NS_URI__LINE = MODEL_ELEMENT__LINE;

	/**
	 * The feature id for the '<em><b>Col</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NS_URI__COL = MODEL_ELEMENT__COL;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NS_URI__VALUE = MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Ns Uri</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NS_URI_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.ObjectImpl <em>Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.ObjectImpl
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getObject()
	 * @generated
	 */
	int OBJECT = 3;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT__LINE = MODEL_ELEMENT__LINE;

	/**
	 * The feature id for the '<em><b>Col</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT__COL = MODEL_ELEMENT__COL;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT__TYPE = MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT__IDENTIFIER = MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.PackageObjectImpl <em>Package Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.PackageObjectImpl
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getPackageObject()
	 * @generated
	 */
	int PACKAGE_OBJECT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.ClassObjectImpl <em>Class Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.ClassObjectImpl
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getClassObject()
	 * @generated
	 */
	int CLASS_OBJECT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.SlotImpl <em>Slot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.SlotImpl
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getSlot()
	 * @generated
	 */
	int SLOT = 6;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_OBJECT__LINE = OBJECT__LINE;

	/**
	 * The feature id for the '<em><b>Col</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_OBJECT__COL = OBJECT__COL;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_OBJECT__TYPE = OBJECT__TYPE;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_OBJECT__IDENTIFIER = OBJECT__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Metamodel</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_OBJECT__METAMODEL = OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Class Objects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_OBJECT__CLASS_OBJECTS = OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Package Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_OBJECT_FEATURE_COUNT = OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_OBJECT__LINE = OBJECT__LINE;

	/**
	 * The feature id for the '<em><b>Col</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_OBJECT__COL = OBJECT__COL;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_OBJECT__TYPE = OBJECT__TYPE;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_OBJECT__IDENTIFIER = OBJECT__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Slots</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_OBJECT__SLOTS = OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Class Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_OBJECT_FEATURE_COUNT = OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT__LINE = MODEL_ELEMENT__LINE;

	/**
	 * The feature id for the '<em><b>Col</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT__COL = MODEL_ELEMENT__COL;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT__FEATURE = MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT__OWNER = MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT__VALUES = MODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.AttributeSlotImpl <em>Attribute Slot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.AttributeSlotImpl
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getAttributeSlot()
	 * @generated
	 */
	int ATTRIBUTE_SLOT = 7;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_SLOT__LINE = SLOT__LINE;

	/**
	 * The feature id for the '<em><b>Col</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_SLOT__COL = SLOT__COL;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_SLOT__FEATURE = SLOT__FEATURE;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_SLOT__OWNER = SLOT__OWNER;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_SLOT__VALUES = SLOT__VALUES;

	/**
	 * The number of structural features of the '<em>Attribute Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_SLOT_FEATURE_COUNT = SLOT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.ClassObjectSlotImpl <em>Class Object Slot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.ClassObjectSlotImpl
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getClassObjectSlot()
	 * @generated
	 */
	int CLASS_OBJECT_SLOT = 8;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_OBJECT_SLOT__LINE = SLOT__LINE;

	/**
	 * The feature id for the '<em><b>Col</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_OBJECT_SLOT__COL = SLOT__COL;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_OBJECT_SLOT__FEATURE = SLOT__FEATURE;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_OBJECT_SLOT__OWNER = SLOT__OWNER;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_OBJECT_SLOT__VALUES = SLOT__VALUES;

	/**
	 * The number of structural features of the '<em>Class Object Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_OBJECT_SLOT_FEATURE_COUNT = SLOT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.ContainmentSlotImpl <em>Containment Slot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.ContainmentSlotImpl
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getContainmentSlot()
	 * @generated
	 */
	int CONTAINMENT_SLOT = 9;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINMENT_SLOT__LINE = CLASS_OBJECT_SLOT__LINE;

	/**
	 * The feature id for the '<em><b>Col</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINMENT_SLOT__COL = CLASS_OBJECT_SLOT__COL;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINMENT_SLOT__FEATURE = CLASS_OBJECT_SLOT__FEATURE;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINMENT_SLOT__OWNER = CLASS_OBJECT_SLOT__OWNER;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINMENT_SLOT__VALUES = CLASS_OBJECT_SLOT__VALUES;

	/**
	 * The feature id for the '<em><b>Class Objects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINMENT_SLOT__CLASS_OBJECTS = CLASS_OBJECT_SLOT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Containment Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINMENT_SLOT_FEATURE_COUNT = CLASS_OBJECT_SLOT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.ReferenceSlotImpl <em>Reference Slot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.ReferenceSlotImpl
	 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getReferenceSlot()
	 * @generated
	 */
	int REFERENCE_SLOT = 10;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SLOT__LINE = CLASS_OBJECT_SLOT__LINE;

	/**
	 * The feature id for the '<em><b>Col</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SLOT__COL = CLASS_OBJECT_SLOT__COL;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SLOT__FEATURE = CLASS_OBJECT_SLOT__FEATURE;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SLOT__OWNER = CLASS_OBJECT_SLOT__OWNER;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SLOT__VALUES = CLASS_OBJECT_SLOT__VALUES;

	/**
	 * The number of structural features of the '<em>Reference Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SLOT_FEATURE_COUNT = CLASS_OBJECT_SLOT_FEATURE_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutn.Spec <em>Spec</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Spec</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.Spec
	 * @generated
	 */
	EClass getSpec();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.hutn.model.hutn.Spec#getNsUris <em>Ns Uris</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ns Uris</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.Spec#getNsUris()
	 * @see #getSpec()
	 * @generated
	 */
	EReference getSpec_NsUris();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.hutn.model.hutn.Spec#getObjects <em>Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Objects</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.Spec#getObjects()
	 * @see #getSpec()
	 * @generated
	 */
	EReference getSpec_Objects();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.model.hutn.Spec#getModelFile <em>Model File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Model File</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.Spec#getModelFile()
	 * @see #getSpec()
	 * @generated
	 */
	EAttribute getSpec_ModelFile();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.model.hutn.Spec#getSourceFile <em>Source File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source File</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.Spec#getSourceFile()
	 * @see #getSpec()
	 * @generated
	 */
	EAttribute getSpec_SourceFile();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutn.NsUri <em>Ns Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ns Uri</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.NsUri
	 * @generated
	 */
	EClass getNsUri();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.model.hutn.NsUri#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.NsUri#getValue()
	 * @see #getNsUri()
	 * @generated
	 */
	EAttribute getNsUri_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutn.ModelElement <em>Model Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Element</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.ModelElement
	 * @generated
	 */
	EClass getModelElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.model.hutn.ModelElement#getLine <em>Line</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.ModelElement#getLine()
	 * @see #getModelElement()
	 * @generated
	 */
	EAttribute getModelElement_Line();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.model.hutn.ModelElement#getCol <em>Col</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Col</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.ModelElement#getCol()
	 * @see #getModelElement()
	 * @generated
	 */
	EAttribute getModelElement_Col();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutn.Object <em>Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.Object
	 * @generated
	 */
	EClass getObject();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.model.hutn.Object#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.Object#getType()
	 * @see #getObject()
	 * @generated
	 */
	EAttribute getObject_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.model.hutn.Object#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Identifier</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.Object#getIdentifier()
	 * @see #getObject()
	 * @generated
	 */
	EAttribute getObject_Identifier();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutn.PackageObject <em>Package Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Package Object</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.PackageObject
	 * @generated
	 */
	EClass getPackageObject();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.epsilon.hutn.model.hutn.PackageObject#getMetamodel <em>Metamodel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Metamodel</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.PackageObject#getMetamodel()
	 * @see #getPackageObject()
	 * @generated
	 */
	EReference getPackageObject_Metamodel();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.hutn.model.hutn.PackageObject#getClassObjects <em>Class Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Class Objects</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.PackageObject#getClassObjects()
	 * @see #getPackageObject()
	 * @generated
	 */
	EReference getPackageObject_ClassObjects();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutn.ClassObject <em>Class Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class Object</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.ClassObject
	 * @generated
	 */
	EClass getClassObject();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.hutn.model.hutn.ClassObject#getSlots <em>Slots</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Slots</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.ClassObject#getSlots()
	 * @see #getClassObject()
	 * @generated
	 */
	EReference getClassObject_Slots();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutn.Slot <em>Slot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Slot</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.Slot
	 * @generated
	 */
	EClass getSlot();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.hutn.model.hutn.Slot#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Feature</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.Slot#getFeature()
	 * @see #getSlot()
	 * @generated
	 */
	EAttribute getSlot_Feature();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.epsilon.hutn.model.hutn.Slot#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.Slot#getOwner()
	 * @see #getSlot()
	 * @generated
	 */
	EReference getSlot_Owner();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.epsilon.hutn.model.hutn.Slot#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Values</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.Slot#getValues()
	 * @see #getSlot()
	 * @generated
	 */
	EAttribute getSlot_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutn.AttributeSlot <em>Attribute Slot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Slot</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.AttributeSlot
	 * @generated
	 */
	EClass getAttributeSlot();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutn.ClassObjectSlot <em>Class Object Slot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class Object Slot</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.ClassObjectSlot
	 * @generated
	 */
	EClass getClassObjectSlot();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot <em>Containment Slot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Containment Slot</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot
	 * @generated
	 */
	EClass getContainmentSlot();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot#getClassObjects <em>Class Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Class Objects</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot#getClassObjects()
	 * @see #getContainmentSlot()
	 * @generated
	 */
	EReference getContainmentSlot_ClassObjects();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot <em>Reference Slot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reference Slot</em>'.
	 * @see org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot
	 * @generated
	 */
	EClass getReferenceSlot();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	HutnFactory getHutnFactory();

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
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.SpecImpl <em>Spec</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.SpecImpl
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getSpec()
		 * @generated
		 */
		EClass SPEC = eINSTANCE.getSpec();

		/**
		 * The meta object literal for the '<em><b>Ns Uris</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SPEC__NS_URIS = eINSTANCE.getSpec_NsUris();

		/**
		 * The meta object literal for the '<em><b>Objects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SPEC__OBJECTS = eINSTANCE.getSpec_Objects();

		/**
		 * The meta object literal for the '<em><b>Model File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SPEC__MODEL_FILE = eINSTANCE.getSpec_ModelFile();

		/**
		 * The meta object literal for the '<em><b>Source File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SPEC__SOURCE_FILE = eINSTANCE.getSpec_SourceFile();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.NsUriImpl <em>Ns Uri</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.NsUriImpl
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getNsUri()
		 * @generated
		 */
		EClass NS_URI = eINSTANCE.getNsUri();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NS_URI__VALUE = eINSTANCE.getNsUri_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.ModelElementImpl <em>Model Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.ModelElementImpl
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getModelElement()
		 * @generated
		 */
		EClass MODEL_ELEMENT = eINSTANCE.getModelElement();

		/**
		 * The meta object literal for the '<em><b>Line</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_ELEMENT__LINE = eINSTANCE.getModelElement_Line();

		/**
		 * The meta object literal for the '<em><b>Col</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_ELEMENT__COL = eINSTANCE.getModelElement_Col();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.ObjectImpl <em>Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.ObjectImpl
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getObject()
		 * @generated
		 */
		EClass OBJECT = eINSTANCE.getObject();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBJECT__TYPE = eINSTANCE.getObject_Type();

		/**
		 * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBJECT__IDENTIFIER = eINSTANCE.getObject_Identifier();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.PackageObjectImpl <em>Package Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.PackageObjectImpl
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getPackageObject()
		 * @generated
		 */
		EClass PACKAGE_OBJECT = eINSTANCE.getPackageObject();

		/**
		 * The meta object literal for the '<em><b>Metamodel</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PACKAGE_OBJECT__METAMODEL = eINSTANCE.getPackageObject_Metamodel();

		/**
		 * The meta object literal for the '<em><b>Class Objects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PACKAGE_OBJECT__CLASS_OBJECTS = eINSTANCE.getPackageObject_ClassObjects();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.ClassObjectImpl <em>Class Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.ClassObjectImpl
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getClassObject()
		 * @generated
		 */
		EClass CLASS_OBJECT = eINSTANCE.getClassObject();

		/**
		 * The meta object literal for the '<em><b>Slots</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_OBJECT__SLOTS = eINSTANCE.getClassObject_Slots();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.SlotImpl <em>Slot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.SlotImpl
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getSlot()
		 * @generated
		 */
		EClass SLOT = eINSTANCE.getSlot();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLOT__FEATURE = eINSTANCE.getSlot_Feature();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLOT__OWNER = eINSTANCE.getSlot_Owner();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SLOT__VALUES = eINSTANCE.getSlot_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.AttributeSlotImpl <em>Attribute Slot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.AttributeSlotImpl
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getAttributeSlot()
		 * @generated
		 */
		EClass ATTRIBUTE_SLOT = eINSTANCE.getAttributeSlot();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.ClassObjectSlotImpl <em>Class Object Slot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.ClassObjectSlotImpl
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getClassObjectSlot()
		 * @generated
		 */
		EClass CLASS_OBJECT_SLOT = eINSTANCE.getClassObjectSlot();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.ContainmentSlotImpl <em>Containment Slot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.ContainmentSlotImpl
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getContainmentSlot()
		 * @generated
		 */
		EClass CONTAINMENT_SLOT = eINSTANCE.getContainmentSlot();

		/**
		 * The meta object literal for the '<em><b>Class Objects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTAINMENT_SLOT__CLASS_OBJECTS = eINSTANCE.getContainmentSlot_ClassObjects();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.hutn.model.hutn.impl.ReferenceSlotImpl <em>Reference Slot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.ReferenceSlotImpl
		 * @see org.eclipse.epsilon.hutn.model.hutn.impl.HutnPackageImpl#getReferenceSlot()
		 * @generated
		 */
		EClass REFERENCE_SLOT = eINSTANCE.getReferenceSlot();

	}

} //HutnPackage
