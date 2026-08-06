/**
 */
package org.eclipse.epsilon.eunit.examples.emfvalidator.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.epsilon.eunit.examples.emfvalidator.EmfvalidatorFactory;
import org.eclipse.epsilon.eunit.examples.emfvalidator.EmfvalidatorPackage;
import org.eclipse.epsilon.eunit.examples.emfvalidator.Model;

import org.eclipse.epsilon.eunit.examples.emfvalidator.util.EmfvalidatorValidator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EmfvalidatorPackageImpl extends EPackageImpl implements EmfvalidatorPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelEClass = null;

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
	 * @see org.eclipse.epsilon.eunit.examples.emfvalidator.EmfvalidatorPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EmfvalidatorPackageImpl() {
		super(eNS_URI, EmfvalidatorFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EmfvalidatorPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EmfvalidatorPackage init() {
		if (isInited) return (EmfvalidatorPackage)EPackage.Registry.INSTANCE.getEPackage(EmfvalidatorPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredEmfvalidatorPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		EmfvalidatorPackageImpl theEmfvalidatorPackage = registeredEmfvalidatorPackage instanceof EmfvalidatorPackageImpl ? (EmfvalidatorPackageImpl)registeredEmfvalidatorPackage : new EmfvalidatorPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theEmfvalidatorPackage.createPackageContents();

		// Initialize created meta-data
		theEmfvalidatorPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theEmfvalidatorPackage,
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return EmfvalidatorValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theEmfvalidatorPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EmfvalidatorPackage.eNS_URI, theEmfvalidatorPackage);
		return theEmfvalidatorPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModel() {
		return modelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModel_PositiveValue() {
		return (EAttribute)modelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmfvalidatorFactory getEmfvalidatorFactory() {
		return (EmfvalidatorFactory)getEFactoryInstance();
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
		modelEClass = createEClass(MODEL);
		createEAttribute(modelEClass, MODEL__POSITIVE_VALUE);
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

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(modelEClass, Model.class, "Model", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModel_PositiveValue(), ecorePackage.getEInt(), "positiveValue", null, 0, 1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore";
		addAnnotation
		  (modelEClass,
		   source,
		   new String[] {
			   "constraints", "PositiveValue"
		   });
	}

} //EmfvalidatorPackageImpl
