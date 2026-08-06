/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.xml;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class Xml {
	
	static DocumentBuilder documentBuilder = null;
	static TransformerFactory transformerFactory = null;
	static SAXParserFactory saxParserFactory = null;
	
	static {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			transformerFactory = TransformerFactory.newInstance();
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			saxParserFactory = SAXParserFactory.newInstance();
		}
		catch (Exception ex) { throw new RuntimeException(ex); }
	}
	
	public static List<Node> getAttributes(Element e) {
		List<Node> attributes = new ArrayList<>();
		NamedNodeMap nodeMap = e.getAttributes();
		for (int i = 0; i < nodeMap.getLength(); i++) {
			attributes.add(nodeMap.item(i));
		}
		return attributes;
	}
	
	public static List<String> getAttributeNames(Element e) {
		NamedNodeMap attributes = e.getAttributes();
		final int attrLen = attributes.getLength();
		List<String> names = new ArrayList<>(attrLen);
		for (int i = 0; i < attrLen; i++) {
			names.add(attributes.item(i).getNodeName());
		}
		return names;
	}
	
	public static List<Element> getDescendant(Element e, String name) {
		return toElementList(e.getElementsByTagName(name));
	}
	
	public static Element getChild(Element e, String name) {
		List<Element> children = getChildren(e, name);
		if (children.isEmpty()) {
			return null;
		}
		else {
			return children.get(0);
		}
	}
	
	public static List<Element> toElementList(NodeList nodeList) {
		List<Element> node = new ArrayList<>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node child = nodeList.item(i);
			if (child instanceof Element) {
				Element childElement = (Element) child;
				node.add(childElement);
			}
		}
		return node;
	}
	
	public static List<Element> getChildren(Element e) {
		return toElementList(e.getChildNodes());
	}
	
	public static List<Element> getChildren(Element e, String name) {
		List<Element> children = new ArrayList<>();
		for (int i = 0; i < e.getChildNodes().getLength(); i++) {
			Node child = e.getChildNodes().item(i);
			if (child instanceof Element) {
				Element childElement = (Element) child;
				if (childElement.getNodeName().equals(name)) children.add(childElement);
			}
		}
		return children;
	}
	
	public static Document parse(String xml) throws Exception {
		Transformer transformer = transformerFactory.newTransformer();
		Document document = documentBuilder.newDocument();
		document.setStrictErrorChecking(false);
		SAXParser saxParser = saxParserFactory.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		transformer.transform(new SAXSource(new LocationRecorder(xmlReader,document), new InputSource(new ByteArrayInputStream(xml.getBytes()))), new DOMResult(document));
		return document;
	}
	
}
