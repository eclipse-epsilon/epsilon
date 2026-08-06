/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.transformers.elements;

import java.io.InputStream;
import java.nio.file.*;
import java.util.LinkedHashSet;
import java.util.Set;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.picto.transformers.ExternalContentTransformation;
import org.w3c.dom.Element;

public class AbsolutePathElementTransformer extends AbstractHtmlElementTransformer {
	
	protected String elementName;
	protected String attributeName;
	
	public AbsolutePathElementTransformer(String elementName, String attributeName) {
		this.elementName = elementName;
		this.attributeName = attributeName;
	}
	
	@Override
	public String getXPath() {
		return "//*[local-name() = '" + elementName + "']";
	}

	@Override
	public void transform(Element element) {
		
		Set<java.net.URI> baseUris = new LinkedHashSet<>(viewContent.getBaseUris());
		if (viewContent.getFile() != null) {
			baseUris.add(viewContent.getFile().toURI());
		}
		
		String attributeValue = element.getAttribute(attributeName);
		
		if (isValidAttribute(attributeValue)) { 
			try {
				java.net.URI uri = new java.net.URI(attributeValue);
				if (!uri.isAbsolute()) {
					for (java.net.URI baseUri : baseUris) {
						java.net.URI fileUri = baseUri.resolve(attributeValue);
						try (InputStream in = fileUri.toURL().openStream()) {
							if ("file".equals(fileUri.getScheme())) {
								element.setAttribute(attributeName, fileUri.toString());
							}
							else {
								String extension = FileUtil.getExtension(attributeValue);
								Path temp = ExternalContentTransformation.createTempFile(extension);
								Files.copy(in, temp, StandardCopyOption.REPLACE_EXISTING);
								element.setAttribute(attributeName, temp.toAbsolutePath().toString());
							}
							break;
						}
						catch (Exception ex) {
							// Try the next one
						}
					}
				}
			}
			catch (Exception ex) {
				// Ignored
			}
		}
	}
	
	protected boolean isValidAttribute(String attributeValue) {
		return attributeValue != null && !attributeValue.trim().isEmpty();
	}

}
