/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.plainxml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.operations.contributors.IOperationContributorProvider;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.models.CachedModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PlainXmlModel extends CachedModel<Element> implements IOperationContributorProvider {
	
	protected String idAttributeName = "id";
	
	protected Document document;
	protected String uri;
	protected File file;
	protected String xml;
	protected PlainXMLOperationContributor operationContributor = new PlainXMLOperationContributor();
	
	protected ArrayList<Element> createdElements = new ArrayList<>();
	protected ArrayList<Binding> bindings = new ArrayList<>();
	protected static final String ELEMENT_TYPE = "Element";
	protected static final String DEFAULT_NEW_TAG_NAME = "element";
	public static final String PROPERTY_FILE = "file";
	public static final String PROPERTY_URI = "uri";
	public static final String PROPERTY_EXPAND = "expand";
	
	public PlainXmlModel() {
		propertyGetter = new PlainXmlPropertyGetter(this);
		propertySetter = new PlainXmlPropertySetter(this);
	}
	
	public synchronized Node getRoot() {
		return document.getFirstChild();
	}
	
	public synchronized void setRoot(Node node) {
		Node oldRoot = getRoot();
		if (oldRoot != null) document.removeChild(oldRoot);
		document.appendChild(node);
	}
	
	@Override
	protected synchronized Collection<Element> allContentsFromModel() {
		Collection<Element> elements = new ArrayList<>();
		collectAllElements(document, elements);
		for (Element created : createdElements) {
			if (!elements.contains(created) && created.getParentNode() == null) {
				elements.add(created);
			}
		}	
		return elements;
	}
	
	public List<Binding> getBindings() {
		return bindings;
	}
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public synchronized String getXml() {
		try (StringWriter sw = new StringWriter()) {
			Source xmlSource = new DOMSource(document);
			Result result = new StreamResult(sw);
	
			// create TransformerFactory
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
	
			// create Transformer for transformation
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");	//Java XML Indent
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			// transform and deliver content to client
			transformer.transform(xmlSource, result);
			return sw.toString();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public void setXml(String xml) {
		this.xml = xml;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Document getDocument() {
		return document;
	}
	
	public void bind(String sourceTag, String sourceAttribute, String targetTag, String targetAttribute, boolean many) {
		bindings.add(new Binding(sourceTag, sourceAttribute, targetTag, targetAttribute, many));
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public boolean isLoaded() {
		return document != null;
	}
	
	@Override
	protected Element createInstanceInModel(String type) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		return createInstance(type, Collections.emptyList());
	}
	
	@Override
	public synchronized Element createInstance(String type, Collection<Object> parameters) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		String tagName = null;
		boolean root = false;
		
		if (ELEMENT_TYPE.equals(type)) {
			if (parameters.size() == 1) {
				tagName = parameters.iterator().next() + "";
			}
			else {
				tagName = DEFAULT_NEW_TAG_NAME;
			}
		}
		else {
			PlainXmlType plainXmlType = PlainXmlType.parse(type);
			if (plainXmlType != null) {
				tagName = plainXmlType.getTagName();
			}
			
			if (parameters.size() == 1) {
				Object param = parameters.iterator().next();
				if (param instanceof Boolean) {
					root = (boolean) param;
				}
			}
			
		}
		
		Element newElement = document.createElement(tagName);
		if (root == false) {
			createdElements.add(newElement);
		}
		else {
			document.appendChild(newElement);
		}

		return newElement;		
	}

	
	@Override
	protected synchronized boolean deleteElementInModel(Object instance) throws EolRuntimeException {
		if (!(instance instanceof Element))
			return false;
		
		Element e = (Element) instance;
		if (e.getParentNode() != null) {
			e.getParentNode().removeChild(e);
		}
		createdElements.remove(e);
		
		// Also remove all its children
		for (Element child : DomUtil.getChildren(e)) {
			deleteElement(child);
		}
			
		return true;
	}
	
	public synchronized void collectAllElements(Node root, Collection<Element> elements) {
		if (root instanceof Element) {
			elements.add((Element) root);
		}
		NodeList childNodes = root.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Object o = childNodes.item(i);
			if (o instanceof Element) {
				collectAllElements((Element) o, elements);
			}
		}
	}
	
	
	@Override
	protected Collection<Element> getAllOfKindFromModel(String type) throws EolModelElementTypeNotFoundException {
		return getAllOfTypeFromModel(type);
	}

	@Override
	protected Collection<Element> getAllOfTypeFromModel(String type) throws EolModelElementTypeNotFoundException {
		if (ELEMENT_TYPE.equals(type)) {
			return allContents();
		}

		PlainXmlType plainXmlType = PlainXmlType.parse(type);
		
		if (plainXmlType == null) {
			throw new EolModelElementTypeNotFoundException(this.getName(), type);
		}
		
		String tagName = plainXmlType.getTagName();
		
		Collection<Element> allOfType = new ArrayList<>();
		for (Element e : allContents()) {
			if (tagMatches(e, tagName)) {
				allOfType.add(e);
			}
		}
		
		return allOfType;
	}
	
	public synchronized boolean tagMatches(Element element, String name) {
		if (element.getTagName().equalsIgnoreCase(name)) {
			return true;
		}
		else {
			int colonIndex = element.getTagName().indexOf(":");
			if (colonIndex >= 0) {
				return element.getTagName().substring(colonIndex + 1).equalsIgnoreCase(name);
			}
			else {
				return false;
			}
		}
	}
	
	@Override
	protected void disposeModel() {
		document = null;
		xml = null;
	}

	@Override
	public Object getElementById(String id) {
		for (Object o : allContents()) {
			Element e = ((Element) o);
			if (e.hasAttribute(idAttributeName) && e.getAttribute(idAttributeName).equals(id)) {
				return e;
			}
		}
		return null;
	}
	
	@Override
	public String getElementId(Object instance) {
		if (instance instanceof Element) {
			Element element = (Element) instance;
			if (element.hasAttribute(idAttributeName)) {
				return element.getAttribute(idAttributeName);
			}
		}
		return null;
	}
	
	@Override
	public void setElementId(Object instance, String newId) {
		// do nothing
	}

	public void setIdAttributeName(String idAttributeName) {
		this.idAttributeName = idAttributeName;
	}
	
	public String getIdAttributeName() {
		return idAttributeName;
	}
	
	@Override
	public Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException {
		return null;
	}

	
	@Override
	public String getTypeNameOf(Object instance) {
		if (instance instanceof Element) {
			return "t_" + ((Element) instance).getTagName();
		}
		else {
			return instance.getClass().getName();
		}
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
	public Object getTypeOf(Object instance) {
		return instance.getClass();
	}

	
	@Override
	public boolean hasType(String type) {
		return ELEMENT_TYPE.equals(type) || (PlainXmlType.parse(type) != null);
	}

	
	@Override
	public boolean isInstantiable(String type) {
		return hasType(type);
	}

	
	@Override
	public boolean isModelElement(Object instance) {
		return (instance instanceof Element);
	}
	

	protected boolean expandEntityReferences = true;

	public boolean isExpandEntityReferences() {
		return expandEntityReferences;
	}

	public void setExpandEntityReferences(boolean expandEntityReferences) {
		this.expandEntityReferences = expandEntityReferences;
	}

	@Override
	protected synchronized void loadModel() throws EolModelLoadingException {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setExpandEntityReferences(this.expandEntityReferences);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			
			if (readOnLoad) {
				if (this.file != null) {
					document = documentBuilder.parse(this.file);
				}
				else if (this.uri != null) {
					document = documentBuilder.parse(this.uri);
				}
				else {
					document = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
				}
			}
			else {
				document = documentBuilder.newDocument();
			}
		}
		catch (Exception ex) {
			throw new EolModelLoadingException(ex, this);
		}
	}

	
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		super.load(properties, resolver);
		
		String filePath = properties.getProperty(PlainXmlModel.PROPERTY_FILE);
		
		if (!StringUtil.isEmpty(filePath)) {
			file = new File(resolver != null ? resolver.resolve(filePath) : filePath);
		}
		else {
			uri = properties.getProperty(PlainXmlModel.PROPERTY_URI);
		}
		
		if (properties.hasProperty(PlainXmlModel.PROPERTY_EXPAND)) {
			this.expandEntityReferences = Boolean.parseBoolean(
				properties.getProperty(PlainXmlModel.PROPERTY_EXPAND, "true"));
		}

		load();
	}
	
	
	@Override
	public boolean owns(Object instance) {
		if (instance instanceof Element) synchronized (this) {
			Element e = (Element) instance;
			Node parent = e.getParentNode();
			
			if (parent == null) {
				return createdElements.contains(instance);
			}
			else {
				while (parent.getParentNode() != null) {
					parent = parent.getParentNode();
				}
				return parent == document || createdElements.contains(parent);
			}
		}
		else {
			return false;
		}
	}

	
	@Override
	public synchronized boolean store(String location) {
		try {
			Source xmlSource = new DOMSource(document);
			Result result = new StreamResult(new FileOutputStream(location));
	
			// create TransformerFactory
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
	
			// create Transformer for transformation
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");	//Java XML Indent
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			// transform and deliver content to client
			transformer.transform(xmlSource, result);
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
		else {
			return store(uri);
		}
	}

	@Override
	public OperationContributor getOperationContributor() {
		return operationContributor;
	}
}
