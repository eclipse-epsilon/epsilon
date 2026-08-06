/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.xmi.parser.sax;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class ContentHandlerMultiplexer implements ContentHandler {
	
	private final List<ContentHandler> handlers = new LinkedList<>();
	
	public ContentHandlerMultiplexer(ContentHandler... handlers) {
		this.handlers.addAll(Arrays.asList(handlers));
	}
	
	public void startDocument() throws SAXException {
		for (ContentHandler handler : handlers) {
			handler.startDocument();
		}
	}
	
	public void endDocument() throws SAXException {
		for (ContentHandler handler : handlers) {
			handler.endDocument();
		}
	}
	
	
	
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		for (ContentHandler handler : handlers) {
			handler.startElement(uri, localName, qName, atts);
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		for (ContentHandler handler : handlers) {
			handler.endElement(uri, localName, qName);
		}
	}
	
	

	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		for (ContentHandler handler : handlers) {
			handler.startPrefixMapping(prefix, uri);
		}
	}
	
	public void endPrefixMapping(String prefix) throws SAXException {
		for (ContentHandler handler : handlers) {
			handler.endPrefixMapping(prefix);
		}
	}

	
	public void characters(char[] ch, int start, int length) throws SAXException {
		for (ContentHandler handler : handlers) {
			handler.characters(ch, start, length);
		}
	}
	

	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		for (ContentHandler handler : handlers) {
			handler.ignorableWhitespace(ch, start, length);
		}
	}

	public void processingInstruction(String target, String data) throws SAXException {
		for (ContentHandler handler : handlers) {
			handler.processingInstruction(target, data);
		}
	}

	public void setDocumentLocator(Locator locator) {
		for (ContentHandler handler : handlers) {
			handler.setDocumentLocator(locator);
		}
	}

	public void skippedEntity(String name) throws SAXException {
		for (ContentHandler handler : handlers) {
			handler.skippedEntity(name);
		}
	}
}
