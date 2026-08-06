/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.filesystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.models.Model;


public class FilesystemModel extends Model {
	
	// Map of directories -> files they contain
	protected Map<File, List<File>> cache = new HashMap<>();
	
	public FilesystemModel(File root) {
		populateCache(root, cache);
	}
	
	@Override
	public boolean hasType(String type) {
		return directoryForName(type) != null;
	}
	
	@Override
	public Collection<?> getAllOfKind(String type)
			throws EolModelElementTypeNotFoundException {
		return cache.get(directoryForName(type));
	}
	
	@Override
	public boolean owns(Object instance) {
		for (File directory : cache.keySet()) {
			if (cache.get(directory).contains(instance)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public IPropertyGetter getPropertyGetter() {
		return new FilesystemModelPropertyGetter();
	}
	
	@Override
	public IPropertySetter getPropertySetter() {
		return new FilesystemModelPropertySetter();
	}
	
	@Override
	public Collection<?> getAllOfType(String type)
			throws EolModelElementTypeNotFoundException {
		
		return cache.get(directoryForName(type));
	}
	
	//TODO: Implement
	
	@Override
	public void load() throws EolModelLoadingException {
		
	}

	@Override
	public Object getEnumerationValue(String enumeration, String label)
			throws EolEnumerationValueNotFoundException {
		return null;
	}

	@Override
	public Collection<?> allContents() {
		return null;
	}

	@Override
	public String getTypeNameOf(Object instance) {
		return null;
	}

	@Override
	public Object createInstance(String type)
			throws EolModelElementTypeNotFoundException,
			EolNotInstantiableModelElementTypeException {
		return null;
	}

	@Override
	public Object getElementById(String id) {
		return null;
	}

	@Override
	public String getElementId(Object instance) {
		return null;
	}

	@Override
	public void setElementId(Object instance, String newId) {
		
	}

	@Override
	public void deleteElement(Object instance) throws EolRuntimeException {
		
	}

	@Override
	public boolean isInstantiable(String type) {
		return false;
	}

	@Override
	public boolean store(String location) {
		return false;
	}

	@Override
	public boolean store() {
		return false;
	}
	
	protected File directoryForName(String name) {
		for (File directory : cache.keySet()) {
			if (directory.getName().equals(name)) {
				return directory;
			}
		}
		return null;
	}
	
	protected void populateCache(File file, Map<File, List<File>> cache) {
		
		if (file.isDirectory()) {
			ArrayList<File> files = new ArrayList<>();
			for (File child : file.listFiles()) {
				if (child.isFile()) {
					files.add(child);
				}
				else {
					populateCache(child, cache);
				}
			}
			cache.put(file, files);
		}
	}
	
}