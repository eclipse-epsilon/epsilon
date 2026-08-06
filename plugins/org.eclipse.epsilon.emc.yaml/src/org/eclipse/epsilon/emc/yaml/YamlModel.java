/*******************************************************************************
 * Copyright (c) 2022 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Ionut Predoaia - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.yaml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.execute.operations.contributors.IOperationContributorProvider;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.models.CachedModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

public class YamlModel extends CachedModel<Entry> implements IOperationContributorProvider {

	protected File file;
	protected Object yamlContent;
	protected YamlModelOperationContributor yamlModelOperationContributor;
	protected List<Entry> createdNodes = new ArrayList<>();
	
	public YamlModel() {
		propertyGetter = new YamlPropertyGetter(this);
		propertySetter = new YamlPropertySetter(this);
	}
	
	public synchronized void clear() {
		this.yamlContent = null;
		this.createdNodes.clear();
	}
	
	public synchronized Object getYamlContent() {
		return this.yamlContent;
	}
	
	public synchronized void setYamlContent(Object yamlContent) {
		this.yamlContent = yamlContent;
	}
	
	public synchronized void setYamlContent(String yamlContent) {
		this.yamlContent = YamlNodeUtility.getYamlContent(yamlContent);
	}
	
	public synchronized Object getRoot() {
		return YamlNodeUtility.getRootNode(this.yamlContent);
	}
	
	public synchronized void setRootAsList() {
		this.yamlContent = new ArrayList<>();
	}
	
	public synchronized void setRootAsMap() {
		this.yamlContent = new LinkedHashMap<String, Object>();
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	@Override
	public boolean isLoaded() {
		return (this.yamlContent != null);
	}
	
	@Override
	public Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException {
		return null;
	}

	@Override
	public String getTypeNameOf(Object instance) {
		return (instance instanceof Entry) ? YamlNodeUtility.getTypeNameOf((Entry)instance) : instance.getClass().getName();
	}
	
	@Override
	public Object getTypeOf(Object instance) {
		return instance.getClass();
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
	public void setElementId(Object instance, String newId) {}

	@Override
	public boolean owns(Object instance) {
		return YamlNodeUtility.ownsYamlObject(this.yamlContent, createdNodes, instance);
	}

	@Override
	public boolean isInstantiable(String type) {
		return hasType(type);
	}

	@Override
	public boolean hasType(String type) {
		return (YamlProperty.parse(type, 1) != null);
	}
	
	@Override
	public boolean isModelElement(Object instance) {
		return (instance instanceof Entry);
	}

	@Override
	public synchronized boolean store(String location) {
		try {
			YamlNodeUtility.storeYamlContent(this.file, this.yamlContent);
		    return true;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean store() {
		if (file != null) {
			return store(file.getAbsolutePath());
		}
		return false;
	}

	@Override
	protected synchronized Collection<Entry> allContentsFromModel() {
		YamlProperty yamlProperty = YamlProperty.parse(YamlNodeType.Node.toString(), 0);
		return YamlNodeUtility.getNodes(this.yamlContent, yamlProperty, true);
	}

	@Override
	protected synchronized Collection<Entry> getAllOfTypeFromModel(String type) throws EolModelElementTypeNotFoundException {
		YamlProperty yamlProperty = YamlProperty.parse(type, 1);		
		return YamlNodeUtility.getNodes(this.yamlContent, yamlProperty, true);
	}	

	@Override
	protected Collection<Entry> getAllOfKindFromModel(String kind) throws EolModelElementTypeNotFoundException {
		return getAllOfTypeFromModel(kind);
	}

	@Override
	protected Entry createInstanceInModel(String type) throws EolModelElementTypeNotFoundException {		
		return createInstance(type, Collections.emptyList());
	}	

	@Override
	public synchronized Entry createInstance(String type, Collection<Object> parameters) throws EolModelElementTypeNotFoundException {
		Entry newNode = YamlNodeUtility.getNode(type, 1, parameters);
		createdNodes.add(newNode);
		return newNode;		
	}
	
	@Override
	protected void disposeModel() {
		clear();
	}

	@Override
	protected synchronized boolean deleteElementInModel(Object instance) throws EolRuntimeException {
		if (!(instance instanceof Entry)) {
			return false;
		}
		Entry node = (Entry) instance;
		return YamlNodeUtility.deleteNode(this.yamlContent, this.createdNodes, node);
	}

	@Override
	protected Object getCacheKeyForType(String type) throws EolModelElementTypeNotFoundException {
		return type;
	}

	@Override
	protected Collection<String> getAllTypeNamesOf(Object instance) {
		return Collections.singleton(getTypeNameOf(instance));
	}
	
	@Override
	protected synchronized void loadModel() throws EolModelLoadingException {
		try {
			if (readOnLoad) {
				if (this.file != null) {
					this.yamlContent = YamlNodeUtility.getYamlContent(this.file);
				}
			}
	    	this.yamlModelOperationContributor = new YamlModelOperationContributor(this);
	    	this.createdNodes = new ArrayList<>();
		}
		catch (Exception ex) {
			throw new EolModelLoadingException(ex, this);
		}
	}
	
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		super.load(properties, resolver);
		String filePath = properties.getProperty(YamlProperty.PROPERTY_FILE);
		if (!StringUtil.isEmpty(filePath)) {
			file = new File(resolver != null ? resolver.resolve(filePath) : filePath);
		}
		load();
	}

	@Override
	public OperationContributor getOperationContributor() {
		return yamlModelOperationContributor;
	}
}