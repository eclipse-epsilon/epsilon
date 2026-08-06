/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.emf.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;

public class CompositeEValidator implements EValidator {
	
	protected Collection<EValidator> delegates = new ArrayList<>();
	
	public Collection<EValidator> getDelegates() {
		return delegates;
	}

	@Override
	public boolean validate(EObject object, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean validate = true;
		for (EValidator validator : delegates) {
			if (validate || diagnostics != null) {
				validate &= validator.validate(object, diagnostics, context);
			}
		}
		return validate;
	}

	@Override
	public boolean validate(EClass class1, EObject object, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean validate = true;
		for (EValidator validator : delegates) {
			if (validate || diagnostics != null) {
				validate &= validator.validate(class1, object, diagnostics, context);
			}
		}
		return validate;
	}

	@Override
	public boolean validate(EDataType dataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean validate = true;
		for (EValidator validator : delegates) {
			if (validate || diagnostics != null) {
				validate &= validator.validate(dataType, value, diagnostics, context);
			}
		}
		return validate;
	}
}
