/*******************************************************************************
 * Copyright (c) 2012 Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio García-Domínguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eunit;

import java.util.Collections;
import java.util.Map;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

/**
 * Data model class for the EUnit <code>$with</code> and <code>$onlywith</code>
 * EOL annotations.
 * 
 * @author Antonio García-Domínguez
 */
public class ModelBindings {

	/**
	 * Model binding exclusivity mode enumeration.
	 */
	public static enum ExclusiveMode {
		/**
		 * Models which are not mentioned should still be included in the
		 * module's model repository.
		 */
		INCLUDE_OTHERS,
		/**
		 * Models which are not mentioned should be excluded from the module's
		 * model repository.
		 */
		EXCLUDE_OTHERS
	}

	private final Map<String, String> modelMapping;
	private final ExclusiveMode exclusiveMode;

	/**
	 * Creates a new instance.
	 * 
	 * @param mapping
	 *            Map from the source models to their new names after the
	 *            rename.
	 * @param mode
	 *            Model binding exclusivity mode enumeration. Indicates whether
	 *            models not mentioned in the map should be included, or whether
	 *            they should be excluded.
	 */
	public ModelBindings(Map<String, String> mapping, ExclusiveMode mode)
			throws EolRuntimeException {
		assert mapping != null : "Model mapping map cannot be null";
		assert mode != null : "Model exclusivity mode cannot be null";

		this.modelMapping = mapping;
		this.exclusiveMode = mode;

		for (Object key : modelMapping.keySet()) {
			final Object value = modelMapping.get(key);
			if (!(key instanceof String) || !(value instanceof String)) {
				throw new EolRuntimeException(
						"Model bindings expect a map with String keys and values");
			}
		}
	}

	/**
	 * Returns the model binding exclusivity mode of this binding.
	 */
	public ExclusiveMode getExclusiveMode() {
		return exclusiveMode;
	}

	/**
	 * Returns an unmodifiable map where the keys are the names of the models to
	 * be renamed, and their respective values are the names they should be
	 * renamed to.
	 * 
	 * @see Collections#unmodifiableMap(Map)
	 */
	public Map<String, String> getMappings() {
		return Collections.unmodifiableMap(modelMapping);
	}

	@Override
	public String toString() {
		return "ModelBindings [modelMapping=" + modelMapping + ", exclusiveMode=" + exclusiveMode + "]";
	}

}
