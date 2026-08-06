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
 * $Id: HutnPackageImpl.java,v 1.4 2008/08/15 10:05:57 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutn.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.ClassObjectSlot;
import org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot;
import org.eclipse.epsilon.hutn.model.hutn.HutnFactory;
import org.eclipse.epsilon.hutn.model.hutn.HutnPackage;
import org.eclipse.epsilon.hutn.model.hutn.ModelElement;
import org.eclipse.epsilon.hutn.model.hutn.NsUri;
import org.eclipse.epsilon.hutn.model.hutn.PackageObject;
import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.model.hutn.Slot;
import org.eclipse.epsilon.hutn.model.hutn.Spec;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HutnPackageImpl extends EPackageImpl implements HutnPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass specEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nsUriEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass packageObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass slotEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeSlotEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classObjectSlotEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass containmentSlotEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass referenceSlotEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private HutnPackageImpl() {
		super(eNS_URI, HutnFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link HutnPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static HutnPackage init() {
		if (isInited) return (HutnPackage)EPackage.Registry.INSTANCE.getEPackage(HutnPackage.eNS_URI);

		// Obtain or create and register package
		HutnPackageImpl theHutnPackage = (HutnPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof HutnPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new HutnPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theHutnPackage.createPackageContents();

		// Initialize created meta-data
		theHutnPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theHutnPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(HutnPackage.eNS_URI, theHutnPackage);
		return theHutnPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSpec() {
		return specEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSpec_NsUris() {
		return (EReference)specEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSpec_Objects() {
		return (EReference)specEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSpec_ModelFile() {
		return (EAttribute)specEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSpec_SourceFile() {
		return (EAttribute)specEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNsUri() {
		return nsUriEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNsUri_Value() {
		return (EAttribute)nsUriEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModelElement() {
		return modelElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModelElement_Line() {
		return (EAttribute)modelElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModelElement_Col() {
		return (EAttribute)modelElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObject() {
		return objectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getObject_Type() {
		return (EAttribute)objectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getObject_Identifier() {
		return (EAttribute)objectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPackageObject() {
		return packageObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackageObject_Metamodel() {
		return (EReference)packageObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackageObject_ClassObjects() {
		return (EReference)packageObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClassObject() {
		return classObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassObject_Slots() {
		return (EReference)classObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSlot() {
		return slotEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSlot_Feature() {
		return (EAttribute)slotEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSlot_Owner() {
		return (EReference)slotEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSlot_Values() {
		return (EAttribute)slotEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeSlot() {
		return attributeSlotEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClassObjectSlot() {
		return classObjectSlotEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getContainmentSlot() {
		return containmentSlotEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getContainmentSlot_ClassObjects() {
		return (EReference)containmentSlotEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReferenceSlot() {
		return referenceSlotEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HutnFactory getHutnFactory() {
		return (HutnFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		specEClass = createEClass(SPEC);
		createEReference(specEClass, SPEC__NS_URIS);
		createEReference(specEClass, SPEC__OBJECTS);
		createEAttribute(specEClass, SPEC__MODEL_FILE);
		createEAttribute(specEClass, SPEC__SOURCE_FILE);

		nsUriEClass = createEClass(NS_URI);
		createEAttribute(nsUriEClass, NS_URI__VALUE);

		modelElementEClass = createEClass(MODEL_ELEMENT);
		createEAttribute(modelElementEClass, MODEL_ELEMENT__LINE);
		createEAttribute(modelElementEClass, MODEL_ELEMENT__COL);

		objectEClass = createEClass(OBJECT);
		createEAttribute(objectEClass, OBJECT__TYPE);
		createEAttribute(objectEClass, OBJECT__IDENTIFIER);

		packageObjectEClass = createEClass(PACKAGE_OBJECT);
		createEReference(packageObjectEClass, PACKAGE_OBJECT__METAMODEL);
		createEReference(packageObjectEClass, PACKAGE_OBJECT__CLASS_OBJECTS);

		classObjectEClass = createEClass(CLASS_OBJECT);
		createEReference(classObjectEClass, CLASS_OBJECT__SLOTS);

		slotEClass = createEClass(SLOT);
		createEAttribute(slotEClass, SLOT__FEATURE);
		createEReference(slotEClass, SLOT__OWNER);
		createEAttribute(slotEClass, SLOT__VALUES);

		attributeSlotEClass = createEClass(ATTRIBUTE_SLOT);

		classObjectSlotEClass = createEClass(CLASS_OBJECT_SLOT);

		containmentSlotEClass = createEClass(CONTAINMENT_SLOT);
		createEReference(containmentSlotEClass, CONTAINMENT_SLOT__CLASS_OBJECTS);

		referenceSlotEClass = createEClass(REFERENCE_SLOT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters
		ETypeParameter slotEClass_T = addETypeParameter(slotEClass, "T");
		ETypeParameter classObjectSlotEClass_T = addETypeParameter(classObjectSlotEClass, "T");

		// Set bounds for type parameters

		// Add supertypes to classes
		nsUriEClass.getESuperTypes().add(this.getModelElement());
		objectEClass.getESuperTypes().add(this.getModelElement());
		packageObjectEClass.getESuperTypes().add(this.getObject());
		classObjectEClass.getESuperTypes().add(this.getObject());
		slotEClass.getESuperTypes().add(this.getModelElement());
		EGenericType g1 = createEGenericType(this.getSlot());
		EGenericType g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		attributeSlotEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getSlot());
		g2 = createEGenericType(classObjectSlotEClass_T);
		g1.getETypeArguments().add(g2);
		classObjectSlotEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getClassObjectSlot());
		g2 = createEGenericType(this.getClassObject());
		g1.getETypeArguments().add(g2);
		containmentSlotEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getClassObjectSlot());
		g2 = createEGenericType(ecorePackage.getEString());
		g1.getETypeArguments().add(g2);
		referenceSlotEClass.getEGenericSuperTypes().add(g1);

		// Initialize classes and features; add operations and parameters
		initEClass(specEClass, Spec.class, "Spec", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSpec_NsUris(), this.getNsUri(), null, "nsUris", null, 0, -1, Spec.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSpec_Objects(), this.getPackageObject(), null, "objects", null, 0, -1, Spec.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSpec_ModelFile(), ecorePackage.getEString(), "modelFile", null, 0, 1, Spec.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSpec_SourceFile(), ecorePackage.getEString(), "sourceFile", null, 0, 1, Spec.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nsUriEClass, NsUri.class, "NsUri", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNsUri_Value(), ecorePackage.getEString(), "value", null, 1, 1, NsUri.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(modelElementEClass, ModelElement.class, "ModelElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModelElement_Line(), ecorePackage.getEInt(), "line", null, 0, 1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModelElement_Col(), ecorePackage.getEInt(), "col", null, 0, 1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(objectEClass, org.eclipse.epsilon.hutn.model.hutn.Object.class, "Object", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getObject_Type(), ecorePackage.getEString(), "type", null, 0, 1, org.eclipse.epsilon.hutn.model.hutn.Object.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObject_Identifier(), ecorePackage.getEString(), "identifier", null, 0, 1, org.eclipse.epsilon.hutn.model.hutn.Object.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(packageObjectEClass, PackageObject.class, "PackageObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPackageObject_Metamodel(), ecorePackage.getEPackage(), null, "metamodel", null, 0, -1, PackageObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPackageObject_ClassObjects(), this.getClassObject(), null, "classObjects", null, 0, -1, PackageObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(packageObjectEClass, ecorePackage.getEClass(), "getAllEClasses", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(classObjectEClass, ClassObject.class, "ClassObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getSlot());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEReference(getClassObject_Slots(), g1, this.getSlot_Owner(), "slots", null, 0, -1, ClassObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(classObjectEClass, null, "findSlot", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "feature", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(this.getSlot());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		addEOperation(classObjectEClass, this.getPackageObject(), "getPackageObject", 1, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(classObjectEClass, ecorePackage.getEClass(), "getEClass", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(classObjectEClass, ecorePackage.getEBoolean(), "hasEClass", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(classObjectEClass, ecorePackage.getEBoolean(), "typeCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEClass(), "eClass", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(classObjectEClass, this.getAttributeSlot(), "findOrCreateAttributeSlot", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "feature", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(classObjectEClass, this.getReferenceSlot(), "findOrCreateReferenceSlot", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "feature", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(classObjectEClass, this.getContainmentSlot(), "findOrCreateContainmentSlot", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "feature", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(slotEClass, Slot.class, "Slot", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSlot_Feature(), ecorePackage.getEString(), "feature", null, 0, 1, Slot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSlot_Owner(), this.getClassObject(), this.getClassObject_Slots(), "owner", null, 1, 1, Slot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(slotEClass_T);
		initEAttribute(getSlot_Values(), g1, "values", null, 0, -1, Slot.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(slotEClass, ecorePackage.getEBoolean(), "typeCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEStructuralFeature(), "eStructuralFeature", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(slotEClass, ecorePackage.getEBoolean(), "multiplicityCompatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEStructuralFeature(), "eStructuralFeature", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(slotEClass, ecorePackage.getEBoolean(), "compatibleWith", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEStructuralFeature(), "eStructuralFeature", 1, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(slotEClass, ecorePackage.getEStructuralFeature(), "getEStructuralFeature", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(slotEClass, ecorePackage.getEBoolean(), "hasEStructuralFeature", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(slotEClass, null, "setValues", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(slotEClass_T);
		addEParameter(op, g1, "values", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(attributeSlotEClass, AttributeSlot.class, "AttributeSlot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(classObjectSlotEClass, ClassObjectSlot.class, "ClassObjectSlot", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(classObjectSlotEClass, this.getClassObject(), "getClassObjects", 0, -1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(classObjectSlotEClass, null, "setClassObjects", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getClassObject(), "classObjects", 0, -1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(classObjectSlotEClass, null, "addClassObject", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getClassObject(), "classObject", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(containmentSlotEClass, ContainmentSlot.class, "ContainmentSlot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContainmentSlot_ClassObjects(), this.getClassObject(), null, "classObjects", null, 0, -1, ContainmentSlot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(referenceSlotEClass, ReferenceSlot.class, "ReferenceSlot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //HutnPackageImpl
