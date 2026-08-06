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

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.flexmi.FlexmiDiagnostic;
import org.eclipse.epsilon.flexmi.FlexmiFlavour;
import org.eclipse.epsilon.flexmi.FlexmiParseException;
import org.eclipse.epsilon.flexmi.FlexmiParser;
import org.eclipse.epsilon.flexmi.FlexmiResource;
import org.eclipse.epsilon.flexmi.templates.Template;
import org.eclipse.epsilon.flexmi.templates.TemplateFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

public class FlexmiXmlParser implements FlexmiParser {
	
	protected FlexmiResource resource;
	protected URI uri;
	
	public void parse(FlexmiResource resource, InputStream inputStream, Handler handler) throws FlexmiParseException {
		try {
			parse(resource, resource.getURI(), inputStream, handler, true);
		} catch (Exception e) {
			if (e instanceof SAXParseException) {
				throw new FlexmiParseException(e, ((SAXParseException) e).getLineNumber());
			}
			else if (e instanceof TransformerException && e.getCause() instanceof SAXParseException) {
				throw new FlexmiParseException(e, ((SAXParseException) e.getCause()).getLineNumber());
			}
			else if (e instanceof FlexmiParseException) {
				throw (FlexmiParseException) e;
			}
			else if (e instanceof RuntimeException && e.getCause() instanceof FlexmiParseException) {
				throw (FlexmiParseException) e.getCause();
			}
			else {
				throw new FlexmiParseException(e);
			}
		}
	}
	
	public void parse(FlexmiResource resource, URI uri, InputStream inputStream, Handler handler, boolean processDocument) throws Exception  {
		
		Document document = parse(inputStream);
		
		this.resource = resource;
		this.uri = uri;
		
		if (isFlexmiRootNode(document.getDocumentElement())) {
			for (Element templateElement : Xml.getChildren(document.getDocumentElement(), Template.NODE_NAME)) {
				resource.addTemplate(TemplateFactory.getInstance().createTemplate(
						templateElement, resource, new java.net.URI(uri.toString()), getFlavour()));
				document.getDocumentElement().removeChild(templateElement);
			}
		}
		else {
			if (isTemplate(document.getDocumentElement())) {
				resource.addTemplate(TemplateFactory.getInstance().createTemplate(
						document.getDocumentElement(), resource, new java.net.URI(uri.toString()), getFlavour()));
				document.removeChild(document.getDocumentElement());
				return;
			}
		}
		
		if (processDocument) handler.startDocument(document);
		visit(document, handler);
		if (processDocument) handler.endDocument(document);
	}
	
	public Document parse(InputStream inputStream) throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.newDocument();
		document.setStrictErrorChecking(false);
		
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		
		transformer.transform(new SAXSource(new LocationRecorder(xmlReader,document), new InputSource(inputStream)), new DOMResult(document));
		return document;
	}
	
	protected void visit(Node node, Handler handler) throws Exception {
		Template template = null;

		if (node instanceof ProcessingInstruction) {
			ProcessingInstruction processingInstruction = (ProcessingInstruction) node;
			String key = processingInstruction.getTarget();
			String value = processingInstruction.getData();
			
			if (key.equalsIgnoreCase("include")) {
				try {
					URI includedURI = URI.createURI(value).resolve(uri);
					InputStream includedInputStream = resource.getResourceSet().getURIConverter().createInputStream(includedURI);

					BufferedInputStream bufferedInputStream = new BufferedInputStream(includedInputStream);
					FlexmiParser parser = resource.createParser(bufferedInputStream);

					resource.startProcessingFragment(includedURI);
					parser.parse(resource, includedURI, bufferedInputStream, handler, false);
					resource.endProcessingFragment();
				}
				catch (Exception ex) {
					resource.getWarnings().add(new FlexmiDiagnostic(ex.getMessage(), uri, resource.getLineNumber(processingInstruction)));
				}
			}
			else if (key.equalsIgnoreCase("import")) {
				try {
					URI importedURI = URI.createURI(value).resolve(uri);
					Resource importedResource = resource.getResourceSet().getResource(importedURI, false);
					if (importedResource == null) {
						importedResource = resource.getResourceSet().createResource(importedURI);
						if (!importedResource.isLoaded()) importedResource.load(null);
						if (importedResource instanceof FlexmiResource) {
							((FlexmiResource) importedResource).setImportedFrom(resource);
						}
					}
					else if (!resource.getResourceSet().getResources().contains(importedResource)){
						// e.g. importing a metamodel in the EPackage.Registry
						Resource copy = new ResourceImpl(importedURI);
						copy.getContents().addAll(EcoreUtil.copyAll(importedResource.getContents()));
						resource.getResourceSet().getResources().add(copy);
					}
					
				}
				catch (Exception ex) {
					resource.getWarnings().add(new FlexmiDiagnostic(ex.getMessage(), uri, resource.getLineNumber(processingInstruction)));					
				}
			}
			else {
				handler.processingInstruction((ProcessingInstruction) node);
			}
		}
		else if (node instanceof Element) {
			Element element = (Element) node;
			if (!isFlexmiRootNode(element)) {
				String templateName = element.getNodeName();
				
				for (Node attribute : Xml.getAttributes(element)) {
					if (attribute.getNodeName().endsWith("_")) {
						try {
							URI external = URI.createFileURI(attribute.getNodeValue()).resolve(uri);
							try (Scanner scanner = new Scanner(new URL(external.toString()).openStream())) {
								attribute.setNodeValue(scanner.useDelimiter("\\Z").next().trim());
							}
						}
						catch (Exception ex) {
							resource.getWarnings().add(new FlexmiDiagnostic(ex.getMessage(), uri, resource.getLineNumber(element)));
						}
					}
				}
				
				for (Resource resource : this.resource.getResourceSet().getResources()) {
					if (resource instanceof FlexmiResource) {
						template = ((FlexmiResource) resource).getTemplate(templateName);
						if (template != null) {
							// ((FlexmiResource) resource).startProcessingFragment(resource.getURI());
							for (Element applicationElement : template.apply(element)) {
								visit(applicationElement, handler);
							}
							break;
							// ((FlexmiResource) resource).endProcessingFragment();
						}
					}
				}
				
				if (template == null) {
					handler.startElement(element);
				}			
			}
		}
		
		if (template == null) {
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				visit(node.getChildNodes().item(i), handler);
			}
			if (node instanceof Element) {
				if (!isFlexmiRootNode((Element) node)) handler.endElement((Element) node);
			}
		}
	}
	
	protected boolean isFlexmiRootNode(Element element) {
		return element.getNodeName().equals(FlexmiResource.ROOT_NODE_NAME) && element.getParentNode() instanceof Document;
	}
	
	protected boolean isTemplate(Element element) {
		return element.getNodeName().equals(Template.NODE_NAME);
	}
	
	@Override
	public FlexmiFlavour getFlavour() {
		return FlexmiFlavour.XML;
	}
	
	
	public interface Handler {

		public void startDocument(Document document);
		
		public void endDocument(Document document);
		
		public void startElement(Element element);

		public void endElement(Element elemen);

		public void processingInstruction(ProcessingInstruction processingInstruction);
	}

}
