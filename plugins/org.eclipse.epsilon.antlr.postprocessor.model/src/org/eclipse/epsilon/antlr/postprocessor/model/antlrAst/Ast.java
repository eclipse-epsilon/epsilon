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
 * $Id: Ast.java,v 1.2 2008/07/30 11:13:15 dkolovos Exp $
 */
package org.eclipse.epsilon.antlr.postprocessor.model.antlrAst;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ast</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast#getRoots <em>Roots</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.AntlrAstPackage#getAst()
 * @model
 * @generated
 */
public interface Ast extends EObject {
	/**
	 * Returns the value of the '<em><b>Roots</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Roots</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Roots</em>' containment reference list.
	 * @see org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.AntlrAstPackage#getAst_Roots()
	 * @model containment="true"
	 * @generated
	 */
	EList<Node> getRoots();

} // Ast
