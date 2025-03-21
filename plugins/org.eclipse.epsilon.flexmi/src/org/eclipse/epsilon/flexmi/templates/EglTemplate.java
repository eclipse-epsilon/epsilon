/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.templates;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.List;

import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.flexmi.FlexmiFlavour;
import org.eclipse.epsilon.flexmi.FlexmiParseException;
import org.eclipse.epsilon.flexmi.FlexmiParser;
import org.eclipse.epsilon.flexmi.FlexmiResource;
import org.eclipse.epsilon.flexmi.xml.FlexmiXmlParser;
import org.eclipse.epsilon.flexmi.xml.Location;
import org.eclipse.epsilon.flexmi.xml.Xml;
import org.eclipse.epsilon.flexmi.yaml.FlexmiYamlParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class EglTemplate extends DynamicTemplate {

	protected EglModule module;
	protected FlexmiFlavour parserFlavour;

	public EglTemplate(Element element, FlexmiResource resource, URI uri, FlexmiFlavour parserFlavour) {
		super(element, resource, uri);

		this.parserFlavour = parserFlavour;
	}

	@Override
	public List<Element> getApplication(Element call) {
		try {
			if (module == null) {
				module = new EglModule(new EglTemplateFactory());
				parse(module, getScript(), uri);
				if (!module.getParseProblems().isEmpty())
					throw new RuntimeException(module.getParseProblems().get(0).toString());
			}
			
			module.getContext().getFrameStack().enterLocal(FrameType.UNPROTECTED, module);

			prepareModule(module, call);
			
			String generated = (module.execute() + "").trim();
			FlexmiParser parser;
			
			if (parserFlavour == FlexmiFlavour.XML) {
				generated = "<?xml version=\"1.0\"?><root>" + generated + "</root>";
				parser = new FlexmiXmlParser();
			}
			else {
				parser = new FlexmiYamlParser();
			}
			
			ByteArrayInputStream stream = new ByteArrayInputStream(generated.getBytes());
			Document document = parser.parse(stream);
			
			replaceSlots(call, document.getDocumentElement());

			module.getContext().getFrameStack().leaveLocal(module);

			// reset for next template application
			module.getCurrentTemplate().reset();

			return Xml.getChildren(document.getDocumentElement());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(new FlexmiParseException(e, 
					((Location) content.getUserData(Location.ID)).getStartLine()));
		}
	}

}
