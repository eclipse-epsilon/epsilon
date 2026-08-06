/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.yaml;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.flexmi.FlexmiFlavour;
import org.eclipse.epsilon.flexmi.FlexmiParseException;
import org.eclipse.epsilon.flexmi.FlexmiResource;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;
import org.eclipse.epsilon.flexmi.xml.FlexmiXmlParser;
import org.eclipse.epsilon.flexmi.xml.Location;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.scanner.ScannerException;

public class FlexmiYamlParser extends FlexmiXmlParser {

	private static final String TYPE_DECLARATION_KEY = "type";

	public static void main(String[] args) throws Exception {
		EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new FlexmiResourceFactory());
		
		FlexmiResource resource =
				(FlexmiResource) resourceSet.createResource(URI.createURI(FlexmiYamlParser.class.getResource("epackage-v2.yaml").toURI().toString()));
		resource.load(null);
		
		EolModule module = new EolModule();
		module.parse(FlexmiYamlParser.class.getResource("ecore.eol").toURI());
		module.getContext().getModelRepository().addModel(new InMemoryEmfModel(resource));
		module.execute();
	}
	
	@Override
	public Document parse(InputStream inputStream) throws Exception {
		Yaml yaml = new Yaml(new LocatedSafeConstructor(new LoaderOptions()));
		try {
			Object root = yaml.load(new InputStreamReader(inputStream));
			Document document = toDocument(root);
			//System.out.println(toXml(document));
			return document;
		}
		catch (ScannerException ex) {
			int line = 0;
			if (ex.getProblemMark() != null) line = ex.getProblemMark().getLine();
			if (ex.getContextMark() != null) line = ex.getContextMark().getLine();
			throw new FlexmiParseException(ex, line + 1);
		}
	}
	
	@Override
	public FlexmiFlavour getFlavour() {
		return FlexmiFlavour.YAML;
	}
	
	protected Document toDocument(Object root) throws Exception {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element element = toRoot(root, document, document);
		document.appendChild(element);
		return document;
	}

	@SuppressWarnings("unchecked")
	protected Element toRoot(Object o, org.w3c.dom.Node parent, Document doc) {
		if (o instanceof LocatedMap) {
			return toElement("_", (LocatedMap<Object, Object>) o, parent, doc);
		} else {
			throw new IllegalArgumentException("Expected a map");
		}
	}

	@SuppressWarnings("unchecked")
	protected Element toElement(String tag, LocatedMap<Object, Object> lm, org.w3c.dom.Node parent, Document doc) {
		Element element = createElement(doc, tag, lm.getLocation());

		for (Entry<Object, Object> e : lm.entrySet()) {
			String key = e.getKey() + "";
			Object value = e.getValue();

			if (isPIKey(key)) {
				// Keys starting with $ and ? (other than the type declaration) become XML PIs
				String keyName = key.substring(1);
				if (!TYPE_DECLARATION_KEY.equals(keyName)) {
					ProcessingInstruction pi = doc.createProcessingInstruction(keyName, value + "");
					element.appendChild(pi);
				} else {
					// ignore: $type is used to use a different tag name for an element
				}
			} else if (isScalarValue(value)) {
				// Generate key=value
				element.setAttribute(key, value + "");
			} else if (isScalarList(value)) {
				// Generate <key>val1</key><key>val2</key>...<key>valN</key>
				LocatedList<Object> ll = (LocatedList<Object>) value;

				for (int i = 0; i < ll.size(); i++) {
					Node loc = ll.getLocation(i);
					Element scalarElement = createElement(doc, key, loc);
					scalarElement.setTextContent(ll.get(i) + "");
					element.appendChild(scalarElement);
				}
			} else if (isMapValue(value)) {
				// Generate <key .../>
				LocatedMap<Object, Object> lmv = (LocatedMap<Object, Object>) value;
				String childTag = computeTagFromMapPair(key, lmv);
				Element nested = toElement(childTag, lmv, element, doc);
				element.appendChild(nested);
			} else if (isMapList(value)) {
				// Generate <key .../><key .../>...<key .../>
				LocatedList<Object> ll = (LocatedList<Object>) value;
				for (int i = 0; i < ll.size(); i++) {
					LocatedMap<Object, Object> llv = (LocatedMap<Object, Object>) ll.get(i);
					String childTag = computeTagFromMapPair(key, llv);
					Element nested = toElement(childTag, llv, element, doc);
					element.appendChild(nested);
				}
			} else {
				throw new IllegalArgumentException("Cannot transform value " + value);
			}
		}

		return element;
	}

	protected String computeTagFromMapPair(String key, Map<Object, Object> value) {
		Object typeDecl = value.get("$" + TYPE_DECLARATION_KEY);
		if (typeDecl == null) {
			typeDecl = value.get("?" + TYPE_DECLARATION_KEY);
		}
		return typeDecl != null ? typeDecl + "" : key;
	}

	protected Element createElement(Document document, String tagName, Node yamlNode) {
		Element scalarElement = document.createElement(tagName);
		scalarElement.setUserData(Location.ID, new Location(yamlNode.getStartMark().getLine()+1, yamlNode.getStartMark().getColumn(), 
				yamlNode.getEndMark().getLine()+1, yamlNode.getEndMark().getColumn()), null);
		return scalarElement;
	}
	
	protected boolean isPIKey(String key) {
		return key.startsWith("$") || key.startsWith("?");
	}

	protected boolean isScalarValue(Object o) {
		return !(o instanceof Map || o instanceof Collection);
	}

	protected boolean isMapValue(Object o) {
		return o instanceof LocatedMap;
	}

	protected boolean isScalarList(Object o) {
		return o instanceof LocatedList && ((List<?>) o).stream().allMatch(e -> isScalarValue(e)); 
	}

	protected boolean isMapList(Object o) {
		if (o instanceof LocatedList) {
			boolean allMaps = ((List<?>) o).stream().allMatch(e -> isMapValue(e));
			return allMaps;
		}
		return false;
	}

	// For debugging only
	protected String toXml(Document document) {
		Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		try (StringWriter writer = new StringWriter()) {
			StreamResult result = new StreamResult(writer);
			transformer.transform(new DOMSource(document.getDocumentElement()), result);
			return writer.toString();
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
}
