/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates.setting;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.BasicSettingDelegate;
import org.eclipse.epsilon.ecore.delegates.EolOperationDelegateContext;
import org.eclipse.epsilon.ecore.delegates.execution.EolOperation;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;

/**
 * A SettingDelegate for derived features.
 * 
 * Whenever a feature is described as derived, we assume it is also volatile, transient, and 
 * non-changeable. 
 * 
 * @since 2.5
 */
public class EolDerivedSettingDelegate extends BasicSettingDelegate.Stateless implements EpsilonSettingDelegate  {

	public EolDerivedSettingDelegate(
		EStructuralFeature eStructuralFeature,
		EolOperationDelegateContext delegateContext,
		SettingUri uri) {
		super(eStructuralFeature);
		this.delegateContext = delegateContext;
		this.uri = uri;
	}
	
	@Override
	protected Object get(InternalEObject owner, boolean resolve, boolean coreType) {
		if (this.errors.contains(owner)) {
			// No need to throw the same exception multiple times.
			return false;
		}
		if (this.program == null) {
			this.program = (EolOperation) this.delegateContext.parse(
					toEolOperation(this.expression()));
		}
		try {
			return this.program.execute(
					owner,
					this.models.computeIfAbsent(this.eStructuralFeature.eResource(), r -> new InMemoryEmfModel(r)));
		} catch (Throwable e) {
			this.errors.add(owner);
			throw new IllegalArgumentException("Error evaluating derived feature " 
					+ this.eStructuralFeature.getName() + " on " + owner, e);
		}
	}

	@Override
	protected boolean isSet(InternalEObject owner) {
		return false;
	}

	@Override
	public void reset() {
		this.program = null;
		this.errors.clear();
		this.models.clear();
	}

	private final Map<Resource, InMemoryEmfModel> models = new HashMap<>();
	private final Set<InternalEObject> errors = new HashSet<>();
	private final EolOperationDelegateContext delegateContext;
	private EolOperation program;
	private final SettingUri uri;
	
	private String toEolOperation(String body) {
		StringBuilder result = new StringBuilder();
		result.append("operation ");
		result.append(this.eStructuralFeature.getName());
		result.append("(");
		result.append(")");
		result.append("{");
		result.append(body);
		result.append("}");
		return result.toString();
	}
	
	private String expression() {
		return this.uri.getEannotionValue(this.eStructuralFeature, "derivation");
	}

}
