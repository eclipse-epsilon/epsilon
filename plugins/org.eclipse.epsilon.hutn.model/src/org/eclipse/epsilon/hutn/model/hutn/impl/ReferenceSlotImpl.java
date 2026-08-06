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
 * $Id$
 */
package org.eclipse.epsilon.hutn.model.hutn.impl;

import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.HutnPackage;
import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reference Slot</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class ReferenceSlotImpl extends ClassObjectSlotImpl<String> implements ReferenceSlot {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferenceSlotImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HutnPackage.Literals.REFERENCE_SLOT;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setClassObjects(EList<ClassObject> classObjects) {
		getValues().clear();
		
		for (ClassObject classObject : classObjects) {
			addClassObject(classObject);
		}
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void addClassObject(ClassObject classObject) {
		getValues().add(classObject.getIdentifier());
	}
	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<ClassObject> getClassObjects() {
		final EList<ClassObject> classObjects = new BasicEList<>();
		
		for (String identifier : getValues()) {
			final ClassObject classObject = getClassObject(identifier);
			
			classObjects.add(classObject);
		}
		
		return new BasicEList.UnmodifiableEList<>(classObjects.size(), classObjects.toArray());
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	private ClassObject getClassObject(String identifier) {
		if(identifier == null)
			return null;
		
		for (ClassObject classObject : eAllClassObjects()) {
			if (identifier.equals(classObject.getIdentifier())) {
				return classObject;
			}
		}
		
		return null;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	private List<ClassObject> eAllClassObjects() {
		return EmfUtil.getAllModelElementsOfType(this, ClassObject.class);
	}

} //ReferenceSlotImpl
