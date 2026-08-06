/*******************************************************************************
 * Copyright (c) 2010 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.hutn;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotAnEnumerationValueException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IReflectivePropertySetter;
import org.eclipse.epsilon.eol.models.IAdaptableModel;
import org.eclipse.epsilon.eol.models.IReflectiveModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.epsilon.eol.models.Model;
import org.eclipse.epsilon.hutn.HutnModule;
import org.eclipse.epsilon.hutn.IHutnModule;
import org.eclipse.epsilon.hutn.exceptions.HutnGenerationException;
import org.eclipse.epsilon.hutn.xmi.HutnXmiBridgeException;
import org.eclipse.epsilon.hutn.xmi.Xmi2Hutn;

public class HutnModel extends Model implements IAdaptableModel, IReflectiveModel {

	public static final String PROPERTY_SOURCE_FILE = "sourceFile";
	
	private String hutn;
	private File hutnSourceFile;
	
	private EmfModel model;
	
	public HutnModel() {}
	
	public HutnModel(String name, String hutn) {
		this.hutn = hutn;
		setName(name);
	}
	
	@Override
	public boolean preventLoadingOfExternalModelElements() {
		return false;
	}
	
	@Override
	public void load() throws EolModelLoadingException {
		try {
			final IHutnModule module = new HutnModule();
			
			if (hutn != null)
				module.parse(hutn);
			else if (hutnSourceFile != null) {
				module.parse(hutnSourceFile);
			}
			
			if (!module.getParseProblems().isEmpty()) {
				final StringBuilder problems = new StringBuilder();
				
				for (ParseProblem problem : module.getParseProblems()) {
					problems.append('\n');
					problems.append(problem);
				}
				
				throw new EolModelLoadingException(new HutnGenerationException("Could not parse HUTN: " + problems), this);
			}

			model = (EmfModel)module.generateEmfModel();
			
			model.load();
		
		} catch (Exception e) {
			throw new EolModelLoadingException(e, this);
		}
	}
	
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		super.load(properties, resolver);
		this.hutnSourceFile = new File(resolver.resolve(properties.getProperty(PROPERTY_SOURCE_FILE)));
		load();
	}
	
	/**
	 * @since 1.6
	 */
	public boolean isLoaded() {
		return model != null && model.isLoaded();
	}

	@Override
	public IPropertyGetter getPropertyGetter() {
		return model.getPropertyGetter();
	}

	@Override
	public IReflectivePropertySetter getPropertySetter() {
		return model.getPropertySetter();
	}

	@Override
	public boolean store(String location) {
		try {
			FileUtil.setFileContents(getHutn(), new File(location));
			return true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean store() {
		return hutnSourceFile == null ? false : store(hutnSourceFile.getAbsolutePath());
	}
	
	@Override
	public String toString() {
		try {
			return getHutn();
		
		} catch (HutnXmiBridgeException e) {
			return "HutnModel(Could not deseralise to HUTN source)"; 
		}
	}

	private String getHutn() throws HutnXmiBridgeException {
		try {
			final StringOutputStream xmi = new StringOutputStream();
			
			model.getResource().save(xmi, Collections.EMPTY_MAP);
			
			return new Xmi2Hutn(xmi.toString()).getHutn();
		
		} catch (IOException e) { // Using a StringOutputStream
			e.printStackTrace();  // so this should never happen
			return "";
		}
	}
	
	class StringOutputStream extends OutputStream {
		
		StringBuffer buffer = new StringBuffer();
		
		@Override
		public void write(int chr) throws IOException {
			buffer.append((char) chr);
		}
		
		@Override
		public String toString() {
			return buffer.toString();
		}
	}
	
	
// Delegates //	

	@Override
	public Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException {
		return model.getEnumerationValue(enumeration, label);
	}

	@Override
	public Collection<?> allContents() {
		return model.allContents();
	}

	@Override
	public Collection<?> getAllOfType(String type) throws EolModelElementTypeNotFoundException {
		return model.getAllOfType(type);
	}

	@Override
	public Collection<?> getAllOfKind(String type) throws EolModelElementTypeNotFoundException {
		return model.getAllOfKind(type);
	}

	@Override
	public Object getTypeOf(Object instance) {
		return model.getTypeOf(instance);
	}

	@Override
	public String getTypeNameOf(Object instance) {
		return model.getTypeNameOf(instance);
	}

	@Override
	public Object createInstance(String type) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		return model.createInstance(type);
	}

	@Override
	public Object getElementById(String id) {
		return model.getElementById(id);
	}

	@Override
	public String getElementId(Object instance) {
		return model.getElementId(instance);
	}

	@Override
	public void setElementId(Object instance, String newId) {
		model.setElementId(instance, newId);
	}

	@Override
	public void deleteElement(Object instance) throws EolRuntimeException {
		model.deleteElement(instance);
	}

	@Override
	public boolean owns(Object instance) {
		return model.owns(instance);
	}

	@Override
	public boolean isInstantiable(String type) {
		return model.isInstantiable(type);
	}
	
	@Override
	public boolean isModelElement(Object instance) {
		return isModelElement(instance);
	}

	@Override
	public boolean hasType(String type) {
		return model.hasType(type);
	}
	
	@Override
	public boolean hasPackage(String packageName) {
		return model.hasPackage(packageName);
	}
	
	@Override
	public Object getContainerOf(Object object) {
		return model.getContainerOf(object);
	}

	@Override
	public Collection<String> getPropertiesOf(String type) throws EolModelElementTypeNotFoundException {
		return model.getPropertiesOf(type);
	}

	@Override
	public boolean hasProperty(String type, String property) throws EolModelElementTypeNotFoundException {
		return model.hasProperty(type, property);
	}

	@Override
	public boolean isEnumerationValue(Object object) {
		return model.isEnumerationValue(object);
	}

	@Override
	public String getEnumerationTypeOf(Object literal) throws EolNotAnEnumerationValueException {
		return model.getEnumerationTypeOf(literal);
	}

	@Override
	public String getEnumerationLabelOf(Object literal) throws EolNotAnEnumerationValueException {
		return model.getEnumerationLabelOf(literal);
	}
	
	@Override
	public <T> T adaptTo(Class<T> modelType) {
		if (modelType.isInstance(model)) {
			return modelType.cast(model);
		}
		else {
			return null;
		}
	}

}
