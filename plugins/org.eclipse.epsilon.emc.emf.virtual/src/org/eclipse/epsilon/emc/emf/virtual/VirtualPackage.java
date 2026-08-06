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
 * $Id: VirtualPackage.java,v 1.2 2008/07/30 11:13:02 dkolovos Exp $
 */
package org.eclipse.epsilon.emc.emf.virtual;

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
 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualFactory
 * @model kind="package"
 * @generated
 */
public interface VirtualPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "virtual";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "virtualEmf";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "virtualEmf";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	VirtualPackage eINSTANCE = org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.VirtualModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualModelImpl
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getVirtualModel()
	 * @generated
	 */
	int VIRTUAL_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Objects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MODEL__OBJECTS = 0;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MODEL_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.TypedElementImpl <em>Typed Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.TypedElementImpl
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getTypedElement()
	 * @generated
	 */
	int TYPED_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT__TYPE = 0;

	/**
	 * The number of structural features of the '<em>Typed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.VirtualObjectImpl <em>Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualObjectImpl
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getVirtualObject()
	 * @generated
	 */
	int VIRTUAL_OBJECT = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_OBJECT__TYPE = TYPED_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_OBJECT__MODEL = TYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Slots</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_OBJECT__SLOTS = TYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_OBJECT_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.SlotImpl <em>Slot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.SlotImpl
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getSlot()
	 * @generated
	 */
	int SLOT = 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT__TYPE = TYPED_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT__OWNER = TYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SLOT_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.StringSlotImpl <em>String Slot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.StringSlotImpl
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getStringSlot()
	 * @generated
	 */
	int STRING_SLOT = 4;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_SLOT__TYPE = SLOT__TYPE;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_SLOT__OWNER = SLOT__OWNER;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_SLOT__VALUES = SLOT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_SLOT_FEATURE_COUNT = SLOT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.IntegerSlotImpl <em>Integer Slot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.IntegerSlotImpl
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getIntegerSlot()
	 * @generated
	 */
	int INTEGER_SLOT = 5;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_SLOT__TYPE = SLOT__TYPE;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_SLOT__OWNER = SLOT__OWNER;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_SLOT__VALUES = SLOT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Integer Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_SLOT_FEATURE_COUNT = SLOT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.BooleanSlotImpl <em>Boolean Slot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.BooleanSlotImpl
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getBooleanSlot()
	 * @generated
	 */
	int BOOLEAN_SLOT = 6;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_SLOT__TYPE = SLOT__TYPE;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_SLOT__OWNER = SLOT__OWNER;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_SLOT__VALUES = SLOT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Boolean Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_SLOT_FEATURE_COUNT = SLOT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.FloatSlotImpl <em>Float Slot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.FloatSlotImpl
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getFloatSlot()
	 * @generated
	 */
	int FLOAT_SLOT = 7;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_SLOT__TYPE = SLOT__TYPE;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_SLOT__OWNER = SLOT__OWNER;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_SLOT__VALUES = SLOT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Float Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_SLOT_FEATURE_COUNT = SLOT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.ReferenceSlotImpl <em>Reference Slot</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.ReferenceSlotImpl
	 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getReferenceSlot()
	 * @generated
	 */
	int REFERENCE_SLOT = 8;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SLOT__TYPE = SLOT__TYPE;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SLOT__OWNER = SLOT__OWNER;

	/**
	 * The feature id for the '<em><b>Values</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SLOT__VALUES = SLOT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Reference Slot</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SLOT_FEATURE_COUNT = SLOT_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.emf.virtual.VirtualModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualModel
	 * @generated
	 */
	EClass getVirtualModel();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.emc.emf.virtual.VirtualModel#getObjects <em>Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Objects</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualModel#getObjects()
	 * @see #getVirtualModel()
	 * @generated
	 */
	EReference getVirtualModel_Objects();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.emf.virtual.TypedElement <em>Typed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Typed Element</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.TypedElement
	 * @generated
	 */
	EClass getTypedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.emc.emf.virtual.TypedElement#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.TypedElement#getType()
	 * @see #getTypedElement()
	 * @generated
	 */
	EAttribute getTypedElement_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.emf.virtual.VirtualObject <em>Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualObject
	 * @generated
	 */
	EClass getVirtualObject();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.epsilon.emc.emf.virtual.VirtualObject#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Model</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualObject#getModel()
	 * @see #getVirtualObject()
	 * @generated
	 */
	EReference getVirtualObject_Model();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.emc.emf.virtual.VirtualObject#getSlots <em>Slots</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Slots</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualObject#getSlots()
	 * @see #getVirtualObject()
	 * @generated
	 */
	EReference getVirtualObject_Slots();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.emf.virtual.Slot <em>Slot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Slot</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.Slot
	 * @generated
	 */
	EClass getSlot();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.epsilon.emc.emf.virtual.Slot#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.Slot#getOwner()
	 * @see #getSlot()
	 * @generated
	 */
	EReference getSlot_Owner();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.emf.virtual.StringSlot <em>String Slot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Slot</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.StringSlot
	 * @generated
	 */
	EClass getStringSlot();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.epsilon.emc.emf.virtual.StringSlot#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Values</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.StringSlot#getValues()
	 * @see #getStringSlot()
	 * @generated
	 */
	EAttribute getStringSlot_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.emf.virtual.IntegerSlot <em>Integer Slot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Slot</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.IntegerSlot
	 * @generated
	 */
	EClass getIntegerSlot();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.epsilon.emc.emf.virtual.IntegerSlot#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Values</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.IntegerSlot#getValues()
	 * @see #getIntegerSlot()
	 * @generated
	 */
	EAttribute getIntegerSlot_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.emf.virtual.BooleanSlot <em>Boolean Slot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Slot</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.BooleanSlot
	 * @generated
	 */
	EClass getBooleanSlot();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.epsilon.emc.emf.virtual.BooleanSlot#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Values</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.BooleanSlot#getValues()
	 * @see #getBooleanSlot()
	 * @generated
	 */
	EAttribute getBooleanSlot_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.emf.virtual.FloatSlot <em>Float Slot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Float Slot</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.FloatSlot
	 * @generated
	 */
	EClass getFloatSlot();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.epsilon.emc.emf.virtual.FloatSlot#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Values</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.FloatSlot#getValues()
	 * @see #getFloatSlot()
	 * @generated
	 */
	EAttribute getFloatSlot_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.emc.emf.virtual.ReferenceSlot <em>Reference Slot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reference Slot</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.ReferenceSlot
	 * @generated
	 */
	EClass getReferenceSlot();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.epsilon.emc.emf.virtual.ReferenceSlot#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Values</em>'.
	 * @see org.eclipse.epsilon.emc.emf.virtual.ReferenceSlot#getValues()
	 * @see #getReferenceSlot()
	 * @generated
	 */
	EReference getReferenceSlot_Values();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VirtualFactory getVirtualFactory();

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
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.VirtualModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualModelImpl
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getVirtualModel()
		 * @generated
		 */
		EClass VIRTUAL_MODEL = eINSTANCE.getVirtualModel();

		/**
		 * The meta object literal for the '<em><b>Objects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_MODEL__OBJECTS = eINSTANCE.getVirtualModel_Objects();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.TypedElementImpl <em>Typed Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.TypedElementImpl
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getTypedElement()
		 * @generated
		 */
		EClass TYPED_ELEMENT = eINSTANCE.getTypedElement();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPED_ELEMENT__TYPE = eINSTANCE.getTypedElement_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.VirtualObjectImpl <em>Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualObjectImpl
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getVirtualObject()
		 * @generated
		 */
		EClass VIRTUAL_OBJECT = eINSTANCE.getVirtualObject();

		/**
		 * The meta object literal for the '<em><b>Model</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_OBJECT__MODEL = eINSTANCE.getVirtualObject_Model();

		/**
		 * The meta object literal for the '<em><b>Slots</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_OBJECT__SLOTS = eINSTANCE.getVirtualObject_Slots();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.SlotImpl <em>Slot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.SlotImpl
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getSlot()
		 * @generated
		 */
		EClass SLOT = eINSTANCE.getSlot();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SLOT__OWNER = eINSTANCE.getSlot_Owner();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.StringSlotImpl <em>String Slot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.StringSlotImpl
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getStringSlot()
		 * @generated
		 */
		EClass STRING_SLOT = eINSTANCE.getStringSlot();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_SLOT__VALUES = eINSTANCE.getStringSlot_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.IntegerSlotImpl <em>Integer Slot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.IntegerSlotImpl
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getIntegerSlot()
		 * @generated
		 */
		EClass INTEGER_SLOT = eINSTANCE.getIntegerSlot();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTEGER_SLOT__VALUES = eINSTANCE.getIntegerSlot_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.BooleanSlotImpl <em>Boolean Slot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.BooleanSlotImpl
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getBooleanSlot()
		 * @generated
		 */
		EClass BOOLEAN_SLOT = eINSTANCE.getBooleanSlot();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_SLOT__VALUES = eINSTANCE.getBooleanSlot_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.FloatSlotImpl <em>Float Slot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.FloatSlotImpl
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getFloatSlot()
		 * @generated
		 */
		EClass FLOAT_SLOT = eINSTANCE.getFloatSlot();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOAT_SLOT__VALUES = eINSTANCE.getFloatSlot_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.emc.emf.virtual.impl.ReferenceSlotImpl <em>Reference Slot</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.ReferenceSlotImpl
		 * @see org.eclipse.epsilon.emc.emf.virtual.impl.VirtualPackageImpl#getReferenceSlot()
		 * @generated
		 */
		EClass REFERENCE_SLOT = eINSTANCE.getReferenceSlot();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REFERENCE_SLOT__VALUES = eINSTANCE.getReferenceSlot_Values();

	}

} //VirtualPackage
