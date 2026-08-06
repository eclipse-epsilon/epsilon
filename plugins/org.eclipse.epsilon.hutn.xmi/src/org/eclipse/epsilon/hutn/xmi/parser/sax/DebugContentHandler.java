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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DebugContentHandler extends DefaultHandler {
	
	@Override
	public void startDocument() {
		System.out.println("start document");
	}
	
	@Override
	public void endDocument() {
		System.out.println("end document");
	}
    
	@Override
    public void startElement (String uri, String name, String qName, Attributes atts) {
    	if ("".equals (uri))
    		System.out.println("Start element: " + qName);
    	else
    		System.out.println("Start element: {" + uri + "}" + name);

    	for (int index = 0; index < atts.getLength(); index++) {
    		System.out.print("\t");
			System.out.print(" uri: " + atts.getURI(index));
			System.out.print(" local: " + atts.getLocalName(index));
			System.out.print(" qname: " + atts.getQName(index));
			System.out.println(" value: " + atts.getValue(index));
		}
    }

    @Override
    public void endElement (String uri, String name, String qName) {
    	if ("".equals (uri))
    		System.out.println("End element: " + qName);
    	else
    		System.out.println("End element:   {" + uri + "}" + name);

    }

    @Override
	public void characters(char[] ch, int start, int length) throws SAXException {
    	System.out.println("Characters: " + new String(ch, start, length));
	}
    
    
    @Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		System.out.println("Start prefix mapping: " + prefix + " to " + uri);
	}
    	
	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		System.out.println("End prefix mapping: " + prefix);
	}
}
