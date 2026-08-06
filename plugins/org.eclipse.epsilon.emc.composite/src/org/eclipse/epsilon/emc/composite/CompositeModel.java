/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.composite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import org.eclipse.epsilon.ecl.trace.Match;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertySetter;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.Model;

public class CompositeModel extends Model {
	
	protected Collection<IModel> models = new ArrayList<>();
	protected ArrayList<ArrayList<Object>> equivalents = new ArrayList<>();
	
	public CompositeModel(Collection<IModel> models) {
		this.models = models;
		propertyGetter = new CompositePropertyGetter();
	}
	
	public CompositeModel(String name, Collection<IModel> models) {
		this(models);
		setName(name);
	}
	
	public CompositeModel(Collection<IModel> models, MatchTrace matchTrace) {
		this(models);
		findEquivalents(matchTrace.getReduced());
	}
	
	protected void findEquivalents(MatchTrace matchTrace) {
		for (Match m : matchTrace) {
			boolean existing = false;
			for (Collection<Object> eq : equivalents) {
				if (eq.contains(m.getLeft()) || eq.contains(m.getRight())) {
					existing = true;
					if (!eq.contains(m.getLeft())) eq.add(m.getLeft());
					if (!eq.contains(m.getRight())) eq.add(m.getRight());
 				}
			}
			if (!existing) {
				ArrayList<Object> eq = new ArrayList<>();
				eq.add(m.getLeft());
				eq.add(m.getRight());
				equivalents.add(eq);
			}
 		}
	}
	
	public Collection<Object> getEquivalents(Object instance) {
		Collection<Object> equivalents = new ArrayList<>();
		for (ArrayList<Object> eq : this.equivalents) {
			if (eq.contains(instance)) {
				equivalents.addAll(eq);
			}
		}
		
		return equivalents;
	}
	
	public void removeDuplicates(Collection<Object> original, Collection<Object> equivalents) {
		
		for (Object eq : equivalents) {
			if (original.contains(eq)) {
				for (Object rem : equivalents) {
					if (rem != eq) original.remove(rem);
				}
			}
		}
	}
	
	protected void removeDuplicates(Collection<Object> original) {
		for (ArrayList<Object> eq : equivalents) {
			removeDuplicates(original, eq);
		}
	}
	
	@Override
	public Collection<?> allContents() {
		
		Collection<Object> all = new ArrayList<>();
		
		for (IModel m : models) {
			all.addAll(m.allContents());
		}
		
		removeDuplicates(all);
		
		return all;
	}

	@Override
	public Object createInstance(String type)
			throws EolModelElementTypeNotFoundException,
			EolNotInstantiableModelElementTypeException {
		
		for (IModel m : models) {
			if (m.hasType(type))
				return m.createInstance(type);
		}
		
		throw new EolNotInstantiableModelElementTypeException(this.name, type);
	}

	@Override
	public void deleteElement(Object instance) throws EolRuntimeException {
		for (IModel m : models) {
			if (m.owns(instance)) m.deleteElement(instance);
		}
	}

	@Override
	public Collection<Object> getAllOfKind(String type)
			throws EolModelElementTypeNotFoundException {

		Collection<Object> all = new ArrayList<>();
		
		for (IModel m : models) {
			if (m.hasType(type))
				all.addAll(m.getAllOfKind(type));
		}
		
		removeDuplicates(all);
		
		return all;
	}

	@Override
	public Collection<Object> getAllOfType(String type)
			throws EolModelElementTypeNotFoundException {


		Collection<Object> all = new ArrayList<>();
		
		for (IModel m : models) {
			if (m.hasType(type))
				all.addAll(m.getAllOfType(type));
		}
		
		removeDuplicates(all);
		
		return all;
	}

	@Override
	public Object getElementById(String id) {
		for (IModel m : models) {
			Object element = null;
			element = m.getElementById(id);
			if (element != null) return element;
		}
		return null;
	}

	@Override
	public String getElementId(Object instance) {
		for (IModel m : models) {
			if (m.owns(instance)) return m.getElementId(instance);
		}
		return null;
	}
	
	@Override
	public void setElementId(Object instance, String newId) {
		for (IModel m : models) {
			if (m.owns(instance)) m.setElementId(instance, newId);
		}
	}

	@Override
	public Object getEnumerationValue(String enumeration, String label)
			throws EolEnumerationValueNotFoundException {
		if (!models.isEmpty()) 
			return models.iterator().next().getEnumerationValue(enumeration, label);
		else return null;
	}

	@Override
	public Object getTypeOf(Object instance) {
		for (IModel m : models) {
			if (m.owns(instance)) return m.getTypeOf(instance);
		}
		return false;
	}
	
	@Override
	public String getTypeNameOf(Object instance) {
		for (IModel m : models) {
			if (m.isModelElement(instance)) return m.getTypeNameOf(instance);
		}

		throw new IllegalArgumentException("Cannot be contained by this model: " + instance);
	}

	@Override
	public boolean hasType(String type) {
		for (IModel m : models) {
			if (m.hasType(type)) return true;
		}
		return false;
	}

	@Override
	public boolean isInstantiable(String type) {
		return false;
	}

	@Override
	public boolean isModelElement(Object instance) {
		for (IModel m : models) {
			if (m.isModelElement(instance)) return true;
		}
		return false;
	}

	@Override
	public void load() throws EolModelLoadingException {
		
	}

	@Override
	public boolean owns(Object instance) {
		for (IModel m : models) {
			if (m.owns(instance)) return true;
		}
		return false;
	}

	@Override
	public void dispose() {
		for (IModel m : models) {
			m.dispose();
		}
		
		super.dispose();
	}
	
	@Override
	public boolean store(String location) {
		for (IModel m : models) {
			if (!m.store(location)) return false;
		}
		return true;
	}

	@Override
	public boolean store() {
		for (IModel m : models) {
			if (!m.store()) return false;
		}
		return true;
	}
	
	@Override
	public IPropertySetter getPropertySetter() {
		return new CompositePropertySetter();
	}
	
	class CompositePropertyGetter extends AbstractPropertyGetter {

		@Override
		public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
			
			HashMap<Object, IPropertyGetter> getters = new HashMap<>();
			Collection<Object> equivalents = getEquivalents(object);
			
			if (equivalents.isEmpty()) equivalents.add(object);

			for (Object equivalent : equivalents) {
				for (IModel model : models) {
					if (model.owns(equivalent)) {
						getters.put(equivalent, model.getPropertyGetter());
					}
				}
			}
			
			Collection<Object> results = new HashSet<>();
			
			for (Object eq : equivalents) {
				Object result = getters.get(eq).invoke(eq, property, context);
				if (result instanceof Collection) {
					results.addAll((Collection<?>) result);
				}
				else {
					return result;
				}
			}
			
			removeDuplicates(results);
			return results;
		}
	}
	
	
	class CompositePropertySetter extends AbstractPropertySetter {
		
		@Override
		public void invoke(Object object, String property, Object value, IEolContext context) throws EolRuntimeException {
			for (IModel model : models) {
				if (model.owns(object)) {
					model.getPropertySetter().invoke(object, property, value, context);
				}
			}
			throw new EolIllegalPropertyException(object, property, context);
		}
	}
}
