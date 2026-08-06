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

import org.eclipse.epsilon.hutn.model.hutn.Spec;
import org.eclipse.epsilon.hutn.xmi.parser.generator.SpecGenerator;
import org.eclipse.epsilon.hutn.xmi.util.StringUtil;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SpecGeneratingContentHandler extends DefaultHandler {

	private final SpecGenerator generator = new SpecGenerator();
	
	public Spec getSpec() {
		return generator.getSpec();
	}
	
	private Locator locator;
	
	@Override
	public void setDocumentLocator(Locator locator) {
		this.locator = locator;
	}
	
	private int getCurrentLineNumber() {
		return locator.getLineNumber();
	}
	
	@Override
	public void startPrefixMapping(String prefix, String uri) {
		if ("xmi".equals(prefix) || "xsi".equals(prefix))
			return;
		
		generator.initialise(uri);
	}

	private boolean skipPopOnNextEndElement = false;
	private String currentElementName;
	
	@Override
    public void startElement (String uri, String name, String qName, Attributes atts) {
		currentElementName = name;
		
    	if (!generator.hasCurrentClassObject()) {
    		
    		// Don't add xmi:XMI elements, which are present in documents
    		// with more than one top level object
    		if (qName.equals("xmi:XMI"))
    			return;
    		
    		generator.generateTopLevelClassObject(atts.getValue("xmi:id"), name, getCurrentLineNumber());
    	
    	} else {
    		
    		if (atts.getIndex("href") >= 0) {
    			generator.addAttributeValue(name, atts.getValue("href"), getCurrentLineNumber());
    			skipPopOnNextEndElement = true;
    			
    		} else if (atts.getIndex("xsi:type") >= 0) {
    			generator.generateContainedClassObject(name, atts.getValue("xmi:id"), getLocalName(atts.getValue("xsi:type")), getCurrentLineNumber());
    			
			} else {
				// XMI doesn't include an xsi:type
				generator.generateContainedClassObject(name, atts.getValue("xmi:id"), getCurrentLineNumber());
			}
    		
    		
    	}
    	
    	processAttributes(atts);
    }
	
    @Override
	public void characters(char[] ch, int start, int length) throws SAXException {
    	final String text = new String(ch, start, length);
    	
    	if (StringUtil.isNotWhitespace(text)) {
			processMultiValuedAttribute(text);
    	}
	}
    
    @Override
    public void endElement (String uri, String name, String qName) {
    	if (generator.hasCurrentClassObject() && !skipPopOnNextEndElement) {
    		generator.stopGeneratingCurrentClassObject();
    	}
    	
    	skipPopOnNextEndElement = false;
    }
    
    private void processMultiValuedAttribute(String value) {
    	// At this point, startElement will have pushed a new class object
  
    	// remove the pushed class object
    	generator.stopGeneratingAndDeleteCurrentClassObject();
    	skipPopOnNextEndElement = true;
    	
    	// and replace with an attribute value
    	generator.addAttributeValue(currentElementName, value, getCurrentLineNumber());
    }
    

	private void processAttributes(Attributes atts) {
		for (int index = 0; index < atts.getLength(); index++) {
			
			if (attributeIsMetadata(atts.getQName(index))) {
			  	  continue;
			}

			generator.addAttributeValue(atts.getLocalName(index), atts.getValue(index), getCurrentLineNumber());
		}
	}

	private boolean attributeIsMetadata(String attributeName) {
		return attributeName.equals("href") || attributeName.startsWith("xmi") || attributeName.startsWith("xsi");
	}
    
    private static String getLocalName(String qualifiedName) {
    	return qualifiedName.split(":")[1];
    }
    
}
