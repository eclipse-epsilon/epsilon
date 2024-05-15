/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.variables;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.models.IReflectiveModel;

public class ModelElementReference extends IdentifiableReference<Object> {

	private static final Logger LOGGER = Logger.getLogger(ModelElementReference.class.getCanonicalName());

	private final String name;
	private final IReflectiveModel model;

	public ModelElementReference(IReflectiveModel rModel, String name, Object value) {
		super(value);
		this.model = rModel;
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public List<IVariableReference> getVariables(SuspendedState state) {
		List<IVariableReference> refs = new ArrayList<>();

		try {
			for (String propName : model.getPropertiesOf(model.getTypeNameOf(target))) {
				Object propValue;
				try {
					propValue = model.getPropertyGetter().invoke(target, propName, state.getContext());
				} catch (EolRuntimeException e) {
					LOGGER.log(Level.SEVERE, e.getMessage(), e);
					propValue = "<error obtaining value>";
				}

				refs.add(state.getValueReference(propName, propValue));
			}
		} catch (EolModelElementTypeNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

		return refs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(model, name);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelElementReference other = (ModelElementReference) obj;
		return Objects.equals(model, other.model) && Objects.equals(name, other.name);
	}

}
