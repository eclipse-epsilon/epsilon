/*******************************************************************************
 * Copyright (c) 2012 The University of York, Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio García-Domínguez - implement getVariables() and hasVariables()
 ******************************************************************************/
package org.eclipse.epsilon.eol.dt.debug;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.epsilon.eol.execute.context.ExtendedProperties;
import org.eclipse.epsilon.eol.util.ReflectionUtil;

public class EolVariableValue extends EolDebugElement implements IValue {
	private final Object value;
	private final EolVariable variable;
	private IVariable[] children;

	private static final Set<Class<?>> PRIMITIVE_WRAPPER_CLASSES = new HashSet<>();

	static {
		PRIMITIVE_WRAPPER_CLASSES.add(Boolean.class);
		PRIMITIVE_WRAPPER_CLASSES.add(Character.class);
		PRIMITIVE_WRAPPER_CLASSES.add(Byte.class);
        PRIMITIVE_WRAPPER_CLASSES.add(Short.class);
        PRIMITIVE_WRAPPER_CLASSES.add(Integer.class);
        PRIMITIVE_WRAPPER_CLASSES.add(Long.class);
        PRIMITIVE_WRAPPER_CLASSES.add(Float.class);
        PRIMITIVE_WRAPPER_CLASSES.add(Double.class);
        PRIMITIVE_WRAPPER_CLASSES.add(Void.class);
	}

	public EolVariableValue(IDebugTarget target, EolVariable variable, Object value) {
		super(target);
		this.variable = variable;
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public EolVariable getVariable() {
		return variable;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return value != null ? value.getClass().getCanonicalName() : "(null)";
	}

	@Override
	public String getValueString() throws DebugException {
		return value + "";
	}

	@Override
	public boolean isAllocated() throws DebugException {
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public synchronized IVariable[] getVariables() throws DebugException {
		if (children == null) {
			// loops, primitive wrappers and null values do not have any children
			if (!isNullOrPrimitiveWrapper(value) && !variable.isLoop()) {
				final List<IVariable> subvars = new ArrayList<>();

				// Elements (for collections and arrays)
				if (value instanceof Collection) {
					int i = 0;
					for (Iterator<Object> it = ((Collection<Object>)value).iterator(); it.hasNext(); ) {
						subvars.add(new EolVariable(getDebugTarget(), "[" + i++ + "]", it.next()));
					}
				}
				else if (value instanceof Object[]) {
					final Object[] array = (Object[])value;
					for (int i = 0; i < array.length; ++i) {
						subvars.add(new EolVariable(getDebugTarget(), "[" + i + "]", array[i]));
					}
				}

				// Fields (for any object)
				final List<Field> fields = ReflectionUtil.getAllInheritedInstanceFields(value.getClass());
				for (Field f : fields) {
					boolean oldAccessible = f.isAccessible();
					try {
						f.setAccessible(true);
						subvars.add(new EolVariable(getDebugTarget(), f.getName(), f.get(value)));
						f.setAccessible(oldAccessible);
					} catch (Exception ex) {
						// could not make the field accessible or access it
					}
				}

				// Extended properties
				final EolDebugTarget dt = (EolDebugTarget)getDebugTarget();
				final ExtendedProperties allExtProps = dt.getModule().getContext().getExtendedProperties();
				for (Map.Entry<String, Object> eP : allExtProps.getPropertyValues(value).entrySet()) {
					subvars.add(new EolVariable(getDebugTarget(), "~" + eP.getKey(), eP.getValue()));
				}

				// Sort fields by name
				Collections.sort(subvars, new IVariableNameComparator());

				// Mark values equal to the current value as loops
				for (IVariable v : subvars) {
					final EolVariable eolVar = (EolVariable)v;
					final EolVariableValue eolVarValue = (EolVariableValue)v.getValue();
					if (eolVarValue.getValue() == value) {
						eolVar.setLoop(true);
					}
				}

				// Convert into an array
				children = subvars.toArray(new IVariable[subvars.size()]);
			}
			else {
				children = new IVariable[0];
			}
		}
		return children;
	}

	private boolean isNullOrPrimitiveWrapper(Object value) {
		return value == null || PRIMITIVE_WRAPPER_CLASSES.contains(value.getClass());
	}

	@Override
	public boolean hasVariables() throws DebugException {
		final IVariable[] vars = getVariables();
		return vars != null && vars.length > 0;
	}
}
